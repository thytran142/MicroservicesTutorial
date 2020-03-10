package com.broadleafsamples.tutorials.services.metadata.recipe;

import com.broadleafcommerce.metadata.domain.Component;
import com.broadleafcommerce.metadata.domain.builder.EntityFormBuilder;

import java.util.Arrays;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RecipeForms {

    @Getter(AccessLevel.PROTECTED)
    private final RecipeFields recipeFields;

    protected EntityFormBuilder generalCreateForm() {
        return generalForm("recipeCreateForm");
    }

    protected EntityFormBuilder generalEditForm() {
        return generalForm("recipeUpdateForm");
    }

    protected EntityFormBuilder generalForm(String id) {
        EntityFormBuilder form = new EntityFormBuilder(id, "Recipes");
        generalFields().forEach(form::addComponent);
        return form;
    }

    protected List<Component> generalFields() {
        return Arrays.asList(
                recipeFields.get(RecipeFields.NAME)
                        .order(1000)
                        .build(),
                recipeFields.get(RecipeFields.DESCRIPTION)
                        .order(2000)
                        .build());
    }

}
