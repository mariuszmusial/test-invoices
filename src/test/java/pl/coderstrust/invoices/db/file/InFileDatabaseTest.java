package pl.coderstrust.invoices.db.file;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import pl.coderstrust.invoices.db.BaseDatabaseTest;
import pl.coderstrust.invoices.db.InFileDatabase;
import pl.coderstrust.invoices.exception.DatabaseException;

class InFileDatabaseTest extends BaseDatabaseTest {

  private static final String FILE_PATH = "db.txt";
  private static final String ID_FILE_PATH = "id.txt";

  @BeforeEach
  void init() throws DatabaseException, IOException {
    new File(FILE_PATH).createNewFile();
    new File(ID_FILE_PATH).createNewFile();
    database = new InFileDatabase(FILE_PATH, ID_FILE_PATH);
  }

  @AfterEach
  void deleteFiles() {
    new File(FILE_PATH).delete();
    new File(ID_FILE_PATH).delete();
  }
}