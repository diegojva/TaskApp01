package com.app.task.service.impl;

import com.app.task.model.Product;
import com.app.task.model.Sale;
import com.app.task.model.SaleDetail;
import com.app.task.repo.IGenericRepo;
import com.app.task.repo.IProductRepo;
import com.app.task.repo.ISaleRepo;
import com.app.task.service.ISaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleServiceImpl extends CRUDImpl<Sale, Integer> implements ISaleService {

    @Autowired
    private ISaleRepo saleRepo;

    @Autowired
    private IProductRepo productRepo;

    @Override
    protected IGenericRepo<Sale, Integer> getRepo() {
        return saleRepo;
    }

    @Transactional
    @Override
    public Sale saveSale(Sale sale) {

        Double total = 0.0;
        sale.getDetails().forEach(detail->detail.setSale(sale));

        for(SaleDetail detail : sale.getDetails()){
            Product p = productRepo.findById(detail.getProduct().getIdProduct()).get();
            sale.setTotal(p.getPrice() * detail.getQuantity());
            total+=sale.getTotal();
        }
        sale.setTotal(total);
        saleRepo.save(sale);

        return sale;
    }

}
