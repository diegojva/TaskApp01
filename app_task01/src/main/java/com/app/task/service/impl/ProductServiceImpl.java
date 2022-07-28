package com.app.task.service.impl;

import com.app.task.model.Product;
import com.app.task.repo.IGenericRepo;
import com.app.task.repo.IProductRepo;
import com.app.task.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends CRUDImpl<Product, Integer> implements IProductService {

    @Autowired
    private IProductRepo productRepo;
    @Override
    protected IGenericRepo<Product, Integer> getRepo() {
        return productRepo;
    }
}
