package jp.ac.hcs.white.examreport;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import javax.validation.Path;

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
