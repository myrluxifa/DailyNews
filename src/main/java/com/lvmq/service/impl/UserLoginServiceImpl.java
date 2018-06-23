package com.lvmq.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.lvmq.api.res.LoginRes;
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
	
	public LoginRes findByUserId(String id) {
		return new LoginRes( userLoginRepository.findById(id).get());
		
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
		
		userLogin.setCreateTime(new Date());
		
		if(StringUtils.isEmpty(userLogin.getNewerMission()))
			// 任务主页 新手任务状态
			userLogin.setNewerMission("0|0|0|0");
		
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
		goldLogRepository.save(goldLog);
		
		
		if(!Util.isBlank(userLogin.getInviteCode())) {
			
			//给邀请人添加邀请数量
			UserLogin inviteUser=userLoginRepository.findByMyInviteCode(userLogin.getInviteCode());
			if(inviteUser!=null) {
				if(inviteUser.getFirstInvite().equals("0")) {
					String money=goldRewardsRepository.findByType(Consts.BalanceLog.Type.FIRST_INVITE).getMoney();
					
					inviteUser.setBalance(String.valueOf(Double.valueOf(inviteUser.getBalance())+Double.valueOf(money)));
					//首次召徒获得现金奖励
					inviteUser.setFirstInvite("1");
					balanceLogRepository.save(new BalanceLog(inviteUser.getId(),money,inviteUser.getBalance(),String.valueOf(Double.valueOf(inviteUser.getBalance())+Double.valueOf(money)),Consts.BalanceLog.Type.FIRST_INVITE));
				}
				inviteUser.setInviteCount(inviteUser.getInviteCount()+1);
				if(!Util.isBlank(inviteUser.getInviteCode())) {
					UserLogin masterMasetUser=userLoginRepository.findByMyInviteCode(inviteUser.getInviteCode());
					if(masterMasetUser.getGrandCnt()<2) {
						masterMasetUser.setGrandCnt(masterMasetUser.getGrandCnt()+1);
						user.setMasterMaster(masterMasetUser.getId());
						userLoginRepository.save(user);
					}
				}
				userLoginRepository.save(inviteUser);
				
				String invite_gold=goldRewardsRepository.findByType(Consts.GoldLog.Type.SET_INVITE).getGold();
				int updateGold=Integer.valueOf(invite_gold)+Integer.valueOf(gold);
				
				user.setGold(Long.valueOf(updateGold));
				userLoginRepository.save(user);
				
				GoldLog goldLogInvite=new GoldLog();
				goldLogInvite.setUserId(user.getId());
				goldLogInvite.setType(Consts.GoldLog.Type.SET_INVITE);
				goldLogInvite.setNum(Integer.valueOf(updateGold));
				goldLogInvite.setOldNum(Integer.valueOf(gold));
				goldLogInvite.setNewNum(Integer.valueOf(invite_gold));
				goldLogInvite.setCreateUser(user.getId());
				goldLogInvite.setCreateTime(new Date());
				goldLogRepository.save(goldLogInvite);
			}
			
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

	@Override
	public List<UserLogin> findByInviteCode(String inviteCode) {
		// TODO Auto-generated method stub
		return null;
	}
}
