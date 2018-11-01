package pl.coderstrust.invoices.db.hibernate;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import pl.coderstrust.invoices.db.Database;
import pl.coderstrust.invoices.db.util.InvoiceUtil;
import pl.coderstrust.invoices.exception.DatabaseException;
import pl.coderstrust.invoices.exception.InvoiceNotFoundException;
import pl.coderstrust.invoices.model.Invoice;

@Repository
public class HibernateDatabase implements Database {

  private static final Logger logger = LoggerFactory.getLogger(HibernateDatabase.class);

  private InvoiceRepository invoiceRepository;

  public HibernateDatabase(InvoiceRepository invoiceRepository) {
    this.invoiceRepository = invoiceRepository;
  }

  @Transactional(rollbackOn = DatabaseException.class)
  @Override
  public Long saveInvoice(Invoice invoice) {
    logger.info("Saving invoice {} to Db.", invoice);
    return invoiceRepository.save(invoice).getId();
  }

  @Override
  public Collection<Invoice> getInvoices() {
    Iterable<Invoice> invoices = invoiceRepository.findAll();
    return StreamSupport.stream(invoices.spliterator(), false).collect(Collectors.toList());
  }

  @Override
  public Optional<Invoice> getInvoiceById(Long id) {
    logger.info("Updating invoice {} in file.", id);
    return invoiceRepository.findById(id);
  }

  @Transactional(rollbackOn = DatabaseException.class)
  @Override
  public void updateInvoice(Invoice invoice) throws DatabaseException {
    logger.info("Updating invoice {} in Database.", invoice);
    throwExceptionIfInvoiceNotFound(invoice.getId());

    invoiceRepository.save(invoice).getId();
  }

  @Override
  public Collection<Invoice> findInvoicesByDateRange(LocalDate startDate, LocalDate endDate) {
    logger.info("Find invoice between {} and {}. ", startDate, endDate);
    return InvoiceUtil.getInvoicesByDateRange(startDate, endDate, getInvoices());
  }

  @Transactional(rollbackOn = DatabaseException.class)
  @Override
  public void deleteInvoice(Long id) throws DatabaseException {
    logger.info("Deleting invoice with id {} from Database.", id);

    invoiceRepository.deleteById(id);
  }

  private void throwExceptionIfInvoiceNotFound(Long id) throws DatabaseException {
    if (!getInvoiceById(id).isPresent()) {
      String message = "Invoice not in database.";
      logger.error(message);
      throw new InvoiceNotFoundException(message);
    }
  }
}

