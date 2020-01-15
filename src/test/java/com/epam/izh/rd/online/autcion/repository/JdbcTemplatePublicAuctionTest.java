package com.epam.izh.rd.online.autcion.repository;


import com.epam.izh.rd.online.autcion.entity.Bid;
import com.epam.izh.rd.online.autcion.entity.Item;
import com.epam.izh.rd.online.autcion.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        long userId = 1;
        List<Bid> userBids = new ArrayList<Bid>() {{
            add(new Bid("1", transformDate(dateFormat.parse("31.12.2004")), 10.0, "1", "1"));
            add(new Bid("2", transformDate(dateFormat.parse("31.12.2004")), 20.0, "2", "1"));
        }};

        List<Bid> items = publicAuction.getUserBids(userId);

        assertIterableEquals(userBids, items);
    }

    @Test
    void getUserItems() throws ParseException {
        long userId = 1;
        List<Item> userBids = new ArrayList<Item>() {{
            add(new Item("1", 1.0, false,
                    "description1", transformDate(dateFormat.parse("31.12.2004")), 100.0,
                    transformDate(dateFormat.parse("31.12.2004")), "title1", "1"));
            add(new Item("2", 2.0, false,
                    "description2", transformDate(dateFormat.parse("31.12.2004")), 100.0,
                    transformDate(dateFormat.parse("31.12.2004")), "title2", "1"));
        }};

        List<Item> items = publicAuction.getUserItems(userId);

        assertIterableEquals(userBids, items);
    }

    @Test
    void getItemByName() throws ParseException {
        String name = "title1";
        Item item = new Item("1", 1.0, false,
                "description1", transformDate(dateFormat.parse("31.12.2004")), 100.0,
                transformDate(dateFormat.parse("31.12.2004")), "title1", "1");

        Item itemByName = publicAuction.getItemByName(name);

        assertEquals(itemByName, item);
    }

    @Test
    void getItemByDescription() throws ParseException {
        String description = "description1";
        Item item = new Item("1", 1.0, false,
                "description1", transformDate(dateFormat.parse("31.12.2004")), 100.0,
                transformDate(dateFormat.parse("31.12.2004")), "title1", "1");

        Item itemByName = publicAuction.getItemByDescription(description);

        assertEquals(itemByName, item);
    }

    @Test
    void getAvgItemCost() {
        //сложна
        Map<User, Double> userAndAvgItemCost = new HashMap<User, Double>() {{
            put(
                    new User("1", "address1", "VASYA1", "VASYALogin1", "VASYAPass1"),
                    30.0
            );
            put(
                    new User("2", "address2", "VASYA2", "VASYALogin2", "VASYAPass2"),
                    30.0
            );
        }};
        Map<User, Double> avgItemCost = publicAuction.getAvgItemCost();

        assertTrue(userAndAvgItemCost.equals(avgItemCost));
    }

    @Test
    void getMaxBidsForEveryItem() {
    }

    @Test
    void getUserActualBids() {
    }

    @Test
    void createUser() {
        User user = new User("3", "address1", "VASYA1", "VASYALogin1", "VASYAPass1");

        boolean result = publicAuction.createUser(user);
        assertTrue(result);

        User dbItem = jdbcTemplate.queryForObject("SELECT * FROM users WHERE user_id=?", (resultSet, i) -> {
            User res = new User();
            res.setUserId(resultSet.getString("user_id"));
            return res;
        }, 3);
        assertEquals(dbItem.getUserId(),user.getUserId());
    }

    @Test
    void createItem() throws ParseException {
        Item item = new Item("4", 1.0, false,
                "description4", transformDate(dateFormat.parse("31.12.2004")), 100.0,
                transformDate(dateFormat.parse("31.12.2004")), "title4", "1");

        boolean result = publicAuction.createItem(item);
        assertTrue(result);

        Item dbItem = jdbcTemplate.queryForObject("SELECT * FROM items WHERE item_id=?", (resultSet, i) -> {
            Item res = new Item();
            res.setItemId(resultSet.getString("item_id"));
            res.setStartDate(resultSet.getDate("start_date"));
            return res;
        }, 4);
        assertEquals(dbItem.getItemId(),item.getItemId());
        assertEquals(dbItem.getStartDate(),item.getStartDate());
    }

    @Test
    void deleteUserBids() {
    }

    @Test
    void doubleItemsStartPrice() {
    }

    private Date transformDate(java.util.Date date){
        return new Date(date.getTime());
    }
}