package com.translator.hub.data;

import com.translator.hub.models.Language;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LangRepository extends CrudRepository<Language, Integer> {

    //methods using repository
    public Language findByName (String name);

}
