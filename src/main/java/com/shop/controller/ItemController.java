package com.shop.controller;

import com.shop.dto.ItemFormDto;
import com.shop.service.FileService;
import com.shop.service.ItemImgService;
import com.shop.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ItemImgService itemImgService;
    private final FileService fileService;

    @PostMapping(value = "/admin/item/new")
    public String itemNew(@Valid ItemFormDto itemFormDto,
                          BindingResult bindingResult,
                          Model model,
                          @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList){

        if(bindingResult.hasErrors()){ // 상품 등록 시 필수 값 없으면 다시 상품 등록 페이지로
            return "item/itemForm";
        }

        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){ // 상품 등록 시 첫 번째 이미지 없으면 등록 페이지로 전환
            model.addAttribute("errorMessage","첫번째 상품 이미지는 필수 입력 값임.");
             return "item/itemForm";
        }

        try{
            itemService.saveItem(itemFormDto, itemImgFileList); // 상품 저장 로직 가져옴
        } catch (Exception e){
            model.addAttribute("errorMessage","상품 등록중 에러 발생");
            return "item/itemForm";
        }

        return "redirect:/";
    }
}
