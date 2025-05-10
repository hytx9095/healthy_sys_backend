package com.naiyin.healthy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naiyin.healthy.common.R;
import com.naiyin.healthy.enums.SysErrorEnum;
import com.naiyin.healthy.exception.ParamException;
import com.naiyin.healthy.model.dto.assistantDialogue.AssistantDialogueDTO;
import com.naiyin.healthy.model.dto.assistantDialogue.AssistantDialogueDeleteDTO;
import com.naiyin.healthy.model.dto.assistantDialogue.AssistantDialogueQueryDTO;
import com.naiyin.healthy.model.dto.assistantDialogue.AssistantDialogueRegenerateDTO;
import com.naiyin.healthy.model.entity.AssistantDialogue;
import com.naiyin.healthy.service.AssistantDialogueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assistantDialogue")
@RequiredArgsConstructor
@Api(tags = "assistantDialogue-controller")
public class AssistantDialogueController {

    private final AssistantDialogueService assistantDialogueService;

    @PostMapping("/chat")
    @ApiOperation("助手对话")
    public R<Boolean> chat(@RequestBody AssistantDialogueDTO assistantDialogueDTO) {
        assistantDialogueService.chat(assistantDialogueDTO);
        return R.success(true);
    }

    @PostMapping("/get/page")
    @ApiOperation("获取助手对话")
    public R<Page<AssistantDialogue>> getAssistantDialoguePage(@RequestBody AssistantDialogueQueryDTO assistantDialogueQueryDTO) {
        Page<AssistantDialogue> page = assistantDialogueService.getAssistantDialoguePage(assistantDialogueQueryDTO);
        return R.success(page);
    }

    @PostMapping("/regenerate")
    @ApiOperation("重新生成")
    public R<Boolean> regenerateDialogue(@RequestBody AssistantDialogueRegenerateDTO regenerateDTO) {
        if (regenerateDTO.getAnswerId() == null){
            throw new ParamException(SysErrorEnum.PARAM_ERROR, "参数错误");
        }
        if (regenerateDTO.getQuestionId() == null){
            throw new ParamException(SysErrorEnum.PARAM_ERROR, "参数错误");
        }
        assistantDialogueService.regenerateDialogue(regenerateDTO);
        return R.success(true);
    }

    @DeleteMapping
    @ApiOperation("删除助手对话")
    public R<Boolean> deleteAssistantDialogue(@RequestBody AssistantDialogueDeleteDTO deleteDTO) {
        if (deleteDTO.getDialogueId() == null){
            throw new ParamException(SysErrorEnum.PARAM_ERROR, "参数错误");
        }
        boolean result = assistantDialogueService.removeById(deleteDTO.getDialogueId());
        return R.success(result);
    }

}


