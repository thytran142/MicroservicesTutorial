package com.broadleafsamples.tutorials.services.catalog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import com.broadleafcommerce.data.tracking.core.Trackable;
import com.broadleafcommerce.data.tracking.core.TrackableRepository;
import com.broadleafcommerce.data.tracking.core.TrackableRsqlFilterExecutor;
import com.broadleafcommerce.data.tracking.core.context.ContextInfo;
import com.broadleafcommerce.data.tracking.core.filtering.Narrow;
import com.broadleafcommerce.data.tracking.core.policy.Policy;
import com.broadleafcommerce.data.tracking.core.type.OperationType;
import com.broadleafcommerce.data.tracking.jpa.filtering.narrow.JpaNarrowExecutor;
import com.broadleafsamples.tutorials.services.catalog.provider.jpa.domain.JpaRecipe;

import java.util.Collection;

@Repository
@Narrow(JpaNarrowExecutor.class)
public interface RecipeRepository<D extends JpaRecipe & Trackable> extends TrackableRepository<D>,
        TrackableRsqlFilterExecutor<D> {

    @Policy(operationTypes = OperationType.READ)
    @NonNull
    Page<D> findByContextIdIn(@NonNull Collection<String> contextIds,
                              @Nullable Pageable page,
                              @Nullable ContextInfo contextInfo);
    
}
