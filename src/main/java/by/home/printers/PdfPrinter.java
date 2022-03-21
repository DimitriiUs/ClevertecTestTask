package by.home.printers;

import by.home.model.Check;
import by.home.printers.template.CheckTemplate;
import by.home.utils.Constants;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfPrinter implements CheckPrinter {

    private CheckTemplate checkTemplate;
    private Constants constants = Constants.getInstance();

    public PdfPrinter(CheckTemplate checkTemplate) {
        this.checkTemplate = checkTemplate;
    }

    @Override
    public void printCheck(Check check) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter pdfWriter = null;

        pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(constants.PDF_FILE_NAME));

        document.open();
        document.newPage();
        PdfReader pdfReader = null;
        FileInputStream fileInputStream = new FileInputStream(constants.PDF_TEMPLATE);
        pdfReader = new PdfReader(fileInputStream);
        PdfImportedPage pdfImportedPage = pdfWriter.getImportedPage(pdfReader, 1);
        PdfContentByte pdfContentByte = pdfWriter.getDirectContent();
        pdfContentByte.addTemplate(pdfImportedPage, 0, 0);
        PdfPTable table = new PdfPTable(1);
        Font font = FontFactory.getFont(FontFactory.COURIER);
        final Paragraph paragraph = new Paragraph(checkTemplate.getCheckForPrint(check), font);
        PdfPCell cell = new PdfPCell();
        cell.addElement(paragraph);
        cell.setBorderWidth(0);
        cell.setPaddingTop(200);
        cell.setPaddingLeft(65);
        table.addCell(cell);

        document.add(table);
        document.setMargins(100, 0, 500, 0);

        document.close();
    }
}
