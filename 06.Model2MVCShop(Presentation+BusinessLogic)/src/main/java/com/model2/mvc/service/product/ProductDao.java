package com.model2.mvc.service.product;

import java.util.Map;
import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;


public interface ProductDao {
	
	// INSERT
	public void insertProduct(Product product) throws Exception;
	
	// SELECT ONE
	public Product findProduct(int prodNo) throws Exception;
	
	// SELECT LIST
	public Map<String, Object> getProductList(Search search) throws Exception;
	
	// UPDATE
	public void updateProduct(Product product) throws Exception;
	
	// Row(totalCount)  return
	public int getTotalCount(Search search) throws Exception;
	
}