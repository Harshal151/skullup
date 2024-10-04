package com.kovidRMS.util;

import java.io.File;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;


public class AWSS3Connect {

	String result = "error";

	ConfigXMLUtil xmlUtil = new ConfigXMLUtil();

	/*final static String accessKey = "AKIAQLGDDBDMDWVHKIGR";

	final static String secreteKey = "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";*/

	
	/**
	 * 
	 * @param inputFile
	 * @param inputFileName
	 * @param bucketName
	 * @param bucketRegion
	 * @param rdmlInputFilePath
	 * @return
	 */
	public String pushFile(File inputFile, String inputFileName, String bucketName, String bucketRegion,
			String rdmlInputFilePath) {

		String accessKey = xmlUtil.getAccessKey();
		
		String secreteKey = xmlUtil.getSecreteKey();
		
		try {

			AWSCredentials credentials = new BasicAWSCredentials(accessKey, secreteKey);

			AmazonS3 s3 = AmazonS3ClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(bucketRegion).build();

			AccessControlList acl = new AccessControlList();
		    acl.grantPermission(GroupGrantee.AllUsers, Permission.Read); //all users or authenticated
		    
			// pushing file into S3 bucket rdml input file path
			s3.putObject(new PutObjectRequest(bucketName + "/" + rdmlInputFilePath, inputFileName, inputFile).withCannedAcl(CannedAccessControlList.PublicRead));
		
			System.out.println("RDML input file: " + inputFileName + " pushed successfully to S3 " + rdmlInputFilePath
					+ " location.");
  
			result = "success";

		} catch (Exception exception) {
			exception.printStackTrace();

			result = "error";
		}

		return result;
	}


}
