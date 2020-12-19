package gameClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class panelStart extends JPanel implements ActionListener {
    private JTextField textID;
    private JTextField textLevel;
    private JButton buttonStart;//button to start the game
    private MyClient n;
    private Thread client;//the thread of the client

    /**
     * constructor
     * @param n,client
     */
    public panelStart(MyClient n,Thread client)
    {
        super();
        this.n=n;
        this.client=client;
    }

    /**
     * A function that add all the graphics to the panel
     * @param g
     */
  protected void paintComponent(Graphics g) {

        g.clearRect(0, 0,this.getWidth(), this.getHeight());//clear the panel
        super.paintComponent(g);
        Image im=new ImageIcon("resources/p.jpg").getImage();//new image
        g.drawImage(im,0,0,null);
        textLevel=new JTextField();
        textLevel.setBounds(this.getWidth()/2-35,this.getHeight()/2,70,30);//Updating the location of textLevel
        g.setColor(Color.BLACK);
        g.setFont(new Font("Ariel",Font.BOLD,14));
        g.drawString("Enter Level:",this.getWidth()/2-125,this.getHeight()/2+20);
        textID=new JTextField();
        textID.setBounds(this.getWidth()/2-100,this.getHeight()/2-40,200,30);//Updating the location of textLID
        g.setColor(Color.BLACK);
        g.setFont(new Font("Ariel",Font.BOLD,14));
        g.drawString("Enter ID:",this.getWidth()/2-170,this.getHeight()/2-20);
      buttonStart = new JButton("start game");
      buttonStart.setBounds(this.getWidth()/2-50,this.getHeight()/2+40,100,30);////Updating the location of _button
        this.add(textID);//add textID to the panelStart
        this.add(textLevel);//add textLevel to the panelStart
        this.add(buttonStart);//add _button to the panelStart
        this.setLayout(null);
        buttonStart.addActionListener(this);//Listener presses the button _button
    }

    /**
     * A function that is performed when you press the button and start the game
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
      int id=999;
      int level=0;
        Ex2.frame.setVisible(false);//after click on buttonStart close the frameStart
        try {
            id = Integer.parseInt(textID.getText());//get the id from the string that insert in textID
            level = Integer.parseInt(textLevel.getText());////get the id from the string that insert in textLevel
        }catch (NumberFormatException E)
        {
            //If the ID is string and not numbers runs the game on level 0 with a 999 id.
            System.out.println("Improper input");
        }
        n.setScenarioNum(level);//set the scenario num of the client to the level that was inserted
        n.setId(id);//set the id of the client to the id that was inserted

        client.start();//start the game

    }
}

