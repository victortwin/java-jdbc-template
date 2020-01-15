package com.epam.izh.rd.online.autcion.repository;

import com.epam.izh.rd.online.autcion.entity.Bid;
import com.epam.izh.rd.online.autcion.entity.Item;
import com.epam.izh.rd.online.autcion.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Component
public class JdbcTemplatePublicAuction implements PublicAuction {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Bid> bidRowMapper = (resultSet, i) ->
            new Bid(
                    resultSet.getString("bid_id"),
                    resultSet.getDate("bid_date"),
                    resultSet.getDouble("bid_value"),
                    resultSet.getString("item_id"),
                    resultSet.getString("user_id")
            );
    private RowMapper<Item> itemRowMapper = (resultSet, i) ->
            new Item(
                    resultSet.getString("item_id"),
                    resultSet.getDouble("bid_increment"),
                    resultSet.getBoolean("buy_it_now"),
                    resultSet.getString("description"),
                    resultSet.getDate("start_date"),
                    resultSet.getDouble("start_price"),
                    resultSet.getDate("stop_date"),
                    resultSet.getString("title"),
                    resultSet.getString("user_id")
            );
    private RowMapper<User> userRowMapper = (resultSet, i) ->
            new User(
                    resultSet.getString("user_id"),
                    resultSet.getString("billing_address"),
                    resultSet.getString("full_name"),
                    resultSet.getString("login"),
                    resultSet.getString("password")
            );

    @Override
    public List<Bid> getUserBids(long id) {
        return jdbcTemplate.query("SELECT * FROM bids WHERE user_id=?", bidRowMapper, id);
    }

    @Override
    public List<Item> getUserItems(long id) {
        return jdbcTemplate.query("SELECT * FROM items WHERE user_id=?", itemRowMapper, id);
    }

    @Override
    public Item getItemByName(String name) {
        return jdbcTemplate.queryForObject("SELECT * FROM items WHERE title=?", itemRowMapper, name);
    }

    @Override
    public Item getItemByDescription(String name) {
        return jdbcTemplate.queryForObject("SELECT * FROM items WHERE description LIKE '%' || ? || '%'", itemRowMapper, name);
    }

    @Override
    public Map<User, Double> getAvgItemCost() {
        return null; //TODO
    }

    @Override
    public Map<Item, Bid> getMaxBidsForEveryItem() {
        return null; //TODO
    }

    @Override
    public List<Bid> getUserActualBids(long id) {
        return null; //TODO
    }

    @Override
    public boolean createUser(User user) {
        return jdbcTemplate.update("INSERT INTO users VALUES (?,?,?,?,?);", ps -> {
            ps.setInt(1, Integer.parseInt(user.getUserId()));
            ps.setString(2, user.getBillingAddress());
            ps.setString(3, user.getFullName());
            ps.setString(4, user.getLogin());
            ps.setString(5, user.getPassword());
        }) != 0;
    }

    @Override
    public boolean createItem(Item item) {
        return jdbcTemplate.update("INSERT INTO items VALUES (?,?,?,?,?,?,?,?,?);", ps -> {
            ps.setInt(1, Integer.parseInt(item.getItemId()));
            ps.setDouble(2, item.getBidIncrement());
            ps.setBoolean(3, item.getBuyItNow());
            ps.setString(4, item.getDescription());
            ps.setDate(5, item.getStartDate());
            ps.setDouble(6, item.getStartPrice());
            ps.setDate(7, item.getStopDate());
            ps.setString(8, item.getTitle());
            ps.setString(9, item.getUserId());
        }) != 0;
    }

    @Override
    public boolean deleteUserBids(long id) {
        return false; //TODO
    }

    @Override
    public boolean doubleItemsStartPrice(long id) {
        return false; //TODO
    }
}
