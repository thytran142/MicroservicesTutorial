package com.broadleafsamples.tutorials.services.catalog.service;

import org.springframework.stereotype.Service;

import com.broadleafcommerce.common.extension.projection.Projection;
import com.broadleafcommerce.data.tracking.core.BaseTrackableRepository;
import com.broadleafcommerce.data.tracking.core.service.BaseRsqlCrudEntityService;
import com.broadleafcommerce.data.tracking.core.service.RsqlCrudEntityHelper;
import com.broadleafsamples.tutorials.services.catalog.provider.jpa.domain.JpaRecipe;

@Service
public class MyRecipeService extends BaseRsqlCrudEntityService<Projection<JpaRecipe>> {

    public MyRecipeService(BaseTrackableRepository<JpaRecipe> repository,
            RsqlCrudEntityHelper helper) {
        super(repository, helper);
    }

}
