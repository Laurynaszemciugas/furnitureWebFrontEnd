package com.example.demo.Services.ProductEditService;

import com.example.demo.ControllerModels.Common.ExtraDetails;
import com.example.demo.ControllerModels.Common.GridMaterials;
import com.example.demo.ControllerModels.Common.ImagesData;
import com.example.demo.ControllerModels.Common.ProductDataEditAddDto;
import com.example.demo.Enums.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductEditService {

    public ProductDataEditAddDto productEditDtoLoad(){

        ProductDataEditAddDto productEditDto = new ProductDataEditAddDto();

        List<ProductDataEditAddDto> productData = new ArrayList<>();

        List<ImagesData> list = new ArrayList<>();
        list.add(new ImagesData(null,"13","image","dataNotFound.png","yes", ImageLogic.Main,null));
        list.add(new ImagesData(null,"33","image23","Screenshot 2026-04-27 001745.png","yes", ImageLogic.NonMain,null));

        productEditDto.setImages(list);

        productEditDto.setProductName("Sofa");
        productEditDto.setSku("313");
        productEditDto.setDescription("111111111111111111111111111111111111111");
        productEditDto.setPrice(65.23);
        productEditDto.setDiscount(0);
        productEditDto.setMaterialCost(0);
        productEditDto.setStockQuantity(10L);
        productEditDto.setLowStockThreshold(50L);
        productEditDto.setCategory(Category.Furniture);
        List<Tags> tags = new ArrayList<>();
        tags.add(Tags.Door);
        tags.add(Tags.Modern);
        productEditDto.setTags(tags);
        productEditDto.setStatus(Status.Enabled);
        productEditDto.setVisibility(Visibility.Visible);

        List<ExtraDetails> extraDetails = new ArrayList<>();
        extraDetails.add(new ExtraDetails(null,"Color","red"));
        productEditDto.setExtraDetails(extraDetails);


        List<GridMaterials> gridMaterials = new ArrayList<>();
        gridMaterials.add(new GridMaterials(1l,"Wood",25l,25.5,"Planks"));
        productEditDto.setMaterials(gridMaterials);


        productData.add(productEditDto);


        return  productEditDto;

    }












}
