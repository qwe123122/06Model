package com.model2.mvc.service.product.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;


//==> productDaoImpl S
@Repository("productDaoImpl")
public abstract class ProductDaoImpl implements ProductDao{
	
	///Field
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	///Constructor
	public ProductDaoImpl() {
		System.out.println(" ProductDaoImpl_start ");
	}

	public void insertProdct(Product product) throws Exception {
		sqlSession.insert("ProductMapper.addProduct", product);
	}

	public Product getProduct(int prodNo) throws SQLException {
		return sqlSession.selectOne("ProductMapper.getProduct", prodNo);
	}

	
	public void updateProduct(Product product) throws Exception {
		sqlSession.update("ProductMapper.updateProduct", product);
	}

	
	public HashMap<String, Object> getProductList(Search search) throws SQLException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<Product> list = sqlSession.selectList("ProductMapper.getProductList", search);
		map.put("list", list);
		return map;
	}
	
	
}