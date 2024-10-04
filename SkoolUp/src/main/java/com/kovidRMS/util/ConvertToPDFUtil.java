package com.kovidRMS.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.kovidRMS.daoImpl.*;
import com.kovidRMS.daoInf.*;
import com.kovidRMS.form.*;
import com.amazonaws.services.s3.AmazonS3;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map.Entry;

/*import org.omg.CORBA.TRANSACTION_MODE;*/

public class ConvertToPDFUtil extends DAOConnection {

	String status = "error";

	/*
	 * Defining Footer event handler class
	 */
	static class FooterTable extends PdfPageEventHelper {
		protected PdfPTable footer;

		public FooterTable(PdfPTable footer) {
			this.footer = footer;
		}

		public void onEndPage(PdfWriter writer, Document document) {
			footer.writeSelectedRows(0, -1, 45, 36, writer.getDirectContent());
		}
	}

	static class FooterTable1 extends PdfPageEventHelper {
		protected PdfPTable footer;

		public FooterTable1(PdfPTable footer) {
			this.footer = footer;
		}

		public void onEndPage(PdfWriter writer, Document document) {
			footer.writeSelectedRows(0, -1, 40, 140, writer.getDirectContent());
			/* footer.writeSelectedRows(0, -1, 45, 170, writer.getDirectContent()); */
		}
	}

	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	PreparedStatement preparedStatement1 = null;
	ResultSet resultSet1 = null;
	PreparedStatement preparedStatement2 = null;
	ResultSet resultSet2 = null;
	PreparedStatement preparedStatement3 = null;
	ResultSet resultSet3 = null;
	PreparedStatement preparedStatement4 = null;
	ResultSet resultSet4 = null;
	PreparedStatement preparedStatement5 = null;
	ResultSet resultSet5 = null;
	PreparedStatement preparedStatement6 = null;
	ResultSet resultSet6 = null;
	PreparedStatement preparedStatement7 = null;
	ResultSet resultSet7 = null;
	PreparedStatement preparedStatement8 = null;
	ResultSet resultSet8 = null;

	/**
	 * 
	 * @param registrationID
	 * @param statusVal
	 * @param s3
	 * @param visitID
	 * @param patientID
	 * @param realPath
	 * @param pdfFIleName
	 * @param logoFilePath
	 * @return
	 */
	public String convertOPDPDF(int studentID, int registrationID, int standardID, String termName, int ayClassID,
			String statusVal, int organisationID, int userID, int AcademicYearID, String realPath, String pdfFIleName) {

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		List<ConfigurationForm> studentDetailsList = null;

		List<ConfigurationForm> coScholasticGradeList = null;

		List<ConfigurationForm> ExtraCurricularGradeList = null;

		List<ConfigurationForm> PersonalityDevelopmentGradeList = null;

		List<ConfigurationForm> PersonalityDevelopmentGradeListNew = null;

		HashMap<String, Integer> NewExaminationList = null;

		List<ConfigurationForm> ScholasticGradeList = null;

		List<ConfigurationForm> AttendanceList = null;

		String studentName = "";
		String standard = "";
		String division = "";
		int rollNo = 0;
		String dateOfBirth = "";
		String GRNo = "";
		int SrNo = 1;

		boolean check9th10th = false;

		/*
		 * Declaring variable which stores the location for FreeSans.ttf file to print
		 * marathi text on pdf
		 */
		String marathiFontDir = realPath + "fonts/FreeSans.ttf";

		try {

			String StandardName = daoInf2.getStandardNameByStandardID(standardID);

			String Stage = daoInf2.getStandardStageByStandardID(standardID);

			String StdDivName = "";

			if (termName.equals("Term II")) {
				StdDivName = daoInf2.retrieveStdDivName(studentID);
			}
			System.out.println("StdDivName:" + StdDivName);

			if (Stage.equals("Primary")) {

				studentDetailsList = daoInf2.retrieveStudentDetailsList(studentID, termName, ayClassID);

				coScholasticGradeList = daoInf2.retrievecoScholasticGradeList(registrationID, termName, ayClassID);

				ExtraCurricularGradeList = daoInf2.retrieveExtraCurricularGradeList(registrationID, termName,
						ayClassID);

				PersonalityDevelopmentGradeList = daoInf2.retrievePersonalityDevelopmentGradeList(registrationID,
						termName, ayClassID);

				PersonalityDevelopmentGradeListNew = daoInf2.retrievePersonalityDevelopmentAbsentList(registrationID,
						termName, ayClassID);

				String attendanceIDList = daoInf2.retrieveAttendanceIDforStudent(termName, AcademicYearID, standardID);

				if (attendanceIDList == null || attendanceIDList == "") {

					AttendanceList = new ArrayList<ConfigurationForm>();

				} else if (attendanceIDList.isEmpty()) {

					AttendanceList = new ArrayList<ConfigurationForm>();

				} else {

					AttendanceList = daoInf2.retrieveAttendanceListforStudent(studentID, attendanceIDList,
							StandardName);
				}

				String NewSubjectList = daoInf2.retrieveSubjectListForStandardByStandardID(standardID);

				NewExaminationList = daoInf2.retrieveExaminationList(AcademicYearID, termName, ayClassID);

				int value, value1, value2 = 0;
				int scaleTo, scaleTo1, scaleTo4 = 0;
				int toatlScaleTo, toatlScaleTo1, totalMarksScaled, internalMarksValue = 0;
				String grade = "";

				if (NewSubjectList.contains(",")) {

					String subArr[] = NewSubjectList.split(",");

					ScholasticGradeList = new ArrayList<ConfigurationForm>();

					for (int j = 0; j < subArr.length; j++) {

						ConfigurationForm form = new ConfigurationForm();

						int gradeCheck = daoInf2.retrieveGradeBasedValue(NewExaminationList.get("Term End"),
								Integer.parseInt(subArr[j]), ayClassID);

						if (gradeCheck == 1) {

							String subjectName = daoInf2.retrieveSubject(Integer.parseInt(subArr[j]));

							boolean termEdCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]), registrationID,
									ayClassID, NewExaminationList.get("Term End"));

							grade = daoInf2.retrieveGradeValue(Integer.parseInt(subArr[j]), registrationID, ayClassID,
									NewExaminationList.get("Term End"));

							if (termEdCheck) {
								grade = "ex";
							}

							form.setTermEndMarks("-");
							form.setFinalTotalMarks("-");
							form.setUnitTestMarks("-");
							form.setSubjectEnrichmentMarks("-");
							form.setSubject(subjectName);
							form.setGrade(grade);

							ScholasticGradeList.add(form);

						} else {

							boolean seAbsentCheck = false;

							for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
								if (entry.getKey().startsWith("Subject Enrichment")
										|| entry.getKey().startsWith("Notebook")) {

									System.out.println(entry.getValue());
									boolean noteBkCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
											registrationID, ayClassID, entry.getValue());

									if (!noteBkCheck) {
										seAbsentCheck = false;
									}

								}
							}

							boolean termEdCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]), registrationID,
									ayClassID, NewExaminationList.get("Term End"));

							boolean unitTestCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]), registrationID,
									ayClassID, NewExaminationList.get("Unit Test"));

							value = daoInf2.retrieveScholasticGradeList(Integer.parseInt(subArr[j]),
									NewExaminationList.get("Term End"), registrationID, ayClassID);

							value1 = daoInf2.retrieveScholasticGradeList(Integer.parseInt(subArr[j]),
									NewExaminationList.get("Unit Test"), registrationID, ayClassID);

							value2 = daoInf2.retrieveScholasticGradeList1(Integer.parseInt(subArr[j]), registrationID,
									ayClassID, termName);

							String subjectName = daoInf2.retrieveSubject(Integer.parseInt(subArr[j]));

							scaleTo = daoInf2.retrieveScaleTo(Integer.parseInt(subArr[j]),
									NewExaminationList.get("Term End"), ayClassID);

							scaleTo1 = daoInf2.retrieveScaleTo(Integer.parseInt(subArr[j]),
									NewExaminationList.get("Unit Test"), ayClassID);

							scaleTo4 = daoInf2.retrieveScaleTo1(Integer.parseInt(subArr[j]), ayClassID);

							internalMarksValue = (value1 + value2);

							totalMarksScaled = (value + internalMarksValue);

							toatlScaleTo = (scaleTo + scaleTo1 + scaleTo4);

							toatlScaleTo1 = (totalMarksScaled * 50) / toatlScaleTo;

							/* Calculating Final Grades */
							if (toatlScaleTo1 >= 46 && toatlScaleTo1 <= 50) {
								grade = "A1";
							} else if (toatlScaleTo1 >= 41 && toatlScaleTo1 <= 45) {
								grade = "A2";
							} else if (toatlScaleTo1 >= 36 && toatlScaleTo1 <= 40) {
								grade = "B1";
							} else if (toatlScaleTo1 >= 31 && toatlScaleTo1 <= 35) {
								grade = "B2";
							} else if (toatlScaleTo1 >= 26 && toatlScaleTo1 <= 30) {
								grade = "C1";
							} else if (toatlScaleTo1 >= 21 && toatlScaleTo1 <= 25) {
								grade = "C2";
							} else if (toatlScaleTo1 >= 17 && toatlScaleTo1 <= 20) {
								grade = "D";
							} else {
								grade = "E";
							}

							/*
							 * To show only grade forEnhanced Marathi Subject in report card and show '-'
							 * for all exams
							 */
							if (subjectName.equals("Enhanced Marathi")) {
								/*
								 * Verifying whether absentFlag is true for any of exam, if so set grade to Ex
								 * else set grade as calculated
								 */
								form.setTermEndMarks("-");
								form.setUnitTestMarks("-");
								// form.setSubjectEnrichmentMarks("-");
								form.setFinalTotalMarks("-");

								if (termEdCheck) {
									grade = "ex";
								}

								if (unitTestCheck || seAbsentCheck) {
									grade = "ex";
								}

							} else {
								/*
								 * Verifying whether absentFlag is true for any of exam, if so set grade to Ex
								 * else set grade as calculated
								 */
								if (termEdCheck) {
									grade = "ex";
									form.setTermEndMarks("ex");
									form.setFinalTotalMarks("ex");
								} else {
									form.setTermEndMarks("" + value);
									form.setFinalTotalMarks("" + totalMarksScaled);
								}

								if (unitTestCheck || seAbsentCheck) {
									form.setUnitTestMarks("ex");
									grade = "ex";
									form.setFinalTotalMarks("ex");
								} else {
									form.setUnitTestMarks("" + internalMarksValue);
									form.setFinalTotalMarks("" + totalMarksScaled);
								}

								if (termEdCheck || unitTestCheck || seAbsentCheck) {

									form.setFinalTotalMarks("ex");
								} else {
									form.setFinalTotalMarks("" + totalMarksScaled);
								}
							}

							form.setSubject(subjectName);
							form.setGrade(grade);

							ScholasticGradeList.add(form);
						}

					}
				}

			} else {

				// Check if the report card is for 9th and 10th student, if so then do not
				// dislay extra curriculum activities table
				check9th10th = daoInf2.verify9th10thClassTeacher(userID, AcademicYearID);

				studentDetailsList = daoInf2.retrieveStudentDetailsList(studentID, termName, ayClassID);

				coScholasticGradeList = daoInf2.retrievecoScholasticGradeList(registrationID, termName, ayClassID);

				ExtraCurricularGradeList = daoInf2.retrieveExtraCurricularGradeList(registrationID, termName,
						ayClassID);

				PersonalityDevelopmentGradeList = daoInf2.retrievePersonalityDevelopmentGradeList(registrationID,
						termName, ayClassID);

				PersonalityDevelopmentGradeListNew = daoInf2.retrievePersonalityDevelopmentAbsentList(registrationID,
						termName, ayClassID);

				String attendanceIDList = daoInf2.retrieveAttendanceIDforStudent(termName, AcademicYearID, standardID);

				if (attendanceIDList == null || attendanceIDList == "") {

					AttendanceList = new ArrayList<ConfigurationForm>();

				} else if (attendanceIDList.isEmpty()) {

					AttendanceList = new ArrayList<ConfigurationForm>();

				} else {

					AttendanceList = daoInf2.retrieveAttendanceListforStudent(studentID, attendanceIDList,
							StandardName);
				}

				String NewSubjectList = daoInf2.retrieveSubjectListForStandardByStandardID(standardID);

				NewExaminationList = daoInf2.retrieveExaminationList(AcademicYearID, termName, ayClassID);

				int value, value1, value2, value3, value4, value5 = 0;
				int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5, toatlScaleTo, toatlScaleTo1,
						totalMarksScaled, internalMarksValue = 0;
				String grade = "";

				if (NewSubjectList.contains(",")) {

					String subArr[] = NewSubjectList.split(",");

					ScholasticGradeList = new ArrayList<ConfigurationForm>();

					for (int j = 0; j < subArr.length; j++) {

						ConfigurationForm form = new ConfigurationForm();

						int gradeCheck = daoInf2.retrieveGradeBasedValue(NewExaminationList.get("Term End"),
								Integer.parseInt(subArr[j]), ayClassID);

						if (gradeCheck == 1) {

							String subjectName = daoInf2.retrieveSubject(Integer.parseInt(subArr[j]));

							boolean termEdCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]), registrationID,
									ayClassID, NewExaminationList.get("Term End"));

							grade = daoInf2.retrieveGradeValue(Integer.parseInt(subArr[j]), registrationID, ayClassID,
									NewExaminationList.get("Term End"));

							if (termEdCheck) {
								grade = "ex";
							}

							form.setTermEndMarks("-");
							form.setFinalTotalMarks("-");
							form.setSubject(subjectName);
							form.setUnitTestMarks("-");
							form.setGrade(grade);

							ScholasticGradeList.add(form);

						} else {

							boolean seAbsentCheck = false;

							for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
								if (entry.getKey().startsWith("Subject Enrichment")) {

									System.out.println(entry.getValue());
									boolean sbjEnrch1Check = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
											registrationID, ayClassID, entry.getValue());

									if (!sbjEnrch1Check) {
										seAbsentCheck = false;
									}

								}
							}

							boolean nbAbsentCheck = false;
							for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
								if (entry.getKey().startsWith("Notebook")) {

									System.out.println(entry.getValue());
									boolean noteBkCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
											registrationID, ayClassID, entry.getValue());

									if (!noteBkCheck) {
										nbAbsentCheck = false;
									}

								}
							}

							boolean portAbsentCheck = false;

							for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
								if (entry.getKey().startsWith("Portfolio")) {

									System.out.println(entry.getValue());
									boolean portCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
											registrationID, ayClassID, entry.getValue());

									if (!portCheck) {
										portAbsentCheck = false;
									}

								}
							}

							boolean multiAbsentCheck = false;
							for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
								if (entry.getKey().startsWith("Multiple Assessment")) {

									System.out.println(entry.getValue());
									boolean multiCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
											registrationID, ayClassID, entry.getValue());

									if (!multiCheck) {
										multiAbsentCheck = false;
									}

								}
							}

							boolean termEdCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]), registrationID,
									ayClassID, NewExaminationList.get("Term End"));

							boolean unitTestCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]), registrationID,
									ayClassID, NewExaminationList.get("Unit Test"));

							value = daoInf2.retrieveScholasticGradeList(Integer.parseInt(subArr[j]),
									NewExaminationList.get("Term End"), registrationID, ayClassID);

							value1 = daoInf2.retrieveScholasticGradeList(Integer.parseInt(subArr[j]),
									NewExaminationList.get("Unit Test"), registrationID, ayClassID);

							value2 = daoInf2.retrieveScholasticGradeListNew(Integer.parseInt(subArr[j]),
									"Subject Enrichment", registrationID, ayClassID, termName);

							value3 = daoInf2.retrieveScholasticGradeListNew(Integer.parseInt(subArr[j]), "Notebook",
									registrationID, ayClassID, termName);

							value4 = daoInf2.retrieveScholasticGradeListNew(Integer.parseInt(subArr[j]), "Portfolio",
									registrationID, ayClassID, termName);

							value5 = daoInf2.retrieveScholasticGradeListNew(Integer.parseInt(subArr[j]),
									"Multiple Assessment", registrationID, ayClassID, termName);

							String subjectName = daoInf2.retrieveSubject(Integer.parseInt(subArr[j]));

							scaleTo = daoInf2.retrieveScaleTo(Integer.parseInt(subArr[j]),
									NewExaminationList.get("Term End"), ayClassID);

							scaleTo1 = daoInf2.retrieveScaleTo(Integer.parseInt(subArr[j]),
									NewExaminationList.get("Unit Test"), ayClassID);

							scaleTo2 = daoInf2.retrieveScaleToNew(Integer.parseInt(subArr[j]), "Subject Enrichment",
									ayClassID, AcademicYearID, termName);

							scaleTo3 = daoInf2.retrieveScaleToNew(Integer.parseInt(subArr[j]), "Notebook", ayClassID,
									AcademicYearID, termName);

							scaleTo4 = daoInf2.retrieveScaleToNew(Integer.parseInt(subArr[j]), "Portfolio", ayClassID,
									AcademicYearID, termName);

							scaleTo5 = daoInf2.retrieveScaleToNew(Integer.parseInt(subArr[j]), "Multiple Assessment",
									ayClassID, AcademicYearID, termName);

							internalMarksValue = (value1 + value2 + value3 + value4 + value5);

							totalMarksScaled = (value + internalMarksValue);

							toatlScaleTo = (scaleTo + scaleTo1 + scaleTo2 + scaleTo3 + scaleTo4 + scaleTo5);

							toatlScaleTo1 = (totalMarksScaled * 100) / toatlScaleTo;

							/* Calculating Final Grades */
							if (toatlScaleTo1 >= 91 && toatlScaleTo1 <= 100) {
								grade = "A1";
							} else if (toatlScaleTo1 >= 81 && toatlScaleTo1 <= 90) {
								grade = "A2";
							} else if (toatlScaleTo1 >= 71 && toatlScaleTo1 <= 80) {
								grade = "B1";
							} else if (toatlScaleTo1 >= 61 && toatlScaleTo1 <= 70) {
								grade = "B2";
							} else if (toatlScaleTo1 >= 51 && toatlScaleTo1 <= 60) {
								grade = "C1";
							} else if (toatlScaleTo1 >= 41 && toatlScaleTo1 <= 50) {
								grade = "C2";
							} else if (toatlScaleTo1 >= 33 && toatlScaleTo1 <= 40) {
								grade = "D";
							} else {
								grade = "E";
							}

							/*
							 * To show only grade forEnhanced Marathi Subject in report card and show '-'
							 * for all exams
							 */
							if (subjectName.equals("Enhanced Marathi")) {
								/*
								 * Verifying whether absentFlag is true for any of exam, if so set grade to Ex
								 * else set grade as calculated
								 */
								form.setTermEndMarks("-");
								form.setUnitTestMarks("-");
								form.setFinalTotalMarks("-");

								if (termEdCheck) {
									grade = "ex";
								}

								if (unitTestCheck || seAbsentCheck || nbAbsentCheck || portAbsentCheck
										|| multiAbsentCheck) {
									grade = "ex";
								}

							} else {
								/*
								 * Verifying whether absentFlag is true for any of exam, if so set grade to Ex
								 * else set grade as calculated
								 */
								if (termEdCheck) {
									grade = "ex";
									form.setTermEndMarks("ex");
									form.setFinalTotalMarks("ex");
								} else {
									form.setTermEndMarks("" + value);
									form.setFinalTotalMarks("" + totalMarksScaled);
								}

								if (unitTestCheck || seAbsentCheck || nbAbsentCheck || portAbsentCheck
										|| multiAbsentCheck) {
									form.setUnitTestMarks("ex");
									grade = "ex";
									form.setFinalTotalMarks("ex");
								} else {
									form.setUnitTestMarks("" + internalMarksValue);
									form.setFinalTotalMarks("" + totalMarksScaled);
								}

								if (termEdCheck || unitTestCheck || seAbsentCheck || nbAbsentCheck || portAbsentCheck
										|| multiAbsentCheck) {

									form.setFinalTotalMarks("ex");
								} else {
									form.setFinalTotalMarks("" + totalMarksScaled);
								}
							}

							form.setSubject(subjectName);
							form.setGrade(grade);

							ScholasticGradeList.add(form);
						}
					}
				}
			}

			for (ConfigurationForm form : studentDetailsList) {
				studentName = form.getStudentName();
				rollNo = form.getRollNumber();
				standard = form.getStandard();
				division = form.getDivision();
				GRNo = form.getGrNumber();
				dateOfBirth = form.getDateOfBirth();
			}

			/*
			 * Image path for posterior segment images
			 */
			String boardLogoImage = daoInf1.retrieveBoardLogo(organisationID);

			String organisationImage = daoInf1.retrieveOrganizationLogo(organisationID);

			System.out.println("organisation : " + organisationImage);

			String SignatureFile = daoInf2.retrieveUserSignatureFile(userID);

			String PrincipalSignature = daoInf1.retrievePrincipalSignatureFile(organisationID);

			String SchoolSeal = daoInf1.retrieveOrganizationSeal(organisationID);

			/*
			 * Setting path to store PDF file
			 */
			File file = new File(realPath + "/" + pdfFIleName);
			/*
			 * Creating Document for PDF
			 */
			Document document = null;

			document = new Document(PageSize.A4);

			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));

			Font Font1 = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.NORMAL);
			Font Font3 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
			Font Font2 = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);
			Font Font4 = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD);
			Font Font5 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
			Font mainContent = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL);
			Font mainContent1 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
			// mainContent1.setColor(BaseColor.GRAY);

			/*
			 * Defining marathi font
			 */
			// Font marathiFont = FontFactory.getFont(marathiFontDir, BaseFont.IDENTITY_H,
			// BaseFont.EMBEDDED);

			document.open();

			Image image1 = Image.getInstance(boardLogoImage);

			Image image2 = Image.getInstance(organisationImage);

			Image signImg = Image.getInstance(SignatureFile);

			Image principalSign = Image.getInstance(PrincipalSignature);

			Image sealImg = Image.getInstance(SchoolSeal);

			String PrimaryHeadmistressSignature = daoInf1.retrievePrimaryHeadmistressSignature(organisationID);
			String PrimaryHeadmistressSeal = daoInf1.retrievePrimaryHeadmistressSeal(organisationID);
			String SecondaryHeadmistressSignature = daoInf1.retrieveSecondaryHeadmistressSignatur(organisationID);
			String SecondaryHeadmistressSeal = daoInf1.retrieveSecondaryHeadmistressSeal(organisationID);
			String signOnRCCheck = daoInf1.retrieveSignOnRC(organisationID);

			Image PrimaryHeadSignature = Image.getInstance(PrimaryHeadmistressSignature);
			Image PrimaryHeadSeal = Image.getInstance(PrimaryHeadmistressSeal);
			Image SecondaryHeadSignature = Image.getInstance(SecondaryHeadmistressSignature);
			Image SecondaryHeadSeal = Image.getInstance(SecondaryHeadmistressSeal);
			/*
			 * Setting header
			 */
			document.addCreator("KovidRMS");
			document.addTitle("Student Report");

			PdfPTable table = new PdfPTable(3);

			table.setFooterRows(1);
			table.setWidthPercentage(100);
			Rectangle rect = new Rectangle(270, 700);
			table.setWidthPercentage(new float[] { 25, 220, 35 }, rect);

			PdfPCell cell = new PdfPCell(image1, true);
			cell.setPaddingBottom(10);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setUseBorderPadding(true);
			cell.setBorderWidthBottom(1f);
			cell.setBorderColor(BaseColor.WHITE);

			PdfPCell cell2 = new PdfPCell(new Paragraph(daoInf1.retrieveOrganizationTagLine(organisationID) + "\n"
					+ daoInf1.retrieveOrganizationName(organisationID) + "\n"
					+ daoInf1.retrieveOrganizationAddress(organisationID) + "\n"
					+ daoInf1.retrieveOrganizationPhone(organisationID), Font5));
			cell2.setBorderWidth(0.01f);
			cell2.setPaddingBottom(10);
			cell2.setBorderWidthLeft(0.2f);
			cell2.setBorderColor(BaseColor.WHITE);
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell cell3 = new PdfPCell(image2, true);
			cell3.setPaddingBottom(10);
			cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell3.setUseBorderPadding(true);
			cell3.setBorderWidthBottom(1f);
			cell3.setBorderColor(BaseColor.WHITE);

			PdfPCell cell4 = new PdfPCell(new Paragraph());
			cell4.setColspan(3);
			cell4.setBorderWidthBottom(0f);
			cell4.setBorderWidthLeft(0f);
			cell4.setBorderWidthRight(0f);
			cell4.setBorderWidthTop(1f);
			cell4.setBorderColor(BaseColor.DARK_GRAY);

			/*
			 * adding all cell to the table to create tabular structure
			 */

			table.addCell(cell);
			// table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			table.addCell(cell4);

			document.add(table);

			/*
			 * for student details
			 */
			PdfPTable table2 = new PdfPTable(4);
			table2.setWidthPercentage(100);
			Rectangle rect1 = new Rectangle(270, 700);
			table2.setWidthPercentage(new float[] { 145, 45, 45, 45 }, rect1);

			// for Title
			PdfPCell cell0 = new PdfPCell(new Paragraph(
					"Report Card " + termName + " (" + daoInf1.retrieveAcademicYearName(organisationID) + ")", Font5));

			cell0.setPaddingTop(10);
			cell0.setPaddingBottom(5);
			cell0.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell0.setUseBorderPadding(true);
			cell0.setColspan(4);
			cell0.setBorderColor(BaseColor.WHITE);

			// Gene
			PdfPCell cell1 = new PdfPCell(new Paragraph("Student Name: " + studentName, mainContent1));

			cell1.setBorderWidth(0.01f);
			cell1.setPaddingBottom(5);
			cell1.setPaddingTop(5);
			cell1.setBorderColor(BaseColor.WHITE);

			// OD RE
			PdfPCell cell5 = new PdfPCell(new Paragraph("Standard: " + standard, mainContent1));

			cell5.setBorderWidth(0.01f);
			cell5.setPaddingBottom(5);
			cell5.setPaddingTop(5);
			cell5.setBorderColor(BaseColor.WHITE);

			// Os LE
			PdfPCell cell6 = new PdfPCell(new Paragraph("Division: " + division, mainContent1));

			cell6.setBorderWidth(0.01f);
			cell6.setPaddingBottom(5);
			cell6.setPaddingTop(5);
			cell6.setBorderColor(BaseColor.WHITE);

			// Os LE
			PdfPCell cell7 = new PdfPCell(new Paragraph("Roll Number: " + rollNo, mainContent1));

			cell7.setBorderWidth(0.01f);
			cell7.setPaddingBottom(5);
			cell7.setPaddingTop(5);
			cell7.setBorderColor(BaseColor.WHITE);

			// Os LE
			PdfPCell cell8 = new PdfPCell(new Paragraph("DOB: " + dateOfBirth, mainContent1));

			cell8.setBorderWidth(0.01f);
			cell8.setPaddingBottom(5);
			cell8.setPaddingTop(5);
			cell8.setBorderColor(BaseColor.WHITE);

			// Os LE
			PdfPCell cell9 = new PdfPCell(new Paragraph("", mainContent1));

			cell9.setBorderWidth(0.01f);
			cell9.setPaddingBottom(5);
			cell9.setPaddingTop(5);
			cell9.setBorderColor(BaseColor.WHITE);

			// Os LE
			PdfPCell cell11 = new PdfPCell(new Paragraph("GR Number: " + GRNo, mainContent1));

			cell11.setBorderWidth(0.01f);
			cell11.setPaddingBottom(5);
			cell11.setColspan(2);
			cell11.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell11.setPaddingTop(5);
			cell11.setBorderColor(BaseColor.WHITE);

			table2.addCell(cell0);
			table2.addCell(cell1);
			table2.addCell(cell5);
			table2.addCell(cell6);
			table2.addCell(cell7);
			table2.addCell(cell8);
			table2.addCell(cell9);
			table2.addCell(cell11);

			document.add(table2);

			if (StandardName.equals("I") || StandardName.equals("II")
					|| StandardName.equals("III") | StandardName.equals("IV")) {

				/*
				 * For scholastic
				 */
				PdfPTable table3 = new PdfPTable(6);
				table3.setWidthPercentage(100);
				Rectangle rect2 = new Rectangle(270, 700);
				table3.setWidthPercentage(new float[] { 30, 60, 45, 45, 45, 45 }, rect2);

				// For blank space
				PdfPCell cell12 = new PdfPCell(new Paragraph("", Font2));
				cell12.setColspan(7);
				cell12.setBorderWidthRight(0f);
				cell12.setBorderWidthLeft(0f);
				cell12.setBorderWidthTop(0f);
				cell12.setBorderWidthBottom(0f);
				cell12.setBorderColorTop(BaseColor.WHITE);

				// For blank space
				PdfPCell cell012 = new PdfPCell(new Paragraph("Scholastic Areas: ", Font5));
				cell012.setColspan(7);
				cell012.setBorderWidthRight(0f);
				cell012.setBorderWidthLeft(0f);
				cell012.setBorderWidthTop(0f);
				cell012.setBorderWidthBottom(0f);
				cell012.setBorderColorTop(BaseColor.WHITE);

				// Compound
				PdfPCell cell103 = new PdfPCell(new Paragraph("Sr No.", mainContent1));

				// cell13.setBorderWidth(0.01f);
				cell103.setPaddingBottom(3);
				cell103.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell103.setBorderColor(BaseColor.BLACK);

				// Compound
				PdfPCell cell13 = new PdfPCell(new Paragraph("Subjects", mainContent1));

				// cell13.setBorderWidth(0.01f);
				cell13.setPaddingBottom(3);
				cell13.setBorderColor(BaseColor.BLACK);

				// Quantity
				PdfPCell cell15 = new PdfPCell(new Paragraph("Internal Marks\n(10)", mainContent1));

				// cell15.setBorderWidth(0.01f);
				cell15.setPaddingBottom(3);
				cell15.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell15.setBorderColor(BaseColor.BLACK);

				// Frequency
				PdfPCell cell17 = new PdfPCell(new Paragraph("Term End\n(40)", mainContent1));

				// cell17.setBorderWidth(0.01f);
				cell17.setPaddingBottom(3);
				cell17.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell17.setBorderColor(BaseColor.BLACK);

				// Comment
				PdfPCell cell18 = new PdfPCell(new Paragraph("Marks Obtained\n(50)", mainContent1));

				// cell18.setBorderWidth(0.01f);
				cell18.setPaddingBottom(3);
				cell18.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell18.setBorderColor(BaseColor.BLACK);

				// Instruction
				PdfPCell cell19 = new PdfPCell(new Paragraph("Grade", mainContent1));

				// cell19.setBorderWidth(0.01f);
				cell19.setPaddingBottom(3);
				cell19.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell19.setBorderColor(BaseColor.BLACK);

				table3.addCell(cell12);
				table3.addCell(cell012);
				table3.addCell(cell103);
				table3.addCell(cell13);
				table3.addCell(cell15);
				table3.addCell(cell17);
				table3.addCell(cell18);
				table3.addCell(cell19);

				for (ConfigurationForm form : ScholasticGradeList) {

					// Compound
					PdfPCell cell120 = new PdfPCell(new Paragraph("" + SrNo, mainContent));

					cell120.setPaddingBottom(3);
					cell120.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell120.setBorderColor(BaseColor.BLACK);

					// Compound
					PdfPCell cell20 = new PdfPCell(new Paragraph(form.getSubject(), mainContent));

					// cell20.setBorderWidth(0.01f);
					cell20.setPaddingBottom(3);
					// cell20.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell20.setBorderColor(BaseColor.BLACK);

					// Quantity
					PdfPCell cell22 = new PdfPCell(new Paragraph("" + form.getUnitTestMarks(), mainContent));

					// cell22.setBorderWidth(0.01f);
					cell22.setPaddingBottom(3);
					cell22.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell22.setBorderColor(BaseColor.BLACK);

					// Frequency
					PdfPCell cell24 = new PdfPCell(new Paragraph("" + form.getTermEndMarks(), mainContent));

					// cell24.setBorderWidth(0.01f);
					cell24.setPaddingBottom(3);
					cell24.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell24.setBorderColor(BaseColor.BLACK);

					// Comment
					PdfPCell cell25 = new PdfPCell(new Paragraph("" + form.getFinalTotalMarks(), mainContent));

					// cell25.setBorderWidth(0.01f);
					cell25.setPaddingBottom(3);
					cell25.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell25.setBorderColor(BaseColor.BLACK);

					// Instruction
					PdfPCell cell26 = new PdfPCell(new Paragraph(form.getGrade(), mainContent));

					// cell26.setBorderWidth(0.01f);
					cell26.setPaddingBottom(3);
					cell26.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell26.setBorderColor(BaseColor.BLACK);

					table3.addCell(cell120);
					table3.addCell(cell20);
					table3.addCell(cell22);
					table3.addCell(cell24);
					table3.addCell(cell25);
					table3.addCell(cell26);

					SrNo++;
				}

				document.add(table3);

			} else {

				/*
				 * For scholastic
				 */
				PdfPTable table3 = new PdfPTable(6);
				table3.setWidthPercentage(100);
				Rectangle rect2 = new Rectangle(270, 700);
				table3.setWidthPercentage(new float[] { 30, 60, 45, 45, 45, 45 }, rect2);

				// For blank space
				PdfPCell cell12 = new PdfPCell(new Paragraph("", Font2));
				cell12.setColspan(6);
				cell12.setBorderWidthRight(0f);
				cell12.setBorderWidthLeft(0f);
				cell12.setBorderWidthTop(0f);
				cell12.setBorderWidthBottom(0f);
				cell12.setBorderColorTop(BaseColor.WHITE);

				// For blank space
				PdfPCell cell012 = new PdfPCell(new Paragraph("Scholastic Areas: ", Font5));
				cell012.setColspan(6);
				cell012.setBorderWidthRight(0f);
				cell012.setBorderWidthLeft(0f);
				cell012.setBorderWidthTop(0f);
				cell012.setBorderWidthBottom(0f);
				cell012.setBorderColorTop(BaseColor.WHITE);

				// Compound
				PdfPCell cell103 = new PdfPCell(new Paragraph("Sr No.", mainContent1));

				// cell13.setBorderWidth(0.01f);
				cell103.setPaddingBottom(3);
				cell103.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell103.setBorderColor(BaseColor.BLACK);

				// Compound
				PdfPCell cell13 = new PdfPCell(new Paragraph("Subjects", mainContent1));

				// cell13.setBorderWidth(0.01f);
				cell13.setPaddingBottom(3);
				cell13.setBorderColor(BaseColor.BLACK);

				// Quantity
				PdfPCell cell16 = new PdfPCell(new Paragraph("Internal Marks\n(20)", mainContent1));

				// cell16.setBorderWidth(0.01f);
				cell16.setPaddingBottom(3);
				cell16.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell16.setBorderColor(BaseColor.BLACK);

				// Frequency
				PdfPCell cell17 = new PdfPCell(new Paragraph("Term End\n(30/80)", mainContent1));

				// cell17.setBorderWidth(0.01f);
				cell17.setPaddingBottom(3);
				cell17.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell17.setBorderColor(BaseColor.BLACK);

				// Comment
				PdfPCell cell18 = new PdfPCell(new Paragraph("Marks Obtained\n(50/100)", mainContent1));

				// cell18.setBorderWidth(0.01f);
				cell18.setPaddingBottom(3);
				cell18.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell18.setBorderColor(BaseColor.BLACK);

				// Instruction
				PdfPCell cell19 = new PdfPCell(new Paragraph("Grade", mainContent1));

				// cell19.setBorderWidth(0.01f);
				cell19.setPaddingBottom(3);
				cell19.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell19.setBorderColor(BaseColor.BLACK);

				table3.addCell(cell12);
				table3.addCell(cell012);
				table3.addCell(cell103);
				table3.addCell(cell13);
				table3.addCell(cell16);
				table3.addCell(cell17);
				table3.addCell(cell18);
				table3.addCell(cell19);

				for (ConfigurationForm form : ScholasticGradeList) {

					// Compound
					PdfPCell cell120 = new PdfPCell(new Paragraph("" + SrNo, mainContent));

					cell120.setPaddingBottom(3);
					cell120.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell120.setBorderColor(BaseColor.BLACK);

					// Compound
					PdfPCell cell20 = new PdfPCell(new Paragraph(form.getSubject(), mainContent));

					// cell20.setBorderWidth(0.01f);
					cell20.setPaddingBottom(3);
					cell20.setBorderColor(BaseColor.BLACK);

					// Quantity
					PdfPCell cell23 = new PdfPCell(new Paragraph("" + form.getUnitTestMarks(), mainContent));

					// cell23.setBorderWidth(0.01f);
					cell23.setPaddingBottom(3);
					cell23.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell23.setBorderColor(BaseColor.BLACK);

					// Frequency
					PdfPCell cell24 = new PdfPCell(new Paragraph("" + form.getTermEndMarks(), mainContent));

					// cell24.setBorderWidth(0.01f);
					cell24.setPaddingBottom(3);
					cell24.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell24.setBorderColor(BaseColor.BLACK);

					// Comment
					PdfPCell cell25 = new PdfPCell(new Paragraph("" + form.getFinalTotalMarks(), mainContent));

					// cell25.setBorderWidth(0.01f);
					cell25.setPaddingBottom(3);
					cell25.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell25.setBorderColor(BaseColor.BLACK);

					// Instruction
					PdfPCell cell26 = new PdfPCell(new Paragraph(form.getGrade(), mainContent));

					// cell26.setBorderWidth(0.01f);
					cell26.setPaddingBottom(3);
					cell26.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell26.setBorderColor(BaseColor.BLACK);

					table3.addCell(cell120);
					table3.addCell(cell20);
					table3.addCell(cell23);
					table3.addCell(cell24);
					table3.addCell(cell25);
					table3.addCell(cell26);

					SrNo++;
				}

				document.add(table3);

			}

			/*
			 * For Co scholastic and Extra curriculum activities
			 */
			PdfPTable table3 = new PdfPTable(2);
			table3.setWidthPercentage(100);
			Rectangle rect2 = new Rectangle(270, 700);
			table3.setWidthPercentage(new float[] { 135, 135 }, rect2);

			PdfPCell cell15 = new PdfPCell(new Paragraph(
					"                                                                                               "
							+ "                                                                                                                            "
							+ "                                                                                                                              ",
					Font2));
			cell15.setColspan(2);
			cell15.setBorderWidthRight(0f);
			cell15.setBorderWidthLeft(0f);
			cell15.setBorderWidthTop(0f);
			cell15.setBorderWidthBottom(0f);
			// cell15.setPaddingTop(5);
			cell15.setBorderColorTop(BaseColor.WHITE);

			table3.addCell(cell15);
			document.add(table3);

			Paragraph paragraph = new Paragraph();
			PdfPCell celll = null;
			// celll = new PdfPCell(new Paragraph("\n\n"));
			// mainTable.addCell(celll);

			if (ExtraCurricularGradeList.size() == 0) {

				// Main table
				PdfPTable mainTable = new PdfPTable(3);
				Rectangle mainTableRect = new Rectangle(270, 700);
				mainTable.setWidthPercentage(new float[] { 65, 140, 65 }, mainTableRect);

				PdfPCell coscholasticBlankTableCell1 = new PdfPCell();
				coscholasticBlankTableCell1.setBorder(PdfPCell.NO_BORDER);
				// coscholasticBlankTableCell1.setPaddingRight(20);

				PdfPCell coscholasticBlankTableCell2 = new PdfPCell();
				coscholasticBlankTableCell2.setBorder(PdfPCell.NO_BORDER);

				// First table
				PdfPCell coscholasticTableCell = new PdfPCell();
				coscholasticTableCell.setBorder(PdfPCell.NO_BORDER);
				// coscholasticTableCell.setPaddingRight(20);

				PdfPTable coscholasticTable = new PdfPTable(3);
				Rectangle coscholasticRect = new Rectangle(270, 700);
				coscholasticTable.setWidthPercentage(new float[] { 50, 170, 50 }, coscholasticRect);
				// coscholasticTable.setWidthPercentage(100);
				celll = new PdfPCell(new Phrase("Co-Scholastic Areas: ", Font5));
				celll.setPaddingBottom(5);
				celll.setColspan(3);
				coscholasticTable.addCell(celll);
				celll = new PdfPCell(new Phrase("Sr No.", mainContent1));
				celll.setHorizontalAlignment(Element.ALIGN_CENTER);
				coscholasticTable.addCell(celll);
				celll = new PdfPCell(new Phrase("Subjects", mainContent1));
				coscholasticTable.addCell(celll);
				celll = new PdfPCell(new Phrase("Grade", mainContent1));
				celll.setHorizontalAlignment(Element.ALIGN_CENTER);
				coscholasticTable.addCell(celll);

				SrNo = 1;

				for (ConfigurationForm form : coScholasticGradeList) {

					celll = new PdfPCell(new Phrase("" + SrNo, mainContent));
					celll.setHorizontalAlignment(Element.ALIGN_CENTER);
					coscholasticTable.addCell(celll);
					celll = new PdfPCell(new Phrase(form.getSubject(), mainContent));
					coscholasticTable.addCell(celll);
					celll = new PdfPCell(new Phrase(form.getGrade(), mainContent));
					celll.setHorizontalAlignment(Element.ALIGN_CENTER);
					coscholasticTable.addCell(celll);

					SrNo++;
				}

				coscholasticTableCell.addElement(coscholasticTable);
				mainTable.addCell(coscholasticBlankTableCell1);
				mainTable.addCell(coscholasticTableCell);
				mainTable.addCell(coscholasticBlankTableCell2);

				paragraph.add(mainTable);

			} else {

				// Main table
				PdfPTable mainTable = new PdfPTable(2);
				mainTable.setWidthPercentage(101);

				// First table
				PdfPCell coscholasticTableCell = new PdfPCell();
				coscholasticTableCell.setBorder(PdfPCell.NO_BORDER);
				coscholasticTableCell.setPaddingRight(20);

				PdfPTable coscholasticTable = new PdfPTable(3);
				Rectangle coscholasticRect = new Rectangle(270, 700);
				coscholasticTable.setWidthPercentage(new float[] { 50, 170, 50 }, coscholasticRect);
				// coscholasticTable.setWidthPercentage(100);
				celll = new PdfPCell(new Phrase("Co-Scholastic Areas: ", Font5));
				celll.setPaddingBottom(5);
				celll.setColspan(3);
				coscholasticTable.addCell(celll);
				celll = new PdfPCell(new Phrase("Sr No.", mainContent1));
				celll.setHorizontalAlignment(Element.ALIGN_CENTER);
				coscholasticTable.addCell(celll);
				celll = new PdfPCell(new Phrase("Subjects", mainContent1));
				coscholasticTable.addCell(celll);
				celll = new PdfPCell(new Phrase("Grade", mainContent1));
				celll.setHorizontalAlignment(Element.ALIGN_CENTER);
				coscholasticTable.addCell(celll);

				SrNo = 1;

				for (ConfigurationForm form : coScholasticGradeList) {

					celll = new PdfPCell(new Phrase("" + SrNo, mainContent));
					celll.setHorizontalAlignment(Element.ALIGN_CENTER);
					coscholasticTable.addCell(celll);
					celll = new PdfPCell(new Phrase(form.getSubject(), mainContent));
					coscholasticTable.addCell(celll);
					celll = new PdfPCell(new Phrase(form.getGrade(), mainContent));
					celll.setHorizontalAlignment(Element.ALIGN_CENTER);
					coscholasticTable.addCell(celll);

					SrNo++;
				}

				coscholasticTableCell.addElement(coscholasticTable);
				mainTable.addCell(coscholasticTableCell);

				// Second table
				PdfPCell extrcrclmTableCell = new PdfPCell();
				extrcrclmTableCell.setBorder(PdfPCell.NO_BORDER);
				extrcrclmTableCell.setPaddingLeft(20);

				PdfPTable extrcrclmTable = new PdfPTable(3);
				Rectangle extrcrclmRect = new Rectangle(270, 700);
				extrcrclmTable.setWidthPercentage(new float[] { 50, 170, 50 }, extrcrclmRect);
				// extrcrclmTable.setWidthPercentage(100);
				celll = new PdfPCell(new Phrase("Extra Curricular Activities: ", Font5));
				celll.setColspan(3);
				celll.setPaddingBottom(5);
				extrcrclmTable.addCell(celll);
				celll = new PdfPCell(new Phrase("Sr No.", mainContent1));
				celll.setHorizontalAlignment(Element.ALIGN_CENTER);
				extrcrclmTable.addCell(celll);
				celll = new PdfPCell(new Phrase("Subjects", mainContent1));
				extrcrclmTable.addCell(celll);
				celll = new PdfPCell(new Phrase("Grade", mainContent1));
				celll.setHorizontalAlignment(Element.ALIGN_CENTER);
				extrcrclmTable.addCell(celll);

				SrNo = 1;

				for (ConfigurationForm form : ExtraCurricularGradeList) {
					celll = new PdfPCell(new Phrase("" + SrNo, mainContent));
					celll.setHorizontalAlignment(Element.ALIGN_CENTER);
					extrcrclmTable.addCell(celll);
					celll = new PdfPCell(new Phrase(form.getSubject(), mainContent));
					extrcrclmTable.addCell(celll);
					celll = new PdfPCell(new Phrase(form.getGrade(), mainContent));
					celll.setHorizontalAlignment(Element.ALIGN_CENTER);
					extrcrclmTable.addCell(celll);

					SrNo++;
				}
				extrcrclmTableCell.addElement(extrcrclmTable);
				mainTable.addCell(extrcrclmTableCell);

				paragraph.add(mainTable);
			}

			document.add(paragraph);

			/*
			 * For Personality Development & Grading Scale
			 */
			PdfPTable table4 = new PdfPTable(2);
			table4.setWidthPercentage(100);
			Rectangle rect3 = new Rectangle(270, 700);
			table4.setWidthPercentage(new float[] { 135, 135 }, rect3);

			PdfPCell cell18 = new PdfPCell(new Paragraph(
					"                                                                                               "
							+ "                                                                                                                            "
							+ "                                                                                                                              ",
					Font2));
			cell18.setColspan(2);
			cell18.setBorderWidthRight(0f);
			cell18.setBorderWidthLeft(0f);
			cell18.setBorderWidthTop(0f);
			cell18.setBorderWidthBottom(0f);
			// cell18.setPaddingTop(10);
			cell18.setBorderColorTop(BaseColor.WHITE);

			table4.addCell(cell18);

			document.add(table4);

			Paragraph paragraph1 = new Paragraph();
			PdfPCell celll1 = null;
			// celll = new PdfPCell(new Paragraph("\n\n"));
			// Main table
			PdfPTable mainTable1 = new PdfPTable(2);
			mainTable1.setWidthPercentage(101);
			// mainTable.addCell(celll);
			// First table
			PdfPCell persnltdvlpmntTableCell = new PdfPCell();
			persnltdvlpmntTableCell.setBorder(PdfPCell.NO_BORDER);
			persnltdvlpmntTableCell.setPaddingRight(20);

			PdfPTable persnltdvlpmntTable = new PdfPTable(3);
			Rectangle persnltdvlpmntRect = new Rectangle(270, 700);
			persnltdvlpmntTable.setWidthPercentage(new float[] { 50, 170, 50 }, persnltdvlpmntRect);
			// persnltdvlpmntTable.setWidthPercentage(100);
			celll1 = new PdfPCell(new Phrase("Personality Development: ", Font5));
			celll1.setColspan(3);
			celll1.setPaddingBottom(5);
			persnltdvlpmntTable.addCell(celll1);
			celll1 = new PdfPCell(new Phrase("Sr No.", mainContent1));
			celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
			persnltdvlpmntTable.addCell(celll1);
			celll1 = new PdfPCell(new Phrase("Subjects", mainContent1));
			persnltdvlpmntTable.addCell(celll1);
			celll1 = new PdfPCell(new Phrase("Grade", mainContent1));
			celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
			persnltdvlpmntTable.addCell(celll1);

			SrNo = 1;

			for (ConfigurationForm form : PersonalityDevelopmentGradeList) {
				celll1 = new PdfPCell(new Phrase("" + SrNo, mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				persnltdvlpmntTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase(form.getSubject(), mainContent));
				persnltdvlpmntTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase(form.getGrade(), mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				persnltdvlpmntTable.addCell(celll1);

				SrNo++;
			}

			for (ConfigurationForm form1 : PersonalityDevelopmentGradeListNew) {
				celll1 = new PdfPCell(new Phrase("" + SrNo, mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				persnltdvlpmntTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase(form1.getSubject(), mainContent));
				persnltdvlpmntTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase(form1.getGrade(), mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				persnltdvlpmntTable.addCell(celll1);

				SrNo++;
			}

			persnltdvlpmntTableCell.addElement(persnltdvlpmntTable);
			mainTable1.addCell(persnltdvlpmntTableCell);

			// Second table
			PdfPCell gradnscleTableCell = new PdfPCell();
			gradnscleTableCell.setBorder(PdfPCell.NO_BORDER);
			gradnscleTableCell.setPaddingLeft(20);

			PdfPTable attendanceTable = new PdfPTable(2);
			attendanceTable.setWidthPercentage(100);

			celll1 = new PdfPCell(new Phrase("Attendance: ", Font5));
			celll1.setColspan(2);
			celll1.setPaddingBottom(5);
			attendanceTable.addCell(celll1);

			for (ConfigurationForm form : AttendanceList) {

				celll1 = new PdfPCell(new Phrase(form.getWorkingMonth(), mainContent));
				attendanceTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase(form.getStudentWorkingDays(), mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				attendanceTable.addCell(celll1);
			}

			System.out.println("...............AttendanceList.size() :" + AttendanceList.size());

			if (AttendanceList.size() > 0) {
				gradnscleTableCell.addElement(attendanceTable);
			}

			PdfPTable gradnscleTable = new PdfPTable(4);
			gradnscleTable.setWidthPercentage(100);

			celll1 = new PdfPCell(new Phrase("\n"));
			celll1.setBorder(PdfPCell.NO_BORDER);
			celll1.setColspan(4);
			gradnscleTable.addCell(celll1);

			celll1 = new PdfPCell(new Phrase("Grading Scale: ", Font5));
			celll1.setPaddingBottom(5);
			celll1.setColspan(4);
			gradnscleTable.addCell(celll1);

			if (Stage.equals("Primary")) {

				celll1 = new PdfPCell(new Phrase("Marks", mainContent1));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("Grade", mainContent1));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("Marks", mainContent1));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("Grade", mainContent1));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("46-50", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("A1", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("26-30", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("C1", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("41-45", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("A2", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("21-25", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("C2", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("36-40", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("B1", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("17-20", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("D", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("31-35", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("B2", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("16 & Below", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("E", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);

			} else {

				celll1 = new PdfPCell(new Phrase("Marks", mainContent1));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("Grade", mainContent1));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("Marks", mainContent1));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("Grade", mainContent1));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("91-100", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("A1", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("51-60", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("C1", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("81-90", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("A2", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("41-50", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("C2", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("71-80", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("B1", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("33-40", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("D", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("61-70", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("B2", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("32 & Below", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("E", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
			}

			gradnscleTableCell.addElement(gradnscleTable);
			// mainTable1.addCell(gradnscleTableCell);

			// grade scale2 table
			PdfPCell persnlygradeTableCell = new PdfPCell();
			persnlygradeTableCell.setBorder(PdfPCell.NO_BORDER);
			persnlygradeTableCell.setPaddingLeft(20);

			PdfPTable persnlygradeTable = new PdfPTable(1);
			persnlygradeTable.setWidthPercentage(100);

			celll1 = new PdfPCell(new Phrase("\n"));
			celll1.setBorder(PdfPCell.NO_BORDER);
			persnlygradeTable.addCell(celll1);

			celll1 = new PdfPCell(new Phrase("Grade Code for Personality Development", Font5));
			celll1.setPaddingBottom(5);
			// celll2.setColspan(2);
			persnlygradeTable.addCell(celll1);
			celll1 = new PdfPCell(new Phrase("G = Good; S = Satisfactory; NI = Needs Improvement", mainContent));
			persnlygradeTable.addCell(celll1);

			gradnscleTableCell.addElement(persnlygradeTable);
			mainTable1.addCell(gradnscleTableCell);

			paragraph1.add(mainTable1);
			document.add(paragraph1);

			/*
			 * For signature in footer
			 */
			PdfPTable table11 = new PdfPTable(1);
			table11.setTotalWidth(510);
			table11.setWidthPercentage(100);
			Rectangle rect6 = new Rectangle(270, 700);
			table11.setWidthPercentage(new float[] { 270 }, rect6);

			float paddingTop = 2;

			if (Stage.equals("Primary")) {
				paddingTop = 10;
			}

			/* for Promoted Standard division */
			if (StdDivName != null && StdDivName != "") {

				if (statusVal == null || statusVal == "") {
					PdfPCell cell110 = new PdfPCell(new Paragraph("Promoted to - " + StdDivName, Font5));

					cell110.setPaddingTop(paddingTop);
					cell110.setPaddingBottom(20);
					cell110.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell110.setUseBorderPadding(true);
					cell110.setColspan(4);
					cell110.setBorderColor(BaseColor.WHITE);
					table11.addCell(cell110);
				} else if (statusVal.isEmpty()) {
					PdfPCell cell110 = new PdfPCell(new Paragraph("Promoted to - " + StdDivName, Font5));

					cell110.setPaddingTop(paddingTop);
					cell110.setPaddingBottom(20);
					cell110.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell110.setUseBorderPadding(true);
					cell110.setColspan(4);
					cell110.setBorderColor(BaseColor.WHITE);
					table11.addCell(cell110);
				} else if (statusVal.equals("null")) {
					PdfPCell cell110 = new PdfPCell(new Paragraph("Promoted to - " + StdDivName, Font5));

					cell110.setPaddingTop(paddingTop);
					cell110.setPaddingBottom(20);
					cell110.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell110.setUseBorderPadding(true);
					cell110.setColspan(4);
					cell110.setBorderColor(BaseColor.WHITE);
					table11.addCell(cell110);
				} else {
					if(statusVal.equals("Detained in")) {
						PdfPCell cell110 = new PdfPCell(new Paragraph(statusVal + " - " + standard+""+division, Font5));

						cell110.setPaddingTop(paddingTop);
						cell110.setPaddingBottom(20);
						cell110.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell110.setUseBorderPadding(true);
						cell110.setColspan(4);
						cell110.setBorderColor(BaseColor.WHITE);
						table11.addCell(cell110);
					}else {
						PdfPCell cell110 = new PdfPCell(new Paragraph(statusVal + " - " + StdDivName, Font5));

						cell110.setPaddingTop(paddingTop);
						cell110.setPaddingBottom(20);
						cell110.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell110.setUseBorderPadding(true);
						cell110.setColspan(4);
						cell110.setBorderColor(BaseColor.WHITE);
						table11.addCell(cell110);
					}
				}

				document.add(table11);
			}

			PdfPTable table1 = new PdfPTable(3);
			table1.setTotalWidth(510);
			table1.setWidthPercentage(100);
			Rectangle rect5 = new Rectangle(270, 700);
			table1.setWidthPercentage(new float[] { 100, 100, 70 }, rect5);

			PdfPTable imageTable2 = new PdfPTable(2);
			imageTable2.setWidthPercentage(80);
			imageTable2.setWidths(new int[] { 1, 1 });
			if (Stage.equals("Primary") && signOnRCCheck.equals("sectionHead")) {
				PdfPCell imageCell12 = new PdfPCell(PrimaryHeadSeal, true);
				imageCell12.setBorderColor(BaseColor.WHITE);
				imageCell12.setPaddingLeft(30);
				// imageCell12.setPaddingTop(30);

				PdfPCell imageCell24 = new PdfPCell(new Paragraph("", mainContent));
				imageCell24.setBorderColor(BaseColor.WHITE);

				//PdfPCell imageCell22 = new PdfPCell(PrimaryHeadSignature, true);
				PdfPCell imageCell22 = new PdfPCell(principalSign, true);
				/* imageCell22.setPaddingTop(50); */
				imageCell22.setPaddingLeft(30);
				imageCell22.setBorderColor(BaseColor.WHITE);

				PdfPCell imageCell26 = new PdfPCell(new Paragraph("", mainContent));
				imageCell26.setBorderColor(BaseColor.WHITE);

				imageTable2.addCell(imageCell12);
				imageTable2.addCell(imageCell24);
				imageTable2.addCell(imageCell22);
				imageTable2.addCell(imageCell26);
			} else if (Stage.equals("Secondary") && signOnRCCheck.equals("sectionHead")) {
				PdfPCell imageCell12 = new PdfPCell(SecondaryHeadSeal, true);
				imageCell12.setBorderColor(BaseColor.WHITE);
				imageCell12.setPaddingLeft(30);
				// imageCell12.setPaddingTop(30);

				PdfPCell imageCell24 = new PdfPCell(new Paragraph("", mainContent));
				imageCell24.setBorderColor(BaseColor.WHITE);

				//PdfPCell imageCell22 = new PdfPCell(SecondaryHeadSignature, true);
				PdfPCell imageCell22 = new PdfPCell(principalSign, true);
				/* imageCell22.setPaddingTop(50); */
				imageCell22.setPaddingLeft(30);
				imageCell22.setBorderColor(BaseColor.WHITE);

				PdfPCell imageCell26 = new PdfPCell(new Paragraph("", mainContent));
				imageCell26.setBorderColor(BaseColor.WHITE);

				imageTable2.addCell(imageCell12);
				imageTable2.addCell(imageCell24);
				imageTable2.addCell(imageCell22);
				imageTable2.addCell(imageCell26);
			} else {
				PdfPCell imageCell12 = new PdfPCell(sealImg, true);
				imageCell12.setBorderColor(BaseColor.WHITE);
				imageCell12.setPaddingLeft(30);
				// imageCell12.setPaddingTop(30);

				PdfPCell imageCell24 = new PdfPCell(new Paragraph("", mainContent));
				imageCell24.setBorderColor(BaseColor.WHITE);

				PdfPCell imageCell22 = new PdfPCell(principalSign, true);
				/* imageCell22.setPaddingTop(50); */
				imageCell22.setPaddingLeft(30);
				imageCell22.setBorderColor(BaseColor.WHITE);

				PdfPCell imageCell26 = new PdfPCell(new Paragraph("", mainContent));
				imageCell26.setBorderColor(BaseColor.WHITE);

				imageTable2.addCell(imageCell12);
				imageTable2.addCell(imageCell24);
				imageTable2.addCell(imageCell22);
				imageTable2.addCell(imageCell26);
			}

			PdfPCell cell11101 = new PdfPCell(imageTable2);
			cell11101.setBackgroundColor(BaseColor.WHITE);
			cell11101.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11101.setBorderColor(BaseColor.WHITE);

			PdfPTable imageTable1 = new PdfPTable(2);
			imageTable1.setWidthPercentage(100);
			imageTable1.setWidths(new int[] { 2, 2 });

			PdfPCell imageCell11 = new PdfPCell(signImg, true);
			imageCell11.setHorizontalAlignment(Element.ALIGN_RIGHT);
			imageCell11.setBorderColor(BaseColor.WHITE);

			PdfPCell imageCell21 = new PdfPCell(new Paragraph("", mainContent));
			imageCell21.setBorderColor(BaseColor.WHITE);

			imageTable1.addCell(imageCell11);
			imageTable1.addCell(imageCell21);

			PdfPCell cell1101 = new PdfPCell(imageTable1);
			cell1101.setBackgroundColor(BaseColor.WHITE);
			cell1101.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1101.setPaddingTop(60);
			cell1101.setPaddingLeft(30);

			cell1101.setBorderColor(BaseColor.WHITE);

			PdfPCell cell111101 = new PdfPCell(new Paragraph("", Font5));
			cell111101.setBackgroundColor(BaseColor.WHITE);
			cell111101.setBorderColor(BaseColor.WHITE);

			if (Stage.equals("Primary") && signOnRCCheck.equals("sectionHead")) {
				PdfPCell cell111 = new PdfPCell(new Paragraph("Signature of Class Teacher", Font5));
				cell111.setBackgroundColor(BaseColor.WHITE);
				cell111.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell111.setBorderColor(BaseColor.WHITE);

				PdfPCell cell1111 = new PdfPCell(
						new Paragraph("Signature of Principal", Font5));
				cell1111.setBackgroundColor(BaseColor.WHITE);
				cell1111.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell1111.setBorderColor(BaseColor.WHITE);

				PdfPCell cell11111 = new PdfPCell(new Paragraph("Signature of Parent", Font5));
				cell11111.setBackgroundColor(BaseColor.WHITE);
				cell11111.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell11111.setBorderColor(BaseColor.WHITE);

				table1.addCell(cell1101);
				table1.addCell(cell11101);
				table1.addCell(cell111101);
				
				table1.addCell(cell111);
				table1.addCell(cell1111);
				table1.addCell(cell11111);
			} else if (Stage.equals("Secondary") && signOnRCCheck.equals("sectionHead")) {
				PdfPCell cell111 = new PdfPCell(new Paragraph("Signature of Class Teacher", Font5));
				cell111.setBackgroundColor(BaseColor.WHITE);
				cell111.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell111.setBorderColor(BaseColor.WHITE);

				PdfPCell cell1111 = new PdfPCell(
						new Paragraph("Signature of Principal", Font5));
				cell1111.setBackgroundColor(BaseColor.WHITE);
				cell1111.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell1111.setBorderColor(BaseColor.WHITE);

				PdfPCell cell11111 = new PdfPCell(new Paragraph("Signature of Parent", Font5));
				cell11111.setBackgroundColor(BaseColor.WHITE);
				cell11111.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell11111.setBorderColor(BaseColor.WHITE);

				table1.addCell(cell1101);
				table1.addCell(cell11101);
				table1.addCell(cell111101);
				
				table1.addCell(cell111);
				table1.addCell(cell1111);
				table1.addCell(cell11111);
			} else {
				PdfPCell cell111 = new PdfPCell(new Paragraph("Signature of Class Teacher", Font5));
				cell111.setBackgroundColor(BaseColor.WHITE);
				cell111.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell111.setBorderColor(BaseColor.WHITE);

				PdfPCell cell1111 = new PdfPCell(new Paragraph("Signature of Principal", Font5));
				cell1111.setBackgroundColor(BaseColor.WHITE);
				cell1111.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell1111.setBorderColor(BaseColor.WHITE);

				PdfPCell cell11111 = new PdfPCell(new Paragraph("Signature of Parent", Font5));
				cell11111.setBackgroundColor(BaseColor.WHITE);
				cell11111.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell11111.setBorderColor(BaseColor.WHITE);

				table1.addCell(cell1101);
				table1.addCell(cell11101);
				table1.addCell(cell111101);

				table1.addCell(cell111);
				table1.addCell(cell1111);
				table1.addCell(cell11111);
			}

			FooterTable1 event = new FooterTable1(table1);
			writer.setPageEvent(event);

			document.close();

			System.out.println("Successfully written and generated Student PDF Report");

			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();
			status = "error";
		}
		return status;
	}

	public String convertOPDPDFALL(int standardID, int divisionID, String termName, int ayClassID, int organisationID,
			int userID, int AcademicYearID, String realPath, String pdfFIleName) {

		// int count = 1;

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		List<ConfigurationForm> studentDetailsList = null;

		List<ConfigurationForm> coScholasticGradeList = null;

		List<ConfigurationForm> ExtraCurricularGradeList = null;

		List<ConfigurationForm> PersonalityDevelopmentGradeList = null;

		List<ConfigurationForm> PersonalityDevelopmentGradeListNew = null;

		HashMap<String, Integer> NewExaminationList = null;

		List<ConfigurationForm> ScholasticGradeList = null;

		List<ConfigurationForm> AttendanceList = null;

		boolean check9th10th = false;

		String studentName = "";
		String standard = "";
		String division = "";
		int rollNo = 0;
		String dateOfBirth = "";
		String GRNo = "";

		ConfigurationForm conform = new ConfigurationForm();

		int studentID = 0;

		int registrationID = 0;

		int SrNo = 1;

		String statusValue = "";

		try {

			/*
			 * Setting path to store PDF file
			 */
			File file = new File(realPath + "/" + pdfFIleName);
			/*
			 * Creating Document for PDF
			 */
			Document document = null;

			document = new Document(PageSize.A4);

			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));

			Font Font1 = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.NORMAL);
			Font Font3 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
			Font Font2 = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);
			Font Font4 = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD);
			Font Font5 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
			Font mainContent = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL);
			Font mainContent1 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
			// mainContent1.setColor(BaseColor.GRAY);

			document.open();

			/*
			 * Image path for posterior segment images
			 */
			String boardLogoImage = daoInf1.retrieveBoardLogo(organisationID);

			String organisationImage = daoInf1.retrieveOrganizationLogo(organisationID);

			String SignatureFile = daoInf2.retrieveUserSignatureFile(userID);

			String PrincipalSignature = daoInf1.retrievePrincipalSignatureFile(organisationID);

			String SchoolSeal = daoInf1.retrieveOrganizationSeal(organisationID);

			String PrimaryHeadmistressSignature = daoInf1.retrievePrimaryHeadmistressSignature(organisationID);
			String PrimaryHeadmistressSeal = daoInf1.retrievePrimaryHeadmistressSeal(organisationID);
			String SecondaryHeadmistressSignature = daoInf1.retrieveSecondaryHeadmistressSignatur(organisationID);
			String SecondaryHeadmistressSeal = daoInf1.retrieveSecondaryHeadmistressSeal(organisationID);

			Image image1 = Image.getInstance(boardLogoImage);

			Image image2 = Image.getInstance(organisationImage);

			Image signImg = Image.getInstance(SignatureFile);

			Image principalSign = Image.getInstance(PrincipalSignature);

			Image sealImg = Image.getInstance(SchoolSeal);

			String signOnRCCheck = daoInf1.retrieveSignOnRC(organisationID);

			Image PrimaryHeadSignature = Image.getInstance(PrimaryHeadmistressSignature);
			Image PrimaryHeadSeal = Image.getInstance(PrimaryHeadmistressSeal);
			Image SecondaryHeadSignature = Image.getInstance(SecondaryHeadmistressSignature);
			Image SecondaryHeadSeal = Image.getInstance(SecondaryHeadmistressSeal);

			connection = getConnection();

			String retrieveStudentIDBYStandardAndDivisionQuery = QueryMaker.RETRIEVE_StudentID_List;

			preparedStatement = connection.prepareStatement(retrieveStudentIDBYStandardAndDivisionQuery);

			preparedStatement.setInt(1, standardID);
			preparedStatement.setInt(2, divisionID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);
			preparedStatement.setString(4, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				studentID = resultSet.getInt("studentID");

				registrationID = resultSet.getInt("id");

				statusValue = resultSet.getString("studentStatus");

				String StandardName = daoInf2.getStandardNameByStandardID(standardID);

				String Stage = daoInf2.getStandardStageByStandardID(standardID);

				String StdDivName = daoInf2.retrieveStdDivName(studentID);

				if (Stage.equals("Primary")) {

					studentDetailsList = daoInf2.retrieveStudentDetailsList(studentID, termName, ayClassID);

					coScholasticGradeList = daoInf2.retrievecoScholasticGradeList(registrationID, termName, ayClassID);

					ExtraCurricularGradeList = daoInf2.retrieveExtraCurricularGradeList(registrationID, termName,
							ayClassID);

					PersonalityDevelopmentGradeList = daoInf2.retrievePersonalityDevelopmentGradeList(registrationID,
							termName, ayClassID);

					PersonalityDevelopmentGradeListNew = daoInf2
							.retrievePersonalityDevelopmentAbsentList(registrationID, termName, ayClassID);

					String attendanceIDList = daoInf2.retrieveAttendanceIDforStudent(termName, AcademicYearID,
							standardID);

					if (attendanceIDList == null || attendanceIDList == "") {

						AttendanceList = new ArrayList<ConfigurationForm>();

					} else if (attendanceIDList.isEmpty()) {

						AttendanceList = new ArrayList<ConfigurationForm>();

					} else {

						AttendanceList = daoInf2.retrieveAttendanceListforStudent(studentID, attendanceIDList,
								StandardName);
					}

					String NewSubjectList = daoInf2.retrieveSubjectListForStandardByStandardID(standardID);

					NewExaminationList = daoInf2.retrieveExaminationList(AcademicYearID, termName, ayClassID);

					int value, value1, value2, totalMarksScaled = 0;
					int scaleTo, scaleTo1, scaleTo4 = 0;
					int toatlScaleTo, toatlScaleTo1, internalMarksValue = 0;
					String grade = "";

					if (NewSubjectList.contains(",")) {

						String subArr[] = NewSubjectList.split(",");

						ScholasticGradeList = new ArrayList<ConfigurationForm>();

						for (int j = 0; j < subArr.length; j++) {

							ConfigurationForm form = new ConfigurationForm();

							int gradeCheck = daoInf2.retrieveGradeBasedValue(NewExaminationList.get("Term End"),
									Integer.parseInt(subArr[j]), ayClassID);

							// System.out.println("grade check.." + gradeCheck);

							if (gradeCheck == 1) {

								String subjectName = daoInf2.retrieveSubject(Integer.parseInt(subArr[j]));

								boolean termEdCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]), registrationID,
										ayClassID, NewExaminationList.get("Term End"));

								grade = daoInf2.retrieveGradeValue(Integer.parseInt(subArr[j]), registrationID,
										ayClassID, NewExaminationList.get("Term End"));

								if (termEdCheck) {
									grade = "ex";
								}

								form.setTermEndMarks("-");
								form.setFinalTotalMarks("-");
								form.setUnitTestMarks("-");
								form.setSubject(subjectName);
								form.setGrade(grade);

								ScholasticGradeList.add(form);

							} else {

								boolean seAbsentCheck = false;

								for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
									if (entry.getKey().startsWith("Subject Enrichment")
											|| entry.getKey().startsWith("Notebook")) {

										System.out.println(entry.getValue());
										boolean noteBkCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
												registrationID, ayClassID, entry.getValue());

										if (!noteBkCheck) {
											seAbsentCheck = false;
										}

									}
								}

								boolean termEdCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]), registrationID,
										ayClassID, NewExaminationList.get("Term End"));

								boolean unitTestCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
										registrationID, ayClassID, NewExaminationList.get("Unit Test"));

								value = daoInf2.retrieveScholasticGradeList(Integer.parseInt(subArr[j]),
										NewExaminationList.get("Term End"), registrationID, ayClassID);

								value1 = daoInf2.retrieveScholasticGradeList(Integer.parseInt(subArr[j]),
										NewExaminationList.get("Unit Test"), registrationID, ayClassID);

								value2 = daoInf2.retrieveScholasticGradeList1(Integer.parseInt(subArr[j]),
										registrationID, ayClassID, termName);

								String subjectName = daoInf2.retrieveSubject(Integer.parseInt(subArr[j]));

								scaleTo = daoInf2.retrieveScaleTo(Integer.parseInt(subArr[j]),
										NewExaminationList.get("Term End"), ayClassID);

								scaleTo1 = daoInf2.retrieveScaleTo(Integer.parseInt(subArr[j]),
										NewExaminationList.get("Unit Test"), ayClassID);

								scaleTo4 = daoInf2.retrieveScaleTo1(Integer.parseInt(subArr[j]), ayClassID);

								internalMarksValue = (value1 + value2);

								totalMarksScaled = (value + internalMarksValue);

								toatlScaleTo = (scaleTo + scaleTo1 + scaleTo4);

								toatlScaleTo1 = (totalMarksScaled * 50) / toatlScaleTo;

								/* Calculating Final Grades */
								if (toatlScaleTo1 >= 46 && toatlScaleTo1 <= 50) {
									grade = "A1";
								} else if (toatlScaleTo1 >= 41 && toatlScaleTo1 <= 45) {
									grade = "A2";
								} else if (toatlScaleTo1 >= 36 && toatlScaleTo1 <= 40) {
									grade = "B1";
								} else if (toatlScaleTo1 >= 31 && toatlScaleTo1 <= 35) {
									grade = "B2";
								} else if (toatlScaleTo1 >= 26 && toatlScaleTo1 <= 30) {
									grade = "C1";
								} else if (toatlScaleTo1 >= 21 && toatlScaleTo1 <= 25) {
									grade = "C2";
								} else if (toatlScaleTo1 >= 17 && toatlScaleTo1 <= 20) {
									grade = "D";
								} else {
									grade = "E";
								}

								/*
								 * To show only grade forEnhanced Marathi Subject in report card and show '-'
								 * for all exams
								 */
								if (subjectName.equals("Enhanced Marathi")) {
									/*
									 * Verifying whether absentFlag is true for any of exam, if so set grade to Ex
									 * else set grade as calculated
									 */
									form.setTermEndMarks("-");
									form.setUnitTestMarks("-");
									form.setFinalTotalMarks("-");

									if (termEdCheck) {
										grade = "ex";
									}

									if (unitTestCheck || seAbsentCheck) {
										grade = "ex";
									}

								} else {

									if (termEdCheck) {
										grade = "ex";
										form.setTermEndMarks("ex");
										form.setFinalTotalMarks("ex");
									} else {
										form.setTermEndMarks("" + value);
										form.setFinalTotalMarks("" + totalMarksScaled);
									}

									if (unitTestCheck || seAbsentCheck) {
										form.setUnitTestMarks("ex");
										grade = "ex";
										form.setFinalTotalMarks("ex");
									} else {
										form.setUnitTestMarks("" + internalMarksValue);
										form.setFinalTotalMarks("" + totalMarksScaled);
									}

									if (termEdCheck || unitTestCheck || seAbsentCheck) {

										form.setFinalTotalMarks("ex");
									} else {
										form.setFinalTotalMarks("" + totalMarksScaled);
									}
								}

								form.setSubject(subjectName);
								form.setGrade(grade);

								ScholasticGradeList.add(form);
							}

						}
					}

				} else {

					// Check if the report card is for 9th and 10th student, if so then do not
					// dislay extra curriculum activities table
					check9th10th = daoInf2.verify9th10thClassTeacher(userID, AcademicYearID);

					studentDetailsList = daoInf2.retrieveStudentDetailsList(studentID, termName, ayClassID);

					coScholasticGradeList = daoInf2.retrievecoScholasticGradeList(registrationID, termName, ayClassID);

					ExtraCurricularGradeList = daoInf2.retrieveExtraCurricularGradeList(registrationID, termName,
							ayClassID);

					PersonalityDevelopmentGradeList = daoInf2.retrievePersonalityDevelopmentGradeList(registrationID,
							termName, ayClassID);

					PersonalityDevelopmentGradeListNew = daoInf2
							.retrievePersonalityDevelopmentAbsentList(registrationID, termName, ayClassID);

					String attendanceIDList = daoInf2.retrieveAttendanceIDforStudent(termName, AcademicYearID,
							standardID);

					if (attendanceIDList == null || attendanceIDList == "") {

						AttendanceList = new ArrayList<ConfigurationForm>();

					} else if (attendanceIDList.isEmpty()) {

						AttendanceList = new ArrayList<ConfigurationForm>();

					} else {

						AttendanceList = daoInf2.retrieveAttendanceListforStudent(studentID, attendanceIDList,
								StandardName);
					}

					String NewSubjectList = daoInf2.retrieveSubjectListForStandardByStandardID(standardID);

					NewExaminationList = daoInf2.retrieveExaminationList(AcademicYearID, termName, ayClassID);

					int value, value1, value2, value3, value4, value5 = 0;
					int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5, toatlScaleTo, toatlScaleTo1,
							totalMarksScaled, internalMarksValue = 0;
					String grade = "";

					if (NewSubjectList.contains(",")) {

						String subArr[] = NewSubjectList.split(",");

						ScholasticGradeList = new ArrayList<ConfigurationForm>();

						for (int j = 0; j < subArr.length; j++) {

							ConfigurationForm form = new ConfigurationForm();

							int gradeCheck = daoInf2.retrieveGradeBasedValue(NewExaminationList.get("Term End"),
									Integer.parseInt(subArr[j]), ayClassID);

							if (gradeCheck == 1) {

								String subjectName = daoInf2.retrieveSubject(Integer.parseInt(subArr[j]));

								boolean termEdCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]), registrationID,
										ayClassID, NewExaminationList.get("Term End"));

								grade = daoInf2.retrieveGradeValue(Integer.parseInt(subArr[j]), registrationID,
										ayClassID, NewExaminationList.get("Term End"));

								if (termEdCheck) {
									grade = "ex";
								}

								form.setTermEndMarks("-");
								form.setFinalTotalMarks("-");
								form.setSubject(subjectName);
								form.setUnitTestMarks("-");
								form.setGrade(grade);

								ScholasticGradeList.add(form);

							} else {

								boolean seAbsentCheck = false;

								for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
									if (entry.getKey().startsWith("Subject Enrichment")) {

										System.out.println(entry.getValue());
										boolean sbjEnrch1Check = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
												registrationID, ayClassID, entry.getValue());

										if (!sbjEnrch1Check) {
											seAbsentCheck = false;
										}

									}
								}

								boolean nbAbsentCheck = false;
								for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
									if (entry.getKey().startsWith("Notebook")) {

										System.out.println(entry.getValue());
										boolean noteBkCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
												registrationID, ayClassID, entry.getValue());

										if (!noteBkCheck) {
											nbAbsentCheck = false;
										}

									}
								}

								boolean portAbsentCheck = false;

								for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
									if (entry.getKey().startsWith("Portfolio")) {

										System.out.println(entry.getValue());
										boolean portCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
												registrationID, ayClassID, entry.getValue());

										if (!portCheck) {
											portAbsentCheck = false;
										}

									}
								}

								boolean multiAbsentCheck = false;
								for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
									if (entry.getKey().startsWith("Multiple Assessment")) {

										System.out.println(entry.getValue());
										boolean multiCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
												registrationID, ayClassID, entry.getValue());

										if (!multiCheck) {
											multiAbsentCheck = false;
										}

									}
								}

								boolean termEdCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]), registrationID,
										ayClassID, NewExaminationList.get("Term End"));

								boolean unitTestCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
										registrationID, ayClassID, NewExaminationList.get("Unit Test"));

								value = daoInf2.retrieveScholasticGradeList(Integer.parseInt(subArr[j]),
										NewExaminationList.get("Term End"), registrationID, ayClassID);

								value1 = daoInf2.retrieveScholasticGradeList(Integer.parseInt(subArr[j]),
										NewExaminationList.get("Unit Test"), registrationID, ayClassID);

								value2 = daoInf2.retrieveScholasticGradeListNew(Integer.parseInt(subArr[j]),
										"Subject Enrichment", registrationID, ayClassID, termName);

								value3 = daoInf2.retrieveScholasticGradeListNew(Integer.parseInt(subArr[j]), "Notebook",
										registrationID, ayClassID, termName);

								value4 = daoInf2.retrieveScholasticGradeListNew(Integer.parseInt(subArr[j]),
										"Portfolio", registrationID, ayClassID, termName);

								value5 = daoInf2.retrieveScholasticGradeListNew(Integer.parseInt(subArr[j]),
										"Multiple Assessment", registrationID, ayClassID, termName);

								String subjectName = daoInf2.retrieveSubject(Integer.parseInt(subArr[j]));

								scaleTo = daoInf2.retrieveScaleTo(Integer.parseInt(subArr[j]),
										NewExaminationList.get("Term End"), ayClassID);

								scaleTo1 = daoInf2.retrieveScaleTo(Integer.parseInt(subArr[j]),
										NewExaminationList.get("Unit Test"), ayClassID);

								scaleTo2 = daoInf2.retrieveScaleToNew(Integer.parseInt(subArr[j]), "Subject Enrichment",
										ayClassID, AcademicYearID, termName);

								scaleTo3 = daoInf2.retrieveScaleToNew(Integer.parseInt(subArr[j]), "Notebook",
										ayClassID, AcademicYearID, termName);

								scaleTo4 = daoInf2.retrieveScaleToNew(Integer.parseInt(subArr[j]), "Portfolio",
										ayClassID, AcademicYearID, termName);

								scaleTo5 = daoInf2.retrieveScaleToNew(Integer.parseInt(subArr[j]),
										"Multiple Assessment", ayClassID, AcademicYearID, termName);

								internalMarksValue = (value1 + value2 + value3 + value4 + value5);

								totalMarksScaled = (value + internalMarksValue);

								toatlScaleTo = (scaleTo + scaleTo1 + scaleTo2 + scaleTo3 + scaleTo4 + scaleTo5);

								toatlScaleTo1 = (totalMarksScaled * 100) / toatlScaleTo;

								/* Calculating Final Grades */
								if (toatlScaleTo1 >= 91 && toatlScaleTo1 <= 100) {
									grade = "A1";
								} else if (toatlScaleTo1 >= 81 && toatlScaleTo1 <= 90) {
									grade = "A2";
								} else if (toatlScaleTo1 >= 71 && toatlScaleTo1 <= 80) {
									grade = "B1";
								} else if (toatlScaleTo1 >= 61 && toatlScaleTo1 <= 70) {
									grade = "B2";
								} else if (toatlScaleTo1 >= 51 && toatlScaleTo1 <= 60) {
									grade = "C1";
								} else if (toatlScaleTo1 >= 41 && toatlScaleTo1 <= 50) {
									grade = "C2";
								} else if (toatlScaleTo1 >= 33 && toatlScaleTo1 <= 40) {
									grade = "D";
								} else {
									grade = "E";
								}

								/*
								 * To show only grade forEnhanced Marathi Subject in report card and show '-'
								 * for all exams
								 */
								if (subjectName.equals("Enhanced Marathi")) {
									/*
									 * Verifying whether absentFlag is true for any of exam, if so set grade to Ex
									 * else set grade as calculated
									 */
									form.setTermEndMarks("-");
									form.setUnitTestMarks("-");
									form.setFinalTotalMarks("-");

									if (termEdCheck) {
										grade = "ex";
									}

									if (unitTestCheck || seAbsentCheck || nbAbsentCheck || portAbsentCheck
											|| multiAbsentCheck) {
										grade = "ex";
									}

								} else {
									/*
									 * Verifying whether absentFlag is true for any of exam, if so set grade to Ex
									 * else set grade as calculated
									 */
									if (termEdCheck) {
										grade = "ex";
										form.setTermEndMarks("ex");
										form.setFinalTotalMarks("ex");
									} else {
										form.setTermEndMarks("" + value);
										form.setFinalTotalMarks("" + totalMarksScaled);
									}

									if (unitTestCheck || seAbsentCheck || nbAbsentCheck || portAbsentCheck
											|| multiAbsentCheck) {
										form.setUnitTestMarks("ex");
										grade = "ex";
										form.setFinalTotalMarks("ex");
									} else {
										form.setUnitTestMarks("" + internalMarksValue);
										form.setFinalTotalMarks("" + totalMarksScaled);
									}

									if (termEdCheck || unitTestCheck || seAbsentCheck || nbAbsentCheck
											|| portAbsentCheck || multiAbsentCheck) {
										form.setFinalTotalMarks("ex");
									} else {
										form.setFinalTotalMarks("" + totalMarksScaled);
									}
								}

								form.setSubject(subjectName);
								form.setGrade(grade);

								ScholasticGradeList.add(form);
							}
						}
					}
				}

				for (ConfigurationForm form : studentDetailsList) {
					studentName = form.getStudentName();
					rollNo = form.getRollNumber();
					standard = form.getStandard();
					division = form.getDivision();
					GRNo = form.getGrNumber();
					dateOfBirth = form.getDateOfBirth();
				}

				/*
				 * Setting header
				 */
				document.addCreator("KovidRMS");
				document.addTitle("Student Report");

				PdfPTable table = new PdfPTable(3);

				table.setFooterRows(1);
				table.setWidthPercentage(100);
				Rectangle rect = new Rectangle(270, 700);
				table.setWidthPercentage(new float[] { 25, 220, 35 }, rect);

				PdfPCell cell = new PdfPCell(image1, true);
				// cell.setPaddingTop(120);
				cell.setPaddingBottom(10);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setUseBorderPadding(true);
				// cell.setColspan(4);
				cell.setBorderWidthBottom(1f);
				// cell.setBorderColorBottom(BaseColor.DARK_GRAY);
				cell.setBorderColor(BaseColor.WHITE);

				PdfPCell cell2 = new PdfPCell(new Paragraph(daoInf1.retrieveOrganizationTagLine(organisationID) + "\n"
						+ daoInf1.retrieveOrganizationName(organisationID) + "\n"
						+ daoInf1.retrieveOrganizationAddress(organisationID) + "\n"
						+ daoInf1.retrieveOrganizationPhone(organisationID), Font5));
				cell2.setBorderWidth(0.01f);
				cell2.setPaddingBottom(10);
				cell2.setBorderWidthLeft(0.2f);
				cell2.setBorderColor(BaseColor.WHITE);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);

				PdfPCell cell3 = new PdfPCell(image2, true);
				// cell.setPaddingTop(120);
				cell3.setPaddingBottom(10);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setUseBorderPadding(true);
				// cell.setColspan(4);
				cell3.setBorderWidthBottom(1f);
				// cell.setBorderColorBottom(BaseColor.DARK_GRAY);
				cell3.setBorderColor(BaseColor.WHITE);

				PdfPCell cell4 = new PdfPCell(new Paragraph());
				// cell.setPaddingTop(120);
				// cell4.setPaddingBottom(10);
				// cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
				// cell4.setUseBorderPadding(true);
				cell4.setColspan(3);
				cell4.setBorderWidthBottom(0f);
				cell4.setBorderWidthLeft(0f);
				cell4.setBorderWidthRight(0f);
				cell4.setBorderWidthTop(1f);
				// cell.setBorderColorBottom(BaseColor.DARK_GRAY);
				cell4.setBorderColor(BaseColor.DARK_GRAY);

				/*
				 * adding all cell to the table to create tabular structure
				 */

				table.addCell(cell);
				// table.addCell(cell1);
				table.addCell(cell2);
				table.addCell(cell3);
				table.addCell(cell4);

				document.add(table);

				/*
				 * for student details
				 */
				PdfPTable table2 = new PdfPTable(4);
				table2.setWidthPercentage(100);
				Rectangle rect1 = new Rectangle(270, 700);
				table2.setWidthPercentage(new float[] { 145, 45, 45, 45 }, rect1);

				// for Title
				PdfPCell cell0 = new PdfPCell(new Paragraph(
						"Report Card " + termName + " (" + daoInf1.retrieveAcademicYearName(organisationID) + ")",
						Font5));

				cell0.setPaddingTop(10);
				cell0.setPaddingBottom(5);
				cell0.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell0.setUseBorderPadding(true);
				cell0.setColspan(4);
				cell0.setBorderColor(BaseColor.WHITE);

				// Gene
				PdfPCell cell1 = new PdfPCell(new Paragraph("Student Name: " + studentName, mainContent1));

				cell1.setBorderWidth(0.01f);
				cell1.setPaddingBottom(5);
				cell1.setPaddingTop(5);
				cell1.setBorderColor(BaseColor.WHITE);

				// OD RE
				PdfPCell cell5 = new PdfPCell(new Paragraph("Standard: " + standard, mainContent1));

				cell5.setBorderWidth(0.01f);
				cell5.setPaddingBottom(5);
				cell5.setPaddingTop(5);
				cell5.setBorderColor(BaseColor.WHITE);

				// Os LE
				PdfPCell cell6 = new PdfPCell(new Paragraph("Division: " + division, mainContent1));

				cell6.setBorderWidth(0.01f);
				cell6.setPaddingBottom(5);
				cell6.setPaddingTop(5);
				cell6.setBorderColor(BaseColor.WHITE);

				// Os LE
				PdfPCell cell7 = new PdfPCell(new Paragraph("Roll Number: " + rollNo, mainContent1));

				cell7.setBorderWidth(0.01f);
				cell7.setPaddingBottom(5);
				cell7.setPaddingTop(5);
				cell7.setBorderColor(BaseColor.WHITE);

				// Os LE
				PdfPCell cell8 = new PdfPCell(new Paragraph("DOB: " + dateOfBirth, mainContent1));

				cell8.setBorderWidth(0.01f);
				cell8.setPaddingBottom(5);
				cell8.setPaddingTop(5);
				cell8.setBorderColor(BaseColor.WHITE);

				// Os LE
				PdfPCell cell9 = new PdfPCell(new Paragraph("", mainContent1));

				cell9.setBorderWidth(0.01f);
				cell9.setPaddingBottom(5);
				cell9.setPaddingTop(5);
				cell9.setBorderColor(BaseColor.WHITE);

				// Os LE
				PdfPCell cell11 = new PdfPCell(new Paragraph("GR Number: " + GRNo, mainContent1));

				cell11.setBorderWidth(0.01f);
				cell11.setPaddingBottom(5);
				cell11.setColspan(2);
				cell11.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell11.setPaddingTop(5);
				cell11.setBorderColor(BaseColor.WHITE);

				table2.addCell(cell0);
				table2.addCell(cell1);
				table2.addCell(cell5);
				table2.addCell(cell6);
				table2.addCell(cell7);
				table2.addCell(cell8);
				table2.addCell(cell9);
				table2.addCell(cell11);

				document.add(table2);

				if (Stage.equals("Primary")) {

					/*
					 * For scholastic
					 */
					PdfPTable table3 = new PdfPTable(6);
					table3.setWidthPercentage(100);
					Rectangle rect2 = new Rectangle(270, 700);
					table3.setWidthPercentage(new float[] { 30, 60, 45, 45, 45, 45 }, rect2);

					// For blank space
					PdfPCell cell12 = new PdfPCell(new Paragraph("", Font2));
					cell12.setColspan(7);
					cell12.setBorderWidthRight(0f);
					cell12.setBorderWidthLeft(0f);
					cell12.setBorderWidthTop(0f);
					cell12.setBorderWidthBottom(0f);
					cell12.setBorderColorTop(BaseColor.WHITE);

					// For blank space
					PdfPCell cell012 = new PdfPCell(new Paragraph("Scholastic Areas: ", Font5));
					cell012.setColspan(7);
					cell012.setBorderWidthRight(0f);
					cell012.setBorderWidthLeft(0f);
					cell012.setBorderWidthTop(0f);
					cell012.setBorderWidthBottom(0f);
					cell012.setBorderColorTop(BaseColor.WHITE);

					// Compound
					PdfPCell cell129 = new PdfPCell(new Paragraph("Sr No.", mainContent1));

					cell129.setPaddingBottom(3);
					cell129.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell129.setBorderColor(BaseColor.BLACK);

					// Compound
					PdfPCell cell13 = new PdfPCell(new Paragraph("Subjects", mainContent1));

					// cell13.setBorderWidth(0.01f);
					cell13.setPaddingBottom(3);
					cell13.setBorderColor(BaseColor.BLACK);

					// Quantity
					PdfPCell cell15 = new PdfPCell(new Paragraph("Internal Marks\n(10)", mainContent1));

					// cell15.setBorderWidth(0.01f);
					cell15.setPaddingBottom(3);
					cell15.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell15.setBorderColor(BaseColor.BLACK);

					// Frequency
					PdfPCell cell17 = new PdfPCell(new Paragraph("Term End\n(40)", mainContent1));

					// cell17.setBorderWidth(0.01f);
					cell17.setPaddingBottom(3);
					cell17.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell17.setBorderColor(BaseColor.BLACK);

					// Comment
					PdfPCell cell18 = new PdfPCell(new Paragraph("Marks Obtained\n(50)", mainContent1));

					// cell18.setBorderWidth(0.01f);
					cell18.setPaddingBottom(3);
					cell18.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell18.setBorderColor(BaseColor.BLACK);

					// Instruction
					PdfPCell cell19 = new PdfPCell(new Paragraph("Grade", mainContent1));

					// cell19.setBorderWidth(0.01f);
					cell19.setPaddingBottom(3);
					cell19.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell19.setBorderColor(BaseColor.BLACK);

					table3.addCell(cell12);
					table3.addCell(cell012);
					table3.addCell(cell129);
					table3.addCell(cell13);
					table3.addCell(cell15);
					table3.addCell(cell17);
					table3.addCell(cell18);
					table3.addCell(cell19);

					SrNo = 1;

					for (ConfigurationForm form : ScholasticGradeList) {

						// Compound
						PdfPCell cell3021 = new PdfPCell(new Paragraph("" + SrNo, mainContent));

						cell3021.setPaddingBottom(3);
						cell3021.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell3021.setBorderColor(BaseColor.BLACK);

						// Compound
						PdfPCell cell20 = new PdfPCell(new Paragraph(form.getSubject(), mainContent));

						// cell20.setBorderWidth(0.01f);
						cell20.setPaddingBottom(3);
						cell20.setBorderColor(BaseColor.BLACK);

						// Quantity
						PdfPCell cell22 = new PdfPCell(new Paragraph("" + form.getUnitTestMarks(), mainContent));

						// cell22.setBorderWidth(0.01f);
						cell22.setPaddingBottom(3);
						cell22.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell22.setBorderColor(BaseColor.BLACK);

						// Frequency
						PdfPCell cell24 = new PdfPCell(new Paragraph("" + form.getTermEndMarks(), mainContent));

						// cell24.setBorderWidth(0.01f);
						cell24.setPaddingBottom(3);
						cell24.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell24.setBorderColor(BaseColor.BLACK);

						// Comment
						PdfPCell cell25 = new PdfPCell(new Paragraph("" + form.getFinalTotalMarks(), mainContent));

						// cell25.setBorderWidth(0.01f);
						cell25.setPaddingBottom(3);
						cell25.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell25.setBorderColor(BaseColor.BLACK);

						// Instruction
						PdfPCell cell26 = new PdfPCell(new Paragraph(form.getGrade(), mainContent));

						// cell26.setBorderWidth(0.01f);
						cell26.setPaddingBottom(3);
						cell26.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell26.setBorderColor(BaseColor.BLACK);

						table3.addCell(cell3021);
						table3.addCell(cell20);
						table3.addCell(cell22);
						table3.addCell(cell24);
						table3.addCell(cell25);
						table3.addCell(cell26);

						SrNo++;
					}

					document.add(table3);

				} else {

					/*
					 * For scholastic
					 */
					PdfPTable table3 = new PdfPTable(6);
					table3.setWidthPercentage(100);
					Rectangle rect2 = new Rectangle(270, 700);
					table3.setWidthPercentage(new float[] { 30, 60, 45, 45, 45, 45 }, rect2);

					// For blank space
					PdfPCell cell12 = new PdfPCell(new Paragraph("", Font2));
					cell12.setColspan(6);
					cell12.setBorderWidthRight(0f);
					cell12.setBorderWidthLeft(0f);
					cell12.setBorderWidthTop(0f);
					cell12.setBorderWidthBottom(0f);
					cell12.setBorderColorTop(BaseColor.WHITE);

					// For blank space
					PdfPCell cell012 = new PdfPCell(new Paragraph("Scholastic Areas: ", Font5));
					cell012.setColspan(6);
					cell012.setBorderWidthRight(0f);
					cell012.setBorderWidthLeft(0f);
					cell012.setBorderWidthTop(0f);
					cell012.setBorderWidthBottom(0f);
					cell012.setBorderColorTop(BaseColor.WHITE);

					// Compound
					PdfPCell cell129 = new PdfPCell(new Paragraph("Sr No.", mainContent1));

					cell129.setPaddingBottom(3);
					cell129.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell129.setBorderColor(BaseColor.BLACK);

					// Compound
					PdfPCell cell13 = new PdfPCell(new Paragraph("Subjects", mainContent1));

					// cell13.setBorderWidth(0.01f);
					cell13.setPaddingBottom(3);
					cell13.setBorderColor(BaseColor.BLACK);

					// Quantity
					PdfPCell cell16 = new PdfPCell(new Paragraph("Internal Marks\n(20)", mainContent1));

					// cell16.setBorderWidth(0.01f);
					cell16.setPaddingBottom(3);
					cell16.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell16.setBorderColor(BaseColor.BLACK);

					// Frequency
					PdfPCell cell17 = new PdfPCell(new Paragraph("Term End\n(30/80)", mainContent1));

					// cell17.setBorderWidth(0.01f);
					cell17.setPaddingBottom(3);
					cell17.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell17.setBorderColor(BaseColor.BLACK);

					// Comment
					PdfPCell cell18 = new PdfPCell(new Paragraph("Marks Obtained\n(50/100)", mainContent1));

					// cell18.setBorderWidth(0.01f);
					cell18.setPaddingBottom(3);
					cell18.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell18.setBorderColor(BaseColor.BLACK);

					// Instruction
					PdfPCell cell19 = new PdfPCell(new Paragraph("Grade", mainContent1));

					// cell19.setBorderWidth(0.01f);
					cell19.setPaddingBottom(3);
					cell19.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell19.setBorderColor(BaseColor.BLACK);

					table3.addCell(cell12);
					table3.addCell(cell012);
					table3.addCell(cell129);
					table3.addCell(cell13);
					table3.addCell(cell16);
					table3.addCell(cell17);
					table3.addCell(cell18);
					table3.addCell(cell19);

					SrNo = 1;

					for (ConfigurationForm form : ScholasticGradeList) {

						// Compound
						PdfPCell cell3021 = new PdfPCell(new Paragraph("" + SrNo, mainContent));

						cell3021.setPaddingBottom(3);
						cell3021.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell3021.setBorderColor(BaseColor.BLACK);

						// Compound
						PdfPCell cell20 = new PdfPCell(new Paragraph(form.getSubject(), mainContent));

						// cell20.setBorderWidth(0.01f);
						cell20.setPaddingBottom(3);
						cell20.setBorderColor(BaseColor.BLACK);

						// Quantity
						PdfPCell cell23 = new PdfPCell(new Paragraph("" + form.getUnitTestMarks(), mainContent));

						// cell23.setBorderWidth(0.01f);
						cell23.setPaddingBottom(3);
						cell23.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell23.setBorderColor(BaseColor.BLACK);

						// Frequency
						PdfPCell cell24 = new PdfPCell(new Paragraph("" + form.getTermEndMarks(), mainContent));

						// cell24.setBorderWidth(0.01f);
						cell24.setPaddingBottom(3);
						cell24.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell24.setBorderColor(BaseColor.BLACK);

						// Comment
						PdfPCell cell25 = new PdfPCell(new Paragraph("" + form.getFinalTotalMarks(), mainContent));

						// cell25.setBorderWidth(0.01f);
						cell25.setPaddingBottom(3);
						cell25.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell25.setBorderColor(BaseColor.BLACK);

						// Instruction
						PdfPCell cell26 = new PdfPCell(new Paragraph(form.getGrade(), mainContent));

						// cell26.setBorderWidth(0.01f);
						cell26.setPaddingBottom(3);
						cell26.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell26.setBorderColor(BaseColor.BLACK);

						table3.addCell(cell3021);
						table3.addCell(cell20);
						table3.addCell(cell23);
						table3.addCell(cell24);
						table3.addCell(cell25);
						table3.addCell(cell26);

						SrNo++;
					}

					document.add(table3);
				}

				/*
				 * For Co scholastic and Extra curriculum activities
				 */
				PdfPTable table3 = new PdfPTable(2);
				table3.setWidthPercentage(100);
				Rectangle rect2 = new Rectangle(270, 700);
				table3.setWidthPercentage(new float[] { 135, 135 }, rect2);

				PdfPCell cell15 = new PdfPCell(new Paragraph(
						"                                                                                               "
								+ "                                                                                                                            "
								+ "                                                                                                                              ",
						Font2));
				cell15.setColspan(2);
				cell15.setBorderWidthRight(0f);
				cell15.setBorderWidthLeft(0f);
				cell15.setBorderWidthTop(0f);
				cell15.setBorderWidthBottom(0f);
				// cell15.setPaddingTop(5);
				cell15.setBorderColorTop(BaseColor.WHITE);

				table3.addCell(cell15);
				document.add(table3);

				Paragraph paragraph = new Paragraph();
				PdfPCell celll = null;
				// celll = new PdfPCell(new Paragraph("\n\n"));

				if (ExtraCurricularGradeList.size() == 0) {

					// Main table
					PdfPTable mainTable = new PdfPTable(3);
					Rectangle mainTableRect = new Rectangle(270, 700);
					mainTable.setWidthPercentage(new float[] { 65, 140, 65 }, mainTableRect);

					PdfPCell coscholasticBlankTableCell1 = new PdfPCell();
					coscholasticBlankTableCell1.setBorder(PdfPCell.NO_BORDER);
					// coscholasticBlankTableCell1.setPaddingRight(20);

					PdfPCell coscholasticBlankTableCell2 = new PdfPCell();
					coscholasticBlankTableCell2.setBorder(PdfPCell.NO_BORDER);

					// First table
					PdfPCell coscholasticTableCell = new PdfPCell();
					coscholasticTableCell.setBorder(PdfPCell.NO_BORDER);
					// coscholasticTableCell.setPaddingRight(20);

					PdfPTable coscholasticTable = new PdfPTable(3);
					Rectangle coscholasticRect = new Rectangle(270, 700);
					coscholasticTable.setWidthPercentage(new float[] { 50, 170, 50 }, coscholasticRect);
					// coscholasticTable.setWidthPercentage(100);
					celll = new PdfPCell(new Phrase("Co-Scholastic Areas: ", Font5));
					celll.setPaddingBottom(5);
					celll.setColspan(3);
					coscholasticTable.addCell(celll);
					celll = new PdfPCell(new Phrase("Sr No.", mainContent1));
					celll.setHorizontalAlignment(Element.ALIGN_CENTER);
					coscholasticTable.addCell(celll);
					celll = new PdfPCell(new Phrase("Subjects", mainContent1));
					coscholasticTable.addCell(celll);
					celll = new PdfPCell(new Phrase("Grade", mainContent1));
					celll.setHorizontalAlignment(Element.ALIGN_CENTER);
					coscholasticTable.addCell(celll);

					SrNo = 1;

					for (ConfigurationForm form : coScholasticGradeList) {

						celll = new PdfPCell(new Phrase("" + SrNo, mainContent));
						celll.setHorizontalAlignment(Element.ALIGN_CENTER);
						coscholasticTable.addCell(celll);
						celll = new PdfPCell(new Phrase(form.getSubject(), mainContent));
						coscholasticTable.addCell(celll);
						celll = new PdfPCell(new Phrase(form.getGrade(), mainContent));
						celll.setHorizontalAlignment(Element.ALIGN_CENTER);
						coscholasticTable.addCell(celll);

						SrNo++;
					}

					coscholasticTableCell.addElement(coscholasticTable);
					mainTable.addCell(coscholasticBlankTableCell1);
					mainTable.addCell(coscholasticTableCell);
					mainTable.addCell(coscholasticBlankTableCell2);

					paragraph.add(mainTable);

				} else {

					// Main table
					PdfPTable mainTable = new PdfPTable(2);
					mainTable.setWidthPercentage(101);

					// First table
					PdfPCell coscholasticTableCell = new PdfPCell();
					coscholasticTableCell.setBorder(PdfPCell.NO_BORDER);
					coscholasticTableCell.setPaddingRight(20);

					PdfPTable coscholasticTable = new PdfPTable(3);
					Rectangle coscholasticRect = new Rectangle(270, 700);
					coscholasticTable.setWidthPercentage(new float[] { 50, 170, 50 }, coscholasticRect);
					// coscholasticTable.setWidthPercentage(100);
					celll = new PdfPCell(new Phrase("Co-Scholastic Areas: ", Font5));
					celll.setPaddingBottom(5);
					celll.setColspan(3);
					coscholasticTable.addCell(celll);
					celll = new PdfPCell(new Phrase("Sr No.", mainContent1));
					celll.setHorizontalAlignment(Element.ALIGN_CENTER);
					coscholasticTable.addCell(celll);
					celll = new PdfPCell(new Phrase("Subjects", mainContent1));
					coscholasticTable.addCell(celll);
					celll = new PdfPCell(new Phrase("Grade", mainContent1));
					celll.setHorizontalAlignment(Element.ALIGN_CENTER);
					coscholasticTable.addCell(celll);

					SrNo = 1;

					for (ConfigurationForm form : coScholasticGradeList) {

						celll = new PdfPCell(new Phrase("" + SrNo, mainContent));
						celll.setHorizontalAlignment(Element.ALIGN_CENTER);
						coscholasticTable.addCell(celll);
						celll = new PdfPCell(new Phrase(form.getSubject(), mainContent));
						coscholasticTable.addCell(celll);
						celll = new PdfPCell(new Phrase(form.getGrade(), mainContent));
						celll.setHorizontalAlignment(Element.ALIGN_CENTER);
						coscholasticTable.addCell(celll);

						SrNo++;
					}

					coscholasticTableCell.addElement(coscholasticTable);
					mainTable.addCell(coscholasticTableCell);

					// Second table
					PdfPCell extrcrclmTableCell = new PdfPCell();
					extrcrclmTableCell.setBorder(PdfPCell.NO_BORDER);
					extrcrclmTableCell.setPaddingLeft(20);

					PdfPTable extrcrclmTable = new PdfPTable(3);
					Rectangle extrcrclmRect = new Rectangle(270, 700);
					extrcrclmTable.setWidthPercentage(new float[] { 50, 170, 50 }, extrcrclmRect);
					// extrcrclmTable.setWidthPercentage(100);
					celll = new PdfPCell(new Phrase("Extra Curricular Activities: ", Font5));
					celll.setColspan(3);
					celll.setPaddingBottom(5);
					extrcrclmTable.addCell(celll);
					celll = new PdfPCell(new Phrase("Sr No.", mainContent1));
					celll.setHorizontalAlignment(Element.ALIGN_CENTER);
					extrcrclmTable.addCell(celll);
					celll = new PdfPCell(new Phrase("Subjects", mainContent1));
					extrcrclmTable.addCell(celll);
					celll = new PdfPCell(new Phrase("Grade", mainContent1));
					celll.setHorizontalAlignment(Element.ALIGN_CENTER);
					extrcrclmTable.addCell(celll);

					SrNo = 1;

					for (ConfigurationForm form : ExtraCurricularGradeList) {
						celll = new PdfPCell(new Phrase("" + SrNo, mainContent));
						celll.setHorizontalAlignment(Element.ALIGN_CENTER);
						extrcrclmTable.addCell(celll);
						celll = new PdfPCell(new Phrase(form.getSubject(), mainContent));
						extrcrclmTable.addCell(celll);
						celll = new PdfPCell(new Phrase(form.getGrade(), mainContent));
						celll.setHorizontalAlignment(Element.ALIGN_CENTER);
						extrcrclmTable.addCell(celll);

						SrNo++;
					}
					extrcrclmTableCell.addElement(extrcrclmTable);
					mainTable.addCell(extrcrclmTableCell);

					paragraph.add(mainTable);
				}

				/*
				 * // First table PdfPCell coscholasticTableCell = new PdfPCell();
				 * coscholasticTableCell.setBorder(PdfPCell.NO_BORDER);
				 * coscholasticTableCell.setPaddingRight(20);
				 * 
				 * PdfPTable coscholasticTable = new PdfPTable(3); Rectangle coscholasticRect =
				 * new Rectangle(270, 700); coscholasticTable.setWidthPercentage(new float[] {
				 * 50, 170, 50 }, coscholasticRect); //
				 * coscholasticTable.setWidthPercentage(100); celll = new PdfPCell(new
				 * Phrase("Co-Scholastic Areas: ", Font5)); celll.setPaddingBottom(5);
				 * celll.setColspan(3); coscholasticTable.addCell(celll); celll = new
				 * PdfPCell(new Phrase("Sr No.", mainContent1));
				 * celll.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * coscholasticTable.addCell(celll); celll = new PdfPCell(new Phrase("Subjects",
				 * mainContent1)); coscholasticTable.addCell(celll); celll = new PdfPCell(new
				 * Phrase("Grade", mainContent1));
				 * celll.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * coscholasticTable.addCell(celll);
				 * 
				 * SrNo = 1;
				 * 
				 * for (ConfigurationForm form : coScholasticGradeList) {
				 * 
				 * celll = new PdfPCell(new Phrase("" + SrNo, mainContent));
				 * celll.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * coscholasticTable.addCell(celll); celll = new PdfPCell(new
				 * Phrase(form.getSubject(), mainContent)); coscholasticTable.addCell(celll);
				 * celll = new PdfPCell(new Phrase(form.getGrade(), mainContent));
				 * celll.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * coscholasticTable.addCell(celll);
				 * 
				 * SrNo++; }
				 * 
				 * coscholasticTableCell.addElement(coscholasticTable);
				 * mainTable.addCell(coscholasticTableCell);
				 * 
				 * // Second table PdfPCell extrcrclmTableCell = new PdfPCell();
				 * extrcrclmTableCell.setBorder(PdfPCell.NO_BORDER);
				 * extrcrclmTableCell.setPaddingLeft(20);
				 * 
				 * PdfPTable extrcrclmTable = new PdfPTable(3); Rectangle extrcrclmRect = new
				 * Rectangle(270, 700); extrcrclmTable.setWidthPercentage(new float[] { 50, 170,
				 * 50 }, extrcrclmRect); // extrcrclmTable.setWidthPercentage(100); celll = new
				 * PdfPCell(new Phrase("Extra Curricular Activities: ", Font5));
				 * celll.setColspan(3); celll.setPaddingBottom(5);
				 * extrcrclmTable.addCell(celll); celll = new PdfPCell(new Phrase("Sr No.",
				 * mainContent1)); celll.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * extrcrclmTable.addCell(celll); celll = new PdfPCell(new Phrase("Subjects",
				 * mainContent1)); extrcrclmTable.addCell(celll); celll = new PdfPCell(new
				 * Phrase("Grade", mainContent1));
				 * celll.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * extrcrclmTable.addCell(celll);
				 * 
				 * SrNo = 1;
				 * 
				 * for (ConfigurationForm form : ExtraCurricularGradeList) { celll = new
				 * PdfPCell(new Phrase("" + SrNo, mainContent));
				 * celll.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * extrcrclmTable.addCell(celll); celll = new PdfPCell(new
				 * Phrase(form.getSubject(), mainContent)); extrcrclmTable.addCell(celll); celll
				 * = new PdfPCell(new Phrase(form.getGrade(), mainContent));
				 * celll.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * extrcrclmTable.addCell(celll);
				 * 
				 * SrNo++; } extrcrclmTableCell.addElement(extrcrclmTable);
				 * mainTable.addCell(extrcrclmTableCell);
				 * 
				 * paragraph.add(mainTable);
				 */
				document.add(paragraph);

				/*
				 * For Personality Development & Grading Scale
				 */
				PdfPTable table4 = new PdfPTable(2);
				table4.setWidthPercentage(100);
				Rectangle rect3 = new Rectangle(270, 700);
				table4.setWidthPercentage(new float[] { 135, 135 }, rect3);

				PdfPCell cell18 = new PdfPCell(new Paragraph(
						"                                                                                               "
								+ "                                                                                                                            "
								+ "                                                                                                                              ",
						Font2));
				cell18.setColspan(2);
				cell18.setBorderWidthRight(0f);
				cell18.setBorderWidthLeft(0f);
				cell18.setBorderWidthTop(0f);
				cell18.setBorderWidthBottom(0f);
				// cell18.setPaddingTop(10);
				cell18.setBorderColorTop(BaseColor.WHITE);

				table4.addCell(cell18);

				document.add(table4);

				Paragraph paragraph1 = new Paragraph();
				PdfPCell celll1 = null;
				// celll = new PdfPCell(new Paragraph("\n\n"));
				// Main table
				PdfPTable mainTable1 = new PdfPTable(2);
				mainTable1.setWidthPercentage(101);
				// mainTable.addCell(celll);
				// First table
				PdfPCell persnltdvlpmntTableCell = new PdfPCell();
				persnltdvlpmntTableCell.setBorder(PdfPCell.NO_BORDER);
				persnltdvlpmntTableCell.setPaddingRight(20);

				PdfPTable persnltdvlpmntTable = new PdfPTable(3);
				Rectangle persnltdvlpmntRect = new Rectangle(270, 700);
				persnltdvlpmntTable.setWidthPercentage(new float[] { 50, 170, 50 }, persnltdvlpmntRect);
				// persnltdvlpmntTable.setWidthPercentage(100);
				celll1 = new PdfPCell(new Phrase("Personality Development: ", Font5));
				celll1.setColspan(3);
				celll1.setPaddingBottom(5);
				persnltdvlpmntTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("Sr No.", mainContent1));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				persnltdvlpmntTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("Subjects", mainContent1));
				persnltdvlpmntTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("Grade", mainContent1));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				persnltdvlpmntTable.addCell(celll1);

				SrNo = 1;

				for (ConfigurationForm form : PersonalityDevelopmentGradeList) {
					celll1 = new PdfPCell(new Phrase("" + SrNo, mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					persnltdvlpmntTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase(form.getSubject(), mainContent));
					persnltdvlpmntTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase(form.getGrade(), mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					persnltdvlpmntTable.addCell(celll1);

					SrNo++;
				}

				for (ConfigurationForm form1 : PersonalityDevelopmentGradeListNew) {
					celll1 = new PdfPCell(new Phrase("" + SrNo, mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					persnltdvlpmntTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase(form1.getSubject(), mainContent));
					persnltdvlpmntTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase(form1.getGrade(), mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					persnltdvlpmntTable.addCell(celll1);

					SrNo++;
				}

				persnltdvlpmntTableCell.addElement(persnltdvlpmntTable);
				mainTable1.addCell(persnltdvlpmntTableCell);

				// Second table
				PdfPCell gradnscleTableCell = new PdfPCell();
				gradnscleTableCell.setBorder(PdfPCell.NO_BORDER);
				gradnscleTableCell.setPaddingLeft(20);

				PdfPTable attendanceTable = new PdfPTable(2);
				attendanceTable.setWidthPercentage(100);

				celll1 = new PdfPCell(new Phrase("Attendance: ", Font5));
				celll1.setColspan(2);
				celll1.setPaddingBottom(5);
				attendanceTable.addCell(celll1);

				for (ConfigurationForm form : AttendanceList) {

					celll1 = new PdfPCell(new Phrase(form.getWorkingMonth(), mainContent));
					attendanceTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase(form.getStudentWorkingDays(), mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					attendanceTable.addCell(celll1);

				}

				System.out.println("...............AttendanceList.size() :" + AttendanceList.size());

				if (AttendanceList.size() > 0) {
					gradnscleTableCell.addElement(attendanceTable);
				}

				PdfPTable gradnscleTable = new PdfPTable(4);
				gradnscleTable.setWidthPercentage(100);

				celll1 = new PdfPCell(new Phrase("\n"));
				celll1.setBorder(PdfPCell.NO_BORDER);
				celll1.setColspan(4);
				gradnscleTable.addCell(celll1);

				celll1 = new PdfPCell(new Phrase("Grading Scale: ", Font5));
				celll1.setPaddingBottom(5);
				celll1.setColspan(4);
				gradnscleTable.addCell(celll1);

				if (Stage.equals("Primary")) {

					celll1 = new PdfPCell(new Phrase("Marks", mainContent1));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("Grade", mainContent1));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("Marks", mainContent1));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("Grade", mainContent1));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("46-50", mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("A1", mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("26-30", mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("C1", mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("41-45", mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("A2", mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("21-25", mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("C2", mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("36-40", mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("B1", mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("17-20", mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("D", mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("31-35", mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("B2", mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("16 & Below", mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("E", mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);

				} else {

					celll1 = new PdfPCell(new Phrase("Marks", mainContent1));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("Grade", mainContent1));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("Marks", mainContent1));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("Grade", mainContent1));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("91-100", mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("A1", mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("51-60", mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("C1", mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("81-90", mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("A2", mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("41-50", mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("C2", mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("71-80", mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("B1", mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("33-40", mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("D", mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("61-70", mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("B2", mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("32 & Below", mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
					celll1 = new PdfPCell(new Phrase("E", mainContent));
					celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
					gradnscleTable.addCell(celll1);
				}

				gradnscleTableCell.addElement(gradnscleTable);
				// mainTable1.addCell(gradnscleTableCell);

				// grade scale2 table
				PdfPCell persnlygradeTableCell = new PdfPCell();
				persnlygradeTableCell.setBorder(PdfPCell.NO_BORDER);
				persnlygradeTableCell.setPaddingLeft(20);

				PdfPTable persnlygradeTable = new PdfPTable(1);
				persnlygradeTable.setWidthPercentage(100);

				celll1 = new PdfPCell(new Phrase("\n"));
				celll1.setBorder(PdfPCell.NO_BORDER);
				persnlygradeTable.addCell(celll1);

				celll1 = new PdfPCell(new Phrase("Grade Code for Personality Development", Font5));
				celll1.setPaddingBottom(5);
				// celll2.setColspan(2);
				persnlygradeTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("G = Good; S = Satisfactory; NI = Needs Improvement", mainContent));
				persnlygradeTable.addCell(celll1);

				gradnscleTableCell.addElement(persnlygradeTable);
				mainTable1.addCell(gradnscleTableCell);

				paragraph1.add(mainTable1);
				document.add(paragraph1);

				/*
				 * For signature in footer
				 */
				PdfPTable table11 = new PdfPTable(1);
				table11.setTotalWidth(510);
				table11.setWidthPercentage(100);
				Rectangle rect6 = new Rectangle(270, 700);
				table11.setWidthPercentage(new float[] { 270 }, rect6);

				float paddingTop = 2;

				if (Stage.equals("Primary")) {
					paddingTop = 10;
				}

				/* for Promoted Standard division */
				if (StdDivName != null && StdDivName != "") {

					if (statusValue == null || statusValue == "") {
						PdfPCell cell110 = new PdfPCell(new Paragraph("Promoted to - " + StdDivName, Font5));

						cell110.setPaddingTop(paddingTop);
						cell110.setPaddingBottom(20);
						cell110.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell110.setUseBorderPadding(true);
						cell110.setColspan(4);
						cell110.setBorderColor(BaseColor.WHITE);
						table11.addCell(cell110);
					} else if (statusValue.isEmpty()) {
						PdfPCell cell110 = new PdfPCell(new Paragraph("Promoted to - " + StdDivName, Font5));

						cell110.setPaddingTop(paddingTop);
						cell110.setPaddingBottom(20);
						cell110.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell110.setUseBorderPadding(true);
						cell110.setColspan(4);
						cell110.setBorderColor(BaseColor.WHITE);
						table11.addCell(cell110);
					} else if (statusValue.equals("null")) {
						PdfPCell cell110 = new PdfPCell(new Paragraph("Promoted to - " + StdDivName, Font5));

						cell110.setPaddingTop(paddingTop);
						cell110.setPaddingBottom(20);
						cell110.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell110.setUseBorderPadding(true);
						cell110.setColspan(4);
						cell110.setBorderColor(BaseColor.WHITE);
						table11.addCell(cell110);
					} else {
						
						if(statusValue.equals("Detained in")) {
							
							PdfPCell cell110 = new PdfPCell(new Paragraph(statusValue + " - " + standard+""+division, Font5));

							cell110.setPaddingTop(paddingTop);
							cell110.setPaddingBottom(20);
							cell110.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell110.setUseBorderPadding(true);
							cell110.setColspan(4);
							cell110.setBorderColor(BaseColor.WHITE);
							table11.addCell(cell110);
							
						}else {
							
							PdfPCell cell110 = new PdfPCell(new Paragraph(statusValue + " - " + StdDivName, Font5));

							cell110.setPaddingTop(paddingTop);
							cell110.setPaddingBottom(20);
							cell110.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell110.setUseBorderPadding(true);
							cell110.setColspan(4);
							cell110.setBorderColor(BaseColor.WHITE);
							table11.addCell(cell110);
							
						}
					}

					document.add(table11);
				}

				/*
				 * PdfPTable table1 = new PdfPTable(3); table1.setTotalWidth(510);
				 * table1.setWidthPercentage(100); Rectangle rect5 = new Rectangle(270, 700);
				 * table1.setWidthPercentage(new float[] { 90, 90, 90 }, rect5);
				 * 
				 * PdfPCell cell111 = new PdfPCell(new Paragraph("Signature of Class Teacher",
				 * Font5)); cell111.setBackgroundColor(BaseColor.WHITE);
				 * cell111.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * cell111.setBorderColor(BaseColor.WHITE);
				 * 
				 * PdfPCell cell1111 = new PdfPCell(new Paragraph("Signature of Principal",
				 * Font5)); cell1111.setBackgroundColor(BaseColor.WHITE);
				 * cell1111.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * cell1111.setBorderColor(BaseColor.WHITE);
				 * 
				 * PdfPCell cell11111 = new PdfPCell(new Paragraph("Signature of Parent",
				 * Font5)); cell11111.setBackgroundColor(BaseColor.WHITE);
				 * cell11111.setHorizontalAlignment(Element.ALIGN_RIGHT);
				 * cell11111.setBorderColor(BaseColor.WHITE);
				 * 
				 * table1.addCell(cell111); table1.addCell(cell1111); table1.addCell(cell11111);
				 * 
				 * FooterTable event = new FooterTable(table1); writer.setPageEvent(event);
				 */

				PdfPTable table1 = new PdfPTable(3);
				table1.setTotalWidth(510);
				table1.setWidthPercentage(100);
				Rectangle rect5 = new Rectangle(270, 700);
				table1.setWidthPercentage(new float[] { 100, 100, 70 }, rect5);

				PdfPTable imageTable2 = new PdfPTable(2);
				imageTable2.setWidthPercentage(100);
				imageTable2.setWidths(new int[] { 2, 2 });

				if (Stage.equals("Primary") && signOnRCCheck.equals("sectionHead")) {
					PdfPCell imageCell12 = new PdfPCell(PrimaryHeadSeal, true);
					imageCell12.setBorderColor(BaseColor.WHITE);
					imageCell12.setPaddingLeft(30);
					// imageCell12.setPaddingTop(30);

					PdfPCell imageCell24 = new PdfPCell(new Paragraph("", mainContent));
					imageCell24.setBorderColor(BaseColor.WHITE);
					
					PdfPCell imageCell22 = new PdfPCell(principalSign, true);
					//PdfPCell imageCell22 = new PdfPCell(PrimaryHeadSignature, true);
					/* imageCell22.setPaddingTop(50); */
					imageCell22.setPaddingLeft(30);
					imageCell22.setBorderColor(BaseColor.WHITE);

					PdfPCell imageCell26 = new PdfPCell(new Paragraph("", mainContent));
					imageCell26.setBorderColor(BaseColor.WHITE);

					imageTable2.addCell(imageCell12);
					imageTable2.addCell(imageCell24);
					imageTable2.addCell(imageCell22);
					imageTable2.addCell(imageCell26);
				} else if (Stage.equals("Secondary") && signOnRCCheck.equals("sectionHead")) {
					PdfPCell imageCell12 = new PdfPCell(SecondaryHeadSeal, true);
					imageCell12.setBorderColor(BaseColor.WHITE);
					imageCell12.setPaddingLeft(30);
					// imageCell12.setPaddingTop(30);

					PdfPCell imageCell24 = new PdfPCell(new Paragraph("", mainContent));
					imageCell24.setBorderColor(BaseColor.WHITE);
					
					PdfPCell imageCell22 = new PdfPCell(principalSign, true);
					//PdfPCell imageCell22 = new PdfPCell(SecondaryHeadSignature, true);
					/* imageCell22.setPaddingTop(50); */
					imageCell22.setPaddingLeft(30);
					imageCell22.setBorderColor(BaseColor.WHITE);

					PdfPCell imageCell26 = new PdfPCell(new Paragraph("", mainContent));
					imageCell26.setBorderColor(BaseColor.WHITE);

					imageTable2.addCell(imageCell12);
					imageTable2.addCell(imageCell24);
					imageTable2.addCell(imageCell22);
					imageTable2.addCell(imageCell26);
				} else {
					PdfPCell imageCell12 = new PdfPCell(sealImg, true);
					imageCell12.setBorderColor(BaseColor.WHITE);
					imageCell12.setPaddingLeft(30);
					// imageCell12.setPaddingTop(30);

					PdfPCell imageCell24 = new PdfPCell(new Paragraph("", mainContent));
					imageCell24.setBorderColor(BaseColor.WHITE);

					PdfPCell imageCell22 = new PdfPCell(principalSign, true);
					/* imageCell22.setPaddingTop(50); */
					imageCell22.setPaddingLeft(30);
					imageCell22.setBorderColor(BaseColor.WHITE);

					PdfPCell imageCell26 = new PdfPCell(new Paragraph("", mainContent));
					imageCell26.setBorderColor(BaseColor.WHITE);

					imageTable2.addCell(imageCell12);
					imageTable2.addCell(imageCell24);
					imageTable2.addCell(imageCell22);
					imageTable2.addCell(imageCell26);
				}

				PdfPCell cell11101 = new PdfPCell(imageTable2);
				cell11101.setBackgroundColor(BaseColor.WHITE);
				cell11101.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell11101.setBorderColor(BaseColor.WHITE);

				PdfPTable imageTable1 = new PdfPTable(2);
				imageTable1.setWidthPercentage(100);
				imageTable1.setWidths(new int[] { 2, 2 });

				PdfPCell imageCell11 = new PdfPCell(signImg, true);
				imageCell11.setHorizontalAlignment(Element.ALIGN_RIGHT);
				imageCell11.setBorderColor(BaseColor.WHITE);

				PdfPCell imageCell21 = new PdfPCell(new Paragraph("", mainContent));
				imageCell21.setBorderColor(BaseColor.WHITE);

				imageTable1.addCell(imageCell11);
				imageTable1.addCell(imageCell21);

				PdfPCell cell1101 = new PdfPCell(imageTable1);
				cell1101.setBackgroundColor(BaseColor.WHITE);
				cell1101.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell1101.setPaddingTop(60);
				cell1101.setPaddingLeft(30);

				cell1101.setBorderColor(BaseColor.WHITE);

				PdfPCell cell111101 = new PdfPCell(new Paragraph("", Font5));
				cell111101.setBackgroundColor(BaseColor.WHITE);
				cell111101.setBorderColor(BaseColor.WHITE);

				if (Stage.equals("Primary") && signOnRCCheck.equals("sectionHead")) {
					PdfPCell cell111 = new PdfPCell(new Paragraph("Signature of Class Teacher", Font5));
					cell111.setBackgroundColor(BaseColor.WHITE);
					cell111.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell111.setBorderColor(BaseColor.WHITE);

					PdfPCell cell1111 = new PdfPCell(
							new Paragraph("Signature of Principal", Font5));
					cell1111.setBackgroundColor(BaseColor.WHITE);
					cell1111.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell1111.setBorderColor(BaseColor.WHITE);

					PdfPCell cell11111 = new PdfPCell(new Paragraph("Signature of Parent", Font5));
					cell11111.setBackgroundColor(BaseColor.WHITE);
					cell11111.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell11111.setBorderColor(BaseColor.WHITE);

					table1.addCell(cell1101);
					table1.addCell(cell11101);
					table1.addCell(cell111101);
					
					table1.addCell(cell111);
					table1.addCell(cell1111);
					table1.addCell(cell11111);
				} else if (Stage.equals("Secondary") && signOnRCCheck.equals("sectionHead")) {
					PdfPCell cell111 = new PdfPCell(new Paragraph("Signature of Class Teacher", Font5));
					cell111.setBackgroundColor(BaseColor.WHITE);
					cell111.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell111.setBorderColor(BaseColor.WHITE);

					PdfPCell cell1111 = new PdfPCell(
							new Paragraph("Signature of Principal", Font5));
					cell1111.setBackgroundColor(BaseColor.WHITE);
					cell1111.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell1111.setBorderColor(BaseColor.WHITE);

					PdfPCell cell11111 = new PdfPCell(new Paragraph("Signature of Parent", Font5));
					cell11111.setBackgroundColor(BaseColor.WHITE);
					cell11111.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell11111.setBorderColor(BaseColor.WHITE);

					table1.addCell(cell1101);
					table1.addCell(cell11101);
					table1.addCell(cell111101);
					
					table1.addCell(cell111);
					table1.addCell(cell1111);
					table1.addCell(cell11111);
				} else {
					PdfPCell cell111 = new PdfPCell(new Paragraph("Signature of Class Teacher", Font5));
					cell111.setBackgroundColor(BaseColor.WHITE);
					cell111.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell111.setBorderColor(BaseColor.WHITE);

					PdfPCell cell1111 = new PdfPCell(new Paragraph("Signature of Principal", Font5));
					cell1111.setBackgroundColor(BaseColor.WHITE);
					cell1111.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell1111.setBorderColor(BaseColor.WHITE);

					PdfPCell cell11111 = new PdfPCell(new Paragraph("Signature of Parent", Font5));
					cell11111.setBackgroundColor(BaseColor.WHITE);
					cell11111.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell11111.setBorderColor(BaseColor.WHITE);
					
					table1.addCell(cell1101);
					table1.addCell(cell11101);
					table1.addCell(cell111101);
					
					table1.addCell(cell111);
					table1.addCell(cell1111);
					table1.addCell(cell11111);
				}

				FooterTable1 event = new FooterTable1(table1);
				writer.setPageEvent(event);

				document.newPage();
			}

			document.close();

			System.out.println("Successfully written and generated Student PDF Report");

			status = "success";

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			status = exception.toString();
		}
		return status;

	}

	public String convertHistoryReportOPDPDF(int studentID, int registrationID, int standardID, String termName,
			int ayClassID, int organisationID, int academicYearID, int userID, String realPath, String pdfFIleName) {
		// int count = 1;

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		List<ConfigurationForm> studentDetailsList = null;

		List<ConfigurationForm> coScholasticGradeList = null;

		List<ConfigurationForm> ExtraCurricularGradeList = null;

		List<ConfigurationForm> PersonalityDevelopmentGradeList = null;

		List<ConfigurationForm> PersonalityDevelopmentGradeListNew = null;

		HashMap<String, Integer> NewExaminationList = null;

		List<ConfigurationForm> ScholasticGradeList = null;

		List<ConfigurationForm> AttendanceList = null;

		String studentName = "";
		String standard = "";
		String division = "";
		String statusVal = "";
		int rollNo = 0;
		String dateOfBirth = "";
		String GRNo = "";
		int SrNo = 1;

		try {

			String StandardName = daoInf2.getStandardNameByStandardID(standardID);

			String Stage = daoInf2.getStandardStageByStandardID(standardID);

			String StdDivName = "";

			if (termName.equals("Term II")) {
				StdDivName = daoInf2.retrieveStdDivName(studentID);
			}

			if (Stage.equals("Primary")) {

				studentDetailsList = daoInf2.retrieveInActiveStudentDetailsList(studentID, termName, ayClassID);

				coScholasticGradeList = daoInf2.retrievecoScholasticGradeList(registrationID, termName, ayClassID);

				ExtraCurricularGradeList = daoInf2.retrieveExtraCurricularGradeList(registrationID, termName,
						ayClassID);

				PersonalityDevelopmentGradeList = daoInf2.retrievePersonalityDevelopmentGradeList(registrationID,
						termName, ayClassID);

				PersonalityDevelopmentGradeListNew = daoInf2.retrievePersonalityDevelopmentAbsentList(registrationID,
						termName, ayClassID);

				String attendanceIDList = daoInf2.retrieveAttendanceIDforStudent(termName, academicYearID, standardID);

				AttendanceList = daoInf2.retrieveAttendanceListforStudent(registrationID, attendanceIDList,
						StandardName);

				String NewSubjectList = daoInf2.retrieveSubjectListForStandardByStandardID(standardID);

				NewExaminationList = daoInf2.retrieveExaminationList(academicYearID, termName, ayClassID);

				int value, value1, value2, value3, value4, finalOutOfMarks = 0;
				double value5, value6, finalSEAMarks = 0D;
				int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5 = 0;
				int toatlScaleTo, toatlScaleTo1, outOfMarks, outOfMarks1, outOfMarks2, finalSEAmarksvalue,
						totalMarksScaled = 0;
				String grade = "";

				if (NewSubjectList.contains(",")) {

					String subArr[] = NewSubjectList.split(",");

					ScholasticGradeList = new ArrayList<ConfigurationForm>();

					for (int j = 0; j < subArr.length; j++) {

						ConfigurationForm form = new ConfigurationForm();

						int gradeCheck = daoInf2.retrieveGradeBasedValue(NewExaminationList.get("Term End"),
								Integer.parseInt(subArr[j]), ayClassID);

						// System.out.println("grade check.." + gradeCheck);

						if (gradeCheck == 1) {

							String subjectName = daoInf2.retrieveSubject(Integer.parseInt(subArr[j]));

							boolean termEdCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]), registrationID,
									ayClassID, NewExaminationList.get("Term End"));

							grade = daoInf2.retrieveGradeValue(Integer.parseInt(subArr[j]), registrationID, ayClassID,
									NewExaminationList.get("Term End"));

							if (termEdCheck) {
								grade = "ex";
							}

							form.setTermEndMarks("-");
							form.setFinalTotalMarks("-");
							form.setUnitTestMarks("-");
							form.setSubjectEnrichmentMarks("-");
							form.setSubject(subjectName);
							form.setGrade(grade);

							ScholasticGradeList.add(form);

						} else {

							boolean seAbsentCheck = false;

							for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
								if (entry.getKey().startsWith("Subject Enrichment")
										|| entry.getKey().startsWith("Notebook")) {

									System.out.println(entry.getValue());
									boolean noteBkCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
											registrationID, ayClassID, entry.getValue());

									if (!noteBkCheck) {
										seAbsentCheck = false;
									}

								}
							}

							boolean termEdCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]), registrationID,
									ayClassID, NewExaminationList.get("Term End"));

							boolean unitTestCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]), registrationID,
									ayClassID, NewExaminationList.get("Unit Test"));

							value = daoInf2.retrieveScholasticGradeList(Integer.parseInt(subArr[j]),
									NewExaminationList.get("Term End"), registrationID, ayClassID);

							value1 = daoInf2.retrieveScholasticGradeList(Integer.parseInt(subArr[j]),
									NewExaminationList.get("Unit Test"), registrationID, ayClassID);

							value2 = daoInf2.retrieveScholasticGradeList1(Integer.parseInt(subArr[j]), registrationID,
									ayClassID, termName);

							String subjectName = daoInf2.retrieveSubject(Integer.parseInt(subArr[j]));

							scaleTo = daoInf2.retrieveScaleTo(Integer.parseInt(subArr[j]),
									NewExaminationList.get("Term End"), ayClassID);

							scaleTo1 = daoInf2.retrieveScaleTo(Integer.parseInt(subArr[j]),
									NewExaminationList.get("Unit Test"), ayClassID);

							scaleTo4 = daoInf2.retrieveScaleTo1(Integer.parseInt(subArr[j]), ayClassID);

							totalMarksScaled = (value + value1 + value2);

							toatlScaleTo = (scaleTo + scaleTo1 + scaleTo4);

							/*
							 * if (toatlScaleTo == 50) {
							 * 
							 * toatlScaleTo1 = (totalMarksScaled * 2); } else {
							 */

							// toatlScaleTo1 = totalMarksScaled;
							/* } */

							toatlScaleTo1 = (totalMarksScaled * 50) / toatlScaleTo;

							/* Calculating Final Grades */
							if (toatlScaleTo1 >= 46 && toatlScaleTo1 <= 50) {
								grade = "A1";
							} else if (toatlScaleTo1 >= 41 && toatlScaleTo1 <= 45) {
								grade = "A2";
							} else if (toatlScaleTo1 >= 36 && toatlScaleTo1 <= 40) {
								grade = "B1";
							} else if (toatlScaleTo1 >= 31 && toatlScaleTo1 <= 35) {
								grade = "B2";
							} else if (toatlScaleTo1 >= 26 && toatlScaleTo1 <= 30) {
								grade = "C1";
							} else if (toatlScaleTo1 >= 21 && toatlScaleTo1 <= 25) {
								grade = "C2";
							} else if (toatlScaleTo1 >= 17 && toatlScaleTo1 <= 20) {
								grade = "D";
							} else {
								grade = "E";
							}

							/*
							 * To show only grade forEnhanced Marathi Subject in report card and show '-'
							 * for all exams
							 */
							if (subjectName.equals("Enhanced Marathi")) {
								/*
								 * Verifying whether absentFlag is true for any of exam, if so set grade to Ex
								 * else set grade as calculated
								 */
								form.setTermEndMarks("-");
								form.setUnitTestMarks("-");
								form.setSubjectEnrichmentMarks("-");
								form.setFinalTotalMarks("-");

								if (termEdCheck) {
									grade = "ex";
								}

								if (unitTestCheck) {
									grade = "ex";
								}

								if (seAbsentCheck) {
									grade = "ex";
								}

							} else {
								/*
								 * Verifying whether absentFlag is true for any of exam, if so set grade to Ex
								 * else set grade as calculated
								 */
								if (termEdCheck) {
									grade = "ex";
									form.setTermEndMarks("ex");
									form.setFinalTotalMarks("ex");
								} else {
									form.setTermEndMarks("" + value);
									form.setFinalTotalMarks("" + totalMarksScaled);
								}

								if (unitTestCheck) {
									form.setUnitTestMarks("ex");
									grade = "ex";
									form.setFinalTotalMarks("ex");
								} else {
									form.setUnitTestMarks("" + value1);
									form.setFinalTotalMarks("" + totalMarksScaled);
								}
								if (seAbsentCheck) {
									grade = "ex";
									form.setSubjectEnrichmentMarks("ex");
									form.setFinalTotalMarks("ex");
								} else {
									form.setSubjectEnrichmentMarks("" + value2);
									form.setFinalTotalMarks("" + totalMarksScaled);
								}
								if (termEdCheck || unitTestCheck || seAbsentCheck) {

									form.setFinalTotalMarks("ex");
								} else {
									form.setFinalTotalMarks("" + totalMarksScaled);
								}
							}

							form.setSubject(subjectName);
							form.setGrade(grade);

							ScholasticGradeList.add(form);
						}

					}
				}

			} else {

				studentDetailsList = daoInf2.retrieveInActiveStudentDetailsList(studentID, termName, ayClassID);

				coScholasticGradeList = daoInf2.retrievecoScholasticGradeList(registrationID, termName, ayClassID);

				ExtraCurricularGradeList = daoInf2.retrieveExtraCurricularGradeList(registrationID, termName,
						ayClassID);

				PersonalityDevelopmentGradeList = daoInf2.retrievePersonalityDevelopmentGradeList(registrationID,
						termName, ayClassID);

				PersonalityDevelopmentGradeListNew = daoInf2.retrievePersonalityDevelopmentAbsentList(registrationID,
						termName, ayClassID);

				String attendanceIDList = daoInf2.retrieveAttendanceIDforStudent(termName, academicYearID, standardID);

				AttendanceList = daoInf2.retrieveAttendanceListforStudent(registrationID, attendanceIDList,
						StandardName);

				String NewSubjectList = daoInf2.retrieveSubjectListForStandardByStandardID(standardID);

				NewExaminationList = daoInf2.retrieveExaminationList(academicYearID, termName, ayClassID);

				int value, value1, value2, value3, value4, value5 = 0;
				int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5, toatlScaleTo, toatlScaleTo1,
						totalMarksScaled = 0;
				String grade = "";

				if (NewSubjectList.contains(",")) {

					String subArr[] = NewSubjectList.split(",");

					ScholasticGradeList = new ArrayList<ConfigurationForm>();

					for (int j = 0; j < subArr.length; j++) {

						ConfigurationForm form = new ConfigurationForm();

						int gradeCheck = daoInf2.retrieveGradeBasedValue(NewExaminationList.get("Term End"),
								Integer.parseInt(subArr[j]), ayClassID);

						if (gradeCheck == 1) {

							String subjectName = daoInf2.retrieveSubject(Integer.parseInt(subArr[j]));

							boolean termEdCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]), registrationID,
									ayClassID, NewExaminationList.get("Term End"));

							grade = daoInf2.retrieveGradeValue(Integer.parseInt(subArr[j]), registrationID, ayClassID,
									NewExaminationList.get("Term End"));

							if (termEdCheck) {
								grade = "ex";
							}

							form.setTermEndMarks("-");
							form.setFinalTotalMarks("-");
							form.setSubject(subjectName);
							form.setUnitTestMarks("-");
							form.setNotebookMarks("-");
							form.setSubjectEnrichmentMarks("-");
							form.setPortfolioMarks("-");
							form.setMultipleAssessmentMarks("-");
							form.setGrade(grade);

							ScholasticGradeList.add(form);

						} else {

							boolean seAbsentCheck = false;

							for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
								if (entry.getKey().startsWith("Subject Enrichment")) {

									System.out.println(entry.getValue());
									boolean sbjEnrch1Check = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
											registrationID, ayClassID, entry.getValue());

									if (!sbjEnrch1Check) {
										seAbsentCheck = false;
									}

								}
							}

							boolean nbAbsentCheck = false;
							for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
								if (entry.getKey().startsWith("Notebook")) {

									System.out.println(entry.getValue());
									boolean noteBkCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
											registrationID, ayClassID, entry.getValue());

									if (!noteBkCheck) {
										nbAbsentCheck = false;
									}

								}
							}

							boolean portAbsentCheck = false;

							for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
								if (entry.getKey().startsWith("Portfolio")) {

									System.out.println(entry.getValue());
									boolean portCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
											registrationID, ayClassID, entry.getValue());

									if (!portCheck) {
										portAbsentCheck = false;
									}

								}
							}

							boolean multiAbsentCheck = false;
							for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
								if (entry.getKey().startsWith("Multiple Assessment")) {

									System.out.println(entry.getValue());
									boolean multiCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
											registrationID, ayClassID, entry.getValue());

									if (!multiCheck) {
										multiAbsentCheck = false;
									}

								}
							}

							boolean termEdCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]), registrationID,
									ayClassID, NewExaminationList.get("Term End"));

							boolean unitTestCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]), registrationID,
									ayClassID, NewExaminationList.get("Unit Test"));

							value = daoInf2.retrieveScholasticGradeList(Integer.parseInt(subArr[j]),
									NewExaminationList.get("Term End"), registrationID, ayClassID);

							value1 = daoInf2.retrieveScholasticGradeList(Integer.parseInt(subArr[j]),
									NewExaminationList.get("Unit Test"), registrationID, ayClassID);

							value2 = daoInf2.retrieveScholasticGradeListNew(Integer.parseInt(subArr[j]),
									"Subject Enrichment", registrationID, ayClassID, termName);

							value3 = daoInf2.retrieveScholasticGradeListNew(Integer.parseInt(subArr[j]), "Notebook",
									registrationID, ayClassID, termName);

							String subjectName = daoInf2.retrieveSubject(Integer.parseInt(subArr[j]));

							scaleTo = daoInf2.retrieveScaleTo(Integer.parseInt(subArr[j]),
									NewExaminationList.get("Term End"), ayClassID);

							scaleTo1 = daoInf2.retrieveScaleTo(Integer.parseInt(subArr[j]),
									NewExaminationList.get("Unit Test"), ayClassID);

							scaleTo2 = daoInf2.retrieveScaleToNew(Integer.parseInt(subArr[j]), "Subject Enrichment",
									ayClassID, academicYearID, termName);

							scaleTo3 = daoInf2.retrieveScaleToNew(Integer.parseInt(subArr[j]), "Notebook", ayClassID,
									academicYearID, termName);

							totalMarksScaled = (value + value1 + value2 + value3);

							toatlScaleTo = (scaleTo + scaleTo1 + scaleTo2 + scaleTo3);

							toatlScaleTo1 = (totalMarksScaled * 100) / toatlScaleTo;

							/* Calculating Final Grades */
							if (toatlScaleTo1 >= 91 && toatlScaleTo1 <= 100) {
								grade = "A1";
							} else if (toatlScaleTo1 >= 81 && toatlScaleTo1 <= 90) {
								grade = "A2";
							} else if (toatlScaleTo1 >= 71 && toatlScaleTo1 <= 80) {
								grade = "B1";
							} else if (toatlScaleTo1 >= 61 && toatlScaleTo1 <= 70) {
								grade = "B2";
							} else if (toatlScaleTo1 >= 51 && toatlScaleTo1 <= 60) {
								grade = "C1";
							} else if (toatlScaleTo1 >= 41 && toatlScaleTo1 <= 50) {
								grade = "C2";
							} else if (toatlScaleTo1 >= 33 && toatlScaleTo1 <= 40) {
								grade = "D";
							} else {
								grade = "E";
							}

							/*
							 * To show only grade forEnhanced Marathi Subject in report card and show '-'
							 * for all exams
							 */
							if (subjectName.equals("Enhanced Marathi")) {
								/*
								 * Verifying whether absentFlag is true for any of exam, if so set grade to Ex
								 * else set grade as calculated
								 */
								form.setTermEndMarks("-");
								form.setUnitTestMarks("-");
								form.setSubjectEnrichmentMarks("-");
								form.setNotebookMarks("-");
								form.setPortfolioMarks("-");
								form.setMultipleAssessmentMarks("-");
								form.setFinalTotalMarks("-");

								if (termEdCheck) {
									grade = "ex";
								}

								if (unitTestCheck) {
									grade = "ex";
								}

								if (seAbsentCheck) {
									grade = "ex";
								}

								if (nbAbsentCheck) {
									grade = "ex";
								}

							} else {
								/*
								 * Verifying whether absentFlag is true for any of exam, if so set grade to Ex
								 * else set grade as calculated
								 */
								if (termEdCheck) {
									grade = "ex";
									form.setTermEndMarks("ex");
									form.setFinalTotalMarks("ex");
								} else {
									form.setTermEndMarks("" + value);
									form.setFinalTotalMarks("" + totalMarksScaled);
								}

								if (unitTestCheck) {
									form.setUnitTestMarks("ex");
									grade = "ex";
									form.setFinalTotalMarks("ex");
								} else {
									form.setUnitTestMarks("" + value1);
									form.setFinalTotalMarks("" + totalMarksScaled);
								}
								if (seAbsentCheck) {
									grade = "ex";
									form.setSubjectEnrichmentMarks("ex");
									form.setFinalTotalMarks("ex");
								} else {
									form.setSubjectEnrichmentMarks("" + value2);
									form.setFinalTotalMarks("" + totalMarksScaled);
								}
								if (nbAbsentCheck) {
									grade = "ex";
									form.setNotebookMarks("ex");
									form.setFinalTotalMarks("ex");
								} else {
									form.setNotebookMarks("" + value3);
									form.setFinalTotalMarks("" + totalMarksScaled);
								}
							}

							form.setSubject(subjectName);
							form.setGrade(grade);

							ScholasticGradeList.add(form);
						}
					}
				}
			}

			for (ConfigurationForm form : studentDetailsList) {
				studentName = form.getStudentName();
				rollNo = form.getRollNumber();
				standard = form.getStandard();
				division = form.getDivision();
				GRNo = form.getGrNumber();
				dateOfBirth = form.getDateOfBirth();
				statusVal = form.getResult();
			}

			/*
			 * Image path for posterior segment images
			 */
			String boardLogoImage = daoInf1.retrieveBoardLogo(organisationID);

			String organisationImage = daoInf1.retrieveOrganizationLogo(organisationID);

			String SignatureFile = daoInf2.retrieveUserSignatureFile(userID);

			String PrincipalSignature = daoInf1.retrievePrincipalSignatureFile(organisationID);

			String SchoolSeal = daoInf1.retrieveOrganizationSeal(organisationID);

			/*
			 * Setting path to store PDF file
			 */
			File file = new File(realPath + "/" + pdfFIleName);
			/*
			 * Creating Document for PDF
			 */
			Document document = null;

			document = new Document(PageSize.A4);

			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));

			Font Font1 = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.NORMAL);
			Font Font3 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
			Font Font2 = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);
			Font Font4 = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD);
			Font Font5 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
			Font mainContent = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL);
			Font mainContent1 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
			// mainContent1.setColor(BaseColor.GRAY);

			document.open();

			Image image1 = Image.getInstance(boardLogoImage);

			Image image2 = Image.getInstance(organisationImage);

			Image signImg = Image.getInstance(SignatureFile);

			Image principalSign = Image.getInstance(PrincipalSignature);

			Image sealImg = Image.getInstance(SchoolSeal);

			/*
			 * Setting header
			 */
			document.addCreator("KovidRMS");
			document.addTitle("Student Report");

			PdfPTable table = new PdfPTable(3);

			table.setFooterRows(1);
			table.setWidthPercentage(100);
			Rectangle rect = new Rectangle(270, 700);
			table.setWidthPercentage(new float[] { 25, 220, 35 }, rect);

			PdfPCell cell = new PdfPCell(image1, true);
			// cell.setPaddingTop(120);
			cell.setPaddingBottom(10);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setUseBorderPadding(true);
			// cell.setColspan(4);
			cell.setBorderWidthBottom(1f);
			// cell.setBorderColorBottom(BaseColor.DARK_GRAY);
			cell.setBorderColor(BaseColor.WHITE);

			PdfPCell cell2 = new PdfPCell(new Paragraph(daoInf1.retrieveOrganizationTagLine(organisationID) + "\n"
					+ daoInf1.retrieveOrganizationName(organisationID) + "\n"
					+ daoInf1.retrieveOrganizationAddress(organisationID) + "\n"
					+ daoInf1.retrieveOrganizationPhone(organisationID), Font5));
			cell2.setBorderWidth(0.01f);
			cell2.setPaddingBottom(10);
			cell2.setBorderWidthLeft(0.2f);
			cell2.setBorderColor(BaseColor.WHITE);
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell cell3 = new PdfPCell(image2, true);
			// cell.setPaddingTop(120);
			cell3.setPaddingBottom(10);
			cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell3.setUseBorderPadding(true);
			// cell.setColspan(4);
			cell3.setBorderWidthBottom(1f);
			// cell.setBorderColorBottom(BaseColor.DARK_GRAY);
			cell3.setBorderColor(BaseColor.WHITE);

			PdfPCell cell4 = new PdfPCell(new Paragraph());
			// cell.setPaddingTop(120);
			// cell4.setPaddingBottom(10);
			// cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			// cell4.setUseBorderPadding(true);
			cell4.setColspan(3);
			cell4.setBorderWidthBottom(0f);
			cell4.setBorderWidthLeft(0f);
			cell4.setBorderWidthRight(0f);
			cell4.setBorderWidthTop(1f);
			// cell.setBorderColorBottom(BaseColor.DARK_GRAY);
			cell4.setBorderColor(BaseColor.DARK_GRAY);

			/*
			 * adding all cell to the table to create tabular structure
			 */

			table.addCell(cell);
			// table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			table.addCell(cell4);

			document.add(table);

			/*
			 * for student details
			 */
			PdfPTable table2 = new PdfPTable(4);
			table2.setWidthPercentage(100);
			Rectangle rect1 = new Rectangle(270, 700);
			table2.setWidthPercentage(new float[] { 145, 45, 45, 45 }, rect1);

			// for Title
			PdfPCell cell0 = new PdfPCell(new Paragraph(
					"Report Card " + termName + " (" + daoInf1.retrieveInActiveAcademicYearName(academicYearID) + ")",
					Font5));

			cell0.setPaddingTop(10);
			cell0.setPaddingBottom(5);
			cell0.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell0.setUseBorderPadding(true);
			cell0.setColspan(4);
			cell0.setBorderColor(BaseColor.WHITE);

			// Gene
			PdfPCell cell1 = new PdfPCell(new Paragraph("Student Name: " + studentName, mainContent1));

			cell1.setBorderWidth(0.01f);
			cell1.setPaddingBottom(5);
			cell1.setPaddingTop(5);
			cell1.setBorderColor(BaseColor.WHITE);

			// OD RE
			PdfPCell cell5 = new PdfPCell(new Paragraph("Standard: " + standard, mainContent1));

			cell5.setBorderWidth(0.01f);
			cell5.setPaddingBottom(5);
			cell5.setPaddingTop(5);
			cell5.setBorderColor(BaseColor.WHITE);

			// Os LE
			PdfPCell cell6 = new PdfPCell(new Paragraph("Division: " + division, mainContent1));

			cell6.setBorderWidth(0.01f);
			cell6.setPaddingBottom(5);
			cell6.setPaddingTop(5);
			cell6.setBorderColor(BaseColor.WHITE);

			// Os LE
			PdfPCell cell7 = new PdfPCell(new Paragraph("Roll Number: " + rollNo, mainContent1));

			cell7.setBorderWidth(0.01f);
			cell7.setPaddingBottom(5);
			cell7.setPaddingTop(5);
			cell7.setBorderColor(BaseColor.WHITE);

			// Os LE
			PdfPCell cell8 = new PdfPCell(new Paragraph("DOB: " + dateOfBirth, mainContent1));

			cell8.setBorderWidth(0.01f);
			cell8.setPaddingBottom(5);
			cell8.setPaddingTop(5);
			cell8.setBorderColor(BaseColor.WHITE);

			// Os LE
			PdfPCell cell9 = new PdfPCell(new Paragraph("", mainContent1));

			cell9.setBorderWidth(0.01f);
			cell9.setPaddingBottom(5);
			cell9.setPaddingTop(5);
			cell9.setBorderColor(BaseColor.WHITE);

			// Os LE
			PdfPCell cell11 = new PdfPCell(new Paragraph("GR Number: " + GRNo, mainContent1));

			cell11.setBorderWidth(0.01f);
			cell11.setPaddingBottom(5);
			cell11.setColspan(2);
			cell11.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell11.setPaddingTop(5);
			cell11.setBorderColor(BaseColor.WHITE);

			table2.addCell(cell0);
			table2.addCell(cell1);
			table2.addCell(cell5);
			table2.addCell(cell6);
			table2.addCell(cell7);
			table2.addCell(cell8);
			table2.addCell(cell9);
			table2.addCell(cell11);

			document.add(table2);

			if (StandardName.equals("I") || StandardName.equals("II")
					|| StandardName.equals("III") | StandardName.equals("IV")) {

				/*
				 * For scholastic
				 */
				PdfPTable table3 = new PdfPTable(6);
				table3.setWidthPercentage(100);
				Rectangle rect2 = new Rectangle(270, 700);
				table3.setWidthPercentage(new float[] { 70, 60, 40, 40, 40, 20 }, rect2);

				// For blank space
				PdfPCell cell12 = new PdfPCell(new Paragraph("", Font2));
				cell12.setColspan(6);
				cell12.setBorderWidthRight(0f);
				cell12.setBorderWidthLeft(0f);
				cell12.setBorderWidthTop(0f);
				cell12.setBorderWidthBottom(0f);
				cell12.setBorderColorTop(BaseColor.WHITE);

				// For blank space
				PdfPCell cell012 = new PdfPCell(new Paragraph("Scholastic Areas: ", Font5));
				cell012.setColspan(6);
				cell012.setBorderWidthRight(0f);
				cell012.setBorderWidthLeft(0f);
				cell012.setBorderWidthTop(0f);
				cell012.setBorderWidthBottom(0f);
				cell012.setBorderColorTop(BaseColor.WHITE);

				// Compound
				PdfPCell cell13 = new PdfPCell(new Paragraph("Subjects", mainContent1));

				// cell13.setBorderWidth(0.01f);
				cell13.setPaddingBottom(3);
				cell13.setBorderColor(BaseColor.BLACK);

				// Dose
				PdfPCell cell14 = new PdfPCell(new Paragraph("Subject Enrichment\n+ Notebook(5)", mainContent1));

				// cell14.setBorderWidth(0.01f);
				cell14.setPaddingBottom(3);
				cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell14.setBorderColor(BaseColor.BLACK);

				// Quantity
				PdfPCell cell15 = new PdfPCell(new Paragraph("Unit Test\n(5)", mainContent1));

				// cell15.setBorderWidth(0.01f);
				cell15.setPaddingBottom(3);
				cell15.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell15.setBorderColor(BaseColor.BLACK);

				// Frequency
				PdfPCell cell17 = new PdfPCell(new Paragraph("Term End\n(40)", mainContent1));

				// cell17.setBorderWidth(0.01f);
				cell17.setPaddingBottom(3);
				cell17.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell17.setBorderColor(BaseColor.BLACK);

				// Comment
				PdfPCell cell18 = new PdfPCell(new Paragraph("Marks Obtained\n(50)", mainContent1));

				// cell18.setBorderWidth(0.01f);
				cell18.setPaddingBottom(3);
				cell18.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell18.setBorderColor(BaseColor.BLACK);

				// Instruction
				PdfPCell cell19 = new PdfPCell(new Paragraph("Grade", mainContent1));

				// cell19.setBorderWidth(0.01f);
				cell19.setPaddingBottom(3);
				cell19.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell19.setBorderColor(BaseColor.BLACK);

				table3.addCell(cell12);
				table3.addCell(cell012);
				table3.addCell(cell13);
				table3.addCell(cell14);
				table3.addCell(cell15);
				table3.addCell(cell17);
				table3.addCell(cell18);
				table3.addCell(cell19);

				for (ConfigurationForm form : ScholasticGradeList) {

					// Compound
					PdfPCell cell20 = new PdfPCell(new Paragraph(form.getSubject(), mainContent));

					// cell20.setBorderWidth(0.01f);
					cell20.setPaddingBottom(3);
					// cell20.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell20.setBorderColor(BaseColor.BLACK);

					// No of days
					PdfPCell cell21 = new PdfPCell(new Paragraph("" + form.getSubjectEnrichmentMarks(), mainContent));

					// cell21.setBorderWidth(0.01f);
					cell21.setPaddingBottom(3);
					cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell21.setBorderColor(BaseColor.BLACK);

					// Quantity
					PdfPCell cell22 = new PdfPCell(new Paragraph("" + form.getUnitTestMarks(), mainContent));

					// cell22.setBorderWidth(0.01f);
					cell22.setPaddingBottom(3);
					cell22.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell22.setBorderColor(BaseColor.BLACK);

					// Frequency
					PdfPCell cell24 = new PdfPCell(new Paragraph("" + form.getTermEndMarks(), mainContent));

					// cell24.setBorderWidth(0.01f);
					cell24.setPaddingBottom(3);
					cell24.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell24.setBorderColor(BaseColor.BLACK);

					// Comment
					PdfPCell cell25 = new PdfPCell(new Paragraph("" + form.getFinalTotalMarks(), mainContent));

					// cell25.setBorderWidth(0.01f);
					cell25.setPaddingBottom(3);
					cell25.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell25.setBorderColor(BaseColor.BLACK);

					// Instruction
					PdfPCell cell26 = new PdfPCell(new Paragraph(form.getGrade(), mainContent));

					// cell26.setBorderWidth(0.01f);
					cell26.setPaddingBottom(3);
					cell26.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell26.setBorderColor(BaseColor.BLACK);

					table3.addCell(cell20);
					table3.addCell(cell21);
					table3.addCell(cell22);
					table3.addCell(cell24);
					table3.addCell(cell25);
					table3.addCell(cell26);

				}

				document.add(table3);

			} else {

				/*
				 * For scholastic
				 */
				PdfPTable table3 = new PdfPTable(7);
				table3.setWidthPercentage(100);
				Rectangle rect2 = new Rectangle(270, 700);
				table3.setWidthPercentage(new float[] { 60, 35, 35, 35, 35, 35, 35 }, rect2);

				// For blank space
				PdfPCell cell12 = new PdfPCell(new Paragraph("", Font2));
				cell12.setColspan(7);
				cell12.setBorderWidthRight(0f);
				cell12.setBorderWidthLeft(0f);
				cell12.setBorderWidthTop(0f);
				cell12.setBorderWidthBottom(0f);
				cell12.setBorderColorTop(BaseColor.WHITE);

				// For blank space
				PdfPCell cell012 = new PdfPCell(new Paragraph("Scholastic Areas: ", Font5));
				cell012.setColspan(7);
				cell012.setBorderWidthRight(0f);
				cell012.setBorderWidthLeft(0f);
				cell012.setBorderWidthTop(0f);
				cell012.setBorderWidthBottom(0f);
				cell012.setBorderColorTop(BaseColor.WHITE);

				// Compound
				PdfPCell cell13 = new PdfPCell(new Paragraph("Subjects", mainContent1));

				// cell13.setBorderWidth(0.01f);
				cell13.setPaddingBottom(3);
				cell13.setBorderColor(BaseColor.BLACK);

				// Dose
				PdfPCell cell14 = new PdfPCell(new Paragraph("Notebook\n(5)", mainContent1));

				// cell14.setBorderWidth(0.01f);
				cell14.setPaddingBottom(3);
				cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell14.setBorderColor(BaseColor.BLACK);

				// No of days
				PdfPCell cell15 = new PdfPCell(new Paragraph("Subject Enrichment\n(5)", mainContent1));

				// cell15.setBorderWidth(0.01f);
				cell15.setPaddingBottom(3);
				cell15.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell15.setBorderColor(BaseColor.BLACK);

				// Quantity
				PdfPCell cell16 = new PdfPCell(new Paragraph("Unit Test\n(10)", mainContent1));

				// cell16.setBorderWidth(0.01f);
				cell16.setPaddingBottom(3);
				cell16.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell16.setBorderColor(BaseColor.BLACK);

				// Frequency
				PdfPCell cell17 = new PdfPCell(new Paragraph("Term End\n(30/80)", mainContent1));

				// cell17.setBorderWidth(0.01f);
				cell17.setPaddingBottom(3);
				cell17.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell17.setBorderColor(BaseColor.BLACK);

				// Comment
				PdfPCell cell18 = new PdfPCell(new Paragraph("Marks Obtained\n(50/100)", mainContent1));

				// cell18.setBorderWidth(0.01f);
				cell18.setPaddingBottom(3);
				cell18.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell18.setBorderColor(BaseColor.BLACK);

				// Instruction
				PdfPCell cell19 = new PdfPCell(new Paragraph("Grade", mainContent1));

				// cell19.setBorderWidth(0.01f);
				cell19.setPaddingBottom(3);
				cell19.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell19.setBorderColor(BaseColor.BLACK);

				table3.addCell(cell12);
				table3.addCell(cell012);
				table3.addCell(cell13);
				table3.addCell(cell14);
				table3.addCell(cell15);
				table3.addCell(cell16);
				table3.addCell(cell17);
				table3.addCell(cell18);
				table3.addCell(cell19);

				for (ConfigurationForm form : ScholasticGradeList) {

					// Compound
					PdfPCell cell20 = new PdfPCell(new Paragraph(form.getSubject(), mainContent));

					// cell20.setBorderWidth(0.01f);
					cell20.setPaddingBottom(3);
					cell20.setBorderColor(BaseColor.BLACK);

					// Dose
					PdfPCell cell21 = new PdfPCell(new Paragraph("" + form.getNotebookMarks(), mainContent));

					// cell21.setBorderWidth(0.01f);
					cell21.setPaddingBottom(3);
					cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell21.setBorderColor(BaseColor.BLACK);

					// No of days
					PdfPCell cell22 = new PdfPCell(new Paragraph("" + form.getSubjectEnrichmentMarks(), mainContent));

					// cell22.setBorderWidth(0.01f);
					cell22.setPaddingBottom(3);
					cell22.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell22.setBorderColor(BaseColor.BLACK);

					// Quantity
					PdfPCell cell23 = new PdfPCell(new Paragraph("" + form.getUnitTestMarks(), mainContent));

					// cell23.setBorderWidth(0.01f);
					cell23.setPaddingBottom(3);
					cell23.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell23.setBorderColor(BaseColor.BLACK);

					// Frequency
					PdfPCell cell24 = new PdfPCell(new Paragraph("" + form.getTermEndMarks(), mainContent));

					// cell24.setBorderWidth(0.01f);
					cell24.setPaddingBottom(3);
					cell24.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell24.setBorderColor(BaseColor.BLACK);

					// Comment
					PdfPCell cell25 = new PdfPCell(new Paragraph("" + form.getFinalTotalMarks(), mainContent));

					// cell25.setBorderWidth(0.01f);
					cell25.setPaddingBottom(3);
					cell25.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell25.setBorderColor(BaseColor.BLACK);

					// Instruction
					PdfPCell cell26 = new PdfPCell(new Paragraph(form.getGrade(), mainContent));

					// cell26.setBorderWidth(0.01f);
					cell26.setPaddingBottom(3);
					cell26.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell26.setBorderColor(BaseColor.BLACK);

					table3.addCell(cell20);
					table3.addCell(cell21);
					table3.addCell(cell22);
					table3.addCell(cell23);
					table3.addCell(cell24);
					table3.addCell(cell25);
					table3.addCell(cell26);

				}

				document.add(table3);

			}

			/*
			 * For Co scholastic and Extra curriculum activities
			 */
			PdfPTable table3 = new PdfPTable(2);
			table3.setWidthPercentage(100);
			Rectangle rect2 = new Rectangle(270, 700);
			table3.setWidthPercentage(new float[] { 135, 135 }, rect2);

			PdfPCell cell15 = new PdfPCell(new Paragraph(
					"                                                                                               "
							+ "                                                                                                                            "
							+ "                                                                                                                              ",
					Font2));
			cell15.setColspan(2);
			cell15.setBorderWidthRight(0f);
			cell15.setBorderWidthLeft(0f);
			cell15.setBorderWidthTop(0f);
			cell15.setBorderWidthBottom(0f);
			// cell15.setPaddingTop(5);
			cell15.setBorderColorTop(BaseColor.WHITE);

			table3.addCell(cell15);
			document.add(table3);

			Paragraph paragraph = new Paragraph();
			PdfPCell celll = null;
			// celll = new PdfPCell(new Paragraph("\n\n"));
			// Main table
			PdfPTable mainTable = new PdfPTable(2);
			mainTable.setWidthPercentage(101);
			// mainTable.addCell(celll);
			// First table
			PdfPCell coscholasticTableCell = new PdfPCell();
			coscholasticTableCell.setBorder(PdfPCell.NO_BORDER);
			coscholasticTableCell.setPaddingRight(20);

			PdfPTable coscholasticTable = new PdfPTable(2);
			Rectangle coscholasticRect = new Rectangle(270, 700);
			coscholasticTable.setWidthPercentage(new float[] { 230, 40 }, coscholasticRect);
			// coscholasticTable.setWidthPercentage(100);
			celll = new PdfPCell(new Phrase("Co-Scholastic Areas: ", Font5));
			celll.setPaddingBottom(5);
			celll.setColspan(2);
			coscholasticTable.addCell(celll);
			celll = new PdfPCell(new Phrase("Subjects", mainContent1));
			coscholasticTable.addCell(celll);
			celll = new PdfPCell(new Phrase("Grade", mainContent1));
			celll.setHorizontalAlignment(Element.ALIGN_CENTER);
			coscholasticTable.addCell(celll);

			for (ConfigurationForm form : coScholasticGradeList) {

				celll = new PdfPCell(new Phrase(form.getSubject(), mainContent));
				coscholasticTable.addCell(celll);
				celll = new PdfPCell(new Phrase(form.getGrade(), mainContent));
				celll.setHorizontalAlignment(Element.ALIGN_CENTER);
				coscholasticTable.addCell(celll);

			}

			coscholasticTableCell.addElement(coscholasticTable);
			mainTable.addCell(coscholasticTableCell);

			// Second table
			PdfPCell extrcrclmTableCell = new PdfPCell();
			extrcrclmTableCell.setBorder(PdfPCell.NO_BORDER);
			extrcrclmTableCell.setPaddingLeft(20);

			PdfPTable extrcrclmTable = new PdfPTable(2);
			Rectangle extrcrclmRect = new Rectangle(270, 700);
			extrcrclmTable.setWidthPercentage(new float[] { 230, 40 }, extrcrclmRect);
			// extrcrclmTable.setWidthPercentage(100);
			celll = new PdfPCell(new Phrase("Extra Curricular Activities: ", Font5));
			celll.setColspan(2);
			celll.setPaddingBottom(5);
			extrcrclmTable.addCell(celll);
			celll = new PdfPCell(new Phrase("Subjects", mainContent1));
			extrcrclmTable.addCell(celll);
			celll = new PdfPCell(new Phrase("Grade", mainContent1));
			celll.setHorizontalAlignment(Element.ALIGN_CENTER);
			extrcrclmTable.addCell(celll);

			for (ConfigurationForm form : ExtraCurricularGradeList) {
				celll = new PdfPCell(new Phrase(form.getSubject(), mainContent));
				extrcrclmTable.addCell(celll);
				celll = new PdfPCell(new Phrase(form.getGrade(), mainContent));
				celll.setHorizontalAlignment(Element.ALIGN_CENTER);
				extrcrclmTable.addCell(celll);
			}
			extrcrclmTableCell.addElement(extrcrclmTable);
			mainTable.addCell(extrcrclmTableCell);

			paragraph.add(mainTable);
			document.add(paragraph);

			/*
			 * For Personality Development & Grading Scale
			 */
			PdfPTable table4 = new PdfPTable(2);
			table4.setWidthPercentage(100);
			Rectangle rect3 = new Rectangle(270, 700);
			table4.setWidthPercentage(new float[] { 135, 135 }, rect3);

			PdfPCell cell18 = new PdfPCell(new Paragraph(
					"                                                                                               "
							+ "                                                                                                                            "
							+ "                                                                                                                              ",
					Font2));
			cell18.setColspan(2);
			cell18.setBorderWidthRight(0f);
			cell18.setBorderWidthLeft(0f);
			cell18.setBorderWidthTop(0f);
			cell18.setBorderWidthBottom(0f);
			// cell18.setPaddingTop(10);
			cell18.setBorderColorTop(BaseColor.WHITE);

			table4.addCell(cell18);

			document.add(table4);

			Paragraph paragraph1 = new Paragraph();
			PdfPCell celll1 = null;
			// celll = new PdfPCell(new Paragraph("\n\n"));
			// Main table
			PdfPTable mainTable1 = new PdfPTable(2);
			mainTable1.setWidthPercentage(101);
			// mainTable.addCell(celll);
			// First table
			PdfPCell persnltdvlpmntTableCell = new PdfPCell();
			persnltdvlpmntTableCell.setBorder(PdfPCell.NO_BORDER);
			persnltdvlpmntTableCell.setPaddingRight(20);

			PdfPTable persnltdvlpmntTable = new PdfPTable(3);
			Rectangle persnltdvlpmntRect = new Rectangle(270, 700);
			persnltdvlpmntTable.setWidthPercentage(new float[] { 50, 170, 50 }, persnltdvlpmntRect);
			// persnltdvlpmntTable.setWidthPercentage(100);
			celll1 = new PdfPCell(new Phrase("Personality Development: ", Font5));
			celll1.setColspan(3);
			celll1.setPaddingBottom(5);
			persnltdvlpmntTable.addCell(celll1);
			celll1 = new PdfPCell(new Phrase("Sr No.", mainContent1));
			celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
			persnltdvlpmntTable.addCell(celll1);
			celll1 = new PdfPCell(new Phrase("Subjects", mainContent1));
			persnltdvlpmntTable.addCell(celll1);
			celll1 = new PdfPCell(new Phrase("Grade", mainContent1));
			celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
			persnltdvlpmntTable.addCell(celll1);

			for (ConfigurationForm form : PersonalityDevelopmentGradeList) {
				celll1 = new PdfPCell(new Phrase("" + SrNo, mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				persnltdvlpmntTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase(form.getSubject(), mainContent));
				persnltdvlpmntTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase(form.getGrade(), mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				persnltdvlpmntTable.addCell(celll1);

				SrNo++;
			}

			for (ConfigurationForm form1 : PersonalityDevelopmentGradeListNew) {
				celll1 = new PdfPCell(new Phrase("" + SrNo, mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				persnltdvlpmntTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase(form1.getSubject(), mainContent));
				persnltdvlpmntTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase(form1.getGrade(), mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				persnltdvlpmntTable.addCell(celll1);

				SrNo++;
			}

			persnltdvlpmntTableCell.addElement(persnltdvlpmntTable);
			mainTable1.addCell(persnltdvlpmntTableCell);

			// Second table
			PdfPCell gradnscleTableCell = new PdfPCell();
			gradnscleTableCell.setBorder(PdfPCell.NO_BORDER);
			gradnscleTableCell.setPaddingLeft(20);

			PdfPTable attendanceTable = new PdfPTable(2);
			attendanceTable.setWidthPercentage(100);

			celll1 = new PdfPCell(new Phrase("Attendance: ", Font5));
			celll1.setColspan(2);
			celll1.setPaddingBottom(5);
			attendanceTable.addCell(celll1);
			/*
			 * celll1 = new PdfPCell(new Phrase("Month", mainContent1));
			 * attendanceTable.addCell(celll1); celll1 = new PdfPCell(new
			 * Phrase("Present Days", mainContent1));
			 * celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * attendanceTable.addCell(celll1);
			 */

			for (ConfigurationForm form : AttendanceList) {

				celll1 = new PdfPCell(new Phrase(form.getWorkingMonth(), mainContent));
				attendanceTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase(form.getStudentWorkingDays(), mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				attendanceTable.addCell(celll1);

			}

			System.out.println("...............AttendanceList.size() :" + AttendanceList.size());

			if (AttendanceList.size() > 0) {
				gradnscleTableCell.addElement(attendanceTable);
			}

			PdfPTable gradnscleTable = new PdfPTable(4);
			gradnscleTable.setWidthPercentage(100);

			celll1 = new PdfPCell(new Phrase("\n"));
			celll1.setBorder(PdfPCell.NO_BORDER);
			celll1.setColspan(4);
			gradnscleTable.addCell(celll1);

			celll1 = new PdfPCell(new Phrase("Grading Scale: ", Font5));
			celll1.setPaddingBottom(5);
			celll1.setColspan(4);
			gradnscleTable.addCell(celll1);

			if (Stage.equals("Primary")) {

				celll1 = new PdfPCell(new Phrase("Marks", mainContent1));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("Grade", mainContent1));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("Marks", mainContent1));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("Grade", mainContent1));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("46-50", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("A1", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("26-30", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("C1", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("41-45", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("A2", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("21-25", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("C2", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("36-40", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("B1", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("17-20", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("D", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("31-35", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("B2", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("16 & Below", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("E", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);

			} else {

				celll1 = new PdfPCell(new Phrase("Marks", mainContent1));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("Grade", mainContent1));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("Marks", mainContent1));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("Grade", mainContent1));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("91-100", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("A1", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("51-60", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("C1", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("81-90", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("A2", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("41-50", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("C2", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("71-80", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("B1", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("33-40", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("D", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("61-70", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("B2", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("32 & Below", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("E", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
			}

			gradnscleTableCell.addElement(gradnscleTable);
			// mainTable1.addCell(gradnscleTableCell);

			// grade scale2 table
			PdfPCell persnlygradeTableCell = new PdfPCell();
			persnlygradeTableCell.setBorder(PdfPCell.NO_BORDER);
			persnlygradeTableCell.setPaddingLeft(20);

			PdfPTable persnlygradeTable = new PdfPTable(1);
			persnlygradeTable.setWidthPercentage(100);

			celll1 = new PdfPCell(new Phrase("\n"));
			celll1.setBorder(PdfPCell.NO_BORDER);
			persnlygradeTable.addCell(celll1);

			celll1 = new PdfPCell(new Phrase("Grade Code for Personality Development", Font5));
			celll1.setPaddingBottom(5);
			// celll2.setColspan(2);
			persnlygradeTable.addCell(celll1);
			celll1 = new PdfPCell(new Phrase("G = Good; S = Satisfactory; NI = Needs Improvement", mainContent));
			persnlygradeTable.addCell(celll1);

			gradnscleTableCell.addElement(persnlygradeTable);
			mainTable1.addCell(gradnscleTableCell);

			paragraph1.add(mainTable1);
			document.add(paragraph1);

			/*
			 * For signature in footer
			 */
			PdfPTable table11 = new PdfPTable(1);
			table11.setTotalWidth(510);
			table11.setWidthPercentage(100);
			Rectangle rect6 = new Rectangle(270, 700);
			table11.setWidthPercentage(new float[] { 270 }, rect6);

			float paddingTop = 2;

			if (Stage.equals("Primary")) {
				paddingTop = 10;
			}

			/* for Promoted Standard division */
			if (StdDivName != null && StdDivName != "") {

				if (statusVal == null || statusVal == "") {
					PdfPCell cell110 = new PdfPCell(new Paragraph("Promoted to - " + StdDivName, Font5));

					cell110.setPaddingTop(paddingTop);
					cell110.setPaddingBottom(20);
					cell110.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell110.setUseBorderPadding(true);
					cell110.setColspan(4);
					cell110.setBorderColor(BaseColor.WHITE);
					table11.addCell(cell110);
				} else if (statusVal.isEmpty()) {
					PdfPCell cell110 = new PdfPCell(new Paragraph("Promoted to - " + StdDivName, Font5));

					cell110.setPaddingTop(paddingTop);
					cell110.setPaddingBottom(20);
					cell110.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell110.setUseBorderPadding(true);
					cell110.setColspan(4);
					cell110.setBorderColor(BaseColor.WHITE);
					table11.addCell(cell110);
				} else if (statusVal.equals("null")) {
					PdfPCell cell110 = new PdfPCell(new Paragraph("Promoted to - " + StdDivName, Font5));

					cell110.setPaddingTop(paddingTop);
					cell110.setPaddingBottom(20);
					cell110.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell110.setUseBorderPadding(true);
					cell110.setColspan(4);
					cell110.setBorderColor(BaseColor.WHITE);
					table11.addCell(cell110);
				} else {
					
					if(statusVal.equals("Detained in")) {
						
						PdfPCell cell110 = new PdfPCell(new Paragraph(statusVal + " - " + standard+""+division, Font5));

						cell110.setPaddingTop(paddingTop);
						cell110.setPaddingBottom(20);
						cell110.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell110.setUseBorderPadding(true);
						cell110.setColspan(4);
						cell110.setBorderColor(BaseColor.WHITE);
						table11.addCell(cell110);
						
					}else {
						
						PdfPCell cell110 = new PdfPCell(new Paragraph(statusVal + " - " + StdDivName, Font5));

						cell110.setPaddingTop(paddingTop);
						cell110.setPaddingBottom(20);
						cell110.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell110.setUseBorderPadding(true);
						cell110.setColspan(4);
						cell110.setBorderColor(BaseColor.WHITE);
						table11.addCell(cell110);
						
					}
				}

				document.add(table11);
			}

			/*
			 * PdfPTable table1 = new PdfPTable(3); table1.setTotalWidth(510);
			 * table1.setWidthPercentage(100); Rectangle rect5 = new Rectangle(270, 700);
			 * table1.setWidthPercentage(new float[] { 90, 90, 90 }, rect5);
			 * 
			 * PdfPCell cell111 = new PdfPCell(new Paragraph("Signature of Class Teacher",
			 * Font5)); cell111.setBackgroundColor(BaseColor.WHITE);
			 * cell111.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * cell111.setBorderColor(BaseColor.WHITE);
			 * 
			 * PdfPCell cell1111 = new PdfPCell(new Paragraph("Signature of Principal",
			 * Font5)); cell1111.setBackgroundColor(BaseColor.WHITE);
			 * cell1111.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * cell1111.setBorderColor(BaseColor.WHITE);
			 * 
			 * PdfPCell cell11111 = new PdfPCell(new Paragraph("Signature of Parent",
			 * Font5)); cell11111.setBackgroundColor(BaseColor.WHITE);
			 * cell11111.setHorizontalAlignment(Element.ALIGN_RIGHT);
			 * cell11111.setBorderColor(BaseColor.WHITE);
			 * 
			 * table1.addCell(cell111); table1.addCell(cell1111); table1.addCell(cell11111);
			 * 
			 * FooterTable event = new FooterTable(table1); writer.setPageEvent(event);
			 */

			PdfPTable table1 = new PdfPTable(3);
			table1.setTotalWidth(510);
			table1.setWidthPercentage(100);
			Rectangle rect5 = new Rectangle(270, 700);
			table1.setWidthPercentage(new float[] { 100, 100, 70 }, rect5);

			PdfPTable imageTable2 = new PdfPTable(2);
			imageTable2.setWidthPercentage(100);
			imageTable2.setWidths(new int[] { 2, 2 });

			PdfPCell imageCell12 = new PdfPCell(sealImg, true);
			imageCell12.setBorderColor(BaseColor.WHITE);
			imageCell12.setPaddingLeft(30);
			imageCell12.setPaddingTop(30);

			PdfPCell imageCell24 = new PdfPCell(new Paragraph("", mainContent));
			imageCell24.setBorderColor(BaseColor.WHITE);

			PdfPCell imageCell22 = new PdfPCell(principalSign, true);
			/* imageCell22.setPaddingTop(50); */
			imageCell22.setPaddingLeft(30);
			imageCell22.setBorderColor(BaseColor.WHITE);

			PdfPCell imageCell26 = new PdfPCell(new Paragraph("", mainContent));
			imageCell26.setBorderColor(BaseColor.WHITE);

			imageTable2.addCell(imageCell12);
			imageTable2.addCell(imageCell24);
			imageTable2.addCell(imageCell22);
			imageTable2.addCell(imageCell26);

			PdfPCell cell11101 = new PdfPCell(imageTable2);
			cell11101.setBackgroundColor(BaseColor.WHITE);
			cell11101.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11101.setBorderColor(BaseColor.WHITE);

			PdfPTable imageTable1 = new PdfPTable(2);
			imageTable1.setWidthPercentage(100);
			imageTable1.setWidths(new int[] { 2, 2 });

			PdfPCell imageCell11 = new PdfPCell(signImg, true);
			imageCell11.setHorizontalAlignment(Element.ALIGN_RIGHT);
			imageCell11.setBorderColor(BaseColor.WHITE);

			PdfPCell imageCell21 = new PdfPCell(new Paragraph("", mainContent));
			imageCell21.setBorderColor(BaseColor.WHITE);

			imageTable1.addCell(imageCell11);
			imageTable1.addCell(imageCell21);

			PdfPCell cell1101 = new PdfPCell(imageTable1);
			cell1101.setBackgroundColor(BaseColor.WHITE);
			cell1101.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1101.setPaddingTop(90);
			cell1101.setPaddingLeft(30);

			cell1101.setBorderColor(BaseColor.WHITE);

			PdfPCell cell111101 = new PdfPCell(new Paragraph("", Font5));
			cell111101.setBackgroundColor(BaseColor.WHITE);
			cell111101.setBorderColor(BaseColor.WHITE);

			PdfPCell cell111 = new PdfPCell(new Paragraph("Signature of Class Teacher", Font5));
			cell111.setBackgroundColor(BaseColor.WHITE);
			cell111.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell111.setBorderColor(BaseColor.WHITE);

			PdfPCell cell1111 = new PdfPCell(new Paragraph("Signature of Principal", Font5));
			cell1111.setBackgroundColor(BaseColor.WHITE);
			cell1111.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell1111.setBorderColor(BaseColor.WHITE);

			PdfPCell cell11111 = new PdfPCell(new Paragraph("Signature of Parent", Font5));
			cell11111.setBackgroundColor(BaseColor.WHITE);
			cell11111.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell11111.setBorderColor(BaseColor.WHITE);
			
			table1.addCell(cell1101);
			table1.addCell(cell11101);
			table1.addCell(cell111101);
			
			table1.addCell(cell111);
			table1.addCell(cell1111);
			table1.addCell(cell11111);

			FooterTable1 event = new FooterTable1(table1);
			writer.setPageEvent(event);

			document.close();

			System.out.println("Successfully written and generated Student PDF Report");

			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();
			status = "error";
		}

		return status;
	}

	public String convertReportReviewPDF(int studentID, int registrationID, int standardID, String termName,
			int ayClassID, int organisationID, int userID, int AcademicYearID, String realPath, String pdfFIleName) {
		// int count = 1;

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		List<ConfigurationForm> studentDetailsList = null;

		List<ConfigurationForm> coScholasticGradeList = null;

		List<ConfigurationForm> ExtraCurricularGradeList = null;

		List<ConfigurationForm> PersonalityDevelopmentGradeList = null;

		List<ConfigurationForm> PersonalityDevelopmentGradeListNew = null;

		HashMap<String, Integer> NewExaminationList = null;

		List<ConfigurationForm> ScholasticGradeList = null;

		List<ConfigurationForm> AttendanceList = null;

		String studentName = "";
		String standard = "";
		String division = "";
		int rollNo = 0;
		String dateOfBirth = "";
		String statusVal = "";
		String GRNo = "";
		int SrNo = 1;

		try {

			String StandardName = daoInf2.getStandardNameByStandardID(standardID);

			String Stage = daoInf2.getStandardStageByStandardID(standardID);

			String StdDivName = "";

			if (termName.equals("Term II")) {
				StdDivName = daoInf2.retrieveStdDivName(studentID);
			}

			if (Stage.equals("Primary")) {

				studentDetailsList = daoInf2.retrieveStudentDetailsList(studentID, termName, ayClassID);

				coScholasticGradeList = daoInf2.retrievecoScholasticGradeList(registrationID, termName, ayClassID);

				ExtraCurricularGradeList = daoInf2.retrieveExtraCurricularGradeList(registrationID, termName,
						ayClassID);

				PersonalityDevelopmentGradeList = daoInf2.retrievePersonalityDevelopmentGradeList(registrationID,
						termName, ayClassID);

				PersonalityDevelopmentGradeListNew = daoInf2.retrievePersonalityDevelopmentAbsentList(registrationID,
						termName, ayClassID);

				String attendanceIDList = daoInf2.retrieveAttendanceIDforStudent(termName, AcademicYearID, standardID);

				AttendanceList = daoInf2.retrieveAttendanceListforStudent(registrationID, attendanceIDList,
						StandardName);

				String NewSubjectList = daoInf2.retrieveSubjectListForStandardByStandardID(standardID);

				NewExaminationList = daoInf2.retrieveExaminationList(AcademicYearID, termName, ayClassID);

				int value, value1, value2, value3, value4, finalOutOfMarks = 0;
				double value5, value6, finalSEAMarks = 0D;
				String retestMarks, retestMarksNew = "";
				int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5 = 0;
				int toatlScaleTo, toatlScaleTo1, outOfMarks, outOfMarks1, outOfMarks2, finalSEAmarksvalue,
						totalMarksScaled = 0;
				String grade = "";

				if (NewSubjectList.contains(",")) {

					String subArr[] = NewSubjectList.split(",");

					ScholasticGradeList = new ArrayList<ConfigurationForm>();

					for (int j = 0; j < subArr.length; j++) {

						ConfigurationForm form = new ConfigurationForm();

						int gradeCheck = daoInf2.retrieveGradeBasedValue(NewExaminationList.get("Term End"),
								Integer.parseInt(subArr[j]), ayClassID);

						if (gradeCheck == 1) {

							String subjectName = daoInf2.retrieveSubject(Integer.parseInt(subArr[j]));

							boolean termEdCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]), registrationID,
									ayClassID, NewExaminationList.get("Term End"));

							grade = daoInf2.retrieveGradeValue(Integer.parseInt(subArr[j]), registrationID, ayClassID,
									NewExaminationList.get("Term End"));

							if (termEdCheck) {
								grade = "ex";
							}

							form.setTermEndMarks("-");
							form.setFinalTotalMarks("-");
							form.setUnitTestMarks("-");
							form.setSubjectEnrichmentMarks("-");
							form.setSubject(subjectName);
							form.setGrade(grade);

							ScholasticGradeList.add(form);

						} else {

							boolean seAbsentCheck = false;

							for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
								if (entry.getKey().startsWith("Subject Enrichment")
										|| entry.getKey().startsWith("Notebook")) {

									System.out.println(entry.getValue());
									boolean noteBkCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
											registrationID, ayClassID, entry.getValue());

									if (!noteBkCheck) {
										seAbsentCheck = false;
									}

								}
							}

							boolean termEdCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]), registrationID,
									ayClassID, NewExaminationList.get("Term End"));

							boolean unitTestCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]), registrationID,
									ayClassID, NewExaminationList.get("Unit Test"));

							value = daoInf2.retrieveScholasticGradeList(Integer.parseInt(subArr[j]),
									NewExaminationList.get("Term End"), registrationID, ayClassID);

							retestMarks = daoInf2.retrieveScholasticRetestMarks(Integer.parseInt(subArr[j]),
									NewExaminationList.get("Term End"), registrationID, ayClassID);

							value1 = daoInf2.retrieveScholasticGradeList(Integer.parseInt(subArr[j]),
									NewExaminationList.get("Unit Test"), registrationID, ayClassID);

							retestMarksNew = daoInf2.retrieveScholasticRetestMarks(Integer.parseInt(subArr[j]),
									NewExaminationList.get("Unit Test"), registrationID, ayClassID);

							value2 = daoInf2.retrieveScholasticGradeList1(Integer.parseInt(subArr[j]), registrationID,
									ayClassID, termName);

							String subjectName = daoInf2.retrieveSubject(Integer.parseInt(subArr[j]));

							scaleTo = daoInf2.retrieveScaleTo(Integer.parseInt(subArr[j]),
									NewExaminationList.get("Term End"), ayClassID);

							scaleTo1 = daoInf2.retrieveScaleTo(Integer.parseInt(subArr[j]),
									NewExaminationList.get("Unit Test"), ayClassID);

							scaleTo4 = daoInf2.retrieveScaleTo1(Integer.parseInt(subArr[j]), ayClassID);

							totalMarksScaled = (value + value1 + value2);

							toatlScaleTo = (scaleTo + scaleTo1 + scaleTo4);

							toatlScaleTo1 = (totalMarksScaled * 50) / toatlScaleTo;

							/* Calculating Final Grades */
							if (toatlScaleTo1 >= 46 && toatlScaleTo1 <= 50) {
								grade = "A1";
							} else if (toatlScaleTo1 >= 41 && toatlScaleTo1 <= 45) {
								grade = "A2";
							} else if (toatlScaleTo1 >= 36 && toatlScaleTo1 <= 40) {
								grade = "B1";
							} else if (toatlScaleTo1 >= 31 && toatlScaleTo1 <= 35) {
								grade = "B2";
							} else if (toatlScaleTo1 >= 26 && toatlScaleTo1 <= 30) {
								grade = "C1";
							} else if (toatlScaleTo1 >= 21 && toatlScaleTo1 <= 25) {
								grade = "C2";
							} else if (toatlScaleTo1 >= 17 && toatlScaleTo1 <= 20) {
								grade = "D";
							} else {
								grade = "E";
							}

							/*
							 * To show only grade forEnhanced Marathi Subject in report card and show '-'
							 * for all exams
							 */
							if (subjectName.equals("Enhanced Marathi")) {

								form.setTermEndMarks("-");
								form.setUnitTestMarks("-");
								form.setSubjectEnrichmentMarks("-");
								form.setFinalTotalMarks("-");

								/*
								 * Verifying whether absentFlag is true for any of exam, if so set grade to Ex
								 * else set grade as calculated
								 */
								if (retestMarks == null || retestMarks == "") {
									if (termEdCheck) {
										grade = "ex";
									}

									if (unitTestCheck) {
										grade = "ex";
									}
								} else if (retestMarks.isEmpty()) {
									if (termEdCheck) {
										grade = "ex";
									}

									if (unitTestCheck) {
										grade = "ex";
									}
								}

								if (retestMarksNew == null || retestMarksNew == "") {
									if (termEdCheck) {
										grade = "ex";
									}

									if (unitTestCheck) {
										grade = "ex";
									}
								} else if (retestMarksNew.isEmpty()) {
									if (termEdCheck) {
										grade = "ex";
									}

									if (unitTestCheck) {
										grade = "ex";
									}
								}

								if (seAbsentCheck) {
									grade = "ex";
								}

							} else {
								/*
								 * Verifying whether absentFlag is true for any of exam, if so set grade to Ex
								 * else set grade as calculated
								 */
								if (retestMarks == null || retestMarks == "") {
									if (termEdCheck) {
										grade = "ex";
										form.setTermEndMarks("ex");
										form.setFinalTotalMarks("ex");
									} else {
										form.setTermEndMarks("" + value);
										form.setFinalTotalMarks("" + totalMarksScaled);
									}
								} else if (retestMarks.isEmpty()) {
									if (termEdCheck) {
										grade = "ex";
										form.setTermEndMarks("ex");
										form.setFinalTotalMarks("ex");
									} else {
										form.setTermEndMarks("" + value);
										form.setFinalTotalMarks("" + totalMarksScaled);
									}
								} else {
									if (termEdCheck) {
										grade = "ex";
										form.setTermEndMarks("ex");
										form.setFinalTotalMarks("ex");
									} else {
										form.setTermEndMarks("" + value + "(" + retestMarks + ")");
										form.setFinalTotalMarks("" + totalMarksScaled);
									}
								}

								if (retestMarksNew == null || retestMarksNew == "") {
									if (unitTestCheck) {
										form.setUnitTestMarks("ex");
										grade = "ex";
										form.setFinalTotalMarks("ex");
									} else {
										form.setUnitTestMarks("" + value1);
										form.setFinalTotalMarks("" + totalMarksScaled);
									}
								} else if (retestMarksNew.isEmpty()) {
									if (unitTestCheck) {
										form.setUnitTestMarks("ex");
										grade = "ex";
										form.setFinalTotalMarks("ex");
									} else {
										form.setUnitTestMarks("" + value1);
										form.setFinalTotalMarks("" + totalMarksScaled);
									}
								} else {
									if (unitTestCheck) {
										form.setUnitTestMarks("ex");
										grade = "ex";
										form.setFinalTotalMarks("ex");
									} else {
										form.setUnitTestMarks("" + value1 + "(" + retestMarksNew + ")");
										form.setFinalTotalMarks("" + totalMarksScaled);
									}
								}

								if (seAbsentCheck) {
									grade = "ex";
									form.setSubjectEnrichmentMarks("ex");
									form.setFinalTotalMarks("ex");
								} else {
									form.setSubjectEnrichmentMarks("" + value2);
									form.setFinalTotalMarks("" + totalMarksScaled);
								}
								if (termEdCheck || unitTestCheck || seAbsentCheck) {

									form.setFinalTotalMarks("ex");
								} else {
									form.setFinalTotalMarks("" + totalMarksScaled);
								}
							}

							form.setSubject(subjectName);
							form.setGrade(grade);

							ScholasticGradeList.add(form);
						}

					}
				}

			} else {

				studentDetailsList = daoInf2.retrieveStudentDetailsList(studentID, termName, ayClassID);

				coScholasticGradeList = daoInf2.retrievecoScholasticGradeList(registrationID, termName, ayClassID);

				ExtraCurricularGradeList = daoInf2.retrieveExtraCurricularGradeList(registrationID, termName,
						ayClassID);

				PersonalityDevelopmentGradeList = daoInf2.retrievePersonalityDevelopmentGradeList(registrationID,
						termName, ayClassID);

				PersonalityDevelopmentGradeListNew = daoInf2.retrievePersonalityDevelopmentAbsentList(registrationID,
						termName, ayClassID);

				String attendanceIDList = daoInf2.retrieveAttendanceIDforStudent(termName, AcademicYearID, standardID);

				AttendanceList = daoInf2.retrieveAttendanceListforStudent(registrationID, attendanceIDList,
						StandardName);

				String NewSubjectList = daoInf2.retrieveSubjectListForStandardByStandardID(standardID);

				NewExaminationList = daoInf2.retrieveExaminationList(AcademicYearID, termName, ayClassID);

				int value, value1, value2, value3, value4, value5 = 0;
				String retestMarks, retestMarksNew = "";
				int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5, toatlScaleTo, toatlScaleTo1,
						totalMarksScaled = 0;
				String grade = "";

				if (NewSubjectList.contains(",")) {

					String subArr[] = NewSubjectList.split(",");

					ScholasticGradeList = new ArrayList<ConfigurationForm>();

					for (int j = 0; j < subArr.length; j++) {

						ConfigurationForm form = new ConfigurationForm();

						int gradeCheck = daoInf2.retrieveGradeBasedValue(NewExaminationList.get("Term End"),
								Integer.parseInt(subArr[j]), ayClassID);

						if (gradeCheck == 1) {

							String subjectName = daoInf2.retrieveSubject(Integer.parseInt(subArr[j]));

							boolean termEdCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]), registrationID,
									ayClassID, NewExaminationList.get("Term End"));

							grade = daoInf2.retrieveGradeValue(Integer.parseInt(subArr[j]), registrationID, ayClassID,
									NewExaminationList.get("Term End"));

							if (termEdCheck) {
								grade = "ex";
							}

							form.setTermEndMarks("-");
							form.setFinalTotalMarks("-");
							form.setSubject(subjectName);
							form.setUnitTestMarks("-");
							form.setNotebookMarks("-");
							form.setSubjectEnrichmentMarks("-");
							form.setPortfolioMarks("-");
							form.setMultipleAssessmentMarks("-");
							form.setGrade(grade);

							ScholasticGradeList.add(form);

						} else {

							boolean seAbsentCheck = false;

							for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
								if (entry.getKey().startsWith("Subject Enrichment")) {

									System.out.println(entry.getValue());
									boolean sbjEnrch1Check = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
											registrationID, ayClassID, entry.getValue());

									if (!sbjEnrch1Check) {
										seAbsentCheck = false;
									}

								}
							}

							boolean nbAbsentCheck = false;
							for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
								if (entry.getKey().startsWith("Notebook")) {

									System.out.println(entry.getValue());
									boolean noteBkCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
											registrationID, ayClassID, entry.getValue());

									if (!noteBkCheck) {
										nbAbsentCheck = false;
									}

								}
							}

							boolean portAbsentCheck = false;

							for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
								if (entry.getKey().startsWith("Portfolio")) {

									System.out.println(entry.getValue());
									boolean portCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
											registrationID, ayClassID, entry.getValue());

									if (!portCheck) {
										portAbsentCheck = false;
									}

								}
							}

							boolean multiAbsentCheck = false;
							for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
								if (entry.getKey().startsWith("Multiple Assessment")) {

									System.out.println(entry.getValue());
									boolean multiCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
											registrationID, ayClassID, entry.getValue());

									if (!multiCheck) {
										multiAbsentCheck = false;
									}

								}
							}

							boolean termEdCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]), registrationID,
									ayClassID, NewExaminationList.get("Term End"));

							boolean unitTestCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]), registrationID,
									ayClassID, NewExaminationList.get("Unit Test"));

							value = daoInf2.retrieveScholasticGradeList(Integer.parseInt(subArr[j]),
									NewExaminationList.get("Term End"), registrationID, ayClassID);

							value1 = daoInf2.retrieveScholasticGradeList(Integer.parseInt(subArr[j]),
									NewExaminationList.get("Unit Test"), registrationID, ayClassID);

							retestMarks = daoInf2.retrieveScholasticRetestMarks(Integer.parseInt(subArr[j]),
									NewExaminationList.get("Term End"), registrationID, ayClassID);

							retestMarksNew = daoInf2.retrieveScholasticRetestMarks(Integer.parseInt(subArr[j]),
									NewExaminationList.get("Unit Test"), registrationID, ayClassID);

							value2 = daoInf2.retrieveScholasticGradeListNew(Integer.parseInt(subArr[j]),
									"Subject Enrichment", registrationID, ayClassID, termName);

							value3 = daoInf2.retrieveScholasticGradeListNew(Integer.parseInt(subArr[j]), "Notebook",
									registrationID, ayClassID, termName);

							value4 = daoInf2.retrieveScholasticGradeListNew(Integer.parseInt(subArr[j]), "Portfolio",
									registrationID, ayClassID, termName);

							value5 = daoInf2.retrieveScholasticGradeListNew(Integer.parseInt(subArr[j]),
									"Multiple Assessment", registrationID, ayClassID, termName);

							String subjectName = daoInf2.retrieveSubject(Integer.parseInt(subArr[j]));

							scaleTo = daoInf2.retrieveScaleTo(Integer.parseInt(subArr[j]),
									NewExaminationList.get("Term End"), ayClassID);

							scaleTo1 = daoInf2.retrieveScaleTo(Integer.parseInt(subArr[j]),
									NewExaminationList.get("Unit Test"), ayClassID);

							scaleTo2 = daoInf2.retrieveScaleToNew(Integer.parseInt(subArr[j]), "Subject Enrichment",
									ayClassID, AcademicYearID, termName);

							scaleTo3 = daoInf2.retrieveScaleToNew(Integer.parseInt(subArr[j]), "Notebook", ayClassID,
									AcademicYearID, termName);

							scaleTo4 = daoInf2.retrieveScaleToNew(Integer.parseInt(subArr[j]), "Portfolio", ayClassID,
									AcademicYearID, termName);

							scaleTo5 = daoInf2.retrieveScaleToNew(Integer.parseInt(subArr[j]), "Multiple Assessment",
									ayClassID, AcademicYearID, termName);

							totalMarksScaled = (value + value1 + value2 + value3 + value4 + value5);

							toatlScaleTo = (scaleTo + scaleTo1 + scaleTo2 + scaleTo3 + scaleTo4 + scaleTo5);

							toatlScaleTo1 = (totalMarksScaled * 100) / toatlScaleTo;

							/* Calculating Final Grades */
							if (toatlScaleTo1 >= 91 && toatlScaleTo1 <= 100) {
								grade = "A1";
							} else if (toatlScaleTo1 >= 81 && toatlScaleTo1 <= 90) {
								grade = "A2";
							} else if (toatlScaleTo1 >= 71 && toatlScaleTo1 <= 80) {
								grade = "B1";
							} else if (toatlScaleTo1 >= 61 && toatlScaleTo1 <= 70) {
								grade = "B2";
							} else if (toatlScaleTo1 >= 51 && toatlScaleTo1 <= 60) {
								grade = "C1";
							} else if (toatlScaleTo1 >= 41 && toatlScaleTo1 <= 50) {
								grade = "C2";
							} else if (toatlScaleTo1 >= 33 && toatlScaleTo1 <= 40) {
								grade = "D";
							} else {
								grade = "E";
							}

							/*
							 * To show only grade forEnhanced Marathi Subject in report card and show '-'
							 * for all exams
							 */
							if (subjectName.equals("Enhanced Marathi")) {

								form.setTermEndMarks("-");
								form.setUnitTestMarks("-");
								form.setSubjectEnrichmentMarks("-");
								form.setNotebookMarks("-");
								form.setPortfolioMarks("-");
								form.setMultipleAssessmentMarks("-");
								form.setFinalTotalMarks("-");

								/*
								 * Verifying whether absentFlag is true for any of exam, if so set grade to Ex
								 * else set grade as calculated
								 */
								if (retestMarks == null || retestMarks == "") {
									if (termEdCheck) {
										grade = "ex";
									}

									if (unitTestCheck) {
										grade = "ex";
									}
								} else if (retestMarks.isEmpty()) {
									if (termEdCheck) {
										grade = "ex";
									}

									if (unitTestCheck) {
										grade = "ex";
									}
								}

								if (retestMarksNew == null || retestMarksNew == "") {
									if (termEdCheck) {
										grade = "ex";
									}

									if (unitTestCheck) {
										grade = "ex";
									}
								} else if (retestMarksNew.isEmpty()) {
									if (termEdCheck) {
										grade = "ex";
									}

									if (unitTestCheck) {
										grade = "ex";
									}
								}

								if (seAbsentCheck) {
									grade = "ex";
								}

								if (nbAbsentCheck) {
									grade = "ex";
								}

								if (portAbsentCheck) {
									grade = "ex";
								}

								if (multiAbsentCheck) {
									grade = "ex";
								}

							} else {
								/*
								 * Verifying whether absentFlag is true for any of exam, if so set grade to Ex
								 * else set grade as calculated
								 */

								if (retestMarks == null || retestMarks == "") {
									if (termEdCheck) {
										grade = "ex";
										form.setTermEndMarks("ex");
										form.setFinalTotalMarks("ex");
									} else {
										form.setTermEndMarks("" + value);
										form.setFinalTotalMarks("" + totalMarksScaled);
									}
								} else if (retestMarks.isEmpty()) {
									if (termEdCheck) {
										grade = "ex";
										form.setTermEndMarks("ex");
										form.setFinalTotalMarks("ex");
									} else {
										form.setTermEndMarks("" + value);
										form.setFinalTotalMarks("" + totalMarksScaled);
									}
								} else {
									if (termEdCheck) {
										grade = "ex";
										form.setTermEndMarks("ex");
										form.setFinalTotalMarks("ex");
									} else {
										form.setTermEndMarks("" + value + "(" + retestMarks + ")");
										form.setFinalTotalMarks("" + totalMarksScaled);
									}
								}

								if (retestMarksNew == null || retestMarksNew == "") {
									if (unitTestCheck) {
										form.setUnitTestMarks("ex");
										grade = "ex";
										form.setFinalTotalMarks("ex");
									} else {
										form.setUnitTestMarks("" + value1);
										form.setFinalTotalMarks("" + totalMarksScaled);
									}
								} else if (retestMarksNew.isEmpty()) {
									if (unitTestCheck) {
										form.setUnitTestMarks("ex");
										grade = "ex";
										form.setFinalTotalMarks("ex");
									} else {
										form.setUnitTestMarks("" + value1);
										form.setFinalTotalMarks("" + totalMarksScaled);
									}
								} else {
									if (unitTestCheck) {
										form.setUnitTestMarks("ex");
										grade = "ex";
										form.setFinalTotalMarks("ex");
									} else {
										form.setUnitTestMarks("" + value1 + "(" + retestMarksNew + ")");
										form.setFinalTotalMarks("" + totalMarksScaled);
									}
								}

								if (seAbsentCheck) {
									grade = "ex";
									form.setSubjectEnrichmentMarks("ex");
									form.setFinalTotalMarks("ex");
								} else {
									form.setSubjectEnrichmentMarks("" + value2);
									form.setFinalTotalMarks("" + totalMarksScaled);
								}
								if (nbAbsentCheck) {
									grade = "ex";
									form.setNotebookMarks("ex");
									form.setFinalTotalMarks("ex");
								} else {
									form.setNotebookMarks("" + value3);
									form.setFinalTotalMarks("" + totalMarksScaled);
								}

								if (portAbsentCheck) {
									grade = "ex";
									form.setPortfolioMarks("ex");
									form.setFinalTotalMarks("ex");
								} else {
									form.setPortfolioMarks("" + value4);
									form.setFinalTotalMarks("" + totalMarksScaled);
								}

								if (multiAbsentCheck) {
									grade = "ex";
									form.setMultipleAssessmentMarks("ex");
									form.setFinalTotalMarks("ex");
								} else {
									form.setMultipleAssessmentMarks("" + value5);
									form.setFinalTotalMarks("" + totalMarksScaled);
								}

							}

							form.setSubject(subjectName);
							form.setGrade(grade);

							ScholasticGradeList.add(form);
						}
					}
				}
			}

			for (ConfigurationForm form : studentDetailsList) {
				studentName = form.getStudentName();
				rollNo = form.getRollNumber();
				standard = form.getStandard();
				division = form.getDivision();
				GRNo = form.getGrNumber();
				dateOfBirth = form.getDateOfBirth();
				statusVal = form.getResult();
			}

			/*
			 * Image path for posterior segment images
			 */
			String boardLogoImage = daoInf1.retrieveBoardLogo(organisationID);

			String organisationImage = daoInf1.retrieveOrganizationLogo(organisationID);

			String SignatureFile = daoInf2.retrieveUserSignatureFile(userID);

			String PrincipalSignature = daoInf1.retrievePrincipalSignatureFile(organisationID);

			String SchoolSeal = daoInf1.retrieveOrganizationSeal(organisationID);

			/*
			 * Setting path to store PDF file
			 */
			File file = new File(realPath + "/" + pdfFIleName);
			/*
			 * Creating Document for PDF
			 */
			Document document = null;

			document = new Document(PageSize.A4);

			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));

			Font Font1 = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.NORMAL);
			Font Font3 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
			Font Font2 = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);
			Font Font4 = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD);
			Font Font5 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
			Font mainContent = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL);
			Font mainContent1 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
			// mainContent1.setColor(BaseColor.GRAY);

			document.open();

			Image image1 = Image.getInstance(boardLogoImage);

			Image image2 = Image.getInstance(organisationImage);

			Image signImg = Image.getInstance(SignatureFile);

			Image principalSign = Image.getInstance(PrincipalSignature);

			Image sealImg = Image.getInstance(SchoolSeal);

			/*
			 * Setting header
			 */
			document.addCreator("KovidRMS");
			document.addTitle("Student Report");

			PdfPTable table = new PdfPTable(3);

			table.setFooterRows(1);
			table.setWidthPercentage(100);
			Rectangle rect = new Rectangle(270, 700);
			table.setWidthPercentage(new float[] { 25, 220, 35 }, rect);

			PdfPCell cell = new PdfPCell(image1, true);
			// cell.setPaddingTop(120);
			cell.setPaddingBottom(10);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setUseBorderPadding(true);
			// cell.setColspan(4);
			cell.setBorderWidthBottom(1f);
			// cell.setBorderColorBottom(BaseColor.DARK_GRAY);
			cell.setBorderColor(BaseColor.WHITE);

			PdfPCell cell2 = new PdfPCell(new Paragraph(daoInf1.retrieveOrganizationTagLine(organisationID) + "\n"
					+ daoInf1.retrieveOrganizationName(organisationID) + "\n"
					+ daoInf1.retrieveOrganizationAddress(organisationID) + "\n"
					+ daoInf1.retrieveOrganizationPhone(organisationID), Font5));
			cell2.setBorderWidth(0.01f);
			cell2.setPaddingBottom(10);
			cell2.setBorderWidthLeft(0.2f);
			cell2.setBorderColor(BaseColor.WHITE);
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell cell3 = new PdfPCell(image2, true);
			// cell.setPaddingTop(120);
			cell3.setPaddingBottom(10);
			cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell3.setUseBorderPadding(true);
			// cell.setColspan(4);
			cell3.setBorderWidthBottom(1f);
			// cell.setBorderColorBottom(BaseColor.DARK_GRAY);
			cell3.setBorderColor(BaseColor.WHITE);

			PdfPCell cell4 = new PdfPCell(new Paragraph());
			// cell.setPaddingTop(120);
			// cell4.setPaddingBottom(10);
			// cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			// cell4.setUseBorderPadding(true);
			cell4.setColspan(3);
			cell4.setBorderWidthBottom(0f);
			cell4.setBorderWidthLeft(0f);
			cell4.setBorderWidthRight(0f);
			cell4.setBorderWidthTop(1f);
			// cell.setBorderColorBottom(BaseColor.DARK_GRAY);
			cell4.setBorderColor(BaseColor.DARK_GRAY);

			/*
			 * adding all cell to the table to create tabular structure
			 */

			table.addCell(cell);
			// table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			table.addCell(cell4);

			document.add(table);

			/*
			 * for student details
			 */
			PdfPTable table2 = new PdfPTable(4);
			table2.setWidthPercentage(100);
			Rectangle rect1 = new Rectangle(270, 700);
			table2.setWidthPercentage(new float[] { 145, 45, 45, 45 }, rect1);

			// for Title
			PdfPCell cell0 = new PdfPCell(new Paragraph(
					"Report Card " + termName + " (" + daoInf1.retrieveAcademicYearName(organisationID) + ")", Font5));

			cell0.setPaddingTop(10);
			cell0.setPaddingBottom(5);
			cell0.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell0.setUseBorderPadding(true);
			cell0.setColspan(4);
			cell0.setBorderColor(BaseColor.WHITE);

			// Gene
			PdfPCell cell1 = new PdfPCell(new Paragraph("Student Name: " + studentName, mainContent1));

			cell1.setBorderWidth(0.01f);
			cell1.setPaddingBottom(5);
			cell1.setPaddingTop(5);
			cell1.setBorderColor(BaseColor.WHITE);

			// OD RE
			PdfPCell cell5 = new PdfPCell(new Paragraph("Standard: " + standard, mainContent1));

			cell5.setBorderWidth(0.01f);
			cell5.setPaddingBottom(5);
			cell5.setPaddingTop(5);
			cell5.setBorderColor(BaseColor.WHITE);

			// Os LE
			PdfPCell cell6 = new PdfPCell(new Paragraph("Division: " + division, mainContent1));

			cell6.setBorderWidth(0.01f);
			cell6.setPaddingBottom(5);
			cell6.setPaddingTop(5);
			cell6.setBorderColor(BaseColor.WHITE);

			// Os LE
			PdfPCell cell7 = new PdfPCell(new Paragraph("Roll Number: " + rollNo, mainContent1));

			cell7.setBorderWidth(0.01f);
			cell7.setPaddingBottom(5);
			cell7.setPaddingTop(5);
			cell7.setBorderColor(BaseColor.WHITE);

			// Os LE
			PdfPCell cell8 = new PdfPCell(new Paragraph("DOB: " + dateOfBirth, mainContent1));

			cell8.setBorderWidth(0.01f);
			cell8.setPaddingBottom(5);
			cell8.setPaddingTop(5);
			cell8.setBorderColor(BaseColor.WHITE);

			// Os LE
			PdfPCell cell9 = new PdfPCell(new Paragraph("", mainContent1));

			cell9.setBorderWidth(0.01f);
			cell9.setPaddingBottom(5);
			cell9.setPaddingTop(5);
			cell9.setBorderColor(BaseColor.WHITE);

			// Os LE
			PdfPCell cell11 = new PdfPCell(new Paragraph("GR Number: " + GRNo, mainContent1));

			cell11.setBorderWidth(0.01f);
			cell11.setPaddingBottom(5);
			cell11.setColspan(2);
			cell11.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell11.setPaddingTop(5);
			cell11.setBorderColor(BaseColor.WHITE);

			table2.addCell(cell0);
			table2.addCell(cell1);
			table2.addCell(cell5);
			table2.addCell(cell6);
			table2.addCell(cell7);
			table2.addCell(cell8);
			table2.addCell(cell9);
			table2.addCell(cell11);

			document.add(table2);

			if (StandardName.equals("I") || StandardName.equals("II")
					|| StandardName.equals("III") | StandardName.equals("IV")) {

				/*
				 * For scholastic
				 */
				PdfPTable table3 = new PdfPTable(6);
				table3.setWidthPercentage(100);
				Rectangle rect2 = new Rectangle(270, 700);
				table3.setWidthPercentage(new float[] { 70, 60, 40, 40, 40, 20 }, rect2);

				// For blank space
				PdfPCell cell12 = new PdfPCell(new Paragraph("", Font2));
				cell12.setColspan(6);
				cell12.setBorderWidthRight(0f);
				cell12.setBorderWidthLeft(0f);
				cell12.setBorderWidthTop(0f);
				cell12.setBorderWidthBottom(0f);
				cell12.setBorderColorTop(BaseColor.WHITE);

				// For blank space
				PdfPCell cell012 = new PdfPCell(new Paragraph("Scholastic Areas: ", Font5));
				cell012.setColspan(6);
				cell012.setBorderWidthRight(0f);
				cell012.setBorderWidthLeft(0f);
				cell012.setBorderWidthTop(0f);
				cell012.setBorderWidthBottom(0f);
				cell012.setBorderColorTop(BaseColor.WHITE);

				// Compound
				PdfPCell cell13 = new PdfPCell(new Paragraph("Subjects", mainContent1));

				// cell13.setBorderWidth(0.01f);
				cell13.setPaddingBottom(3);
				cell13.setBorderColor(BaseColor.BLACK);

				// Dose
				PdfPCell cell14 = new PdfPCell(new Paragraph("Subject Enrichment\n+ Notebook(5)", mainContent1));

				// cell14.setBorderWidth(0.01f);
				cell14.setPaddingBottom(3);
				cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell14.setBorderColor(BaseColor.BLACK);

				// Quantity
				PdfPCell cell15 = new PdfPCell(new Paragraph("Unit Test\n(5)", mainContent1));

				// cell15.setBorderWidth(0.01f);
				cell15.setPaddingBottom(3);
				cell15.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell15.setBorderColor(BaseColor.BLACK);

				// Frequency
				PdfPCell cell17 = new PdfPCell(new Paragraph("Term End\n(40)", mainContent1));

				// cell17.setBorderWidth(0.01f);
				cell17.setPaddingBottom(3);
				cell17.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell17.setBorderColor(BaseColor.BLACK);

				// Comment
				PdfPCell cell18 = new PdfPCell(new Paragraph("Marks Obtained\n(50)", mainContent1));

				// cell18.setBorderWidth(0.01f);
				cell18.setPaddingBottom(3);
				cell18.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell18.setBorderColor(BaseColor.BLACK);

				// Instruction
				PdfPCell cell19 = new PdfPCell(new Paragraph("Grade", mainContent1));

				// cell19.setBorderWidth(0.01f);
				cell19.setPaddingBottom(3);
				cell19.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell19.setBorderColor(BaseColor.BLACK);

				table3.addCell(cell12);
				table3.addCell(cell012);
				table3.addCell(cell13);
				table3.addCell(cell14);
				table3.addCell(cell15);
				table3.addCell(cell17);
				table3.addCell(cell18);
				table3.addCell(cell19);

				for (ConfigurationForm form : ScholasticGradeList) {

					// Compound
					PdfPCell cell20 = new PdfPCell(new Paragraph(form.getSubject(), mainContent));

					// cell20.setBorderWidth(0.01f);
					cell20.setPaddingBottom(3);
					// cell20.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell20.setBorderColor(BaseColor.BLACK);

					// No of days
					PdfPCell cell21 = new PdfPCell(new Paragraph("" + form.getSubjectEnrichmentMarks(), mainContent));

					// cell21.setBorderWidth(0.01f);
					cell21.setPaddingBottom(3);
					cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell21.setBorderColor(BaseColor.BLACK);

					// Quantity
					PdfPCell cell22 = new PdfPCell(new Paragraph("" + form.getUnitTestMarks(), mainContent));

					// cell22.setBorderWidth(0.01f);
					cell22.setPaddingBottom(3);
					cell22.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell22.setBorderColor(BaseColor.BLACK);

					// Frequency
					PdfPCell cell24 = new PdfPCell(new Paragraph("" + form.getTermEndMarks(), mainContent));

					// cell24.setBorderWidth(0.01f);
					cell24.setPaddingBottom(3);
					cell24.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell24.setBorderColor(BaseColor.BLACK);

					// Comment
					PdfPCell cell25 = new PdfPCell(new Paragraph("" + form.getFinalTotalMarks(), mainContent));

					// cell25.setBorderWidth(0.01f);
					cell25.setPaddingBottom(3);
					cell25.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell25.setBorderColor(BaseColor.BLACK);

					// Instruction
					PdfPCell cell26 = new PdfPCell(new Paragraph(form.getGrade(), mainContent));

					// cell26.setBorderWidth(0.01f);
					cell26.setPaddingBottom(3);
					cell26.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell26.setBorderColor(BaseColor.BLACK);

					table3.addCell(cell20);
					table3.addCell(cell21);
					table3.addCell(cell22);
					table3.addCell(cell24);
					table3.addCell(cell25);
					table3.addCell(cell26);

				}

				document.add(table3);

			} else {

				/*
				 * For scholastic
				 */
				PdfPTable table3 = new PdfPTable(7);
				table3.setWidthPercentage(100);
				Rectangle rect2 = new Rectangle(270, 700);
				table3.setWidthPercentage(new float[] { 60, 35, 35, 35, 35, 35, 35 }, rect2);

				// For blank space
				PdfPCell cell12 = new PdfPCell(new Paragraph("", Font2));
				cell12.setColspan(7);
				cell12.setBorderWidthRight(0f);
				cell12.setBorderWidthLeft(0f);
				cell12.setBorderWidthTop(0f);
				cell12.setBorderWidthBottom(0f);
				cell12.setBorderColorTop(BaseColor.WHITE);

				// For blank space
				PdfPCell cell012 = new PdfPCell(new Paragraph("Scholastic Areas: ", Font5));
				cell012.setColspan(7);
				cell012.setBorderWidthRight(0f);
				cell012.setBorderWidthLeft(0f);
				cell012.setBorderWidthTop(0f);
				cell012.setBorderWidthBottom(0f);
				cell012.setBorderColorTop(BaseColor.WHITE);

				// Compound
				PdfPCell cell13 = new PdfPCell(new Paragraph("Subjects", mainContent1));

				// cell13.setBorderWidth(0.01f);
				cell13.setPaddingBottom(3);
				cell13.setBorderColor(BaseColor.BLACK);

				// Dose
				PdfPCell cell14 = new PdfPCell(new Paragraph("Notebook\n(5)", mainContent1));

				// cell14.setBorderWidth(0.01f);
				cell14.setPaddingBottom(3);
				cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell14.setBorderColor(BaseColor.BLACK);

				// No of days
				PdfPCell cell15 = new PdfPCell(new Paragraph("Subject Enrichment\n(5)", mainContent1));

				// cell15.setBorderWidth(0.01f);
				cell15.setPaddingBottom(3);
				cell15.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell15.setBorderColor(BaseColor.BLACK);

				// No of days
				PdfPCell cell150 = new PdfPCell(new Paragraph("Portfolio\n(5)", mainContent1));

				// cell15.setBorderWidth(0.01f);
				cell150.setPaddingBottom(3);
				cell150.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell150.setBorderColor(BaseColor.BLACK);

				// No of days
				PdfPCell cell151 = new PdfPCell(new Paragraph("Portfolio\n(5)", mainContent1));

				// cell15.setBorderWidth(0.01f);
				cell151.setPaddingBottom(3);
				cell151.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell151.setBorderColor(BaseColor.BLACK);

				// Quantity
				PdfPCell cell16 = new PdfPCell(new Paragraph("Unit Test\n(10)", mainContent1));

				// cell16.setBorderWidth(0.01f);
				cell16.setPaddingBottom(3);
				cell16.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell16.setBorderColor(BaseColor.BLACK);

				// Frequency
				PdfPCell cell17 = new PdfPCell(new Paragraph("Term End\n(30/80)", mainContent1));

				// cell17.setBorderWidth(0.01f);
				cell17.setPaddingBottom(3);
				cell17.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell17.setBorderColor(BaseColor.BLACK);

				// Comment
				PdfPCell cell18 = new PdfPCell(new Paragraph("Marks Obtained\n(50/100)", mainContent1));

				// cell18.setBorderWidth(0.01f);
				cell18.setPaddingBottom(3);
				cell18.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell18.setBorderColor(BaseColor.BLACK);

				// Instruction
				PdfPCell cell19 = new PdfPCell(new Paragraph("Grade", mainContent1));

				// cell19.setBorderWidth(0.01f);
				cell19.setPaddingBottom(3);
				cell19.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell19.setBorderColor(BaseColor.BLACK);

				table3.addCell(cell12);
				table3.addCell(cell012);
				table3.addCell(cell13);
				table3.addCell(cell14);
				table3.addCell(cell15);
				table3.addCell(cell150);
				table3.addCell(cell151);
				table3.addCell(cell16);
				table3.addCell(cell17);
				table3.addCell(cell18);
				table3.addCell(cell19);

				for (ConfigurationForm form : ScholasticGradeList) {

					// Compound
					PdfPCell cell20 = new PdfPCell(new Paragraph(form.getSubject(), mainContent));

					// cell20.setBorderWidth(0.01f);
					cell20.setPaddingBottom(3);
					cell20.setBorderColor(BaseColor.BLACK);

					// Dose
					PdfPCell cell21 = new PdfPCell(new Paragraph("" + form.getNotebookMarks(), mainContent));

					// cell21.setBorderWidth(0.01f);
					cell21.setPaddingBottom(3);
					cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell21.setBorderColor(BaseColor.BLACK);

					// No of days
					PdfPCell cell22 = new PdfPCell(new Paragraph("" + form.getSubjectEnrichmentMarks(), mainContent));

					// cell22.setBorderWidth(0.01f);
					cell22.setPaddingBottom(3);
					cell22.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell22.setBorderColor(BaseColor.BLACK);

					// No of days
					PdfPCell cell221 = new PdfPCell(new Paragraph("" + form.getPortfolioMarks(), mainContent));

					// cell22.setBorderWidth(0.01f);
					cell221.setPaddingBottom(3);
					cell221.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell221.setBorderColor(BaseColor.BLACK);

					// No of days
					PdfPCell cell222 = new PdfPCell(new Paragraph("" + form.getMultipleAssessmentMarks(), mainContent));

					// cell22.setBorderWidth(0.01f);
					cell222.setPaddingBottom(3);
					cell222.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell222.setBorderColor(BaseColor.BLACK);

					// Quantity
					PdfPCell cell23 = new PdfPCell(new Paragraph("" + form.getUnitTestMarks(), mainContent));

					// cell23.setBorderWidth(0.01f);
					cell23.setPaddingBottom(3);
					cell23.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell23.setBorderColor(BaseColor.BLACK);

					// Frequency
					PdfPCell cell24 = new PdfPCell(new Paragraph("" + form.getTermEndMarks(), mainContent));

					// cell24.setBorderWidth(0.01f);
					cell24.setPaddingBottom(3);
					cell24.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell24.setBorderColor(BaseColor.BLACK);

					// Comment
					PdfPCell cell25 = new PdfPCell(new Paragraph("" + form.getFinalTotalMarks(), mainContent));

					// cell25.setBorderWidth(0.01f);
					cell25.setPaddingBottom(3);
					cell25.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell25.setBorderColor(BaseColor.BLACK);

					// Instruction
					PdfPCell cell26 = new PdfPCell(new Paragraph(form.getGrade(), mainContent));

					// cell26.setBorderWidth(0.01f);
					cell26.setPaddingBottom(3);
					cell26.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell26.setBorderColor(BaseColor.BLACK);

					table3.addCell(cell20);
					table3.addCell(cell21);
					table3.addCell(cell22);
					table3.addCell(cell221);
					table3.addCell(cell222);
					table3.addCell(cell23);
					table3.addCell(cell24);
					table3.addCell(cell25);
					table3.addCell(cell26);

				}

				document.add(table3);

			}

			/*
			 * For Co scholastic and Extra curriculum activities
			 */
			PdfPTable table3 = new PdfPTable(2);
			table3.setWidthPercentage(100);
			Rectangle rect2 = new Rectangle(270, 700);
			table3.setWidthPercentage(new float[] { 135, 135 }, rect2);

			PdfPCell cell15 = new PdfPCell(new Paragraph(
					"                                                                                               "
							+ "                                                                                                                            "
							+ "                                                                                                                              ",
					Font2));
			cell15.setColspan(2);
			cell15.setBorderWidthRight(0f);
			cell15.setBorderWidthLeft(0f);
			cell15.setBorderWidthTop(0f);
			cell15.setBorderWidthBottom(0f);
			// cell15.setPaddingTop(5);
			cell15.setBorderColorTop(BaseColor.WHITE);

			table3.addCell(cell15);
			document.add(table3);

			Paragraph paragraph = new Paragraph();
			PdfPCell celll = null;
			// celll = new PdfPCell(new Paragraph("\n\n"));
			// Main table
			PdfPTable mainTable = new PdfPTable(2);
			mainTable.setWidthPercentage(101);
			// mainTable.addCell(celll);
			// First table
			PdfPCell coscholasticTableCell = new PdfPCell();
			coscholasticTableCell.setBorder(PdfPCell.NO_BORDER);
			coscholasticTableCell.setPaddingRight(20);

			PdfPTable coscholasticTable = new PdfPTable(2);
			Rectangle coscholasticRect = new Rectangle(270, 700);
			coscholasticTable.setWidthPercentage(new float[] { 230, 40 }, coscholasticRect);
			// coscholasticTable.setWidthPercentage(100);
			celll = new PdfPCell(new Phrase("Co-Scholastic Areas: ", Font5));
			celll.setPaddingBottom(5);
			celll.setColspan(2);
			coscholasticTable.addCell(celll);
			celll = new PdfPCell(new Phrase("Subjects", mainContent1));
			coscholasticTable.addCell(celll);
			celll = new PdfPCell(new Phrase("Grade", mainContent1));
			celll.setHorizontalAlignment(Element.ALIGN_CENTER);
			coscholasticTable.addCell(celll);

			for (ConfigurationForm form : coScholasticGradeList) {

				celll = new PdfPCell(new Phrase(form.getSubject(), mainContent));
				coscholasticTable.addCell(celll);
				celll = new PdfPCell(new Phrase(form.getGrade(), mainContent));
				celll.setHorizontalAlignment(Element.ALIGN_CENTER);
				coscholasticTable.addCell(celll);

			}

			coscholasticTableCell.addElement(coscholasticTable);
			mainTable.addCell(coscholasticTableCell);

			// Second table
			PdfPCell extrcrclmTableCell = new PdfPCell();
			extrcrclmTableCell.setBorder(PdfPCell.NO_BORDER);
			extrcrclmTableCell.setPaddingLeft(20);

			PdfPTable extrcrclmTable = new PdfPTable(2);
			Rectangle extrcrclmRect = new Rectangle(270, 700);
			extrcrclmTable.setWidthPercentage(new float[] { 230, 40 }, extrcrclmRect);
			// extrcrclmTable.setWidthPercentage(100);
			celll = new PdfPCell(new Phrase("Extra Curricular Activities: ", Font5));
			celll.setColspan(2);
			celll.setPaddingBottom(5);
			extrcrclmTable.addCell(celll);
			celll = new PdfPCell(new Phrase("Subjects", mainContent1));
			extrcrclmTable.addCell(celll);
			celll = new PdfPCell(new Phrase("Grade", mainContent1));
			celll.setHorizontalAlignment(Element.ALIGN_CENTER);
			extrcrclmTable.addCell(celll);

			for (ConfigurationForm form : ExtraCurricularGradeList) {
				celll = new PdfPCell(new Phrase(form.getSubject(), mainContent));
				extrcrclmTable.addCell(celll);
				celll = new PdfPCell(new Phrase(form.getGrade(), mainContent));
				celll.setHorizontalAlignment(Element.ALIGN_CENTER);
				extrcrclmTable.addCell(celll);
			}
			extrcrclmTableCell.addElement(extrcrclmTable);
			mainTable.addCell(extrcrclmTableCell);

			paragraph.add(mainTable);
			document.add(paragraph);

			/*
			 * For Personality Development & Grading Scale
			 */
			PdfPTable table4 = new PdfPTable(2);
			table4.setWidthPercentage(100);
			Rectangle rect3 = new Rectangle(270, 700);
			table4.setWidthPercentage(new float[] { 135, 135 }, rect3);

			PdfPCell cell18 = new PdfPCell(new Paragraph(
					"                                                                                               "
							+ "                                                                                                                            "
							+ "                                                                                                                              ",
					Font2));
			cell18.setColspan(2);
			cell18.setBorderWidthRight(0f);
			cell18.setBorderWidthLeft(0f);
			cell18.setBorderWidthTop(0f);
			cell18.setBorderWidthBottom(0f);
			// cell18.setPaddingTop(10);
			cell18.setBorderColorTop(BaseColor.WHITE);

			table4.addCell(cell18);

			document.add(table4);

			Paragraph paragraph1 = new Paragraph();
			PdfPCell celll1 = null;
			// celll = new PdfPCell(new Paragraph("\n\n"));
			// Main table
			PdfPTable mainTable1 = new PdfPTable(2);
			mainTable1.setWidthPercentage(101);
			// mainTable.addCell(celll);
			// First table
			PdfPCell persnltdvlpmntTableCell = new PdfPCell();
			persnltdvlpmntTableCell.setBorder(PdfPCell.NO_BORDER);
			persnltdvlpmntTableCell.setPaddingRight(20);

			PdfPTable persnltdvlpmntTable = new PdfPTable(3);
			Rectangle persnltdvlpmntRect = new Rectangle(270, 700);
			persnltdvlpmntTable.setWidthPercentage(new float[] { 50, 170, 50 }, persnltdvlpmntRect);
			// persnltdvlpmntTable.setWidthPercentage(100);
			celll1 = new PdfPCell(new Phrase("Personality Development: ", Font5));
			celll1.setColspan(3);
			celll1.setPaddingBottom(5);
			persnltdvlpmntTable.addCell(celll1);
			celll1 = new PdfPCell(new Phrase("Sr No.", mainContent1));
			celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
			persnltdvlpmntTable.addCell(celll1);
			celll1 = new PdfPCell(new Phrase("Subjects", mainContent1));
			persnltdvlpmntTable.addCell(celll1);
			celll1 = new PdfPCell(new Phrase("Grade", mainContent1));
			celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
			persnltdvlpmntTable.addCell(celll1);

			for (ConfigurationForm form : PersonalityDevelopmentGradeList) {
				celll1 = new PdfPCell(new Phrase("" + SrNo, mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				persnltdvlpmntTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase(form.getSubject(), mainContent));
				persnltdvlpmntTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase(form.getGrade(), mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				persnltdvlpmntTable.addCell(celll1);

				SrNo++;
			}

			for (ConfigurationForm form1 : PersonalityDevelopmentGradeListNew) {
				celll1 = new PdfPCell(new Phrase("" + SrNo, mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				persnltdvlpmntTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase(form1.getSubject(), mainContent));
				persnltdvlpmntTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase(form1.getGrade(), mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				persnltdvlpmntTable.addCell(celll1);

				SrNo++;
			}

			persnltdvlpmntTableCell.addElement(persnltdvlpmntTable);
			mainTable1.addCell(persnltdvlpmntTableCell);

			// Second table
			PdfPCell gradnscleTableCell = new PdfPCell();
			gradnscleTableCell.setBorder(PdfPCell.NO_BORDER);
			gradnscleTableCell.setPaddingLeft(20);

			PdfPTable attendanceTable = new PdfPTable(2);
			attendanceTable.setWidthPercentage(100);

			celll1 = new PdfPCell(new Phrase("Attendance: ", Font5));
			celll1.setColspan(2);
			celll1.setPaddingBottom(5);
			attendanceTable.addCell(celll1);
			/*
			 * celll1 = new PdfPCell(new Phrase("Month", mainContent1));
			 * attendanceTable.addCell(celll1); celll1 = new PdfPCell(new
			 * Phrase("Present Days", mainContent1));
			 * celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * attendanceTable.addCell(celll1);
			 */

			for (ConfigurationForm form : AttendanceList) {

				celll1 = new PdfPCell(new Phrase(form.getWorkingMonth(), mainContent));
				attendanceTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase(form.getStudentWorkingDays(), mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				attendanceTable.addCell(celll1);
			}

			System.out.println("...............AttendanceList.size() :" + AttendanceList.size());

			if (AttendanceList.size() > 0) {
				gradnscleTableCell.addElement(attendanceTable);
			}

			PdfPTable gradnscleTable = new PdfPTable(4);
			gradnscleTable.setWidthPercentage(100);

			celll1 = new PdfPCell(new Phrase("\n"));
			celll1.setBorder(PdfPCell.NO_BORDER);
			celll1.setColspan(4);
			gradnscleTable.addCell(celll1);

			celll1 = new PdfPCell(new Phrase("Grading Scale: ", Font5));
			celll1.setPaddingBottom(5);
			celll1.setColspan(4);
			gradnscleTable.addCell(celll1);

			if (Stage.equals("Primary")) {

				celll1 = new PdfPCell(new Phrase("Marks", mainContent1));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("Grade", mainContent1));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("Marks", mainContent1));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("Grade", mainContent1));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("46-50", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("A1", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("26-30", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("C1", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("41-45", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("A2", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("21-25", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("C2", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("36-40", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("B1", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("17-20", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("D", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("31-35", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("B2", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("16 & Below", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("E", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);

			} else {

				celll1 = new PdfPCell(new Phrase("Marks", mainContent1));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("Grade", mainContent1));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("Marks", mainContent1));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("Grade", mainContent1));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("91-100", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("A1", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("51-60", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("C1", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("81-90", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("A2", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("41-50", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("C2", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("71-80", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("B1", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("33-40", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("D", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("61-70", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("B2", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("32 & Below", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
				celll1 = new PdfPCell(new Phrase("E", mainContent));
				celll1.setHorizontalAlignment(Element.ALIGN_CENTER);
				gradnscleTable.addCell(celll1);
			}

			gradnscleTableCell.addElement(gradnscleTable);
			// mainTable1.addCell(gradnscleTableCell);

			// grade scale2 table
			PdfPCell persnlygradeTableCell = new PdfPCell();
			persnlygradeTableCell.setBorder(PdfPCell.NO_BORDER);
			persnlygradeTableCell.setPaddingLeft(20);

			PdfPTable persnlygradeTable = new PdfPTable(1);
			persnlygradeTable.setWidthPercentage(100);

			celll1 = new PdfPCell(new Phrase("\n"));
			celll1.setBorder(PdfPCell.NO_BORDER);
			persnlygradeTable.addCell(celll1);

			celll1 = new PdfPCell(new Phrase("Grade Code for Personality Development", Font5));
			celll1.setPaddingBottom(5);
			// celll2.setColspan(2);
			persnlygradeTable.addCell(celll1);
			celll1 = new PdfPCell(new Phrase("G = Good; S = Satisfactory; NI = Needs Improvement", mainContent));
			persnlygradeTable.addCell(celll1);

			gradnscleTableCell.addElement(persnlygradeTable);
			mainTable1.addCell(gradnscleTableCell);

			paragraph1.add(mainTable1);
			document.add(paragraph1);

			/*
			 * For signature in footer
			 */
			PdfPTable table11 = new PdfPTable(1);
			table11.setTotalWidth(510);
			table11.setWidthPercentage(100);
			Rectangle rect6 = new Rectangle(270, 700);
			table11.setWidthPercentage(new float[] { 270 }, rect6);

			float paddingTop = 2;

			if (Stage.equals("Primary")) {
				paddingTop = 10;
			}

			/* for Promoted Standard division */
			if (StdDivName != null && StdDivName != "") {

				if (statusVal == null || statusVal == "") {
					PdfPCell cell110 = new PdfPCell(new Paragraph("Promoted to - " + StdDivName, Font5));

					cell110.setPaddingTop(paddingTop);
					cell110.setPaddingBottom(20);
					cell110.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell110.setUseBorderPadding(true);
					cell110.setColspan(4);
					cell110.setBorderColor(BaseColor.WHITE);
					table11.addCell(cell110);
				} else if (statusVal.isEmpty()) {
					PdfPCell cell110 = new PdfPCell(new Paragraph("Promoted to - " + StdDivName, Font5));

					cell110.setPaddingTop(paddingTop);
					cell110.setPaddingBottom(20);
					cell110.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell110.setUseBorderPadding(true);
					cell110.setColspan(4);
					cell110.setBorderColor(BaseColor.WHITE);
					table11.addCell(cell110);
				} else if (statusVal.equals("null")) {
					PdfPCell cell110 = new PdfPCell(new Paragraph("Promoted to - " + StdDivName, Font5));

					cell110.setPaddingTop(paddingTop);
					cell110.setPaddingBottom(20);
					cell110.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell110.setUseBorderPadding(true);
					cell110.setColspan(4);
					cell110.setBorderColor(BaseColor.WHITE);
					table11.addCell(cell110);
				} else {
					
					if(statusVal.equals("Detained in")) {
						
						PdfPCell cell110 = new PdfPCell(new Paragraph(statusVal + " - " + standard+""+division, Font5));

						cell110.setPaddingTop(paddingTop);
						cell110.setPaddingBottom(20);
						cell110.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell110.setUseBorderPadding(true);
						cell110.setColspan(4);
						cell110.setBorderColor(BaseColor.WHITE);
						table11.addCell(cell110);
						
					}else {
						
						PdfPCell cell110 = new PdfPCell(new Paragraph(statusVal + " - " + StdDivName, Font5));

						cell110.setPaddingTop(paddingTop);
						cell110.setPaddingBottom(20);
						cell110.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell110.setUseBorderPadding(true);
						cell110.setColspan(4);
						cell110.setBorderColor(BaseColor.WHITE);
						table11.addCell(cell110);
						
					}
				}

				document.add(table11);
			}

			PdfPTable table1 = new PdfPTable(3);
			table1.setTotalWidth(510);
			table1.setWidthPercentage(100);
			Rectangle rect5 = new Rectangle(270, 700);
			table1.setWidthPercentage(new float[] { 100, 100, 70 }, rect5);

			PdfPTable imageTable2 = new PdfPTable(2);
			imageTable2.setWidthPercentage(100);
			imageTable2.setWidths(new int[] { 2, 2 });

			PdfPCell imageCell12 = new PdfPCell(sealImg, true);
			imageCell12.setBorderColor(BaseColor.WHITE);
			imageCell12.setPaddingLeft(30);
			imageCell12.setPaddingTop(30);

			PdfPCell imageCell24 = new PdfPCell(new Paragraph("", mainContent));
			imageCell24.setBorderColor(BaseColor.WHITE);

			PdfPCell imageCell22 = new PdfPCell(principalSign, true);
			/* imageCell22.setPaddingTop(50); */
			imageCell22.setPaddingLeft(30);
			imageCell22.setBorderColor(BaseColor.WHITE);

			PdfPCell imageCell26 = new PdfPCell(new Paragraph("", mainContent));
			imageCell26.setBorderColor(BaseColor.WHITE);

			imageTable2.addCell(imageCell12);
			imageTable2.addCell(imageCell24);
			imageTable2.addCell(imageCell22);
			imageTable2.addCell(imageCell26);

			PdfPCell cell11101 = new PdfPCell(imageTable2);
			cell11101.setBackgroundColor(BaseColor.WHITE);
			cell11101.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11101.setBorderColor(BaseColor.WHITE);

			PdfPTable imageTable1 = new PdfPTable(2);
			imageTable1.setWidthPercentage(100);
			imageTable1.setWidths(new int[] { 2, 2 });

			PdfPCell imageCell11 = new PdfPCell(signImg, true);
			imageCell11.setHorizontalAlignment(Element.ALIGN_RIGHT);
			imageCell11.setBorderColor(BaseColor.WHITE);

			PdfPCell imageCell21 = new PdfPCell(new Paragraph("", mainContent));
			imageCell21.setBorderColor(BaseColor.WHITE);

			imageTable1.addCell(imageCell11);
			imageTable1.addCell(imageCell21);

			PdfPCell cell1101 = new PdfPCell(imageTable1);
			cell1101.setBackgroundColor(BaseColor.WHITE);
			cell1101.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1101.setPaddingTop(90);
			cell1101.setPaddingLeft(30);

			cell1101.setBorderColor(BaseColor.WHITE);

			PdfPCell cell111101 = new PdfPCell(new Paragraph("", Font5));
			cell111101.setBackgroundColor(BaseColor.WHITE);
			cell111101.setBorderColor(BaseColor.WHITE);

			PdfPCell cell111 = new PdfPCell(new Paragraph("Signature of Class Teacher", Font5));
			cell111.setBackgroundColor(BaseColor.WHITE);
			cell111.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell111.setBorderColor(BaseColor.WHITE);

			PdfPCell cell1111 = new PdfPCell(new Paragraph("Signature of Principal", Font5));
			cell1111.setBackgroundColor(BaseColor.WHITE);
			cell1111.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell1111.setBorderColor(BaseColor.WHITE);

			PdfPCell cell11111 = new PdfPCell(new Paragraph("Signature of Parent", Font5));
			cell11111.setBackgroundColor(BaseColor.WHITE);
			cell11111.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell11111.setBorderColor(BaseColor.WHITE);

			table1.addCell(cell1101);
			table1.addCell(cell11101);
			table1.addCell(cell111101);

			table1.addCell(cell111);
			table1.addCell(cell1111);
			table1.addCell(cell11111);

			FooterTable1 event = new FooterTable1(table1);
			writer.setPageEvent(event);

			document.close();

			System.out.println("Successfully written and generated Student PDF Report");

			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();
			status = "error";
		}
		return status;
	}

	public String convertToLeavingCertificatePDF(int studentID, int registrationID, int ayClassID, int organizationID,
			String realPath, String pdfFIleName) {

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		List<ConfigurationForm> studentDetailsList = null;

		String studentName = "";
		String standard = "";
		String division = "";
		int rollNo = 0;
		String dateOfBirth = "";
		String GRNo = "";

		try {

			String StdDivName = daoInf2.retrieveStdDivName(studentID);

			studentDetailsList = daoInf2.retrieveStudentDetailsFromLeavingCertificate(studentID, registrationID,
					ayClassID);

			/*
			 * Image path for posterior segment images
			 */
			String boardLogoImage = daoInf1.retrieveBoardLogo(organizationID);

			String organisationImage = daoInf1.retrieveOrganizationLogo(organizationID);

			/*
			 * Setting path to store PDF file
			 */
			File file = new File(realPath + "/" + pdfFIleName);
			/*
			 * Creating Document for PDF
			 */
			Document document = null;

			document = new Document(PageSize.LEGAL);

			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));

			Font Font1 = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.NORMAL);
			Font Font3 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
			Font Font2 = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);
			Font Font4 = new Font(Font.FontFamily.TIMES_ROMAN, 19, Font.BOLD);
			Font Font5 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
			Font mainContent = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL);
			Font mainContent1 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
			// mainContent1.setColor(BaseColor.GRAY);

			document.open();

			Image image1 = Image.getInstance(boardLogoImage);

			Image image2 = Image.getInstance(organisationImage);
			image2.scaleToFit(50, 50);

			/*
			 * Setting header
			 */
			document.addCreator("KovidRMS");
			document.addTitle("Student Leaving Certificate PDF");

			PdfPTable table = new PdfPTable(3);

			table.setFooterRows(1);
			table.setWidthPercentage(100);
			Rectangle rect = new Rectangle(270, 700);
			table.setWidthPercentage(new float[] { 25, 220, 35 }, rect);

			PdfPCell cell = new PdfPCell(image1, true);
			cell.setPaddingBottom(5);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setUseBorderPadding(true);
			cell.setBorderWidthBottom(1f);
			cell.setBorderColor(BaseColor.WHITE);

			PdfPCell cell2 = new PdfPCell(new Paragraph(daoInf1.retrieveOrganizationTagLine(organizationID) + "\n"
					+ daoInf1.retrieveOrganizationName(organizationID) + "\n"
					+ daoInf1.retrieveOrganizationAddress(organizationID) + "\n"
					+ daoInf1.retrieveOrganizationPhone(organizationID) + " Email: "
					+ daoInf1.retrieveOrganizationEmail(organizationID), Font5));

			cell2.setBorderWidth(0.01f);
			cell2.setPaddingBottom(5);
			cell2.setBorderWidthLeft(0.2f);
			cell2.setBorderColor(BaseColor.WHITE);
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell cell3 = new PdfPCell(image2, true);
			cell3.setPaddingBottom(5);
			cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell3.setUseBorderPadding(true);
			cell3.setBorderWidthBottom(1f);
			cell3.setBorderColor(BaseColor.WHITE);

			PdfPCell cell4 = new PdfPCell(new Paragraph());
			cell4.setColspan(3);
			cell4.setBorderWidthBottom(0f);
			cell4.setBorderWidthLeft(0f);
			cell4.setBorderWidthRight(0f);
			cell4.setBorderWidthTop(1f);
			cell4.setBorderColor(BaseColor.DARK_GRAY);

			/*
			 * adding all cell to the table to create tabular structure
			 */

			table.addCell(cell);
			// table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			table.addCell(cell4);

			document.add(table);

			/*
			 * for student details
			 */
			PdfPTable table2 = new PdfPTable(3);
			table2.setWidthPercentage(100);
			Rectangle rect1 = new Rectangle(270, 700);
			table2.setWidthPercentage(new float[] { 110, 90, 70 }, rect1);

			// for Title
			PdfPCell cell0 = new PdfPCell(new Paragraph("U-DISE No.27251400611", mainContent1));

			cell0.setPaddingTop(5);
			cell0.setPaddingBottom(5);
			cell0.setUseBorderPadding(true);
			cell0.setBorderColor(BaseColor.WHITE);

			// OD RE
			PdfPCell cell5 = new PdfPCell(new Paragraph("CBSE Affiliation No.1130690", mainContent1));

			cell5.setBorderWidth(0.01f);
			cell5.setPaddingBottom(5);
			cell5.setPaddingTop(5);
			cell5.setBorderColor(BaseColor.WHITE);

			// Os LE
			PdfPCell cell6 = new PdfPCell(new Paragraph("School Code.30596", mainContent1));

			cell6.setBorderWidth(0.01f);
			cell6.setPaddingBottom(5);
			cell6.setPaddingTop(5);
			cell6.setBorderColor(BaseColor.WHITE);

			// Os LE
			PdfPCell cell11 = new PdfPCell(new Paragraph("School Leaving Certificate", Font4));

			cell11.setBorderWidth(0.01f);
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11.setPaddingBottom(5);
			cell11.setPaddingTop(5);
			cell11.setColspan(4);
			cell11.setBorderColor(BaseColor.WHITE);

			// Os LE
			PdfPCell cell112 = new PdfPCell(new Paragraph(
					"(No change in any entry in this certificate is to be made except by the authority issuing it and any infringement liable to involve the imposition of penalty.)",
					mainContent));

			cell112.setBorderWidth(0.01f);
			cell112.setPaddingBottom(15);
			cell112.setPaddingTop(5);
			cell112.setColspan(4);
			cell112.setBorderColor(BaseColor.WHITE);

			table2.addCell(cell0);
			table2.addCell(cell5);
			table2.addCell(cell6);
			table2.addCell(cell11);
			table2.addCell(cell112);

			document.add(table2);

			for (ConfigurationForm form : studentDetailsList) {

				/*
				 * For scholastic
				 */
				PdfPTable table3 = new PdfPTable(4);
				table3.setWidthPercentage(100);
				Rectangle rect2 = new Rectangle(270, 700);
				table3.setWidthPercentage(new float[] { 100, 50, 70, 50 }, rect2);

				PdfPCell cell1 = new PdfPCell(new Paragraph("Sr No.", mainContent1));

				cell1.setPaddingBottom(5);
				cell1.setColspan(1);
				cell1.setBorderColor(BaseColor.BLACK);

				// Gene
				PdfPCell cell198 = new PdfPCell(new Paragraph("" + form.getSerialNo(), mainContent));

				cell198.setPaddingBottom(10);
				cell198.setColspan(1);
				cell198.setBorderColor(BaseColor.BLACK);

				PdfPCell cell101 = new PdfPCell(new Paragraph("Book No.", mainContent1));

				cell101.setPaddingBottom(5);
				cell101.setColspan(1);
				cell101.setBorderColor(BaseColor.BLACK);

				// Gene
				PdfPCell cell1980 = new PdfPCell(new Paragraph("" + form.getBookNo(), mainContent));

				cell1980.setPaddingBottom(10);
				cell1980.setColspan(1);
				cell1980.setBorderColor(BaseColor.BLACK);

				// For blank space
				PdfPCell cell012 = new PdfPCell(new Paragraph("Student ID.", mainContent1));

				cell012.setPaddingBottom(5);
				cell012.setColspan(1);
				cell012.setBorderColor(BaseColor.BLACK);

				PdfPTable table4 = new PdfPTable(19);
				table4.setWidthPercentage(100);
				table4.setWidths(new int[] { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 });

				String StudentNo = form.getStudentNo();

				try {

					PdfPCell cell130 = new PdfPCell(new Paragraph("" + StudentNo.charAt(0), mainContent));

					cell130.setPaddingBottom(5);
					cell130.setBorderColor(BaseColor.BLACK);
					cell130.setHorizontalAlignment(Element.ALIGN_CENTER);

					table4.addCell(cell130);

				} catch (Exception e) {

					PdfPCell cell130 = new PdfPCell(new Paragraph("", mainContent));

					cell130.setPaddingBottom(5);
					cell130.setBorderColor(BaseColor.BLACK);

					table4.addCell(cell130);

				}

				try {

					PdfPCell cell131 = new PdfPCell(new Paragraph("" + StudentNo.charAt(1), mainContent));

					cell131.setPaddingBottom(5);
					cell131.setBorderColor(BaseColor.BLACK);
					cell131.setHorizontalAlignment(Element.ALIGN_CENTER);

					table4.addCell(cell131);

				} catch (Exception e) {

					PdfPCell cell131 = new PdfPCell(new Paragraph("", mainContent));

					cell131.setPaddingBottom(5);
					cell131.setBorderColor(BaseColor.BLACK);

					table4.addCell(cell131);

				}

				try {
					PdfPCell cell132 = new PdfPCell(new Paragraph("" + StudentNo.charAt(2), mainContent));

					cell132.setPaddingBottom(5);
					cell132.setBorderColor(BaseColor.BLACK);
					cell132.setHorizontalAlignment(Element.ALIGN_CENTER);

					table4.addCell(cell132);

				} catch (Exception e) {

					PdfPCell cell132 = new PdfPCell(new Paragraph("", mainContent));

					cell132.setPaddingBottom(5);
					cell132.setBorderColor(BaseColor.BLACK);

					table4.addCell(cell132);
				}

				try {

					PdfPCell cell133 = new PdfPCell(new Paragraph("" + StudentNo.charAt(3), mainContent));

					cell133.setPaddingBottom(5);
					cell133.setBorderColor(BaseColor.BLACK);
					cell133.setHorizontalAlignment(Element.ALIGN_CENTER);

					table4.addCell(cell133);

				} catch (Exception e) {

					PdfPCell cell133 = new PdfPCell(new Paragraph("", mainContent));

					cell133.setPaddingBottom(5);
					cell133.setBorderColor(BaseColor.BLACK);

					table4.addCell(cell133);
				}

				try {

					PdfPCell cell134 = new PdfPCell(new Paragraph("" + StudentNo.charAt(4), mainContent));

					cell134.setPaddingBottom(5);
					cell134.setBorderColor(BaseColor.BLACK);
					cell134.setHorizontalAlignment(Element.ALIGN_CENTER);

					table4.addCell(cell134);

				} catch (Exception e) {

					PdfPCell cell134 = new PdfPCell(new Paragraph("", mainContent));

					cell134.setPaddingBottom(5);
					cell134.setBorderColor(BaseColor.BLACK);

					table4.addCell(cell134);
				}

				try {

					PdfPCell cell135 = new PdfPCell(new Paragraph("" + StudentNo.charAt(5), mainContent));

					cell135.setPaddingBottom(5);
					cell135.setBorderColor(BaseColor.BLACK);
					cell135.setHorizontalAlignment(Element.ALIGN_CENTER);

					table4.addCell(cell135);
				} catch (Exception e) {

					PdfPCell cell135 = new PdfPCell(new Paragraph("", mainContent));

					cell135.setPaddingBottom(5);
					cell135.setBorderColor(BaseColor.BLACK);

					table4.addCell(cell135);
				}

				try {

					PdfPCell cell136 = new PdfPCell(new Paragraph("" + StudentNo.charAt(6), mainContent));

					cell136.setPaddingBottom(5);
					cell136.setBorderColor(BaseColor.BLACK);
					cell136.setHorizontalAlignment(Element.ALIGN_CENTER);

					table4.addCell(cell136);
				} catch (Exception e) {

					PdfPCell cell136 = new PdfPCell(new Paragraph("", mainContent));

					cell136.setPaddingBottom(5);
					cell136.setBorderColor(BaseColor.BLACK);

					table4.addCell(cell136);
				}

				try {

					PdfPCell cell137 = new PdfPCell(new Paragraph("" + StudentNo.charAt(7), mainContent));

					cell137.setPaddingBottom(5);
					cell137.setBorderColor(BaseColor.BLACK);
					cell137.setHorizontalAlignment(Element.ALIGN_CENTER);

					table4.addCell(cell137);

				} catch (Exception e) {

					PdfPCell cell137 = new PdfPCell(new Paragraph("", mainContent));

					cell137.setPaddingBottom(5);
					cell137.setBorderColor(BaseColor.BLACK);

					table4.addCell(cell137);
				}

				try {

					PdfPCell cell138 = new PdfPCell(new Paragraph("" + StudentNo.charAt(8), mainContent));

					cell138.setPaddingBottom(5);
					cell138.setBorderColor(BaseColor.BLACK);
					cell138.setHorizontalAlignment(Element.ALIGN_CENTER);

					table4.addCell(cell138);
				} catch (Exception e) {

					PdfPCell cell138 = new PdfPCell(new Paragraph("", mainContent));

					cell138.setPaddingBottom(5);
					cell138.setBorderColor(BaseColor.BLACK);

					table4.addCell(cell138);
				}

				try {

					PdfPCell cell139 = new PdfPCell(new Paragraph("" + StudentNo.charAt(9), mainContent));

					cell139.setPaddingBottom(5);
					cell139.setBorderColor(BaseColor.BLACK);
					cell139.setHorizontalAlignment(Element.ALIGN_CENTER);

					table4.addCell(cell139);
				} catch (Exception e) {

					PdfPCell cell139 = new PdfPCell(new Paragraph("", mainContent));

					cell139.setPaddingBottom(5);
					cell139.setBorderColor(BaseColor.BLACK);

					table4.addCell(cell139);
				}

				try {

					PdfPCell cell1311 = new PdfPCell(new Paragraph("" + StudentNo.charAt(10), mainContent));

					cell1311.setPaddingBottom(5);
					cell1311.setBorderColor(BaseColor.BLACK);
					cell1311.setHorizontalAlignment(Element.ALIGN_CENTER);

					table4.addCell(cell1311);
				} catch (Exception e) {

					PdfPCell cell1311 = new PdfPCell(new Paragraph("", mainContent));

					cell1311.setPaddingBottom(5);
					cell1311.setBorderColor(BaseColor.BLACK);

					table4.addCell(cell1311);
				}

				try {

					PdfPCell cell1331 = new PdfPCell(new Paragraph("" + StudentNo.charAt(11), mainContent));

					cell1331.setPaddingBottom(5);
					cell1331.setBorderColor(BaseColor.BLACK);
					cell1331.setHorizontalAlignment(Element.ALIGN_CENTER);

					table4.addCell(cell1331);
				} catch (Exception e) {

					PdfPCell cell1331 = new PdfPCell(new Paragraph("", mainContent));

					cell1331.setPaddingBottom(5);
					cell1331.setBorderColor(BaseColor.BLACK);

					table4.addCell(cell1331);
				}

				try {

					PdfPCell cell1301 = new PdfPCell(new Paragraph("" + StudentNo.charAt(12), mainContent));

					cell1301.setPaddingBottom(5);
					cell1301.setBorderColor(BaseColor.BLACK);
					cell1301.setHorizontalAlignment(Element.ALIGN_CENTER);

					table4.addCell(cell1301);
				} catch (Exception e) {

					PdfPCell cell1301 = new PdfPCell(new Paragraph("", mainContent));

					cell1301.setPaddingBottom(5);
					cell1301.setBorderColor(BaseColor.BLACK);

					table4.addCell(cell1301);
				}

				try {

					PdfPCell cell1312 = new PdfPCell(new Paragraph("" + StudentNo.charAt(13), mainContent));

					cell1312.setPaddingBottom(5);
					cell1312.setBorderColor(BaseColor.BLACK);
					cell1312.setHorizontalAlignment(Element.ALIGN_CENTER);

					table4.addCell(cell1312);
				} catch (Exception e) {

					PdfPCell cell1312 = new PdfPCell(new Paragraph("", mainContent));

					cell1312.setPaddingBottom(5);
					cell1312.setBorderColor(BaseColor.BLACK);

					table4.addCell(cell1312);
				}

				try {

					PdfPCell cell1323 = new PdfPCell(new Paragraph("" + StudentNo.charAt(14), mainContent));

					cell1323.setPaddingBottom(5);
					cell1323.setBorderColor(BaseColor.BLACK);
					cell1323.setHorizontalAlignment(Element.ALIGN_CENTER);

					table4.addCell(cell1323);
				} catch (Exception e) {

					PdfPCell cell1323 = new PdfPCell(new Paragraph("", mainContent));

					cell1323.setPaddingBottom(5);
					cell1323.setBorderColor(BaseColor.BLACK);

					table4.addCell(cell1323);
				}

				try {

					PdfPCell cell1334 = new PdfPCell(new Paragraph("" + StudentNo.charAt(15), mainContent));

					cell1334.setPaddingBottom(5);
					cell1334.setBorderColor(BaseColor.BLACK);
					cell1334.setHorizontalAlignment(Element.ALIGN_CENTER);

					table4.addCell(cell1334);
				} catch (Exception e) {

					PdfPCell cell1334 = new PdfPCell(new Paragraph("", mainContent));

					cell1334.setPaddingBottom(5);
					cell1334.setBorderColor(BaseColor.BLACK);

					table4.addCell(cell1334);
				}

				try {

					PdfPCell cell13120 = new PdfPCell(new Paragraph("" + StudentNo.charAt(16), mainContent));

					cell13120.setPaddingBottom(5);
					cell13120.setBorderColor(BaseColor.BLACK);
					cell13120.setHorizontalAlignment(Element.ALIGN_CENTER);

					table4.addCell(cell13120);
				} catch (Exception e) {

					PdfPCell cell13120 = new PdfPCell(new Paragraph("", mainContent));

					cell13120.setPaddingBottom(5);
					cell13120.setBorderColor(BaseColor.BLACK);

					table4.addCell(cell13120);
				}

				try {

					PdfPCell cell13232 = new PdfPCell(new Paragraph("" + StudentNo.charAt(17), mainContent));

					cell13232.setPaddingBottom(5);
					cell13232.setBorderColor(BaseColor.BLACK);
					cell13232.setHorizontalAlignment(Element.ALIGN_CENTER);

					table4.addCell(cell13232);
				} catch (Exception e) {

					PdfPCell cell13232 = new PdfPCell(new Paragraph("", mainContent));

					cell13232.setPaddingBottom(5);
					cell13232.setBorderColor(BaseColor.BLACK);

					table4.addCell(cell13232);
				}

				try {

					PdfPCell cell13341 = new PdfPCell(new Paragraph("" + StudentNo.charAt(18), mainContent));

					cell13341.setPaddingBottom(5);
					cell13341.setBorderColor(BaseColor.BLACK);
					cell13341.setHorizontalAlignment(Element.ALIGN_CENTER);

					table4.addCell(cell13341);
				} catch (Exception e) {

					PdfPCell cell13341 = new PdfPCell(new Paragraph("", mainContent));

					cell13341.setPaddingBottom(5);
					cell13341.setBorderColor(BaseColor.BLACK);

					table4.addCell(cell13341);
				}

				// Compound
				PdfPCell cell13 = new PdfPCell(table4);

				cell13.setColspan(3);
				cell13.setBorderColor(BaseColor.BLACK);

				// Dose
				PdfPCell cell14 = new PdfPCell(new Paragraph("Admission No.", mainContent1));

				cell14.setPaddingBottom(5);
				cell14.setColspan(1);
				cell14.setBorderColor(BaseColor.BLACK);

				// Quantity
				PdfPCell cell15 = new PdfPCell(new Paragraph("" + form.getGrNumber(), mainContent));

				cell15.setPaddingBottom(5);
				cell15.setColspan(3);
				cell15.setBorderColor(BaseColor.BLACK);

				// Frequency
				PdfPCell cell17 = new PdfPCell(new Paragraph("Name of pupil in full", mainContent1));

				cell17.setPaddingBottom(5);
				cell17.setColspan(1);
				cell17.setBorderColor(BaseColor.BLACK);

				// Comment
				PdfPCell cell18 = new PdfPCell(new Paragraph("" + form.getStudentName(), mainContent));

				cell18.setPaddingBottom(5);
				cell18.setColspan(3);
				cell18.setBorderColor(BaseColor.BLACK);

				// Instruction
				PdfPCell cell109 = new PdfPCell(new Paragraph("Mother's full name", mainContent1));

				cell109.setPaddingBottom(5);
				cell109.setColspan(1);
				cell109.setBorderColor(BaseColor.BLACK);

				PdfPCell cell102 = new PdfPCell(new Paragraph("" + form.getMotherName(), mainContent));

				cell102.setPaddingBottom(5);
				cell102.setColspan(3);
				cell102.setBorderColor(BaseColor.BLACK);

				// Instruction
				PdfPCell cell190 = new PdfPCell(new Paragraph("Father's full name", mainContent1));

				cell190.setPaddingBottom(5);
				cell190.setColspan(1);
				cell190.setBorderColor(BaseColor.BLACK);

				PdfPCell cell120 = new PdfPCell(new Paragraph("" + form.getFatherName(), mainContent));

				cell120.setPaddingBottom(5);
				cell120.setColspan(3);
				cell120.setBorderColor(BaseColor.BLACK);

				// Instruction
				PdfPCell cell19 = new PdfPCell(new Paragraph("UID(Aadhar card) No of Pupil", mainContent1));

				cell19.setPaddingBottom(5);
				cell19.setColspan(1);
				cell19.setBorderColor(BaseColor.BLACK);

				PdfPTable table6 = new PdfPTable(12);
				table6.setWidthPercentage(100);
				table6.setWidths(new int[] { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 });

				String Aadhaar = form.getAadhaar();

				try {

					PdfPCell cell1209 = new PdfPCell(new Paragraph("" + Aadhaar.charAt(0), mainContent));

					cell1209.setPaddingBottom(5);
					cell1209.setBorderColor(BaseColor.BLACK);
					cell1209.setHorizontalAlignment(Element.ALIGN_CENTER);

					table6.addCell(cell1209);
				} catch (Exception e) {

					PdfPCell cell1209 = new PdfPCell(new Paragraph("", mainContent));

					cell1209.setPaddingBottom(5);
					cell1209.setBorderColor(BaseColor.BLACK);

					table6.addCell(cell1209);
				}

				try {

					PdfPCell cell12091 = new PdfPCell(new Paragraph("" + Aadhaar.charAt(1), mainContent));

					cell12091.setPaddingBottom(5);
					cell12091.setBorderColor(BaseColor.BLACK);
					cell12091.setHorizontalAlignment(Element.ALIGN_CENTER);

					table6.addCell(cell12091);
				} catch (Exception e) {

					PdfPCell cell12091 = new PdfPCell(new Paragraph("", mainContent));

					cell12091.setPaddingBottom(5);
					cell12091.setBorderColor(BaseColor.BLACK);

					table6.addCell(cell12091);
				}

				try {

					PdfPCell cell12092 = new PdfPCell(new Paragraph("" + Aadhaar.charAt(2), mainContent));

					cell12092.setPaddingBottom(5);
					cell12092.setBorderColor(BaseColor.BLACK);
					cell12092.setHorizontalAlignment(Element.ALIGN_CENTER);

					table6.addCell(cell12092);
				} catch (Exception e) {

					PdfPCell cell12092 = new PdfPCell(new Paragraph("", mainContent));

					cell12092.setPaddingBottom(5);
					cell12092.setBorderColor(BaseColor.BLACK);

					table6.addCell(cell12092);
				}

				try {

					PdfPCell cell12093 = new PdfPCell(new Paragraph("" + Aadhaar.charAt(3), mainContent));

					cell12093.setPaddingBottom(5);
					cell12093.setBorderColor(BaseColor.BLACK);
					cell12093.setHorizontalAlignment(Element.ALIGN_CENTER);

					table6.addCell(cell12093);
				} catch (Exception e) {

					PdfPCell cell12093 = new PdfPCell(new Paragraph("", mainContent));

					cell12093.setPaddingBottom(5);
					cell12093.setBorderColor(BaseColor.BLACK);

					table6.addCell(cell12093);
				}

				try {

					PdfPCell cell12094 = new PdfPCell(new Paragraph("" + Aadhaar.charAt(4), mainContent));

					cell12094.setPaddingBottom(5);
					cell12094.setBorderColor(BaseColor.BLACK);
					cell12094.setHorizontalAlignment(Element.ALIGN_CENTER);

					table6.addCell(cell12094);
				} catch (Exception e) {

					PdfPCell cell12094 = new PdfPCell(new Paragraph("", mainContent));

					cell12094.setPaddingBottom(5);
					cell12094.setBorderColor(BaseColor.BLACK);

					table6.addCell(cell12094);
				}

				try {

					PdfPCell cell12095 = new PdfPCell(new Paragraph("" + Aadhaar.charAt(5), mainContent));

					cell12095.setPaddingBottom(5);
					cell12095.setBorderColor(BaseColor.BLACK);
					cell12095.setHorizontalAlignment(Element.ALIGN_CENTER);

					table6.addCell(cell12095);
				} catch (Exception e) {

					PdfPCell cell12095 = new PdfPCell(new Paragraph("", mainContent));

					cell12095.setPaddingBottom(5);
					cell12095.setBorderColor(BaseColor.BLACK);

					table6.addCell(cell12095);
				}

				try {

					PdfPCell cell12096 = new PdfPCell(new Paragraph("" + Aadhaar.charAt(6), mainContent));

					cell12096.setPaddingBottom(5);
					cell12096.setBorderColor(BaseColor.BLACK);
					cell12096.setHorizontalAlignment(Element.ALIGN_CENTER);

					table6.addCell(cell12096);
				} catch (Exception e) {

					PdfPCell cell12096 = new PdfPCell(new Paragraph("", mainContent));

					cell12096.setPaddingBottom(5);
					cell12096.setBorderColor(BaseColor.BLACK);

					table6.addCell(cell12096);
				}

				try {

					PdfPCell cell12097 = new PdfPCell(new Paragraph("" + Aadhaar.charAt(7), mainContent));

					cell12097.setPaddingBottom(5);
					cell12097.setBorderColor(BaseColor.BLACK);
					cell12097.setHorizontalAlignment(Element.ALIGN_CENTER);

					table6.addCell(cell12097);
				} catch (Exception e) {

					PdfPCell cell12097 = new PdfPCell(new Paragraph("", mainContent));

					cell12097.setPaddingBottom(5);
					cell12097.setBorderColor(BaseColor.BLACK);

					table6.addCell(cell12097);
				}

				try {

					PdfPCell cell12098 = new PdfPCell(new Paragraph("" + Aadhaar.charAt(8), mainContent));

					cell12098.setPaddingBottom(5);
					cell12098.setBorderColor(BaseColor.BLACK);
					cell12098.setHorizontalAlignment(Element.ALIGN_CENTER);

					table6.addCell(cell12098);
				} catch (Exception e) {

					PdfPCell cell12098 = new PdfPCell(new Paragraph("", mainContent));

					cell12098.setPaddingBottom(5);
					cell12098.setBorderColor(BaseColor.BLACK);

					table6.addCell(cell12098);
				}

				try {

					PdfPCell cell12099 = new PdfPCell(new Paragraph("" + Aadhaar.charAt(9), mainContent));

					cell12099.setPaddingBottom(5);
					cell12099.setBorderColor(BaseColor.BLACK);
					cell12099.setHorizontalAlignment(Element.ALIGN_CENTER);

					table6.addCell(cell12099);
				} catch (Exception e) {

					PdfPCell cell12099 = new PdfPCell(new Paragraph("", mainContent));

					cell12099.setPaddingBottom(5);
					cell12099.setBorderColor(BaseColor.BLACK);

					table6.addCell(cell12099);
				}

				try {

					PdfPCell cell120910 = new PdfPCell(new Paragraph("" + Aadhaar.charAt(10), mainContent));

					cell120910.setPaddingBottom(5);
					cell120910.setBorderColor(BaseColor.BLACK);
					cell120910.setHorizontalAlignment(Element.ALIGN_CENTER);

					table6.addCell(cell120910);
				} catch (Exception e) {

					PdfPCell cell120910 = new PdfPCell(new Paragraph("", mainContent));

					cell120910.setPaddingBottom(5);
					cell120910.setBorderColor(BaseColor.BLACK);

					table6.addCell(cell120910);
				}

				try {

					PdfPCell cell120911 = new PdfPCell(new Paragraph("" + Aadhaar.charAt(11), mainContent));

					cell120911.setPaddingBottom(5);
					cell120911.setBorderColor(BaseColor.BLACK);
					cell120911.setHorizontalAlignment(Element.ALIGN_CENTER);

					table6.addCell(cell120911);
				} catch (Exception e) {

					PdfPCell cell120911 = new PdfPCell(new Paragraph("", mainContent));

					cell120911.setPaddingBottom(5);
					cell120911.setBorderColor(BaseColor.BLACK);

					table6.addCell(cell120911);
				}

				PdfPCell cell12 = new PdfPCell(table6);

				cell12.setColspan(3);
				cell12.setBorderColor(BaseColor.BLACK);

				// Instruction
				PdfPCell cell191 = new PdfPCell(new Paragraph("Date of birth (in Christian Era)", mainContent1));

				cell191.setPaddingBottom(5);
				cell191.setColspan(1);
				cell191.setBorderColor(BaseColor.BLACK);

				String DateOfBirth = form.getDateOfBirth();

				PdfPTable table5 = new PdfPTable(20);
				table5.setWidthPercentage(100);
				table5.setWidths(new int[] { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 });

				try {

					PdfPCell cell12100 = new PdfPCell(new Paragraph("" + DateOfBirth.charAt(0), mainContent));

					cell12100.setPaddingBottom(5);
					cell12100.setBorderColor(BaseColor.BLACK);
					cell12100.setHorizontalAlignment(Element.ALIGN_CENTER);

					table5.addCell(cell12100);
				} catch (Exception e) {

					PdfPCell cell12100 = new PdfPCell(new Paragraph("", mainContent));

					cell12100.setPaddingBottom(5);
					cell12100.setBorderColor(BaseColor.BLACK);

					table5.addCell(cell12100);
				}

				try {

					PdfPCell cell12110 = new PdfPCell(new Paragraph("" + DateOfBirth.charAt(1), mainContent));

					cell12110.setPaddingBottom(5);
					cell12110.setBorderColor(BaseColor.BLACK);
					cell12110.setHorizontalAlignment(Element.ALIGN_CENTER);

					table5.addCell(cell12110);
				} catch (Exception e) {

					PdfPCell cell12110 = new PdfPCell(new Paragraph("", mainContent));

					cell12110.setPaddingBottom(5);
					cell12110.setBorderColor(BaseColor.BLACK);

					table5.addCell(cell12110);
				}

				try {

					PdfPCell cell121101 = new PdfPCell(new Paragraph("" + DateOfBirth.charAt(2), mainContent));

					cell121101.setPaddingBottom(5);
					cell121101.setBorderColor(BaseColor.BLACK);
					cell121101.setHorizontalAlignment(Element.ALIGN_CENTER);

					table5.addCell(cell121101);
				} catch (Exception e) {

					PdfPCell cell121101 = new PdfPCell(new Paragraph("", mainContent));

					cell121101.setPaddingBottom(5);
					cell121101.setBorderColor(BaseColor.BLACK);

					table5.addCell(cell121101);
				}

				try {

					PdfPCell cell1212 = new PdfPCell(new Paragraph("" + DateOfBirth.charAt(3), mainContent));

					cell1212.setPaddingBottom(5);
					cell1212.setBorderColor(BaseColor.BLACK);
					cell1212.setHorizontalAlignment(Element.ALIGN_CENTER);

					table5.addCell(cell1212);
				} catch (Exception e) {

					PdfPCell cell1212 = new PdfPCell(new Paragraph("", mainContent));

					cell1212.setPaddingBottom(5);
					cell1212.setBorderColor(BaseColor.BLACK);

					table5.addCell(cell1212);
				}

				try {

					PdfPCell cell1213 = new PdfPCell(new Paragraph("" + DateOfBirth.charAt(4), mainContent));

					cell1213.setPaddingBottom(5);
					cell1213.setBorderColor(BaseColor.BLACK);
					cell1213.setHorizontalAlignment(Element.ALIGN_CENTER);

					table5.addCell(cell1213);
				} catch (Exception e) {

					PdfPCell cell1213 = new PdfPCell(new Paragraph("", mainContent));

					cell1213.setPaddingBottom(5);
					cell1213.setBorderColor(BaseColor.BLACK);

					table5.addCell(cell1213);
				}

				try {

					PdfPCell cell12132 = new PdfPCell(new Paragraph("" + DateOfBirth.charAt(5), mainContent));

					cell12132.setPaddingBottom(5);
					cell12132.setBorderColor(BaseColor.BLACK);
					cell12132.setHorizontalAlignment(Element.ALIGN_CENTER);

					table5.addCell(cell12132);
				} catch (Exception e) {

					PdfPCell cell12132 = new PdfPCell(new Paragraph("", mainContent));

					cell12132.setPaddingBottom(5);
					cell12132.setBorderColor(BaseColor.BLACK);

					table5.addCell(cell12132);
				}

				try {

					PdfPCell cell1214 = new PdfPCell(new Paragraph("" + DateOfBirth.charAt(6), mainContent));

					cell1214.setPaddingBottom(5);
					cell1214.setBorderColor(BaseColor.BLACK);
					cell1214.setHorizontalAlignment(Element.ALIGN_CENTER);

					table5.addCell(cell1214);
				} catch (Exception e) {

					PdfPCell cell1214 = new PdfPCell(new Paragraph("", mainContent));

					cell1214.setPaddingBottom(5);
					cell1214.setBorderColor(BaseColor.BLACK);

					table5.addCell(cell1214);
				}

				try {

					PdfPCell cell1215 = new PdfPCell(new Paragraph("" + DateOfBirth.charAt(7), mainContent));

					cell1215.setPaddingBottom(5);
					cell1215.setBorderColor(BaseColor.BLACK);
					cell1215.setHorizontalAlignment(Element.ALIGN_CENTER);

					table5.addCell(cell1215);
				} catch (Exception e) {

					PdfPCell cell1215 = new PdfPCell(new Paragraph("", mainContent));

					cell1215.setPaddingBottom(5);
					cell1215.setBorderColor(BaseColor.BLACK);

					table5.addCell(cell1215);
				}

				try {

					PdfPCell cell1216 = new PdfPCell(new Paragraph("" + DateOfBirth.charAt(8), mainContent));

					cell1216.setPaddingBottom(5);
					cell1216.setBorderColor(BaseColor.BLACK);
					cell1216.setHorizontalAlignment(Element.ALIGN_CENTER);

					table5.addCell(cell1216);
				} catch (Exception e) {
					PdfPCell cell1216 = new PdfPCell(new Paragraph("", mainContent));

					cell1216.setPaddingBottom(5);
					cell1216.setBorderColor(BaseColor.BLACK);

					table5.addCell(cell1216);
				}

				try {

					PdfPCell cell1217 = new PdfPCell(new Paragraph("" + DateOfBirth.charAt(9), mainContent));

					cell1217.setPaddingBottom(5);
					cell1217.setBorderColor(BaseColor.BLACK);
					cell1217.setHorizontalAlignment(Element.ALIGN_CENTER);

					table5.addCell(cell1217);
				} catch (Exception e) {

					PdfPCell cell1217 = new PdfPCell(new Paragraph("", mainContent));

					cell1217.setPaddingBottom(5);
					cell1217.setBorderColor(BaseColor.BLACK);

					table5.addCell(cell1217);
				}

				PdfPCell cell12171 = new PdfPCell();

				cell12171.setColspan(10);
				cell12171.setPaddingBottom(5);
				cell12171.setBorderColor(BaseColor.BLACK);
				cell12171.setHorizontalAlignment(Element.ALIGN_CENTER);

				table5.addCell(cell12171);

				PdfPCell cell1210 = new PdfPCell(
						new Paragraph("In Words:" + form.getDateOfBirthInWords(), mainContent));

				cell1210.setColspan(20);
				cell1210.setPaddingBottom(5);
				cell1210.setBorderColor(BaseColor.BLACK);

				table5.addCell(cell1210);

				PdfPCell cell121 = new PdfPCell(table5);

				cell121.setColspan(3);
				cell121.setBorderColor(BaseColor.BLACK);

				/*
				 * // Instruction PdfPCell cell11921 = new PdfPCell();
				 * 
				 * cell11921.setPaddingBottom(5); cell11921.setColspan(2);
				 * cell11921.setBorderColor(BaseColor.BLACK);
				 */

				// Instruction
				PdfPCell cell119 = new PdfPCell(new Paragraph("Place of birth. (Tal.Dist.State)", mainContent1));

				cell119.setPaddingBottom(5);
				cell119.setColspan(1);
				cell119.setBorderColor(BaseColor.BLACK);

				PdfPCell cell122 = new PdfPCell(new Paragraph("" + form.getBirthPlace(), mainContent));

				cell122.setPaddingBottom(5);
				cell122.setColspan(3);
				cell122.setBorderColor(BaseColor.BLACK);

				// Instruction
				PdfPCell cell192 = new PdfPCell(new Paragraph("Nationality", mainContent1));

				cell192.setPaddingBottom(5);
				cell192.setColspan(1);
				cell192.setBorderColor(BaseColor.BLACK);

				PdfPCell cell123 = new PdfPCell(new Paragraph("" + form.getNationality(), mainContent));

				cell123.setPaddingBottom(5);
				cell123.setColspan(1);
				cell123.setBorderColor(BaseColor.BLACK);

				// Instruction
				PdfPCell cell193 = new PdfPCell(new Paragraph("Mother Tongue", mainContent1));

				cell193.setPaddingBottom(5);
				cell193.setColspan(1);
				cell193.setBorderColor(BaseColor.BLACK);

				PdfPCell cell124 = new PdfPCell(new Paragraph("" + form.getMotherTongue(), mainContent));

				cell124.setPaddingBottom(5);
				cell124.setColspan(1);
				cell124.setBorderColor(BaseColor.BLACK);

				// Instruction
				PdfPCell cell194 = new PdfPCell(new Paragraph("Religion, Caste, Sub Caste", mainContent1));

				cell194.setPaddingBottom(5);
				cell194.setColspan(1);
				cell194.setBorderColor(BaseColor.BLACK);

				PdfPCell cell125 = new PdfPCell(new Paragraph("Religion: " + form.getReligion(), mainContent));

				cell125.setPaddingBottom(5);
				cell125.setColspan(1);
				cell125.setBorderColor(BaseColor.BLACK);

				PdfPCell cell1251 = new PdfPCell(new Paragraph("Caste: " + form.getCaste(), mainContent));

				cell1251.setPaddingBottom(5);
				cell1251.setColspan(1);
				cell1251.setBorderColor(BaseColor.BLACK);

				PdfPCell cell1252 = new PdfPCell(new Paragraph("Sub Caste: " + form.getSubCaste(), mainContent));

				cell1252.setPaddingBottom(5);
				cell1252.setColspan(1);
				cell1252.setBorderColor(BaseColor.BLACK);

				// Instruction
				PdfPCell cell195 = new PdfPCell(new Paragraph(
						"Whether the candidate belongs to Schedule Caste or Schedule Tribe", mainContent1));

				cell195.setPaddingBottom(5);
				cell195.setColspan(1);
				cell195.setBorderColor(BaseColor.BLACK);

				PdfPCell cell126 = new PdfPCell(new Paragraph("" + form.getCategory(), mainContent));

				cell126.setPaddingBottom(5);
				cell126.setColspan(3);
				cell126.setBorderColor(BaseColor.BLACK);

				// Instruction
				PdfPCell cell196 = new PdfPCell(new Paragraph("Last school attended", mainContent1));

				cell196.setPaddingBottom(5);
				cell196.setColspan(1);
				cell196.setBorderColor(BaseColor.BLACK);

				PdfPCell cell127 = new PdfPCell(new Paragraph("" + form.getLastSchoolAttended(), mainContent));

				cell127.setPaddingBottom(5);
				cell127.setColspan(3);
				cell127.setBorderColor(BaseColor.BLACK);

				// Instruction
				PdfPCell cell1961 = new PdfPCell(
						new Paragraph("Date of admission in the school & Class", mainContent1));

				cell1961.setPaddingBottom(5);
				cell1961.setColspan(1);
				cell1961.setBorderColor(BaseColor.BLACK);

				String AdmissionDate = form.getAdmissionDate();

				PdfPTable table51 = new PdfPTable(10);
				table51.setWidthPercentage(100);
				table51.setWidths(new int[] { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 });

				try {

					PdfPCell cell1210010 = new PdfPCell(new Paragraph("" + AdmissionDate.charAt(0), mainContent));

					cell1210010.setPaddingBottom(5);
					cell1210010.setBorderColor(BaseColor.BLACK);
					cell1210010.setHorizontalAlignment(Element.ALIGN_CENTER);

					table51.addCell(cell1210010);
				} catch (Exception e) {

					PdfPCell cell1210010 = new PdfPCell(new Paragraph("", mainContent));

					cell1210010.setPaddingBottom(5);
					cell1210010.setBorderColor(BaseColor.BLACK);

					table51.addCell(cell1210010);
				}

				try {

					PdfPCell cell121101 = new PdfPCell(new Paragraph("" + AdmissionDate.charAt(1), mainContent));

					cell121101.setPaddingBottom(5);
					cell121101.setBorderColor(BaseColor.BLACK);
					cell121101.setHorizontalAlignment(Element.ALIGN_CENTER);

					table51.addCell(cell121101);
				} catch (Exception e) {

					PdfPCell cell121101 = new PdfPCell(new Paragraph("", mainContent));

					cell121101.setPaddingBottom(5);
					cell121101.setBorderColor(BaseColor.BLACK);

					table51.addCell(cell121101);
				}

				try {

					PdfPCell cell1211011 = new PdfPCell(new Paragraph("" + AdmissionDate.charAt(2), mainContent));

					cell1211011.setPaddingBottom(5);
					cell1211011.setBorderColor(BaseColor.BLACK);
					cell1211011.setHorizontalAlignment(Element.ALIGN_CENTER);

					table51.addCell(cell1211011);
				} catch (Exception e) {

					PdfPCell cell1211011 = new PdfPCell(new Paragraph("", mainContent));

					cell1211011.setPaddingBottom(5);
					cell1211011.setBorderColor(BaseColor.BLACK);

					table51.addCell(cell1211011);
				}

				try {

					PdfPCell cell12121 = new PdfPCell(new Paragraph("" + AdmissionDate.charAt(3), mainContent));

					cell12121.setPaddingBottom(5);
					cell12121.setBorderColor(BaseColor.BLACK);
					cell12121.setHorizontalAlignment(Element.ALIGN_CENTER);

					table51.addCell(cell12121);
				} catch (Exception e) {

					PdfPCell cell12121 = new PdfPCell(new Paragraph("", mainContent));

					cell12121.setPaddingBottom(5);
					cell12121.setBorderColor(BaseColor.BLACK);

					table51.addCell(cell12121);
				}

				try {

					PdfPCell cell12131 = new PdfPCell(new Paragraph("" + AdmissionDate.charAt(4), mainContent));

					cell12131.setPaddingBottom(5);
					cell12131.setBorderColor(BaseColor.BLACK);
					cell12131.setHorizontalAlignment(Element.ALIGN_CENTER);

					table51.addCell(cell12131);
				} catch (Exception e) {

					PdfPCell cell12131 = new PdfPCell(new Paragraph("", mainContent));

					cell12131.setPaddingBottom(5);
					cell12131.setBorderColor(BaseColor.BLACK);

					table51.addCell(cell12131);
				}

				try {

					PdfPCell cell121321 = new PdfPCell(new Paragraph("" + AdmissionDate.charAt(5), mainContent));

					cell121321.setPaddingBottom(5);
					cell121321.setBorderColor(BaseColor.BLACK);
					cell121321.setHorizontalAlignment(Element.ALIGN_CENTER);

					table51.addCell(cell121321);
				} catch (Exception e) {

					PdfPCell cell121321 = new PdfPCell(new Paragraph("", mainContent));

					cell121321.setPaddingBottom(5);
					cell121321.setBorderColor(BaseColor.BLACK);

					table51.addCell(cell121321);
				}

				try {

					PdfPCell cell12141 = new PdfPCell(new Paragraph("" + AdmissionDate.charAt(6), mainContent));

					cell12141.setPaddingBottom(5);
					cell12141.setBorderColor(BaseColor.BLACK);
					cell12141.setHorizontalAlignment(Element.ALIGN_CENTER);

					table51.addCell(cell12141);
				} catch (Exception e) {

					PdfPCell cell12141 = new PdfPCell(new Paragraph("", mainContent));

					cell12141.setPaddingBottom(5);
					cell12141.setBorderColor(BaseColor.BLACK);

					table51.addCell(cell12141);
				}

				try {

					PdfPCell cell12151 = new PdfPCell(new Paragraph("" + AdmissionDate.charAt(7), mainContent));

					cell12151.setPaddingBottom(5);
					cell12151.setBorderColor(BaseColor.BLACK);
					cell12151.setHorizontalAlignment(Element.ALIGN_CENTER);

					table51.addCell(cell12151);
				} catch (Exception e) {

					PdfPCell cell12151 = new PdfPCell(new Paragraph("", mainContent));

					cell12151.setPaddingBottom(5);
					cell12151.setBorderColor(BaseColor.BLACK);

					table51.addCell(cell12151);
				}

				try {

					PdfPCell cell12161 = new PdfPCell(new Paragraph("" + AdmissionDate.charAt(8), mainContent));

					cell12161.setPaddingBottom(5);
					cell12161.setBorderColor(BaseColor.BLACK);
					cell12161.setHorizontalAlignment(Element.ALIGN_CENTER);

					table51.addCell(cell12161);
				} catch (Exception e) {
					PdfPCell cell12161 = new PdfPCell(new Paragraph("", mainContent));

					cell12161.setPaddingBottom(5);
					cell12161.setBorderColor(BaseColor.BLACK);

					table51.addCell(cell12161);
				}

				try {

					PdfPCell cell121711 = new PdfPCell(new Paragraph("" + AdmissionDate.charAt(9), mainContent));

					cell121711.setPaddingBottom(5);
					cell121711.setBorderColor(BaseColor.BLACK);
					cell121711.setHorizontalAlignment(Element.ALIGN_CENTER);

					table51.addCell(cell121711);
				} catch (Exception e) {

					PdfPCell cell121711 = new PdfPCell(new Paragraph("", mainContent));

					cell121711.setPaddingBottom(5);
					cell121711.setBorderColor(BaseColor.BLACK);

					table51.addCell(cell121711);
				}

				PdfPCell cell1210011 = new PdfPCell(new Paragraph("(dd/MM/yyyy)", mainContent));

				cell1210011.setColspan(10);
				cell1210011.setBorderColor(BaseColor.BLACK);

				table51.addCell(cell1210011);

				PdfPCell cell1271 = new PdfPCell(table51);

				cell1271.setColspan(1);
				cell1271.setBorderColor(BaseColor.BLACK);

				PdfPCell cell12710 = new PdfPCell(new Paragraph("Class: " + form.getFirstClass(), mainContent));

				cell12710.setPaddingBottom(5);
				cell12710.setColspan(2);
				cell12710.setBorderColor(BaseColor.BLACK);

				// Instruction
				PdfPCell cell1962 = new PdfPCell(new Paragraph("Class in which the pupil last studied", mainContent1));

				cell1962.setPaddingBottom(5);
				cell1962.setColspan(1);
				cell1962.setBorderColor(BaseColor.BLACK);

				PdfPCell cell1272 = new PdfPCell(
						new Paragraph("In Figures: " + form.getLastStudiedClass(), mainContent));

				cell1272.setPaddingBottom(5);
				cell1272.setColspan(1);
				cell1272.setBorderColor(BaseColor.BLACK);

				PdfPCell cell12720 = new PdfPCell(
						new Paragraph("In Words: " + form.getLastStudiedClassWords(), mainContent));

				cell12720.setPaddingBottom(5);
				cell12720.setColspan(2);
				cell12720.setBorderColor(BaseColor.BLACK);

				// Instruction
				PdfPCell cell1963 = new PdfPCell(
						new Paragraph("School/Board annual examination last taken with result", mainContent1));

				cell1963.setPaddingBottom(5);
				cell1963.setColspan(1);
				cell1963.setBorderColor(BaseColor.BLACK);

				PdfPCell cell1273 = new PdfPCell(new Paragraph("" + form.getResult(), mainContent));

				cell1273.setPaddingBottom(5);
				cell1273.setColspan(3);
				cell1273.setBorderColor(BaseColor.BLACK);

				// Instruction
				PdfPCell cell1964 = new PdfPCell(new Paragraph("Subjects", mainContent1));

				cell1964.setPaddingBottom(5);
				cell1964.setColspan(1);
				cell1964.setBorderColor(BaseColor.BLACK);

				PdfPCell cell1274 = new PdfPCell(new Paragraph("" + form.getSubject(), mainContent));

				cell1274.setPaddingBottom(5);
				cell1274.setColspan(3);
				cell1274.setBorderColor(BaseColor.BLACK);

				// Instruction
				PdfPCell cell1965 = new PdfPCell(
						new Paragraph("Whether qualified for promotion to the higher class", mainContent1));

				cell1965.setPaddingBottom(5);
				cell1965.setColspan(1);
				cell1965.setBorderColor(BaseColor.BLACK);

				PdfPCell cell1275 = new PdfPCell(new Paragraph("" + form.getHigherClass(), mainContent));

				cell1275.setPaddingBottom(5);
				cell1275.setColspan(1);
				cell1275.setBorderColor(BaseColor.BLACK);

				PdfPCell cell12750 = new PdfPCell(
						new Paragraph("Which class in Figure: " + form.getWichClass(), mainContent));

				cell12750.setPaddingBottom(5);
				cell12750.setColspan(1);
				cell12750.setBorderColor(BaseColor.BLACK);

				PdfPCell cell12751 = new PdfPCell(new Paragraph("In Words: " + form.getWichClassWords(), mainContent));

				cell12751.setPaddingBottom(5);
				cell12751.setColspan(1);
				cell12751.setBorderColor(BaseColor.BLACK);

				// Instruction
				PdfPCell cell1966 = new PdfPCell(
						new Paragraph("Month upto which the pupil has paid school dues", mainContent1));

				cell1966.setPaddingBottom(5);
				cell1966.setColspan(1);
				cell1966.setBorderColor(BaseColor.BLACK);

				PdfPCell cell1276 = new PdfPCell(new Paragraph("" + form.getDuesMonths(), mainContent));

				cell1276.setPaddingBottom(5);
				cell1276.setColspan(3);
				cell1276.setBorderColor(BaseColor.BLACK);

				// Instruction
				PdfPCell cell1967 = new PdfPCell(new Paragraph("Any fee concession availed of.", mainContent1));

				cell1967.setPaddingBottom(5);
				cell1967.setColspan(1);
				cell1967.setBorderColor(BaseColor.BLACK);

				PdfPCell cell1277 = new PdfPCell(new Paragraph("" + form.getFeeConcession(), mainContent));

				cell1277.setPaddingBottom(5);
				cell1277.setColspan(3);
				cell1277.setBorderColor(BaseColor.BLACK);

				// Instruction
				PdfPCell cell1968 = new PdfPCell(
						new Paragraph("Total No. of working days in the academic session", mainContent1));

				cell1968.setPaddingBottom(5);
				cell1968.setColspan(1);
				cell1968.setBorderColor(BaseColor.BLACK);

				PdfPCell cell1278 = new PdfPCell(new Paragraph("" + form.getWorkingDays(), mainContent));

				cell1278.setPaddingBottom(5);
				cell1278.setColspan(1);
				cell1278.setBorderColor(BaseColor.BLACK);

				// Instruction
				PdfPCell cell199 = new PdfPCell(
						new Paragraph("Total No. of working days pupil present in the school", mainContent1));

				cell199.setPaddingBottom(5);
				cell199.setColspan(1);
				cell199.setBorderColor(BaseColor.BLACK);

				PdfPCell cell1279 = new PdfPCell(new Paragraph("" + form.getPresentDays(), mainContent));

				cell1279.setPaddingBottom(5);
				cell1279.setColspan(1);
				cell1279.setBorderColor(BaseColor.BLACK);

				// Instruction
				PdfPCell cell19660 = new PdfPCell(
						new Paragraph("Whether NCC Cadet/Boy Scout/Girl guide", mainContent1));

				cell19660.setPaddingBottom(5);
				cell19660.setColspan(1);
				cell19660.setBorderColor(BaseColor.BLACK);

				PdfPCell cell12760 = new PdfPCell(new Paragraph("" + form.getNccGuide(), mainContent));

				cell12760.setPaddingBottom(5);
				cell12760.setColspan(3);
				cell12760.setBorderColor(BaseColor.BLACK);

				// Instruction
				PdfPCell cell19670 = new PdfPCell(
						new Paragraph("Games played or extracurricular activities in which the pupil usually took part",
								mainContent1));

				cell19670.setPaddingBottom(5);
				cell19670.setColspan(1);
				cell19670.setBorderColor(BaseColor.BLACK);

				PdfPCell cell12770 = new PdfPCell(new Paragraph("" + form.getPhysicalActivity(), mainContent));

				cell12770.setPaddingBottom(5);
				cell12770.setColspan(3);
				cell12770.setBorderColor(BaseColor.BLACK);

				// Instruction
				PdfPCell cell19680 = new PdfPCell(new Paragraph("General conduct", mainContent1));

				cell19680.setPaddingBottom(5);
				cell19680.setColspan(1);
				cell19680.setBorderColor(BaseColor.BLACK);

				PdfPCell cell12780 = new PdfPCell(new Paragraph("" + form.getGeneralConduct(), mainContent));

				cell12780.setPaddingBottom(5);
				cell12780.setColspan(1);
				cell12780.setBorderColor(BaseColor.BLACK);

				PdfPCell cell127801 = new PdfPCell(new Paragraph("Academic progress", mainContent1));

				cell127801.setPaddingBottom(5);
				cell127801.setColspan(1);
				cell127801.setBorderColor(BaseColor.BLACK);

				PdfPCell cell1278011 = new PdfPCell(new Paragraph("" + form.getAcademicProgress(), mainContent));

				cell1278011.setPaddingBottom(5);
				cell1278011.setColspan(1);
				cell1278011.setBorderColor(BaseColor.BLACK);

				// Instruction
				PdfPCell cell1990 = new PdfPCell(new Paragraph("Date of issue of certificate", mainContent1));

				cell1990.setPaddingBottom(5);
				cell1990.setColspan(1);
				cell1990.setBorderColor(BaseColor.BLACK);

				String DateOfapplication = form.getDateOfapplication();

				PdfPTable table52 = new PdfPTable(10);
				table52.setWidthPercentage(100);
				table52.setWidths(new int[] { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 });

				try {

					PdfPCell cell12100101 = new PdfPCell(new Paragraph("" + DateOfapplication.charAt(0), mainContent));

					cell12100101.setPaddingBottom(5);
					cell12100101.setBorderColor(BaseColor.BLACK);
					cell12100101.setHorizontalAlignment(Element.ALIGN_CENTER);

					table52.addCell(cell12100101);
				} catch (Exception e) {

					PdfPCell cell12100101 = new PdfPCell(new Paragraph("", mainContent));

					cell12100101.setPaddingBottom(5);
					cell12100101.setBorderColor(BaseColor.BLACK);

					table52.addCell(cell12100101);
				}

				try {

					PdfPCell cell1211013 = new PdfPCell(new Paragraph("" + DateOfapplication.charAt(1), mainContent));

					cell1211013.setPaddingBottom(5);
					cell1211013.setBorderColor(BaseColor.BLACK);
					cell1211013.setHorizontalAlignment(Element.ALIGN_CENTER);

					table52.addCell(cell1211013);
				} catch (Exception e) {

					PdfPCell cell1211013 = new PdfPCell(new Paragraph("", mainContent));

					cell1211013.setPaddingBottom(5);
					cell1211013.setBorderColor(BaseColor.BLACK);

					table52.addCell(cell1211013);
				}

				try {

					PdfPCell cell12110113 = new PdfPCell(new Paragraph("" + DateOfapplication.charAt(2), mainContent));

					cell12110113.setPaddingBottom(5);
					cell12110113.setBorderColor(BaseColor.BLACK);
					cell12110113.setHorizontalAlignment(Element.ALIGN_CENTER);

					table52.addCell(cell12110113);
				} catch (Exception e) {

					PdfPCell cell12110113 = new PdfPCell(new Paragraph("", mainContent));

					cell12110113.setPaddingBottom(5);
					cell12110113.setBorderColor(BaseColor.BLACK);

					table52.addCell(cell12110113);
				}

				try {

					PdfPCell cell121213 = new PdfPCell(new Paragraph("" + DateOfapplication.charAt(3), mainContent));

					cell121213.setPaddingBottom(5);
					cell121213.setBorderColor(BaseColor.BLACK);
					cell121213.setHorizontalAlignment(Element.ALIGN_CENTER);

					table52.addCell(cell121213);
				} catch (Exception e) {

					PdfPCell cell121213 = new PdfPCell(new Paragraph("", mainContent));

					cell121213.setPaddingBottom(5);
					cell121213.setBorderColor(BaseColor.BLACK);

					table52.addCell(cell121213);
				}

				try {

					PdfPCell cell121313 = new PdfPCell(new Paragraph("" + DateOfapplication.charAt(4), mainContent));

					cell121313.setPaddingBottom(5);
					cell121313.setBorderColor(BaseColor.BLACK);
					cell121313.setHorizontalAlignment(Element.ALIGN_CENTER);

					table52.addCell(cell121313);
				} catch (Exception e) {

					PdfPCell cell121313 = new PdfPCell(new Paragraph("", mainContent));

					cell121313.setPaddingBottom(5);
					cell121313.setBorderColor(BaseColor.BLACK);

					table52.addCell(cell121313);
				}

				try {

					PdfPCell cell1213213 = new PdfPCell(new Paragraph("" + DateOfapplication.charAt(5), mainContent));

					cell1213213.setPaddingBottom(5);
					cell1213213.setBorderColor(BaseColor.BLACK);
					cell1213213.setHorizontalAlignment(Element.ALIGN_CENTER);

					table52.addCell(cell1213213);
				} catch (Exception e) {

					PdfPCell cell1213213 = new PdfPCell(new Paragraph("", mainContent));

					cell1213213.setPaddingBottom(5);
					cell1213213.setBorderColor(BaseColor.BLACK);

					table52.addCell(cell1213213);
				}

				try {

					PdfPCell cell121413 = new PdfPCell(new Paragraph("" + DateOfapplication.charAt(6), mainContent));

					cell121413.setPaddingBottom(5);
					cell121413.setBorderColor(BaseColor.BLACK);
					cell121413.setHorizontalAlignment(Element.ALIGN_CENTER);

					table52.addCell(cell121413);
				} catch (Exception e) {

					PdfPCell cell121413 = new PdfPCell(new Paragraph("", mainContent));

					cell121413.setPaddingBottom(5);
					cell121413.setBorderColor(BaseColor.BLACK);

					table52.addCell(cell121413);
				}

				try {

					PdfPCell cell121513 = new PdfPCell(new Paragraph("" + DateOfapplication.charAt(7), mainContent));

					cell121513.setPaddingBottom(5);
					cell121513.setBorderColor(BaseColor.BLACK);
					cell121513.setHorizontalAlignment(Element.ALIGN_CENTER);

					table52.addCell(cell121513);
				} catch (Exception e) {

					PdfPCell cell121513 = new PdfPCell(new Paragraph("", mainContent));

					cell121513.setPaddingBottom(5);
					cell121513.setBorderColor(BaseColor.BLACK);

					table52.addCell(cell121513);
				}

				try {

					PdfPCell cell121613 = new PdfPCell(new Paragraph("" + DateOfapplication.charAt(8), mainContent));

					cell121613.setPaddingBottom(5);
					cell121613.setBorderColor(BaseColor.BLACK);
					cell121613.setHorizontalAlignment(Element.ALIGN_CENTER);

					table52.addCell(cell121613);
				} catch (Exception e) {
					PdfPCell cell121613 = new PdfPCell(new Paragraph("", mainContent));

					cell121613.setPaddingBottom(5);
					cell121613.setBorderColor(BaseColor.BLACK);

					table52.addCell(cell121613);
				}

				try {

					PdfPCell cell121713 = new PdfPCell(new Paragraph("" + DateOfapplication.charAt(9), mainContent));

					cell121713.setPaddingBottom(5);
					cell121713.setBorderColor(BaseColor.BLACK);
					cell121713.setHorizontalAlignment(Element.ALIGN_CENTER);

					table52.addCell(cell121713);
				} catch (Exception e) {

					PdfPCell cell121713 = new PdfPCell(new Paragraph("", mainContent));

					cell121713.setPaddingBottom(5);
					cell121713.setBorderColor(BaseColor.BLACK);

					table52.addCell(cell121713);
				}

				PdfPCell cell1210010123 = new PdfPCell(new Paragraph("(dd/MM/yyyy)", mainContent));

				cell1210010123.setColspan(10);
				cell1210010123.setBorderColor(BaseColor.BLACK);

				table52.addCell(cell1210010123);

				PdfPCell cell12790 = new PdfPCell(table52);

				cell12790.setColspan(3);
				cell12790.setBorderColor(BaseColor.BLACK);

				// Instruction
				PdfPCell cell1991 = new PdfPCell(new Paragraph("Reasons for leaving the school", mainContent1));

				cell1991.setPaddingBottom(5);
				cell1991.setColspan(1);
				cell1991.setBorderColor(BaseColor.BLACK);

				PdfPCell cell12791 = new PdfPCell(new Paragraph("" + form.getReasons(), mainContent));

				cell12791.setPaddingBottom(5);
				cell12791.setColspan(3);
				cell12791.setBorderColor(BaseColor.BLACK);

				// Instruction
				PdfPCell cell1992 = new PdfPCell(new Paragraph("Any other remarks", mainContent1));

				cell1992.setBorderWidth(0.1f);
				cell1992.setPaddingBottom(5);
				cell1992.setColspan(1);
				cell1992.setBorderColor(BaseColor.BLACK);
				cell1992.setBorderColorBottom(BaseColor.BLACK);

				PdfPCell cell12792 = new PdfPCell(new Paragraph("" + form.getRemarks(), mainContent));

				cell12792.setBorderWidth(0.1f);
				cell12792.setPaddingBottom(5);
				cell12792.setColspan(3);
				cell12792.setBorderColor(BaseColor.BLACK);
				cell12792.setBorderColorBottom(BaseColor.BLACK);

				/*
				 * // Instruction PdfPCell cell1993 = new PdfPCell(new Paragraph(
				 * "Note: Certified that the above information is in accordance with the school register"
				 * , mainContent));
				 * 
				 * cell1993.setBorderWidth(2f); cell1993.setPaddingTop(5);
				 * cell1993.setPaddingBottom(1); cell1993.setColspan(4);
				 * cell1993.setBorderColor(BaseColor.WHITE);
				 * cell1993.setBorderColorTop(BaseColor.BLACK);
				 */

				// Paragraph notePara = new Paragraph("Note: Certified that the above
				// information is in accordance with the school register");

				table3.addCell(cell1);
				table3.addCell(cell198);
				table3.addCell(cell101);
				table3.addCell(cell1980);
				table3.addCell(cell012);
				table3.addCell(cell13);
				table3.addCell(cell14);
				table3.addCell(cell15);
				table3.addCell(cell17);
				table3.addCell(cell18);
				table3.addCell(cell109);
				table3.addCell(cell102);
				table3.addCell(cell190);
				table3.addCell(cell120);
				table3.addCell(cell19);
				table3.addCell(cell12);
				table3.addCell(cell191);
				table3.addCell(cell121);
				// table3.addCell(cell11921);
				table3.addCell(cell119);
				table3.addCell(cell122);
				table3.addCell(cell192);
				table3.addCell(cell123);
				table3.addCell(cell193);
				table3.addCell(cell124);
				table3.addCell(cell194);
				table3.addCell(cell125);
				table3.addCell(cell1251);
				table3.addCell(cell1252);
				table3.addCell(cell195);
				table3.addCell(cell126);
				table3.addCell(cell196);
				table3.addCell(cell127);
				table3.addCell(cell1961);
				table3.addCell(cell1271);
				table3.addCell(cell12710);
				table3.addCell(cell1962);
				table3.addCell(cell1272);
				table3.addCell(cell12720);
				table3.addCell(cell1963);
				table3.addCell(cell1273);
				table3.addCell(cell1964);
				table3.addCell(cell1274);
				table3.addCell(cell1965);
				table3.addCell(cell1275);
				table3.addCell(cell12750);
				table3.addCell(cell12751);
				table3.addCell(cell1966);
				table3.addCell(cell1276);
				table3.addCell(cell1967);
				table3.addCell(cell1277);
				table3.addCell(cell1968);
				table3.addCell(cell1278);
				table3.addCell(cell199);
				table3.addCell(cell1279);
				table3.addCell(cell19660);
				table3.addCell(cell12760);
				table3.addCell(cell19670);
				table3.addCell(cell12770);
				table3.addCell(cell19680);
				table3.addCell(cell12780);
				table3.addCell(cell127801);
				table3.addCell(cell1278011);
				table3.addCell(cell1990);
				table3.addCell(cell12790);
				table3.addCell(cell1991);
				table3.addCell(cell12791);
				table3.addCell(cell1992);
				table3.addCell(cell12792);

				// table3.addCell(cell1993);

				document.add(table3);

				// document.add(notePara);

			}

			PdfPTable table1 = new PdfPTable(3);
			// table1.setTotalWidth(510);
			table1.setWidthPercentage(100);
			Rectangle rect5 = new Rectangle(270, 700);
			table1.setWidthPercentage(new float[] { 90, 90, 90 }, rect5);

			PdfPCell cell1110 = new PdfPCell(new Paragraph(
					"Note: Certified that the above information is in accordance with the school register", Font3));
			cell1110.setBackgroundColor(BaseColor.WHITE);
			// cell111.setPaddingTop(20);
			cell1110.setColspan(3);
			cell1110.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell1110.setBorderColor(BaseColor.WHITE);
			cell1110.setBorderColorTop(BaseColor.BLACK);
			cell1110.setBorderWidthTop(1f);

			PdfPCell cell111 = new PdfPCell(new Paragraph("\n\nSignature of Class Teacher", Font5));
			cell111.setBackgroundColor(BaseColor.WHITE);
			// cell111.setPaddingTop(20);
			cell111.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell111.setBorderColor(BaseColor.WHITE);

			PdfPCell cell1111 = new PdfPCell(new Paragraph("\n\nChecked by", Font5));
			cell1111.setBackgroundColor(BaseColor.WHITE);
			// cell111.setPaddingTop(20);
			cell1111.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1111.setBorderColor(BaseColor.WHITE);

			PdfPCell cell11111 = new PdfPCell(
					new Paragraph("\n\nSignature of Principal \n with date & School seal", Font5));
			cell11111.setBackgroundColor(BaseColor.WHITE);
			// cell111.setPaddingTop(20);
			cell11111.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell11111.setBorderColor(BaseColor.WHITE);

			table1.addCell(cell1110);
			table1.addCell(cell111);
			table1.addCell(cell1111);
			table1.addCell(cell11111);

			document.add(table1);

			/*
			 * FooterTable1 event = new FooterTable1(table1); writer.setPageEvent(event);
			 */

			document.close();

			System.out.println("Successfully written and generated Student's Leaving Certificate PDF Report");

			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();
			status = "error";
		}
		return status;

	}

	public String convertToLeavingCertificatePDFALL(int ayClassID, int organizationID, String realPath,
			String pdfFIleName) {
		// int count = 1;

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		List<ConfigurationForm> studentDetailsList = null;

		int studentID = 0;
		int registrationID = 0;

		try {

			/*
			 * Image path for posterior segment images
			 */
			String boardLogoImage = daoInf1.retrieveBoardLogo(organizationID);

			String organisationImage = daoInf1.retrieveOrganizationLogo(organizationID);

			/*
			 * Setting path to store PDF file
			 */

			File file = new File(realPath + "/" + pdfFIleName);
			/*
			 * Creating Document for PDF
			 */
			Document document = null;

			document = new Document(PageSize.LEGAL);

			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));

			Font Font1 = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.NORMAL);
			Font Font3 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
			Font Font2 = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);
			Font Font4 = new Font(Font.FontFamily.TIMES_ROMAN, 19, Font.BOLD);
			Font Font5 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
			Font mainContent = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL);
			Font mainContent1 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);

			document.open();

			Image image1 = Image.getInstance(boardLogoImage);

			Image image2 = Image.getInstance(organisationImage);

			image2.scaleToFit(50, 50);

			String StdDivName = daoInf2.retrieveStdDivName(studentID);

			connection = getConnection();

			String retrieveStudentIDBYStandardAndDivisionQuery = QueryMaker.RETRIEVE_StudentID_List_For_LeavingCertificate;

			preparedStatement = connection.prepareStatement(retrieveStudentIDBYStandardAndDivisionQuery);

			preparedStatement.setInt(1, ayClassID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			System.out.println("retrieveStudentIDBYStandardAndDivisionQuery: "
					+ retrieveStudentIDBYStandardAndDivisionQuery + "....ayClassID: " + ayClassID);

			while (resultSet.next()) {

				System.out.println("studentID: " + studentID);
				studentID = resultSet.getInt("studentID");

				registrationID = resultSet.getInt("id");

				studentDetailsList = daoInf2.retrieveStudentDetailsFromLeavingCertificate(studentID, registrationID,
						ayClassID);
				/*
				 * Setting header
				 */
				document.addCreator("KovidRMS");
				document.addTitle("Student Leaving Certificate PDF");

				PdfPTable table = new PdfPTable(3);

				table.setFooterRows(1);
				table.setWidthPercentage(100);
				Rectangle rect = new Rectangle(270, 700);
				table.setWidthPercentage(new float[] { 25, 220, 35 }, rect);

				PdfPCell cell = new PdfPCell(image1, true);
				cell.setPaddingBottom(5);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setUseBorderPadding(true);
				cell.setBorderWidthBottom(1f);
				cell.setBorderColor(BaseColor.WHITE);

				PdfPCell cell2 = new PdfPCell(new Paragraph(daoInf1.retrieveOrganizationTagLine(organizationID) + "\n"
						+ daoInf1.retrieveOrganizationName(organizationID) + "\n"
						+ daoInf1.retrieveOrganizationAddress(organizationID) + "\n"
						+ daoInf1.retrieveOrganizationPhone(organizationID) + " Email: "
						+ daoInf1.retrieveOrganizationEmail(organizationID), Font5));

				cell2.setBorderWidth(0.01f);
				cell2.setPaddingBottom(5);
				cell2.setBorderWidthLeft(0.2f);
				cell2.setBorderColor(BaseColor.WHITE);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);

				PdfPCell cell3 = new PdfPCell(image2, true);
				cell3.setPaddingBottom(5);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setUseBorderPadding(true);
				cell3.setBorderWidthBottom(1f);
				cell3.setBorderColor(BaseColor.WHITE);

				PdfPCell cell4 = new PdfPCell(new Paragraph());
				cell4.setColspan(3);
				cell4.setBorderWidthBottom(0f);
				cell4.setBorderWidthLeft(0f);
				cell4.setBorderWidthRight(0f);
				cell4.setBorderWidthTop(1f);
				cell4.setBorderColor(BaseColor.DARK_GRAY);

				/*
				 * adding all cell to the table to create tabular structure
				 */

				table.addCell(cell);
				// table.addCell(cell1);
				table.addCell(cell2);
				table.addCell(cell3);
				table.addCell(cell4);

				document.add(table);

				/*
				 * for student details
				 */
				PdfPTable table2 = new PdfPTable(3);
				table2.setWidthPercentage(100);
				Rectangle rect1 = new Rectangle(270, 700);
				table2.setWidthPercentage(new float[] { 110, 90, 70 }, rect1);

				// for Title
				PdfPCell cell0 = new PdfPCell(new Paragraph("U-DISE No.27251400611", mainContent1));

				cell0.setPaddingTop(5);
				cell0.setPaddingBottom(5);
				cell0.setUseBorderPadding(true);
				cell0.setBorderColor(BaseColor.WHITE);

				// OD RE
				PdfPCell cell5 = new PdfPCell(new Paragraph("CBSC Affiliation No.1130690", mainContent1));

				cell5.setBorderWidth(0.01f);
				cell5.setPaddingBottom(5);
				cell5.setPaddingTop(5);
				cell5.setBorderColor(BaseColor.WHITE);

				// Os LE
				PdfPCell cell6 = new PdfPCell(new Paragraph("School Code.30596", mainContent1));

				cell6.setBorderWidth(0.01f);
				cell6.setPaddingBottom(5);
				cell6.setPaddingTop(5);
				cell6.setBorderColor(BaseColor.WHITE);

				// Os LE
				PdfPCell cell11 = new PdfPCell(new Paragraph("School Leaving Certificate", Font4));

				cell11.setBorderWidth(0.01f);
				cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell11.setPaddingBottom(5);
				cell11.setPaddingTop(5);
				cell11.setColspan(4);
				cell11.setBorderColor(BaseColor.WHITE);

				// Os LE
				PdfPCell cell112 = new PdfPCell(new Paragraph(
						"(No change in any entry in this certificate is to be made except by the authority issuing it and any infringement liable to involve the imposition of penalty.)",
						mainContent));

				cell112.setBorderWidth(0.01f);
				cell112.setPaddingBottom(5);
				cell112.setPaddingTop(5);
				cell112.setColspan(4);
				cell112.setBorderColor(BaseColor.WHITE);

				table2.addCell(cell0);
				table2.addCell(cell5);
				table2.addCell(cell6);
				table2.addCell(cell11);
				table2.addCell(cell112);

				document.add(table2);

				for (ConfigurationForm form : studentDetailsList) {

					/*
					 * For scholastic
					 */
					PdfPTable table3 = new PdfPTable(4);
					table3.setWidthPercentage(100);
					Rectangle rect2 = new Rectangle(270, 700);
					table3.setWidthPercentage(new float[] { 100, 50, 70, 50 }, rect2);

					PdfPCell cell1 = new PdfPCell(new Paragraph("Sr No.", mainContent1));

					cell1.setPaddingBottom(5);
					cell1.setColspan(1);
					cell1.setBorderColor(BaseColor.BLACK);

					// Gene
					PdfPCell cell198 = new PdfPCell(new Paragraph("" + form.getSerialNo(), mainContent));

					cell198.setPaddingBottom(10);
					cell198.setColspan(1);
					cell198.setBorderColor(BaseColor.BLACK);

					PdfPCell cell101 = new PdfPCell(new Paragraph("Book No.", mainContent1));

					cell101.setPaddingBottom(5);
					cell101.setColspan(1);
					cell101.setBorderColor(BaseColor.BLACK);

					// Gene
					PdfPCell cell1980 = new PdfPCell(new Paragraph("" + form.getBookNo(), mainContent));

					cell1980.setPaddingBottom(10);
					cell1980.setColspan(1);
					cell1980.setBorderColor(BaseColor.BLACK);

					// For blank space
					PdfPCell cell012 = new PdfPCell(new Paragraph("Student ID.", mainContent1));

					cell012.setPaddingBottom(5);
					cell012.setColspan(1);
					cell012.setBorderColor(BaseColor.BLACK);

					PdfPTable table4 = new PdfPTable(19);
					table4.setWidthPercentage(100);
					table4.setWidths(new int[] { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 });

					String StudentNo = form.getStudentNo();

					try {

						PdfPCell cell130 = new PdfPCell(new Paragraph("" + StudentNo.charAt(0), mainContent));

						cell130.setPaddingBottom(5);
						cell130.setBorderColor(BaseColor.BLACK);
						cell130.setHorizontalAlignment(Element.ALIGN_CENTER);

						table4.addCell(cell130);

					} catch (Exception e) {

						PdfPCell cell130 = new PdfPCell(new Paragraph("", mainContent));

						cell130.setPaddingBottom(5);
						cell130.setBorderColor(BaseColor.BLACK);

						table4.addCell(cell130);

					}

					try {

						PdfPCell cell131 = new PdfPCell(new Paragraph("" + StudentNo.charAt(1), mainContent));

						cell131.setPaddingBottom(5);
						cell131.setBorderColor(BaseColor.BLACK);
						cell131.setHorizontalAlignment(Element.ALIGN_CENTER);

						table4.addCell(cell131);

					} catch (Exception e) {

						PdfPCell cell131 = new PdfPCell(new Paragraph("", mainContent));

						cell131.setPaddingBottom(5);
						cell131.setBorderColor(BaseColor.BLACK);

						table4.addCell(cell131);

					}

					try {
						PdfPCell cell132 = new PdfPCell(new Paragraph("" + StudentNo.charAt(2), mainContent));

						cell132.setPaddingBottom(5);
						cell132.setBorderColor(BaseColor.BLACK);
						cell132.setHorizontalAlignment(Element.ALIGN_CENTER);

						table4.addCell(cell132);

					} catch (Exception e) {

						PdfPCell cell132 = new PdfPCell(new Paragraph("", mainContent));

						cell132.setPaddingBottom(5);
						cell132.setBorderColor(BaseColor.BLACK);

						table4.addCell(cell132);
					}

					try {

						PdfPCell cell133 = new PdfPCell(new Paragraph("" + StudentNo.charAt(3), mainContent));

						cell133.setPaddingBottom(5);
						cell133.setBorderColor(BaseColor.BLACK);
						cell133.setHorizontalAlignment(Element.ALIGN_CENTER);

						table4.addCell(cell133);

					} catch (Exception e) {

						PdfPCell cell133 = new PdfPCell(new Paragraph("", mainContent));

						cell133.setPaddingBottom(5);
						cell133.setBorderColor(BaseColor.BLACK);

						table4.addCell(cell133);
					}

					try {

						PdfPCell cell134 = new PdfPCell(new Paragraph("" + StudentNo.charAt(4), mainContent));

						cell134.setPaddingBottom(5);
						cell134.setBorderColor(BaseColor.BLACK);
						cell134.setHorizontalAlignment(Element.ALIGN_CENTER);

						table4.addCell(cell134);

					} catch (Exception e) {

						PdfPCell cell134 = new PdfPCell(new Paragraph("", mainContent));

						cell134.setPaddingBottom(5);
						cell134.setBorderColor(BaseColor.BLACK);

						table4.addCell(cell134);
					}

					try {

						PdfPCell cell135 = new PdfPCell(new Paragraph("" + StudentNo.charAt(5), mainContent));

						cell135.setPaddingBottom(5);
						cell135.setBorderColor(BaseColor.BLACK);
						cell135.setHorizontalAlignment(Element.ALIGN_CENTER);

						table4.addCell(cell135);
					} catch (Exception e) {

						PdfPCell cell135 = new PdfPCell(new Paragraph("", mainContent));

						cell135.setPaddingBottom(5);
						cell135.setBorderColor(BaseColor.BLACK);

						table4.addCell(cell135);
					}

					try {

						PdfPCell cell136 = new PdfPCell(new Paragraph("" + StudentNo.charAt(6), mainContent));

						cell136.setPaddingBottom(5);
						cell136.setBorderColor(BaseColor.BLACK);
						cell136.setHorizontalAlignment(Element.ALIGN_CENTER);

						table4.addCell(cell136);
					} catch (Exception e) {

						PdfPCell cell136 = new PdfPCell(new Paragraph("", mainContent));

						cell136.setPaddingBottom(5);
						cell136.setBorderColor(BaseColor.BLACK);

						table4.addCell(cell136);
					}

					try {

						PdfPCell cell137 = new PdfPCell(new Paragraph("" + StudentNo.charAt(7), mainContent));

						cell137.setPaddingBottom(5);
						cell137.setBorderColor(BaseColor.BLACK);
						cell137.setHorizontalAlignment(Element.ALIGN_CENTER);

						table4.addCell(cell137);

					} catch (Exception e) {

						PdfPCell cell137 = new PdfPCell(new Paragraph("", mainContent));

						cell137.setPaddingBottom(5);
						cell137.setBorderColor(BaseColor.BLACK);

						table4.addCell(cell137);
					}

					try {

						PdfPCell cell138 = new PdfPCell(new Paragraph("" + StudentNo.charAt(8), mainContent));

						cell138.setPaddingBottom(5);
						cell138.setBorderColor(BaseColor.BLACK);
						cell138.setHorizontalAlignment(Element.ALIGN_CENTER);

						table4.addCell(cell138);
					} catch (Exception e) {

						PdfPCell cell138 = new PdfPCell(new Paragraph("", mainContent));

						cell138.setPaddingBottom(5);
						cell138.setBorderColor(BaseColor.BLACK);

						table4.addCell(cell138);
					}

					try {

						PdfPCell cell139 = new PdfPCell(new Paragraph("" + StudentNo.charAt(9), mainContent));

						cell139.setPaddingBottom(5);
						cell139.setBorderColor(BaseColor.BLACK);
						cell139.setHorizontalAlignment(Element.ALIGN_CENTER);

						table4.addCell(cell139);
					} catch (Exception e) {

						PdfPCell cell139 = new PdfPCell(new Paragraph("", mainContent));

						cell139.setPaddingBottom(5);
						cell139.setBorderColor(BaseColor.BLACK);

						table4.addCell(cell139);
					}

					try {

						PdfPCell cell1311 = new PdfPCell(new Paragraph("" + StudentNo.charAt(10), mainContent));

						cell1311.setPaddingBottom(5);
						cell1311.setBorderColor(BaseColor.BLACK);
						cell1311.setHorizontalAlignment(Element.ALIGN_CENTER);

						table4.addCell(cell1311);
					} catch (Exception e) {

						PdfPCell cell1311 = new PdfPCell(new Paragraph("", mainContent));

						cell1311.setPaddingBottom(5);
						cell1311.setBorderColor(BaseColor.BLACK);

						table4.addCell(cell1311);
					}

					try {

						PdfPCell cell1331 = new PdfPCell(new Paragraph("" + StudentNo.charAt(11), mainContent));

						cell1331.setPaddingBottom(5);
						cell1331.setBorderColor(BaseColor.BLACK);
						cell1331.setHorizontalAlignment(Element.ALIGN_CENTER);

						table4.addCell(cell1331);
					} catch (Exception e) {

						PdfPCell cell1331 = new PdfPCell(new Paragraph("", mainContent));

						cell1331.setPaddingBottom(5);
						cell1331.setBorderColor(BaseColor.BLACK);

						table4.addCell(cell1331);
					}

					try {

						PdfPCell cell1301 = new PdfPCell(new Paragraph("" + StudentNo.charAt(12), mainContent));

						cell1301.setPaddingBottom(5);
						cell1301.setBorderColor(BaseColor.BLACK);
						cell1301.setHorizontalAlignment(Element.ALIGN_CENTER);

						table4.addCell(cell1301);
					} catch (Exception e) {

						PdfPCell cell1301 = new PdfPCell(new Paragraph("", mainContent));

						cell1301.setPaddingBottom(5);
						cell1301.setBorderColor(BaseColor.BLACK);

						table4.addCell(cell1301);
					}

					try {

						PdfPCell cell1312 = new PdfPCell(new Paragraph("" + StudentNo.charAt(13), mainContent));

						cell1312.setPaddingBottom(5);
						cell1312.setBorderColor(BaseColor.BLACK);
						cell1312.setHorizontalAlignment(Element.ALIGN_CENTER);

						table4.addCell(cell1312);
					} catch (Exception e) {

						PdfPCell cell1312 = new PdfPCell(new Paragraph("", mainContent));

						cell1312.setPaddingBottom(5);
						cell1312.setBorderColor(BaseColor.BLACK);

						table4.addCell(cell1312);
					}

					try {

						PdfPCell cell1323 = new PdfPCell(new Paragraph("" + StudentNo.charAt(14), mainContent));

						cell1323.setPaddingBottom(5);
						cell1323.setBorderColor(BaseColor.BLACK);
						cell1323.setHorizontalAlignment(Element.ALIGN_CENTER);

						table4.addCell(cell1323);
					} catch (Exception e) {

						PdfPCell cell1323 = new PdfPCell(new Paragraph("", mainContent));

						cell1323.setPaddingBottom(5);
						cell1323.setBorderColor(BaseColor.BLACK);

						table4.addCell(cell1323);
					}

					try {

						PdfPCell cell1334 = new PdfPCell(new Paragraph("" + StudentNo.charAt(15), mainContent));

						cell1334.setPaddingBottom(5);
						cell1334.setBorderColor(BaseColor.BLACK);
						cell1334.setHorizontalAlignment(Element.ALIGN_CENTER);

						table4.addCell(cell1334);
					} catch (Exception e) {

						PdfPCell cell1334 = new PdfPCell(new Paragraph("", mainContent));

						cell1334.setPaddingBottom(5);
						cell1334.setBorderColor(BaseColor.BLACK);

						table4.addCell(cell1334);
					}

					try {

						PdfPCell cell13120 = new PdfPCell(new Paragraph("" + StudentNo.charAt(16), mainContent));

						cell13120.setPaddingBottom(5);
						cell13120.setBorderColor(BaseColor.BLACK);
						cell13120.setHorizontalAlignment(Element.ALIGN_CENTER);

						table4.addCell(cell13120);
					} catch (Exception e) {

						PdfPCell cell13120 = new PdfPCell(new Paragraph("", mainContent));

						cell13120.setPaddingBottom(5);
						cell13120.setBorderColor(BaseColor.BLACK);

						table4.addCell(cell13120);
					}

					try {

						PdfPCell cell13232 = new PdfPCell(new Paragraph("" + StudentNo.charAt(17), mainContent));

						cell13232.setPaddingBottom(5);
						cell13232.setBorderColor(BaseColor.BLACK);
						cell13232.setHorizontalAlignment(Element.ALIGN_CENTER);

						table4.addCell(cell13232);
					} catch (Exception e) {

						PdfPCell cell13232 = new PdfPCell(new Paragraph("", mainContent));

						cell13232.setPaddingBottom(5);
						cell13232.setBorderColor(BaseColor.BLACK);

						table4.addCell(cell13232);
					}

					try {

						PdfPCell cell13341 = new PdfPCell(new Paragraph("" + StudentNo.charAt(18), mainContent));

						cell13341.setPaddingBottom(5);
						cell13341.setBorderColor(BaseColor.BLACK);
						cell13341.setHorizontalAlignment(Element.ALIGN_CENTER);

						table4.addCell(cell13341);
					} catch (Exception e) {

						PdfPCell cell13341 = new PdfPCell(new Paragraph("", mainContent));

						cell13341.setPaddingBottom(5);
						cell13341.setBorderColor(BaseColor.BLACK);

						table4.addCell(cell13341);
					}

					// Compound
					PdfPCell cell13 = new PdfPCell(table4);

					cell13.setColspan(3);
					cell13.setBorderColor(BaseColor.BLACK);

					// Dose
					PdfPCell cell14 = new PdfPCell(new Paragraph("Admission No.", mainContent1));

					cell14.setPaddingBottom(5);
					cell14.setColspan(1);
					cell14.setBorderColor(BaseColor.BLACK);

					// Quantity
					PdfPCell cell15 = new PdfPCell(new Paragraph("" + form.getGrNumber(), mainContent));

					cell15.setPaddingBottom(5);
					cell15.setColspan(3);
					cell15.setBorderColor(BaseColor.BLACK);

					// Frequency
					PdfPCell cell17 = new PdfPCell(new Paragraph("Name of pupil in full", mainContent1));

					cell17.setPaddingBottom(5);
					cell17.setColspan(1);
					cell17.setBorderColor(BaseColor.BLACK);

					// Comment
					PdfPCell cell18 = new PdfPCell(new Paragraph("" + form.getStudentName(), mainContent));

					cell18.setPaddingBottom(5);
					cell18.setColspan(3);
					cell18.setBorderColor(BaseColor.BLACK);

					// Instruction
					PdfPCell cell109 = new PdfPCell(new Paragraph("Mother’s full name", mainContent1));

					cell109.setPaddingBottom(5);
					cell109.setColspan(1);
					cell109.setBorderColor(BaseColor.BLACK);

					PdfPCell cell102 = new PdfPCell(new Paragraph("" + form.getMotherName(), mainContent));

					cell102.setPaddingBottom(5);
					cell102.setColspan(3);
					cell102.setBorderColor(BaseColor.BLACK);

					// Instruction
					PdfPCell cell190 = new PdfPCell(new Paragraph("Father’s full name", mainContent1));

					cell190.setPaddingBottom(5);
					cell190.setColspan(1);
					cell190.setBorderColor(BaseColor.BLACK);

					PdfPCell cell120 = new PdfPCell(new Paragraph("" + form.getFatherName(), mainContent));

					cell120.setPaddingBottom(5);
					cell120.setColspan(3);
					cell120.setBorderColor(BaseColor.BLACK);

					// Instruction
					PdfPCell cell19 = new PdfPCell(new Paragraph("UID(Aadhar card) No of Pupil", mainContent1));

					cell19.setPaddingBottom(5);
					cell19.setColspan(1);
					cell19.setBorderColor(BaseColor.BLACK);

					PdfPTable table6 = new PdfPTable(12);
					table6.setWidthPercentage(100);
					table6.setWidths(new int[] { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 });

					String Aadhaar = form.getAadhaar();

					try {

						PdfPCell cell1209 = new PdfPCell(new Paragraph("" + Aadhaar.charAt(0), mainContent));

						cell1209.setPaddingBottom(5);
						cell1209.setBorderColor(BaseColor.BLACK);
						cell1209.setHorizontalAlignment(Element.ALIGN_CENTER);

						table6.addCell(cell1209);
					} catch (Exception e) {

						PdfPCell cell1209 = new PdfPCell(new Paragraph("", mainContent));

						cell1209.setPaddingBottom(5);
						cell1209.setBorderColor(BaseColor.BLACK);

						table6.addCell(cell1209);
					}

					try {

						PdfPCell cell12091 = new PdfPCell(new Paragraph("" + Aadhaar.charAt(1), mainContent));

						cell12091.setPaddingBottom(5);
						cell12091.setBorderColor(BaseColor.BLACK);
						cell12091.setHorizontalAlignment(Element.ALIGN_CENTER);

						table6.addCell(cell12091);
					} catch (Exception e) {

						PdfPCell cell12091 = new PdfPCell(new Paragraph("", mainContent));

						cell12091.setPaddingBottom(5);
						cell12091.setBorderColor(BaseColor.BLACK);

						table6.addCell(cell12091);
					}

					try {

						PdfPCell cell12092 = new PdfPCell(new Paragraph("" + Aadhaar.charAt(2), mainContent));

						cell12092.setPaddingBottom(5);
						cell12092.setBorderColor(BaseColor.BLACK);
						cell12092.setHorizontalAlignment(Element.ALIGN_CENTER);

						table6.addCell(cell12092);
					} catch (Exception e) {

						PdfPCell cell12092 = new PdfPCell(new Paragraph("", mainContent));

						cell12092.setPaddingBottom(5);
						cell12092.setBorderColor(BaseColor.BLACK);

						table6.addCell(cell12092);
					}

					try {

						PdfPCell cell12093 = new PdfPCell(new Paragraph("" + Aadhaar.charAt(3), mainContent));

						cell12093.setPaddingBottom(5);
						cell12093.setBorderColor(BaseColor.BLACK);
						cell12093.setHorizontalAlignment(Element.ALIGN_CENTER);

						table6.addCell(cell12093);
					} catch (Exception e) {

						PdfPCell cell12093 = new PdfPCell(new Paragraph("", mainContent));

						cell12093.setPaddingBottom(5);
						cell12093.setBorderColor(BaseColor.BLACK);

						table6.addCell(cell12093);
					}

					try {

						PdfPCell cell12094 = new PdfPCell(new Paragraph("" + Aadhaar.charAt(4), mainContent));

						cell12094.setPaddingBottom(5);
						cell12094.setBorderColor(BaseColor.BLACK);
						cell12094.setHorizontalAlignment(Element.ALIGN_CENTER);

						table6.addCell(cell12094);
					} catch (Exception e) {

						PdfPCell cell12094 = new PdfPCell(new Paragraph("", mainContent));

						cell12094.setPaddingBottom(5);
						cell12094.setBorderColor(BaseColor.BLACK);

						table6.addCell(cell12094);
					}

					try {

						PdfPCell cell12095 = new PdfPCell(new Paragraph("" + Aadhaar.charAt(5), mainContent));

						cell12095.setPaddingBottom(5);
						cell12095.setBorderColor(BaseColor.BLACK);
						cell12095.setHorizontalAlignment(Element.ALIGN_CENTER);

						table6.addCell(cell12095);
					} catch (Exception e) {

						PdfPCell cell12095 = new PdfPCell(new Paragraph("", mainContent));

						cell12095.setPaddingBottom(5);
						cell12095.setBorderColor(BaseColor.BLACK);

						table6.addCell(cell12095);
					}

					try {

						PdfPCell cell12096 = new PdfPCell(new Paragraph("" + Aadhaar.charAt(6), mainContent));

						cell12096.setPaddingBottom(5);
						cell12096.setBorderColor(BaseColor.BLACK);
						cell12096.setHorizontalAlignment(Element.ALIGN_CENTER);

						table6.addCell(cell12096);
					} catch (Exception e) {

						PdfPCell cell12096 = new PdfPCell(new Paragraph("", mainContent));

						cell12096.setPaddingBottom(5);
						cell12096.setBorderColor(BaseColor.BLACK);

						table6.addCell(cell12096);
					}

					try {

						PdfPCell cell12097 = new PdfPCell(new Paragraph("" + Aadhaar.charAt(7), mainContent));

						cell12097.setPaddingBottom(5);
						cell12097.setBorderColor(BaseColor.BLACK);
						cell12097.setHorizontalAlignment(Element.ALIGN_CENTER);

						table6.addCell(cell12097);
					} catch (Exception e) {

						PdfPCell cell12097 = new PdfPCell(new Paragraph("", mainContent));

						cell12097.setPaddingBottom(5);
						cell12097.setBorderColor(BaseColor.BLACK);

						table6.addCell(cell12097);
					}

					try {

						PdfPCell cell12098 = new PdfPCell(new Paragraph("" + Aadhaar.charAt(8), mainContent));

						cell12098.setPaddingBottom(5);
						cell12098.setBorderColor(BaseColor.BLACK);
						cell12098.setHorizontalAlignment(Element.ALIGN_CENTER);

						table6.addCell(cell12098);
					} catch (Exception e) {

						PdfPCell cell12098 = new PdfPCell(new Paragraph("", mainContent));

						cell12098.setPaddingBottom(5);
						cell12098.setBorderColor(BaseColor.BLACK);

						table6.addCell(cell12098);
					}

					try {

						PdfPCell cell12099 = new PdfPCell(new Paragraph("" + Aadhaar.charAt(9), mainContent));

						cell12099.setPaddingBottom(5);
						cell12099.setBorderColor(BaseColor.BLACK);
						cell12099.setHorizontalAlignment(Element.ALIGN_CENTER);

						table6.addCell(cell12099);
					} catch (Exception e) {

						PdfPCell cell12099 = new PdfPCell(new Paragraph("", mainContent));

						cell12099.setPaddingBottom(5);
						cell12099.setBorderColor(BaseColor.BLACK);

						table6.addCell(cell12099);
					}

					try {

						PdfPCell cell120910 = new PdfPCell(new Paragraph("" + Aadhaar.charAt(10), mainContent));

						cell120910.setPaddingBottom(5);
						cell120910.setBorderColor(BaseColor.BLACK);
						cell120910.setHorizontalAlignment(Element.ALIGN_CENTER);

						table6.addCell(cell120910);
					} catch (Exception e) {

						PdfPCell cell120910 = new PdfPCell(new Paragraph("", mainContent));

						cell120910.setPaddingBottom(5);
						cell120910.setBorderColor(BaseColor.BLACK);

						table6.addCell(cell120910);
					}

					try {

						PdfPCell cell120911 = new PdfPCell(new Paragraph("" + Aadhaar.charAt(11), mainContent));

						cell120911.setPaddingBottom(5);
						cell120911.setBorderColor(BaseColor.BLACK);
						cell120911.setHorizontalAlignment(Element.ALIGN_CENTER);

						table6.addCell(cell120911);
					} catch (Exception e) {

						PdfPCell cell120911 = new PdfPCell(new Paragraph("", mainContent));

						cell120911.setPaddingBottom(5);
						cell120911.setBorderColor(BaseColor.BLACK);

						table6.addCell(cell120911);
					}

					PdfPCell cell12 = new PdfPCell(table6);

					cell12.setColspan(3);
					cell12.setBorderColor(BaseColor.BLACK);

					// Instruction
					PdfPCell cell191 = new PdfPCell(new Paragraph("Date of birth (in Christian Era)", mainContent1));

					cell191.setPaddingBottom(5);
					cell191.setColspan(1);
					cell191.setBorderColor(BaseColor.BLACK);

					String DateOfBirth = form.getDateOfBirth();

					PdfPTable table5 = new PdfPTable(20);
					table5.setWidthPercentage(100);
					table5.setWidths(new int[] { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 });

					try {

						PdfPCell cell12100 = new PdfPCell(new Paragraph("" + DateOfBirth.charAt(0), mainContent));

						cell12100.setPaddingBottom(5);
						cell12100.setBorderColor(BaseColor.BLACK);
						cell12100.setHorizontalAlignment(Element.ALIGN_CENTER);

						table5.addCell(cell12100);
					} catch (Exception e) {

						PdfPCell cell12100 = new PdfPCell(new Paragraph("", mainContent));

						cell12100.setPaddingBottom(5);
						cell12100.setBorderColor(BaseColor.BLACK);

						table5.addCell(cell12100);
					}

					try {

						PdfPCell cell12110 = new PdfPCell(new Paragraph("" + DateOfBirth.charAt(1), mainContent));

						cell12110.setPaddingBottom(5);
						cell12110.setBorderColor(BaseColor.BLACK);
						cell12110.setHorizontalAlignment(Element.ALIGN_CENTER);

						table5.addCell(cell12110);
					} catch (Exception e) {

						PdfPCell cell12110 = new PdfPCell(new Paragraph("", mainContent));

						cell12110.setPaddingBottom(5);
						cell12110.setBorderColor(BaseColor.BLACK);

						table5.addCell(cell12110);
					}

					try {

						PdfPCell cell121101 = new PdfPCell(new Paragraph("" + DateOfBirth.charAt(2), mainContent));

						cell121101.setPaddingBottom(5);
						cell121101.setBorderColor(BaseColor.BLACK);
						cell121101.setHorizontalAlignment(Element.ALIGN_CENTER);

						table5.addCell(cell121101);
					} catch (Exception e) {

						PdfPCell cell121101 = new PdfPCell(new Paragraph("", mainContent));

						cell121101.setPaddingBottom(5);
						cell121101.setBorderColor(BaseColor.BLACK);

						table5.addCell(cell121101);
					}

					try {

						PdfPCell cell1212 = new PdfPCell(new Paragraph("" + DateOfBirth.charAt(3), mainContent));

						cell1212.setPaddingBottom(5);
						cell1212.setBorderColor(BaseColor.BLACK);
						cell1212.setHorizontalAlignment(Element.ALIGN_CENTER);

						table5.addCell(cell1212);
					} catch (Exception e) {

						PdfPCell cell1212 = new PdfPCell(new Paragraph("", mainContent));

						cell1212.setPaddingBottom(5);
						cell1212.setBorderColor(BaseColor.BLACK);

						table5.addCell(cell1212);
					}

					try {

						PdfPCell cell1213 = new PdfPCell(new Paragraph("" + DateOfBirth.charAt(4), mainContent));

						cell1213.setPaddingBottom(5);
						cell1213.setBorderColor(BaseColor.BLACK);
						cell1213.setHorizontalAlignment(Element.ALIGN_CENTER);

						table5.addCell(cell1213);
					} catch (Exception e) {

						PdfPCell cell1213 = new PdfPCell(new Paragraph("", mainContent));

						cell1213.setPaddingBottom(5);
						cell1213.setBorderColor(BaseColor.BLACK);

						table5.addCell(cell1213);
					}

					try {

						PdfPCell cell12132 = new PdfPCell(new Paragraph("" + DateOfBirth.charAt(5), mainContent));

						cell12132.setPaddingBottom(5);
						cell12132.setBorderColor(BaseColor.BLACK);
						cell12132.setHorizontalAlignment(Element.ALIGN_CENTER);

						table5.addCell(cell12132);
					} catch (Exception e) {

						PdfPCell cell12132 = new PdfPCell(new Paragraph("", mainContent));

						cell12132.setPaddingBottom(5);
						cell12132.setBorderColor(BaseColor.BLACK);

						table5.addCell(cell12132);
					}

					try {

						PdfPCell cell1214 = new PdfPCell(new Paragraph("" + DateOfBirth.charAt(6), mainContent));

						cell1214.setPaddingBottom(5);
						cell1214.setBorderColor(BaseColor.BLACK);
						cell1214.setHorizontalAlignment(Element.ALIGN_CENTER);

						table5.addCell(cell1214);
					} catch (Exception e) {

						PdfPCell cell1214 = new PdfPCell(new Paragraph("", mainContent));

						cell1214.setPaddingBottom(5);
						cell1214.setBorderColor(BaseColor.BLACK);

						table5.addCell(cell1214);
					}

					try {

						PdfPCell cell1215 = new PdfPCell(new Paragraph("" + DateOfBirth.charAt(7), mainContent));

						cell1215.setPaddingBottom(5);
						cell1215.setBorderColor(BaseColor.BLACK);
						cell1215.setHorizontalAlignment(Element.ALIGN_CENTER);

						table5.addCell(cell1215);
					} catch (Exception e) {

						PdfPCell cell1215 = new PdfPCell(new Paragraph("", mainContent));

						cell1215.setPaddingBottom(5);
						cell1215.setBorderColor(BaseColor.BLACK);

						table5.addCell(cell1215);
					}

					try {

						PdfPCell cell1216 = new PdfPCell(new Paragraph("" + DateOfBirth.charAt(8), mainContent));

						cell1216.setPaddingBottom(5);
						cell1216.setBorderColor(BaseColor.BLACK);
						cell1216.setHorizontalAlignment(Element.ALIGN_CENTER);

						table5.addCell(cell1216);
					} catch (Exception e) {
						PdfPCell cell1216 = new PdfPCell(new Paragraph("", mainContent));

						cell1216.setPaddingBottom(5);
						cell1216.setBorderColor(BaseColor.BLACK);

						table5.addCell(cell1216);
					}

					try {

						PdfPCell cell1217 = new PdfPCell(new Paragraph("" + DateOfBirth.charAt(9), mainContent));

						cell1217.setPaddingBottom(5);
						cell1217.setBorderColor(BaseColor.BLACK);
						cell1217.setHorizontalAlignment(Element.ALIGN_CENTER);

						table5.addCell(cell1217);
					} catch (Exception e) {

						PdfPCell cell1217 = new PdfPCell(new Paragraph("", mainContent));

						cell1217.setPaddingBottom(5);
						cell1217.setBorderColor(BaseColor.BLACK);

						table5.addCell(cell1217);
					}

					PdfPCell cell121700 = new PdfPCell();

					cell121700.setColspan(10);
					cell121700.setPaddingBottom(5);
					cell121700.setBorderColor(BaseColor.BLACK);

					table5.addCell(cell121700);

					PdfPCell cell1210 = new PdfPCell(
							new Paragraph("In Words:" + form.getDateOfBirthInWords(), mainContent));

					cell1210.setColspan(20);
					cell1210.setPaddingBottom(5);
					cell1210.setBorderColor(BaseColor.BLACK);

					table5.addCell(cell1210);

					PdfPCell cell121 = new PdfPCell(table5);

					cell121.setColspan(3);
					cell121.setBorderColor(BaseColor.BLACK);

					// Instruction
					PdfPCell cell119 = new PdfPCell(new Paragraph("Place of birth. (Tal.Dist.State)", mainContent1));

					cell119.setPaddingBottom(5);
					cell119.setColspan(1);
					cell119.setBorderColor(BaseColor.BLACK);

					PdfPCell cell122 = new PdfPCell(new Paragraph("" + form.getBirthPlace(), mainContent));

					cell122.setPaddingBottom(5);
					cell122.setColspan(3);
					cell122.setBorderColor(BaseColor.BLACK);

					// Instruction
					PdfPCell cell192 = new PdfPCell(new Paragraph("Nationality", mainContent1));

					cell192.setPaddingBottom(5);
					cell192.setColspan(1);
					cell192.setBorderColor(BaseColor.BLACK);

					PdfPCell cell123 = new PdfPCell(new Paragraph("" + form.getNationality(), mainContent));

					cell123.setPaddingBottom(5);
					cell123.setColspan(1);
					cell123.setBorderColor(BaseColor.BLACK);

					// Instruction
					PdfPCell cell193 = new PdfPCell(new Paragraph("Mother Tongue", mainContent1));

					cell193.setPaddingBottom(5);
					cell193.setColspan(1);
					cell193.setBorderColor(BaseColor.BLACK);

					PdfPCell cell124 = new PdfPCell(new Paragraph("" + form.getMotherTongue(), mainContent));

					cell124.setPaddingBottom(5);
					cell124.setColspan(1);
					cell124.setBorderColor(BaseColor.BLACK);

					// Instruction
					PdfPCell cell194 = new PdfPCell(new Paragraph("Religion, Caste, Sub Caste", mainContent1));

					cell194.setPaddingBottom(5);
					cell194.setColspan(1);
					cell194.setBorderColor(BaseColor.BLACK);

					PdfPCell cell125 = new PdfPCell(new Paragraph("Religion: " + form.getReligion(), mainContent));

					cell125.setPaddingBottom(5);
					cell125.setColspan(1);
					cell125.setBorderColor(BaseColor.BLACK);

					PdfPCell cell1251 = new PdfPCell(new Paragraph("Caste: " + form.getCaste(), mainContent));

					cell1251.setPaddingBottom(5);
					cell1251.setColspan(1);
					cell1251.setBorderColor(BaseColor.BLACK);

					PdfPCell cell1252 = new PdfPCell(new Paragraph("Sub Caste: " + form.getSubCaste(), mainContent));

					cell1252.setPaddingBottom(5);
					cell1252.setColspan(1);
					cell1252.setBorderColor(BaseColor.BLACK);

					// Instruction
					PdfPCell cell195 = new PdfPCell(new Paragraph(
							"Whether the candidate belongs to Schedule Caste or Schedule Tribe", mainContent1));

					cell195.setPaddingBottom(5);
					cell195.setColspan(1);
					cell195.setBorderColor(BaseColor.BLACK);

					PdfPCell cell126 = new PdfPCell(new Paragraph("" + form.getCategory(), mainContent));

					cell126.setPaddingBottom(5);
					cell126.setColspan(3);
					cell126.setBorderColor(BaseColor.BLACK);

					// Instruction
					PdfPCell cell196 = new PdfPCell(new Paragraph("Last school attended", mainContent1));

					cell196.setPaddingBottom(5);
					cell196.setColspan(1);
					cell196.setBorderColor(BaseColor.BLACK);

					PdfPCell cell127 = new PdfPCell(new Paragraph("" + form.getLastSchoolAttended(), mainContent));

					cell127.setPaddingBottom(5);
					cell127.setColspan(3);
					cell127.setBorderColor(BaseColor.BLACK);

					// Instruction
					PdfPCell cell1961 = new PdfPCell(
							new Paragraph("Date of admission in the school & Class", mainContent1));

					cell1961.setPaddingBottom(5);
					cell1961.setColspan(1);
					cell1961.setBorderColor(BaseColor.BLACK);

					String AdmissionDate = form.getAdmissionDate();

					PdfPTable table51 = new PdfPTable(10);
					table51.setWidthPercentage(100);
					table51.setWidths(new int[] { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 });

					try {

						PdfPCell cell1210010 = new PdfPCell(new Paragraph("" + AdmissionDate.charAt(0), mainContent));

						cell1210010.setPaddingBottom(5);
						cell1210010.setBorderColor(BaseColor.BLACK);
						cell1210010.setHorizontalAlignment(Element.ALIGN_CENTER);

						table51.addCell(cell1210010);
					} catch (Exception e) {

						PdfPCell cell1210010 = new PdfPCell(new Paragraph("", mainContent));

						cell1210010.setPaddingBottom(5);
						cell1210010.setBorderColor(BaseColor.BLACK);

						table51.addCell(cell1210010);
					}

					try {

						PdfPCell cell121101 = new PdfPCell(new Paragraph("" + AdmissionDate.charAt(1), mainContent));

						cell121101.setPaddingBottom(5);
						cell121101.setBorderColor(BaseColor.BLACK);
						cell121101.setHorizontalAlignment(Element.ALIGN_CENTER);

						table51.addCell(cell121101);
					} catch (Exception e) {

						PdfPCell cell121101 = new PdfPCell(new Paragraph("", mainContent));

						cell121101.setPaddingBottom(5);
						cell121101.setBorderColor(BaseColor.BLACK);

						table51.addCell(cell121101);
					}

					try {

						PdfPCell cell1211011 = new PdfPCell(new Paragraph("" + AdmissionDate.charAt(2), mainContent));

						cell1211011.setPaddingBottom(5);
						cell1211011.setBorderColor(BaseColor.BLACK);
						cell1211011.setHorizontalAlignment(Element.ALIGN_CENTER);

						table51.addCell(cell1211011);
					} catch (Exception e) {

						PdfPCell cell1211011 = new PdfPCell(new Paragraph("", mainContent));

						cell1211011.setPaddingBottom(5);
						cell1211011.setBorderColor(BaseColor.BLACK);

						table51.addCell(cell1211011);
					}

					try {

						PdfPCell cell12121 = new PdfPCell(new Paragraph("" + AdmissionDate.charAt(3), mainContent));

						cell12121.setPaddingBottom(5);
						cell12121.setBorderColor(BaseColor.BLACK);
						cell12121.setHorizontalAlignment(Element.ALIGN_CENTER);

						table51.addCell(cell12121);
					} catch (Exception e) {

						PdfPCell cell12121 = new PdfPCell(new Paragraph("", mainContent));

						cell12121.setPaddingBottom(5);
						cell12121.setBorderColor(BaseColor.BLACK);

						table51.addCell(cell12121);
					}

					try {

						PdfPCell cell12131 = new PdfPCell(new Paragraph("" + AdmissionDate.charAt(4), mainContent));

						cell12131.setPaddingBottom(5);
						cell12131.setBorderColor(BaseColor.BLACK);
						cell12131.setHorizontalAlignment(Element.ALIGN_CENTER);

						table51.addCell(cell12131);
					} catch (Exception e) {

						PdfPCell cell12131 = new PdfPCell(new Paragraph("", mainContent));

						cell12131.setPaddingBottom(5);
						cell12131.setBorderColor(BaseColor.BLACK);

						table51.addCell(cell12131);
					}

					try {

						PdfPCell cell121321 = new PdfPCell(new Paragraph("" + AdmissionDate.charAt(5), mainContent));

						cell121321.setPaddingBottom(5);
						cell121321.setBorderColor(BaseColor.BLACK);
						cell121321.setHorizontalAlignment(Element.ALIGN_CENTER);

						table51.addCell(cell121321);
					} catch (Exception e) {

						PdfPCell cell121321 = new PdfPCell(new Paragraph("", mainContent));

						cell121321.setPaddingBottom(5);
						cell121321.setBorderColor(BaseColor.BLACK);

						table51.addCell(cell121321);
					}

					try {

						PdfPCell cell12141 = new PdfPCell(new Paragraph("" + AdmissionDate.charAt(6), mainContent));

						cell12141.setPaddingBottom(5);
						cell12141.setBorderColor(BaseColor.BLACK);
						cell12141.setHorizontalAlignment(Element.ALIGN_CENTER);

						table51.addCell(cell12141);
					} catch (Exception e) {

						PdfPCell cell12141 = new PdfPCell(new Paragraph("", mainContent));

						cell12141.setPaddingBottom(5);
						cell12141.setBorderColor(BaseColor.BLACK);

						table51.addCell(cell12141);
					}

					try {

						PdfPCell cell12151 = new PdfPCell(new Paragraph("" + AdmissionDate.charAt(7), mainContent));

						cell12151.setPaddingBottom(5);
						cell12151.setBorderColor(BaseColor.BLACK);
						cell12151.setHorizontalAlignment(Element.ALIGN_CENTER);

						table51.addCell(cell12151);
					} catch (Exception e) {

						PdfPCell cell12151 = new PdfPCell(new Paragraph("", mainContent));

						cell12151.setPaddingBottom(5);
						cell12151.setBorderColor(BaseColor.BLACK);

						table51.addCell(cell12151);
					}

					try {

						PdfPCell cell12161 = new PdfPCell(new Paragraph("" + AdmissionDate.charAt(8), mainContent));

						cell12161.setPaddingBottom(5);
						cell12161.setBorderColor(BaseColor.BLACK);
						cell12161.setHorizontalAlignment(Element.ALIGN_CENTER);

						table51.addCell(cell12161);
					} catch (Exception e) {
						PdfPCell cell12161 = new PdfPCell(new Paragraph("", mainContent));

						cell12161.setPaddingBottom(5);
						cell12161.setBorderColor(BaseColor.BLACK);

						table51.addCell(cell12161);
					}

					try {

						PdfPCell cell12171 = new PdfPCell(new Paragraph("" + AdmissionDate.charAt(9), mainContent));

						cell12171.setPaddingBottom(5);
						cell12171.setBorderColor(BaseColor.BLACK);
						cell12171.setHorizontalAlignment(Element.ALIGN_CENTER);

						table51.addCell(cell12171);
					} catch (Exception e) {

						PdfPCell cell12171 = new PdfPCell(new Paragraph("", mainContent));

						cell12171.setPaddingBottom(5);
						cell12171.setBorderColor(BaseColor.BLACK);

						table51.addCell(cell12171);
					}

					PdfPCell cell1210011 = new PdfPCell(new Paragraph("(dd/MM/yyyy)", mainContent));

					cell1210011.setColspan(10);
					cell1210011.setBorderColor(BaseColor.BLACK);

					table51.addCell(cell1210011);

					PdfPCell cell1271 = new PdfPCell(table51);

					cell1271.setColspan(1);
					cell1271.setBorderColor(BaseColor.BLACK);

					PdfPCell cell12710 = new PdfPCell(new Paragraph("Class: " + form.getFirstClass(), mainContent));

					cell12710.setPaddingBottom(5);
					cell12710.setColspan(2);
					cell12710.setBorderColor(BaseColor.BLACK);

					// Instruction
					PdfPCell cell1962 = new PdfPCell(
							new Paragraph("Class in which the pupil last studied", mainContent1));

					cell1962.setPaddingBottom(5);
					cell1962.setColspan(1);
					cell1962.setBorderColor(BaseColor.BLACK);

					PdfPCell cell1272 = new PdfPCell(
							new Paragraph("In Figures: " + form.getLastStudiedClass(), mainContent));

					cell1272.setPaddingBottom(5);
					cell1272.setColspan(1);
					cell1272.setBorderColor(BaseColor.BLACK);

					PdfPCell cell12720 = new PdfPCell(
							new Paragraph("In Words: " + form.getLastStudiedClassWords(), mainContent));

					cell12720.setPaddingBottom(5);
					cell12720.setColspan(2);
					cell12720.setBorderColor(BaseColor.BLACK);

					// Instruction
					PdfPCell cell1963 = new PdfPCell(
							new Paragraph("School/Board annual examination last taken with result", mainContent1));

					cell1963.setPaddingBottom(5);
					cell1963.setColspan(1);
					cell1963.setBorderColor(BaseColor.BLACK);

					PdfPCell cell1273 = new PdfPCell(new Paragraph("" + form.getResult(), mainContent));

					cell1273.setPaddingBottom(5);
					cell1273.setColspan(3);
					cell1273.setBorderColor(BaseColor.BLACK);

					// Instruction
					PdfPCell cell1964 = new PdfPCell(new Paragraph("Subjects", mainContent1));

					cell1964.setPaddingBottom(5);
					cell1964.setColspan(1);
					cell1964.setBorderColor(BaseColor.BLACK);

					PdfPCell cell1274 = new PdfPCell(new Paragraph("" + form.getSubject(), mainContent));

					cell1274.setPaddingBottom(5);
					cell1274.setColspan(3);
					cell1274.setBorderColor(BaseColor.BLACK);

					// Instruction
					PdfPCell cell1965 = new PdfPCell(
							new Paragraph("Whether qualified for promotion to the higher class", mainContent1));

					cell1965.setPaddingBottom(5);
					cell1965.setColspan(1);
					cell1965.setBorderColor(BaseColor.BLACK);

					PdfPCell cell1275 = new PdfPCell(new Paragraph("" + form.getHigherClass(), mainContent));

					cell1275.setPaddingBottom(5);
					cell1275.setColspan(1);
					cell1275.setBorderColor(BaseColor.BLACK);

					PdfPCell cell12750 = new PdfPCell(
							new Paragraph("Which class in Figure: " + form.getWichClass(), mainContent));

					cell12750.setPaddingBottom(5);
					cell12750.setColspan(1);
					cell12750.setBorderColor(BaseColor.BLACK);

					PdfPCell cell12751 = new PdfPCell(
							new Paragraph("In Words: " + form.getWichClassWords(), mainContent));

					cell12751.setPaddingBottom(5);
					cell12751.setColspan(1);
					cell12751.setBorderColor(BaseColor.BLACK);

					// Instruction
					PdfPCell cell1966 = new PdfPCell(
							new Paragraph("Month upto which the pupil has paid school dues", mainContent1));

					cell1966.setPaddingBottom(5);
					cell1966.setColspan(1);
					cell1966.setBorderColor(BaseColor.BLACK);

					PdfPCell cell1276 = new PdfPCell(new Paragraph("" + form.getDuesMonths(), mainContent));

					cell1276.setPaddingBottom(5);
					cell1276.setColspan(3);
					cell1276.setBorderColor(BaseColor.BLACK);

					// Instruction
					PdfPCell cell1967 = new PdfPCell(new Paragraph("Any fee concession availed of.", mainContent1));

					cell1967.setPaddingBottom(5);
					cell1967.setColspan(1);
					cell1967.setBorderColor(BaseColor.BLACK);

					PdfPCell cell1277 = new PdfPCell(new Paragraph("" + form.getFeeConcession(), mainContent));

					cell1277.setPaddingBottom(5);
					cell1277.setColspan(3);
					cell1277.setBorderColor(BaseColor.BLACK);

					// Instruction
					PdfPCell cell1968 = new PdfPCell(
							new Paragraph("Total No. of working days in the academic session", mainContent1));

					cell1968.setPaddingBottom(5);
					cell1968.setColspan(1);
					cell1968.setBorderColor(BaseColor.BLACK);

					PdfPCell cell1278 = new PdfPCell(new Paragraph("" + form.getWorkingDays(), mainContent));

					cell1278.setPaddingBottom(5);
					cell1278.setColspan(1);
					cell1278.setBorderColor(BaseColor.BLACK);

					// Instruction
					PdfPCell cell199 = new PdfPCell(
							new Paragraph("Total No. of working days pupil present in the school", mainContent1));

					cell199.setPaddingBottom(5);
					cell199.setColspan(1);
					cell199.setBorderColor(BaseColor.BLACK);

					PdfPCell cell1279 = new PdfPCell(new Paragraph("" + form.getPresentDays(), mainContent));

					cell1279.setPaddingBottom(5);
					cell1279.setColspan(1);
					cell1279.setBorderColor(BaseColor.BLACK);

					// Instruction
					PdfPCell cell19660 = new PdfPCell(
							new Paragraph("Whether NCC Cadet/Boy Scout/Girl guide", mainContent1));

					cell19660.setPaddingBottom(5);
					cell19660.setColspan(1);
					cell19660.setBorderColor(BaseColor.BLACK);

					PdfPCell cell12760 = new PdfPCell(new Paragraph("" + form.getNccGuide(), mainContent));

					cell12760.setPaddingBottom(5);
					cell12760.setColspan(3);
					cell12760.setBorderColor(BaseColor.BLACK);

					// Instruction
					PdfPCell cell19670 = new PdfPCell(new Paragraph(
							"Games played or extracurricular activities in which the pupil usually took part",
							mainContent1));

					cell19670.setPaddingBottom(5);
					cell19670.setColspan(1);
					cell19670.setBorderColor(BaseColor.BLACK);

					PdfPCell cell12770 = new PdfPCell(new Paragraph("" + form.getPhysicalActivity(), mainContent));

					cell12770.setPaddingBottom(5);
					cell12770.setColspan(3);
					cell12770.setBorderColor(BaseColor.BLACK);

					// Instruction
					PdfPCell cell19680 = new PdfPCell(new Paragraph("General conduct", mainContent1));

					cell19680.setPaddingBottom(5);
					cell19680.setColspan(1);
					cell19680.setBorderColor(BaseColor.BLACK);

					PdfPCell cell12780 = new PdfPCell(new Paragraph("" + form.getGeneralConduct(), mainContent));

					cell12780.setPaddingBottom(5);
					cell12780.setColspan(1);
					cell12780.setBorderColor(BaseColor.BLACK);

					PdfPCell cell127801 = new PdfPCell(new Paragraph("Academic progress", mainContent1));

					cell127801.setPaddingBottom(5);
					cell127801.setColspan(1);
					cell127801.setBorderColor(BaseColor.BLACK);

					PdfPCell cell1278011 = new PdfPCell(new Paragraph("" + form.getAcademicProgress(), mainContent));

					cell1278011.setPaddingBottom(5);
					cell1278011.setColspan(1);
					cell1278011.setBorderColor(BaseColor.BLACK);

					// Instruction
					PdfPCell cell1990 = new PdfPCell(new Paragraph("Date of issue of certificate", mainContent1));

					cell1990.setPaddingBottom(5);
					cell1990.setColspan(1);
					cell1990.setBorderColor(BaseColor.BLACK);

					String DateOfapplication = form.getDateOfapplication();

					PdfPTable table52 = new PdfPTable(10);
					table52.setWidthPercentage(100);
					table52.setWidths(new int[] { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 });

					try {

						PdfPCell cell12100101 = new PdfPCell(
								new Paragraph("" + DateOfapplication.charAt(0), mainContent));

						cell12100101.setPaddingBottom(5);
						cell12100101.setBorderColor(BaseColor.BLACK);
						cell12100101.setHorizontalAlignment(Element.ALIGN_CENTER);

						table52.addCell(cell12100101);
					} catch (Exception e) {

						PdfPCell cell12100101 = new PdfPCell(new Paragraph("", mainContent));

						cell12100101.setPaddingBottom(5);
						cell12100101.setBorderColor(BaseColor.BLACK);

						table52.addCell(cell12100101);
					}

					try {

						PdfPCell cell1211013 = new PdfPCell(
								new Paragraph("" + DateOfapplication.charAt(1), mainContent));

						cell1211013.setPaddingBottom(5);
						cell1211013.setBorderColor(BaseColor.BLACK);
						cell1211013.setHorizontalAlignment(Element.ALIGN_CENTER);

						table52.addCell(cell1211013);
					} catch (Exception e) {

						PdfPCell cell1211013 = new PdfPCell(new Paragraph("", mainContent));

						cell1211013.setPaddingBottom(5);
						cell1211013.setBorderColor(BaseColor.BLACK);

						table52.addCell(cell1211013);
					}

					try {

						PdfPCell cell12110113 = new PdfPCell(
								new Paragraph("" + DateOfapplication.charAt(2), mainContent));

						cell12110113.setPaddingBottom(5);
						cell12110113.setBorderColor(BaseColor.BLACK);
						cell12110113.setHorizontalAlignment(Element.ALIGN_CENTER);

						table52.addCell(cell12110113);
					} catch (Exception e) {

						PdfPCell cell12110113 = new PdfPCell(new Paragraph("", mainContent));

						cell12110113.setPaddingBottom(5);
						cell12110113.setBorderColor(BaseColor.BLACK);

						table52.addCell(cell12110113);
					}

					try {

						PdfPCell cell121213 = new PdfPCell(
								new Paragraph("" + DateOfapplication.charAt(3), mainContent));

						cell121213.setPaddingBottom(5);
						cell121213.setBorderColor(BaseColor.BLACK);
						cell121213.setHorizontalAlignment(Element.ALIGN_CENTER);

						table52.addCell(cell121213);
					} catch (Exception e) {

						PdfPCell cell121213 = new PdfPCell(new Paragraph("", mainContent));

						cell121213.setPaddingBottom(5);
						cell121213.setBorderColor(BaseColor.BLACK);

						table52.addCell(cell121213);
					}

					try {

						PdfPCell cell121313 = new PdfPCell(
								new Paragraph("" + DateOfapplication.charAt(4), mainContent));

						cell121313.setPaddingBottom(5);
						cell121313.setBorderColor(BaseColor.BLACK);
						cell121313.setHorizontalAlignment(Element.ALIGN_CENTER);

						table52.addCell(cell121313);
					} catch (Exception e) {

						PdfPCell cell121313 = new PdfPCell(new Paragraph("", mainContent));

						cell121313.setPaddingBottom(5);
						cell121313.setBorderColor(BaseColor.BLACK);

						table52.addCell(cell121313);
					}

					try {

						PdfPCell cell1213213 = new PdfPCell(
								new Paragraph("" + DateOfapplication.charAt(5), mainContent));

						cell1213213.setPaddingBottom(5);
						cell1213213.setBorderColor(BaseColor.BLACK);
						cell1213213.setHorizontalAlignment(Element.ALIGN_CENTER);

						table52.addCell(cell1213213);
					} catch (Exception e) {

						PdfPCell cell1213213 = new PdfPCell(new Paragraph("", mainContent));

						cell1213213.setPaddingBottom(5);
						cell1213213.setBorderColor(BaseColor.BLACK);

						table52.addCell(cell1213213);
					}

					try {

						PdfPCell cell121413 = new PdfPCell(
								new Paragraph("" + DateOfapplication.charAt(6), mainContent));

						cell121413.setPaddingBottom(5);
						cell121413.setBorderColor(BaseColor.BLACK);
						cell121413.setHorizontalAlignment(Element.ALIGN_CENTER);

						table52.addCell(cell121413);
					} catch (Exception e) {

						PdfPCell cell121413 = new PdfPCell(new Paragraph("", mainContent));

						cell121413.setPaddingBottom(5);
						cell121413.setBorderColor(BaseColor.BLACK);

						table52.addCell(cell121413);
					}

					try {

						PdfPCell cell121513 = new PdfPCell(
								new Paragraph("" + DateOfapplication.charAt(7), mainContent));

						cell121513.setPaddingBottom(5);
						cell121513.setBorderColor(BaseColor.BLACK);
						cell121513.setHorizontalAlignment(Element.ALIGN_CENTER);

						table52.addCell(cell121513);
					} catch (Exception e) {

						PdfPCell cell121513 = new PdfPCell(new Paragraph("", mainContent));

						cell121513.setPaddingBottom(5);
						cell121513.setBorderColor(BaseColor.BLACK);

						table52.addCell(cell121513);
					}

					try {

						PdfPCell cell121613 = new PdfPCell(
								new Paragraph("" + DateOfapplication.charAt(8), mainContent));

						cell121613.setPaddingBottom(5);
						cell121613.setBorderColor(BaseColor.BLACK);
						cell121613.setHorizontalAlignment(Element.ALIGN_CENTER);

						table52.addCell(cell121613);
					} catch (Exception e) {
						PdfPCell cell121613 = new PdfPCell(new Paragraph("", mainContent));

						cell121613.setPaddingBottom(5);
						cell121613.setBorderColor(BaseColor.BLACK);

						table52.addCell(cell121613);
					}

					try {

						PdfPCell cell121713 = new PdfPCell(
								new Paragraph("" + DateOfapplication.charAt(9), mainContent));

						cell121713.setPaddingBottom(5);
						cell121713.setBorderColor(BaseColor.BLACK);
						cell121713.setHorizontalAlignment(Element.ALIGN_CENTER);

						table52.addCell(cell121713);
					} catch (Exception e) {

						PdfPCell cell121713 = new PdfPCell(new Paragraph("", mainContent));

						cell121713.setPaddingBottom(5);
						cell121713.setBorderColor(BaseColor.BLACK);

						table52.addCell(cell121713);
					}

					PdfPCell cell1210010123 = new PdfPCell(new Paragraph("(dd/MM/yyyy)", mainContent));

					cell1210010123.setColspan(10);
					cell1210010123.setBorderColor(BaseColor.BLACK);

					table52.addCell(cell1210010123);

					PdfPCell cell12790 = new PdfPCell(table52);

					cell12790.setColspan(3);
					cell12790.setBorderColor(BaseColor.BLACK);

					// Instruction
					PdfPCell cell1991 = new PdfPCell(new Paragraph("Reasons for leaving the school", mainContent1));

					cell1991.setPaddingBottom(5);
					cell1991.setColspan(1);
					cell1991.setBorderColor(BaseColor.BLACK);

					PdfPCell cell12791 = new PdfPCell(new Paragraph("" + form.getReasons(), mainContent));

					cell12791.setPaddingBottom(5);
					cell12791.setColspan(3);
					cell12791.setBorderColor(BaseColor.BLACK);

					// Instruction
					PdfPCell cell1992 = new PdfPCell(new Paragraph("Any other remarks", mainContent1));

					cell1992.setBorderWidth(0.1f);
					cell1992.setPaddingBottom(5);
					cell1992.setColspan(1);
					cell1992.setBorderColor(BaseColor.BLACK);
					cell1992.setBorderColorBottom(BaseColor.BLACK);

					PdfPCell cell12792 = new PdfPCell(new Paragraph("" + form.getRemarks(), mainContent));

					cell12792.setBorderWidth(0.1f);
					cell12792.setPaddingBottom(5);
					cell12792.setColspan(3);
					cell12792.setBorderColor(BaseColor.BLACK);
					cell12792.setBorderColorBottom(BaseColor.BLACK);

					/*
					 * // Instruction PdfPCell cell1993 = new PdfPCell(new Paragraph(
					 * "Note: Certified that the above information is in accordance with the school register"
					 * , mainContent));
					 * 
					 * cell1993.setBorderWidth(0.1f); cell1993.setPaddingTop(5);
					 * cell1993.setPaddingBottom(1); cell1993.setColspan(4);
					 * cell1993.setBorderColor(BaseColor.WHITE);
					 * cell1993.setBorderColorTop(BaseColor.BLACK);
					 */

					// Paragraph notePara = new Paragraph("Note: Certified that the above
					// information is in accordance with the school register");

					table3.addCell(cell1);
					table3.addCell(cell198);
					table3.addCell(cell101);
					table3.addCell(cell1980);
					table3.addCell(cell012);
					table3.addCell(cell13);
					table3.addCell(cell14);
					table3.addCell(cell15);
					table3.addCell(cell17);
					table3.addCell(cell18);
					table3.addCell(cell109);
					table3.addCell(cell102);
					table3.addCell(cell190);
					table3.addCell(cell120);
					table3.addCell(cell19);
					table3.addCell(cell12);
					table3.addCell(cell191);
					table3.addCell(cell121);
					// table3.addCell(cell11921);
					table3.addCell(cell119);
					table3.addCell(cell122);
					table3.addCell(cell192);
					table3.addCell(cell123);
					table3.addCell(cell193);
					table3.addCell(cell124);
					table3.addCell(cell194);
					table3.addCell(cell125);
					table3.addCell(cell1251);
					table3.addCell(cell1252);
					table3.addCell(cell195);
					table3.addCell(cell126);
					table3.addCell(cell196);
					table3.addCell(cell127);
					table3.addCell(cell1961);
					table3.addCell(cell1271);
					table3.addCell(cell12710);
					table3.addCell(cell1962);
					table3.addCell(cell1272);
					table3.addCell(cell12720);
					table3.addCell(cell1963);
					table3.addCell(cell1273);
					table3.addCell(cell1964);
					table3.addCell(cell1274);
					table3.addCell(cell1965);
					table3.addCell(cell1275);
					table3.addCell(cell12750);
					table3.addCell(cell12751);
					table3.addCell(cell1966);
					table3.addCell(cell1276);
					table3.addCell(cell1967);
					table3.addCell(cell1277);
					table3.addCell(cell1968);
					table3.addCell(cell1278);
					table3.addCell(cell199);
					table3.addCell(cell1279);
					table3.addCell(cell19660);
					table3.addCell(cell12760);
					table3.addCell(cell19670);
					table3.addCell(cell12770);
					table3.addCell(cell19680);
					table3.addCell(cell12780);
					table3.addCell(cell127801);
					table3.addCell(cell1278011);
					table3.addCell(cell1990);
					table3.addCell(cell12790);
					table3.addCell(cell1991);
					table3.addCell(cell12791);
					table3.addCell(cell1992);
					table3.addCell(cell12792);

					// table3.addCell(cell1993);

					document.add(table3);

					// document.add(notePara);
				}

				PdfPTable table1 = new PdfPTable(3);
				table1.setTotalWidth(510);
				table1.setWidthPercentage(100);
				Rectangle rect5 = new Rectangle(270, 700);
				table1.setWidthPercentage(new float[] { 90, 90, 90 }, rect5);

				PdfPCell cell1110 = new PdfPCell(new Paragraph(
						"Note: Certified that the above information is in accordance with the school register", Font3));
				cell1110.setBackgroundColor(BaseColor.WHITE);
				// cell111.setPaddingTop(20);
				cell1110.setColspan(3);
				cell1110.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell1110.setBorderColor(BaseColor.WHITE);
				cell1110.setBorderColorTop(BaseColor.BLACK);
				cell1110.setBorderWidthTop(1f);

				PdfPCell cell111 = new PdfPCell(new Paragraph("\n\nSignature of Class Teacher", Font5));
				cell111.setBackgroundColor(BaseColor.WHITE);
				// cell111.setPaddingTop(20);
				cell111.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell111.setBorderColor(BaseColor.WHITE);

				PdfPCell cell1111 = new PdfPCell(new Paragraph("\n\nChecked by", Font5));
				cell1111.setBackgroundColor(BaseColor.WHITE);
				// cell111.setPaddingTop(20);
				cell1111.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1111.setBorderColor(BaseColor.WHITE);

				PdfPCell cell11111 = new PdfPCell(
						new Paragraph("\n\nSignature of Principal \n with date & School seal", Font5));
				cell11111.setBackgroundColor(BaseColor.WHITE);
				// cell111.setPaddingTop(20);

				cell11111.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell11111.setBorderColor(BaseColor.WHITE);

				table1.addCell(cell1110);
				table1.addCell(cell111);
				table1.addCell(cell1111);
				table1.addCell(cell11111);

				document.add(table1);

				/*
				 * FooterTable1 event = new FooterTable1(table1); writer.setPageEvent(event);
				 */

				document.newPage();

			}

			document.close();

			System.out.println("Successfully written and generated Student's Leaving Certificate PDF Report");

			status = "success";

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			status = "error";
		}
		return status;
	}

	public String convertVotingReportOPDPDF(int academicYearID, String academicYearName, String realPath,
			String pdfFIleName) {

		VotingDAOInf votingDao = new VotingDAOImpl();

		List<VotingForm> HeadGirlList = null;

		List<VotingForm> HeadBoyList = null;

		List<VotingForm> RedList = null;

		List<VotingForm> BlueList = null;

		List<VotingForm> GreenList = null;

		List<VotingForm> YellowList = null;

		HashMap<String, String> HeadGirlDetailsList = null;

		HashMap<String, String> HeadBoyDetailsList = null;

		HashMap<String, String> RedCaptainDetailsList = null;

		HashMap<String, String> BlueCaptainDetailsList = null;

		HashMap<String, String> GreenCaptainDetailsList = null;

		HashMap<String, String> YellowCaptainDetailsList = null;

		String HeadGirlName = "";

		String HeadBoyName = "";

		String RedCaptainName = "";

		String BlueCaptainName = "";

		String GreenCaptainName = "";

		String YellowCaptainName = "";

		String HeadGirls = "";

		String HeadBoys = "";

		String RedCaptains = "";

		String BlueCaptains = "";

		String GreenCaptains = "";

		String YellowCaptains = "";

		try {

			HeadGirlList = votingDao.retreiveHeadGirlsList(academicYearID);
			for (VotingForm form : HeadGirlList) {
				HeadGirls += ", " + form.getName() + ":" + form.getVoteCount();
			}

			if (HeadGirls.startsWith(",")) {
				HeadGirls = HeadGirls.substring(1);
			}

			HeadBoyList = votingDao.retreiveHeadBoysList(academicYearID);
			for (VotingForm form : HeadBoyList) {
				HeadBoys += ", " + form.getName() + ":" + form.getVoteCount();
			}

			if (HeadBoys.startsWith(",")) {
				HeadBoys = HeadBoys.substring(1);
			}

			RedList = votingDao.retreiveHouseCaptainRedList(academicYearID);
			for (VotingForm form : RedList) {
				RedCaptains += ", " + form.getName() + ":" + form.getVoteCount();
			}

			if (RedCaptains.startsWith(",")) {
				RedCaptains = RedCaptains.substring(1);
			}

			BlueList = votingDao.retreiveHouseCaptainBlueList(academicYearID);
			for (VotingForm form : BlueList) {
				BlueCaptains += ", " + form.getName() + ":" + form.getVoteCount();
			}

			if (BlueCaptains.startsWith(",")) {
				BlueCaptains = BlueCaptains.substring(1);
			}

			GreenList = votingDao.retreiveHouseCaptainGreenList(academicYearID);
			for (VotingForm form : GreenList) {
				GreenCaptains += ", " + form.getName() + ":" + form.getVoteCount();
			}

			if (GreenCaptains.startsWith(",")) {
				GreenCaptains = GreenCaptains.substring(1);
			}

			YellowList = votingDao.retreiveHouseCaptainYellowList(academicYearID);
			for (VotingForm form : YellowList) {
				YellowCaptains += ", " + form.getName() + ":" + form.getVoteCount();
			}

			if (YellowCaptains.startsWith(",")) {
				YellowCaptains = YellowCaptains.substring(1);
			}

			HeadGirlName = votingDao.retrieveHeadGirlName(academicYearID);
			String HeadGirl[] = HeadGirlName.split(",");

			HeadBoyName = votingDao.retrieveHeadBoyName(academicYearID);
			String HeadBoy[] = HeadBoyName.split(",");

			RedCaptainName = votingDao.retrieveRedCaptainName(academicYearID);
			String RedCaptain[] = RedCaptainName.split(",");

			BlueCaptainName = votingDao.retrieveBlueCaptainName(academicYearID);
			String BlueCaptain[] = BlueCaptainName.split(",");

			GreenCaptainName = votingDao.retrieveGreenCaptainName(academicYearID);
			String GreenCaptain[] = GreenCaptainName.split(",");

			YellowCaptainName = votingDao.retrieveYellowCaptainName(academicYearID);
			String YellowCaptain[] = YellowCaptainName.split(",");

			HeadGirlDetailsList = votingDao.retrieveHeadGirlDetailsList(academicYearID);

			HeadBoyDetailsList = votingDao.retrieveHeadBoyDetailsList(academicYearID);

			RedCaptainDetailsList = votingDao.retrieveRedCaptainDetailsList(academicYearID);

			BlueCaptainDetailsList = votingDao.retrieveBlueCaptainDetailsList(academicYearID);

			GreenCaptainDetailsList = votingDao.retrieveGreenCaptainDetailsList(academicYearID);

			YellowCaptainDetailsList = votingDao.retrieveYellowCaptainDetailsList(academicYearID);

			/* Image path for posterior segment images */

			String organisationImage = realPath + File.separator + "images/pdfimg.jpg";

			File file = new File(realPath + pdfFIleName);

			/* Creating Document for PDF */

			Document document = null;

			document = new Document(PageSize.A4);

			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));

			Font Font1 = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.NORMAL);
			Font Font3 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
			Font Font2 = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);
			Font Font4 = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD);
			Font Font5 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
			Font mainContent = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL);
			Font mainContent1 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
			// mainContent1.setColor(BaseColor.GRAY);

			document.open();

			Image image1 = Image.getInstance(organisationImage);

			/* Setting header */

			document.addCreator("KovidSVS");
			document.addTitle("Students Election Report");

			PdfPTable table = new PdfPTable(4);
			table.setFooterRows(1);
			table.setWidthPercentage(100);
			Rectangle rect = new Rectangle(270, 700);
			table.setWidthPercentage(new float[] { 65, 70, 65, 70 }, rect);

			PdfPCell cell = new PdfPCell(image1, true);
			cell.setBorderWidth(0.01f);
			cell.setColspan(4);
			// cell.setPaddingTop(2);
			cell.setPaddingBottom(5);
			cell.setFixedHeight(100);
			cell.setBorderWidthLeft(1f);
			cell.setBorderColor(BaseColor.WHITE);
			cell.setUseBorderPadding(true);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell cell2 = new PdfPCell(new Paragraph("School Voting System", Font1));
			cell2.setBorderWidth(0.01f);
			cell2.setColspan(4);
			// cell2.setPaddingTop(2);
			// cell2.setPaddingBottom(10);
			cell2.setBorderWidthLeft(0.2f);
			cell2.setBorderColor(BaseColor.WHITE);
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell cell02 = new PdfPCell(
					new Paragraph("Results for Academic Year: " + academicYearName, mainContent1));
			cell02.setBorderWidth(0.01f);
			cell02.setColspan(4);
			cell02.setPaddingTop(5);
			cell02.setPaddingBottom(10);
			cell02.setBorderWidthLeft(0.2f);
			cell02.setBorderColor(BaseColor.WHITE);
			cell02.setHorizontalAlignment(Element.ALIGN_CENTER);

			table.addCell(cell);
			table.addCell(cell2);
			table.addCell(cell02);

			document.add(table);

			PdfPTable table3 = new PdfPTable(3);
			table3.setWidthPercentage(100);
			Rectangle rect2 = new Rectangle(270, 700);
			table3.setWidthPercentage(new float[] { 90, 90, 90 }, rect2);

			PdfPCell cell131 = new PdfPCell(new Paragraph("Results", mainContent1));

			// cell13.setBorderWidth(0.01f);
			cell131.setPaddingBottom(11);
			cell131.setBorderColor(BaseColor.BLACK);

			PdfPCell cell13 = new PdfPCell(new Paragraph("Name", mainContent1));

			// cell13.setBorderWidth(0.01f);
			cell13.setPaddingBottom(11);
			cell13.setBorderColor(BaseColor.BLACK);

			// Dose
			PdfPCell cell14 = new PdfPCell(new Paragraph("Votes", mainContent1));

			cell14.setPaddingBottom(11);
			cell14.setBorderColor(BaseColor.BLACK);

			table3.addCell(cell131);
			table3.addCell(cell13);
			table3.addCell(cell14);

			PdfPCell cell0200 = new PdfPCell(new Paragraph("Head Girl", mainContent1));

			cell0200.setPaddingBottom(3);
			cell0200.setBorderColor(BaseColor.BLACK);

			// HeadGirl name
			PdfPCell cell200 = new PdfPCell(new Paragraph(HeadGirl[0], mainContent));

			cell200.setPaddingBottom(3);
			cell200.setBorderColor(BaseColor.BLACK);

			// HeadGirl vote
			PdfPCell cell201 = new PdfPCell(new Paragraph(HeadGirl[1], mainContent));

			// cell21.setBorderWidth(0.01f);
			cell201.setPaddingBottom(3);
			cell201.setBorderColor(BaseColor.BLACK);

			// HeadBoy
			PdfPCell cell20 = new PdfPCell(new Paragraph("Head Boy", mainContent1));

			// cell20.setBorderWidth(0.01f);
			cell20.setPaddingBottom(3);
			// cell20.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell20.setBorderColor(BaseColor.BLACK);

			// HeadBoy name
			PdfPCell cell21 = new PdfPCell(new Paragraph(HeadBoy[0], mainContent));

			// cell21.setBorderWidth(0.01f);
			cell21.setPaddingBottom(3);
			// cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell21.setBorderColor(BaseColor.BLACK);

			// HeadBoy Vote
			PdfPCell cell219 = new PdfPCell(new Paragraph(HeadBoy[1], mainContent));

			// cell21.setBorderWidth(0.01f);
			cell219.setPaddingBottom(3);
			// cell219.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell219.setBorderColor(BaseColor.BLACK);

			// Red
			PdfPCell cell22 = new PdfPCell(new Paragraph("House Captain: Red", mainContent1));

			// cell22.setBorderWidth(0.01f);
			cell22.setPaddingBottom(3);
			// cell22.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell22.setBorderColor(BaseColor.BLACK);

			// Red name
			PdfPCell cell225 = new PdfPCell(new Paragraph(RedCaptain[0], mainContent));

			// cell22.setBorderWidth(0.01f);
			cell225.setPaddingBottom(3);
			// cell225.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell225.setBorderColor(BaseColor.BLACK);

			// Red Vote
			PdfPCell cell204 = new PdfPCell(new Paragraph(RedCaptain[1], mainContent));

			// cell24.setBorderWidth(0.01f);
			cell204.setPaddingBottom(3);
			// cell204.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell204.setBorderColor(BaseColor.BLACK);

			// Blue
			PdfPCell cell24 = new PdfPCell(new Paragraph("House Captain: Blue", mainContent1));

			// cell24.setBorderWidth(0.01f);
			cell24.setPaddingBottom(3);
			// cell24.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell24.setBorderColor(BaseColor.BLACK);

			// Blue name
			PdfPCell cell25 = new PdfPCell(new Paragraph(BlueCaptain[0], mainContent));

			// cell25.setBorderWidth(0.01f);
			cell25.setPaddingBottom(3);
			// cell25.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell25.setBorderColor(BaseColor.BLACK);

			// Blue vote
			PdfPCell cell27 = new PdfPCell(new Paragraph(BlueCaptain[1], mainContent));

			// cell26.setBorderWidth(0.01f);
			cell27.setPaddingBottom(3);
			// cell27.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell27.setBorderColor(BaseColor.BLACK);

			// Green
			PdfPCell cell28 = new PdfPCell(new Paragraph("House Captain: Green", mainContent1));

			// cell24.setBorderWidth(0.01f);
			cell28.setPaddingBottom(3);
			// cell28.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell28.setBorderColor(BaseColor.BLACK);

			// Green name
			PdfPCell cell29 = new PdfPCell(new Paragraph(GreenCaptain[0], mainContent));

			// cell25.setBorderWidth(0.01f);
			cell29.setPaddingBottom(3);
			// cell29.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell29.setBorderColor(BaseColor.BLACK);

			// Green vote
			PdfPCell cell30 = new PdfPCell(new Paragraph(GreenCaptain[1], mainContent));

			// cell26.setBorderWidth(0.01f);
			cell30.setPaddingBottom(3);
			// cell30.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell30.setBorderColor(BaseColor.BLACK);

			// Yellow
			PdfPCell cell31 = new PdfPCell(new Paragraph("House Captain: Yellow", mainContent1));

			// cell24.setBorderWidth(0.01f);
			cell31.setPaddingBottom(3);
			// cell31.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell31.setBorderColor(BaseColor.BLACK);

			// Yellow name
			PdfPCell cell32 = new PdfPCell(new Paragraph(YellowCaptain[0], mainContent));

			// cell25.setBorderWidth(0.01f);
			cell32.setPaddingBottom(3);
			// cell32.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell32.setBorderColor(BaseColor.BLACK);

			// Yellow vote
			PdfPCell cell33 = new PdfPCell(new Paragraph(YellowCaptain[1], mainContent));

			// cell26.setBorderWidth(0.01f);
			cell33.setPaddingBottom(3);
			// cell33.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell33.setBorderColor(BaseColor.BLACK);

			table3.addCell(cell0200);
			table3.addCell(cell200);
			table3.addCell(cell201);
			table3.addCell(cell20);
			table3.addCell(cell21);
			table3.addCell(cell219);
			table3.addCell(cell22);
			table3.addCell(cell225);
			table3.addCell(cell204);
			table3.addCell(cell24);
			table3.addCell(cell25);
			table3.addCell(cell27);
			table3.addCell(cell28);
			table3.addCell(cell29);
			table3.addCell(cell30);
			table3.addCell(cell31);
			table3.addCell(cell32);
			table3.addCell(cell33);

			document.add(table3);

			PdfPTable table2 = new PdfPTable(1);
			table2.setFooterRows(1);
			table2.setWidthPercentage(100);
			Rectangle rect1 = new Rectangle(270, 700);
			table2.setWidthPercentage(new float[] { 270 }, rect);

			PdfPCell cell211 = new PdfPCell(new Paragraph("Head Girl:", Font5));
			cell211.setBorderWidth(0.01f);
			cell211.setColspan(4);
			cell211.setPaddingTop(20);
			// cell211.setPaddingBottom(10);
			cell211.setBorderWidthLeft(0.2f);
			cell211.setBorderColor(BaseColor.WHITE);
			/* cell211.setHorizontalAlignment(Element.ALIGN_CENTER); */

			PdfPCell cell001 = new PdfPCell(
					new Paragraph("Total Candidates: " + HeadGirlDetailsList.get("HeadGirlCount"), mainContent));
			cell001.setBorderWidth(0.01f);
			cell001.setPaddingTop(5);
			// cell001.setPaddingBottom(10);
			cell001.setBorderWidthLeft(0.2f);
			cell001.setBorderColor(BaseColor.WHITE);

			PdfPCell cell002 = new PdfPCell(new Paragraph(
					"Candidates Approved: " + HeadGirlDetailsList.get("HeadGirlApprovedCount"), mainContent));
			cell002.setBorderWidth(0.01f);
			cell002.setPaddingTop(5);
			// cell002.setPaddingBottom(10);
			cell002.setBorderWidthLeft(0.2f);
			cell002.setBorderColor(BaseColor.WHITE);

			PdfPCell cell003 = new PdfPCell(
					new Paragraph("Total Votes: " + HeadGirlDetailsList.get("HeadGirlTotalVote"), mainContent));
			cell003.setBorderWidth(0.01f);
			cell003.setPaddingTop(5);
			// cell003.setPaddingBottom(10);
			cell003.setBorderWidthLeft(0.2f);
			cell003.setBorderColor(BaseColor.WHITE);

			PdfPCell cell005 = new PdfPCell(new Paragraph("Candidate-wise Votes: " + HeadGirls, mainContent));
			cell005.setBorderWidth(0.01f);
			cell005.setPaddingTop(5);
			// cell005.setPaddingBottom(10);
			cell005.setBorderWidthLeft(0.2f);
			cell005.setBorderColor(BaseColor.WHITE);

			PdfPCell cell006 = new PdfPCell(new Paragraph("Elected Head-Girl: " + HeadGirl[0], mainContent1));
			cell006.setBorderWidth(0.01f);
			cell006.setPaddingTop(5);
			// cell006.setPaddingBottom(10);
			cell006.setBorderWidthLeft(0.2f);
			cell006.setBorderColor(BaseColor.WHITE);

			table2.addCell(cell211);
			table2.addCell(cell001);
			table2.addCell(cell002);
			table2.addCell(cell003);
			table2.addCell(cell005);
			table2.addCell(cell006);

			document.add(table2);

			PdfPTable table4 = new PdfPTable(1);
			table4.setFooterRows(1);
			table4.setWidthPercentage(100);
			Rectangle rect3 = new Rectangle(270, 700);
			table4.setWidthPercentage(new float[] { 270 }, rect);

			PdfPCell cell2110 = new PdfPCell(new Paragraph("Head Boy:", Font5));
			cell2110.setBorderWidth(0.01f);
			cell2110.setColspan(4);
			cell2110.setPaddingTop(20);
			// cell2110.setPaddingBottom(10);
			cell2110.setBorderWidthLeft(0.2f);
			cell2110.setBorderColor(BaseColor.WHITE);
			/* cell211.setHorizontalAlignment(Element.ALIGN_CENTER); */

			PdfPCell cell0010 = new PdfPCell(
					new Paragraph("Total Candidates: " + HeadBoyDetailsList.get("HeadBoyCount"), mainContent));
			cell0010.setBorderWidth(0.01f);
			cell0010.setPaddingTop(5);
			// cell0010.setPaddingBottom(10);
			cell0010.setBorderWidthLeft(0.2f);
			cell0010.setBorderColor(BaseColor.WHITE);

			PdfPCell cell0020 = new PdfPCell(new Paragraph(
					"Candidates Approved: " + HeadBoyDetailsList.get("HeadBoyApprovedCount"), mainContent));
			cell0020.setBorderWidth(0.01f);
			cell0020.setPaddingTop(5);
			// cell0020.setPaddingBottom(10);
			cell0020.setBorderWidthLeft(0.2f);
			cell0020.setBorderColor(BaseColor.WHITE);

			PdfPCell cell0030 = new PdfPCell(
					new Paragraph("Total Votes: " + HeadBoyDetailsList.get("HeadBoyTotalVote"), mainContent));
			cell0030.setBorderWidth(0.01f);
			cell0030.setPaddingTop(5);
			// cell0030.setPaddingBottom(10);
			cell0030.setBorderWidthLeft(0.2f);
			cell0030.setBorderColor(BaseColor.WHITE);

			PdfPCell cell0050 = new PdfPCell(new Paragraph("Candidate-wise Votes:" + HeadBoys, mainContent));
			cell0050.setBorderWidth(0.01f);
			cell0050.setPaddingTop(5);
			// cell0050.setPaddingBottom(10);
			cell0050.setBorderWidthLeft(0.2f);
			cell0050.setBorderColor(BaseColor.WHITE);

			PdfPCell cell0060 = new PdfPCell(new Paragraph("Elected Head-Boy: " + HeadBoy[0], mainContent1));
			cell0060.setBorderWidth(0.01f);
			cell0060.setPaddingTop(5);
			// cell0060.setPaddingBottom(10);
			cell0060.setBorderWidthLeft(0.2f);
			cell0060.setBorderColor(BaseColor.WHITE);

			table4.addCell(cell2110);
			table4.addCell(cell0010);
			table4.addCell(cell0020);
			table4.addCell(cell0030);
			table4.addCell(cell0050);
			table4.addCell(cell0060);

			document.add(table4);

			PdfPTable table5 = new PdfPTable(1);
			table5.setFooterRows(1);
			table5.setWidthPercentage(100);
			Rectangle rect4 = new Rectangle(270, 700);
			table5.setWidthPercentage(new float[] { 270 }, rect);

			PdfPCell cell2111 = new PdfPCell(new Paragraph("House Captain – Red:", Font5));
			cell2111.setBorderWidth(0.01f);
			cell2111.setColspan(4);
			cell2111.setPaddingTop(20);
			// cell2111.setPaddingBottom(10);
			cell2111.setBorderWidthLeft(0.2f);
			cell2111.setBorderColor(BaseColor.WHITE);
			/* cell211.setHorizontalAlignment(Element.ALIGN_CENTER); */

			PdfPCell cell0011 = new PdfPCell(
					new Paragraph("Total Candidates: " + RedCaptainDetailsList.get("RedCaptainCount"), mainContent));
			cell0011.setBorderWidth(0.01f);
			cell0011.setPaddingTop(5);
			// cell0011.setPaddingBottom(10);
			cell0011.setBorderWidthLeft(0.2f);
			cell0011.setBorderColor(BaseColor.WHITE);

			PdfPCell cell0021 = new PdfPCell(new Paragraph(
					"Candidates Approved: " + RedCaptainDetailsList.get("RedCaptainApprovedCount"), mainContent));
			cell0021.setBorderWidth(0.01f);
			cell0021.setPaddingTop(5);
			// cell0021.setPaddingBottom(10);
			cell0021.setBorderWidthLeft(0.2f);
			cell0021.setBorderColor(BaseColor.WHITE);

			PdfPCell cell0031 = new PdfPCell(
					new Paragraph("Total Votes: " + RedCaptainDetailsList.get("RedCaptainTotalVote"), mainContent));
			cell0031.setBorderWidth(0.01f);
			cell0031.setPaddingTop(5);
			// cell0031.setPaddingBottom(10);
			cell0031.setBorderWidthLeft(0.2f);
			cell0031.setBorderColor(BaseColor.WHITE);

			PdfPCell cell0051 = new PdfPCell(new Paragraph("Candidate-wise Votes:" + RedCaptains, mainContent));
			cell0051.setBorderWidth(0.01f);
			cell0051.setPaddingTop(5);
			// cell0051.setPaddingBottom(10);
			cell0051.setBorderWidthLeft(0.2f);
			cell0051.setBorderColor(BaseColor.WHITE);

			PdfPCell cell0061 = new PdfPCell(
					new Paragraph("Elected House Captain for House Red: " + RedCaptain[0], mainContent1));
			cell0061.setBorderWidth(0.01f);
			cell0061.setPaddingTop(5);
			// cell0061.setPaddingBottom(10);
			cell0061.setBorderWidthLeft(0.2f);
			cell0061.setBorderColor(BaseColor.WHITE);

			table5.addCell(cell2111);
			table5.addCell(cell0011);
			table5.addCell(cell0021);
			table5.addCell(cell0031);
			table5.addCell(cell0051);
			table5.addCell(cell0061);

			document.add(table5);

			PdfPTable table6 = new PdfPTable(1);
			table6.setFooterRows(1);
			table6.setWidthPercentage(100);
			Rectangle rect5 = new Rectangle(270, 700);
			table6.setWidthPercentage(new float[] { 270 }, rect);

			PdfPCell cell2112 = new PdfPCell(new Paragraph("House Captain – Blue:", Font5));
			cell2112.setBorderWidth(0.01f);
			cell2112.setColspan(4);
			cell2112.setPaddingTop(20);
			// cell2112.setPaddingBottom(10);
			cell2112.setBorderWidthLeft(0.2f);
			cell2112.setBorderColor(BaseColor.WHITE);
			/* cell211.setHorizontalAlignment(Element.ALIGN_CENTER); */

			PdfPCell cell0012 = new PdfPCell(
					new Paragraph("Total Candidates: " + BlueCaptainDetailsList.get("BlueCaptainCount"), mainContent));
			cell0012.setBorderWidth(0.01f);
			cell0012.setPaddingTop(5);
			// cell0012.setPaddingBottom(10);
			cell0012.setBorderWidthLeft(0.2f);
			cell0012.setBorderColor(BaseColor.WHITE);

			PdfPCell cell0022 = new PdfPCell(new Paragraph(
					"Candidates Approved: " + BlueCaptainDetailsList.get("BlueCaptainApprovedCount"), mainContent));
			cell0022.setBorderWidth(0.01f);
			cell0022.setPaddingTop(5);
			// cell0022.setPaddingBottom(10);
			cell0022.setBorderWidthLeft(0.2f);
			cell0022.setBorderColor(BaseColor.WHITE);

			PdfPCell cell0032 = new PdfPCell(
					new Paragraph("Total Votes: " + BlueCaptainDetailsList.get("BlueCaptainTotalVote"), mainContent));
			cell0032.setBorderWidth(0.01f);
			cell0032.setPaddingTop(5);
			// cell0032.setPaddingBottom(10);
			cell0032.setBorderWidthLeft(0.2f);
			cell0032.setBorderColor(BaseColor.WHITE);

			PdfPCell cell0052 = new PdfPCell(new Paragraph("Candidate-wise Votes:" + BlueCaptains, mainContent));
			cell0052.setBorderWidth(0.01f);
			cell0052.setPaddingTop(5);
			// cell0052.setPaddingBottom(10);
			cell0052.setBorderWidthLeft(0.2f);
			cell0052.setBorderColor(BaseColor.WHITE);

			PdfPCell cell0062 = new PdfPCell(
					new Paragraph("Elected House Captain for House Blue: " + BlueCaptain[0], mainContent1));
			cell0062.setBorderWidth(0.01f);
			cell0062.setPaddingTop(5);
			// cell0062.setPaddingBottom(10);
			cell0062.setBorderWidthLeft(0.2f);
			cell0062.setBorderColor(BaseColor.WHITE);

			table6.addCell(cell2112);
			table6.addCell(cell0012);
			table6.addCell(cell0022);
			table6.addCell(cell0032);
			table6.addCell(cell0052);
			table6.addCell(cell0062);

			document.add(table6);

			PdfPTable table7 = new PdfPTable(1);
			table7.setFooterRows(1);
			table7.setWidthPercentage(100);
			Rectangle rect6 = new Rectangle(270, 700);
			table7.setWidthPercentage(new float[] { 270 }, rect);

			PdfPCell cell2113 = new PdfPCell(new Paragraph("House Captain – Green:", Font5));
			cell2113.setBorderWidth(0.01f);
			cell2113.setColspan(4);
			cell2113.setPaddingTop(20);
			// cell2113.setPaddingBottom(10);
			cell2113.setBorderWidthLeft(0.2f);
			cell2113.setBorderColor(BaseColor.WHITE);
			/* cell211.setHorizontalAlignment(Element.ALIGN_CENTER); */

			PdfPCell cell0013 = new PdfPCell(new Paragraph(
					"Total Candidates: " + GreenCaptainDetailsList.get("GreenCaptainCount"), mainContent));
			cell0013.setBorderWidth(0.01f);
			cell0013.setPaddingTop(5);
			// cell0013.setPaddingBottom(10);
			cell0013.setBorderWidthLeft(0.2f);
			cell0013.setBorderColor(BaseColor.WHITE);

			PdfPCell cell0023 = new PdfPCell(new Paragraph(
					"Candidates Approved: " + GreenCaptainDetailsList.get("GreenCaptainApprovedCount"), mainContent));
			cell0023.setBorderWidth(0.01f);
			cell0023.setPaddingTop(5);
			// cell0023.setPaddingBottom(10);
			cell0023.setBorderWidthLeft(0.2f);
			cell0023.setBorderColor(BaseColor.WHITE);

			PdfPCell cell0033 = new PdfPCell(
					new Paragraph("Total Votes: " + GreenCaptainDetailsList.get("GreenCaptainTotalVote"), mainContent));
			cell0033.setBorderWidth(0.01f);
			cell0033.setPaddingTop(5);
			// cell0033.setPaddingBottom(10);
			cell0033.setBorderWidthLeft(0.2f);
			cell0033.setBorderColor(BaseColor.WHITE);

			PdfPCell cell0053 = new PdfPCell(new Paragraph("Candidate-wise Votes:" + GreenCaptains, mainContent));
			cell0053.setBorderWidth(0.01f);
			cell0053.setPaddingTop(5);
			// cell0053.setPaddingBottom(10);
			cell0053.setBorderWidthLeft(0.2f);
			cell0053.setBorderColor(BaseColor.WHITE);

			PdfPCell cell0063 = new PdfPCell(
					new Paragraph("Elected House Captain for House Green: " + GreenCaptain[0], mainContent1));
			cell0063.setBorderWidth(0.01f);
			cell0063.setPaddingTop(5);
			// cell0063.setPaddingBottom(10);
			cell0063.setBorderWidthLeft(0.2f);
			cell0063.setBorderColor(BaseColor.WHITE);

			table7.addCell(cell2113);
			table7.addCell(cell0013);
			table7.addCell(cell0023);
			table7.addCell(cell0033);
			table7.addCell(cell0053);
			table7.addCell(cell0063);

			document.add(table7);

			PdfPTable table8 = new PdfPTable(1);
			table8.setFooterRows(1);
			table8.setWidthPercentage(100);
			Rectangle rect7 = new Rectangle(270, 700);
			table8.setWidthPercentage(new float[] { 270 }, rect);

			PdfPCell cell2114 = new PdfPCell(new Paragraph("House Captain – Yellow:", Font5));
			cell2114.setBorderWidth(0.01f);
			cell2114.setColspan(4);
			cell2114.setPaddingTop(20);
			// cell2114.setPaddingBottom(10);
			cell2114.setBorderWidthLeft(0.2f);
			cell2114.setBorderColor(BaseColor.WHITE);
			/* cell211.setHorizontalAlignment(Element.ALIGN_CENTER); */

			PdfPCell cell0014 = new PdfPCell(new Paragraph(
					"Total Candidates: " + YellowCaptainDetailsList.get("YellowCaptainCount"), mainContent));
			cell0014.setBorderWidth(0.01f);
			cell0014.setPaddingTop(5);
			// cell0014.setPaddingBottom(10);
			cell0014.setBorderWidthLeft(0.2f);
			cell0014.setBorderColor(BaseColor.WHITE);

			PdfPCell cell0024 = new PdfPCell(new Paragraph(
					"Candidates Approved: " + YellowCaptainDetailsList.get("YellowCaptainApprovedCount"), mainContent));
			cell0024.setBorderWidth(0.01f);
			cell0024.setPaddingTop(5);
			// cell0024.setPaddingBottom(10);
			cell0024.setBorderWidthLeft(0.2f);
			cell0024.setBorderColor(BaseColor.WHITE);

			PdfPCell cell0034 = new PdfPCell(new Paragraph(
					"Total Votes: " + YellowCaptainDetailsList.get("YellowCaptainTotalVote"), mainContent));
			cell0034.setBorderWidth(0.01f);
			cell0034.setPaddingTop(5);
			// cell0034.setPaddingBottom(10);
			cell0034.setBorderWidthLeft(0.2f);
			cell0034.setBorderColor(BaseColor.WHITE);

			PdfPCell cell0054 = new PdfPCell(new Paragraph("Candidate-wise Votes:" + YellowCaptains, mainContent));
			cell0054.setBorderWidth(0.01f);
			cell0054.setPaddingTop(5);
			// cell0054.setPaddingBottom(10);
			cell0054.setBorderWidthLeft(0.2f);
			cell0054.setBorderColor(BaseColor.WHITE);

			PdfPCell cell0064 = new PdfPCell(
					new Paragraph("Elected House Captain for House Yellow: " + YellowCaptain[0], mainContent1));
			cell0064.setBorderWidth(0.01f);
			cell0064.setPaddingTop(5);
			// cell0064.setPaddingBottom(10);
			cell0064.setBorderWidthLeft(0.2f);
			cell0064.setBorderColor(BaseColor.WHITE);

			table8.addCell(cell2114);
			table8.addCell(cell0014);
			table8.addCell(cell0024);
			table8.addCell(cell0034);
			table8.addCell(cell0054);
			table8.addCell(cell0064);

			document.add(table8);

			document.close();

			System.out.println("Successfully written and generated Student PDF Report");

			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();
			status = "error";
		}
		return status;
	}

}
