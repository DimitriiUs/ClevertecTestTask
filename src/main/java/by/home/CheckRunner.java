package by.home;

import by.home.db.DiscountCardDB;
import by.home.db.ProductDB;
import by.home.factory.CheckPrinterType;
import by.home.printers.CheckPrinter;
import by.home.printers.template.SimpleCheckTemplate;

public class CheckRunner {

    public static final int PRODUCTS_DB_FILENAME = 0;
    public static final int CARDS_DB_FILENAME = 1;

    public static void main(String[] args) {
        var productDB = new ProductDB(args[PRODUCTS_DB_FILENAME]);
        var discountCardDB = new DiscountCardDB(args[CARDS_DB_FILENAME]);

        var checkUtils = new CheckUtils();
        Check check = checkUtils.getCheck(args, productDB, discountCardDB);

        SimpleCheckTemplate template = new SimpleCheckTemplate();
        CheckPrinter pdfPrinter = CheckPrinterType.PDF.getCheckPrinter(template);
        CheckPrinter txtPrinter = CheckPrinterType.TXT.getCheckPrinter(template);
        CheckPrinter consolePrinter = CheckPrinterType.CONSOLE.getCheckPrinter(template);

        txtPrinter.printCheck(check);
        pdfPrinter.printCheck(check);
        consolePrinter.printCheck(check);
    }
}
