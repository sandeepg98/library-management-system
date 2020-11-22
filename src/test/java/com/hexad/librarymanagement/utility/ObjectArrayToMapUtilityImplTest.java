package com.hexad.librarymanagement.utility;

import com.hexad.librarymanagement.domain.Book;
import com.hexad.librarymanagement.domain.BookDTO;
import com.hexad.librarymanagement.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
public class ObjectArrayToMapUtilityImplTest {

    private ObjectArrayToMapUtility objectArrayToMapUtility;
    private final User[] users = new User[1];
    private final Book[] catalogue = new Book[1];
    private final BookDTO bookDTO = new BookDTO("12345", "A Brief History of Time");
    private final List<BookDTO> borrowedBooks = new ArrayList<>();

    @Before
    public void setUp(){
        objectArrayToMapUtility = new ObjectArrayToMapUtilityImpl();
        borrowedBooks.add(bookDTO);
        catalogue[0] = new Book("32445", "The Da Vinci Code", "Dan Brown", 4,3);
        users[0] = new User("001", "Sandeep Grover", borrowedBooks);
    }

    @Test
    public void testGetUserMap(){
        Map<String, User> resultsMap = new HashMap<>();
        resultsMap = objectArrayToMapUtility.getUserMap(users);
        assertEquals(users[0].getUserName(), resultsMap.get("001").getUserName());
    }

    @Test
    public void testGetBookMap(){
        Map<String, Book> resultsMap = new HashMap<>();
        resultsMap = objectArrayToMapUtility.getBookMap(catalogue);
        assertEquals(catalogue[0].getBookTitle(), resultsMap.get("32445").getBookTitle());
    }
}