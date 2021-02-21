/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.aws.s3.command.runner;

import com.epion_t3.aws.core.configuration.AwsCredentialsProviderConfiguration;
import com.epion_t3.aws.core.holder.AwsCredentialsProviderHolder;
import com.epion_t3.aws.s3.command.model.AwsS3ListObjects;
import com.epion_t3.aws.s3.command.model.ListObjectsV2ContentsInfo;
import com.epion_t3.aws.s3.messages.AwsS3Messages;
import com.epion_t3.core.command.bean.CommandResult;
import com.epion_t3.core.command.runner.impl.AbstractCommandRunner;
import com.epion_t3.core.exception.SystemException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

/**
 * S3のリスト取得.
 */
@Slf4j
public class AwsS3ListObjectsRunner extends AbstractCommandRunner<AwsS3ListObjects> {

    /**
     * オブジェクトマッパー.
     */
    private static final ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.findAndRegisterModules();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandResult execute(AwsS3ListObjects command, Logger logger) throws Exception {

        var awsCredentialsProviderConfiguration = (AwsCredentialsProviderConfiguration) referConfiguration(
                command.getCredentialsConfigRef());

        var credencialsProvider = AwsCredentialsProviderHolder.getInstance()
                .getCredentialsProvider(awsCredentialsProviderConfiguration);

        var s3 = S3Client.builder().credentialsProvider(credencialsProvider).build();

        // キーはスラッシュが含まれるため、そのまま保存するとエビデンスのパスが狂う
        // そのため、キーの末尾のファイル名のみをパスに利用する.
        var evidencePath = getEvidencePath("listObjects.json");

        try {
            var listObjectV2Request = ListObjectsV2Request.builder()
                    .bucket(command.getBucket())
                    .prefix(command.getPrefix())
                    .build();
            var listObjectsV2Iterable = s3.listObjectsV2Paginator(listObjectV2Request);
            var listObjectsV2ContentsInfoList = listObjectsV2Iterable.contents()
                    .stream()
                    .map(x -> new ListObjectsV2ContentsInfo(x.eTag(), x.key(),
                            LocalDateTime.ofInstant(x.lastModified(), ZoneId.systemDefault()), x.size()))
                    .collect(Collectors.toList());

            // ファイルエビデンス書き出し
            try (var fos = new FileOutputStream(evidencePath.toFile())) {
                objectMapper.writeValue(fos, listObjectsV2ContentsInfoList);
            }

            // オブジェクトエビデンス登録
            registrationObjectEvidence(listObjectsV2ContentsInfoList);

            // ファイルエビデンス登録
            registrationFileEvidence(evidencePath);

        } catch (NoSuchKeyException nse) {
            // 指定されたバケット＆Prefixが存在しない場合
            throw new SystemException(AwsS3Messages.AWS_S3_ERR_9003, command.getBucket(), command.getPrefix());
        }
        return CommandResult.getSuccess();
    }

}
