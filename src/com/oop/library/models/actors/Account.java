package com.oop.library.models.actors;

import com.oop.library.enums.AccountStatus;

public abstract class Account {
    private String id;
    private String password;
    private AccountStatus status;
    private Person person;

    public boolean resetPassword() {
        return false;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }
}
