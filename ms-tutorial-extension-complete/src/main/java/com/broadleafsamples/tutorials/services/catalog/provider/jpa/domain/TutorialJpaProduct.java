package com.broadleafsamples.tutorials.services.catalog.provider.jpa.domain;

import com.broadleafcommerce.catalog.provider.jpa.domain.product.JpaProduct;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "TUTORIAL_PRODUCT")
@Data
@EqualsAndHashCode(callSuper = true)
public class TutorialJpaProduct extends JpaProduct {

    private static final long serialVersionUID = 1L;

    @Column(name = "MY_PROPERTY")
    private String myProperty;

}
