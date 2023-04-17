package tw.com.eeit162.meepleMasters.jim.mall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Product;
import tw.com.eeit162.meepleMasters.jim.mall.model.dao.ProductDAO;

@Service
public class ProductService {

	@Autowired
	private ProductDAO pDAO;

	public List<Product> findAllProduct() {
		return pDAO.findAll();
	}

	public Product findProductById(Integer id) {
		return pDAO.findById(id).get();
	}

	public void insertProduct(Product product) {
		pDAO.save(product);
	}

	public void deleteProductById(Integer id) {
		pDAO.deleteById(id);
	}
}
