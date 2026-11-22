package com.example.demo.Common;

import com.example.demo.DTOS.Client.AddReview;
import com.example.demo.Entity.ProductComments;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.dom.Style;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Setter
public class ReviewCrafter {


    CommonComponents commonComponents;
    Common common;
    ProductService productService;

    Consumer<AddReview> updateReview;


    int starsSelected = 0;

    public ReviewCrafter(CommonComponents commonComponents, Common common, ProductService productService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.productService = productService;
    }


    // method that containts comments holder and the add new comment availability
    public VerticalLayout allInOneComment(List<ProductComments> comments){

        VerticalLayout v = new VerticalLayout();
        v.setPadding(false);



        v.add(
                commentsHolder(comments),
                addNewComments()
        );


        return v;
    }


    public VerticalLayout addNewComments(){

        starsSelected = 0;

        List<Icon> stars = new ArrayList<>();


        VerticalLayout v = new VerticalLayout();
        v.setPadding(false);

        TextArea comment = new TextArea("Review");
        comment.setWidthFull();
        comment.setHeight("150px");

        HorizontalLayout starHolder = new HorizontalLayout();

        // spin normal loop for 5 times
        for(int i = 0; i < 5; i++){

            Icon star =  commonComponents.iconCrafter(VaadinIcon.STAR,"25px","grey");
            star.addClassName("icon-hover");

            starHolder.add(star);


            stars.add(
                    star
            );

            int finalI = i+1;
            star.addClickListener(e->{

                // reset all
                stars.forEach(ee-> ee.setColor("grey"));

                for(int j = 0; j < finalI; j++){
                    stars.get(j).setColor("Yellow");
                }

                starsSelected = finalI;


            });



        }

        HorizontalLayout holder = new HorizontalLayout();
        holder.setWidthFull();
        holder.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        Button createReview = new Button("Add a review");
        createReview.addThemeVariants(ButtonVariant.PRIMARY);

        createReview.addClickListener(e->{


            AddReview addReview = new AddReview();
            addReview.setReview((long) starsSelected);
            addReview.setComment(comment.getValue());



                updateReview.accept(addReview);


        });

        holder.add(
                starHolder,
                createReview
        );

        v.add(
                comment,
                holder
        );

        return v;
    }


    public VerticalLayout commentsHolder(List<ProductComments> comments){
        VerticalLayout h = new VerticalLayout();
        h.setWidthFull();
        h.setAlignItems(FlexComponent.Alignment.CENTER);

        double review = 0;
        if(!comments.isEmpty()){
            double total = 0;
            for(var s : comments){
                total+=s.getReview();
            }



            review = Math.ceil(total/comments.size());
            System.out.println(review);

        }
        h.add(commonComponents.spanCrafterWordNoHide("Reviews of the product","activityFeed-name"), starCrafter(review));

        for(var s : comments){

                    h.add(commentCrafter(s.getCommenter(),s.getComment(),s.getReview(), s.getCreated()));
        }

        return h;
    }




    public HorizontalLayout commentCrafter(String comenter, String comment, double review, LocalDateTime created){

        HorizontalLayout h = new HorizontalLayout();
        h.getStyle().setPosition(Style.Position.RELATIVE);
        h.addClassName("island");
        h.setWidthFull();

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setWidthFull();

       // Button removeReview = commonComponents.noNav

        //Button editShortCut = commonComponents.smallIconButtons("1", VaadinIcon.PENCIL,"black");

        Button reportReview = new Button("Report as appropriate",commonComponents.iconCrafter(VaadinIcon.FLAG,"35px","royalblue"));
        reportReview.setTooltipText("Report this review as appropriate message (ALL reports are anonymous)");
        reportReview.addThemeVariants(ButtonVariant.TERTIARY);
        reportReview.getStyle().setPosition(Style.Position.ABSOLUTE).setRight("10px").setTop("10px");


        if(comenter != null) {


                verticalLayout.add(
                        reportReview,
                        commonComponents.spanCrafter(common.dateFormatterLocalDateTime(created,"dd MMM yyyy, HH:mm"), "stat-example"),
                        commonComponents.doubleValueRow(
                        commonComponents.spanCrafterWordNoHide(comenter, "activityFeed-name"),starCrafter(review)),
                        commonComponents.spanCrafterWordNoHide(comment,"stat-example")
                );
            }

        else{
            verticalLayout.add(commonComponents.noDataFound());
        }

        Avatar avatar = new Avatar("Client");

        h.add(
                avatar,
                verticalLayout
        );

        return h;
    }

    public HorizontalLayout starCrafter(double star){
        HorizontalLayout starHolder = new HorizontalLayout();
        double stars = Math.ceil(star);


        if(stars != 0) {
            for (int i = 1; i <= stars; i++) {
                Icon starr = VaadinIcon.STAR.create();
                starr.getStyle().set("color", "yellow");
                starHolder.add(starr);
            }
            double remaining = Math.abs(star-5);
            System.out.println("Remainer" +  star % 2);
            for (int i = 1; i <= remaining; i++) {
                Icon starr = VaadinIcon.STAR.create();
                starr.getStyle().set("color", "black");
                starHolder.add(starr);
            }

        }
        else{
            for (int i = 1; i <= 5; i++) {
                Icon starr = VaadinIcon.STAR.create();
                starr.getStyle().set("color", "black");
                starHolder.add(VaadinIcon.STAR.create());
            }
        }
        return starHolder;
    }


}
