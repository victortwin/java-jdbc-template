package com.epam.izh.rd.online.autcion.mappers;

import com.epam.izh.rd.online.autcion.entity.Item;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemMapper implements RowMapper<Item> {

    @Override
    public Item mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Item();
    }
}
