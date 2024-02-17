package com.codecool.bookdb.manager;

import com.codecool.bookdb.model.AuthorDao;
import com.codecool.bookdb.model.AuthorDaoJdbc;
import com.codecool.bookdb.model.BookDao;
import com.codecool.bookdb.model.BookDaoJdbc;
import com.codecool.bookdb.view.UserInterface;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class BookDatabaseManager {
    UserInterface ui;
    AuthorDao authorDao;
    BookDao bookDao;

    public BookDatabaseManager(UserInterface ui) {
        this.ui = ui;
    }

    public void run() {
        try {
            setup();
        } catch (SQLException throwables) {
            System.err.println("Could not connect to the database.");
            return;
        }
        mainMenu();
    }


    private void mainMenu() {
        boolean running = true;

        while (running) {
            ui.printTitle("Main Menu");
            ui.printOption('a', "Authors");
            ui.printOption('b', "Books");
            ui.printOption('q', "Quit");
            switch (ui.choice("abq")) {
                case 'a':
                    new AuthorManager(ui, authorDao).run();
                    break;
                case 'b':
                    new BookManager(ui, bookDao, authorDao).run();
                    break;
                case 'q':
                    running = false;
                    break;
            }
        }
    }

    private void setup() throws SQLException {
        DataSource dataSource = connect();
        authorDao = new AuthorDaoJdbc(dataSource);
        bookDao = new BookDaoJdbc(dataSource, authorDao);
    }


    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setDatabaseName("books");
        dataSource.setUser("gabi");
        dataSource.setPassword("1111");
        System.out.println("Trying to connect...");
        dataSource.getConnection().close();
        System.out.println("Connection OK");
        return dataSource;
    }


}