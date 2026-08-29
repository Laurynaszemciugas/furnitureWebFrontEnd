package com.example.demo.Common.Logic.InternetScraper.ImagesScraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ImageScraper {

    HttpClient client = HttpClient.newHttpClient();

    public List<String> imageScraper(String text) throws IOException, InterruptedException {

        List<String> imageUrls = new ArrayList<>();

        if(text!=null) {

            text = text.replace(" ","");

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(String.format("https://www.kaina24.lt/s/%s/", text)))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            String html = response.body();


            //System.out.println(html);

            Document document = Jsoup.parse(response.body());


            Elements element = document.select(".product-item-h");

            if (element.isEmpty()) {
                return imageDifferentLayout(html);
            }




            for (Element product : element) {


                String productPicture = product.select(".image-wrap img").attr("data-src");


                imageUrls.add(productPicture);


            }

        }

        return imageUrls;





    }

    public List<String> imagesMultipleVendorPage(String vendorUrl) throws IOException, InterruptedException {

        List<String> imageUrls = new ArrayList<>();


        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(URI.create(vendorUrl))
                .GET()
                .build();

        HttpResponse<String> response2 =
                client.send(request2, HttpResponse.BodyHandlers.ofString());

        Document document2 = Jsoup.parse(response2.body());

        Elements element2 = document2.select(".item-table-wrap[itemprop=offers] .seller-item-table");

        if(element2.isEmpty()){
            return null;
        }

        for(Element extraProducts : element2){




            String expandedProductName = extraProducts.select(".col-4 h3").first().text();
            imageUrls.add(expandedProductName);




        }


        return imageUrls;

    }



    public List<String> imageDifferentLayout(String html) throws IOException, InterruptedException {

        List<String> image = new ArrayList<>();

        Document document = Jsoup.parse(html);

        Elements element = document.select(".product-item");


        for (Element product : element) {


            String imageUrl = product.select(".product-item img").attr("data-src");
            image.add(imageUrl);






        }

        return image;
    }

}
