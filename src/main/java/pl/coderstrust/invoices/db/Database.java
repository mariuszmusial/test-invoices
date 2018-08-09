package pl.coderstrust.invoices.db;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import pl.coderstrust.invoices.exception.DatabaseException;
import pl.coderstrust.invoices.model.Invoice;

public interface Database {

  Long saveInvoice(Invoice invoice) throws DatabaseException;

  List<Invoice> getInvoices();

  Optional<Invoice> getInvoiceById(Long id);

  int updateInvoice(Invoice invoice) throws DatabaseException;

  List<Invoice> findInvoicesByDateRange(LocalDate startDate, LocalDate endDate);

  void deleteInvoice(Long id) throws DatabaseException, IOException;
}