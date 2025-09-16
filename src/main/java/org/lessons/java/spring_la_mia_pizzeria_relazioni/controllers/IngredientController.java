package org.lessons.java.spring_la_mia_pizzeria_relazioni.controllers;

import java.util.ArrayList;
import java.util.List;

import org.lessons.java.spring_la_mia_pizzeria_relazioni.model.Ingredient;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {

    @Autowired
    private IngredientRepository ingredientRepository;

    @GetMapping
    public String index(@RequestParam(required = false) String ingredientName, Model model) {

        List<Ingredient> ingredientsList = new ArrayList<>();

        if (ingredientName == null || ingredientName.isEmpty()) {
            ingredientsList = ingredientRepository.findAll();
        } else {
            ingredientsList = ingredientRepository.findByNameStartingWith(ingredientName);
        }

        model.addAttribute("ingredientsList", ingredientsList);

        return "ingredients/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int ingredientId, Model model) {

        List<Ingredient> ingredientList = ingredientRepository.findAll();

        for (Ingredient currentIngredient : ingredientList) {
            if (ingredientId == currentIngredient.getId()) {
                model.addAttribute("ingredient", currentIngredient);
            }
        }

        return "ingredients/ingredientDetails";
    }

    @GetMapping("/create")
    public String create(Model model) {

        model.addAttribute("ingredient", new Ingredient());

        return "ingredients/createIngredient";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("ingredient") Ingredient formIngredient, BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "ingredients/createIngredient";
        }

        ingredientRepository.save(formIngredient);

        return "redirect:/ingredients";
    }

    @GetMapping("/update/{id}")
    public String edit(@PathVariable("id") int ingredientId, Model model) {

        model.addAttribute("ingredient", ingredientRepository.findById(ingredientId).get());

        return "ingredients/updateIngredient";
    }

    @PostMapping("/update/{id}")
    public String update(@Valid @ModelAttribute("ingredient") Ingredient formIngredient, BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "ingredients/updateIngredient";
        }

        ingredientRepository.save(formIngredient);

        return "redirect:/ingredients";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int ingredientId) {

        Ingredient ingredientToDelete = ingredientRepository.findById(ingredientId).get();

        for (Pizza currentPizza : ingredientToDelete.getPizzas()) {
            currentPizza.getIngredients().remove(ingredientToDelete);
        }

        ingredientRepository.deleteById(ingredientId);

        return "redirect:/ingredients";
    }
}
