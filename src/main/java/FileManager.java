import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.*;
import com.google.gson.JsonArray;
import com.google.gson.*;
import com.google.gson.JsonParser;
import org.omg.CORBA.INTERNAL;

import java.io.FileNotFoundException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class FileManager {
    private Gson gson;
    public FileManager()  {
        gson = new Gson();
    }

    public Integer[][] loadLevels(int levelNo) throws FileNotFoundException{//load specific level
        JsonReader jsonReader = new JsonReader(new FileReader("src/level.json"));

        Integer[][][] levels = gson.fromJson(jsonReader, Integer[][][].class);
        return  levels[levelNo-1];
    }
    public Integer[][] loadSolution(int levelNo) throws FileNotFoundException{//load specific level
        JsonReader jsonReader = new JsonReader(new FileReader("src/solution.json"));
        Integer[][][] solutions = gson.fromJson(jsonReader, Integer[][][].class);

        return  solutions[levelNo-1];
    }

    boolean saveCustomBoard(int[][] board) throws IOException {
        String dir = System.getProperty("user.dir") + "/src/CustomBoards" ;
        File file = new File(dir);
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                return false;
            }
        }
        try {
            long count;
            try (Stream<Path> files = Files.list(Paths.get(dir))) {
                count = files.count();
            }
            count++;
            String boardName = "board_" + count;
            String filename = dir+"/"+boardName+".json";
            FileWriter fw = new FileWriter(filename);
            System.out.println(filename);
            fw.write(gson.toJson(board));
            fw.flush();
            fw.close();
            return true;
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
        /*

        int latest=0;
        try {
            FileReader fileReader =new FileReader("src/level.json");
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = parser.parse(fileReader).getAsJsonArray();
            for(int i=0;i<jsonArray.size();i++){
                    latest++;
                }
          //  JsonObject object = new JsonObject();
           // JsonPrimitive element = new JsonPrimitive(board);
         //   JsonElement element = new  JsonElement(board);
            JsonElement element =  gson.toJsonTree(board) ;
         //   object.add(element);
            jsonArray.add(element);
           System.out.print( jsonArray.toString());
            Writer writer =new FileWriter("src/level.json");
            String result = gson.toJson(jsonArray);
            writer.write(result);
            writer.close();
        }
        catch (FileNotFoundException e) {
        }
       return  false;

    */
    }

    public Player loadPlayer(String name) {
        try {
            FileReader fileReader = new FileReader("src/players.json");
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

    public ArrayList<Player> getAllPLayers() {
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

    public boolean saveANewPlayer(Player player) throws IOException {
        boolean succeed=false;
        try {
            FileReader fileReader =new FileReader("src/players.json");
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = parser.parse(fileReader).getAsJsonArray();
            for(int i=0;i<jsonArray.size();i++)
                if(jsonArray.get(i).getAsJsonObject().get("playerName").getAsString().equals(player.getPlayerName())) {
                    System.out.print("Wrong!");
                    return succeed;
                }
            JsonObject object = new JsonObject();
            JsonElement element = gson.fromJson (player.getPlayerName(), JsonElement.class);
            object.add("playerName",element );
            element = gson.fromJson (player.getHighScore()+"", JsonElement.class);
            object.add("highScore",element );
            element = gson.fromJson (player.getAccessibleLevel()+"", JsonElement.class);
            object.add("accessibleLevel",element );
            element = gson.fromJson(gson.toJson(player.getLatestBoard()),JsonElement.class);
            object.add("latestBoard",element );
            element = gson.fromJson(gson.toJson(player.getLatestTime()),JsonElement.class);
            object.add("latestTime",element );
            jsonArray.add(object);
            Writer writer =new FileWriter("src/players.json");
            String result = gson.toJson(jsonArray);
            writer.write(result);
            writer.close();
            succeed=true;

        }
        catch (FileNotFoundException e) {
        }
        return succeed;
    }

    public boolean updatePlayerInfoInFile(Player player) throws IOException {
        boolean succeed = false;
        try {
            FileReader fileReader = new FileReader("src/players.json");
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = parser.parse(fileReader).getAsJsonArray();
            String name = "";
            int i;
            for (i = 0; i < jsonArray.size(); i++) {
                name = jsonArray.get(i).getAsJsonObject().get("playerName").getAsString();
                if (name.equals(player.getPlayerName())) {
                    succeed = true;
                    break;
                }
            }
            if (succeed) {
                JsonObject object = jsonArray.get(i).getAsJsonObject();
                jsonArray.remove(object);

                JsonElement element = gson.fromJson(player.getPlayerName(), JsonElement.class);
                object.add("playerName", element);
                element = gson.fromJson(player.getHighScore() + "", JsonElement.class);
                object.add("highScore", element);
                element = gson.fromJson(player.getAccessibleLevel() + "", JsonElement.class);
                object.add("accessibleLevel", element);
                element = gson.fromJson(gson.toJson(player.getLatestBoard()), JsonElement.class);
                object.add("latestBoard", element);
                element = gson.fromJson(gson.toJson(player.getLatestTime()), JsonElement.class);
                object.add("latestTime", element);
                jsonArray.add(object);
                System.out.print(jsonArray);
            } else {
                JsonObject object = new JsonObject();
                JsonElement element = gson.fromJson(player.getPlayerName(), JsonElement.class);
                object.add("playerName", element);
                element = gson.fromJson(player.getHighScore() + "", JsonElement.class);
                object.add("highScore", element);
                element = gson.fromJson(player.getAccessibleLevel() + "", JsonElement.class);
                object.add("accessibleLevel", element);
                element = gson.fromJson(gson.toJson(player.getLatestBoard()), JsonElement.class);
                object.add("latestBoard", element);
                element = gson.fromJson(gson.toJson(player.getLatestTime()), JsonElement.class);
                object.add("latestTime", element);
                jsonArray.add(object);
                System.out.print(jsonArray);
            }

            Writer writer =new FileWriter("src/players.json");
            String result = gson.toJson(jsonArray);
            writer.write(result);
            writer.close();
            succeed=true;

        }
        catch (FileNotFoundException e) {
        }
        return succeed;
    }

    public HashMap< String,Integer>  getHighScores(){
        HashMap<String, Integer>  map = new HashMap<>();
        try {
            FileReader fileReader = new FileReader("src/players.json");
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = parser.parse(fileReader).getAsJsonArray();
            String name = "";
            Integer score = 0;
            for (int i = 0; i < jsonArray.size(); i++){
                name = jsonArray.get(i).getAsJsonObject().get("playerName").getAsString();
                score = jsonArray.get(i).getAsJsonObject().get("highScore").getAsInt();
                map.put(name,score);
            }
            return map;
        }
        catch (FileNotFoundException e) {
        }
        return null;
    }
}
