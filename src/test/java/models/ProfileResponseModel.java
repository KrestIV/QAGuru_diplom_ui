package models;

import lombok.Data;

@Data
public class ProfileResponseModel {
    String userId, username;
    BookModel[] books;
}
