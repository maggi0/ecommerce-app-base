package com.virtuslab.internship.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtuslab.internship.basket.BasketInfo;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptGenerator;
import com.virtuslab.internship.receipt.ReceiptService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class ReceiptControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void checkStatusAndReceiptInfo() throws Exception {
        //Given
        var request = MockMvcRequestBuilders.get("/receipt");
        var result = mvc.perform(request).andReturn();
        var status = result.getResponse().getStatus();
        var content = result.getResponse().getContentAsString();
        var receiptResponse = objectMapper.readValue(content, Receipt.class);
        var entriesResponse = receiptResponse.entries();
        var discountsResponse = receiptResponse.discounts();
        var totalPriceResponse = receiptResponse.totalPrice();

        //When
        var basketInfo = new BasketInfo();
        var receiptGenerator = new ReceiptGenerator();
        var receiptService = new ReceiptService(basketInfo, receiptGenerator);
        var receipt = receiptService.getReceipt();
        var entries = receipt.entries();
        var discounts = receipt.discounts();
        var totalPrice = receipt.totalPrice();

        //Then
        assertEquals(200, status);
        assertEquals(entriesResponse, entries);
        assertEquals(discountsResponse, discounts);
        assertEquals(totalPriceResponse, totalPrice);
    }
}
