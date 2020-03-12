package com.broadleafsamples.tutorials.services.catalog.web.endpoint;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.broadleafcommerce.catalog.domain.product.Product;
import com.broadleafcommerce.catalog.service.product.ProductService;
import com.broadleafcommerce.common.extension.data.DataRouteByExample;
import com.broadleafcommerce.data.tracking.core.context.ContextInfo;
import com.broadleafcommerce.data.tracking.core.context.ContextOperation;
import com.broadleafcommerce.data.tracking.core.exception.EntityMissingException;
import com.broadleafcommerce.data.tracking.core.mapping.support.HydrationUtility;
import com.broadleafcommerce.data.tracking.core.policy.Policy;
import com.broadleafcommerce.data.tracking.core.type.OperationType;
import com.broadleafsamples.tutorials.services.catalog.domain.ProductRecipe;
import com.broadleafsamples.tutorials.services.catalog.domain.Recipe;
import com.broadleafsamples.tutorials.services.catalog.service.ProductRecipeService;
import com.broadleafsamples.tutorials.services.catalog.service.RecipeService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import cz.jirutka.rsql.parser.ast.Node;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@DataRouteByExample(Product.class)
public class ProductRecipeEndpoint {

    public static final String CATALOG_SCOPE = "CATALOG";
    public static final String PRODUCT_SCOPE = "PRODUCT";

    @Getter(AccessLevel.PROTECTED)
    private final ProductService<Product> productSvc;

    @Getter(AccessLevel.PROTECTED)
    private final RecipeService<Recipe> recipeService;

    @Getter(AccessLevel.PROTECTED)
    private final ProductRecipeService<ProductRecipe> productRecipeService;

    @GetMapping("/products/{id}/recipes")
    @Policy(permissionRoots = {PRODUCT_SCOPE, CATALOG_SCOPE})
    public Page<ProductRecipe> readProductRecipes(@PathVariable("id") String productId,
                            @PageableDefault(size = 50) Pageable page,
                            @ContextOperation(value = OperationType.READ) ContextInfo contextInfo,
                            Node filters) {
        final Product product = productSvc.readByContextId(productId, contextInfo);
        final Page<ProductRecipe> results = productRecipeService
                .readByProductContextId(productId, filters, page, contextInfo);

        List<String> recipeIdsFromResults =
                results.map(productRecipe -> productRecipe.getRecipe().getId()).getContent();
        Map<String, Recipe> recipes = fetchRecipes(recipeIdsFromResults, contextInfo);

        return results.map(productRecipe -> {
            productRecipe.setProduct(product);
            String recipeId = productRecipe.getRecipe().getId();
            HydrationUtility.hydrateIfNotNull(
                    recipes.get(recipeId),
                    productRecipe::setRecipe,
                    HydrationUtility.getGenericErrorMessage(
                            "ProductRecipe#recipe",
                            "Recipe",
                            productId));
            return productRecipe;
        });
    }

    @PostMapping(value = "/products/{id}/recipes", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Policy(permissionRoots = {PRODUCT_SCOPE, CATALOG_SCOPE})
    public ProductRecipe addProductRecipe(@PathVariable("id") String productId,
                        @RequestBody ProductRecipe productRecipe,
                        @ContextOperation(value = OperationType.CREATE) ContextInfo contextInfo) {

        Product product = productSvc.readByContextId(productId, contextInfo);
        Recipe childRecipe =
                recipeService.readByContextId(productRecipe.getRecipe().getId(), contextInfo);

        productRecipe.setProduct(product);
        ProductRecipe result = productRecipeService.create(productRecipe, contextInfo);

        // hydrate the response
        result.setProduct(product);
        result.setRecipe(childRecipe);
        return result;
    }

    @DeleteMapping("/products/{id}/recipes/{productRecipeId}")
    @Policy(permissionRoots = {PRODUCT_SCOPE, CATALOG_SCOPE})
    public void removeGeneralProduct(@PathVariable("id") String productId,
                         @PathVariable("productRecipeId") String productRecipeId,
                         @ContextOperation(value = OperationType.DELETE) ContextInfo contextInfo) {
        ProductRecipe productRecipe =
                productRecipeService.readByContextId(productRecipeId, contextInfo);
        if (ObjectUtils.notEqual(productId, productRecipe.getProduct().getId())) {
            throw new EntityMissingException();
        }
        productRecipeService.delete(productRecipe.getId(), contextInfo);
    }

    private Map<String, Recipe> fetchRecipes(List<String> benefitIds,
                                               ContextInfo contextInfo) {
        Page<Recipe> recipes =
                recipeService.readAllByContextIds(benefitIds, Pageable.unpaged(), contextInfo);

        return recipes.stream()
                .collect(Collectors.toMap(Recipe::getId, Function.identity()));
    }
}
