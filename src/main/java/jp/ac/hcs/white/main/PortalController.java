package jp.ac.hcs.white.main;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.ac.hcs.white.user.UserData;

@Controller
public class PortalController {

	@Autowired
	LoginService loginService;
	@RequestMapping("/")
	public String index(Principal principal) {

		UserData userData = loginService.getLoginUserInfo(principal);

		if (loginService.judgeUserStatus(userData)) {
			// OK
			return "index";
		} else {
			// NG
			return "login";
		}

	}
}
