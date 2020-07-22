package jp.ac.hcs.white.user;

import java.sql.Timestamp;
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
public class UserRepository {

	/** SQL 全件取得（ユーザID昇順） */
	private static final String SQL_SELECT_ALL = "SELECT * FROM m_user order by user_id";

	/** SQL 1件取得 */
	private static final String SQL_SELECT_ONE = "SELECT * FROM m_user WHERE user_id = ?";

	/** SQL 1件追加  */
	private static final String SQL_INSERT_ONE = "INSERT INTO m_user(user_id, encrypted_password, user_name, user_role, user_class, user_student_no, user_darkmode, user_status, created_at, created_user_id, update_at, update_user_id) "
									+ "VALUES(?, ?, ?, ?, ?, ?, false, 'VALID', ? , ?, ?, ?)";

	/** SQL 1件更新 パスワード更新有 */
	private static final String SQL_UPDATE_ONE_WITH_PASSWORD = "UPDATE m_user SET encrypted_password = ?, user_name = ?, user_role = ?, User_class = ?, User_student_no = ? ,update_at = ?,update_user_id = ? WHERE user_id = ?";

	/** SQL 1件更新 管理者 パスワード更新無 */
	private static final String SQL_UPDATE_ONE = "UPDATE m_user SET user_name = ?, user_role = ?, User_class = ?, User_student_no = ? ,update_at = ?,update_user_id = ? WHERE user_id = ?";

	/** SQL 1件更新 パスワード更新有 */
	private static final String SQL_UPDATE_PROONE_WITH_PASSWORD = "UPDATE m_user SET encrypted_password = ?, user_name = ? ,User_class = ?, User_student_no = ? ,update_at = ?,update_user_id = ? WHERE user_id = ?";

	/** SQL 1件更新 管理者 パスワード更新無 */
	private static final String SQL_UPDATE_PROONE = "UPDATE m_user SET user_name = ? , User_class = ?, User_student_no = ? ,update_at = ?,update_user_id = ? WHERE user_id = ?";

	/** SQL 1件削除 */
	private static final String SQL_DELETE_ONE = "DELETE FROM m_user WHERE user_id = ?";

    /** SQL 検索*/
	private static final String SQL_SEARCH_BY_USER_ID_AND_USER_NAME ="SELECT * FROM m_user where user_id LIKE ? and user_name LIKE ?";


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
			//data.setPassword((String) map.get("password"));
			data.setUser_name((String) map.get("user_name"));
			data.setUser_class((String)map.get("user_class"));
			data.setUser_darkmode((boolean) map.get("user_darkmode"));
			data.setRole((String) map.get("user_role"));
			data.setUser_student_no((String)map.get("user_student_no"));
			data.setUser_status((String)map.get("user_status"));
			data.setCreated_at((Timestamp) map.get("created_at"));
			data.setCreated_user_id((String)map.get("created_user_id"));
			data.setUpdate_at((Timestamp) map.get("update_at"));
			data.setUpdate_user_id((String)map.get("Update_user_id"));
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
				data.getUser_class(),
				data.getUser_student_no(),
				new Timestamp(System.currentTimeMillis()),
				data.getUpdate_user_id(),
				data.getUser_id());
		return rowNumber;
	}

	/**
	 * Userテーブルのデータを1件更新する(パスワード更新無).
	 * @param data 更新するユーザ情報
	 * @return 更新データ数
	 * @throws DataAccessException
	 */
	public int updateOne(UserData data) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_UPDATE_ONE,
				data.getUser_name(),
				data.getRole(),
				data.getUser_class(),
				data.getUser_student_no(),
				new Timestamp(System.currentTimeMillis()),
				data.getUpdate_user_id(),
				data.getUser_id());
		return rowNumber;
	}

	/**
	 * (管理用)Userテーブルのデータを1件更新する(パスワード更新有).
	 * @param data 更新するユーザ情報
	 * @return 更新データ数
	 * @throws DataAccessException
	 */
	public int updateProOneWithPassword(UserData data) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_UPDATE_ONE_WITH_PASSWORD,
				passwordEncoder.encode(data.getPassword()),
				data.getUser_name(),
				data.getUser_class(),
				data.getUser_student_no(),
				new Timestamp(System.currentTimeMillis()),
				data.getUpdate_user_id(),
				data.getUser_id());
		return rowNumber;
	}

	/**
	 * Userテーブルのデータを1件更新する(パスワード更新無).
	 * @param data 更新するユーザ情報
	 * @return 更新データ数
	 * @throws DataAccessException
	 */
	public int updateProOne(UserData data) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_UPDATE_ONE,
				data.getUser_name(),
				data.getUser_class(),
				data.getUser_student_no(),
				new Timestamp(System.currentTimeMillis()),
				data.getUpdate_user_id(),
				data.getUser_id());
		return rowNumber;
	}

	/**
	 * Userテーブルのデータを1件追加する
	 * @param data
	 * @return rowNumber
	 * @throws DataAccessException
	 */
	public int insertOne(UserData data,String user_id) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_INSERT_ONE,
						data.getUser_id(),
						passwordEncoder.encode(data.getPassword()),
						data.getUser_name(),
						data.getRole(),
						data.getUser_class(),
						data.getUser_student_no(),
						new Timestamp(System.currentTimeMillis()),
						user_id,
						new Timestamp(System.currentTimeMillis()),
						user_id);

		return rowNumber;
	}

	/**
	 * Userテーブルのデータを1件削除する
	 * @param user_id
	 * @return rowNumber
	 * @throws DataAccessException
	 */
	public int deleteOne(String user_id) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_DELETE_ONE, user_id);
		return rowNumber;
}
	/**
	 * ユーザを検索する
	 * @param search_user_id
	 * @param search_user_name
	 * @return userEntity
	 * @throws DataAccessException
	 */
	public UserEntity searchByUserIdAndUsername(String search_user_id, String search_user_name)
			throws DataAccessException {
		String like_search_user_id = '%' + search_user_id + '%';
		String like_search_user_name = '%' + search_user_name + '%';
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SEARCH_BY_USER_ID_AND_USER_NAME,
				like_search_user_id, like_search_user_name);
		UserEntity userEntity = mappingSelectResult(resultList);
		return userEntity;
	}

}
