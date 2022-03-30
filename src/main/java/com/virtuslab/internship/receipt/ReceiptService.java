package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.BasketInfo;
import org.springframework.stereotype.Service;

@Service
public class ReceiptService {

    private final BasketInfo basketInfo;

    public ReceiptService(BasketInfo basketInfo) {
        this.basketInfo = basketInfo;
    }

    public Receipt getReceipt() {
        var receiptGenerator = new ReceiptGenerator();

        return receiptGenerator.generate(basketInfo.getBasketInfo());
    }
}
