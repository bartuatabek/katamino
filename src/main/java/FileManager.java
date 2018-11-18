import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class FileManager {
    private Gson gson;
    public FileManager()  {
        gson = new Gson();
    }

    public Level loadLevels() throws FileNotFoundException{//load specific level
        JsonReader jsonReader = new JsonReader(new FileReader("src/level.json"));
        /*
        Level level = new Level((Integer[][]) gson.fromJson(jsonReader, Integer[][].class));
        for(int i = 0; i < level.getGrid().length;i++){
            for(int j = 0; j < level.getGrid()[i].length;j++){
                System.out.print("["+level.getGrid()[i][j]+"]");

            }
            System.out.println();
        }
        */
        return new Level(gson.fromJson(jsonReader, Integer[][].class));
    }
}
