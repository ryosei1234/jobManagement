package jp.ac.hcs.white.examreport;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

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

	/**
	 * 受験報告を全権取得する
	 * @param user_id ログイン情報
	 * @return ExamReportEntity
	 * @throws DataAccessException
	 */
	public ExamReportEntity selectAll(String user_id) throws DataAccessException {
		return examRepository.selectAll(user_id);
	}

	/**
	 * 指定した受験報告IDの受験報告情報を取得する
	 * @param examreport_id 受験報告ID
	 * @return ExamReportData
	 */
	public ExamReportData selectOne(String examreport_id) {
		return examRepository.selectOne(examreport_id);

	}

	/**
	 * 受験報告を一件追加する
	 * @param examdata 追加する受験報告情報
	 * @return 処理結果(成功:true, 失敗:false)
	 * @throws DataAccessException
	 */
	public boolean insertOne(ExamReportData examdata) throws DataAccessException {
		int rowNumber = examRepository.insertOne(examdata);
		boolean result = (rowNumber> 0) ? true : false;
		return result;
	}

	/**
	 * 受験報告の検索結果を取得
	 * @param search_examreport_id 検索したい受験報告ID
	 * @param search_user_id 検索したいユーザID
	 * @param search_company_name 検索したい企業名
	 * @return ExamReportEntity
	 */
	public ExamReportEntity search(String search_examreport_id, String search_user_id, String search_company_name) {

		ExamReportEntity examEntity = null;

		examEntity = examRepository.searchByExam_idAndUsernameANDCompanyname(search_examreport_id, search_user_id,search_company_name);

		return examEntity;
	}

	/**
	 * 情報をCSVファイルとしてサーバに保存する.
	 * @throws DataAccessException
	 */
	public void saveCsv() throws DataAccessException {

		examRepository.saveCsv();
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

}
