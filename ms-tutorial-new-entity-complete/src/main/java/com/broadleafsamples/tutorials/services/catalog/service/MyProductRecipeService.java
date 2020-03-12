package com.broadleafsamples.tutorials.services.catalog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.broadleafcommerce.data.tracking.core.Trackable;
import com.broadleafcommerce.data.tracking.core.context.ContextInfo;
import com.broadleafcommerce.data.tracking.core.service.BaseRsqlCrudEntityService;
import com.broadleafcommerce.data.tracking.core.service.RsqlCrudEntityHelper;
import com.broadleafsamples.tutorials.services.catalog.domain.ProductRecipe;
import com.broadleafsamples.tutorials.services.catalog.repository.ProductRecipeRepository;

import cz.jirutka.rsql.parser.ast.Node;
import lombok.AccessLevel;
import lombok.Getter;

@Service
public class MyProductRecipeService<P extends ProductRecipe> extends BaseRsqlCrudEntityService<P>
        implements ProductRecipeService<P> {

    @Getter(value = AccessLevel.PROTECTED, onMethod_ = {@NonNull})
    private final ProductRecipeRepository repository;

    public MyProductRecipeService(ProductRecipeRepository repository,
                           RsqlCrudEntityHelper helper) {
        super(repository, helper);
        this.repository = repository;
    }

    @Override
    @NonNull
    public Page<P> readByProductContextId(@NonNull String productContextId,
                                          Node filters,
                                          Pageable page,
                                          ContextInfo contextInfo) {
        return repository.findByProductContextId(productContextId, filters, page, contextInfo)
                .map(domain -> convertFromPersistentDomain((Trackable) domain, contextInfo));
    }
    
}
