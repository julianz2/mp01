package com.distribuida.servicios;

import com.distribuida.db.Book;
import jakarta.enterprise.context.ApplicationScoped;
import com.distribuida.configuracion.Dbconfig;
import jakarta.json.Json;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class BookServiceImpl implements BookService {

    public Book findById(Integer id){
        Jdbi jdbi= Dbconfig.init();
        Handle handle= jdbi.open();
        return handle.select("select * from books where id = :id ")
                .bind("id",id)
                .mapToBean(Book.class).first();

    }
    @Override
    public List<Book> findAll(){
        Jdbi jdbi= Dbconfig.init();
        Handle handle= jdbi.open();
        return handle.createQuery("SELECT * FROM \"books\" ").mapToBean(Book.class).list();

    }

    @Override
    public Boolean delete(Integer id) {
        boolean flag;
        try {
            Jdbi jdbi= Dbconfig.init();
            Handle handle= jdbi.open();
            handle.createUpdate("delete from books where id = :id ")
                    .bind("id",id)
                    .execute();
                flag = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            flag = false;
        }
        return flag;
    }


    public Book update(Book book,Integer id) {
        try {
            Jdbi jdbi= Dbconfig.init();
            Handle handle= jdbi.open();
            handle.createUpdate("update books set author= :author ,isbn= :isbn, price= :price, title= :title where id= :id ")
                    .bind("author",book.getAuthor())
                    .bind("isbn",book.getIsbn())
                    .bind("price",book.getPrice())
                    .bind("title",book.getTitle())
                    .bind("id",id)
                    .execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return book;
    }


    public Book insert(Book book) {
        try {
        Jdbi jdbi= Dbconfig.init();
        Handle handle= jdbi.open();
        handle.createUpdate("insert into books (author,id,isbn,price,title) values (:author,:id,:isbn,:price,:title)")
                .bind("author",book.getAuthor())
                .bind("id",book.getId())
                .bind("isbn",book.getIsbn())
                .bind("price",book.getPrice())
                .bind("title",book.getTitle())
                .execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return book;
    }

}
