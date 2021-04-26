package com.yyshare.web;

import com.yyshare.Service.IShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/share")
public class ShareController {

    @Autowired
    private IShareService shareService;

    /**
     * 获取股票基本数据
     * @param params
     * @return
     */
    @PostMapping("/data/base")
    public ResponseEntity getBaseShareData(@RequestBody Map params) {
        return new ResponseEntity(shareService.findBaseShares(params), HttpStatus.OK);
    }
}
