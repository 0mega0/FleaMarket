package com.finlu.market.controller.admin.param;

import lombok.Data;


@Data
public class UserReq {
    private String username;
    private Integer staff;
    private boolean isAdmin;
    private boolean isActive;
}
