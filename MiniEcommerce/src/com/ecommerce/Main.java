package com.ecommerce;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        // Start independent web service API container on port 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        
        // Map the network endpoint path "/checkout"
        server.createContext("/checkout", new CheckoutHandler());
        
        System.out.println("=== Standalone API Backend Online ===");
        System.out.println("Listening for separate frontend requests on port: 8080");
        
        server.start();
    }

    static class CheckoutHandler implements HttpHandler {
        private OrderService orderService = new OrderService();

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // CRITICAL: Cross-Origin Resource Sharing (CORS) header injection
            // This tells the browser to allow a separate external file to talk to this server
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");

            // Handle pre-flight handshake validations from web browsers
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                try (InputStream is = exchange.getRequestBody();
                     Scanner s = new Scanner(is).useDelimiter("\\A")) {
                    
                    String requestBody = s.hasNext() ? s.next() : "";
                    
                    // Simple custom string protocol: parsing "userId,productId,qty"
                    String[] params = requestBody.split(",");
                    int userId = Integer.parseInt(params[0].trim());
                    int productId = Integer.parseInt(params[1].trim());
                    int qty = Integer.parseInt(params[2].trim());

                    // Delegate directly downstream to your existing database business core
                    String systemResponse = orderService.processOrder(userId, productId, qty);

                    // Ship structural text confirmation parameters back across the port
                    byte[] responseBytes = systemResponse.getBytes();
                    exchange.getResponseHeaders().add("Content-Type", "text/plain");
                    exchange.sendResponseHeaders(200, responseBytes.length);
                    try (OutputStream os = exchange.getResponseBody()) { os.write(responseBytes); }
                }
            } else {
                String errorMsg = "Method Not Allowed";
                exchange.sendResponseHeaders(405, errorMsg.length());
                try (OutputStream os = exchange.getResponseBody()) { os.write(errorMsg.getBytes()); }
            }
        }
    }
}
