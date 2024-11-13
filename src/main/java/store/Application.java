package store;

import store.controller.ConvenienceController;
import store.model.CReceipt;
import store.model.Convenience;
import store.model.Receipt;
import store.view.*;

public class Application {
    public static void main(String[] args) {

        Convenience convenience = new Convenience();
        Receipt receipt = new CReceipt();
        InputView inputView = CInputView.getInstance();
        OutputView outputView = COutputView.getInstance();
        FileReaderHelper fileReaderHelper = FileReaderHelper.getInstance();

        ConvenienceController convenienceController = new ConvenienceController(convenience, receipt,
                inputView, outputView, fileReaderHelper);

        convenienceController.run();
    }
}
