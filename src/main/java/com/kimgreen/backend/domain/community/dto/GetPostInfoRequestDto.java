package com.kimgreen.backend.domain.community.dto;

import com.kimgreen.backend.domain.community.entity.Category;
import com.kimgreen.backend.domain.community.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetPostInfoRequestDto {
    private Category category;
    private Tag tag;
    private String search;
}
