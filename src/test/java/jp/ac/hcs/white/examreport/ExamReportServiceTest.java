package jp.ac.hcs.white.examreport;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jp.ac.hcs.white.WebConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ExamReportServiceTest {

	@Autowired
	ExamReportService examService;
	@SpyBean
	ExamReportRepository examRepository;

	@Test
	public void testSelectAll() {
		// 1.Ready
		// 2.Do
		ExamReportEntity examEntity = examService.selectAll("yamada@xxx.co.jp");
		// 3.Assert
		//assertNotNull(examEntity);
		// 4.logs
		log.warn("[testSelectAll]examEntity:" + examEntity.toString());
	}

	@Test
	public void testInsertOne() {
		// 1.Ready
		ExamReportData data = new ExamReportData();
		data.setDepartment("S");
		data.setUser_id("yamada@xxx.co.jp");
		data.setCompany_name_top("エイチ");
		data.setRecruitment_number(110);
		data.setCompany_name("株式会社");
		data.setApplication_route("斡旋");
		data.setExam_date_time("2020-07-29");
		data.setExamination_location("東京");
		data.setContens_test("1次試験");
		data.setRemarks("備考");
		// 2.Do
		boolean result = examService.insertOne(data);
		// 3.Assert
		assertEquals(true, result);
		// 4.logs
		ExamReportEntity examEntity = examService.selectAll("yamada@xxx.co.jp");
		log.warn("[testInsertOne]examEntity:" + examEntity.toString());
	}

	@Test
	public void testInsertOne_失敗() {
		// 1.Ready
		ExamReportData data = new ExamReportData();
		data.setDepartment("S");
		data.setUser_id("yamada@xxx.co.jp");
		data.setCompany_name_top("エイチ");
		data.setRecruitment_number(110);
		data.setCompany_name("株式会社");
		data.setApplication_route("斡旋");
		data.setExam_date_time("2020-07-29");
		data.setExamination_location("東京");
		data.setContens_test("1次試験");
		data.setRemarks("備考");
		// 2.Do
		doReturn(0).when(examRepository).insertOne(any());
		boolean result = examService.insertOne(data);
		// 3.Assert
		assertEquals(false, result);
		// 4.logs
		ExamReportEntity examEntity = examService.selectAll("yamada@xxx.co.jp");
		log.warn("[testInsertOne_失敗]examEntity:" + examEntity.toString());
	}

	@Test
	public void testSelectOne() {
		// 1.Ready
		// 2.Do
		ExamReportData examEntity = examService.selectOne("0000000001");
		// 3.Assert
		//assertNotNull(examEntity);
		// 4.logs
		log.warn("[testSelectOne]examEntity:" + examEntity.toString());
	}

	@Test
	public void testSearch() {
		// 1.Ready
		// 2.Do
		ExamReportEntity examEntity = examService.search("0000000001", "", "");
		// 3.Assert
		//assertNotNull(examEntity);
		// 4.logs
		log.warn("[testSearch]examEntity:" + examEntity.toString());
	}

	@Test
	public void testSaveCsv() {
		// 1.Ready
		examService.saveCsv();
		// 2.Do
		// 3.Assert
		//assertNotNull(examEntity);
		// 4.logs
		ExamReportEntity examEntity = examService.selectAll("yamada@xxx.co.jp");
		log.warn("[testSaveCsv]examEntity:" + examEntity.toString());
	}

	@Test
	public void testLoadCsv() throws IOException {
		// 1.Ready
		// 2.Do

		byte[] bytes = examService.loadCsv(WebConfig.FILENAME_CSV);
		// TODO 自動生成された catch ブロック

		// 3.Assert
		//assertNotNull(examEntity);
		// 4.logs
		log.warn("[testLoadCsv]examEntity:" + bytes.toString());
	}

	@Test
	public void testpostExamUpdate() {
		// 1.Ready
		ExamReportData data = new ExamReportData();
		data.setExamreport_id("0000000001");
		data.setDepartment("S");
		data.setUser_id("yamada@xxx.co.jp");
		data.setCompany_name_top("エイチ");
		data.setRecruitment_number(110);
		data.setCompany_name("株式会社");
		data.setApplication_route("斡旋");
		data.setExam_date_time("2020-07-29");
		data.setExamination_location("東京");
		data.setContens_test("2次試験");
		data.setRemarks("備考");
		// 2.Do
		boolean result = examService.updateOne(data,"0000000001");
		// 3.Assert
		assertEquals(true, result);
		// 4.logs
		ExamReportEntity examEntity = examService.selectAll("yamada@xxx.co.jp");
		log.warn("[testUpdateOne]examEntity:" + examEntity.toString());
	}

	@Test
	public void testExamUpdate_失敗() {
		// 1.Ready
		ExamReportData data = new ExamReportData();
		data.setDepartment("S");
		data.setUser_id("yamada@xxx.co.jp");
		data.setCompany_name_top("エイチ");
		data.setRecruitment_number(110);
		data.setCompany_name("株式会社");
		data.setApplication_route("斡旋");
		data.setExam_date_time("2020-07-29");
		data.setExamination_location("東京");
		data.setContens_test("2次試験");
		data.setRemarks("備考");
		// 2.Do
		doReturn(0).when(examRepository).updatereport(any(),anyString());
		boolean result = examService.updateOne(data,"");
		// 3.Assert
		assertEquals(false, result);
		// 4.logs
		ExamReportEntity examEntity = examService.selectAll("yamada@xxx.co.jp");
		log.warn("[testUpdateOne_失敗]examEntity:" + examEntity.toString());
	}

	@Test
	public void testStatus() {
		// 1.Ready
		// 2.Do
		boolean result = examService.examstatus("0000000001","承認済み");
		// 3.Assert
		assertEquals(true, result);
		// 4.logs
		ExamReportEntity examEntity = examService.selectAll("yamada@xxx.co.jp");
		log.warn("[testStatus]examEntity:" + examEntity.toString());
	}

	@Test
	public void testStatus_失敗() {
		// 1.Ready
		// 2.Do
		boolean result = examService.examstatus("","承認済み");
		// 3.Assert
		assertEquals(false, result);
		// 4.logs
		ExamReportEntity examEntity = examService.selectAll("yamada@xxx.co.jp");
		log.warn("[testStatus_失敗]examEntity:" + examEntity.toString());
	}

}
