package space.cuongnh2k.core.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsS3ClientConfig {
    @Value("${cloud.aws.credentials.accessKey}")
    private String ACCESS_KEY;
    @Value("${cloud.aws.credentials.secretKey}")
    private String SECRET_KEY;
    @Value("${cloud.aws.region.static}")
    private String REGION;

    @Bean
    public AmazonS3 s3client() {
        return AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(REGION))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY)))
                .build();
    }
}
