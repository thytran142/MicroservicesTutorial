package com.broadleafsamples.tutorials.services.metadata.movie;

import com.broadleafcommerce.metadata.domain.Component;
import com.broadleafcommerce.metadata.domain.builder.EntityFormBuilder;

import java.util.Arrays;
import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MovieForms {
    private final MovieFields movieFields;

    protected EntityFormBuilder generalCreateForm() {
        return generalForm("movieCraeteForm");
    }

    protected EntityFormBuilder generalEditForm() {
        return generalForm("movieUpdateForm");
    }

    protected EntityFormBuilder generalForm(String id) {
        EntityFormBuilder form = new EntityFormBuilder(id, "Movies");
        generalFields().forEach(form::addComponent);
        return form;
    }

    protected List<Component> generalFields() {
        return Arrays.asList(
                movieFields.get(MovieFields.TITLE)
                    .order(1000)
                    .build());
    }
}
