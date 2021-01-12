/**
 * 
 */
package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Model;

/**
 * @author mirajul.islam
 *
 */
@Repository("modelRepo")
public interface ModelRepo extends JpaRepository<Model, Integer>{

}
