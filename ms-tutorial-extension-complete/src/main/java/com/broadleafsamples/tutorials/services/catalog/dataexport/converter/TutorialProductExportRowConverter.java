package com.broadleafsamples.tutorials.services.catalog.dataexport.converter;

import static com.broadleafcommerce.common.extension.reflection.InvocationUtils.withExample;

import com.broadleafcommerce.catalog.dataexport.converter.ProductExportRowConverter;
import com.broadleafcommerce.catalog.dataexport.converter.ToStringConverter;
import com.broadleafcommerce.catalog.dataexport.converter.support.ConversionUtils;
import com.broadleafcommerce.catalog.domain.product.Product;
import com.broadleafsamples.tutorials.services.catalog.provider.jpa.domain.TutorialJpaProduct;

import java.util.LinkedHashSet;
import java.util.Map;

import lombok.NonNull;

public class TutorialProductExportRowConverter extends ProductExportRowConverter {

    public TutorialProductExportRowConverter(@NonNull ToStringConverter<Object> toStringConverter) {
        super(toStringConverter);
    }

    @Override
    public LinkedHashSet<String> getHeaders() {
        LinkedHashSet<String> headers = super.getHeaders();
        headers.add(TutorialFields.MY_PROPERTY);
        return headers;
    }

    @Override
    public Map<String, String> convert(Product source) {
        Map<String, String> result = super.convert(source);
        ConversionUtils.putIfNotNull(TutorialFields.MY_PROPERTY,
                withExample(TutorialJpaProduct.class).andTarget(source).getMyProperty(), result);
        return result;
    }

    public static class TutorialFields extends Fields {
        public static final String MY_PROPERTY = "myProperty";
    }

}
