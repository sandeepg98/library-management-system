package com.hexad.librarymanagement.service;

import com.hexad.librarymanagement.domain.Book;
import com.hexad.librarymanagement.domain.User;

import java.io.IOException;

public interface ReadJsonService {

    public Book[] readBooksCatalogue() throws IOException;
    public User[] readUsers() throws IOException;
}