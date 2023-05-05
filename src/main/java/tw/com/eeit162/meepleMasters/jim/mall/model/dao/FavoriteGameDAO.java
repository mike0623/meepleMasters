package tw.com.eeit162.meepleMasters.jim.mall.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.com.eeit162.meepleMasters.jim.mall.model.bean.FavoriteGame;

public interface FavoriteGameDAO extends JpaRepository<FavoriteGame, Integer> {

	List<FavoriteGame> findByFkMemberId(Integer memberId);

	FavoriteGame findByFkMemberIdAndFkProductId(Integer memberId, Integer productId);
}
