package gameClient;

public class main {
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
                client.start();
            } catch (NumberFormatException e) {
                n.setId(999);
                n.setScenarioNum(0);
                client.start();
            }
        } else {
            frameStart frame=new frameStart("open",n,client);
            frame.setSize(500,250);
            //frame.show();
            frame.setVisible(true);
           // System.out.println(frame.myPanel.getID());
           // client.start();

        }
//        n.setId(id);
//        n.setScenarioNum(scenario_num);
       // client.start();
    }
}
