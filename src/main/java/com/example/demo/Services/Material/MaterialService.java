package com.example.demo.Services.Material;

import com.example.demo.ControllerModels.Common.MiniStatHolder;
import com.example.demo.ControllerModels.Material.MaterialBriefDto;
import com.example.demo.ControllerModels.Filter.Material.MaterialFilterHolder;
import com.example.demo.ControllerModels.Material.MaterialMiniStat;
import com.example.demo.ServerDBCall.MaterialCalls.MaterialCalls;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MaterialService {

    MaterialCalls materialCalls;

    public MaterialService(MaterialCalls materialCalls) {
        this.materialCalls = materialCalls;
    }


    @SneakyThrows
    public List<MaterialBriefDto> getUserMaterialsList(MaterialFilterHolder materialFilterHolder){
        return materialCalls.getMaterials(materialFilterHolder);
    }

    @SneakyThrows
    public Long getPageCount(MaterialFilterHolder materialFilterHolder){
        return materialCalls.getPages(materialFilterHolder);
    }

    @SneakyThrows
    public MiniStatHolder getMiniStats(LocalDate fromDate, LocalDate toDate){
        return materialCalls.getMiniStatData(fromDate,toDate);
    }

}
