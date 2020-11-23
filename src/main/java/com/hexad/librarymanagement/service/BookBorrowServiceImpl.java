package com.hexad.librarymanagement.service;

import com.hexad.librarymanagement.domain.Book;
import com.hexad.librarymanagement.domain.BookDTO;
import com.hexad.librarymanagement.domain.User;
import com.hexad.librarymanagement.domain.UserDTO;
import com.hexad.librarymanagement.utility.JsonReadWriteUtilityImpl;
import com.hexad.librarymanagement.utility.ObjectArrayToMapUtilityImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class BookBorrowServiceImpl implements BookBorrowService {

    @Autowired
    JsonReadWriteUtilityImpl jsonReadWriteUtility;

    @Autowired
    ObjectArrayToMapUtilityImpl objectArrayToMapUtility;

    @Override
    public User[] borrowBook(UserDTO user) throws IOException {

        // Load the current details of all users & the current catalogue of books
        Book[] catalogue = jsonReadWriteUtility.readBooksCatalogue();
        User[] users = jsonReadWriteUtility.readUsers();
        String userId = user.getUserId();

        for(String bookId : user.getBookIds()) {
            try {
                // Convert the current Array of users & the current catalogue of books to Hash Maps
                Map<String, User> userMap = objectArrayToMapUtility.getUserMap(users);
                Map<String, Book> bookMap = objectArrayToMapUtility.getBookMap(catalogue);
                User currentUser = userMap.get(userId);

                // Add the borrowed book to user's bucket and update the base users.json file
                currentUser.getBorrowedBooks().add(new BookDTO(bookId, bookMap.get(bookId).getBookTitle()));
                userMap.put(userId, currentUser);
                jsonReadWriteUtility.writeUsers(userMap.values().toArray(new User[0]));

                // Remove the borrowed book from the available copies in the books catalogue and update the base catalogue.json file
                bookMap.get(bookId).setAvailableCopies(bookMap.get(bookId).getAvailableCopies() - 1);
                jsonReadWriteUtility.writeBookCatalogue(bookMap.values().toArray(new Book[0]));
            } catch (IOException e) {
                log.error("Exception is" + e);
            }
        }
        return jsonReadWriteUtility.readUsers();
    }
}
