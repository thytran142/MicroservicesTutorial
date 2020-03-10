package com.broadleafsamples.tutorials.services.metadata.recipe;

import com.broadleafcommerce.metadata.contribute.ComponentContributor;
import com.broadleafcommerce.metadata.domain.Component;
import com.broadleafcommerce.metadata.domain.Link;
import com.broadleafcommerce.metadata.domain.builder.CreateEntityViewBuilder;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RecipeCreateViewContributor implements ComponentContributor {

    public static final String RECIPE_SCOPE = "PRODUCT";
    public static final String ID = "catalog:recipes:create";

    @Getter(AccessLevel.PROTECTED)
    private final RecipeForms recipeForms;

    @Override
    public Component contribute() {
        // @formatter:off
        return new CreateEntityViewBuilder(ID,
                RECIPE_SCOPE,
                "/catalog/recipes",
                "Create Recipe")
                .sandboxDiscriminated("RECIPE")
                .catalogDiscriminated()
                .backLabel("Back")
                .backLink(Link.byId(RecipeBrowseViewContributor.ID))
                .createLabel("Create")
                .addForm(recipeForms.generalCreateForm()
                        .order(1000)
                        .build())
                .build();
        // @formatter:on
    }
}
