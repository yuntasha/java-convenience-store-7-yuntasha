package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.dto.BuyInputDto;
import store.exception.ConvenienceException;
import store.exception.ErrorMessage;
import store.util.StringUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class CInputView implements InputView {

    private static CInputView inputView;

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
        System.out.println(StringUtil.BUY_MESSAGE);

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
        System.out.println(String.format(StringUtil.ADD_MESSAGE_FORMAT, name));
        return isYes();
    }

    @Override
    public boolean isRemove(String name, int count) {
        System.out.println(String.format(StringUtil.REMOVE_MESSAGE_FORMAT, name, count));
        return isYes();
    }

    @Override
    public boolean isMembership() {
        System.out.println(StringUtil.MEMBERSHIP_MESSAGE);
        return isYes();
    }

    @Override
    public boolean continueShopping() {
        System.out.println(StringUtil.CONTINUE_SHOPPING_MESSAGE);
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
