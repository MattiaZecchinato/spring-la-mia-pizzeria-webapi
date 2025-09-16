package org.lessons.java.spring_la_mia_pizzeria_relazioni.repository;

import java.util.List;

import org.lessons.java.spring_la_mia_pizzeria_relazioni.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
    List<Ingredient> findByNameStartingWith(String name);
}
