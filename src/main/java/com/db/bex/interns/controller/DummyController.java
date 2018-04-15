package com.db.bex.interns.controller;

import com.db.bex.interns.dto.DummyDto;
import com.db.bex.interns.service.DummyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DummyController{

    @Autowired
    private DummyService dummyService;

    @GetMapping("/dummy/")
    public @ResponseBody
    List<DummyDto> search(){
        return dummyService.search();
    }
}