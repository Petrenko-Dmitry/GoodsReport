package com.example.goodsreport;

import com.example.goodsreport.dto.GoodsDto;
import com.example.goodsreport.service.GoodsService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class GoodsReportApplication {

    public static void main(String[] args) throws IOException {
        GoodsService goodsService = new GoodsService();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        GoodsDto goodsDto = goodsService.readFile();
        System.out.printf(goodsDto.getNameGoods() + " " + goodsDto.getDate().format(formatter) + " " + goodsDto.getPrice());
    }

}
