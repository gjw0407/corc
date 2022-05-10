package com.web.shinhan.service.payment;

import com.web.shinhan.model.PaymentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PaymentService {
    Page<PaymentDto> findUserPayment(int userId, Pageable pageable);

    Page<PaymentDto> findAll(Pageable pageable);

    boolean confirmPayment(int paymentId);

    Page<PaymentDto> findStorePayment(int storeId, Pageable pageable);

    int findStoreTotal();

    List<PaymentDto> findAllByStatus();

    int calcTotalExpense();

    int notConfirmed();

    int expenseByMonth(int now, int year);

    int findTotal(int storeId);

    int findNotConfirmed(int storeId);

    int findUserPaymentCustom(int now, int year);

    Page<PaymentDto> findUserPaymentCustom(int userId, Pageable pageable, int startDate, int endDate);

    void pay(int userId, int storeId, int bill);

    int findLastPayment();

    PaymentDto findPayment(int paymentId);

    List<Integer[]> calcNotConfirmed();

    int countStorePayment(int storeId);

    int countPayment();

    int countUserPayment(int userId);
}
