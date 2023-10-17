package exceptions;

public class InvalidCommandArgumentException extends Exception{

    /**
     * default constructor
     */

    public InvalidCommandArgumentException(){}

    /**
     * @param message exception message
     */

    public InvalidCommandArgumentException(String message) {
        super("%s - %s".formatted(message, "Invalid arguments"));
    }

}
