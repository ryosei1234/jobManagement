package jp.ac.hcs.white.examreport;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ExamReportRepositoryTest {

	@Autowired
	ExamReportRepository examreportRepository;

	@Test
	void testSelectAll() {
		// 1.Ready
		// 2.Do
		ExamReportEntity examreportEntity = examreportRepository.selectAll();
		// 3.Assert
		assertNotNull(examreportEntity);
		// 4.logs
		log.warn("[testSelectAll]examreportEntity:" + examreportEntity.toString());
			}

	@Test
	void testSelectOne() {
		fail("まだ実装されていません");
	}

	@Test
	void testUpdateOne() {
		fail("まだ実装されていません");
	}

	@Test
	void testInsertOne() {
		fail("まだ実装されていません");
	}

}
