package pl.coderstrust.project.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.coderstrust.project.model.Invoice;

import java.time.LocalDate;

public class InvoiceJsonMapper {

    public static Invoice fromJson(String json) {
        return null;
    }

    public static String toJson(Invoice invoice) {
        ObjectMapper objectMapper = new ObjectMapper();
        
        invoice.setId("abc");
        invoice.setIssueDate(LocalDate.parse("2018-07-23"));

        return null;
    }
}
