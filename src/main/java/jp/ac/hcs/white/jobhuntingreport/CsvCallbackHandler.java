package jp.ac.hcs.white.jobhuntingreport;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;

import jp.ac.hcs.white.WebConfig;

/**
 * SQLで取得した結果をCSVファイルとしてサーバに保存する.
 */
public class CsvCallbackHandler implements RowCallbackHandler {

	@Override
	public void processRow(ResultSet rs) throws SQLException {

		try {

			File file = new File(WebConfig.FILENAME_CSV);
			FileWriter fw = new FileWriter(file.getAbsoluteFile());

			BufferedWriter bw = new BufferedWriter(fw);
			do {
				// テーブルのデータ構造
				String str = rs.getInt("examination_report_id") + ","
						+ rs.getString("user_class") + ","
						+ rs.getString("user_student_no") + ","
						+ rs.getString("user_name") + ","
						+ rs.getString("examination_status_id") + ","
						+ rs.getString("action_day") + ","
						+ rs.getDate("action_end_day") + ","
						+ rs.getInt("recruitment_number") + ","
						+ rs.getString("action_place") + ","
						+ rs.getString("action_id") + ","
						+ rs.getString("company_name") + ","
						+ rs.getString("action_status_id") + ","
						+ rs.getString("attendance_id") + ","
						+ rs.getString("attendance_day") + ","
						+ rs.getString("lodging_day_id") + ","
						+ rs.getString("schedule");
				bw.write(str);
				bw.newLine();
			} while (rs.next());
			bw.flush();
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
			throw new SQLException(e);
		}

	}

}
