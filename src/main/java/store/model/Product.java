package store.model;

import store.dto.BuyDto;
import store.dto.BuyStateDto;
import store.dto.ProductDetailDto;
import store.exception.ConvenienceException;

import java.time.LocalDate;

public interface Product {
    /**
     * 프로모션 재고 추가
     *
     * @param pQuantity 추가 재고 개수
     * @param promotion 프로모션
     */
    void addProduct(int pQuantity, Promotion promotion);

    /**
     * 기본 재고 추가
     *
     * @param quantity 추가 재고 개수
     */
    void addProduct(int quantity);

    /**
     * 재고 정보 반환
     * 프로모션 기간이 아닌 경우 프로모션 재고와 프로모션 재공하지 않음
     *
     * @param now 현재 시각
     * @return 재고 정보 DTO
     */
    ProductDetailDto getProductDetailDto(LocalDate now);

    /**
     * 구매 상태(구매 가능, 프로모션 추가 가능, 일부 프로모션 불가, 재고 부족) 반환
     *
     * @param buyCount 구매 개수
     * @param now      현재 시각
     * @return 구매 상태 반환
     * @throws ConvenienceException 재고 부족 예외
     */
    public BuyStateDto getBuyState(int buyCount, LocalDate now) throws ConvenienceException;

    /**
     * N개를 구매
     *
     * @param buyCount 구매 수량
     * @param now      현재 시각
     * @return 구매 데이터
     * @throws ConvenienceException 구매 수량 초과 에러
     */
    public BuyDto buy(int buyCount, LocalDate now) throws ConvenienceException;
}
