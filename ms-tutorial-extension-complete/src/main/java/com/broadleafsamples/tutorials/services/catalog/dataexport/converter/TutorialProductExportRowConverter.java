package com.broadleafsamples.tutorials.services.catalog.dataexport.converter;

import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import com.broadleafcommerce.catalog.dataexport.converter.ProductExportRowConverter;
import com.broadleafcommerce.catalog.dataexport.converter.ToStringConverter;
import com.broadleafcommerce.catalog.dataexport.converter.support.ConversionUtils;
import com.broadleafcommerce.catalog.domain.product.Product;

import java.lang.reflect.Method;
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
        Method getMyProperty = ReflectionUtils.findMethod(source.getClass(), "getMyProperty");
        Assert.notNull(getMyProperty, "Expected a getMyProperty method");
        ConversionUtils.putIfNotNull(TutorialFields.MY_PROPERTY,
                (String) ReflectionUtils.invokeMethod(getMyProperty, source), result);
        return result;
    }

    public static class TutorialFields extends Fields {
        public static final String MY_PROPERTY = "myProperty";
    }

}
