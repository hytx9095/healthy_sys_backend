package com.naiyin.healthy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naiyin.healthy.model.dto.assistantDialogue.AssistantDialogueDTO;
import com.naiyin.healthy.model.dto.assistantDialogue.AssistantDialogueQueryDTO;
import com.naiyin.healthy.model.dto.assistantDialogue.AssistantDialogueRegenerateDTO;
import com.naiyin.healthy.model.entity.AssistantDialogue;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author wang'ren
* @description 针对表【assistant_dialogue】的数据库操作Service
* @createDate 2024-12-09 19:21:14
*/
public interface AssistantDialogueService extends IService<AssistantDialogue> {
    Page<AssistantDialogue> getAssistantDialoguePage(AssistantDialogueQueryDTO assistantDialogueQueryDTO);

    void regenerateDialogue(AssistantDialogueRegenerateDTO regenerateDTO);


    void chat(AssistantDialogueDTO assistantDialogueDTO);

    QueryWrapper<AssistantDialogue> getQueryWrapper(AssistantDialogueQueryDTO assistantDialogueQueryDTO);
}
