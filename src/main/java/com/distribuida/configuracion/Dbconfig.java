package com.distribuida.configuracion;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jdbi.v3.core.Jdbi;

@ApplicationScoped
public class Dbconfig{

    public static Jdbi init(){

        System.out.println("**********post construct");

        Config config = ConfigProvider.getConfig();

        String url = config.getValue("db.url", String.class);
        String password = config.getValue("db.password", String.class);
        String driver = config.getValue("db.driver",String.class);
        String username = config.getValue("db.username", String.class);

        HikariConfig pool= new HikariConfig();
        pool.setMaximumPoolSize(6);
        pool.setJdbcUrl(url);
        pool.setUsername(username);
        pool.setPassword(password);
        pool.setDriverClassName(driver);

        Jdbi jdbi = Jdbi.create(new HikariDataSource(pool));

        return jdbi;

    }
}

