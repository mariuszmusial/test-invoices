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
import pl.coderstrust.invoices.db.util.InvoiceUtil;
import pl.coderstrust.invoices.exception.DatabaseException;
import pl.coderstrust.invoices.helper.FileHelper;
import pl.coderstrust.invoices.helper.InvoiceJsonMapper;
import pl.coderstrust.invoices.model.Invoice;

public class InFileDatabase implements Database {

  private File databaseFile;
  private File idFile;

  public InFileDatabase(String filePath, String idFilePath) throws DatabaseException {
    this.databaseFile = new File(filePath);
    if (!databaseFile.exists() || !databaseFile.isFile()) {
      throw new DatabaseException("Database file does not exist! Path: " + filePath);
    }
    this.idFile = new File(idFilePath);
    if (!idFile.exists() || !idFile.isFile()) {
      throw new DatabaseException("ID file does not exist! Path: " + idFilePath);
    }
  }

  // testy:
  // 1. zapisujesz jednego przykladowego Invoice'a, sprawdzasz czy zostal zapisany
  // 2. zapisujesz kilka invoiceow, sprawdzasz, czy plik z ID zawiera odpowiednie ID, czy sie zwieksza
  // 3. Co jesli invoice jest nullem
  @Override
  public Long saveInvoice(Invoice invoice) throws DatabaseException {
    long newId;
    try {
      String invoiceJson = InvoiceJsonMapper.toJson(invoice);
      newId = getAndIncrementId();
      FileHelper.writeLineToFile(databaseFile, invoiceJson);
    } catch (Exception e) {
      throw new DatabaseException(e);
    }
    return newId;
  }

  //testy:
  // 1. Zadne, bo to jest metoda prywatna
  private Long getAndIncrementId() throws FileNotFoundException {
    List<String> lines = FileHelper.readLinesFromFile(idFile);
    Long id = lines.isEmpty() ? -1L : Long.valueOf(lines.get(0));
    id++;
    FileHelper.writeLineToFile(idFile, String.valueOf(id), true);
    return id;
  }

  // testy:
  // 1. Przygotowujesz plik z invoice'ami w formacie JSON, wolasz te metode i sprwadzasz czy zwrocila Ci wszystkie te invoice'y z pliku
  // 2. Co zwroci metoda gdy w bazie nie bedzie zadnego invoice'a
  // 3. Co zwroci metoda, gdy ktorys invoice (ktoras linijka w pliku) bedzie zle sformatowana
  @Override
  public List<Invoice> getInvoices() throws DatabaseException {
    List<String> lines;
    List<Invoice> result = new ArrayList<>();
    try {
      lines = FileHelper.readLinesFromFile(databaseFile);
      for (String line : lines) {
        Invoice invoice = InvoiceJsonMapper.fromJson(line);
        result.add(invoice);
      }
    } catch (Exception e) {
      throw new DatabaseException(e);
    }
    return result;
  }

  // testy:
  // 1. Co gdy ktos probuje zrobic update invoice'a, a nie podał ID
  // 2. Sprawdz czy na pewno ten invoice sie zaktualizowal (najlepiej zmienic wszystkie pola w nim)
  // 3. Powinien rzucic wyjatkiem gdy probuje sie update'owac invoice'a ktorego nei ma w bazie
  @Override
  public void updateInvoice(Invoice invoice) throws DatabaseException, NullPointerException {
    Optional<Invoice> currentInvoice = null;
    currentInvoice = getInvoiceById(invoice.getId());
    if (!currentInvoice.isPresent()) {
      throw new DatabaseException("Invoice with given ID does not exist.");
    }
    List<Invoice> invoicesAfterUpdate = getInvoices().stream().filter(inv -> !inv.getId()
        .equals(invoice.getId())).collect(Collectors.toList());
    invoicesAfterUpdate.add(invoice);
    List<String> invoicesAsJson = mapInvoicesToJsons(invoicesAfterUpdate);
    FileHelper.writeLinesToFile(databaseFile, invoicesAsJson);
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

  // testy:
  // 1. Przygotuj baze danych z kilkoma Invoice'ami, zeby mialy rozne daty
  // i sprawdz czy znajdzie dobrze invoice'y miedzy danymi datami
  // 2. Jesli nie poda sie startDate, to powinien zwrocic wszystkie az do endDate
  // 3. Jesli nie poda sie endDate, to powinien zwrocic wszystkie od startDate (np. jak podalem 01-01-2018, null, to powinien znalezc mi wszystkie invoice'y z tego roku)
  @Override
  public Collection<Invoice> findInvoicesByDateRange(LocalDate startDate, LocalDate endDate)
      throws DatabaseException {
    return InvoiceUtil.getInvoicesByDateRange(startDate, endDate, getInvoices());
  }

  // testy
  // 1. Nie powinien usunac niczego jesli sie poda ID ktorego nie ma w bazie
  // 2. Powinien usunac rekord z bazy o zadanym ID
  @Override
  public void deleteInvoice(Long id) throws DatabaseException {
    List<Invoice> invoices = getInvoices().stream().filter(inv -> !inv.getId()
        .equals(id)).collect(Collectors.toList());
    List<String> invoicesAsJson = mapInvoicesToJsons(invoices);
    FileHelper.writeLinesToFile(databaseFile, invoicesAsJson);
  }

  // testy
  // 1. Powinien zwrocic invoice znaleziony z danym ID
  // 2. Powinien zwrocic pustego optionala jesli dnaego ID nie ma w bazie
  @Override
  public Optional<Invoice> getInvoiceById(Long id) throws DatabaseException {
    List<String> lines; // regex101.com
    try {
      lines = FileHelper.readLinesFromFile(databaseFile);
    } catch (FileNotFoundException fileNotFoundException) {
      throw new DatabaseException(fileNotFoundException);
    }
    List<String> foundInvoices = lines.stream().filter(line -> line.startsWith("(\"id\":" + id))
        .filter(line -> line.matches("$(\"id\":" + id + "\\s")).collect(Collectors.toList());
    if (foundInvoices.size() > 1) {
      throw new DatabaseException("Found more than one invoice with given ID.");
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
}