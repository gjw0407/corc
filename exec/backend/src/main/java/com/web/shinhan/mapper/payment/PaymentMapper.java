package com.web.shinhan.mapper.payment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.web.shinhan.entity.Payment;
import com.web.shinhan.model.PaymentDto;

@Mapper
public interface PaymentMapper {
	PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class); // 2

	@Mapping(target = "")
	PaymentDto paymentToDto(Payment payment);
}
