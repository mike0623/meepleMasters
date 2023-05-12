package tw.com.eeit162.meepleMasters.jack.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tw.com.eeit162.meepleMasters.jack.model.bean.CollectLibrary;
import tw.com.eeit162.meepleMasters.jack.model.bean.Member;

public interface CollectLibraryDao extends JpaRepository<CollectLibrary, Integer> {
	
	@Query("FROM CollectLibrary JOIN Member on fk_memberId = memberId JOIN Product on productId = fk_productId WHERE memberId = :memberId")
	List<Object[]> findMemberCollect(@Param(value = "memberId") Integer memberId);
}
