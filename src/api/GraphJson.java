package api;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.util.Map.Entry;
import java.util.jar.JarEntry;

public class GraphJson implements JsonDeserializer<directed_weighted_graph>,JsonSerializer<directed_weighted_graph> {
    @Override
    public directed_weighted_graph deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        directed_weighted_graph g=new DWGraph_DS();
        JsonArray nodesArray=jsonObject.get("Nodes").getAsJsonArray();
        NodeJson nodeJson=new NodeJson();
        for (int i = 0; i < nodesArray.size(); i++)
            g.addNode(nodeJson.deserialize(nodesArray.get(i),type,jsonDeserializationContext));
        double w;
        int src;
        int dest;
        JsonArray edgesArray=jsonObject.getAsJsonArray("Edges");
        for (int i = 0; i <edgesArray.size() ; i++)
        {

                w = edgesArray.get(i).getAsJsonObject().get("w").getAsDouble();
                dest = edgesArray.get(i).getAsJsonObject().get("dest").getAsInt();
                src = edgesArray.get(i).getAsJsonObject().get("src").getAsInt();
                g.connect(src, dest, w);
        }
        return g;

    }

    @Override
    public JsonElement serialize(directed_weighted_graph directed_weighted_graph, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject=new JsonObject();
        NodeJson nodeJson=new NodeJson();
        EdgeJson edgeJson=new EdgeJson();
        JsonArray nodeArray=new JsonArray(directed_weighted_graph.nodeSize());
        JsonArray edgeArray=new JsonArray(directed_weighted_graph.edgeSize());
        for (node_data node : directed_weighted_graph.getV()) {
            nodeArray.add(nodeJson.serialize(node, type, jsonSerializationContext));
            for(edge_data edge :directed_weighted_graph.getE(node.getKey()))
                edgeArray.add(edgeJson.serialize(edge, type, jsonSerializationContext));
        }
        jsonObject.add("Edges",edgeArray);
        jsonObject.add("Nodes",nodeArray);
        return jsonObject;
    }
}
