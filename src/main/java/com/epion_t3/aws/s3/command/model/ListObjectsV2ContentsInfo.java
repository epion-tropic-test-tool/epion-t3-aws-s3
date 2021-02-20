/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.aws.s3.command.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ListObjectsV2ContentsInfo implements Serializable {
    private String eTag;
    private String key;
    private LocalDateTime lastModified;
    private Long size;
}
