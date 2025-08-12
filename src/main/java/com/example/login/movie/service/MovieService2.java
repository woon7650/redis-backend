package com.example.login.movie.service;

import com.example.login.movie.dto.DailyBoxOfficeResponse;
import com.example.login.movie.dto.MovieDto;
import com.example.login.movie.dto.MovieInfoResponse;
import com.example.login.movie.model.Movie;
import com.example.login.movie.repository.MovieRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import kr.or.kobis.kobisopenapi.consumer.rest.KobisOpenAPIRestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;


@Service("movieService2")
@Transactional
public class MovieService2 {

    @Resource(name ="movieRepository")
    private MovieRepository movieRepository;

    private final MovieMapper movieMapper;
    private final ObjectMapper mapper;
    private final KobisOpenAPIRestService kobis;

    private final String    ITEM_PER_PAGE = "5";
    private final String    API_KEY = "684faef71fea38cb296ed239f9d6830e";

    public MovieService2(MovieMapper movieMapper) {
        this.movieMapper = movieMapper;
        this.mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.kobis = new KobisOpenAPIRestService(API_KEY);
    }


//    public void saveInternalMovies(MovieDto movieDto){
//        String                  yesterday = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//        String                  multiMovieYn = "N";
//        String                  repNationCd = "";
//        String                  wideAreaCd = "";
//        String                  dailyResponse = "";
//        ObjectMapper mapper = new ObjectMapper();
//        HashMap<String, Object> dailyResult;
//        KobisOpenAPIRestService kobisService = new KobisOpenAPIRestService(API_KEY);
//
//        try {
//            dailyResponse = kobisService.getDailyBoxOffice(true, yesterday,ITEM_PER_PAGE,multiMovieYn,repNationCd,wideAreaCd);
//            dailyResult = mapper.readValue(dailyResponse, HashMap.class);
//            System.out.println(dailyResult);
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//
////        movieRepository.save(movieDto.toEntity());
//    }

    public void saveInternalMovies() {
        String targetDt = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String dailyJson = safeCall(() ->
                kobis.getDailyBoxOffice(true, targetDt, ITEM_PER_PAGE, "N", "", "")
        );

        DailyBoxOfficeResponse daily = read(dailyJson, DailyBoxOfficeResponse.class);
        if (daily == null || daily.getBoxOfficeResult() == null
                || daily.getBoxOfficeResult().getDailyBoxOfficeList() == null) return;

        List<Movie> toSave = new ArrayList<>();
        for (DailyBoxOfficeResponse.DailyBoxOffice d : daily.getBoxOfficeResult().getDailyBoxOfficeList()) {
            String infoJson = safeCall(() -> kobis.getMovieInfo(true, d.getMovieCd()));
            MovieInfoResponse info = read(infoJson, MovieInfoResponse.class);
            if (info == null || info.getMovieInfoResult() == null || info.getMovieInfoResult().getMovieInfo() == null) continue;

            Movie entity = movieMapper.toEntity(info.getMovieInfoResult().getMovieInfo());
            toSave.add(entity);
        }

        System.out.println(toSave);
        movieRepository.saveAll(toSave);
    }

    private <T> T read(String json, Class<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (Exception e) {
            System.out.println("parse error: " + e.getMessage());
            return null;
        }
    }

    private String  safeCall(Callable<String> call) {
        try {
            return call.call();
        } catch (Exception e) {
            System.out.println("api error: " + e.getMessage()); return null;
        }
    }
}

