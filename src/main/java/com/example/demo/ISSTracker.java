package com.example.demo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;
import org.json.JSONArray;
public class ISSTracker {

    private static final String ASTROS_API_URL = "http://api.open-notify.org/astros.json";
    private static final String ISS_NOW_API_URL = "http://api.open-notify.org/iss-now.json";

    public static String displayAstronautInfo() {
        try {
            URL url = new URL(ASTROS_API_URL);
            Scanner scanner = new Scanner(url.openStream());
            StringBuilder response = new StringBuilder();

            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }

            scanner.close();

            String jsonString = response.toString();

            // Parse the JSON string
            JSONObject jsonObject = new JSONObject(jsonString);

            // Extract the "people" array
            JSONArray peopleArray = jsonObject.getJSONArray("people");
            StringBuilder b = new StringBuilder();
            // Extract names from the "people" array
            for (int i = 0; i < peopleArray.length(); i++) {
                JSONObject person = peopleArray.getJSONObject(i);
                b.append(person.getString("name")).append(", ");
            }
            return b.toString();

        } catch (IOException e) {
            throw new RuntimeException("Error when displaying astronauts", e);
        }
    }

    public static String updateISSPosition() {
        try {
            URL url = new URL(ISS_NOW_API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            StringBuilder response = new StringBuilder();

            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }

            scanner.close();

            // Parse the JSON string
            JSONObject jsonObject = new JSONObject(response.toString());

            // Extract longitude and latitude from the "iss_position" object
            JSONObject issPosition = jsonObject.getJSONObject("iss_position");
            String longitude = issPosition.getString("longitude");
            String latitude = issPosition.getString("latitude");

            // Print the values
            return "Longitude: " + longitude + System.lineSeparator() + "Latitude: " + latitude;

        } catch (IOException e) {
            throw new RuntimeException("Error when getting ISS position", e);
        }
    }
}
