package com.demo.database;

import com.demo.model.Product;
import com.demo.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;


/*
Now connect with mysql using jpa
docker run -d --rm --name mysql-spring-boot-tutorial \
-e MYSQL_ROOT_PASSWORD=123456\
-e MYSQL_USER=sa \
-e MYSQL_PASSWORD=123456\
-p 3309:3306 \
--volume mysql-spring-boot-tutorial-volume:/var/lib/mysql \
mysql:5.7.35

mysql -h localhost -P 3309 --protocol=tcp -u sa -p 123456
 */


@Configuration
public class Database {
    private static final Logger logger=Logger.getLogger(String.valueOf(Database.class));
    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository){
        return  new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
//                Product p1 = new Product("Iphone 7",2019,699,"");
//                Product p2 = new Product("Iphone 8",2022,799,"");
//                logger.info("insert data : "+ productRepository.save(p1));
//                logger.info("insert data : "+ productRepository.save(p2));
            }
        };
    }
}
