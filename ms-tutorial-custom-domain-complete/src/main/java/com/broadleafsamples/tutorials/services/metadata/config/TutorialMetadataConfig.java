package com.broadleafsamples.tutorials.services.metadata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import com.broadleafcommerce.customer.metadata.customer.CustomerAddressFields;
import com.broadleafcommerce.customer.metadata.customer.CustomerEditViewContributor;
import com.broadleafcommerce.customer.metadata.customer.CustomerFields;
import com.broadleafcommerce.customer.metadata.customer.CustomerForms;
import com.broadleafcommerce.metadata.domain.Endpoint;
import com.broadleafcommerce.metadata.domain.FieldComponent;
import com.broadleafcommerce.metadata.domain.OperationType;
import com.broadleafcommerce.metadata.domain.builder.EntityFormBuilder;
import com.broadleafcommerce.metadata.domain.builder.ExternalGridBuilder;
import com.broadleafcommerce.metadata.domain.builder.FieldGroupBuilder;
import com.broadleafcommerce.metadata.domain.type.EndpointType;
import com.broadleafcommerce.metadata.route.ComponentRouteLocator;
import com.broadleafcommerce.metadata.route.builder.ComponentRouteLocatorBuilder;
import com.broadleafsamples.tutorials.services.metadata.movie.MovieBrowseViewContributor;
import com.broadleafsamples.tutorials.services.metadata.movie.MovieCreateViewContributor;
import com.broadleafsamples.tutorials.services.metadata.movie.MovieEditViewContributor;
import com.broadleafsamples.tutorials.services.metadata.movie.MovieFields;
import com.broadleafsamples.tutorials.services.metadata.movie.MovieForms;

import java.util.Collections;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class TutorialMetadataConfig {

    public static final String MOVIE_SCOPE = "CUSTOMER";

    private final Environment environment;

    @Bean
    public ComponentRouteLocator movieRoutes() {
        return ComponentRouteLocatorBuilder.routes()
                .route("/movies",
                        r -> r.componentId(MovieBrowseViewContributor.ID)
                            .scope(MOVIE_SCOPE))
                .route("/movies/create",
                        r -> r.componentId(MovieCreateViewContributor.ID)
                            .scope(MOVIE_SCOPE))
                .route("/movies/:id",
                        r -> r.componentId(MovieEditViewContributor.ID)
                            .scope(MOVIE_SCOPE))
                .build();
    }

    @Bean
    public MovieBrowseViewContributor movieBrowseViewContributor(MovieFields movieFields) {
        return new MovieBrowseViewContributor(movieFields);
    }

    @Bean
    public MovieCreateViewContributor movieCreateViewContributor(MovieForms movieForms) {
        return new MovieCreateViewContributor(movieForms);
    }

    @Bean
    public MovieEditViewContributor movieEditViewContributor(MovieForms movieForms) {
        return new MovieEditViewContributor(movieForms);
    }

    @Bean
    public MovieForms movieForms(MovieFields movieFields) {
        return new MovieForms(movieFields);
    }

    @Bean
    public MovieFields movieFields() {
        return new MovieFields();
    }

    @Bean
    @Primary
    public CustomerEditViewContributor customerEditViewContributor(CustomerFields customerFields,
                                                                   CustomerAddressFields customerAddressFields) {
        Boolean useFullName = environment.getProperty("broadleaf.customer.useFullName", Boolean.class, Boolean.TRUE);
        TutorialCustomerForms tutorialCustomerForms = new TutorialCustomerForms(customerFields, customerAddressFields, useFullName);

        return new CustomerEditViewContributor(tutorialCustomerForms);
    }

    class TutorialCustomerForms extends CustomerForms {
        public TutorialCustomerForms(CustomerFields customerFields, CustomerAddressFields customerAddressFields, boolean useFullName) {
            super(customerFields, customerAddressFields, useFullName);
        }

        @Override
        protected EntityFormBuilder generalForm(String id) {
            return super.generalForm(id)
                    .addGroup(new FieldGroupBuilder("Movies")
                    .id("customerMoviesFieldsGroup")
                    .addComponent(movies().build()));
        }
    }

    public Endpoint.EndpointBuilder createCustomerMovieEndpoint() {
        return Endpoint.builder(EndpointType.CREATE)
                .uri("/customers/${parent.id}/movies")
                .method(Endpoint.Method.POST)
                .operationType(OperationType.CREATE)
                .scope(MOVIE_SCOPE);
    }

    public ExternalGridBuilder movies() {
        return new ExternalGridBuilder("Movies",
                MOVIE_SCOPE,
                "/customers/${parent.id}/movies")
                .id("moviesExternalGrid")
                .sandboxDiscriminated("CUSTOMER_MOVIES")
                .catalogDiscriminated()
                .enableNarrowPaging()
                .order(1000)
                .addField(FieldComponent.builder("movie.title")
                        .label("Title")
                        .order(1000)
                        .build())
                .enableCreate("Add Movie",
                        createCustomerMovieEndpoint().build(),
                        Collections.singletonList(
                                movieFields().get(MovieFields.TITLE)
                                .order(1000)
                                .build()))
                .enableDelete("Remove", "/customers/${parent.id}/movies/${row.id}");
    }
}
