package com.lvmq.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lvmq.base.Argument;
import com.lvmq.base.Consts;
import com.lvmq.model.BalanceLog;
import com.lvmq.model.GoldLog;
import com.lvmq.model.UserLogin;
import com.lvmq.repository.BalanceLogRepository;
import com.lvmq.repository.GoldLogRepository;
import com.lvmq.repository.GoldRewardsRepository;
import com.lvmq.repository.UserLoginRepository;
import com.lvmq.service.UserLoginService;
import com.lvmq.util.MD5;
import com.lvmq.util.Util;

@Component
public class UserLoginServiceImpl implements UserLoginService{
	
	@Autowired
	private UserLoginRepository userLoginRepository;
	
	@Autowired
	private GoldRewardsRepository goldRewardsRepository;
	
	@Autowired
	private GoldLogRepository goldLogRepository;
	
	@Autowired
	private BalanceLogRepository balanceLogRepository;
	
	public int countByUserName(String userName) {
		return userLoginRepository.countByUserName(userName);
	}

	@Override
	public Optional<UserLogin> login(UserLogin userLogin) {
		// TODO Auto-generated method stub
		return userLoginRepository.findByUserNameAndPasswd(userLogin.getUserName(),userLogin.getPasswd());
	}
	
	public UserLogin save(UserLogin userLogin) {
		userLogin.setFlag(0);
		//邀请人数
		userLogin.setInviteCount(0);
		//余额
		userLogin.setBalance("0.00");
		//收益
		userLogin.setEarnings(0);
		
		userLogin.setFirstInvite("0");
		
		String gold=goldRewardsRepository.findByType(Consts.GoldLog.Type.REGISTER).getGold();
		
		userLogin.setGold(Long.valueOf(gold));
		
		
		
		String _myInviteCode=Util.getRandomReferralCode();
		while(userLoginRepository.countByMyInviteCode(_myInviteCode)>0) {
			_myInviteCode=Util.getRandomReferralCode();
		}
		
		userLogin.setMyInviteCode(_myInviteCode);
		
		UserLogin user=userLoginRepository.save(userLogin);
		
		GoldLog goldLog=new GoldLog();
		goldLog.setUserId(user.getId());
		goldLog.setType(Consts.GoldLog.Type.SIGN);
		goldLog.setNum(Integer.valueOf(gold));
		goldLog.setOldNum(0);
		goldLog.setNewNum(Integer.valueOf(gold));
		goldLog.setCreateUser(user.getId());
		goldLog.setCreateTime(new Date());
		goldLogRepository.save(new GoldLog());
		
		if(!Util.isBlank(userLogin.getInviteCode())) {
			
			//给邀请人添加邀请数量
			UserLogin inviteUser=userLoginRepository.findByMyInviteCode(userLogin.getInviteCode());
			if(inviteUser.getFirstInvite().equals("0")) {
				String money=goldRewardsRepository.findByType(Consts.BalanceLog.Type.FIRST_INVITE).getMoney();
				
				inviteUser.setBalance(String.valueOf(Double.valueOf(userLogin.getBalance())+Double.valueOf(money)));
				//首次召徒获得现金奖励
				inviteUser.setFirstInvite("1");
				balanceLogRepository.save(new BalanceLog(inviteUser.getId(),money,"0.00",money,Consts.BalanceLog.Type.FIRST_INVITE));
			}
			inviteUser.setInviteCount(inviteUser.getInviteCount()+1);
			userLoginRepository.save(inviteUser);
		}
		
		return user;
	}

	@Override
	public UserLogin updatePasswd(String userName,String passwd) {
		// TODO Auto-generated method stub
		UserLogin userLogin=userLoginRepository.findByUserName(userName);
		userLogin.setPasswd(MD5.getMD5(passwd));
		userLogin=userLoginRepository.save(userLogin);
		return userLogin;
	}
}
