package com.epam.izh.rd.online.autcion.repository;

import com.epam.izh.rd.online.autcion.entity.Bid;
import com.epam.izh.rd.online.autcion.entity.Item;
import com.epam.izh.rd.online.autcion.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class JdbcTemplatePublicAuction implements PublicAuction {

    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Bid> getUserBids(long id) {
        return null; //TODO
    }

    @Override
    public List<Item> getUserItems(long id) {
        return null; //TODO
    }

    @Override
    public Item getItemByName(String name) {
        return null; //TODO
    }

    @Override
    public Item getItemByDescription(String name) {
        return null; //TODO
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
        return false; //TODO
    }

    @Override
    public boolean createItem(Item item) {
        return false; //TODO
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
