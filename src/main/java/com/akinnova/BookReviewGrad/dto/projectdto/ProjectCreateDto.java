package com.akinnova.BookReviewGrad.dto.projectdto;

import com.akinnova.BookReviewGrad.enums.Category;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class ProjectCreateDto {
    private String coverImage;
    private String title;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String content;
    private String clientUsername;

    public ProjectCreateDto(String coverImage, String title, Category category, String content, String clientUsername) {
        this.coverImage = coverImage;
        this.title = title;
        this.category = category;
        this.content = content;
        this.clientUsername = clientUsername;
    }

    public ProjectCreateDto(){}
}

