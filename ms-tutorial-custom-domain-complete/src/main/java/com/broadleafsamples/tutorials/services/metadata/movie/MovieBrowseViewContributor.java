package com.broadleafsamples.tutorials.services.metadata.movie;

import com.broadleafcommerce.metadata.contribute.ComponentContributor;
import com.broadleafcommerce.metadata.domain.Component;
import com.broadleafcommerce.metadata.domain.Link;
import com.broadleafcommerce.metadata.domain.builder.EntityGridBuilder;
import com.broadleafcommerce.metadata.domain.type.ComponentClassifier;
import com.broadleafcommerce.metadata.domain.type.FieldType;
import com.broadleafcommerce.metadata.domain.type.ViewType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MovieBrowseViewContributor implements ComponentContributor {
    public static final String MOVIE_SCOPE = "CUSTOMER";
    public static final String ID = "customer:movies:list";

    private final MovieFields fields;

    public Component contribute() {
        // @formatter:off

        return Component.builder(ComponentClassifier.VIEW, ViewType.ENTITY_BROWSE_VIEW)
                .id(ID)
                .label("Movies")
                .scope(MOVIE_SCOPE)
                .subComponent(new EntityGridBuilder("mainMoviesGridView",
                        MOVIE_SCOPE,
                        "/customer/movies",
                        "Movies")
                        .sandboxDiscriminated("MOVIE")
                        .catalogDiscriminated()
                        .enableFulltextSearch("query")
                        .enableAdvancedSearchQueryBuilder("cq")
                        .enableNumberedPaging()
                        .enableColumnSorting()
                        .enableAddButton("Add", Link.byId(MovieCreateViewContributor.ID))
                        .addField(fields.get(MovieFields.TITLE)
                                .type(FieldType.Grid.LINK)
                                .order(1000)
                                .attribute("link", Link.byId(MovieEditViewContributor.ID), Link.class)
                                .build())
                        .translationsAware()
                        .build())
                .build();
        // @formatter:on
    }
}
