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
public class BookBorrowServiceImpl implements BookBorrowService {

    @Autowired
    JsonReadWriteUtilityImpl jsonReadWriteUtility;

    @Autowired
    ObjectArrayToMapUtilityImpl objectArrayToMapUtility;

    @Override
    public String borrowBook(UserDTO userDTO, int maxBorrowCapacity) throws IOException {

        // Load the current details of all users & the current catalogue of books
        Book[] catalogue = jsonReadWriteUtility.readBooksCatalogue();
        User[] users = jsonReadWriteUtility.readUsers();
        String userId = userDTO.getUserId();

        // Convert the current Array of users & the current catalogue of books to Hash Maps
        Map<String, User> userMap = objectArrayToMapUtility.getUserMap(users);
        Map<String, Book> bookMap = objectArrayToMapUtility.getBookMap(catalogue);
        User currentUser = userMap.get(userId);

        if(checkBorrowingCapacity(currentUser, userDTO, maxBorrowCapacity)) {
            for (String bookId : userDTO.getBookIds()) {
                try {
                    // Add the borrowed book to user's bucket and update the base users.json file
                    currentUser.getBorrowedBooks().add(new BookDTO(bookId, bookMap.get(bookId).getBookTitle()));
                    userMap.put(userId, currentUser);
                    jsonReadWriteUtility.writeUsers(userMap.values().toArray(new User[0]));

                    // Remove the borrowed book from the available copies in the books catalogue and update the base catalogue.json file
                    bookMap.get(bookId).setAvailableCopies(bookMap.get(bookId).getAvailableCopies() - 1);
                    jsonReadWriteUtility.writeBookCatalogue(bookMap.values().toArray(new Book[0]));
                } catch (IOException e) {
                    log.error("An Exception Occurred: " + e);
                }
            }
            return Constants.BORROW_SUCCESS_MSG;
        }
        return Constants.BORROW_FAIL_MSG;
    }

    private boolean checkBorrowingCapacity(User currentUser, UserDTO userDTO, int maxBorrowCapacity){
        List<BookDTO> currentBorrowedBooksBooks = currentUser.getBorrowedBooks();
        String[] newlyBorrowedBook = userDTO.getBookIds();

        /* Check if user already has 1 or more borrowed books
         * and if he is trying to borrow another copy of an
         * already borrowed book.
         */
        if((currentBorrowedBooksBooks.size() + newlyBorrowedBook.length) <= maxBorrowCapacity){
            for(String newBookId : newlyBorrowedBook){
                for (BookDTO currentBook : currentBorrowedBooksBooks){
                    if (currentBook.getBookId().equals(newBookId))
                        return false;
                }
            }
        } else return false;

        return true;
    }
}
