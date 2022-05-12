package com.web.shinhan.service.user;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.shinhan.entity.Admin;
import com.web.shinhan.entity.User;
import com.web.shinhan.model.AdminDto;
import com.web.shinhan.model.UserDto;
import com.web.shinhan.mapper.user.UserMapper;
import com.web.shinhan.repository.AdminRepository;
import com.web.shinhan.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	private final PasswordEncoder passwordEncoder;

	private final UserRepository userRepository;

	private final AdminRepository adminRepository;

	private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

	@Override
    @Transactional
	public Page<UserDto> findAllUser(Pageable pageable) {
		Page<User> users = userRepository.findAll(pageable);
		return users.map(UserDto::of);
	}

	@Override
    public UserDto findUserInfo(int userId) {
		User userInfo = userRepository.findByUserId(userId);
		UserDto userDto = mapper.INSTANCE.userToDto(userInfo);
		return userDto;
	}

	@Override
    public UserDto findUserByEmail(String email) {
		User userInfo = userRepository.findByEmail(email);
		UserDto userDto = mapper.INSTANCE.userToDto(userInfo);
		return userDto;
	}

	@Override
    @Transactional
	public void registUser(UserDto userDto) {
		String encodePassword = passwordEncoder.encode(userDto.getPassword());
		userDto.setPassword(encodePassword);
		userDto.setBalance(userDto.getCardLimit());
		userDto.setActive(1);
		userRepository.save(userDto.toEntity());
	}

	@Override
    public boolean emailCheck(String email) {
		boolean result = userRepository.existsByEmail(email);
		return result;
	}

	@Override
    public boolean employeeNumCheck(int employee_num) {
		boolean result = userRepository.existsByEmployeeNum(employee_num);
		return result;
	}

	@Override
    public boolean modifyUserInfo(String email, UserDto newDto) {
		User userInfo = userRepository.findByEmail(email);
		UserDto userDto = mapper.INSTANCE.userToDto(userInfo);

		String encodePassword = passwordEncoder.encode(newDto.getPassword());
		userDto.setEmployeeNum(newDto.getEmployeeNum());
		userDto.setPassword(encodePassword);
		userDto.setUserName(newDto.getUserName());
		userDto.setDepartment(newDto.getDepartment());
		userDto.setPosition(newDto.getPosition());
		userDto.setContact(newDto.getContact());
		userDto.setDays(newDto.getDays());
		userDto.setSidoCode(newDto.getSidoCode());
		userDto.setGugunCode(newDto.getGugunCode());
		userDto.setBalance(newDto.getBalance());
		userDto.setCardLimit(newDto.getCardLimit());
		userDto.setAccessTime(newDto.getAccessTime());
		userDto.setActive(newDto.getActive());
		userRepository.save(userDto.toEntity());

		newDto.setPassword(encodePassword);
		newDto.setUserId(userDto.getUserId());
		if (newDto.equals(userDto)) {
			return true;
		}
		return false;
	}

	@Override
    @Transactional
	public boolean modifyCardLimit(int userId, int limit) {
		User user = userRepository.findByUserId(userId);
		if (user.getActive() != 0) {
			UserDto userDto = mapper.INSTANCE.userToDto(user);
			userDto.setCardLimit(limit);
			userRepository.save(userDto.toEntity());
			return true;
		}
		return false;
	}

	@Override
    @Transactional
	public int banUser(int userId) {
		User user = userRepository.findByUserId(userId);
		if (user.getActive() != 2) {
			UserDto userDto = mapper.INSTANCE.userToDto(user);
			userDto.setActive(2);
			userRepository.save(userDto.toEntity());
			return 1;
		}
		return 0;
	}

	@Override
    @Transactional
	public int deleteUser(int userId) {
		User user = userRepository.findByUserId(userId);
		if (user.getActive() != 0) {
			UserDto userDto = mapper.INSTANCE.userToDto(user);
			userDto.setActive(0);
			userRepository.save(userDto.toEntity());
			return 1;
		}
		return 0;
	}

	@Override
    @Transactional
	public int activateUser(int userId) {
		User user = userRepository.findByUserId(userId);
		if (user.getActive() != 1) {
			UserDto userDto = mapper.INSTANCE.userToDto(user);
			userDto.setActive(1);
			userRepository.save(userDto.toEntity());
			return 1;
		}
		return 0;
	}

	@Override
    public boolean login(UserDto user) {
		User userInfo = userRepository.findByEmail(user.getEmail());
		UserDto userDto = mapper.INSTANCE.userToDto(userInfo);
		String encodedPassword = userRepository.findPwd(user.getEmail());
		if (passwordEncoder.matches(user.getPassword(), encodedPassword)
				&& user.getEmail().equals(userInfo.getEmail())) {
			user.setPassword(encodedPassword);
			boolean result = userRepository.existsByEmailAndPassword(user.getEmail(), user.getPassword());
			if (result) {
				userDto.setAccessTime(user.getAccessTime());
				userRepository.save(userDto.toEntity());
			}
			return result;
		} else {
			return false;
		}
	}

	@Override
    public boolean loginAdmin(AdminDto admin) {
		String encodedPassword = adminRepository.findPwd(admin.getEmail());
		Admin adminET = adminRepository.findByEmail(admin.getEmail());
		if (passwordEncoder.matches(admin.getPassword(), encodedPassword)
				&& adminET.getEmail().equals(admin.getEmail())) {
			admin.setPassword(encodedPassword);
			boolean result = adminRepository.existsByEmailAndPassword(admin.getEmail(), admin.getPassword());
			return result;
		} else {
			return false;
		}
	}

	@Override
    public int assignedTotal() {
		int total = 0;
		List<Integer> assigned = userRepository.findCardLimitByActive();
		for (int cnt : assigned) {
			total += cnt;
		}
		return total;
	}

	@Override
    public Boolean pay(int userId, int bill) {
		User user = userRepository.findByUserId(userId);
		UserDto userDto = mapper.INSTANCE.userToDto(user);
		if (userDto.getBalance() - bill >= 0) {
			userDto.setBalance(userDto.getBalance() - bill);
			userRepository.save(userDto.toEntity());
			return true;
		} else {
			return false;
		}
	}

	@Override
    public int countUser() {
		int count = (int) userRepository.count();
		return 0;
	}

}