package com.hexad.librarymanagement.utility;

import com.hexad.librarymanagement.domain.Book;
import com.hexad.librarymanagement.domain.User;

import java.util.Map;

public interface ObjectArrayToMapUtility {
    public Map<String, User> getUserMap(User[] users);
    public Map<String, Book> getBookMap(Book[] catalogue);
}
