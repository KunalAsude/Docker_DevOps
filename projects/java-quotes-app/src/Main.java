import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {
    private static List<String> quotes;
    
    public static void main(String[] args) throws IOException {
        // Load quotes from an external file
        quotes = loadQuotesFromFile("quotes.txt");
        
        if (quotes.isEmpty()) {
            System.err.println("No quotes found in the file. Please ensure 'quotes.txt' has content.");
            return;
        }
        
        // Create an HTTP server listening on port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // Define a context for the root path ("/")
        server.createContext("/", exchange -> {
            // Get a random quote
            String quote = getRandomQuote();

            // Create a JSON response
            String jsonResponse = String.format("{\"quote\": \"%s\"}", quote);

            // Convert the JSON response to bytes
            byte[] responseBytes = jsonResponse.getBytes(StandardCharsets.UTF_8);

            // Set the response headers
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, responseBytes.length);

            // Write the JSON response to the output stream
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(responseBytes);
            }
        });

        // Start the server
        server.start();
        System.out.println("Server is running on port 8000...");
    }

    // Helper method to get a random quote
    private static String getRandomQuote() {
        Random random = new Random();
        return quotes.get(random.nextInt(quotes.size()));
    }

    // Helper method to load quotes from an external file
    private static List<String> loadQuotesFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error reading quotes file: " + e.getMessage());
            return List.of();
        }
    }
}

