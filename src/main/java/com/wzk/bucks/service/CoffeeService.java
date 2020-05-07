package com.wzk.bucks.service;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

import com.wzk.bucks.model.Coffee;
import com.wzk.bucks.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CoffeeService {

    @Autowired
    private CoffeeRepository coffeeRepository;

    public Optional<Coffee> findOneCoffee(String name) {
        ExampleMatcher.GenericPropertyMatcher ignoreCase = exact().ignoreCase();
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("name", ignoreCase);

        Example<Coffee> example = Example.of(Coffee.builder().name(name).build(), matcher);
        Optional<Coffee> coffee = coffeeRepository.findOne(example);
        log.info("Coffee Found: {}", coffee);
        return coffee;
    }

    public List<Coffee> findAllCoffee() {
        return coffeeRepository.findAll();
    }
}
