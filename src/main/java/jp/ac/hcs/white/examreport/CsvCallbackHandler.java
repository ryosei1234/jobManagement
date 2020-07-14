package jp.ac.hcs.white.examreport;

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
				String str = rs.getInt("examreport_id") + ","
						+ rs.getString("company_name") + ","
						+ rs.getString("examination_location") + ","
						+ rs.getDate("report_day");

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
