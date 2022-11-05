package com.bahar.tacos.controller;

import com.bahar.tacos.OrderProps;
import com.bahar.tacos.model.TacoOrder;
import com.bahar.tacos.model.user.User;
import com.bahar.tacos.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.awt.print.Pageable;

@Controller
@Slf4j
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {
    private final OrderRepository orderRepository;
    private final OrderProps orderProps;

    public OrderController(OrderRepository orderRepository, OrderProps orderProps) {
        this.orderRepository = orderRepository;
        this.orderProps = orderProps;
    }

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder tacoOrder, Errors errors
            , SessionStatus sessionStatus) {
        if (errors.hasErrors())
            return "orderForm";

        log.info("Order Submitted: {}", tacoOrder);
        sessionStatus.setComplete();

        return "redirect:/";

    }

    @GetMapping
    public String orderForUser(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        model.addAttribute("orders",
            orderRepository.findByUserOrderByPlacedAtDesc(user, PageRequest.of(0,orderProps.getPageSize()))
        );

        return "orderList";
    }
}
