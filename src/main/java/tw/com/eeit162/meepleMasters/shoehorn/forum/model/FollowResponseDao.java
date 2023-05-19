package tw.com.eeit162.meepleMasters.shoehorn.forum.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowResponseDao extends JpaRepository<FollowResponse, Integer> {

	List<FollowResponse> findByFkMemberId(Integer fkMemberId);
	
	List<FollowResponse> findByFkResponseId(Integer fkMemberId);
	
}
