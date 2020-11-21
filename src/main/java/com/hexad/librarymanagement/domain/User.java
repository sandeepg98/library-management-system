package com.hexad.librarymanagement.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private int userId;         //unique key for each user
    private String userName;
    List<BookDTO> borrowedBooks = new ArrayList<>();
}
