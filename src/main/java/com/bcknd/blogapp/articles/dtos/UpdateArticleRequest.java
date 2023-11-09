package com.bcknd.blogapp.articles.dtos;

import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
public class UpdateArticleRequest {
    @Nullable
    private String title;
    @Nullable
    private String body;
    @Nullable
    private String subtitle;
}
