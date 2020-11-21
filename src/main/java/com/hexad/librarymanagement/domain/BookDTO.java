package com.hexad.librarymanagement.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private int bookId;         //unique key for each book
    private String bookTitle;
}
