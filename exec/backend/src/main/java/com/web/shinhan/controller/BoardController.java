package com.web.shinhan.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.web.shinhan.service.payment.PaymentService;
import com.web.shinhan.service.store.StoreService;
import com.web.shinhan.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.shinhan.model.PaymentDto;
import com.web.shinhan.model.StoreDto;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/board")
@CrossOrigin(origins = { "*" })
public class BoardController {

	public static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Autowired
	UserService userService;

	@Autowired
	StoreService storeService;

	@Autowired
	PaymentService paymentService;

	@ApiOperation(value = "사용된 금액", notes = "assignedTotal: 배정된 총액\r\n" + "notConfirmed: 정산되어야 하는 금액\r\n"
			+ "used: 사용된 금액", response = HashMap.class)
	@GetMapping("/expenses")
	public ResponseEntity<Map<String, Object>> expenses() {
		logger.info("expenses - 호출");

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;

		try {
			int used = paymentService.calcTotalExpense();
			resultMap.put("used", used);
			int notConfirmed = paymentService.notConfirmed();
			resultMap.put("notConfirmed", notConfirmed);
			int assignedTotal = userService.assignedTotal();
			resultMap.put("assignedTotal", assignedTotal);
			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "월간 소비량", notes = "월간 소비량을 보여준다.")
	@GetMapping("/expenses/month")
	public ResponseEntity<Map<Integer, Object>> expenseByMonth(@RequestParam int year) {
		logger.info("expenseByMonth - 호출");
		Map<Integer, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;

		try {
			for (int i = 1; i <= 12; i++) {
				int payment = paymentService.expenseByMonth(i, year);
				resultMap.put(i, payment);
			}
			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<Integer, Object>>(resultMap, status);
	}

	@ApiOperation(value = "통계", notes = "통계에 쓰일 모든 결제 내역을 반환한다.", response = Boolean.class)
	@GetMapping("/expenses/statistics")
	public ResponseEntity<Map<String, Object>> expenseForStatistics() {
		logger.info("expenseForStatistics - 호출 ");

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;

		try {
			List<PaymentDto> paymentList = paymentService.findAllByStatus();
			resultMap.put("payment", paymentList);
			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "가맹점 신청 현황", notes = "가맹점 신청 현황을 반환한다.", response = HashMap.class)
	@GetMapping("/store/unassigned")
	public ResponseEntity<Map<String, Object>> unassignedStoreList(Pageable pageable) throws Exception {
		logger.info("unassignedStoreList - 호출");

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		Page<StoreDto> page = null;

		// 회원 정보 조회
		try {
			page = storeService.findAllUnassignedStore(pageable);
			resultMap.put("storeList", page);
			status = HttpStatus.OK;
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "실시간 결제 현황", notes = "실시간 결제 현황 정보를 가지고 온다.", response = HashMap.class)
	@GetMapping("/payment/recent")
	public ResponseEntity<Map<String, Object>> recentPayment(Pageable pageable) throws Exception {
		logger.info("recentPayment - 호출");

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
				Sort.by(Sort.Direction.DESC, "date"));

		try {
			resultMap.put("payment", paymentService.findAll(pageable));
			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

}