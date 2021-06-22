package com.finlu.market.controller;

import com.finlu.market.controller.param.ProductReq;
import com.finlu.market.domain.Product;
import com.finlu.market.domain.ResultData;
import com.finlu.market.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RequestMapping("/api/product")
@RestController
@Slf4j
public class ProductController {
    @Autowired
    private ProductService productService;


    @GetMapping("/list")
    public ResultData getAllProduct() {
        return ResultData.success(this.productService.getAllCheckedProducts());
    }

    @GetMapping("/")
    public ResultData getOneProduct(@RequestParam Integer productId) {
        return ResultData.success(this.productService.findProductById(Long.valueOf(productId)));
    }


    /**
     * 新增一个商品
     *
     * @param productReq {PublishReq}
     * @return {ResultData}
     */
    @PostMapping("/add")
    public ResultData addProduct(@RequestBody  ProductReq productReq) {
        log.info("productReq: {}", productReq);
        Product product = this.productService.createProduct(productReq);
        return ResultData.success(product);
    }
}