package com.translator.hub.data;

import com.translator.hub.models.Translator;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import java.util.List;

@Controller
@Repository("translators")
public interface TranslatorRepository extends CrudRepository <Translator, Integer> {

    //methods using repository



    public Translator findByEmail(String email);
//    public Translator listByLanguage(String language);
//    public Translator listByCountry(String country);

    List<Translator> findByLanguageContainsIgnoreCase(String language);

    List<Translator> findByAddressIgnoreCase(String address);




}
