package com.techelevator.dao;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.techelevator.model.Book;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.*;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Component
public class JdbcBookDao implements BookDao {

    private final RestTemplate restTemplate;
    private final JdbcTemplate jdbcTemplate;
    private final String API_SEARCH = "https://openlibrary.org/search.json?";
    private final int LIMIT_RESULTS = 10; //How many results to get at a time
    private UserDao userDao;

    //API communication
    private HttpHeaders httpHeaders; //Headers for an HTTP request

    public JdbcBookDao(UserDao userDao) {
        this.restTemplate = new RestTemplate();
        this.jdbcTemplate = new JdbcTemplate();
        this.httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_PLAIN);
        this.userDao = userDao;
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

    public Book searchBookByIsbn(String isbn) {
        List<Book> bookList = mapJsonToBooks(
                getJsonFromApi(isbn, SearchType.Isbn)
        );
        return bookList.get(0); //Return only 1 book, in case there are multiple results for some reason
//        return mapJsonToBooks(
//                getJsonFromApi(isbn, SearchType.Isbn)
//        );
    }

    @Override
    public Book addBookToReadingList(Book book, String username) {

        //add book to user's reading list via user_book table in database
        Book newBook = new Book();
        String sql = "insert into user_book (book_isbn, user_id, finished, date_finished) values (?,?,?,?)";
        int userId = userDao.findIdByUsername(username);
        int result = jdbcTemplate.update(book.getIsbn(), userId, false, null);
        if (result == 1) {
            newBook = getBookFromDatabaseByISBN(book.getIsbn());
        }
        return newBook;
    }

    @Override
    public Book createBook(Book book) {
        String sql = "INSERT INTO book" +
                "(book_isbn, title, author, num_pages)" +
                "VALUES(?,?,?,?)";
        try {
            jdbcTemplate.update(sql,
                    book.getIsbn(), book.getTitle(), book.getAuthor(), book.getNumPages());
            return book;

        } catch (DataAccessException e) {
            System.out.println("Error creating book.");
        }
        return null;
    }

    @Override
    public List<Book> getFamilyReadingList(int familyId, boolean finished) {
        List<Book> familyReading = new ArrayList<>();
        String sql = "";
        if (finished) {
            sql = "SELECT * FROM user_book WHERE finished = true AND user_book.user_id IN (SELECT users.user_id FROM users WHERE family_id = ?)";
        } else {
            sql = "SELECT * FROM user_book WHERE user_book.user_id IN (SELECT users.user_id FROM users WHERE family_id = ?)";
        }
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, familyId);
        while (results.next()) {
            Book book = mapRowToBook(results);
            familyReading.add(book);
        }
        return familyReading;
    }

    @Override
    public List<Book> getUserReadingList(String username, boolean finished) {
        List<Book> userReading = new ArrayList<>();
        String sql = "";
        if (finished){
            sql = "SELECT * FROM user_book " +
                    "WHERE user_book.user_id = (SELECT users.user_id FROM users WHERE username = ?) " +
                    "AND finished = true";
        }else {
            sql = "SELECT * FROM user_book WHERE user_book.user_id = (SELECT users.user_id FROM users WHERE username = ?)";
        }
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        while (results.next()) {
            Book book = mapRowToBook(results);
            userReading.add(book);
        }
        return userReading;
    }

    @Override
    public void deleteBookById(String isbn){
        String activitySql = "DELETE FROM reading_activity WHERE book_isbn = ?";
        String userBookSql = "DELETE FROM user_book WHERE book_isbn = ?";
        String bookSql = "DELETE FROM book WHERE book_isbn = ?";
        try {
            jdbcTemplate.update(activitySql, isbn);
            jdbcTemplate.update(userBookSql, isbn);
            jdbcTemplate.update(bookSql, isbn);
        } catch(CannotGetJdbcConnectionException e){
            throw new RuntimeException("Cannot connect to database", e);
        }catch(BadSqlGrammarException e){
            throw new RuntimeException("Syntax error", e);
        }catch(DataIntegrityViolationException e){
            throw new RuntimeException("Data integrity violation, delete not completed", e);
        }
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

    private Book getBookFromDatabaseByISBN(String isbn) {
        Book book = new Book();
        String sql = "SELECT book FROM book WHERE book_isbn = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, isbn);
        if (result.next()) {
            book = mapRowToBook(result);
        }
        return book;
    }

    /**
     * Return a list of Book objects created from a JsonObject
     *
     * @param jsonObject JsonObject to parse
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

    private Book mapRowToBook(SqlRowSet rs) {
        Book book = new Book();
        book.setIsbn(rs.getString("book_isbn"));
        book.setAuthor(rs.getString("author"));
        book.setTitle(rs.getString("title"));
        book.setNumPages(rs.getInt("num_pages"));
        return book;
    }
}
