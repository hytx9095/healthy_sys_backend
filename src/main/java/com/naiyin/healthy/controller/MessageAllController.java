package com.naiyin.healthy.controller;

import com.naiyin.healthy.common.R;
import com.naiyin.healthy.model.dto.BasicHealthInfoDTO;
import com.naiyin.healthy.model.dto.message.MessageDTO;
import com.naiyin.healthy.model.entity.BasicHealthInfo;
import com.naiyin.healthy.service.MessageAllService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message_all")
@RequiredArgsConstructor
@Api(tags = "message-all-controller")
public class MessageAllController {

    private final MessageAllService messageALlService;

    @GetMapping("/get")
    @ApiOperation("获取信息")
    public R<BasicHealthInfo> getBasicHealthInfo() {
        return R.success();
    }

    @PostMapping("/send")
    @ApiOperation("发送全体消息")
    public R<Boolean> sendMessage(@RequestBody MessageDTO messageDTO) {
        messageALlService.sendMessage(messageDTO);
        return R.success(true);
    }

    @PostMapping("/add")
    @ApiOperation("更新用户基本健康信息")
    public R<Boolean> addBasicHealthInfo(@RequestBody BasicHealthInfoDTO basicHealthInfoDTO) {
        return R.success(true);
    }

    @PostMapping("/update")
    @ApiOperation("更新用户基本健康信息")
    public R updateBasicHealthInfo(@RequestBody BasicHealthInfoDTO basicHealthInfoDTO) {
        return R.success();
    }

    @PostMapping("/list/page")
    @ApiOperation("分页获取消息")
    public R getMessageList(@RequestBody BasicHealthInfoDTO basicHealthInfoDTO) {
        return R.success();
    }
}


