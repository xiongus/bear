package com.xiongus.bear.console.component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return false;
    }
    // validate phone numbers of format "1234567890"
    if (value.matches("\\d{10}")) return true;
    // validating phone number with -, . or spaces
    else if (value.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) return true;
    // validating phone number with extension length from 3 to 5
    else if (value.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) return true;
    // validating phone number where area code is in braces ()
    else if (value.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) return true;
    // return false if nothing matches the input
    else return false;
  }
}
