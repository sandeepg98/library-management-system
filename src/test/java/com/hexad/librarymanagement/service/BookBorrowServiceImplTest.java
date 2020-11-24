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
    private final Book[] catalogue = new Book[3];
    private UserDTO userDTOSuccess;
    private UserDTO userDTOFail1;
    private UserDTO userDTOFail2;
    private BookDTO bookDTO;
    private final List<BookDTO> borrowedBooks = new ArrayList<>();
    Map<String, User> userMap = new HashMap<>();
    Map<String, Book> bookMap = new HashMap<>();

    @Before
    public void setUp(){
        jsonReadWriteUtility = new JsonReadWriteUtilityImpl(objectMapper);
        bookBorrowService = new BookBorrowServiceImpl(jsonReadWriteUtility, objectArrayToMapUtility);

        catalogue[0] = new Book("1", "Book 1", "Author 1", 4,3);
        catalogue[1] = new Book("2", "Book 2", "Author 2", 4,3);
        catalogue[2] = new Book("3", "Book 3", "Author 3", 4,3);

        bookDTO = new BookDTO("1", "Book 1");
        borrowedBooks.add(bookDTO);
        users[0] = new User("001", "Sandeep Grover", borrowedBooks);

        userDTOSuccess = new UserDTO("001", new String[]{"2"});
        userDTOFail1 = new UserDTO("001", new String[]{"1"});
        userDTOFail2 = new UserDTO("001", new String[]{"2", "3"});

        for (User user : users) {
            userMap.put(user.getUserId(), user);
        }
        for (Book book : catalogue) {
            bookMap.put(book.getBookId(), book);
        }
    }

    @Test
    public void testBorrowBookSuccess() throws IOException {
        Mockito.when(objectMapper.readValue(new File("catalogue.json"), Book[].class)).thenReturn(catalogue);
        Mockito.when(objectMapper.readValue(new File("users.json"), User[].class)).thenReturn(users);
        Mockito.when(jsonReadWriteUtility.readBooksCatalogue()).thenReturn(catalogue);
        Mockito.when(jsonReadWriteUtility.readUsers()).thenReturn(users);
        Mockito.when(objectArrayToMapUtility.getUserMap(users)).thenReturn(userMap);
        Mockito.when(objectArrayToMapUtility.getBookMap(catalogue)).thenReturn(bookMap);
        assertEquals(TestConstants.BORROW_SUCCESS_MSG, bookBorrowService.borrowBook(userDTOSuccess, 2));
    }

    @Test
    public void testBorrowBookFail_MoreCopyOfSameBook() throws IOException {
        Mockito.when(objectMapper.readValue(new File("catalogue.json"), Book[].class)).thenReturn(catalogue);
        Mockito.when(objectMapper.readValue(new File("users.json"), User[].class)).thenReturn(users);
        Mockito.when(jsonReadWriteUtility.readBooksCatalogue()).thenReturn(catalogue);
        Mockito.when(jsonReadWriteUtility.readUsers()).thenReturn(users);
        Mockito.when(objectArrayToMapUtility.getUserMap(users)).thenReturn(userMap);
        Mockito.when(objectArrayToMapUtility.getBookMap(catalogue)).thenReturn(bookMap);
        assertEquals(TestConstants.BORROW_FAIL_MSG, bookBorrowService.borrowBook(userDTOFail1, 2));
    }

    @Test
    public void testBorrowBookFail_MoreThanTwoBooks() throws IOException {
        Mockito.when(objectMapper.readValue(new File("catalogue.json"), Book[].class)).thenReturn(catalogue);
        Mockito.when(objectMapper.readValue(new File("users.json"), User[].class)).thenReturn(users);
        Mockito.when(jsonReadWriteUtility.readBooksCatalogue()).thenReturn(catalogue);
        Mockito.when(jsonReadWriteUtility.readUsers()).thenReturn(users);
        Mockito.when(objectArrayToMapUtility.getUserMap(users)).thenReturn(userMap);
        Mockito.when(objectArrayToMapUtility.getBookMap(catalogue)).thenReturn(bookMap);
        assertEquals(TestConstants.BORROW_FAIL_MSG, bookBorrowService.borrowBook(userDTOFail2, 2));
    }
}