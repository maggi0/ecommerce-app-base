package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.discount.FifteenPercentDiscount;
import com.virtuslab.internship.discount.TenPercentDiscount;
import com.virtuslab.internship.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ReceiptGenerator {

    public Receipt generate(Basket basket) {
        FifteenPercentDiscount fifteenPercentDiscount = new FifteenPercentDiscount();
        TenPercentDiscount tenPercentDiscount = new TenPercentDiscount();
        List<ReceiptEntry> receiptEntries = new ArrayList<>();

        Map<Product, Long> products = basket.getProducts().stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        for (var entry : products.entrySet()) {
            ReceiptEntry receiptEntry = new ReceiptEntry(entry.getKey(), entry.getValue().intValue());
            receiptEntries.add(receiptEntry);
        }

        var receipt = new Receipt((receiptEntries));
        receipt = fifteenPercentDiscount.apply(receipt);
        receipt = tenPercentDiscount.apply(receipt);

        return receipt;
    }
}
