package goldenshadow.warp;

import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.lang.reflect.Type;

public class LocationTypeAdapter implements JsonSerializer<Location>, JsonDeserializer<Location> {
    @Override
    public Location deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        double x = jsonObject.get("x").getAsDouble();
        double y = jsonObject.get("y").getAsDouble();
        double z = jsonObject.get("z").getAsDouble();
        String worldName = jsonObject.get("worldName").getAsString();
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            throw new JsonParseException("Invalid world name: " + worldName);
        }
        return new Location(world, x, y, z);
    }

    @Override
    public JsonElement serialize(Location location, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("x", location.getX());
        jsonObject.addProperty("y", location.getY());
        jsonObject.addProperty("z", location.getZ());
        if (location.getWorld() == null) {
            throw new RuntimeException("World was null!");
        }
        jsonObject.addProperty("worldName", location.getWorld().getName());
        return jsonObject;
    }
}
