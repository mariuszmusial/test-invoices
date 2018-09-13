package pl.coderstrust.invoices.db.memory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.coderstrust.invoices.helper.TestData;
import pl.coderstrust.invoices.model.Invoice;

class InMemoryDatabaseTest {

  private InMemoryDatabase database;

  @BeforeEach
  void init() {
    database = new InMemoryDatabase();
  }

  @Test
  void shouldIncrementIdWhenInvoiceWithNullIdIsPassed() {
    //given
    Invoice invoice = TestData.sampleInvoice();

    //when
    database.saveInvoice(invoice);
    database.saveInvoice(invoice);
    database.saveInvoice(invoice);

    //then
    assertNotNull(database.getInvoiceById(1L));
    assertNotNull(database.getInvoiceById(2L));
    assertNotNull(database.getInvoiceById(3L));
  }

  @Test
  void shouldSaveSampleInvoices() {
    //given
    Invoice invoice1 = TestData.sampleInvoice(1L);
    Invoice invoice2 = TestData.sampleInvoice(2L);

    //when
    database.saveInvoice(invoice1);
    database.saveInvoice(invoice2);

    //then
    assertThat(database.getInvoiceById(1L).get().getIdentifier(), is(invoice1.getIdentifier()));
    assertThat(database.getInvoiceById(2L).get().getIdentifier(), is(invoice2.getIdentifier()));
  }

  @Test
  void shouldDeleteSampleInvoices() {
    //given
    Invoice invoice1 = TestData.sampleInvoice(1L);
    Invoice invoice2 = TestData.sampleInvoice(2L);
    Invoice invoice3 = TestData.sampleInvoice(3L);

    //when
    database.saveInvoice(invoice1);
    database.saveInvoice(invoice2);
    database.saveInvoice(invoice3);
    database.deleteInvoice(1L);
    database.deleteInvoice(2L);

    //then
    assertEquals(Optional.empty(), database.getInvoiceById(1L));
    assertEquals(Optional.empty(), database.getInvoiceById(2L));
    assertNotNull(database.getInvoiceById(3L));
  }


  @Test
  void shouldThrowExceptionWhenTryingToUpdateWithNullId() {
    //given
    Invoice invoice = TestData.emptyInvoice();

    //when
    assertThrows(IllegalArgumentException.class, () -> database.updateInvoice(invoice));
  }

  @Test
  void shouldThrowExceptionWhenTryingToUpdateInvoiceThatDoesNotExist() {
    //given
    Invoice invoice = TestData.sampleInvoice();

    //when
    assertThrows(IllegalArgumentException.class, () -> database.updateInvoice(invoice));
  }

  @Test
  void shouldUpdateInvoice() {
    //given
    long id = 1L;
    Invoice invoice = TestData.sampleInvoice(id);
    String newIdentifier = "ABC";
    LocalDate newDate = LocalDate.now();
    Invoice updatedInvoice = new Invoice(id, newIdentifier, newDate,
        TestData.sampleCompany(), TestData.sampleCompany2(),
        TestData.sampleInvoiceEntries());

    //when
    database.saveInvoice(invoice);
    database.updateInvoice(updatedInvoice);

    //then
    Optional<Invoice> actual = database.getInvoiceById(id);
    assertThat(actual.get().getIdentifier(), is(newIdentifier));
    assertThat(actual.get().getIssuedDate(), is(newDate));
  }

  @Test
  void shouldFindInvoiceByDateRange() {
    //given
    Invoice sampleInvoice = TestData.sampleInvoice(1L);
    database.saveInvoice(sampleInvoice);
    database.saveInvoice(new Invoice(50L, "test", sampleInvoice.getIssuedDate().minusDays(2),
        TestData.sampleCompany(), TestData.sampleCompany2(),
        TestData.sampleInvoiceEntries()));

    //when
    Collection<Invoice> result = database
        .findInvoicesByDateRange(sampleInvoice.getIssuedDate().minusDays(1),
            sampleInvoice.getIssuedDate().plusDays(1));

    //then
    assertNotNull(result);
    assertFalse(result.isEmpty());
    Invoice actual = result.iterator().next();
    assertThat(actual.getId(), is(1L));
    assertThat(actual.getIdentifier(), is(sampleInvoice.getIdentifier()));
  }

  @Test
  void shouldFindInvoiceByIssuedDateFrom() {
    //given
    Invoice sampleInvoice = TestData.sampleInvoice(1L);
    database.saveInvoice(sampleInvoice);
    database.saveInvoice(new Invoice(50L, "test", sampleInvoice.getIssuedDate().minusDays(2),
        TestData.sampleCompany(), TestData.sampleCompany2(),
        TestData.sampleInvoiceEntries()));

    //when
    Collection<Invoice> result = database
        .findInvoicesByDateRange(sampleInvoice.getIssuedDate().minusDays(1),
            null);

    //then
    assertNotNull(result);
    assertFalse(result.isEmpty());
    Invoice actual = result.iterator().next();
    assertThat(actual.getId(), is(1L));
    assertThat(actual.getIdentifier(), is(sampleInvoice.getIdentifier()));
  }

  @Test
  void shouldFindInvoiceByIssuedDateTo() {
    //given
    Invoice sampleInvoice = TestData.sampleInvoice(1L);
    database.saveInvoice(sampleInvoice);
    database.saveInvoice(new Invoice(50L, "test", sampleInvoice.getIssuedDate().plusDays(2),
        TestData.sampleCompany(), TestData.sampleCompany2(),
        TestData.sampleInvoiceEntries()));

    //when
    Collection<Invoice> result = database.findInvoicesByDateRange(null,
        sampleInvoice.getIssuedDate().plusDays(1));

    //then
    assertNotNull(result);
    assertFalse(result.isEmpty());
    Invoice actual = result.iterator().next();
    assertThat(actual.getId(), is(1L));
  }

  @Test
  void shouldGetAllInvoices() {
    //given
    Invoice sampleInvoice = TestData.sampleInvoice(1L);
    database.saveInvoice(sampleInvoice);
    database.saveInvoice(TestData.sampleInvoice(2L));
    database.saveInvoice(TestData.sampleInvoice(3L));

    //when
    Collection<Invoice> result = database.getInvoices();

    //then
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertThat(result.size(), is(3));
    Invoice actual = result.iterator().next();
    assertThat(actual.getId(), is(1L));
    assertThat(actual.getIdentifier(), is(sampleInvoice.getIdentifier()));
  }
}