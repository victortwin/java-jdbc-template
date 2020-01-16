package com.epam.izh.rd.online.autcion.repository;

import com.epam.izh.rd.online.autcion.entity.Bid;
import com.epam.izh.rd.online.autcion.entity.Item;
import com.epam.izh.rd.online.autcion.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        HashSet<User> users = new HashSet<>(jdbcTemplate.query("SELECT * FROM users RIGHT JOIN items ON users.user_id=items.user_id", userRowMapper));
        return users.stream()
                .collect(Collectors.toMap(user -> user, user -> getUserItems(Long.parseLong(user.getUserId())).stream()
                        .mapToDouble(Item::getStartPrice)
                        .average()
                        .orElse(0)));
    }

    @Override
    public Map<Item, Bid> getMaxBidsForEveryItem() {
        List<Bid> bids = jdbcTemplate.query("SELECT * FROM bids", bidRowMapper);
        List<Item> items = jdbcTemplate.query("SELECT DISTINCT items.item_id, items.bid_increment, items.buy_it_now, items.description, items.start_date, " +
                        "items.stop_date, items.title, items.start_price, items.user_id " +
                        "FROM items RIGHT JOIN bids ON items.item_id=bids.item_id",
                itemRowMapper);
        return items.stream()
                .collect(Collectors.toMap(item -> item,item -> bids.stream()
                        .filter(bid -> item.getItemId().equals(bid.getItemId()))
                        .max(Comparator.comparingDouble(Bid::getBidValue)).get()
                ));
    }

    @Override
    public List<Bid> getUserActualBids(long id) {
        return jdbcTemplate.query("SELECT bids.bid_id, bids.bid_date, bids.bid_value, bids.item_id, bids.user_id FROM bids LEFT JOIN users " +
                        "ON users.user_id=bids.user_id LEFT JOIN items " +
                        "ON bids.item_id=items.item_id WHERE items.buy_it_now=false",
                bidRowMapper);
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
    public boolean createBid(Bid bid) {
        return jdbcTemplate.update("INSERT INTO bids VALUES (?,?,?,?,?);", ps -> {
            ps.setInt(1, Integer.parseInt(bid.getBidId()));
            ps.setDate(2, bid.getBidDate());
            ps.setDouble(3, bid.getBidValue());
            ps.setString(4, bid.getItemId());
            ps.setString(5, bid.getUserId());
        }) != 0;
    }

    @Override
    public boolean deleteUserBids(long id) {
        return jdbcTemplate.update("DELETE FROM bids WHERE user_id=?", id) != 0;
    }

    @Override
    public boolean doubleItemsStartPrice(long id) {
        return jdbcTemplate.update("UPDATE items " +
                "SET start_price = start_price * 2 " +
                "WHERE user_id = ?", id) != 0;
    }
}
