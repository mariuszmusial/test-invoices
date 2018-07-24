package pl.coderstrust.invoices.db;

import pl.coderstrust.invoices.model.Invoice;

import java.util.List;

public interface Database {

  void saveInvoice(Invoice invoice);

  List<Invoice> getInvoices();
}
