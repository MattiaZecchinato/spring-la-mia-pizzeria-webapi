package org.lessons.java.spring_la_mia_pizzeria_relazioni.controllers;

import org.lessons.java.spring_la_mia_pizzeria_relazioni.model.Discount;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/discount")
public class DiscountController {

    @Autowired
    private DiscountRepository discountRepository;

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("discount") Discount formDiscount, BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {

            return "discounts/createDiscount";
        }

        discountRepository.save(formDiscount);

        return "redirect:/pizzas/" + formDiscount.getPizza().getId();
    }

    @GetMapping("/update/{id}")
    public String edit(@PathVariable("id") int discountId, Model model) {

        model.addAttribute("discount", discountRepository.findById(discountId).get());

        return "discounts/updateDiscount";
    }

    @PostMapping("/update/{id}")
    public String update(@Valid @ModelAttribute("discount") Discount formDiscount, BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {

            return "discounts/updateDiscount";
        }

        discountRepository.save(formDiscount);

        return "redirect:/pizzas/" + formDiscount.getPizza().getId();
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int discountId) {

        discountRepository.deleteById(discountId);

        return "redirect:/pizzas";
    }
}