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

		examRepository.selectAll(userId);

		//ExamReportData data = new ExamReportData();

		return examRepository.selectAll(userId);
	}


	public boolean insertOne(ExamReportData examdata) throws DataAccessException {
		int rowNumber = examRepository.insertOne(examdata);
		boolean result = (rowNumber> 0) ? true : false;
		return result;
	}


	//public boolean deleteOne(ExamReportData )

}
