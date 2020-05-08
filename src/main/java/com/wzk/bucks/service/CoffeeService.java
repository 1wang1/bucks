package com.wzk.bucks.service;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

import com.wzk.bucks.model.Coffee;
import com.wzk.bucks.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@CacheConfig(cacheNames = "coffee") // 缓存名字为coffee
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

    // 方法执行后会把值放到缓存中
    @Cacheable
    public List<Coffee> findAllCoffee() {
        return coffeeRepository.findAll();
    }

    // 缓存清理
    @CacheEvict
    public void reloadCoffee() {

    }
}
