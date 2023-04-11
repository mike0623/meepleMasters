package tw.com.eeit162.meepleMasters.jack.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;

public interface MemberDao extends JpaRepository<Member, Integer> {

}
