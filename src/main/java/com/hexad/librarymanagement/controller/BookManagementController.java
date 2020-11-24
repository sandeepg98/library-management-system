package com.hexad.librarymanagement.controller;

import com.hexad.librarymanagement.domain.Book;
import com.hexad.librarymanagement.domain.BookDTO;
import com.hexad.librarymanagement.domain.User;
import com.hexad.librarymanagement.domain.UserDTO;
import com.hexad.librarymanagement.service.BookBorrowServiceImpl;
import com.hexad.librarymanagement.service.BookReturnServiceImpl;
import com.hexad.librarymanagement.utility.JsonReadWriteUtilityImpl;
import com.hexad.librarymanagement.utility.ObjectArrayToMapUtilityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/hexad")
public class BookManagementController {

    @Value("${maximum.borrowing.capacity}")
    private int maxBorrowCapacity;

    @Autowired
    JsonReadWriteUtilityImpl jsonReadWriteUtility;

    @Autowired
    ObjectArrayToMapUtilityImpl objectArrayToMapUtility;

    @Autowired
    BookReturnServiceImpl bookReturnService;

    @Autowired
    BookBorrowServiceImpl bookBorrowService;

    /*
     * This method is for returning a book by the user.
     * The method will also update the base user.json and
     * the base catalogue.json files accordingly.
     *
     * The method will return an updated list of all users
     * and the books they have currently with them.
     */
    @PostMapping("/returnBook")
    public String returnBook(@RequestBody UserDTO userObject) throws IOException {
        return bookReturnService.returnBook(userObject);
    }

    /*
     * This method is for borrowing a book by the user.
     * The method will also update the base user.json and
     * the base catalogue.json files accordingly.
     *
     * The method will return an updated list of all users
     * and the books they have currently with them.
     */
    @PostMapping("/borrowBook")
    public String borrowBook(@RequestBody UserDTO userObject) throws IOException {
        return bookBorrowService.borrowBook(userObject, maxBorrowCapacity);
    }

    /*
    * This method will return the list of books borrowed by
    * a given user at that moment.
    */
    @GetMapping("/viewBorrowedBooks/{userId}")
    public List<BookDTO> viewBorrowedBooks(@PathVariable String userId) throws IOException {
        return objectArrayToMapUtility.getUserMap(jsonReadWriteUtility.readUsers())
                .get(userId).getBorrowedBooks();
    }

    /*
     * This method will return the complete catalogue of
     * all the books (loaded from the base JSON file).
     *
     * This API can be used to verify if the base JSON
     * file containing the book catalogue can be accessed
     * by the system or not.
     */
    @GetMapping("/viewCatalogue")
    public Book[] viewCatalogue() throws IOException {
        return jsonReadWriteUtility.readBooksCatalogue();
    }

    /*
     * This method will return the complete list of
     * all the users (loaded from the base JSON file).
     *
     * This API can be used to verify if the base JSON
     * file containing the user details can be accessed
     * by the system or not.
     */
    @GetMapping("/viewUsers")
    public User[] viewUsers() throws IOException {
        return jsonReadWriteUtility.readUsers();
    }
}