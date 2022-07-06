package com.example.demo.controller;


import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/android")
public class CodeController {

    @PostMapping(value = "/operation")
    public String sendCmd(@RequestBody Map<String, Object> map) {
        String id = map.get("id").toString();
        String codeID = map.get("codeId").toString();
//        String textSize = map.get("textSize").toString();

//        String message = new Gson().toJson(codeID+msg);
        String message = codeID + ">" + id;
        System.out.println("message in json is :"+message);

        return WebSocket.sendMessage(message,101);

    }

    @RequestMapping(value = "/test")
    public String test(){
        System.out.print("test");
        return "This is a test.";
    }

}
