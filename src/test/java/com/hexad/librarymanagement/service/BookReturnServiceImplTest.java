package com.hexad.librarymanagement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexad.librarymanagement.domain.Book;
import com.hexad.librarymanagement.domain.BookDTO;
import com.hexad.librarymanagement.domain.User;
import com.hexad.librarymanagement.domain.UserDTO;
import com.hexad.librarymanagement.utility.JsonReadWriteUtilityImpl;
import com.hexad.librarymanagement.utility.ObjectArrayToMapUtilityImpl;
import com.hexad.librarymanagement.utility.TestConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class BookReturnServiceImplTest {

    @Mock
    private JsonReadWriteUtilityImpl jsonReadWriteUtility;

    @Mock
    private ObjectArrayToMapUtilityImpl objectArrayToMapUtility;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private BookReturnServiceImpl bookReturnService;

    private final User[] users = new User[1];
    private final Book[] catalogue = new Book[3];
    private UserDTO userDTO;
    private BookDTO bookDTO;
    private final List<BookDTO> borrowedBooks = new ArrayList<>();
    Map<String, User> userMap = new HashMap<>();
    Map<String, Book> bookMap = new HashMap<>();

    @Before
    public void setUp(){
        jsonReadWriteUtility = new JsonReadWriteUtilityImpl(objectMapper);
        bookReturnService = new BookReturnServiceImpl(jsonReadWriteUtility, objectArrayToMapUtility);

        catalogue[0] = new Book("1", "Book 1", "Author 1", 4,3);
        catalogue[1] = new Book("2", "Book 2", "Author 2", 4,3);
        catalogue[2] = new Book("3", "Book 3", "Author 3", 4,3);

        bookDTO = new BookDTO("1", "Book 1");
        borrowedBooks.add(bookDTO);
        users[0] = new User("001", "Sandeep Grover", borrowedBooks);

        String[] bookIds = {"1"};
        userDTO = new UserDTO("001", bookIds);

        for (User user : users) {
            userMap.put(user.getUserId(), user);
        }
        for (Book book : catalogue) {
            bookMap.put(book.getBookId(), book);
        }
    }

    @Test
    public void testReturnBook() throws IOException {
        Mockito.when(objectMapper.readValue(new File("catalogue.json"), Book[].class)).thenReturn(catalogue);
        Mockito.when(objectMapper.readValue(new File("users.json"), User[].class)).thenReturn(users);
        Mockito.when(jsonReadWriteUtility.readBooksCatalogue()).thenReturn(catalogue);
        Mockito.when(jsonReadWriteUtility.readUsers()).thenReturn(users);
        Mockito.when(objectArrayToMapUtility.getUserMap(users)).thenReturn(userMap);
        Mockito.when(objectArrayToMapUtility.getBookMap(catalogue)).thenReturn(bookMap);
        assertEquals(TestConstants.RETURN_SUCCESS_MSG, bookReturnService.returnBook(userDTO));
    }
}