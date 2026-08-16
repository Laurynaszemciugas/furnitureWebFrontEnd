package com.example.demo.Services.InternetScraping;

import com.example.demo.Common.Logic.HttpCallLogic;
import com.example.demo.Common.Logic.InternetScraper.PriceResult;
import com.example.demo.ControllerModels.Material.MaterialInfo;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ScraperService {

    HttpCallLogic httpCallLogic;

    public ScraperService(HttpCallLogic httpCallLogic) {
        this.httpCallLogic = httpCallLogic;
    }

    public List<PriceResult> getDataFromInternetScraping(String product,String jwt) {

        product = product.replace(" ", "+");

        return Arrays.stream(httpCallLogic.HttpCallWithJwt("internetScraper/getDataFromInternetScraping", HttpMethod.GET ,product, PriceResult[].class,true,jwt)).toList();

    }


}
