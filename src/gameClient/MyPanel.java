package gameClient;

import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.List;

public class MyPanel  extends JPanel {

    private Arena _ar;
    private gameClient.util.Range2Range _w2f;

    public MyPanel(Arena ar)
    {
        super();
        this.setBackground(new Color(141, 142, 146));
        this._ar=ar;
        updateFrame();
    }

    /**
     *A function that turns the size of the graph to the size that will be fit to the panel.
     */
    private void updateFrame() {
        Range rx = new Range(20,this.getWidth()-20);
        Range ry = new Range(this.getHeight()-10,150);
        Range2D frame = new Range2D(rx,ry);
        directed_weighted_graph g = _ar.getGraph();
        _w2f = Arena.w2f(g,frame);
    }



    /**
     * A function that draw all the graphics to the panel
     *  @param g
     **/

   protected void paintComponent(Graphics g) {
        int w = this.getWidth();
        int h = this.getHeight();
        g.clearRect(0, 0, w, h);//clear all the panel
        updateFrame();
        super.paintComponent(g);
        drawBackgrounds(g);
        drawGraph(g);
        drawPokemons(g);
        drawAgents(g);

   }

    /**
     * Adds the background images
     * @param g
     */
    private void drawBackgrounds(Graphics g) {

        Image im=getToolkit().getImage("resources/pokemon1.png");
        g.drawImage(im,(int)(this.getWidth()/2-300),0,null);
        Image im1=new ImageIcon("resources/pikachu.png").getImage();
        g.drawImage(im1,(this.getWidth()/8)*6,this.getHeight()/3*2,null);
        Image im2=new ImageIcon("resources/back.png").getImage();
        g.drawImage(im2,this.getWidth()/8,this.getHeight()/3+30,null);
    }

    /**
     *add all the nodes and the edges to the panel
     * @param g
     */

    private void drawGraph(Graphics g) {
        directed_weighted_graph gg = _ar.getGraph();
        Iterator<node_data> iter = gg.getV().iterator();
        //Goes over all the nodes in the graph
        while (iter.hasNext()) {
            node_data n = iter.next();
            g.setColor(Color.BLACK);
            drawNode(n, 5, g);
            Iterator<edge_data> itr = gg.getE(n.getKey()).iterator();
            //for each node go over his neighbors
                while (itr.hasNext()) {
                    edge_data e = itr.next();
                    g.setColor(Color.BLACK);
                    drawEdge(e, g);
                }
            }
        }

    /**
     * add all the pokemons to the panel
     * @param g
     */
    private void drawPokemons(Graphics g) {
        java.util.List<CL_Pokemon> fs = _ar.getPokemons();
        if(fs!=null) {
            Iterator<CL_Pokemon> itr = fs.iterator();
            //go over all the pokemons
            while(itr.hasNext()) {
                CL_Pokemon f = itr.next();
                Point3D c = f.getLocation();
                Image im1=new ImageIcon("resources/bal.png").getImage();
                //if the pokemon on edge that the src is bigger then the dest put a picture of a different Pokemon
                if(f.getType()<0)
                    im1=new ImageIcon("resources/pokemon1.gif").getImage();
                if(c!=null) {
                    geo_location fp = this._w2f.world2frame(c);
                    g.drawImage(im1,(int)fp.x()-24/2,(int)fp.y()-27/2,null);//draw the image in the pokemon location
                    g.setColor(Color.ORANGE);
                    g.setFont(new Font("NSimSum",Font.BOLD,12));
                    g.drawString(""+f.getValue(),(int)fp.x()-24/2,(int)fp.y()-15);//add above each pokemon his value
//
                }
            }
        }
    }

    /**
     * add all  agents to the panel
     * @param g
     */
    private void drawAgents(Graphics g) {
        List<CL_Agent> rs = _ar.getAgents();
        int i=0;
        //Goes through all the agents
        while(rs!=null && i<rs.size()) {
            geo_location c = rs.get(i).getLocation();
            i++;
            if(c!=null) {
                Image im1=new ImageIcon("resources/hash.gif").getImage();
                geo_location fp = this._w2f.world2frame(c);
                g.drawImage(im1,(int)fp.x()-12,(int)fp.y()-20,null);//take the location of the agent and draw im1 in this location
                g.setColor(Color.ORANGE);
                g.setFont(new Font("NSimSum",Font.BOLD,20));
                g.drawString("Agent "+rs.get(i-1).getID()+" grade: "+rs.get(i-1).getValue(),this.getWidth()-200,i*20);//add the grade of the agent


                // g.drawString(""+rs.get(i-1).getID()+" "+rs.get(i-1).getValue()+" "+rs.get(i-1).getNextNode()+" "+rs.get(i-1).getSpeed(), (int)fp.x()-12, (int)fp.y()-20);
            }
        }
    }



    /**
     * help function that receives node and size and draws a oval in this node with the size she received
     * @param n,r,g
     */
    private void drawNode(node_data n, int r, Graphics g) {
        geo_location pos = n.getLocation();
        geo_location fp = this._w2f.world2frame(pos);
        g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
        g.drawString(""+n.getKey(), (int)fp.x(), (int)fp.y()-4*r);//add the node key above the node
    }

    /**
     * help function that gets an edge and draws a line in the appropriate place
     * @param e,g
     */
    private void drawEdge(edge_data e, Graphics g) {
        directed_weighted_graph gg = _ar.getGraph();
        geo_location s = gg.getNode(e.getSrc()).getLocation();
        geo_location d = gg.getNode(e.getDest()).getLocation();
        geo_location s0 = this._w2f.world2frame(s);
        geo_location d0 = this._w2f.world2frame(d);
        Graphics2D g1=(Graphics2D)g;
        g1.setStroke(new BasicStroke(2));//Thicken the line
        g1.drawLine((int)s0.x(), (int)s0.y(), (int)d0.x(), (int)d0.y());
    }
}





