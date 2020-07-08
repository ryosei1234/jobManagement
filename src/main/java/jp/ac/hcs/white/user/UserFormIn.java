package jp.ac.hcs.white.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class UserFormIn {
	//ユーザID
	@NotNull
	@NotBlank(message = "{require_check}")
	@Email(message = "{email_check}")
	private String user_id;

	//パスワード
	@NotBlank(message="{require_check}")
	@Length(min = 4, max = 100, message="{password_check}")
	@Pattern(regexp = "^[a-zA-Z0-9]+$",message="{password_check}")
	private String password;

	/** ダークモードフラグ */
	private boolean darkmode;

	//ユーザ名
	@NotNull
	@NotBlank(message="{require_check}")
	private String user_name;

	//権限
	@NotNull
	@NotBlank(message="{require_check}")
	private String role;

	//所属クラス
	@NotNull
	@NotBlank(message="{require_check}")
	private String user_class;

	//出席番号
	@NotNull
	@NotBlank(message="{require_check}")
	private int user_student_no;

	//ユーザ状態
	@NotNull
	@NotBlank(message="{require_check}")
	private String user_status;

	//受験報告作成日時
	@NotNull
	@NotBlank(message="{require_check}")
	private String created_at;

	//作成者のユーザid
	@NotNull
	@NotBlank(message="{require_check}")
	private String created_user_id;

	//受験報告更新日時
	@NotNull
	@NotBlank(message="{require_check}")
	private String update_at;

	//更新者のユーザid
	@NotNull
	@NotBlank(message="{require_check}")
	private String update_user_id;
}
