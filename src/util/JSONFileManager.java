package util;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import common.Dragon;
import exceptions.InvalidFieldValueException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class JSONFileManager {
    /**
     * input/output file
     */

    private final File file;

    /**
     * container for data from file
     */
    public final StringBuilder fileData = new StringBuilder();
    /**
     * please check {@link JSONCollectionReader}
     */
    public final JSONCollectionReader parser = new JSONCollectionReader();

    /**
     * please check {@link JSONCollectionReader}
     */
    private final JSONCollectionWriter writer = new JSONCollectionWriter();

    /**
     * please check {@link JSONCollectionWriter}, {@link JSONCollectionReader}
     * @param inputFileName name of that will be read by manager
     * @throws FileNotFoundException if file doesn't exist
     */
    public JSONFileManager(String inputFileName) throws FileNotFoundException{
        file = new File(inputFileName);
        if (!file.exists())
            throw new FileNotFoundException("File with name/path: %s doesn't exist".formatted(inputFileName));
    }

    /**
     * please check {@link common.Dragon}
     * @return collection that was read from file and parsed to ArrayList
     */
    public ArrayList<Dragon> readFromFile(){
        try(BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            in.lines().forEach(this.fileData::append);
            ArrayList<Dragon> dragons = parser.parse(this.fileData.toString());
            dragons.removeIf(dragon -> {
                try {
                    Dragon.VALIDATOR.validate(dragon);
                } catch (InvalidFieldValueException  e) {
                    System.err.printf("Object: %s - can't be added due to invalid field values%n", dragon.toString());
                    return true;
                }
                return false;
            });
            return dragons;
        } catch (IOException | JsonParseException e) {
            System.err.printf("%s: %s%n", e.getMessage(), "empty collection created" );
        }
        return new ArrayList<>();
    }

    /**
     * parses collection via {@link #writer#parse()} and saves it to file
     * @param collection that will be saved to file
     * @return true if successfully saved, false if else
     */
    public boolean saveToFile(ArrayList<Dragon> collection) {
        try (FileOutputStream out = new FileOutputStream(file)) {
            out.write(writer.parse(collection).getBytes());
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * inner class to provide writing to .json file
     */
    private static class JSONCollectionWriter {

        /**
         * utility to work with .json format, please check {@link com.google.gson.Gson}
         */
        private final Gson gson = new Gson();

        /**
         * parses collection to .json format
         * @param collection of elements that will be converted to .json format, please check {@link common.Dragon}
         * @return parsed to .json format String
         */
        public String parse(ArrayList<Dragon> collection) {
            return gson.toJson(collection);
        }
    }

    /**
     * inner class to provide reading of .json file
     */
    private static class JSONCollectionReader {
        /**
         * utility to work with .json format, please check {@link com.google.gson.Gson}
         */
        private final Gson gson = new Gson();

        /**
         * parses string from .json structure to ArrayList collection, please check {@link common.Dragon}
         * @param jsonString loaded from file
         * @return collection parsed from .json format string
         * @throws JsonParseException if string has incorrect .json structure
         */
        public ArrayList<Dragon> parse(String jsonString) throws JsonParseException{
            Dragon[] dragons;
            try {
                dragons = gson.fromJson(jsonString, Dragon[].class);
            } catch (NumberFormatException| JsonSyntaxException exception) {
                throw new JsonParseException("Invalid file structure or value for fields");
            }
            return new ArrayList<>(Arrays.asList(dragons));
        }
    }
}
