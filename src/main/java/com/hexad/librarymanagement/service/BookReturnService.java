package com.hexad.librarymanagement.service;

import com.hexad.librarymanagement.domain.User;
import com.hexad.librarymanagement.domain.UserDTO;

import java.io.IOException;

public interface BookReturnService {
    public String returnBook(UserDTO user) throws IOException;
}
