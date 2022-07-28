package com.app.task.repo;

import com.app.task.model.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepo extends IGenericRepo<Product, Integer> {


}
