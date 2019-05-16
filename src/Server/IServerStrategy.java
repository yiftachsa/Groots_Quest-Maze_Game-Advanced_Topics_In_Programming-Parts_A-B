package Server;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * A server strategy interface
 */
public interface IServerStrategy {
    void serverStrategy(InputStream inFromClient, OutputStream outToClient);
}
