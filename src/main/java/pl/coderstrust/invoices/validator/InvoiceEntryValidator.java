package pl.coderstrust.invoices.validator;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import pl.coderstrust.invoices.model.InvoiceEntry;

public class InvoiceEntryValidator {

  private static final String EXPECTED_CORRECT_AMOUNT = "Expected correct amount (1 or more)";
  private static final String EXPECTED_NOT_EMPTY_PRODUCT_NAME = "Expected not empty product name";
  private static final String EXPECTED_NOT_EMPTY_PRICE = "Expected not empty price";
  private static final String EXPECTED_NOT_EMPTY_VAT = "Expected not empty vat";

  public Collection<String> validate(InvoiceEntry invoiceEntry) {
    List<String> validationExceptions = new LinkedList<>();

    if (invoiceEntry.getProductName() == null || invoiceEntry.getProductName().isEmpty()) {
      validationExceptions.add(EXPECTED_NOT_EMPTY_PRODUCT_NAME);
    }

    if (invoiceEntry.getAmount() == null || invoiceEntry.getAmount() <= 0) {
      validationExceptions.add(EXPECTED_CORRECT_AMOUNT);
    }

    if (invoiceEntry.getPrice() == null
        || invoiceEntry.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
      validationExceptions.add(EXPECTED_NOT_EMPTY_PRICE);
    }

    if (invoiceEntry.getVat() == null) {
      validationExceptions.add(EXPECTED_NOT_EMPTY_VAT);
    }
    return validationExceptions;
  }
}