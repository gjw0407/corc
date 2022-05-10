package com.web.shinhan.service.area;

import com.web.shinhan.model.GugunDto;
import com.web.shinhan.model.SidoDto;

import java.util.List;

public interface AreaService {
    List<SidoDto> findSidoAll();

    List<GugunDto> findGugun(String sidoCode);
}
