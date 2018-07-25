package pl.coderstrust.invoices.db;

import pl.coderstrust.invoices.model.Invoice;

import java.util.List;

public interface Database {

  Long saveInvoice(Invoice invoice);

  Invoice getInvoiceById(long id);

  void updateInvoice(Invoice invoice);

  List<Invoice> getInvoices();

  void removeInvoice(long id);
}