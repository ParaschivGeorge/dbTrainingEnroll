package com.db.bex.interns.service;

import com.db.bex.interns.dao.DummyRepository;
import com.db.bex.interns.dto.DummyDTOTransformer;
import com.db.bex.interns.dto.DummyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DummyService {

    @Autowired
    private DummyRepository dummyRepository;

    @Autowired
    private DummyDTOTransformer dummyDTOTransformer;

    public List<DummyDto> search(){
        return dummyDTOTransformer.transform(dummyRepository.findAll());
    }
}
