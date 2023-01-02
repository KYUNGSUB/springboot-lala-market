package kr.talanton.lala.product.service;

import java.util.ArrayList;
import java.util.List;

import kr.talanton.lala.attach.dto.AttachFileDTO;
import kr.talanton.lala.attach.entity.AttachFile;
import kr.talanton.lala.category.entity.Category;
import kr.talanton.lala.common.dto.PageResultDTO;
import kr.talanton.lala.member.entity.Member;
import kr.talanton.lala.product.dto.ProductDTO;
import kr.talanton.lala.product.dto.ProductMappingDTO;
import kr.talanton.lala.product.dto.ProductPageRequestDTO;
import kr.talanton.lala.product.dto.UploadProductForm;
import kr.talanton.lala.product.entity.Product;
import kr.talanton.lala.product.entity.ProductInfo;
import kr.talanton.lala.product.entity.ProductMapping;
import kr.talanton.lala.product.entity.ProductOption;

public interface ProductService {
	public void register(UploadProductForm form);
	public PageResultDTO<ProductDTO, Object[]> getList(ProductPageRequestDTO pageReuestDTO);
	
	default Product uploadProductFormToProduct(UploadProductForm form) {
		Member register = Member.builder().userid(form.getUserid()).build(); 
		Category category1 = Category.builder().code(form.getC1_code()).build();
		Category category2 = Category.builder().code(form.getC2_code()).build();
		Product product = Product.builder()
				.pid(form.getPid())
				.category1(category1)
				.category2(category2)
				.name(form.getName())
				.price(form.getPrice())
				.saleprice(form.getSalePrice())
				.maxpurchase(form.getMaxPurchase())
				.register(register)
				.introduction(form.getIntroduction())
				.pc_detail(form.getPc_detail())
				.mobile_detail(form.getMobile_detail())
				.memo(form.getMemo())
				.expose(form.getExpose())
				.build();
		if(form.getPoint().equals("basic")) {
			product.setDeposit(-1);			// 기본 포인트 적용
		} else if(form.getPoint().equals("apart")) {
			product.setDeposit(form.getDeposit());	// 개별 포인트 적용
		} else {
			product.setDeposit(0);			// 포인트 없음
		}
		if(form.getFee().equals("basic")) {
			product.setDelivery(-1);		// 기본 배송비
		} else if(form.getFee().equals("apart")) {
			product.setDelivery(form.getDelivery());	// 개별 배송비
		} else {
			product.setDelivery(0);	// 무료 배송비
		}
		for(String f : form.getFeature()) {
			if(f.equals("newp")) {
				product.setNewp(true);
			} else if(f.equals("best")) {
				product.setBest(true);
			} else {
				product.setDiscount(true);
			}
		}
		if(form.getInfoBtn().equals("no")) {
			product.setInfo(false);
		} else {
			product.setInfo(true);
		}
		if(form.getOptionBtn().equals("no")) {
			product.setOpt(false);
		} else {
			product.setOpt(true);
		}
		if(form.getDguide().equals("indivisual")) {	// 개별 배송 안내
			product.setPc_delivery(form.getPc_delivery());
			product.setMobile_delivery(form.getMobile_delivery());
		}
		if(form.getExchange().equals("indivisual")) {
			product.setPc_exchange(form.getPc_exchange());
			product.setMobile_exchange(form.getMobile_exchange());
		}
		return product;
	}
	
	default List<ProductInfo> uploadProductFormToProductInfo(UploadProductForm form)  {
		List<ProductInfo> list = new ArrayList<>();
		Product product = Product.builder().pid(form.getPid()).build();
		for(int i = 0;i < form.getIname().length;i++) {
			ProductInfo info = ProductInfo.builder()
					.name(form.getIname()[i])
					.description(form.getIdescription()[i])
					.main(product)
					.build();
			list.add(info);
		}
		return list;
	}
	
	default List<ProductOption> uploadProductFormToProductOption(UploadProductForm form)  {
		List<ProductOption> list = new ArrayList<>();
		String name = null;
		int gid = -1;
		Product product = Product.builder().pid(form.getPid()).build();
		for(int i = 0;i < form.getOname().length;i++) {
			if(!form.getOname()[i].equals(name)) {
				gid++;
				name = form.getOname()[i];
			}
			ProductOption option = ProductOption.builder()
					.gid(gid)
					.name(name)
					.description(form.getOname()[i])
					.price(Integer.parseInt(form.getOprice()[i]))
					.main(product)
					.build();
			list.add(option);
		}
		return list;
	}

	default ProductDTO entityToDto(Product product, ProductMapping mapping, AttachFile attach, Category category1, Category category2, Member member) {
		AttachFileDTO attachDTO = attachFileToAttachFileDTO(attach);
		ProductMappingDTO pc_list = productMappingToProductMappingDTO(mapping, attachDTO);
		ProductDTO dto = ProductDTO.builder()
				.pid(product.getPid())
				.c1_code(category1.getCode())
				.c1_name(category1.getName())
				.c2_code(category2.getCode())
				.c2_name(category2.getName())
				.name(product.getName())
				.price(product.getPrice())
				.salePrice(product.getSaleprice())
				.maxPurchase(product.getMaxpurchase())
				.deposit(product.getDeposit())
				.delivery(product.getDelivery())
				.newp(product.isNewp())
				.best(product.isBest())
				.discount(product.isDiscount())
				.info(product.isInfo())
				.opt(product.isOpt())
				.readCount(product.getReadcount())
				.register_userid(member.getUserid())
				.register_name(member.getName())
				.pc_list(pc_list)
				.introduction(product.getIntroduction())
				.pc_detail(product.getPc_detail())
				.mobile_detail(product.getMobile_detail())
				.pc_delivery(product.getPc_delivery())
				.mobile_delivery(product.getMobile_delivery())
				.pc_exchange(product.getPc_exchange())
				.mobile_exchange(product.getMobile_exchange())
				.memo(product.getMemo())
				.expose(product.getExpose())
				.regDate(product.getRegDate())
				.modDate(product.getModDate())
				.build();
		return dto;
	}
	
	default ProductMappingDTO productMappingToProductMappingDTO(ProductMapping mapping, AttachFileDTO attachDTO) {
		ProductMappingDTO dto = ProductMappingDTO.builder()
				.mid(mapping.getMid())
				.afDTO(attachDTO)
				.mainPid(mapping.getProduct().getPid())
				.kind(mapping.getKind())
				.build();
		return dto;
	}
	
	default AttachFileDTO attachFileToAttachFileDTO(AttachFile attach) {
		AttachFileDTO dto = AttachFileDTO.builder()
				.inum(attach.getInum())
				.uuid(attach.getUuid())
				.imgName(attach.getImgName())
				.path(attach.getPath())
				.fileType(attach.getFileType())
				.regDate(attach.getRegDate())
				.modDate(attach.getModDate())
				.build();
		return dto;
	}
}