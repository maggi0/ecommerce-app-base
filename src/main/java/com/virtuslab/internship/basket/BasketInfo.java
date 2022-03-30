package com.virtuslab.internship.basket;

import com.virtuslab.internship.product.ProductDb;
import org.springframework.stereotype.Component;

@Component
public class BasketInfo {
    private final Basket basket;

    public BasketInfo() {
        basket = new Basket();
        var productDb = new ProductDb();

        basket.addProduct(productDb.getProduct("Apple"));
        basket.addProduct(productDb.getProduct("Orange"));
        basket.addProduct(productDb.getProduct("Apple"));
        basket.addProduct(productDb.getProduct("Bread"));
        basket.addProduct(productDb.getProduct("Bread"));
        basket.addProduct(productDb.getProduct("Bread"));
    }

    public Basket getBasketInfo() {
        return basket;
    }
}
