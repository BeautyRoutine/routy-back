package com.routy.routyback.service.user;

import com.routy.routyback.dto.user.myrouty.DashboardResponse;
import com.routy.routyback.dto.user.myrouty.RoutineDetailResponse;
import com.routy.routyback.dto.weather.Season;
import com.routy.routyback.dto.weather.WeatherResponse;
import com.routy.routyback.mapper.user.UserMapper;
import com.routy.routyback.service.weather.WeatherService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * DashboardService
 *
 * - MyRouty 대시보드 화면에서 필요한 정보를 한 번에 조립하고 반환하는 서비스
 * - 날씨 + 루틴 요약 + 사용 제품 통계 + 스킨케어 팁 + 계절 알림 등 생성
 */
@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserMapper userMapper;
    private final WeatherService weatherService;
    private final RoutineService routineService;
    private final MyUsedProductService myUsedProductService;
    private final UserWeatherPreferenceService preferenceService;

    /**
     * 대시보드 정보 조립
     *
     * @param userId 문자열 사용자 아이디
     * @return DashboardResponse DTO
     */
    public DashboardResponse buildDashboard(String userId) {

        Long userNo = userMapper.findUserNoByUserId(userId);
        DashboardResponse dto = new DashboardResponse();

        // 1) 날씨 조회 (사용자 선호지역 → 없으면 WeatherService 기본값)
        String city = preferenceService.getPreferredCityOrDefault(userId);
        WeatherResponse weather = weatherService.getTodayWeatherByCity(city);
        dto.setWeather(weather);

        // 2) 오늘 날짜 루틴 요약
        String today = LocalDate.now().toString();
        RoutineDetailResponse routine = routineService.getRoutineByDate(userId, today);

        if (routine != null) {
            dto.setRoutineExists(true);
            dto.setTodayRoutineSummary(routine.getSummary());
        } else {
            dto.setRoutineExists(false);
            dto.setTodayRoutineSummary(null);
        }

        // 3) 사용 중 제품 개수
        int usedCount = myUsedProductService.getUsedProducts(userId).size();
        dto.setUsedProductCount(usedCount);

        // 4) 계절 기반 스킨케어 팁 생성
        dto.setSkincareTip(generateSkincareTip(weather.getSeason()));

        // 5) 계절 변화 알림 생성
        dto.setSeasonalAlert(generateSeasonAlert(weather.isSeasonChangeSoon()));

        return dto;
    }

    /**
     * 계절 기반 스킨케어 추천 메시지 생성
     */
    private String generateSkincareTip(Season season) {
        if (season == null) {
            return "피부 타입에 맞는 기본 루틴을 꾸준히 유지해 주세요!";
        }

        switch (season) {
            case WINTER:
                return "겨울철에는 보습 위주의 루틴이 필요해요!";
            case SUMMER:
                return "여름철에는 유분 조절과 자외선 차단이 중요해요!";
            case SPRING:
                return "봄에는 민감해진 피부를 위해 진정 케어를 추천해요!";
            case AUTUMN:
                return "가을에는 건조함이 시작되니 수분 공급을 강화하세요!";
            default:
                return "피부 타입에 맞는 기본 루틴을 꾸준히 유지해 주세요!";
        }
    }

    /**
     * 계절 변화 알림 메시지 생성
     */
    private String generateSeasonAlert(boolean changeSoon) {
        if (changeSoon) {
            return "곧 계절이 바뀌어요! 루틴 점검을 추천드립니다.";
        }
        return null;
    }

}
