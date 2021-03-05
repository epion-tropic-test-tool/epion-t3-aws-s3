/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.aws.s3.command.model;

import com.epion_t3.core.common.bean.scenario.Command;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AwsS3Base extends Command {

    /** 資格情報設定の参照名. */
    private String credentialsConfigRef;

    /** HTTPクライアント設定の参照名. */
    private String sdkHttpClientConfigRef;
}
