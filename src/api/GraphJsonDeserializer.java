package api;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.util.Map.Entry;
import java.util.jar.JarEntry;

public class GraphJsonDeserializer implements JsonDeserializer<directed_weighted_graph> {
    @Override
    public directed_weighted_graph deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        directed_weighted_graph g=new DWGraph_DS();
        JsonObject nodeJsonObject=jsonObject.get("nodeHash").getAsJsonObject();
        int key;
        double weight;
        int src;
        int dest;
        for(Entry<String,JsonElement> set: nodeJsonObject.entrySet())
        {
            JsonElement node=set.getValue();
            key=node.getAsJsonObject().get("key").getAsInt();
            node_data nodeData=new NodeData(key);
            g.addNode(nodeData);
        }
        JsonObject edgeJasonObject=jsonObject.get("edgeHashOut").getAsJsonObject();
        for(int i=0;i<edgeJasonObject.;i++)
            for(Entry<String,JsonElement> set: edgeJasonObject.get(i).getAsJsonObject().entrySet())
            {
            JsonElement edge= set.getValue();
            if (edge.getAsJsonObject().get("src") != null && edge.getAsJsonObject().get("w") != null && edge.getAsJsonObject().get("dest") != null) {
                src = edge.getAsJsonObject().get("src").getAsInt();
                dest = edge.getAsJsonObject().get("dest").getAsInt();
                weight = edge.getAsJsonObject().get("w").getAsDouble();
                g.connect(src, dest, weight);
            }
        }
        return g;
    }
}
