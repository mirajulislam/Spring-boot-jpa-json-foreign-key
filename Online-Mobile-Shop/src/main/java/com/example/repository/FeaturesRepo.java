/**
 * 
 */
package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Features;

/**
 * @author mirajul.islam
 *
 */
@Repository("featuresRepo")
public interface FeaturesRepo extends JpaRepository<Features, Integer>{

}
