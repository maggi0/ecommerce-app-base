package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.BasketInfo;
import org.springframework.stereotype.Service;

@Service
public class ReceiptService {

    private final BasketInfo basketInfo;
    private final ReceiptGenerator receiptGenerator;

    public ReceiptService(BasketInfo basketInfo, ReceiptGenerator receiptGenerator) {
        this.basketInfo = basketInfo;
        this.receiptGenerator = receiptGenerator;
    }

    public Receipt getReceipt() {
        return receiptGenerator.generate(basketInfo.getBasketInfo());
    }
}
