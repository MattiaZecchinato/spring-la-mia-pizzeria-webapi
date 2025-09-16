package org.lessons.java.spring_la_mia_pizzeria_relazioni.controllers;

import java.util.List;
import java.util.Optional;

import org.lessons.java.spring_la_mia_pizzeria_relazioni.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Pizza>> index() {

        List<Pizza> pizzasList = pizzaRepository.findAll();

        if (pizzasList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(pizzasList, HttpStatus.OK);

    }

    // show get(id)
    @GetMapping("/{id}")
    public ResponseEntity<Pizza> show(@PathVariable("id") int pizzaId) {

        Optional<Pizza> pizza = pizzaRepository.findById(pizzaId);

        if (pizza.isEmpty()) {
            return new ResponseEntity<Pizza>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Pizza>(pizza.get(), HttpStatus.OK);
    }

    // create post
    @PostMapping("/create")
    public ResponseEntity<Pizza> create(@RequestBody Pizza pizza) {

        return new ResponseEntity<>(pizzaRepository.save(pizza), HttpStatus.OK);
    }

    // edit put(id)
    @PutMapping("/edit/{id}")
    public ResponseEntity<Pizza> update(@RequestBody Pizza pizza, @PathVariable("id") int pizzaId) {

        Optional<Pizza> pizzaToFind = pizzaRepository.findById(pizzaId);

        if (pizzaToFind.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        pizza.setId(pizzaId);
        pizzaRepository.save(pizza);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // delete delete(id)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Pizza> delete(@PathVariable("id") int pizzaId) {

        Optional<Pizza> pizzaToFind = pizzaRepository.findById(pizzaId);

        if (pizzaToFind.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        pizzaRepository.deleteById(pizzaId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
