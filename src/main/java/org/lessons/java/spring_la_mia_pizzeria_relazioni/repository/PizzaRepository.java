package org.lessons.java.spring_la_mia_pizzeria_relazioni.repository;

import java.util.List;

import org.lessons.java.spring_la_mia_pizzeria_relazioni.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {

    List<Pizza> findByNameStartingWith(String name);
}
