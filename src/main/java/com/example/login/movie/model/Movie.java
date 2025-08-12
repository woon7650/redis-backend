package com.example.login.movie.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
@Table(name = "TB_MOVIE")
public class Movie {

    @Id
    @Column(name = "MOVIE_CD", unique = true, nullable = false)
    private String movieCd;

    @Column(name = "MOVIE_NM")
    private String movieNm;

    @Column(name = "MOVIE_NM_EN")
    private String movieNmEn;

    @Column(name = "REP_NATION_ALT")
    private String repNationAlt;

    @Column(name = "REP_GENRE_ALT")
    private String repGenreAlt;

    @Column(name = "DIRECTORS")
    private String directors;

    @Column(name = "COMPANY_CD")
    private String companyCd;

    @Column(name = "COMPANY_NM")
    private String companyNm;

    @Column(name = "PRDT_YEAR")
    private String prdtYear;

    @Column(name = "OPEN_DT")
    private String openDt;
}
