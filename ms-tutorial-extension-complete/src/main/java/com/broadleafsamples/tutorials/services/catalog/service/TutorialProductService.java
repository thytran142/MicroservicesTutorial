package com.broadleafsamples.tutorials.services.catalog.service;

import com.broadleafcommerce.catalog.repository.product.ProductRepository;
import com.broadleafcommerce.catalog.service.product.DefaultProductService;
import com.broadleafcommerce.catalog.service.product.VariantService;
import com.broadleafcommerce.data.tracking.core.context.ContextInfo;
import com.broadleafcommerce.data.tracking.core.service.RsqlCrudEntityHelper;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
public class TutorialProductService extends DefaultProductService {

    public TutorialProductService(ProductRepository repository, RsqlCrudEntityHelper helper,
                                  VariantServ]]]]ice variantService) {
        super(repository, helper, variantService);
    }

    @Override
    public Object create(Object businessInstance, ContextInfo context) {
        log.info("EXTENSION TUTORIAL - DEMONSTRATE EXECUTION OF CUSTOM BUSINESS LOGIC");
        return super.create(businessInstance, context);
    }
}