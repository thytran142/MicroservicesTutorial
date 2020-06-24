package com.broadleafsamples.tutorials.services.customer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.broadleafcommerce.data.tracking.core.context.ContextInfo;
import com.broadleafcommerce.data.tracking.core.service.BaseRsqlCrudEntityService;
import com.broadleafcommerce.data.tracking.core.service.RsqlCrudEntityHelper;
import com.broadleafsamples.tutorials.services.customer.domain.CustomerMovie;
import com.broadleafsamples.tutorials.services.customer.repository.CustomerMovieRepository;

import cz.jirutka.rsql.parser.ast.Node;

@Service
public class CustomerMovieServiceImpl extends BaseRsqlCrudEntityService<CustomerMovie> implements CustomerMovieService {
    private final CustomerMovieRepository customerMovieRepository;

    public CustomerMovieServiceImpl(RsqlCrudEntityHelper helper, CustomerMovieRepository customerMovieRepository) {
        super(customerMovieRepository, helper);
        this.customerMovieRepository = customerMovieRepository;
    }

    @Override
    public Page<CustomerMovie> readByCustomerContextId(@NonNull String customerContextId,
                                                       Node filters,
                                                       Pageable page,
                                                       ContextInfo contextInfo) {
        return customerMovieRepository.findByCustomerContextId(customerContextId, filters, page, contextInfo)
                .map(domain -> convertFromPersistentDomain(domain, contextInfo));
    }
}
