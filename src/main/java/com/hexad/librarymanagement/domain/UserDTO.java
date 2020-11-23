package com.hexad.librarymanagement.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String userId;         //unique key for each user
    private String[] bookIds;      //array of unique keys for each book
}
