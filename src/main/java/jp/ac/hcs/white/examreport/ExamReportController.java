package jp.ac.hcs.white.examreport;

import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.ac.hcs.white.user.UserData;
import lombok.extern.slf4j.Slf4j;

/**
 * 受験報告一覧情報
 */
@Slf4j
@Controller
public class ExamReportController {

	@Autowired
	ExamReportService examService;

	/**
	 *
	 * @param principal ログイン情報
	 * @param model
	 * @return 受験報告一覧画面
	 */
	@PostMapping("/exam/examlist")
	public String getExamList(Principal principal, Model model) {
		UserData userdata = new UserData();
		//TODO	selectone使う
		System.out.println(userdata.getRole());
		log.info("[" + principal.getName() + "]受験報告検索" + principal.getName());

		ExamReportEntity examEntity = examService.selectAll(principal.getName());
		model.addAttribute("examEntity",examEntity);

		return "exam/examlist";
	}

	@PostMapping("/exam/insert")
	public String postExamInsert(@RequestParam("examreport_id") String examreport_id, @RequestParam("department") String department,
			@RequestParam("company_name_top") String company_name_top, @RequestParam("report_day") Date report_day,
			@RequestParam("recruitment_number") int recruitment_number, @RequestParam("company_name") String company_name,
			@RequestParam("application_route") String application_route,@RequestParam("exam_date_time") String exam_date_time,
			@RequestParam("examination_location") String examination_location, @RequestParam("contens_test") String contens_test,@RequestParam("remarks") String remarks,
			@RequestParam("exam_report_status") String exam_report_status, Principal principal,Model model) {

			ExamReportData data = new ExamReportData();
			data.setExamreport_id(examreport_id);
			data.setDepartment(department);
			data.setCompany_name_top(company_name_top);
			data.setReport_day(report_day);
			data.setRecruitment_number(recruitment_number);
			data.setCompany_name(company_name);
			data.setApplication_route(application_route);
			data.setExamination_location(examination_location);
			data.setContens_test(contens_test);
			data.setRemarks(remarks);
			data.setExam_report_status(exam_report_status);

			boolean result = examService.insertOne(data);

			if (result) {
				log.info("[" + principal.getName() + "]受験報告登録成功");
			} else {
				log.warn("[" + principal.getName() + "]受験報告登録失敗");
			}
			return getExamList(principal, model);

	}
	@GetMapping("/exam/examDetail/{examreport_id+.*}")
	public String getExamDetail(@PathVariable("examreport_id") String examreport_id, Principal principal,Model model) {

		ExamReportData data = examService.selectOne(examreport_id);

		model.addAttribute("examdata", data);

		return "exam/examDetail";

	}
}
