package pl.coderstrust.invoices.helper;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import org.junit.Test;
import pl.coderstrust.invoices.model.Invoice;

public class InvoiceJsonMapperTest {

  @Test
  public void shouldConvertEmptyInvoiceToJson() throws JsonProcessingException {
    //given
    Invoice invoice = TestData.emptyInvoice();

    //when
    String result = InvoiceJsonMapper.toJson(invoice);

    //then
    assertThat(result, is("{\"id\":0}"));
  }

  @Test
  public void shouldConvertInvoiceWithEverythingToJson() throws JsonProcessingException {
    //given
    Invoice invoice = TestData.sampleInvoice();

    //when
    String result = InvoiceJsonMapper.toJson(invoice);

    //then
    assertThat(result, is(TestData.sampleInvoiceAsString()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionWhenPassedNullInvoice() throws JsonProcessingException {
    InvoiceJsonMapper.toJson(null);
  }

  @Test
  public void shouldConvertJsonWithEverythingToInvoice() throws IOException {
    //given
    String json = TestData.sampleInvoiceAsString();

    //when
    Invoice result = InvoiceJsonMapper.fromJson(json);

    //then
    assertThat(result, is(TestData.sampleInvoice()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionWhenPassedNullJson() throws IOException {
    InvoiceJsonMapper.fromJson(null);
  }

  @Test(expected = JsonParseException.class)
  public void shouldThrowExceptionWhenPassedRandomString() throws IOException {
    InvoiceJsonMapper.fromJson("tererbnfjd ff r");
  }
}