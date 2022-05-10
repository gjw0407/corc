package com.web.shinhan.service.user;

import com.web.shinhan.model.AdminDto;
import com.web.shinhan.model.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserDto> findAllUser(Pageable pageable);

    UserDto findUserInfo(int userId);

    UserDto findUserByEmail(String email);

    void registUser(UserDto userDto);

    boolean emailCheck(String email);

    boolean employeeNumCheck(int employee_num);

    boolean modifyUserInfo(String email, UserDto newDto);

    boolean modifyCardLimit(int userId, int limit);

    int banUser(int userId);

    int deleteUser(int userId);

    int activateUser(int userId);

    boolean login(UserDto user);

    boolean loginAdmin(AdminDto admin);

    int assignedTotal();

    Boolean pay(int userId, int bill);

    int countUser();
}
