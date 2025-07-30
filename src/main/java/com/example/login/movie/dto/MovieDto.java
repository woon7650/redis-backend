package com.example.login.movie.dto;

import com.example.login.movie.model.Movie;
import com.example.login.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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


    public Movie toEntity(){
        return Movie.builder()
                .movieCd(this.movieCd)
                .movieNm(this.movieNm)
                .moviceNmEn(this.moviceNmEn)
                .repNationAlt(this.repNationAlt)
                .repGenreAlt(this.repGenreAlt)
                .directors(this.directors)
                .companyCd(this.companyCd)
                .companyNm(this.companyNm)
                .prdtYear(this.prdtYear)
                .openDt(this.openDt)
                .build();
    }
}
