package jp.ac.hcs.white.examreport;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jp.ac.hcs.white.WebConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ExamReportServiceTest {

	@Autowired
	ExamReportService examService;

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
		log.warn("[testInsertOne]taskEntity:" + examEntity.toString());
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

	}

	@Test
	public void testLoadCsv() {
		// 1.Ready
		// 2.Do
		byte[] bytes = null;

		try {
			bytes = examService.loadCsv(WebConfig.FILENAME_CSV);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}


		// 3.Assert
		//assertNotNull(examEntity);
		// 4.logs

	}

}
