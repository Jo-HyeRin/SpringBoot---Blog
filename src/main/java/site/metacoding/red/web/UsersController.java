package site.metacoding.red.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.domain.users.UsersDao;
import site.metacoding.red.web.dto.request.users.JoinDto;
import site.metacoding.red.web.dto.request.users.LoginDto;

@RequiredArgsConstructor
@Controller
public class UsersController {
	
	private final HttpSession session; // 스프링이 서버시작시에 ioc컨테이너에 보관함
	private final UsersDao usersDao;
	
	@GetMapping("/logout")
	public String logout() {
		session.invalidate(); // session 값 날려버리기
		return "redirect:/";
	}
	
	
	@PostMapping("/login")
	public String login(LoginDto loginDto) {
		Users usersPS = usersDao.login(loginDto);		
		if(usersPS != null) { // 인증됨 
			session.setAttribute("principal", usersPS);  // session.setAttribute(name, Object value) : 세션에값저장. 모든 유형을 받기 위해 가장 최상위 타입인 오브젝트 타입으로 받는다.
			return "redirect:/";
		}else { // 인증안됨.
			return "redirect:/loginForm";
		}
		
	}
	
	@PostMapping("/join")
	public String join(JoinDto joinDto) {
		usersDao.insert(joinDto);
		return "redirect:/loginForm";
	}

	@GetMapping("/loginForm")
	public String loginForm() {
		return "users/loginForm";
	}
	
	@GetMapping("/joinForm")
	public String joinForm() {
		return "users/joinForm";
	}	

}
