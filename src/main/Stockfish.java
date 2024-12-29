package main;

import javax.net.ssl.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.cert.X509Certificate;

public class Stockfish {
    private static SSLContext createInsecureSSLContext() throws Exception {
        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, new TrustManager[]{new InsecureTrustManager()}, new java.security.SecureRandom());
        return sc;
    }

    private static class InsecureTrustManager implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    public static String  postRequest(String fen) {
        return postRequest(fen, 18);
    }

    public static String postRequest(String fen, int depth) {
        try {
            String requestBody = "{\"fen\": \"" + fen + "\" , \"depth\": " + depth + "}";
            HttpClient client = HttpClient.newBuilder()
                    .sslContext(createInsecureSSLContext())
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://chess-api.com/v1"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return null;
    }
    public static double getEval(String jsonString) {
        int evalIndex = jsonString.indexOf("\"eval\"");
        if (evalIndex == -1)
            throw new IllegalArgumentException("Key 'eval' not found in JSON string.");
        int colonIndex = jsonString.indexOf(":", evalIndex);
        if (colonIndex == -1)
            throw new IllegalArgumentException("Invalid JSON format after 'eval'.");

        int startIndex = colonIndex + 1;
        int endIndex = jsonString.indexOf(",", startIndex);
        if (endIndex == -1)
            endIndex = jsonString.indexOf("}", startIndex); // Handle if it's the last field

        String evalValue = jsonString.substring(startIndex, endIndex).trim();

        return Double.parseDouble(evalValue);
    }
}
