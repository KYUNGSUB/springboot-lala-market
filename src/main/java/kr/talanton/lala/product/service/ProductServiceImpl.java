package kr.talanton.lala.product.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.talanton.lala.attach.dto.AttachFileDTO;
import kr.talanton.lala.attach.repository.AttachFileRepository;
import kr.talanton.lala.category.entity.Category;
import kr.talanton.lala.common.dto.PageResultDTO;
import kr.talanton.lala.common.utils.Common;
import kr.talanton.lala.common.utils.UploadFileUtil;
import kr.talanton.lala.member.entity.Member;
import kr.talanton.lala.policy.dto.PolicyCode;
import kr.talanton.lala.policy.entity.Policy;
import kr.talanton.lala.policy.repository.PolicyRepository;
import kr.talanton.lala.product.dto.ProductDTO;
import kr.talanton.lala.product.dto.ProductPageRequestDTO;
import kr.talanton.lala.product.dto.UploadProductForm;
import kr.talanton.lala.product.entity.Product;
import kr.talanton.lala.product.entity.ProductHistory;
import kr.talanton.lala.product.entity.ProductInfo;
import kr.talanton.lala.product.entity.ProductMapping;
import kr.talanton.lala.product.entity.ProductOption;
import kr.talanton.lala.product.repository.ProductHistoryRepository;
import kr.talanton.lala.product.repository.ProductInfoRepository;
import kr.talanton.lala.product.repository.ProductMappingRepository;
import kr.talanton.lala.product.repository.ProductOptionRepository;
import kr.talanton.lala.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	@Value("${kr.talanton.upload.path}")
    private String uploadFolder;
	
	private static final int PRODUCT_REGISTRATION = 1;
	private static final int PRODUCT_UPDATE = 2;
	
	private final ProductRepository productRepository;
	private final ProductHistoryRepository historyRepository;
	private final ProductInfoRepository infoRepository;
	private final ProductOptionRepository optionRepository;
	private final ProductMappingRepository mappingRepository;
	private final PolicyRepository policyRepository;
	private final AttachFileRepository attachRepository;

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
		Product main = Product.builder().pid(form.getPid()).build();
		// MultipartFile pclist 저장
		UploadFileUtil util = UploadFileUtil.getInstance(attachRepository, uploadFolder);
		for(int i = 0;i < form.getImgList().size();i++) {
			MultipartFile imgFile = form.getImgList().get(i);
			if(imgFile.getSize() > 0) {
				AttachFileDTO attachDTO = util.storeImageFiles(imgFile);
				ProductMapping pm = ProductMapping.builder()
						.attach(attachDTO.dtoToEntity())
						.product(main)
						.kind(getFileType(i))
						.build();
				mappingRepository.save(pm);
			}
		}
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
	public PageResultDTO<ProductDTO, Object[]> getList(ProductPageRequestDTO pageRequestDTO) {
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