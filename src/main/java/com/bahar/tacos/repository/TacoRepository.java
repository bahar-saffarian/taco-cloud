package com.bahar.tacos.repository;

import com.bahar.tacos.model.Taco;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends CrudRepository<Taco, Long> {
}
