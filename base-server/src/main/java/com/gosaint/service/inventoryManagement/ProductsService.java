package com.gosaint.service.inventoryManagement;

import com.gosaint.model.inventoryManagement.Products;

import java.util.List;

public interface ProductsService {
    // 创建产品
    Products createProduct(Products product);

    // 根据ID查询产品
    Products getProductById(String productId);

    // 查询所有产品
    List<Products> getAllProducts();

    // 更新产品
    Products updateProduct(Products product);

    // 删除产品
    boolean deleteProduct(String productId);
}