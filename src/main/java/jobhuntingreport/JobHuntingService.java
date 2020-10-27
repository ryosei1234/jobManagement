package jobhuntingreport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import jp.ac.hcs.white.examreport.ExamReportRepository;

public class JobHuntingService {
	@Autowired
	ExamReportRepository examRepository;

	/**
	 * 受験報告を全権取得する
	 * @param user_id ログイン情報
	 * @return ExamReportEntity
	 * @throws DataAccessException
	 */
	public JobHuntingEntity selectAll(String user_id) throws DataAccessException {
		return examRepository.selectAll(user_id);
	}
}
