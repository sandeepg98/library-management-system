package com.hexad.librarymanagement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexad.librarymanagement.domain.Book;
import com.hexad.librarymanagement.domain.BookDTO;
import com.hexad.librarymanagement.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
public class ReadJsonServiceImplTest {

    @Mock
    private ObjectMapper objectMapper;

    private ReadJsonServiceImpl readJsonService;
    private final Book[] catalogue = new Book[1];
    private final List<BookDTO> borrowedBooks = new ArrayList<>();
    private final User[] users = new User[1];

    @Before
    public void setUp() throws IOException {
        readJsonService = new ReadJsonServiceImpl(objectMapper);
        Book book = new Book(123, "Test Name", "Test Author", 4, 3);
        BookDTO bookDTO = new BookDTO(123, "Test Name");
        catalogue[0] = book;
        borrowedBooks.add(bookDTO);
        User user = new User(123, "Test Name", borrowedBooks);
        users[0] = user;
    }

    @Test
    public void whenCatalogueJsonReadSuccess() throws IOException {
        Mockito.when(objectMapper.readValue(new File("catalogue.json"), Book[].class)).thenReturn(catalogue);
        Book[] result = readJsonService.readBooksCatalogue();
        assertEquals(catalogue, result);
    }

    @Test
    public void whenUsersJsonReadSuccess() throws IOException {
        Mockito.when(objectMapper.readValue(new File("users.json"), User[].class)).thenReturn(users);
        User[] result = readJsonService.readUsers();
        assertEquals(users, result);
    }
}