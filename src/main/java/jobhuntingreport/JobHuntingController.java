package jobhuntingreport;

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

	/**
	 * 就職活動申請・報告一覧を表示
	 * @param principal ログイン情報
	 * @param model
	 * @return 就職活動申請・報告一覧画面
	 */
	@PostMapping("/job/joblist")
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
			@RequestParam("search_user_name") String search_action_day,
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
	@GetMapping("/exam/examDetail/{examination_report_id:.+}")
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
	 * 一件分の就活情報新規作成画面を追加する
	 * @param form	追加する就職活動申請情報
	 * @param model
	 * @return	就職活動申請新規作成画面
	 */
	@GetMapping("/job/jobSInsert")
	public String getJobInsert(@ModelAttribute JobForm form, Model model) {
		// ラジオボタンの準備
		radioactivity = initRadioActivity();
		model.addAttribute("radioActivitycontent", radioactivity);

		radioattendance = initRadioAttendance();
		model.addAttribute("radioAttendance", radioattendance);

		return "job/jobSInsert";
	}

	/**
	 * 一件分の就職活動申請を追加する
	 * @param form	追加する就職活動申請情報
	 * @param bindingResult データバインド実施結果
	 * @param principal ログイン情報
	 * @param model
	 * @return 就職活動申請・報告一覧画面
	 */
	@PostMapping("/job/jobSInsert")
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
		data.setAction_id(Integer.parseInt(form.getAction_id()));
		data.setCompany_name(form.getCompany_name());
		data.setAttendance_id(Integer.parseInt(form.getAttendance_id()));
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


}