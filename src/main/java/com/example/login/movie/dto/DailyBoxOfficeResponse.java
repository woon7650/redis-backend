package com.example.login.movie.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

// 일별 박스오피스 응답
@Getter
@Setter
public class DailyBoxOfficeResponse {
    private BoxOfficeResult boxOfficeResult;

    @Getter @Setter
    public static class BoxOfficeResult {
        private List<DailyBoxOffice> dailyBoxOfficeList;
    }
    @Getter @Setter
    public static class DailyBoxOffice {
        private String movieCd;
        private String movieNm;
        private String openDt; // YYYYMMDD
    }
}