/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.aws.s3.command.runner;

import com.epion_t3.aws.core.configuration.AwsCredentialsProviderConfiguration;
import com.epion_t3.aws.core.configuration.AwsSdkHttpClientConfiguration;
import com.epion_t3.aws.core.holder.AwsCredentialsProviderHolder;
import com.epion_t3.aws.core.holder.AwsSdkHttpClientHolder;
import com.epion_t3.aws.s3.command.model.AwsS3GetObject;
import com.epion_t3.aws.s3.messages.AwsS3Messages;
import com.epion_t3.core.command.bean.CommandResult;
import com.epion_t3.core.command.runner.impl.AbstractCommandRunner;
import com.epion_t3.core.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

import java.nio.file.Path;

@Slf4j
public class AwsS3GetObjectRunner extends AbstractCommandRunner<AwsS3GetObject> {

    @Override
    public CommandResult execute(AwsS3GetObject command, Logger logger) throws Exception {

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

        // キーはスラッシュが含まれるため、そのまま保存するとエビデンスのパスが狂う
        // そのため、キーの末尾のファイル名のみをパスに利用する.
        Path evidencePath = getEvidencePath(command.getKey().split("/")[command.getKey().split("/").length - 1]);

        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(command.getBucket())
                    .key(command.getKey())
                    .build();
            s3.getObject(getObjectRequest, ResponseTransformer.toFile(evidencePath));
            // エビデンス登録
            registrationFileEvidence(evidencePath);
        } catch (NoSuchKeyException nse) {
            // 指定されたキー（パス）が存在しない場合
            throw new SystemException(AwsS3Messages.AWS_S3_ERR_9002, command.getBucket(), command.getKey());
        }
        return CommandResult.getSuccess();
    }

}
