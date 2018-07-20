package pl.coderstrust.invoices.model;

import java.util.Objects;

public class Company {

  private String name;
  private String taxIdentyficationNumber;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTaxIdentyficationNumber() {
    return taxIdentyficationNumber;
  }

  public void setTaxIdentyficationNumber(String taxIdentyficationNumber) {
    this.taxIdentyficationNumber = taxIdentyficationNumber;
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
        Objects.equals(getTaxIdentyficationNumber(), company.getTaxIdentyficationNumber());
  }

  @Override
  public int hashCode() {

    return Objects.hash(getName(), getTaxIdentyficationNumber());
  }

  @Override
  public String toString() {
    return "Company{"
        + "name='" + name + '\''
        + ", taxIdentyficationNumber='" + taxIdentyficationNumber + '\''
        + '}';
  }
}
