package com.epam.izh.rd.online.autcion.mappers;

import com.epam.izh.rd.online.autcion.entity.Bid;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BidMapper implements RowMapper<Bid> {

    @Override
    public Bid mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Bid();
    }
}
