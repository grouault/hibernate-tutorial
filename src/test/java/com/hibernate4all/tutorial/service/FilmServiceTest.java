package com.hibernate4all.tutorial.service;

import com.hibernate4all.tutorial.config.PersistenceConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes= {PersistenceConfig.class})
@SqlConfig(dataSource = "dataSource", transactionManager = "transactionManager")
@Sql({"/datas/datas-test.sql"})
public class FilmServiceTest {

    @Autowired
    private MovieService movieService;

    @Test
    public void updateDescription() {
        movieService.updateDescription(-2L, "super film mais j'ai oublié le pitch");
    }

}
