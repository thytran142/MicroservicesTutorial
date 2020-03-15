package com.broadleafsamples.tutorials.services.catalog.service;

import org.springframework.stereotype.Service;

import com.broadleafcommerce.common.extension.projection.Projection;
import com.broadleafcommerce.data.tracking.core.service.BaseRsqlCrudEntityService;
import com.broadleafcommerce.data.tracking.core.service.RsqlCrudEntityHelper;
import com.broadleafsamples.tutorials.services.catalog.provider.jpa.domain.JpaRecipe;
import com.broadleafsamples.tutorials.services.catalog.repository.RecipeRepository;

@Service
public class MyRecipeService extends BaseRsqlCrudEntityService<Projection<JpaRecipe>> {

    private final RecipeRepository repository;

    public MyRecipeService(RecipeRepository repository,
            RsqlCrudEntityHelper helper) {
        super(repository, helper);
        this.repository = repository;
    }

}
