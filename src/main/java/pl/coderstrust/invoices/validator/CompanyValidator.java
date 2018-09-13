package pl.coderstrust.invoices.validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import pl.coderstrust.invoices.model.Company;

public class CompanyValidator implements Validator<Company> {

  private static final String EXPECTED_NOT_EMPTY_CITY = "Expected not empty city";
  private static final String EXPECTED_NOT_EMPTY_NAME = "Expected not empty name";
  private static final String EXPECTED_NOT_EMPTY_POSTAL_CODE = "Expected not empty postal code";
  private static final String EXPECTED_NOT_EMPTY_STREET_ADDRESS = "Expected not empty street address";
  private static final String EXPECTED_NOT_EMPTY_TAX_IDENTIFICATION_NUMBER = "Expected not empty tax ID";

  private static final String INVALID_ADDRESS = "Invalid address";
  private static final String INVALID_IDENTIFICATION_NUMBER = "Invalid tax ID";
  private static final String INVALID_POSTAL_CODE = "Invalid postal code";

  private static final String POSTAL_CODE_REGEX = "\\d{2}-\\d{3}";
  private static final String TAX_ID_REGEX = "^(\\d{3}-\\d{3}-\\d{2}-\\d{2})|^(\\d{3}-\\d{2}-\\d{2}-\\d{3})|^(\\d{10})$";
  private static final String ADDRESS_REGEX = "^.{1,100} \\d.*$";

  private final Pattern postalCodePattern;
  private final Pattern taxIdPattern;
  private final Pattern addressPattern;

  public CompanyValidator() {
    postalCodePattern = Pattern.compile(POSTAL_CODE_REGEX);
    taxIdPattern = Pattern.compile(TAX_ID_REGEX);
    addressPattern = Pattern.compile(ADDRESS_REGEX);
  }

  public Collection<String> validate(Company company) {
    List<String> validationExceptions = new ArrayList<>();

    if (company.getName() == null || company.getName().isEmpty()) {
      validationExceptions.add(EXPECTED_NOT_EMPTY_NAME);
    }
    if (company.getTaxIdentificationNumber() == null || company.getTaxIdentificationNumber()
        .isEmpty()) {
      validationExceptions.add(EXPECTED_NOT_EMPTY_TAX_IDENTIFICATION_NUMBER);
    } else {
      if (!taxIdPattern.matcher(company.getTaxIdentificationNumber()).matches()) {
        validationExceptions
            .add(INVALID_IDENTIFICATION_NUMBER + ": " + company.getTaxIdentificationNumber());
      }
    }
    if (company.getStreetAddress() == null || company.getStreetAddress().isEmpty()) {
      validationExceptions.add(EXPECTED_NOT_EMPTY_STREET_ADDRESS);
    } else {
      if (!addressPattern.matcher(company.getStreetAddress()).matches()) {
        validationExceptions.add(INVALID_ADDRESS + ": " + company.getStreetAddress());
      }
    }
    if (company.getPostalCode() == null || company.getPostalCode().isEmpty()) {
      validationExceptions.add(EXPECTED_NOT_EMPTY_POSTAL_CODE);
    } else {
      if (!postalCodePattern.matcher(company.getPostalCode()).matches()) {
        validationExceptions.add(INVALID_POSTAL_CODE + ": " + company.getPostalCode());
      }
    }
    if (company.getCity() == null || company.getCity().isEmpty()) {
      validationExceptions.add(EXPECTED_NOT_EMPTY_CITY);
    }
    return validationExceptions;
  }
}