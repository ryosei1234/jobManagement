package jobhuntingreport;

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
import org.springframework.web.bind.annotation.PostMapping;

import jp.ac.hcs.white.examreport.ExamForm;
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
	 * 一件分の就活情報新規作成画面を追加する
	 * @param form	追加する就職活動申請情報
	 * @param model
	 * @return	就職活動申請新規作成画面
	 */
	@GetMapping("/job/jobSInsert")
	public String getJobInsert(@ModelAttribute ExamForm form, Model model) {
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
		data.setaction_day(form.getaction_day());
		data.setaction_end_day(form.getaction_end_day());
		data.setCompany_name(form.getCompany_name());
		data.setRecruitment_number(Integer.parseInt(form.getRecruitment_number()));
		data.setCompany_name(form.getCompany_name());
		data.setApplication_route(form.getApplication_route());
		data.setExam_date_time(form.getExam_date_time());
		data.setExamination_location(form.getExamination_location());
		data.setContens_test(form.getContens_test());
		data.setRemarks(form.getRemarks());

		boolean result = jobService.insertOne(data);

		if (result) {
			log.info("[" + principal.getName() + "]就職活動申請登録成功");
		} else {
			log.warn("[" + principal.getName() + "]就職活動申請登録失敗");
		}

		return getJobList(principal, model);

	}


}