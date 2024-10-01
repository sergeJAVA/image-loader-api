package com.example.image_loader_api.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.File;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Image {
    private File data;
    private Long id;
    private Long userId;
}
