package com.example.itune.iTUneSearch.test.api;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.apache.commons.io.IOUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SearchSongTest {

    @Test
    public void testSearchSongs() {
        Object mockApiResponse= fromJsonFile("ituneSearch.json",Object.class);
            CloseableHttpClient httpClient = HttpClientBuilder.create()
                    .build();
            String apiUrl  = "https://itunes.apple.com/search?term=jack+johnson";
            try {
                HttpGet httpGet = new HttpGet(apiUrl);
                HttpResponse response = httpClient.execute(httpGet);
                assertEquals(200, response.getStatusLine().getStatusCode());
                HttpEntity entity = response.getEntity();
                String responseString = EntityUtils.toString(entity);
              // assertEquals(mockApiResponse, responseString);
            } catch (Exception e) {
                fail("An exception occurred during the API call: " + e.getMessage());
            }
        }



    public  <T> T fromJsonFile(String file, Class<T> clazz) {
        String mockJson;
        try {
            mockJson = readFile(file);
            return fromJson(clazz, mockJson);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(Class<T> clazz, String mockJson) {
        try {
            return new ObjectMapper().readValue(mockJson,clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public  String readFile(String file)
    {
        try {
            InputStream json = getClass().getClassLoader().getResourceAsStream(file);
            return IOUtils.toString(json, StandardCharsets.UTF_8);
            //return new String(Files.readAllBytes(getClass().getClassLoader().getResource("mock/"+file).getPath())), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
