package chessgame;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

/**
 * class that manage files
 */

public class FileManager
{
    public static final String RESULT_FILE_PATH = "save.txt";

    /**
     * saves game state to file
     *
     * @param gameState string that contains game state, and can be reas to recreate board
     * @throws IOException is thrown something goes wrong while saving
     */

    public void saveGameState(String gameState)
    {
        if (Files.notExists(Path.of(RESULT_FILE_PATH)))
        {
            try
            {
                Files.createFile(Path.of(RESULT_FILE_PATH));
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
        try
        {
            Files.write(Path.of(RESULT_FILE_PATH), Collections.singleton(gameState));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}