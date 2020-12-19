package gameClient;

public class Ex2 {
    public static frameStart frame;
    public static void main(String[] a) {
        MyClient n=new MyClient();
        Thread client = new Thread(n);
        //client.start();
        int id;
        int scenario_num;
        if(a.length == 2) {
            try {
                id = Integer.parseInt(a[0]);
                scenario_num = Integer.parseInt(a[1]);
                n.setId(id);
                n.setScenarioNum(scenario_num);
                client.start();
            } catch (NumberFormatException e) {
                n.setId(999);
                n.setScenarioNum(0);
                client.start();
            }
        } else {
            frame=new frameStart("open",n,client);//creat a new frameStart
            frame.setSize(500,250);//set the frame size
            frame.setVisible(true);//make frame visible
        }
    }
}
