package api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class DWGraph_Algo implements dw_graph_algorithms
{
    directed_weighted_graph myGraph=new DWGraph_DS();
    public DWGraph_Algo(directed_weighted_graph g)
    {
        myGraph=g;
    }

    @Override
    public void init(directed_weighted_graph g) {
        myGraph=g;
    }

    @Override
    public directed_weighted_graph getGraph()
    {
        return myGraph;
    }

    @Override
    public directed_weighted_graph copy() {
        return new DWGraph_DS(myGraph);
    }
    private HashMap<Integer, NodeAlgo> Dijkstra(int src) {
        HashMap<Integer, NodeAlgo> nodes = new HashMap<Integer, NodeAlgo>();
        for (node_data node : myGraph.getV())
            nodes.put(node.getKey(), new NodeAlgo(node.getKey(), Double.MAX_VALUE, null));

        PriorityQueue<NodeAlgo> help = new PriorityQueue<NodeAlgo>();
        NodeAlgo n;
        double m;
        nodes.get(src).setWeight(0);
        nodes.get(src).setParent(null);
        help.add(nodes.get(src));
        while (!help.isEmpty()) {
            n = help.poll();
            for (edge_data ni : myGraph.getE(n.getKey()))//go over his neighbors
            {
                m = n.getWeight() + ni.getWeight();
                if (nodes.get(ni.getDest()).getWeight() > m) {
                    nodes.get(ni.getDest()).setWeight((m));
                    nodes.get(ni.getDest()).setParent(myGraph.getNode(n.getKey()));
                    help.add(nodes.get(ni.getDest()));
                }
            }
        }
        return nodes;
    }
    private void bfs(int src,boolean b)
    {
        if ((myGraph.getV().contains(myGraph.getNode(src)) == false))
            return;
        for(node_data node:myGraph.getV())
            node.setTag(-1);
        Queue<node_data> help = new LinkedList<node_data>();
        node_data node = myGraph.getNode(src);
        help.add(node);
        node.setTag(0);
        while (!help.isEmpty()) {
            node = help.poll();
            for(edge_data edge:myGraph.getE(node.getKey()))
            {
                if(b==true) {
                    if (myGraph.getNode(edge.getDest()).getTag() == -1) {
                        myGraph.getNode(edge.getDest()).setTag(0);
                        help.add(myGraph.getNode(edge.getDest()));
                    }
                }
                else{
                    if(myGraph.getEdge(edge.getDest(),edge.getSrc())!=null)
                    {
                        if (myGraph.getNode(edge.getDest()).getTag() == -1)
                        {
                        myGraph.getNode(edge.getDest()).setTag(0);
                        help.add(myGraph.getNode(edge.getDest()));
                    }
                    }
                }
                }
            }
        }
    @Override
    public boolean isConnected() {
        if(myGraph==null)
            return true;
        if (myGraph.nodeSize() == 0 || myGraph.nodeSize() == 1)
            return true;
        Iterator<node_data> iter1 = myGraph.getV().iterator();
        node_data node1 = iter1.next();
        bfs(node1.getKey(),true);
        for(node_data node: myGraph.getV())
            if (node.getTag()==-1)
                return false;
        bfs(node1.getKey(),false);
        for(node_data node: myGraph.getV())
            if (node.getTag()==-1)
                return false;
      return true;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        if(myGraph==null)
            return -1;
        if((myGraph.getV().contains(myGraph.getNode(src)) == false) || (myGraph.getV().contains(myGraph.getNode(dest)) == false))
            return -1;
        HashMap<Integer,NodeAlgo> help=Dijkstra(src);
        double dist=help.get(dest).getWeight();
        if(dist==Double.MAX_VALUE)//no such path
            return -1;
        return dist;
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        if(myGraph==null)
            return null;
        if (myGraph.getV().contains(myGraph.getNode(src)) == false || myGraph.getV().contains(myGraph.getNode(dest)) == false)
            return null;
        Stack<node_data> path = new Stack<node_data>();
        HashMap <Integer,NodeAlgo> g = Dijkstra(src);
        if(g.get(dest).getWeight()==Double.MAX_VALUE)
            return path;
        node_data parent = g.get(dest).getParent();
        path.push(myGraph.getNode(dest));
        while (parent != null) {
            path.push(parent);
            parent = g.get(parent.getKey()).getParent();
        }
        Stack<node_data> help = new Stack<node_data>();
        while(!path.isEmpty())
            help.push(path.pop());
        return help;
    }
    @Override
    public boolean save(String file)
    {

        Gson gson= new GsonBuilder().registerTypeAdapter(DWGraph_DS.class,new GraphJson()).create();
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
      if(myGraph==null)
            return false;
        try {
            GsonBuilder builder=new GsonBuilder();
            builder.registerTypeAdapter(directed_weighted_graph.class, new GraphJson());
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
