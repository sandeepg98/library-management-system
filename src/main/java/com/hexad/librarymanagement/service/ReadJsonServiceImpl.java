package com.hexad.librarymanagement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexad.librarymanagement.domain.Book;
import com.hexad.librarymanagement.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@AllArgsConstructor
public class ReadJsonServiceImpl implements ReadJsonService {
    private ObjectMapper objectMapper = new ObjectMapper();

    /*
     * This method will return the Array of Book objects
     * after reading it from the provided base JSON file.
     *
     * The mentioned catalogue.json file contains an
     * JSONArray of multiple Books.
     */
    @Override
    public Book[] readBooksCatalogue() throws IOException {
        return objectMapper.readValue(new File("catalogue.json"), Book[].class);
    }


    /*
     * This method will return the Array of User objects
     * after reading it from the provided base JSON file.
     *
     * The mentioned user.json file contains an
     * JSONArray of multiple Users.
     */
    @Override
    public User[] readUsers() throws IOException {
        return objectMapper.readValue(new File("users.json"), User[].class);
    }
}