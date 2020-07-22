package jp.ac.hcs.white.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserFormForUpdate {
	//ユーザID
	@NotNull
	@NotBlank(message = "{require_check}")
	@Email(message = "{email_check}")
	private String user_id;

	//パスワード
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
	private String user_student_no;


}
