package pl.coderstrust.invoices.db.memory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import pl.coderstrust.invoices.db.Database;
import pl.coderstrust.invoices.exception.DatabaseException;
import pl.coderstrust.invoices.exception.InvoiceNotFoundException;
import pl.coderstrust.invoices.model.Invoice;

@Repository
public class InMemoryDatabase implements Database {

  private static final Logger logger = LoggerFactory.getLogger(InMemoryDatabase.class);

  private final Map<Long, Invoice> invoices = new HashMap<>();
  private long id = 0;
  private static final String WRONG_ID_MESSAGE = "The invoice with given ID does not exist: ";
  private List<Invoice> invoiceList;

  private InMemoryDatabase() {
    this.invoiceList = new ArrayList<>();
  }

  @Override
  public Long saveInvoice(Invoice invoice) {
    invoices
        .put(++id, new Invoice(id, invoice.getIdentifier(), invoice.getIssuedDate(),
            invoice.getBuyer(), invoice.getSeller(), invoice.getEntries()));
    return id;
  }

  @Override
  public void updateInvoice(Invoice invoice) throws DatabaseException {
    if (!invoices.containsKey(invoice.getId())) {
      logger.error("Invoice {} not found.", invoice);
      throw new InvoiceNotFoundException("Invoice not in database.");
    }
    invoices.put(invoice.getId(), invoice);
  }

  @Override
  public void deleteInvoice(Long id) throws InvoiceNotFoundException {
    if (getInvoices().stream()
        .noneMatch(inv -> inv.getId().equals(id))) {
      logger.error("Invoice with id {} not found.", id);
      throw new InvoiceNotFoundException("Invoice not in database.");
    }
    invoiceList.remove(getInvoiceById(id));
  }


  @Override
  public Optional<Invoice> getInvoiceById(Long id) {
    return Optional.ofNullable(invoices.get(id));
  }

  @Override
  public Collection<Invoice> findInvoicesByDateRange(LocalDate issuedDateFrom,
      LocalDate issuedDateTo) {
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
    return result;
  }

  @Override
  public Collection<Invoice> getInvoices() {
    return invoices.values();
  }
}