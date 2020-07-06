package com.broadleafsamples.tutorials.services.customer.web.endpoint;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.broadleafcommerce.common.extension.data.DataRouteByExample;
import com.broadleafcommerce.common.extension.projection.Projection;
import com.broadleafcommerce.customer.domain.Customer;
import com.broadleafcommerce.customer.service.CustomerService;
import com.broadleafcommerce.data.tracking.core.context.ContextInfo;
import com.broadleafcommerce.data.tracking.core.context.ContextOperation;
import com.broadleafcommerce.data.tracking.core.exception.EntityMissingException;
import com.broadleafcommerce.data.tracking.core.mapping.support.HydrationUtility;
import com.broadleafcommerce.data.tracking.core.policy.Policy;
import com.broadleafcommerce.data.tracking.core.service.RsqlCrudEntityService;
import com.broadleafcommerce.data.tracking.core.type.OperationType;
import com.broadleafsamples.tutorials.services.customer.domain.CustomerMovie;
import com.broadleafsamples.tutorials.services.customer.provider.jpa.domain.JpaMovie;
import com.broadleafsamples.tutorials.services.customer.service.CustomerMovieService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import cz.jirutka.rsql.parser.ast.Node;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@DataRouteByExample(Customer.class)
public class CustomerMovieEndpoint {
    public static final String CUSTOMER_SCOPE  = "CUSTOMER";

    private final CustomerService<Customer> customerService;
    private final RsqlCrudEntityService<Projection<JpaMovie>> movieService;
    private final CustomerMovieService customerMovieService;

    @GetMapping("/customers/{id}/movies")
    @Policy(permissionRoots = {CUSTOMER_SCOPE})
    public Page<CustomerMovie> readCustomerMovies(@PathVariable("id") String customerId,
                                                  @PageableDefault(size = 50)Pageable page,
                                                  @ContextOperation(value = OperationType.READ)ContextInfo contextInfo,
                                                  Node filters) {
        final Customer customer = customerService.readByContextId(customerId, contextInfo);
        final Page<CustomerMovie> results = customerMovieService.readByCustomerContextId(customerId, filters, page, contextInfo);

        List<String> movieIds = results.map(customerMovie -> customerMovie.getMovie().getId()).getContent();
        Map<String, Projection<JpaMovie>> movies = fetchMovies(movieIds, contextInfo);

        return results.map(customerMovie -> {
            customerMovie.setCustomer(customer);
            String movieId = customerMovie.getMovie().getId();
            HydrationUtility.hydrateIfNotNull(
                    movies.get(movieId),
                    customerMovie::setMovie,
                    HydrationUtility.getGenericErrorMessage("CustomerMovie#movie", "Movie", movieId));
            return customerMovie;
        });
    }

    @PostMapping(value = "/customers/{id}/movies", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Policy(permissionRoots = {CUSTOMER_SCOPE})
    public CustomerMovie addCustomerMovie(@PathVariable("id") String customerId,
                                          @RequestBody CustomerMovie customerMovie,
                                          @ContextOperation(value = OperationType.CREATE) ContextInfo contextInfo) {
        final Customer customer = customerService.readByContextId(customerId, contextInfo);
        Projection<JpaMovie> childMovie = movieService.readByContextId(customerMovie.getMovie().getId(), contextInfo);

        customerMovie.setCustomer(customer);
        CustomerMovie result = customerMovieService.create(customerMovie, contextInfo);

        result.setCustomer(customer);
        result.setMovie(childMovie);

        return result;
    }

    @DeleteMapping("/customers/{id}/movies/{customerMovieId}")
    @Policy(permissionRoots = {CUSTOMER_SCOPE})
    public void removeCustomerMovie(@PathVariable("id") String customerId,
                                    @PathVariable("customerMovieId") String customerMovieId,
                                    @ContextOperation(value = OperationType.DELETE) ContextInfo contextInfo) {
        CustomerMovie customerMovie = customerMovieService.readByContextId(customerMovieId, contextInfo);
        if (ObjectUtils.notEqual(customerId, customerMovie.getCustomer().getId())) {
            throw new EntityMissingException();
        }

        customerMovieService.delete(customerMovie.getId(), contextInfo);
    }

    private Map<String, Projection<JpaMovie>> fetchMovies(List<String> movieIds, ContextInfo contextInfo) {
        if (movieIds.isEmpty()) {
            return Collections.emptyMap();
        }

        Stream<Projection<JpaMovie>> movies = StreamSupport.stream(
                movieService.readAllByContextId(movieIds.stream()::iterator, contextInfo)
                        .spliterator(), false);

        return movies.collect(Collectors.toMap(Projection::getId, Function.identity()));
    }
}
