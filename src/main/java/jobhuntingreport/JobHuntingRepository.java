package jobhuntingreport;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import jp.ac.hcs.white.examreport.CsvCallbackHandler;
import jp.ac.hcs.white.examreport.ExamReportData;
import jp.ac.hcs.white.examreport.ExamReportEntity;

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
	private static final String SQL_SEARCH_BY_EXAMINATION_REPORT_ID_AND_USER_ID_AND_COMPANY_NAME ="SELECT * FROM application_and_report WHERE examination_status_id LIKE ? AND action_day LIKE ? AND user_id LIKE ? and company_name LIKE ?";

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

		JobHuntingEntity jobhuntingEntity = mappingSelectExamResult(resultList);
		return jobhuntingEntity;
	}

	/**
	 * examreportテーブルから取得したデータをExamReportEntity形式にマッピングする.
	 * @param resultList
	 * @return entity
	 */
	private JobHuntingEntity mappingSelectExamResult(List<Map<String, Object>> resultList) {
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
			data.setAction_status_id((Date) map.get("action_status_id"));
			data.setAttendance_id((String) map.get("attendance_id"));
			data.setAttendance_day((String) map.get("attendance_day"));
			data.setLodging_day_id((String) map.get("lodging_day_id"));
			data.setInformation((String) map.get("information"));
			data.setSchedule((String) map.get("schedule"));
			data.setContents_report((String) map.get("contents_report"));

			entity.getExamlist().add(data);
		}
		return entity;
	}

	/**
	 * application_and_reportテーブルから就職活動申請・報告IDをキーにデータを一件取得
	 * @param  examination_report_id 検索する就職活動申請・報告ID
	 * @return data
	 * @throws DataAccessException
	 */
	public JobHuntingData selectOne(String examreport_id) throws DataAccessException {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ONE, examreport_id);
		JobHuntingEntity entity = mappingSelectExamResult(resultList);
		// 必ず1件のみのため、最初のUserDataを取り出す
		JobHuntingData data = entity.getExamlist().get(0);
		return data;
	}

	/**
	 *  examreportテーブルのデータを1件更新する
	 * @param ExamReportData 更新する受験報告情報
	 * @param examreport_id 受験報告ID
	 * @return rowNumber
	 * @throws DataAccessException
	 */
	public int updatereport(ExamReportData ExamReportData, String examreport_id) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_UPDATE_REPORT,
				ExamReportData.getDepartment(),
				ExamReportData.getCompany_name_top(),
				ExamReportData.getRecruitment_number(),
				ExamReportData.getCompany_name(),
				ExamReportData.getApplication_route(),
				ExamReportData.getExam_date_time(),
				ExamReportData.getExamination_location(),
				ExamReportData.getContens_test(),
				ExamReportData.getRemarks(),
				examreport_id

				);
		return rowNumber;
	}

	/**
	 * examreportテーブルのデータを1件追加する
	 * @param data
	 * @return eowNumber
	 * @throws DataAccessException
	 */
	public int insertOne(ExamReportData data) throws DataAccessException {
		int cnt = String.valueOf(1 + Integer.parseInt(((jdbc.queryForMap(SQL_REPORT_COUNT)).get("COUNT(*)")).toString())).length();

		String  examreport_id = "0";

		for(int i = 0;i<(9 - cnt); i++) {
			examreport_id += "0";
		}
		examreport_id += String.valueOf(1 + Integer.parseInt(((jdbc.queryForMap(SQL_REPORT_COUNT)).get("COUNT(*)")).toString()));

		int rowNumber = jdbc.update(SQL_INSERT_ONE,
				examreport_id,
				data.getUser_id(),
				data.getDepartment(),
				data.getCompany_name_top(),
				new Timestamp(System.currentTimeMillis()),
				data.getRecruitment_number(),
				data.getCompany_name(),
				data.getApplication_route(),
				data.getExam_date_time(),
				data.getExamination_location(),
				data.getContens_test(),
				data.getRemarks(),
				"新規作成");

		return rowNumber;
	}

	/**
	 * examreportテーブルから一致するデータを検索する
	 * @param search_examreport_id
	 * @param search_user_id
	 * @param search_company_name
	 * @return exaEntity
	 * @throws DataAccessException
	 */
	public ExamReportEntity searchByExam_idAndUsernameANDCompanyname(String search_examreport_id,String search_user_id, String search_company_name)
			throws DataAccessException {
		String like_search_examreport_id = '%' + search_examreport_id + '%';
		String like_search_user_id = '%' + search_user_id + '%';
		String like_search_company_name = '%' + search_company_name + '%';
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SEARCH_BY_EXAMREPORT_ID_AND_USER_ID_AND_COMPANY_NAME,
				like_search_examreport_id,like_search_user_id, like_search_company_name);
		ExamReportEntity examEntity = mappingSelectExamResult(resultList);
		return examEntity;
	}

	/**
	 * テーブルからデータを全件取得し、CSVファイルとしてサーバに保存する
	 * @throws DataAccessException
	 */
	public void saveCsv() throws DataAccessException {

		// CSVファイル出力用設定
		CsvCallbackHandler handler = new CsvCallbackHandler();

		jdbc.query(SQL_SELECT_CSV, handler);
	}

	/**
	 *
	 * @param examreport_id 1承認変更する受験報告ID
	 * @param exam_report_status 現在の受験報告ステータス
	 * @return rowNumber
	 * @throws DataAccessException
	 */
	public int statusOne(String examreport_id, String exam_report_status) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_STATUS,
				exam_report_status,
				examreport_id
				);
		return rowNumber;
	}

}
