package common;

import exceptions.InvalidFieldValueException;

public class DragonCave {
    /**
     * default validator
     */

    public static final Validator VALIDATOR = new Validator();

    private long depth;

    private int numberOfTreasures;

    /**
     * creates new object of class and validates it via {@link #VALIDATOR#validate()}
     * @param startDepth value to validate
     * @param startNumberOfTreasures value have to be greater than zero
     */

    public DragonCave(long startDepth, int startNumberOfTreasures) {
        this.depth = startDepth;
        this.numberOfTreasures = startNumberOfTreasures;
        VALIDATOR.validate(this);
    }

    /**
     * update fields with new values, validates them before via {@link #VALIDATOR#validate()}
     * @param depth value to update
     * @param numberOfTreasures value to update
     */
    public void setCave(long depth, int numberOfTreasures) {
        VALIDATOR.validateNumberOfTreasures(numberOfTreasures);
        this.depth = depth;
        this.numberOfTreasures = numberOfTreasures;
    }

    @Override
    public String toString() {
        return "[%s, %s]".formatted(depth, numberOfTreasures);
    }

    public static class Validator implements AbstractValidator<DragonCave> {
        @Override
        public void validate(DragonCave cave) throws InvalidFieldValueException {
            validateNumberOfTreasures(cave.numberOfTreasures);
        }

        /**
         *
         * @param numberOfTreasures value to validate
         * @throws InvalidFieldValueException if value is invalid
         */
        public void validateNumberOfTreasures(int numberOfTreasures) throws InvalidFieldValueException{
            if (numberOfTreasures <= 0)
                throw new InvalidFieldValueException("Number of treasures can't be lower than zero");
        }
    }
}
