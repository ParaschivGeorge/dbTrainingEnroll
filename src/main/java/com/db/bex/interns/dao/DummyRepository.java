package com.db.bex.interns.dao;

import com.db.bex.interns.entity.Dummy;
import org.springframework.data.repository.CrudRepository;

public interface DummyRepository extends CrudRepository<Dummy, Long> {
}
