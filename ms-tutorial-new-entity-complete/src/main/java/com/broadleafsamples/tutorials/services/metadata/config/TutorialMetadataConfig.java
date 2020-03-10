package com.broadleafsamples.tutorials.services.metadata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.broadleafcommerce.catalog.metadata.product.CommonPriceDataComponents;
import com.broadleafcommerce.catalog.metadata.product.IncludedProductFields;
import com.broadleafcommerce.catalog.metadata.product.NonSkuPriceDataComponents;
import com.broadleafcommerce.catalog.metadata.product.ProductEditViewContributor;
import com.broadleafcommerce.catalog.metadata.product.ProductFields;
import com.broadleafcommerce.catalog.metadata.product.ProductForms;
import com.broadleafcommerce.catalog.metadata.product.ProductOptionFields;
import com.broadleafcommerce.catalog.metadata.product.ProductOptionForms;
import com.broadleafcommerce.catalog.metadata.product.PromotionalProductFields;
import com.broadleafcommerce.catalog.metadata.product.VariantFields;
import com.broadleafcommerce.catalog.metadata.product.pricing.PriceDataFields;
import com.broadleafcommerce.metadata.domain.Endpoint;
import com.broadleafcommerce.metadata.domain.FieldComponent;
import com.broadleafcommerce.metadata.domain.OperationType;
import com.broadleafcommerce.metadata.domain.builder.EntityFormBuilder;
import com.broadleafcommerce.metadata.domain.builder.ExternalGridBuilder;
import com.broadleafcommerce.metadata.domain.builder.FieldGroupBuilder;
import com.broadleafcommerce.metadata.domain.type.EndpointType;
import com.broadleafcommerce.metadata.route.ComponentRouteLocator;
import com.broadleafcommerce.metadata.route.builder.ComponentRouteLocatorBuilder;
import com.broadleafsamples.tutorials.services.metadata.recipe.RecipeBrowseViewContributor;
import com.broadleafsamples.tutorials.services.metadata.recipe.RecipeCreateViewContributor;
import com.broadleafsamples.tutorials.services.metadata.recipe.RecipeEditViewContributor;
import com.broadleafsamples.tutorials.services.metadata.recipe.RecipeFields;
import com.broadleafsamples.tutorials.services.metadata.recipe.RecipeForms;

import java.util.Collections;

@Configuration
public class TutorialMetadataConfig {

    public static final String RECIPE_SCOPE = "PRODUCT";

    @Bean
    public ComponentRouteLocator recipeRoutes() {
        return ComponentRouteLocatorBuilder.routes()
                .route("/recipes",
                        r -> r.componentId(RecipeBrowseViewContributor.ID)
                                .scope(RECIPE_SCOPE))
                .route("/recipes/create",
                        r -> r.componentId(RecipeCreateViewContributor.ID)
                                .scope(RECIPE_SCOPE))
                .route("/recipes/:id",
                        r -> r.componentId(RecipeEditViewContributor.ID)
                                .scope(RECIPE_SCOPE))
                .build();
    }

    @Bean
    public RecipeBrowseViewContributor recipeBrowseView(RecipeFields recipeFields) {
        return new RecipeBrowseViewContributor(recipeFields);
    }

    @Bean
    public RecipeCreateViewContributor recipeCreateView(RecipeForms recipeForms) {
        return new RecipeCreateViewContributor(recipeForms);
    }

    @Bean
    public RecipeEditViewContributor recipeEditView(RecipeForms recipeForms) {
        return new RecipeEditViewContributor(recipeForms);
    }

    @Bean
    public RecipeForms recipeForms(RecipeFields recipeFields) {
        return new RecipeForms(recipeFields);
    }

    @Bean
    public RecipeFields recipeFields() {
        return new RecipeFields();
    }

    @Bean
    @Primary
    public ProductEditViewContributor productEditView(ProductForms productForms,
                                  ProductFields productFields,
                                  ProductOptionFields productOptionFields,
                                  VariantFields variantFields,
                                  PromotionalProductFields promotionalProductFields,
                                  IncludedProductFields includedProductFields,
                                  PriceDataFields priceDataFields,
                                  CommonPriceDataComponents commonPriceDataComponents,
                                  ProductOptionForms optionForms,
                                  NonSkuPriceDataComponents nonSkuPriceDataComponents) {

        TutorialProductForms tutorialProductForms = new TutorialProductForms(productFields,
                productOptionFields,
                variantFields,
                promotionalProductFields,
                includedProductFields,
                priceDataFields,
                commonPriceDataComponents,
                optionForms,
                nonSkuPriceDataComponents);

        return new ProductEditViewContributor(tutorialProductForms, productFields);
    }

    class TutorialProductForms extends ProductForms {

        public TutorialProductForms(ProductFields productFields,
                                    ProductOptionFields productOptionFields,
                                    VariantFields variantFields,
                                    PromotionalProductFields promotionalProductFields,
                                    IncludedProductFields includedProductFields,
                                    PriceDataFields priceDataFields,
                                    CommonPriceDataComponents commonPriceDataComponents,
                                    ProductOptionForms optionForms,
                                    NonSkuPriceDataComponents nonSkuPriceDataComponents) {
            super(productFields, productOptionFields, variantFields, promotionalProductFields,
                    includedProductFields, priceDataFields, commonPriceDataComponents, optionForms,
                    nonSkuPriceDataComponents);
        }

        @Override
        protected EntityFormBuilder generalForm() {
            return super.generalForm()
                    .addGroup(new FieldGroupBuilder("Recipes")
                            .id("productRecipesFieldsGroup")
                            .addComponent(recipes().build()));
        }
    }

    public Endpoint.EndpointBuilder createProductRecipeEndpoint() {
        return Endpoint.builder(EndpointType.CREATE)
                .uri("/catalog/products/${parent.id}/recipes")
                .method(Endpoint.Method.POST)
                .operationType(OperationType.CREATE)
                .scope(RECIPE_SCOPE);
    }

    public ExternalGridBuilder recipes() {
        return new ExternalGridBuilder("Recipes",
                RECIPE_SCOPE,
                "/catalog/products/${parent.id}/recipes")
                .id("recipesExternalGrid")
                .sandboxDiscriminated("PRODUCT_RECIPES")
                .catalogDiscriminated()
                .enableNarrowPaging()
                .order(1000)
                .addField(FieldComponent.builder("recipe.name")
                        .label("Name")
                        .order(1000)
                        .build())
                .addField(FieldComponent.builder("recipe.description")
                        .label("Description")
                        .order(2000)
                        .build())
                .enableCreate("Add Recipe",
                        createProductRecipeEndpoint().build(),
                        Collections.singletonList(
                                recipeFields().get(RecipeFields.RECIPE)
                                        .order(1000)
                                        .build()))
                .enableDelete("Remove",
                        "/catalog/products/${parent.id}/recipes/${row.id}");

    }

}
