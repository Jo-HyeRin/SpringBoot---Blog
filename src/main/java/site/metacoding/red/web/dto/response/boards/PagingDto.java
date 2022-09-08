package site.metacoding.red.web.dto.response.boards;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PagingDto {
	private String keyword;
	private Integer blockCount; // 상수 한 페이지에 페이지 넘 게시물 개수 (5)
	private Integer currentBlock; // 변수 1~5=0, 6~10=1
	private Integer startPageNum; // 변수 1 -> 6 -> 11 
	private Integer lastPageNum; // 변수 5 -> 10 -> 15
	
	private Integer totalCount;
	private Integer totalPage;
	private Integer currentPage;
	private boolean isLast; // boolean일 때 is로 시작하는 메서드는 getter가 만들어지면 isLast() 이름 으로 만들어짐. -> EL표현식 에서 last로 찾음. 
	private boolean isFirst;
	
	public void makeBlockInfo(String keyword) {
		this.keyword = keyword;
		this.blockCount = 5;
		this.currentBlock = currentPage / blockCount;
		this.startPageNum = 1 + blockCount * currentBlock;
		this.lastPageNum = 5 + blockCount * currentBlock;

		if (totalPage < lastPageNum) {
			this.lastPageNum = totalPage;
		}
	}
	
}
