package com.example.demo.Services.Products;

import com.example.demo.ControllerModels.Products.ProductFeedModel;
import com.example.demo.ControllerModels.Products.ProductPageData;
import com.example.demo.Enums.Category;
import com.example.demo.Enums.Stock;
import com.example.demo.ServerDBCall.ProductAdd.ProductAddCall;
import com.example.demo.ServerDBCall.ProductPage.ProductsCall;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {


    ProductsCall productsCall;

    public ProductService(ProductsCall productsCall) {
        this.productsCall = productsCall;
    }


    public List<ProductFeedModel> loadProductFeedModel(Stock stock, Category category, int page, int size) throws IOException, InterruptedException {

        page = page -1;

        return productsCall.getAllProducts(stock, category,page,size);
    }


    public ProductPageData loadProductData(Stock stock, Category category, int page, int size) throws IOException, InterruptedException {

        ProductPageData productPageData = new ProductPageData();
            productPageData.setProductFeedModelList(loadProductFeedModel(stock,category,page,size));

        productPageData.setCreatedAt(LocalDateTime.now());

        return productPageData;

    }

    @SneakyThrows
    public Long loadProductPageCount(){
        return productsCall.getProductPages();
    }



}
