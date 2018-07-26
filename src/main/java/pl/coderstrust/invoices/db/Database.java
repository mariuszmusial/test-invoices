package pl.coderstrust.invoices.db;

import pl.coderstrust.invoices.exception.DatabaseException;
import pl.coderstrust.invoices.model.Invoice;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface Database {

  Long saveInvoice(Invoice invoice) throws DatabaseException;

  Collection<Invoice> getInvoices();

  Optional<Invoice> getInvoiceById(Long id);

  int updateInvoice(Invoice invoice) throws DatabaseException;

  List<Invoice> findInvoicesByDateRange(LocalDate startDate, LocalDate endDate)
      throws DatabaseException;

  void deleteInvoice(Long id) throws DatabaseException;


}