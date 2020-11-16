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
	 * - 学生 : "ROLE_STUDENT"
	 * - 先生 : "ROLE_TEACHAR"
	 * - 事務 : "ROLE_STAFF"
	 * 必須入力
	 */
	private String role;

	/**
	 *ユーザのクラス
	 */
	private String user_class;

	/**
	 * ユーザの出席番号
	 */
	private String user_student_no;

	/**
	 * ユーザ状態
	 * 必須入力
	 */
	private int user_status;

	/**
	 * 作成時のタイムスタンプ
	 * 必須入力
	 */
	private Timestamp created_at;

	/**
	 * 作成時のユーザID
	 * 必須入力
	 */
	private String created_user_id;

	/**
	 * 更新時のタイムスタンプ
	 */
	private Timestamp update_at;

	/**
	 * 更新時のユーザID
	 */
	private String update_user_id;

	/**
	 * パスワードエラー回数
	 * ユーザ作成時は0固定
	 * ユーザ状態を有効にした際に0に戻す
	 */
	private int password_error_count;

}
