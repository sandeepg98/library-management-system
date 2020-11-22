package com.hexad.librarymanagement.utility;

import com.hexad.librarymanagement.domain.Book;
import com.hexad.librarymanagement.domain.User;

import java.io.IOException;

public interface JsonReadWriteUtility {
    public Book[] readBooksCatalogue() throws IOException;
    public User[] readUsers() throws IOException;
    public void writeBookCatalogue(Book[] catalogue) throws IOException;
    public void writeUsers(User[] users) throws IOException;
}