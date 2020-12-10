package gameClient;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class AgentJason implements JsonSerializer<CL_Agent> {
    @Override
    public JsonElement serialize(CL_Agent cl_agent, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject obj=new JsonObject();
        obj.addProperty("id",cl_agent.getID());
        obj.addProperty("value",cl_agent.getValue());
        obj.addProperty("src",cl_agent.getSrcNode());
        obj.addProperty("dest",cl_agent.getNextNode());
        obj.addProperty("speed",cl_agent.getSpeed());
        obj.addProperty("pos",cl_agent.getLocation().toString());

        return obj;
    }
}
