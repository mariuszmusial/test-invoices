package pl.coderstrust.invoices.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Invoice {

  private String id;
  private LocalDate issuedData;
  private Company seller;
  private Company bayer;

  private List<InvoiceEntry> entries;

  public Invoice(String id, LocalDate issueData, Company sellect,
      Company bayer, List<InvoiceEntry> entries) {
    this.id = id;
    this.issuedData = issueData;
    this.seller = sellect;
    this.bayer = bayer;
    this.entries = entries;
  }

  public String getId() {
    return id;
  }

  public LocalDate getIssueData() {
    return issuedData;
  }

  public Company getSellect() {
    return seller;
  }

  public Company getBayer() {
    return bayer;
  }

  public List<InvoiceEntry> getEntries() {
    return entries;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Invoice)) {
      return false;
    }
    Invoice invoice = (Invoice) o;
    return Objects.equals(getId(), invoice.getId()) &&
        Objects.equals(getIssueData(), invoice.getIssueData()) &&
        Objects.equals(getSellect(), invoice.getSellect()) &&
        Objects.equals(getBayer(), invoice.getBayer()) &&
        Objects.equals(getEntries(), invoice.getEntries());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getIssueData(), getSellect(), getBayer(), getEntries());
  }
}
