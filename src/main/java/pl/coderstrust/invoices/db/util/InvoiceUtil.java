package pl.coderstrust.invoices.db.util;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import pl.coderstrust.invoices.model.Invoice;

public class InvoiceUtil {

  public static Collection<Invoice> getInvoicesByDateRange(LocalDate issuedDateFrom,
      LocalDate issuedDateTo, Collection<Invoice> invoices) {
    Set<Invoice> result = new HashSet<>();
    for (Invoice invoice : invoices) {
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

}
