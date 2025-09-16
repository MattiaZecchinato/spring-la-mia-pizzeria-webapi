package org.lessons.java.spring_la_mia_pizzeria_relazioni.controllers;

import java.util.List;

import org.lessons.java.spring_la_mia_pizzeria_relazioni.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pizzas")
public class PizzaRestController {

    @Autowired
    private PizzaRepository pizzaRepository;

    // index get
    @GetMapping
    public List<Pizza> index() {

        return pizzaRepository.findAll();
    }

    // show get(id)
    @GetMapping("/{id}")
    public Pizza show(@PathVariable("id") int pizzaId) {

        return pizzaRepository.findById(pizzaId).get();
    }

    // create post
    @PostMapping("/create")
    public Pizza create(@RequestBody Pizza pizza) {

        return pizzaRepository.save(pizza);
    }

    // edit put(id)
    @PutMapping("/edit/{id}")
    public Pizza update(@RequestBody Pizza pizza, @PathVariable("id") int pizzaId) {

        pizza.setId(pizzaId);

        return pizzaRepository.save(pizza);
    }

    // delete delete(id)
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") int pizzaId) {

        pizzaRepository.deleteById(pizzaId);
    }

}
