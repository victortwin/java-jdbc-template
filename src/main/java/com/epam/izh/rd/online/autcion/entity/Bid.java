package com.epam.izh.rd.online.autcion.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * Ставка
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bid {
    private String bidId;
    private Date bidDate;
    private Double bidValue;
    private String itemId;
    private String userId;
}
