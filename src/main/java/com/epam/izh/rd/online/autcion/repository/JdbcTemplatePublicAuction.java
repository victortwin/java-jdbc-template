package com.epam.izh.rd.online.autcion.repository;

import com.epam.izh.rd.online.autcion.entity.Bid;
import com.epam.izh.rd.online.autcion.entity.Item;
import com.epam.izh.rd.online.autcion.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Collections.*;

public class JdbcTemplatePublicAuction implements PublicAuction {

    @Override
    public List<Bid> getUserBids(long id) {
        return emptyList();
    }

    @Override
    public List<Item> getUserItems(long id) {
        return emptyList();
    }

    @Override
    public Item getItemByName(String name) {
        return new Item();
    }

    @Override
    public Item getItemByDescription(String name) {
        return new Item();
    }

    @Override
    public Map<User, Double> getAvgItemCost() {
        return emptyMap();
    }

    @Override
    public Map<Item, Bid> getMaxBidsForEveryItem() {
        return emptyMap();
    }

    @Override
    public List<Bid> getUserActualBids(long id) {
        return emptyList();
    }

    @Override
    public boolean createUser(User user) {
        return false;
    }

    @Override
    public boolean createItem(Item item) {
        return false;
    }

    @Override
    public boolean createBid(Bid bid) {
        return false;
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
