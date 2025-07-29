package com.example.login.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MovieDto {

    //영화코드
    private String movieCd;
    //영화명
    private String movieNm;
    //영화명
    private String moviceNmEn;
    //제작국가
    private String repNationAlt;
    //장르
    private String repGenreAlt;
    //영화감독
    private String directors;
    //제작사코드
    private String companyCd;
    //제작사명
    private String companyNm;

    //제작연도
    private String prdtYear;
    //개봉일
    private String openDt;
}
