package tw.com.eeit162.meepleMasters.jim.mall.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.com.eeit162.meepleMasters.jim.mall.model.bean.FavoriteGame;

public interface FavoriteGameDAO extends JpaRepository<FavoriteGame, Integer> {

}
