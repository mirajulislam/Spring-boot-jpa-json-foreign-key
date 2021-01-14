/**
 * 
 */
package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.model.GridData;
import com.example.model.Smartphone;

/**
 * @author mirajul.islam
 */
@Repository("smartphoneRepo")
public interface SmartphoneRepo extends JpaRepository<Smartphone, Integer> {

	@Query(value = "SELECT \r\n" + 
			"  sm.b_id\r\n" + 
			"  , sm.brand\r\n" + 
			"  , m.name\r\n" + 
			"  , m.version\r\n" + 
			"  , sm.d_code\r\n" + 
			"  , m.mod_code\r\n" + 
			"  , f.camera\r\n" + 
			"  , f.battary\r\n" + 
			"  , f.capacity\r\n" + 
			"  , f.screen \r\n" + 
			"  FROM smartphone sm\r\n" + 
			"  JOIN model m ON sm.b_id = m.mod_id\r\n" + 
			"  JOIN features f ON sm.b_id = f.fs_id\r\n" + 
			"   WHERE (:#{#smartphone.b_id} is null or sm.b_id = :#{#smartphone.b_id}) \r\n" + 
			"   AND   (:#{#smartphone.brand} is null or sm.brand = :#{#smartphone.brand})\r\n" + 
			"   AND   (:#{#smartphone.d_code} is null or sm.d_code = :#{#smartphone.d_code})", nativeQuery = true)
	public List<GridData> getSearchGridData( @Param("smartphone") Smartphone smartphone);

}
