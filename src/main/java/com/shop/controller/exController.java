package com.shop.controller;

import com.shop.dto.ItemDto;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/ex")
public class exController {

    @GetMapping(value = "/ex01")
    public String ex01(Model model){
        ItemDto itemDto = new ItemDto();
        itemDto.setItemDetail("상품 상셋 설명");
        itemDto.setItemNm("테스트 상품1");
        itemDto.setPrice(10000);
        itemDto.setRegTime(LocalDateTime.now());

        model.addAttribute("itemDto", itemDto);
        return "ex/ex01";
    }

    @GetMapping(value = "/ex02")
    public String ex02(Model model){

        List<ItemDto> itemDtoList = new ArrayList<>();

        for(int i=0; i<=10; i++){
            ItemDto itemDto = new ItemDto();
            itemDto.setItemDetail("상품 상셋 설명" + i);
            itemDto.setItemNm("테스트 상품1" + i);
            itemDto.setPrice(1000 + i);
            itemDto.setRegTime(LocalDateTime.now());
            itemDtoList.add(itemDto);
        }

        model.addAttribute("itemList", itemDtoList);
        return "ex/ex02";
    }
}
