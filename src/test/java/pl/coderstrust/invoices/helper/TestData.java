package pl.coderstrust.invoices.helper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import pl.coderstrust.invoices.model.Company;
import pl.coderstrust.invoices.model.Invoice;
import pl.coderstrust.invoices.model.InvoiceEntry;
import pl.coderstrust.invoices.model.Vat;

public class TestData {

  public static Invoice emptyInvoice() {
    return new Invoice(0L, null, null, null, null, null);
  }

  public static Invoice sampleInvoice() {
    return new Invoice(11L,
        "test-invoice",
        LocalDate.of(2018, 5, 10),
        sampleCompany(),
        sampleCompany2(),
        sampleInvoiceEntries());
  }

  public static String sampleInvoiceAsString() {
    return "{\"id\":11,\"identifier\":\"test-invoice\",\"issuedDate\":\"2018-05-10\","
        + "\"seller\":{\"name\":\"company\",\"taxIdentificationNumber\":\"767-294-43-78\","
        + "\"streetAddress\":\"Królewska 25\",\"postalCode\":\"30-440\",\"city\":\"Warszawa\"},"
        + "\"buyer\":{\"name\":\"company1\",\"taxIdentificationNumber\":\"959-294-43-78\","
        + "\"streetAddress\":\"Marszałkowska 5\",\"postalCode\":\"25-440\",\"city\":\"Kraków\"},"
        + "\"entries\":[{\"productName\":\"testProduct\",\"amount\":2,\"price\":2.47,"
        + "\"vat\":\"VAT_5\"},{\"productName\":\"testProduct 2\",\"amount\":10,\"price\":1.92,"
        + "\"vat\":\"VAT_23\"}]}";
  }

  private static List<InvoiceEntry> sampleInvoiceEntries() {
    return Arrays.asList(
        new InvoiceEntry("testProduct", 2, BigDecimal.valueOf(2.47d), Vat.VAT_5),
        new InvoiceEntry("testProduct 2", 10, BigDecimal.valueOf(1.92d), Vat.VAT_23)
    );
  }

  private static Company sampleCompany2() {
    return new Company("company1", "959-294-43-78",
        "Marszałkowska 5", "25-440", "Kraków");
  }

  private static Company sampleCompany() {
    return new Company("company", "767-294-43-78",
        "Królewska 25", "30-440", "Warszawa");
  }
}
