package com.broadleafsamples.tutorials.services.catalog.repository;

import org.springframework.stereotype.Repository;

import com.broadleafcommerce.data.tracking.core.BaseTrackableRepository;
import com.broadleafcommerce.data.tracking.core.filtering.Narrow;
import com.broadleafcommerce.data.tracking.jpa.filtering.narrow.JpaNarrowExecutor;
import com.broadleafsamples.tutorials.services.catalog.provider.jpa.domain.JpaRecipe;

@Repository
@Narrow(JpaNarrowExecutor.class)
public interface RecipeRepository extends BaseTrackableRepository<JpaRecipe> {}
