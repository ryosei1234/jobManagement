package jobhuntingreport;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class JobHuntingController {





	/**
	 * 受験報告一覧を表示
	 * @param principal ログイン情報
	 * @param model
	 * @return 受験報告一覧画面
	 */
	@PostMapping("/job/joblist")
	public String getJobList(Principal principal, Model model) {
		log.info("[" + principal.getName() + "]就職活動検索" + principal.getName());
		JobHuntingEntity jobHuntingEntity;
		jobHuntingEntity = jobHuntingService.selectAll(principal.getName());
		model.addAttribute("jobHuntingEntity", jobHuntingEntity);

		return "job/joblist";
	}


}