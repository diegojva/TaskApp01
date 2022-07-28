package com.app.task.service.impl;

import com.app.task.model.Person;
import com.app.task.repo.IGenericRepo;
import com.app.task.repo.IPersonRepo;
import com.app.task.repo.IProductRepo;
import com.app.task.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl extends CRUDImpl<Person, Integer> implements IPersonService {

    @Autowired
    private IPersonRepo personRepo;
    @Override
    protected IGenericRepo<Person, Integer> getRepo() {
        return personRepo;
    }

}
