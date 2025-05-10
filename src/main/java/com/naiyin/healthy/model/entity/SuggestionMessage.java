package com.naiyin.healthy.model.entity;

import com.naiyin.healthy.model.dto.suggestions.SuggestionDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SuggestionMessage implements Serializable {

    private List<SuggestionDTO> suggestionDTOList;

}
