package com.broadleafsamples.tutorials.services.metadata.movie;

import com.broadleafcommerce.metadata.contribute.DefaultFieldLibrary;
import com.broadleafcommerce.metadata.domain.FieldComponent;
import com.broadleafcommerce.metadata.domain.builder.field.LookupFieldBuilder;

public class MovieFields extends DefaultFieldLibrary {
    public static final String MOVIE_SCOPE = "MOVIE";
    public static final String MOVIE = "MOVIE";
    public static final String TITLE = "title";

    public MovieFields() {
        add(FieldComponent.builder(TITLE)
            .label("Title"));

        add(this.createMovieLookup(MOVIE)
            .label("Select Movie")
            .required(true));
    }

    public LookupFieldBuilder createMovieLookup(String title) {
        return new LookupFieldBuilder(LookupFieldBuilder.SelectionType.OPTION,
                title,
                "Movie",
                readEndpoint -> readEndpoint
                    .narrowPaging()
                    .param("q", "${filter.q}")
                    .param("cq", "${filter.cq}")
                    .scope(MOVIE_SCOPE)
                    .uri("/customer/movies"))
                .catalogDiscriminated()
                .sandboxDiscriminated("MOVIES")
                .configureHydration(
                        hydrationEndpointBuilder -> hydrationEndpointBuilder
                            .scope(MOVIE_SCOPE)
                            .uri("/customer/movies/${id}"))
                .configureSelect(LookupFieldBuilder.SelectComponents.DEFAULT)
                .configureModal(
                        modalBuilder -> modalBuilder
                            .label("Select Movie")
                            .configureQuery()
                            .configureQueryBuilder()
                            .column(this.get(title)
                                    .order(1000)
                                    .build()));
    }
}
