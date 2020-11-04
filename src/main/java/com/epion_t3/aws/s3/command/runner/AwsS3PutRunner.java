package com.epion_t3.aws.s3.command.runner;

import com.epion_t3.aws.core.configuration.AwsCredentialsProviderConfiguration;
import com.epion_t3.aws.core.holder.AwsCredentialsProviderHolder;
import com.epion_t3.aws.s3.command.model.AwsS3Put;
import com.epion_t3.core.command.bean.CommandResult;
import com.epion_t3.core.command.runner.impl.AbstractCommandRunner;
import lombok.extern.slf4j.Slf4j;
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
public class AwsS3PutRunner extends AbstractCommandRunner<AwsS3Put> {

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandResult execute(AwsS3Put command, Logger logger) throws Exception {

        AwsCredentialsProviderConfiguration configuration =
                referConfiguration(command.getCredentialsConfigRef());

        AwsCredentialsProvider credencialsProvider =
                AwsCredentialsProviderHolder.getInstance()
                        .getCredentialsProvider(configuration);

        S3Client s3 = S3Client.builder().credentialsProvider(credencialsProvider).build();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(command.getBucket()).key(command.getKey()).build();
        // 配置オブジェクトのパスを解決
        Path objectPath = Paths.get(getCommandBelongScenarioDirectory(), command.getTarget());
        s3.putObject(putObjectRequest, objectPath);
        return CommandResult.getSuccess();
    }

}
