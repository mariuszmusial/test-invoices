package pl.coderstrust.project.helper;

import org.junit.Test;
import pl.coderstrust.project.model.Invoice;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class InvoiceJsonMapperTest {

    @Test
    public void shouldConvertSimpleInvoiceToJson() {
        //given
        Invoice invoice = new Invoice();
        invoice.setId("abc");
        invoice.setIssueDate(new Date());

        //when
        String result = InvoiceJsonMapper.toJson(invoice);

        //then
        assertThat(result, is("{id: \"abc\", issueDate:\"2018-07-23\"}"));
    }
}