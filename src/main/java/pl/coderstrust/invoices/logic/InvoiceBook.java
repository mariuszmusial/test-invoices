package pl.coderstrust.invoices.logic;

import pl.coderstrust.invoices.db.Database;
import pl.coderstrust.invoices.model.Invoice;

import java.util.List;

public class InvoiceBook {

  private Database database;

  public InvoiceBook(Database database) {
    this.database = database;
  }

  public void saveInvoice(Invoice invoice) {
    database.saveInvoice(invoice);
  }

  public List<Invoice> getInvoices() {
    return database.getInvoices();
  }
}