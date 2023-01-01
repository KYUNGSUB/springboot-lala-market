package kr.talanton.lala.attach.dto;

import java.time.LocalDateTime;

import kr.talanton.lala.attach.entity.AttachFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttachFileDTO {
	private Long inum;
	private String uuid;
	private String imgName;
	private String path;
	private String fileType;	// 이미지 여부 ('1': 이미지, '0': 일반)
	private LocalDateTime regDate;
	private LocalDateTime modDate;
	
	public AttachFile dtoToEntity() {
		AttachFile entity = AttachFile.builder()
				.inum(inum)
				.uuid(uuid)
				.imgName(imgName)
				.path(path)
				.fileType(fileType)
				.build();
		return entity;
	}
}