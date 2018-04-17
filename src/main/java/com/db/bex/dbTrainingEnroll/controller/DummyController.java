package com.db.bex.dbTrainingEnroll.controller;

import com.db.bex.dbTrainingEnroll.dto.DummyDto;
import com.db.bex.dbTrainingEnroll.service.DummyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
public class DummyController{

    @Autowired
    private DummyService dummyService;

    @GetMapping("/dummy/")
    public @ResponseBody
    List<DummyDto> search(Principal principal){
        return dummyService.search();
    }
}