package pl.coderstrust.invoices.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pl.coderstrust.invoices.helper.TestData;
import pl.coderstrust.invoices.model.Company;
import pl.coderstrust.invoices.model.Invoice;
import pl.coderstrust.invoices.model.InvoiceEntry;

class InvoiceValidatorTest {

  @Mock
  private CompanyValidator companyValidatorMock;

  @Mock
  private InvoiceEntryValidator invoiceEntryValidatorMock;

  private InvoiceValidator invoiceValidator;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    invoiceValidator = new InvoiceValidator(invoiceEntryValidatorMock, companyValidatorMock);
  }

  @Test
  void shouldCheckIfIdFieldGreaterThenOrEqualZero() {

    Invoice testInvoice = new Invoice(-9L, "Sample Identifier",
        LocalDate.of(2017, Month.JANUARY, 1),
        TestData.sampleCompany(), TestData.sampleCompany2(),
        TestData.sampleInvoiceEntries());

    Collection<String> result = invoiceValidator
        .validateInvoiceForUpdate(testInvoice);

    assertNotNull(result);
    assertEquals(1, result.size());
    assertTrue(result.iterator().next().contains("Id"));
  }

  @Test
  void shouldCheckIfIdFieldIsNotNull() {

    Invoice testInvoice = new Invoice(null, "Sample Identifier",
        LocalDate.of(2017, Month.JANUARY, 1),
        TestData.sampleCompany(), TestData.sampleCompany2(),
        TestData.sampleInvoiceEntries());

    Collection<String> result = invoiceValidator
        .validateInvoiceForUpdate(testInvoice);

    assertNotNull(result);
    assertEquals(1, result.size());
    assertTrue(result.iterator().next().contains("Id"));
  }

  @Test
  void shouldCheckIfIssueDateFieldIsNotLaterThanToday() {

    Invoice testInvoice = new Invoice(9L, "Sample Identifier", LocalDate.of(2019, Month.JANUARY, 1),
        TestData.sampleCompany(), TestData.sampleCompany2(),
        TestData.sampleInvoiceEntries());

    Collection<String> result = invoiceValidator
        .validateInvoiceForUpdate(testInvoice);

    assertNotNull(result);
    assertEquals(1, result.size());
    assertTrue(result.iterator().next().contains("date"));
  }

  @Test
  void shouldCheckIfIssueDateFieldIsNotNull() {

    Invoice testInvoice = new Invoice(9L, "Sample Identifier", null,
        TestData.sampleCompany(), TestData.sampleCompany2(),
        TestData.sampleInvoiceEntries());

    Collection<String> result = invoiceValidator
        .validateInvoiceForUpdate(testInvoice);

    assertNotNull(result);
    assertEquals(1, result.size());
    assertTrue(result.iterator().next().contains("date"));
  }

  @Test
  void shouldCheckIfIdentifierFieldIsNotNull() {

    Invoice testInvoice = new Invoice(9L, null, LocalDate.of(2017, Month.JANUARY, 1),
        TestData.sampleCompany(), TestData.sampleCompany2(),
        TestData.sampleInvoiceEntries());

    Collection<String> result = invoiceValidator
        .validateInvoiceForSave(testInvoice);

    assertNotNull(result);
    assertEquals(1, result.size());
    assertTrue(result.iterator().next().contains("identifier"));
  }

  @Test
  void shouldCheckIfSellerFieldIsNotNull() {

    Invoice testInvoice = new Invoice(9L, "Sample Identifier", LocalDate.of(2017, Month.JANUARY, 1),
        null, TestData.sampleCompany2(), TestData.sampleInvoiceEntries());

    Collection<String> result = invoiceValidator
        .validateInvoiceForSave(testInvoice);

    assertNotNull(result);
    assertEquals(1, result.size());
    assertTrue(result.iterator().next().contains("seller"));
  }

  @Test
  void shouldCheckIfBuyerFieldIsNotNull() {

    Invoice testInvoice = new Invoice(9L, "Sample Identifier", LocalDate.of(2017, Month.JANUARY, 1),
        TestData.sampleCompany(), null, TestData.sampleInvoiceEntries());

    Collection<String> result = invoiceValidator
        .validateInvoiceForUpdate(testInvoice);

    assertNotNull(result);
    assertEquals(1, result.size());
    assertTrue(result.iterator().next().contains("buyer"));
  }

  @Test
  void shouldCheckIfEntriesFieldIsNotNull() {

    Invoice testInvoice = new Invoice(9L, "Sample Identifier", LocalDate.of(2017, Month.JANUARY, 1),
        TestData.sampleCompany(), TestData.sampleCompany2(), null);

    Collection<String> result = invoiceValidator
        .validateInvoiceForUpdate(testInvoice);

    assertNotNull(result);
    assertEquals(1, result.size());
    assertTrue(result.iterator().next().contains("entries"));
  }

  @Test
  void shouldReturnMultipleValidationExceptions() {
    Invoice testInvoice = new Invoice(null, null, null, null, null, null);

    Collection<String> result = invoiceValidator
        .validateInvoiceForUpdate(testInvoice);

    assertNotNull(result);
    assertEquals(6, result.size());
  }

  @Test
  void shouldValidateCorrectInvoice() {
    Invoice testInvoice = TestData.sampleInvoice();

    Collection<String> result = invoiceValidator
        .validateInvoiceForUpdate(testInvoice);

    assertNotNull(result);
    assertEquals(0, result.size());
  }

  @Test
  void shouldValidateBuyerAndSeller() {
    Company sampleBuyer = TestData.sampleCompany();

    List<String> buyerValidationExceptions = new ArrayList<>();
    buyerValidationExceptions.add("TEST_BUYER");
    when(companyValidatorMock.validate(sampleBuyer)).thenReturn(buyerValidationExceptions);

    Company sampleSeller = TestData.sampleCompany2();

    List<String> sellerValidationExcetpions = new ArrayList<>();
    sellerValidationExcetpions.add("TEST_SELLER");
    when(companyValidatorMock.validate(sampleSeller)).thenReturn(sellerValidationExcetpions);

    Invoice testInvoice = new Invoice(9L, "Sample Identifier", LocalDate.of(2017, Month.JANUARY, 1),
        sampleBuyer, sampleSeller, TestData.sampleInvoiceEntries());

    Collection<String> result = invoiceValidator
        .validateInvoiceForUpdate(testInvoice);

    assertNotNull(result);
    Mockito.verify(companyValidatorMock).validate(sampleBuyer);
    Mockito.verify(companyValidatorMock).validate(sampleSeller);
    assertEquals(2, result.size());
  }

  @Test
  void shouldValidateInvoiceEntries() {
    List<InvoiceEntry> sampleListOfEntries = TestData.sampleInvoiceEntries();
    Invoice testInvoice = new Invoice(9L, "Sample Identifier", LocalDate.of(2017, Month.JANUARY, 1),
        TestData.sampleCompany(), TestData.sampleCompany2(),
        sampleListOfEntries);

    List<String> validationExceptions = new ArrayList<>();
    validationExceptions.add("TEST_BUYER");
    when(invoiceEntryValidatorMock.validate(sampleListOfEntries.get(0)))
        .thenReturn(validationExceptions);

    Collection<String> result = invoiceValidator
        .validateInvoiceForUpdate(testInvoice);

    assertNotNull(result);
    assertEquals(1, result.size());
    Mockito.verify(invoiceEntryValidatorMock).validate(sampleListOfEntries.get(0));
    assertTrue(result.iterator().next().contains("entry"));
  }
}