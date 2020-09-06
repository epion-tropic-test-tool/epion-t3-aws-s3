package com.epion_t3.aws.s3.common;

import com.epion_t3.core.exception.SystemException;
import org.apache.commons.lang3.StringUtils;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class AwsCredentialsProviderHolder {

    /**
     * シングルトンインスタンス.
     */
    private static final AwsCredentialsProviderHolder instance = new AwsCredentialsProviderHolder();

    /**
     * AwsCredentialsProviderの保持マップ.
     */
    private static final Map<String, AwsCredentialsProvider> providerMap = new ConcurrentHashMap<>();

    /**
     * デフォルトの保持名.
     */
    private static final String DEFAULT_AWS_CREDENTIALS_PROVIDER = "AwsCredentialsProviderHolder_default";

    static {
        // デフォルトを設定
        providerMap.put(DEFAULT_AWS_CREDENTIALS_PROVIDER, DefaultCredentialsProvider.builder().build());
    }

    /**
     * インスタンスを取得します.
     *
     * @return {@link AwsCredentialsProviderHolder}
     */
    public static AwsCredentialsProviderHolder getInstance() {
        return instance;
    }

    /**
     * 資格プロバイダーを取得します.
     *
     * @param refName 参照名
     * @return {@AwsCredentialsProvider} 資格プロバイダー
     */
    public AwsCredentialsProvider getCredentialsProvider(String refName) {
        if (StringUtils.isEmpty(refName)) {
            return providerMap.get(DEFAULT_AWS_CREDENTIALS_PROVIDER);
        } else {
            if (providerMap.containsKey(refName)) {
                return providerMap.get(refName);
            } else {
                throw new SystemException();
            }
        }
    }

}
