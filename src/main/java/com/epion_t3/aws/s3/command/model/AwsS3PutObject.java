/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.aws.s3.command.model;

import com.epion_t3.aws.s3.command.runner.AwsS3PutObjectRunner;
import com.epion_t3.core.common.annotation.CommandDefinition;
import lombok.Getter;
import lombok.Setter;
import org.apache.bval.constraints.NotEmpty;

/**
 * Put Object to AWS S3.
 *
 * @author takashno
 */
@Getter
@Setter
@CommandDefinition(id = "AwsS3PutObject", runner = AwsS3PutObjectRunner.class)
public class AwsS3PutObject extends AwsS3Base {

    /**
     * S3 Bucket.
     */
    @NotEmpty
    private String bucket;

    /**
     * S3 Path.
     */
    @NotEmpty
    private String key;

}
