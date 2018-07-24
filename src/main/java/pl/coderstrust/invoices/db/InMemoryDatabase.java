package pl.coderstrust.invoices.db;

import pl.coderstrust.invoices.model.Invoice;

import java.util.List;

public class InMemoryDatabase implements Database {

  private List<Invoice> invoices;

  @Override
  public void saveInvoice(Invoice invoice) {
    invoices.add(invoice);
  }

  @Override
  public List<Invoice> getInvoices() {
    return invoices;
  }
}
