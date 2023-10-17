package com.soclosetoheaven.common.io;


import java.io.*;
import java.util.LinkedList;

/**
 * Class is provided for processing user's input and output stream
 */
public class BasicIO {
    /**
     * stack of input streams
     */

    private final LinkedList<BufferedReader> stack = new LinkedList<>();

    /**
     * output stream writer
     */
    private final BufferedWriter writer;

    private final BufferedWriter errWriter;


    /**
     * default BasicClientIO constructor
     */
    public BasicIO() {
        this.writer = null;
        this.errWriter = new BufferedWriter(new OutputStreamWriter(System.err));
    }

    /**
     * adds new input stream to streams {@link #stack} and output stream to {@link #writer}
     * @param in input stream
     * @param out output stream
     */

    public BasicIO(InputStream in, OutputStream out) {
        this.stack.add(new BufferedReader(new InputStreamReader(in)));
        this.writer = new BufferedWriter(new OutputStreamWriter(out));
        this.errWriter = new BufferedWriter(new OutputStreamWriter(System.err));
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
            writeErr("%s: %s%n".formatted("Something went wrong with userIO", e.getMessage()));
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
            writeErr("%s: %s%n".formatted("Something went wrong with userIO", e.getMessage()));
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
            String line;
            do {
                line = stack.getLast().readLine();
                if (line == null) {
                    removeAndClose();
                    continue;
                }
                break;
            } while (!stack.isEmpty());
            return line;
        } catch (IOException e) {
            return null;
        }
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
    public void removeAndClose() throws IOException {
        stack.getLast().close();
        remove();
    }

    public BufferedReader getFirstReader() {
        return this.stack.getFirst();
    }

    public void remove() {
        stack.removeLast();
    }



    public void writeErr(Object obj) {

    }
}
