package tw.com.eeit162.meepleMasters.jim.mall.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Product;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.ShoppingCart;

public interface ShoppingCartDAO extends JpaRepository<ShoppingCart, Integer> {
	ShoppingCart findByMemberAndProduct(Member m, Product p);

	List<ShoppingCart> findByMember(Member m);

}
