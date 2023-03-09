package com.example.expenseTracker.expenseTracker.controller;

import com.example.expenseTracker.expenseTracker.model.Category;
import com.example.expenseTracker.expenseTracker.model.Expense;
import com.example.expenseTracker.expenseTracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;

    public ExpenseController(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @GetMapping("/expenses")
    List<Expense> getAllExpenses(){
        return expenseRepository.findAll();
    }

    @GetMapping("/expenses/{id}")
    ResponseEntity<?> getExpense(@PathVariable Long id){
        Optional<Expense> expense = expenseRepository.findById(id);
        return expense.map(response -> ResponseEntity.ok().body(response)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/expenses")
    ResponseEntity<Expense> createExpense(@RequestBody Expense expense) throws URISyntaxException {
        Expense result=expenseRepository.save(expense);
        return ResponseEntity.created(new URI("/api/expenses" + result.getId())).body(result);
    }

    @PutMapping("/expenses/{id}")
    ResponseEntity<Expense> updateExpense(@RequestBody Expense expense){
        Expense result = expenseRepository.save(expense);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/expenses/{id}")
    ResponseEntity<?> deleteExpense(@PathVariable Long id){
        expenseRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
