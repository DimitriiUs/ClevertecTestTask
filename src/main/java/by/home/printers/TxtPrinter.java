package by.home.printers;

import by.home.Check;
import by.home.printers.template.CheckTemplate;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FilePrinter implements CheckPrinter{
    private CheckTemplate checkTemplate;
    private final String fileName = "check.txt";

    public FilePrinter(CheckTemplate checkTemplate) {
        this.checkTemplate = checkTemplate;
    }

    @Override
    public void printCheck(Check check) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(checkTemplate.getCheckForPrint(check));
        printWriter.close();
    }
}
