package com.soclosetoheaven.common.model;


import com.soclosetoheaven.common.exception.InvalidFieldValueException;
import com.soclosetoheaven.common.util.AbstractValidator;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Coordinates  implements Serializable {
    @Serial
    private static final long serialVersionUID = -551936122399L;
    /**
     *  default validator for coordinates
     */
    public static final Validator VALIDATOR = new Validator();

    private Integer x;

    private int y;

    /**
     * creates object and validate its fields via {@link #VALIDATOR#validate()}
     * @param startX can't be null
     * @param startY can't be greater than {@link #VALIDATOR#Y_MAX_VALUE}
     */

    public Coordinates(Integer startX, int startY) {
        this.x = startX;
        this.y = startY;
        VALIDATOR.validate(this);
    }


    public Integer getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setCoordinates(Integer x, int y) {
        VALIDATOR.validateX(x);
        VALIDATOR.validateY(y);
        this.x = x;
        this.y = y;
    }
    @Override
    public String toString() {
        return "[%s, %s]".formatted(String.valueOf(x),y);
    }


    public static class Validator implements AbstractValidator<Coordinates> {
        @Serial
        private static final long serialVersionUID = -55221488786L;

        public static final int X_MIN_VALUE = 0; // TODO: проверить, что будет если вписать при создании объекта 0
        public static final int X_MAX_VALUE = 3782;

        public static final int Y_MAX_VALUE = 2274;

        public static final int Y_MIN_VALUE = 0;
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
        public void validateX(Integer x) {
            AbstractValidator.checkIfNull(x, "Invalid Field Value");
            if (!(x >= X_MIN_VALUE && x <= X_MAX_VALUE))
                throw new InvalidFieldValueException("Invalid Field Value");
        }

        /**
         *
         * @param y value to validate
         * @throws InvalidFieldValueException if value is invalid
         */
        public void validateY(int y) {
            if (!(y <= Y_MAX_VALUE && y >= Y_MIN_VALUE))
                throw new InvalidFieldValueException("Invalid Field Value");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return that.y == y && x.equals(that.x);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
