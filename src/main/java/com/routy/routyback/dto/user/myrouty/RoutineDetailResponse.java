package com.routy.routyback.dto.user.myrouty;

import lombok.Data;
import java.util.List;


@Data
public class RoutineDetailResponse {

    private Long routineId;
    private String date;
    private String summary;
    private List<String> extraActivities;
    private String dailyReview;
    private List<UsedProductDetail> products;
}
