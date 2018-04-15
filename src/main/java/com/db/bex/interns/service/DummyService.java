package com.db.bex.interns.service;

import com.db.bex.interns.dto.DummyDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DummyService {

    public List<DummyDto> search(){
        return new ArrayList<DummyDto>();
    }
}
