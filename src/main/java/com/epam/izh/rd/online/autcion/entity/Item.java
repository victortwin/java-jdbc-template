package com.epam.izh.rd.online.autcion.entity;

import javax.persistence.*;
import java.sql.Date;

/**
 * Лот в ставке
 */
@Entity
public class Items {

    @Id
    @GeneratedValue
    private long item_id;
    private String title;
    private String description;
    private double start_price;
    private double bid_increment;
    private Date start_date;
    private Date stop_date;
    private boolean buy_it_now;
    @OneToMany
    @JoinColumn(name = "user_id")
    private long users_user_id;
}
