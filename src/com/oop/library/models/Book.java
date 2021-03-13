package com.oop.library.models;

import com.oop.library.enums.LanguageCode;

import java.util.ArrayList;
import java.util.Date;

public class Book {
    private String id; // ISBN

    private String title;
    private String author;
    private LanguageCode languageCode;


    private ArrayList<String> categories;

    private String publisher;
    private Date publishedAt;
    private Integer numberOfPages;

}
