package pl.coderstrust.invoices.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Company {

  @Id
  @GeneratedValue
  private int id;

  private String name;
  private String taxIdentificationNumber;
  private String streetAddress;
  private String postalCode;
  private String city;

  public Company() {
    //constructor for use only by Jackson
  }

  public Company(String name, String taxIdentificationNumber, String streetAddress,
      String postalCode, String city) {
    this.name = name;
    this.taxIdentificationNumber = taxIdentificationNumber;
    this.streetAddress = streetAddress;
    this.postalCode = postalCode;
    this.city = city;
  }

  public String getName() {
    return name;
  }

  public String getTaxIdentificationNumber() {
    return taxIdentificationNumber;
  }

  public String getStreetAddress() {
    return streetAddress;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public String getCity() {
    return city;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Company)) {
      return false;
    }
    Company company = (Company) obj;
    return Objects.equals(getName(), company.getName())
        && Objects.equals(getTaxIdentificationNumber(), company.getTaxIdentificationNumber())
        && Objects.equals(getStreetAddress(), company.getStreetAddress())
        && Objects.equals(getPostalCode(), company.getPostalCode())
        && Objects.equals(getCity(), company.getCity());
  }

  @Override
  public int hashCode() {

    return Objects
        .hash(getName(), getTaxIdentificationNumber(), getStreetAddress(), getPostalCode(),
            getCity());
  }
}