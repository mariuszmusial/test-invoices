package pl.coderstrust.invoices.db.hibernate;

import org.springframework.data.repository.CrudRepository;
import pl.coderstrust.invoices.model.Invoice;

public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

}
