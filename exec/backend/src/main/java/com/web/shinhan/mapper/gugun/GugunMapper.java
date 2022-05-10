package com.web.shinhan.mapper.gugun;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.web.shinhan.entity.Gugun;
import com.web.shinhan.model.GugunDto;

@Mapper
public interface GugunMapper {
	GugunMapper INSTANCE = Mappers.getMapper(GugunMapper.class); // 2

	@Mapping(target = "")
	GugunDto guguncodeToDto(Gugun guguncode);
}
