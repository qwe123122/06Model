package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@Controller
public class ProductController {
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService proService;
	
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;

	public ProductController() {
		// TODO Auto-generated constructor stub
	}

	@RequestMapping("/addProduct.do")
	public String addProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Product product=new Product();
		product.setProdName(request.getParameter("prodName"));
		product.setProdDetail(request.getParameter("prodDetail"));
		product.setManuDate(request.getParameter("manuDate").replace("-", ""));
		product.setPrice(Integer.parseInt(request.getParameter("price")));
		product.setFileName(request.getParameter("fileName"));
		
		proService.addProduct(product);
		
		return "redirect:/product/addProduct.jsp";
	}
	
	@RequestMapping("/getProduct.do")
	public String getProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int prodNo=Integer.parseInt(request.getParameter("prodNo"));
		
		Product product=proService.getProduct(prodNo);
		
		Cookie[] cookies = request.getCookies();
	    if (cookies != null && cookies.length > 0) {
	        for (int i = 0 ; i < cookies.length ; i++) {
	        	if (cookies[i].getName().equals("history")) {
	        		String value=cookies[i].getValue()+",";
	        		value+=Integer.toString(prodNo);
	        		cookies[i].setValue(value);
	                response.addCookie(cookies[i]);
				}else {
			    	Cookie cookie = new Cookie("history", Integer.toString(prodNo));
		            response.addCookie(cookie);
			    }
	        }
	    }
		request.setAttribute("product", product);

		return "forward:/product/getProduct.jsp";
	}
	
	/*
	@RequestMapping("/listProduct.do")
	public String listProduct(@ModelAttribute("search") Search search , Model model , HttpServletRequest request) throws Exception {
		System.out.println("리스트프로덕트엑션 시작");
		if(search.getCurrentPage()==0){
			search.setCurrentPage(1);
		}
		System.out.println("search::::::::"+search);
		// web.xml  meta-data 로 부터 상수 추출 
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=proService.getProductList(search);
		
		Page resultPage	= 
					new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("resultPage ::"+resultPage);
		
		// Model 과 View 연결
		model.addAttribute("listProd", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		System.out.println("리스트프로덕트엑션 끝");
		
		return "forward:/product/listProduct.jsp";
	}
	*/
	
	@RequestMapping("/listProduct.do")
	public String listProduct(@ModelAttribute("search") Search search, Model model, HttpServletRequest request ) throws Exception {
		
		System.out.println("/listProduct.do");
		
		if(search.getCurrentPage()==0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		System.out.println(search.getSearchKeyword());

		//Business Logic 수행
		Map<String, Object> map = proService.getProductList(search);
		
		Page resultPage = new Page(search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);

		//Model과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/product/listProduct.jsp";
	}
	
	@RequestMapping("/updateProduct.do")
	public String updateProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String prodNo=(String)request.getParameter("prodNo");
		
		Product product = new Product();
		product.setProdNo(Integer.parseInt(prodNo));
		product.setProdName(request.getParameter("prodName"));
		product.setProdDetail(request.getParameter("prodDetail"));
		product.setManuDate(request.getParameter("manuDate"));
		product.setPrice(Integer.parseInt(request.getParameter("price")));
		product.setFileName(request.getParameter("fileName"));
		
		proService.updateProduct(product);
		System.out.println("ProductController end");
		return "redirect:/getProduct.do?prodNo="+prodNo;
	}
	
	@RequestMapping("/updateProductView.do")
	public String updateProductView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String prodNo=request.getParameter("prodNo");
		
		
		Product product=proService.getProduct(Integer.parseInt(prodNo));
		
		request.setAttribute("product", product);
		
		return "forward:/product/updateProduct.jsp";
	}
}