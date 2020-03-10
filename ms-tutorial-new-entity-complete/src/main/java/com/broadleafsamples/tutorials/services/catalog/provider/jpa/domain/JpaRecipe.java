package com.broadleafsamples.tutorials.services.catalog.provider.jpa.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import com.broadleafcommerce.common.jpa.JpaConstants;
import com.broadleafcommerce.data.tracking.core.CatalogTrackable;
import com.broadleafcommerce.data.tracking.core.mapping.BusinessTypeAware;
import com.broadleafcommerce.data.tracking.core.mapping.FilterAndSortAlias;
import com.broadleafcommerce.data.tracking.core.mapping.ModelMapperMappable;
import com.broadleafcommerce.data.tracking.jpa.UlidConverter;
import com.broadleafcommerce.data.tracking.jpa.filtering.TrackingListener;
import com.broadleafcommerce.data.tracking.jpa.filtering.domain.CatalogJpaTracking;
import com.broadleafsamples.tutorials.services.catalog.domain.Recipe;

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
@Table(name = "MY_RECIPE")
@Data
@EqualsAndHashCode(exclude = "_id")
@EntityListeners(TrackingListener.class)
public class JpaRecipe implements Serializable, CatalogTrackable<CatalogJpaTracking>,
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
    private CatalogJpaTracking tracking;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION", length = JpaConstants.MEDIUM_TEXT_LENGTH)
    private String description;

    @Override
    public ModelMapper fromMe() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.createTypeMap(JpaRecipe.class, Recipe.class)
                .addMapping(JpaRecipe::getContextId, Recipe::setId);
        return mapper;
    }

    @Override
    public ModelMapper toMe() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.createTypeMap(Recipe.class, JpaRecipe.class)
                .addMappings(mapping -> mapping.when(Conditions.isNotNull())
                        .map(Recipe::getId, JpaRecipe::setContextId));
        return mapper;
    }

    @Override
    public Class<?> getBusinessDomainType() {
        return Recipe.class;
    }

    @Override
    public Optional<String> getDisplay() {
        return Optional.ofNullable(getName());
    }
}
