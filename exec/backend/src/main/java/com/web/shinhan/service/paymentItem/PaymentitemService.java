package com.web.shinhan.service.paymentItem;

import java.util.Map;

public interface PaymentitemService {
    Map<String, Object> findItems(int storeId, int paymentId);

    void registPaymentitem(String productName, int price, int amount, int paymentId);
}
