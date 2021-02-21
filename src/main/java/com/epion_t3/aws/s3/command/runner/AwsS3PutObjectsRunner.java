/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.aws.s3.command.runner;

import com.epion_t3.aws.core.configuration.AwsCredentialsProviderConfiguration;
import com.epion_t3.aws.core.holder.AwsCredentialsProviderHolder;
import com.epion_t3.aws.s3.command.model.AwsS3PutObjects;
import com.epion_t3.aws.s3.messages.AwsS3Messages;
import com.epion_t3.core.command.bean.CommandResult;
import com.epion_t3.core.command.runner.impl.AbstractCommandRunner;
import com.epion_t3.core.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * AWS S3へのオブジェクト登録コマンドの実行処理.
 *
 * @author takashno
 */
@Slf4j
public class AwsS3PutObjectsRunner extends AbstractCommandRunner<AwsS3PutObjects> {

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandResult execute(AwsS3PutObjects command, Logger logger) throws Exception {

        var awsCredentialsProviderConfiguration = (AwsCredentialsProviderConfiguration) referConfiguration(
                command.getCredentialsConfigRef());

        var credentialsProvider = AwsCredentialsProviderHolder.getInstance()
                .getCredentialsProvider(awsCredentialsProviderConfiguration);

        var s3 = S3Client.builder().credentialsProvider(credentialsProvider).build();

        // 配置オブジェクトのパスを解決
        var dirPath = Paths.get(getCommandBelongScenarioDirectory(), command.getTarget());

        if (Files.isDirectory(dirPath)) {
            Files.list(dirPath).forEach(x -> {
                logger.info("put object. target : {}", x);
                var putObjectRequest = PutObjectRequest.builder()
                        .bucket(command.getBucket())
                        .key(command.getPrefix())
                        .build();
                s3.putObject(putObjectRequest, x);
            });
        } else {
            // 対象がディレクトリ出ない場合
            throw new SystemException(AwsS3Messages.AWS_S3_ERR_9004, dirPath);
        }
        return CommandResult.getSuccess();
    }

}
