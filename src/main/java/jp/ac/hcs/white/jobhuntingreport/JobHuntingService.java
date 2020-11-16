package jp.ac.hcs.white.jobhuntingreport;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Transactional
@Service
public class JobHuntingService {
	@Autowired
	JobHuntingRepository jobRepository;

	/**
	 *  就職活動書を全権取得する
	 * @param user_id ログイン情報
	 * @return JobHuntingEntity
	 * @throws DataAccessException
	 */
	public JobHuntingEntity selectAll(String user_id) throws DataAccessException {
		return jobRepository.selectAll(user_id);
	}

	/**
	 * 指定した就職活動IDの申請・報告情報を取得する
	 * @param examination_report_id  就職活動ID
	 * @return JobHuntingData
	 */
	public JobHuntingData selectOne(String examination_report_id) {
		return jobRepository.selectOne(examination_report_id);

	}

	/**
	 *  就職活動申請を一件追加する
	 * @param jobdata 追加する 就職活動情報
	 * @return 処理結果(成功:true, 失敗:false)
	 * @throws DataAccessException
	 */
	public boolean insertOne(JobHuntingData jobdata) throws DataAccessException {
		int rowNumber = jobRepository.insertOne(jobdata);
		boolean result = (rowNumber> 0) ? true : false;
		return result;
	}

	/**
	 *  就職活動の検索結果を取得
	 * @param search_examination_report_id 検索したい就職活動ID
	 * @param serch_action_day 検索したい開始日時
	 * @param search_user_id 検索したいユーザID
	 * @param search_company_name 検索したい企業名
	 * @return ExamReportEntity
	 */
	public JobHuntingEntity search(String search_examination_report_id,String serch_action_day, String search_user_name, String search_company_name, String user_id) {

		JobHuntingEntity jobEntity = null;

		jobEntity = jobRepository.jobSearch(search_examination_report_id, serch_action_day, search_user_name, search_company_name, user_id);

		return jobEntity;
	}

	/**
	 * 情報をCSVファイルとしてサーバに保存する.
	 * @throws DataAccessException
	 */
	public void saveCsv() throws DataAccessException {

		jobRepository.saveCsv();
	}

	/**
	 * サーバーに保存されているファイルを取得して、byte配列に変換する.
	 * @param fileName ファイル名
	 * @return ファイルのbyte配列
	 * @throws IOException ファイル取得エラー
	 */
	public byte[] loadCsv(String fileName) throws IOException {

		FileSystem fs = FileSystems.getDefault();
		Path p = fs.getPath(fileName);
		byte[] bytes = Files.readAllBytes(p);

		return bytes;
	}

	/**
	 * 就職活動申請書を一件分変更する
	 * @param jobdata 就職活動情報
	 * @param examination_report_id 就職活動ID
	 * @return 処理結果(成功:true, 失敗:false)
	 * @throws DataAccessException
	 */
	public boolean updateOneS(JobHuntingData jobdata, String examination_report_id) throws DataAccessException {
		int rowNumber = jobRepository.updateOneS(jobdata, examination_report_id);
		boolean result = (rowNumber > 0) ? true : false;
		return result;
	}

	/**
	 * 就職活動報告書を作成・変更する
	 * @param jobdata 就職活動情報
	 * @param examination_report_id 就職活動ID
	 * @return 処理結果(成功:true, 失敗:false)
	 * @throws DataAccessException
	 */
	public boolean updateOneH(JobHuntingData jobdata, String examination_report_id) throws DataAccessException {

		int rowNumber = jobRepository.updateOneH(jobdata, examination_report_id);
		boolean result = (rowNumber > 0) ? true : false;
		return result;
	}

	/**
	 * 就職活動書を承認変更する
	 * @param examination_report_id　就職活動ID
	 * @param examination_status_id　
	 * @return 処理結果(成功:true, 失敗:false)
	 * @throws DataAccessException
	 */
	public boolean jobstatus(String examination_report_id, String examination_status_id) throws DataAccessException {
		int rowNumber = jobRepository.statusOne(examination_report_id, examination_status_id);
		boolean result = (rowNumber > 0) ? true : false;
		return result;
	}
}
