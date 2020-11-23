package com.hexad.librarymanagement.service;

import com.hexad.librarymanagement.domain.User;
import com.hexad.librarymanagement.domain.UserDTO;

import java.io.IOException;

public interface BookBorrowService {
    public String borrowBook(UserDTO user) throws IOException;
}
