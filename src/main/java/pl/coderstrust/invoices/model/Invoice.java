package pl.coderstrust.invoices.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Invoice {

  private Long id;

  private String identifier;
  private LocalDate issuedDate;
  private Company seller;
  private Company buyer;
  private List<InvoiceEntry> entries;

  public Invoice() {
    //constructor for use only by Jackson
  }

  public Invoice(Long id, String identifier, LocalDate issuedDate,
      Company seller, Company buyer,
      List<InvoiceEntry> entries) {
    this.id = id;
    this.identifier = identifier;
    this.issuedDate = issuedDate;
    this.seller = seller;
    this.buyer = buyer;
    this.entries = entries;
  }

  public Long getId() {
    return id;
  }

  public String getIdentifier() {
    return identifier;
  }

  public LocalDate getIssuedDate() {
    return issuedDate;
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
        Objects.equals(getIdentifier(), invoice.getIdentifier()) &&
        Objects.equals(getIssuedDate(), invoice.getIssuedDate()) &&
        Objects.equals(getSeller(), invoice.getSeller()) &&
        Objects.equals(getBuyer(), invoice.getBuyer()) &&
        Objects.equals(getEntries(), invoice.getEntries());
  }

  @Override
  public int hashCode() {

    return Objects
        .hash(getId(), getIdentifier(), getIssuedDate(), getSeller(), getBuyer(), getEntries());
  }
}