package com.bahar.tacos.controller;

import com.bahar.tacos.model.Ingredient;
import com.bahar.tacos.model.Taco;
import com.bahar.tacos.model.TacoOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.bahar.tacos.model.Ingredient.Type;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {
    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    @ModelAttribute
    public void addInIngredientsToTheMode(Model model){
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "flour tortilla", Type.WRAP),
                new Ingredient("COTO", "corn tortilla", Type.WRAP),
                new Ingredient("GRBF", "ground beef", Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
        );

        Type[] types = Type.values();
        Arrays.stream(types).forEach(
                type -> model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type))
        );
    }

    @ModelAttribute("tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    @ModelAttribute("taco")
    public Taco taco() {
        return new Taco();
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream().filter(ingredient -> ingredient.getType().equals(type)).collect(Collectors.toList());
    }

//    The @ModelAttribute applied to the TacoOrder parameter indicates that it should use the TacoOrder object that was placed
//    into the model via the @ModelAttribute -annotated order() method
    @PostMapping
    public String processOrder(@Valid Taco taco, Errors errors,
                               @ModelAttribute TacoOrder tacoOrder) {
        if (errors.hasErrors())
            return "design";
        tacoOrder.addTaco(taco);
        log.info("processing Taco: {}", taco);

        return "redirect:/orders/current";
    }
}
