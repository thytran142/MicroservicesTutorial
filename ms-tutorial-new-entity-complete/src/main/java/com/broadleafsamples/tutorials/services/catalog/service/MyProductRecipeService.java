package com.broadleafsamples.tutorials.services.catalog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.broadleafcommerce.data.tracking.core.context.ContextInfo;
import com.broadleafcommerce.data.tracking.core.service.BaseRsqlCrudEntityService;
import com.broadleafcommerce.data.tracking.core.service.RsqlCrudEntityHelper;
import com.broadleafsamples.tutorials.services.catalog.domain.ProductRecipe;
import com.broadleafsamples.tutorials.services.catalog.repository.ProductRecipeRepository;

import cz.jirutka.rsql.parser.ast.Node;

@Service
public class MyProductRecipeService extends BaseRsqlCrudEntityService<ProductRecipe> {

    private final ProductRecipeRepository repository;

    public MyProductRecipeService(ProductRecipeRepository repository,
            RsqlCrudEntityHelper helper) {
        super(repository, helper);
        this.repository = repository;
    }

    @NonNull
    public Page<ProductRecipe> readByProductContextId(@NonNull String productContextId,
            Node filters,
            Pageable page,
            ContextInfo contextInfo) {
        return repository.findByProductContextId(productContextId, filters, page, contextInfo)
                .map(domain -> convertFromPersistentDomain(domain, contextInfo));
    }

}
