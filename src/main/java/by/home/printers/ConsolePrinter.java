package by.home.printers;

import by.home.model.Check;
import by.home.printers.template.CheckTemplate;

public class ConsolePrinter implements CheckPrinter {
    private CheckTemplate checkTemplate;

    public ConsolePrinter(CheckTemplate checkTemplate) {
        this.checkTemplate = checkTemplate;
    }

    @Override
    public void printCheck(Check check) {
        System.out.println(checkTemplate.getCheckForPrint(check));
    }
}
