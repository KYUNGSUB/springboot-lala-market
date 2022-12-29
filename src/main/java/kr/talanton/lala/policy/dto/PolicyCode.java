package kr.talanton.lala.policy.dto;

public enum PolicyCode {
	NONE,					// Dummy
	DELEVERY_FEE,			// 기본 배송료
	FREE_SHIPPING,			// 무료 배송
	SUBSCRIPTION_POINT,		// 가입 포인트
	PURCHASE_POINT,			// 구매 포인트
	WITHOUT_BANKBOOK,		// 무통장 입금 경과일
	PC_COMMON_DELIVERY,		// 공통 배송 안내 PC
	MOBILE_COMMON_DELIVERY,	// 공통 배송 안내 Mobile
	PC_EXCHANGE_RETURNS,	// 공통 교환 반품 안내 PC
	MOBILE_EXCHANGE_RETURNS,// 공통 교환 반품 안내 Mobile
	NO_DELIVERY_AREA		// 배송 불가 지역
}