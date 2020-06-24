package com.broadleafsamples.tutorials.services.customer.domain;

import com.broadleafcommerce.common.extension.RequestView;
import com.broadleafcommerce.common.extension.ResponseView;
import com.broadleafcommerce.common.extension.projection.Projection;
import com.broadleafcommerce.customer.domain.Customer;
import com.broadleafcommerce.data.tracking.core.ContextStateAware;
import com.broadleafcommerce.data.tracking.core.filtering.business.domain.ContextState;
import com.broadleafsamples.tutorials.services.customer.provider.jpa.domain.JpaMovie;
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
public class CustomerMovie implements Serializable, ContextStateAware {
    private static final long serialVersionUID = 1L;

    @JsonView(ResponseView.class)
    private String id;

    private Projection<JpaMovie> movie;

    private Customer customer;

    @Accessors
    private ContextState contextState;

    public void setupMovie(String id) {
        this.movie = Projection.get(JpaMovie.class, id);
    }
}
