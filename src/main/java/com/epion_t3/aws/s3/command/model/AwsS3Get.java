/* Copyright (c) 2017-2020 Nozomu Takashima. */
package com.epion_t3.aws.s3.command.model;

import com.epion_t3.aws.s3.command.runner.AwsS3GetRunner;
import com.epion_t3.core.common.annotation.CommandDefinition;
import com.epion_t3.core.common.bean.scenario.Command;
import lombok.Getter;
import lombok.Setter;
import org.apache.bval.constraints.NotEmpty;

/**
 * Get Object from AWS S3.
 *
 * @author takashno
 */
@Getter
@Setter
@CommandDefinition(id = "AwsS3Get", runner = AwsS3GetRunner.class)
public class AwsS3Get extends AwsS3Base {

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
