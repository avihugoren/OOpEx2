package api;

import com.google.gson.*;

import java.lang.reflect.Type;

public class NodeJson implements JsonDeserializer<node_data>, JsonSerializer<node_data> {
    @Override
    public node_data deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException
    {
        node_data node=new NodeData(jsonElement.getAsJsonObject().get("id").getAsInt());
        double x,y,z;
        String s=jsonElement.getAsJsonObject().get("pos").getAsString();
        String[] geo;
        geo=s.split(",");
        x=Double.parseDouble(geo[0]);
        y=Double.parseDouble(geo[1]);
        z=Double.parseDouble(geo[2]);
        node.setLocation(new GeoLocation(x,y,z));

        return node;
    }

    @Override
    public JsonElement serialize(node_data node_data, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject obj=new JsonObject();
        obj.addProperty("pos",node_data.getLocation().toString());
        obj.addProperty("id",node_data.getKey());
        return obj;
        };
    }

