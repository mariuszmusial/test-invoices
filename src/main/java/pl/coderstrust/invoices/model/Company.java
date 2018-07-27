package pl.coderstrust.invoices.model;

import java.util.Objects;

public class Company {

  private String name;
  private String taxIdentificationNumber;
  private String streetAddress;
  private String postalCode;
  private String city;

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

  public void setName(String name) {
    this.name = name;
  }

  public String getTaxIdentificationNumber() {
    return taxIdentificationNumber;
  }

  public void setTaxIdentificationNumber(String taxIdentificationNumber) {
    this.taxIdentificationNumber = taxIdentificationNumber;
  }

  public String getStreetAddress() {
    return streetAddress;
  }

  public void setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Company)) {
      return false;
    }
    Company company = (Company) o;
    return Objects.equals(getName(), company.getName()) &&
        Objects.equals(getTaxIdentificationNumber(), company.getTaxIdentificationNumber()) &&
        Objects.equals(getStreetAddress(), company.getStreetAddress()) &&
        Objects.equals(getPostalCode(), company.getPostalCode()) &&
        Objects.equals(getCity(), company.getCity());
  }

  @Override
  public int hashCode() {

    return Objects
        .hash(getName(), getTaxIdentificationNumber(), getStreetAddress(), getPostalCode(),
            getCity());
  }
}