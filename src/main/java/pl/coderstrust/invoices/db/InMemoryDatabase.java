package pl.coderstrust.invoices.db;

import pl.coderstrust.invoices.model.Invoice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryDatabase implements Database {

  private final Map<Integer, Invoice> invoices = new HashMap<>();

  @Override
  public void saveInvoice(Invoice invoice) {

  }

  @Override
  public List<Invoice> getInvoices() {
    return null;
  }

  @Override
  public void updateInvoices(Invoice invoice) {

  }

  @Override
  public void removeInvoicesById(int id) {

  }
}