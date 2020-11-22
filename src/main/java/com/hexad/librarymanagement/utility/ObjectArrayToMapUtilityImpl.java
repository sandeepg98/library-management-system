package com.hexad.librarymanagement.utility;

import com.hexad.librarymanagement.domain.Book;
import com.hexad.librarymanagement.domain.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ObjectArrayToMapUtilityImpl implements ObjectArrayToMapUtility {

    /*
    * This is a utility method to convert a given
    * Array of Users to a Hash Map to facilitate
    * faster operations.
    */
    @Override
    public Map<String, User> getUserMap(User[] users) {

        Map<String, User> resultsMap = new HashMap<>();
        for (User user : users) {
            resultsMap.put(user.getUserId(), user);
        }
        return resultsMap;
    }

    /*
     * This is a utility method to convert a given
     * Array of Books to a Hash Map to facilitate
     * faster operations.
     */
    @Override
    public Map<String, Book> getBookMap(Book[] catalogue) {

        Map<String, Book> resultsMap = new HashMap<>();
        for (Book book : catalogue) {
            resultsMap.put(book.getBookId(), book);
        }
        return resultsMap;
    }
}
