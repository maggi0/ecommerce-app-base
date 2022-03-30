package com.virtuslab.internship.integration;

import com.virtuslab.internship.basket.BasketInfo;
import com.virtuslab.internship.receipt.ReceiptService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class ReceiptControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void checkStatusAndReceiptInfo() throws Exception {
        //Given
        var request = MockMvcRequestBuilders.get("/receipt");
        var result = mvc.perform(request).andReturn();
        var status = result.getResponse().getStatus();
        var content = result.getResponse().getContentAsString();
        final var obj = new JSONObject(content);
        final var jEntries = obj.getJSONArray("entries");
        final var jDiscounts = obj.getJSONArray("discounts");

        //When
        var basketInfo = new BasketInfo();
        var receiptService = new ReceiptService(basketInfo);
        var receipt = receiptService.getReceipt();
        var entries = receipt.entries();
        var discounts = receipt.discounts();
        var totalPrice = receipt.totalPrice();

        //Then
        assertEquals(200, status);
        assertEquals(jEntries.length(), entries.size());
        assertEquals(jDiscounts.length(), discounts.size());
    }
}
