package site.metacoding.red.domain.boards.mapper;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MainView { // DAO와 DB 통신에 필요한 Entity, View
	private Integer id;
	private String title;
	private String username;
}
