package com.example.demo.Pages.CommonComponents.ProductComponents.RightSide.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.CommonDtos.Comments;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;

public class ReviewCrafter {


    CommonComponents commonComponents;
    Common common;

    public ReviewCrafter(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
    }

    public VerticalLayout commentsHolder(List<Comments> comments){
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

                    h.add(commentCrafter(s.getCommenter(),s.getComment(),s.getReview()));
        }

        return h;
    }




    public VerticalLayout commentCrafter(String comenter, String comment, double review){

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setWidthFull();

       // Button removeReview = commonComponents.noNav

        //Button editShortCut = commonComponents.smallIconButtons("1", VaadinIcon.PENCIL,"black");

        if(comenter != null) {

                verticalLayout.addClassName("island");
                verticalLayout.add(commonComponents.doubleValueRow(commonComponents.spanCrafterWordNoHide(comenter, "activityFeed-name"),starCrafter(review)),
                        commonComponents.spanCrafterWordNoHide(comment,"stat-example")
                );
            }

        else{
            verticalLayout.add(commonComponents.noDataFound());
        }

        return verticalLayout;
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
            System.out.println(remaining +  " remaining" );
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
