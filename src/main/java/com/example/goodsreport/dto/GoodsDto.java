package com.example.goodsreport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDto {
    private String nameGoods;
    private LocalDate date;
    private Integer price;
}

