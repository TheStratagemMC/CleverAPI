package code.akselm.cleverapi;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Axel on 5/23/2016.
 */
public class CleverBot {
    private String username;
    private String key;
    private String nick;

    public CleverBot(String username, String key, String nick) {
        this.username = username;
        this.key = key;
        this.nick = nick;
    }

    public void getResponse(String text, CleverCallback callback){
        try{
            String rawData = "user="+username+"&key="+key+"&nick="+nick;//+"&text="+text.replace("&", " and ");
            String type = "application/x-www-form-urlencoded";
            String encodedData = URLEncoder.encode(rawData, "UTF-8");
            URL u = new URL("https://cleverbot.io/1.0/create");
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "*/*");
            conn.setRequestProperty( "Content-Type", type );
            conn.setRequestProperty("accept-encoding", "gzip, deflate");

            conn.setRequestProperty( "Content-Length", String.valueOf(encodedData.length()));
            conn.setUseCaches(false);
            OutputStream os = conn.getOutputStream();
            os.write(encodedData.getBytes());

            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = reader.readLine()) != null) System.out.println(line);

        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
