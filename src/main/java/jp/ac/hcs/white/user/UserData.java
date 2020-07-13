package jp.ac.hcs.white.user;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 1件分のユーザ情報.
 * 各項目のデータ仕様も合わせて管理する.
 */
@Data
@NoArgsConstructor
public class UserData {

	/**
	 * ユーザID（メールアドレス）
	 * 主キー、必須入力、メールアドレス形式
	 */
	private String user_id;

	/**
	 * パスワード
	 * 必須入力、長さ4から100桁まで、半角英数字のみ
	 */
	private String password;

	/**
	 * ユーザ名
	 * 必須入力
	 */
	private String user_name;

	/**
	 * ダークモードフラグ
	 * - ON  : true
	 * - OFF : false
	 * ユーザ作成時はfalse固定
	 */
	private boolean user_darkmode;

	/**
	 * 権限
	 * - 管理 : "ROLE_ADMIN"
	 * - 一般 : "ROLE_GENERAL"
	 * 必須入力
	 */
	private String role;

	/**
	 *
	 */
	private String user_class;

	/**
	 *
	 */
	private int user_student_no;

	/**
	 *
	 */
	private String user_status;

	/**
	 *
	 */
	private Timestamp created_at;

	/**
	 *
	 */
	private String created_user_id;

	/**
	 *
	 */
	private Timestamp update_at;

	/**
	 *
	 */
	private String update_user_id;

}
