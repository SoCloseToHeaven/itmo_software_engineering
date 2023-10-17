package collectionmanagers;



import common.Dragon;
import util.JSONFileManager;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;

/**
 * This class is used to organise working with collection, that could be saved in file or loaded from it
 */
public class FileCollectionManager implements SaveableCollectionManager<Dragon>{

    /**
     * collection of {@link common.Dragon}
     */

    private ArrayList<Dragon> collection = new ArrayList<>();

    /**
     * file manager, that is used for saving collection in file, please check {@link util.JSONFileManager}
     */

    private final JSONFileManager fileManager;

    /**
     * initialization date
     */
    private final Date initDate;

    public FileCollectionManager(String filePath) throws FileNotFoundException{
        fileManager = new JSONFileManager(filePath);
        initDate = new Date();
    }


    /**
     * @param id id of {@link common.Dragon}
     * @return element with given ID
     * @throws NoSuchElementException if there is no element with given ID
     */
    public Dragon getByID(long id) {
        for (Dragon dragon : collection) {
            if (dragon.getID() == id)
                return dragon;
        }
        throw new NoSuchElementException("No such ID found");
    }

    @Override
    public void add(Dragon dragon) {
        collection.add(dragon);
    }

    @Override
    public void clear() {
        this.collection = new ArrayList<>();
        Dragon.VALIDATOR.clearUsedID();
    }

    @Override
    public void sort() { // sorting by ID
        this.collection.sort((o1, o2) -> {
            if (o1.getID() < o2.getID())
                return -1;
            else if (o1.getID().equals(o2.getID()))
                return 0;
            return 1;
        });
    }

    @Override
    public boolean remove(int index) {
        if (index >= 0 && index < collection.size()) {
            Dragon.VALIDATOR.removeUsedID(collection.get(index).getID());
            collection.remove(index);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeIf(long id) {
        return collection.removeIf(element -> {
            if (element.getID() == id) {
                Dragon.VALIDATOR.removeUsedID(id);
                return true;
            }
            return false;
        });
    }

    @Override
    public boolean save() {

        return fileManager.saveToFile(collection);
    }

    @Override
    public ArrayList<Dragon> getCollection() {
        return this.collection;
    }

    @Override
    public void open(){
        this.collection = fileManager.readFromFile();
    }

    @Override
    public String toString() {
        return "%s; %s - %d; %s - %s".formatted(
                "Collection type - ArrayList", "current size", collection.size(), "Initial data", initDate.toString());
    }
}

