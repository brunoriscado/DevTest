package com.example.model.google.geocoding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;

public class GeocodingAPIHandler {
    private static final Logger LOGGER = Logger.getLogger(GeocodingAPIHandler.class.getName());

    private String addressLookupURL;

    public GeocodingAPIHandler(String addressLookupURL) {
        this.addressLookupURL = addressLookupURL;
    }

    public GoogleReverseGeocodingResponse getAddress(double latitude, double longitude) {
        GoogleReverseGeocodingResponse response = null;
        String coordenatesURL = addressLookupURL.replaceAll("\\{lat\\}", String.valueOf(latitude))
                .replaceAll("\\{lng\\}", String.valueOf(longitude));
        Gson gson = new Gson();
        URL url;
        HttpsURLConnection connection = null;
        BufferedReader responseReader = null;
        try {
            LOGGER.log(Level.INFO, "Performing reverse geocoding address lookup request: {0}", coordenatesURL);
            url = new URL(coordenatesURL);
            connection = (HttpsURLConnection)url.openConnection();
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer responseContent = new StringBuffer();
            while ((inputLine = responseReader.readLine()) != null) {
                responseContent.append(inputLine);
            }
            if (responseContent.toString() != null && !responseContent.toString().equals("")) {
                response = gson.fromJson(responseContent.toString(), GoogleReverseGeocodingResponse.class);
            }
        } catch (NullPointerException e) {
            LOGGER.log(Level.WARNING, "The address url hasn't been set");
        } catch (MalformedURLException e) {
            LOGGER.log(Level.WARNING, "The url: {0} is not valid", coordenatesURL);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error establishing https connection");
        } finally {
            try {
                responseReader.close();
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "Unable to close resource streams!");
            } catch (NullPointerException e) {
                LOGGER.log(Level.WARNING, "Unable to close resource streams!");
            }
            connection.disconnect();
            gson = null;
        }
        return response;
    }

}
