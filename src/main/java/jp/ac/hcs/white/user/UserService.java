package jp.ac.hcs.white.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ユーザ情報を操作する.
 */
@Transactional
@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	/**
	 * ユーザ情報を全件取得する.
	 * @return UserEntity
	 */
	public UserEntity selectAll() {
		return userRepository.selectAll();
	}

	/**
	 * 指定したユーザIDのユーザ情報を取得する.
	 * @param user_id ユーザID
	 * @return UserData
	 */
	public UserData selectOne(String user_id) {
		return userRepository.selectOne(user_id);
	}


	/**
	 * (管理用)ユーザ情報を1件更新する(パスワード更新有).
	 * @param userData 更新するユーザ情報(パスワードは平文)
	 * @return 処理結果(成功:true, 失敗:false)
	 */
	public boolean updateOneWithPassword(UserData userData) {
		int rowNumber = userRepository.updateOneWithPassword(userData);
		boolean result = (rowNumber > 0) ? true : false;
		return result;
	}


	/**
	 * (管理用)ユーザ情報を1件更新する(パスワード更新無).
	 * @param userData 更新するユーザ情報(パスワードは設定しない)
	 * @return 処理結果(成功:true, 失敗:false)
	 */
	public boolean updateOne(UserData userData) {
		int rowNumber = userRepository.updateOne(userData);
		boolean result = (rowNumber > 0) ? true : false;
		return result;
	}
	/**
	 * (管理用)Userテーブルのデータを1件追加する
	 * @param user_id
	 * @param password
	 * @param user_name
	 * @param role
	 * @return userinsert
	 */
	public int insertOne(String user_id,String password, String user_name, String role) {
		UserData userData = new UserData();
		userData.setUser_id(user_id);
		userData.setPassword(password);
		userData.setUser_name(user_name);
		userData.setRole(role);
		int userinsert = userRepository.insertOne(userData);
		return userinsert;
	}
	/**
	 * (管理用)Userテーブルのデータを1件削除する
	 * @param user_id
	 * @return rowNumber
	 */
	public int deleteOne(String user_id) {
		int rowNumber = userRepository.deleteOne(user_id);
		return rowNumber;
	}
}
