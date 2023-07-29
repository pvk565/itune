package com.example.itune.iTUneSearch.Itune.api;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;

public class iTunesApiClient {

    private static CloseableHttpClient httpClient;

    // Constructor
    public iTunesApiClient() {
        // Initialize the HTTP client
        httpClient = HttpClientBuilder.create().build();
    }

    // Set a custom HTTP client (useful for testing with a mock client)
    public static void setHttpClient(CloseableHttpClient customHttpClient) {
        httpClient = customHttpClient;
    }

    // Method to search for songs based on a query
    public String searchSongs(String query) {
        // Create the API endpoint URL with the search query
        String apiUrl = "https://developer.apple.com/library/archive/\n" +
                "        documentation/AudioVideo/Conceptual/iTuneSearchAPI/index.html#//apple_ref/doc/uid/\n" +
                "        TP40017632-CH3-SW1";

        try {
            // Create an HTTP GET request to the API endpoint
            HttpGet httpGet = new HttpGet(apiUrl);

            // Execute the GET request and get the response
            CloseableHttpResponse response = httpClient.execute(httpGet);

            // Check the response status code
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                // If the response is successful (status code 200), parse the response entity
                InputStream responseStream = response.getEntity().getContent();
                String responseBody = EntityUtils.toString((HttpEntity) responseStream);
                return responseBody;
            } else {
                // Handle other response status codes (e.g., 404, 500) as needed
                // You can throw exceptions or return specific error messages
                return "Error: " + statusCode;
            }

        } catch (IOException e) {
            // Handle any IO or connection-related exceptions
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }



}

