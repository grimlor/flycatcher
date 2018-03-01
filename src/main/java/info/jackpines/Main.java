package info.jackpines;

import org.apache.commons.validator.ValidatorException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.apache.commons.validator.routines.UrlValidator;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            validate(args);
        } catch (ValidatorException e) {
            System.out.println(e.getMessage());
        }

        try {
            Document doc = Jsoup.connect(args[0]).get();
            String text = doc.text();
            System.out.print(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void validate(String[] args) throws ValidatorException {
        if (args == null || args.length < 1) {
            throw new ValidatorException("Please provide one or more URLs");
        }
        for (String arg : args) {
            try {
                validate(arg);
            } catch (ValidatorException e) {
                e.printStackTrace();
            }
        }
    }

    private static void validate(String urlString) throws ValidatorException {
        String[] schemes = {"http","https"}; // DEFAULT schemes = "http", "https", "ftp"
        UrlValidator urlValidator = new UrlValidator(schemes);
        if (!urlValidator.isValid(urlString)) {
            throw new ValidatorException(urlString);
        }
    }
}
