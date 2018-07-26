package pl.coderstrust.invoices.db;

import pl.coderstrust.invoices.exception.DatabaseException;
import pl.coderstrust.invoices.model.Invoice;

import java.util.List;
import java.util.Optional;

public interface Database {

  Long saveInvoice(Invoice invoice) throws DatabaseException;

  Optional<Invoice> getInvoiceById(long id);

  int updateInvoice(Invoice invoice) throws DatabaseException;

  List<Invoice> getInvoices();

  void removeInvoice(long id) throws DatabaseException;
}