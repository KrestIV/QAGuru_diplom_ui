package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BookModel {
    String isbn, title, subTitle, author;

    @JsonProperty("publish_date")
    String publishDate;

    String publisher, description, website;
    int pages;
}
