package com.app.task.service;

import com.app.task.model.Sale;

import java.util.List;

public interface ISaleService extends ICRUD<Sale, Integer> {

    Sale saveSale(Sale sale);

}
