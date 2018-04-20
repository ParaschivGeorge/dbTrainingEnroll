package com.db.bex.dbTrainingEnroll.controller;

import com.db.bex.dbTrainingEnroll.dto.DummyDto;
import com.db.bex.dbTrainingEnroll.service.DummyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class DummyController{

    private DummyService dummyService;

    public DummyController(DummyService dummyService) {
        this.dummyService = dummyService;
    }

    @GetMapping("/dummy/")
    public @ResponseBody
    List<DummyDto> search(Principal principal){
        return dummyService.search();
    }

    @PostMapping("/dummypost/")
    public String test(@RequestBody String data) {
        return data + "aaa";
    }
}