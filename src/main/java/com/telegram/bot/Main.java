package com.telegram.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new EchoBot());
    }

    static class EchoBot extends TelegramLongPollingBot {
        @Override
        public String getBotUsername() {
            return "Попугай бот";
        }

        @Override
        public String getBotToken() {
            return "5603720209:AAGE1FhlYQoAEfJYZxfvg4eDwnJDN5KtJVo";
        }

        private static int userCount = 0;

        @Override
        public void onUpdateReceived(Update update) {
            if (update.hasMessage() && update.getMessage().hasText()) {
                var chatId = update.getMessage().getChatId().toString();
                if (update.getMessage().getText().equals("/start")) {
                    userCount++;
                    System.out.println("New user, total: " + userCount);
                    sentMsg(chatId, "Привет, пришли мне начальную комиссию и процент снижения в формате '1000 30', где 1000 начальная комиссия выставленная банком, а 30 процент снижения за счет КВ агента и я рассчитаю тебе комиссию\uD83D\uDE3C");
                } else {
                    var arg = update.getMessage().getText().split(" ");
                        var comm = Integer.parseInt(arg[0]) / 100 * 70;
                        var agentprc = 100 - Integer.parseInt(arg[1]);
                        var rezult = comm / 100 * agentprc;
                        var rezultS = Integer.toString(rezult);
                    sentMsg(chatId, rezultS);
                    System.out.println(rezult);
                }
            }
            System.out.println("Someone use bot");
        }

        private void sentMsg(String chatId, String text) {
            SendMessage msg = new SendMessage(chatId, text);
            try {
                execute(msg);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        }
    }
}