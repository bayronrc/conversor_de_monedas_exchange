
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    public static double getExchange(String base, String target, float amount) {
        String apiKey = "2f746a9eb1de9d185932db86";
        String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/" + base + "/" + target;

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
            double conversionRate = jsonObject.get("conversion_rate").getAsDouble();

            return amount * conversionRate;

        } catch (IOException | InterruptedException e) {
            System.out.println("Error al obtener el tipo de cambio: " + e.getMessage());
            return 0.0;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*********************************************************");
        System.out.println("Sea Bienvenido al conversor de moneda:");

        String menu = """
                1) Dólar ==> Peso Argentino
                2) Peso Argentino ==> Dólar
                3) Dólar ==> Real Brasileño
                4) Real Brasileño ==> Dólar
                5) Dólar ==> Peso Colombiano
                6) Peso Colombiano ==> Dólar
                7) Salir
                Elija una opción válida:
                ***************************************
                """;

        int opcion;
        float amount;

        do {
            System.out.println(menu);
            opcion = scanner.nextInt();

            if (opcion >= 1 && opcion <= 6) {
                System.out.println("Ingrese la cantidad a convertir:");
                amount = scanner.nextFloat();

                if (amount <= 0){
                    System.out.println("Por favor ingrese una cantidad mayor que 0");
                    continue;
                }

                String baseCurrency = "";
                String targetCurrency = "";

                switch (opcion) {
                    case 1 -> {
                        baseCurrency = "USD";
                        targetCurrency = "ARS";
                    }
                    case 2 -> {
                        baseCurrency = "ARS";
                        targetCurrency = "USD";
                    }
                    case 3 -> {
                        baseCurrency = "USD";
                        targetCurrency = "BRL";
                    }
                    case 4 -> {
                        baseCurrency = "BRL";
                        targetCurrency = "USD";
                    }
                    case 5 -> {
                        baseCurrency = "USD";
                        targetCurrency = "COP";
                    }
                    case 6 -> {
                        baseCurrency = "COP";
                        targetCurrency = "USD";
                    }
                }

                double result = getExchange(baseCurrency, targetCurrency, amount);
                System.out.printf("El cambio de ==> " + amount  + "[" + baseCurrency + "]" + "\nLa cantidad convertida es ==> %.2f %s\n", result, "[" + targetCurrency + "]\n");
            } else if (opcion != 7) {
                System.out.println("Opción no válida. Inténtelo de nuevo.");
            }

        } while (opcion != 7);

        scanner.close();
        System.out.println("Gracias por usar el conversor de moneda.");
    }
}
