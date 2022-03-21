package by.home.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Constants {

    private static volatile Constants instance;
    private static final String PROPERTY_FILE = "src/main/resources/config.properties";
    public String PDF_TEMPLATE;
    public String PDF_FILE_NAME;
    public String TXT_FILE_NAME;
    public String TXT_TEMPLATE;

    private Constants() {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(PROPERTY_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }

        PDF_TEMPLATE = props.getProperty("pdf_template");
        PDF_FILE_NAME = props.getProperty("pdf_file_name");
        TXT_FILE_NAME = props.getProperty("txt_file_name");
        TXT_TEMPLATE = props.getProperty("txt_template");
    }

    public static Constants getInstance() {
        Constants localInstance = instance;
        if (localInstance == null) {
            synchronized (Constants.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Constants();
                }
            }
        }
        return localInstance;
    }
}
