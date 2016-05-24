package code.akselm.cleverapi;

import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Axel on 5/23/2016.
 */
public class Clever {
    private String host;
    private int port;



    public Clever(String host, int port){
        this.host = host;
        this.port = port;
    }


    public void ask(final String string, final CleverCallback callback){
        Bukkit.getScheduler().runTaskAsynchronously(CleverAPI.instance, new Runnable(){
            public void run(){
                try{
                    Socket socket = new Socket(InetAddress.getByName(host), port);
                    socket.getOutputStream().write(string.getBytes());
                    socket.getOutputStream().flush();
                    BufferedReader read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String line;
                    while ((line = read.readLine()) != null){

                        if (!line.isEmpty() && line.length() > 2){
                            final String output = line;
                            Bukkit.getScheduler().runTask(CleverAPI.instance, new Runnable() {
                                @Override
                                public void run() {
                                    callback.callback(output);
                                }
                            });
                        }
                    }

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

    }
}
