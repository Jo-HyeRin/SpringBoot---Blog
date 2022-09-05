package site.metacoding.red.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.domain.users.UsersDao;

@RequiredArgsConstructor
@Controller
public class BoardsController {
	
	private final HttpSession session;
	//@PostMapping("/boards/{id}/delete")
	//@PostMapping("/boards/{id}/update")

	@GetMapping({"/", "/boards"})
	public String getBoardList() {
		return "boards/main";
	}
	
	@GetMapping("/boards/{id}")
	public String getBoardList(@PathVariable Integer id) {
		return "boards/detail";
	}
	
	@GetMapping("/boards/writeForm")
	public String writeForm() {
		Users principal = (Users)session.getAttribute("principal"); // 세션의 값을 가지고 올 때는 다운캐스팅해서 가져온다. 
		if(principal == null) { // 로그인이 안 된 경우
			return "redirect:/loginForm";
		} else {
			return "boards/writeForm";
		}
	}
	
}
