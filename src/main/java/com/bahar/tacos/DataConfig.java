package com.bahar.tacos;

import com.bahar.tacos.model.Ingredient;
import com.bahar.tacos.model.Taco;
import com.bahar.tacos.model.user.User;
import com.bahar.tacos.repository.IngredientRepository;
import com.bahar.tacos.repository.TacoRepository;
import com.bahar.tacos.repository.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

import static com.bahar.tacos.model.Ingredient.Type.*;

@Configuration
public class DataConfig {
    @Bean
    public CommandLineRunner dataLoader(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            IngredientRepository ingredientRepository,
            TacoRepository tacoRepository
    ) {
        return args -> {
            saveIngredientsAndTacos(ingredientRepository, tacoRepository);
            saveDefultUser(userRepository, passwordEncoder);
        };
    }

    private void saveDefultUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        User myUser = new User("bahar", passwordEncoder.encode("bahar"), "Bahar Saffarian",
                "Mofatteh", "Tehran", "Tehran", "000", "09342345127");
        userRepository.save(myUser);
    }

    private void saveIngredientsAndTacos(IngredientRepository ingredientRepository,
                                         TacoRepository tacoRepository) {
        Ingredient flourTortilla = new Ingredient(
                "FLTO", "Flour Tortilla", WRAP);
        Ingredient cornTortilla = new Ingredient(
                "COTO", "Corn Tortilla", WRAP);
        Ingredient groundBeef = new Ingredient(
                "GRBF", "Ground Beef", PROTEIN);
        Ingredient carnitas = new Ingredient(
                "CARN", "Carnitas", PROTEIN);
        Ingredient tomatoes = new Ingredient(
                "TMTO", "Diced Tomatoes", VEGGIES);
        Ingredient lettuce = new Ingredient(
                "LETC", "Lettuce", VEGGIES);
        Ingredient cheddar = new Ingredient(
                "CHED", "Cheddar", CHEESE);
        Ingredient jack = new Ingredient(
                "JACK", "Monterrey Jack", CHEESE);
        Ingredient salsa = new Ingredient(
                "SLSA", "Salsa", SAUCE);
        Ingredient sourCream = new Ingredient(
                "SRCR", "Sour Cream", SAUCE);
        ingredientRepository.save(flourTortilla);
        ingredientRepository.save(cornTortilla);
        ingredientRepository.save(groundBeef);
        ingredientRepository.save(carnitas);
        ingredientRepository.save(tomatoes);
        ingredientRepository.save(lettuce);
        ingredientRepository.save(cheddar);
        ingredientRepository.save(jack);
        ingredientRepository.save(salsa);
        ingredientRepository.save(sourCream);

        Taco taco1 = new Taco();
        taco1.setName("Carnivore");
        taco1.setIngredients(Arrays.asList(
                flourTortilla, groundBeef, carnitas,
                sourCream, salsa, cheddar));
        tacoRepository.save(taco1);
        Taco taco2 = new Taco();
        taco2.setName("Bovine Bounty");
        taco2.setIngredients(Arrays.asList(
                cornTortilla, groundBeef, cheddar,
                jack, sourCream));
        tacoRepository.save(taco2);
        Taco taco3 = new Taco();
        taco3.setName("Veg-Out");
        taco3.setIngredients(Arrays.asList(
                flourTortilla, cornTortilla, tomatoes,
                lettuce, salsa));
        tacoRepository.save(taco3);
    }
}
