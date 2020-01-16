package com.epam.izh.rd.online.autcion.repository;


import com.epam.izh.rd.online.autcion.entity.Bid;
import com.epam.izh.rd.online.autcion.entity.Item;
import com.epam.izh.rd.online.autcion.entity.User;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@ComponentScan(basePackages = "com.epam.izh.rd.online.autcion")
class JdbcTemplatePublicAuctionTest {

    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @Autowired
    private PublicAuction publicAuction;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void getUserBids() throws ParseException {
        long userId = 3;
        List<Bid> userBids = Lists.newArrayList(
                new Bid("1", transformDate(dateFormat.parse("31.12.2004")), 10.0, "1", "3"),
                new Bid("3", transformDate(dateFormat.parse("31.12.2004")), 30.0, "2", "3")
        );

        List<Bid> items = publicAuction.getUserBids(userId);

        assertIterableEquals(userBids, items);
    }

    @Test
    void getUserItems() throws ParseException {
        long userId = 1;
        List<Item> userBids = Lists.newArrayList(
                new Item("1", 1.0, true,
                        "description1", transformDate(dateFormat.parse("31.12.2004")), 50.0,
                        transformDate(dateFormat.parse("31.12.2004")), "title1", "1"),
                new Item("2", 2.0, false,
                        "description2", transformDate(dateFormat.parse("31.12.2004")), 100.0,
                        transformDate(dateFormat.parse("31.12.2004")), "title2", "1")
        );

        List<Item> items = publicAuction.getUserItems(userId);

        assertIterableEquals(userBids, items);
    }

    @Test
    void getItemByName() throws ParseException {
        String name = "title1";
        Item item = new Item("1", 1.0, true,
                "description1", transformDate(dateFormat.parse("31.12.2004")), 50.0,
                transformDate(dateFormat.parse("31.12.2004")), "title1", "1");

        Item itemByName = publicAuction.getItemByName(name);

        assertEquals(itemByName, item);
    }

    @Test
    void getItemByDescription() throws ParseException {
        String description = "description1";
        Item item = new Item("1", 1.0, true,
                "description1", transformDate(dateFormat.parse("31.12.2004")), 50.0,
                transformDate(dateFormat.parse("31.12.2004")), "title1", "1");

        Item itemByName = publicAuction.getItemByDescription(description);

        assertEquals(itemByName, item);
    }

    @Test
    void getAvgItemCost() {
        Map<User, Double> userAndAvgItemCost = new HashMap<>();
        userAndAvgItemCost.put(
                new User("1", "address1", "VASYA1", "VASYALogin1", "VASYAPass1"),
                75.0
        );
        userAndAvgItemCost.put(
                new User("2", "address2", "VASYA2", "VASYALogin2", "VASYAPass2"),
                120.0
        );
        Map<User, Double> avgItemCost = publicAuction.getAvgItemCost();

        assertEquals(userAndAvgItemCost, avgItemCost);
    }

    @Test
    void getMaxBidsForEveryItem() throws ParseException {
        Map<Item, Bid> itemsAndBids = new HashMap<>();
        itemsAndBids.put(
                new Item("1", 1.0, true,
                        "description1", transformDate(dateFormat.parse("31.12.2004")), 50.0,
                        transformDate(dateFormat.parse("31.12.2004")), "title1", "1"),
                new Bid("2", transformDate(dateFormat.parse("31.12.2004")), 20.0, "1", "2")
        );
        itemsAndBids.put(
                new Item("2", 2.0, false,
                        "description2", transformDate(dateFormat.parse("31.12.2004")), 100.0,
                        transformDate(dateFormat.parse("31.12.2004")), "title2", "1"),
                new Bid("3", transformDate(dateFormat.parse("31.12.2004")), 30.0, "2", "3")
        );
        Map<Item, Bid> maxBidsForEveryItem = publicAuction.getMaxBidsForEveryItem();

        assertEquals(itemsAndBids, maxBidsForEveryItem);
    }

    @Test
    void getUserActualBids() throws ParseException {
        long userId = 3;
        List<Bid> userBids = Lists.newArrayList(
                new Bid("3", transformDate(dateFormat.parse("31.12.2004")), 30.0, "2", "3")
        );
        List<Bid> userActualBids = publicAuction.getUserActualBids(userId);

        assertIterableEquals(userBids, userActualBids);
    }

    @Test
    void createUser() {
        User user = new User("4", "address1", "VASYA1", "VASYALogin1", "VASYAPass1");

        assertTrue(publicAuction.createUser(user));

        User dbItem = jdbcTemplate.queryForObject("SELECT * FROM users WHERE user_id=?", (resultSet, i) -> {
            User res = new User();
            res.setUserId(resultSet.getString("user_id"));
            return res;
        }, 4);
        assertEquals(dbItem.getUserId(), user.getUserId());

        jdbcTemplate.execute("DELETE FROM users WHERE user_id=4");
    }

    @Test
    void createItem() throws ParseException {
        Item item = new Item("4", 1.0, false,
                "description4", transformDate(dateFormat.parse("31.12.2004")), 100.0,
                transformDate(dateFormat.parse("31.12.2004")), "title4", "1");

        assertTrue(publicAuction.createItem(item));

        Item dbItem = jdbcTemplate.queryForObject("SELECT * FROM items WHERE item_id=?", (resultSet, i) -> {
            Item res = new Item();
            res.setItemId(resultSet.getString("item_id"));
            res.setStartDate(resultSet.getDate("start_date"));
            return res;
        }, 4);
        assertEquals(dbItem.getItemId(), item.getItemId());
        assertEquals(dbItem.getStartDate(), item.getStartDate());

        jdbcTemplate.execute("DELETE FROM items WHERE item_id=4");
    }

    @Test
    void createBid() throws ParseException {
        Bid bid = new Bid("4", transformDate(dateFormat.parse("31.12.2004")), 10.0, "1", "1");

        assertTrue(publicAuction.createBid(bid));

        Bid dbItem = jdbcTemplate.queryForObject("SELECT * FROM bids WHERE bid_id=?", (resultSet, i) -> {
            Bid res = new Bid();
            res.setBidId(resultSet.getString("bid_id"));
            res.setBidDate(resultSet.getDate("bid_date"));
            return res;
        }, 4);
        assertEquals(dbItem.getBidId(), dbItem.getBidId());
        assertEquals(dbItem.getBidDate(), bid.getBidDate());

        jdbcTemplate.execute("DELETE FROM bids WHERE bid_id=4");
    }

    @Test
    void deleteUserBids() throws ParseException {
        User user = new User("4", "address1", "VASYA1", "VASYALogin1", "VASYAPass1");
        Bid bid = new Bid("4", transformDate(dateFormat.parse("31.12.2004")), 10.0, "1", "4");
        publicAuction.createUser(user);
        publicAuction.createBid(bid);

        assertTrue(publicAuction.deleteUserBids(Long.parseLong(user.getUserId())));
        assertThrows(EmptyResultDataAccessException.class, () -> {
            jdbcTemplate.queryForObject("SELECT * FROM bids WHERE bid_id=?", (resultSet, i) -> {
                Bid res = new Bid();
                res.setBidId(resultSet.getString("bid_id"));
                res.setBidDate(resultSet.getDate("bid_date"));
                return res;
            }, 4);
        });

        jdbcTemplate.execute("DELETE FROM users WHERE user_id=4");
    }


    @Test
    void doubleItemsStartPrice() throws ParseException {
        long userId = 3;
        Item item = new Item("4", 1.0, false,
                "description4", transformDate(dateFormat.parse("31.12.2004")), 100.0,
                transformDate(dateFormat.parse("31.12.2004")), "title4", "3");
        publicAuction.createItem(item);

        assertTrue(publicAuction.doubleItemsStartPrice(userId));
        Item dbItem = jdbcTemplate.queryForObject("SELECT * FROM items WHERE item_id=?", (resultSet, i) -> {
            Item res = new Item();
            res.setItemId(resultSet.getString("item_id"));
            res.setStartDate(resultSet.getDate("start_date"));
            res.setStartPrice(resultSet.getDouble("start_price"));
            return res;
        }, 4);
        assertEquals(item.getStartPrice() * 2, dbItem.getStartPrice(), 0.1);

        jdbcTemplate.execute("DELETE FROM items WHERE item_id=4");
    }

    private Date transformDate(java.util.Date date) {
        return new Date(date.getTime());
    }
}