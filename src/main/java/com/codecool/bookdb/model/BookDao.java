package com.codecool.bookdb.model;

import java.util.List;

// we implement DAO layer -> keep the domain model completely decoupled from the persistence layer.
// Dao interface defines an abstract API that performs CRUD operations on objects of type Book.


public interface BookDao {
    void add(Book book);

    void update(Book book);

    Book get(int id);

    List<Book> getAll();
}