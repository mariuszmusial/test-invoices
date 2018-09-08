package pl.coderstrust.invoices.validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import pl.coderstrust.invoices.model.Company;

public class CompanyValidator {    // implements Validator<Company>

  private static final String EXPECTED_NOT_EMPTY_NAME = "Expected not empty name";
  private static final String EXPECTED_NOT_EMPTY_TAX_IDENTIFICATION_NUMBER = "Expected not empty tax ID";
  private static final String INVALID_IDENTIFICATION_NUMBER = "Invalid tax ID";

  //  {1,100} \d.*

// ^(\d{3}-\d{3}-\d{2}-\d{2})|^(\d{3}-\d{2}-\d{2}-\d{3})|^(\d{10})$

  private static final String EXPECTED_NOT_EMPTY_STREET_ADDRESS = "";

  private static final String EXPECTED_NOT_EMPTY_POSTAL_CODE = "";

  private static final String INVALID_POSTAL_CODE = "Invalid postal code";

  private static final String EXPECTED_NOT_EMPTY_CITY = "";
  private static final String POSTAL_CODE_REGEX = "\\d{2}-\\d{3}";

  private final Pattern postalCodePattern;

  public CompanyValidator() {

    postalCodePattern = Pattern.compile(POSTAL_CODE_REGEX);
  }

  public Collection<String> validate(Company company) {
    List<String> validationExceptions = new ArrayList<>();

    if (company.getName() == null || company.getName().isEmpty()) {
      validationExceptions.add(EXPECTED_NOT_EMPTY_NAME);
    }
    if (!postalCodePattern.matcher(company.getPostalCode()).matches()) {
      validationExceptions.add(INVALID_POSTAL_CODE + ": " + company.getPostalCode());
    }

    return validationExceptions;
  }
}
