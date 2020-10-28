package jobhuntingreport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

public class JobHuntingService {
	@Autowired
	JobHuntingRepository jobRepository;

	/**
	 * 受験報告を全権取得する
	 * @param user_id ログイン情報
	 * @return JobHuntingEntity
	 * @throws DataAccessException
	 */
	public JobHuntingEntity selectAll(String user_id) throws DataAccessException {
		return jobRepository.selectAll(user_id);
	}
}
