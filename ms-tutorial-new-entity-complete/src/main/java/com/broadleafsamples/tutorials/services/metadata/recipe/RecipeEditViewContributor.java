package com.broadleafsamples.tutorials.services.metadata.recipe;

import com.broadleafcommerce.metadata.contribute.ComponentContributor;
import com.broadleafcommerce.metadata.domain.Component;
import com.broadleafcommerce.metadata.domain.Link;
import com.broadleafcommerce.metadata.domain.builder.EditEntityViewBuilder;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RecipeEditViewContributor implements ComponentContributor {

    public static final String RECIPE_SCOPE = "PRODUCT";
    public static final String ID = "catalog:recipes:update";
    protected static final String FULL_URI = "/catalog/recipes/${id}";

    @Getter(AccessLevel.PROTECTED)
    private final RecipeForms recipeForms;

    @Override
    public Component contribute() {
        // @formatter:off
        return new EditEntityViewBuilder(ID,
                RECIPE_SCOPE,
                "Edit")
                .sandboxDiscriminated("RECIPE")
                .catalogDiscriminated()
                .backLabel("Back")
                .backLink(Link.byId(RecipeBrowseViewContributor.ID))
                .fetchUri(FULL_URI)
                .updateUri(FULL_URI)
                .deleteUri(FULL_URI)
                .addForm(recipeForms.generalEditForm()
                        .order(1000)
                        .build())
                .build();
        // @formatter:on
    }
}
