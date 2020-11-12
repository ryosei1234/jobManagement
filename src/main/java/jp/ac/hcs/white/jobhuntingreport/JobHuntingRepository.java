package jp.ac.hcs.white.jobhuntingreport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import jp.ac.hcs.white.examreport.CsvCallbackHandler;
@Repository
public class JobHuntingRepository {
	/** SQL 生徒用全件取得(期限日昇順)*/
	private static final String SQL_SELECT_STUDENT_ALL = "SELECT app.examination_report_id, app.user_id, user.user_class, user.user_student_no, user.user_name,app.examination_status_id,app.action_id,app.action_place,app.action_day,app.action_end_day,app.company_name,app.action_status_id,app.attendance_id,app.attendance_day,app.attendance_end_day,app.lodging_day_id,app.information,app.schedule,app.contents_report FROM application_and_report app, m_user user WHERE app.user_id = user.user_id AND app.user_id = ? ORDER BY examination_report_id";
	/** SQL 教員、事務用全件取得(期限日昇順)*/
	private static final String SQL_SELECT_ALL = "SELECT app.examination_report_id, app.user_id, user.user_class, user.user_student_no, user.user_name,app.examination_status_id,app.action_id,app.action_place,app.action_day,app.action_end_day,app.company_name,app.action_status_id,app.attendance_id,app.attendance_day,app.attendance_end_day,app.lodging_day_id,app.information,app.schedule,app.contents_report FROM application_and_report app, m_user user  WHERE app.user_id = user.user_id ORDER BY examination_report_id";
	/** SQL ユーザ情報1件取得 */
	private static final String SQL_SELECT_USER_ONE = "SELECT * FROM m_user WHERE user_id = ?";
	/** SQL 1件取得 */
	private static final String SQL_SELECT_APPLICATION_ONE = "SELECT app.examination_report_id,user.user_id,user.user_class, user.user_student_no, user.user_name,app.examination_status_id,app.action_id,app.action_place,app.action_day,app.action_end_day,app.company_name,app.action_status_id,app.attendance_id,app.attendance_day,app.attendance_end_day,app.lodging_day_id,app.information,app.schedule,app.contents_report FROM application_and_report app,m_user user WHERE app.user_id = user.user_id AND examination_report_id = ?";
	/** SQL 申請1件追加  */
	private static final String SQL_INSERT_APPLICATION_ONE = "INSERT INTO application_and_report(examination_report_id,user_id,examination_status_id,action_id,action_place,action_day,action_end_day,company_name,action_status_id,attendance_id,attendance_day,attendance_end_day,lodging_day_id,information,schedule,contents_report) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	/** SQL 就職活動申請・報告IDをカウントアップ */
	private static final String SQL_APPLICATION_AND_REPORT_COUNT ="SELECT COUNT(*) FROM application_and_report";
	/** SQL 就職活動申請・報告書検索 管理者用 */
	private static final String SQL_SEARCH_BY_EXAMINATION_STATUS_ID_AND_USER_NAME_AND_COMPANY_NAME ="SELECT * FROM application_and_report app, m_user user WHERE app.user_id = user.user_id AND app.examination_status_id LIKE ? AND app.action_day LIKE ? AND user.user_name LIKE ? and app.company_name LIKE ?";
	/** SQL 就職活動申請・報告書検索 学生用  */
	private static final String SQL_SEARCH_BY_EXAMINATION_STATUS_ID ="SELECT * FROM application_and_report app, m_user user WHERE app.user_id = ? AND app.examination_status_id LIKE ? AND app.action_day LIKE ? AND user.user_name LIKE ? and app.company_name LIKE ?";
	/** SQL 就職活動申請更新*/
	private static final String SQL_UPDATE_APPLICATION = "UPDATE application_and_report SET examination_status_id = ?,action_id = ?,action_place = ?,action_day = ?,action_end_day = ?,company_name = ?,action_status_id = ?,attendance_id = ?,attendance_day = ?,attendance_end_day = ?,lodging_day_id = ?,information = ?,schedule = ?,contents_report = ? WHERE  examination_report_id = ?";
	/** SQL 就職活動報告更新*/
	private static final String SQL_UPDATE_AND_REPORT = "UPDATE application_and_report SET examination_status_id = ?,contents_report = ? WHERE  examination_report_id = ?";
	/** SQL 就職活動状態変更 */
	private static final String SQL_UPDATE_ID = "UPDATE application_and_report SET examination_status_id = ? WHERE  examination_report_id = ?";
	/** SQL 就職活動申請・報告削除 */
	private static final String SQL_DELETE_ONE = "DLETE FROM application_and_report WHERE examination_report_id = ?";
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
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			JobHuntingData data = new JobHuntingData();
			data.setExamination_report_id(((String) map.get("examination_report_id")));
			data.setUser_id((String) map.get("user_id"));
			data.setExamination_status_id(((String) map.get("examination_status_id")));
			data.setAction_id(((String) map.get("action_id")));
			data.setAction_place((String) map.get("action_place"));
			String action_day = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format((Date) map.get("action_day"));
			data.setAction_day(action_day);
			try {
				//date型をString型に変換
				String action_end_day = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format((Date) map.get("action_end_day"));
				data.setAction_end_day(action_end_day);
			} catch (Exception e) {
				data.setAction_end_day(null);
			}
			data.setCompany_name((String) map.get("company_name"));
			data.setAction_status_id(((String) map.get("action_status_id")));
			data.setAttendance_id(((String) map.get("attendance_id")));
			String attendance_day = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format((Date) map.get("attendance_day"));
			data.setAttendance_day((String)attendance_day);
			try {
				//date型をString型に変換
				String attendance_end_day = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format((Date) map.get("attendance_end_day"));
				data.setAttendance_end_day((String)attendance_end_day);
			} catch (Exception e) {
				data.setAttendance_end_day(null);
			}
			data.setLodging_day_id(((String) map.get("lodging_day_id")));
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
	 * application_and_reportテーブルから就職活動申請・報告IDをキーにデータを一件取得
	 * @param  examination_report_id 検索する就職活動申請・報告ID
	 * @return data
	 * @throws DataAccessException
	 */
	public JobHuntingData selectOne(String examination_report_id) throws DataAccessException {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_APPLICATION_ONE, examination_report_id);
		JobHuntingEntity entity = mappingSelectJobResult(resultList);
		// 必ず1件のみのため、最初のUserDataを取り出す
		JobHuntingData data = entity.getJoblist().get(0);
		System.out.println(data + "でーたあ");
		return data;
	}

	/**
	 * application_and_reportテーブルから一致するデータを検索する
	 * @param search_application_id
	 * @param search_user_id
	 * @param search_company_name
	 * @return exaEntity
	 * @throws DataAccessException
	 */
	public JobHuntingEntity jobSearch(String search_application_id,String search_action_day,String search_user_id, String search_company_name, String user_id)
			throws DataAccessException {

		String like_search_application_id = '%' + search_application_id + '%';
		String like_search_action_day = '%' + search_action_day + '%';
		String like_search_user_id = '%' + search_user_id + '%';
		String like_search_company_name = '%' + search_company_name + '%';
		List<Map<String, Object>> resultList = null;
		System.out.print(user_id + "ロール別に検索");
		List<Map<String, Object>> role = jdbc.queryForList(SQL_SELECT_USER_ONE, user_id);
		//学生だったら自分の申請・報告書のみ、担任ならすべて見ることができる
		if ("ROLE_STUDENT".equals(role.get(0).get("USER_ROLE"))) {
			resultList = jdbc.queryForList(SQL_SEARCH_BY_EXAMINATION_STATUS_ID,
					user_id,like_search_application_id,like_search_action_day,like_search_user_id, like_search_company_name);
			System.out.println(resultList + "学生");
		} else {
			resultList = jdbc.queryForList(SQL_SEARCH_BY_EXAMINATION_STATUS_ID_AND_USER_NAME_AND_COMPANY_NAME,
					like_search_application_id,like_search_action_day,like_search_user_id, like_search_company_name);
			System.out.println(resultList + "担任");
		}


		JobHuntingEntity jobEntity = mappingSelectJobResult(resultList);
		return jobEntity;
	}

	/**
	 *  application_and_reportテーブルのデータを申請を1件更新する
	 * @param JobHuntingData 更新する就職活動申請・報告ID
	 * @param examination_report_id 就職活動申請・報告ID
	 * @param
	 * @return rowNumber
	 * @throws DataAccessException
	 */
	public int updateOneS(JobHuntingData JobHuntingData, String examination_report_id) throws DataAccessException {
		String dt = JobHuntingData.getAction_end_day();
		if(JobHuntingData.getAction_end_day() == "") {
			JobHuntingData.setAction_end_day(null);
			dt = JobHuntingData.getAction_end_day();
		}
		if(JobHuntingData.getAttendance_end_day() == "") {
			JobHuntingData.setAttendance_end_day(null);
			dt = JobHuntingData.getAttendance_end_day();
		}
		System.out.println(examination_report_id + "うｐ");
		System.out.println(JobHuntingData + "ｗ");
		int rowNumber = jdbc.update(SQL_UPDATE_APPLICATION,
				"申請承認待",
				JobHuntingData.getAction_id(),
				JobHuntingData.getAction_place(),
				JobHuntingData.getAction_day(),
				JobHuntingData.getAction_end_day(),
				JobHuntingData.getCompany_name(),
				JobHuntingData.getAction_status_id(),
				JobHuntingData.getAttendance_id(),
				JobHuntingData.getAttendance_day(),
				JobHuntingData.getAttendance_end_day(),
				JobHuntingData.getLodging_day_id(),
				JobHuntingData.getInformation(),
				JobHuntingData.getSchedule(),
				JobHuntingData.getContents_report(),
				examination_report_id
				);
			return rowNumber;
	}

	/**
	 *  application_and_reportテーブルのデータを報告を1件更新する
	 * @param JobHuntingData 更新する就職活動申請・報告ID
	 * @param examination_report_id 就職活動申請・報告ID
	 * @param
	 * @return rowNumber
	 * @throws DataAccessException
	 */
	public int updateOneH(JobHuntingData JobHuntingData, String examination_report_id) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_UPDATE_AND_REPORT,
				"報告承認待",
				JobHuntingData.getContents_report(),
				examination_report_id
				);
			return rowNumber;
	}

	/**
	 * 就職活動申請・報告状態のIDを取得する
	 */


	/**
	 * application_and_reportテーブルの状態変更をする
	 *
	 */
	public int statusOne(String examination_report_id, String examination_status_id) throws DataAccessException {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_APPLICATION_ONE, examination_report_id);
		JobHuntingEntity entity = mappingSelectJobResult(resultList);
		JobHuntingData data = entity.getJoblist().get(0);
		System.out.println(data.getExamination_status_id() + "でーた");
		System.out.println(examination_status_id);
		String search = "報告承認待";
		String status = "申請完了";
		if(data.getExamination_status_id().equals(search)) {
			if(examination_status_id.equals(status)) {
				examination_status_id = "報告完了";
			}
		}
		int rowNumber = jdbc.update(SQL_UPDATE_ID,
				examination_status_id,
				examination_report_id
				);
			return rowNumber;
	}

	/**
	 * application_and_reportテーブルの申請データを1件追加する
	 * @param data
	 * @return eowNumber
	 * @throws DataAccessException
	 */
	public int insertOne(JobHuntingData data) throws DataAccessException {
		int cnt = String.valueOf(1 + Integer.parseInt(((jdbc.queryForMap(SQL_APPLICATION_AND_REPORT_COUNT)).get("COUNT(*)")).toString())).length();
		String  examination_report_id = "0";
		for(int i = 0;i<(9 - cnt); i++) {
			examination_report_id += "0";
		}
		System.out.println(data + "でーた");
		examination_report_id += String.valueOf(1 + Integer.parseInt(((jdbc.queryForMap(SQL_APPLICATION_AND_REPORT_COUNT)).get("COUNT(*)")).toString()));
			if(data.getAction_end_day() == "") {
				data.setAction_end_day(null);
			}
			if(data.getAttendance_end_day() == "") {
				data.setAttendance_end_day(null);
			}
			int rowNumber = jdbc.update(SQL_INSERT_APPLICATION_ONE,
						examination_report_id,
						data.getUser_id(),
						"申請承認待",
						data.getAction_id(),
						data.getAction_place(),
						data.getAction_day(),
						data.getAction_end_day(),
						data.getCompany_name(),
						"進める",
						data.getAttendance_id(),
						data.getAttendance_day(),
						data.getAttendance_end_day(),
						data.getLodging_day_id(),
						data.getInformation(),
						data.getSchedule(),
						data.getContents_report()
			);
			System.out.println(rowNumber + "ああああ");
			return rowNumber;
	}

	/**
	 * テーブルから削除したいデータを一件取得し、削除する
	 */
	public void deleteOne(JobHuntingData data,String examination_report_id) throws DataAccessException {

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
}