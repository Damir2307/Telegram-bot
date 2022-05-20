import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
public class Main {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        log.info("Run Application");
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi();
            botsApi.registerBot(new TBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
