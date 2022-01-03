package com.translator.hub.data;

import com.translator.hub.models.Translator;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

@Controller
@Repository("translators")
public interface TranslatorRepository extends CrudRepository <Translator, Integer> {

    public Translator findByEmail(String email);

}
