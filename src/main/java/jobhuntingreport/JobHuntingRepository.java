package jobhuntingreport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

public class JobHuntingRepository {
	/** SQL 生徒用全件取得(期限日昇順)*/
	private static final String SQL_SELECT_STUDENT_ALL = "SELECT app.examination_report_id, app.user_id, user.user_class, user.user_student_no, user.user_name,app.examination_status_id,app.action_id,app.action_place,app.action_day,app.action_end_day,app.company_name,app.attendance_id,app.attendance_day,app.lodging_day_id,app.information,app.schedule,app.contents_report FROM application_and_report app, m_user user WHERE app.user_id = user.user_id AND app.user_id = ? ORDER BY examination_report_id";

	/** SQL 教員、事務用全件取得(期限日昇順)*/
	private static final String SQL_SELECT_ALL = "SELECT app.examination_report_id, app.user_id, user.user_class, user.user_student_no, user.user_name,app.examination_status_id,app.action_id,app.action_place,app.action_day,app.action_end_day,app.company_name,app.attendance_id,app.attendance_day,app.lodging_day_id,app.information,app.schedule,app.contents_report FROM application_and_report app, m_user user  WHERE app.user_id = user.user_id ORDER BY examination_report_id";

	/** SQL ユーザ情報1件取得 */
	private static final String SQL_SELECT_USER_ONE = "SELECT * FROM m_user WHERE user_id = ?";

	/** SQL 申請1件取得 */
	private static final String SQL_SELECT_APPLICATION_ONE = "SELECT app.examination_report_id,u.user_id,app.examination_status_id,app.action_id,app.action_place,app.action_day,app.action_end_day,app.company_name,app.action_status_id,app.attendance_id,app.attendance_day,app.lodging_day_id,app.information,app.schedule FROM application_and_report app,m_user user WHERE app.user_id = user.user_id AND examination_report_id = ?";

	/** SQL 報告1件取得 */
	private static final String SQL_SELECT_REPORT_ONE = "SELECT app.examination_report_id,u.user_id,app.contents_report FROM application_and_report WHERE app.user_id = u.user_id AND examination_report_id = ?";

	/** SQL 申請1件追加  */
	private static final String SQL_INSERT_APPLICATION_ONE = "INSERT INTO application_and_report(examination_report_id,user_id,examination_status_id,action_id,action_place,action_day,action_end_day,company_name,action_status_id,attendance_id,attendance_day,lodging_day_id,information,schedule) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	/** SQL 報告1件追加 */
	private static final String SQL_INSERT_REPORT_ONE = "INSERT INTO application_and_report((examination_report_id,user_id,examination_status_id,action_id,action_place,action_day,action_end_day,company_name,action_status_id,attendance_id,attendance_day,lodging_day_id,information,contents_report) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	/** SQL 就職活動申請・報告IDをカウントアップ */
	private static final String SQL_APPLICATION_AND_REPORT_COUNT ="SELECT COUNT(*) FROM application_and_report";

	/** SQL 就職活動申請・報告書検索*/
	private static final String SQL_SEARCH_BY_EXAMINATION_STATUS_ID_AND_USER_NAME_AND_COMPANY_NAME ="SELECT app.examination_status_id,app.action_day,user.user_name,app.company_name FROM application_and_report app, m_user user WHERE app.user_id = user.user_id,app.examination_status_id LIKE ? AND app.action_day LIKE ? AND user.user_name LIKE ? and app.company_name LIKE ?";

	/** SQL 就職活動申請更新*/
	private static final String SQL_UPDATE_APPLICATION = "UPDATE application_and_report SET examination_status_id = ?,action_id = ?,action_place = ?,action_day = ?,action_end_day = ?,company_name = ?,action_status_id = ?,attendance_id = ?,attendance_day = ?,lodging_day_id = ?,information = ?,schedule = ? WHERE  examination_report_id = ?";

	/** SQL 就職活動報告更新*/
	private static final String SQL_UPDATE_AND_REPORT = "UPDATE application_and_report SET contents_report = ? WHERE  examination_report_id = ?";

	/** SQL CSV出力*/
	private static final String SQL_SELECT_CSV = "SELECT * FROM application_and_report JOIN m_user ON application_and_report.user_id = m_user.user_id order by company_name_top";


	@Autowired
	private JdbcTemplate jdbc;

	@Autowired
	PasswordEncoder passwordEncoder;
	/**
	 * application_and_reportテーブルから全件取得
	 * @param user_id
	 * @return examreportEntity
	 * @throws DataAccessException
	 */
	public JobHuntingEntity selectAll(String user_id) throws DataAccessException {

		List<Map<String, Object>> resultList = null;
		List<Map<String, Object>> role = jdbc.queryForList(SQL_SELECT_USER_ONE, user_id);
		if ("ROLE_STUDENT".equals(role.get(0).get("USER_ROLE"))) {
			resultList = jdbc.queryForList(SQL_SELECT_STUDENT_ALL, user_id);
		} else {
			resultList = jdbc.queryForList(SQL_SELECT_ALL);
		}

		JobHuntingEntity jobhuntingEntity = mappingSelectJobResult(resultList);
		return jobhuntingEntity;
	}

	/**
	 * examreportテーブルから取得したデータをJobReportEntity形式にマッピングする.
	 * @param resultList
	 * @return entity
	 */
	private JobHuntingEntity mappingSelectJobResult(List<Map<String, Object>> resultList) {
		JobHuntingEntity entity = new JobHuntingEntity();

		for (Map<String, Object> map : resultList) {
			JobHuntingData data = new JobHuntingData();
			data.setExamination_report_id((int) map.get("examination_report_id"));
			data.setUser_id((String) map.get("user_id"));
			data.setExamination_status_id((int) map.get("examination_status_id"));
			data.setAction_id((int) map.get("action_id"));
			data.setAction_place((String) map.get("action_place"));
			String action_day = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format((Date) map.get("action_day"));
			String action_end_day = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format((Date) map.get("action_end_day"));
			data.setCompany_name((String) map.get("company_name"));
			data.setAction_status_id((int) map.get("action_status_id"));
			data.setAttendance_id((int) map.get("attendance_id"));
			data.setAttendance_day((String) map.get("attendance_day"));
			data.setLodging_day_id((int) map.get("lodging_day_id"));
			data.setInformation((String) map.get("information"));
			data.setSchedule((String) map.get("schedule"));
			data.setContents_report((String) map.get("contents_report"));
			data.setUser_name((String)map.get("user_name"));
			data.setUser_class((String)map.get("user_class"));
			data.setUser_student_no((String)map.get("user_student_no"));

			entity.getJoblist().add(data);
		}
		return entity;
	}

	/**
	 * application_and_reportテーブルから就職活動申請・報告状態、ユーザー名、活動開始日時、企業名をキーにデータを一件取得
	 * @param  examination_report_id 検索する就職活動申請・報告ID
	 * @return data
	 * @throws DataAccessException
	 */
	public JobHuntingData search(int examination_status_id,String action_day,String user_name,String company_name) throws DataAccessException {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SEARCH_BY_EXAMINATION_STATUS_ID_AND_USER_NAME_AND_COMPANY_NAME, examination_status_id,action_day,user_name,company_name);
		JobHuntingEntity entity = mappingSelectJobResult(resultList);
		// 必ず1件のみのため、最初のUserDataを取り出す
		JobHuntingData data = entity.getJoblist()
		return data;
	}

}
