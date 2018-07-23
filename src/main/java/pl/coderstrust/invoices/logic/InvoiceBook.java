package pl.coderstrust.invoices.logic;

import pl.coderstrust.invoices.model.Invoice;

import java.util.List;

public class InvoiceBook {

  private List<Invoice> invoices;

  public void saveInvoice(Invoice invoice) {
    invoices.add(invoice);
  }

  public List<Invoice> getInvoices() {
    return invoices;
  }
}
