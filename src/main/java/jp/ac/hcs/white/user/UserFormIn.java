package jp.ac.hcs.white.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

/**
 * アップデート用にパスワード、ダークモード、権限のチェックを外したUserForm.
 * その他の内容はUserFormInに準じる.
 */
@Data
public class UserFormIn {

	//ユーザID
	@NotBlank(message = "{require_check}")
	@Email(message = "{email_check}")
	private String user_id;

	//パスワード
	@NotBlank(message="{require_check}")
	@Length(min = 4, max = 100, message="{length_check}")
	@Pattern(regexp = "^[a-zA-Z0-9]+$",message="{pattern_check}")
	private String password;

	// ダークモードフラグ
	private boolean darkmode;

	//ユーザ名
	@NotBlank(message="{require_check}")
	private String user_name;

	//権限
	@NotBlank(message="{require_check}")
	private String role;

	//所属クラス
	@NotNull
	@NotBlank(message="{require_check}")
	private String user_class;

	//出席番号
	@NotBlank(message="{require_check}")
	private String user_student_no;

}
