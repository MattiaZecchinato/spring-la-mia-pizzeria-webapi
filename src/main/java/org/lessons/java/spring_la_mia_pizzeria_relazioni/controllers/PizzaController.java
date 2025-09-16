package org.lessons.java.spring_la_mia_pizzeria_relazioni.controllers;

import java.util.List;
import java.util.ArrayList;

import org.lessons.java.spring_la_mia_pizzeria_relazioni.model.Discount;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.repository.DiscountRepository;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.repository.IngredientRepository;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @GetMapping
    public String index(@RequestParam(required = false) String pizzaName, Model model) {

        List<Pizza> pizzasList = new ArrayList<>();

        if (pizzaName == null || pizzaName.isEmpty()) {
            pizzasList = pizzaRepository.findAll();
        } else {
            pizzasList = pizzaRepository.findByNameStartingWith(pizzaName);
        }

        model.addAttribute("pizzasList", pizzasList);

        return "pizzas/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int pizzaId, Model model) {

        List<Pizza> pizzasList = pizzaRepository.findAll();

        for (Pizza currentPizza : pizzasList) {
            if (pizzaId == currentPizza.getId()) {
                model.addAttribute("pizza", currentPizza);
            }
        }

        return "pizzas/pizzaDetails";
    }

    @GetMapping("/create")
    public String create(Model model) {

        model.addAttribute("pizza", new Pizza());
        model.addAttribute("ingredientList", ingredientRepository.findAll());

        return "pizzas/createPizza";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredientList", ingredientRepository.findAll());
            return "pizzas/createPizza";
        }

        pizzaRepository.save(formPizza);

        return "redirect:/pizzas";
    }

    @GetMapping("/update/{id}")
    public String edit(@PathVariable("id") int pizzaId, Model model) {

        model.addAttribute("pizza", pizzaRepository.findById(pizzaId).get());
        model.addAttribute("ingredientList", ingredientRepository.findAll());

        return "pizzas/updatePizza";
    }

    @PostMapping("/update/{id}")
    public String update(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredientList", ingredientRepository.findAll());
            return "pizzas/updatePizza";
        }

        pizzaRepository.save(formPizza);

        return "redirect:/pizzas";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int pizzaId) {

        Pizza currentPizza = pizzaRepository.findById(pizzaId).get();

        for (Discount discount : currentPizza.getDiscounts()) {
            discountRepository.deleteById(discount.getId());
        }

        pizzaRepository.deleteById(pizzaId);

        return "redirect:/pizzas";
    }

    @GetMapping("/{id}/discount")
    public String discount(@PathVariable("id") int pizzaId, Model model) {
        Discount discount = new Discount();

        discount.setPizza(pizzaRepository.findById(pizzaId).get());

        model.addAttribute("discount", discount);

        return "discounts/createDiscount";
    }
}
