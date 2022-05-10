package com.web.shinhan.service.paymentItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.web.shinhan.mapper.paymentItem.PaymentitemMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.shinhan.entity.Paymentitem;
import com.web.shinhan.model.PaymentitemDto;
import com.web.shinhan.repository.PaymentitemRepository;

@Service
public class PaymentitemServiceImpl implements PaymentitemService {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private PaymentitemRepository paymentitemRepository;

	private final PaymentitemMapper mapper = Mappers.getMapper(PaymentitemMapper.class);

	@Override
	@Transactional
	public Map<String, Object> findItems(int storeId, int paymentId) {
		Map<String, Object> resultMap = new HashMap<>();
		List<Paymentitem> paymentItem = paymentitemRepository.findByPaymentId(paymentId);
		resultMap.put("paymentItem", paymentItem);
		return resultMap;
	}

	@Override
	@Transactional
	public void registPaymentitem(String productName, int price, int amount, int paymentId) {
		PaymentitemDto paymentitem = new PaymentitemDto();
		paymentitem.setPaymentId(paymentId);
		paymentitem.setProductName(productName);
		paymentitem.setPrice(price);
		paymentitem.setAmount(amount);
		paymentitemRepository.save(paymentitem.toEntity());
//		return 
//		int paymentitemId = paymentitemRepository.findPaymentitemIdBy
	}

}