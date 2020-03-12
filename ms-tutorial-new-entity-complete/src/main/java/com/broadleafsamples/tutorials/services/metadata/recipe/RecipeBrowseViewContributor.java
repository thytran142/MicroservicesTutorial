package com.broadleafsamples.tutorials.services.metadata.recipe;

import com.broadleafcommerce.metadata.contribute.ComponentContributor;
import com.broadleafcommerce.metadata.domain.Component;
import com.broadleafcommerce.metadata.domain.Link;
import com.broadleafcommerce.metadata.domain.builder.EntityGridBuilder;
import com.broadleafcommerce.metadata.domain.type.ComponentClassifier;
import com.broadleafcommerce.metadata.domain.type.FieldType;
import com.broadleafcommerce.metadata.domain.type.ViewType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RecipeBrowseViewContributor implements ComponentContributor {

    public static final String RECIPE_SCOPE = "PRODUCT";
    public static final String ID = "catalog:recipes:list";

    @Getter(AccessLevel.PROTECTED)
    private final RecipeFields fields;

    @Override
    public Component contribute() {
        // @formatter:off
        return Component.builder(ComponentClassifier.VIEW, ViewType.ENTITY_BROWSE_VIEW)
                .id(ID)
                .label("Recipes")
                .scope(RECIPE_SCOPE)
                .subComponent(new EntityGridBuilder("mainRecipesGridView",
                        RECIPE_SCOPE,
                        "/catalog/recipes",
                        "Recipes")
                        .sandboxDiscriminated("RECIPE")
                        .catalogDiscriminated()
                        .enableFulltextSearch("query")
                        .enableAdvancedSearchQueryBuilder("cq")
                        .enableNumberedPaging()
                        .enableColumnSorting()
                        .enableAddButton("Add", Link.byId(RecipeCreateViewContributor.ID))
                        .addField(fields.get(RecipeFields.NAME)
                                .type(FieldType.Grid.LINK)
                                .order(1000)
                                .attribute("link", Link.byId(RecipeEditViewContributor.ID), Link.class)
                                .build())
                        .addField(fields.get(RecipeFields.DESCRIPTION)
                                .order(2000)
                                .build())
                        .translationsAware()
                        .build())
                .build();
        // @formatter:on
    }
}