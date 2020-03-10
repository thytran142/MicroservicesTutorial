package com.broadleafsamples.tutorials.services.catalog.provider.jpa.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import com.broadleafcommerce.catalog.provider.jpa.domain.product.JpaProduct;
import com.broadleafcommerce.data.tracking.core.CatalogTrackable;
import com.broadleafcommerce.data.tracking.core.filtering.fetch.Link;
import com.broadleafcommerce.data.tracking.core.mapping.BusinessTypeAware;
import com.broadleafcommerce.data.tracking.core.mapping.FilterAndSortAlias;
import com.broadleafcommerce.data.tracking.core.mapping.ModelMapperMappable;
import com.broadleafcommerce.data.tracking.jpa.UlidConverter;
import com.broadleafcommerce.data.tracking.jpa.filtering.TrackingListener;
import com.broadleafcommerce.data.tracking.jpa.filtering.domain.CatalogJpaTracking;
import com.broadleafsamples.tutorials.services.catalog.domain.ProductRecipe;

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
@Table(name = "MY_PRODUCT_BENEFIT")
@Data
@EqualsAndHashCode(exclude = "_id")
@EntityListeners(TrackingListener.class)
public class JpaProductRecipe implements Serializable, CatalogTrackable<CatalogJpaTracking>,
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

    @Link(JpaProduct.class)
    @FilterAndSortAlias("product")
    @Column(name = "PRODUCT_CONTEXT_ID")
    @Convert(converter = UlidConverter.class)
    private String productContextId;

    @Link(JpaRecipe.class)
    @FilterAndSortAlias("recipe")
    @Column(name = "RECIPE_CONTEXT_ID")
    @Convert(converter = UlidConverter.class)
    private String recipeContextId;

    @Override
    public ModelMapper fromMe() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.createTypeMap(JpaProductRecipe.class, ProductRecipe.class)
                .addMapping(JpaProductRecipe::getContextId, ProductRecipe::setId)
                .addMappings(mapping -> mapping.map(JpaProductRecipe::getRecipeContextId,
                        (productRecipe, value) -> productRecipe.getRecipe()
                                .setId((String) value)))
                .addMappings(mapping -> mapping.map(JpaProductRecipe::getProductContextId,
                        (productRecipe, value) -> productRecipe.getProduct()
                                .setId((String) value)));
        return mapper;
    }

    @Override
    public ModelMapper toMe() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.createTypeMap(ProductRecipe.class, JpaProductRecipe.class)
                .addMappings(mapping -> mapping.when(Conditions.isNotNull())
                        .map(ProductRecipe::getId, JpaProductRecipe::setContextId))
                .addMappings(
                        mapping -> mapping.map(
                                productRecipe -> productRecipe.getProduct().getId(),
                                JpaProductRecipe::setProductContextId))
                .addMappings(
                        mapping -> mapping.map(
                                productRecipe -> productRecipe.getRecipe().getId(),
                                JpaProductRecipe::setRecipeContextId));
        return mapper;
    }

    @Override
    public Class<?> getBusinessDomainType() {
        return ProductRecipe.class;
    }

    @Override
    public Optional<String> getDisplay() {
        return Optional.empty();
    }

}
