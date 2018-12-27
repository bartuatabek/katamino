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
    public int levelNum() throws FileNotFoundException{//load specific level
        JsonReader jsonReader = new JsonReader(new FileReader("src/level.json"));

        Integer[][][] levels = gson.fromJson(jsonReader, Integer[][][].class);
        return  levels.length;
    }
    public Integer[][] loadSolution(int levelNo) throws FileNotFoundException{//load specific level
        JsonReader jsonReader = new JsonReader(new FileReader("src/solution.json"));
        Integer[][][] solutions = gson.fromJson(jsonReader, Integer[][][].class);

        return  solutions[levelNo-1];
    }

    boolean saveCustomBoard(int[][] board) throws IOException {
        ArrayList<Integer> ıdList=new ArrayList<>();
ArrayList<ArrayList< Integer> > allInfo= new ArrayList<>();
        for(int x=0;x<board.length;x++)
        {
            for(int k=0;k<board[x].length;k++)
            {
                if((board[x][k]>0)) {
                    if((!(ıdList.contains(board[x][k]))&&(board[x][k]!=0)))
                        ıdList.add(board[x][k]);
                    ArrayList<Integer> min =new ArrayList<>();
                    min.add(board[x][k]);
                    min.add(x);
                    min.add(k);
                    allInfo.add(min);
                    board[x][k]=0;
                }

            }
        }

                ArrayList< ArrayList<Integer>> friends = new ArrayList<>();
                    friends.add(( allInfo.get(0)));
                    int i = 0;
                    for(int r=0;r<ıdList.size();r++) {
                        int currentID = ıdList.get(r);
                        // int connection=0;

                        for (int p = 0; p < allInfo.size(); p++) {
                            if (allInfo.get(p).get(0) == currentID)
                                friends.add(allInfo.get(p));
                        }
                        boolean movable=true;
                        boolean movable2=true;
                        boolean movable3=true;
                        boolean movable4=true;
                        boolean couldNotCheck=false;
                        boolean couldNotCheck2=false;
                        boolean couldNotCheck3=false;
                        boolean couldNotCheck4=false;
                        boolean movable6=true;
                        boolean couldNotCheck6=false;
                        boolean movable5=true;
                        boolean couldNotCheck5=false;
                        boolean movable8=true;
                        boolean couldNotCheck8=false;
                        boolean movable7=true;
                        boolean couldNotCheck7=false;
                        boolean movable9=true;
                        boolean couldNotCheck9=false;
                        boolean movable10=true;
                        boolean couldNotCheck10=false;
                        for(ArrayList<Integer> all:friends)
                        {
                            System.out.println(all);
                            if(((all.get(1)+8)<11)&&((all.get(2)+8)<22)){
                                if(board[all.get(1)+8][all.get(2)+8]!=-1)
                                    movable3=false;
                            }
                            else
                                couldNotCheck3=true;

                            if(((all.get(1)+6)<11)&&((all.get(2)+6)<22)){
                                if(board[all.get(1)+6][all.get(2)+6]!=-1)
                                    movable5=false;
                            }
                            else
                                couldNotCheck5=true;
                            if(((all.get(1)-6)>0)&&((all.get(2)-6)>0)){
                                if(board[all.get(1)-6][all.get(2)-6]!=-1)
                                    movable6=false;
                            }
                            else
                                couldNotCheck6=true;


                            if(((all.get(1)-8)>0)&&((all.get(2)-8)>0)){
                                if(board[all.get(1)-8][all.get(2)-8]!=-1)
                                    movable4=false;
                            }
                            else
                                couldNotCheck4=true;

                            if(((all.get(1)+3)<11)&&((all.get(2)+3)<22)){
                                if(board[all.get(1)+3][all.get(2)+3]!=-1)
                                    movable=false;
                            }
                            else
                                couldNotCheck=true;

                            if(((all.get(1)-3)>0)&&((all.get(2)-3)>0)){
                            if(board[all.get(1)-3][all.get(2)-3]!=-1)
                                 movable2=false;
                        }
                            else
                            couldNotCheck2=true;


                            if(((all.get(1)-2)>0)&&((all.get(2)-2)>0)){
                                if(board[all.get(1)-2][all.get(2)-2]!=-1)
                                    movable8=false;
                            }
                            else
                                couldNotCheck8=true;



                            if(((all.get(1)+2)>11)&&((all.get(2)+2)>22)){
                                if(board[all.get(1)+2][all.get(2)+2]!=-1)
                                    movable7=false;
                            }
                            else
                                couldNotCheck7=true;

                            if(((all.get(1)-1)>11)&&((all.get(2)-1)>22)){
                                if(board[all.get(1)-1][all.get(2)-1]!=-1)
                                    movable9=false;
                            }
                            else
                                couldNotCheck9=true;
                            if(((all.get(1)+1)>11)&&((all.get(2)+1)>22)){
                                if(board[all.get(1)+1][all.get(2)+1]!=-1)
                                    movable10=false;
                            }
                            else
                                couldNotCheck10=true;


                        }
                        System.out.println("CurrentId:" +currentID);
                        for(ArrayList<Integer> all:friends) {
                            if (movable3&&(!couldNotCheck3)) {
                                board[all.get(1)+8][all.get(2)+8]=currentID;
                                 }
                                 else if(movable4&&(!couldNotCheck4))
                            {
                                board[all.get(1)-8][all.get(2)-8]=currentID;
                            }
                            else if (movable5&&(!couldNotCheck5)) {
                                board[all.get(1)+6][all.get(2)+6]=currentID;
                            }
                            else if(movable6&&(!couldNotCheck6))
                            {
                                board[all.get(1)-6][all.get(2)-6]=currentID;
                            }
                            else if (movable&&(!couldNotCheck)) {
                                board[all.get(1)+3][all.get(2)+3]=currentID;

                            }
                            else if(movable2&&(!couldNotCheck2))
                            {
                                board[all.get(1)-3][all.get(2)-3]=currentID;
                            }
                            else if(movable8&&(!couldNotCheck8))
                            {
                                board[all.get(1)-2][all.get(2)-2]=currentID;
                            }
                            else if(movable7&&(!couldNotCheck7))
                            {
                                board[all.get(1)+2][all.get(2)+2]=currentID;
                            }
                            else if(movable9&&(!couldNotCheck9))
                            {
                                board[all.get(1)-1][all.get(2)-1]=currentID;
                            }
                            else if(movable10&&(!couldNotCheck10))
                            {
                                board[all.get(1)+1][all.get(2)+1]=currentID;
                            }
                            else
                                System.out.println("OLMADI");
                        }
                        friends=new ArrayList<>();
                    }
/*

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
    */

        int latest=0;
        try {
            FileReader fileReader =new FileReader("src/level.json");
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = parser.parse(fileReader).getAsJsonArray();
            for(int p=0;p<jsonArray.size();p++){
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
