package pl.coderstrust.invoices.logic;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderstrust.invoices.db.Database;
import pl.coderstrust.invoices.exception.DatabaseException;
import pl.coderstrust.invoices.model.Invoice;

@Service
public class InvoiceBook {

  private Database database;

  @Autowired
  public InvoiceBook(Database database) {
    this.database = database;
  }

  public Long saveInvoice(Invoice invoice) throws DatabaseException {
    return database.saveInvoice(invoice);
  }

  public Collection<Invoice> getInvoices() throws DatabaseException {
    return database.getInvoices();
  }

  public Optional<Invoice> getInvoiceById(Long id) throws DatabaseException, IOException {
    return database.getInvoiceById(id);
  }

  public void updateInvoice(Invoice invoice) throws DatabaseException {
    database.updateInvoice(invoice);
  }

  public Collection<Invoice> findInvoicesByDateRange(LocalDate startDate, LocalDate endDate)
      throws DatabaseException {
    return database.findInvoicesByDateRange(startDate, endDate);
  }

  public void deleteInvoice(Long id) throws DatabaseException, IOException {
    database.deleteInvoice(id);
  }
}
