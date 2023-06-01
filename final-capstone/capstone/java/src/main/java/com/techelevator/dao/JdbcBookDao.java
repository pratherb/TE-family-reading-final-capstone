package com.techelevator.dao;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.techelevator.model.Book;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Component
public class JdbcBookDao implements BookDao {

    private final RestTemplate restTemplate;
    private final String API_SEARCH = "https://openlibrary.org/search.json?";
    private final int LIMIT_RESULTS = 10; //How many results to get at a time

    //API communication
    private HttpHeaders httpHeaders; //Headers for an HTTP request

    public JdbcBookDao() {
        this.restTemplate = new RestTemplate();
        this.httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_PLAIN);
    }

    //Call these methods from a Controller to get a list of Book objects, which will display as neatly organized
    //JSON data in Postman and for use in front-end
    @Override
    //Attempting to call Open Library and search by title, returning a list of results
    public List<Book> searchBooksByTitle(String title) {
        //mapJsonToBooks returns a list of Book objects
        //It takes a JsonObject to parse, which we get from the method getJsonFromApi
        //getJsonFromApi requests JSON data from Open Library and turns it into a JsonObject using the Gson library
        return mapJsonToBooks(
                getJsonFromApi(title, SearchType.Title)
        );
    }

    public List<Book> searchBooksByIsbn(String isbn) {
        return mapJsonToBooks(
                getJsonFromApi(isbn, SearchType.Isbn)
        );
    }

    //Enum to define the type of search we want to do
    //Define the search type by using SearchType.Title..etc as a parameter
    public enum SearchType {
        Title,
        Isbn
    }

    /**
     * Send a search query to OpenLibrary.
     *
     * @param query      Search term.
     * @param searchType The type of search. SearchType.Title, SearchType.Isbn, etc
     * @return
     */
    public JsonObject getJsonFromApi(String query, SearchType searchType) {
        query = query.replace(" ", "+"); //Replace spaces with plus sign because web apis don't like spaces
        switch (searchType) { //Adjust the API call URL depending on the search type
            case Title:
                query = "title=" + query;
                break;
            case Isbn:
                query = "isbn=" + query;
                break;
        }
        query = API_SEARCH + query + "&sort=rating" + "&limit=" + LIMIT_RESULTS; //Sorting by rating seems to give the best results
        HttpEntity<String> request = new HttpEntity<>(query, httpHeaders); //Setup request object
        String response = restTemplate.exchange(query, HttpMethod.GET, request, String.class).getBody(); //Call to API, get response
        String results = "";
        Gson gson = new Gson();
        return gson.fromJson(response, JsonObject.class); //Convert the raw JSON response to a JsonObject and return it

    }

    /**
     * Return a list of Book objects created from a JsonObject
     * @param jsonObject
     * JsonObject to parse
     * @return
     */
    public List<Book> mapJsonToBooks(JsonObject jsonObject) {
        List<Book> bookList = new ArrayList<>();
        JsonArray docsArray = jsonObject.getAsJsonArray("docs"); //Access the 'docs' array from the JSON Object, which contains the book's key:value
        for (JsonElement i : docsArray) { //For each element in "docs"..
            //Set default values. Sometimes the API may not have this information, so we need defaults in case.
            String title = "Not found";
            String author = "Not found";
            String isbn = "Not found";
            int numPages = 0;
            Book book = new Book();
            JsonObject doc = i.getAsJsonObject(); //Get each element as an individual JsonObject
            //We're going to try to get these values, but they may not exist.
            try {
                title = doc.get("title").getAsString();
                JsonArray isbnArray = doc.getAsJsonArray("isbn"); //Array of ISBNs - there are often multiples, so let's just get the first one
                isbn = isbnArray.get(0).getAsString();
                JsonArray authorArray = doc.getAsJsonArray("author_name"); //There could be multiple authors
                author = authorArray.get(0).getAsString();
                numPages = doc.get("number_of_pages_median").getAsInt();
            } catch (RuntimeException e) {
                //If a value isn't found, we end up here. let's set the Book object's values to the default values then continue the loop
                //If this isn't caught here, we might crash.
                System.out.println("Some expected data was not found.");
                book.setTitle(title);
                book.setIsbn(isbn);
                book.setAuthor(author);
                book.setNumPages(numPages);
                bookList.add(book);
                continue;
            }
            //If we get here, we found all the values we want. Put them into the Book
            book.setTitle(title);
            book.setIsbn(isbn);
            book.setAuthor(author);
            book.setNumPages(numPages);
            bookList.add(book);
        }
        return bookList;
    }
}
