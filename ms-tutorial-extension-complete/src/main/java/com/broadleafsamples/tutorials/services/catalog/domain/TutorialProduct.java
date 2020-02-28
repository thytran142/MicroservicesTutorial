package com.broadleafsamples.tutorials.services.catalog.domain;

import com.broadleafcommerce.catalog.domain.RequestView;
import com.broadleafcommerce.catalog.domain.ResponseView;
import com.broadleafcommerce.catalog.domain.product.Product;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonView({RequestView.class, ResponseView.class})
public class TutorialProduct extends Product {

    private String myProperty;

}
