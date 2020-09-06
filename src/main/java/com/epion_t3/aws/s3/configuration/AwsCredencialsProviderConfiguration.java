package com.epion_t3.aws.s3.configuration;

import com.epion_t3.core.common.annotation.CustomConfigurationDefinition;
import com.epion_t3.core.common.bean.scenario.Configuration;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@CustomConfigurationDefinition(id = "AwsCredencialsProviderConfiguration")
public class AwsCredencialsProviderConfiguration  extends Configuration {
}
