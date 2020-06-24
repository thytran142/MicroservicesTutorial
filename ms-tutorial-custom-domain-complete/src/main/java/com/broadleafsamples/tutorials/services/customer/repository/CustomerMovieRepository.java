package com.broadleafsamples.tutorials.services.customer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import com.broadleafcommerce.data.tracking.core.TrackableRepository;
import com.broadleafcommerce.data.tracking.core.TrackableRsqlFilterExecutor;
import com.broadleafcommerce.data.tracking.core.context.ContextInfo;
import com.broadleafcommerce.data.tracking.core.filtering.Narrow;
import com.broadleafcommerce.data.tracking.core.policy.Policy;
import com.broadleafcommerce.data.tracking.core.type.OperationType;
import com.broadleafcommerce.data.tracking.jpa.filtering.narrow.JpaNarrowExecutor;
import com.broadleafsamples.tutorials.services.customer.provider.jpa.domain.JpaCustomerMovie;

import cz.jirutka.rsql.parser.ast.Node;

@Repository
@Narrow((JpaNarrowExecutor.class))
public interface CustomerMovieRepository
        extends TrackableRepository<JpaCustomerMovie>,
        TrackableRsqlFilterExecutor<JpaCustomerMovie> {

    @Policy(operationTypes = OperationType.READ)
    @NonNull
    Page<JpaCustomerMovie> findByCustomerContextId(@NonNull String customerContextId,
                                                   Node filters,
                                                   Pageable page,
                                                   @Nullable ContextInfo contextinfo);
}
