package com.bahar.tacos.repository;

import com.bahar.tacos.model.TacoOrder;
import com.bahar.tacos.model.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


import java.util.List;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
    List<TacoOrder> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}
