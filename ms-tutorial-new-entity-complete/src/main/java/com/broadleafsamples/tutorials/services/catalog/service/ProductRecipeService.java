package com.broadleafsamples.tutorials.services.catalog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import com.broadleafcommerce.data.tracking.core.context.ContextInfo;
import com.broadleafcommerce.data.tracking.core.service.RsqlCrudEntityService;
import com.broadleafsamples.tutorials.services.catalog.domain.ProductRecipe;

import cz.jirutka.rsql.parser.ast.Node;

public interface ProductRecipeService <P extends ProductRecipe> extends RsqlCrudEntityService<P> {

    @NonNull
    Page<P> readByProductContextId(@NonNull String productContextId,
                                   Node filters,
                                   Pageable page,
                                   ContextInfo contextInfo);

}
