package gameClient;

import javax.swing.*;

public class frameStart extends JFrame {

    panelStart myPanel;
    public frameStart(String s,MyClient n,Thread client)
    {
        super(s);
        myPanel=new panelStart(n,client);
        this.add(myPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }

}