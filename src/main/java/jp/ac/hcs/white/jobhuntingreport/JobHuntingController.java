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

/**
 *
 * 就活状況申請・報告機能
 *
 */
@Slf4j
@Controller
public class JobHuntingController {

	@Autowired
	JobHuntingService jobService;

	/** 権限のラジオボタン用変数 */
	private Map<String, String> radioaction;
	private Map<String, String> radioattendance;
	private Map<String, String> radiostatus;
	private Map<String, String> radioactionstatus;

	/** 権限のラジオボタンを初期化する処理 */
	private Map<String, String> initRadioAction() {

		Map<String, String> radioaction = new LinkedHashMap<>();
		radioaction.put("合同企業説明会", "合同企業説明会");
		radioaction.put("単独企業説明会", "単独企業説明会");
		radioaction.put("試験", "試験");
		radioaction.put("内定式", "内定式");
		radioaction.put("インターンシップ", "インターンシップ");

		return radioaction;
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
		radiostatus.put("承認", "申請承認済");
		radiostatus.put("差戻", "申請作成中");
		radiostatus.put("取消", "取消済");

		return radiostatus;
	}

	/** 権限のラジオボタンを初期化する処理 */
	private Map<String, String> apRadioStatus() {

		Map<String, String> radiostatus = new LinkedHashMap<>();
		radiostatus.put("差戻", "申請作成中");
		radiostatus.put("取消", "取消済");

		return radiostatus;
	}

	/** 権限のラジオボタンを初期化する処理 */
	private Map<String, String> rpRadioStatus() {

		Map<String, String> radiostatus = new LinkedHashMap<>();
		radiostatus.put("取消", "取消済");

		return radiostatus;
	}

	/** 権限のラジオボタンを初期化する処理 */
	private Map<String, String> initRadioActionStatus() {

		Map<String, String> radioactionstatus = new LinkedHashMap<>();
		radioactionstatus.put("続行", "続行");

		return radioactionstatus;
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
	 * @param search_action_day 検索したい日付
	 * * @param search_user_name 検索したい氏名
	 * * @param search_company_name 検索したい会社名
	 * @param model
	 * @return 就職活動申請・報告一覧画面
	 */
	@PostMapping("/job/search")
	public String search(@RequestParam("search_status_id") String search_job_id,
			@RequestParam("search_action_day") String search_action_day,
			@RequestParam("search_user_name") String search_user_name,
			@RequestParam("search_company_name") String search_company_name,
			Principal principal,
			Model model) {

		JobHuntingEntity jobHuntingEntity = jobService.search(search_job_id, search_action_day, search_user_name, search_company_name, principal.getName());
		model.addAttribute("jobHuntingEntity", jobHuntingEntity);
		// 検索ワードの連携
		model.addAttribute("search_examreport_id", search_job_id);
		model.addAttribute("search_action_day", search_action_day);
		model.addAttribute("search_user_id", search_user_name);
		model.addAttribute("search_company_name", search_company_name);

		return "job/joblist";
	}

	/**
	 * 就職活動新規報告作成の詳細画面を表示する
	 * @param examination_report_id 就職活動申請・報告ID
	 * @param principal ログイン情報
	 * @param model
	 * @return 就職活動申請・報告詳細画面
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
		jobService.saveCsv();

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
	 * 一件分の就活情報申請新規作成画面を表示する
	 * @param form	追加する就職活動申請情報
	 * @param model
	 * @return	就職活動申請新規作成画面
	 * 	 */
	@GetMapping("/job/jobInsertS")
	public String getJobInsert(@ModelAttribute JobFormS form, Model model) {

		// ラジオボタンの準備
		radioaction = initRadioAction();
		model.addAttribute("radioAction", radioaction);

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
	public String postJobSInsert(@ModelAttribute @Validated JobFormS form,
			BindingResult bindingResult,
			Principal principal,
			Model model) {

		// 入力チェックに引っかかった場合、登録画面に戻る
		if (bindingResult.hasErrors()) {
			return getJobInsert(form, model);
		}
		JobHuntingData data = new JobHuntingData();
		data.setAction_day(form.getAction_day());
		data.setAction_end_day(form.getAction_end_day());
		data.setAction_place(form.getAction_place());
		data.setAction_id(form.getAction_id());
		data.setCompany_name(form.getCompany_name());
		data.setAttendance_id(form.getAttendance_id());
		data.setAttendance_day(form.getAttendance_day());
		data.setAttendance_end_day(form.getAttendance_end_day());
		data.setSchedule(form.getSchedule());
		data.setInformation(form.getInformation());
		data.setUser_id(principal.getName());

		boolean result = jobService.insertOne(data);

		if (result) {
			log.info("[" + principal.getName() + "]就職活動申請登録成功");
		} else {
			log.warn("[" + principal.getName() + "]就職活動申請登録失敗");
		}

		return getJobList(principal, model);

	}

	/**
	 * 一件分の就職活動申請を状態変更する画面を表示する
	 * @param examination_report_id 就職活動申請・報告ID
	 * @param form	状態変更する就職活動申請情報
	 * @param model
	 * @return	就職活動申請変更画面
	 */
	@GetMapping("/job/jobApproval/{examination_report_id:.+}")
	public String getStatus(@ModelAttribute JobFormForStatus form, Model model, Principal principal, @PathVariable("examination_report_id") String examination_report_id) {
		JobHuntingData data = jobService.selectOne(examination_report_id);

		// ラジオボタンの準備
		if (data.getExamination_status_id().equals("申請承認済") || data.getExamination_status_id().equals("申請完了")){
			radiostatus = apRadioStatus();
		}else if(data.getExamination_status_id().equals("報告完了")) {
			radiostatus = rpRadioStatus();
		}else {
			radiostatus = initRadioStatus();
		}
		model.addAttribute("radiostatus", radiostatus);
		model.addAttribute("examination_report_id", examination_report_id);

		return "job/jobApproval";
	}
	/**
	 *	一件分の就職活動申請の承認変更をする
	 * @param form 承認変更する就職活動申請情報
	 * @param bindingResult データバインド実施結果
	 * @param principal ログイン情報
	 * @param model
	 * @param examination_report_id 就職活動申請・報告ID
	 * @return 就職活動申請・報告一覧画面
	 */
	@PostMapping("/job/jobApproval/{examination_report_id:.+}")
	public String postStatus(@ModelAttribute @Validated JobFormForStatus form,
			BindingResult bindingResult,
			Principal principal,
			Model model,
			@PathVariable("examination_report_id") String examination_report_id) {

		// 入力チェックに引っかかった場合、登録画面に戻る
		if (bindingResult.hasErrors()) {
			return getStatus(form, model, principal,form.getExamination_report_id());
		}
		boolean result;
		JobHuntingData data = jobService.selectOne(examination_report_id);

		if (data.getExamination_status_id().equals("報告承認待") && form.getExamination_status_id().equals("申請作成中")){
			result = jobService.jobstatus(examination_report_id,"報告作成中");
		}else {
			result = jobService.jobstatus(examination_report_id,form.getExamination_status_id());
		}

		if (result) {
			log.info("[" + principal.getName() + "]	承認変更成功");
		} else {
			log.warn("[" + principal.getName() + "]承認変更失敗");
		}
		return getJobList(principal, model);
	}


	/**
	 * 一件分の就職活動申請・報告内容を更新する
	 * @param form 変更する就職活動申請情報
	 * @param model
	 * @param principal ログイン情報
	 * @return 就職活動申請・報告更新画面
	 */
	@GetMapping("/job/jobUpdate/{examination_report_id:.+}")
	public String getJobUpdate(@ModelAttribute JobFormForUpdate form,
			Model model,
			Principal principal,
			@PathVariable("examination_report_id")  String examination_report_id
			) {

		// ラジオボタンの準備
		radioaction = initRadioAction();
		model.addAttribute("radioAction", radioaction);

		radioattendance = initRadioAttendance();
		model.addAttribute("radioAttendance", radioattendance);

		JobHuntingData data = jobService.selectOne(examination_report_id);
		log.warn(data.toString());
		form.setAction_day((data.getAction_day()!= null) ? data.getAction_day().replace(" ", "T") : null);
		form.setAction_end_day((data.getAction_end_day()!= null) ? data.getAction_end_day().replace(" ", "T") : null);
		form.setAction_place(data.getAction_place());
		form.setAction_id(data.getAction_id());
		form.setCompany_name(data.getCompany_name());
		form.setAttendance_id(data.getAttendance_id());
		form.setAttendance_day((data.getAttendance_day()!= null) ? data.getAttendance_day().replace(" ", "T") : null);
		form.setAttendance_end_day((data.getAttendance_end_day()!= null) ? data.getAttendance_end_day().replace(" ", "T") : null);
		form.setSchedule(data.getSchedule());
		form.setInformation(data.getInformation());
		form.setContents_report(data.getContents_report());
		model.addAttribute("JobFormForUpdate", form);
		model.addAttribute("examination_report_id",examination_report_id);

		return "job/jobUpdate";
	}

	/**
	 * 一件分の就職活動申請・報告内容を更新する
	 * @param form 変更する就職活動申請情報
	 * @param bindingResult データバインド実施結果
	 * @param principal ログイン情報
	 * @param model
	 * @return 就職活動申請・報告一覧画面
	 */
	@PostMapping("/job/jobUpdate/{examination_report_id:.+}")
	public String postJobUpdate(@ModelAttribute @Validated JobFormForUpdate form,
			BindingResult bindingResult,
			Principal principal,
			Model model) {

		// 入力チェックに引っかかった場合、登録画面に戻る
		if (bindingResult.hasErrors()) {
			return getJobUpdate(form, model,principal,form.getExamination_report_id());
		}
		JobHuntingData data = new JobHuntingData();
		data.setAction_day(form.getAction_day());
		data.setAction_end_day(form.getAction_end_day());
		data.setAction_place(form.getAction_place());
		data.setAction_id(form.getAction_id());
		data.setCompany_name(form.getCompany_name());
		data.setAttendance_id(form.getAttendance_id());
		data.setAttendance_day(form.getAttendance_day());
		data.setAttendance_end_day(form.getAttendance_end_day());
		data.setSchedule(form.getSchedule());
		data.setInformation(form.getInformation());
		data.setContents_report(form.getContents_report());

		boolean result = jobService.updateOneS(data,form.getExamination_report_id());

		if (result) {
			log.info("[" + principal.getName() + "]受験報告登録成功");
		} else {
			log.warn("[" + principal.getName() + "]受験報告登録失敗");
		}
		return getJobList(principal, model);

	}

	/**
	 * 一件分の就職活動報告新規作成画面を登表示する
	 * @param form 登録する就職活動報告情報
	 * @param model
	 * @param principal ログイン情報
	 * @return 就職活動報告新規作成画面
	 */
	@GetMapping("/job/jobInsertH/{examination_report_id:.+}")
	public String getJobInsertH(@ModelAttribute JobFormH form,
			Model model,
			Principal principal,
			@PathVariable("examination_report_id")  String examination_report_id
			) {

		// ラジオボタンの準備
		radioaction = initRadioAction();
		model.addAttribute("radioAction", radioaction);
		radioattendance = initRadioAttendance();
		model.addAttribute("radioAttendance", radioattendance);
		radioactionstatus = initRadioActionStatus();
		model.addAttribute("radioActionStatus", radioactionstatus);

		JobHuntingData data = jobService.selectOne(examination_report_id);
		form.setAction_day(data.getAction_day());
		form.setAction_end_day(data.getAction_end_day());
		form.setAction_place(data.getAction_place());
		form.setAction_id(data.getAction_id());
		form.setCompany_name(data.getCompany_name());
		form.setAttendance_id(data.getAttendance_id());
	    form.setAttendance_day(data.getAttendance_day());
		form.setAttendance_end_day(data.getAttendance_end_day());
		form.setSchedule(data.getSchedule());
		form.setInformation(data.getInformation());
		form.setContents_report(data.getContents_report());
		model.addAttribute("JobForm", form);
		model.addAttribute("examination_report_id",examination_report_id);

		return "job/jobInsertH";
	}

	/**
	 * 一件分の就職活動報告内容を登録する
	 * @param form 変更する就職活動報告情報
	 * @param bindingResult データバインド実施結果
	 * @param principal ログイン情報
	 * @param model
	 * @return 就職活動申請・報告一覧画面
	 */
	@PostMapping("/job/jobInsertH/{examination_report_id:.+}")
	public String postJobInsertH(@ModelAttribute @Validated JobFormH form,
			BindingResult bindingResult,
			Principal principal,
			Model model) {

		// 入力チェックに引っかかった場合、登録画面に戻る
		if (bindingResult.hasErrors()) {
			return getJobInsertH(form, model,principal,form.getExamination_report_id());
		}
		JobHuntingData data = new JobHuntingData();
		data.setAction_day(form.getAction_day());
		data.setAction_end_day(form.getAction_end_day());
		data.setAction_place(form.getAction_place());
		data.setAction_id(form.getAction_id());
		data.setCompany_name(form.getCompany_name());
		data.setAttendance_id(form.getAttendance_id());
		data.setAttendance_day(form.getAttendance_day());
		data.setAttendance_end_day(form.getAttendance_end_day());
		data.setSchedule(form.getSchedule());
		data.setInformation(form.getInformation());
		data.setContents_report(form.getContents_report());
		data.setAction_status_id(form.getAction_status_id());

		boolean result = jobService.updateOneH(data,form.getExamination_report_id());

		if (result) {
			log.info("[" + principal.getName() + "]就職活動報告登録成功");
		} else {
			log.warn("[" + principal.getName() + "]就職活動報告登録失敗");
		}
		return getJobList(principal, model);

	}

	/**
	 * 一件分の就職活動申請を申請承認済みに変更する
	 * @param principal ログイン情報
	 * @param model
	 * @return 就職活動申請・報告一覧画面
	 */
	@GetMapping("/job/jobsyonin/{examination_report_id:.+}")
	public String getJobList(Model model,Principal principal,
			@PathVariable("examination_report_id")  String examination_report_id) {

		boolean result = jobService.jobstatus(examination_report_id,"申請完了");

		if (result) {
			log.info("[" + principal.getName() + "]	承認変更成功");
		} else {
			log.warn("[" + principal.getName() + "]承認変更失敗");
		}
		return getJobList(principal, model);

	}

}