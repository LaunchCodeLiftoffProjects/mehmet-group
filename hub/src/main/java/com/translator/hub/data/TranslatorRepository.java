package com.translator.hub.data;

import com.translator.hub.models.Translator;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslatorRepository extends CrudRepository <Translator, Integer> {
    public Translator findByEmail(String email);
}
