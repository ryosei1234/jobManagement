package jp.ac.hcs.white.examreport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

/**
 * ユーザ情報のデータを管理する.
 * - Userテーブル
 */
@Repository
@Slf4j
public class ExamReportRepository {



	 /** SQL 生徒用全件取得(期限日昇順)*/
	 private static final String SQL_SELECT_STUDENT_ALL = "SELECT examreport_id, examreport.user_id, user_class, user_student_no, user_name, department ,company_name_top ,company_name, report_day,recruitment_number, exam_date_time FROM examreport, m_user WHERE examreport.user_id = m_user.user_id AND examreport.user_id = ? ORDER BY examreport_id";


	 /** SQL 教員、事務用全件取得(期限日昇順)*/
	 private static final String SQL_SELECT_ALL = "SELECT examreport_id, examreport.user_id, user_class, user_student_no, user_name, department, company_name_top, company_name, report_day,recruitment_number,exam_date_time FROM examreport, m_user WHERE examreport.user_id = m_user.user_id ORDER BY examreport_id";

	/** SQL 全件取得（ユーザID昇順） */
	//private static final String SQL_SELECT_ALL = "SELECT * FROM examreport order by examreport_id";

	 /** SQL ユーザ情報1件取得 */
	 private static final String SQL_SELECT_USER_ONE = "SELECT * FROM m_user WHERE user_id = ?";

	/** SQL 1件取得 */
	private static final String SQL_SELECT_ONE = "SELECT examreport_id,user_class,user_student_no,user_name,department,company_name_top,report_day,recruitment_number,company_name,application_route,exam_date_time,examination_location,contens_test,remarks,Exam_report_status FROM examreport, m_user WHERE m_user.user_id = examreport.user_id AND examreport_id = ?";

	//private static final String SQL_SELECT_ROLE ="SELECT user_role FROM m_user WHERE user_id = ?";
	/** SQL 1件追加  */
	private static final String SQL_INSERT_ONE = "INSERT INTO examreport(examreport_id,user_id,department, company_name_top,report_day,recruitment_number,company_name,application_route,exam_date_time,examination_location,contens_test,remarks,exam_report_status ) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	/** SQL 1件更新 管理者 パスワード更新無 */
	private static final String SQL_UPDATE_ONE = "UPDATE examreport SET examreport_id=?,department=?, company_name_top=?,report_day=?,recruitment_number=?,company_name=?,application_route=?,exam_date_time=?,examination_location=?,contens_test=?,remarks=?,exam_report_status=?";

	private static final String SQL_REPORT_COUNT ="SELECT count(*) FROM examreport";

	private static final String SQL_SEARCH_BY_USER_ID_AND_USER_NAME ="SELECT * FROM m_user where user_id LIKE ? and user_name LIKE ?";

	private static final String SQL_SEARCH_BY_EXAMREPORT_ID_AND_USER_ID_AND_COMPANY_NAME ="SELECT * FROM m_user where examreport_id LIKE ? and user_id LIKE ? and company_name LIKE ?";

	@Autowired
	private JdbcTemplate jdbc;

	@Autowired
	PasswordEncoder passwordEncoder;

	/**
	 * Userテーブルから全データを取得.
	 * @param user_Id
	 * @return UserEntity
	 * @throws DataAccessException
	 */
	public ExamReportEntity selectAll(String user_id) throws DataAccessException {

		  List<Map<String, Object>> resultList = null;
		  List<Map<String, Object>> role = jdbc.queryForList(SQL_SELECT_USER_ONE, user_id);

		  if ("STUDENT".equals(role.get(0).get("USER_ROLE"))) {
		   resultList = jdbc.queryForList(SQL_SELECT_STUDENT_ALL, user_id);
		  } else {
		   resultList = jdbc.queryForList(SQL_SELECT_ALL);
		  }

		  ExamReportEntity examreportEntity = mappingSelectExamResult(resultList);
		  return examreportEntity;


		/*ExamReportData role = selectRole(user_id);
		log.warn(role.toString());
		if (role.toString() == "STUDENT") {

		}else {

		}
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ALL);
		ExamReportEntity examreportEntity = mappingSelectExamResult(resultList);
		return examreportEntity;*/
	}


	/**
	 * Userテーブルから取得したデータをUserEntity形式にマッピングする.
	 * @param resultList Userテーブルから取得したデータ
	 * @return UserEntity
	 */
	private ExamReportEntity mappingSelectExamResult(List<Map<String, Object>> resultList) {
		ExamReportEntity entity = new ExamReportEntity();

		for (Map<String, Object> map : resultList) {
			ExamReportData data = new ExamReportData();
			log.warn("検査ぴえん:" + resultList.toString());
			data.setExamreport_id((String) map.get("examreport_id"));
			data.setUser_id((String) map.get("user_id"));
			data.setDepartment((String) map.get("department"));
			data.setCompany_name_top((String) map.get("company_name_top"));
			data.setReport_day((Date) map.get("report_day"));
			data.setRecruitment_number((Integer) map.get("recruitment_number"));
			data.setCompany_name((String) map.get("company_name"));
			data.setApplication_route((String) map.get("application_route"));
			String exam_date_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format((Date) map.get("exam_date_time"));
			data.setExam_date_time(exam_date_time);
			data.setExamination_location((String) map.get("examination_location"));
			data.setContens_test((String) map.get("contens_test"));
			data.setRemarks((String) map.get("remarks"));
			data.setExam_report_status((String) map.get("exam_report_status"));
			data.setUser_class((String) map.get("user_class"));
			data.setUser_student_no((int) map.get("user_student_no"));
			data.setUser_name((String) map.get("user_name"));

			entity.getExamlist().add(data);
		}
		return entity;
	}
	/*public ExamReportData selectRole(String user_id) throws DataAccessException {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ROLE, user_id);
		ExamReportEntity entity = mappingSelectExamResult(resultList);
		// 必ず1件のみのため、最初のUserDataを取り出す
		ExamReportData data = entity.getExamlist().get(0);
		return data;
	}*/

	/**
	 * UserテーブルからユーザIDをキーにデータを1件を取得.
	 * @param user_id 検索するユーザID
	 * @return UserEntity
	 * @throws DataAccessException
	 */
	public ExamReportData selectOne(String examreport_id) throws DataAccessException {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ONE, examreport_id);
		ExamReportEntity entity = mappingSelectExamResult(resultList);
		// 必ず1件のみのため、最初のUserDataを取り出す
		ExamReportData data = entity.getExamlist().get(0);
		return data;
	}


	/**
	 * (管理用)Userテーブルのデータを1件更新する(パスワード更新無).
	 * @param data 更新するユーザ情報
	 * @return 更新データ数
	 * @throws DataAccessException
	 */
	public int updateOne(ExamReportData ExamReportData) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_UPDATE_ONE,
				ExamReportData.getDepartment(),
				ExamReportData.getCompany_name_top(),
				ExamReportData.getReport_day(),
				ExamReportData.getRecruitment_number(),
				ExamReportData.getCompany_name(),
				ExamReportData.getApplication_route(),
				ExamReportData.getExam_date_time(),
				ExamReportData.getExamination_location(),
				ExamReportData.getContens_test(),
				ExamReportData.getRemarks()

				);
		return rowNumber;
	}

	/**
	 * (管理用)Userテーブルのデータを1件追加する
	 * @param data
	 * @return rowNumber
	 * @throws DataAccessException
	 */
	public int insertOne(ExamReportData data) throws DataAccessException {
		//List<Map<String, Object>> cnt = jdbc.queryForList(SQL_REPORT_COUNT);
		//data.setExamreport_id(cnt.get(0));
		log.warn("検査インサートぴえん:" + data.toString());
		int rowNumber = jdbc.update(SQL_INSERT_ONE,
						1,
						data.getUser_id(),
						data.getDepartment(),
						data.getCompany_name_top(),
						data.getReport_day(),
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

	public ExamReportEntity searchByExam_idAndUsernameANDCompanyname(String search_examreport_id,String search_user_id, String search_company_name)
			throws DataAccessException {
		String like_search_examreport_id = '%' + search_examreport_id + '%';
		String like_search_user_id = '%' + search_user_id + '%';
		String like_search_company_name = '%' + search_company_name + '%';
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SEARCH_BY_USER_ID_AND_USER_NAME,
				like_search_examreport_id,like_search_user_id, like_search_company_name);
		ExamReportEntity examReportEntity = mappingSelectExamResult(resultList);
		return examReportEntity;
	}


}
