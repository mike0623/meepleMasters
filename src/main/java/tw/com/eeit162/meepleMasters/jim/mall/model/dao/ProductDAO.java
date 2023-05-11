package tw.com.eeit162.meepleMasters.jim.mall.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Product;

public interface ProductDAO extends JpaRepository<Product, Integer> {

	Product findByProductName(String productName);

}
