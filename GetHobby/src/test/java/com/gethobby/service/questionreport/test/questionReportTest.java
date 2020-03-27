package com.gethobby.service.questionreport.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gethobby.common.Search;
import com.gethobby.service.domain.Question;
import com.gethobby.service.domain.Report;
import com.gethobby.service.domain.User;
import com.gethobby.service.questionreport.QuestionReportService;

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

public class questionReportTest {
	@Autowired
	@Qualifier("questionReportServiceImpl")
	private QuestionReportService questionReportService;
	
	//@Test
	public void testGetQuestion() throws Exception {
		Question question = questionReportService.getQuestion(10000);
		
		System.out.println("-----question ? : " + question);
	}
	
	//@Test
	public void testGetQuestionList() throws Exception {
		Map<String, Object> inputData = new HashMap<String, Object>();
		
		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		
		//search.setSearchCondition("1");
		
		inputData.put("search", search);
		inputData.put("userId", "a@a.a");
		
		// 자신이 문의한 리스트 보기 : 매개변수 search, userId 
		Map<String, Object> resultMap = questionReportService.getQuestionList(inputData);
		
		List<Question> questionList = (List<Question>)resultMap.get("list");
		
		for(Question question : questionList) {
			System.out.println("------ question ? : " + question);
		}
		
		System.out.println("--------------------total ? : " + resultMap.get("total"));
	}
	
	@Test
	public void testAddAnswerAdmin() throws Exception {
		Question question = new Question();
		question.setQuestionNo(10000);
		question.setAnswerContent("문의 답변입니다. 답변답변");
		
		// 해당 문의에 답변 추가 : 매개변수 Question
		questionReportService.addAnswerAdmin(question);
	}
	
	//@Test
	public void testAddQuestion() throws Exception {
		Question question = new Question();
		question.setQuestionTitle("��� �����Դϴ�.");
		question.setQuestionContent("��� �����Դϴ�. ���ǹ���");
		question.setQuestionType("2");
		
		User user = new User();
		user.setUserId("b@b.b");
		
		question.setUser(user);
		
		questionReportService.addQuestion(question);
	}
	
	//@Test 
	public void testGetTotalQuestionAdmin() throws Exception {
//		Map<String, Object> inputData = new HashMap<String, Object>();
//		
//		Search search = new Search();
//		
//		search.setCurrentPage(1);
//		search.setPageSize(3);
//		search.setSearchCondition("1");
//		
//		inputData.put("search", search);
//		
//		List<Question> questionList = questionReportService.getTotalQuestionAdmin(inputData);
//		
//		for(Question question : questionList) {
//			System.out.println("---question ? : " + question);
//		}
	}
	
	//@Test 
	public void testDeleteQuestion() throws Exception {
		questionReportService.deleteQuestion(10007);
	}
	
	//@Test
	public void testAddReport() throws Exception {
		Report report = new Report();
		
		User user = new User();
		user.setUserId("a@a.a");
		
		report.setUser(user);
		// 5���� �̻��̸� ���� ��� �� ���ϸ� �ڰ�/��� ���
		report.setReplyNo(10000);
		report.setReportCode("1");
		
		questionReportService.addReport(report);
	}
	
	//@Test 
	public void testGetTotalReportAdmin() throws Exception {
//		Search search = new Search();
//		
//		search.setCurrentPage(1);
//		search.setPageSize(3);
//		search.setSearchCondition("0");
//		Map<String, Object> inputData = new HashMap<String, Object>();
//		
//		inputData.put("search", search);
//		
//		List<Report> reportList = questionReportService.getTotalReportAdmin(inputData);
//		
//		for(Report report : reportList) {
//			System.out.println("------report ? : " + report);
//		}
	}
	
	//@Test 
	public void testProgressReportAdmin() throws Exception {
		
		Map<String, Object> inputData = new HashMap<String, Object>();
		
		inputData.put("reportNo", 10000);
		inputData.put("reportState", "2");
		
		questionReportService.processReportAdmin(inputData);
	}
}
