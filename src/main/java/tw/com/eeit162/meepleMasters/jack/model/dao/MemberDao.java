package tw.com.eeit162.meepleMasters.jack.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;

public interface MemberDao extends JpaRepository<Member, Integer> {
	
	@Query(value="from Member where memberEmail = :memberEmail" )
	Member findMemberByEmail(@Param(value="memberEmail") String memberEmail);
}
