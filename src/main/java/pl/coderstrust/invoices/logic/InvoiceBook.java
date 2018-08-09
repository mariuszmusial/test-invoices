package pl.coderstrust.invoices.logic;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import pl.coderstrust.invoices.db.Database;
import pl.coderstrust.invoices.exception.DatabaseException;
import pl.coderstrust.invoices.model.Invoice;

public class InvoiceBook {

  private Database database;

  public InvoiceBook(Database database) {
    this.database = database;
  }

  public Long saveInvoice(Invoice invoice) throws DatabaseException {
    return database.saveInvoice(invoice);
  }

  public Collection<Invoice> getInvoices() {
    return database.getInvoices();
  }

  public Optional<Invoice> getInvoiceById(Long id) {
    return database.getInvoiceById(id);
  }

  public int updateInvoice(Invoice invoice) throws DatabaseException {
    return database.updateInvoice(invoice);
  }

  public List<Invoice> findInvoicesByDateRange(LocalDate startDate, LocalDate endDate) {
    return database.findInvoicesByDateRange(startDate, endDate);
  }

  public void deleteInvoice(Long id) throws DatabaseException, IOException {
    database.deleteInvoice(id);
  }
}
