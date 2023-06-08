package goldenshadow.warp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Location;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataManager {

    private static HashMap<String, Location> warpMap = new HashMap<>();


    public static void saveToFile() {
        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Location.class, new LocationTypeAdapter())
                    .create();
            File file = new File(Warp.getPlugin().getDataFolder().getAbsolutePath() + "/warps.json");

            file.getParentFile().mkdir();
            file.createNewFile();

            Writer writer = new FileWriter(file, false);
            gson.toJson(warpMap, writer);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadFromFile() {
        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Location.class, new LocationTypeAdapter())
                    .create();
            File file = new File(Warp.getPlugin().getDataFolder().getAbsolutePath() + "/warps.json");
            if (file.exists()) {
                Reader reader = new FileReader(file);
                Type type = new TypeToken<HashMap<String, Location>>(){}.getType();
                warpMap = gson.fromJson(reader, type);
                if (warpMap == null) warpMap = new HashMap<>();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean doesWarpExist(String s) {
        return warpMap.containsKey(s);
    }

    public static Location getLocation(String s) {
        return warpMap.getOrDefault(s, null);
    }

    public static void addLocation(String s, Location location) {
        warpMap.put(s, location);
    }

    public static void removeLocation(String s) {
        warpMap.remove(s);
    }

    public static List<String> getKeys() {
        return new ArrayList<>(warpMap.keySet());
    }

    public static void renameKey(String originalName, String newName) {
        if (warpMap.containsKey(originalName)) {
            Location location = warpMap.get(originalName);
            warpMap.remove(originalName);
            warpMap.put(newName, location);
        } else throw new RuntimeException("The key to rename doesn't exist!");
    }
}
