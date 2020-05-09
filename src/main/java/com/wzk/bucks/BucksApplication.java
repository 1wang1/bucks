package com.wzk.bucks;

import com.wzk.bucks.model.Coffee;
import com.wzk.bucks.model.CoffeeOrder;
import com.wzk.bucks.model.OrderState;
import com.wzk.bucks.repository.CoffeeRepository;
import com.wzk.bucks.service.CoffeeOrderService;
import com.wzk.bucks.service.CoffeeService;
import io.lettuce.core.ReadFrom;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@EnableTransactionManagement
@EnableJpaRepositories
@SpringBootApplication
//@EnableCaching(proxyTargetClass = true) // 拦截类的执行
public class BucksApplication implements ApplicationRunner {

    @Autowired
    private CoffeeRepository coffeeRepository;
    @Autowired
    private CoffeeService coffeeService;
    @Autowired
    private CoffeeOrderService orderService;
/*    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private JedisPoolConfig jedisPoolConfig;*/

    public static void main(String[] args) {
        SpringApplication.run(BucksApplication.class, args);
    }

    @Bean
    public RedisTemplate<String,Coffee> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Coffee> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    public LettuceClientConfigurationBuilderCustomizer customizer(){
        return builder->builder.readFrom(ReadFrom.MASTER_PREFERRED);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Coffee> c = coffeeService.findOneCoffee("mocha");
        log.info("Coffee:{}",c);

        for (int i = 0; i < 5; i++) {
            c = coffeeService.findOneCoffee("mocha");
        }

        log.info("value from Redis:{}",c);


        /* log.info("count:{}",coffeeService.findAllCoffee().size());
        for (int i = 0; i < 10; i++) {
            log.info("Reading from cache.");
            coffeeService.findAllCoffee();
        }
//        coffeeService.reloadCoffee();
        // 设置了缓存的失效时间为5秒
        Thread.sleep(5000);
        log.info("Reading after refresh.");
        coffeeService.findAllCoffee().forEach(c->log.info("coffee:{}",c));*/

/*        log.info(jedisPoolConfig.toString());
        try (Jedis jedis = jedisPool.getResource()) {
            List<Coffee> allCoffee = coffeeService.findAllCoffee();
            log.info("all coffee:{}",allCoffee.size());
            log.info("all coffee:{}",allCoffee);
            allCoffee.forEach(c -> {
                jedis.hset("springbucks-menu", c.getName(),
                           Long.toString(c.getPrice().getAmountMinorLong()));
            });
            Map<String, String> menu = jedis.hgetAll("springbucks-menu");
            log.info("Menu;{}", menu);

            String price = jedis.hget("springbucks-menu", "espresso");
            log.info("espresson-{}", Money.ofMinor(CurrencyUnit.of("CNY"), Long.parseLong(price)));
        }*/


//        log.info("All Coffee: {}", coffeeRepository.findAll());
//        Optional<Coffee> latte = coffeeService.findOneCoffee("Latte");
//        if (latte.isPresent()) {
//            CoffeeOrder order = orderService.createOrder("Li Lei", latte.get());
//            log.info("Update INIT to PAID: {}", orderService.updateState(order, OrderState.PAID));
//            log.info("Update PAID to INIT: {}", orderService.updateState(order, OrderState.INIT));
//        }
    }

    /*@Bean
    @ConfigurationProperties("redis")
    public JedisPoolConfig jedisPoolConfig() {
        return new JedisPoolConfig();
    }

    @Bean(destroyMethod = "close")
    public JedisPool jedisPool(@Value("${redis.host}") String host) {
        return new JedisPool(jedisPoolConfig(), host);
    }*/
}
