package com.oop.library.models.actors;

import com.oop.library.Constants;
import com.oop.library.enums.BookStatus;
import com.oop.library.enums.ReservationStatus;
import com.oop.library.functionality.transactions.BookLending;
import com.oop.library.functionality.transactions.Fine;
import com.oop.library.models.books.BookItem;
import com.oop.library.functionality.transactions.BookReservation;

import java.time.LocalDate;
import java.util.Date;

public class Member extends Account {
    private String id; // it does have an id in BookReservation
    private Date memberFrom;
    private int booksCheckedout;

    boolean reserve(BookItem bookItem) {
        return false;
    }

    // Can checkout 1 book at a time? Or is there a cycle somewhere?
    boolean checkoutBook(BookItem bookItem) {
        if (this.getBooksCheckedout() >= Constants.BOOKS_ISSUED_TO_USER_LIMIT) {
            System.out.println("Too many books on hands");
            return false;
        }

        boolean canCheckout = checkReservationStatus(bookItem, ReservationStatus.COMPLETED);

        if (canCheckout && bookItem.checkout(this.getId())) {
            this.incrementBooksCheckedout();
            return true;
        }

        return false;
    }

    void returnBook(BookItem bookItem) {
        this.checkForFine(bookItem.getBarcode());

        BookReservation bookReservation = BookReservation.fetchReservationDetails(bookItem.getBarcode());
        if (bookReservation != null && bookReservation.getMemberId().equals(this.getId())) {
            // book item has a pending reservation
            bookItem.updateBookItem(BookStatus.RESERVED);
            bookReservation.sendBookAvailableNotification(); // why not NotificationService.notify(EventType, data) ?
        }

        bookItem.updateBookItem(BookStatus.AVAILABLE); // we are managing state of the book here but not when checking out?
    }

    boolean extendBookReservation(BookItem bookItem) {
        this.checkForFine(bookItem.getBarcode());

        BookReservation bookReservation = BookReservation.fetchReservationDetails(bookItem.getBarcode());

        // check if this book item has a pending reservation from another member
        if (bookReservation != null && !bookReservation.getMemberId().equals(this.getId())) {
            System.out.println("This book is reserved by another member");
            this.decrementBooksCheckedout();
            bookItem.updateBookItem(BookStatus.RESERVED);
            bookReservation.sendBookAvailableNotification();
            return false;
        } else if (bookReservation != null) {
            // book item has a pending reservation from this member
            bookReservation.updateBookReservation(ReservationStatus.COMPLETED);
        }
        BookLending.lendBook(bookItem.getBarcode(), this.getId());
        bookItem.updateBookItemDueDate(LocalDate.now().plusDays(Constants.USER_MUST_RETURN_BOOKS_IN_DAYS_LIMIT));
        return true;
    }

    int getBooksCheckedout() {
        return booksCheckedout;
    }


    private void incrementBooksCheckedout() {
        this.booksCheckedout ++;
    }

    private void decrementBooksCheckedout() {
        this.booksCheckedout --;
    }

    private boolean checkReservationStatus(BookItem bookItem, ReservationStatus status) {
        BookReservation  bookReservation = BookReservation.fetchReservationDetails(bookItem.getBarcode());

        if (bookReservation != null && bookReservation.getMemberId().equals(this.getId())) {
            bookReservation.updateBookReservation(status);
            return true;
        }

        if (bookReservation != null) {
            System.out.println("Reserved by another user");
        }

        return false;
    }

    private void checkForFine(String bookItemBarcode) {
        BookLending bookLending = BookLending.fetchLendingDetails(bookItemBarcode);
        Date dueDate = bookLending.getDueAt();

        Date today = new Date();
        // check if the book has been returned within the due date
        if (today.compareTo(dueDate) > 0) {
            long diff = today.getTime() - dueDate.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);

            Fine.collectFine(this.id, diffDays); // do we use this.id or getId?
        }
    }

    public String getId() {
        return id;
    }
}
