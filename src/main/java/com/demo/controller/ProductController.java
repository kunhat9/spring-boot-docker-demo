package com.demo.controller;

import com.demo.model.Product;
import com.demo.model.ResponeObject;
import com.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//Now connect with my sql using jpa
@RestController
@RequestMapping(path = "/api/v1/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @GetMapping("")
    public ResponseEntity<ResponeObject> getAllProduct(){
        try{
            List<Product> product= productRepository.findAll();
            return product.size() >0 ? ResponseEntity.status(HttpStatus.OK).body(
                    new ResponeObject("ok","Query success!", product)
            ): ResponseEntity.status(HttpStatus.FOUND).body(
                    new ResponeObject("err","Can not find product with ", "")
            );
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                    new ResponeObject("err"," Loi !", "")
            );
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponeObject> findById(@PathVariable Long id){
        try{
            Optional<Product> product = productRepository.findById(id);
            return product.isPresent()?  ResponseEntity.status(HttpStatus.OK).body(
                    new ResponeObject("ok","Query success !", product)
            ): ResponseEntity.status(HttpStatus.FOUND).body(
                    new ResponeObject("err","Can not find product with id = "+id, "")
            );
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                    new ResponeObject("ok"," Loi !", "")
            );
        }

    }
    @PostMapping("/insert")
    public  ResponseEntity<ResponeObject> create(@RequestBody Product product){
        List<Product> foundProduct = productRepository.findByProductName(product.getProductName().trim());
        //check the same product name
        if (foundProduct.size() >0)
            return  ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponeObject("err","Product is the same name","")
            );
        return  ResponseEntity.status(HttpStatus.OK).body(
                new ResponeObject("ok","insert success",productRepository.save(product))
        );
    }
    // update, insert = update if found, otherwise insert
    @PutMapping("/{id}")
    public ResponseEntity<ResponeObject> updateProduct(@RequestBody Product newProduct, @PathVariable Long id){
        try{
            Product updateProduct =  productRepository.findById(id)
                    .map(product -> {
                        product.setProductName(newProduct.getProductName());
                        product.setPrice(newProduct.getPrice());
                        product.setYear(newProduct.getYear());
                        product.setUrl(newProduct.getUrl());
                        return  productRepository.save(product);
                    }).orElseGet(() -> {
                        newProduct.setId(id);
                        return  productRepository.save(newProduct);
                    });
            return  ResponseEntity.status(HttpStatus.OK).body(
                    new ResponeObject("ok","updated success",updateProduct)
            );
        }catch (Exception exception){
            int code = HttpStatus.EXPECTATION_FAILED.value();
            return  ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                    new ResponeObject("err",exception.toString(),"")
            );
        }
    }
    @DeleteMapping("/{id}")
    public  ResponseEntity<ResponeObject> deleteProduct(@PathVariable Long id){
        boolean exitsts = productRepository.existsById(id);
        if(exitsts){
            productRepository.deleteById(id);
            return  ResponseEntity.status(HttpStatus.OK).body(
                    new ResponeObject("ok","delete success !","")
            );
        }
        else
            return  ResponseEntity.status(HttpStatus.OK).body(
                    new ResponeObject("ok","Not already product","")
            );
    }
}
