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
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;


@Component
@CrossOrigin
public class JdbcBookDao implements BookDao {

    private final RestTemplate restTemplate;
    private final JdbcTemplate jdbcTemplate;
    private final String API_SEARCH = "https://openlibrary.org/search.json?";
    private final String COVER_URL_BASE = "https://covers.openlibrary.org/b/olid/";
    private final String COVER_URL_SIZE = "L";
    private final int LIMIT_RESULTS = 24; //How many results to get at a time
    private final String[] filterList = {
            "title", "author_name", "isbn", "publisher", "number_of_pages_median", "cover_edition_key"
    };


    private UserDao userDao;

    //API communication
    private HttpHeaders httpHeaders; //Headers for an HTTP request

    public JdbcBookDao(UserDao userDao, JdbcTemplate jdbcTemplate) {
        this.restTemplate = new RestTemplate();
        this.jdbcTemplate = jdbcTemplate;
        this.httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_PLAIN);
        this.userDao = userDao;
    }

    //Call these methods from a Controller to get a list of Book objects, which will display as neatly organized
    //JSON data in Postman and for use in front-end
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

        //If this book is not yet found in the reading list
        if (getBookFromReadingListByIsbn(book.getIsbn(), username) == null) {
            Book newBook;
            //Is this book not in the database? If not create it
            if (getBookFromDatabaseByISBN(book.getIsbn()) == null) {
                newBook = createBook(book);
            }
            //If the book is there, get a reference to it
            else {
                newBook = getBookFromDatabaseByISBN(book.getIsbn());
            }
            String sql = "insert into user_book (book_isbn, user_id, finished, date_finished) values (?,?,?,?)";
            try {
                int userId = userDao.findIdByUsername(username);
                int result = jdbcTemplate.update(sql,
                        newBook.getIsbn(), userId, false, null);
                if (result > 0) {
                    newBook = getBookFromDatabaseByISBN(newBook.getIsbn());
                }
                return newBook;
            } catch (DataAccessException e) {
                System.out.println("Error adding book " + book.getTitle() + " to user list " + username);
            }
        } else {
            System.out.println("Book is already in reading list for " + username + ".");
        }
        return null;
    }

    @Override
    public Book addBookToReadingListByPrincipal(Book book, String username) {
        //If this book is not yet found in the reading list
        if (getBookFromReadingListByIsbn(book.getIsbn(), username) == null) {
            Book newBook;
            //Is this book not in the database? If not create it
            if (getBookFromDatabaseByISBN(book.getIsbn()) == null) {
                newBook = createBook(book);
            }
            //If the book is there, get a reference to it
            else {
                newBook = getBookFromDatabaseByISBN(book.getIsbn());
            }
            String sql = "insert into user_book (book_isbn, user_id, finished, date_finished) values (?,?,?,?)";
            try {
                int userId = userDao.findIdByUsername(username);
                int result = jdbcTemplate.update(sql,
                        newBook.getIsbn(), userId, false, null);
                if (result > 0) {
                    newBook = getBookFromDatabaseByISBN(newBook.getIsbn());
                }
                return newBook;
            } catch (DataAccessException e) {
                System.out.println("Error adding book " + book.getTitle() + " to user list " + username);
            }
        } else {
            System.out.println("Book is already in reading list for " + username + ".");
        }
        return null;
    }

    private Book getBookFromReadingListByIsbn(String isbn, String username) {
        String sql = "SELECT book_isbn FROM user_book WHERE book_isbn = ? AND user_id = ?";
        try {
            int userId = userDao.findByUsername(username).getId();
            String result = jdbcTemplate.queryForObject(sql, String.class, isbn, userId);
            if (result != null) return getBookFromDatabaseByISBN(isbn);
            else throw new RuntimeException("Book " + isbn + " not found in local db.");
        } catch (DataAccessException e) {
            System.out.println("Did not find book " + isbn + " in user list " + username);
        }
        return null;
    }

    @Override
    public Book createBook(Book book) {
        String sql = "INSERT INTO book" +
                "(book_isbn, title, author, num_pages, publisher, cover_url)" +
                "VALUES(?,?,?,?,?,?)";
        try {
            //First, check to see if book is not already in DB
            if (getBookFromDatabaseByISBN(book.getIsbn()) == null) {
                jdbcTemplate.update(sql,
                        book.getIsbn(), book.getTitle(), book.getAuthor(), book.getNumPages(),
                        book.getPublisher(), book.getCoverUrl());
                return book;
            }
            //If book is already there, return a reference to the book in question
            return getBookFromDatabaseByISBN(book.getIsbn());

        } catch (DataAccessException e) {
            System.out.println("Error creating book.");
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void delete(String isbn) {
        String activitySql = "DELETE FROM reading_activity WHERE book_isbn = ?";
        String userBookSql = "DELETE FROM user_book WHERE book_isbn = ?";
        String bookSql = "DELETE FROM book WHERE book_isbn = ?";
        try {
            jdbcTemplate.update(activitySql, isbn);
            jdbcTemplate.update(userBookSql, isbn);
            jdbcTemplate.update(bookSql, isbn);
        } catch (CannotGetJdbcConnectionException e) {
            throw new RuntimeException("Cannot connect to database", e);
        } catch (BadSqlGrammarException e) {
            throw new RuntimeException("Syntax error", e);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Data integrity violation, delete not completed", e);
        }
    }

    @Override
    public int getFamilyBooksFinished(int familyId) {
        int booksFinished = 0;
        String sql = "SELECT COUNT(*) as num_finished from book b " +
                "JOIN user_book ub ON b.book_isbn = ub.book_isbn " +
                "JOIN users u ON u.user_id = ub.user_id " +
                "JOIN family f ON f.family_id = u.family_id " +
                "WHERE f.family_id = ? AND ub.finished = true";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, familyId);
            if (result.next()) {
                booksFinished = result.getInt("num_finished");
            }
            return booksFinished;
        } catch (DataAccessException e) {
            System.out.println("Error retrieving reading list of family with id " + familyId);
        }
        return- 1;
    }

    @Override
    public List<Book> getUserCurrentlyReading(String username) {
        List<Book> bookList = new ArrayList<>();
        int userId = userDao.findByUsername(username).getId();
        String sql = "SELECT * from book JOIN user_book ON user_book.book_isbn = book.book_isbn " +
                    "WHERE user_id =? AND finished=false";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                Book book = mapRowToBook(results);
                bookList.add(book);
            }
            return bookList;
        } catch (DataAccessException e) {
            System.out.println("Error retrieving reading list of user with id " + userId);
        }
        return null;
    }

    @Override
    public int getNumberOfBooksFinished(String username) {
        int numBooks = 0;
        int userId = userDao.findByUsername(username).getId();
        String sql = "SELECT COUNT(*) AS book_total FROM user_book WHERE user_id = ? AND finished = true";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
            if (result.next()) {
                numBooks = result.getInt("book_total");
            }
            return numBooks;
        } catch (DataAccessException e) {
            System.out.println("Error retrieving read books total for user " + username);
        }
        return -1;
    }

    @Override
    public void markBookAsFinished(String username, String isbn) {
        int userId = userDao.findIdByUsername(username);
        String sql = "UPDATE user_book SET finished = true WHERE user_id = ? AND book_isbn = ?";
        try {
            jdbcTemplate.update(sql, userId, isbn);
        } catch (DataAccessException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

//    @Override
//    public List<Book> getUserReadingListByCompletion(int userId, Boolean completed) {
//        List<Book> bookList = new ArrayList<>();
//        String sql = "SELECT * FROM user_book\n" +
//                "WHERE user_id = ?";
//        //Plug value of completed into sql string
//        sql = sql.concat("\nAND finished = " + Boolean.toString(completed).toUpperCase());
//        try {
//            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
//            while (results.next()) {
//                Book book = mapRowToBook(results);
//                bookList.add(book);
//            }
//            return bookList;
//        } catch (DataAccessException e) {
//            System.out.println("Error retrieving completed books for user with id " + userId);
//        }
//        return null;
//    }

    @Override
    public List<Book> getFamilyReadingListByCompletion(int familyId, Boolean completed) {
        List<Book> bookList = new ArrayList<>();
        String sql = "SELECT * from book b\n" +
                "JOIN user_book ub ON b.book_isbn = ub.book_isbn\n" +
                "JOIN users u ON u.user_id = ub.user_id\n" +
                "JOIN family f ON f.family_id = u.family_id\n" +
                "WHERE f.family_id = ?";
        sql = sql.concat("\nAND finished = " + Boolean.toString(completed).toUpperCase());
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, familyId);
            while (results.next()) {
                Book book = mapRowToBook(results);
                bookList.add(book);
            }
            return bookList;
        } catch (DataAccessException e) {
            System.out.println("Error retrieving reading list of family with id " + familyId);
        }
        return null;
    }

    @Override
    public Book updateReadingListEntryByIsbn(Book book, String isbn) {
        return null;
    }

    @Override
    public void deleteReadingListEntryByIsbn(String isbn) {

    }

    private Book getBookFromDatabaseByISBN(String isbn) {
        Book book = new Book();
        String sql = "SELECT * FROM book WHERE book_isbn = ?";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, isbn);
            if (result.next()) {
                book = mapRowToBook(result);
                return book;
            }
        } catch (DataAccessException e) {
            System.out.println("ISBN " + isbn + " was not found in local db.");
            return null;
        }
        return null;
    }

    //Filter out junk data that's probably not relevant for the end user

    //Enum to define the type of search we want to do
    //Define the search type by using SearchType.Title..etc as a parameter
    public enum SearchType {
        Title,
        Isbn
    }

    private Book mapRowToBook(SqlRowSet rs) {
        Book book = new Book();
        book.setIsbn(rs.getString("book_isbn"));
        book.setAuthor(rs.getString("author"));
        book.setTitle(rs.getString("title"));
        book.setNumPages(rs.getInt("num_pages"));
        book.setPublisher(rs.getString("publisher"));
        book.setCoverUrl(rs.getString("cover_url"));
        return book;
    }

    public List<Book> mapJsonToBooks(JsonObject jsonObject) {
        List<Book> bookList = new ArrayList<>();
        JsonArray docsArray = jsonObject.getAsJsonArray("docs"); //Access the 'docs' array from the JSON Object, which contains the book's key:value
        docsArray = filterJsonArray(docsArray); //Run the results through a filter and get a filtered junk-free list out
        for (JsonElement i : docsArray) { //For each element in "docs"..
            //Set default values. Sometimes the API may not have this information, so we need defaults in case.
            String title = "Not found";
            String author = "Unknown";
            String isbn = "Not found";
            String publisher = "Unknown";
            String coverUrl = "";
            int numPages = 0;
            Book book = new Book();
            JsonObject doc = i.getAsJsonObject(); //Get each element as an individual JsonObject
            //We're going to try to get these values, but they may not exist.
            //Update: they should always exist now thanks to filtering method
            try {
                title = doc.get("title").getAsString();
                JsonArray isbnArray = doc.getAsJsonArray("isbn"); //Array of ISBNs - there are often multiples, so let's just get the first one
                isbn = isbnArray.get(0).getAsString();
                JsonArray authorArray = doc.getAsJsonArray("author_name"); //There could be multiple authors
                author = authorArray.get(0).getAsString();
                numPages = doc.get("number_of_pages_median").getAsInt();
                JsonArray publisherArray = doc.getAsJsonArray("publisher");
                publisher = publisherArray.get(0).getAsString();
                //System.out.println("Cover key: " + doc.get("cover_edition_key").getAsString() + " for " + title);
                coverUrl = COVER_URL_BASE + doc.get("cover_edition_key").getAsString() + "-" + COVER_URL_SIZE + ".jpg";
            } catch (RuntimeException e) {
                //If a value isn't found, we end up here. let's set the Book object's values to the default values then continue the loop
                //If this isn't caught here, we might crash.
                //NOTEL: none of this should be required anymore, since the filtering method guarantees this data
                //System.out.println("Some expected data was not found.");
                book.setTitle(title);
                book.setIsbn(isbn);
                book.setAuthor(author);
                book.setNumPages(numPages);
                book.setPublisher(publisher);
                book.setCoverUrl(coverUrl);
                bookList.add(book);
                continue;
            }
            //If we get here, we found all the values we want. Put them into the Book
            book.setTitle(title);
            book.setIsbn(isbn);
            book.setAuthor(author);
            book.setNumPages(numPages);
            book.setPublisher(publisher);
            book.setCoverUrl(coverUrl);
            bookList.add(book);
        }
        return bookList;
    }

    private JsonArray filterJsonArray(JsonArray jsonArray) {
        JsonArray filteredArray = new JsonArray();
        for (JsonElement i : jsonArray) {
            // Get the book to look at
            JsonObject book = i.getAsJsonObject();
            boolean add = true;
            // For every element in filterList, check if it exists in the JsonObject.
            // If anything expected isn't there, we don't add it to the final filtered list.
            for (String s : filterList) {
                if (!book.has(s)) {
                    add = false;
                    break;
                }
                else if(s.equalsIgnoreCase("number_of_pages_median") && book.get("number_of_pages_median").getAsInt() < 1){
                    add = false;
                    break;
                }
            }
            if (add) {
                filteredArray.add(book);
            }
        }
        return filteredArray;
    }

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
        //String sort ="&sort=rating";
        String sort="";
        query = API_SEARCH + query + sort + "&limit=" + LIMIT_RESULTS; //Sorting by rating seems to give the best results
        HttpEntity<String> request = new HttpEntity<>(query, httpHeaders); //Setup request object
        String response = restTemplate.exchange(query, HttpMethod.GET, request, String.class).getBody(); //Call to API, get response
        String results = "";
        Gson gson = new Gson();
        return gson.fromJson(response, JsonObject.class); //Convert the raw JSON response to a JsonObject and return it

    }



}
