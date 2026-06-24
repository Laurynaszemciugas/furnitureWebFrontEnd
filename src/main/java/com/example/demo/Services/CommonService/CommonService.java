package com.example.demo.Services.CommonService;

import com.example.demo.Common.Logic.HttpCallLogic;
import com.example.demo.ControllerModels.CommonDtos.Materials;
import com.example.demo.ControllerModels.CommonDtos.Orders;
import com.example.demo.ControllerModels.Error.ErrorResponse;
import com.example.demo.DTOS.ComboBoxMaterial;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CommonService {

    HttpCallLogic httpCallLogic;

    public CommonService(HttpCallLogic httpCallLogic) {
        this.httpCallLogic = httpCallLogic;
    }

    @SneakyThrows
    public List<ComboBoxMaterial> getMaterialNames() {

        return Arrays.stream(httpCallLogic.HttpCall("material/getMaterialNames", HttpMethod.GET,null, ComboBoxMaterial[].class,false)).toList();

    }

    @SneakyThrows
    public Materials getMaterialDataAccordingToId(Long id) {

        return httpCallLogic.HttpCall("material/getMaterialDataAccordingToName", HttpMethod.POST,id, Materials.class,false);

    }




}
