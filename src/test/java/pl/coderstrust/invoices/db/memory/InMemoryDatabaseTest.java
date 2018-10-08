package pl.coderstrust.invoices.db.memory;

import org.junit.jupiter.api.BeforeEach;
import pl.coderstrust.invoices.db.BaseDatabaseTest;

class InMemoryDatabaseTest extends BaseDatabaseTest {

  @BeforeEach
  void init() {
    database = new InMemoryDatabase();
  }
}