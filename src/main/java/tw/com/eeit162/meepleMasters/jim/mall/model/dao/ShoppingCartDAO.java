package tw.com.eeit162.meepleMasters.jim.mall.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.com.eeit162.meepleMasters.jim.mall.model.bean.ShoppingCart;

public interface ShoppingCartDAO extends JpaRepository<ShoppingCart, Integer> {
	ShoppingCart findByFkMemberIdAndFkProductId(Integer memberId, Integer productId);
}
