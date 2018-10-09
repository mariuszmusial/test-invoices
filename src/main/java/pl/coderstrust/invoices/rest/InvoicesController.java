package pl.coderstrust.invoices.rest;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
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
import pl.coderstrust.invoices.validator.InvoiceValidator;


@RestController
@RequestMapping("/invoices")
public class InvoicesController {

  private InvoiceValidator invoiceValidator;

  private InvoiceService invoiceService;

  @Autowired
  public void setInvoiceService(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }

  @Autowired
  public void setInvoiceValidator(
      InvoiceValidator invoiceValidator) {
    this.invoiceValidator = invoiceValidator;
  }

  @PostMapping("/add")
  public ResponseEntity<?> saveInvoice(@RequestBody Invoice invoice) throws DatabaseException {
    Collection<String> validateMessages = invoiceValidator.validateInvoiceForSave(invoice);
    if (validateMessages.isEmpty()) {
      return ResponseEntity.ok(invoiceService.saveInvoice(invoice));
    } else {
      return ResponseEntity.badRequest().body(validateMessages);
    }
  }

  @GetMapping
  public Collection<Invoice> getAllInvoices() throws DatabaseException {
    return invoiceService.getInvoices();
  }

  @GetMapping("/{id}")
  public Optional<Invoice> getInvoiceById(@PathVariable(name = "id") Long id)
      throws DatabaseException {
    // jesli znajdziesz to zwroc ResponseEntity.ok
    // jesli nie znajdziesz to zwroc RepsonseEntity.notFound().build()
    return invoiceService.getInvoiceById(id);
  }

  @PutMapping("/{id}")
  public void updateInvoice(@PathVariable(name = "id") Invoice invoice) throws DatabaseException {
    invoiceService.updateInvoice(invoice);
  }

  @GetMapping("/{dateFrom}/{dateTo}")
  public Collection<Invoice> findInvoicesByDateRange(
      @PathVariable("dateFrom") @DateTimeFormat(iso = ISO.DATE) LocalDate startDate,
      @PathVariable("dateTo") @DateTimeFormat(iso = ISO.DATE) LocalDate endDate)
      throws DatabaseException {
    return invoiceService.findInvoicesByDateRange(startDate, endDate);
  }

  @DeleteMapping("/{id}")
  public void deleteInvoice(@PathVariable("id") Long id) throws DatabaseException, IOException {
    invoiceService.deleteInvoice(id);
  }
}