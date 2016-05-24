package code.akselm.cleverapi;

import com.thestratagemmc.biff.api.BiffConviction;
import com.thestratagemmc.biff.event.BiffStimuliEvent;
import com.thestratagemmc.droolchat.DroolChat;
import com.thestratagemmc.droolchat.bot.Bot;
import com.thestratagemmc.droolchat.bot.BotInfo;
import com.thestratagemmc.droolchat.event.ChannelChatEvent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Axel on 5/23/2016.
 */
public class CleverAPI extends JavaPlugin implements Listener {
    public static CleverAPI instance;
    private static Clever clever;


    public void onEnable(){
        instance = this;
        clever = new Clever("localhost", 6369);
        getServer().getPluginManager().registerEvents(this, this);
        DroolChat.getInstance().getBotStore().registerBot(this, "Steve", new Bot(){
            private BotInfo info;

            @Override
            public BotInfo getInfo() {
                if (info == null) info = new BotInfo("Steve","Reality show host, personality.", ChatColor.GREEN, "0.1");
                return info;
            }

            @Override
            public String respondToPM(Player player, String s) {
                return null;
            }

            @EventHandler
            public void onChat(final ChannelChatEvent event){
                if (event.getMessage().toLowerCase().length() < 8) return;
                if (event.getMessage().toLowerCase().contains("steve")) {
                    clever.ask(event.getMessage(), new CleverCallback() {
                        @Override
                        public void callback(String string) {
                            event.respond(DroolChat.getBot("Steve"), string);
                        }
                    });
                }
            }
        });
    }

    @EventHandler
    public void stim(final BiffStimuliEvent event){
       // Bukkit.broadcast("test","test");
        if (event.getRequest().getOriginalMessage().toLowerCase().contains("biff") && event.getRequest().getWordArray().length > 3){
            //Bukkit.broadcast("test","test2");
            clever.ask(event.getMessage(), new CleverCallback() {
                @Override
                public void callback(String string) {
                    event.respond("CleverAPI", string, BiffConviction.COMPLETELY_SURE);
                }
            });
        }
    }
}
