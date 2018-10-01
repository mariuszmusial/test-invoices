package pl.coderstrust.invoices.db.memory;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Repository;
import pl.coderstrust.invoices.db.Database;
import pl.coderstrust.invoices.exception.DatabaseException;
import pl.coderstrust.invoices.model.Invoice;

@Repository
public class InMemoryDatabase implements Database {

  private final Map<Long, Invoice> invoices = new HashMap<>();
  private long id = 0;
  private static final String WRONG_ID_MESSAGE = "The invoice with given ID does not exist: ";

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
      throw new DatabaseException(
          WRONG_ID_MESSAGE + invoice.getId());
    }
    invoices.put(invoice.getId(), invoice);
  }

  @Override
  public void deleteInvoice(Long id) {
    invoices.remove(id);
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