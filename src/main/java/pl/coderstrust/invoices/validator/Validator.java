package pl.coderstrust.invoices.validator;

import java.util.Collection;

public interface Validator<T> {
  Collection<String> validate(T objectToValidate);
}
