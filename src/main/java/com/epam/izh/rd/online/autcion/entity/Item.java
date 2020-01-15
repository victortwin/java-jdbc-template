package com.epam.izh.rd.online.autcion.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * Лот в ставке
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private String itemId;
    private Double bidIncrement;
    private Boolean buyItNow;
    private String description;
    private Date startDate;
    private Double startPrice;
    private Date stopDate;
    private String title;
    private String userId;
}
