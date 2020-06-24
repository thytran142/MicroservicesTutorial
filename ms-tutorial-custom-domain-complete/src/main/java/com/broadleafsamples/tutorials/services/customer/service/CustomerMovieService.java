package com.broadleafsamples.tutorials.services.customer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import com.broadleafcommerce.data.tracking.core.context.ContextInfo;
import com.broadleafcommerce.data.tracking.core.service.RsqlCrudEntityService;
import com.broadleafsamples.tutorials.services.customer.domain.CustomerMovie;

import cz.jirutka.rsql.parser.ast.Node;

public interface CustomerMovieService extends RsqlCrudEntityService<CustomerMovie> {
    Page<CustomerMovie> readByCustomerContextId(@NonNull String customerContextId,
                                                Node filters,
                                                Pageable page,
                                                ContextInfo contextInfo);
}
