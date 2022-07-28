package com.app.task.repo;

import com.app.task.model.Person;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonRepo extends IGenericRepo<Person, Integer> {


}
