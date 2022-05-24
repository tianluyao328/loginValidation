package me.tianluyao.loginValidation.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("index")
public class WebController {

    @Autowired
    private RedisTemplate emRedisTemplate;

    @RequestMapping("test")
    public String testPage(){
        return "test/test";
    }

    @RequestMapping("login")
    public String login(){
        return "login/login";
    }

    @RequestMapping("index")
    public String index(){
        return "index";
    }

    @ResponseBody
    @RequestMapping("loginCheck")
    public String loginCheck(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = request.getParameter("username");
        String passWord = request.getParameter("password");

        String rightUserName = "admin";
        String rightPassWord = "123123";

        Map<String, String> resultMap = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();

        if(rightUserName.equals(userName) && rightPassWord.equals(passWord)){
            String token = "user:"+ UUID.randomUUID()+"";

            emRedisTemplate.opsForValue().set(token, rightUserName, Duration.ofMinutes(30L));
            Long expire = emRedisTemplate.getExpire(token);

            request.getSession().setAttribute("token", token);

            resultMap.put("code", "200");
            resultMap.put("isSuccess", "1");
            resultMap.put("errorCode", "00000");
            resultMap.put("errorMessage", "登录成功");
            resultMap.put("message", "登录成功");
            resultMap.put("token", token);
        }else{
            resultMap.put("code", "200");
            resultMap.put("isSuccess", "0");
            resultMap.put("errorCode", "A0001");
            resultMap.put("errorMessage", "用户名或密码输入错误！");
            resultMap.put("message", "用户名或密码输入错误！");
        }

        String resultJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultMap);

        return resultJson;
    }

}
