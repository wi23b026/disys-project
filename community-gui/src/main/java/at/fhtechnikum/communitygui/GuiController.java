package at.fhtechnikum.communitygui;

// import com.fasterxml.jackson.databind.ObjectMapper;
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
        comPool.setText("Hewwo!");

        LocalDate start = startDatePicker.getValue();
        LocalDate end = endDatePicker.getValue();

        /*
        String urlString = "http://localhost:8080/energy/historical?start=" + start + "&end=" + end;
        if (start == null || end == null) {
            System.out.println("Start/End missing");
            return;
        }

        var request = HttpRequest
                .newBuilder(URI.create(urlString))
                .GET()
                .build();
        try {
            var response = HttpClient
                    .newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            ObjectMapper objectMapper = new ObjectMapper();
            List<EnergyData> energyData = objectMapper.readValue(response.body(), List.class);
            System.out.println(response.body());
            System.out.println(energyData);
        }*/
    }
}
