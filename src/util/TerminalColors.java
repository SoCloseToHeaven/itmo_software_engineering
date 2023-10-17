package util;

/**
 * This class if provided to color output lines via ansi-colors
 */
public enum TerminalColors {
    GREEN("\u001b[32m"),
    RED("\u001b[31m"),
    BLUE("\u001b[34m"),
    CYAN("\u001B[36m"),
    RESET("\u001b[0m");


    private final String ansiColor;

    /**
     * @param ansiColor - color code
     */
    TerminalColors(String ansiColor) {
        this.ansiColor = ansiColor;
    }

    /**
     * @param line to color
     * @param color to use
     * @return colored line
     */
    public static String setColor(String line, TerminalColors color) {
        return "%s%s%s".formatted(color.toString(), line, TerminalColors.RESET);
    }

    @Override
    public String toString() {
        return this.ansiColor;
    }
}
