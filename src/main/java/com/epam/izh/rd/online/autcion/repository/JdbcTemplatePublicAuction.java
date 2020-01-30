package com.epam.izh.rd.online.autcion.repository;

import com.epam.izh.rd.online.autcion.entity.Bid;
import com.epam.izh.rd.online.autcion.entity.Item;
import com.epam.izh.rd.online.autcion.entity.User;
import com.epam.izh.rd.online.autcion.mappers.BidMapper;
import com.epam.izh.rd.online.autcion.mappers.ItemMapper;
import com.epam.izh.rd.online.autcion.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

@Repository
public class JdbcTemplatePublicAuction implements PublicAuction {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BidMapper bidMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Bid> getUserBids(long id) {

        String sql = "select * from bids where user_id = ?";

        List<Bid> bidList = jdbcTemplate.query(sql, new Object[]{id}, bidMapper);

        return bidList;
    }

    @Override
    public List<Item> getUserItems(long id) {

        String sql = "select * from items where user_id = ?";

        List<Item> itemList = jdbcTemplate.query(sql, new Object[]{id}, itemMapper);

        return itemList;
    }

    @Override
    public Item getItemByName(String name) {

        String sql = "select * from items where title like '%" + name + "%' limit 1";

        return jdbcTemplate.queryForObject(sql, itemMapper);
    }

    @Override
    public Item getItemByDescription(String name) {

        String sql = "select * from items where description like '%" + name + "%' limit 1";

        return jdbcTemplate.queryForObject(sql, itemMapper);
    }

    @Override
    public Map<User, Double> getAvgItemCost() {

        Map<User, Double> userAndAvgItemCost = new HashMap<>();
        List<User> userList = jdbcTemplate.query("select * from users", userMapper);
        List<Item> itemList;
        double summaryItemCost;

        for (User user : userList) {
            itemList = getUserItems(user.getUserId());
            summaryItemCost = 0;
            for (Item item : itemList) {
                summaryItemCost += item.getStartPrice();
            }
            if (itemList.size() > 0) {
                userAndAvgItemCost.put(user, summaryItemCost / itemList.size());
            }
        }
        return userAndAvgItemCost;
    }

    @Override
    public Map<Item, Bid> getMaxBidsForEveryItem() {

        Map<Item, Bid> itemBidMap = new HashMap<>();
        List<Item> itemList = jdbcTemplate.query("select * from items", itemMapper);
        List<Bid> bidList;
        Bid maxBid;

        for (Item item : itemList) {
            maxBid = null;
            bidList = jdbcTemplate.query("select * from bids where item_id = " + item.getItemId(), bidMapper);
            for (Bid bid : bidList) {
                if (maxBid == null || bid.getBidValue() > maxBid.getBidValue()) {
                    maxBid = bid;
                }
                itemBidMap.put(item, maxBid);
            }
        }
        return itemBidMap;
    }

    @Override
    public List<Bid> getUserActualBids(long id) {

        List<Bid> userBidList;

        return emptyList();
    }

    @Override
    public boolean createUser(User user) {

        List<User> existingUsersList = jdbcTemplate.query("select * from users", userMapper);

        for (User existingUser : existingUsersList) {
            if (user.getUserId().equals(existingUser.getUserId())) {
                return false;
            }
        }
        int affectedRows = jdbcTemplate.update("insert into users values (?, ?, ?, ?, ?)",
                user.getUserId(), user.getBillingAddress(), user.getFullName(), user.getLogin(), user.getPassword());
        return affectedRows > 0;
    }

    @Override
    public boolean createItem(Item item) {

        List<Item> existingItemsList = jdbcTemplate.query("select * from items", itemMapper);

        for (Item existingItem : existingItemsList) {
            if (item.getUserId().equals(existingItem.getUserId())) {
                return false;
            }
        }
        int affectedRows = jdbcTemplate.update("insert into items values (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                item.getItemId(), item.getBidIncrement(), item.getBuyItNow(), item.getDescription(),
                item.getStartDate(), item.getStartPrice(), item.getStopDate(), item.getTitle(), item.getUserId());
        return affectedRows > 0;
    }

    @Override
    public boolean createBid(Bid bid) {
        List<Bid> existingBidList = jdbcTemplate.query("select * from bids", bidMapper);

        for (Bid existingBid : existingBidList) {
            if (bid.getUserId().equals(existingBid.getUserId())) {
                return false;
            }
        }
        int affectedRows = jdbcTemplate.update("insert into bids values (?, ?, ?, ?, ?)",
                bid.getBidId(), bid.getBidDate(), bid.getBidValue(), bid.getItemId(),
                bid.getUserId());
        return affectedRows > 0;
    }

    @Override
    public boolean deleteUserBids(long id) {
        return false;
    }

    @Override
    public boolean doubleItemsStartPrice(long id) {
        return false;
    }
}
