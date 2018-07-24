package pl.coderstrust.invoices.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Invoice {

  private String id;
  private LocalDate issuedData;
  private Company seller;
  private Company buyer;

  private List<InvoiceEntry> entries;

  public Invoice(String id, LocalDate issueData, Company seller,
      Company buyer, List<InvoiceEntry> entries) {
    this.id = id;
    this.issuedData = issueData;
    this.seller = seller;
    this.buyer = buyer;
    this.entries = entries;
  }

  public String getId() {
    return id;
  }

  public LocalDate getIssueData() {
    return issuedData;
  }

  public Company getSeller() {
    return seller;
  }

  public Company getBuyer() {
    return buyer;
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
        Objects.equals(getSeller(), invoice.getSeller()) &&
        Objects.equals(getBuyer(), invoice.getBuyer()) &&
        Objects.equals(getEntries(), invoice.getEntries());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getIssueData(), getSeller(), getBuyer(), getEntries());
  }
}