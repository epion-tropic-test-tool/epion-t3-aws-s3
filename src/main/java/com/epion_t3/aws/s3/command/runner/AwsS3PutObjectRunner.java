/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.aws.s3.command.runner;

import com.epion_t3.aws.core.configuration.AwsCredentialsProviderConfiguration;
import com.epion_t3.aws.core.configuration.AwsSdkHttpClientConfiguration;
import com.epion_t3.aws.core.holder.AwsCredentialsProviderHolder;
import com.epion_t3.aws.core.holder.AwsSdkHttpClientHolder;
import com.epion_t3.aws.s3.command.model.AwsS3PutObject;
import com.epion_t3.core.command.bean.CommandResult;
import com.epion_t3.core.command.runner.impl.AbstractCommandRunner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * AWS S3へのオブジェクト登録コマンドの実行処理.
 *
 * @author takashno
 */
@Slf4j
public class AwsS3PutObjectRunner extends AbstractCommandRunner<AwsS3PutObject> {

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandResult execute(AwsS3PutObject command, Logger logger) throws Exception {

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

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(command.getBucket())
                .key(command.getKey())
                .build();
        // 配置オブジェクトのパスを解決
        Path objectPath = Paths.get(getCommandBelongScenarioDirectory(), command.getTarget());
        s3.putObject(putObjectRequest, objectPath);
        return CommandResult.getSuccess();
    }

}
