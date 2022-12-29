package kr.talanton.lala.banner.service;

import kr.talanton.lala.attach.dto.AttachFileDTO;
import kr.talanton.lala.attach.entity.AttachFile;
import kr.talanton.lala.banner.dto.BannerDTO;
import kr.talanton.lala.banner.dto.BannerMappingDTO;
import kr.talanton.lala.banner.dto.BannerUploadForm;
import kr.talanton.lala.banner.entity.Banner;
import kr.talanton.lala.banner.entity.BannerMapping;

public interface BannerService {
	BannerDTO getBanner(int kind, int position);
	BannerDTO addBanner(BannerUploadForm form);
	BannerDTO updateBanner(BannerUploadForm form);
	void removeBannerInfo(int info_id);
	void deleteBanner(Long bid);
	
	default BannerDTO entityToDto(Banner entity) {
		BannerDTO dto = BannerDTO.builder()
				.bid(entity.getBid())
				.kind(entity.getKind())
				.position(entity.getPosition())
				.location(entity.getLocation())
				.regDate(entity.getRegDate())
				.modDate(entity.getModDate())
				.build();
		return dto;
	}
	
	default BannerMappingDTO entityToDto(Banner banner, BannerMapping bm, AttachFile af) {
		AttachFileDTO aDto = AttachFileDTO.builder()
				.inum(af.getInum())
				.uuid(af.getUuid())
				.imgName(af.getImgName())
				.path(af.getPath())
				.regDate(af.getRegDate())
				.modDate(af.getModDate())
				.build();
		BannerMappingDTO bmDto = BannerMappingDTO.builder()
				.mid(bm.getMid())
				.attach(aDto)
				.bid(banner.getBid())
				.url(bm.getUrl())
				.alt(bm.getAlt())
				.target(bm.getTarget())
				.loginBefore(bm.getLoginBefore())
				.build();
		return bmDto;
	}
	
	default Banner dtoToEntity(BannerDTO dto) {
		Banner entity = Banner.builder()
				.bid(dto.getBid())
				.kind(dto.getKind())
				.position(dto.getPosition())
				.location(dto.getLocation())
				.build();
		return entity;
	}
	
	default BannerMapping bannerMappingDtoToEntity(BannerMappingDTO dto) {
		Banner banner = Banner.builder().bid(dto.getBid()).build();
		AttachFile af = AttachFile.builder().inum(dto.getAttach().getInum()).build();
		BannerMapping entity = BannerMapping.builder()
				.mid(dto.getMid())
				.attach(af)
				.banner(banner)
				.url(dto.getUrl())
				.alt(dto.getAlt())
				.target(dto.getTarget())
				.loginBefore(dto.getLoginBefore())
				.build();
		return entity;
	}
}