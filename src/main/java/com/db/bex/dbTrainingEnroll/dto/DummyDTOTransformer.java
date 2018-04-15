package com.db.bex.dbTrainingEnroll.dto;

import com.db.bex.dbTrainingEnroll.entity.Dummy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DummyDTOTransformer {

    public List<DummyDto> transform( Iterable<Dummy> it){
        return ((List<Dummy>)it).stream().map(this::transform).collect(Collectors.toList());
    }

    public DummyDto transform(Dummy dummy){
        DummyDto dto = new DummyDto();
        dto.setId(dummy.getId());
        dto.setName(dummy.getName());
        return dto;
    }
}
