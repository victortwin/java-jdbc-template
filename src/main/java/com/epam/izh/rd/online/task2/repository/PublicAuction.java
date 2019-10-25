package com.epam.izh.rd.online.task2.repository;

import com.epam.izh.rd.online.task2.entity.Bid;
import com.epam.izh.rd.online.task2.entity.Item;
import com.epam.izh.rd.online.task2.entity.User;

import java.util.List;
import java.util.Map;

public interface PublicAuction {

    /**
     * Список ставок данного пользователя
     */
    List<Bid> getUserBids(long id);

    /**
     * Список лотов данного пользователя
     */
    List<Item> getUserItems(long id);

    /**
     * Поиск лотов по подстроке в названии
     */
    Item getItemByName(String name);

    /**
     * Поиск лотов по подстроке в описании
     */
    Item getItemByDescription(String name);

    /**
     * Средняя цена лотов каждого пользователя
     */
    Map<User, Double> getAvgItemCost();

    /**
     * Максимальный размер имеющихся ставок на каждый лот
     */
    Map<Item, Bid> getMaxBidsForEveryItem();

    /**
     * Список действующих лотов данного пользователя
     */
    List<Bid> getUserActualBids(long id);

    /**
     * Добавить нового пользователя
     */
    boolean createUser(User user);

    /**
     * Добавить новый лот
     */
    boolean createItem(Item item);

    /**
     *  Увеличить стартовые цены товаров данного пользователя вдвое
     */
    boolean deleteUserBids(long id);

    /**
     *  Увеличить стартовые цены товаров данного пользователя вдвое
     */
    boolean doubleItemsStartPrice(long id);

}
