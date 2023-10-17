import clientio.BasicClientIO;
import commandmanagers.BasicCommandManager;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.*;


/**
 * Main-Class
 */
public class Client {

    /**
     * private class-constructor
     */
    private Client() {
        throw new UnsupportedOperationException("This is utility class!");
    }

    /**
     * main method, runs ConsoleClient
     * @param args command-string arguments
     */
    public static void main(String[] args) {
        ConsoleClient client = new ConsoleClient(new BasicClientIO(), new BasicCommandManager());
        client.run();

        Channel channel = Channels.newChannel();
        Selector selector = Selector.open();
        SelectionKey key = channel
    }
}
