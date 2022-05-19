import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TBot extends TelegramLongPollingBot {

    public void mainMenu(Message message) throws IOException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Choose: ");
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        sendMessage.setChatId(message.getChatId());

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true).setSelective(true);
        List<KeyboardRow> keyboardRowsList = new ArrayList<>();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardButton keyboardButton1= new KeyboardButton();
        keyboardButton1.setText("Add");
        KeyboardButton keyboardButton2= new KeyboardButton();
        keyboardButton2.setText("Minus");
        keyboardRow1.add(keyboardButton1);
        keyboardRow1.add(keyboardButton2);
        keyboardRowsList.add(keyboardRow1);
        replyKeyboardMarkup.setKeyboard(keyboardRowsList);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);



        try {
            execute(sendMessage);
        }
        catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
    public int getPrice() throws IOException {
        //get
        String url = "https://api.sheetson.com/v2/sheets/q/2";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");
        //add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setRequestProperty("Authorization","Bearer rQEWpohxFYtWTHdVfz7MKyiAuHu8UHbstAGFOlk0ac9h7A5pF5-qnSoy_Zg");
        con.setRequestProperty("X-Spreadsheet-Id","1DCUkwa-joV7l-vscfP6nNesTTQbMxsqfccWX7JbEbJ4");
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //print in String
        System.out.println(response.toString());
        //Read JSON response and print
        JSONObject myResponse = new JSONObject(response.toString());
        System.out.println("kr4 price 2: "+myResponse.getInt("price"));
        int price = myResponse.getInt("price");
        return price;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                String text = message.getText();
                if (text.equals("/start")) {
                    try {
                        mainMenu(message);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else if (text.equals("Add")) {
                    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                    try {
                        //put
                        HttpPut request = new HttpPut   ("https://api.sheetson.com/v2/sheets/q/2");
                        JSONObject json = new JSONObject();
                        json.put("price", getPrice()+1);
                        StringEntity params = new StringEntity(json.toString());
                        request.addHeader("content-type", "application/json");
                        request.addHeader("Authorization","Bearer rQEWpohxFYtWTHdVfz7MKyiAuHu8UHbstAGFOlk0ac9h7A5pF5-qnSoy_Zg");
                        request.addHeader("X-Spreadsheet-Id","1DCUkwa-joV7l-vscfP6nNesTTQbMxsqfccWX7JbEbJ4");
                        request.setEntity(params);
                        httpClient.execute(request);
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setText("Added!");
                        sendMessage.setChatId(message.getChatId());
                        execute(sendMessage);
// handle response here...
                    } catch (Exception ex) {
                        // handle exception here
                        System.out.println(ex);
                    } finally {
                        try {
                            httpClient.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                } else if (text.equals("Minus")) {
                    CloseableHttpClient httpClient = HttpClientBuilder.create().build();

                    try {
                        //put
                        HttpPut request = new HttpPut   ("https://api.sheetson.com/v2/sheets/q/2");
                        JSONObject json = new JSONObject();
                        json.put("price", getPrice()-1);
                        StringEntity params = new StringEntity(json.toString());
                        request.addHeader("content-type", "application/json");
                        request.addHeader("Authorization","Bearer rQEWpohxFYtWTHdVfz7MKyiAuHu8UHbstAGFOlk0ac9h7A5pF5-qnSoy_Zg");
                        request.addHeader("X-Spreadsheet-Id","1DCUkwa-joV7l-vscfP6nNesTTQbMxsqfccWX7JbEbJ4");
                        request.setEntity(params);
                        httpClient.execute(request);
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setText("Minus!");
                        sendMessage.setChatId(message.getChatId());
                        execute(sendMessage);
// handle response here...
                    } catch (Exception ex) {
                        // handle exception here
                        System.out.println("Mal boldy");
                    } finally {
                        try {
                            httpClient.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }


            }
        }
    }

    @Override
    public String getBotUsername() {
        return "test_dddadsadsa_bot";
    }

    @Override
    public String getBotToken() {
        return "5171332693:AAEuFdeJcI6M_A_H780VQjTUNZ5MCz-T6y4";
    }

    public static String parse(String responseBody) {
        JSONArray albums = new JSONArray(responseBody);
        for (int i = 0 ; i < albums.length(); i++) {
            JSONObject album = albums.getJSONObject(i);
            int price = album.getInt("price");
            System.out.println(price);
        }
        return null;
    }

}
