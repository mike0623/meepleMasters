package tw.com.eeit162.meepleMasters.jim.mall.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Product;
import tw.com.eeit162.meepleMasters.jim.mall.model.dao.ProductDAO;

@Service
public class ProductService {

	@Autowired
	private ProductDAO pDAO;

	public Page<Product> findAllProduct(Integer page, Integer count) {

		PageRequest pageRequest = PageRequest.of(page - 1, count);

		Page<Product> productPage = pDAO.findAll(pageRequest);

		return productPage;
	}

	public Product findProductById(Integer id) {
		Optional<Product> op = pDAO.findById(id);

		if (op.isPresent()) {
			return op.get();
		}
		return null;
	}

	public Product findProductByProductName(String productName) {
		Product product = pDAO.findByProductName(productName);

		if (product != null) {
			return product;
		}
		return null;
	}

	public Product addProduct(Product p) {
		return pDAO.save(p);
	}

	public void deleteProductById(Integer id) {
		pDAO.deleteById(id);
	}

	@Transactional
	public Product updateProductById(Product p) {
		return pDAO.save(p);
	}
}
