/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.aws.s3.messages;

import com.epion_t3.core.message.Messages;

/**
 * aws-s3用メッセージ定義Enum.<br>
 *
 * @author epion-t3-devtools
 */
public enum AwsS3Messages implements Messages {

    /** 対象（target）のバケット及びプレフィックスは存在しません.Bucket:{0}, Prefix:{1} */
    AWS_S3_ERR_9003("com.epion_t3.aws.s3.err.9003"),

    /** 対象（target）のバケット及びキーへアクセスできません.Bucket:{0}, Path:{1} */
    AWS_S3_ERR_9001("com.epion_t3.aws.s3.err.9001"),

    /** 対象（target）のバケット及びキーは存在しません.Bucket:{0}, Path:{1} */
    AWS_S3_ERR_9002("com.epion_t3.aws.s3.err.9002");

    /** メッセージコード */
    private String messageCode;

    /**
     * プライベートコンストラクタ<br>
     *
     * @param messageCode メッセージコード
     */
    private AwsS3Messages(final String messageCode) {
        this.messageCode = messageCode;
    }

    /**
     * messageCodeを取得します.<br>
     *
     * @return messageCode
     */
    public String getMessageCode() {
        return this.messageCode;
    }
}
