package com.broadleafsamples.tutorials.services.metadata.movie;

import com.broadleafcommerce.metadata.contribute.ComponentContributor;
import com.broadleafcommerce.metadata.domain.Component;
import com.broadleafcommerce.metadata.domain.Link;
import com.broadleafcommerce.metadata.domain.builder.CreateEntityViewBuilder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MovieCreateViewContributor implements ComponentContributor {
    public static final String MOVIE_SCOPE = "CUSTOMER";
    public static final String ID = "customer:movies:create";

    private final MovieForms movieForms;

    @Override
    public Component contribute() {
        // @formatter:off

        return new CreateEntityViewBuilder(ID,
                MOVIE_SCOPE,
                "/customer/movies",
                "Create Movie")
                .sandboxDiscriminated("MOVIE")
                .catalogDiscriminated()
                .backLabel("Back")
                .backLink(Link.byId(MovieBrowseViewContributor.ID))
                .createLabel("Create")
                .addForm(movieForms.generalCreateForm()
                        .order(1000)
                        .build())
                .build();

        // @formatter:on
    }

}
