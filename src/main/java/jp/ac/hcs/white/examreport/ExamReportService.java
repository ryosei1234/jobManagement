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


	public ExamReportEntity selectAll(String user_id) throws DataAccessException {
		//ExamReportData data = new ExamReportData();
		return examRepository.selectAll(user_id);
	}


	public boolean insertOne(ExamReportData examdata) throws DataAccessException {
		int rowNumber = examRepository.insertOne(examdata);
		boolean result = (rowNumber> 0) ? true : false;
		return result;
	}

	public ExamReportData selectOne(String examreport_id) {
		return examRepository.selectOne(examreport_id);

	}
	/*public ExamReportData selectRole(String user_id) throws DataAccessException {
		return examRepository.selectRole(user_id);
	}*/



	//public boolean deleteOne(ExamReportData )

	public ExamReportEntity search(String search_examreport_id, String search_user_id, String search_company_name) {

		ExamReportEntity examreportEntity = null;

		// 検索条件によって異なるSQLを使用する場合は、ここで分岐させる
		examreportEntity = examRepository.searchByExam_idAndUsernameANDCompanyname(search_examreport_id, search_user_id,search_company_name);

		return examreportEntity;
	}

}
