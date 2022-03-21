package by.home.printers;

import by.home.model.Check;
import by.home.printers.template.CheckTemplate;
import by.home.utils.Constants;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TxtPrinter implements CheckPrinter {
    private CheckTemplate checkTemplate;
    private Constants constants = Constants.getInstance();

    public TxtPrinter(CheckTemplate checkTemplate) {
        this.checkTemplate = checkTemplate;
    }

    @Override
    public void printCheck(Check check) throws IOException {
        FileWriter fileWriter = null;
        fileWriter = new FileWriter(constants.TXT_FILE_NAME);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(checkTemplate.getCheckForPrint(check));
        printWriter.close();
    }
}
