package com.example.ikergomezrubio;

import java.util.List;

public class RespuestaTiempo {
    private Main main;
    private List<Weather> weather;
    private Wind wind;
    private int visibility;

    public Main getMain() {
        return main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public Wind getWind() {
        return wind;
    }

    public int getVisibility() {
        return visibility;
    }

    // Clase interna para datos principales (temperatura, presión, humedad, etc.)
    public static class Main {
        private float temp;
        private float feels_like;
        private int pressure;
        private int humidity;
        private float dew_point; // OpenWeatherMap no lo tiene directamente, necesitas calcularlo si es necesario.

        public float getTemp() {
            return temp;
        }

        public float getFeelsLike() {
            return feels_like;
        }

        public int getPressure() {
            return pressure;
        }

        public int getHumidity() {
            return humidity;
        }

        public float getDewPoint() {
            return dew_point;
        }
    }

    // Clase interna para la descripción del clima
    public static class Weather {
        private String description;

        public String getDescription() {
            return description;
        }
    }

    // Clase interna para datos del viento
    public static class Wind {
        private float speed;
        private int deg; // Dirección en grados

        public float getSpeed() {
            return speed;
        }

        public String getDirection() {
            return getWindDirection(deg);
        }

        // Método para convertir grados en una dirección cardinal (N, NE, E, etc.)
        private String getWindDirection(int degrees) {
            String[] directions = {"N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE",
                    "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW"};
            return directions[Math.round(degrees / 22.5f) % 16];
        }
    }
}
