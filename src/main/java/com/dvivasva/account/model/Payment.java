package com.dvivasva.account.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private String id;
    private double amount;
    private String numberPhoneOrigin;
    private String numberPhoneDestination;
    private Date date;
}
