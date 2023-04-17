package tw.com.eeit162.meepleMasters.jim.mall.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Product;
import tw.com.eeit162.meepleMasters.jim.mall.model.dao.ProductDAO;

@Service
public class ProductService {

	@Autowired
	private ProductDAO pDAO;

	public List<Product> findAllProduct(Integer page, Integer count) {

		PageRequest pageRequest = PageRequest.of(page - 1, count, Sort.Direction.ASC, "productId");

		Page<Product> productPage = pDAO.findAll(pageRequest);

		return productPage.getContent();
	}

	public Product findProductById(Integer id) {
		Optional<Product> op = pDAO.findById(id);

		if (op.isPresent()) {
			return op.get();
		}
		return null;
	}

	public void insertProduct(Product product) {
		pDAO.save(product);
	}

	public void deleteProductById(Integer id) {
		pDAO.deleteById(id);
	}

	@Transactional
	public Product updateProductById(Product product) {

		Optional<Product> op = pDAO.findById(product.getProductId());

		if (op.isPresent()) {
			Product newProduct = op.get();

			newProduct.setProductName(product.getProductName());
			newProduct.setProductDescription(product.getProductDescription());
			newProduct.setProductDifficulty(product.getProductDifficulty());
			newProduct.setProductMaxPlayer(product.getProductMaxPlayer());
			newProduct.setProductMinPlayer(product.getProductMinPlayer());
			newProduct.setProductPlayTime(product.getProductPlayTime());
			newProduct.setProductPrice(product.getProductPrice());

			newProduct.setProductImg(product.getProductImg());
			newProduct.setAddedTime(product.getAddedTime());

			return newProduct;
		}
		return null;
	}
}
