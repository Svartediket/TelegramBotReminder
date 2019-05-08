/*
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.db.MapDBContext;
import org.telegram.abilitybots.api.objects.Ability;
//import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

public class MyBot extends AbilityBot {

    protected MyBot(String botToken, String botUsername, DefaultBotOptions botOptions) {
        super(botToken, botUsername, MapDBContext.onlineInstance(botUsername), botOptions);
        System.out.println("111");
    }

    @Override
    public int creatorId() {
        return 0;
    }

    public Ability sayHelloWorld() {
        System.out.println("HELLO");

        */
/*SendMessage snd = new SendMessage();
        snd.setChatId("hi");
        snd.setText("Hello world!");*//*

        return Ability
                .builder()
                .name("hello")
                .info("says hello world!")
                .input(0)
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send("Hello world!", ctx.chatId()))
                .post(ctx -> silent.send("Bye world!", ctx.chatId()))
                .build();
    }

    public void onUpdateReceived(Update update) {

    }
*/
/*
    @Override
    public void clearWebhook() throws TelegramApiRequestException {
        super.clearWebhook();
        System.out.println("webhook");
    }*//*



}*/
