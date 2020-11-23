package com.hexad.librarymanagement.service;

import com.hexad.librarymanagement.domain.Book;
import com.hexad.librarymanagement.domain.BookDTO;
import com.hexad.librarymanagement.domain.User;
import com.hexad.librarymanagement.domain.UserDTO;
import com.hexad.librarymanagement.utility.Constants;
import com.hexad.librarymanagement.utility.JsonReadWriteUtilityImpl;
import com.hexad.librarymanagement.utility.ObjectArrayToMapUtilityImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class BookReturnServiceImpl implements BookReturnService {

    @Autowired
    JsonReadWriteUtilityImpl jsonReadWriteUtility;

    @Autowired
    ObjectArrayToMapUtilityImpl objectArrayToMapUtility;

    @Override
    public String returnBook(UserDTO user) throws IOException {

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
                List<BookDTO> borrowedBooks = currentUser.getBorrowedBooks();

                // Find the book that user wants to return
                BookDTO returnedBook = null;
                for (BookDTO book : borrowedBooks) {
                    if (book.getBookId().equals(bookId))
                        returnedBook = book;
                }

                if(returnedBook != null) {
                    // Remove the returned book from user's bucket and update the base users.json file
                    currentUser.getBorrowedBooks().remove(returnedBook);
                    userMap.put(userId, currentUser);
                    jsonReadWriteUtility.writeUsers(userMap.values().toArray(new User[0]));

                    // Add the returned book to the available copies in the books catalogue and update the base catalogue.json file
                    bookMap.get(bookId).setAvailableCopies(bookMap.get(bookId).getAvailableCopies() + 1);
                    jsonReadWriteUtility.writeBookCatalogue(bookMap.values().toArray(new Book[0]));
                }
            } catch (IOException e) {
                log.error("An Exception Occurred: " + e);
            }
        }

        return Constants.RETURN_SUCCESS_MSG;
    }
}