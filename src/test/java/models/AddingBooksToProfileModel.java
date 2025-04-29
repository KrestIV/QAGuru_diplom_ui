package models;

import lombok.Data;

@Data
public class AddingBooksToProfileModel {
    String userId;
    BookIsbnModel[] collectionOfIsbns;
}