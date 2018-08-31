package pl.coderstrust.invoices.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.coderstrust.invoices.model.InvoiceEntry;
import pl.coderstrust.invoices.model.Vat;

class InvoiceEntryValidatorTest {

  private InvoiceEntryValidator invoiceEntryValidator;

  @BeforeEach
  void setUp() {
    invoiceEntryValidator = new InvoiceEntryValidator();
  }

  @Test
  void shouldValidateCorrectInvoiceEntry() {
    InvoiceEntry testInvoiceEntry = new InvoiceEntry("A product", 1, BigDecimal.TEN, Vat.VAT_5);

    Collection<String> result = invoiceEntryValidator.validate(testInvoiceEntry);

    assertNotNull(result);
    assertEquals(0, result.size());
  }


  @ParameterizedTest
  @MethodSource("incorrectInvoiceEntries")
  void shouldValidateIncorrectInvoiceEntries(InvoiceEntry invoiceEntry,
      int expectedExceptionsCount, String expectedMessageContains) {

    Collection<String> result = invoiceEntryValidator.validate(invoiceEntry);

    assertNotNull(result);
    assertEquals(expectedExceptionsCount, result.size());
    assertTrue(result.iterator().next().contains(expectedMessageContains));
  }

  private static Stream<Arguments> incorrectInvoiceEntries() {
    return Stream.of(
        Arguments.of(new InvoiceEntry(null, 1, BigDecimal.TEN, Vat.VAT_23), 1, "product name"),
        Arguments.of(new InvoiceEntry("", 1, BigDecimal.TEN, Vat.VAT_23), 1, "product name"),
        Arguments.of(new InvoiceEntry("A product", null, BigDecimal.TEN, Vat.VAT_23), 1, "amount"),
        Arguments.of(new InvoiceEntry("A product", 0, BigDecimal.TEN, Vat.VAT_23), 1, "amount"),
        Arguments.of(new InvoiceEntry("A product", -120, BigDecimal.TEN, Vat.VAT_23), 1, "amount"),
        Arguments.of(new InvoiceEntry("A product", 20, null, Vat.VAT_23), 1, "price"),
        Arguments.of(new InvoiceEntry("A product", 20, BigDecimal.ZERO, Vat.VAT_23), 1, "price"),
        Arguments.of(new InvoiceEntry("A product", 20, BigDecimal.valueOf(-123L), Vat.VAT_23), 1,
            "price"),
        Arguments.of(new InvoiceEntry("A product", 20, BigDecimal.valueOf(10.5), null), 1, "vat"),
        Arguments
            .of(new InvoiceEntry(null, -20, BigDecimal.valueOf(-10.5), null), 4, "product name")
    );
  }
}
