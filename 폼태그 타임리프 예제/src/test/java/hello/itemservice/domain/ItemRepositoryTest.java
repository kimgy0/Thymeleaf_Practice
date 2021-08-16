package hello.itemservice.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

//junit5에서는 public 안적어줘도됨.
class ItemRepositoryTest {
    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    //테스트가 끝날때마다 이 메서드를 실행한다.
    void afterEach(){
        itemRepository.clearStore();
    }


    @Test
    void save(){
        //given
        Item item = new Item("itemA", 10000, 10);
        //when
        Item save = itemRepository.save(item);
        //then
        Item byId = itemRepository.findById(save.getId());

        assertThat(byId).isEqualTo(save);
    }


    @Test
    void findAll(){
        //given
        Item item1 = new Item("item1", 10000, 10);
        Item item2 = new Item("item2", 10000, 10);
        Item item3 = new Item("item3", 10000, 10);

        //when
        Item saveItem1 = itemRepository.save(item1);
        Item saveItem2 = itemRepository.save(item2);
        Item saveItem3 = itemRepository.save(item3);

        //then
        List<Item> findAllItems = itemRepository.findAll();
        assertThat(findAllItems.size()).isEqualTo(3);
        assertThat(findAllItems).contains(item1, item2, item3);
    }

    @Test
    void updateItem(){
        //given
        Item item1 = new Item("item1", 10000, 10);


        //when
        Item saveItem1 = itemRepository.save(item1);
        Long item1Id = item1.getId();

        Item updateParam = new Item("item2", 20000, 30);
        itemRepository.update(item1Id, updateParam);


        //then
        Item byId = itemRepository.findById(item1Id);
        assertThat(byId.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(byId.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(byId.getQuantity()).isEqualTo(updateParam.getQuantity());


    }

}