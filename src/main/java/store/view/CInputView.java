package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.dto.BuyInputDto;
import store.exception.ConvenienceException;
import store.exception.ErrorMessage;
import store.util.StringUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static store.util.Validator.validateBuyInput;

public class CInputView implements InputView {

    private static CInputView inputView;
    private static final String BUY_MESSAGE = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private static final String ADD_MESSAGE_FORMAT = "현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)";
    private static final String REMOVE_MESSAGE_FORMAT = "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)";
    private static final String MEMBERSHIP_MESSAGE = "멤버십 할인을 받으시겠습니까? (Y/N)";
    private static final String CONTINUE_SHOPPING_MESSAGE = "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)";

    public static CInputView getInstance() {
        if (Objects.isNull(inputView)) {
            inputView = new CInputView();
        }
        return inputView;
    }

    /**
     * 구매 입력 받기
     *
     * @return 구매 입력 데이터
     * @throws ConvenienceException 형식에 맞지않음
     */
    @Override
    public List<BuyInputDto> getBuyInput() throws ConvenienceException {
        System.out.println(BUY_MESSAGE);

        String input = Console.readLine();

        validateBuyInput(input);

        return Arrays.stream(input.split(StringUtil.COMMA)).map(this::toBuyInputDto).toList();
    }

    /**
     * 분리된 텍스트를 데이터로 변환
     *
     * @param line 입력
     * @return 입력 데이터
     */
    private BuyInputDto toBuyInputDto(String line) {
        String[] productCount = line.substring(1, line.length() - 1).split(StringUtil.HIGH_BAR);
        return new BuyInputDto(productCount);
    }

    @Override
    public boolean isAdd(String name) {
        System.out.println(String.format(ADD_MESSAGE_FORMAT, name));
        return isYes();
    }

    @Override
    public boolean isRemove(String name, int count) {
        System.out.println(String.format(REMOVE_MESSAGE_FORMAT, name, count));
        return isYes();
    }

    @Override
    public boolean isMembership() {
        System.out.println(MEMBERSHIP_MESSAGE);
        return isYes();
    }

    @Override
    public boolean continueShopping() {
        System.out.println(CONTINUE_SHOPPING_MESSAGE);
        return isYes();
    }

    private boolean isYes() {
        String input = Console.readLine();
        if (input.equals("Y")) {
            return true;
        }
        if (input.equals("N")) {
            return false;
        }
        throw new ConvenienceException(ErrorMessage.OTHER_INPUT_ERROR);
    }
}
