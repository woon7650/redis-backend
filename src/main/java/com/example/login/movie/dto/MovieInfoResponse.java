package com.example.login.movie.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

// 영화 상세 응답
@Getter @Setter
public class MovieInfoResponse {
    private MovieInfoResult movieInfoResult;

    @Getter @Setter
    public static class MovieInfoResult {
        private MovieInfo movieInfo;
    }
    @Getter @Setter
    public static class MovieInfo {
        private String movieCd;
        private String movieNm;
        private String movieNmEn;
        private String prdtYear;
        private String openDt;
        private List<SimpleName> nations;   // [{nationNm:"대한민국"}]
        private List<SimpleName> genres;    // [{genreNm:"드라마"}]
        private List<Director> directors;   // [{peopleNm:"감독명"}]
        private List<Company>  companys;    // [{companyCd:"", companyNm:""}]
    }

    @Getter @Setter
    public static class SimpleName {
        private String nationNm;
        private String genreNm;
    }

    @Getter
    @Setter
    public static class Director   {
        private String peopleNm;
    }

    @Getter @Setter
    public static class Company    {
        private String companyCd;
        private String companyNm;
    }
}