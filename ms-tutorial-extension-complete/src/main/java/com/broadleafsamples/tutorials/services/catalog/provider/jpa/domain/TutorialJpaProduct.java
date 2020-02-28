package com.broadleafsamples.tutorials.services.catalog.provider.jpa.domain;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import com.broadleafcommerce.catalog.provider.jpa.domain.product.JpaProduct;
import com.broadleafsamples.tutorials.services.catalog.domain.TutorialProduct;
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

    @Override
    public ModelMapper fromMe() {
        ModelMapper mapper = super.fromMe();
        mapper.createTypeMap(TutorialJpaProduct.class, TutorialProduct.class)
                .addMapping(TutorialJpaProduct::getContextId, TutorialProduct::setId);
        return mapper;
    }

    @Override
    public ModelMapper toMe() {
        ModelMapper mapper = super.toMe();
        mapper.createTypeMap(TutorialProduct.class, TutorialJpaProduct.class)
                .addMappings(mapping -> mapping.when(Conditions.isNotNull())
                        .map(TutorialProduct::getId, TutorialJpaProduct::setContextId));
        return mapper;
    }

    @Override
    public Class<?> getBusinessDomainType() {
        return TutorialProduct.class;
    }


}
