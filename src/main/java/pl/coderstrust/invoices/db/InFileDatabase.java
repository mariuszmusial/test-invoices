package pl.coderstrust.invoices.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.coderstrust.invoices.db.util.InvoiceUtil;
import pl.coderstrust.invoices.exception.DatabaseException;
import pl.coderstrust.invoices.exception.InvoiceNotFoundException;
import pl.coderstrust.invoices.helper.FileHelper;
import pl.coderstrust.invoices.helper.InvoiceJsonMapper;
import pl.coderstrust.invoices.model.Invoice;

public class InFileDatabase implements Database {

  private static final Logger logger = LoggerFactory.getLogger(InFileDatabase.class);

  private File databaseFile;
  private File idFile;

  public InFileDatabase(String filePath, String idFilePath) throws DatabaseException {
    this.databaseFile = new File(filePath);
    if (!databaseFile.exists() || !databaseFile.isFile()) {
      String message = "Database file does not exist! Path: " + filePath;
      logger.error(message);
      throw new DatabaseException(message);
    }
    this.idFile = new File(idFilePath);
    if (!idFile.exists() || !idFile.isFile()) {
      String message = "ID file does not exist! Path: " + idFilePath;
      logger.error(message);
      throw new DatabaseException(message);
    }
  }

  @Override
  public Long saveInvoice(Invoice invoice) throws DatabaseException {
    logger.info("Saving invoice {} to file.", invoice);
    long newId;
    try {
      String invoiceJson = InvoiceJsonMapper.toJson(invoice);
      newId = getAndIncrementId();
      FileHelper.writeLineToFile(databaseFile, invoiceJson);
    } catch (Exception exception) {
      throw new DatabaseException(exception);
    }
    logger.info("Saved invoice with id {}.", newId);
    return newId;
  }

  private Long getAndIncrementId() throws FileNotFoundException {
    List<String> lines = FileHelper.readLinesFromFile(idFile);
    Long id = lines.isEmpty() ? -1L : Long.valueOf(lines.get(0));
    id++;
    FileHelper.writeLineToFile(idFile, String.valueOf(id), true);
    return id;
  }

  @Override
  public List<Invoice> getInvoices() throws DatabaseException {
    logger.info("Get invoice {} to file.");
    List<String> lines;
    List<Invoice> result = new ArrayList<>();
    try {
      lines = FileHelper.readLinesFromFile(databaseFile);
      for (String line : lines) {
        Invoice invoice = InvoiceJsonMapper.fromJson(line);
        result.add(invoice);
      }
    } catch (Exception exception) {
      throw new DatabaseException(exception);
    }
    logger.info("Get invoice with id {}.");
    return result;
  }

  @Override
  public void updateInvoice(Invoice invoice) throws DatabaseException, NullPointerException {
    logger.info("Updating invoice {} in file.", invoice);
    throwExceptionIfInvoiceNotFound(invoice.getId());
    Optional<Invoice> currentInvoice = getInvoiceById(invoice.getId());
    if (!currentInvoice.isPresent()) {
      String message = "Invoice with ID " + invoice.getId()
          + " that you are trying to update does not exist in the database.";
      logger.error(message);
      throw new DatabaseException(message);
    }
    List<Invoice> invoicesAfterUpdate = getInvoices().stream().filter(inv -> !inv.getId()
        .equals(invoice.getId())).collect(Collectors.toList());
    invoicesAfterUpdate.add(invoice);
    List<String> invoicesAsJson = mapInvoicesToJsons(invoicesAfterUpdate);
    FileHelper.writeLinesToFile(databaseFile, invoicesAsJson);
    logger.info("Update invoice with id {}", invoice);
  }

  private List<String> mapInvoicesToJsons(List<Invoice> invoicesAfterUpdate)
      throws DatabaseException {
    List<String> invoicesAsJson = new ArrayList<>();
    for (Invoice inv : invoicesAfterUpdate) {
      try {
        invoicesAsJson.add(InvoiceJsonMapper.toJson(inv));
      } catch (JsonProcessingException jsonProcessingException) {
        throw new DatabaseException(jsonProcessingException);
      }
    }
    return invoicesAsJson;
  }

  public Collection<Invoice> findInvoicesByDateRange(LocalDate startDate, LocalDate endDate)
      throws DatabaseException {
    logger.info("Find invoice between {} and {}. ", startDate, endDate);
    return InvoiceUtil.getInvoicesByDateRange(startDate, endDate, getInvoices());
  }

  @Override
  public void deleteInvoice(Long id) throws DatabaseException {
    logger.info("Deleting invoice with id {} from file.", id);
    throwExceptionIfInvoiceNotFound(id);
    List<Invoice> invoices = getInvoices().stream().filter(inv -> !inv.getId()
        .equals(id)).collect(Collectors.toList());
    List<String> invoicesAsJson = mapInvoicesToJsons(invoices);
    FileHelper.writeLinesToFile(databaseFile, invoicesAsJson);
  }

  @Override
  public Optional<Invoice> getInvoiceById(Long id) throws DatabaseException {
    List<String> lines;
    try {
      lines = FileHelper.readLinesFromFile(databaseFile);
    } catch (FileNotFoundException fileNotFoundException) {
      throw new DatabaseException(fileNotFoundException);
    }
    List<String> foundInvoices = lines.stream()
        .filter(line -> line.startsWith("{\"id\":" + id + ","))
        .collect(Collectors.toList());
    if (foundInvoices.size() > 1) {
      String message = "Found more than one invoice with given ID.";
      logger.error(message);
      throw new DatabaseException(message);
    }
    if (foundInvoices.size() == 0) {
      return Optional.empty();
    }
    try {
      return Optional.of(InvoiceJsonMapper.fromJson(foundInvoices.get(0)));
    } catch (IOException exception) {
      throw new DatabaseException(exception);
    }
  }

  private void throwExceptionIfInvoiceNotFound(Long id) throws DatabaseException {
    if (!getInvoiceById(id).isPresent()) {
      String message = "Invoice not in database.";
      logger.error(message);
      throw new InvoiceNotFoundException(message);
    }
  }
}