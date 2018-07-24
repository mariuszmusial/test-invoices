package pl.coderstrust.invoices.model;

import java.util.Objects;

public class Company {

  private String name;
  private String NIP;

  public Company(String name, String NIP) {
    this.name = name;
    this.NIP = NIP;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNIP() {
    return NIP;
  }

  public void setNIP(String NIP) {
    this.NIP = NIP;
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
        Objects.equals(getNIP(), company.getNIP());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getNIP());
  }
}