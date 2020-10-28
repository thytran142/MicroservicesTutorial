package com.broadleafsamples.tutorials.services.catalog.dataexport.converter;

import com.broadleafcommerce.catalog.dataexport.converter.DimensionsExportRowConverter;
import com.broadleafcommerce.catalog.dataexport.converter.ProductExportRowConverter;
import com.broadleafcommerce.catalog.dataexport.converter.ToStringConverter;
import com.broadleafcommerce.catalog.dataexport.converter.WeightExportRowConverter;
import com.broadleafcommerce.catalog.dataexport.converter.support.ConversionUtils;
import com.broadleafcommerce.catalog.dataexport.specification.ProductExportSpecification;
import com.broadleafcommerce.catalog.domain.product.Product;
import com.broadleafsamples.tutorials.services.catalog.provider.jpa.domain.TutorialJpaProduct;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedHashSet;
import java.util.Map;

import static com.broadleafcommerce.common.extension.reflection.InvocationUtils.withExample;

public class TutorialProductExportRowConverter extends ProductExportRowConverter {


    public TutorialProductExportRowConverter(ProductExportSpecification specification,
                                             ObjectMapper objectMapper,
                                             ToStringConverter<Object> toStringConverter,
                                             DimensionsExportRowConverter dimensionsExportRowConverter,
                                             WeightExportRowConverter weightExportRowConverter) {
        super(specification, objectMapper, toStringConverter, dimensionsExportRowConverter, weightExportRowConverter);
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

    public static class TutorialFields {
        public static final String MY_PROPERTY = "myProperty";
    }

}
