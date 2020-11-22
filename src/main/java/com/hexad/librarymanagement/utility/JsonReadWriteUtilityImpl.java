package com.hexad.librarymanagement.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexad.librarymanagement.domain.Book;
import com.hexad.librarymanagement.domain.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class JsonReadWriteUtilityImpl implements JsonReadWriteUtility {
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

    /*
     * This method will take an Array of Book objects
     * and write it to the mentioned catalogue.json file.
     */
    @Override
    public void writeBookCatalogue(Book[] catalogue) throws IOException {
        objectMapper.writeValue(new File("catalogue.json"), catalogue);
    }

    /*
     * This method will take an Array of User objects
     * and write it to the mentioned user.json file.
     */
    @Override
    public void writeUsers(User[] users) throws IOException {
        objectMapper.writeValue(new File("users.json"), users);
    }
}