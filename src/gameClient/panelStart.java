package gameClient;

import com.sun.jdi.IntegerValue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class panelStart extends JPanel implements ActionListener {
    private JTextField textID;
    private JTextField textLevel;
    private JButton _button;
    private MyClient n;
    private Thread client;

    public panelStart(MyClient n,Thread client)
    {
        super();
        this.n=n;
        this.client=client;
    }

  protected void paintComponent(Graphics g) {

        g.clearRect(0, 0,this.getWidth(), this.getHeight());
        super.paintComponent(g);
        Image im=new ImageIcon("resources/p.jpg").getImage();
       // g.drawImage(im,this.getWidth()/2-500,this.getHeight()/2-250,null);
        g.drawImage(im,0,0,null);
        textLevel=new JTextField();
        //textLevel.setBounds(465,250,70,30);
        textLevel.setBounds(this.getWidth()/2-35,this.getHeight()/2,70,30);
        textLevel.setText("");
         g.setColor(Color.BLACK);
        g.setFont(new Font("Ariel",Font.BOLD,14));
        g.drawString("Enter Level:",this.getWidth()/2-125,this.getHeight()/2+20);
        textID=new JTextField();
        //textID.setBounds(400,200,200,30);
        textID.setBounds(this.getWidth()/2-100,this.getHeight()/2-40,200,30);
        textID.setText("");
      g.setColor(Color.BLACK);
      g.setFont(new Font("Ariel",Font.BOLD,14));
      g.drawString("Enter ID:",this.getWidth()/2-170,this.getHeight()/2-20);
        _button = new JButton("start game");
        _button.setBounds(this.getWidth()/2-50,this.getHeight()/2+40,100,30);
        this.add(textID);
        this.add(textLevel);
        this.add(_button);
        this.setLayout(null);
        _button.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e) {
        int id= Integer.parseInt(textID.getText());
        int level= Integer.parseInt(textLevel.getText());
        n.setScenarioNum(level);
        n.setId(id);
        client.start();

    }
}
