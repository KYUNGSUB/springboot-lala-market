package kr.talanton.lala.main.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MainPmainDTO {
	private String kind;
	private String title;
	private int display;
	@Builder.Default
	private List<MainPlistDTO> list = new ArrayList<>();
	private LocalDateTime regDate;
	private LocalDateTime modDate;
}