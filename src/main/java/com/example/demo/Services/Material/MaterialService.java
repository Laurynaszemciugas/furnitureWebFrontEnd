package com.example.demo.Services.Material;

import com.example.demo.Common.Logic.HttpCallLogic;
import com.example.demo.ControllerModels.Common.MiniStatHolder;
import com.example.demo.ControllerModels.CommonDtos.Materials;
import com.example.demo.ControllerModels.Material.MaterialBriefDto;
import com.example.demo.ControllerModels.Filter.Material.MaterialFilterHolder;
import com.example.demo.ControllerModels.Material.MaterialMiniStat;
import com.example.demo.DTOS.ComboBoxMaterial;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class MaterialService {

    HttpCallLogic httpCallLogic;

    public MaterialService(HttpCallLogic httpCallLogic) {
        this.httpCallLogic = httpCallLogic;
    }



    @SneakyThrows
    public List<MaterialBriefDto> getUserMaterialsList(MaterialFilterHolder materialFilterHolder) {

        return Arrays.stream(httpCallLogic.HttpCall("material/getAllMaterialForFeed", HttpMethod.POST,materialFilterHolder, MaterialBriefDto[].class,false)).toList();

    }

    @SneakyThrows
    public Long getPageCount(MaterialFilterHolder materialFilterHolder) {

        return httpCallLogic.HttpCall("material/getTotalPages", HttpMethod.POST,materialFilterHolder, Long.class,false);

    }


    @SneakyThrows
    public MiniStatHolder getMiniStats(LocalDate fromDate, LocalDate toDate) {

        return httpCallLogic.HttpCall("material/getMaterialMiniStats", HttpMethod.GET,String.format("%s/%s",fromDate,toDate), MiniStatHolder.class,true);

    }




}
