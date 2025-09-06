package com.gosaint.web.inventoryManagement;

import com.gosaint.model.inventoryManagement.Products;
import com.gosaint.service.inventoryManagement.ProductsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "产品管理", description = "产品相关的CRUD操作")
@RestController
@RequestMapping("/api/products")
public class ProductsController {
    
    @Autowired
    private ProductsService productsService;

    @Operation(summary = "创建产品", description = "添加新的产品信息到系统")
    @ApiResponse(responseCode = "200", description = "产品创建成功", content = @Content(schema = @Schema(implementation = Products.class)))
    @PostMapping
    @PreAuthorize("hasAuthority('PRODUCT_MANAGE') or hasRole('ADMIN')")
    public ResponseEntity<Products> createProduct(
            @Parameter(description = "产品对象，包含产品名称、SKU等信息", required = true, schema = @Schema(implementation = Products.class))
            @RequestBody Products product) {
        Products createdProduct = productsService.createProduct(product);
        return ResponseEntity.ok(createdProduct);
    }

    @Operation(summary = "根据ID查询产品", description = "通过产品ID获取产品详细信息")
    @ApiResponse(responseCode = "200", description = "查询成功", content = @Content(schema = @Schema(implementation = Products.class)))
    @ApiResponse(responseCode = "404", description = "产品不存在")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_MANAGE') or hasRole('ADMIN')")
    public ResponseEntity<Products> getProductById(
            @Parameter(description = "产品唯一标识符", required = true) @PathVariable("id") String id) {
        Products product = productsService.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @Operation(summary = "查询所有产品", description = "获取系统中所有产品的列表")
    @ApiResponse(responseCode = "200", description = "查询成功", content = @Content(schema = @Schema(implementation = Products[].class)))
    @GetMapping
    @PreAuthorize("hasAuthority('PRODUCT_MANAGE') or hasRole('ADMIN')")
    public ResponseEntity<List<Products>> getAllProducts() {
        List<Products> products = productsService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "更新产品", description = "更新现有产品的信息")
    @ApiResponse(responseCode = "200", description = "产品更新成功", content = @Content(schema = @Schema(implementation = Products.class)))
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_MANAGE') or hasRole('ADMIN')")
    public ResponseEntity<Products> updateProduct(
            @Parameter(description = "产品唯一标识符", required = true) @PathVariable("id") String id,
            @Parameter(description = "更新后的产品信息", required = true, schema = @Schema(implementation = Products.class)) @RequestBody Products product) {
        product.setProductId(id);
        Products updatedProduct = productsService.updateProduct(product);
        return ResponseEntity.ok(updatedProduct);
    }

    @Operation(summary = "删除产品", description = "从系统中删除指定的产品")
    @ApiResponse(responseCode = "204", description = "产品删除成功")
    @ApiResponse(responseCode = "404", description = "产品不存在")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_MANAGE') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "产品唯一标识符", required = true) @PathVariable("id") String id) {
        boolean deleted = productsService.deleteProduct(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}