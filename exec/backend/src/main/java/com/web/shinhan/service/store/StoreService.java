package com.web.shinhan.service.store;

import com.web.shinhan.model.StoreDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreService {
    Page<StoreDto> findAllStore(Pageable pageable);

    Page<StoreDto> findAllUnassignedStore(Pageable pageable);

    StoreDto findStoreInfo(int storeId);

    boolean emailCheck(String email);

    void registStore(StoreDto storeDto);

    boolean modifyStoreInfo(String email, StoreDto newDto);

    int allowStoreApplication(int storeId);

    int denyStoreApplication(int storeId);

    boolean login(StoreDto storeDto);

    StoreDto findStoreByEmail(String email);

    boolean checkCrNum(String crNum);

    int countStore();
}
