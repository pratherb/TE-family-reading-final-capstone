package com.techelevator.dao;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class JdbcBookDao implements BookDao {

    private final RestTemplate restTemplate;
    private final String API_SEARCH = "https://openlibrary.org/search.json?";

    //API communication
    private HttpHeaders httpHeaders;

    public JdbcBookDao() {
        this.restTemplate = new RestTemplate();
        this.httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_PLAIN);
    }

    @Override
    //Attempting to call Open Library and search by title, returning a list of results
    public String searchBooksByTitle(String title) {
        String response = callApi(title, SearchType.Title);
        return response;
    }

    public String searchBooksByIsbn(String isbn){
        String response = callApi(isbn, SearchType.Isbn);
        return response;
    }

    public enum SearchType{
        Title,
        Isbn
    }

    /**
     * Send a search query to OpenLibrary.
     * @param query
     * Search term.
     * @param searchType
     * The type of search. SearchType.Title, SearchType.Isbn, etc
     * @return
     */
    public String callApi(String query, SearchType searchType){
        query = query.replace(" ", "+");
        switch(searchType){
            case Title:
                query = "title=" + query;
                break;
            case Isbn:
                query = "isbn=" + query;
                break;
        }
        query = query.replace(" ", "+");

        HttpEntity<String> request = new HttpEntity<>(query, httpHeaders); //Setup request object
        String response = restTemplate.exchange(API_SEARCH + query, HttpMethod.GET, request, String.class).getBody(); //Call to API, get response
        String results = "";
        Gson gson = new Gson();
        JsonObject responseJson = gson.fromJson(response, JsonObject.class);
        JsonArray docsArray = responseJson.getAsJsonArray("docs");

        for(JsonElement i: docsArray){
            JsonObject doc = i.getAsJsonObject();
            String title = doc.get("title").getAsString();
            JsonArray isbnArray = doc.getAsJsonArray("isbn");
            results += "Title: " + title + "\n";
            results += "ISBNs: ";
            for(JsonElement element: isbnArray){
                results += element + ", ";
            }
        }
        System.out.println(results);
        return results;

    }
}
