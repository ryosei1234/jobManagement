package jp.ac.hcs.white.examreport;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

/**
 * ユーザ情報のデータを管理する.
 * - Userテーブル
 */
@Repository
public class ExamReportRepository {

	/** SQL 全件取得（ユーザID昇順） */
	private static final String SQL_SELECT_ALL = "SELECT * FROM examreport order by examreport_id";

	/** SQL 1件取得 */
	private static final String SQL_SELECT_ONE = "SELECT * FROM examreport WHERE examreport_id = ?";


	/** SQL 1件追加  */
	private static final String SQL_INSERT_ONE = "INSERT INTO examreport(examreport_id,department, company_name_top,report_day,recruitment_number,company_name,exam_application_place,exam_date_time,examination_location,remarks,exam_report_status ) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, '新規作成')";

	/** SQL 1件更新 管理者 パスワード更新無 */
	private static final String SQL_UPDATE_ONE = "UPDATE examreport SET examreport_id=?,department=?, company_name_top=?,report_day=?,recruitment_number=?,company_name=?,exam_application_place=?,exam_date_time=?,examination_location=?,remarks=?,exam_report_status=?";


	@Autowired
	private JdbcTemplate jdbc;

	@Autowired
	PasswordEncoder passwordEncoder;

	/**
	 * Userテーブルから全データを取得.
	 * @return UserEntity
	 * @throws DataAccessException
	 */
	public ExamReportEntity selectAll() throws DataAccessException {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ALL);
		ExamReportEntity examreportEntity = mappingSelectExamResult(resultList);
		return examreportEntity;
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
			data.setExamreport_id((String) map.get("examreport_id"));
			data.setDepartment((String) map.get("department"));
			data.setCompany_name_top((String) map.get("company_name_top"));
			data.setReport_day((Date) map.get("report_day"));
			data.setRecruitment_number((int) map.get("recruitment_number"));
			data.setExamreport_id((String) map.get("examreport_id"));
			data.setDepartment((String) map.get("department"));
			data.setCompany_name((String) map.get("company_name"));
			data.setExam_application_place((String) map.get("exam_application_place"));
			data.setExam_date_time((Date) map.get("exam_date_time"));
			data.setExamination_location((String) map.get("examination_location"));
			data.setRemarks((String) map.get("remarks"));
			data.setExam_report_status((String) map.get("exam_report_status"));
			entity.getExamlist().add(data);
		}
		return entity;
	}


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
				ExamReportData.getExam_application_place(),
				ExamReportData.getExam_date_time(),
				ExamReportData.getExamination_location(),
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
		int rowNumber = jdbc.update(SQL_INSERT_ONE,
						data.getExamreport_id(),
						data.getDepartment(),
						data.getCompany_name_top(),
						data.getReport_day(),
						data.getRecruitment_number(),
						data.getCompany_name(),
						data.getExam_application_place(),
						data.getExam_date_time(),
						data.getExamination_location(),
						data.getRemarks(),
						data.getExam_report_status());

		return rowNumber;
	}

}
