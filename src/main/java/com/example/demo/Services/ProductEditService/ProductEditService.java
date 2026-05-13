package com.example.demo.Services.ProductEditService;

import com.example.demo.ControllerModels.Common.GridMaterials;
import com.example.demo.ControllerModels.Common.ImagesData;
import com.example.demo.ControllerModels.ProductEdit.ProductEditDto;
import com.example.demo.Enums.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

@Service
public class ProductEditService {

    public ProductEditDto productEditDtoLoad(){

        ProductEditDto productEditDto = new ProductEditDto();

        List<ProductEditDto> productData = new ArrayList<>();

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
        productEditDto.setStockQuantity(10);
        productEditDto.setLowStockThreshold(50);
        productEditDto.setCategory(Category.Furniture);
        List<Tags> tags = new ArrayList<>();
        tags.add(Tags.Door);
        tags.add(Tags.Modern);
        productEditDto.setTags(tags);
        productEditDto.setStatus(Status.Enabled);
        productEditDto.setVisibility(Visibility.Visible);
        List<GridMaterials> gridMaterials = new ArrayList<>();
        gridMaterials.add(new GridMaterials(1,"Wood",25,"Planks"));
        productEditDto.setMaterials(gridMaterials);


        productData.add(productEditDto);


        return  productEditDto;

    }












}
