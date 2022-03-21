package by.home.factory;

import by.home.printers.CheckPrinter;
import by.home.printers.ConsolePrinter;
import by.home.printers.TxtPrinter;
import by.home.printers.PdfPrinter;
import by.home.printers.template.CheckTemplate;

public enum CheckPrinterType {
    CONSOLE("Console Printer") {
        @Override
        public CheckPrinter getCheckPrinter(CheckTemplate checkTemplate) {
            return new ConsolePrinter(checkTemplate);
        }
    },
    TXT("TXT Printer") {
        @Override
        public CheckPrinter getCheckPrinter(CheckTemplate checkTemplate) {
            return new TxtPrinter(checkTemplate);
        }
    },
    PDF("PDF Printer") {
        @Override
        public CheckPrinter getCheckPrinter(CheckTemplate checkTemplate) {
            return new PdfPrinter(checkTemplate);
        }
    };

    private final String type;

    CheckPrinterType(String type) {
        this.type = type;
    }

    public abstract CheckPrinter getCheckPrinter(CheckTemplate checkTemplate);

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Type -> " + type;
    }
}
