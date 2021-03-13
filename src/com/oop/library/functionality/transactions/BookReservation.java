package com.oop.library.functionality.transactions;

import com.oop.library.enums.ReservationStatus;

import java.util.Date;

public class BookReservation {
//    private String id; // WHY no id?
    private Date creationDate;
    private ReservationStatus status;
    private String bookItemBarcode;
    private String memberId;

    // WHy is this static? Singletone?
    public static BookReservation fetchReservationDetails(String barcode) {
        return null;
    }

    public void updateBookReservation(ReservationStatus status) {
        // call DB to update book item
        this.setStatus(status);
    }

    public void sendBookAvailableNotification() {
    }


    public String getMemberId() {
        return memberId;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}
