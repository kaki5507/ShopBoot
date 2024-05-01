package com.shop.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import com.shop.entity.QItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@DisplayName("테스트 전체")
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest() {
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        Item savedItem = itemRepository.save(item);
        System.out.println(savedItem.toString());
    }

    public void createItemList() {
        for (int i = 1; i <= 10; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNmTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNm("테스트 상품1");
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("상품명 , 상품 상세설명 OR TEST")
    public void findByItemOrItemDetailTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품1", "테스트 상품 설명4");
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("Less Than 메소드 테스트")
    public void findByLessThanItemTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThan(10002);
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("Less Than + Order By 테스트")
    public void findByLessThanItemOrderByTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPrice(10007);
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("@Query를 이용한 상품 조회 테스트")
    public void findByItemDetailTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 설명");
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("@Query + Native 속성 이용 상품 조회 테스트")
    public void findByItemDetailNativeTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetailNative("테스트 상품 설명");
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("Querydsl 설정 조회 테스트")
    public void queryDslTest(){
        this.createItemList();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QItem qItem = QItem.item;
        JPAQuery<Item> query = queryFactory.selectFrom(qItem)
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qItem.itemDetail.like("%" + "테스트 상품 상세 설명" + "%"))
                .orderBy(qItem.price.desc());

        List<Item> itemList = query.fetch();

        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    public void createItemList2(){
        for(int i=1; i<=5; i++){
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }

        for(int i=6; i<=10; i++){
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
            item.setStockNumber(0);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("QuerydslPredicateExecutor 추가")
    public void queryDslPredicateTest() {
        this.createItemList2();
        BooleanBuilder booleanBuilder = new BooleanBuilder(); // 쿼리 조건 들어갈 빌더
        QItem item = QItem.item;

        String itemDetail = "테스트 상품 상세 설명";
        int price = 10003;
        String itemSellStatus = "SELL";

        booleanBuilder.and(item.itemDetail.like("%" + itemDetail + "%"));
        booleanBuilder.and(item.price.gt(price)); // gt greater than으로 " > "

        if (StringUtils.equals(itemSellStatus, ItemSellStatus.SELL)) { // 판매상태이면
            booleanBuilder.and(item.itemSellStatus.eq(ItemSellStatus.SELL));
        }

        // 테이블 페이징 조회
        Pageable pageable = PageRequest.of(0,5);
        Page<Item> itemPagingResult = itemRepository.findAll(booleanBuilder, pageable);
        itemRepository.findAll(booleanBuilder, pageable);
        System.out.println("total : " + itemPagingResult.getTotalElements());

        List<Item> resultItemList = itemPagingResult.getContent();
        for(Item itemResult : resultItemList){
            System.out.println(itemResult.toString());
        }
    }
}