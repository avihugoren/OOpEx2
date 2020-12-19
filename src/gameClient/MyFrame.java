package gameClient;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {

     MyPanel myPanel;
    public MyFrame(String s, Arena ar)
    {
        super(s);
        myPanel=new MyPanel(ar);
        this.add(myPanel);//add the panel to the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Closing the frame will stop the program
    }
}
