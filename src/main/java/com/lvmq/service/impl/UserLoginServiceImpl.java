package com.lvmq.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.lvmq.api.res.EveryDayShareRes;
import com.lvmq.api.res.LoginRes;
import com.lvmq.base.Argument;
import com.lvmq.base.Consts;
import com.lvmq.model.BalanceLog;
import com.lvmq.model.DayMission;
import com.lvmq.model.GoldLog;
import com.lvmq.model.Official;
import com.lvmq.model.UserLogin;
import com.lvmq.repository.BalanceLogRepository;
import com.lvmq.repository.GoldLogRepository;
import com.lvmq.repository.GoldRewardsRepository;
import com.lvmq.repository.OfficialRepository;
import com.lvmq.repository.UserLoginRepository;
import com.lvmq.service.DayMissionService;
import com.lvmq.service.UserLoginService;
import com.lvmq.util.MD5;
import com.lvmq.util.NumberUtils;
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
	
	@Autowired
	private OfficialRepository officialRepository;
	
	@Autowired
	private DayMissionService dayMissionService;
	
	public int countByUserName(String userName) {
		return userLoginRepository.countByUserName(userName);
	}
	
	public LoginRes findByUserId(String id) {
		return new LoginRes( userLoginRepository.findById(id).get());
		
	}
	
	@Override
	public LoginRes findByUserId(String id, int cnt, int tag) {
		this.getUserEarnings(id);
		return new LoginRes( userLoginRepository.findById(id).get(), cnt, tag, this.getUserEarnings(id));
		
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
		
		userLogin.setGold(0l);
		//收益
		userLogin.setEarnings(0);
		
		userLogin.setFirstInvite("0");
		
		userLogin.setCreateTime(new Date());
		
		if(StringUtils.isEmpty(userLogin.getNewerMission()))
			// 任务主页 新手任务状态
			userLogin.setNewerMission("0|0|0|0");
		
		//String gold=goldRewardsRepository.findByType(Consts.GoldLog.Type.REGISTER).getGold();
		String balance=goldRewardsRepository.findByType(Consts.GoldLog.Type.REGISTER).getMoney();
		
		userLogin.setBalance(balance);
		
		
		
		String _myInviteCode=Util.getRandomReferralCode();
		while(userLoginRepository.countByMyInviteCode(_myInviteCode)>0) {
			_myInviteCode=Util.getRandomReferralCode();
		}
		
		userLogin.setMyInviteCode(_myInviteCode);
		
		String inviteCode=userLogin.getInviteCode()==null?"":userLogin.getInviteCode();
		userLogin.setInviteCode("");
		UserLogin user=userLoginRepository.save(userLogin);
		
//		GoldLog goldLog=new GoldLog();
//		goldLog.setUserId(user.getId());
//		goldLog.setType(Consts.GoldLog.Type.REGISTER);
//		goldLog.setNum(Integer.valueOf(gold));
//		goldLog.setOldNum(0);
//		goldLog.setNewNum(Integer.valueOf(gold));
//		goldLog.setCreateUser(user.getId());
//		goldLog.setCreateTime(new Date());
//		goldLogRepository.save(goldLog);
		BalanceLog balanceLog = new BalanceLog();
		balanceLog.setUserId(user.getId());
		balanceLog.setType(Consts.BalanceLog.Type.REGISTER);
		balanceLog.setNum(balance);
		balanceLog.setOldNum("0.00");
		balanceLog.setNewNum(balance);
		balanceLog.setCreateUser(user.getId());
		balanceLog.setCreateTime(new Date());
		balanceLogRepository.save(balanceLog);
		
		if(!Util.isBlank(inviteCode)) {
			boolean firstInvite = false;
			//给邀请人添加邀请数量
			UserLogin inviteUser=new UserLogin();
			
			if(inviteCode.length()>6) {
				inviteUser=userLoginRepository.findByUserName(inviteCode);
			}else {
				inviteUser=userLoginRepository.findByMyInviteCode(inviteCode);
			}
			if(inviteUser!=null) {
				if(inviteUser.getFirstInvite().equals("0")) {
					String money=goldRewardsRepository.findByType(Consts.BalanceLog.Type.FIRST_INVITE).getMoney();
					
					inviteUser.setBalance(String.valueOf(Double.valueOf(inviteUser.getBalance())+Double.valueOf(money)));
					//首次召徒获得现金奖励
					inviteUser.setFirstInvite("1");
					balanceLogRepository.save(new BalanceLog(inviteUser.getId(),money,inviteUser.getBalance(),String.valueOf(Double.valueOf(inviteUser.getBalance())+Double.valueOf(money)),Consts.BalanceLog.Type.FIRST_INVITE));
					firstInvite = true;
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
				int updateGold=(int) (Integer.valueOf(invite_gold)+user.getGold());
				
				long userGold=user.getGold();
				
				user.setGold(Long.valueOf(updateGold));
				user.setInviteCode(inviteUser.getMyInviteCode());
				userLoginRepository.save(user);
				
				GoldLog goldLogInvite=new GoldLog();
				goldLogInvite.setUserId(user.getId());
				goldLogInvite.setType(Consts.GoldLog.Type.SET_INVITE);
				goldLogInvite.setNum(Integer.valueOf(invite_gold));
				goldLogInvite.setOldNum(userGold);
				goldLogInvite.setNewNum(Integer.valueOf(updateGold));
				goldLogInvite.setCreateUser(user.getId());
				goldLogInvite.setCreateTime(new Date());
				goldLogRepository.save(goldLogInvite);
			}
			
			
			// 日常任务 邀请好友
			if(!firstInvite) {
				DayMission dm = dayMissionService.updateDayMission(inviteUser.getId(), Consts.DayMission.Type.INVITE);					
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
	
	
	public String getUserEarnings(String userId) {
		double bsum=Double.valueOf(balanceLogRepository.sumNumByUserId(userId));
		double gsum=(double) goldLogRepository.sumNumByUserId(userId)/Consts.GOLD_RATIO;
		return String.valueOf(NumberUtils.feeFormat(bsum+gsum));
	}
	
	public static void main(String[] args) {
		System.out.println(1008d/1000l);
	}
	
	
	public EveryDayShareRes shareEveryDay(String userId) {
		String bsum=balanceLogRepository.sumNumByUserId(userId);
		
		double gsum=(double)goldLogRepository.sumNumByUserId(userId)/Consts.GOLD_RATIO;
		
		Optional<UserLogin> u=userLoginRepository.findById(userId);
		if(u.isPresent()) {
			UserLogin userLogin=u.get();
			return new EveryDayShareRes(userLogin.getMyInviteCode(),userLogin.getHeadPortrait(),NumberUtils.feeFormat(Double.valueOf(bsum)+Double.valueOf(gsum)),userLogin.getUserName());
		}
		return new EveryDayShareRes();
	}
	
	public String getOfficial(String type) {
		Official of=officialRepository.findByType(type);
		return of.getDetails();
	}
}
