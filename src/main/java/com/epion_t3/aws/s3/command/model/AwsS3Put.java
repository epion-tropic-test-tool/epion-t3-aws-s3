package com.epion_t3.aws.s3.command.model;

import com.epion_t3.aws.s3.command.runner.AwsS3PutRunner;
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
@CommandDefinition(id = "AwsS3Put", runner = AwsS3PutRunner.class)
public class AwsS3Put extends AwsS3Base {

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
