import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi();
            botsApi.registerBot(new TBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
