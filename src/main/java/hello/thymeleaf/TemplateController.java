package hello.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/template")
public class TemplateController {

    //1. 템플릿 박아버리기
    @GetMapping("/fragment")
    public String template(){
        return "template/fragment/fragmentMain";
    }

    //2. 템플릿 레이아웃
    @GetMapping("layout")
    public String layout(){
        return "template/layout/layoutMain";
    }

    //3. html에 일부가 아니라 전체 html에 적용하고 싶을때
    @GetMapping("/layoutExtends")
    public String layoutExtend(){
        return "layoutExtend/layoutExtendMain";
    }
}
