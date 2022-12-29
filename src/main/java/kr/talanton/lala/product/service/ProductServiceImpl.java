package kr.talanton.lala.product.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.talanton.lala.category.entity.Category;
import kr.talanton.lala.common.dto.PageRequestDTO;
import kr.talanton.lala.common.dto.PageResultDTO;
import kr.talanton.lala.common.utils.Common;
import kr.talanton.lala.member.entity.Member;
import kr.talanton.lala.policy.dto.PolicyCode;
import kr.talanton.lala.policy.entity.Policy;
import kr.talanton.lala.policy.repository.PolicyRepository;
import kr.talanton.lala.policy.service.PolicyService;
import kr.talanton.lala.product.dto.ProductDTO;
import kr.talanton.lala.product.dto.UploadProductForm;
import kr.talanton.lala.product.entity.Product;
import kr.talanton.lala.product.entity.ProductHistory;
import kr.talanton.lala.product.entity.ProductImage;
import kr.talanton.lala.product.entity.ProductInfo;
import kr.talanton.lala.product.entity.ProductOption;
import kr.talanton.lala.product.repository.ProductHistoryRepository;
import kr.talanton.lala.product.repository.ProductImageRepository;
import kr.talanton.lala.product.repository.ProductInfoRepository;
import kr.talanton.lala.product.repository.ProductOptionRepository;
import kr.talanton.lala.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	@Value("${kr.talanton.upload.path}")
    private String uploadFolder;
	
	private static final int PRODUCT_REGISTRATION = 1;
	private static final int PRODUCT_UPDATE = 2;
	private static final int PC_LIST_TYPE = 2;
	private static final int MOBILE_EXPOSE_TYPE = 7;
	
	private final ProductRepository productRepository;
	private final ProductHistoryRepository historyRepository;
	private final ProductInfoRepository infoRepository;
	private final ProductOptionRepository optionRepository;
	private final ProductImageRepository imageRepository;
	private final PolicyRepository policyRepository;
	
	private File uploadPath;
	private String uploadFolderPath;

	@Override
	public void register(UploadProductForm form) {
		Product product = uploadProductFormToProduct(form);
		log.info("product..." + product);
		productRepository.save(product);
		form.setPid(product.getPid());
		
		// 상품 이력 관리 : 등록 추가
		Member register = Member.builder().userid(form.getUserid()).build();
		ProductHistory history = ProductHistory.builder()
				.item(PRODUCT_REGISTRATION)
				.register(register)
				.main(product)
				.build();
		historyRepository.save(history);

		List<ProductInfo> infoList = uploadProductFormToProductInfo(form);
		for(ProductInfo info : infoList) {
			infoRepository.save(info);
		}
		
		List<ProductOption> optionList = uploadProductFormToProductOption(form);
		for(ProductOption option : optionList) {
			optionRepository.save(option);
		}
		storeImageFiles(form);
	}
	
	private void storeImageFiles(UploadProductForm form) {
		uploadFolderPath = Common.getFolder();

		// 업로드 폴더 생성
		uploadPath = new File(uploadFolder, uploadFolderPath);
		log.info("upload path: " + uploadPath);
		if (uploadPath.exists() == false) { // 폴더가 존재하지 않을 때만 생성
			uploadPath.mkdirs(); // 중간 경로에 없는 폴더가 있을 경우, 그것까지도 생성해 준다.
		} // make yyyy/MM/dd folder
		
		// MultipartFile pclist 저장
		for(int i = 0;i < form.getImgList().size();i++) {
			MultipartFile imgFile = form.getImgList().get(i);
			if(imgFile.getSize() > 0) {
				ProductImage attach = storeImageFileAndTable(imgFile);
				Product product = Product.builder().pid(form.getPid()).build();
				attach.setProduct(product);
				attach.setFileType(getFileType(i));
				imageRepository.save(attach);
			}
		}
	}
	
	private ProductImage storeImageFileAndTable(MultipartFile multipartFile) {
		ProductImage attach = ProductImage.builder().build();
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

		try {
			File saveFile = new File(uploadPath, uploadFileName);
			multipartFile.transferTo(saveFile);
			attach.setUuid(uuid.toString());
			attach.setPath(uploadFolderPath);
			// 이미지 파일 유형인지 검사
			if (Common.checkImageType(saveFile)) {
				FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
				Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
				thumbnail.close();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return attach;
	}
	
	private String getFileType(int i) {
		if(i == 0) {
			return Common.PC_LIST_TYPE;
		} else if(i >= 1 && i <= 4) {
			return Common.PC_MAIN_TYPE;
		} else if(i == 5) {
			return Common.PC_EXPOSE_TYPE;
		} else if(i == 6) {
			return Common.MOBILE_LIST_TYPE;
		} else if(i >= 7 && i <= 10) {
			return Common.MOBILE_MAIN_TYPE;
		} else {
			return Common.MOBILE_EXPOSE_TYPE;
		}
	}

	@Transactional
	@Override
	public PageResultDTO<ProductDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
		log.info(pageRequestDTO);
		int total = (int)productRepository.count();
		Pageable pageable = pageRequestDTO.getPageable(Sort.by("pid").descending());
		Function<Object[], ProductDTO> fn = (en -> entityToDto((Product)en[0], (Category)en[1], (Category)en[2], (Member)en[3]));
        Page<Object[]> result = productRepository.getProductWithRegister(pageable);
        
//        Page<Object[]> result = repository.searchPage(
//                pageRequestDTO.getType(),
//                pageRequestDTO.getKeyword(),
//                pageRequestDTO.getPageable(Sort.by("bno").descending())
//        );
        PageResultDTO<ProductDTO, Object[]> resp = new PageResultDTO<>(result, fn);
        for(ProductDTO dto : resp.getDtoList()) {
			if(dto.getDeposit() == -1) {	// 기본 적립금
				log.info("PolicyCode.PURCHASE_POINT.ordinal()=" + PolicyCode.PURCHASE_POINT.ordinal());
				Optional<Policy> policyRes = policyRepository.findById(PolicyCode.PURCHASE_POINT.ordinal());	// 적립금
				if(policyRes.isPresent()) {
					Policy depositPolicy = policyRes.get();
					String value = depositPolicy.getValue();
					dto.setDeposit(dto.getSalePrice() * Integer.parseInt(value) / 100);
				} else {	// error 처리
					
				}
			} else {
				dto.setDeposit(dto.getSalePrice() * dto.getDeposit() / 100);
			}
        }
        resp.setTotal(total);
        return resp;
	}
}