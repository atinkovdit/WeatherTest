package app;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label logo;

    @FXML
    private TextField enter_city;

    @FXML
    private Button search;

    @FXML
    private Label temp_label;

    @FXML
    private Label fiel_label;

    @FXML
    private Label max_label;

    @FXML
    private Label min_label;

    @FXML
    private Label dav_label;

    @FXML
    private Label temp_out;

    @FXML
    private Label feel_out;

    @FXML
    private Label max_out;

    @FXML
    private Label min_out;

    @FXML
    private Label dav_out;

    @FXML
    private Label sys_message;

    @FXML
    void initialize() {
        search.setOnAction(event -> {
        // Получаем данные из текстового поля
        String getUserCity = enter_city.getText().trim();
        if(!getUserCity.equals("")) { // Если данные не пустые
            // Получаем данные о погоде с сайта openweathermap
            String output = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=" + getUserCity + "&appid=26de7cb537748661a394fff1ecee88ef&units=metric");
            System.out.println(getUserCity);
            if (!output.isEmpty()) { // Нет ошибки и такой город есть
                JSONObject obj = new JSONObject(output);
                // Обрабатываем JSON и устанавливаем данные в текстовые надписи
                temp_out.setText(String.valueOf(obj.getJSONObject("main").getDouble("temp")));
                feel_out.setText(String.valueOf(obj.getJSONObject("main").getDouble("feels_like")));
                max_out.setText(String.valueOf(obj.getJSONObject("main").getDouble("temp_max")));
                min_out.setText(String.valueOf(obj.getJSONObject("main").getDouble("temp_min")));
                dav_out.setText(String.valueOf(obj.getJSONObject("main").getDouble("pressure")));
            }
        }
    });
    }

    // Обработка URL адреса и получение данных с него
    private static String getUrlContent(String urlAdress) {
        StringBuffer content = new StringBuffer();

        try {
            URL url = new URL(urlAdress);
            URLConnection urlConn = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;

            while((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch(Exception e) {
            System.out.println("Такой город был не найден!");
        }
        return content.toString();
    }
}
