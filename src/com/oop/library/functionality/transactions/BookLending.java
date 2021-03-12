package com.oop.library.functionality.transactions;

import java.util.Date;

public class BookLending {
    private String id;
    private Date createdAt;
    private Date dueAt;
    private Date returnedAt;
    private String bookItemBarcode;
    private String memberId;

    public static boolean lendBook(String barcode, String memberId) {
        return false;
    }

    public static BookLending fetchLendingDetails(String bookItemBarcode) {
        return null;
    }

    public Date getDueAt() {
        return dueAt;
    }
}
