package com.lmz.study.learning.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmz.study.learning.dto.AccesstokenDto;
import com.lmz.study.learning.dto.GitHubUser;
import com.lmz.study.learning.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: lmz
 * @Date: 2019/5/23 17:50
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GitHubProvider gitHubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,@RequestParam(name = "state")String state){
        AccesstokenDto accesstokenDto = new AccesstokenDto();
        accesstokenDto.setClient_id(clientId);
        accesstokenDto.setClient_secret(clientSecret);
        accesstokenDto.setCode(code);
        accesstokenDto.setRedirect_uri(redirectUri);
        accesstokenDto.setState(state);
        String accesstoken = gitHubProvider.getAccesstoken(accesstokenDto);
        GitHubUser user = gitHubProvider.getUser(accesstoken);
        System.out.println(user.getName());
        return "index";
    }
}
