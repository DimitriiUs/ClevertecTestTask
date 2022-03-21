package by.home.printers.template;

import by.home.model.Check;
import by.home.utils.Constants;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

public class SimpleCheckTemplate implements CheckTemplate {
    private List<String> checkTemplateFileLines = Collections.emptyList();
    private Constants constants = Constants.getInstance();

    public SimpleCheckTemplate() throws IOException {
        readCheckTemplateFile();
    }

    private void readCheckTemplateFile() throws IOException {
        checkTemplateFileLines = Files.readAllLines(
                Paths.get(constants.TXT_TEMPLATE),
                StandardCharsets.UTF_8
        );
    }

    @Override
    public String getCheckForPrint(Check check) {
        StringBuilder result = new StringBuilder();
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        final String NEW_LINE = "\n";
        final int CHECK_ID_AND_DATE_LINE = 6;
        final int TIME_LINE = 7;
        final int PRODUCTS_LINE = 11;
        final int TOTAL_COST_LINE = 14;

        checkTemplateFileLines.forEach(
                line -> {
                    switch (checkTemplateFileLines.indexOf(line)) {
                        case CHECK_ID_AND_DATE_LINE -> result.append(String.format((line), check.getId(), date, date, date)).append(NEW_LINE);
                        case TIME_LINE -> result.append(String.format((line), time, time)).append(NEW_LINE);
                        case PRODUCTS_LINE -> result.append(createProductsLines(check));
                        case TOTAL_COST_LINE -> result.append(String.format((line), check.getTotalPriceForCheck())).append(NEW_LINE);
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
                            prepareNameForCheck(k.getName()),
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

    private String prepareNameForCheck(String name) {
        StringBuilder sb = new StringBuilder(name);
        final int MAX_LENGTH_NAME_FOR_CHECK = 20;

        int i = name.length() / MAX_LENGTH_NAME_FOR_CHECK;
        if (name.length() > MAX_LENGTH_NAME_FOR_CHECK) {
            sb.insert(20, "............|\n|.....");
        }

        for (int j = 20; j < i * 10; j += 10) {
            sb.insert(j + 1, "|     ");
        }


        return sb.toString();
    }


}
