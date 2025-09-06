package com.gosaint.service.inventoryManagement.impl;

import com.gosaint.dao.inventoryManagement.InventoryItemsDao;
import com.gosaint.model.inventoryManagement.InventoryItems;
import com.gosaint.service.inventoryManagement.InventoryItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InventoryItemsServiceImpl implements InventoryItemsService {
    
    @Autowired
    private InventoryItemsDao inventoryItemsDao;

    @Override
    public InventoryItems createInventoryItem(InventoryItems inventoryItem) {
        if (inventoryItem.getItemId() == null) {
            inventoryItem.setItemId(UUID.randomUUID().toString());
        }
        inventoryItemsDao.insert(inventoryItem);
        return inventoryItemsDao.selectById(inventoryItem.getItemId());
    }

    @Override
    public InventoryItems getInventoryItemById(String itemId) {
        return inventoryItemsDao.selectById(itemId);
    }

    @Override
    public List<InventoryItems> getAllInventoryItems() {
        return inventoryItemsDao.selectAll();
    }

    @Override
    public InventoryItems updateInventoryItem(InventoryItems inventoryItem) {
        inventoryItemsDao.update(inventoryItem);
        return inventoryItemsDao.selectById(inventoryItem.getItemId());
    }

    @Override
    public boolean deleteInventoryItem(String itemId) {
        int result = inventoryItemsDao.delete(itemId);
        return result > 0;
    }

    @Override
    public List<InventoryItems> getInventoryItemsByProductId(String productId) {
        return inventoryItemsDao.selectByProductId(productId);
    }

    @Override
    public List<InventoryItems> getInventoryItemsByWarehouseId(String warehouseId) {
        return inventoryItemsDao.selectByWarehouseId(warehouseId);
    }
}