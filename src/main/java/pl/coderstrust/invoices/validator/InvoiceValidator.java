package pl.coderstrust.invoices.validator;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import pl.coderstrust.invoices.model.Company;
import pl.coderstrust.invoices.model.Invoice;
import pl.coderstrust.invoices.model.InvoiceEntry;

public class InvoiceValidator {

  private final Validator<InvoiceEntry> invoiceEntryValidator;
  private final Validator<Company> companyValidator;

  private static final String EXPECTED_NOT_EMPTY_ID = "Expected not empty Id";
  private static final String ID_SHOULD_BE_GREATER_THAN_ZERO = "Expected Id to be greater than 0,"
      + " got: ";
  private static final String EXPECTED_NOT_EMPTY_ISSUE_DATE = "Expected not empty issue date";
  private static final String EXPECTED_DATE_NOT_GREATER_THAN_TODAY = "Expected issue date not"
      + " later than now, got: ";
  private static final String EXP_NOT_EMPTY_INV_IDENTIFIER = "Expected not empty invoice "
      + "identifier";

  public InvoiceValidator(Validator<InvoiceEntry> invoiceEntryValidator,
      Validator<Company> companyValidator) {
    this.invoiceEntryValidator = invoiceEntryValidator;
    this.companyValidator = companyValidator;
  }

  public List<String> validateInvoiceForUpdate(Invoice invoice) {
    return validate(invoice, true);
  }

  public List<String> validateInvoiceForSave(Invoice invoice) {
    return validate(invoice, false);
  }

  private List<String> validate(Invoice invoice, boolean checkForId) {
    List<String> validationExceptions = new LinkedList<>();
    if (checkForId) {
      if (invoice.getId() == null) {
        validationExceptions.add(EXPECTED_NOT_EMPTY_ID);
      } else {
        if (invoice.getId() < 0) {
          validationExceptions.add(
              ID_SHOULD_BE_GREATER_THAN_ZERO + String.valueOf(invoice.getId()));
        }
      }
    }
    if (invoice.getIssuedDate() == null) {

      validationExceptions
          .add(EXPECTED_NOT_EMPTY_ISSUE_DATE);
    } else {
      if (invoice.getIssuedDate().isAfter(LocalDate.now())) {
        validationExceptions.add(EXPECTED_DATE_NOT_GREATER_THAN_TODAY + String
            .valueOf(invoice.getIssuedDate()));
      }
    }

    if (invoice.getIdentifier() == null) {
      validationExceptions.add(
          EXP_NOT_EMPTY_INV_IDENTIFIER);
    }
    if (invoice.getEntries() == null) {
      validationExceptions.add("Expected not empty invoice entries");
    } else {
      invoice.getEntries().forEach(invoiceEntry -> invoiceEntryValidator.validate(invoiceEntry)
          .forEach(validationException -> validationExceptions.add(
              "Validation of entry failed, message: " + validationException)));
    }
    if (invoice.getBuyer() == null) {
      validationExceptions.add("Expected not empty buyer");
    } else {
      companyValidator.validate(invoice.getBuyer())
          .forEach((validationException -> validationExceptions.add(
              "Validation of buyer failed, message: " + validationException)));
    }
    if (invoice.getSeller() == null) {
      validationExceptions.add("Expected not empty seller");
    } else {
      companyValidator.validate(invoice.getSeller())
          .forEach((validationException -> validationExceptions.add(
              "Validation of seller failed, message: " + validationException)));
    }
    return validationExceptions;
  }
}