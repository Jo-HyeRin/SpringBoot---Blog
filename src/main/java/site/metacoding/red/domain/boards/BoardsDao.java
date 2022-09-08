package site.metacoding.red.domain.boards;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import site.metacoding.red.web.dto.response.boards.MainDto;
import site.metacoding.red.web.dto.response.boards.PagingDto;

public interface BoardsDao {
	public List<MainDto> findSearch(@Param("startNum") int startNum, @Param("keyword") String keyword);
	public PagingDto paging(@Param("page") Integer page, @Param("keyword") String keyword);
	public void insert(Boards boards); // DTO 생각해보기
	public Boards findById(Integer id);
	public List<MainDto> findAll(int startNum);
	public void update(Boards boards); // dto에 id가 없으므로 dto를 넣을 수 없네. 영속화변경수정해보자
	public void delete(Integer id);
}








