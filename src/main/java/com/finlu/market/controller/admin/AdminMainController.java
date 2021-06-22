package com.finlu.market.controller.admin;

import com.finlu.market.controller.admin.param.UserReq;
import com.finlu.market.controller.param.LoginReq;
import com.finlu.market.domain.Product;
import com.finlu.market.domain.ResultData;
import com.finlu.market.domain.User;
import com.finlu.market.service.AdminService;
import com.finlu.market.service.ProductService;
import com.finlu.market.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/api/admin")
public class AdminMainController {
    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ProductService productService;

    @PostMapping("/product/check")
    public ResultData check(@RequestParam(value = "product_id") Integer productId, @RequestParam Integer status) {
        boolean check = this.adminService.check(Long.valueOf(productId), status);
        return check ? ResultData.success() : ResultData.fail();
    }

    @GetMapping("/product/get-all")
    public ResultData getAll() {
        List<Product> products = this.adminService.getAll();
        return ResultData.success(products);
    }

    @GetMapping("/user/get-all")
    public ResultData getAllUser() {
        List<User> allUser = this.userService.getAllUser();
        return ResultData.success(allUser);
    }

    @PostMapping("/user/update")
    public ResultData updateUser(@RequestBody  UserReq userReq) {
        User user = this.userService.updateUser(userReq);
        return user == null ? ResultData.fail() : ResultData.success(user);
    }

    @PostMapping("/user/del")
    public ResultData delUser(@RequestParam(value = "user_id") Integer userId) {
        return this.userService.delUserById(Long.valueOf(userId)) ? ResultData.success() : ResultData.fail();
    }

}
