/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.aws.s3.command.model;

import com.epion_t3.aws.s3.command.runner.AwsS3ListObjectsRunner;
import com.epion_t3.core.common.annotation.CommandDefinition;
import lombok.Getter;
import lombok.Setter;
import org.apache.bval.constraints.NotEmpty;

@Getter
@Setter
@CommandDefinition(id = "AwsS3ListObjects", runner = AwsS3ListObjectsRunner.class)
public class AwsS3ListObjects extends AwsS3Base {

    /**
     * S3 Bucket.
     */
    @NotEmpty
    private String bucket;

    /**
     * S3 Prefix.
     */
    @NotEmpty
    private String prefix;
}
