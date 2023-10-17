package com.soclosetoheaven.common.models;



import com.soclosetoheaven.common.exceptions.InvalidFieldValueException;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;

public class Dragon implements Serializable, Comparable<Dragon> {
    /**
     * Default validator for dragon
     */
    @Serial
    private static final long serialVersionUID = -361223588786L;
    public static final Validator VALIDATOR = new Validator();


    /**
     * The ID of dragon, it can't be null, it's value have to be positive, unique and auto-generated
     */
    private Long id;

    private String name;

    private final Coordinates coordinates;

    /**
     * The creation date of dragon, can't be null, generates automatically
     */
    private final Date creationDate;


    private Long age;



    private String description;

    private Integer wingspan;

    private DragonType type;


    private final DragonCave cave;

    /**
     * Minimum value for ID
     */

    private final static long ID_MIN_VALUE = 1L;

    public Dragon(String name, Coordinates coordinates, Long age, String description, Integer wingspan, DragonType type, DragonCave cave) {
        do {
            this.id = new Random().nextLong(Integer.MAX_VALUE) + ID_MIN_VALUE;
        } while (VALIDATOR.usedID.contains(this.id));
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = new Date();
        this.age = age;
        this.description = description;
        this.wingspan = wingspan;
        this.type = type;
        this.cave = cave;
        VALIDATOR.validate(this);
    }

    public Long getID() {
        return this.id;
    }

    public String getName() {return this.name;}

    public Dragon setName(String name) {
        VALIDATOR.validateName(name);
        this.name = name;
        return this;
    }
    public Coordinates getCoordinates(){
        return this.coordinates;
    }

    public Dragon setCoordinates(Integer x, double y) {
        coordinates.setCoordinates(x,y);
        return this;
    }
    public Date getCreationDate() {
        return creationDate;
    }


    public Long getAge() {return this.age;}

    public Dragon setAge(Long age) {
        VALIDATOR.validateAge(age);
        this.age = age;
        return this;
    }

    public String getDescription() {return this.description;}

    public Dragon setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getWingspan() {return this.wingspan;}

    public Dragon setWingspan(Integer wingspan) {
        VALIDATOR.validateWingspan(wingspan);
        this.wingspan = wingspan;
        return this;
    }

    public DragonType getType() {return this.type;}

    public Dragon setType(DragonType type) {
        this.type = type;
        return this;
    }

    public void update(String name,
                       Integer x,
                       double y,
                       Long age,
                       String desc,
                       Integer wingspan,
                       DragonType type,
                       long depth,
                       int numberOfTreasures) {
        this.
                setName(name).
                setCoordinates(x,y).
                setAge(age).
                setDescription(desc).
                setWingspan(wingspan).
                setType(type).
                setCave(depth, numberOfTreasures);
    }

    @Override
    public int compareTo(Dragon o) {
        if (this.getID() < o.getID())
            return -1;
        else if (this.getID().equals(o.getID()))
            return 0;
        return 1;
    }

    public DragonCave getCave() {return this.cave;}

    public Dragon setCave(long depth, int numberOfTreasures) {
        this.cave.setCave(depth, numberOfTreasures);
        return this;
    }
    @Override
    public String toString() {

        return "Name: %s; ID: %s; CreationDate: %s; Age: %s; Description: %s; Wingspan: %s; Type: %s; Cave: %s; Coordinates: %s".
                formatted(
                        String.valueOf(getName()),
                        String.valueOf(getID()),
                        String.valueOf(getCreationDate()),
                        String.valueOf(getAge()),
                        String.valueOf(getDescription()),
                        String.valueOf(getWingspan()),
                        String.valueOf(getType()),
                        String.valueOf(getCave()),
                        String.valueOf(getCoordinates()));
    }

    public void setID(Long id) {
        VALIDATOR.validateID(id);
        VALIDATOR.removeUsedID(this.id);
        VALIDATOR.addUsedID(id);
        this.id = id;
    }

    public static class Validator implements AbstractValidator<Dragon> {

        /**
         * collection to cache usedIDs
         */
        @Serial
        private static final long serialVersionUID = -5519361186L;
        private HashSet<Long> usedID = new HashSet<>();
        @Override
        public void validate(Dragon dragon) {
            validateID(dragon.id);
            validateName(dragon.name);
            validateCoordinates(dragon.coordinates);
            validateCreationDate(dragon.creationDate);
            validateAge(dragon.age);
            validateDescription(dragon.description);
            validateWingspan(dragon.wingspan);
            validateDragonCave(dragon.cave);
            validateDragonType(dragon.type);
        }

        /**
         * validates object's ID
         * @param id value to validate
         * @throws InvalidFieldValueException if value is invalid
         */
        public void validateID(Long id) throws InvalidFieldValueException {
            AbstractValidator.checkIfNull(id, "ID can't be null!");
            if (id.intValue() <= 0 || usedID.contains(id))
                throw new InvalidFieldValueException("Invalid ID value!");
            usedID.add(id);
        }

        /**
         * validates object's name
         * @param name value to validate
         * @throws InvalidFieldValueException if value is invalid
         */
        public void validateName(String name) throws InvalidFieldValueException{
            AbstractValidator.checkIfNull(name, "Name can't be null!");
            if (name.isEmpty())
                throw new InvalidFieldValueException("Field name can't be empty!");
        }

        /**
         * validates object's coordinates
         * @param coordinates value to validate
         * @throws InvalidFieldValueException if value is invalid
         */

        public void validateCoordinates(Coordinates coordinates) throws InvalidFieldValueException {
            AbstractValidator.checkIfNull(coordinates, "Coordinates can't be null!");
        }

        /**
         * validates object's creation date
         * @param creationDate value to validate
         * @throws InvalidFieldValueException if value is invalid
         */
        public void validateCreationDate(Date creationDate) throws InvalidFieldValueException {
            AbstractValidator.checkIfNull(creationDate, "Creation date can't be null!");
        }

        /**
         * validates object's age
         * @param age value to validate
         * @throws InvalidFieldValueException if value is invalid
         */
        public void validateAge(Long age) throws InvalidFieldValueException {
            if (age == null) return;
            if (age <= 0) throw new InvalidFieldValueException("Age can't be lower than zero!");
        }

        /**
         * validates object's description
         * @param description value to validate
         * @throws InvalidFieldValueException if value is invalid
         */
        public void validateDescription(String description) throws InvalidFieldValueException {
            AbstractValidator.checkIfNull(description, "Description can't be null!");
        }

        /**
         * validates object's wingspan
         * @param wingspan value to validate
         * @throws InvalidFieldValueException if value is invalid
         */
        public void validateWingspan(Integer wingspan) throws InvalidFieldValueException {
            AbstractValidator.checkIfNull(wingspan, "Wingspan can't be null!");
            if (wingspan <= 0)
                throw new InvalidFieldValueException("Wingspan value can't be lower than zero!");
        }

        /**
         * validates object's type
         * @param dragonType value to validate
         * @throws InvalidFieldValueException if value is invalid
         */
        public void validateDragonType(DragonType dragonType) throws InvalidFieldValueException {
            AbstractValidator.checkIfNull(dragonType, "Dragon type can't be null!");
        }

        /**
         * validates object's cave
         * @param dragonCave value to validate
         * @throws InvalidFieldValueException if value is invalid
         */

        public void validateDragonCave(DragonCave dragonCave) throws InvalidFieldValueException {
            AbstractValidator.checkIfNull(dragonCave, "Dragon cave can't be null!");
        }

        /**
         * clears collection of usedIDs
         */
        public void clearUsedID() {
            usedID = new HashSet<>();
        }

        /**
         * @param id to remove from usedIDs collection
         */

        public void removeUsedID(Long id) {
            usedID.remove(id);
        }

        public void addUsedID(Long id) {
            usedID.add(id);
        }
    }
}