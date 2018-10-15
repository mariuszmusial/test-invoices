package pl.coderstrust.invoices.rest;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
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

  private static final Logger logger = LoggerFactory.getLogger(InvoicesController.class);
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
  public ResponseEntity<?> saveInvoice(@RequestBody Invoice invoice) {
    logger.info("Trying to save invoice");
    Collection<String> validationMessages = invoiceValidator.validateInvoiceForSave(invoice);
    if (validationMessages.isEmpty()) {
      try {
        logger.info("Saved invoice");
        return ResponseEntity.ok(invoiceService.saveInvoice(invoice));
      } catch (DatabaseException databaseException) {
        String message =
            "There was an error while saving the invoice. Message: " + databaseException
                .getMessage();
        logger.error(message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
      }
    } else {
      logger.error("There were validation errors " + validationMessages);
      return ResponseEntity.badRequest().body(validationMessages);
    }
  }

  @GetMapping
  public Collection<Invoice> getAllInvoices() throws DatabaseException {
    logger.info("Getting all invoices");
    return invoiceService.getInvoices();
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getInvoiceById(@PathVariable(name = "id") Long id) {
    logger.info("Trying to get invoice with ID " + id);
    Optional<Invoice> invoice = null;
    try {
      invoice = invoiceService.getInvoiceById(id);
    } catch (DatabaseException databaseException) {
      String message = "There was an error while getting the invoice. Message: " + databaseException
          .getMessage();
      logger.error(message);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
          message);
    }
    if (invoice.isPresent()) {
      logger.info("Invoice found");
      return ResponseEntity.ok(invoice);

    } else {
      logger.warn("Invoice not found");
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateInvoice(@PathVariable(name = "id", required = true) Long id,
      @RequestBody Invoice invoice) {
    logger.info("Trying to update invoice with ID " + id);
    Collection<String> validationMessages = invoiceValidator.validateInvoiceForUpdate(invoice);
    if (!validationMessages.isEmpty()) {
      logger.error("There were validations errors: " + validationMessages);
      return ResponseEntity.badRequest().body(validationMessages);
    }
    try {
      if (invoiceService.getInvoiceById(id) == null) {
        logger.error("Invoice was not found");
        return ResponseEntity.notFound().build();
      }
      invoiceService.updateInvoice(invoice);
      logger.info("Invoice updated");
      return ResponseEntity.ok().build();
    } catch (DatabaseException databaseException) {
      String message =
          "There was an error while updating the invoice. Message: " + databaseException
              .getMessage();
      logger.error(message);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
          message);
    }
  }

  @GetMapping("/{dateFrom}/{dateTo}")
  public Collection<Invoice> findInvoicesByDateRange(
      @PathVariable("dateFrom") @DateTimeFormat(iso = ISO.DATE) LocalDate startDate,
      @PathVariable("dateTo") @DateTimeFormat(iso = ISO.DATE) LocalDate endDate)
      throws DatabaseException {
    logger.info("Finding invoices between " + startDate + " and " + endDate);
    return invoiceService.findInvoicesByDateRange(startDate, endDate);
  }

  @DeleteMapping("/{id}")
  public void deleteInvoice(@PathVariable("id") Long id) throws DatabaseException {
    logger.info("Deleting invoice with ID " + id);
    invoiceService.deleteInvoice(id);
  }
}