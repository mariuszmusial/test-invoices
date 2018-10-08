package pl.coderstrust.invoices.rest;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderstrust.invoices.exception.DatabaseException;
import pl.coderstrust.invoices.logic.InvoiceService;
import pl.coderstrust.invoices.model.Invoice;
import pl.coderstrust.invoices.validator.Validator;


@RestController
@RequestMapping("/invoices")
public class InvoicesController {

  private Validator<Invoice> invoiceValidator;

  private InvoiceService invoiceService;

  @Autowired
  public void setInvoiceService(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }

  @PostMapping("/add")
  public Long saveInvoice(@RequestBody Invoice invoice) throws DatabaseException {
    return invoiceService.saveInvoice(invoice);
  }

  @Autowired
  public void setInvoiceValidator(
      Validator<Invoice> invoiceValidator) {
    this.invoiceValidator = invoiceValidator;
  }

//  @PostMapping("/add")
//  public Long saveInvoice(@RequestBody Invoice invoice) throws DatabaseException {
//    Collection<String> validateMessages = invoiceValidator.validate(invoice);
//    if (validateMessages.isEmpty()) {
//      return invoiceService.saveInvoice(invoice);
//    } else {
//
//    }
//    return validateMessages
//    // jesli validator zwrocil bledy, to je wywal w tym momencie, czyli przeczytaj jak to powinno byc zrobione :)
//  }

  @GetMapping
  public Collection<Invoice> getAllInvoices() {
    return invoiceService.getInvoices();
  }

  @GetMapping("/{id}")
  public Optional<Invoice> getInvoiceById(@PathVariable(name = "id") Long id) {
    return invoiceService.getInvoiceById(id);
  }

  @PutMapping("/{id}")
  public int updateInvoice(@PathVariable(name = "id") Invoice invoice) throws DatabaseException {
    return invoiceService.updateInvoice(invoice);
  }

  @GetMapping("/{dateFrom}/{dateTo}")
  public Collection<Invoice> findInvoicesByDateRange(LocalDate startDate, LocalDate endDate) {
    return invoiceService.findInvoicesByDateRange(startDate, endDate);
  }

  @DeleteMapping("/{id}")
  public void deleteInvoice(@PathVariable("id") Long id) throws DatabaseException, IOException {
    invoiceService.deleteInvoice(id);
  }
}

// jesli jest ok to zapisuje to przez uzycie invoiceService to zwracam ResponseEntity.ok
// jest walidator zwroci jakies bledy to zwracam ResponseEntity.badRequest()