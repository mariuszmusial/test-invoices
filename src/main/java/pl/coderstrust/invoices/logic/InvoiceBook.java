package pl.coderstrust.invoices.logic;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderstrust.invoices.db.Database;
import pl.coderstrust.invoices.exception.DatabaseException;
import pl.coderstrust.invoices.model.Invoice;

@Service
public class InvoiceBook {

  private Database database;
  private static final Logger logger = LoggerFactory.getLogger(InvoiceBook.class);

  @Autowired
  public InvoiceBook(Database database) {
    this.database = database;
  }

  public Long saveInvoice(Invoice invoice) throws DatabaseException {

    logger.debug("Saving invoice with ID " + invoice.getId());

    return database.saveInvoice(invoice);
  }

  public Collection<Invoice> getInvoices() throws DatabaseException {

    logger.debug("Getting invoices.");

    return database.getInvoices();
  }

  public Optional<Invoice> getInvoiceById(Long id) throws DatabaseException {

    logger.debug("Trying to get invoice by id {}.", id);

    return database.getInvoiceById(id);
  }

  public void updateInvoice(Invoice invoice) throws DatabaseException {

    logger.debug("Incorrect data provided while updating invoice.");

    database.updateInvoice(invoice);
  }

  public Collection<Invoice> findInvoicesByDateRange(LocalDate startDate, LocalDate endDate)
      throws DatabaseException {

    logger.debug("Incorrect dates provided.");

    return database.findInvoicesByDateRange(startDate, endDate);
  }

  public void deleteInvoice(Long id) throws DatabaseException {

    logger.debug("Invoice with id {} not found.", id);

    database.deleteInvoice(id);
  }
}
