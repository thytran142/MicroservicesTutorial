package com.broadleafsamples.tutorials.services.customer.provider.jpa.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import com.broadleafcommerce.customer.provider.jpa.domain.JpaCustomer;
import com.broadleafcommerce.data.tracking.core.CustomerContextTrackable;
import com.broadleafcommerce.data.tracking.core.filtering.fetch.Link;
import com.broadleafcommerce.data.tracking.core.mapping.BusinessTypeAware;
import com.broadleafcommerce.data.tracking.core.mapping.FilterAndSortAlias;
import com.broadleafcommerce.data.tracking.core.mapping.ModelMapperMappable;
import com.broadleafcommerce.data.tracking.jpa.UlidConverter;
import com.broadleafcommerce.data.tracking.jpa.filtering.TrackingListener;
import com.broadleafcommerce.data.tracking.jpa.filtering.domain.CustomerContextJpaTracking;
import com.broadleafsamples.tutorials.services.customer.domain.CustomerMovie;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "TUTORIAL_CUSTOMER_MOVIE")
@Data
@EqualsAndHashCode(exclude = "_id")
@EntityListeners(TrackingListener.class)
public class JpaCustomerMovie implements Serializable, CustomerContextTrackable<CustomerContextJpaTracking>,
        ModelMapperMappable, BusinessTypeAware {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "blcid")
    @GenericGenerator(name = "blcid", strategy = "blcid")
    @Type(type = "com.broadleafcommerce.data.tracking.jpa.hibernate.ULidType")
    @Column(name = "ID", nullable = false)
    @SuppressWarnings("squid:S00116")
    private String _id;

    @Column(name = "CONTEXT_ID")
    @Convert(converter = UlidConverter.class)
    @FilterAndSortAlias("id")
    private String contextId;

    @Embedded
    private CustomerContextJpaTracking tracking;

    @Link(JpaCustomer.class)
    @FilterAndSortAlias("customer")
    @Column(name = "CUSTOMER_CONTEXT_ID")
    @Convert(converter = UlidConverter.class)
    private String customerContextId;

    @Link(JpaMovie.class)
    @FilterAndSortAlias("movie")
    @Column(name = "MOVIE_COTEXT_ID")
    @Convert(converter = UlidConverter.class)
    private String movieContextId;

    @Override
    public ModelMapper fromMe() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.createTypeMap(JpaCustomerMovie.class, CustomerMovie.class)
                .addMapping(JpaCustomerMovie::getContextId, CustomerMovie::setId)
                .addMappings(mapping -> mapping.map(JpaCustomerMovie::getMovieContextId,
                        (customerMovie, value) -> customerMovie.setupMovie((String)value)))
                .addMappings(mapping -> mapping.map(JpaCustomerMovie::getCustomerContextId,
                        (customerMovie, value) -> customerMovie.getCustomer().setId((String)value)));

        return mapper;
    }

    @Override
    public ModelMapper toMe() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.createTypeMap(CustomerMovie.class, JpaCustomerMovie.class)
                .addMappings(mapping -> mapping.when(Conditions.isNotNull())
                    .map(CustomerMovie::getId, JpaCustomerMovie::setContextId))
                .addMappings(mapping -> mapping.map(
                                customerMovie -> customerMovie.getCustomer().getId(),
                                JpaCustomerMovie::setCustomerContextId))
                .addMappings(mapping -> mapping.map(
                        customerMovie -> customerMovie.getMovie().getId(),
                        JpaCustomerMovie::setMovieContextId));
        return mapper;
    }

    @Override
    public Class<?> getBusinessDomainType() {
        return CustomerMovie.class;
    }

    @Override
    public Optional<String> getDisplay() {
        return Optional.empty();
    }
}
