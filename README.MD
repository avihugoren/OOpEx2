This project divided into 2 parts: 
The first part is about developing a data structure of a directional weighted graph.
On This part we implemented 6 interfaces: node_data, directed_weighted_graph, dw_graph_algorithms, geo_location,edge_data.
1.NodeData implements node_data-in this class we can get and set the nodes information.
2.NodeAlgo-we add this class so we can save the parent and the weight of each node this class will be used in the Dijkstra algorithms.
3.EdgeData implements edge_data-in this class we can get and set the edges information.
4.DWGraph_DS implements directed_weighted_graph-
This class represent a directed weighted graph the main data structure that we used to implement this class is Hashmap because its ability to perform get and contain actions in constant time.  
The first HashMap named nodeHash is used to hold all the nodes that belong to the graph.
The second Hashmap named edgesHashOut this hash map hold all the nodes keys and for each key its value is HashMap that hold all the edges that coming out of this node. 
The third HashMap named edgesHashIn holding all the nodes keys and for each key its value is hash map that represent all the nodes that have edges towards the nodes with that key.
In this class we can perform many actions such as getNode, getEdge, connect, removeNode, removeEdge, getV(return collection of all the nodes in the graph),getE(return collection of all the edges coming out of a specific node),
copy Constractur,nodeSize,edgeSize,getMc(return how many changes happened in the graph),equals and toString.
5.DWGraph_Algo implement dw_graph_algorithms-
This class have field named myGraph that holds a directed weighted graph and we preform all the actions and algorithms on this graph.
This class has 2 constructors empty one and the second get graph and work on him.
The functions in this class are:
-init: init the directed weighted graph that this class operate on    
-getGraph: return the graph that this class operate on
-copy: return a deep copy of the graph (using the copy constractur of DWGraph_DS).  
-isConnected: return true if the graph is strongly connected false if otherwise using bfs algorithm. 
-shortestPathDis: get 2 nodes and return the shortest path weight between them using Dijkstra algorithm.  
-shortestPath: get 2 nodes and return list that contain all the nodes that are in the shortest path between them using Dijkstra algorithm. 
-save save the graph to file using Json format.
-load: load a graph from a file.
Dijkstra:
This algorithm using the NodeAlgo class because we want to save the weight and the parent for each node.
This algorithms get src and set for each nodeAlgo his wieght of the shortest past between src to him. This algorithm used PriorityQueue which compares by weight that in the nodeAlgo.
Bfs:
This algorithm gets node and Boolean b that tell him which way to look at the edges.
The algorithms set the tag of each node that have a path from src to 0 if there is not path the tag of the node is -1.
To check if the graph is connected, we call the bfs from one node and check that all the other nodes tag is 0 if not return false otherwise we check again with upside down edges with the same src.
6.GeoLocation implements geo_location-This class have 3 field-x, y, z.
The function in this class is get x, y, z, we can get the distance between 2 GeoLocation and equals 2 GeoLocation nodes.

The second  part is about creating a game on the directed weighted graph the game name is the pokemons game.
On the game we have agents and pokemons and the agents needs to catch as many pokemon as possible the more the agents catch the score go higher.
The game is managed by a server and the communication with it is done by the game client interface.The information is sent from the server in json configuration.
In addition, we implemented a graphical interface on which the game can be displayed, where you can see the movement of the agents and their score on the graph, as well as the location of the Pokemon and their value.
We will split the explanation into 2 parts, the first part on the management of the game itself and the second part on the graphical interface.

In the part of game management there are several classes the first class is

myClient : 
This class is the one that actually mange and runs the game Its functions are :
-run: This function receives the information about the game from the server. This information contains the game stage, the number of Pokemon, the number of agents, the duration of the game, and then it receives from the server the graph on which the stage takes place in the json format.
after receiving the graph, she builds it and then calls the init function whose job is to initialize the game on the graph i.e. place the pokemon and agents on the graph and also activates the graphical interface.
As long as the game continues it calls the moveAgent function whose job is to manage the movement of agents on the graph in a smart way.
The moveAgent function moves the agents with the nextNode function that selects each agent the next node it needs to go to.
The nextNode function ensures that each agent goes to a different Pokemon based on the shortest path algorithm, the pokemon value and also the agent speed.
The game is divided into 3 time periods when in each part the number of moves per second increases, which means that at the beginning there are relatively few moves then as time progresses the number of moves increases.

Arena:
This class actually helps run the game and she holds inside the graph and also a list of pokemons and a list of agents
it has a number of functions like setAgent which gives a list of agents to keep in it ,setPokemon,getAgents that return list of all the agent in the game and getPokemons that return list of all the pokemons in the game in addition it has a the function json2Pokemons that converts the json string to pokemon and the same also to agents.
In addition it has the updateEdge function which gets pokemon and a graph ans put the pokemon on the right edge according to the pokemon geoLocation.

Cl_Agent : this class actually represents an agent in the game it has several fields like myPokemeon which represents the Pokemon the agent intends to go to it also has a field of the agent value which is basically the values of all the Pokemon he ate the agent speed and more
this class contain many functions such as update that receive a Jason string of agent and update the agent according to the data in the string,getLocation return the geo location of the agent ,getNextNode return the dest of the agent if its not moving right now return -1 and much more


Cl_Pokemon :this class represents a pokemon In a game Pokemon has several fields such as myAgent which represents the agent on its way to the Pokemon,dist which represents the distance of the agent closest to the Pokemon , it has also _edge field which represents the edge on which the Pokemon is,_pos which represents the Pokemon location  and more.
This class has a number of functions like setAgent that gets an agent and its distance from the Pokemon and updates these fields, getEdge that returns the edge on which the Pokemon is,getLocation,update that gets another Pokemon and updates the agent and the distance in the current Pokemon, equals that equals 2 Pokemon based on location, compareTo Which sort Pokemons based on their value from highest to lowest. 

The graphical interface has 2 parts Part 1 is the opening screen where you can enter id and level number between 0 and 23  and part two on which the game is seen
The 2 parts are made up of different frames. Each frame has a suitable panel feature where we basically draw what we want to be displayed on the screen to the frame of the game itself. We also add the time panel to count the game time.


 
















   

 


 

.
