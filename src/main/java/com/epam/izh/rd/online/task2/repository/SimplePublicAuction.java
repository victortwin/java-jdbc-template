package com.epam.izh.rd.online.task2.repository;

import com.epam.izh.rd.online.task2.entity.Bid;
import com.epam.izh.rd.online.task2.entity.Item;
import com.epam.izh.rd.online.task2.entity.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class SimplePublicAuction implements PublicAuction {

    private Connection connection;

    public SimplePublicAuction(String url, String user, String password) {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
