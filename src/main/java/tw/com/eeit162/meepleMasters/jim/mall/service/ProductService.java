package tw.com.eeit162.meepleMasters.jim.mall.service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.jack.model.dao.CollectLibraryDao;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.FavoriteGame;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Product;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.ShoppingCart;
import tw.com.eeit162.meepleMasters.jim.mall.model.dao.FavoriteGameDAO;
import tw.com.eeit162.meepleMasters.jim.mall.model.dao.ProductDAO;
import tw.com.eeit162.meepleMasters.jim.mall.model.dao.ShoppingCartDAO;
import tw.com.eeit162.meepleMasters.jim.mall.model.dto.ProductDTO;

@Service
public class ProductService {

	@Autowired
	private ProductDAO pDAO;

	@Autowired
	private ShoppingCartDAO scDAO;

	@Autowired
	private FavoriteGameDAO fgDAO;

	@Autowired
	private CollectLibraryDao clDAO;

	public PageImpl<ProductDTO> multiConditionQuery(Integer page, Integer count, String productContent, Integer mID) {

		List<Product> pList = pDAO.findAll();

		JSONObject jsonProduct = new JSONObject(productContent);

		List<Product> collect = pList.stream()
				.filter(p -> p.getProductPlayTime().contains(jsonProduct.getString("productpPlayTime")))
				.filter(p -> p.getProductDifficulty().contains(jsonProduct.getString("productDifficulty")))
				.filter(p -> p.getProductPrice() >= jsonProduct.getInt("productMinPrice")
						&& p.getProductPrice() <= jsonProduct.getInt("productMaxPrice"))
				.collect(Collectors.toList());

		List<ProductDTO> productDTOs = convertProductToDTO(mID, collect);

		int originSize = productDTOs.size();

		if (productDTOs.size() > count) {
			productDTOs = productDTOs.subList((page - 1) * count, (page - 1) * count + 6);
		}

		PageImpl<ProductDTO> filterPage = new PageImpl<>(productDTOs, PageRequest.ofSize(count), originSize);

		return filterPage;
	}

	public PageImpl<ProductDTO> findAllProduct(Integer page, Integer count, Integer mID) {
		PageRequest pageRequest = PageRequest.of(page - 1, count);

		Page<Product> productPerPage = pDAO.findAll(pageRequest);

		List<Product> content = productPerPage.getContent();
		List<ProductDTO> collect = convertProductToDTO(mID, content);
		PageImpl<ProductDTO> pageImpl = new PageImpl<>(collect, productPerPage.getPageable(),
				productPerPage.getTotalElements());
		return pageImpl;
	}

	private List<ProductDTO> convertProductToDTO(Integer mID, List<Product> content) {
		List<ProductDTO> collect = content.stream().map(p -> {
			ProductDTO pDTO = new ProductDTO(p);
			if (mID != null) {
				List<ShoppingCart> currentMemberShoppingCarts = scDAO.findByMember(new Member(mID));
				for (ShoppingCart sc : currentMemberShoppingCarts) {
					if (sc.getProduct().equals(p)) {
						pDTO.setIsInCart(true);
						break;
					}
				}
				List<FavoriteGame> currentMemberFavoriteGames = fgDAO.findByMember(new Member(mID));
				for (FavoriteGame fg : currentMemberFavoriteGames) {
					if (fg.getProduct().equals(p)) {
						pDTO.setIsFavorite(true);
						break;
					}
				}
				List<Object[]> findMemberCollect = clDAO.findMemberCollect(mID);
				List<Product> currentMemberCollects = new ArrayList<>();
				for (Object[] cl : findMemberCollect) {
					Product product = new Product();
					product = (Product) cl[2];
					currentMemberCollects.add(product);
				}
				for (Product clp : currentMemberCollects) {
					if (clp.equals(p)) {
						pDTO.setIsInLibrary(true);
						break;
					}
				}
			}
			return pDTO;
		}).collect(Collectors.toList());
		return collect;
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

	public String deleteProductById(Integer id) {
		Product product = pDAO.findById(id).get();
		if (product != null) {
			pDAO.deleteById(id);
			return "刪除成功";
		}
		return "刪除失敗";
	}

	@Transactional
	public Product updateProductById(Product p, MultipartFile pImg) {

		Optional<Product> op = pDAO.findById(p.getProductId());

		if (op.isPresent()) {
			Product product = op.get();
			product.setProductDescription(p.getProductDescription());
			product.setProductDifficulty(p.getProductDifficulty());
			product.setProductMaxPlayer(p.getProductMaxPlayer());
			product.setProductMinPlayer(p.getProductMinPlayer());
			product.setProductName(p.getProductName());
			product.setProductPlayTime(p.getProductPlayTime());
			product.setProductPrice(p.getProductPrice());
			if (!pImg.isEmpty()) {
				try {
					BufferedInputStream bis = new BufferedInputStream(pImg.getInputStream());
					product.setProductImg(bis.readAllBytes());
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return product;
		}
		return null;
	}

}
