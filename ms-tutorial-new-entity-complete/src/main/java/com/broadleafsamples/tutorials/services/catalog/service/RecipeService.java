package com.broadleafsamples.tutorials.services.catalog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.broadleafcommerce.data.tracking.core.context.ContextInfo;
import com.broadleafcommerce.data.tracking.core.service.RsqlCrudEntityService;
import com.broadleafsamples.tutorials.services.catalog.domain.Recipe;

import java.util.Collection;

public interface RecipeService <P extends Recipe> extends RsqlCrudEntityService<P> {

    @NonNull
    Page<P> readAllByContextIds(@NonNull Collection<String> contextIds,
                                @Nullable Pageable page,
                                @Nullable ContextInfo contextInfo);
    
}
