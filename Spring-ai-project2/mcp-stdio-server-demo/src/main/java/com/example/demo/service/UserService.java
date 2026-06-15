package com.example.demo.service;

import com.example.demo.entity.UserInfo;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    static Map<String , UserInfo> userInfoMap = new HashMap<>();

    static {
        userInfoMap.put("zhangsan", new UserInfo("zhangsan", 18, "男", "北京"));
        userInfoMap.put("lisi", new UserInfo("lisi", 19, "男", "上海"));
        userInfoMap.put("wangwu", new UserInfo("wangwu", 20, "女", "广州"));
        userInfoMap.put("zhaoliu", new UserInfo("zhaoliu", 21, "女", "深圳"));
        userInfoMap.put("sunqi", new UserInfo("sunqi", 22, "男", "西安"));
        userInfoMap.put("zhouba", new UserInfo("zhouba", 23, "女", "天津"));
    }

    @Tool(description = "根据用户的姓名,返回用户的信息")
    public String getUserInfo(@ToolParam(description = "用户的姓名") String name){
        if(userInfoMap.containsKey(name)){
            return userInfoMap.get(name).toString();
        }

        return "未查询到用户信息";
    }
}
