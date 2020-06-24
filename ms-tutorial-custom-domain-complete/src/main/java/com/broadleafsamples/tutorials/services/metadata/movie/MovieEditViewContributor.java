package com.broadleafsamples.tutorials.services.metadata.movie;

import com.broadleafcommerce.metadata.contribute.ComponentContributor;
import com.broadleafcommerce.metadata.domain.Component;
import com.broadleafcommerce.metadata.domain.Link;
import com.broadleafcommerce.metadata.domain.builder.EditEntityViewBuilder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MovieEditViewContributor implements ComponentContributor {
    public static final String MOVIE_SCOPE = "CUSTOMER";
    public static final String ID = "customer:movies:update";
    protected static final String FULL_URI = "/customer/movies/${id}";

    private final MovieForms movieForms;

    @Override
    public Component contribute() {
        // @formatter:off

        return new EditEntityViewBuilder(ID,
                MOVIE_SCOPE,
                "Edit")
                .sandboxDiscriminated("MOVIE")
                .catalogDiscriminated()
                .backLabel("Back")
                .backLink(Link.byId(MovieBrowseViewContributor.ID))
                .fetchUri(FULL_URI)
                .updateUri(FULL_URI)
                .deleteUri(FULL_URI)
                .addForm(movieForms.generalEditForm()
                    .order(1000)
                    .build())
                .build();

        // @formatter:on
    }
}
