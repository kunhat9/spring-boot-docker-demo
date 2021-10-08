package com.demo.model;

import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Table(name = "tblProduct")
public class Product {
    @Id
    //you can also sequence
    @SequenceGenerator(
            name="product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1 // increment by 1

    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator ="product_sequence"
    )
    private  Long id;
    @Column(nullable = false,unique = true,length = 255)
    private  String productName;
    private  int year;
    private double price;
    private String url;

    //calculated filed


    public Product() {
    }


    public Product(Long id, String productName, int year, double price, String url) {
        this.id = id;
        this.productName = productName;
        this.year = year;
        this.price = price;
        this.url = url;
    }

    public Product(String productName, int year, double price, String url) {
        this.productName = productName;
        this.year = year;
        this.price = price;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public int getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }

    public String getUrl() {
        return url;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return year == product.year
                && Double.compare(product.price, price) == 0
                && Objects.equals(id, product.id)
                && Objects.equals(productName, product.productName)
                && Objects.equals(url, product.url);
    }
}
