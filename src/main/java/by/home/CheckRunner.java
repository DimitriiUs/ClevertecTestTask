package by.home;

import by.home.db.DiscountCardDB;
import by.home.db.ProductDB;
import by.home.factory.CheckPrinterType;
import by.home.model.Check;
import by.home.printers.CheckPrinter;
import by.home.printers.template.SimpleCheckTemplate;
import by.home.utils.CheckUtils;
import com.itextpdf.text.DocumentException;

import java.io.IOException;

public class CheckRunner {

    public static final int PRODUCTS_DB_FILENAME = 0;
    public static final int CARDS_DB_FILENAME = 1;

    public static void main(String[] args) {
        var productDB = new ProductDB(args[PRODUCTS_DB_FILENAME]);
        var discountCardDB = new DiscountCardDB(args[CARDS_DB_FILENAME]);

        var checkUtils = new CheckUtils();
        Check checkJson = checkUtils.getCheck(args, productDB, discountCardDB);
        Check checkDB = checkUtils.getCheck(args);

        SimpleCheckTemplate template = null;
        try {
            template = new SimpleCheckTemplate();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CheckPrinter pdfPrinter = CheckPrinterType.PDF.getCheckPrinter(template);
        CheckPrinter txtPrinter = CheckPrinterType.TXT.getCheckPrinter(template);
        CheckPrinter consolePrinter = CheckPrinterType.CONSOLE.getCheckPrinter(template);

        try {
            txtPrinter.printCheck(checkDB);
            pdfPrinter.printCheck(checkDB);
            consolePrinter.printCheck(checkDB);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }
}
