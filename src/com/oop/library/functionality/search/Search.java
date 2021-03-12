package com.oop.library.functionality.search;

import com.oop.library.models.Book;

import java.util.Date;
import java.util.List;

public interface Search {
    List<Book> byTitle(String title);
    List<Book> byAuthor(String author);
    List<Book> bySubject(String subject);
    List<Book> byPubDate(Date publishDate);
}


//Interface is used here to represent another view of the underline class. Take the example of Library Management System - here ‘Member’ and ‘Librarian’ are interested only in searching the ‘Catalog’ without doing any modification to it, so we’ve created an interface to expose a search view for these users. Another implementation can be to expose static methods in ‘Catalog’ for searching.
//
//        We also intend to make Catalog a Singleton, as we wanted to have only one object of ‘Catalog’ in the system so that all updates should go through that object only. In this case, ‘Member’ and ‘Librarian’ can be passed the interface of the ‘Catalog’ object.
//
//        Hope this clarifies your question.