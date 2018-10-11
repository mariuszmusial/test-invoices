package pl.coderstrust.invoices.db;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import pl.coderstrust.invoices.exception.DatabaseException;
import pl.coderstrust.invoices.model.Invoice;

public interface Database {

  Long saveInvoice(Invoice invoice) throws DatabaseException;

  Collection<Invoice> getInvoices() throws DatabaseException;

  Optional<Invoice> getInvoiceById(Long id) throws DatabaseException;

  Long updateInvoice(Invoice invoice) throws DatabaseException;

  Collection<Invoice> findInvoicesByDateRange(LocalDate startDate, LocalDate endDate)
      throws DatabaseException;

  void deleteInvoice(Long id) throws DatabaseException;
}