package pl.coderstrust.invoices.db.hibernate;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
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
    return Optional.empty();
  }

  @Override
  public Long updateInvoice(Invoice invoice) throws DatabaseException {
    logger.info("Updating invoice {} in Database.", invoice);
    throwExceptionIfInvoiceNotFound(invoice.getId());

    invoiceRepository.save(invoice).getId();
    return invoice.getId();

  }

  @Override
  public Collection<Invoice> findInvoicesByDateRange(LocalDate startDate, LocalDate endDate) {
    logger.info("Find invoice between {} and {}. ", startDate, endDate);
    return InvoiceUtil.getInvoicesByDateRange(startDate, endDate, getInvoices());
  }

  @Override
  public void deleteInvoice(Long id) throws DatabaseException {
    logger.info("Deleting invoice with id {} from Database.", id);
    throwExceptionIfInvoiceNotFound(id);

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

