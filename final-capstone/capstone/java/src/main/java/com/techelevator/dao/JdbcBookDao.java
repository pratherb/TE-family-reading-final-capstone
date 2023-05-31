package com.techelevator.dao;

import com.techelevator.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class JdbcBookDao implements BookDao {

    private final RestTemplate restTemplate;
    private final String API_SEARCH = "https://openlibrary.org/search.json?q=";

    public JdbcBookDao() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    //Attempting to call Open Library and search by title, returning a list of results
    public String searchBooksByTitle(String title) {
        String query = title.replace(" ", "+");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> request = new HttpEntity<>(query, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(API_SEARCH + query, HttpMethod.GET, request, String.class);
        String responseBody = response.getBody();
        System.out.println(responseBody);
        return responseBody;
    }
}
