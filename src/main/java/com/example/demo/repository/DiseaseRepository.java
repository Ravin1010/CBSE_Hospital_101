package com.springRest.DAO;

import com.springRest.enitity.Disease;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DiseaseRepository extends JpaRepository<Disease, Integer>
{
    public Long countById(int id);
}
