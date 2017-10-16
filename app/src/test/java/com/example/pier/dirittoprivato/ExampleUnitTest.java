package com.example.pier.dirittoprivato;

import com.example.pier.dirittoprivato.db.DbHelper;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void createTable_isCorrect() throws Exception {
        assertEquals(DbHelper.SQL_CREATE_TABLE,"create table domanda (domanda text not null, rx1 text not null, rx2 text not null, rx3 text not null, rx4 text not null, corretta int not null, capitolo int not null );");
    }
}