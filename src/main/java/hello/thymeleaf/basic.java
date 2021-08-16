package hello.thymeleaf;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/basic")
public class basic {

    //1
    @GetMapping("text-basic")
    public String textBasic(Model model){
        model.addAttribute("data","Hello Spring!");
        //        model.addAttribute("data","<b> Hello Spring </b>!");
        /*
         * 우리는 위의 데이터 전달이 <br>로 인해서 글씨가 진하게 떴으면 하는 것을 예상했는데 &lt--- 라며 html엔티티로 바뀐다.
         * html 은 <를 태그의 시작으로 인식한다. 렌더링 할때 < >를 $lt gt 등으로 바꿔서 보여준다.
         * 그게 escape 라고 한다. (실제 태그를 렌터링 하지 않는다)
         *
         * 이스케이프를 사용하지 않으려면 어떻게 해야 되는가?
         * [[]] 를 [()] 로 바꿔준다.
         */
        return "basic/text-basic";
    }

    //2 이스케이프 사용하지 않는 것 th:inline=false
    @GetMapping("text-unescaped")
    public String textUnescaped(Model model){
        model.addAttribute("data","<b> Hello <b> Spring!");
        return "basic/text-unescaped";
    }

    //3 변수 타임리프 문법확인하기
    @GetMapping("/variable")
    public String variable(Model model){
        User userA = new User("userA", 10);
        User userB = new User("userB", 10);

        List<User> list = new ArrayList<>();
        list.add(userA);
        list.add(userB);

        Map<String, User> map = new HashMap<>();
        map.put("userA", userA);
        map.put("userB", userB);

        model.addAttribute("user", userA);
        model.addAttribute("users", list);
        model.addAttribute("userMap",map);


        return "basic/variable";


    }

    @Data
    static class User{
        private String username;
        private int age;

        public User(String username, int age) {
            this.username = username;
            this.age = age;
        }
    }


    //4 객체 request , session 등등 @빈이름으로 직접 접근하는 방법
    @GetMapping("/basic-objects")
    public String basicObject(HttpSession session){
        session.setAttribute("sessionData", "Hello Session");
        return "basic/basic-objects";
    }

    //5
    @Component("helloBean")
    static class HelloBean{
        public String hello(String data){
            return "Hello" + data;
        }
    }

    //6 날짜 , 유틸 등등 여기서는 날짜만 다룬다 나머지는유틸은 타임리프 문서를 보기로 하자.
    @GetMapping("/date")
    public String date(Model model){
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "basic/date";
    }

    //7 url 접근방법
    @GetMapping("link")
    public String link(Model model){
        model.addAttribute("param1","data1");
        model.addAttribute("param2","data2");
        return "basic/link";
    }

    //8 리터럴
    @GetMapping("/literal")
    public String literal(Model model){
        model.addAttribute("data", "Spring!");
        return "basic/literal";
    }

    //9 앨비스
    @GetMapping("/operation")
    public String operation(Model model){
        model.addAttribute("data", "Spring!");
        model.addAttribute("nullData", null);
        return "basic/operation";
    }


    //10.속성 추가 (체크옵션까지)
    @GetMapping("/attribute")
    public String attribute(){
        return "basic/attribute";
    }

    //11 반복(each 속성과 반복상태에 대한 코드)
    @GetMapping("/each")
    public String each(Model model){
        addUsers(model);
        return "basic/each";
    }

    public void addUsers(Model model){
        List<User> list = new ArrayList<>();
        list.add(new User("UserA",10));
        list.add(new User("UserB",20));
        list.add(new User("UserC",30));

        model.addAttribute("users",list);
    }

    //12 if/ unless
    @GetMapping("/condition")
    public String condition(Model model) {
        addUsers(model);
        return "basic/condition";
    }

    //13 타임리프식 주석??
    @GetMapping("/comments")
    public String comments(Model model) {
        model.addAttribute("data", "Spring!");
        return "basic/comments";
    }

    //14 타임리프가 제공하는 유일한 자체태그
    @GetMapping("/block")
    public String block(Model model){
        addUsers(model);
        return "basic/block";
    }

    //15 자바스크립트에서 타임리프를 사용하는 방법
    @GetMapping("/javascript")
    public String javascript(Model model){
        model.addAttribute("user",new User("UserA",10));
        addUsers(model);
        return "basic/javascript";
    }

}
