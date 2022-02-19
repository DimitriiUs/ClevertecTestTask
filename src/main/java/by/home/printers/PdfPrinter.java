package by.home.printers;

import by.home.Check;
import by.home.printers.template.CheckTemplate;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
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

    public PdfPrinter(CheckTemplate checkTemplate) {
        this.checkTemplate = checkTemplate;
    }

    @Override
    public void printCheck(Check check) {
        final String pdfTemplate = "D:\\WORK\\00WORKSPACE\\Clevertec\\BackEnd\\TestTask\\src\\main\\resources\\Clevertec_Template.pdf";
        final String fileName = "check.pdf";
        Document document = new Document();
        PdfWriter pdfWriter = null;

        try {
            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(fileName));
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }

        document.open();
        document.newPage();
        PdfReader pdfReader = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(pdfTemplate);
            pdfReader = new PdfReader(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PdfImportedPage pdfImportedPage = pdfWriter.getImportedPage(pdfReader, 1);
        PdfContentByte pdfContentByte = pdfWriter.getDirectContent();
        pdfContentByte.addTemplate(pdfImportedPage, 0, 0);
        PdfPTable table = new PdfPTable(1);
//        Phrase phrase = new Phrase();
//        phrase.setFont(new Font());
//        phrase.add(checkTemplate.getCheckForPrint(check));
//        table.addCell(phrase);
        Font font = FontFactory.getFont(FontFactory.COURIER);
        final Paragraph paragraph = new Paragraph(checkTemplate.getCheckForPrint(check), font);
        PdfPCell cell = new PdfPCell();
        cell.addElement(paragraph);
        cell.setBorderWidth(0);
        cell.setPaddingTop(200);
        cell.setPaddingLeft(65);
        table.addCell(cell);


        try {
            document.add(table); document.setMargins(100,0,500,0);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        document.close();
    }
}
