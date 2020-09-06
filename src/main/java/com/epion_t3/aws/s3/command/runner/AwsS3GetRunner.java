package com.epion_t3.aws.s3.command.runner;

import com.epion_t3.aws.s3.command.model.AwsS3Get;
import com.epion_t3.aws.s3.common.AwsCredentialsProviderHolder;
import com.epion_t3.aws.s3.messages.AwsS3Messages;
import com.epion_t3.core.command.bean.CommandResult;
import com.epion_t3.core.command.runner.impl.AbstractCommandRunner;
import com.epion_t3.core.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

import java.nio.file.Path;

@Slf4j
public class AwsS3GetRunner extends AbstractCommandRunner<AwsS3Get> {

    @Override
    public CommandResult execute(AwsS3Get command, Logger logger) throws Exception {

        AwsCredentialsProvider credencialsProvider =
                AwsCredentialsProviderHolder.getInstance()
                        .getCredentialsProvider(command.getCredencialsConfigRef());

        S3Client s3 = S3Client.builder().credentialsProvider(credencialsProvider).build();

        // キーはスラッシュが含まれるため、そのまま保存するとエビデンスのパスが狂う
        // そのため、キーの末尾のファイル名のみをパスに利用する.
        Path evidencePath = getEvidencePath(command.getKey().split("/")[command.getKey().split("/").length - 1]);

        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(command.getBucket()).key(command.getKey()).build();
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
