package com.codecool.bookdb.model;

import com.codecool.bookdb.model.Author;
import com.codecool.bookdb.model.AuthorDao;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//The AuthorDaoJbdc class implements all the functionality required for fetching, updating, and removing Author objects.

public class AuthorDaoJdbc implements AuthorDao {
    private DataSource dataSource;

    public AuthorDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Author author) {
        try (Connection conn = dataSource.getConnection()) {
            // At first we create a Connection with the database.
            String sql = "INSERT INTO author (first_name, last_name, birth_date) VALUES (?, ?, ?)";
            //Next, we prepare our query. PreparedStatement protects us from SQL Injection attacks, by escaping all special characters.
            //A SQL statement is precompiled and stored in a PreparedStatement object. This object can then be used to efficiently execute this statement multiple times.
            //. We also tell the statement to return generated keys, that we will use to update the key of Author instance -> ?
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, author.getFirstName());
            st.setString(2, author.getLastName());
            st.setDate(3, author.getBirthDate());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next(); // Read next returned value - in this case the first one. See ResultSet docs for more explanation
            author.setId(rs.getInt(1));
//        } catch (SQLException | SQLException throwables) {
        } catch (SQLException throwables) {
            throw new RuntimeException("Error while adding new Author.", throwables);
        }
    }

    @Override
    public void update(Author author) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE author SET first_name = ?, last_name = ?, birth_date = ? WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, author.getFirstName());
            st.setString(2, author.getLastName());
            st.setDate(3, author.getBirthDate());
            st.setInt(4, author.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Author get(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT first_name, last_name, birth_date FROM author WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            // First one is used of DDL operations, and returns an int (usually number of rows affected), and the second one is used for queries that return something.
            if (!rs.next()) {
                return null;
            }
            // Now we extract data from ResultSet. We do it by invoking getString, getDate, getInt and many others.
            // Important thing to keep in mind is that numbers we pass as parameters are numbers of returned columns, not their indexes
            // !!!  Our query returned first name, last name and birth date (in this order), but instead of using numbers [0, 1, 2] we use [1, 2, 3]. !!!
            Author author = new Author(rs.getString(1), rs.getString(2), rs.getDate(3));
            author.setId(id);
            return author;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading author with id: " + id, e);
        }
    }

    @Override
    public List<Author> getAll() {
        // reads all authors from database in a loop
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, first_name, last_name, birth_date FROM author";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<Author> result = new ArrayList<>();
            while (rs.next()) { // while result set pointer is positioned before or on last row read authors
                Author author = new Author(rs.getString(2), rs.getString(3), rs.getDate(4));
                author.setId(rs.getInt(1));
                result.add(author);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all authors", e);
        }
    }
}