package store.controller;

import store.dto.BuyDto;
import store.dto.BuyInputDto;
import store.dto.BuyStateDto;
import store.exception.ConvenienceException;
import store.model.Convenience;
import store.model.Receipt;
import store.util.TimeUtil;
import store.view.FileReaderHelper;
import store.view.InputView;
import store.view.OutputView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConvenienceController {
    private final Convenience convenience;
    private final Receipt receipt;
    private final InputView inputView;
    private final OutputView outputView;
    private final FileReaderHelper fileReaderHelper;

    public ConvenienceController(Convenience convenience, Receipt receipt, InputView inputView,
                                 OutputView outputView, FileReaderHelper fileReaderHelper) {
        this.convenience = convenience;
        this.receipt = receipt;
        this.inputView = inputView;
        this.outputView = outputView;
        this.fileReaderHelper = fileReaderHelper;
    }

    /**
     * 편의점 동작
     */
    public void run() {
        boolean isContinue = true;

        init();

        while (isContinue) {
            LocalDate now = TimeUtil.getNowDate();
            welcome(now);
            buy(now);
            isMembership();
            totalPrint();
            isContinue = askContinue();
        }
    }

    /**
     * 편의점 초기화 - 재고와 프로모션 파일에서 불러옵니다.
     */
    private void init() {
        convenience.init(fileReaderHelper.readPromotionFile(), fileReaderHelper.readProductFile());
    }

    /**
     * 환영 인사와 편의점 상품을 보여줍니다.
     *
     * @param now 현재 시각
     */
    private void welcome(LocalDate now) {
        outputView.welcome(convenience.getProductDetails(now));
    }

    /**
     * 구매하기
     * <pre>
     *     구매할 제품들을 받고 특이사항이 있다면 질문을 통해 해결합니다.
     * </pre>
     *
     * @param now 현재 시각
     */
    private void buy(LocalDate now) {
        boolean isGood = false;
        while (!isGood) {
            try {
                List<BuyInputDto> buyInputs = inputView.getBuyInput();
                checkState(buyInputs, now);
                List<BuyDto> buyDtos = buyAll(buyInputs, now);
                buyDtos.forEach(receipt::addProduct);
                isGood = true;
            } catch (ConvenienceException e) {
                outputView.printErrorMessage(e);
            }
        }
    }

    /**
     * 구매 상태 확인 후 질문
     *
     * @param buyInputs 구매 데이터
     * @param now       현재 시각
     */
    private void checkState(List<BuyInputDto> buyInputs, LocalDate now) {
        for (BuyInputDto buyInput : buyInputs) {
            ask(buyInput, convenience.getBuyState(buyInput, now));
        }
    }

    /**
     * 구매 상태에 따른 프로모션 상품 추가 혹은 프로모션 미적용 상품 질문 후 적용
     *
     * @param buyInput 구매 데이터
     * @param buyState 구매 상태
     */
    private void ask(BuyInputDto buyInput, BuyStateDto buyState) {
        boolean isGood = false;
        while (!isGood) {
            try {
                buyState.getBuyState().check(buyInput, buyState, inputView);
                isGood = true;
            } catch (ConvenienceException e) {
                outputView.printErrorMessage(e);
            }
        }
    }

    /**
     * 전부 구매
     *
     * @param buyInputs 구매 데이터 리스트
     * @param now       현재 시각
     * @return 구매 후 처리 데이터
     */
    private List<BuyDto> buyAll(List<BuyInputDto> buyInputs, LocalDate now) {
        List<BuyDto> buyList = new ArrayList<>();
        for (BuyInputDto buyInput : buyInputs) {
            buyList.add(convenience.buyProduct(buyInput, now));
        }
        return buyList;
    }

    /**
     * 멤버십 할인을 적용할 것인지 질문합니다.
     */
    private void isMembership() {
        while (true) {
            try {
                receipt.applyMembership(inputView.isMembership());
                return;
            } catch (ConvenienceException e) {
                outputView.printErrorMessage(e);
            }
        }
    }

    /**
     * 구매한 제품에 대해 영수증을 출력합니다.
     */
    private void totalPrint() {
        outputView.totalPrint(receipt.getProducts(), receipt.getPromotions(), receipt.getTotal());
    }

    /**
     * 추가로 구매할 물품이 있는지 질문합니다.
     *
     * @return 추가로 구매한 물품이 있는 경우 true
     */
    private boolean askContinue() {
        while (true) {
            try {
                return inputView.continueShopping();
            } catch (ConvenienceException e) {
                outputView.printErrorMessage(e);
            }
        }
    }
}
