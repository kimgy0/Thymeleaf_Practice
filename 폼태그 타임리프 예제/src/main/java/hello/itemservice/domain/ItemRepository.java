package hello.itemservice.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {
    private static final Map<Long, Item> store = new HashMap<>(); //실전에서는 해시맵 사용 금지
                                                                    //멀티스레드가 여러개 붙으면 x 콘커런트해시맵
    private static long sequence = 0L;

    public Item save(Item item){
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id){
        return store.get(id);
    }

    public List<Item> findAll(){
        return new ArrayList<>(store.values());
        // 한 번 감싼 이유는 나중에 싱글톤이라 상관없긴 한데 hashmap에 직접적인 추가도 없고,,? 타입체킹 문제도 없고?,,,,
    }

    public void update(Long itemId, Item updateParam){
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
        findItem.setOpen(updateParam.getOpen());
        findItem.setRegions(updateParam.getRegions());
        findItem.setDeliveryCode(updateParam.getDeliveryCode());
        findItem.setItemType(updateParam.getItemType());

        //아이디가 사용이 안된다. set을 함부로 해줄 수도 있으니 dto나 그런거 하나 만들어서 명확하게 하는게 낫다.
    }

    public void clearStore(){
        store.clear();
    }
}
