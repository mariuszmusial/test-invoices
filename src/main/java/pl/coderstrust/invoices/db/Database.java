package pl.coderstrust.invoices.db;

import pl.coderstrust.invoices.model.Invoice;

import java.util.List;

public interface Database {

  void get(int id);

  void saveInvoice(Invoice invoice);

  List<Invoice> getInvoices();

  void update(Invoice invoice);

  void remove(int id);
}