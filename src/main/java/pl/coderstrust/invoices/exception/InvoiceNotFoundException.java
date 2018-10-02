package pl.coderstrust.invoices.exception;

public class InvoiceNotFoundException extends DatabaseException {

  public InvoiceNotFoundException(String message) {
    super(message);
  }
}