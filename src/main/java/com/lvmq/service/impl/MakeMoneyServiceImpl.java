package com.lvmq.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.lvmq.api.res.EasyMoneyListRes;
import com.lvmq.api.res.EasyMoneyShareRes;
import com.lvmq.api.res.EasyMoneyTaskRes;
import com.lvmq.api.res.MakeMoneyDetailRes;
import com.lvmq.api.res.MakeMoneyImgsRes;
import com.lvmq.api.res.MakeMoneyRes;
import com.lvmq.api.res.MakeMoneyTaskRes;
import com.lvmq.base.Consts;
import com.lvmq.model.EasyMoney;
import com.lvmq.model.EasyMoneyLog;
import com.lvmq.model.GoldLog;
import com.lvmq.model.GoldRewards;
import com.lvmq.model.MakeMoney;
import com.lvmq.model.MakeMoneyLog;
import com.lvmq.model.UserLogin;
import com.lvmq.repository.EasyMoneyLogRepository;
import com.lvmq.repository.EasyMoneyRepository;
import com.lvmq.repository.GoldLogRepository;
import com.lvmq.repository.GoldRewardsRepository;
import com.lvmq.repository.MakeMoneyLogRepository;
import com.lvmq.repository.MakeMoneyRepository;
import com.lvmq.repository.ReadRewardsRepository;
import com.lvmq.repository.UserLoginRepository;
import com.lvmq.service.MakeMoneyService;
import com.lvmq.util.ArrayUtil;
import com.lvmq.util.PagePlugin;

@Component
public class MakeMoneyServiceImpl implements MakeMoneyService {
	
	@Autowired
	private MakeMoneyRepository makeMoneyRepository;
	
	@Autowired
	private MakeMoneyLogRepository makeMoneyLogRepository;
	
	@Autowired
	private EasyMoneyRepository easyMoneyRepository;
	
	@Autowired
	private EasyMoneyLogRepository easyMoneyLogRepository;
	
	@Autowired
	private GoldRewardsRepository goldRewardsRepository;
	
	@Autowired
	private GoldLogRepository goldLogRepository;
	
	@Autowired
	private UserLoginRepository userLoginRepository;
	
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<MakeMoneyRes> makeMoneyList(String userId,String page,String pageSize) {
		List<MakeMoney> makeMoneyList=makeMoneyRepository.findByFlag(PagePlugin.pagePlugin(Integer.valueOf(page),Integer.valueOf(pageSize)),0);
		List<MakeMoneyRes> makeMoneyResList=new ArrayList<MakeMoneyRes>();
		for(MakeMoney m:makeMoneyList) {
			
			Optional<MakeMoneyLog> makeMoneyLog=makeMoneyLogRepository.findByMakeMoneyIdAndUserId(m.getId(), userId);
			if(makeMoneyLog.isPresent()) {
				Date date=new Date();
				int status=makeMoneyLog.get().getStatus();
				if(makeMoneyLog.get().getEndTime().getTime()-date.getTime()<0) {
					if(status==1||status==2) {
						status=6;
					}
				}
				makeMoneyResList.add(new MakeMoneyRes(m,status,String.valueOf(makeMoneyLog.get().getEndTime().getTime()-date.getTime())));
			}else {
				makeMoneyResList.add(new MakeMoneyRes(m));
			}
		}
		return makeMoneyResList;
	}
	
	public	void takePartIn(String userId,String id) {
		Optional<MakeMoney> makeMoney=makeMoneyRepository.findById(id);
		if(makeMoney.isPresent()) {
			Optional<MakeMoneyLog> makeMoneyLog=makeMoneyLogRepository.findByMakeMoneyIdAndUserId(id, userId);
			if(!makeMoneyLog.isPresent()) {
				Calendar cal = Calendar.getInstance();   
		        cal.setTime(new Date());
		        cal.add(Calendar.MINUTE, makeMoney.get().getTimeLimit());
				makeMoneyLogRepository.save(new MakeMoneyLog(userId,id,1,cal.getTime()));
			}
		}
	}
	
	
	public	void saveImgs(String imgUrls,String userId,String id) {
		Optional<MakeMoneyLog> makeMoneyLog=makeMoneyLogRepository.findByMakeMoneyIdAndUserId(id, userId);
		if(makeMoneyLog.isPresent()) {
			MakeMoneyLog m=makeMoneyLog.get();
			m.setImgs(imgUrls);
			m.setStatus(2);
			makeMoneyLogRepository.save(m);
		}
	}
	
	public	void cancel(String userId,String id) {
		Optional<MakeMoneyLog> makeMoneyLog=makeMoneyLogRepository.findByMakeMoneyIdAndUserId(id, userId);
		if(makeMoneyLog.isPresent()) {
			MakeMoneyLog m=makeMoneyLog.get();
			m.setStatus(5);
			makeMoneyLogRepository.save(m);
		}
	}
	
	public MakeMoneyDetailRes detail(String userId,String id) {
		Optional<MakeMoney> makeMoney=makeMoneyRepository.findById(id);
		if(makeMoney.isPresent()) {
			Optional<MakeMoneyLog> makeMoneyLog=makeMoneyLogRepository.findByMakeMoneyIdAndUserId(id, userId);
			SimpleDateFormat dateFormate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(makeMoneyLog.isPresent()) {
				return new MakeMoneyDetailRes(makeMoney.get(), makeMoneyLog.get().getStatus(),dateFormate.format(makeMoneyLog.get().getEndTime()));
			}else {
				return new MakeMoneyDetailRes(makeMoney.get(), 0,"");
			}
		}else {
			return new MakeMoneyDetailRes();
		}
	}

	
	public List<EasyMoneyListRes> easyMoneyList(String userId,String page,String pageSize) {
		
		List<EasyMoney> easyMoneyList=easyMoneyRepository.findByFlag(com.lvmq.util.PagePlugin.pagePlugin(Integer.valueOf(page),Integer.valueOf(pageSize)), 0);
		
		List<EasyMoneyListRes> el=new ArrayList<EasyMoneyListRes>();
		for(EasyMoney e:easyMoneyList) {
			GoldRewards goldRewards=goldRewardsRepository.findByType(Consts.GoldLog.Type.EASY_MONEY_SHARE);
			el.add(new EasyMoneyListRes(e.getId(), e.getTitle(), e.getImg(),goldRewards.getGold()));
		}
		return el;
	}
	
	public List<MakeMoneyTaskRes> makeMoneyTask(String userId,String page,String pageSize){
		List<MakeMoneyLog> makeMoneyLogList =makeMoneyLogRepository.findByUserId(com.lvmq.util.PagePlugin.pagePlugin(Integer.valueOf(page),Integer.valueOf(pageSize)),userId);
		
		List<MakeMoneyTaskRes> mlr=new ArrayList<MakeMoneyTaskRes>();
		for(MakeMoneyLog m:makeMoneyLogList) {
			Optional<MakeMoney> ml=makeMoneyRepository.findById(m.getMakeMoneyId());
			String rewards="0";
			String status=String.valueOf(m.getStatus());
			
			if(m.getStatus()==3) {
				rewards=ml.get().getCash();
			}else if(m.getStatus()==1||m.getStatus()==2) {
				Date d=new Date();
				if(!d.before(m.getEndTime())) {
					status="6";
				}
			}
			
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
			mlr.add(new MakeMoneyTaskRes(m.getId(),ml.get().getLogo(),ml.get().getTitle(),simpleDateFormat.format(m.getEndTime()),status,rewards));
		}
		return mlr;
	}
	
	
	public  EasyMoneyShareRes easyMoneyShare(String userId,String id) {
		EasyMoneyLog el=easyMoneyLogRepository.save(new EasyMoneyLog(userId,id));
		EasyMoneyShareRes es=new EasyMoneyShareRes();
		es.setToken(el.getId());
		return es;
	}
	
	
	public  String readEasyMoneyShare(String token) {
		Optional<EasyMoneyLog> op=easyMoneyLogRepository.findById(token);
		
		if(op.isPresent()) {
			EasyMoneyLog em=op.get();
			if(em.getFlag()==0) {
				int count=easyMoneyLogRepository.countByUserIdAndEmIdAndFlag(em.getUserId(), em.getEmId(), 2);
				if(count==0) {
					
					Optional<UserLogin> uop=userLoginRepository.findById(em.getUserId());
					if(uop.isPresent()) {
						UserLogin ul=uop.get();
						long gold=Long.valueOf(goldRewardsRepository.findByType(Consts.GoldLog.Type.EASY_MONEY_SHARE).getGold());
						//更新为已获得奖励状态
						em.setFlag(2);
						easyMoneyLogRepository.save(em);
						//添加奖励
						ul.setGold(ul.getGold()+gold);
						userLoginRepository.save(ul);
						//添加奖励日志
						goldLogRepository.save(new GoldLog(em.getUserId(),ul.getGold()+gold , gold, ul.getGold(), Consts.GoldLog.Type.EASY_MONEY_SHARE));
					}else {
						em.setFlag(1);
						easyMoneyLogRepository.save(em);
					}
				}
			}
			Optional<EasyMoney> emoney=easyMoneyRepository.findById(em.getEmId());
			if(emoney.isPresent()) {
				return emoney.get().getTextare();
			}else {
				return "";
			}
		}
		
		return "";
	}
	
	
	public List<EasyMoneyTaskRes> easyMoneyTask(String userId,Pageable pageable){
		StringBuffer sql=new StringBuffer();
		sql.append("select * from v_easy_money_task where user_id='").append(userId).append("' limit ").append(pageable.getPageNumber()).append(",").append(pageable.getPageSize());
		
		
		GoldRewards goldRewards=goldRewardsRepository.findByType(Consts.GoldLog.Type.EASY_MONEY_SHARE);
		
		Query query=entityManager.createNativeQuery(sql.toString());
		List<Object> objs=query.getResultList();
		
		List<EasyMoneyTaskRes> elr=new ArrayList<EasyMoneyTaskRes>();
		for (Object o : objs) {
			Object[] obj=(Object[]) o;
			elr.add(new EasyMoneyTaskRes(obj,goldRewards.getGold()));
		}
		entityManager.close();
		
		
		return elr;
	}
	
	public List<MakeMoneyImgsRes> getMakeMoneyExample(String id){
		Optional<MakeMoney> makeMoney=makeMoneyRepository.findById(id);
		List<MakeMoneyImgsRes> mr=new ArrayList<MakeMoneyImgsRes>();
		if(makeMoney.isPresent()) {
			MakeMoney m=makeMoney.get();
			List<String> s=ArrayUtil.stringToList(m.getImgs());
			for(String ss:s) {
				mr.add(new MakeMoneyImgsRes(ss));
			}
		}
		return mr;
	}
}
