/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.aws.s3.command.runner;

import com.epion_t3.aws.core.configuration.AwsCredentialsProviderConfiguration;
import com.epion_t3.aws.core.configuration.AwsSdkHttpClientConfiguration;
import com.epion_t3.aws.core.holder.AwsCredentialsProviderHolder;
import com.epion_t3.aws.core.holder.AwsSdkHttpClientHolder;
import com.epion_t3.aws.s3.command.model.AwsS3DeleteObjects;
import com.epion_t3.aws.s3.messages.AwsS3Messages;
import com.epion_t3.core.command.bean.CommandResult;
import com.epion_t3.core.command.runner.impl.AbstractCommandRunner;
import com.epion_t3.core.exception.SystemException;
import com.epion_t3.core.message.MessageResolver;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Delete;
import software.amazon.awssdk.services.s3.model.DeleteObjectsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.ObjectIdentifier;

import java.util.ArrayList;

/**
 *
 */
@Slf4j
public class AwsS3DeleteObjectsRunner extends AbstractCommandRunner<AwsS3DeleteObjects> {

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandResult execute(AwsS3DeleteObjects command, Logger logger) throws Exception {

        var awsCredentialsProviderConfiguration = (AwsCredentialsProviderConfiguration) referConfiguration(
                command.getCredentialsConfigRef());

        var credentialsProvider = AwsCredentialsProviderHolder.getInstance()
                .getCredentialsProvider(awsCredentialsProviderConfiguration);

        var s3 = (S3Client) null;
        if (StringUtils.isEmpty(command.getSdkHttpClientConfigRef())) {
            s3 = S3Client.builder().credentialsProvider(credentialsProvider).build();
        } else {
            var sdkHttpClientConfiguration = (AwsSdkHttpClientConfiguration) referConfiguration(
                    command.getSdkHttpClientConfigRef());
            var sdkHttpClient = AwsSdkHttpClientHolder.getInstance().getSdkHttpClient(sdkHttpClientConfiguration);
            s3 = S3Client.builder().credentialsProvider(credentialsProvider).httpClient(sdkHttpClient).build();
        }

        try {

            var prefix = StringUtils.isNotEmpty(command.getPrefix())
                    ? command.getPrefix().endsWith("/") ? command.getPrefix() : command.getPrefix() + "/"
                    : StringUtils.EMPTY;

            var listObjectV2Request = ListObjectsV2Request.builder().bucket(command.getBucket()).prefix(prefix).build();
            var listObjectsV2Iterable = s3.listObjectsV2Paginator(listObjectV2Request);

            var deleteTargets = new ArrayList<ObjectIdentifier>();
            listObjectsV2Iterable.contents().forEach(x -> {
                deleteTargets.add(ObjectIdentifier.builder().key(x.key()).build());
            });

            if (deleteTargets.isEmpty()) {
                logger.info("target is none.");
                return CommandResult.getSuccess();
            }

            var deleteObjectsRequest = DeleteObjectsRequest.builder()
                    .bucket(command.getBucket())
                    .delete(Delete.builder().objects(deleteTargets).build())
                    .build();

            var response = s3.deleteObjects(deleteObjectsRequest);

            // 削除済のオブジェクトをログ出力
            logger.info("deleted target.");
            response.deleted().forEach(x -> {
                logger.info(x.key());
            });

        } catch (NoSuchKeyException nse) {
            // 指定されたキー（パス）が存在しない場合
            throw new SystemException(AwsS3Messages.AWS_S3_ERR_9003, command.getBucket(), command.getPrefix());
        }
        return CommandResult.getSuccess();
    }

}
