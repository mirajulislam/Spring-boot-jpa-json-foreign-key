/**
 * 
 */
package com.example.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Smartphone;

/**
 * @author mirajul.islam
 *
 */
@Repository("smartphoneRepo")
public interface SmartphoneRepo extends JpaRepository<Smartphone, Integer>{

}
