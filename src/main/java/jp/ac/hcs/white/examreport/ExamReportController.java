package jp.ac.hcs.white.examreport;

import java.io.IOException;
import java.security.Principal;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.ac.hcs.white.WebConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * 受験報告一覧情報
 */
@Slf4j
@Controller
public class ExamReportController {

	@Autowired
	ExamReportService examService;

	/** 権限のラジオボタン用変数 */
	private Map<String, String> radioroute;
	private Map<String, String> radiotest;

	/** 権限のラジオボタンを初期化する処理 */
	private Map<String, String> initRadioRoute() {
		Map<String, String> radioroute = new LinkedHashMap<>();
		radioroute.put("学校斡旋", "学校斡旋");
		radioroute.put("ネット", "ネット");
		radioroute.put("斡旋サイト", "斡旋サイト");
		radioroute.put("企業HP", "企業HP");
		radioroute.put("新聞・雑誌", "新聞・雑誌");
		radioroute.put("ジョブカフェ等", "ジョブカフェ等");
		radioroute.put("その他", "その他");
		return radioroute;
	}

	/** 権限のラジオボタンを初期化する処理 */
	private Map<String, String> initRadioTest() {
		Map<String, String> radiotest = new LinkedHashMap<>();
		radiotest.put("1次試験", "1次試験");
		radiotest.put("2次試験", "2次試験");
		radiotest.put("3次試験", "3次試験");
		radiotest.put("4次試験", "4次試験");
		radiotest.put("次試験", "次試験");
		radiotest.put("最終試験", "最終試験");
		return radiotest;
	}

	/**
	 * 受験報告一覧を表示
	 * @param principal ログイン情報
	 * @param model
	 * @return 受験報告一覧画面
	 */
	@PostMapping("/exam/examlist")
	public String getExamList(Principal principal, Model model) {
		log.info("[" + principal.getName() + "]受験報告検索" + principal.getName());
		ExamReportEntity examEntity;
		examEntity = examService.selectAll(principal.getName());
		model.addAttribute("examEntity", examEntity);

		return "exam/examlist";
	}

	/**
	 * 一件分の受験報告を追加する
	 * @param form	追加する受験報告情報
	 * @param model
	 * @return	受験報告登録画面
	 */
	@GetMapping("/exam/examInsert")
	public String getExamInsert(@ModelAttribute ExamForm form, Model model) {
		// ラジオボタンの準備
		radioroute = initRadioRoute();
		model.addAttribute("radioRoute", radioroute);

		radiotest = initRadioTest();
		model.addAttribute("radioTest", radiotest);

		return "exam/examInsert";
	}

	/**
	 * 一件分の受験報告を追加する
	 * @param form	追加する受験報告情報
	 * @param bindingResult データバインド実施結果
	 * @param principal ログイン情報
	 * @param model
	 * @return 受験報告一覧画面
	 */
	@PostMapping("/exam/examInsert")
	public String postExamInsert(@ModelAttribute @Validated ExamForm form,
			BindingResult bindingResult,
			Principal principal,
			Model model) {

		// 入力チェックに引っかかった場合、登録画面に戻る
		if (bindingResult.hasErrors()) {
			return getExamInsert(form, model);
		}

		ExamReportData data = new ExamReportData();
		data.setDepartment(form.getDepartment());
		data.setUser_id(principal.getName());
		data.setCompany_name_top(form.getCompany_name_top());
		data.setRecruitment_number(form.getRecruitment_number());
		data.setCompany_name(form.getCompany_name());
		data.setApplication_route(form.getApplication_route());
		data.setExam_date_time(form.getExam_date_time());
		data.setExamination_location(form.getExamination_location());
		data.setContens_test(form.getContens_test());
		data.setRemarks(form.getRemarks());

		boolean result = examService.insertOne(data);

		if (result) {
			log.info("[" + principal.getName() + "]受験報告登録成功");
		} else {
			log.warn("[" + principal.getName() + "]受験報告登録失敗");
		}

		return getExamList(principal, model);

	}

	/**
	 * 受験報告の詳細画面を表示する
	 * @param examreport_id 受験報告ID
	 * @param principal ログイン情報
	 * @param model
	 * @return 受験報告詳細画面
	 */
	@GetMapping("/exam/examDetail/{examreport_id:.+}")
	public String getExamDetail(@PathVariable("examreport_id") String examreport_id, Principal principal, Model model) {

		ExamReportData data = examService.selectOne(examreport_id);

		model.addAttribute("examdata", data);

		return "exam/examDetail";
	}

	/**
	 * 受験報告を検索する
	 * @param search_examreport_id 検索したい受験報告ID
	 * @param search_user_id 検索したいユーザID
	 * @param search_company_name 検索したい企業名
	 * @param model
	 * @return 受験報告一覧画面
	 */
	@PostMapping("/exam/search")
	public String search(@RequestParam("search_examreport_id") String search_examreport_id,
			@RequestParam("search_user_id") String search_user_id,
			@RequestParam("search_company_name") String search_company_name,
			Model model) {

		ExamReportEntity examEntity = examService.search(search_examreport_id, search_user_id, search_company_name);
		model.addAttribute("examEntity", examEntity);

		// 検索ワードの連携
		model.addAttribute("search_examreport_id", search_examreport_id);
		model.addAttribute("search_user_id", search_user_id);
		model.addAttribute("search_company_name", search_company_name);

		return "exam/examlist";
	}

	/**
	 * 情報をCSVファイルとしてダウンロードさせる.
	 * @param principal ログイン情報
	 * @param model
	 * @return CSVファイル
	 */
	@PostMapping("/exam/csv")
	public ResponseEntity<byte[]> getCsv(Principal principal, Model model) {

		log.info("[" + principal.getName() + "]CSVファイル作成:" + WebConfig.FILENAME_CSV);

		// タスク情報のCSVファイルをサーバ上に保存
		examService.saveCsv();

		// CSVファイルをサーバから読み込み
		byte[] bytes = null;
		try {
			bytes = examService.loadCsv(WebConfig.FILENAME_CSV);
			log.info("[" + principal.getName() + "]CSVファイル読み込み成功:" + WebConfig.FILENAME_CSV);
		} catch (IOException e) {
			log.warn("[" + principal.getName() + "]CSVファイル読み込み失敗:" + WebConfig.FILENAME_CSV);
			e.printStackTrace();
		}

		// CSVファイルのダウンロード用ヘッダー情報設定
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "text/csv; charset=UTF-8");
		header.setContentDispositionFormData("filename", WebConfig.FILENAME_CSV);

		// CSVファイルを端末へ送信
		return new ResponseEntity<byte[]>(bytes, header, HttpStatus.OK);
	}




	@GetMapping("/exam/examUpdate/{examreport_id:.+}")
	public String getExamUpdate(@ModelAttribute ExamFormForUpdate form,
			Model model,
			Principal principal,
			@PathVariable("examreport_id")  String examreport_id
			) {

		// ラジオボタンの準備
		radioroute = initRadioRoute();
		model.addAttribute("radioRoute", radioroute);

		radiotest = initRadioTest();
		model.addAttribute("radioTest", radiotest);

		ExamReportData data = examService.selectOne(examreport_id);
		log.warn(data.toString());
		form.setExamreport_id(data.getExamreport_id());
		form.setDepartment(data.getDepartment());
		form.setCompany_name_top(data.getCompany_name_top());
		form.setRecruitment_number(String.valueOf(data.getRecruitment_number()));
		form.setCompany_name(data.getCompany_name());
		form.setApplication_route(data.getApplication_route());
		Date sqlDate= Date.valueOf((data.getExam_date_time()));
		form.setExamination_location(data.getExamination_location());
		form.setContens_test(data.getContens_test());
		form.setRemarks(data.getRemarks());
		model.addAttribute("examFormForUpdate", form);
		//model.addAttribute("sqlDate", sqlDate);
		model.addAttribute("examreport_id",examreport_id);


		return "exam/examUpdate";
	}


	@PostMapping("/exam/examUpdate")
	public String postExamUpdate(@ModelAttribute @Validated ExamFormForUpdate form,
			BindingResult bindingResult,
			Principal principal,
			Model model) {

		// 入力チェックに引っかかった場合、登録画面に戻る
		if (bindingResult.hasErrors()) {
			return getExamUpdate(form, model,principal,form.getExamreport_id());
		}

		ExamReportData data = new ExamReportData();
		data.setExamreport_id(form.getExamreport_id());
		data.setDepartment(form.getDepartment());
		data.setUser_id(principal.getName());
		data.setCompany_name_top(form.getCompany_name_top());
		data.setRecruitment_number(Integer.parseInt(form.getRecruitment_number()));
		data.setCompany_name(form.getCompany_name());
		data.setApplication_route(form.getApplication_route());
		data.setExam_date_time(form.getExam_date_time());
		data.setExamination_location(form.getExamination_location());
		data.setContens_test(form.getContens_test());
		data.setRemarks(form.getRemarks());

		boolean result = examService.updateOne(data,form.getExamreport_id());

		if (result) {
			log.info("[" + principal.getName() + "]受験報告登録成功");
		} else {
			log.warn("[" + principal.getName() + "]受験報告登録失敗");
		}

		return getExamList(principal, model);

	}
}
