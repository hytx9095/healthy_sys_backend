package com.naiyin.healthy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.naiyin.healthy.common.R;
import com.naiyin.healthy.enums.MessageTypeEnum;
import com.naiyin.healthy.model.dto.common.CommonDeleteDTO;
import com.naiyin.healthy.model.dto.message.MessageDTO;
import com.naiyin.healthy.model.dto.message.MessageDeleteDTO;
import com.naiyin.healthy.model.dto.message.MessageQueryDTO;
import com.naiyin.healthy.model.vo.MessageVO;
import com.naiyin.healthy.service.MessageAllService;
import com.naiyin.healthy.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
@Api(tags = "message-controller")
public class MessageController {

    private final MessageService messageService;
    private final MessageAllService messageAllService;

    @GetMapping("/get")
    @ApiOperation("获取信息")
    public R<MessageVO> getSystemMessage() {
        return R.success();
    }

    @PostMapping("/send/system")
    @ApiOperation("发送系统信息信息")
    public R<Boolean> sendMessageBySystem(@RequestBody MessageDTO messageDTO) {
        messageService.sendMessageBySystem(messageDTO);
        return R.success(true);
    }

    @PostMapping("/add")
    @ApiOperation("系统信息信息")
    public R<Boolean> addSystemMessage(@RequestBody MessageDTO messageDTO) throws ParseException {
        messageService.addSystemMessage(messageDTO);
        return R.success(true);
    }

    @PostMapping("/update")
    @ApiOperation("更新系统信息信息")
    public R updateSystemMessage(@RequestBody MessageDTO messageDTO) {
        messageService.updateSystemMessage(messageDTO);
        return R.success();
    }

    @PostMapping("/page/user")
    @ApiOperation("分页获取消息")
    public R<IPage<MessageVO>> getMessageUserPage(@RequestBody MessageQueryDTO messageQueryDTO) {
        IPage<MessageVO> page = messageService.getMessageUserPage(messageQueryDTO);
        return R.success(page);
    }

    @PostMapping("/page/status")
    @ApiOperation("分页获取消息")
    public R<IPage<MessageVO>> getMessagePageByStatus(@RequestBody MessageQueryDTO messageQueryDTO) {
        IPage<MessageVO> page = messageService.getMessagePageByStatus(messageQueryDTO);
        return R.success(page);
    }

    @PostMapping("/page")
    @ApiOperation("分页获取消息")
    public R<IPage<MessageVO>> getMessagePage(@RequestBody MessageQueryDTO messageQueryDTO) {
        IPage<MessageVO> page = messageService.getMessagePage(messageQueryDTO);
        return R.success(page);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除消息")
    public R<Boolean> deleteMessage(@RequestBody MessageDeleteDTO deleteDTO) {
        String messageType = deleteDTO.getMessageType();
        if (messageType.equals(MessageTypeEnum.ALL.getValue())){
            boolean b = messageAllService.removeById(deleteDTO.getId());
            if (!b) {
                return R.error("删除失败");
            }
        } else {
            boolean b = messageService.removeById(deleteDTO.getId());
            if (!b) {
                return R.error("删除失败");
            }
        }
        return R.success(true);
    }
}


