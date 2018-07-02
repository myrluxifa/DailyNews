package com.lvmq.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import com.lvmq.api.res.BannerRes;
import com.lvmq.api.res.ImgBanner;
import com.lvmq.api.res.InviteInfoRes;
import com.lvmq.api.res.RecallListRes;
import com.lvmq.api.res.RecallRes;
import com.lvmq.api.res.TudiArrayRes;
import com.lvmq.api.res.TudiRes;
import com.lvmq.base.Consts;
import com.lvmq.model.BalanceLog;
import com.lvmq.model.GoldLog;
import com.lvmq.model.InviteCharBanner;
import com.lvmq.model.InviteImgBanner;
import com.lvmq.model.RecallLog;
import com.lvmq.model.UserLogin;
import com.lvmq.repository.BalanceLogRepository;
import com.lvmq.repository.GoldLogRepository;
import com.lvmq.repository.GoldRewardsRepository;
import com.lvmq.repository.InviteCharBannerRepository;
import com.lvmq.repository.InviteImgBannerRepository;
import com.lvmq.repository.RecallLogRepository;
import com.lvmq.repository.UserLoginRepository;
import com.lvmq.service.InviteService;
import com.lvmq.util.TimeUtil;
import com.lvmq.util.Util;


@Component
public class InviteServiceImpl implements InviteService {
	
	@Autowired
	private InviteImgBannerRepository inviteImgBannerRepository;
	
	@Autowired
	private InviteCharBannerRepository inviteCharBannerRepository;
	
	@Autowired
	private GoldLogRepository goldLogRepository;
	
	@Autowired
	private BalanceLogRepository balanceLogRepository;
	
	@Autowired
	private UserLoginRepository userLoginRepository;
	
	@Autowired
	private GoldRewardsRepository goldRewardsRepository;
	
	@Autowired
	private RecallLogRepository recallLogRepository;
	
	
	private static final Logger log = LoggerFactory.getLogger(InviteServiceImpl.class);

	@Override
	public BannerRes getBanner() {
		// TODO Auto-generated method stub
		List<InviteImgBanner> inviteImgBannerArray=inviteImgBannerRepository.findAllByFlag(0);
		List<ImgBanner> imgBannerArray=new ArrayList<ImgBanner>();
		inviteImgBannerArray.forEach(x->imgBannerArray.add(new ImgBanner(x)));
		Optional<InviteCharBanner> opt=inviteCharBannerRepository.findById("1");
		List<String> strArray=new ArrayList<String>();
		if(opt.isPresent()) {
			String content=opt.get().getContent();
			for(int i=0;i<10;i++) {
				strArray.add(Util.getContent(content));
			}
		}
		
		return new BannerRes(imgBannerArray,strArray);
	}

	
	
	public InviteInfoRes getInviteInfo(String userId) {
		//金币奖励
		List<String> goldTypeList=Arrays.asList(Consts.GoldLog.Type.MASTER_READ_REWARDS,
				Consts.GoldLog.Type.MASTER_MASTER_READ_REWARDS,
				Consts.GoldLog.Type.FIVE_MASTER_MASTER_READ_REWARDS);
		int goldSum=goldLogRepository.sumNumByTypeInAndUserId(goldTypeList,userId);
		
		log.info(String.valueOf(goldSum));
		
		//现金奖励
		List<String> balanceTypeList=Arrays.asList(Consts.BalanceLog.Type.EIGHT_DAY_REWARDS,Consts.BalanceLog.Type.FIRST_INVITE);
		String balanceSum=balanceLogRepository.sumNumByTypeInAndUserId(balanceTypeList, userId);
		log.info(balanceSum);
		
		
		String  income=String.valueOf((long)Double.parseDouble(balanceSum)*Consts.GOLD_RATIO+goldSum);
		
		String inviteCount="0";
		String inviteCode="";
		Optional<UserLogin> u=userLoginRepository.findById(userId);
		if(u.isPresent()) {
			List<UserLogin> userLoginArray=userLoginRepository.findByInviteCode(u.get().getMyInviteCode());
			inviteCount=String.valueOf(userLoginArray.size());
			inviteCode=u.get().getMyInviteCode();
		}
		
		return new InviteInfoRes(income,inviteCount,inviteCode);
		
	}
	
	
	public RecallRes  recallList(String userId) {
		Optional<UserLogin> opt=userLoginRepository.findById(userId);
		
		List<RecallListRes> recallList=new ArrayList<RecallListRes>();
		
		if(opt.isPresent()) {
			List<UserLogin> userLoginArray=userLoginRepository.findByInviteCode(opt.get().getMyInviteCode());
			for(UserLogin u:userLoginArray) {
				List<GoldLog> goldLog=goldLogRepository.findByTypeAndUserId(com.lvmq.util.PagePlugin.pagePluginSort(0, 1,Direction.DESC, "createTime"), Consts.GoldLog.Type.LOGIN, u.getId());
				if(goldLog.size()>0) {
					if(TimeUtil.ifNeedReCall(goldLog.get(0).getCreateTime().getTime())){
						if(recallLogRepository.countByUserIdAndRecallUserAndCreateTimeBetween(userId, u.getId(), TimeUtil.getHistoryDay(new Date(),3), new Date())==0) {
							recallList.add(new RecallListRes(u.getId(),u.getUserName(),u.getName()));
						}
					}
				}else {
					if(TimeUtil.ifNeedReCall(u.getCreateTime().getTime())) {
						if(recallLogRepository.countByUserIdAndRecallUserAndCreateTimeBetween(userId, u.getId(), TimeUtil.getHistoryDay(new Date(),3), new Date())==0) {
							recallList.add(new RecallListRes(u.getId(),u.getUserName(),u.getName()));
						}
					}
				}
			
			}
			
		}
		return new RecallRes(recallList);
		
	}
	
	
	public boolean recall(String userId,String recallUser){
		try {
			RecallLog recallLog=recallLogRepository.save(new RecallLog(userId,recallUser));
			return true;
		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	public TudiArrayRes tudiList(String userId) {
		Optional<UserLogin> opt=userLoginRepository.findById(userId);
		if(opt.isPresent()) {
			UserLogin userLogin=opt.get();
			String myInviteCode=userLogin.getMyInviteCode();
			List<UserLogin> userLoginArray=userLoginRepository.findByInviteCode(myInviteCode);
			
			List<TudiRes> tudiArray=new ArrayList<TudiRes>(); 
			for(UserLogin u:userLoginArray) {
				int g=goldLogRepository.sumByTypeAndTriggerUserIdAndUserId(Consts.GoldLog.Type.MASTER_READ_REWARDS,u.getId(),userId);
				List<String> bl=Arrays.asList(Consts.BalanceLog.Type.EIGHT_DAY_REWARDS,Consts.BalanceLog.Type.FIRST_INVITE);
				String bs=balanceLogRepository.sumByTypeAndTriggerUserIdAndUserId(bl, u.getId(),userId);
				String  ic=String.valueOf(Double.parseDouble(bs)+(double)g/Consts.GOLD_RATIO);
				tudiArray.add(new TudiRes(u.getHeadPortrait(), u.getUserName(), u.getName(),ic));
			}
			
			//金币奖励
			List<String> goldTypeList=Arrays.asList(Consts.GoldLog.Type.MASTER_READ_REWARDS);
			int goldSum=goldLogRepository.sumNumByTypeInAndUserId(goldTypeList,userId);
			
			//现金奖励
			List<String> balanceTypeList=Arrays.asList(Consts.BalanceLog.Type.EIGHT_DAY_REWARDS,Consts.BalanceLog.Type.FIRST_INVITE);
			String balanceSum=balanceLogRepository.sumNumByTypeInAndUserId(balanceTypeList, userId);
			
			String  income=String.valueOf(Double.parseDouble(balanceSum)+(double)goldSum/Consts.GOLD_RATIO);
			
			return new TudiArrayRes(String.valueOf(userLoginArray.size()),income,tudiArray);
		}else {
			return new TudiArrayRes();
		}
		
	}
	
	public int setInviteCode(String userId,String inviteCode) {
		try {
			Optional<UserLogin> opt=userLoginRepository.findById(userId);
			if(opt.isPresent()) {
				UserLogin userLogin=opt.get();
				//给邀请人添加邀请数量
				UserLogin inviteUser=userLoginRepository.findByMyInviteCode(inviteCode);
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
							userLogin.setMasterMaster(masterMasetUser.getId());
							userLoginRepository.save(userLogin);
						}
					}
					userLoginRepository.save(inviteUser);
					
					String invite_gold=goldRewardsRepository.findByType(Consts.GoldLog.Type.SET_INVITE).getGold();
					int updateGold=Integer.valueOf(invite_gold)+Integer.valueOf(String.valueOf(userLogin.getGold()));
					
					userLogin.setGold(Long.valueOf(updateGold));
					userLogin.setInviteCode(inviteCode);
					userLoginRepository.save(userLogin);
					
					GoldLog goldLogInvite=new GoldLog();
					goldLogInvite.setUserId(userLogin.getId());
					goldLogInvite.setType(Consts.GoldLog.Type.SET_INVITE);
					goldLogInvite.setNum(Integer.valueOf(invite_gold));
					goldLogInvite.setOldNum(userLogin.getGold());
					goldLogInvite.setNewNum(Integer.valueOf(updateGold));
					goldLogInvite.setCreateUser(userLogin.getId());
					goldLogInvite.setCreateTime(new Date());
					goldLogRepository.save(goldLogInvite);
					return 0;
				}else {
					return -1;
				}
			}else {
				return -2;
			}
		}catch(Exception e) {
				return -2;
		}
	}
	
}
