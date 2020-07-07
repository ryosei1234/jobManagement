package jp.ac.hcs.white.user;

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
public class UserRepository {

	/** SQL 全件取得（ユーザID昇順） */
	private static final String SQL_SELECT_ALL = "SELECT * FROM m_user order by user_id";

	/** SQL 1件取得 */
	private static final String SQL_SELECT_ONE = "SELECT * FROM m_user WHERE user_id = ?";

	/** SQL 1件更新 管理者 パスワード更新有 */
	private static final String SQL_UPDATE_ONE_WITH_PASSWORD = "UPDATE m_user SET encrypted_password = ?, user_name = ?, role = ? WHERE user_id = ?";

	/** SQL 1件追加  */
	private static final String SQL_INSERT_ONE = "INSERT INTO m_user(user_id, encrypted_password, user_name, darkmode, role) VALUES(?, ?, ?, false, ?)";

	private static final String SQL_INSERT_GONE = "INSERT INTO m_user(user_id, encrypted_password, user_name, darkmode, role) VALUES(?, ?, ?, false, ?)";


	/** SQL 1件更新 管理者 パスワード更新無 */
	private static final String SQL_UPDATE_ONE = "UPDATE m_user SET user_name = ?, role = ? WHERE user_id = ?";

	/** SQL 1件削除 */
	private static final String SQL_DELETE_ONE = "DELETE FROM m_user WHERE user_id = ?";

	@Autowired
	private JdbcTemplate jdbc;

	@Autowired
	PasswordEncoder passwordEncoder;

	/**
	 * Userテーブルから全データを取得.
	 * @return UserEntity
	 * @throws DataAccessException
	 */
	public UserEntity selectAll() throws DataAccessException {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ALL);
		UserEntity userEntity = mappingSelectResult(resultList);
		return userEntity;
	}

	/**
	 * Userテーブルから取得したデータをUserEntity形式にマッピングする.
	 * @param resultList Userテーブルから取得したデータ
	 * @return UserEntity
	 */
	private UserEntity mappingSelectResult(List<Map<String, Object>> resultList) {
		UserEntity entity = new UserEntity();

		for (Map<String, Object> map : resultList) {
			UserData data = new UserData();
			data.setUser_id((String) map.get("user_id"));
			data.setUser_name((String) map.get("user_name"));
			data.setDarkmode((boolean) map.get("darkmode"));
			data.setRole((String) map.get("role"));

			entity.getUserlist().add(data);
		}
		return entity;
	}

	/**
	 * UserテーブルからユーザIDをキーにデータを1件を取得.
	 * @param user_id 検索するユーザID
	 * @return UserEntity
	 * @throws DataAccessException
	 */
	public UserData selectOne(String user_id) throws DataAccessException {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ONE, user_id);
		UserEntity entity = mappingSelectResult(resultList);
		// 必ず1件のみのため、最初のUserDataを取り出す
		UserData data = entity.getUserlist().get(0);
		return data;
	}

	public UserEntity selectOne2(String user_id) {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ONE,user_id);
		UserEntity userEntity = mappingSelectResult(resultList);
		return userEntity;
	}

	/**
	 * (管理用)Userテーブルのデータを1件更新する(パスワード更新有).
	 * @param data 更新するユーザ情報
	 * @return 更新データ数
	 * @throws DataAccessException
	 */
	public int updateOneWithPassword(UserData data) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_UPDATE_ONE_WITH_PASSWORD,
				passwordEncoder.encode(data.getPassword()),
				data.getUser_name(),
				data.getRole(),
				data.getUser_id());
		return rowNumber;
	}

	/**
	 * (管理用)Userテーブルのデータを1件更新する(パスワード更新無).
	 * @param data 更新するユーザ情報
	 * @return 更新データ数
	 * @throws DataAccessException
	 */
	public int updateOne(UserData userData) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_UPDATE_ONE,
				userData.getUser_name(),
				userData.getRole(),
				userData.getUser_id());
		return rowNumber;
	}

	/**
	 * (管理用)Userテーブルのデータを1件追加する
	 * @param data
	 * @return rowNumber
	 * @throws DataAccessException
	 */
	public int insertOne(UserData data) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_INSERT_ONE,
						data.getUser_id(),
						passwordEncoder.encode(data.getPassword()),
						data.getUser_name(),
						data.getRole());

		return rowNumber;
	}
	/**
	 * (一般用）新規ユーザ情報を１件登録する
	 * @param data
	 * @return rowNumber
	 * @throws DataAccessException
	 */
	public int insertGOne(UserData data) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_INSERT_GONE,
						data.getUser_id(),
						passwordEncoder.encode(data.getPassword()),
						data.getUser_name(),
						"ROLE_GENERAL");

		return rowNumber;
	}

	/**
	 * (管理用)Userテーブルのデータを1件削除する
	 * @param user_id
	 * @return rowNumber
	 * @throws DataAccessException
	 */
	public int deleteOne(String user_id) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_DELETE_ONE, user_id);
		return rowNumber;
}


}
