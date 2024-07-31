package com.uno.getinline.constant;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@RequiredArgsConstructor
public enum AdminOperationStatus {
    CREATE("저장"),
    MODIFY("수정"),
    DELETE("삭제");

    private final String message;
}
