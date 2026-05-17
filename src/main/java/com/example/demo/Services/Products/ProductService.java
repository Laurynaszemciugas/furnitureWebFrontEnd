package com.example.demo.Services.Products;

import com.example.demo.ControllerModels.CommonDtos.Product;
import com.example.demo.ControllerModels.Products.ProductFeedModel;
import com.example.demo.ControllerModels.Products.ProductPageData;
import com.example.demo.Enums.Category;
import com.example.demo.Enums.ProductCategory;
import com.example.demo.Enums.Stock;
import com.example.demo.ServerCall.ProductAdd.ProductAddCall;
import jakarta.validation.constraints.Null;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {


    ProductAddCall productAddCall;

    public ProductService(ProductAddCall productAddCall) {
        this.productAddCall = productAddCall;
    }


    public List<ProductFeedModel> loadProductFeedModel(Stock stock, Category category, int page, int size) throws IOException, InterruptedException {

        page = page -1;

        return productAddCall.getAllProducts(stock, category,page,size);
    }


    public ProductPageData loadDashboardData(Stock stock, Category category, int page, int size)  {

        ProductPageData productPageData = new ProductPageData();
        try {
            productPageData.setProductFeedModelList(loadProductFeedModel(stock,category,page,size));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        productPageData.setCreatedAt(LocalDateTime.now());

        return productPageData;

    }



}
