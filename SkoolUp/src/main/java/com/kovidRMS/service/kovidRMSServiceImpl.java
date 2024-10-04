package com.kovidRMS.service;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.*;
import com.amazonaws.services.s3.*;
import com.kovidRMS.daoImpl.*;
import com.kovidRMS.daoInf.*;
import com.kovidRMS.form.*;
import com.kovidRMS.util.*;

public class kovidRMSServiceImpl implements kovidRMSServiceInf {

	String statusMessage = "error";
	LoginDAOInf daoInf = null;

	LibraryDAOInf librarydaoInf = null;

	VotingDAOInf votingDaoInf = null;

	ConfigXMLUtil xmlUtil = new ConfigXMLUtil();

	public String registerUser(LoginForm form, String realPath, HttpServletRequest request, int organizationID) {
		// TODO Auto-generated method stub
		LoginDAOInf daoInf = new LoginDAOImpl();

		String accessKey = xmlUtil.getAccessKey();

		String secreteKey = xmlUtil.getSecreteKey();

		AWSS3Connect awss3Connect = new AWSS3Connect();

		// getting input file location from S3 bucket
		String s3rdmlFilePath = xmlUtil.getS3RDMLFilePath();

		// getting s3 bucket name
		String bucketName = xmlUtil.getS3BucketName();

		// getting s3 bucket region
		String bucketRegion = xmlUtil.getS3BucketRegion();

		// Set the presigned URL to expire after one hour.
		java.util.Date expiration = new java.util.Date();
		long expTimeMillis = expiration.getTime();
		expTimeMillis += 1000 * 60 * 60;
		expiration.setTime(expTimeMillis);

		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secreteKey);

		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(bucketRegion).build();

		int status = 0;

		try {

			// Setting profilePicDBname as NULL
			form.setProfilePicDBName(null);

			/*
			 * Storing user uploaded profile pic file into image/profilePic directory of
			 * Project, only if the profilePic variable is not null
			 */
			if (form.getProfilePic() != null) {

				String[] array = form.getProfilePicFileName().split("\\.");

				String fileExtension = array[1];

				// Setting content type according to file extension
				if (fileExtension.equalsIgnoreCase("jpg")) {

					form.setProfilePicContentType("image/jpg");
				} else if (fileExtension.equalsIgnoreCase("jpeg")) {

					form.setProfilePicContentType("image/jpeg");
				} else if (fileExtension.equalsIgnoreCase("png")) {

					form.setProfilePicContentType("image/png");
				} else if (fileExtension.equalsIgnoreCase("bmp")) {

					form.setProfilePicContentType("image/bmp");
				}

				String fileNameToBeStored = form.getUsername() + organizationID + "." + fileExtension;

				System.out.println("Original File name is ::::" + form.getProfilePicFileName());

				System.out.println("File Name to be stored into DB is ::: " + fileNameToBeStored);
				/*
				 * Setting file name with S3 bucket path to be inserted into AppUser table into
				 * profilePicDBName
				 */

				form.setProfilePicDBName(
						s3.getUrl(bucketName, s3rdmlFilePath).toExternalForm() + "/" + fileNameToBeStored);

				// Storing file to S3 RDML INPUT FILE location
				statusMessage = awss3Connect.pushFile(form.getProfilePic(), fileNameToBeStored, bucketName,
						bucketRegion, s3rdmlFilePath);
			}

			// Setting signatureDBname as NULL
			form.setSignatureDBName(null);

			/*
			 * Storing user uploaded profile pic file into image/profilePic directory of
			 * Project, only if the profilePic variable is not null
			 */
			if (form.getSignature() != null) {

				String[] array = form.getSignatureFileName().split("\\.");

				String fileExtension = array[1];

				// Setting content type according to file extension

				if (fileExtension.equalsIgnoreCase("jpg")) {

					form.setSignatureContentType("image/jpg");
				} else if (fileExtension.equalsIgnoreCase("jpeg")) {

					form.setSignatureContentType("image/jpeg");
				} else if (fileExtension.equalsIgnoreCase("png")) {

					form.setSignatureContentType("image/png");
				} else if (fileExtension.equalsIgnoreCase("bmp")) {

					form.setSignatureContentType("image/bmp");
				}

				String fileNameToBeStored = form.getUsername() + organizationID + "_Signature." + fileExtension;

				System.out.println("Original File name is ::::" + form.getSignatureFileName());

				System.out.println("File Name to be stored into DB is ::: " + fileNameToBeStored);

				/*
				 * Setting file name to be inserted into AppUser table into profilePicDBName
				 * variable of UserForm
				 */
				form.setSignatureDBName(
						s3.getUrl(bucketName, s3rdmlFilePath).toExternalForm() + "/" + fileNameToBeStored);

				// Storing file to S3 RDML INPUT FILE location
				statusMessage = awss3Connect.pushFile(form.getSignature(), fileNameToBeStored, bucketName, bucketRegion,
						s3rdmlFilePath);
			}

			/*
			 * Verifying whether user with same username already exist into User table or
			 * not, if not then only proceed for insertion else give error msg saying User
			 * with sme username already exists.
			 */

			status = daoInf.verifyUsername(form.getUsername(), organizationID);

			if (status != 1) {

				/*
				 * Inserting user detail into User table
				 */
				statusMessage = daoInf.insertUser(form, organizationID);
				if (statusMessage.equalsIgnoreCase("success")) {

					/*
					 * Retrieving userID from username from User table
					 */
					int userID = daoInf.retrieveUserIDByUsername(form.getUsername(), organizationID);

					System.out.println("Successfully inserted user detail into User Table.");

					/*
					 * Inserting password into PasswordHistroy table
					 */
					daoInf.insertPasswordHistory(userID, form.getPassword());

					return statusMessage;

				} else {
					System.out.println("Failed to insert user detail into User table.");
					statusMessage = "error";
					return statusMessage;
				}

			} else {
				System.out.println("Username already exists into User table...");
				statusMessage = "input";
				return statusMessage;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			statusMessage = "error";
			return statusMessage;

		}

	}

	public String editUserDetail(LoginForm form, int organizationID, String realPath, HttpServletRequest request) {
		daoInf = new LoginDAOImpl();

		String accessKey = xmlUtil.getAccessKey();

		String secreteKey = xmlUtil.getSecreteKey();

		AWSS3Connect awss3Connect = new AWSS3Connect();

		// getting input file location from S3 bucket
		String s3rdmlFilePath = xmlUtil.getS3RDMLFilePath();

		// getting s3 bucket name
		String bucketName = xmlUtil.getS3BucketName();

		// getting s3 bucket region
		String bucketRegion = xmlUtil.getS3BucketRegion();

		// Set the presigned URL to expire after one hour.
		java.util.Date expiration = new java.util.Date();
		long expTimeMillis = expiration.getTime();
		expTimeMillis += 1000 * 60 * 60;
		expiration.setTime(expTimeMillis);

		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secreteKey);

		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(bucketRegion).build();

		try {

			/*
			 * Verify whether changed username already exist with other userID, if yes, then
			 * give error else proceed further
			 */
			boolean check = false;

			check = daoInf.verifyUsernameWithUserID(form.getUsername(), form.getUserID(), organizationID);

			if (check) {

				// Setting profilePicDBname as NULL
				/* form.setProfilePicDBName(null); */

				/*
				 * Storing user uploaded profile pic file into images/profilePics directory of
				 * Project, only if the profilePic variable is not null
				 */
				if (form.getProfilePic() != null) {

					String[] array = form.getProfilePicFileName().split("\\.");

					String fileExtension = array[1];

					// Setting content type according to file extension
					if (fileExtension.equalsIgnoreCase("jpg")) {

						form.setProfilePicContentType("image/jpg");

					} else if (fileExtension.equalsIgnoreCase("jpeg")) {

						form.setProfilePicContentType("image/jpeg");

					} else if (fileExtension.equalsIgnoreCase("png")) {

						form.setProfilePicContentType("image/png");

					} else if (fileExtension.equalsIgnoreCase("bmp")) {

						form.setProfilePicContentType("image/bmp");

					}

					String fileNameToBeStored = form.getUsername() + organizationID + "." + fileExtension;

					System.out.println("Original File name is ::::" + form.getProfilePicFileName());

					System.out.println("File Name to be stored into DB is ::: " + fileNameToBeStored);
					/*
					 * Setting file name to be inserted into AppUser table into profilePicDBName
					 * variable of UserForm
					 */
					form.setProfilePicDBName(
							s3.getUrl(bucketName, s3rdmlFilePath).toExternalForm() + "/" + fileNameToBeStored);

					// Storing file to S3 RDML INPUT FILE location
					statusMessage = awss3Connect.pushFile(form.getProfilePic(), fileNameToBeStored, bucketName,
							bucketRegion, s3rdmlFilePath);
				}

				/*
				 * Storing user uploaded profile pic file into image/profilePic directory of
				 * Project, only if the profilePic variable is not null
				 */
				if (form.getSignature() != null) {

					String[] array = form.getSignatureFileName().split("\\.");

					String fileExtension = array[1];

					// Setting content type according to file extension
					if (fileExtension.equalsIgnoreCase("jpg")) {

						form.setSignatureContentType("image/jpg");
					} else if (fileExtension.equalsIgnoreCase("jpeg")) {

						form.setSignatureContentType("image/jpeg");
					} else if (fileExtension.equalsIgnoreCase("png")) {

						form.setSignatureContentType("image/png");
					} else if (fileExtension.equalsIgnoreCase("bmp")) {

						form.setSignatureContentType("image/bmp");
					}

					String fileNameToBeStored = form.getUsername() + organizationID + "_Signature." + fileExtension;

					System.out.println("Original File name is ::::" + form.getSignatureFileName());

					System.out.println("File Name to be stored into DB is ::: " + fileNameToBeStored);

					/*
					 * Setting file name to be inserted into AppUser table into profilePicDBName
					 * variable of UserForm
					 */
					form.setSignatureDBName(
							s3.getUrl(bucketName, s3rdmlFilePath).toExternalForm() + "/" + fileNameToBeStored);

					// Storing file to S3 RDML INPUT FILE location
					statusMessage = awss3Connect.pushFile(form.getSignature(), fileNameToBeStored, bucketName,
							bucketRegion, s3rdmlFilePath);
				}

				/*
				 * Check whether password from table for userID is same as that entered by user
				 * for the same userID, then proceed for updation else check for last five
				 * password existence from PasswordHistory table
				 */
				check = daoInf.verifyPassword(form.getUserID(), form.getPassword());

				if (check) {

					/*
					 * Updating user details from User table
					 */
					statusMessage = daoInf.updateUserDetail(form);

					if (statusMessage.equalsIgnoreCase("success")) {

						return statusMessage;

					} else {

						System.out.println("Failed to update user details into User table");

						statusMessage = "error";
						return statusMessage;

					}

					/*
					 * Password match check; checking whether entered password matches last five
					 * passwords from Password History, then give error message else proceed further
					 */
				} else {

					check = daoInf.verifyPasswordHistory(form.getUserID(), form.getPassword());

					if (check) {

						System.out.println("Entered password lies within last 5 passwords.");

						statusMessage = "input";

						return statusMessage;

					} else {

						/*
						 * Updating user details from User table
						 */
						statusMessage = daoInf.updateUserDetail(form);

						if (statusMessage.equalsIgnoreCase("success")) {

							/*
							 * Inserting password into PasswordHistroy table
							 */
							daoInf.insertPasswordHistory(form.getUserID(), form.getPassword());

							return statusMessage;

						} else {

							System.out.println("Failed to update user details into User table");

							statusMessage = "error";
							return statusMessage;

						}
					}
				}

			} else {
				System.out.println("Username already exist for another user.");
				statusMessage = "error";
				return statusMessage;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			statusMessage = "error";
			return statusMessage;
		}

	}

	public String addStudent(StudentForm studform, int id) {

		StuduntDAOInf daoInf = new StudentDAOImpl();

		int status = 0;
		try {

			status = daoInf.verifyAadhaar(studform.getAadhaar());

			statusMessage = daoInf.insertStudentDetails(studform, id);
			if (statusMessage.equalsIgnoreCase("success")) {
				/*
				 * Retrieving patientID from Patient table on the basis of firstname lastname
				 * and dateOfBirth
				 */

				int studentID = daoInf.retrievestudentID(studform.getAadhaar());

				studform.setStudentID(studentID);

				if (studentID != 0) {
					System.out.println("Student ID is ::: " + studentID);
					/*
					 * Inserting identification details into Identification table
					 */
					statusMessage = daoInf.insertStudentContact(studform, studentID);
					if (statusMessage.equalsIgnoreCase("success")) {
						/*
						 * inserting emergency contact information into EmergencyContact table
						 */

						statusMessage = daoInf.insertStudentPersonalInfo(studform, studentID);
						if (statusMessage.equalsIgnoreCase("success")) {

							statusMessage = daoInf.insertFatherContact(studform.getParentfirstName(),
									studform.getParentmiddleName(), studform.getParentlastName(),
									studform.getRelation(), studform.getMobile(), studform.getParentPhone(),
									studform.getParentAddress(), studform.getParentcity(), studform.getParentstate(),
									studform.getParentcountry(), studform.getParentpinCode(), studform.getOccupation(),
									studform.getEmailId(), studentID);
							if (statusMessage.equalsIgnoreCase("success")) {

								statusMessage = daoInf.insertMotherContact(studform.getMotherfirstName(),
										studform.getMothermiddleName(), studform.getMotherlastName(),
										studform.getMotherrelation(), studform.getMothermobile(),
										studform.getMotherPhone(), studform.getMotherAddress(),
										studform.getMothercity(), studform.getMotherstate(),
										studform.getMothercountry(), studform.getMotherpinCode(),
										studform.getMotheroccupation(), studform.getMotheremailId(), studentID);
								if (statusMessage.equalsIgnoreCase("success")) {

									statusMessage = daoInf.insertEmergencyContacts(studform, studentID);

									if (statusMessage.equalsIgnoreCase("success")) {

										if (studform.getNewCondition() == null) {

											System.out.println("No new Condition added");

											statusMessage = "success";

										} else {

											for (int i = 0; i < studform.getNewCondition().length; i++) {
												statusMessage = daoInf.insertCondition(studform.getNewCondition()[i],
														studentID);
												statusMessage = "success";
											}
										}

										if (statusMessage.equalsIgnoreCase("success")) {

											int AYClassID = 0;

											AYClassID = daoInf.retrieveAYClassID(studform.getStandardID(),
													studform.getDivisionID());

											/*
											 * inserting emergency contact information into EmergencyContact table
											 */
											statusMessage = daoInf.insertRegistrationInfo(studform, studentID,
													AYClassID);

											if (statusMessage.equalsIgnoreCase("success")) {

												return statusMessage;

											} else {
												System.out.println(
														"Failed to insert Student's Registration information details into Registration table");
												statusMessage = "error";
												return statusMessage;
											}
										} else {
											System.out.println(
													"Failed to insert Student's Medical Condition information details into MedicalHistory table");
											statusMessage = "error";
											return statusMessage;
										}

									} else {
										System.out.println(
												"Failed to insert Student's Emergency Contact information details into Parent table");
										statusMessage = "error";
										return statusMessage;
									}

								} else {
									System.out.println(
											"Failed to insert Student's Mother information details into Parent table");
									statusMessage = "error";
									return statusMessage;
								}
							} else {
								System.out.println(
										"Failed to insert Student's Father information details into Parent table");
								statusMessage = "error";
								return statusMessage;
							}

						} else {
							System.out.println(
									"Failed to insert Student's Personal information details into StudentDetails table");
							statusMessage = "error";
							return statusMessage;
						}

					} else {
						System.out.println("Failed to insert Student's Contact details into StudentContact table");
						statusMessage = "error";
						return statusMessage;
					}
				} else {
					System.out.println("Failed to retrieve studentID from database...");
					statusMessage = "error";
					return statusMessage;
				}

			} else {
				System.out.println("Failed to insert student Details into student Table");
				statusMessage = "error";
				return statusMessage;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			statusMessage = "error";
			return statusMessage;

		}
	}

	public String editStudentDetail(StudentForm studform) {
		StuduntDAOInf daoInf = new StudentDAOImpl();

		try {
			/*
			 * updating patient details into Patient table
			 */
			statusMessage = daoInf.updateStudentDetail(studform);

			if (statusMessage.equalsIgnoreCase("success")) {
				/*
				 * Updating identification details
				 */

				statusMessage = daoInf.updateStudentContacts(studform);

				if (statusMessage.equalsIgnoreCase("success")) {
					/*
					 * Updating StudentInfo contact information
					 */
					statusMessage = daoInf.updateStudentInfo(studform);
					if (statusMessage.equalsIgnoreCase("success")) {

						/*
						 * Updating Fathers information
						 */

						boolean check = daoInf.verifyParentsDetail(studform.getStudentID(), "Father");
						System.out.println("check" + check);
						if (check) {
							System.out.println("Inside......." + check);
							statusMessage = daoInf.updateFathersInfo(studform.getParentfirstName(),
									studform.getParentmiddleName(), studform.getParentlastName(),
									studform.getRelation(), studform.getMobile(), studform.getParentPhone(),
									studform.getParentAddress(), studform.getParentcity(), studform.getParentstate(),
									studform.getParentcountry(), studform.getParentpinCode(), studform.getOccupation(),
									studform.getEmailId(), studform.getStudentID());

						} else {
							System.out.println("Outside......." + check);
							statusMessage = daoInf.insertFatherContact(studform.getParentfirstName(),
									studform.getParentmiddleName(), studform.getParentlastName(),
									studform.getRelation(), studform.getMobile(), studform.getParentPhone(),
									studform.getParentAddress(), studform.getParentcity(), studform.getParentstate(),
									studform.getParentcountry(), studform.getParentpinCode(), studform.getOccupation(),
									studform.getEmailId(), studform.getStudentID());
						}

						if (statusMessage.equalsIgnoreCase("success")) {

							boolean check1 = daoInf.verifyParentsDetail(studform.getStudentID(), "Mother");
							System.out.println("check1" + check1);
							if (check1) {
								System.out.println("Inside Mother......." + check1);
								statusMessage = daoInf.updateMothersInfo(studform.getMotherfirstName(),
										studform.getMothermiddleName(), studform.getMotherlastName(),
										studform.getMotherrelation(), studform.getMothermobile(),
										studform.getMotherPhone(), studform.getMotherAddress(),
										studform.getMothercity(), studform.getMotherstate(),
										studform.getMothercountry(), studform.getMotherpinCode(),
										studform.getMotheroccupation(), studform.getMotheremailId(),
										studform.getStudentID());
							} else {
								/*
								 * Updating Mothers information
								 */
								System.out.println("Outside Mother......." + check1);
								statusMessage = daoInf.insertMotherContact(studform.getMotherfirstName(),
										studform.getMothermiddleName(), studform.getMotherlastName(),
										studform.getMotherrelation(), studform.getMothermobile(),
										studform.getMotherPhone(), studform.getMotherAddress(),
										studform.getMothercity(), studform.getMotherstate(),
										studform.getMothercountry(), studform.getMotherpinCode(),
										studform.getMotheroccupation(), studform.getMotheremailId(),
										studform.getStudentID());

							}

							if (statusMessage.equalsIgnoreCase("success")) {

								/*
								 * Updating Emergency Contact information
								 */
								statusMessage = daoInf.updateEmergencyContact(studform);

								if (statusMessage.equalsIgnoreCase("success")) {

									/*
									 * Update Registration information into Registration table
									 */
									statusMessage = daoInf.updateRegistrationInfo(studform);
									if (statusMessage.equalsIgnoreCase("success")) {

										if (studform.getNewCondition() == null) {

											System.out.println("No new examination added");

											statusMessage = "success";

										} else {

											for (int i = 0; i < studform.getNewCondition().length; i++) {

												statusMessage = daoInf.insertCondition(studform.getNewCondition()[i],
														studform.getStudentID());

												if (statusMessage.equalsIgnoreCase("success")) {

													System.out.println(
															"Successfully inserted Medical Condition detail into MedicalHistory Table.");

													statusMessage = "success";
												} else {

													System.out.println(
															"Failed to add Medical Condition details into MedicalHistory table");

													statusMessage = "error";
												}
											}
										}

										if (studform.getEditmedID() == null) {

											System.out.println("No new examination added");

											statusMessage = "success";

										} else {

											for (int i = 0; i < studform.getEditmedID().length; i++) {

												statusMessage = daoInf.updateCondition(
														studform.getEditmedCondition()[i],
														Integer.parseInt(studform.getEditmedID()[i]));

												if (statusMessage.equalsIgnoreCase("success")) {

													System.out.println(
															"Successfully updated Medical Condition detail into MedicalHistory Table.");

													statusMessage = "success";
												} else {
													System.out.println(
															"Failed to updated Medical Condition detail into MedicalHistory table.");
													statusMessage = "error";
												}

											}
										}

										return statusMessage;

									} else {

										System.out.println("Failed to Student Registration details.");
										statusMessage = "error";
										return statusMessage;
									}
								} else {
									System.out.println("Failed to Update Emergency Contact information details.");
									statusMessage = "error";
									return statusMessage;
								}

							} else {
								System.out.println("Failed to Update Mothers information details.");
								statusMessage = "error";
								return statusMessage;
							}

						} else {
							System.out.println("Failed to Update Fathers information details.");
							statusMessage = "error";
							return statusMessage;
						}

					} else {
						System.out.println("Failed to Update Student information details.");
						statusMessage = "error";
						return statusMessage;
					}

				} else {
					System.out.println("Failed to Update Student contact details.");
					statusMessage = "error";
					return statusMessage;
				}

			} else {
				System.out.println("Failed to update Student details.");
				statusMessage = "error";
				return statusMessage;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			statusMessage = "error";
			return statusMessage;

		}
	}

	public String registerAcademicYear(LoginForm form, int organizationID) {
		// TODO Auto-generated method stub
		LoginDAOInf daoInf = new LoginDAOImpl();

		int status = 0;

		int yearStatus = 0;

		int attendance = 0;

		try {

			/*
			 * Check whether AcademicYears Name present in AcademicYear table or not if name
			 * present then give error msg & dont allow to insertion
			 */
			status = daoInf.CheckAcademicYearName(form.getYearName(), organizationID);

			if (status == 0) {

				yearStatus = daoInf.CheckFirstEntryAcademicYear(organizationID);

				if (yearStatus == 0) {

					/*
					 * Inserting AcademicYear detail into User table
					 */
					statusMessage = daoInf.insertAcademicYear(form, organizationID, "Active");
					if (statusMessage.equalsIgnoreCase("success")) {

						System.out.println("Successfully inserted AcademicYear detail into AcademicYear Table.");

						/*
						 * Retrieving academicYearID from AcademicYear table
						 */
						int academicYearID = daoInf.retrieveAcademicYearID(organizationID);

						if (form.getNewAddAttendance() == null) {

							System.out.println("No new Attendance added");

							statusMessage = "success";

						} else {

							for (int i = 0; i < form.getNewAddAttendance().length; i++) {

								String AttendanceEvent = form.getNewAddAttendance()[i];

								System.out.println("Configuration event before isnerting ... " + AttendanceEvent);

								if (AttendanceEvent == null || AttendanceEvent == "") {

									System.out.println("No Attendance Event details added.");

								} else {

									if (AttendanceEvent.isEmpty()) {
										System.out.println("No Attendance Event details added.");
									} else {

										/*
										 * Checking whether Configuration starts with *, if so, remove first * and
										 * proceed further
										 */
										if (AttendanceEvent.startsWith("*")) {
											AttendanceEvent = AttendanceEvent.substring(1);
										}

										/*
										 * Checking whether adverseEvent contains * in it, if so, splitting the String
										 * by '*' and inserting into ConfigEvent table
										 */
										System.out.println("Attendance event string ::: " + AttendanceEvent);
										if (AttendanceEvent.contains("*")) {

											String[] confEventArray = AttendanceEvent.split("\\*");

											for (int j = 0; j < confEventArray.length; j++) {

												String[] attendanceEventArray = confEventArray[j].split("\\$");

												attendance = daoInf.CheckAttendance(
														Integer.parseInt(attendanceEventArray[1]),
														attendanceEventArray[0], attendanceEventArray[2],
														academicYearID);

												if (attendance == 0) {
													statusMessage = daoInf.insertAttendance(confEventArray[j],
															academicYearID);
												} else {
													System.out.println(
															"Attendance details already exist in table. Please Add new details for Attendance");
													statusMessage = "success";
												}
											}
										} else {

											String[] attendanceEventArray = AttendanceEvent.split("\\$");

											attendance = daoInf.CheckAttendance(
													Integer.parseInt(attendanceEventArray[1]), attendanceEventArray[0],
													attendanceEventArray[2], academicYearID);

											if (attendance == 0) {
												statusMessage = daoInf.insertAttendance(AttendanceEvent,
														academicYearID);
											} else {
												System.out.println(
														"Attendance details already exist in table. Please Add new details for Attendance");
												statusMessage = "success";
											}

										}
									}
								}
								statusMessage = "success";
							}
						}

						if (form.getNewLibrary() == null) {

							System.out.println("No new library added");

							statusMessage = "success";

						} else {

							for (int i = 0; i < form.getNewLibrary().length; i++) {

								statusMessage = daoInf.insertLibrary(form.getNewLibrary()[i], academicYearID);
							}
						}

						return statusMessage;

					} else {
						System.out.println("Failed to insert AcademicYear detail into AcademicYear table.");
						statusMessage = "error";
						return statusMessage;
					}

				} else {
					/*
					 * Inserting AcademicYear detail into User table
					 */
					statusMessage = daoInf.insertAcademicYear(form, organizationID, "DRAFT");
					if (statusMessage.equalsIgnoreCase("success")) {

						System.out.println("Successfully inserted AcademicYear detail into AcademicYear Table.");

						/*
						 * Retrieving academicYearID from AcademicYear table
						 */
						int academicYearID = daoInf.retrieveAcademicYearID(organizationID);

						if (form.getNewAddAttendance() == null) {

							System.out.println("No new Attendance added");

							statusMessage = "success";

						} else {

							for (int i = 0; i < form.getNewAddAttendance().length; i++) {

								String AttendanceEvent = form.getNewAddAttendance()[i];

								System.out.println("Configuration event before isnerting ... " + AttendanceEvent);

								if (AttendanceEvent == null || AttendanceEvent == "") {

									System.out.println("No Attendance Event details added.");

								} else {

									if (AttendanceEvent.isEmpty()) {
										System.out.println("No Attendance Event details added.");
									} else {

										/*
										 * Checking whether Configuration starts with *, if so, remove first * and
										 * proceed further
										 */
										if (AttendanceEvent.startsWith("*")) {
											AttendanceEvent = AttendanceEvent.substring(1);
										}

										/*
										 * Checking whether adverseEvent contains * in it, if so, splitting the String
										 * by '*' and inserting into ConfigEvent table
										 */
										System.out.println("Attendance event string ::: " + AttendanceEvent);
										if (AttendanceEvent.contains("*")) {

											String[] confEventArray = AttendanceEvent.split("\\*");

											for (int j = 0; j < confEventArray.length; j++) {

												String[] attendanceEventArray = confEventArray[j].split("\\$");

												attendance = daoInf.CheckAttendance(
														Integer.parseInt(attendanceEventArray[1]),
														attendanceEventArray[0], attendanceEventArray[2],
														academicYearID);

												if (attendance == 0) {
													statusMessage = daoInf.insertAttendance(confEventArray[j],
															academicYearID);
												} else {
													System.out.println(
															"Attendance details already exist in table. Please Add new details for Attendance");
													statusMessage = "success";
												}
											}
										} else {

											String[] attendanceEventArray = AttendanceEvent.split("\\$");

											attendance = daoInf.CheckAttendance(
													Integer.parseInt(attendanceEventArray[1]), attendanceEventArray[0],
													attendanceEventArray[2], academicYearID);

											if (attendance == 0) {
												statusMessage = daoInf.insertAttendance(AttendanceEvent,
														academicYearID);
											} else {
												System.out.println(
														"Attendance details already exist in table. Please Add new details for Attendance");
												statusMessage = "success";
											}
										}
									}
								}
								statusMessage = "success";
							}
						}

						if (form.getNewLibrary() == null) {

							System.out.println("No new library added");

							statusMessage = "success";

						} else {

							for (int i = 0; i < form.getNewLibrary().length; i++) {

								statusMessage = daoInf.insertLibrary(form.getNewLibrary()[i], academicYearID);
							}
						}

						return statusMessage;

					} else {
						System.out.println("Failed to insert AcademicYear detail into AcademicYear table.");
						statusMessage = "error";
						return statusMessage;
					}
				}

			} else {
				System.out.println("AcademicYear name already exist in table. Please Add new name for AcademicYear");
				statusMessage = "input";
				return statusMessage;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			statusMessage = "error";
			return statusMessage;

		}

	}

	public String registerExamination(ConfigurationForm conform, int academicYearID) {

		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();

		if (conform.getNewTerm() == null) {

			System.out.println("No new examination added");

			statusMessage = "success";

		} else {

			for (int i = 0; i < conform.getNewTerm().length; i++) {

				statusMessage = daoInf.insertExamination(conform.getNewTerm()[i], conform.getNewExamName()[i],
						conform.getNewExamType()[i], conform.getNewStartDate()[i], conform.getNewEndDate()[i],
						academicYearID);

				if (statusMessage.equalsIgnoreCase("success")) {

					System.out.println("Successfully inserted Examination detail into Examination Table.");

					statusMessage = "success";
				} else {

					System.out.println("Failed to add Examination details into Examination table");

					statusMessage = "error";
				}
			}
		}
		/*
		 * update inserted row with present ExaminationList retriving from Examination
		 * table
		 */
		if (conform.getEditExamID() == null) {

			System.out.println("No new examination added");

			statusMessage = "success";

		} else {
			for (int i = 0; i < conform.getEditExamID().length; i++) {

				statusMessage = daoInf.updateExamination(conform.getEditTerm()[i], conform.getEditExamNameID()[i],
						conform.getEditExamTypeID()[i], conform.getEditStartDateID()[i], conform.getEditEndDateID()[i],
						academicYearID, Integer.parseInt(conform.getEditExamID()[i]));

				if (statusMessage.equalsIgnoreCase("success")) {

					System.out.println("Successfully updated Examination detail into Examination Table.");

					statusMessage = "success";
				} else {

					System.out.println("Failed to update Examination details into Examination table");

					statusMessage = "error";
				}

			}
		}

		return statusMessage;

	}

	public String registerAcademicYearList(ConfigurationForm conform, String activityStatus, int AcademicYearID) {

		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();
		int AYClassID = 0;
		if (conform.getNewStandard() == null) {

			System.out.println("No new AcademicYear List added");

			statusMessage = "success";

		} else {

			for (int i = 0; i < conform.getNewStandard().length; i++) {

				statusMessage = daoInf.insertAcademicYearList(Integer.parseInt(conform.getNewStandard()[i]),
						Integer.parseInt(conform.getNewDivision()[i]), Integer.parseInt(conform.getNewTeacher()[i]),
						AcademicYearID, activityStatus);

				if (statusMessage.equalsIgnoreCase("success")) {

					System.out.println("Successfully inserted AcademicYear List detail into AYClass Table.");

					/*
					 * Retrieving treatment ID based on agent in order to insert it into
					 * Configuration table
					 */
					AYClassID = daoInf.retrieveAYClassID();

					String ConfigEvent = conform.getNewConfID()[i];

					System.out.println("Configuration event before isnerting ... " + ConfigEvent);

					if (ConfigEvent == null || ConfigEvent == "") {

						System.out.println("No Configuration event details added.");

					} else {

						if (ConfigEvent.isEmpty()) {

							System.out.println("No Configuration event details added.");

						} else {

							/*
							 * Checking whether Configuration starts with *, if so, remove first * and
							 * proceed further
							 */
							if (ConfigEvent.startsWith("*")) {
								ConfigEvent = ConfigEvent.substring(1);
							}

							/*
							 * Checking whether adverseEvent contains * in it, if so, splitting the String
							 * by '*' and inserting into ConfigEvent table
							 */
							System.out.println("Adverse event string ::: " + ConfigEvent);
							if (ConfigEvent.contains("*")) {

								String[] confEventArray = ConfigEvent.split("\\*");

								for (int j = 0; j < confEventArray.length; j++) {

									statusMessage = daoInf.insertConfigDetails(confEventArray[j], AYClassID);

								}

							} else {

								statusMessage = daoInf.insertConfigDetails(ConfigEvent, AYClassID);

							}

						}
					}

					statusMessage = "success";

				} else {

					System.out.println("Failed to add AcademicYear List details into AYClass table");

					statusMessage = "error";
				}

			}

		}

		/*
		 * update inserted row with present AcademicYear List retriving from Examination
		 * table
		 */
		if (conform.getAYClassID1() == null) {

			System.out.println("No new AcademicYear List added");

			statusMessage = "success";

		} else {
			for (int i = 0; i < conform.getAYClassID1().length; i++) {

				int ayClassID = Integer.parseInt(conform.getAYClassID1()[i]);

				statusMessage = daoInf.updateAcademicYearList(Integer.parseInt(conform.getEditStandardID()[i]),
						Integer.parseInt(conform.getEditDivisionID()[i]),
						Integer.parseInt(conform.getEditTeacherID()[i]), AcademicYearID, ayClassID);

				if (statusMessage.equalsIgnoreCase("success")) {

					System.out.println("Successfully updated AcademicYear List detail into AYClass Table.");

					// Checking whether new subject teacher configuration added for the particular
					// ayClass or not, if added the iterating over the pattern and storing it in
					// relation with the current ayCLassID else proceed further
					String configText = conform.getEditConfText()[i];

					if (configText == null || configText == "") {

						System.out.println("No subject teacher configuration added for ayClassID : " + ayClassID);
						statusMessage = "success";

					} else if (configText.isEmpty()) {

						System.out.println("No subject teacher configuration added for ayClassID : " + ayClassID);
						statusMessage = "success";

					} else {

						/*
						 * Check whether configText starts with * if so, remove first * and proceed to
						 * insert into AYSubject table for the current ayClassID
						 */
						if (configText.startsWith("*")) {
							configText = configText.substring(1);
						}

						System.out.println("Subject teacher config text after first * removal ::: " + configText);

						/*
						 * Check whether configText contains *, if so then splitting according to * and
						 * inserting each subject teacher combination into AYSUbject table else
						 * inserting a single subject teacher combination into AYSubject table
						 */
						if (configText.contains("*")) {

							String array[] = configText.split("\\*");

							for (int j = 0; j < array.length; j++) {
								statusMessage = daoInf.insertConfigDetails(array[j], ayClassID);
							}

						} else {
							statusMessage = daoInf.insertConfigDetails(configText, ayClassID);
						}

					}

				} else {

					System.out.println("Failed to update AcademicYear List details into AYClass table");

					statusMessage = "error";
				}

			}
		}

		return statusMessage;
	}

	public String transferStudent(StudentForm studform) {
		// TODO Auto-generated method stub
		StuduntDAOInf daoInf = new StudentDAOImpl();

		for (int i = 0; i < studform.getNewStudentID().length; i++) {

			int ayID = 0;
			ayID = daoInf.retrieveAyIDByStandardID(Integer.parseInt(studform.getNewStandardID()[i]),
					Integer.parseInt(studform.getNewDivisionID()[i]), studform.getAyID());

			System.out.println("ayID....: " + ayID + "-" + studform.getAyID() + "-"
					+ Integer.parseInt(studform.getNewStandardID()[i]) + "-"
					+ Integer.parseInt(studform.getNewDivisionID()[i]));

			String PhysicalActivities = studform.getNewPhysicalActivities()[i].replace("=", ",");

			if (ayID == 0) {
				statusMessage = "success";
			} else {

				statusMessage = daoInf.insertNewStudentDetails(Integer.parseInt(studform.getNewStudentID()[i]),
						PhysicalActivities, studform.getNewCreativeActivities()[i],
						studform.getNewCompulsoryActivities()[i], Double.parseDouble(studform.getNewWeight()[i]),
						Double.parseDouble(studform.getNewHeight()[i]), ayID);

			}

			if (statusMessage.equalsIgnoreCase("success")) {

				System.out.println("Successfully inserted New Student Details into Registration Table.");

				statusMessage = "success";

			} else {

				System.out.println("Failed to insert New Student Details into Registration table");

				statusMessage = "error";
			}

		}
		return statusMessage;
	}

	public String configureSubjectAssessment(ConfigurationForm conform) {

		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();

		int ayCLassID = daoInf.retrieveAYCLassID(conform.getStandardID(), conform.getDivisionID(),
				conform.getAcademicYearID());

		if (conform.getNewScaleTo() == null) {
			System.out.println("No details added");

			statusMessage = "success";

		} else {

			/*
			 * System.out.println("ayCLassID......."+ayCLassID+"std..."+conform.
			 * getStandardID()+"divid..."+conform.getDivisionID()+"AcademicYearID..."+
			 * conform.getAcademicYearID());
			 */

			/* System.out.println("..." + conform.getNewScaleTo().length); */

			for (int i = 0; i < conform.getNewScaleTo().length; i++) {

				String maxMark = conform.getNewMaximumMark()[i];

				if (maxMark == null || maxMark == "") {
					maxMark = "0";
				} else if (maxMark.isEmpty()) {
					maxMark = "0";
				}

				String gradeBase = conform.getNewGradeBase()[i];

				if (gradeBase == null || gradeBase == "") {
					gradeBase = "0";
				} else if (gradeBase.isEmpty()) {
					gradeBase = "0";
				}

				String scaleTo = conform.getNewScaleTo()[i];

				if (scaleTo == null || scaleTo == "") {
					scaleTo = "0";
				} else if (scaleTo.isEmpty()) {
					scaleTo = "0";
				}

				statusMessage = daoInf.insertSubjectAssessment(conform.getExaminationID(),
						Integer.parseInt(conform.getNewSubjectID()[i]), Double.parseDouble(maxMark),
						Double.parseDouble(scaleTo), Integer.parseInt(gradeBase), ayCLassID);

				if (statusMessage.equals("success")) {
					System.out.println("Successfully added subject assesment details");

					String ActivitiesString = conform.getActivityString();

					if (ActivitiesString.startsWith("*")) {
						ActivitiesString = ActivitiesString.substring(1);
					}

					boolean check = daoInf
							.verifySubjectIDIsCoScholastic(Integer.parseInt(conform.getNewSubjectID()[i]));

					if (check) {

						String subStr = Integer.parseInt(conform.getNewSubjectID()[i]) + "$";

						String[] newSubStr = ActivitiesString.split("\\*");

						for (int k = 0; k < newSubStr.length; k++) {

							if (newSubStr[k].startsWith(subStr)) {

								String[] newSubjectString = newSubStr[k].split("\\$");

								int subjectAssessmentID = daoInf.retrievesubjectAssessmentID(conform.getExaminationID(),
										Integer.parseInt(newSubjectString[0]), ayCLassID);

								statusMessage = daoInf.insertActivityAssessment(subjectAssessmentID,
										Integer.parseInt(newSubjectString[1]), Integer.parseInt(newSubjectString[2]));

								if (statusMessage.equals("success")) {

									System.out.println("Successfully added Activity Assesment details");

								} else {
									System.out.println("Failed to add Activity Assessment details");
								}
							}
						}
					}

				} else {

					System.out.println("Failed to add subject assessment details");
				}
			}
		}

		if (conform.getEditSubjectName() == null) {

			System.out.println("No details added");

			statusMessage = "success";

			return statusMessage;

		} else {
			for (int j = 0; j < conform.getEditSubjectName().length; j++) {

				String editMaxMark = conform.getEditMaximumMark()[j];

				if (editMaxMark == null || editMaxMark == "") {
					editMaxMark = "0";
				} else if (editMaxMark.isEmpty()) {
					editMaxMark = "0";
				}

				String editGradeBase = conform.getEditGradeBase()[j];

				if (editGradeBase == null || editGradeBase == "") {
					editGradeBase = "0";
				} else if (editGradeBase.isEmpty()) {
					editGradeBase = "0";
				}

				String editScaleTo = conform.getEditScaleTo()[j];

				if (editScaleTo == null || editScaleTo == "") {
					editScaleTo = "0";
				} else if (editScaleTo.isEmpty()) {
					editScaleTo = "0";
				}

				statusMessage = daoInf.updateSubjectAssessment(conform.getExaminationID(),
						Integer.parseInt(conform.getEditSubjectID()[j]), Double.parseDouble(editMaxMark),
						Double.parseDouble(editScaleTo), Integer.parseInt(editGradeBase), ayCLassID,
						Integer.parseInt(conform.getEditSubjectAssessmentID()[j]));

				if (statusMessage.equals("success")) {
					System.out.println("Successfully Updated subject assesment details");

				} else {
					System.out.println("Failed to add subject assessment details");
				}
			}

			String ActivitiesString = conform.getActivityString();

			if ((ActivitiesString == null) || (ActivitiesString == "")) {
				System.out.println("Please add Activities and total marks for the Co-Scholostic Subject");

			} else if (ActivitiesString.isEmpty()) {
				System.out.println("Please add Activities and total marks for the Co-Scholostic Subject");

			} else {

				if (ActivitiesString.startsWith("*")) {
					ActivitiesString = ActivitiesString.substring(1);
				}

				if (ActivitiesString.contains("*")) {

					String[] newSubStr = ActivitiesString.split("\\*");

					for (int k = 0; k < newSubStr.length; k++) {

						String[] newSubjectString = newSubStr[k].split("\\$");

						statusMessage = daoInf.insertActivityAssessment(Integer.parseInt(newSubjectString[0]),
								Integer.parseInt(newSubjectString[1]), Integer.parseInt(newSubjectString[2]));

						if (statusMessage.equals("success")) {

							System.out.println("Successfully added Activity Assesment details");

						} else {
							System.out.println("Failed to add Activity Assessment details");
						}
					}
				} else {

					String[] newSubStr = ActivitiesString.split("\\$");

					statusMessage = daoInf.insertActivityAssessment(Integer.parseInt(newSubStr[0]),
							Integer.parseInt(newSubStr[1]), Integer.parseInt(newSubStr[2]));

					if (statusMessage.equals("success")) {

						System.out.println("Successfully added Activity Assesment details");

					} else {
						System.out.println("Failed to add Activity Assessment details");
					}
				}
			}

			return statusMessage;
		}

	}

	public String addStudentAssessment(ConfigurationForm conform, int activityAssessmentCheck) {

		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();

		// Retrieving subjectAssessmentID based on examinationID, ayClassID and subject
		// name from SubjectAssessment table

		/*
		 * System.out.println("conform.getAYClassID():" + conform.getAYClassID());
		 * System.out.println("conform.getExaminationID():" +
		 * conform.getExaminationID()); System.out.println("conform.getSubjectID():" +
		 * conform.getSubjectID());
		 */

		if (conform.getStudID() == null) {
			System.out.println("No student details found.");

			statusMessage = "success";

			// return statusMessage;
		} else {

			for (int i = 0; i < conform.getStudID().length; i++) {

				// int subjectAssessmentID =
				// daoInf.retrieveSubjectAssessmentID(conform.getExaminationID(),
				// conform.getAYClassID(), Integer.parseInt(conform.getNewSubjectID()[i]));

				int subjectAssessmentID = Integer.parseInt(conform.getNewSubjAssmntID()[i]);

				double totalMarks = 0D;

				String totalMarksStr = conform.getNewTotalMarksID()[i];

				if (totalMarksStr == null || totalMarksStr == "") {
					totalMarks = 0D;
				} else if (totalMarksStr.isEmpty()) {
					totalMarks = 0D;
				} else {
					totalMarks = Double.parseDouble(totalMarksStr);
				}

				String value = conform.getMarksObtainedArr()[i];

				double marksObtained = 0D;

				if (value == null || value == "") {
					value = "0";
				} else if (value.isEmpty()) {
					value = "0";
				}

				// System.out.println("conform.getNewAbsentFlag()[i] :

				String Flag = conform.getNewAbsentFlag()[i];
				int absentFlag = Integer.parseInt(Flag);

				// System.out.println("absentFlag" + absentFlag);

				if (absentFlag == 1) {
					absentFlag = 1;
					marksObtained = 0D;

				} else if (absentFlag == 0) {
					absentFlag = 0;
					marksObtained = Double.parseDouble(value);
				} else {
					absentFlag = 0;
					marksObtained = 0D;
				}
				/*
				 * System.out.println("absentFlag" + absentFlag);
				 * System.out.println("Add subjectAssessmentID:" + subjectAssessmentID);
				 */

				/*
				 * retrieving scale to from SubjectAssessment table based on subjectAssessmentID
				 */
				double scaleTo = daoInf.retrieveSubjectScaleTo(subjectAssessmentID);

				/* System.out.println("Scale to value..." + scaleTo); */

				double scaledMarks = 0D;

				/*
				 * Checking whether gradeBased is 1 or 0, if 0 then calculate scaledMarks marks
				 * by dividing marksObtained by totalMarks and multiplying the result by
				 * scaleTo, else setting 0 to scaledMarks
				 */
				if (Integer.parseInt(conform.getNewGradeBase()[i]) == 0) {
					scaledMarks = (marksObtained / totalMarks) * scaleTo;

					String numberAsString = Double.toString(scaledMarks);
					String marks[] = numberAsString.split("\\.");

					if (Long.parseLong(marks[1]) > 0) {

						scaledMarks = Double.parseDouble(marks[0]) + 1;

					} else {

						scaledMarks = Double.parseDouble(marks[0]);
					}

				} else {
					scaledMarks = 0D;
				}

				// System.out.println("Scaled marks are..." + scaledMarks);

				// Retrieving registrationID based on studentID and status as Active from
				// Registration table
				// System.out.println("getStudID : " + conform.getStudID()[i]);
				int registrationID = daoInf.retrieveRegistrationID(Integer.parseInt(conform.getStudID()[i]));
				System.out.println("registrationID : " + registrationID);

				/*
				 * INserting details into StudentAssessment table
				 */

				System.out.println(
						"values are here: " + subjectAssessmentID + "--" + registrationID + "--" + marksObtained + "--"
								+ scaledMarks + "--" + absentFlag + "--" + conform.getGradeObtainedArr()[i]);

				boolean marksCheck = daoInf.checkMarksPresent(subjectAssessmentID, registrationID);

				if (!marksCheck) {
					statusMessage = daoInf.insertStudentAssessmentDetails(subjectAssessmentID, registrationID,
							marksObtained, conform.getGradeObtainedArr()[i], scaledMarks, absentFlag);

					if (statusMessage.equals("success")) {

						System.out.println("Student Assessment details added successfully");

						if (activityAssessmentCheck == 1) {

							String ActivityAssessmentMarks = conform.getActivityAssessmentArr()[i];

							if (ActivityAssessmentMarks == null || ActivityAssessmentMarks == "") {
								continue;
							} else if (ActivityAssessmentMarks.isEmpty()) {
								continue;
							} else {
								if (ActivityAssessmentMarks.startsWith("@")) {
									ActivityAssessmentMarks = ActivityAssessmentMarks.substring(1);
								}

								String[] ActivityMarks = ActivityAssessmentMarks.split("@");

								int studentAssessmentID = daoInf.retrievestudentAssessmentID(subjectAssessmentID,
										registrationID);

								for (int k = 0; k < ActivityMarks.length; k++) {

									String[] ActivityNewMarks = ActivityMarks[k].split("=");

									if (ActivityNewMarks[1] == null || ActivityNewMarks[1] == "") {

										ActivityNewMarks[1] = "0";
									} else if (ActivityNewMarks[1].isEmpty()) {

										ActivityNewMarks[1] = "0";
									}

									statusMessage = daoInf.insertStudentActivityAssessmentDetails(studentAssessmentID,
											Integer.parseInt(ActivityNewMarks[0]),
											Integer.parseInt(ActivityNewMarks[1]));
								}
							}
						}

					} else {

						System.out.println("Failed to add student assessment details");

					}
				}

			}
		}

		/*
		 * Check whether edit fields are empty or not if empty then proceed further else
		 * update records into StudentAssessment table
		 */
		if (conform.getStudAssessmntID() == null) {
			System.out.println("No student edit details found.");

			statusMessage = "success";

		} else {

			for (int i = 0; i < conform.getStudAssessmntID().length; i++) {

				String value = conform.getEditMarksObtainedArr()[i];

				if (value == null || value == "") {
					value = "0";
				} else if (value.isEmpty()) {
					value = "0";
				}

				double marksObtained = 0D;

				String editFlag = conform.getEditAbsentFlag()[i];
				int editabsentFlag = Integer.parseInt(editFlag);

				/* System.out.println("editabsentFlag" + editabsentFlag); */

				if (editabsentFlag == 1) {
					editabsentFlag = 1;
					marksObtained = 0D;

				} else if (editabsentFlag == 0) {
					editabsentFlag = 0;
					marksObtained = Double.parseDouble(value);
				} else {
					editabsentFlag = 0;
					marksObtained = 0D;
				}

				// int Retest =Integer.parseInt(conform.getEditRetestArr()[i]);

				/* check if student having retest or not */

				if (conform.getEditRetestArr()[i].equals("1")) {

					// Disabling old studentAssessmentID record
					daoInf.disableOldStudentID(Integer.parseInt(conform.getStudAssessmntID()[i]));

					int subjectAssessmentID = Integer.parseInt(conform.getEditSubjAssmntID()[i]);

					double totalMarks = 0D;

					String totalMarksStr = conform.getEditTotalMarks()[i];

					if (totalMarksStr == null || totalMarksStr == "") {
						totalMarks = 0D;
					} else if (totalMarksStr.isEmpty()) {
						totalMarks = 0D;
					} else {
						totalMarks = Double.parseDouble(totalMarksStr);
					}
					// System.out.println("conform.getEditMarksObtainedArr()[i] :
					// "+conform.getEditMarksObtainedArr()[i]);
					String value1 = conform.getEditMarksObtainedArr()[i];

					double marksObtained1 = 0D;

					if (value1 == null || value1 == "") {
						value1 = "0";
					} else if (value1.isEmpty()) {
						value1 = "0";

					}

					String Flag = conform.getEditAbsentFlag()[i];
					int absentFlag = Integer.parseInt(Flag);

					// System.out.println("absentFlag" + absentFlag);

					if (absentFlag == 1) {
						absentFlag = 1;
						marksObtained1 = 0D;

					} else if (absentFlag == 0) {
						absentFlag = 0;
						marksObtained1 = Double.parseDouble(value1);
					} else {
						absentFlag = 0;
						marksObtained1 = 0D;
					}
					/*
					 * System.out.println("absentFlag" + absentFlag);
					 * System.out.println("edit subjectAssessmentID:" + subjectAssessmentID);
					 */

					/*
					 * retrieving scale to from SubjectAssessment table based on subjectAssessmentID
					 */
					double scaleTo = daoInf.retrieveSubjectScaleTo(subjectAssessmentID);

					// System.out.println("Scale to value..." + scaleTo);

					double scaledMarks = 0D;

					/*
					 * Checking whether gradeBased is 1 or 0, if 0 then calculate scaledMarks marks
					 * by dividing marksObtained by totalMarks and multiplying the result by
					 * scaleTo, else setting 0 to scaledMarks
					 */
					if (Integer.parseInt(conform.getEditGradeBase()[i]) == 0) {
						scaledMarks = (marksObtained1 / totalMarks) * scaleTo;

						String numberAsString = Double.toString(scaledMarks);
						String marks[] = numberAsString.split("\\.");

						if (Long.parseLong(marks[1]) > 0) {

							scaledMarks = Double.parseDouble(marks[0]) + 1;

						} else {

							scaledMarks = Double.parseDouble(marks[0]);
						}

					} else {
						scaledMarks = 0D;
					}

					// Retrieving registrationID based on studentID and status as Active from
					// Registration table
					// System.out.println("getStudAssessmntID : " +
					// conform.getStudAssessmntID()[i]);

					/*
					 * INserting details into StudentAssessment table
					 */

					boolean marksCheck = daoInf.checkMarksPresent(subjectAssessmentID,
							Integer.parseInt(conform.getEditRegsitrationID()[i]));

					if (!marksCheck) {
						statusMessage = daoInf.insertStudentAssessmentDetails(subjectAssessmentID,
								Integer.parseInt(conform.getEditRegsitrationID()[i]), marksObtained1,
								conform.getEditGradeObtainedArr()[i], scaledMarks, absentFlag);
					}

					if (statusMessage.equals("success")) {

						System.out.println("Student Assessment details added successfully");

					} else {

						System.out.println("Failed to add student assessment details");

					}

				} else {

					int subjectAssessmentID = Integer.parseInt(conform.getEditSubjAssmntID()[i]);

					double totalMarks = 0D;

					String totalMarksStr = conform.getEditTotalMarks()[i];

					if (totalMarksStr == null || totalMarksStr == "") {
						totalMarks = 0D;
					} else if (totalMarksStr.isEmpty()) {
						totalMarks = 0D;
					} else {
						totalMarks = Double.parseDouble(totalMarksStr);
					}

					// System.out.println("Edit subjectAssessmentID:" + subjectAssessmentID);

					double scaleTo = Double.parseDouble(conform.getEditScaleTo()[i]);

					// System.out.println("Scale to value..." + scaleTo);

					double scaledMarks = 0D;

					/*
					 * Checking whether gradeBased is 1 or 0, if 0 then calculate scaledMarks marks
					 * by dividing marksObtained by totalMarks and multiplying the result by
					 * scaleTo, else setting 0 to scaledMarks
					 */
					if (Integer.parseInt(conform.getEditGradeBase()[i]) == 0) {
						scaledMarks = (marksObtained / totalMarks) * scaleTo;

						String numberAsString = Double.toString(scaledMarks);
						String marks[] = numberAsString.split("\\.");

						if (Long.parseLong(marks[1]) > 0) {

							scaledMarks = Double.parseDouble(marks[0]) + 1;

						} else {

							scaledMarks = Double.parseDouble(marks[0]);
						}

					} else {
						scaledMarks = 0D;
					}
					/*
					 * INserting details into StudentAssessment table
					 */
					/*
					 * System.out.println("editabsentFlag : "+Integer.parseInt(conform.
					 * getStudAssessmntID()[i])+"-"+marksObtained+"-"+conform.
					 * getEditGradeObtainedArr()[i]+"-"+scaledMarks+"-"+editabsentFlag);
					 */
					statusMessage = daoInf.updateStudentAssessmentDetails(
							Integer.parseInt(conform.getStudAssessmntID()[i]), marksObtained,
							conform.getEditGradeObtainedArr()[i], scaledMarks, editabsentFlag);

					if (statusMessage.equals("success")) {

						System.out.println("Student Assessment details updated successfully");

					} else {

						System.out.println("Failed to update student assessment details");

					}

				}
			}

			if (conform.getStudentActivityAssessmentIDArr() == null) {
				System.out.println("No student edit details found.");

				statusMessage = "success";

				return statusMessage;

			} else {

				for (int i = 0; i < conform.getStudentActivityAssessmentIDArr().length; i++) {

					String ActivityAssessmentMarks = conform.getStudentActivityAssessmentIDArr()[i];
					System.out.println("ActivityAssessmentMarks: " + ActivityAssessmentMarks);
					String[] ActivityMarks = ActivityAssessmentMarks.split("=");

					statusMessage = daoInf.updateStudentActivityAssessmentDetails(Double.parseDouble(ActivityMarks[1]),
							Integer.parseInt(ActivityMarks[3]));

					if (statusMessage.equals("success")) {

						System.out.println("Student Activity Assessment Details updated successfully");

					} else {

						System.out.println("Failed to Update Student Activity Assessment Details");

					}

				}
			}
		}

		return statusMessage;
	}

	public String editAcademicYear(LoginForm form, int organizationID) {
		// TODO Auto-generated method stub
		LoginDAOInf daoInf = new LoginDAOImpl();

		int status = 0;

		try {

			/*
			 * Check whether AcademicYears Name present in AcademicYear table or not if name
			 * present then give error msg & dont allow to insertion
			 */
			status = daoInf.CheckAcademicYearNameEdit(form.getYearName(), form.getAcademicYearID(), organizationID);

			if (status == 0) {
				/*
				 * Inserting AcademicYear detail into User table
				 */
				statusMessage = daoInf.updateAcademicYear(form);
				if (statusMessage.equalsIgnoreCase("success")) {

					System.out.println("Successfully updated AcademicYear detail into AcademicYear Table.");

					return statusMessage;

				} else {
					System.out.println("Failed to update AcademicYear detail into AcademicYear table.");
					statusMessage = "error";
					return statusMessage;
				}

			} else {

				System.out.println("AcademicYear name already exist in table. Please update new name for AcademicYear");
				statusMessage = "input";
				return statusMessage;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			statusMessage = "error";
			return statusMessage;
		}
	}

	public String bulkUpdateStudent(StudentForm studform, String activityStatus) {
		StuduntDAOInf daoInf = new StudentDAOImpl();
		/*
		 * Updating Bulk Student Details In Registration table
		 */
		for (int j = 0; j < studform.getNewStudentID().length; j++) {

			if (studform.getToRollNumber()[j] == null || studform.getToRollNumber()[j] == "") {
				continue;
			} else if (studform.getToRollNumber()[j].isEmpty()) {
				continue;
			} else {
				System.out.println("Values: " + studform.getNewCreativeActivities()[j] + "-"
						+ studform.getNewPhysicalActivities()[j]);
				
				String compulsoryActivity = "";
				
				if(studform.getNewCompulsoryActivities() == null ) {
					compulsoryActivity = "";
				}else {
					compulsoryActivity = studform.getNewCompulsoryActivities()[j];
				}
				
				statusMessage = daoInf.updateBulkStudentRegistration(Integer.parseInt(studform.getToRollNumber()[j]),
						studform.getNewCreativeActivities()[j], studform.getNewPhysicalActivities()[j],
						compulsoryActivity, 
						Double.parseDouble(studform.getNewHeight()[j]),
						Double.parseDouble(studform.getNewWeight()[j]), Integer.parseInt(studform.getNewStudentID()[j]),
						activityStatus);

				if (statusMessage.equalsIgnoreCase("success")) {
					/*
					 * Updating Bulk Student Details In StudentDetails table
					 */

					statusMessage = daoInf.updateBulkStudentDetails(studform.getNewGrNumber()[j],
							Integer.parseInt(studform.getNewStudentID()[j]));
					if (statusMessage.equalsIgnoreCase("success")) {

						System.out.println("Successfully updated Medical Condition detail into MedicalHistory Table.");

						statusMessage = "success";

					} else {

						System.out.println("Failed to updated Medical Condition detail into MedicalHistory table.");

						statusMessage = "error";
					}
				} else {

					System.out.println("Failed to update Bulk Student Details In Registration table.");

					statusMessage = "error";
				}
			}
		}
		return statusMessage;
	}

	public String addStudentAttendance(StudentForm studform, int AcademicYearID) {

		StuduntDAOInf daoInf = new StudentDAOImpl();
		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		System.out.println("studform.getNewStudAssmntID():" + studform.getStudID());

		if (studform.getStudID() == null) {
			System.out.println("No student details found.");

			statusMessage = "success";

			// return statusMessage;
		} else {

			for (int i = 0; i < studform.getStudID().length; i++) {

				// int subjectAssessmentID =
				// daoInf.retrieveSubjectAssessmentID(conform.getExaminationID(),
				// conform.getAYClassID(), Integer.parseInt(conform.getNewSubjectID()[i]));

				String value = studform.getDaysPresentArr()[i];

				int daysPresent = 0;

				if (value == null || value == "") {
					daysPresent = 0;
				} else if (value.isEmpty()) {
					daysPresent = 0;
				} else {
					daysPresent = Integer.parseInt(value);
				}

				// Retrieving registrationID based on studentID and status as Active from
				// Registration table

				int registrationID = daoInf.retrieveRegistrationID(Integer.parseInt(studform.getStudID()[i]));

				int attendanceID = daoInf.retrieveattendanceID(studform.getTerm(), studform.getWorkingMonth(),
						AcademicYearID, studform.getStandardID());

				/*
				 * INserting details into StudentAssessment table
				 */
				statusMessage = daoInf.insertStudentAttendanceDetails(registrationID, daysPresent, attendanceID);

				if (statusMessage.equals("success")) {

					System.out.println("Student Attandance details added successfully");

				} else {

					System.out.println("Failed to add student Attandance details");

				}

			}
			return statusMessage;
		}

		/*
		 * Check whether edit fields are empty or not if empty then proceed further else
		 * update records into StudentAssessment table
		 */
		if (studform.getStudAttndsID() == null) {
			System.out.println("No student edit details found.");

			statusMessage = "success";

			return statusMessage;
		} else {

			for (int i = 0; i < studform.getStudAttndsID().length; i++) {

				String value = studform.getEditdaysPresentArr()[i];

				int daysPresent = 0;

				if (value == null || value == "") {
					daysPresent = 0;
				} else if (value.isEmpty()) {
					daysPresent = 0;
				} else {
					daysPresent = Integer.parseInt(value);
				}

				/* update records into StudentAttandance table */

				statusMessage = daoInf.updateStudentAttandanceDetails(Integer.parseInt(studform.getStudAttndsID()[i]),
						daysPresent);

				if (statusMessage.equals("success")) {

					System.out.println("Student Attandance details updated successfully");

				} else {

					System.out.println("Failed to update student Attandance details");
				}

			}
			return statusMessage;
		}

	}

	public String editAttendance(LoginForm form, int organizationID, String realPath, HttpServletRequest request) {
		// TODO Auto-generated method stub
		LoginDAOInf daoInf = new LoginDAOImpl();

		int attendance = 0;

		try {
			if (form.getNewstandardID() == null) {

				System.out.println("No new Attendance added");

				statusMessage = "success";

			} else {

				System.out.println("inside editAttendance else loop");
				for (int i = 0; i < form.getNewstandardID().length; i++) {

					attendance = daoInf.CheckAttendance(Integer.parseInt(form.getNewstandardID()[i]),
							form.getEditTerm(), form.getNewMonth()[i], form.getAcademicYearID());

					if (attendance == 0) {
						statusMessage = daoInf.insertAttendanceNew(form.getEditTerm(), form.getNewMonth()[i],
								Integer.parseInt(form.getNewWorkdays()[i]), form.getAcademicYearID(),
								Integer.parseInt(form.getNewstandardID()[i]));
					} else {
						System.out.println(
								"Attendance details already exist in table. Please Add new details for Attendance");
						statusMessage = "success";
					}
				}
			}

			statusMessage = "success";

			if (form.getEditTermID() == null) {

				System.out.println("No new  editAttendance added");

				statusMessage = "success";

			} else {

				System.out.println("inside editAttendance else else loop");

				for (int i = 0; i < form.getEditTermID().length; i++) {

					System.out.println("inside editAttendance else else for loop");

					statusMessage = daoInf.updateAttendance(Integer.parseInt(form.getEditworkingDaysID()[i]),
							Integer.parseInt(form.getEditTermID()[i]));
				}

				if (statusMessage.equalsIgnoreCase("success")) {

					System.out.println("Successfully updated Attendance detail into Attendance Table.");

					return statusMessage;

				} else {
					System.out.println("Failed to updated Attendance detail into Attendance table.");
					statusMessage = "error";
					return statusMessage;
				}

			}
			return statusMessage;
		} catch (Exception exception) {
			exception.printStackTrace();
			statusMessage = "input";
			return statusMessage;

		}

	}

	public String activateAcademicYear(LoginForm form, int academicYearID, int organizationID) {
		// TODO Auto-generated method stub
		LoginDAOInf daoInf = new LoginDAOImpl();

		int status = 0;

		try {

			/*
			 * Check whether previous AcademicYears Activity status is Inactive or not if
			 * not the set it is Inactive then Activate DRAFT Status to ACTIVE
			 */
			int academicYear = daoInf.getActiveacademicYearID(organizationID);

			if (academicYear == 0) {
				statusMessage = daoInf.UpdateAcademicYear(academicYearID);

				if (statusMessage.equalsIgnoreCase("success")) {

					System.out.println("Successfully Updated Draft AcademicYear into AcademicYear Table.");

					return statusMessage;

				} else {

					System.out.println("Failed to Update Draft AcademicYearID into AcademicYear table.");
					statusMessage = "error";
					return statusMessage;
				}
			} else {

				status = daoInf.InActiveAcademicYearStatus(academicYear);

				if (status == 1) {

					List<Integer> AYClassID = daoInf.getAYClassIDList(academicYear);

					for (int i = 0; i < AYClassID.size(); i++) {
						int AYID = AYClassID.get(i);

						daoInf.UpdateAYClassID(AYID);
						daoInf.UpdateRegistrationAYClassID(AYID);
						System.out.println("Successfully Inactivated Active AYClassID into AYClass table.");
					}

				} else {
					System.out.println("ActivityStatus Could not changed to InActive...");
					statusMessage = "input";
					return statusMessage;
				}

				/*
				 * Activate Draft AcademicYear detail into User table
				 */
				statusMessage = daoInf.UpdateAcademicYear(academicYearID);
				if (statusMessage.equalsIgnoreCase("success")) {

					System.out.println("Successfully Updated Draft AcademicYear into AcademicYear Table.");

					List<Integer> DraftAYClassID = daoInf.getDraftAYClassIDList(academicYearID);

					for (int j = 0; j < DraftAYClassID.size(); j++) {
						int DraftAYID = DraftAYClassID.get(j);

						daoInf.UpdateDraftAYClassID(DraftAYID);
						daoInf.UpdateRegistrationDraftAYClassID(DraftAYID);
						System.out.println("Successfully Activated Draft AYClassID into AYClass table.");
					}

					return statusMessage;

				} else {
					System.out.println("Failed to Update Draft AcademicYearID into AcademicYear table.");
					statusMessage = "error";
					return statusMessage;
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			statusMessage = "input";
			return statusMessage;

		}
	}

	public String addTimeTable(LoginForm form) {

		LoginDAOInf daoInf = new LoginDAOImpl();

		if (form.getNewExam() == null) {

			System.out.println("No new Time Table added");

			statusMessage = "success";

		} else {

			for (int i = 0; i < form.getNewExam().length; i++) {

				statusMessage = daoInf.insertTimeTable(Integer.parseInt(form.getNewstandardID()[i]),
						Integer.parseInt(form.getNewExam()[i]), form.getNewDate()[i], form.getNewsubjectID()[i]);

				if (statusMessage.equalsIgnoreCase("success")) {

					System.out.println("Successfully inserted AcademicYear List detail into AYClass Table.");

					statusMessage = "success";

				} else {

					System.out.println("Failed to add AcademicYear List details into AYClass table");

					statusMessage = "error";
				}
			}
		}

		/*
		 * update inserted row with present AcademicYear List retriving from Examination
		 * table
		 */
		if (form.getTimeTableID1() == null) {

			System.out.println("No new AcademicYear List added");

			statusMessage = "success";

		} else {
			for (int i = 0; i < form.getTimeTableID1().length; i++) {

				int timeTableID = Integer.parseInt(form.getTimeTableID1()[i]);

				statusMessage = daoInf.updateTimeTableList(Integer.parseInt(form.getEditStandardID()[i]),
						form.getEditExamDate()[i], form.getEditSubject()[i], Integer.parseInt(form.getEditExamID()[i]),
						timeTableID);

				if (statusMessage.equalsIgnoreCase("success")) {

					System.out.println("Successfully updated TimeTable List detail into AYClass Table.");

					statusMessage = "success";

				} else {

					System.out.println("Failed to update TimeTable List details into AYClass table");

					statusMessage = "error";
				}
			}
		}

		return statusMessage;
	}

	public String editSubject(StudentForm studform, int organizationID) {

		StuduntDAOInf daoInf = new StudentDAOImpl();
		int status = 0;
		int status1 = 0;
		int statusActivity = 0;
		int statusEditActivity = 0;

		try {
			/*
			 * Check whether subject Name present in Subject table or not if name present
			 * then give error msg & dont allow to insertion
			 */
			status = daoInf.CheckSubjectNameEdit(studform.getName(), studform.getSubjectType(), studform.getSubjectID(),
					organizationID);

			if (status == 0) {

				status1 = daoInf.CheckSubjectOrderEdit(studform.getSubjectType(), studform.getSortOrder(),
						studform.getSubjectID(), organizationID);

				if (status1 == 0) {

					statusMessage = daoInf.updateSubject(studform);

					if (statusMessage.equalsIgnoreCase("success")) {

						if (studform.getNewactivity() == null) {

							System.out.println("No new activity added");

							statusMessage = "success";

						} else {

							for (int i = 0; i < studform.getNewactivity().length; i++) {

								/*
								 * Check whether Activity Name present in Activity table or not if name present
								 * then give error msg & dont allow to insertion
								 */
								statusActivity = daoInf.CheckSubjectActivityName(studform.getNewactivity()[i],
										studform.getSubjectID());

								if (statusActivity == 0) {

									statusMessage = daoInf.insertActivity(studform.getNewactivity()[i],
											studform.getSubjectID());

									if (statusMessage.equalsIgnoreCase("success")) {

										System.out
												.println("Successfully inserted Activity detail into Activity Table.");
										statusMessage = "success";
									} else {

										System.out.println("Failed to add Activity details into Activity table");
										statusMessage = "error";
									}
								} else {
									System.out.println(
											"Activity name already exist in table. Please add new name for Activity");
									statusMessage = "input";
									return statusMessage;
								}
							}
						}

						if (studform.getEditactivityID() == null) {

							System.out.println("No new Activity added");
							statusMessage = "success";

						} else {

							for (int i = 0; i < studform.getEditactivityID().length; i++) {

								/*
								 * Check whether Activity Name present in Activity table or not if name present
								 * then give error msg & dont allow to insertion
								 */
								statusEditActivity = daoInf.CheckEditSubjectActivityName(studform.getEditactivity()[i],
										Integer.parseInt(studform.getEditactivityID()[i]), studform.getSubjectID());

								if (statusEditActivity == 0) {

									statusMessage = daoInf.updateActivity(studform.getEditactivity()[i],
											Integer.parseInt(studform.getEditactivityID()[i]));

									if (statusMessage.equalsIgnoreCase("success")) {

										System.out.println("Successfully updated Activity detail into Activity Table.");

									} else {

										System.out.println("Failed to updated Activity detail into Activity table.");
										statusMessage = "error";
									}
								} else {
									System.out.println(
											"Activity name already exist in table. Please update new name for Activity");
									statusMessage = "input";
									return statusMessage;
								}
							}
						}
						return statusMessage;

					} else {
						System.out.println("Failed to update Subject details.");
						statusMessage = "error";
						return statusMessage;
					}

				} else {
					System.out.println(
							"Subject sort order already exist in table. Please update new sort order for Subject");
					statusMessage = "input";
					return statusMessage;
				}
			} else {
				System.out.println("Subject name already exist in table. Please update new name for Subject");
				statusMessage = "input";
				return statusMessage;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			statusMessage = "error";
			return statusMessage;

		}
	}

	public String addStudentAssessmentForCoScholastic(ConfigurationForm conform) {

		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();

		// Retrieving subjectAssessmentID based on examinationID, ayClassID and subject
		// name from SubjectAssessment table

		if (conform.getStudID() == null) {
			System.out.println("No student details found.");

			statusMessage = "success";

			// return statusMessage;
		} else {

			for (int i = 0; i < conform.getStudID().length; i++) {

				// int subjectAssessmentID =
				// daoInf.retrieveSubjectAssessmentID(conform.getExaminationID(),
				// conform.getAYClassID(), Integer.parseInt(conform.getNewSubjectID()[i]));

				int subjectAssessmentID = Integer.parseInt(conform.getNewSubjAssmntID()[i]);

				String ActivityAssessmentMarks = conform.getActivityAssessmentArr()[i];

				String value = conform.getMarksObtainedArr()[i];

				double marksObtained = 0D;

				if (value == null || value == "") {
					value = "0";
				} else if (value.isEmpty()) {
					value = "0";
				}

				// System.out.println("conform.getNewAbsentFlag()[i] :
				String Flag = conform.getNewAbsentFlag()[i];
				int absentFlag = Integer.parseInt(Flag);

				// System.out.println("absentFlag" + absentFlag);

				if (absentFlag == 1) {
					absentFlag = 1;
					marksObtained = 0D;

				} else if (absentFlag == 0) {
					absentFlag = 0;
					marksObtained = Double.parseDouble(value);
				} else {
					absentFlag = 0;
					marksObtained = 0D;
				}

				double scaledmarksObtained = 0D;
				String numberAsString = Double.toString(marksObtained);
				String marks[] = numberAsString.split("\\.");

				if (Long.parseLong(marks[1]) > 0) {

					scaledmarksObtained = Double.parseDouble(marks[0]) + 1;

				} else {

					scaledmarksObtained = Double.parseDouble(marks[0]);
				}

				double scaledMarks = 0D;

				// Retrieving registrationID based on studentID and status as Active from
				// Registration table

				int registrationID = daoInf.retrieveRegistrationID(Integer.parseInt(conform.getStudID()[i]));

				/*
				 * INserting details into StudentAssessment table
				 */

				boolean marksCheck = daoInf.checkMarksPresent(subjectAssessmentID, registrationID);

				if (!marksCheck) {
					statusMessage = daoInf.insertStudentAssessmentDetails(subjectAssessmentID, registrationID,
							scaledmarksObtained, conform.getGradeObtainedArr()[i], scaledMarks, absentFlag);
				}

				if (statusMessage.equals("success")) {

					System.out.println("Student Assessment details added successfully");

					if (ActivityAssessmentMarks == null || ActivityAssessmentMarks == "") {
						continue;
					} else if (ActivityAssessmentMarks.isEmpty()) {
						continue;
					} else {
						if (ActivityAssessmentMarks.startsWith("@")) {
							ActivityAssessmentMarks = ActivityAssessmentMarks.substring(1);
						}

						String[] ActivityMarks = ActivityAssessmentMarks.split("@");

						int studentAssessmentID = daoInf.retrievestudentAssessmentID(subjectAssessmentID,
								registrationID);

						for (int k = 0; k < ActivityMarks.length; k++) {

							String[] ActivityNewMarks = ActivityMarks[k].split("=");

							if (ActivityNewMarks[1] == null || ActivityNewMarks[1] == "") {

								ActivityNewMarks[1] = "0";
							} else if (ActivityNewMarks[1].isEmpty()) {

								ActivityNewMarks[1] = "0";
							}

							statusMessage = daoInf.insertStudentActivityAssessmentDetails(studentAssessmentID,
									Integer.parseInt(ActivityNewMarks[0]), Integer.parseInt(ActivityNewMarks[1]));
						}
					}

				} else {

					System.out.println("Failed to add student assessment details");

				}

			}
			// return statusMessage;
		}

		/*
		 * Check whether edit fields are empty or not if empty then proceed further else
		 * update records into StudentAssessment table
		 */
		if (conform.getStudAssessmntID() == null) {
			System.out.println("No student edit details found.");

			statusMessage = "success";

			return statusMessage;
		} else {

			for (int i = 0; i < conform.getStudAssessmntID().length; i++) {

				String value = conform.getEditMarksObtainedArr()[i];

				if (value == null || value == "") {
					value = "0";
				} else if (value.isEmpty()) {
					value = "0";
				}

				double marksObtained = 0D;

				String editFlag = conform.getEditAbsentFlag()[i];
				int editabsentFlag = Integer.parseInt(editFlag);

				if (editabsentFlag == 1) {
					editabsentFlag = 1;
					marksObtained = 0D;

				} else if (editabsentFlag == 0) {
					editabsentFlag = 0;
					marksObtained = Double.parseDouble(value);
				} else {
					editabsentFlag = 0;
					marksObtained = 0D;
				}

				int subjectAssessmentID = Integer.parseInt(conform.getEditSubjAssmntID()[i]);

				String value1 = conform.getEditMarksObtainedArr()[i];

				double marksObtained1 = 0D;

				if (value1 == null || value1 == "") {
					value1 = "0";
				} else if (value1.isEmpty()) {
					value1 = "0";
				}

				String Flag = conform.getEditAbsentFlag()[i];
				int absentFlag = Integer.parseInt(Flag);

				// System.out.println("absentFlag" + absentFlag);

				if (absentFlag == 1) {
					absentFlag = 1;
					marksObtained1 = 0D;

				} else if (absentFlag == 0) {
					absentFlag = 0;
					marksObtained1 = Double.parseDouble(value1);
				} else {
					absentFlag = 0;
					marksObtained1 = 0D;
				}

				/*
				 * retrieving scale to from SubjectAssessment table based on subjectAssessmentID
				 */
				double scaleTo = daoInf.retrieveSubjectScaleTo(subjectAssessmentID);

				double scaledMarks = 0D;

				/*
				 * INserting details into StudentAssessment table
				 */
				statusMessage = daoInf.updateStudentAssessmentDetails(Integer.parseInt(conform.getStudAssessmntID()[i]),
						marksObtained, conform.getEditGradeObtainedArr()[i], scaledMarks, editabsentFlag);

				if (statusMessage.equals("success")) {

					System.out.println("Student Assessment details updated successfully");

				} else {

					System.out.println("Failed to update student assessment details");

				}
			}

			if (conform.getStudentActivityAssessmentIDArr() == null) {
				System.out.println("No student edit details found.");

				statusMessage = "success";

				return statusMessage;

			} else {

				for (int i = 0; i < conform.getStudentActivityAssessmentIDArr().length; i++) {

					String ActivityAssessmentMarks = conform.getStudentActivityAssessmentIDArr()[i];
					System.out.println("ActivityAssessmentMarks: " + ActivityAssessmentMarks);
					String[] ActivityMarks = ActivityAssessmentMarks.split("=");

					statusMessage = daoInf.updateStudentActivityAssessmentDetails(Double.parseDouble(ActivityMarks[1]),
							Integer.parseInt(ActivityMarks[3]));

					if (statusMessage.equals("success")) {

						System.out.println("Student Activity Assessment Details updated successfully");

					} else {

						System.out.println("Failed to Update Student Activity Assessment Details");

					}

				}
			}
			return statusMessage;
		}

	}

	public String registerLeavingCertificate(ConfigurationForm conform) {

		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();

		boolean check = daoInf.verifyleavingCertificate("LeavingCertificate", conform.getStudentID());

		if (check) {

			statusMessage = daoInf.updateLeavingCertificate(conform);

			if (statusMessage.equalsIgnoreCase("success")) {

				System.out.println("Successfully updated Leaving Certificate detail into LeavingCertificate Table.");

				statusMessage = "success";
			} else {

				System.out.println("Failed to update Leaving Certificate details into LeavingCertificate table");

				statusMessage = "error";
			}

		} else {

			statusMessage = daoInf.insertLeavingCertificate(conform);

			if (statusMessage.equalsIgnoreCase("success")) {

				System.out.println("Successfully inserted Leaving Certificate detail into LeavingCertificate Table.");

				statusMessage = "success";
			} else {

				System.out.println("Failed to add Leaving Certificate details into LeavingCertificate table");

				statusMessage = "error";
			}

		}

		return statusMessage;

	}

	public String addStudentStatusDetails(ConfigurationForm conform) {

		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();

		if (conform.getStudID() == null) {
			System.out.println("No student details found.");

			statusMessage = "success";

			// return statusMessage;
		} else {

			for (int i = 0; i < conform.getStudID().length; i++) {

				/*
				 * Check whether studentID having details or not if not then proceed for
				 * insertion else update records into PVStudentStatus table
				 */

				boolean presentCheck = daoInf.verifyStudentData(Integer.parseInt(conform.getStudID()[i]));

				if (presentCheck) {
					statusMessage = daoInf.updateStudentStatusDetails(conform.getStudentStatus()[i],
							Integer.parseInt(conform.getStudID()[i]));
				} else {
					statusMessage = daoInf.insertStudentStatusDetails(conform.getStudentStatus()[i],
							Integer.parseInt(conform.getStudID()[i]));
				}

				if (statusMessage.equals("success")) {

					System.out.println("Student status details added successfully");

				} else {

					System.out.println("Failed to add student status details");

				}
			}
		}

		return statusMessage;
	}

	public String registerStandards(LoginForm form, int organizationID) {
		// TODO Auto-generated method stub
		LoginDAOInf daoInf = new LoginDAOImpl();

		int status = 0;

		try {

			/*
			 * Check whether standard Name present in Standard table or not if name present
			 * then give error msg & dont allow to insertion
			 */
			status = daoInf.CheckStandardName(form.getStandard(), form.getStage(), organizationID);

			if (status == 0) {

				/*
				 * Inserting AcademicYear detail into User table
				 */
				statusMessage = daoInf.insertStandard(form, organizationID);
				if (statusMessage.equalsIgnoreCase("success")) {

					return statusMessage;

				} else {
					System.out.println("Failed to insert Standard detail into Standard table.");
					statusMessage = "error";
					return statusMessage;
				}

			} else {
				System.out.println("Standard name already exist in table. Please Add new name for Standard");
				statusMessage = "input";
				return statusMessage;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			statusMessage = "error";
			return statusMessage;

		}

	}

	public String editRegisterStandard(LoginForm form, int organizationID) {
		// TODO Auto-generated method stub
		LoginDAOInf daoInf = new LoginDAOImpl();

		int status = 0;

		try {

			/*
			 * Check whether standard Name present in Standard table or not if name present
			 * then give error msg & dont allow to insertion
			 */
			status = daoInf.CheckStandardNameEdit(form.getStandard(), form.getStage(), form.getStandardID(),
					organizationID);

			if (status == 0) {

				/*
				 * Inserting AcademicYear detail into User table
				 */
				statusMessage = daoInf.updateStandard(form);
				if (statusMessage.equalsIgnoreCase("success")) {

					return statusMessage;

				} else {
					System.out.println("Failed to update Standard detail into Standard table.");
					statusMessage = "error";
					return statusMessage;
				}

			} else {
				System.out.println("Standard name already exist in table. Please update new name for Standard");
				statusMessage = "input";
				return statusMessage;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			statusMessage = "error";
			return statusMessage;

		}

	}

	public String registerSubject(StudentForm studform, int organizationID) {
		// TODO Auto-generated method stub
		StuduntDAOInf daoInf = new StudentDAOImpl();

		int status = 0;
		int status1 = 0;

		try {

			/*
			 * Check whether subject Name present in Subject table or not if name present
			 * then give error msg & dont allow to insertion
			 */
			status = daoInf.CheckSubjectName(studform.getName(), studform.getSubjectType(), organizationID);

			if (status == 0) {

				/*
				 * Check whether subject Name present in Subject table or not if name present
				 * then give error msg & dont allow to insertion
				 */
				status1 = daoInf.CheckSubjectOrder(studform.getSubjectType(), studform.getSortOrder(), organizationID);

				if (status1 == 0) {

					/*
					 * Inserting AcademicYear detail into User table
					 */
					statusMessage = daoInf.insertSubject(studform, organizationID);
					if (statusMessage.equalsIgnoreCase("success")) {

						return statusMessage;
					} else {
						System.out.println("Failed to insert Subject detail into Subject table.");
						statusMessage = "error";
						return statusMessage;
					}

				} else {
					System.out.println(
							"Subject sort order already exist in table. Please Add new sort order for Subject");
					statusMessage = "input";
					return statusMessage;
				}

			} else {
				System.out.println("Subject name already exist in table. Please Add new name for Subject");
				statusMessage = "input";
				return statusMessage;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			statusMessage = "error";
			return statusMessage;

		}

	}

	public String registerDivision(LoginForm form) {
		// TODO Auto-generated method stub
		LoginDAOInf daoInf = new LoginDAOImpl();

		int status = 0;

		try {

			/*
			 * Check whether Division Name present in Division table or not if name present
			 * then give error msg & dont allow to insertion
			 */
			status = daoInf.CheckDivisionName(form.getDivision(), form.getStandardID());

			if (status == 0) {

				/*
				 * Inserting Division detail into User table
				 */
				statusMessage = daoInf.insertDivision(form);
				if (statusMessage.equalsIgnoreCase("success")) {

					return statusMessage;

				} else {
					System.out.println("Failed to insert Division detail into Division table.");
					statusMessage = "error";
					return statusMessage;
				}

			} else {
				System.out.println("Division name already exist in table. Please Add new name");
				statusMessage = "input";
				return statusMessage;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			statusMessage = "error";
			return statusMessage;

		}

	}

	public String registerupdateDivision(LoginForm form) {
		// TODO Auto-generated method stub
		LoginDAOInf daoInf = new LoginDAOImpl();

		int status = 0;

		try {

			/*
			 * Check whether division Name present in Division table or not if name present
			 * then give error msg & dont allow to insertion
			 */
			status = daoInf.CheckDivisionNameEdit(form.getDivision(), form.getStandardID(), form.getDivisionID());

			if (status == 0) {

				/*
				 * updating Division detail into Division table
				 */
				statusMessage = daoInf.updateDivision(form);
				if (statusMessage.equalsIgnoreCase("success")) {

					return statusMessage;

				} else {
					System.out.println("Failed to update Division detail into Division table.");
					statusMessage = "error";
					return statusMessage;
				}

			} else {
				System.out.println("Division name already exist in table. Please update new name");
				statusMessage = "input";
				return statusMessage;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			statusMessage = "error";
			return statusMessage;

		}

	}

	public String registerOrganization(LoginForm form) {

		// TODO Auto-generated method stub
		LoginDAOInf daoInf = new LoginDAOImpl();

		String accessKey = xmlUtil.getAccessKey();

		String secreteKey = xmlUtil.getSecreteKey();

		AWSS3Connect awss3Connect = new AWSS3Connect();

		// getting input file location from S3 bucket
		String s3rdmlFilePath = xmlUtil.getS3RDMLFilePath();

		// getting s3 bucket name
		String bucketName = xmlUtil.getS3BucketName();

		// getting s3 bucket region
		String bucketRegion = xmlUtil.getS3BucketRegion();

		// Set the presigned URL to expire after one hour.
		java.util.Date expiration = new java.util.Date();
		long expTimeMillis = expiration.getTime();
		expTimeMillis += 1000 * 60 * 60;
		expiration.setTime(expTimeMillis);

		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secreteKey);

		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(bucketRegion).build();

		try {

			// Setting LogoDBname as NULL
			form.setLogoDBName(null);

			/*
			 * Storing user uploaded profile pic file into image/profilePic directory of
			 * Project, only if the profilePic variable is not null
			 */
			if (form.getLogo() != null) {

				String[] array = form.getLogoFileName().split("\\.");

				String fileExtension = array[1];

				// Setting content type according to file extension
				if (fileExtension.equalsIgnoreCase("jpg")) {

					form.setLogoContentType("image/jpg");

				} else if (fileExtension.equalsIgnoreCase("jpeg")) {

					form.setLogoContentType("image/jpeg");

				} else if (fileExtension.equalsIgnoreCase("png")) {

					form.setLogoContentType("image/png");

				} else if (fileExtension.equalsIgnoreCase("bmp")) {

					form.setLogoContentType("image/bmp");
				}

				String LogoPic = form.getName().replaceAll(" ", "_");

				String fileNameToBeStored = LogoPic + "_Logo" + "." + fileExtension;

				System.out.println("Original File name is ::::" + form.getLogoFileName());

				System.out.println("File Name to be stored into DB is ::: " + fileNameToBeStored);
				/*
				 * Setting file name to be inserted into AppUser table into LogoDBName variable
				 * of UserForm
				 */
				form.setLogoDBName(s3.getUrl(bucketName, s3rdmlFilePath).toExternalForm() + "/" + fileNameToBeStored);

				// Storing file to S3 RDML INPUT FILE location
				statusMessage = awss3Connect.pushFile(form.getLogo(), fileNameToBeStored, bucketName, bucketRegion,
						s3rdmlFilePath);
			}

			// Setting BoardLogo as NULL
			form.setBoardLogoDBName(null);

			/*
			 * Storing user uploaded profile pic file into image/profilePic directory of
			 * Project, only if the profilePic variable is not null
			 */
			if (form.getBoardLogo() != null) {

				String[] array1 = form.getBoardLogoFileName().split("\\.");

				String fileExtension1 = array1[1];

				// Setting content type according to file extension
				if (fileExtension1.equalsIgnoreCase("jpg")) {

					form.setBoardLogoContentType("image/jpg");

				} else if (fileExtension1.equalsIgnoreCase("jpeg")) {

					form.setBoardLogoContentType("image/jpeg");

				} else if (fileExtension1.equalsIgnoreCase("png")) {

					form.setBoardLogoContentType("image/png");

				} else if (fileExtension1.equalsIgnoreCase("bmp")) {

					form.setBoardLogoContentType("image/bmp");
				}

				String BoardLogoPic = form.getName().replaceAll(" ", "_");

				String fileNameToBeStored1 = BoardLogoPic + "_BoardLogo" + "." + fileExtension1;

				System.out.println("Original File name is ::::" + form.getBoardLogoFileName());

				System.out.println("File Name to be stored into DB is ::: " + fileNameToBeStored1);
				/*
				 * Setting file name to be inserted into AppUser table into LogoDBName variable
				 * of UserForm
				 */
				form.setBoardLogoDBName(
						s3.getUrl(bucketName, s3rdmlFilePath).toExternalForm() + "/" + fileNameToBeStored1);

				// Storing file to S3 RDML INPUT FILE location
				statusMessage = awss3Connect.pushFile(form.getBoardLogo(), fileNameToBeStored1, bucketName,
						bucketRegion, s3rdmlFilePath);
			}

			// Setting signatureDBname as NULL
			form.setSignatureDBName(null);

			/*
			 * Storing user uploaded profile pic file into image/profilePic directory of
			 * Project, only if the profilePic variable is not null
			 */
			if (form.getSignature() != null) {

				String[] array = form.getSignatureFileName().split("\\.");

				String fileExtension = array[1];

				// Setting content type according to file extension

				if (fileExtension.equalsIgnoreCase("jpg")) {

					form.setSignatureContentType("image/jpg");
				} else if (fileExtension.equalsIgnoreCase("jpeg")) {

					form.setSignatureContentType("image/jpeg");
				} else if (fileExtension.equalsIgnoreCase("png")) {

					form.setSignatureContentType("image/png");
				} else if (fileExtension.equalsIgnoreCase("bmp")) {

					form.setSignatureContentType("image/bmp");
				}

				String PrincipalSignature = form.getName().replaceAll(" ", "_");

				String fileNameToBeStored = PrincipalSignature + "_Principal_Signature." + fileExtension;

				System.out.println("Original File name is ::::" + form.getSignatureFileName());

				System.out.println("File Name to be stored into DB is ::: " + fileNameToBeStored);

				/*
				 * Setting file name to be inserted into AppUser table into profilePicDBName
				 * variable of UserForm
				 */
				form.setSignatureDBName(
						s3.getUrl(bucketName, s3rdmlFilePath).toExternalForm() + "/" + fileNameToBeStored);

				// Storing file to S3 RDML INPUT FILE location
				statusMessage = awss3Connect.pushFile(form.getSignature(), fileNameToBeStored, bucketName, bucketRegion,
						s3rdmlFilePath);
			}

			// Setting sealDBname as NULL
			form.setSealDBName(null);

			/*
			 * Storing user uploaded profile pic file into image/profilePic directory of
			 * Project, only if the profilePic variable is not null
			 */
			if (form.getSeal() != null) {

				String[] array = form.getSealFileName().split("\\.");

				String fileExtension = array[1];

				// Setting content type according to file extension

				if (fileExtension.equalsIgnoreCase("jpg")) {

					form.setSealContentType("image/jpg");
				} else if (fileExtension.equalsIgnoreCase("jpeg")) {

					form.setSealContentType("image/jpeg");
				} else if (fileExtension.equalsIgnoreCase("png")) {

					form.setSealContentType("image/png");
				} else if (fileExtension.equalsIgnoreCase("bmp")) {

					form.setSealContentType("image/bmp");
				}

				String SchoolSeal = form.getName().replaceAll(" ", "_");

				String fileNameToBeStored = SchoolSeal + "_School_Seal." + fileExtension;

				System.out.println("Original File name is ::::" + form.getSealFileName());

				System.out.println("File Name to be stored into DB is ::: " + fileNameToBeStored);

				/*
				 * Setting file name to be inserted into AppUser table into profilePicDBName
				 * variable of UserForm
				 */
				form.setSealDBName(s3.getUrl(bucketName, s3rdmlFilePath).toExternalForm() + "/" + fileNameToBeStored);

				// Storing file to S3 RDML INPUT FILE location
				statusMessage = awss3Connect.pushFile(form.getSeal(), fileNameToBeStored, bucketName, bucketRegion,
						s3rdmlFilePath);
			}

			// Setting sealDBname as NULL
			form.setSignatureDBPrimary(null);

			/*
			 * Storing user uploaded profile pic file into image/profilePic directory of
			 * Project, only if the profilePic variable is not null
			 */
			if (form.getSignaturePrimary() != null) {

				String[] array = form.getSealFileName().split("\\.");

				String fileExtension = array[1];

				// Setting content type according to file extension

				if (fileExtension.equalsIgnoreCase("jpg")) {

					form.setSealContentType("image/jpg");
				} else if (fileExtension.equalsIgnoreCase("jpeg")) {

					form.setSealContentType("image/jpeg");
				} else if (fileExtension.equalsIgnoreCase("png")) {

					form.setSealContentType("image/png");
				} else if (fileExtension.equalsIgnoreCase("bmp")) {

					form.setSealContentType("image/bmp");
				}

				String SchoolSeal = form.getName().replaceAll(" ", "_");

				String fileNameToBeStored = SchoolSeal + "_Primary_School_Signature." + fileExtension;

				System.out.println("Original File name is ::::" + form.getSealFileName());

				System.out.println("File Name to be stored into DB is ::: " + fileNameToBeStored);

				/*
				 * Setting file name to be inserted into AppUser table into profilePicDBName
				 * variable of UserForm
				 */
				form.setSignatureDBPrimary(
						s3.getUrl(bucketName, s3rdmlFilePath).toExternalForm() + "/" + fileNameToBeStored);

				// Storing file to S3 RDML INPUT FILE location
				statusMessage = awss3Connect.pushFile(form.getSeal(), fileNameToBeStored, bucketName, bucketRegion,
						s3rdmlFilePath);
			}

			// Setting sealDBname as NULL
			form.setSealDBPrimary(null);

			/*
			 * Storing user uploaded profile pic file into image/profilePic directory of
			 * Project, only if the profilePic variable is not null
			 */
			if (form.getSealPrimary() != null) {

				String[] array = form.getSealFileName().split("\\.");

				String fileExtension = array[1];

				// Setting content type according to file extension

				if (fileExtension.equalsIgnoreCase("jpg")) {

					form.setSealContentType("image/jpg");
				} else if (fileExtension.equalsIgnoreCase("jpeg")) {

					form.setSealContentType("image/jpeg");
				} else if (fileExtension.equalsIgnoreCase("png")) {

					form.setSealContentType("image/png");
				} else if (fileExtension.equalsIgnoreCase("bmp")) {

					form.setSealContentType("image/bmp");
				}

				String SchoolSeal = form.getName().replaceAll(" ", "_");

				String fileNameToBeStored = SchoolSeal + "_Primary_School_Seal." + fileExtension;

				System.out.println("Original File name is ::::" + form.getSealFileName());

				System.out.println("File Name to be stored into DB is ::: " + fileNameToBeStored);

				/*
				 * Setting file name to be inserted into AppUser table into profilePicDBName
				 * variable of UserForm
				 */
				form.setSealDBPrimary(
						s3.getUrl(bucketName, s3rdmlFilePath).toExternalForm() + "/" + fileNameToBeStored);

				// Storing file to S3 RDML INPUT FILE location
				statusMessage = awss3Connect.pushFile(form.getSeal(), fileNameToBeStored, bucketName, bucketRegion,
						s3rdmlFilePath);
			}

			// Setting sealDBname as NULL
			form.setSignatureDBSecondary(null);

			/*
			 * Storing user uploaded profile pic file into image/profilePic directory of
			 * Project, only if the profilePic variable is not null
			 */
			if (form.getSignatureSecondary() != null) {

				String[] array = form.getSealFileName().split("\\.");

				String fileExtension = array[1];

				// Setting content type according to file extension

				if (fileExtension.equalsIgnoreCase("jpg")) {

					form.setSealContentType("image/jpg");
				} else if (fileExtension.equalsIgnoreCase("jpeg")) {

					form.setSealContentType("image/jpeg");
				} else if (fileExtension.equalsIgnoreCase("png")) {

					form.setSealContentType("image/png");
				} else if (fileExtension.equalsIgnoreCase("bmp")) {

					form.setSealContentType("image/bmp");
				}

				String SchoolSeal = form.getName().replaceAll(" ", "_");

				String fileNameToBeStored = SchoolSeal + "_Secondary_School_Signature." + fileExtension;

				System.out.println("Original File name is ::::" + form.getSealFileName());

				System.out.println("File Name to be stored into DB is ::: " + fileNameToBeStored);

				/*
				 * Setting file name to be inserted into AppUser table into profilePicDBName
				 * variable of UserForm
				 */
				form.setSignatureDBSecondary(
						s3.getUrl(bucketName, s3rdmlFilePath).toExternalForm() + "/" + fileNameToBeStored);

				// Storing file to S3 RDML INPUT FILE location
				statusMessage = awss3Connect.pushFile(form.getSeal(), fileNameToBeStored, bucketName, bucketRegion,
						s3rdmlFilePath);
			}

			// Setting sealDBname as NULL
			form.setSealDBSecondary(null);

			/*
			 * Storing user uploaded profile pic file into image/profilePic directory of
			 * Project, only if the profilePic variable is not null
			 */
			if (form.getSealSecondary() != null) {

				String[] array = form.getSealFileName().split("\\.");

				String fileExtension = array[1];

				// Setting content type according to file extension

				if (fileExtension.equalsIgnoreCase("jpg")) {

					form.setSealContentType("image/jpg");
				} else if (fileExtension.equalsIgnoreCase("jpeg")) {

					form.setSealContentType("image/jpeg");
				} else if (fileExtension.equalsIgnoreCase("png")) {

					form.setSealContentType("image/png");
				} else if (fileExtension.equalsIgnoreCase("bmp")) {

					form.setSealContentType("image/bmp");
				}

				String SchoolSeal = form.getName().replaceAll(" ", "_");

				String fileNameToBeStored = SchoolSeal + "_Secondary_School_Seal." + fileExtension;

				System.out.println("Original File name is ::::" + form.getSealFileName());

				System.out.println("File Name to be stored into DB is ::: " + fileNameToBeStored);

				/*
				 * Setting file name to be inserted into AppUser table into profilePicDBName
				 * variable of UserForm
				 */
				form.setSealDBSecondary(
						s3.getUrl(bucketName, s3rdmlFilePath).toExternalForm() + "/" + fileNameToBeStored);

				// Storing file to S3 RDML INPUT FILE location
				statusMessage = awss3Connect.pushFile(form.getSeal(), fileNameToBeStored, bucketName, bucketRegion,
						s3rdmlFilePath);
			}

			/*
			 * Inserting Organization detail into Organization table
			 */
			statusMessage = daoInf.insertOrganization(form);
			if (statusMessage.equalsIgnoreCase("success")) {

				System.out.println("Successfully inserted Organization detail into Organization Table.");

				/*
				 * Retrieving organizationID from Organization table
				 */
				int organizationID = daoInf.retrieveOrganizationID();

				if (form.getNewLibrary() == null) {

					System.out.println("No new library added");

					statusMessage = "success";
				} else {

					for (int i = 0; i < form.getNewLibrary().length; i++) {

						statusMessage = daoInf.insertLibrary(form.getNewLibrary()[i], organizationID);
					}
				}

				return statusMessage;

			} else {
				System.out.println("Failed to insert Organization detail into Organization table.");
				statusMessage = "error";
				return statusMessage;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			statusMessage = "error";
			return statusMessage;
		}

	}

	public String updateOrganization(LoginForm form) {

		// TODO Auto-generated method stub
		LoginDAOInf daoInf = new LoginDAOImpl();

		String accessKey = xmlUtil.getAccessKey();

		String secreteKey = xmlUtil.getSecreteKey();

		AWSS3Connect awss3Connect = new AWSS3Connect();

		// getting input file location from S3 bucket
		String s3rdmlFilePath = xmlUtil.getS3RDMLFilePath();

		// getting s3 bucket name
		String bucketName = xmlUtil.getS3BucketName();

		// getting s3 bucket region
		String bucketRegion = xmlUtil.getS3BucketRegion();

		// Set the presigned URL to expire after one hour.
		java.util.Date expiration = new java.util.Date();
		long expTimeMillis = expiration.getTime();
		expTimeMillis += 1000 * 60 * 60;
		expiration.setTime(expTimeMillis);

		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secreteKey);

		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(bucketRegion).build();

		try {

			/*
			 * Storing user uploaded profile pic file into images/profilePics directory of
			 * Project, only if the profilePic variable is not null
			 */
			if (form.getLogo() != null) {

				String[] array = form.getLogoFileName().split("\\.");

				String fileExtension = array[1];

				// Setting content type according to file extension
				if (fileExtension.equalsIgnoreCase("jpg")) {

					form.setLogoContentType("image/jpg");

				} else if (fileExtension.equalsIgnoreCase("jpeg")) {

					form.setLogoContentType("image/jpeg");

				} else if (fileExtension.equalsIgnoreCase("png")) {

					form.setLogoContentType("image/png");

				} else if (fileExtension.equalsIgnoreCase("bmp")) {

					form.setLogoContentType("image/bmp");
				}

				String LogoPic = form.getName().replaceAll(" ", "_");

				String fileNameToBeStored = LogoPic + "_Logo" + "." + fileExtension;

				System.out.println("Original File name is ::::" + form.getLogoFileName());

				System.out.println("File Name to be stored into DB is ::: " + fileNameToBeStored);
				/*
				 * Setting file name to be inserted into AppUser table into profilePicDBName
				 * variable of UserForm
				 */
				form.setLogoDBName(s3.getUrl(bucketName, s3rdmlFilePath).toExternalForm() + "/" + fileNameToBeStored);

				// Storing file to S3 RDML INPUT FILE location
				statusMessage = awss3Connect.pushFile(form.getLogo(), fileNameToBeStored, bucketName, bucketRegion,
						s3rdmlFilePath);
			}

			/*
			 * Storing user uploaded profile pic file into images/profilePics directory of
			 * Project, only if the profilePic variable is not null
			 */
			if (form.getBoardLogo() != null) {

				String[] array1 = form.getBoardLogoFileName().split("\\.");

				String fileExtension1 = array1[1];

				// Setting content type according to file extension
				if (fileExtension1.equalsIgnoreCase("jpg")) {

					form.setBoardLogoContentType("image/jpg");

				} else if (fileExtension1.equalsIgnoreCase("jpeg")) {

					form.setBoardLogoContentType("image/jpeg");

				} else if (fileExtension1.equalsIgnoreCase("png")) {

					form.setBoardLogoContentType("image/png");

				} else if (fileExtension1.equalsIgnoreCase("bmp")) {

					form.setBoardLogoContentType("image/bmp");
				}

				String BoardLogoPic = form.getName().replaceAll(" ", "_");

				String fileNameToBeStored1 = BoardLogoPic + "_BoardLogo" + "." + fileExtension1;

				System.out.println("Original File name is ::::" + form.getBoardLogoFileName());

				System.out.println("File Name to be stored into DB is ::: " + fileNameToBeStored1);
				/*
				 * Setting file name to be inserted into AppUser table into profilePicDBName
				 * variable of UserForm
				 */
				form.setBoardLogoDBName(
						s3.getUrl(bucketName, s3rdmlFilePath).toExternalForm() + "/" + fileNameToBeStored1);

				// Storing file to S3 RDML INPUT FILE location
				statusMessage = awss3Connect.pushFile(form.getBoardLogo(), fileNameToBeStored1, bucketName,
						bucketRegion, s3rdmlFilePath);

			}

			/*
			 * Storing user uploaded profile pic file into image/profilePic directory of
			 * Project, only if the profilePic variable is not null
			 */
			if (form.getSignature() != null) {

				String[] array = form.getSignatureFileName().split("\\.");

				String fileExtension = array[1];

				// Setting content type according to file extension

				if (fileExtension.equalsIgnoreCase("jpg")) {

					form.setSignatureContentType("image/jpg");
				} else if (fileExtension.equalsIgnoreCase("jpeg")) {

					form.setSignatureContentType("image/jpeg");
				} else if (fileExtension.equalsIgnoreCase("png")) {

					form.setSignatureContentType("image/png");
				} else if (fileExtension.equalsIgnoreCase("bmp")) {

					form.setSignatureContentType("image/bmp");
				}

				String PrincipalSignature = form.getName().replaceAll(" ", "_");

				String fileNameToBeStored = PrincipalSignature + "_Principal_Signature." + fileExtension;

				System.out.println("Original File name is ::::" + form.getSignatureFileName());

				System.out.println("File Name to be stored into DB is ::: " + fileNameToBeStored);

				/*
				 * Setting file name to be inserted into AppUser table into profilePicDBName
				 * variable of UserForm
				 */
				form.setSignatureDBName(
						s3.getUrl(bucketName, s3rdmlFilePath).toExternalForm() + "/" + fileNameToBeStored);
				System.out.println("signature NAme ::: " + form.getSignature() + "  file nAme ::: " + fileNameToBeStored
						+ "Bucket nAme ::: " + bucketName + " Bucket Region ::: " + bucketRegion
						+ " real file path ::: " + s3rdmlFilePath);
				// Storing file to S3 RDML INPUT FILE location
				statusMessage = awss3Connect.pushFile(form.getSignature(), fileNameToBeStored, bucketName, bucketRegion,
						s3rdmlFilePath);
			}

			/*
			 * Storing user uploaded profile pic file into image/profilePic directory of
			 * Project, only if the profilePic variable is not null
			 */
			if (form.getSeal() != null) {
				System.out.println("seal name :::: " + form.getSealFileName());
				String[] array = form.getSealFileName().split("\\.");

				String fileExtension = array[1];

				// Setting content type according to file extension

				if (fileExtension.equalsIgnoreCase("jpg")) {

					form.setSealContentType("image/jpg");
				} else if (fileExtension.equalsIgnoreCase("jpeg")) {

					form.setSealContentType("image/jpeg");
				} else if (fileExtension.equalsIgnoreCase("png")) {

					form.setSealContentType("image/png");
				} else if (fileExtension.equalsIgnoreCase("bmp")) {

					form.setSealContentType("image/bmp");
				}

				String SchoolSeal = form.getName().replaceAll(" ", "_");

				String fileNameToBeStored = SchoolSeal + "_School_Seal." + fileExtension;

				System.out.println("Original File name is ::::" + form.getSealFileName());

				System.out.println("File Name to be stored into DB is ::: " + fileNameToBeStored);

				/*
				 * Setting file name to be inserted into AppUser table into profilePicDBName
				 * variable of UserForm
				 */
				form.setSealDBName(s3.getUrl(bucketName, s3rdmlFilePath).toExternalForm() + "/" + fileNameToBeStored);

				// Storing file to S3 RDML INPUT FILE location
				statusMessage = awss3Connect.pushFile(form.getSeal(), fileNameToBeStored, bucketName, bucketRegion,
						s3rdmlFilePath);
			}

			if (form.getSignaturePrimary() != null) {
				System.out.println("primary signature  name :::: " + form.getSealFileName());
				String[] array = form.getSealFileName().split("\\.");

				String fileExtension = array[1];

				// Setting content type according to file extension

				if (fileExtension.equalsIgnoreCase("jpg")) {

					form.setSealContentType("image/jpg");
				} else if (fileExtension.equalsIgnoreCase("jpeg")) {

					form.setSealContentType("image/jpeg");
				} else if (fileExtension.equalsIgnoreCase("png")) {

					form.setSealContentType("image/png");
				} else if (fileExtension.equalsIgnoreCase("bmp")) {

					form.setSealContentType("image/bmp");
				}

				String SchoolSeal = form.getName().replaceAll(" ", "_");

				String fileNameToBeStored = SchoolSeal + "_Primary_School_Signature." + fileExtension;

				System.out.println("Original File name is ::::" + form.getSealFileName());

				System.out.println("File Name to be stored into DB is ::: " + fileNameToBeStored);

				/*
				 * Setting file name to be inserted into AppUser table into profilePicDBName
				 * variable of UserForm
				 */
				form.setSignatureDBPrimary(
						s3.getUrl(bucketName, s3rdmlFilePath).toExternalForm() + "/" + fileNameToBeStored);

				// Storing file to S3 RDML INPUT FILE location
				statusMessage = awss3Connect.pushFile(form.getSignaturePrimary(), fileNameToBeStored, bucketName,
						bucketRegion, s3rdmlFilePath);
			}

			if (form.getSealPrimary() != null) {
				System.out.println("primary seal name :::: " + form.getSealFileName());
				String[] array = form.getSealFileName().split("\\.");

				String fileExtension = array[1];

				// Setting content type according to file extension

				if (fileExtension.equalsIgnoreCase("jpg")) {

					form.setSealContentType("image/jpg");
				} else if (fileExtension.equalsIgnoreCase("jpeg")) {

					form.setSealContentType("image/jpeg");
				} else if (fileExtension.equalsIgnoreCase("png")) {

					form.setSealContentType("image/png");
				} else if (fileExtension.equalsIgnoreCase("bmp")) {

					form.setSealContentType("image/bmp");
				}

				String SchoolSeal = form.getName().replaceAll(" ", "_");

				String fileNameToBeStored = SchoolSeal + "_Primary_School_Seal." + fileExtension;

				System.out.println("Original File name is ::::" + form.getSealFileName());

				System.out.println("File Name to be stored into DB is ::: " + fileNameToBeStored);

				/*
				 * Setting file name to be inserted into AppUser table into profilePicDBName
				 * variable of UserForm
				 */
				form.setSealDBPrimary(
						s3.getUrl(bucketName, s3rdmlFilePath).toExternalForm() + "/" + fileNameToBeStored);

				// Storing file to S3 RDML INPUT FILE location
				statusMessage = awss3Connect.pushFile(form.getSealPrimary(), fileNameToBeStored, bucketName,
						bucketRegion, s3rdmlFilePath);
			}

			if (form.getSignatureSecondary() != null) {
				System.out.println("secondary signatuire  name :::: " + form.getSealFileName());
				String[] array = form.getSealFileName().split("\\.");

				String fileExtension = array[1];

				// Setting content type according to file extension

				if (fileExtension.equalsIgnoreCase("jpg")) {

					form.setSealContentType("image/jpg");
				} else if (fileExtension.equalsIgnoreCase("jpeg")) {

					form.setSealContentType("image/jpeg");
				} else if (fileExtension.equalsIgnoreCase("png")) {

					form.setSealContentType("image/png");
				} else if (fileExtension.equalsIgnoreCase("bmp")) {

					form.setSealContentType("image/bmp");
				}

				String SchoolSeal = form.getName().replaceAll(" ", "_");

				String fileNameToBeStored = SchoolSeal + "_Secondary_School_Signature." + fileExtension;

				System.out.println("Original File name is ::::" + form.getSealFileName());

				System.out.println("File Name to be stored into DB is ::: " + fileNameToBeStored);

				/*
				 * Setting file name to be inserted into AppUser table into profilePicDBName
				 * variable of UserForm
				 */
				form.setSignatureDBSecondary(
						s3.getUrl(bucketName, s3rdmlFilePath).toExternalForm() + "/" + fileNameToBeStored);

				// Storing file to S3 RDML INPUT FILE location
				statusMessage = awss3Connect.pushFile(form.getSignatureSecondary(), fileNameToBeStored, bucketName,
						bucketRegion, s3rdmlFilePath);
			}

			if (form.getSealSecondary() != null) {
				System.out.println("secondary seal name :::: " + form.getSealFileName());
				String[] array = form.getSealFileName().split("\\.");

				String fileExtension = array[1];

				// Setting content type according to file extension

				if (fileExtension.equalsIgnoreCase("jpg")) {

					form.setSealContentType("image/jpg");
				} else if (fileExtension.equalsIgnoreCase("jpeg")) {

					form.setSealContentType("image/jpeg");
				} else if (fileExtension.equalsIgnoreCase("png")) {

					form.setSealContentType("image/png");
				} else if (fileExtension.equalsIgnoreCase("bmp")) {

					form.setSealContentType("image/bmp");
				}

				String SchoolSeal = form.getName().replaceAll(" ", "_");

				String fileNameToBeStored = SchoolSeal + "_Secondary_School_Seal." + fileExtension;

				System.out.println("Original File name is ::::" + form.getSealFileName());

				System.out.println("File Name to be stored into DB is ::: " + fileNameToBeStored);

				/*
				 * Setting file name to be inserted into AppUser table into profilePicDBName
				 * variable of UserForm
				 */
				form.setSealDBSecondary(
						s3.getUrl(bucketName, s3rdmlFilePath).toExternalForm() + "/" + fileNameToBeStored);

				// Storing file to S3 RDML INPUT FILE location
				statusMessage = awss3Connect.pushFile(form.getSealSecondary(), fileNameToBeStored, bucketName,
						bucketRegion, s3rdmlFilePath);
			}

			/*
			 * Updating Organization detail into Organization table
			 */
			statusMessage = daoInf.updateOrganization(form);
			if (statusMessage.equalsIgnoreCase("success")) {

				System.out.println("Successfully updated Organization detail into Organization Table.");

				if (form.getNewLibrary() == null) {

					System.out.println("No new library added");

					statusMessage = "success";
				} else {

					for (int i = 0; i < form.getNewLibrary().length; i++) {

						statusMessage = daoInf.insertLibrary(form.getNewLibrary()[i], form.getOrganizationsID());
					}
				}

				if (form.getEditLibrary() == null) {

					System.out.println("No new library added");

					statusMessage = "success";
				} else {

					for (int i = 0; i < form.getEditLibrary().length; i++) {

						statusMessage = daoInf.updateLibrary(form.getEditLibrary()[i],
								Integer.parseInt(form.getEditLibraryID()[i]));
					}
				}

				return statusMessage;

			} else {
				System.out.println("Failed to update Organization detail into Organization table.");
				statusMessage = "error";
				return statusMessage;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			statusMessage = "error";
			return statusMessage;
		}

	}

	@Override
	public String registerRules(LibraryForm form, int libraryID) {

		librarydaoInf = new LibraryDAOImpl();

		if (form.getRuleForArr() == null) {
			System.out.println("No Rules add details found.");

			statusMessage = "success";
		} else {

			for (int i = 0; i < form.getRuleForArr().length; i++) {

				statusMessage = librarydaoInf.insertRulesFor(form.getRuleForArr()[i],
						Integer.parseInt(form.getBookCountArr()[i]), Integer.parseInt(form.getIssueDaysArr()[i]),
						Integer.parseInt(form.getFinePerDayArr()[i]), form.getGenreArr()[i], libraryID);

				if (statusMessage.equalsIgnoreCase("success")) {

					System.out.println("Successfully inserted rules details into rule Table.");

					statusMessage = "success";

				} else {
					System.out.println("Failed to insert rules details into rule table.");
					statusMessage = "error";
				}
			}
		}

		return statusMessage;
	}

	@Override
	public String editRulesFor(LibraryForm form) {

		librarydaoInf = new LibraryDAOImpl();

		if (form.getRuleForEditArr() == null) {
			System.out.println("No Rules edit details found.");

			statusMessage = "success";

		} else {

			for (int i = 0; i < form.getRuleForEditArr().length; i++) {

				statusMessage = librarydaoInf.updateRulesFor(form.getRuleForEditArr()[i],
						Integer.parseInt(form.getBookCountEditArr()[i]),
						Integer.parseInt(form.getIssueDaysEditArr()[i]),
						Integer.parseInt(form.getFinePerDayEditArr()[i]), Integer.parseInt(form.getRuleForNewID()[i]));

				if (statusMessage.equalsIgnoreCase("success")) {

					System.out.println("Successfully updated rules details into rule Table.");
					statusMessage = "success";

				} else {
					System.out.println("Failed to update rules details into rule table.");
					statusMessage = "error";
				}
			}
		}

		return statusMessage;
	}

	@Override
	public String configureCupboard(LibraryForm form) {
		librarydaoInf = new LibraryDAOImpl();

		if (form.getEditName().equals("Add")) {

			statusMessage = librarydaoInf.insertCupboard(form.getName());

			if (statusMessage.equalsIgnoreCase("success")) {

				System.out.println("Successfully inserted Cupboard details into Cupboard Table.");

				statusMessage = "success";

			} else {
				System.out.println("Failed to insert Cupboard details into Cupboard table.");
				statusMessage = "error";
			}

		} else {

			statusMessage = librarydaoInf.updateCupboard(form.getName(), form.getCupboardID());

			if (statusMessage.equalsIgnoreCase("success")) {

				System.out.println("Successfully updated Cupboard details into Cupboard Table.");

				statusMessage = "success";

			} else {
				System.out.println("Failed to update Cupboard details into Cupboard table.");
				statusMessage = "error";
			}
		}

		return statusMessage;
	}

	@Override
	public String configureShelf(LibraryForm form) {
		librarydaoInf = new LibraryDAOImpl();

		if (form.getEditName().equals("Add")) {

			statusMessage = librarydaoInf.insertShelf(form.getName(), form.getGenre(), form.getCupboardID());

			if (statusMessage.equalsIgnoreCase("success")) {

				System.out.println("Successfully inserted Shelf details into Shelf Table.");

				statusMessage = "success";

			} else {
				System.out.println("Failed to insert Shelf details into Shelf table.");
				statusMessage = "error";
			}
		} else {

			statusMessage = librarydaoInf.updateShelf(form.getName(), form.getGenre(), form.getShelfID());

			if (statusMessage.equalsIgnoreCase("success")) {

				System.out.println("Successfully updated Shelf details into Shelf Table.");

				statusMessage = "success";

			} else {
				System.out.println("Failed to update Shelf details into Shelf table.");
				statusMessage = "error";
			}
		}

		return statusMessage;
	}

	@Override
	public String registerBooks(LibraryForm form, int academicYearID, int libraryID) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		librarydaoInf = new LibraryDAOImpl();

		int NoOFCopies = form.getNoOfCopies();

		for (int i = 0; i < NoOFCopies; i++) {

			statusMessage = librarydaoInf.insertBooks(form.getName(), form.getAuthor(), form.getGenre(),
					form.getPublication(), form.getEdition(), form.getNewAccNum()[i], form.getPages(),
					form.getDescription(), form.getNewBarcode()[i], form.getPublicationYear(), form.getRegDate(),
					form.getStatus(), form.getDateInactive(), form.getType(), form.getSection(), form.getShelfID(),
					form.getColNo(), form.getVendorID(), form.getPrice(), libraryID, academicYearID);

			int bookID = librarydaoInf.retrieveBookID(form.getName(), form.getStatus());
			Date date = new java.util.Date();

			String NewDate = dateFormat.format(date);

			statusMessage = librarydaoInf.updateBookStatus(form.getStatus(), NewDate, bookID);
		}

		if (statusMessage.equalsIgnoreCase("success")) {

			System.out.println("Successfully inserted rules details into rule Table.");

			statusMessage = "success";

		} else {
			System.out.println("Failed to insert rules details into rule table.");
			statusMessage = "error";
		}

		return statusMessage;
	}

	@Override
	public String updateBookStatus(LibraryForm form) {

		librarydaoInf = new LibraryDAOImpl();

		for (int i = 0; i < form.getNewstatus().length; i++) {

			statusMessage = librarydaoInf.updateBookStatus(form.getNewstatus()[i], form.getNewdate()[i],
					form.getBookID());
		}

		if (statusMessage.equalsIgnoreCase("success")) {

			System.out.println("Successfully updated Book Status details into Status Table.");

			statusMessage = "success";

		} else {
			System.out.println("Failed to update Book Status details into Status table.");
			statusMessage = "error";
		}

		return statusMessage;
	}

	@Override
	public String moveBooks(LibraryForm form) {

		librarydaoInf = new LibraryDAOImpl();

		String NewCheckboxValue = form.getCheckboxValue();

		if (NewCheckboxValue == null) {

			System.out.println("No details found");
			statusMessage = "success";

		} else {

			if (NewCheckboxValue.startsWith(",")) {

				NewCheckboxValue = NewCheckboxValue.substring(1);

				String[] NewCheckboxValue1 = NewCheckboxValue.split(",");

				for (int i = 0; i < NewCheckboxValue1.length; i++) {

					statusMessage = librarydaoInf.transferBooks(form, form.getShelfID(),
							Integer.parseInt(NewCheckboxValue1[i]));
				}

				if (statusMessage.equalsIgnoreCase("success")) {

					System.out.println("Successfully moved books details into books Table.");

				} else {
					System.out.println("Failed to move books details into books table.");
					statusMessage = "error";
				}
			}
		}

		return statusMessage;
	}

	@Override
	public String configureVotingDetails(VotingForm form) {

		votingDaoInf = new VotingDAOImpl();

		try {

			for (int i = 0; i < form.getStudentName().length; i++) {

				if (form.getNameType()[i].equals("HeadGirl")) {

					statusMessage = votingDaoInf.insertHeadGirlDetails(form, form.getStudentName()[i],
							form.getAcademicYearID());
					if (statusMessage.equalsIgnoreCase("success")) {
						System.out.println("Successfully inserted Head Girls Details into table");

					} else {
						System.out.println("Failed to insert Head Girls Details into table");

						statusMessage = "error";
						return statusMessage;
					}
				} else if (form.getNameType()[i].equals("HeadBoy")) {

					statusMessage = votingDaoInf.insertHeadBoyDetails(form, form.getStudentName()[i],
							form.getAcademicYearID());
					if (statusMessage.equalsIgnoreCase("success")) {
						System.out.println("Successfully inserted Head Boys Details into table");

					} else {
						System.out.println("Failed to insert Head Boys Details into table");

						statusMessage = "error";
						return statusMessage;
					}
				} else if (form.getNameType()[i].equals("Red")) {

					statusMessage = votingDaoInf.insertHouseCaptainRedDetails(form, form.getStudentName()[i],
							form.getAcademicYearID());
					if (statusMessage.equalsIgnoreCase("success")) {
						System.out.println("Successfully inserted Red House Captain Details into table");

					} else {
						System.out.println("Failed to insert Red House Captain Details into table");

						statusMessage = "error";
						return statusMessage;
					}
				} else if (form.getNameType()[i].equals("Blue")) {

					statusMessage = votingDaoInf.insertHouseCaptainBlueDetails(form, form.getStudentName()[i],
							form.getAcademicYearID());
					if (statusMessage.equalsIgnoreCase("success")) {
						System.out.println("Successfully inserted Students Details into table");

					} else {
						System.out.println("Failed to insert Students details into table");

						statusMessage = "error";
						return statusMessage;
					}
				} else if (form.getNameType()[i].equals("Green")) {

					statusMessage = votingDaoInf.insertHouseCaptainGreenDetails(form, form.getStudentName()[i],
							form.getAcademicYearID());
					if (statusMessage.equalsIgnoreCase("success")) {
						System.out.println("Successfully inserted Green House Captain Details into table");

					} else {
						System.out.println("Failed to insert Green House Captain Details into table");

						statusMessage = "error";
						return statusMessage;
					}
				} else if (form.getNameType()[i].equals("Yellow")) {

					statusMessage = votingDaoInf.insertHouseCaptainYellowDetails(form, form.getStudentName()[i],
							form.getAcademicYearID());
					if (statusMessage.equalsIgnoreCase("success")) {
						System.out.println("Successfully inserted Yellow House Captain Details into table");

					} else {
						System.out.println("Failed to insert Yellow House Captain Details into table");

						statusMessage = "error";
						return statusMessage;
					}
				}
			}

			return statusMessage;

		} catch (Exception exception) {
			exception.printStackTrace();
			statusMessage = "input";
			return statusMessage;

		}
	}

	@Override
	public String updateVotingDetails(VotingForm form, int academicYearID) {

		votingDaoInf = new VotingDAOImpl();

		if (form.getEditStudentName() == null) {

			System.out.println("No new student added");

			statusMessage = "success";

		} else {

			for (int i = 0; i < form.getEditStudentName().length; i++) {

				if (form.getEditNameType()[i].equals("HeadGirl")) {

					statusMessage = votingDaoInf.insertHeadGirlDetails(form, form.getEditStudentName()[i],
							academicYearID);
					if (statusMessage.equalsIgnoreCase("success")) {
						System.out.println("Successfully inserted Head Girls Details into table");
						statusMessage = "success";
					} else {
						System.out.println("Failed to insert Head Girls Details into table");

						statusMessage = "error";

					}

				} else if (form.getEditNameType()[i].equals("HeadBoy")) {

					statusMessage = votingDaoInf.insertHeadBoyDetails(form, form.getEditStudentName()[i],
							academicYearID);
					if (statusMessage.equalsIgnoreCase("success")) {

						System.out.println("Successfully inserted Head Boys Details into table");
						statusMessage = "success";
					} else {
						System.out.println("Failed to insert Head Boys Details into table");

						statusMessage = "error";

					}

				} else if (form.getEditNameType()[i].equals("Red")) {

					statusMessage = votingDaoInf.insertHouseCaptainRedDetails(form, form.getEditStudentName()[i],
							academicYearID);
					if (statusMessage.equalsIgnoreCase("success")) {
						System.out.println("Successfully inserted Red House Captain Details into table");
						statusMessage = "success";
					} else {
						System.out.println("Failed to insert Red House Captain Details into table");

						statusMessage = "error";

					}

				} else if (form.getEditNameType()[i].equals("Blue")) {

					statusMessage = votingDaoInf.insertHouseCaptainBlueDetails(form, form.getEditStudentName()[i],
							academicYearID);
					if (statusMessage.equalsIgnoreCase("success")) {
						System.out.println("Successfully inserted Students Details into table");
						statusMessage = "success";
					} else {
						System.out.println("Failed to insert Students details into table");

						statusMessage = "error";

					}

				} else if (form.getEditNameType()[i].equals("Green")) {

					statusMessage = votingDaoInf.insertHouseCaptainGreenDetails(form, form.getEditStudentName()[i],
							academicYearID);
					if (statusMessage.equalsIgnoreCase("success")) {
						System.out.println("Successfully inserted Green House Captain Details into table");
						statusMessage = "success";
					} else {
						System.out.println("Failed to insert Green House Captain Details into table");

						statusMessage = "error";

					}

				} else if (form.getEditNameType()[i].equals("Yellow")) {

					statusMessage = votingDaoInf.insertHouseCaptainYellowDetails(form, form.getEditStudentName()[i],
							academicYearID);
					if (statusMessage.equalsIgnoreCase("success")) {
						System.out.println("Successfully inserted Yellow House Captain Details into table");
						statusMessage = "success";
					} else {
						System.out.println("Failed to insert Yellow House Captain Details into table");

						statusMessage = "error";

					}

				}
			}

		}

		/* update election details */
		System.out.println("form.getStudentName()[i]:" + form.getHouseColorID() + "--" + form.getUpdateNameType());

		if (form.getHouseColorID() == null) {

			System.out.println("No new edit AcademicYear List added");
			statusMessage = "success";

		} else {
			System.out.println("form.getHouseColorID()");
			for (int j = 0; j < form.getHouseColorID().length; j++) {

				System.out.println("form.getStudentName()[i]:" + form.getHouseColorID()[j] + "--"
						+ form.getUpdateNameType()[j] + "--" + form.getCheckboxValue()[j]);

				if (form.getUpdateNameType()[j].equals("HeadGirl")) {

					statusMessage = votingDaoInf.updateHeadGirlDetails(form,
							Integer.parseInt(form.getHouseColorID()[j]), form.getCheckboxValue()[j]);
					if (statusMessage.equalsIgnoreCase("success")) {
						System.out.println("Successfully inserted Head Girls Details into table");
						statusMessage = "success";
					} else {
						System.out.println("Failed to insert Head Girls Details into table");

						statusMessage = "error";

					}

				} else if (form.getUpdateNameType()[j].equals("HeadBoy")) {

					statusMessage = votingDaoInf.updateHeadBoyDetails(form, Integer.parseInt(form.getHouseColorID()[j]),
							form.getCheckboxValue()[j]);
					if (statusMessage.equalsIgnoreCase("success")) {

						System.out.println("Successfully inserted Head Boys Details into table");
						statusMessage = "success";
					} else {
						System.out.println("Failed to insert Head Boys Details into table");

						statusMessage = "error";

					}

				} else if (form.getUpdateNameType()[j].equals("Red")) {

					statusMessage = votingDaoInf.updateHouseCaptainRedDetails(form,
							Integer.parseInt(form.getHouseColorID()[j]), form.getCheckboxValue()[j]);
					if (statusMessage.equalsIgnoreCase("success")) {
						System.out.println("Successfully inserted Red House Captain Details into table");
						statusMessage = "success";
					} else {
						System.out.println("Failed to insert Red House Captain Details into table");

						statusMessage = "error";

					}

				} else if (form.getUpdateNameType()[j].equals("Blue")) {

					statusMessage = votingDaoInf.updateHouseCaptainBlueDetails(form,
							Integer.parseInt(form.getHouseColorID()[j]), form.getCheckboxValue()[j]);
					if (statusMessage.equalsIgnoreCase("success")) {
						System.out.println("Successfully inserted Students Details into table");
						statusMessage = "success";
					} else {
						System.out.println("Failed to insert Students details into table");

						statusMessage = "error";

					}

				} else if (form.getUpdateNameType()[j].equals("Green")) {

					statusMessage = votingDaoInf.updateHouseCaptainGreenDetails(form,
							Integer.parseInt(form.getHouseColorID()[j]), form.getCheckboxValue()[j]);
					if (statusMessage.equalsIgnoreCase("success")) {
						System.out.println("Successfully inserted Green House Captain Details into table");
						statusMessage = "success";
					} else {
						System.out.println("Failed to insert Green House Captain Details into table");

						statusMessage = "error";

					}

				} else if (form.getUpdateNameType()[j].equals("Yellow")) {

					statusMessage = votingDaoInf.updateHouseCaptainYellowDetails(form,
							Integer.parseInt(form.getHouseColorID()[j]), form.getCheckboxValue()[j]);
					if (statusMessage.equalsIgnoreCase("success")) {
						System.out.println("Successfully inserted Yellow House Captain Details into table");
						statusMessage = "success";
					} else {
						System.out.println("Failed to insert Yellow House Captain Details into table");

						statusMessage = "error";

					}

				}

			}

		}
		return statusMessage;
	}

}
