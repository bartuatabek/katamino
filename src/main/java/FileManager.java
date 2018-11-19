import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class FileManager {
    private Gson gson;
    public FileManager()  {
        gson = new Gson();
    }

    public Level loadLevels() throws FileNotFoundException{//load specific level
        JsonReader jsonReader = new JsonReader(new FileReader("level.json"));
        /*
        Level level = new Level((Integer[][]) gson.fromJson(jsonReader, Integer[][].class));
        for(int i = 0; i < level.getGrid().length;i++){
            for(int j = 0; j < level.getGrid()[i].length;j++){
                System.out.print("["+level.getGrid()[i][j]+"]");

            }
            System.out.println();
        }
        */
        return new Level((Integer[][]) gson.fromJson(jsonReader, Integer[][].class));
    }
    public Player loadPlayer(String name) {

        try {
            FileReader fileReader =new FileReader("src/players.json");
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = parser.parse(fileReader).getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                Player person = gson.fromJson(jsonArray.get(i), Player.class);
                    if (person.getPlayerName().equals(name))
                        return person;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File is corrupted:"+e);

        }
        return null;
    }

    public ArrayList<Player>getAllPLayers()
    {
        try {
            ArrayList<Player> list = new ArrayList<>();
            FileReader fileReader =new FileReader("src/players.json");
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = parser.parse(fileReader).getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                Player person = gson.fromJson(jsonArray.get(i), Player.class);
                list.add(person);
            }
            return list;
        } catch (FileNotFoundException e) {
            System.out.println("File is corrupted:"+e);

        }
        return null;
    }
    public ArrayList<String> loadPlayerNames(){
        try {
            FileReader fileReader = new FileReader("src/players.json");
            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = jsonParser.parse(fileReader).getAsJsonArray();
            ArrayList<String> playerNames = new ArrayList<>(jsonArray.size());
            for (int i = 0; i < jsonArray.size(); i++){
                playerNames.add(jsonArray.get(i).getAsJsonObject().get("playerName").getAsString());
            }
            return playerNames;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }return null;
    }
}
