package tw.com.eeit162.meepleMasters.jim.mall.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.FavoriteGame;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Product;

public interface FavoriteGameDAO extends JpaRepository<FavoriteGame, Integer> {

	List<FavoriteGame> findByMember(Member m);

	FavoriteGame findByMemberAndProduct(Member m, Product p);
}
