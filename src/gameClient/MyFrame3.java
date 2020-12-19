package gameClient;

import javax.swing.*;
import java.awt.*;

public class MyFrame3 extends JFrame {

     MyPanel myPanel;
    public MyFrame3(String s,Arena ar)
    {
        super(s);
        myPanel=new MyPanel(ar);
        this.add(myPanel);//add the panel to the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Closing the frame will stop the program
    }
}
