package kr.talanton.lala.banner.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.talanton.lala.attach.dto.AttachFileDTO;
import kr.talanton.lala.attach.entity.AttachFile;
import kr.talanton.lala.attach.repository.AttachFileRepository;
import kr.talanton.lala.banner.dto.BannerDTO;
import kr.talanton.lala.banner.dto.BannerMappingDTO;
import kr.talanton.lala.banner.dto.BannerUploadForm;
import kr.talanton.lala.banner.entity.Banner;
import kr.talanton.lala.banner.entity.BannerMapping;
import kr.talanton.lala.banner.repository.BannerMappingRepository;
import kr.talanton.lala.banner.repository.BannerRepository;
import kr.talanton.lala.common.utils.Common;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;

@Service
@RequiredArgsConstructor
@Log4j2
public class BannerServiceImpl implements BannerService {
	@Value("${kr.talanton.upload.path}")
    private String uploadFolder;
	
	private final BannerRepository bannerRepository;
	private final BannerMappingRepository mappingRepository;
	private final AttachFileRepository attachRepository;
	
	private File uploadPath;
	private String uploadFolderPath;

	@Override
	public BannerDTO getBanner(int kind, int position) {
		BannerDTO bannerDTO = null;
		List<Object[]> result = bannerRepository.getBanner(kind, position);
		log.info("getBanner: " + result);
		if(!result.isEmpty()) {
			Object[] data = result.get(0);
			bannerDTO = entityToDto((Banner)data[0]);
			for(Object[] obj : result) {
				BannerMappingDTO bannerMappingDto = entityToDto((Banner)obj[0], (BannerMapping)obj[1], (AttachFile)obj[2]);
				bannerDTO.addBannerMappingToList(bannerMappingDto);
			}
		}
		return bannerDTO;
	}

	@Transactional
	@Override
	public BannerDTO addBanner(BannerUploadForm form) {
		// banner 정보 처리
		BannerDTO reqDTO = form.toBannerDTO();
		Banner banner = dtoToEntity(reqDTO);
		List<Object[]> result = bannerRepository.getBanner(reqDTO.getKind(), reqDTO.getPosition());
		if(result.isEmpty()) {	// 존재하지 않음(추가 필요)
			bannerRepository.save(banner);
			form.setBid(banner.getBid());
		}
		else {	// 기존에 존재하는 배너가 있을 때
			Object[] data = result.get(0);
			BannerDTO dbDTO = entityToDto((Banner)data[0]);
			if (reqDTO.getLocation() != dbDTO.getLocation()) {				// 기존에 존재함
				banner.setLocation(reqDTO.getLocation());
				bannerRepository.save(banner);
			}
			form.setBid(dbDTO.getBid());
		}

		// 파일 저장 및 데이터베이스 테이블 저장
		AttachFileDTO attach = storeImageFiles(form);
		
		// bannerInfo 정보 처리
		BannerMappingDTO bannerMappingDTO = form.toBannerMappingDTO();
		bannerMappingDTO.setAttach(attach);
		BannerMapping mapping = bannerMappingDtoToEntity(bannerMappingDTO);
		mappingRepository.save(mapping);
		return reqDTO;
	}
	
	private AttachFileDTO storeImageFiles(BannerUploadForm form) {
		AttachFileDTO attachDTO = null;
		uploadFolderPath = Common.getFolder();

		// 업로드 폴더 생성
		uploadPath = new File(uploadFolder, uploadFolderPath);
		log.info("upload path: " + uploadPath);
		if (uploadPath.exists() == false) { // 폴더가 존재하지 않을 때만 생성
			uploadPath.mkdirs(); // 중간 경로에 없는 폴더가 있을 경우, 그것까지도 생성해 준다.
		} // make yyyy/MM/dd folder
		
		// MultipartFile pclist 저장
		MultipartFile imgFile = form.getBannerImg();
		if(imgFile.getSize() > 0) {
			attachDTO = storeImageFileAndTable(imgFile);
			AttachFile af = attachFileDtoToEntity(attachDTO);
			attachRepository.save(af);
			attachDTO.setInum(af.getInum());
		}
		return attachDTO;
	}
	
	private AttachFile attachFileDtoToEntity(AttachFileDTO attachDTO) {
		AttachFile entity = AttachFile.builder()
				.uuid(attachDTO.getUuid())
				.imgName(attachDTO.getImgName())
				.path(attachDTO.getPath())
				.build();
		return entity;
	}

	private AttachFileDTO storeImageFileAndTable(MultipartFile multipartFile) {
		AttachFileDTO attach = new AttachFileDTO();
		log.info("------------------------------");
		log.info("upload file name: " + multipartFile.getOriginalFilename());
		log.info("upload File Size: " + multipartFile.getSize());
		
		String uploadFileName = multipartFile.getOriginalFilename();
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
				Thumbnailator.createThumbnail(saveFile, new File(uploadPath, "s_" + uploadFileName), 100, 100);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return attach;
	}

	@Override
	public BannerDTO updateBanner(BannerUploadForm form) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeBannerInfo(int info_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteBanner(Long bid) {
		// TODO Auto-generated method stub
		
	}
}