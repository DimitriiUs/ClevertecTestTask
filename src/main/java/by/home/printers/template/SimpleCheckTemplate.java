package by.home.printers.template;

import by.home.model.Check;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

public class SimpleCheckTemplate implements CheckTemplate{
    private String TEMPLATE_FILE_NAME = "check_template.txt";
    private List<String> checkTemplateFileLines = Collections.emptyList();

    public SimpleCheckTemplate() {
        readCheckTemplateFile(TEMPLATE_FILE_NAME);
    }

    private void readCheckTemplateFile(String fileName) {
        try {
            checkTemplateFileLines = Files.readAllLines(
                    Paths.get(this
                            .getClass()
                            .getClassLoader()
                            .getResource(fileName)
                            .toURI()),
                    StandardCharsets.UTF_8);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getCheckForPrint(Check check) {
        StringBuilder result = new StringBuilder();
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        final String NEW_LINE = "\n";
        final int CHECK_ID_AND_DATE_LINE =  6;
        final int TIME_LINE =               7;
        final int PRODUCTS_LINE =           11;
        final int TOTAL_COST_LINE =         14;

        checkTemplateFileLines.forEach(
                line -> {
                    switch (checkTemplateFileLines.indexOf(line)) {
                        case CHECK_ID_AND_DATE_LINE ->
                                result.append(String.format((line), check.getId(), date, date, date)).append(NEW_LINE);
                        case TIME_LINE ->
                                result.append(String.format((line), time, time)).append(NEW_LINE);
                        case PRODUCTS_LINE ->
                                result.append(createProductsLines(check));
                        case TOTAL_COST_LINE ->
                                result.append(String.format((line), check.getTotalPriceForCheck())).append(NEW_LINE);
                        default -> result.append(line).append(NEW_LINE);
                    }
                }
        );
        return result.toString();
    }

    private String createProductsLines(Check check) {
        StringBuilder result = new StringBuilder();
        final String PRODUCT_LINE = "|%2d   %-16s    %5.2f  %5.2f|%n";
        final String DISCOUNT_LINE = "|Discount: %s%-13%            |%n";

        check.getProducts().forEach(
                (k, v) -> {
                    result.append(String.format(PRODUCT_LINE,
                            v,
                            k.getName(),
                            k.getPrice(),
                            check.getTotalPriceForProduct(k)));
                    if (check.checkProductForDiscount(k)) {
                        result.append(String.format(DISCOUNT_LINE,
                                check.getDiscountCard().getDiscount()));
                    }
                }
        );

        return result.toString();
    }
}
