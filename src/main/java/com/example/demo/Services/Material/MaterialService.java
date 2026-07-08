package com.example.demo.Services.Material;

import com.example.demo.Common.Logic.HttpCallLogic;
import com.example.demo.ControllerModels.Common.MiniStatHolder;
import com.example.demo.ControllerModels.CommonDtos.Materials;
import com.example.demo.ControllerModels.Error.ErrorResponse;
import com.example.demo.ControllerModels.Material.MaterialBriefDto;
import com.example.demo.ControllerModels.Filter.Material.MaterialFilterHolder;
import com.example.demo.DTOS.ComboBoxMaterial;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Service
@Setter
public class MaterialService {

    HttpCallLogic httpCallLogic;
    Consumer<Boolean> success;

    public MaterialService(HttpCallLogic httpCallLogic) {
        this.httpCallLogic = httpCallLogic;
    }



    public List<MaterialBriefDto> getUserMaterialsList(MaterialFilterHolder materialFilterHolder,String jwt) {

        return Arrays.stream(httpCallLogic.HttpCallWithJwt("material/getAllMaterialForFeed", HttpMethod.POST,materialFilterHolder, MaterialBriefDto[].class,false,jwt)).toList();

    }

    @SneakyThrows
    public Long getPageCount(MaterialFilterHolder materialFilterHolder) {

        return httpCallLogic.HttpCall("material/getTotalPages", HttpMethod.POST,materialFilterHolder, Long.class,false);

    }


    @SneakyThrows
    public MiniStatHolder getMiniStats(LocalDate fromDate, LocalDate toDate) {

        return httpCallLogic.HttpCall("material/getMaterialMiniStats", HttpMethod.GET,String.format("%s/%s",fromDate,toDate), MiniStatHolder.class,true);

    }


    @SneakyThrows
    public void saveNewMaterial(Materials mat) {

        httpCallLogic.checkResponse(
                httpCallLogic.HttpCall("material/createNewMaterial", HttpMethod.POST, mat, ErrorResponse.class,false),null,success,true);

    }

    @SneakyThrows
    public void removeProduct(Long id) {

        httpCallLogic.checkResponse(
                httpCallLogic.HttpCall("material/deleteMaterial", HttpMethod.GET, id, ErrorResponse.class,true),null,success,true);

    }

    @SneakyThrows
    public Materials getMaterial(Long id) {
               return  httpCallLogic.HttpCall("material/getMaterial", HttpMethod.GET, id, Materials.class,true);

    }


    @SneakyThrows
    public void editProduct(Materials mat) {
        httpCallLogic.checkResponse(
                httpCallLogic.HttpCall("material/editExistingMaterial", HttpMethod.POST, mat, ErrorResponse.class,false),null,success,true);
    }



}
