package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;

public class myClientNew implements Runnable {
    public static void main(String[] a) {
        Thread client = new Thread(new myClientNew());
        client.start();
    }

    @Override
    public void run() {
        int level;
        level = 5;
        game_service game = Game_Server_Ex2.getServer(level);
        directed_weighted_graph g = buildMygraph(game.getGraph());

    }

    public directed_weighted_graph buildMygraph(String s) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(directed_weighted_graph.class, new GraphJson());
        {
            Gson json = builder.create();
            directed_weighted_graph g = json.fromJson(s, directed_weighted_graph.class);
            return g;
        }
    }
}
