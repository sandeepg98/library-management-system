package com.hexad.librarymanagement.controller;

import com.hexad.librarymanagement.domain.Book;
import com.hexad.librarymanagement.domain.User;
import com.hexad.librarymanagement.service.ReadJsonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/hexad")
public class BookManagementController {

    @Autowired
    ReadJsonServiceImpl readJsonService;

    /*
     * This method will return the complete catalogue of
     * all the books (loaded from the base JSON file).
     *
     * This API can be used to verify if the base JSON
     * file containing the book catalogue can be accessed
     * by the system or not.
     */
    @GetMapping("/testCatalogue")
    public Book[] testCatalogue() throws IOException {
        return readJsonService.readBooksCatalogue();
    }

    /*
     * This method will return the complete list of
     * all the users (loaded from the base JSON file).
     *
     * This API can be used to verify if the base JSON
     * file containing the user details can be accessed
     * by the system or not.
     */
    @GetMapping("/testUsers")
    public User[] testUsers() throws IOException {
        return readJsonService.readUsers();
    }
}