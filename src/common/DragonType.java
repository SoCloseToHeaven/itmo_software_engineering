package common;


public enum DragonType {
    WATER(1),

    UNDERGROUND(2),

    AIR(3),

    FIRE(4);

    /**
     * parses String to DragonType
     * @param line value to parse
     * @return DragonType's value
     * @throws UnsupportedOperationException if value is invalid
     */
    private int number;
    DragonType(int number) {
        this.number = number;
    }
    public static DragonType parseDragonType(String line) {
        for (DragonType type : DragonType.values()) {
            if (type.toString().equals(line.toUpperCase()) ||
                    (line.matches("[1-9]\\d*") && Integer.parseInt(line) == type.number))
                return type;
        }
        throw new UnsupportedOperationException("%s - %s".formatted(line, "can't be converted to DragonType"));
    }

    /**
     * @return all DragonType.values() in one line
     */

    public static String stringValues() {
        StringBuilder line = new StringBuilder();
        for (DragonType type : DragonType.values()) {
            line.append("%s ".formatted(type.toString()));
        }
        return line.toString().strip();
    }
}
