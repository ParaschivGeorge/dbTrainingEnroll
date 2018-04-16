package com.db.bex.dbTrainingEnroll.dto;

import com.db.bex.dbTrainingEnroll.entity.Dummy1;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DummyDTOTransformer {

    public List<DummyDto> transform( Iterable<Dummy1> it){
        return ((List<Dummy1>)it).stream().map(this::transform).collect(Collectors.toList());
    }

    public DummyDto transform(Dummy1 dummy){
        DummyDto dto = new DummyDto();
        dto.setId(dummy.getId());
        dto.setName(dummy.getName());
        return dto;
    }
}
