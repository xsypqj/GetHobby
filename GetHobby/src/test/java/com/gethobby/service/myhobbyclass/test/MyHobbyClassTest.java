package com.gethobby.service.myhobbyclass.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gethobby.common.Search;
import com.gethobby.service.domain.HobbyClass;
import com.gethobby.service.domain.User;
import com.gethobby.service.myhobbyclass.MyHobbyClassService;
import com.gethobby.service.searchhobbyclass.SearchHobbyClassService;

@RunWith(SpringJUnit4ClassRunner.class)
/*
@ContextConfiguration (locations = { "classpath:config/context-common.xml",
									 "classpath:config/context-aspect.xml",
									 "classpath:config/context-mybatis.xml",
									 "classpath:config/context-transaction.xml"})
*/
@ContextConfiguration (locations = { "classpath:config/context-common.xml",
		 							 "classpath:config/context-mybatis.xml",
		 							 "classpath:config/context-transaction.xml"})

public class MyHobbyClassTest {
	@Autowired
	@Qualifier("myHobbyClassServiceImpl")
	private MyHobbyClassService myHobbyClassService;
	
	@Autowired
	@Qualifier("searchHobbyClassServiceImpl")
	private SearchHobbyClassService searchHobbyClassService;
	
	@Value("#{hashtagProperties}")
	private Properties hashtag;
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	//@Test
	public void testGetSteamHobbyClassList() throws Exception {
		Map<String, Object> inputData = new HashMap<String, Object>();

		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(5);
		
		String userId = "a@a.a";
		
		inputData.put("search", search);
		inputData.put("userId", userId);
		
		Map<String, Object> returnMap = myHobbyClassService.getSteamHobbyClassList(inputData);
		List<HobbyClass> list = (List<HobbyClass>)returnMap.get("list");
		
		for ( HobbyClass hobbyClass : list) {
			System.out.println("------------hobbyClass ? :"  + hobbyClass);
			System.out.println("------------category ? : " + hobbyClass.getCategory());
		}
		
		System.out.println("------totalCount ? : " + returnMap.get("total"));
	}
	/*
	//@Test
	public void testGetRecentlyHobbyClassList() throws Exception {
		List<Integer> hobbyClassNoList = new ArrayList<Integer>();
		hobbyClassNoList.add(10000);
		hobbyClassNoList.add(10001);
		hobbyClassNoList.add(10002);
		
		Map<String, Object> inputData = new HashMap<String, Object>();
		inputData.put("hobbyClassNoList", hobbyClassNoList);
		inputData.put("userId", "a@a.a");
		
		List<HobbyClass> recentlyhobbyClassList = myHobbyClassService.getRecentlyHobbyClass(inputData);
		
		for(HobbyClass hobbyClass : recentlyhobbyClassList) {
			System.out.println("----------hobbyClass ? : " + hobbyClass);
		}
	}
	*/
	//@Test
	public void testGetPurchaseHobbyClassSchedult() throws Exception {
		List<HobbyClass> purchaseHobbyClassList = myHobbyClassService.getPurchaseHobbyClassSchedult("a@a.a");
		
		for(HobbyClass hobbyClass : purchaseHobbyClassList) {
			System.out.println("-------hobbyClass ? : " + hobbyClass);
		}
		
	}
	
	//@Test
//	public void testGetHobbyClassBuyerStats() throws Exception {
//		List<User> getHobbyClassBuyerList = myHobbyClassService.getHobbyClassBuyerStats(10000);
//		
//		for(User user : getHobbyClassBuyerList) {
//			System.out.println("------------user ? : " + user);
//		}
//	}
	
	//@Test
	public void testAddSteam() throws Exception {
		Map<String, Object> inputData = new HashMap<String, Object>();
		inputData.put("userId", "a@a.a");
		inputData.put("hobbyClassNo", 10002);
		
		int steamCount = searchHobbyClassService.getHobbyClass(inputData).getSteamCount();
		
		inputData.put("steamCount", steamCount + 1);
		
		myHobbyClassService.addSteamHobbyClass(inputData);
	}
	
	//@Test
	public void testdeleteSteam() throws Exception {
		Map<String, Object> inputData = new HashMap<String, Object>();
		inputData.put("userId", "a@a.a");
		inputData.put("hobbyClassNo", 10002);
		
		int steamCount = searchHobbyClassService.getHobbyClass(inputData).getSteamCount();
		
		inputData.put("steamCount", steamCount - 1);
		
		myHobbyClassService.deleteSteamHobbyClass(inputData);
	}

	//@Test
	public void testRecommendHobbyClassList() throws Exception {
		Map<String, Object> inputData = new HashMap<String, Object>();

		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(5);
		
		String userId = "a@a.a";
		
		inputData.put("search", search);
		inputData.put("userId", userId);
		
		Map<String, Object> returnMap = myHobbyClassService.getRecommendHobbyClassList(inputData);
		List<HobbyClass> list = (List<HobbyClass>)returnMap.get("list");
		
		for ( HobbyClass hobbyClass : list) {
			System.out.println("------------hobbyClass ? :"  + hobbyClass);
			System.out.println("------------category ? : " + hobbyClass.getCategory());
		}
		
		System.out.println("------totalCount ? : " + returnMap.get("total"));
	}
	
}
