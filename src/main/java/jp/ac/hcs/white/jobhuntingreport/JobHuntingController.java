package jp.ac.hcs.white.jobhuntingreport;

import java.io.IOException;
import java.security.Principal;
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

@Slf4j
@Controller
public class JobHuntingController {

	@Autowired
	JobHuntingService jobService;

	/** 権限のラジオボタン用変数 */
	private Map<String, String> radioactivity;
	private Map<String, String> radioattendance;
	private Map<String, String> radiostatus;

	/** 権限のラジオボタンを初期化する処理 */
	private Map<String, String> initRadioActivity() {
		Map<String, String> radioactivity = new LinkedHashMap<>();
		radioactivity.put("合同企業説明会", "合同企業説明会");
		radioactivity.put("単独企業説明会", "単独企業説明会");
		radioactivity.put("試験", "試験");
		radioactivity.put("内定式", "内定式");
		radioactivity.put("インターンシップ", "インターンシップ");
		return radioactivity;
	}

	/** 権限のラジオボタンを初期化する処理 */
	private Map<String, String> initRadioAttendance() {
		Map<String, String> radioattendance = new LinkedHashMap<>();
		radioattendance.put("休日に実施", "休日に実施");
		radioattendance.put("欠席", "欠席");
		radioattendance.put("早退", "早退");
		radioattendance.put("遅刻", "遅刻");
		return radioattendance;
	}

	/** 権限のラジオボタンを初期化する処理 */
	private Map<String, String> initRadioStatus() {
		Map<String, String> radiostatus = new LinkedHashMap<>();
		radiostatus.put("承認済", "承認済");
		radiostatus.put("差戻", "差戻");
		radiostatus.put("取消", "取消");
		return radiostatus;
	}

	/**
	 * 就職活動申請・報告一覧を表示
	 * @param principal ログイン情報
	 * @param model
	 * @return 就職活動申請・報告一覧画面
	 */
	@PostMapping("/job/jobList")
	public String getJobList(Principal principal, Model model) {
		log.info("[" + principal.getName() + "]就職活動検索" + principal.getName());
		JobHuntingEntity jobHuntingEntity;
		jobHuntingEntity = jobService.selectAll(principal.getName());
		model.addAttribute("jobHuntingEntity", jobHuntingEntity);

		return "job/joblist";
	}

	/**
	 * 就職活動申請を検索する
	 * @param search_job_id 検索したい報告状況
	 * @param search_user_name 検索したい氏名
	 * @param model
	 * @return 就職活動申請・報告一覧画面
	 */
	@PostMapping("/job/search")
	public String search(@RequestParam("search_job_id") String search_job_id,
			@RequestParam("search_action_day") String search_action_day,
			@RequestParam("search_user_name") String search_user_name,
			@RequestParam("search_company_name") String search_company_name,
			Model model) {

		JobHuntingEntity jobHuntingEntity = jobService.search(search_job_id, search_action_day, search_user_name, search_company_name);
		model.addAttribute("jobHuntingEntity", jobHuntingEntity);

		// 検索ワードの連携
		model.addAttribute("search_examreport_id", search_job_id);
		model.addAttribute("search_action_day", search_action_day);
		model.addAttribute("search_user_id", search_user_name);
		model.addAttribute("search_company_name", search_company_name);

		return "job/joblist";
	}

	/**
	 * 受験報告の詳細画面を表示する
	 * @param examreport_id 受験報告ID
	 * @param principal ログイン情報
	 * @param model
	 * @return 受験報告詳細画面
	 */
	@GetMapping("/job/jobDetail/{examination_report_id:.+}")
	public String getExamDetail(@PathVariable("examination_report_id") String examination_report_id, Principal principal, Model model) {

		JobHuntingData data = jobService.selectOne(examination_report_id);

		model.addAttribute("jobdata", data);

		return "job/jobDetail";
	}


	/**
	 * 情報をCSVファイルとしてダウンロードさせる.
	 * @param principal ログイン情報
	 * @param model
	 * @return CSVファイル
	 */
	@PostMapping("/job/csv")
	public ResponseEntity<byte[]> getCsv(Principal principal, Model model) {

		log.info("[" + principal.getName() + "]CSVファイル作成:" + WebConfig.FILENAME_CSV);

	// タスク情報のCSVファイルをサーバ上に保存
	//jobService.saveCsv();

		// CSVファイルをサーバから読み込み
		byte[] bytes = null;
		try {
			bytes = jobService.loadCsv(WebConfig.FILENAME_CSV);
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


	/**
	 * 一件分の就活情報新規作成画面を追加する
	 * @param form	追加する就職活動申請情報
	 * @param model
	 * @return	就職活動申請新規作成画面
	 * 	 */
	@GetMapping("/job/jobInsertS")
	public String getJobInsert(@ModelAttribute JobForm form, Model model) {
		// ラジオボタンの準備
		radioactivity = initRadioActivity();
		model.addAttribute("radioActivitycontent", radioactivity);

		radioattendance = initRadioAttendance();
		model.addAttribute("radioAttendance", radioattendance);

		return "job/jobInsertS";
	}




	/**
	 * 一件分の就職活動申請を追加する
	 * @param form	追加する就職活動申請情報
	 * @param bindingResult データバインド実施結果
	 * @param principal ログイン情報
	 * @param model
	 * @return 就職活動申請・報告一覧画面
	 */
	@PostMapping("/job/jobInsertS")
	public String postJobSInsert(@ModelAttribute @Validated JobForm form,
			BindingResult bindingResult,
			Principal principal,
			Model model) {

		// 入力チェックに引っかかった場合、登録画面に戻る
		if (bindingResult.hasErrors()) {
			return getJobInsert(form, model);
		}

		JobHuntingData data = new JobHuntingData();
		data.setAction_day(form.getAction_day());
		data.setAction_end_day(form.getAction_day());
		data.setAction_place(form.getAction_place());
		data.setAction_id(form.getAction_id());
		data.setCompany_name(form.getCompany_name());
		data.setAttendance_id(form.getAttendance_id());
		data.setSchedule(form.getSchedule());
		data.setInformation(form.getInformation());

		boolean result = jobService.insertOne(data);

		if (result) {
			log.info("[" + principal.getName() + "]就職活動申請登録成功");
		} else {
			log.warn("[" + principal.getName() + "]就職活動申請登録失敗");
		}

		return getJobList(principal, model);

	}

//	/**
//	 * 一件分の就職活動申請を状態変更する画面を表示する
//	 * @param form	状態変更する就職活動申請情報
//	 * @param model
//	 * @return	画面
//	 */
//	@GetMapping("/job/jobApproval/{examination_report_id:.+}")
//	public String getStatus(@ModelAttribute JobFormForStatus form, Model model, @PathVariable("examination_report_id") String examination_report_id) {
//		// ラジオボタンの準備
//		radiostatus = initRadioStatus();
//		model.addAttribute("radiostatus", radiostatus);
//		model.addAttribute("examination_report_id", examination_report_id);
//		log.warn(examination_report_id);
//
//
//		return "job/jobApproval";
//	}
//
//	/**
//	 *	一件分の受験報告を承認変更をする
//	 * @param form 承認変更する受験報告情報
//	 * @param bindingResult データバインド実施結果
//	 * @param principal ログイン情報
//	 * @param model
//	 * @param examreport_id 受験報告ID
//	 * @return 受験報告一覧画面
//	 */
//	@PostMapping("/job/jobApproval/{examination_report_id:.+}")
//	public String postStatus(@ModelAttribute @Validated JobFormForStatus form,
//			BindingResult bindingResult,
//			Principal principal,
//			Model model,
//			@PathVariable("examination_report_id") String examination_report_id) {
//
//		log.warn(form.getJob_report_status());
//		log.warn(examination_report_id);
//
//		boolean result = jobService.jobstatus(examination_report_id,form.getJob_report_status());
//		if (result) {
//			log.info("[" + principal.getName() + "]	承認変更成功");
//		} else {
//			log.warn("[" + principal.getName() + "]承認変更失敗");
//		}
//
//		return getJobList(principal, model);
//	}
//
//
//	/**
//	 * 一件分の受験報告を変更する
//	 * @param form 変更する受験報告情報
//	 * @param model
//	 * @param principal ログイン情報
//	 * @param examreport_id 受験報告ID
//	 * @return 受験報告変更画面
//	 */
//	@GetMapping("/exam/examUpdate/{examreport_id:.+}")
//	public String getExamUpdate(@ModelAttribute ExamFormForUpdate form,
//			Model model,
//			Principal principal,
//			@PathVariable("examreport_id")  String examreport_id
//			) {
//
//		// ラジオボタンの準備
//		radioroute = initRadioRoute();
//		model.addAttribute("radioRoute", radioroute);
//
//		radiotest = initRadioTest();
//		model.addAttribute("radioTest", radiotest);
//
//		ExamReportData data = examService.selectOne(examreport_id);
//		log.warn(data.toString());
//		form.setExamreport_id(data.getExamreport_id());
//		form.setDepartment(data.getDepartment());
//		form.setCompany_name_top(data.getCompany_name_top());
//		form.setRecruitment_number(String.valueOf(data.getRecruitment_number()));
//		form.setCompany_name(data.getCompany_name());
//		form.setApplication_route(data.getApplication_route());
//		form.setExam_date_time(data.getExam_date_time());
//		form.setExamination_location(data.getExamination_location());
//		form.setContens_test(data.getContens_test());
//		form.setRemarks(data.getRemarks());
//		model.addAttribute("examFormForUpdate", form);
//		model.addAttribute("examreport_id",examreport_id);
//
//
//		return "exam/examUpdate";
//	}
//
//	/**
//	 * 一件分の受験報告を変更する
//	 * @param form 変更する受験報告情報
//	 * @param bindingResult データバインド実施結果
//	 * @param principal ログイン情報
//	 * @param model
//	 * @return 受験報告一覧画面
//	 */
//	@PostMapping("/exam/examUpdate/{examreport_id:.+}")
//	public String postExamUpdate(@ModelAttribute @Validated ExamFormForUpdate form,
//			BindingResult bindingResult,
//			Principal principal,
//			Model model) {
//
//		// 入力チェックに引っかかった場合、登録画面に戻る
//		if (bindingResult.hasErrors()) {
//			return getExamUpdate(form, model,principal,form.getExamreport_id());
//		}
//
//		ExamReportData data = new ExamReportData();
//		data.setExamreport_id(form.getExamreport_id());
//		data.setDepartment(form.getDepartment());
//		data.setUser_id(principal.getName());
//		data.setCompany_name_top(form.getCompany_name_top());
//		data.setRecruitment_number(Integer.parseInt(form.getRecruitment_number()));
//		data.setCompany_name(form.getCompany_name());
//		data.setApplication_route(form.getApplication_route());
//		data.setExam_date_time(form.getExam_date_time());
//		data.setExamination_location(form.getExamination_location());
//		data.setContens_test(form.getContens_test());
//		data.setRemarks(form.getRemarks());
//
//		boolean result = examService.updateOne(data,form.getExamreport_id());
//
//		if (result) {
//			log.info("[" + principal.getName() + "]受験報告登録成功");
//		} else {
//			log.warn("[" + principal.getName() + "]受験報告登録失敗");
//		}
//
//		return getExamList(principal, model);
//
//	}


}