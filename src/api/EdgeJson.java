package api;

import com.google.gson.*;

import java.lang.reflect.Type;

public class EdgeJson implements JsonSerializer<edge_data>{
    @Override
    public JsonElement serialize(edge_data edge_data, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject obj=new JsonObject();
        obj.addProperty("src",edge_data.getSrc());
        obj.addProperty("w",edge_data.getWeight());
        obj.addProperty("dest",edge_data.getDest());
        return obj;
    }
}
