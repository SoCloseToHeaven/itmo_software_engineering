package clientio;

import util.TerminalColors;

import java.io.*;
import java.util.LinkedList;

/**
 * Class is provided for processing user's input and output stream
 */
public class BasicClientIO {
    /**
     * stack of input streams
     */

    private final LinkedList<BufferedReader> stack = new LinkedList<>();

    /**
     * output stream writer
     */
    private final BufferedWriter writer;

    /**
     * default BasicClientIO constructor
     */
    public BasicClientIO() {
        this(System.in, System.out);
    }

    /**
     * adds new input stream to streams {@link #stack} and output stream to {@link #writer}
     * @param in input stream
     * @param out output stream
     */

    public BasicClientIO(InputStream in, OutputStream out) {
        this.stack.add(new BufferedReader(new InputStreamReader(in)));
        this.writer = new BufferedWriter(new OutputStreamWriter(out));
    }


    /**
     * writes object to output stream
     * @param obj any java object
     */
    public void write(Object obj) {
        try {
            writer.write(obj.toString());
            writer.flush();
        } catch (IOException e) {
            System.err.printf("%s: %s%n", "Something went wrong with userIO", e.getMessage());
            System.exit(-1);
        } catch (NullPointerException e) {
            this.write("null");
        }
    }

    /**
     * the same method as {@link #write} but next output starts with new line
     * @param obj any java object
     */
    public void writeln(Object obj) {
        try {
            writer.write(obj.toString());
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.err.printf("%s: %s%n", "Something went wrong with userIO", e.getMessage());
            System.exit(-1);
        } catch (NullPointerException e) {
            this.writeln("null");
        }
    }


    /**
     *
     * @return read one line from input stream, if it ends stream will be closed via method {@link #removeAndClose()}
     */
    public String read() {
        try {
            String input;
            BufferedReader reader = stack.getLast();
            do {
                input = reader.readLine();
                if (input == null) {
                    //stream ended
                    //move to next stream
                    removeAndClose();
                    continue;
                }
                break;
            } while (stack.size() > 0);
            if (stack.size() == 0) {
                writeln(TerminalColors.setColor("All streams ended, stopping program", TerminalColors.RED));
                System.exit(-1);
            }
            return input;
        } catch (IOException e) {
            return  "%s: %s".formatted("Something went wrong with userIO", e.getMessage());
        }
    }


    /**
     *
     * @param message that will be written in output stream before reading data from input stream
     * @return String read from input stream
     */
    public String read(String message) {
        this.write(message);
        return this.read();
    }

    /**
     * the same method as {@link #read()} but if String is empty it'll return null
     * @return String read from input stream
     */
    public String readLineWithNull() {
        String line = read();
        return (line.isEmpty()) ? null : line;
    }

    /**
     * the same method as {@link #read()} but if String is empty it'll return null
     * @param message writes this message in output stream before reading from input stream
     * @return String read from input stream
     */
    public String readLineWithNull(String message) {
        this.write(message);
        return this.readLineWithNull();
    }

    /**
     * @param reader will be added to {@link #stack}
     */
    public void add(BufferedReader reader) {
        stack.add(reader);
    }


    /**
     * removes last stream from {@link #stack}
     */
    public void removeAndClose() {
        try {
            stack.getLast().close();
            remove();
        } catch (IOException e) {
            System.err.printf("%s: %s%n", "Something went wrong with userIO when closing stream", e.getMessage());
            System.exit(-1);
        }
    }

    public BufferedReader getFirstReader() {
        return this.stack.getFirst();
    }

    public void remove() {
        stack.removeLast();
    }


    public String stdRead() {
        BufferedReader stdReader = getFirstReader();
        String input;
        try {
            input = stdReader.readLine();
            if (input == null) {
                stdReader.close();
                throw new IOException("Default stream ended, no option to continue program");
            }
            return input;
        } catch (IOException e) {
            writeln(TerminalColors.setColor(e.getMessage(), TerminalColors.RED));
            System.exit(-1);
        }
        return "Something went wrong with userIO";
    }
    public String stdRead(String message) {
        this.write(message);
        return this.stdRead();
    }

    public String stdReadLineWithNull() {
        String line = stdRead();
        return (line.isEmpty()) ? null : line;
    }


    public String stdReadLineWithNull(String message) {
        this.write(message);
        return this.stdReadLineWithNull();
    }
}
