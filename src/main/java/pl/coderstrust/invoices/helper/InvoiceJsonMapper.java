package pl.coderstrust.invoices.helper;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import pl.coderstrust.invoices.model.Invoice;

public class InvoiceJsonMapper {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  static {
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    objectMapper.setSerializationInclusion(Include.NON_NULL);
  }

  public static Invoice fromJson(String json) throws IOException {
    if (json == null) {
      throw new IllegalArgumentException("JSON cannot be null");
    }
    return objectMapper.readValue(json, Invoice.class);
  }

  public static String toJson(Invoice invoice) throws JsonProcessingException {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null");
    }
    return objectMapper.writeValueAsString(invoice);
  }
}
