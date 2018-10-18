package pl.coderstrust.invoices.db.hibernate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.coderstrust.invoices.db.BaseDatabaseTest;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class HibernateDatabaseTest extends BaseDatabaseTest {

  @Autowired
  private InvoiceRepository invoiceRepository;

  @BeforeEach
  void init() {
    database = new HibernateDatabase(invoiceRepository);
  }
}