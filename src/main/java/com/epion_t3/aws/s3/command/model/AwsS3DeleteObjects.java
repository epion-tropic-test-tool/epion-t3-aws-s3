/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.aws.s3.command.model;

import com.epion_t3.aws.s3.command.runner.AwsS3DeleteObjectsRunner;
import com.epion_t3.core.common.annotation.CommandDefinition;
import lombok.Getter;
import lombok.Setter;
import org.apache.bval.constraints.NotEmpty;

/**
 * Delete Objects from AWS S3.
 *
 * @author takashno
 */
@Getter
@Setter
@CommandDefinition(id = "AwsS3DeleteObjects", runner = AwsS3DeleteObjectsRunner.class)
public class AwsS3DeleteObjects extends AwsS3Base {

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
