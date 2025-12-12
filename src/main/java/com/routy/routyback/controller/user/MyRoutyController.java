package com.routy.routyback.controller.user;

import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.dto.user.WeatherPreferenceRequest;
import com.routy.routyback.dto.user.myrouty.DashboardResponse;
import com.routy.routyback.dto.user.myrouty.MyProductResponse;
import com.routy.routyback.dto.user.myrouty.RoutineDetailResponse;
import com.routy.routyback.dto.user.myrouty.RoutineMonthlyCalendarResponse;
import com.routy.routyback.dto.user.myrouty.RoutineRequest;
import com.routy.routyback.service.user.DashboardService;
import com.routy.routyback.service.user.MyRoutyService;
import com.routy.routyback.service.user.MyUsedProductService;
import com.routy.routyback.service.user.RoutineService;
import com.routy.routyback.service.user.UserWeatherPreferenceService;
import com.routy.routyback.service.weather.WeatherService;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class MyRoutyController {

    private final MyRoutyService myRoutyService;
    private final MyUsedProductService myUsedProductService;   // 현재 사용 중 제품 기능 서비스
    private final UserWeatherPreferenceService userWeatherPreferenceService;   // 날씨 선호 지역 관리 서비스
    private final WeatherService weatherService;                               // 날씨 정보 제공 서비스
    private final RoutineService routineService;   // 루틴 기능 서비스
    private final DashboardService dashboardService;   // 대시보드 조립 서비스

    @GetMapping("/{userId}/my-products")            // GET /api/users/{userId}/my-products
    public ApiResponse<List<MyProductResponse>> getMyProducts(
        @PathVariable("userId") String userId     // 경로에서 사용자 번호 추출
    ) {
        List<MyProductResponse> products =          // 서비스에서 내 제품 목록 조회
            myRoutyService.getMyProducts(userId);

        return new ApiResponse<>(                   // 공통 응답 형태로 감싸서 반환
            200,                                // resultCode : 성공
            "SUCCESS",                          // resultMsg  : 성공 메시지
            products                            // data       : 내 제품 목록
        );
    }

    // 현재 사용 중인 제품 번호 리스트 조회
    @GetMapping("/{userId}/used-products")
    public ApiResponse<List<Long>> getUsedProducts(@PathVariable("userId") String userId) {
        List<Long> used = myUsedProductService.getUsedProducts(userId);
        return new ApiResponse<>(200, "SUCCESS", used);
    }

    // 제품 사용 중 체크
    @PostMapping("/{userId}/used-products/{prdNo}")
    public ApiResponse<Void> addUsedProduct(
        @PathVariable("userId") String userId,
        @PathVariable("prdNo") Long prdNo) {

        myUsedProductService.addUsedProduct(userId, prdNo);
        return new ApiResponse<>(200, "SUCCESS", null);
    }

    // 제품 사용 중 해제
    @DeleteMapping("/{userId}/used-products/{prdNo}")
    public ApiResponse<Void> removeUsedProduct(
        @PathVariable("userId") String userId,
        @PathVariable("prdNo") Long prdNo) {

        myUsedProductService.removeUsedProduct(userId, prdNo);
        return new ApiResponse<>(200, "SUCCESS", null);
    }

    // 사용자의 날씨 선호 지역 조회
    @GetMapping("/{userId}/preferences/weather")
    public ApiResponse<String> getWeatherPreference(
        @PathVariable("userId") String userId) {

        String city = userWeatherPreferenceService.getPreferredCity(userId);
        return new ApiResponse<>(200, "SUCCESS", city);
    }

    // 사용자의 날씨 선호 지역 저장
    @PostMapping("/{userId}/preferences/weather")
    public ApiResponse<Void> updateWeatherPreference(
        @PathVariable("userId") String userId,
        @RequestBody WeatherPreferenceRequest request) {

        userWeatherPreferenceService.updatePreferredCity(userId, request.getCityName());
        return new ApiResponse<>(200, "SUCCESS", null);
    }

    // MyRouty Dashboard : 날씨 + 루틴요약 + 기타정보
    @GetMapping("/{userId}/dashboard")
    public ApiResponse<DashboardResponse> getDashboard(
        @PathVariable("userId") String userId) {

        DashboardResponse dashboard = dashboardService.buildDashboard(userId);
        return new ApiResponse<>(200, "SUCCESS", dashboard);
    }

    // -----------------------------------------------------------
    // 🧴 루틴(Routine) 기능 API
    //   1) 월간 루틴 조회
    //   2) 특정 날짜 루틴 상세 조회
    //   3) 특정 날짜 루틴 저장(Upsert)
    // -----------------------------------------------------------

    // 1) 월간 루틴 조회
    // GET /api/users/{userId}/routines/monthly?year=2025&month=12
    @GetMapping("/{userId}/routines/monthly")
    public ApiResponse<RoutineMonthlyCalendarResponse> getMonthlyRoutine(
        @PathVariable("userId") String userId,
        @RequestParam("year") int year,
        @RequestParam("month") int month
    ) {
        RoutineMonthlyCalendarResponse resp = new RoutineMonthlyCalendarResponse();
        resp.setYear(year);
        resp.setMonth(month);
        resp.setRoutines(routineService.getMonthlyRoutine(userId, year, month));

        return new ApiResponse<>(200, "SUCCESS", resp);
    }

    // 2) 특정 날짜 루틴 상세 조회
    // GET /api/users/{userId}/routines/{date}
    @GetMapping("/{userId}/routines/{date}")
    public ApiResponse<RoutineDetailResponse> getRoutineByDate(
        @PathVariable("userId") String userId,
        @PathVariable("date") String date   // yyyy-MM-dd 형식
    ) {
        try {
            LocalDate.parse(date);   // 날짜 형식 검증
        } catch (DateTimeParseException e) {
            return new ApiResponse<>(400, "INVALID_DATE_FORMAT", null);
        }

        return new ApiResponse<>(200, "SUCCESS",
            routineService.getRoutineByDate(userId, date));
    }

    // 3) 특정 날짜 루틴 저장(Upsert)
    // POST /api/users/{userId}/routines/{date}
    @PostMapping("/{userId}/routines/{date}")
    public ApiResponse<Void> saveRoutine(
        @PathVariable("userId") String userId,
        @PathVariable("date") String date,   // yyyy-MM-dd
        @RequestBody RoutineRequest request
    ) {
        routineService.saveRoutine(userId, date, request);
        return new ApiResponse<>(200, "SUCCESS", null);
    }

    // 4) 특정 날짜 루틴 삭제
    // DELETE /api/users/{userId}/routines/{date}
    @DeleteMapping("/{userId}/routines/{date}")
    public ApiResponse<Void> deleteRoutine(
        @PathVariable("userId") String userId,
        @PathVariable("date") String date   // yyyy-MM-dd
    ) {
        try {
            LocalDate.parse(date);   // 날짜 형식 검증
        } catch (DateTimeParseException e) {
            return new ApiResponse<>(400, "INVALID_DATE_FORMAT", null);
        }

        routineService.deleteRoutine(userId, date);
        return new ApiResponse<>(200, "SUCCESS", null);
    }
}