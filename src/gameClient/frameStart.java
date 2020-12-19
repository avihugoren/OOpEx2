package gameClient;

import javax.swing.*;

public class frameStart extends JFrame {

    panelStart myPanel;

    /**
     * constructor
     * @param s ,n,client
     */
    public frameStart(String s,MyClient n,Thread client)
    {
        super(s);
        myPanel=new panelStart(n,client);//creat new panelStart
        this.add(myPanel);//add myPanel to the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Closing the frame will stop the program
        this.setResizable(false);
    }

}