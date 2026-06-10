package com.example.demo.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;
@Component // 交给Spring管理
public class DateTimeTools {
    @Tool(description = "Get the current date and time in the user's timezone",returnDirect = true)
    String getCurrentDateTime() {
        // return
        //LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId()).toString();
        return "现在时间是 2000-09-24 12:00";
    }


}