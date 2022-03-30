package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BothDiscountsTest {

    @Test
    void shouldApplyBothDiscountsWhenPriceDoesntDropBelow50AfterFirstDiscount() {
        // Given
        var productDb = new ProductDb();
        var cereals = productDb.getProduct("Cereals");
        var bread = productDb.getProduct("Bread");
        var steak = productDb.getProduct("Steak");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(cereals, 1));
        receiptEntries.add(new ReceiptEntry(bread, 2));
        receiptEntries.add(new ReceiptEntry(steak, 2));

        var receipt = new Receipt(receiptEntries);
        var firstDiscount = new FifteenPercentDiscount();
        var secondDiscount = new TenPercentDiscount();

        var steakPrice = steak.price().multiply(BigDecimal.valueOf(2));
        var breadPrice = bread.price().multiply(BigDecimal.valueOf(2));
        var initialPrice = cereals.price().add(steakPrice).add(breadPrice);
        var firstDiscountPrice = initialPrice.multiply(BigDecimal.valueOf(0.85));
        var expectedTotalPrice = firstDiscountPrice.multiply(BigDecimal.valueOf(0.9));
        // When
        var receiptAfterDiscounts = secondDiscount.apply(firstDiscount.apply(receipt));

        // Then
        assertTrue(initialPrice.intValue() >= 50);
        assertTrue(firstDiscountPrice.intValue() >= 50);
        assertEquals(expectedTotalPrice, receiptAfterDiscounts.totalPrice());
        assertEquals(2, receiptAfterDiscounts.discounts().size());
    }

    @Test
    void shouldApplyOnlyOneDiscountIfPriceDropsBelow50AfterFirstDiscount() {
        // Given
        var productDb = new ProductDb();
        var cereals = productDb.getProduct("Cereals");
        var bread = productDb.getProduct("Bread");
        var pork = productDb.getProduct("Pork");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(cereals, 1));
        receiptEntries.add(new ReceiptEntry(bread, 2));
        receiptEntries.add(new ReceiptEntry(pork, 2));

        var receipt = new Receipt(receiptEntries);
        var firstDiscount = new FifteenPercentDiscount();
        var secondDiscount = new TenPercentDiscount();

        var porkPrice = pork.price().multiply(BigDecimal.valueOf(2));
        var breadPrice = bread.price().multiply(BigDecimal.valueOf(2));
        var initialPrice =  cereals.price().add(porkPrice).add(breadPrice);
        var expectedTotalPrice = initialPrice.multiply(BigDecimal.valueOf(0.85));

        // When
        var receiptAfterDiscounts = secondDiscount.apply(firstDiscount.apply(receipt));

        // Then
        assertTrue(initialPrice.intValue() >= 50);
        assertTrue(expectedTotalPrice.intValue() <= 50);
        assertEquals(expectedTotalPrice, receiptAfterDiscounts.totalPrice());
        assertEquals(1, receiptAfterDiscounts.discounts().size());
    }
}
