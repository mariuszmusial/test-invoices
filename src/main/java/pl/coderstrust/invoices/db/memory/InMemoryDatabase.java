package pl.coderstrust.invoices.db.memory;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import pl.coderstrust.invoices.db.Database;
import pl.coderstrust.invoices.exception.DatabaseException;
import pl.coderstrust.invoices.model.Invoice;

@Repository
public class InMemoryDatabase implements Database {

  private static final Logger logger = LoggerFactory.getLogger(InMemoryDatabase.class);

  private final Map<Long, Invoice> invoices = new HashMap<>();
  private long id = 0;
  private static final String WRONG_ID_MESSAGE = "The invoice with given ID does not exist: ";

  public InMemoryDatabase() {
  }

  @Override
  public Long saveInvoice(Invoice invoice) {
    logger.info("Trying to save invoice with id {}", invoice.getId());
    invoices
        .put(++id, new Invoice(id, invoice.getIdentifier(), invoice.getIssuedDate(),
            invoice.getBuyer(), invoice.getSeller(), invoice.getEntries()));
    logger.info("Invoice with id {} saved.", invoice.getId());
    return id;
  }

  @Override
  public void updateInvoice(Invoice invoice) throws DatabaseException {
    logger.info("Trying to update invoice with id {}", invoice.getId());
    if (!invoices.containsKey(invoice.getId())) {
      String message = WRONG_ID_MESSAGE + invoice.getId();
      logger.error(message);
      throw new DatabaseException(message);
    }
    invoices.put(invoice.getId(), invoice);
    logger.info("Invoice with id {} updated.", invoice.getId());
  }

  @Override
  public void deleteInvoice(Long id) {
    logger.info("Trying to delete invoice with id {}", id);
    if (invoices.remove(id) == null) {
      logger.warn("Invoice with id {} not found.", id);
    } else {
      logger.info("Invoice with id {} deleted.", id);
    }
  }

  @Override
  public Optional<Invoice> getInvoiceById(Long id) {
    logger.info("Trying to get invoice with id {}", id);
    return Optional.ofNullable(invoices.get(id));
  }

  @Override
  public Collection<Invoice> findInvoicesByDateRange(LocalDate issuedDateFrom,
      LocalDate issuedDateTo) {
    logger.info("Trying to find invoices between {} and {}.", issuedDateFrom, issuedDateTo);
    Set<Invoice> result = new HashSet<>();
    for (Invoice invoice : invoices.values()) {
      if (issuedDateFrom != null && issuedDateTo == null) {
        if (issuedDateFrom.isBefore(invoice.getIssuedDate()) || issuedDateFrom
            .isEqual(invoice.getIssuedDate())) {
          result.add(invoice);
        }
      }
      if (issuedDateTo != null && issuedDateFrom == null) {
        if (issuedDateTo.isAfter(invoice.getIssuedDate()) || issuedDateTo
            .isEqual(invoice.getIssuedDate())) {
          result.add(invoice);
        }
      }
      if (issuedDateTo != null && issuedDateFrom != null) {
        if ((issuedDateTo.isAfter(invoice.getIssuedDate()) || issuedDateTo
            .isEqual(invoice.getIssuedDate())) && (issuedDateFrom.isBefore(invoice.getIssuedDate())
            || issuedDateFrom.isEqual(invoice.getIssuedDate()))) {
          result.add(invoice);
        }
      }
    }
    logger.info("Found {} invoices.", result.size());
    return result;
  }

  @Override
  public Collection<Invoice> getInvoices() {
    logger.info("Getting all invoices.");
    return invoices.values();
  }
}