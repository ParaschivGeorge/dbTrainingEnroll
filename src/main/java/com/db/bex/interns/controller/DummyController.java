package com.db.bex.interns.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DummyController{

    @GetMapping("/dummy/")
    public @ResponseBody
    String search(){
        return "[{\"id\":1,\"name\":\"dummy1\"},{\"id\":2,\"name\":\"dummy2\"}]";
    }
}