package com.broadleafsamples.tutorials.services.metadata.recipe;

import com.broadleafcommerce.metadata.contribute.DefaultFieldLibrary;
import com.broadleafcommerce.metadata.domain.FieldComponent;
import com.broadleafcommerce.metadata.domain.builder.field.LookupFieldBuilder;
import com.broadleafcommerce.metadata.domain.type.FieldType;

public class RecipeFields extends DefaultFieldLibrary {

    public static final String RECIPE_SCOPE = "PRODUCT";
    public static final String RECIPE = "recipe";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";

    public RecipeFields() {
        add(FieldComponent.builder(NAME)
                .label("Name"));

        add(FieldComponent.builder(FieldType.HTML, DESCRIPTION)
                .label("Description"));

        add(this.createRecipeLookup(RECIPE)
                .label("Select Recipe")
                .required(true));

    }

    public LookupFieldBuilder createRecipeLookup(String name) {
        return new LookupFieldBuilder(LookupFieldBuilder.SelectionType.OPTION,
                name,
                "Recipe",
                readEndpoint -> readEndpoint
                        .narrowPaging()
                        .param("q", "${filter.q}")
                        .param("cq", "${filter.cq}")
                        .scope(RECIPE_SCOPE)
                        .uri("/catalog/recipes"))
                .catalogDiscriminated()
                .sandboxDiscriminated("RECIPES")
                .configureHydration(
                        hydrateEndpointBuilder -> hydrateEndpointBuilder
                                .scope(RECIPE_SCOPE)
                                .uri("/catalog/recipes/${id}"))
                .configureSelect(LookupFieldBuilder.SelectComponents.DEFAULT)
                .configureModal(
                        modalBuilder -> modalBuilder
                                .label("Select Recipe")
                                .configureQuery()
                                .configureQueryBuilder()
                                .column(this.get(RecipeFields.NAME)
                                        .order(1000)
                                        .build())
                                .column(this.get(RecipeFields.DESCRIPTION)
                                        .order(2000)
                                        .build()));
    }

}

