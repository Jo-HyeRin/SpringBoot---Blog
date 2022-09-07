package site.metacoding.red.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.boards.Boards;
import site.metacoding.red.domain.boards.BoardsDao;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.web.dto.request.boards.WriteDto;
import site.metacoding.red.web.dto.response.boards.MainDto;
import site.metacoding.red.web.dto.response.boards.PagingDto;

@RequiredArgsConstructor
@Controller
public class BoardsController {
	
	private final BoardsDao boardsDao;
	private final HttpSession session;
	//@PostMapping("/boards/{id}/delete")
	//@PostMapping("/boards/{id}/update")
	
	@PostMapping("/boards/{id}/delete")
	public String deleteBoards(@PathVariable Integer id) { // 트랜잭션때문에 영속화가 좋음
		Users principal = (Users) session.getAttribute("principal");
		Boards boardsPS = boardsDao.findById(id);
		
		// 비정상 요청 체크
		if(boardsPS == null) { // if는 비정상 로직으로 걸러내는 필터 역할을 하도록 하자.
			return "redirect:/boards/"+id;
		}
		
		// 인증 체크 
		if(principal == null) {
			return "redirect:/loginForm";			
		}
		
		// 권한 체크(principal.getId() 와 boardsPS의 usersId를 비교)
		if(principal.getId() != boardsPS.getUsersId()) {
			return "redirect:/boards/"+id;
		}		
		
		boardsDao.delete(id);
		return "redirect:/";
	}
	
	@PostMapping("/boards")
	public String writeBoards(WriteDto writeDto) {
		// 1번 세션에 접근해서 세션값을 확인한다. 그때 Users로 다운캐스팅하고 키값은 principal로 한다.
		Users principal = (Users) session.getAttribute("principal");
		
		// 2번 principal null인지 확인하고 null이면 loginForm redirection 해준다.
		if(principal == null) { // else를 쓰지 않아도 된다. if 안걸리면 알아서 아래 코드 실행될거니까. 반복되는 필터링 메서드는 따로 빼서 모아 이용. 
			return "redirect:/loginForm";
		}
		
		// 3번 BoardsDao에 접근해서 insert 메서드를 호출한다.
		// 조건 : dto를 entity로 변환해서 인수로 담아준다.
		// 조건 : entity에는 세션의 principal에 getId가 필요하다.		
		boardsDao.insert(writeDto.toEntity(principal.getId()));
		return "redirect:/";

	}
		
	@GetMapping({"/", "/boards"})
	public String getBoardList(Model model, Integer page) {
		if(page==null) page=0;
		int startNum = page*3;
		
		List<MainDto> boardsList = boardsDao.findAll(startNum);
		PagingDto paging = boardsDao.paging(page);
		
		paging.makeBlockInfo();
		
		model.addAttribute("boardsList", boardsList);
		model.addAttribute("paging",paging);
		return "boards/main";		
	}
		
	@GetMapping("/boards/{id}")
	public String getBoardDetail(@PathVariable Integer id, Model model) {
		model.addAttribute("boards", boardsDao.findById(id));
		return "boards/detail";
	}
	
	@GetMapping("/boards/writeForm")
	public String writeForm() {
		Users principal = (Users)session.getAttribute("principal"); // 세션의 값을 가지고 올 때는 다운캐스팅해서 가져온다. 
		if(principal == null) { // 로그인이 안 된 경우,  반복되는 필터링 메서드는 따로 빼서 모아 이용. 
			return "redirect:/loginForm";
		}		
		return "boards/writeForm";		
	}			
	
}
