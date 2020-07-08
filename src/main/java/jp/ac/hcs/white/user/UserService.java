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
	 * (管理用)Userテーブルのデータを1件追加する
	 * @param user_id
	 * @param password
	 * @param user_name
	 * @param role
	 * @return userinsert
	 */
	public int insertOne(String user_id,String password, String user_name, String role, String user_class, int user_student_no, String user_status,
						String created_at, String created_user_id) {

		UserData userData = new UserData();
		userData.setUser_id(user_id);
		userData.setPassword(password);
		userData.setUser_name(user_name);
		userData.setRole(role);
		userData.setUser_class(user_class);
		userData.setUser_student_no(user_student_no);
		userData.setUser_status(user_status);
		userData.setCreated_at(created_at);
		userData.setCreated_user_id(created_user_id);
		int userinsert = userRepository.insertOne(userData);
		return userinsert;
	}

	public int deleteOne(String user_id) {
		int rowNumber = userRepository.deleteOne(user_id);
		return rowNumber;
	}
}
