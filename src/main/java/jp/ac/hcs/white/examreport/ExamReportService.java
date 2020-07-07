package jp.ac.hcs.white.examreport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 受験報告情報を操作する.
 */
@Transactional
@Service
public class ExamReportService {

	@Autowired
	ExamReportRepository examRepository;


	public ExamReportEntity selectAll(String userId) throws DataAccessException {

		String role = examRepository.selectRole(userId);

		return examRepository.selectAll(userId,role);
	}

	public boolean insertOne(ExamReportData examdata) throws DataAccessException {
		int lowNumber = examRepository.insertOne(examdata);
	}

}
