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
    protected void onShowDataClick() {
        LocalDate start = startDatePicker.getValue();
        LocalDate end = endDatePicker.getValue();

        if (start == null || end == null) {
            comPool.setText("Start oder Ende fehlt!");
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
                comPool.setText("Fehler: " + response.statusCode());
                return;
            }

            // JSON in EnergyData[] umwandeln
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            EnergyData[] dataArray = objectMapper.readValue(response.body(), EnergyData[].class);

            if (dataArray.length == 0) {
                comPool.setText("Keine Daten im Zeitraum gefunden.");
                comProduced.setText("");
                comUsed.setText("");
                gridUsed.setText("");
                return;
            }

            // Nur das erste Ergebnis anzeigen (zum Testen)
            EnergyData data = dataArray[0];

            comPool.setText("GridPortion: " + data.getGridPortion());
            comProduced.setText("Produced: " + data.getCommunityProduced());
            comUsed.setText("Used: " + data.getCommunityUsed());
            gridUsed.setText("Grid Used: " + data.getGridUsed());

        } catch (Exception e) {
            e.printStackTrace();
            comPool.setText("Fehler beim Abrufen!");
        }
    }

}
