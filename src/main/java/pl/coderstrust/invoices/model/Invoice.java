package pl.coderstrust.invoices.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
@EntityScan
public class Invoice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String identifier;
  private LocalDate issuedDate;

  @ManyToOne(cascade = CascadeType.ALL)
  private Company seller;

  @ManyToOne(cascade = CascadeType.ALL)
  private Company buyer;

  @ManyToMany(cascade = CascadeType.ALL)
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
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Invoice)) {
      return false;
    }
    Invoice invoice = (Invoice) obj;
    return Objects.equals(getId(), invoice.getId())
        && Objects.equals(getIdentifier(), invoice.getIdentifier())
        && Objects.equals(getIssuedDate(), invoice.getIssuedDate())
        && Objects.equals(getSeller(), invoice.getSeller())
        && Objects.equals(getBuyer(), invoice.getBuyer())
        && Objects.equals(getEntries(), invoice.getEntries());
  }

  @Override
  public int hashCode() {

    return Objects
        .hash(getId(), getIdentifier(), getIssuedDate(), getSeller(), getBuyer(), getEntries());
  }
}