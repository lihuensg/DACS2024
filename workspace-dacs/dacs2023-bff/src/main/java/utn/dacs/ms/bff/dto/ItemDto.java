package utn.dacs.ms.bff.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDto {
	private Integer userId;
	private Integer id;
	private String title;
	private boolean completed;
}
