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
				String str = rs.getInt("id") + ","
						+ rs.getString("user_id") + ","
						+ rs.getString("comment") + ","
						+ rs.getDate("limitday");

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
