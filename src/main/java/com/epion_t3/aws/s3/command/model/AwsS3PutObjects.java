/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.aws.s3.command.model;

import com.epion_t3.aws.s3.command.runner.AwsS3PutObjectsRunner;
import com.epion_t3.core.common.annotation.CommandDefinition;
import lombok.Getter;
import lombok.Setter;
import org.apache.bval.constraints.NotEmpty;

/**
 * Put Objects to AWS S3.
 *
 * @author Nozomu Takashima.
 */
@Getter
@Setter
@CommandDefinition(id = "AwsS3PutObjects", runner = AwsS3PutObjectsRunner.class)
public class AwsS3PutObjects extends AwsS3Base {

    /**
     * S3 Bucket.
     */
    @NotEmpty
    private String bucket;

    /**
     * S3 prefix.
     */
    @NotEmpty
    private String prefix;

}
