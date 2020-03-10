package com.broadleafsamples.tutorials.services.catalog.domain;

import com.broadleafcommerce.catalog.domain.RequestView;
import com.broadleafcommerce.catalog.domain.ResponseView;
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
public class Recipe implements Serializable, ContextStateAware {

    private static final long serialVersionUID = 1L;

    /**
     * The context ID of the recipe.
     *
     * @param id the context ID of the recipe
     * @return the context ID of the recipe
     */
    @JsonView(ResponseView.class)
    private String id;

    /**
     * The name of the recipe.
     *
     * @param name the name of the recipe
     * @return the name of the recipe
     */
    private String name;

    /**
     * HTML-formatted description of the recipe.
     *
     * @param description the HTML-formatted description of the recipe
     * @return the HTML-formatted description of the recipe
     */
    private String description;

    /**
     * A subset of {@link com.broadleafcommerce.data.tracking.core.filtering.domain.Tracking}
     * information to expose the context state for this object.
     *
     * @param contextState
     * a subset of {@link com.broadleafcommerce.data.tracking.core.filtering.domain.Tracking}
     * information to expose the context state for this object
     * @return a subset of
     * {@link com.broadleafcommerce.data.tracking.core.filtering.domain.Tracking} information to
     * expose the context state for this object
     */
    @JsonView(ResponseView.class)
    @Accessors(chain = false)
    private ContextState contextState;
    
}
