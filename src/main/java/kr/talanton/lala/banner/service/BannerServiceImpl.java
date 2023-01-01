package kr.talanton.lala.banner.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import kr.talanton.lala.common.utils.UploadFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class BannerServiceImpl implements BannerService {
	@Value("${kr.talanton.upload.path}")
    private String uploadFolder;
	
	private final BannerRepository bannerRepository;
	private final BannerMappingRepository mappingRepository;
	private final AttachFileRepository attachRepository;

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
		UploadFileUtil util = UploadFileUtil.getInstance(attachRepository, uploadFolder);
		AttachFileDTO attach = util.storeImageFiles(form.getBannerImg());
		
		// bannerInfo 정보 처리
		BannerMappingDTO bannerMappingDTO = form.toBannerMappingDTO();
		bannerMappingDTO.setAttach(attach);
		BannerMapping mapping = bannerMappingDtoToEntity(bannerMappingDTO);
		mappingRepository.save(mapping);
		return reqDTO;
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