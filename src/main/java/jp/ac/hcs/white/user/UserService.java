package jp.ac.hcs.white.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

/**
 * ユーザ情報を操作する.
 */
@Slf4j
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
	 * ユーザ情報を1件更新する(パスワード更新有).
	 * @param userData 更新するユーザ情報(パスワードは平文)
	 * @return 処理結果(成功:true, 失敗:false)
	 */
	public boolean updateOneWithPassword(UserData userData) {
		int rowNumber = userRepository.updateOneWithPassword(userData);
		boolean result = (rowNumber > 0) ? true : false;
		return result;
	}


	/**
	 * ユーザ情報を1件更新する(パスワード更新無).
	 * @param userData 更新するユーザ情報(パスワードは設定しない)
	 * @return 処理結果(成功:true, 失敗:false)
	 */
	public boolean updateOne(UserData userData) {
		int rowNumber = userRepository.updateOne(userData);
		boolean result = (rowNumber > 0) ? true : false;
		return result;
	}

	/**
	 * Userテーブルのデータを1件追加する
	 * @param user_id
	 * @param password
	 * @param user_name
	 * @param role
	 * @return userinsert
	 */
	public boolean insertOne(UserData userData, String userId) {

		int rowNumber = userRepository.insertOne(userData, userId);
		boolean result = (rowNumber > 0) ? true : false;
		return result;
	}

	/**
	 * ユーザ情報を1件削除する.
	 * @param userData 削除するユーザID(メールアドレス)
	 * @return 処理結果(成功:true, 失敗:false)
	 */
	public boolean deleteOne(String user_id) {
		int rowNumber = userRepository.deleteOne(user_id);
		boolean result = (rowNumber > 0) ? true : false;
		return result;
	}
	/**
	 * ユーザ検索をする
	 * @param search_user_id
	 * @param search_user_name
	 * @return userEntity
	 */
	public UserEntity search(String search_user_id, String search_user_name) {

		UserEntity userEntity = null;

		// 検索条件によって異なるSQLを使用する場合は、ここで分岐させる
		userEntity = userRepository.searchByUserIdAndUsername(search_user_id, search_user_name);

		return userEntity;
	}

}
