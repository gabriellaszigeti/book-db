package com.codecool.bookdb;

import com.codecool.bookdb.manager.BookDatabaseManager;
import com.codecool.bookdb.view.UserInterface;

public class Main {

    public static void main(String[] args) {
        UserInterface ui = new UserInterface(System.in, System.out);
        new BookDatabaseManager(ui).run();
    }
    }
