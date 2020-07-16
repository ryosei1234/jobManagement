package jp.ac.hcs.white.user;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

/**
 * ユーザ情報に関する機能・画面を制御する.
 */
@Slf4j
@Controller
public class UserController {

	@Autowired
	UserService userService;

	/** 権限のラジオボタン用変数 */
	private Map<String, String> radioRole;

	/** 権限のラジオボタンを初期化する処理 */
	private Map<String, String> initRadioRole() {
		Map<String, String> radio = new LinkedHashMap<>();
		radio.put("学生", "ROLE_STUDENT");
		radio.put("担任", "ROLE_TEACHER");
		radio.put("事務", "ROLE_STAFF" );
		return radio;
	}

	/**
	 * ユーザ一覧画面を表示する.
	 * @param model
	 * @return ユーザ一覧画面
	 */
	@GetMapping("/user/userList")
	public String getUserList(Model model) {

		UserEntity userEntity = userService.selectAll();
		model.addAttribute("userEntity", userEntity);
		return "user/userList";
	}

	/**
	 * ユーザ詳細情報画面を表示する.
	 * @param form 更新時の入力チェック用UserForm
	 * @param user_id 検索するユーザID
	 * @param principal ログイン情報
	 * @param model
	 * @return ユーザ詳細情報画面
	 */
	@GetMapping("/user/userDetail/{id:.+}")
	public String getUserDetail(@ModelAttribute UserFormForUpdate form,
			@PathVariable("id") String user_id,
			Principal principal,
			Model model) {

		// ラジオボタンの準備
		radioRole = initRadioRole();
		model.addAttribute("radioRole", radioRole);

		// 検索するユーザーIDのチェック
		if (user_id != null && user_id.length() > 0) {

			log.info("[" + principal.getName() + "]ユーザ検索:" + user_id);

			UserData data = userService.selectOne(user_id);

			// データ表示準備(パスワードは暗号化済の為、表示しない)
			form.setUser_id(data.getUser_id());
			form.setUser_name(data.getUser_name());
			form.setDarkmode(data.isUser_darkmode());
			form.setUser_class(data.getUser_class());
			form.setUser_student_no(data.getUser_student_no());
			form.setRole(data.getRole());
			model.addAttribute("userFormForUpdate", form);
		}

		return "user/userDetail";
	}

	/**
	 * 1件分のユーザ情報でデータベースを更新する.
	 * パスワードの更新が不要の場合は、画面側で何も値を設定しないものとする.
	 * @param form 更新するユーザ情報(パスワードは平文)
	 * @param bindingResult データバインド実施結果
	 * @param principal ログイン情報
	 * @param model
	 * @return ユーザ一覧画面
	 */
	@PostMapping(value = "/user/userDetail", params = "update")
	public String postUserDetailUpdate(@ModelAttribute @Validated UserFormForUpdate form,
			BindingResult bindingResult,
			Principal principal,
			Model model) {
		System.out.println(form.getUser_student_no());
		System.out.println();
		// 入力チェックに引っかかった場合、前の画面に戻る
		if (bindingResult.hasErrors()) {
			return getUserDetail(form, form.getUser_id(), principal, model);
		}

		log.info("[" + principal.getName() + "]ユーザ更新:" + form.toString());

		// ダークモードは更新しない為、設定無し
		UserData data = new UserData();
		data.setUser_id(form.getUser_id());
		data.setUser_name(form.getUser_name());
		data.setRole(form.getRole());
		data.setUser_class(form.getUser_class());
		data.setUser_student_no(form.getUser_student_no());
		data.setUpdate_user_id(principal.getName());

		boolean result = false;

		// パスワード更新有
		data.setPassword(form.getPassword());
		result = userService.updateOneWithPassword(data);

		if (result) {
			log.info("[" + principal.getName() + "]ユーザ更新成功");
			model.addAttribute("result", "ユーザ更新成功");
		} else {
			log.warn("[" + principal.getName() + "]ユーザ更新失敗");
			model.addAttribute("result", "ユーザ更新失敗");
		}

		return getUserList(model);
	}

	/**
	 * 一件分のユーザを追加する
	 * @param userform 追加するユーザ情報
	 * @param model データバインド実施結果
	 * @return 新規登録画面
	 */
	@GetMapping("/user/userInsert")
	public String getUserInsert(@ModelAttribute UserFormIn userformin, Model model){

		// ラジオボタンの準備
		radioRole = initRadioRole();
		model.addAttribute("radioRole", radioRole);

		return "user/userInsert";
	}

	/**
	 *  一件分のユーザを追加する
	 * @param form 追加するユーザ情報
	 * @param bindingResult
	 * @param principal ログイン情報
	 * @param model
	 * @return ユーザ一覧画面
	 */
	@PostMapping("/user/userInsert")
	public String insertOne(@ModelAttribute @Validated UserFormIn form,
			BindingResult bindingResult,
			Principal principal,
			Model model){

		UserEntity userEntity = userService.selectAll();
		System.out.println("ユーザエンティティ" + userEntity.getUserlist());
		model.addAttribute("userEntity", userEntity);
		for (UserData data : userEntity.getUserlist()) {
			if (form.getUser_id().equals(data.getUser_id())){
				return "/user/userInsert";
			}else {
				System.out.println("aaaaa" + data.getUser_id());
			};
		}

		if (bindingResult.hasErrors()) {
			return getUserList(model);
		}
		log.info("[" + principal.getName() + "]ユーザ追加:" + principal.getName());

		UserData data = new UserData();
		data.setUser_id(form.getUser_id());
		data.setPassword(form.getPassword());
		data.setUser_name(form.getUser_name());
		data.setUser_darkmode(false);
		data.setRole(form.getRole());
		data.setUser_class(form.getUser_class());
		data.setUser_student_no(form.getUser_student_no());
		log.warn("インサート;;" + data.toString());

		boolean result = userService.insertOne(data, principal.getName());

		if (result) {
			log.info("[" + principal.getName() + "]ユーザ登録成功");
		} else {
			log.warn("[" + principal.getName() + "]ユーザ登録失敗");
		}

		return getUserList(model);
	}

	/**
	 * 一件分のユーザを削除する
	 * @param user_id 削除するユーザID（メールアドレス)
	 * @param principal ログイン情報
	 * @param model
	 * @return ユーザ一覧画面
	 */
	@PostMapping(value = "user/userDetail", params = "delete")
	public String postUserDetailDelete(@RequestParam("user_id") String user_id,
			Principal principal,
			Model model) {
		log.info("[" + principal.getName() + "]ユーザ削除:" + user_id);

		boolean result = userService.deleteOne(user_id);

		if (result) {
			log.info("[" + principal.getName() + "]ユーザ削除成功");
		} else {
			log.warn("[" + principal.getName() + "]ユーザ削除失敗");
		}

		return getUserList(model);
	}

	/**
	 * ユーザを検索する
	 * @param search_user_id 検索するユーザID
	 * @param search_user_name 検索するユーザ名
	 * @param model
	 * @return ユーザ一覧画面
	 */
	@PostMapping("/user/search")
	public String search(@RequestParam("search_user_id") String search_user_id,
			@RequestParam("search_user_name") String search_user_name, Model model) {

		UserEntity userEntity = userService.search(search_user_id, search_user_name);
		model.addAttribute("userEntity", userEntity);

		// 検索ワードの連携
		model.addAttribute("search_user_id", search_user_id);
		model.addAttribute("search_user_name", search_user_name);

		return "user/userList";
	}
}

