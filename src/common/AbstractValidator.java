package common;

import exceptions.InvalidFieldValueException;

@FunctionalInterface
public interface AbstractValidator<T> {

    /**
     * abstract method to validate any fields of class
     * @param somethingToValidate Object of class that needs to be validated
     * @throws InvalidFieldValueException if value of any field is not correct
     */
    void validate(T somethingToValidate) throws InvalidFieldValueException;

    /**
     * validates field if it is not null
     * @param field something to validate
     * @param fieldName name of field used in exception message
     * @throws InvalidFieldValueException if object is null
     */
    static void checkIfNull(Object field, String fieldName) throws  InvalidFieldValueException {
        if (field == null)
            throw new InvalidFieldValueException(fieldName);
    }

}
