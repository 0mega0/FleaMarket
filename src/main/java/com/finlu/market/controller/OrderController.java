package com.finlu.market.controller;

import com.finlu.market.annotation.UserLoginToken;
import com.finlu.market.domain.CartRecord;
import com.finlu.market.domain.OrderRecord;
import com.finlu.market.domain.ResultData;
import com.finlu.market.domain.User;
import com.finlu.market.service.OrderService;
import com.finlu.market.service.ProductService;
import com.finlu.market.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/order")
@Slf4j
public class OrderController {
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private HttpServletRequest request;

    @PostMapping("/cart/add")
    @UserLoginToken
    public ResultData addProductToMall(@RequestParam(value = "product_id") Integer productId, Integer count) {
        User user = (User) request.getAttribute("user");
        CartRecord cartRecord = this.orderService.addProductToCart(user.getId(), Long.valueOf(productId), count);
        return ResultData.success(cartRecord);
    }

    @PostMapping("/cart/change-count")
    @UserLoginToken
    public ResultData changeCartProductCount(@RequestParam(value = "cart_id") Integer cartId, Integer count) {
        boolean res = this.orderService.changeCartProductCount(Long.valueOf(cartId), count);
        return res ? ResultData.success() : ResultData.fail();
    }

    @UserLoginToken
    @PostMapping("/cart/remove-all")
    public ResultData removeAllProductsFromCart() {
        User user = (User) request.getAttribute("user");
        boolean b = this.orderService.removeAllProductFromCart(user.getId());
        return b ? ResultData.success() : ResultData.fail();
    }

    @UserLoginToken
    @PostMapping("/cart/remove-one")
    public ResultData removeOneProductsFromCart(@RequestParam(value = "cart_id") Integer cartId) {
        User user = (User) request.getAttribute("user");
        boolean b = this.orderService.removeOneProductFromCart(user.getId(), Long.valueOf(cartId));
        return b ? ResultData.success() : ResultData.fail();
    }

    @GetMapping("/cart/get-all")
    @UserLoginToken
    public ResultData getAllCartProduct() {
        User user = (User) request.getAttribute("user");
        List<CartRecord> allCartRecordByUserId = this.orderService.getAllCartRecordByUserId(user.getId());
        return ResultData.success(allCartRecordByUserId);
    }

    @GetMapping("/record")
    @UserLoginToken
    public ResultData getAllOrderRecord() {
        User user = (User) request.getAttribute("user");
        List<OrderRecord> orderRecords = this.orderService.getAllOrderRecordByUserId(user.getId());
        return ResultData.success(orderRecords);
    }

    @UserLoginToken
    @PostMapping(value = "/buy-one")
    public ResultData buyOneProduct(@RequestParam(name = "product_id") Long productId, @RequestParam(name = "item_count") Integer itemCount) {
        log.info("productId: {}", productId);
        log.info("itemCount: {}", itemCount);
        User user = (User) request.getAttribute("user");
        double totalPrice = this.productService.buyProduct(productId, user.getId(), itemCount);
        if (totalPrice == -1) {
            return ResultData.success("购买失败");
        }
        Map<String, Double> map = new HashMap<>(16);
        map.put("total_price", totalPrice);
        return ResultData.success(map, "购买成功");
    }

    @PostMapping("/updateOrderStatus")
    public ResultData updateOrderStatus(@RequestParam(name = "order_id") Integer orderId, @RequestParam(name = "new_status") Integer newStatus) {
        this.orderService.updateOrderStatus(Long.valueOf(orderId), newStatus);
        return ResultData.success();
    }
}
