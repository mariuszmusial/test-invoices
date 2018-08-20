package pl.coderstrust.invoices.helper;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import pl.coderstrust.invoices.model.Invoice;

class InvoiceJsonMapperTest {

  @Test
  void shouldConvertEmptyInvoiceToJson() throws JsonProcessingException {
    //given
    Invoice invoice = TestData.emptyInvoice();

    //when
    String result = InvoiceJsonMapper.toJson(invoice);

    //then
    assertThat(result, is("{\"id\":0}"));
  }

  @Test
  void shouldConvertInvoiceWithEverythingToJson() throws JsonProcessingException {
    //given
    Invoice invoice = TestData.sampleInvoice();

    //when
    String result = InvoiceJsonMapper.toJson(invoice);

    //then
    assertThat(result, is(TestData.sampleInvoiceAsString()));
  }

  @Test
  void shouldThrowExceptionWhenPassedNullInvoice() {
    assertThrows(IllegalArgumentException.class, () -> InvoiceJsonMapper.toJson(null));
  }

  @Test
  void shouldConvertJsonWithEverythingToInvoice() throws IOException {
    //given
    String json = TestData.sampleInvoiceAsString();

    //when
    Invoice result = InvoiceJsonMapper.fromJson(json);

    //then
    assertThat(result, is(TestData.sampleInvoice()));
  }

  @Test
  void shouldThrowExceptionWhenPassedNullJson() {
    assertThrows(IllegalArgumentException.class, () -> InvoiceJsonMapper.fromJson(null));
  }

  @Test
  void shouldThrowExceptionWhenPassedRandomString() {
    assertThrows(JsonParseException.class, () -> InvoiceJsonMapper.fromJson("tererbnfjd ff r"));
  }
}