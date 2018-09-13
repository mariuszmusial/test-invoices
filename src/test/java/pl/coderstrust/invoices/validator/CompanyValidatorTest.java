package pl.coderstrust.invoices.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.stream.Stream;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.coderstrust.invoices.model.Company;

class CompanyValidatorTest {

  private CompanyValidator companyValidator;

  @BeforeEach
  void setUp() {
    companyValidator = new CompanyValidator();
  }

  @Test
  void shouldValidateCorrectCompany() {
    Company testCompany = new Company("BigFirm", "897-562-87-85", "Marszałkowska 34", "00-987",
        "Warszawa");
    Collection<String> result = companyValidator.validate(testCompany);

    assertNotNull(result);
    assertEquals(0, result.size());
  }

  @ParameterizedTest
  @MethodSource("incorrectCompany")
  void shouldValidateIncorrectCompany(Company company, int expectedExceptionsCount,
      String expectedMessageContains) {

    Collection<String> result = companyValidator.validate(company);

    assertNotNull(result);
    assertEquals(expectedExceptionsCount, result.size());
    assertTrue(result.iterator().next().contains(expectedMessageContains));
  }

  private static Stream<Arguments> incorrectCompany() {
    return Stream.of(
        Arguments
            .of(new Company(null, "897-885-56-98", "Kobierzynska 98", "89-898", "Radom"), 1,
                "name"),
        Arguments
            .of(new Company("", "874-589-87-23", "Latawców 33", "89-888", "Wojkowice"), 1, "name"),
        Arguments.of(new Company("Terefe", "888-88", "Górska 3", "55-098", "Opoczno"), 1,
            "Invalid tax ID"),
        Arguments.of(new Company("Terefe", "nkj", "Górska 3", "09-098", "Opoczno"), 1,
            "Invalid tax ID"),
        Arguments.of(new Company("Polandia", "909-090-09-90", "", "09-008", "Radom"), 1,
            "street address"),
        Arguments
            .of(new Company("Polandia", "909-090-09-90", "tr-77-667-8d(", "09-008", "Radom"), 1,
                "Invalid address"),
        Arguments
            .of(new Company("Korda", "789-098-09-90", "Orkana 3", "87588", "Opole"), 1,
                "postal code"),
        Arguments
            .of(new Company("Qwert", "43-32-332-2-2", "Florka 1", "43456", ""), 3, "Invalid tax ID",
                "postal code", "city"),
        Arguments.of(new Company("", "", "", "", ""), 5, "name", "Invalid tax ID", "street address",
            "postal code", "city")
    );
  }
}
