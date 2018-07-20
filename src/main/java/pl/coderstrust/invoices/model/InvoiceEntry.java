package pl.coderstrust.invoices.model;

import java.math.BigDecimal;
import java.util.Objects;

public class InvoiceEntry {

  private String productName;
  private String amount;
  private BigDecimal price;
  private Vat vat;

  public InvoiceEntry(String productName, String amount, BigDecimal price,
      Vat vat) {
    this.productName = productName;
    this.amount = amount;
    this.price = price;
    this.vat = vat;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Vat getVat() {
    return vat;
  }

  public void setVat(Vat vat) {
    this.vat = vat;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof InvoiceEntry)) {
      return false;
    }
    InvoiceEntry that = (InvoiceEntry) o;
    return Objects.equals(getProductName(), that.getProductName()) &&
        Objects.equals(getAmount(), that.getAmount()) &&
        Objects.equals(getPrice(), that.getPrice()) &&
        getVat() == that.getVat();
  }

  @Override
  public int hashCode() {

    return Objects.hash(getProductName(), getAmount(), getPrice(), getVat());
  }
}