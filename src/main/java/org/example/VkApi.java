package org.example;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.opencagedata.jopencage.JOpenCageGeocoder;
import com.opencagedata.jopencage.model.JOpenCageForwardRequest;
import com.opencagedata.jopencage.model.JOpenCageResponse;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.base.City;
import com.vk.api.sdk.objects.users.Fields;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


class ConfigLoader {
    public static String getApiKey(String key) {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
            return properties.getProperty(key);
        } catch (IOException e) {
            throw new RuntimeException("Ключа не существует", e);
        }
    }
}


class Distance {
    private final int APP_ID = Integer.parseInt(ConfigLoader.getApiKey("vk_api_code"));
    private final String CODE = ConfigLoader.getApiKey("vk_api_key");

    private final VkApiClient vk;
    private final UserActor actor;

    public Distance() {
        TransportClient transportClient = new HttpTransportClient();
        vk = new VkApiClient(transportClient);
        actor = new UserActor(APP_ID, CODE);
    }

    public String getCityIDs() throws ClientException, ApiException {
        return vk.database().getCities(actor, 1).execute().toString();
    }

    public City GetCityByStudent(String studentName) throws ClientException, ApiException { // 477
        return vk.users().search(actor).q(studentName).fields(Fields.CITY).university(1).execute().getItems().getFirst().getCity();
    }

    public static double calculateDistance(double lat1, double lon1) {
        int earthRadius = 6371; // km
        double lat2 = 56.8356;
        double lon2 = 60.6128;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRadius * c;
    }

    public static double getDistanceBetweenCities(String cityJson) {

        JsonObject jsonObject = JsonParser.parseString(cityJson).getAsJsonObject();
        String city = jsonObject.get("title").getAsString();

        var geocode = Geocoder.Geocode(city);
        var geocodeToArr = geocode.split(",");
        try {
            return calculateDistance(Double.parseDouble(geocodeToArr[0]), Double.parseDouble(geocodeToArr[1]));

        }catch (java.lang.NumberFormatException e){
            return 0.0;
        }
    }

    public static double getDistance(String name) throws ClientException, ApiException, InterruptedException {
        Thread.sleep(1500);
        City city;
        Distance distance = new Distance();
        try {
            city = distance.GetCityByStudent(name);

        }catch (java.util.NoSuchElementException e){
            return 0.0;
        }
        if (city == null){
            return 0.0;
        }
        System.out.println(name);


        var distanceBetweenCities = getDistanceBetweenCities(city.toString());

        JsonObject jsonObject = JsonParser.parseString(city.toString()).getAsJsonObject();
        String city1 = jsonObject.get("title").getAsString();

        var test = Geocoder.Geocode(city1);
        return distanceBetweenCities;
    }
}


class Geocoder {
    public static String Geocode(String city) {
        JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder(ConfigLoader.getApiKey("opencage_api_key"));

        JOpenCageForwardRequest request = new JOpenCageForwardRequest(city);
        request.setRestrictToCountryCode("ru"); // флаг для страны
        JOpenCageResponse response = jOpenCageGeocoder.forward(request);

        if (response.getFirstPosition() != null) {
            var lat = response.getFirstPosition().getLat();
            var lng = response.getFirstPosition().getLng();
            return lat + ", " + lng;
        } else {
            return "Координаты не найдены для города: " + city;
        }
    }
}
public class VkApi {
}
