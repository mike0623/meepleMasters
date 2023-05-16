package tw.com.eeit162.meepleMasters.jack.model.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;

public interface MemberDao extends JpaRepository<Member, Integer> {
	
	@Query(value="from Member where memberEmail = :memberEmail" )
	Member findMemberByEmail(@Param(value="memberEmail") String memberEmail);
	
	@Query(value="from Member where memberEmail = :memberEmail and memberPwd = :memberPwd")
	Member findMemberByEmailandPassword(String memberEmail, String memberPwd);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value="update Member set memberPwd = :memberPwd where memberEmail = :memberEmail")
	Integer updatePasswordByEmail(@Param(value = "memberEmail") String memberEmail, @Param(value = "memberPwd") String memberPwd); 
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value="update Member set memberName = :memberName, memberBirth = :memberBirth, memberGender = :memberGender, memberTel = :memberTel, memberAddress = :memberAddress where memberId = :memberId")
	Integer updateMemberById(@Param(value = "memberId") Integer memberId, @Param(value = "memberName") String memberName, 
			@Param(value = "memberBirth") Date memberBirth, @Param(value = "memberGender") String memberGender, 
			@Param(value = "memberTel") String memberTel, @Param(value = "memberAddress") String memberAddress);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value="update Member set memberImg = :memberImg where memberId = :memberId")
	Integer updateImgById(@Param(value = "memberId") Integer memberId, @Param(value = "memberImg") byte[] memberImg);
	
	@Query(value="from Member where memberName like %:memberName%")
	List<Member> findMemberByName(@Param(value = "memberName") String memberName);
	
	//Card
	@Modifying(clearAutomatically = true)
	@Query(value="update Member set memberCoin = memberCoin+ :coin where memberId = :memberId")
	Integer updateMemberCoin(@Param(value = "memberId") Integer memberId, @Param(value = "coin") Integer coin);
	
}
