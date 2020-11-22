package com.hexad.librarymanagement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexad.librarymanagement.domain.Book;
import com.hexad.librarymanagement.domain.BookDTO;
import com.hexad.librarymanagement.domain.User;
import com.hexad.librarymanagement.domain.UserDTO;
import com.hexad.librarymanagement.utility.JsonReadWriteUtilityImpl;
import com.hexad.librarymanagement.utility.ObjectArrayToMapUtilityImpl;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
public class BookBorrowServiceImplTest {

    @Mock
    private JsonReadWriteUtilityImpl jsonReadWriteUtility;

    @Mock
    private ObjectArrayToMapUtilityImpl objectArrayToMapUtility;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private BookBorrowServiceImpl bookBorrowService;

    private final User[] users = new User[1];
    private final Book[] catalogue = new Book[2];
    private UserDTO userDTO;
    private BookDTO bookDTO;
    private final List<BookDTO> borrowedBooks = new ArrayList<>();
    Map<String, User> userMap = new HashMap<>();
    Map<String, Book> bookMap = new HashMap<>();

    @Before
    public void setUp(){
        jsonReadWriteUtility = new JsonReadWriteUtilityImpl(objectMapper);
        bookBorrowService = new BookBorrowServiceImpl(jsonReadWriteUtility, objectArrayToMapUtility);
        userDTO = new UserDTO("001", "12345");
        bookDTO = new BookDTO("12345", "A Brief History of Time");
        catalogue[0] = new Book("32445", "The Da Vinci Code", "Dan Brown", 4,3);
        catalogue[1] = new Book("12345", "A Brief History of Time", "Stephen Hawking", 3,2);
        borrowedBooks.add(bookDTO);
        users[0] = new User("001", "Sandeep Grover", borrowedBooks);
        for (User user : users) {
            userMap.put(user.getUserId(), user);
        }
        for (Book book : catalogue) {
            bookMap.put(book.getBookId(), book);
        }
    }

    @Test
    public void testBorrowBook() throws IOException {
        Mockito.when(objectMapper.readValue(new File("catalogue.json"), Book[].class)).thenReturn(catalogue);
        Mockito.when(objectMapper.readValue(new File("users.json"), User[].class)).thenReturn(users);
        Mockito.when(jsonReadWriteUtility.readBooksCatalogue()).thenReturn(catalogue);
        Mockito.when(jsonReadWriteUtility.readUsers()).thenReturn(users);
        Mockito.when(objectArrayToMapUtility.getUserMap(users)).thenReturn(userMap);
        Mockito.when(objectArrayToMapUtility.getBookMap(catalogue)).thenReturn(bookMap);
        User[] result = bookBorrowService.borrowBook(userDTO);
        assertEquals(users[0].getUserName(), result[0].getUserName());
    }
}