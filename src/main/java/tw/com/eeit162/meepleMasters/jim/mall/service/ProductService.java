package tw.com.eeit162.meepleMasters.jim.mall.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.json.JSONException;
import org.json.JSONObject;
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

	public Product insertProduct(String json) {
		try {
			JSONObject object = new JSONObject(json);

			String name = object.isNull("productName") ? null : object.getString("productName");
			Integer price = object.isNull("productPrice") ? null : object.getInt("productPrice");
			String description = object.isNull("productDescription") ? null : object.getString("productDescription");
			String playTime = object.isNull("productPlayTime") ? null : object.getString("productPlayTime");
			Integer maxPlayer = object.isNull("productMaxPlayer") ? null : object.getInt("productMaxPlayer");
			Integer minPlayer = object.isNull("productMinPlayer") ? null : object.getInt("productMinPlayer");
			String Difficulty = object.isNull("productDifficulty") ? null : object.getString("productDifficulty");

//			String img = object.isNull("productImg") ? null : object.getString("productImg");

			Product product = new Product();
			product.setProductName(name);
			product.setProductPrice(price);
			product.setProductDescription(description);
			product.setProductPlayTime(playTime);
			product.setProductMaxPlayer(maxPlayer);
			product.setProductMinPlayer(minPlayer);
			product.setProductDifficulty(Difficulty);

//			product.setProductImg(null);

			return pDAO.save(product);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void deleteProductById(Integer id) {
		pDAO.deleteById(id);
	}

	@Transactional
	public Product updateProductById(String json) {
		try {
			JSONObject object = new JSONObject(json);

			Integer id = object.isNull("productId") ? null : object.getInt("productId");
			String name = object.isNull("productName") ? null : object.getString("productName");
			Integer price = object.isNull("productPrice") ? null : object.getInt("productPrice");
			String description = object.isNull("productDescription") ? null : object.getString("productDescription");
			String playTime = object.isNull("productPlayTime") ? null : object.getString("productPlayTime");
			Integer maxPlayer = object.isNull("productMaxPlayer") ? null : object.getInt("productMaxPlayer");
			Integer minPlayer = object.isNull("productMinPlayer") ? null : object.getInt("productMinPlayer");
			String Difficulty = object.isNull("productDifficulty") ? null : object.getString("productDifficulty");

//			String img = object.isNull("productImg") ? null : object.getString("productImg");
//			String time = object.isNull("addedTime") ? null : object.getString("addedTime");

			Product product = pDAO.findById(id).get();
			product.setProductName(name);
			product.setProductPrice(price);
			product.setProductDescription(description);
			product.setProductPlayTime(playTime);
			product.setProductMaxPlayer(maxPlayer);
			product.setProductMinPlayer(minPlayer);
			product.setProductDifficulty(Difficulty);

//			product.setProductImg(null);
//			product.setAddedTime(null);

			return pDAO.save(product);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
