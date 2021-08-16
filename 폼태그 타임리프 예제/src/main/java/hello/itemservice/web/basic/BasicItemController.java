package hello.itemservice.web.basic;

import hello.itemservice.domain.DeliveryCode;
import hello.itemservice.domain.Item;
import hello.itemservice.domain.ItemRepository;
import hello.itemservice.domain.ItemType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
@Slf4j
public class BasicItemController {

    private final ItemRepository itemRepository;

    @ModelAttribute("regions")
    public Map<String, String> regions(){
        Map<String, String> regions = new LinkedHashMap<>();
        // 일반 해시맵은 순서 보장이 되지 않음.
        regions.put("SEOUL","서울");
        regions.put("BUSAN","부산");
        regions.put("JEJU","제주주");

        return regions;

//        Map<String, String> regions = new LinkedHashMap<>();
//        // 일반 해시맵은 순서 보장이 되지 않음.
//        regions.put("SEOUL","서울");
//        regions.put("BUSAN","부산");
//        regions.put("JEJU","제주주");
//
//        model.addAttribute("regions",regions);

//        기존 컨트롤러에서는 이거를 일일이 넣어줘야 했는데 그러면 코드가 중복이 너무 심하니까
//        modelAttribute를 쓰면 이런식으로 중복을 줄이고 컨트롤러에서 어떤메서드가 실행되던지 간에 model객체에 return한 것을 담아준다.
    }

    @ModelAttribute("itemTypes")
    public ItemType[] itemTypes(){
        ItemType[] values = ItemType.values();
        return ItemType.values();
    }




    @ModelAttribute("deliveryCodes")
    public List<DeliveryCode> deliveryCodes(){
        List<DeliveryCode> deliveryCodes = new ArrayList<>();
        deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
        deliveryCodes.add(new DeliveryCode("NORMAL", "일반 배송"));
        deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));
        return deliveryCodes;
    }








    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items",items);
        return "basic/items";
    }
    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(Model model){
        model.addAttribute("item", new Item());
       return "basic/addForm";
    }

//    @PostMapping("/add")
//    public String save(@ModelAttribute("item") Item item, Model model){
//        itemRepository.save(item);
//        return "basic/item";
//    }

//    @PostMapping("/add")
//    public String save(@ModelAttribute("item") Item item, Model model){
//        itemRepository.save(item);
//        return "redirect:/basic/items"+item.getId();
//        //리디렉트로 아예 초기화하고 남아있던 정보를 싹다 지우면서 get으로 요청하는거
//        /**
//         * prg post/redirect/get
//         */
//    }



    @PostMapping("/add")
    public String save(Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId",savedItem.getId());
        //이렇게 리디렉트에 넘겨주게 되면 id값이 리턴의 itemId로 넘어가게 되면서

        redirectAttributes.addAttribute("status",true);
        //이문장은 ?status="true" 이런식으로 쿼리파라미터 형식으로 넘어가게된다.
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String aditForm(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId,
                       @ModelAttribute Item item){
        itemRepository.update(itemId,item);
        return "redirect:/basic/items/{itemId}";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA",10000,10));
        itemRepository.save(new Item("itemB",20000,20));
        log.info("{}",itemRepository.findAll());
    }

}
