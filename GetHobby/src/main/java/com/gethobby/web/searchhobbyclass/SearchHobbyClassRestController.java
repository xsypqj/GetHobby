package com.gethobby.web.searchhobbyclass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gethobby.common.Page;
import com.gethobby.common.Search;
import com.gethobby.service.community.CommunityService;
import com.gethobby.service.domain.Article;
import com.gethobby.service.domain.ClassAssess;
import com.gethobby.service.domain.Event;
import com.gethobby.service.domain.HobbyClass;
import com.gethobby.service.domain.HobbyClassMin;
import com.gethobby.service.domain.Reply;
import com.gethobby.service.domain.User;
import com.gethobby.service.event.EventService;
import com.gethobby.service.searchhobbyclass.SearchHobbyClassService;

@RestController
@RequestMapping("/searchHobbyClass/*")
public class SearchHobbyClassRestController {
	
	@Autowired
	@Qualifier("searchHobbyClassServiceImpl")
	private SearchHobbyClassService searchHobbyClassService;
	
	@Autowired
	@Qualifier("eventServiceImpl")
	private EventService eventService;
	
	@Autowired
	@Qualifier("communityServiceImpl")
	private CommunityService communityService;
	
	@Value("#{hashtagProperties}")
	private Properties hashtag;
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	@Value("#{apiKeyProperties['channelTalk']}")
	String channelTalk;
	
	public SearchHobbyClassRestController() {
		System.out.println(this.getClass());
	}
	
	@RequestMapping( value ="json/getSearchHashtag" )
	public Map<String, List<String>> getSearchHashtag( @RequestBody Map<String, String> categoryCode ) throws Exception {
		String hashtagCode = categoryCode.get("categoryCode");
		System.out.println("------hashtagCode ? : " + hashtagCode);
		Map<String, List<String>> returnMap = new HashMap<String, List<String>>();
		
		List<String> hashtagCodeList = new ArrayList<String>();
		List<String> hashtagNameList = new ArrayList<String>();
		
		for(int i = 0; i < 10; i++) {
			String newHashtagCode = hashtagCode + "0" + i;
			hashtagCodeList.add(newHashtagCode);
			System.out.println("-------newHashtagCode ? : " + newHashtagCode);
			String hashtagString = new String(hashtag.getProperty(newHashtagCode).getBytes("ISO-8859-1"), "utf-8");
			hashtagNameList.add(hashtagString);
		}
		
		returnMap.put("hashtagCodeList", hashtagCodeList);
		returnMap.put("hashtagNameList", hashtagNameList);
		
		return returnMap;
	}
	
	@RequestMapping( value = "json/getHobbyClassList" ) 
	public Map<String, Object> getHobbyClassList( @RequestBody Map<String, Object> jsonMap, HttpSession session ) throws Exception {
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		String mapString = objectMapper.writeValueAsString(jsonMap);
		JSONObject jsonObject = (JSONObject)JSONValue.parse(mapString);
		
		// Search 도메인 ajax로 가져오기 
		Search search = objectMapper.readValue(jsonObject.get("search").toString(), Search.class);
		
		System.out.println("--------jsonMap ? : " + jsonMap);
		
		search.setPageSize(3);
		
		if (search.getHashtag() != null) {
			
			if ( search.getHashtag().size() == 0 || search.getHashtag().get(0).equals("all") ) {
				search.setHashtag(null);
			}

		}
		
		String classState = (String)jsonMap.get("stateValue");
		
		Map<String, Object> inputData = new HashMap<String, Object>();
		inputData.put("search", search);
		inputData.put("classState", classState);
		
		User user = (User)session.getAttribute("user");
		String userId = null;
		
		if ( user != null ) {
			userId = user.getUserId();
		}

		inputData.put("userId", userId);
		inputData.put("logonUser", userId);
		
		Map<String, Object> resultMap = searchHobbyClassService.getHobbyClassList(inputData);
		
		List<HobbyClass> hobbyClassList = (List<HobbyClass>)resultMap.get("list"); // 기존거
		
		for(HobbyClass hobbyClass : hobbyClassList) {
			System.out.println("--------hobbyClass ? : " + hobbyClass);
		}
		
		
		System.out.println("---------totalCount ? : " + resultMap.get("total"));
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)resultMap.get("total")).intValue(), pageUnit, pageSize);
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("hobbyClassList", hobbyClassList);
		returnMap.put("resultPage", resultPage);
		System.out.println("----resultPage ? : " + resultPage);
		return returnMap;
	}
	
	@RequestMapping( value = "json/getHobbyClassAssessContent" )
	public Map<String, Object> getHobbyClassAssessContent( @RequestBody Map<String, String> jsonMap ) throws Exception {
		Search search = new Search();
		search.setCurrentPage(Integer.parseInt(jsonMap.get("currentPage")));
		search.setPageSize(10);
		
		Map<String, Object> inputData = new HashMap<String, Object>();
		inputData.put("hobbyClassNo", jsonMap.get("hobbyClassNo"));
		inputData.put("search", search);
		
		Map<String, Object> resultMap = searchHobbyClassService.getHobbyClassAssessContent(inputData);
		
		List<ClassAssess> listAssessContent = (List<ClassAssess>)resultMap.get("list"); 
		
		for(ClassAssess classAssess : listAssessContent) {
			System.out.println("----------classAssess ? : " + classAssess);
		}
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("assessContentList", listAssessContent);

		Page resultPage = new Page( search.getCurrentPage(), ((Integer)resultMap.get("total")).intValue(), pageUnit, search.getPageSize());
		returnMap.put("resultPage", resultPage);
		
		return returnMap;
	}
	
	@RequestMapping( value = "json/getPopularHobbyClassList" )
	public Map<String, Object> getPopularHobbyClassList( HttpSession session ) throws Exception {
		Search search = new Search();
		
		// 무조건 첫페이지부터 보여줌 
		search.setCurrentPage(1);
		
		// 3개씩 4페이지 보여주기로 했으므로 12로 고정 
		search.setPageSize(12);
		
		// 로그인 여부 판별 
		User user = (User)session.getAttribute("user");
		
		String userId = null;
		
		if ( user != null ) { 
			userId = user.getUserId();
		}
		
		Map<String, Object> inputData = new HashMap<String, Object>();
		inputData.put("search", search);
		inputData.put("userId", userId);
		
		Map<String, Object> map = searchHobbyClassService.getPopularHobbyClassList(inputData);
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		List<HobbyClass> forlist = (List<HobbyClass>)map.get("list");
		
		for( HobbyClass hobbyClass : forlist) {
			System.out.println("-----popular hobbyClass ? : " + hobbyClass);
		}
		
		returnMap.put("popularHobbyClassList", map.get("list"));
		
		return returnMap;
	}
	
	@RequestMapping( value = "json/addHobbyClassAssess" ) 
	public Map<String, Object> addHobbyClassAssess( @RequestBody Map<String, String> jsonMap, HttpSession session) throws Exception {
		System.out.println("---------jsonMap ? : " + jsonMap);
		User user = (User)session.getAttribute("user");
		System.out.println("----------user ? : " + user);

		String hobbyClassNo = jsonMap.get("hobbyClassNo");
		String assessContent = jsonMap.get("assessContent");
		String assessStar = jsonMap.get("assessStar");
		
		HobbyClass hobbyClass = new HobbyClass();
		hobbyClass.setHobbyClassNo(Integer.parseInt(hobbyClassNo));

		ClassAssess classAssess = new ClassAssess();
		classAssess.setAssessContent(assessContent);
		classAssess.setAssessStar(Integer.parseInt(assessStar));
		classAssess.setUser(user);
		classAssess.setHobbyClass(hobbyClass);
	
		int classAssessNo = searchHobbyClassService.addHobbyClassAssess(classAssess);
		
		Map<String, Object> inputData = new HashMap<String, Object>();
		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(10);
		
		inputData.put("search", search);
		inputData.put("hobbyClassNo", hobbyClassNo);
		
		Map<String, Object> resultMap = searchHobbyClassService.getHobbyClassAssessContent(inputData);
		
		List<ClassAssess> classAssessList = (List<ClassAssess>)resultMap.get("list");
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)resultMap.get("total")).intValue(), pageUnit, pageSize);
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		returnMap.put("classAssessList", classAssessList);
		returnMap.put("resultPage", resultPage);
		System.out.println("----------------classAssessList ? : " + classAssessList);
		HobbyClass lastHobbyClass = searchHobbyClassService.getHobbyClass(inputData); 
		returnMap.put("hobbyClass", lastHobbyClass);
		
		return returnMap;
	}
	
	@RequestMapping( value = "json/getRegisterHobbyClassList" )
	public Map<String, Object> getRegisterHobbyClassList( HttpSession session ) throws Exception {
		Search search = new Search();
		
		// 무조건 첫페이지부터 보여줌 
		search.setCurrentPage(1);
		
		// 3개씩 4페이지 보여주기로 했으므로 12로 고정 
		search.setPageSize(12);
		
		// 로그인 여부 판별 
		User user = (User)session.getAttribute("user");
		
		String userId = null;
		
		if ( user != null ) { 
			userId = user.getUserId();
		}
		
		Map<String, Object> inputData = new HashMap<String, Object>();
		inputData.put("search", search);
		inputData.put("userId", userId);
		
		Map<String, Object> map = searchHobbyClassService.getRegisterHobbyClassList(inputData);
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("registerHobbyClassList", map.get("list"));
		
		return returnMap;
	}
	
	@RequestMapping( value = "json/openChannelTalk" )
	public Map<String, Object> openChannelTalk(HttpSession session) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		User user = (User)session.getAttribute("user");
		
		String userId = "비회원";
		String userPhone = "없음";
		
		if ( user != null ) {
			if ( user.getNickName() != null ) {
				userId = user.getNickName();
			}
			else if ( user.getNickName() == null ) {
				userId = user.getName();
			}
			userPhone = user.getPhone();
		}
		
		returnMap.put("userId", userId);
		returnMap.put("channelTalk", channelTalk);
		returnMap.put("userPhone", userPhone);
		
		return returnMap;
	}
	
	@RequestMapping( value = "json/getNowEventList" )
	public Map<String, Object> getNowEventList() throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();

		returnMap.put("eventList", searchHobbyClassService.getNowEventList());
		System.out.println("---------returnMap ? : " + returnMap);
		return returnMap;
	}
	
	@RequestMapping( value = "json/addCommunityReply" )
	public Map<String, Object> addCommunityReply(@RequestBody Reply reply, HttpSession session) throws Exception {
		User user = (User)session.getAttribute("user");
		
		reply.setUser(user);
		System.out.println("------------addCommunityRely ? : " + reply);
		communityService.addCommunityReply(reply);
		
		Map<String, Object> map = communityService.getCommunity(reply.getArticle().getArticleNo());
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("replyList", map.get("replyList"));
		
		return returnMap;
	}
	
	@RequestMapping( value = "json/deleteCommunityReply" )
	public Map<String, Object> deleteCommunityReply(@RequestBody Reply reply, HttpSession session) throws Exception {
		
		communityService.deleteCommunityReply(reply.getReplyNo());
		
		Map<String, Object> map = communityService.getCommunity(reply.getArticle().getArticleNo());
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("replyList", map.get("replyList"));
		return returnMap;
	}
	
	@RequestMapping( value = "json/updateCommunityReply" )
	public Map<String, Object> updateCommunityReply(@RequestBody Map<String, String> jsonMap, HttpSession session) throws Exception {
		User user = (User)session.getAttribute("user");
		
		Reply reply = new Reply();
		reply.setReplyNo(Integer.parseInt(jsonMap.get("replyNo")));
		reply.setReplyContent(jsonMap.get("replyContent"));
		
		communityService.updateCommunityReply(reply);
		reply = communityService.getCommunityReply(Integer.parseInt(jsonMap.get("replyNo")));
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("reply", reply);
		
		return returnMap;
	}
	
	@RequestMapping( value = "json/getReportTagetCommunityReply" ) 
	public Map<String, Object> getReportTagetCommunityReply(@RequestBody Map<String, String> jsonMap, HttpSession session) throws Exception {
		int replyNo = Integer.parseInt(jsonMap.get("replyNo"));
		
		Reply reply = communityService.getCommunityReply(replyNo);
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("reply", reply);
		return returnMap;
	}
	
}
