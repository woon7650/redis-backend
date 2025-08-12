package com.example.login.movie.service;

import com.example.login.movie.dto.MovieInfoResponse;
import com.example.login.movie.model.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {
    public Movie toEntity(MovieInfoResponse.MovieInfo mi) {
        String repNation = (mi.getNations()!=null && !mi.getNations().isEmpty())
                ? mi.getNations().get(0).getNationNm() : null;
        String repGenre  = (mi.getGenres()!=null && !mi.getGenres().isEmpty())
                ? mi.getGenres().get(0).getGenreNm()   : null;
        String director  = (mi.getDirectors()!=null && !mi.getDirectors().isEmpty())
                ? mi.getDirectors().get(0).getPeopleNm(): null;
        String companyCd = (mi.getCompanys()!=null && !mi.getCompanys().isEmpty())
                ? mi.getCompanys().get(0).getCompanyCd(): null;
        String companyNm = (mi.getCompanys()!=null && !mi.getCompanys().isEmpty())
                ? mi.getCompanys().get(0).getCompanyNm(): null;

        return Movie.builder()
                .movieCd(mi.getMovieCd())
                .movieNm(mi.getMovieNm())
                .movieNmEn(mi.getMovieNmEn()) // 엔티티 필드가 moviceNmEn(오타)로 선언되어 있음
                .repNationAlt(repNation)
                .repGenreAlt(repGenre)
                .directors(director)
                .companyCd(companyCd)
                .companyNm(companyNm)
                .prdtYear(mi.getPrdtYear())
                .openDt(mi.getOpenDt()) // yyyyMMdd
                .build();
    }
}
