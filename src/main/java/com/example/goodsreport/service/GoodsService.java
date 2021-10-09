package com.example.goodsreport.service;

import com.example.goodsreport.dto.GoodsDto;

import com.google.common.base.Strings;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GoodsService {

    public GoodsDto readFile() throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        List<GoodsDto> goodsDtoList = new ArrayList<>();
        String[] partOfLine;
        String line;
        while ((line = reader.readLine()) != null) {
            partOfLine = line.split("#");
            GoodsDto goodsDto = new GoodsDto();

            if (!Strings.isNullOrEmpty(line)) {
                goodsDto.setNameGoods(partOfLine[0]);
                try {
                    goodsDto.setDate(LocalDate.parse((partOfLine[1])));
                } catch (DateTimeException dte) {
                    goodsDto.setDate(LocalDate.now());
                }
                if (partOfLine.length>2){
                    goodsDto.setPrice(Integer.parseInt(partOfLine[2]));
                } else{
                    goodsDto.setPrice(0);
                }
                goodsDtoList.add(goodsDto);
            }
        }

        return processing(goodsDtoList);
    }

    public GoodsDto processing(List<GoodsDto> goodsDtoList) {
        Map<String, Integer> valueOfGoods = goodsDtoList.stream()
                .collect(Collectors.groupingBy(GoodsDto::getNameGoods,
                        Collectors.summingInt(GoodsDto::getPrice)));

        List<GoodsDto> mostExpensiveGoods = mapToList(valueOfGoods);

        GoodsDto expensiveGoods = mostExpensiveGoods.stream()
                .max(Comparator.comparing(GoodsDto::getPrice)).get();

        expensiveGoods.setDate(goodsDtoList.stream()
                .filter(n -> n.getNameGoods().equals(expensiveGoods.getNameGoods()))
                .map(GoodsDto::getDate).max(LocalDate::compareTo).get());

        return expensiveGoods;
    }

    public List<GoodsDto> mapToList(Map<String, Integer> valueOfGoods) {
        List<GoodsDto> sumPrices = new ArrayList<>();
        for (String name : valueOfGoods.keySet()) {
            GoodsDto goodsDto = new GoodsDto();
            goodsDto.setNameGoods(name);
            goodsDto.setPrice((valueOfGoods.get(name)));
            sumPrices.add(goodsDto);
        }
        return sumPrices;
    }
}
