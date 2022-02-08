package com.translator.hub.data;

import com.translator.hub.models.Testimonial;
import org.aspectj.weaver.ast.Test;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestimonialRepository extends CrudRepository<Testimonial, Integer> {
    List<Testimonial> findByApprovedTrue();
}
