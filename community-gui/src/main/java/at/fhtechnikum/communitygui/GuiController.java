package at.fhtechnikum.communitygui;

// import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class GuiController {
    @FXML
    private Label comPool;

    @FXML
    private Label gridPortion;

    @FXML
    private Label comProduced;

    @FXML
    private Label comUsed;

    @FXML
    private Label gridUsed;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private Label message;

    @FXML
    protected void onShowDataClick() {
        LocalDate start = startDatePicker.getValue();
        LocalDate end = endDatePicker.getValue();

        if (start == null || end == null) {
            message.setText("Start oder Ende fehlt!");
            return;
        }

        String urlString = "http://localhost:8080/energy/historical?start=" +
                start.atStartOfDay() + "&end=" + end.atTime(23, 59);

        System.out.println("Sende Request an URL: " + urlString);

        var request = HttpRequest
                .newBuilder(URI.create(urlString))
                .GET()
                .build();

        try {
            var response = HttpClient
                    .newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("HTTP Status Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());

            if (response.statusCode() != 200) {
                message.setText("Fehler: " + response.statusCode());
                return;
            }

            // JSON in EnergyData[] umwandeln
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            EnergyData[] dataArray = objectMapper.readValue(response.body(), EnergyData[].class);

            if (dataArray.length == 0) {
                message.setText("Keine Daten im Zeitraum gefunden.");
                comProduced.setText("");
                comUsed.setText("");
                gridUsed.setText("");
                return;
            }

            // Nur das erste Ergebnis anzeigen (zum Testen)
            EnergyData data = dataArray[0];

            gridPortion.setText(data.getGridPortion() + "%");
            comProduced.setText(data.getCommunityProduced() + " kWh");
            comUsed.setText(data.getCommunityUsed() + " kWh");
            gridUsed.setText(data.getGridUsed() + " kWh");

        } catch (Exception e) {
            e.printStackTrace();
            message.setText("Fehler beim Abrufen!");
        }
    }

    @FXML
    protected void onRefreshClick() {

        String urlString = "http://localhost:8080/energy/current";

        var request = HttpRequest
                .newBuilder(URI.create(urlString))
                .GET()
                .build();

        try {
            var response = HttpClient
                    .newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Sende Request an URL: " + urlString);
            System.out.println("HTTP Status Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());

            if (response.statusCode() != 200) {
                comPool.setText("Keine aktuellen Daten (" + response.statusCode() + ")");
                comProduced.setText("");
                comUsed.setText("");
                gridUsed.setText("");
                return;
            }

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();

            EnergyData data = objectMapper.readValue(response.body(), EnergyData.class);

            // Community Pool berechnen
            float pool = data.getCommunityProduced() - data.getCommunityUsed();
            String poolText = pool >= 0 ? (pool + " % available") : (Math.abs(pool) + " % missing");

            comPool.setText(poolText);
            comProduced.setText("Produced: " + data.getCommunityProduced());
            comUsed.setText("Used: " + data.getCommunityUsed());
            gridUsed.setText("Grid Used: " + data.getGridUsed());

        } catch (Exception e) {
            e.printStackTrace();
            comPool.setText("Error fetching data");
            comProduced.setText("");
            comUsed.setText("");
            gridUsed.setText("");
        }
    }


}
