package jp.ac.hcs.white.examreport;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ExamReportServiceTest {

	@Autowired
	ExamReportService examreportService;

	@Test
	public void testSelectAll() {
		// 1.Ready
		// 2.Do
		ExamReportEntity examEntity = examreportService.selectAll("yamada@xxx.co.jp");
		// 3.Assert
		//assertNotNull(examEntity);
		// 4.logs
		log.warn("[testSelectAll]examEntity:" + examEntity.toString());
	}

	@Test
	public void testInsertOne() {
		fail("まだ実装されていません");
	}

	@Test
	public void testSelectOne() {
		fail("まだ実装されていません");
	}

	@Test
	public void testSearch() {
		fail("まだ実装されていません");
	}

	@Test
	public void testSaveCsv() {
		fail("まだ実装されていません");
	}

	@Test
	public void testLoadCsv() {
		fail("まだ実装されていません");
	}

}
