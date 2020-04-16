package com.cheddar.robinhood.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Execution {
    private String id;

    private String price;

    private String quantity;

    private Date settlement_date;

    private String timestamp;


}
