package kr.talanton.lala.common.utils;

import java.io.IOException;
import java.io.InputStream;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

public class S3Util {
	private static final String BUCKET = "ksseobucket";
    
	public static void uploadFile(String fileName, InputStream inputStream)
			throws S3Exception, AwsServiceException, SdkClientException, IOException {
		ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();
		Region region = Region.AP_NORTHEAST_2;
		S3Client client = S3Client.builder()
			.region(region)
			.credentialsProvider(credentialsProvider)
			.build();

		PutObjectRequest request = PutObjectRequest.builder()
			.bucket(BUCKET)
        	.key(fileName)
        	.build();
        
		client.putObject(request,
			RequestBody.fromInputStream(inputStream, inputStream.available()));
        
		S3Waiter waiter = client.waiter();
		HeadObjectRequest waitRequest = HeadObjectRequest.builder()
			.bucket(BUCKET)
        	.key(fileName)
        	.build();
         
        WaiterResponse<HeadObjectResponse> waitResponse = waiter.waitUntilObjectExists(waitRequest);
        waitResponse.matched().response().ifPresent(response -> {
            // run custom logics when the file exists on S3
        	System.out.println("File upload to s3 succeeded");
        });
    }
}