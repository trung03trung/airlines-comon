package com.skyviet.common.event;

import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseEvent<T> {
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private String type;
    private String source;
    @Builder.Default
    private Instant timestamp = Instant.now();
    private String correlationId;
    private T data;

    public static <T> BaseEvent<T> of(String type, String source, String correlationId, T data) {
        return BaseEvent.<T>builder()
                .type(type)
                .source(source)
                .correlationId(correlationId)
                .data(data)
                .build();
    }
}
