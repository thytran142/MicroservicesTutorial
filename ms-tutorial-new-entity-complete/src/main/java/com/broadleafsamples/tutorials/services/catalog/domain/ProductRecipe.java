package com.broadleafsamples.tutorials.services.catalog.domain;

import com.broadleafcommerce.catalog.domain.RequestView;
import com.broadleafcommerce.catalog.domain.ResponseView;
import com.broadleafcommerce.catalog.domain.product.Product;
import com.broadleafcommerce.data.tracking.core.ContextStateAware;
import com.broadleafcommerce.data.tracking.core.filtering.business.domain.ContextState;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import lombok.experimental.Accessors;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonView({RequestView.class, ResponseView.class})
@Accessors(chain = true)
public class ProductRecipe implements Serializable, ContextStateAware {

    private static final long serialVersionUID = 1L;

    @JsonView(ResponseView.class)
    private String id;

    private Recipe recipe;

    private Product product;

    @Accessors(chain = false)
    private ContextState contextState;

}