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
        JsonArray nodesArray=jsonObject.get("Nodes").getAsJsonArray();//gets the array of the nodes
        NodeJson nodeJson=new NodeJson();// create NodeJson so i can read the nodes with my deserialize
        for (int i = 0; i < nodesArray.size(); i++)
            //add the nodes from the json array to the graph
            g.addNode(nodeJson.deserialize(nodesArray.get(i),type,jsonDeserializationContext));
        double w;
        int src;
        int dest;
        JsonArray edgesArray=jsonObject.getAsJsonArray("Edges");
        //for loop that reads the jsonEdges array and create this nodes in the graph by connecting src and dest with the weight from the json
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
        //creates nodeJson and edgeJson so i can add them to the object with my serialize
        NodeJson nodeJson=new NodeJson();
        EdgeJson edgeJson=new EdgeJson();
        JsonArray nodeArray=new JsonArray(directed_weighted_graph.nodeSize());//json array for the nodes
        JsonArray edgeArray=new JsonArray(directed_weighted_graph.edgeSize());//json array for the edges
        for (node_data node : directed_weighted_graph.getV())
        {
            nodeArray.add(nodeJson.serialize(node, type, jsonSerializationContext));//add each node to the array with my node serialize
            for(edge_data edge :directed_weighted_graph.getE(node.getKey()))
                edgeArray.add(edgeJson.serialize(edge, type, jsonSerializationContext));//add each edge to the array with my edge serialize
        }
        //adds the arrays to the graph objects
        jsonObject.add("Edges",edgeArray);
        jsonObject.add("Nodes",nodeArray);
        return jsonObject;
    }
}
