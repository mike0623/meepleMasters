package tw.com.eeit162.meepleMasters.jim.mall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.eeit162.meepleMasters.jim.mall.model.dao.ProductDAO;

@Service
public class ProductService {

	@Autowired
	private ProductDAO pDAO;

}
