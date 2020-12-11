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
            } catch (NumberFormatException e) {
                id=999;
                scenario_num=0;
            }
        } else {
            id=999;
            scenario_num=11;
            System.out.println("noaaaaaa");
        }
        n.setId(id);
        n.setScenarioNum(scenario_num);
        client.start();
    }
}
