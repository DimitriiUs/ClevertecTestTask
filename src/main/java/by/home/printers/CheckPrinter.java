package by.home.printers;

import by.home.model.Check;
import com.itextpdf.text.DocumentException;

import java.io.IOException;

public interface CheckPrinter {
    void printCheck(Check check) throws IOException, DocumentException;
}
