package com.gosaint.service.inventoryManagement.impl;

import com.gosaint.dao.inventoryManagement.ProductsDao;
import com.gosaint.model.inventoryManagement.Products;
import com.gosaint.service.inventoryManagement.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductsServiceImpl implements ProductsService {
    
    @Autowired
    private ProductsDao productsDao;

    @Override
    public Products createProduct(Products product) {
        if (product.getProductId() == null) {
            product.setProductId(UUID.randomUUID().toString());
        }
        productsDao.insert(product);
        return productsDao.selectById(product.getProductId());
    }

    @Override
    public Products getProductById(String productId) {
        return productsDao.selectById(productId);
    }

    @Override
    public List<Products> getAllProducts() {
        return productsDao.selectAll();
    }

    @Override
    public Products updateProduct(Products product) {
        productsDao.update(product);
        return productsDao.selectById(product.getProductId());
    }

    @Override
    public boolean deleteProduct(String productId) {
        int result = productsDao.delete(productId);
        return result > 0;
    }
}