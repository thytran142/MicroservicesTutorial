package com.broadleafsamples.tutorials.services.catalog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.broadleafcommerce.data.tracking.core.Trackable;
import com.broadleafcommerce.data.tracking.core.context.ContextInfo;
import com.broadleafcommerce.data.tracking.core.service.BaseRsqlCrudEntityService;
import com.broadleafcommerce.data.tracking.core.service.RsqlCrudEntityHelper;
import com.broadleafsamples.tutorials.services.catalog.domain.Recipe;
import com.broadleafsamples.tutorials.services.catalog.repository.RecipeRepository;

import java.util.Collection;

import lombok.AccessLevel;
import lombok.Getter;

@Service
public class MyRecipeService<P extends Recipe> extends BaseRsqlCrudEntityService<P>
        implements RecipeService<P> {

    @Getter(value = AccessLevel.PROTECTED, onMethod_ = {@NonNull})
    private final RecipeRepository repository;

    public MyRecipeService(RecipeRepository repository,
                              RsqlCrudEntityHelper helper) {
        super(repository, helper);
        this.repository = repository;
    }

    @Override
    public Page<P> readAllByContextIds(@NonNull Collection<String> contextIds,
                                       Pageable page,
                                       ContextInfo contextInfo) {
        return repository.findByContextIdIn(contextIds, page, contextInfo)
                .map(domain -> convertFromPersistentDomain((Trackable) domain, contextInfo));
    }
    
}
