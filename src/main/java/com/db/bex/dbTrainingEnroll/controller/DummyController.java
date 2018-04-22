package com.db.bex.dbTrainingEnroll.controller;

import com.db.bex.dbTrainingEnroll.dto.DummyDto;
import com.db.bex.dbTrainingEnroll.service.DummyService;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
public class DummyController{

    private DummyService dummyService;

    public DummyController(DummyService dummyService) {
        this.dummyService = dummyService;
    }

    @GetMapping("/dummy")
    public @ResponseBody DummyDto search(Principal principal){
        DummyDto dummyDto = new DummyDto();
        dummyDto.setId(new Long(1));
        dummyDto.setName("ION");
        return dummyDto;
    }

    @PostMapping("/dummypost")
    public DummyDto test(@RequestBody DummyDto dummyDto) {
        dummyDto.setName("Vasile");
        return dummyDto;
    }
}