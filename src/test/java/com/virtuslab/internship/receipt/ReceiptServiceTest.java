package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.BasketInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReceiptServiceTest {

    @Test
    void shouldContainSameAmountOfProductsAsBasket(){
        //Given
        var basketInfo = new BasketInfo();
        var products = basketInfo.getBasketInfo().getProducts();

        var receiptService = new ReceiptService(basketInfo);

        //When
        var receipt = receiptService.getReceipt();
        var entries = receipt.entries();
        var receiptQuantity = 0;
        for(var entry : entries) {
            receiptQuantity += entry.quantity();
        }

        //Then
        assertEquals(products.size(), receiptQuantity);
    }

}
