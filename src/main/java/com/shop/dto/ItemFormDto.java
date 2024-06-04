package com.shop.dto;

import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ItemFormDto {

    private Long id;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String itemNm;

    @NotBlank(message = "가격은 필수 입력 값입니다.")
    private Integer price;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String itemDetail;

    @NotNull(message = "재고는 필수 입력 값입니다.")
    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();

    private List<Long> itemImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Item creatItem(){
        return modelMapper.map(this, Item.class); // Item 클래스 모델에 담어서 생성
    }

    public static ItemFormDto of(Item item){
        return modelMapper.map(item,ItemFormDto.class);
    }

}
