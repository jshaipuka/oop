package com.oop.library.models.books;

import com.oop.library.enums.BookFormat;
import com.oop.library.enums.BookStatus;
import com.oop.library.functionality.transactions.BookLending;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class BookItem extends Book {
    private String id;
    private String barcode; // Same as id?
    private BookFormat format;
    private Date publishedAt;

    // state management
    private boolean isReferenceOnly;
    private BookStatus status;
    private Date borrowedAt;
    private LocalDate dueAt;

    // locate
    private Rack rack;

    // bookkeeping
    private double price;
    private Date purchasedAt;

    public boolean checkout(String memberId) {
        if (this.isReferenceOnly()) {
            System.out.println("This book is Reference only and can't be issued");
            return false;
        }

        boolean canLendBookToThisUser = BookLending.lendBook(this.getBarcode(), memberId);

        if (!canLendBookToThisUser) {
            return false;
        }

        this.updateBookItem(BookStatus.LOANED);
        return true;
    }

    public void updateBookItem(BookStatus status) {
        // call DB to update book item
        this.setStatus(status);
    }

    public void updateBookItemDueDate(LocalDate date) {
        // call DB to update book item
        this.setDueAt(date);
    }

    public String getBarcode() {
        return barcode;
    }

    public boolean isReferenceOnly() {
        return isReferenceOnly;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public void setDueAt(LocalDate dueAt) {
        this.dueAt = dueAt;
    }
}

abstract class Book {
    private String id;

    // search functionality
    private String ISBN; // same as id
    private String title;
    private String subject;
    private String publisher;
    private String language;
    private List<Author> authors;

    private int numberOfPages; // some analytics
}

class Rack {
    private int number; // same as id
    private String locationIdentifier;
}

class Author {}