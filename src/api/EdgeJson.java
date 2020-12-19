package api;

import com.google.gson.*;

import java.lang.reflect.Type;
/**
 * this class implements  serializer for edge_data
 */
public class EdgeJson implements JsonSerializer<edge_data>
{
    /**
     * serilizer for edge_data
     * @param type
     * @param jsonSerializationContext
     * @param edge_data
     */
    @Override
    public JsonElement serialize(edge_data edge_data, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject obj=new JsonObject();
        obj.addProperty("src",edge_data.getSrc());
        obj.addProperty("w",edge_data.getWeight());
        obj.addProperty("dest",edge_data.getDest());
        return obj;
    }
}
