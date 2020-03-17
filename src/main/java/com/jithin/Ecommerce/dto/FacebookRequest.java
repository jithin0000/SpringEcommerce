package com.jithin.Ecommerce.dto;

import lombok.Data;

@Data
public class FacebookRequest {

    private String facebookAccessToken;
    private String facebookUserId;
    private String fbEmail;
    private String fbUsername;
    private String fbProfilePicture;
}
