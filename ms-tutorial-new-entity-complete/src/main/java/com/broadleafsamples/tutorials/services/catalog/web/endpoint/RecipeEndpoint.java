package com.broadleafsamples.tutorials.services.catalog.web.endpoint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.broadleafcommerce.catalog.domain.product.Product;
import com.broadleafcommerce.common.extension.RequestView;
import com.broadleafcommerce.common.extension.data.DataRouteByExample;
import com.broadleafcommerce.common.extension.projection.Projection;
import com.broadleafcommerce.data.tracking.core.context.ContextInfo;
import com.broadleafcommerce.data.tracking.core.context.ContextOperation;
import com.broadleafcommerce.data.tracking.core.policy.Policy;
import com.broadleafcommerce.data.tracking.core.type.OperationType;
import com.broadleafsamples.tutorials.services.catalog.provider.jpa.domain.JpaRecipe;
import com.broadleafsamples.tutorials.services.catalog.service.MyRecipeService;
import com.fasterxml.jackson.annotation.JsonView;

import javax.servlet.http.HttpServletRequest;

import cz.jirutka.rsql.parser.ast.Node;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@RestController
@AllArgsConstructor
@DataRouteByExample(Product.class)
public class RecipeEndpoint {

    public static final String RECIPE_SCOPE = "PRODUCT";

    @Getter(AccessLevel.PROTECTED)
    private final MyRecipeService recipeService;

    @GetMapping("/recipes")
    @Policy(permissionRoots = {RECIPE_SCOPE})
    public Page<Projection<JpaRecipe>> readAllRecipes(HttpServletRequest request,
            @ContextOperation(value = OperationType.READ) ContextInfo context,
            @RequestParam(value = "q", required = false) String query,
            @PageableDefault(size = 50) Pageable page,
            Node filters) {
        return recipeService.readAll(filters, page, context);
    }

    @PostMapping(value = "/recipes", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Policy(permissionRoots = {RECIPE_SCOPE})
    public Projection<JpaRecipe> createRecipe(HttpServletRequest request,
            @ContextOperation(value = OperationType.CREATE) ContextInfo context,
            @RequestBody Projection<JpaRecipe> req) {
        return recipeService.create(req, context);
    }

    @GetMapping("/recipes/{id}")
    @Policy(permissionRoots = {RECIPE_SCOPE})
    public Projection<JpaRecipe> readRecipeById(HttpServletRequest request,
            @ContextOperation ContextInfo context,
            @PathVariable("id") String id) {
        return recipeService.readByContextId(id, context);
    }

    @PatchMapping(value = "/recipes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Policy(permissionRoots = {RECIPE_SCOPE})
    public Projection<JpaRecipe> updateRecipe(HttpServletRequest request,
            @ContextOperation(value = OperationType.UPDATE) ContextInfo context,
            @PathVariable("id") String id,
            @JsonView(RequestView.class) @RequestBody Projection<JpaRecipe> req) {
        return recipeService.update(id, req, context);
    }

    @PutMapping(value = "/recipes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Policy(permissionRoots = {RECIPE_SCOPE})
    public Projection<JpaRecipe> replaceRecipe(HttpServletRequest request,
            @ContextOperation(value = OperationType.UPDATE) ContextInfo context,
            @PathVariable("id") String id,
            @JsonView(RequestView.class) @RequestBody Projection<JpaRecipe> req) {
        req.setId(id);
        return recipeService.replace(id, req, context);
    }

    @DeleteMapping(value = "/recipes/{id}")
    @Policy(permissionRoots = {RECIPE_SCOPE})
    public void deleteRecipe(HttpServletRequest request,
            @ContextOperation(value = OperationType.DELETE) ContextInfo context,
            @PathVariable("id") String id) {
        recipeService.delete(id, context);
    }

}
