package jp.ac.hcs.white.examreport;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
		radioroute.put("学校斡旋", "1");
		radioroute.put("ネット", "2");
		radioroute.put("斡旋サイト", "3");
		radioroute.put("企業HP", "4");
		radioroute.put("新聞・雑誌", "5");
		radioroute.put("ジョブカフェ等", "6");
		radioroute.put("その他", "99");
		return radioroute;
	}

	private Map<String, String> initRadioTest() {
		Map<String, String> radiotest = new LinkedHashMap<>();
		radiotest.put("1次試験", "1");
		radiotest.put("2次試験", "2");
		radiotest.put("3次試験", "3");
		radiotest.put("4次試験", "4");
		radiotest.put("次試験", "5");
		radiotest.put("最終試験", "6");
		return radiotest;
	}

	/**
	 *
	 * @param principal ログイン情報
	 * @param model
	 * @return 受験報告一覧画面
	 */
	@PostMapping("/exam/examlist")
	public String getExamList(Principal principal, Model model) {
		System.out.println(principal.getName());
		log.info("[" + principal.getName() + "]受験報告検索" + principal.getName());
		ExamReportEntity examEntity;
		examEntity = examService.selectAll(principal.getName());
		model.addAttribute("examEntity",examEntity);

		return "exam/examlist";
	}

	@GetMapping("/exam/examInsert")
	public String getExamInsert(@ModelAttribute ExamForm form, Model model) {
		// ラジオボタンの準備
				radioroute = initRadioRoute();
				model.addAttribute("radioRoute", radioroute);

				radiotest = initRadioTest();
				model.addAttribute("radioTest", radiotest);

		return "exam/examInsert";
	}


	@PostMapping("/exam/examInsert")
	public String postExamInsert(@ModelAttribute @Validated ExamForm form,
			BindingResult bindingResult,
			Principal principal,
			Model model) {

		// 入力チェックに引っかかった場合、登録画面に戻る
		if (bindingResult.hasErrors()) {
			return getExamInsert(form, model);
		}
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        Date date = null;
		try {
			date = sdFormat.parse(form.getExam_date_time());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		ExamReportData data = new ExamReportData();
		data.setDepartment(form.getDepartment());
		data.setUser_id(principal.getName());
		data.setCompany_name_top(form.getCompany_name_top());
		data.setRecruitment_number(form.getRecruitment_number());
		data.setCompany_name(form.getCompany_name());
		data.setApplication_route(form.getApplication_route());
		data.setExam_date_time(date);
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
	@GetMapping("/exam/examDetail/{examreport_id:.+}")
	public String getExamDetail(@PathVariable("examreport_id") String examreport_id, Principal principal,Model model) {

		ExamReportData data = examService.selectOne(examreport_id);

		model.addAttribute("examdata", data);

		return "exam/examDetail";

	}
}
