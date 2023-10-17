package com.soclosetoheaven.common.models;


import com.soclosetoheaven.common.exceptions.InvalidFieldValueException;

import java.io.Serial;
import java.io.Serializable;

public class Coordinates  implements Serializable {
    @Serial
    private static final long serialVersionUID = -551936122399L;
    /**
     *  default validator for coordinates
     */
    public static final Validator VALIDATOR = new Validator();

    private Integer x;

    private double y;

    /**
     * creates object and validate its fields via {@link #VALIDATOR#validate()}
     * @param startX can't be null
     * @param startY can't be greater than 36
     */

    public Coordinates(Integer startX, double startY) {
        this.x = startX;
        this.y = startY;
        VALIDATOR.validate(this);
    }


    /**
     * sets new values for field {@link #x} and {@link #y} if they pass validation
     * @param x value to validate
     * @param y value to validate
     */
    public void setCoordinates(Integer x, double y) {
        VALIDATOR.validateX(x);
        VALIDATOR.validateY(y);
        this.x = x;
        this.y = y;
    }
    @Override
    public String toString() {
        return "[%s, %f]".formatted(String.valueOf(x),y);
    }


    public static class Validator implements AbstractValidator<Coordinates> {
        @Serial
        private static final long serialVersionUID = -55221488786L;
        @Override
        public void validate(Coordinates cords) {
            validateX(cords.x);
            validateY(cords.y);
        }

        /**
         *
         * @param x value to validate
         * @throws InvalidFieldValueException if value is invalid
         */
        public void validateX(Integer x) throws InvalidFieldValueException {
            AbstractValidator.checkIfNull(x, "Field X can't be null");
        }

        /**
         *
         * @param y value to validate
         * @throws InvalidFieldValueException if value is invalid
         */
        public void validateY(double y) throws InvalidFieldValueException{
            if (y >= 36.0)
                throw new InvalidFieldValueException("Field Y can't be greater than 36");
        }
    }
}
