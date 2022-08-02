package vttp2022.ssf.day15redis.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping (path={"/", ""})
public class GreetingsController {
    
    @Autowired
    @Qualifier("myredislab")
    private RedisTemplate<String, String> redisTemplate;
    
    @GetMapping
    public String getGreetings(Model model) {
        
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        
        Object greetings = ops.get("greetings");
        model.addAttribute("hello", greetings.toString());
        return "index";
    }

    @PostMapping (path={"/update"})
    public String updateText 
    (@RequestBody MultiValueMap<String, String> form,
    Model model) {

        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        String text = form.getFirst("name");

        ops.set("greetings", text);

        model.addAttribute("hello", text);

        return "index";


    }
}
