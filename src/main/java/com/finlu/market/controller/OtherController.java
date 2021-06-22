package com.finlu.market.controller;

import com.alibaba.fastjson.JSONObject;
import com.finlu.market.annotation.UserLoginToken;
import com.finlu.market.domain.ResultData;
import com.finlu.market.domain.User;
import com.finlu.market.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/api")
@Slf4j
public class OtherController {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ProductService productService;

    @RequestMapping("/upload")
    @ResponseBody
    public ResultData upload(@RequestParam MultipartFile file) throws IOException {
        // 获取原先文件的文件名
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        int split = fileName.lastIndexOf(".");
        String suffix = fileName.substring(split + 1, fileName.length());
        String url = "";
        if ("jpg".equals(suffix) || "jpeg".equals(suffix) || "gif".equals(suffix) || "png".equals(suffix)) {
            try {
                File path = new File(ResourceUtils.getURL("classpath:").getPath());
                File upload = new File(path.getAbsolutePath(), "/static/upload/");
                if (!upload.exists()) {
                    upload.mkdirs();
                }
                String newName = System.currentTimeMillis() + "." + suffix;
                File savedFile = new File(upload+ "/" + newName);
                file.transferTo(savedFile);
                url = "/upload/" + newName;
                log.info("url: " + url);
                request.getPathInfo();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            ResultData.fail("文件类型错误");
        }
        Map<String, String> map = new HashMap<>(16);
        map.put("url", url);
        return ResultData.success(map);
    }

//    @Transactional
//    @UserLoginToken
//    @PostMapping(value = "/buy")
//    public ResultData buyManyProduct(@RequestBody JSONObject params) throws Exception {
//        User user = (User) request.getAttribute("user");
//        double totalPrice = this.productService.buyProduct(params, user.getId());
//        if (totalPrice == -1) {
//            return ResultData.success("购买失败");
//        }
//        Map<String, Double> map = new HashMap<>(16);
//        map.put("total_price", totalPrice);
//        return ResultData.success(map, "购买成功");
//    }

//    @UserLoginToken
//    @PostMapping(value = "/buy-one")
//    public ResultData buyOneProduct(@RequestParam(name = "product_id") Long productId, @RequestParam(name = "item_count") Integer itemCount) {
//        User user = (User) request.getAttribute("user");
//        log.info("productId: {}", productId);
//        log.info("itemCount: {}", itemCount);
//        double totalPrice = this.productService.buyOneProduct(productId, user.getId(), itemCount);
//        if (totalPrice == -1) {
//            return ResultData.success("购买失败");
//        }
//        Map<String, Double> map = new HashMap<>(16);
//        map.put("total_price", totalPrice);
//        return ResultData.success(map, "购买成功");
//    }

}
