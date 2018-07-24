package pl.coderstrust.invoices.model;

import java.util.Objects;

public class Company {

  private String name;
  private String taxID;

  public Company(String name, String taxID) {
    this.name = name;
    this.taxID = taxID;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTaxID() {
    return taxID;
  }

  public void setTaxID(String taxID) {
    this.taxID = taxID;
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
        Objects.equals(getTaxID(), company.getTaxID());
  }

  @Override
  public int hashCode() {

    return Objects.hash(getName(), getTaxID());
  }
}
