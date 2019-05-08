import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyPoolBot extends TelegramLongPollingBot {

    boolean folderExist = false;
    String targetFolder = System.getProperty("user.home") + "\\"+"Desktop" + "\\LOG";
    FileWriter writer;
    long chat_id = 0;

    boolean logContinues = false;
    boolean pause = false;

    public MyPoolBot(DefaultBotOptions options) {
        super(options);
    }

        public void onUpdateReceived(Update update) {
            Message message = update.getMessage();
            String messageOfUser = "";
            String regex = "/log\\s+[a-z0-9]+\\s+[a-z0-9]+(\\s+[a-z0-9]+){0,1}+\\s+%";
            Pattern p = Pattern.compile(regex);
            Matcher m;

        if (message != null && message.hasText()) {
                messageOfUser = message.getText();
            chat_id = update.getMessage().getChatId(); ///

                if (p.matcher(messageOfUser).find()) {
                    if(!logContinues){
                        //p.matcher(messageOfUser).group(); //работаем с этой строкой
                        //while (messageOfUser.contains("  "))                                        //
                        //    messageOfUser = messageOfUser.replace("  ", " ");    //
                        //String[] params = messageOfUser.split(" ");                          //

                        logContinues = true;
                        try {
                            createFileAndStartLogging();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        sendMsg(message, "Начат процесс логирования");
                    } else
                        sendMsg(message, "ОШИБКА! Процесс логирования уже начат!" +
                                "Вы можете его остановить(/pause) или завершить(/stop).");

               }else {
                    if(logContinues) {
                        try {
                            writer.write(messageOfUser + "\t" + new Date() + ";\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    switch (messageOfUser){
                        case ("/start"):  {
                            sendMsg(message, "Привет! Я робот, осуществляющий логирование :)" +
                                    "\nЧтобы ознакомиться с перечнем моих команд, наберите команду /help." +
                                    "\nПриятной работы!");

                            sendMsg(message, Long.toString(chat_id));

                        } break;
                        case ("/pause"): {
                            if (logContinues){
                                pause = true;
                                logContinues = false;
                                sendMsg(message, "Процесс логирования приостановлен");
                            }else if(pause)
                                sendMsg(message, "ОШИБКА! Данная команда уже приведена в действие!" +
                                        "Вы можете либо прекратить логирование (/stop), либо возобновить его (/resume).");
                            else
                                sendMsg(message, "ОШИБКА! Процесс логирования был завершен!" +
                                        "Вы можете начать новый (/log p1 p2 p3 p4)");

                        } break;
                        case ("/resume"): {
                            if(pause) {
                                pause = false;
                                logContinues = true;
                                sendMsg(message, "Процесс логирования возобновлен");
                            } else
                                sendMsg(message, "ОШИБКА! Процесс логирования может быть возобновлен только после использование команды остановки(/pause).");

                        } break;
                        case ("/stop"):  {
                            if(logContinues || pause) { //если идет лог, или лог пославлен на паузу
                                try {
                                    writer.close();
                                    logContinues = false;
                                    pause = false;
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                sendMsg(message, "Процесс логирования завершен");
                            } else
                                sendMsg(message, "ОШИБКА! \nПроцесс логирования уже был завершен!");

                        }break;
                        case ("/help"):  sendMsg(message, "Возможные команды:" +
                                "\n1. /log р1 р2 р3 %  - Начать процесс логирования" +
                                "\n\t,(где параметры р1,р1 ∈ {a-z0-9}, а параметр р3 либо принадлежит этому же множеству, либо отсутствует);" +
                                "\n2. /pause  - Приостановить процесс логирования;" +
                                "\n3. /resume  - Завершить процесс логирования." +
                                "\n4. /null  - Завершить процесс логирования." +
                                "\n5. /stop  - Завершить процесс логирования." ); break;
                        default: sendMsg(message, "Набери команду /help"); break;

                    }
                }
            }
        }


    private void createFileAndStartLogging() throws IOException {
        if(!folderExist || Files.notExists(new File(targetFolder).toPath())){ //создаем папку с логами, если папка не создана или не найдена
            File f = new File(targetFolder);
            f.mkdir();
            folderExist = true;
        }
        File logFile = new File(targetFolder + "\\" + chat_id + "_log" + ".txt");
        writer = new FileWriter(logFile, true); //осуществление дозаписи в файл
        writer.write("Начат процесс логирования. \t" + new Date() +";\n");
    }

    private void sendMsg(Message message, String text) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.enableMarkdown(true);
            sendMessage.setChatId(message.getChatId().toString());
            //sendMessage.setReplyToMessageId(message.getMessageId()); //пересыл введенного сообщения
            sendMessage.setText(text);
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    @Override
    public String getBotUsername() {
        return "Listner";
    }
    /*public String getBotUsername() {
        return "test";
    }*/

    @Override
    /*public String getBotToken() {
        return "873055753:AAHOy-4_W4itGcloiBJ0nrI-GKgBB1UXMQ8";
    }
    public String getBotToken() {
        return "763898431:AAGfTzKaQba2wMc2wTaXGRqqkTlFVJ5iQrc";
    }*/


    public String getBotToken() {
       return "706260648:AAGdBI9fDMZgPnPbrVCj4O_mboxlkQmTn2k";
    }
}
