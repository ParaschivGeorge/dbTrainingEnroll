package com.db.bex.dbTrainingEnroll.service;

import com.db.bex.dbTrainingEnroll.dao.DummyRepository;
import com.db.bex.dbTrainingEnroll.dto.DummyDto;
import com.db.bex.dbTrainingEnroll.dto.DummyDtoTransformer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DummyService {

    private DummyRepository dummyRepository;
    private DummyDtoTransformer dummyDtoTransformer;

    public DummyService(DummyRepository dummyRepository, DummyDtoTransformer dummyDtoTransformer) {
        this.dummyRepository = dummyRepository;
        this.dummyDtoTransformer = dummyDtoTransformer;
    }

    public List<DummyDto> search(){
        return dummyDtoTransformer.transform(dummyRepository.findAll());
    }
}
