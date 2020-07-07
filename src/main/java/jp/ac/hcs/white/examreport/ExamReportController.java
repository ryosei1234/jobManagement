package jp.ac.hcs.white.examreport;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;

/**
 * 受験報告一覧情報
 */
@Slf4j
@Controller
public class ExamReportController {

	@Autowired
	ExamReportService examreportService;

	/**
	 *
	 * @param principal ログイン情報
	 * @param model
	 * @return 受験報告一覧画面
	 */
	@PostMapping("/exam/examlist")
	public String getExamList(Principal principal, Model model) {

		log.info("[" + principal.getName() + "]受験報告検索" + principal.getName());

		ExamReportEntity examreportEntity = examreportService.selectAll(principal.getName());
		model.addAttribute("examreportEntity",examreportEntity);

		return "exam/examlist";
	}
}
