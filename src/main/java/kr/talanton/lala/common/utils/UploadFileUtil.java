package kr.talanton.lala.common.utils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import kr.talanton.lala.attach.dto.AttachFileDTO;
import kr.talanton.lala.attach.entity.AttachFile;
import kr.talanton.lala.attach.repository.AttachFileRepository;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;

@Log4j2
public class UploadFileUtil {
	private static UploadFileUtil instance = null;
	private UploadFileUtil() { }
	public static UploadFileUtil getInstance(AttachFileRepository repository, String folder) {
		if(instance == null) {
			instance = new UploadFileUtil();
		}
		attachRepository = repository;
		uploadFolder = folder;
		return instance;
	}

	private static String uploadFolder;
	private static AttachFileRepository attachRepository;
	
	private File uploadPath;
	private String uploadFolderPath;
	
	public AttachFileDTO storeImageFiles(MultipartFile imgFile) {
		AttachFileDTO attachDTO = null;
		// MultipartFile pclist 저장
		if(imgFile.getSize() > 0) {
			attachDTO = storeImageFileAndTable(imgFile);
			AttachFile af = attachDTO.dtoToEntity();
			attachRepository.save(af);
			attachDTO.setInum(af.getInum());
		}
		return attachDTO;
	}
	
	private AttachFileDTO storeImageFileAndTable(MultipartFile multipartFile) {
		uploadFolderPath = Common.getFolder();

		// 업로드 폴더 생성
		uploadPath = new File(uploadFolder, uploadFolderPath);
		log.info("upload path: " + uploadPath);
		if (uploadPath.exists() == false) { // 폴더가 존재하지 않을 때만 생성
			uploadPath.mkdirs(); // 중간 경로에 없는 폴더가 있을 경우, 그것까지도 생성해 준다.
		} // make yyyy/MM/dd folder
		
		AttachFileDTO attach = new AttachFileDTO();
		String uploadFileName = multipartFile.getOriginalFilename();
		log.info("------------------------------");
		log.info("upload file name: " + uploadFileName);
		log.info("upload File Size: " + multipartFile.getSize());
		
		// IE has file path -> 경로 자르기 (전체 경로 중에 파일 이름만 잘라낸다.)
		uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
		log.info("only file name: " + uploadFileName);
		
		attach.setImgName(uploadFileName);
		// 파일이름 중복 방지
		// 원래 파일 이름도 보존할 수 있다.
		UUID uuid = UUID.randomUUID();
		uploadFileName = uuid.toString() + "_" + uploadFileName;
		String saveName = uploadPath + File.separator + uploadFileName;
		Path savePath = Paths.get(saveName);
		try {
			File saveFile = savePath.toFile();
			multipartFile.transferTo(saveFile);
			attach.setUuid(uuid.toString());
			attach.setPath(uploadFolderPath);
			// 이미지 파일 유형인지 검사
			if (Common.checkImageType(saveFile)) {
				attach.setFileType("1");	// 이미지
				Thumbnailator.createThumbnail(saveFile, new File(uploadPath, "s_" + uploadFileName), 100, 100);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return attach;
	}
}