package com.broadleafsamples.tutorials.services.catalog.domain.product.commerce;

import com.broadleafcommerce.catalog.domain.product.IncludedProduct;
import com.broadleafcommerce.catalog.domain.product.Product;
import com.broadleafcommerce.catalog.domain.product.commerce.ProductDetails;
import com.broadleafcommerce.catalog.domain.product.option.ProductOption;
import com.broadleafcommerce.common.extension.ResponseView;
import com.broadleafsamples.tutorials.services.catalog.domain.product.TutorialProduct;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.Delegate;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"contextState", "defaultPrice", "msrp", "salePrice"})
@JsonView({ResponseView.class})
public class TutorialProductDetails extends ProductDetails {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @Delegate(excludes = ExcludedProductMethods.class)
    private TutorialProduct product;

    @Override
    public void setProduct(Product product) {
        this.product = (TutorialProduct) product;
        super.setProduct(product);
    }

    @Override
    public TutorialProduct getProduct() {
        return product;
    }

    @Data
    @Accessors(chain = true)
    @AllArgsConstructor
    private abstract static class ExcludedProductMethods {
        private List<IncludedProduct> includedProducts;

        private List<ProductOption> options;

        public abstract String getMetaDescription();

        public abstract String getMetaTitle();
    }


}
