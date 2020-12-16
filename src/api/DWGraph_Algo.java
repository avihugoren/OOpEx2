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

    /**
     * constructor that get graph and put it in myGraph
     * @param g
     */
    public DWGraph_Algo(directed_weighted_graph g)
    {
        myGraph=g;
    }

    /**
     * constructor
     */
    public DWGraph_Algo()
    {

    }

    /**
     * Init the graph on which this set of algorithms operates on
     * @param g
     */
    @Override
    public void init(directed_weighted_graph g) {
        myGraph=g;
    }

    /**
     * Return the underlying graph of which this class works
     * @return
     */
    @Override
    public directed_weighted_graph getGraph()
    {
        return myGraph;
    }

    /**
     * Compute a deep copy of this weighted graph-call the copy constructor of DWGraph_DS with myGraph
     * @return
     */
    @Override
    public directed_weighted_graph copy()
    {
        return new DWGraph_DS(myGraph);
    }

    /**
     *help function that calculates for each node the weight of the shortestPath between src to him and save it in the NodeAlgo weight
     * @param src
     * @return HashMap of NodeAlgo with their parents so that we can reconstruct the path,key for get the NOdeDate and weight of the short path
     */
     HashMap<Integer, NodeAlgo> Dijkstra(int src) {
        HashMap<Integer, NodeAlgo> nodes = new HashMap<Integer, NodeAlgo>();
        for (node_data node : myGraph.getV())//take the key from each node in the graph and creat new NodeAlgo with this key,parent null and weight infinity and add the NodeAlgo to the HashMap
            nodes.put(node.getKey(), new NodeAlgo(node.getKey(), Double.MAX_VALUE, null));

        PriorityQueue<NodeAlgo> help = new PriorityQueue<NodeAlgo>();
        NodeAlgo n;
        double m;
        nodes.get(src).setWeight(0);//the shortPath node to himself is 0
        nodes.get(src).setParent(null);
        help.add(nodes.get(src));
        while (!help.isEmpty()) {
            n = help.poll();//take the NodeAlgo with the smallest weight in the PriorityQueue
            for (edge_data ni : myGraph.getE(n.getKey()))//go over his neighbors
            {
                m = n.getWeight() + ni.getWeight();
                //if this neighbor have bigger tag, update his tag and his parent and add him to the PriorityQueue(There is a shorter path from src to him)
                if (nodes.get(ni.getDest()).getWeight() > m) {
                    nodes.get(ni.getDest()).setWeight((m));
                    nodes.get(ni.getDest()).setParent(myGraph.getNode(n.getKey()));
                    help.add(nodes.get(ni.getDest()));
                }
            }
        }
        return nodes;
    }

    /**
     *help function which set in  each node tag if there is a path from src to him
     * If there is -1 in the tag there is no route
     * @param src
     * @param b-if false we look at the edges upside down
     */
    private void bfs(int src,boolean b)
    {
        if ((myGraph.getV().contains(myGraph.getNode(src)) == false))//if the node not in the graph
            return;
        for(node_data node:myGraph.getV())//set each node tag to -1
            node.setTag(-1);
        Queue<node_data> help = new LinkedList<node_data>();
        node_data node = myGraph.getNode(src);
        help.add(node);
        node.setTag(0);
        while (!help.isEmpty()) {
            node = help.poll();
            for(edge_data edge:myGraph.getE(node.getKey()))//go over his neighbors
            {
                //look at the edges as they are
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

    /**
     * Returns true if and only if there is a valid path from each node to each other node.
     * @return
     */
    @Override
    public boolean isConnected() {
        if(myGraph==null)
            return true;
        if (myGraph.nodeSize() == 0 || myGraph.nodeSize() == 1)//empty graph and graph with one node is connected
            return true;
        Iterator<node_data> iter1 = myGraph.getV().iterator();
        node_data node1 = iter1.next();//take a node from the graph
        bfs(node1.getKey(),true);//Check if it is possible to get from this node1 to the rest of the nodes in the graph
        for(node_data node: myGraph.getV())
            if (node.getTag()==-1)
                return false;
        bfs(node1.getKey(),false);//Reversing the sides of the graph edges and check if it is still possible to reach each node
        for(node_data node: myGraph.getV())
            if (node.getTag()==-1)
                return false;
      return true;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        if(myGraph==null)
            return -1;
        if((myGraph.getV().contains(myGraph.getNode(src)) == false) || (myGraph.getV().contains(myGraph.getNode(dest)) == false))//if src or dest not in the graph
            return -1;
        HashMap<Integer,NodeAlgo> help=Dijkstra(src);//update each NodeAlgo weight to be the distance from src
        double dist=help.get(dest).getWeight();
        if(dist==Double.MAX_VALUE)//no such path
            return -1;
        return dist;
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        if(myGraph==null)
            return null;
        if (myGraph.getV().contains(myGraph.getNode(src)) == false || myGraph.getV().contains(myGraph.getNode(dest)) == false)//if src or dest not in the graph
            return null;
        HashMap <Integer,NodeAlgo> g = Dijkstra(src);//return HashMap of NodeAlgo
        if(g.get(dest).getWeight()==Double.MAX_VALUE)//no path from src to dest
            return null;
        Stack<node_data> path = new Stack<node_data>();
        node_data parent = g.get(dest).getParent();//each NodeAlgo save his parent
        path.push(myGraph.getNode(dest));
        //Finds the path according to the parent of each NodeAlgo
        while (parent != null) {
            path.push(parent);
            parent = g.get(parent.getKey()).getParent();
        }
        Stack<node_data> help = new Stack<node_data>();
        while(!path.isEmpty())//flip the stack
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
