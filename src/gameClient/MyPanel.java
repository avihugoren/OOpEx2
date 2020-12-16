package gameClient;

import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class MyPanel  extends JPanel {

    private Arena _ar;
    private gameClient.util.Range2Range _w2f;

    public MyPanel(Arena ar)
    {
        super();
       /// Image im=getToolkit().getImage("unnamed.jpg/pokemon.png");
        this.setBackground(new Color(141, 142, 146));
        this._ar=ar;
        updateFrame();
    }

//    private int _ind;
//      private Arena _ar;
//      private gameClient.util.Range2Range _w2f;
////    MyFrame(String a) {
////        super(a);
////        int _ind = 0;
////    }
//    public void update(Arena ar) {
//        this._ar = ar;
//        updateFrame();
//    }
//
    private void updateFrame() {
        Range rx = new Range(20,this.getWidth()-20);
        Range ry = new Range(this.getHeight()-10,150);
        Range2D frame = new Range2D(rx,ry);
        directed_weighted_graph g = _ar.getGraph();
        _w2f = Arena.w2f(g,frame);
    }


   // protected void paintComponent(Graphics g) {
   protected void paintComponent(Graphics g) {
        int w = this.getWidth();
        int h = this.getHeight();
        g.clearRect(0, 0, w, h);
        updateFrame();
        super.paintComponent(g);
//        int w = this.getWidth();
//        int h = this.getHeight();
//        g.clearRect(0, 0, w, h);
    	//updateFrame();
        drawTitle(g);
       drawBack(g);
        //drawImage(g);
        drawGraph(g);
        drawPokemons(g);
        drawAgants(g);

       // drawTimer(g);
//        drawInfo(g);
   }

    private void drawBack(Graphics g) {
//        Image im=new ImageIcon("resources/back.png").getImage();
//        g.drawImage(im,this.getWidth()/8,this.getHeight()/2-105,null);
        Image im1=new ImageIcon("resources/pikachu.png").getImage();
        g.drawImage(im1,(this.getWidth()/8)*6,this.getHeight()/3*2,null);
//        Image im2=new ImageIcon("resources/bal.png").getImage();
//        g.drawImage(im2,this.getWidth()/8*4,this.getHeight()/3*2,null);
//        Image im3=new ImageIcon("resources/pok.png").getImage();
//        g.drawImage(im3,this.getWidth()/8*2,this.getHeight()/3*2,null);
    }


//    private void drawTimer(Graphics g) {
//        g.setColor(Color.BLACK);
//        g.setFont(new Font("ARIEL",Font.BOLD,24));
//        g.drawString("time to end: "+timeToEnd,20,20);
//    }

    //    private void drawInfo(Graphics g) {
//        java.util.List<String> str = _ar.get_info();
//        String dt = "none";
//        for(int i=0;i<str.size();i++) {
//            g.drawString(str.get(i)+" dt: "+dt,100,60+i*20);
//        }
//
//    }
    private void drawTitle(Graphics g)  {

        Image im=getToolkit().getImage("resources/pokemon1.png");
        g.drawImage(im,(int)(this.getWidth()/2-300),0,null);


    }
    private void drawGraph(Graphics g) {
        directed_weighted_graph gg = _ar.getGraph();
        Iterator<node_data> iter = gg.getV().iterator();
        while (iter.hasNext()) {
            node_data n = iter.next();
            g.setColor(Color.BLACK);
            drawNode(n, 5, g);
            Iterator<edge_data> itr = gg.getE(n.getKey()).iterator();
                while (itr.hasNext()) {
                    edge_data e = itr.next();
                    g.setColor(Color.BLACK);
                    drawEdge(e, g);
                }
            }
        }

    private void drawPokemons(Graphics g) {
        java.util.List<CL_Pokemon> fs = _ar.getPokemons();
        if(fs!=null) {
            Iterator<CL_Pokemon> itr = fs.iterator();

            while(itr.hasNext()) {

                CL_Pokemon f = itr.next();
                Point3D c = f.getLocation();

                int r=10;
                Image im1=new ImageIcon("resources/bal.png").getImage();
                if(f.getType()<0) {im1=new ImageIcon("resources/pokemon1.gif").getImage(); }
                if(c!=null) {
                    //im1=getToolkit().getImage("resources/bal.png");
                    geo_location fp = this._w2f.world2frame(c);
                    g.drawImage(im1,(int)fp.x()-27/2,(int)fp.y()-24/2,null);
                    // g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
//                    g.setColor(Color.orange);
                    if(f.getMyAgent()!=null)
                        g.drawString(""+f.getMyAgent().getID(), (int)fp.x()-27/2,(int)fp.y()-27/2);
                    g.setColor(Color.red);
                    g.drawString(""+f.getValue(),(int)fp.x()-27/2+10,(int)fp.y()-27/2);
                }
            }
        }
    }
    private void drawAgants(Graphics g) {
        List<CL_Agent> rs = _ar.getAgents();
        //	Iterator<OOP_Point3D> itr = rs.iterator();
        g.setColor(Color.red);
        int i=0;
        while(rs!=null && i<rs.size()) {
            geo_location c = rs.get(i).getLocation();
            i++;
            if(c!=null) {
                Image im1=getToolkit().getImage("resources/hash.gif");
                geo_location fp = this._w2f.world2frame(c);
                //g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
                g.drawImage(im1,(int)fp.x()-12,(int)fp.y()-20,null);
                g.setColor(Color.BLACK);
                g.drawString(""+rs.get(i-1).getID()+" "+rs.get(i-1).getValue()+" "+rs.get(i-1).getNextNode()+" "+rs.get(i-1).getSpeed(), (int)fp.x()-12, (int)fp.y()-20);
            }
        }
    }

    private void drawNode(node_data n, int r, Graphics g) {
        geo_location pos = n.getLocation();
        geo_location fp = this._w2f.world2frame(pos);
        g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
        g.drawString(""+n.getKey(), (int)fp.x(), (int)fp.y()-4*r);
    }

    private void drawEdge(edge_data e, Graphics g) {
        directed_weighted_graph gg = _ar.getGraph();
        geo_location s = gg.getNode(e.getSrc()).getLocation();
        geo_location d = gg.getNode(e.getDest()).getLocation();
        geo_location s0 = this._w2f.world2frame(s);
        geo_location d0 = this._w2f.world2frame(d);
        Graphics2D g1=(Graphics2D)g;
        g1.setStroke(new BasicStroke(2));
        g1.drawLine((int)s0.x(), (int)s0.y(), (int)d0.x(), (int)d0.y());
      //  g.drawString(""+n.getKey(), fp.ix(), fp.iy()-4*r);
    }
}





