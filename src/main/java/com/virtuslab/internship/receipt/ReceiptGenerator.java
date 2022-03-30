package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.discount.FifteenPercentDiscount;
import com.virtuslab.internship.discount.TenPercentDiscount;
import com.virtuslab.internship.product.Product;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ReceiptGenerator {

    public Receipt generate(Basket basket) {
        var fifteenPercentDiscount = new FifteenPercentDiscount();
        var tenPercentDiscount = new TenPercentDiscount();

        Map<Product, Long> products = basket.getProducts().stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        var receiptEntries = products.entrySet().stream()
                .map(entry -> new ReceiptEntry(entry.getKey(), entry.getValue().intValue()))
                .toList();

        var receipt = new Receipt((receiptEntries));
        receipt = fifteenPercentDiscount.apply(receipt);
        receipt = tenPercentDiscount.apply(receipt);

        return receipt;
    }
}
