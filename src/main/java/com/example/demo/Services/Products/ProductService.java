package com.example.demo.Services.Products;

import com.example.demo.ControllerModels.Products.ProductFeedModel;
import com.example.demo.ControllerModels.Products.ProductPageData;
import com.example.demo.Enums.ProductCategory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {


    public List<ProductFeedModel> loadProductFeedModel(){
        List<ProductFeedModel> productFeedModel =  new ArrayList<>();

        productFeedModel.add(new ProductFeedModel(
                1,
                "https://picsum.photos/300/200?1",
                "Gaming Mouse",
                ProductCategory.Books,
                49.99,
                15,
                3
        ));

        productFeedModel.add(new ProductFeedModel(
                2,
                "https://picsum.photos/300/200?2",
                "Mechanical Keyboard",
                ProductCategory.Books,
                89.99,
                8,
                33
        ));


        productFeedModel.add(new ProductFeedModel(
                4,
                "https://picsum.photos/300/200?4",
                "4K Monitor",
                ProductCategory.Books,
                349.99,
                12,
                12
        ));

        productFeedModel.add(new ProductFeedModel(
                5,
                "https://picsum.photos/300/200?5",
                "Wireless Headphones",
                ProductCategory.Books,
                129.99,
                20,
                10
        ));

        productFeedModel.add(new ProductFeedModel(
                6,
                "https://picsum.photos/300/200?6",
                "Gaming Desk",
                ProductCategory.Books,
                279.99,
                5,
                4
        ));

        productFeedModel.add(new ProductFeedModel(
                7,
                "https://picsum.photos/300/200?7",
                "Smartphone",
                ProductCategory.Books,
                799.99,
                10,
                6
        ));


        return productFeedModel;
    }


    public ProductPageData loadDashboardData(){

        ProductPageData productPageData = new ProductPageData();
        productPageData.setProductFeedModelList(loadProductFeedModel());
        productPageData.setCreatedAt(LocalDateTime.now());

        return productPageData;

    }



}
