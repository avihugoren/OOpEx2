package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
//import com.sun.tools.jdi.resources.jdi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.List;

public class DWGraph_Algo implements dw_graph_algorithms
{
    directed_weighted_graph myGraph=new DWGraph_DS();
    public DWGraph_Algo(directed_weighted_graph g)
    {
        myGraph=g;
    }
    @Override
    public void init(directed_weighted_graph g) {

    }

    @Override
    public directed_weighted_graph getGraph() {
        return myGraph;
    }

    @Override
    public directed_weighted_graph copy() {
        return null;
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public boolean save(String file)
    {
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        String json=gson.toJson(myGraph);
        //Write json to file
        try {
            PrintWriter pw=new PrintWriter(new File(file));
            pw.write(json);
            pw.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
            return false;
    }
        return true;
    }

    @Override
    public boolean load(String file) {
        try {
            GsonBuilder builder=new GsonBuilder();
            builder.registerTypeAdapter(directed_weighted_graph.class, new GraphJsonDeserializer());
            {
                Gson json = builder.create();
           FileReader reader= new FileReader(file);
           directed_weighted_graph g= json.fromJson(reader,directed_weighted_graph.class);
           myGraph=g;
        }
        }
        catch (FileNotFoundException fileNotFoundException) {
            System.out.println("file not found");
            return false;
        }
        return true;
    }



    }
