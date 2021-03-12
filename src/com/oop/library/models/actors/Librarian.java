package com.oop.library.models.actors;

import com.oop.library.models.books.BookItem;


public class Librarian extends Account {
    boolean addBookItem(BookItem bookItem) {
        return false;
    }

    boolean blockMember(Member member) // WHY not account? Ah, this is person's id blacklisting? What is we only need to suspend/block account or card?
    {
        return false;
    }

    boolean unblockMember(Member member) // Why not be part of blockMember with boolean flag
    {
        return false;
    }
}
