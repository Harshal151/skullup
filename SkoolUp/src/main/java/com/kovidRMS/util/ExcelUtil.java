package com.kovidRMS.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.util.SystemOutLogger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.amazonaws.services.s3.AmazonS3;
import com.kovidRMS.daoImpl.ConfigurationDAOImpl;
import com.kovidRMS.daoImpl.LibraryDAOImpl;
import com.kovidRMS.daoImpl.LoginDAOImpl;
import com.kovidRMS.daoImpl.StudentDAOImpl;
import com.kovidRMS.daoInf.ConfigurationDAOInf;
import com.kovidRMS.daoInf.LibraryDAOInf;
import com.kovidRMS.daoInf.LoginDAOInf;
import com.kovidRMS.daoInf.StuduntDAOInf;
import com.kovidRMS.form.ConfigurationForm;
import com.kovidRMS.form.LibraryForm;
import com.kovidRMS.form.LoginForm;
import com.kovidRMS.form.ReportRow;
import com.kovidRMS.form.StudentForm;

import javassist.expr.NewArray;

public class ExcelUtil extends DAOConnection {

	String status = "error";

	String message = null;

	String statusMessage = "error";

	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	PreparedStatement preparedStatement1 = null;
	ResultSet resultSet1 = null;

	PreparedStatement preparedStatement2 = null;
	ResultSet resultSet2 = null;

	PreparedStatement preparedStatement3 = null;
	ResultSet resultSet3 = null;

	private String inputFile;

	ConfigXMLUtil xmlUtil = new ConfigXMLUtil();

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public String generateStudentsCustomisedReport(String standard, String division, String finalcheckBoxList,
			List<String> studentsBasedCustomReportList, String excelFileName, String realPath) {

		int currentRow = 1;

		try {

			File file = new File(realPath + "/" + excelFileName);

			// Workbook wb = new HSSFWorkbook();
			XSSFWorkbook wb = new XSSFWorkbook();

			XSSFSheet spreadSheet = wb.createSheet("Students customise report");
			Row row;

			row = spreadSheet.createRow(0);

			/*
			 * Creating header in XLSX
			 */
			CellStyle headerCellStyle = wb.createCellStyle();

			/*
			 * Setting up the font
			 */
			Font setFont = wb.createFont();
			setFont.setFontHeightInPoints((short) 12);
			setFont.setBold(true);
			headerCellStyle.setFont(setFont);

			/*
			 * Setting up Data style in XLSX
			 */
			CellStyle dataCellStyle = wb.createCellStyle();
			Font setDataFont = wb.createFont();
			setDataFont.setFontHeightInPoints((short) 12);
			dataCellStyle.setFont(setDataFont);

			/*
			 * Initializing Cell reference variable
			 */
			Cell cell = null;

			/*
			 * Generating XLSX Header value for Patient Information
			 */

			int counter = 2;

			if (finalcheckBoxList != null) {

				spreadSheet.setColumnWidth((short) 0, (short) (256 * 25));
				spreadSheet.setColumnWidth((short) 1, (short) (256 * 25));

				String[] newcheckBoxList = finalcheckBoxList.split(",");

				for (int i = 0; i < newcheckBoxList.length; i++) {

					spreadSheet.setColumnWidth((short) counter, (short) (256 * 25));

					counter++;
				}

			} else {

				spreadSheet.setColumnWidth((short) 0, (short) (256 * 25));
				spreadSheet.setColumnWidth((short) 1, (short) (256 * 25));
			}

			/*
			 * Giving values to header
			 */

			int counter1 = 2;

			if (finalcheckBoxList != null) {

				cell = row.createCell((short) 0);
				cell.setCellValue("Roll_No");
				cell.setCellStyle(headerCellStyle);

				cell = row.createCell((short) 1);
				cell.setCellValue("Student_Name");
				cell.setCellStyle(headerCellStyle);

				String[] newcheckBoxList = finalcheckBoxList.split(",");

				for (int i = 0; i < newcheckBoxList.length; i++) {

					cell = row.createCell((short) counter1);
					cell.setCellValue(newcheckBoxList[i]);
					cell.setCellStyle(headerCellStyle);

					counter1++;
				}

			} else {

				cell = row.createCell((short) 0);
				cell.setCellValue("Roll_No");
				cell.setCellStyle(headerCellStyle);

				cell = row.createCell((short) 1);
				cell.setCellValue("Student_Name");
				cell.setCellStyle(headerCellStyle);
			}

			/*
			 * For Receipt items values
			 */

			for (String FinalStudentsReportData : studentsBasedCustomReportList) {
				int counter2 = 0;
				row = spreadSheet.createRow(currentRow++);

				String[] List = FinalStudentsReportData.split("\\$");

				for (int j = 0; j < List.length; j++) {

					cell = row.createCell((short) counter2);
					cell.setCellValue(List[j]);
					cell.setCellStyle(dataCellStyle);
					counter2++;
				}
			}

			/*
			 * writing to the xlsx file
			 */
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wb.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();

			/*
			 * Write the output to a file
			 */
			FileOutputStream fileOut = new FileOutputStream(new File(realPath + "/" + excelFileName));
			fileOut.write(outArray);
			fileOut.flush();
			fileOut.close();

			System.out.println("Excel sheet for Students Customised Report is created successfully.");

			status = "success";

			/*
			 * Closing resultSet, preparedStatement, connection objects
			 */

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;

	}

	public String generateExamsCustomisedReport(StudentForm studform, int UserID, int AcademicYearID, String realPath,
			String excelFileName) {

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		StuduntDAOInf daoInf = new StudentDAOImpl();

		HashMap<String, String> StandardDivisionList = null;

		HashMap<String, String> ExamList = null;

		HashMap<Integer, String> SubjectListByStandard = null;

		HashMap<String, Integer> NewExaminationList = null;

		List<String> studentFinalExamCustomReportList = null;

		List<StudentForm> studentsBasedExamCustomReportList = null;

		int currentRow = 5;

		try {

			File file = new File(realPath + "/" + excelFileName);

			/*
			 * Generating XLSX Header value for Patient Information
			 */

			String StandardName = daoInf1.retrieveStandardName(UserID, AcademicYearID);

			int StandardID = daoInf1.retrieveStandardID(UserID, AcademicYearID);

			String DivisionName = daoInf1.retrieveDivisionName(UserID, AcademicYearID);

			int DivisionID = daoInf1.retrieveDivisionID(UserID, AcademicYearID);

			String Stage = daoInf2.getStandardStageByStandardID(StandardID);

			int AYClassID = 0;

			if (studform.getDivision().contains(",")) {

				AYClassID = daoInf2.retrieveAYCLassIDForAll(studform.getStandardID(), AcademicYearID);
			} else {
				AYClassID = daoInf2.retrieveAYCLassID(studform.getStandardID(),
						Integer.parseInt(studform.getDivision()), AcademicYearID);
			}

			String Subject = daoInf2.retrieveSubject(studform.getSubjectID());

			// getting DivisionList value
			StandardDivisionList = daoInf1.getDivisionList(StandardID);

			if (studform.getContainsName().equals("GradeBased")) {

				if (studform.getTerm().equals("All")) {
					ExamList = daoInf.retrieveAllExamTermEndAndAcademicYearID(AcademicYearID);
				} else {
					ExamList = daoInf.retrieveExamTermEndAndAcademicYearID(AcademicYearID, studform.getTerm());
				}

				SubjectListByStandard = daoInf.getSubjectListByStandardForNonScholastic(studform.getStandardID());

			} else {

				if (studform.getTerm().equals("All")) {
					ExamList = daoInf2.getALLExamListByTermAcademicYearID(StandardID, AcademicYearID);
				} else {
					ExamList = daoInf2.getExamListByTermAcademicYearID(StandardID, AcademicYearID, studform.getTerm());
				}

				SubjectListByStandard = daoInf.getSubjectListByStandard(StandardID);
			}

			String checkBoxList;
			checkBoxList = studform.getCheckBoxList();

			String ExamCustomList = "Roll_No, Student_Name";

			String finalcheckBoxList = "";

			int SECheck = 0;

			int NBCheck = 0;

			int SENBCheck = 0;

			int counterNew = 0;

			String term1CheckList = "";

			String term2CheckList = "";

			String termEndTerm1 = "";
			String unitTestTerm1 = "";
			String SEATerm1 = "";
			String NBTerm1 = "";
			String termEndTerm2 = "";
			String unitTestTerm2 = "";
			String SEATerm2 = "";
			String NBTerm2 = "";

			if (checkBoxList != null) {

				String[] newcheckBoxList = checkBoxList.split(",");

				for (int i = 0; i < newcheckBoxList.length; i++) {
					String[] array = newcheckBoxList[i].split("\\$");

					int examinationID = Integer.parseInt(array[1]);

					String term = daoInf.retrieveTermByExaminationID(examinationID);

					String examType = daoInf.retrieveExaminationType(examinationID);

					if (term.equals("Term I")) {
						if (examType.equals("Term End")) {
							termEndTerm1 = newcheckBoxList[i];
						} else if (examType.equals("Unit Test")) {
							unitTestTerm1 = newcheckBoxList[i];
						} else if (examType.equals("Subject Enrichment")) {
							SEATerm1 = SEATerm1 + "," + newcheckBoxList[i];
						} else if (examType.equals("Notebook")) {
							NBTerm1 = NBTerm1 + "," + newcheckBoxList[i];
						}

					} else {
						if (examType.equals("Term End")) {
							termEndTerm2 = newcheckBoxList[i];
						} else if (examType.equals("Unit Test")) {
							unitTestTerm2 = newcheckBoxList[i];
						} else if (examType.equals("Subject Enrichment")) {
							SEATerm2 = SEATerm2 + "," + newcheckBoxList[i];
						} else if (examType.equals("Notebook")) {
							NBTerm2 = NBTerm2 + "," + newcheckBoxList[i];
						}
					}

				}

				term1CheckList = termEndTerm1 + "," + unitTestTerm1 + SEATerm1 + NBTerm1;

				term2CheckList = termEndTerm2 + "," + unitTestTerm2 + SEATerm2 + NBTerm2;

				if (studform.getTerm().contains("All")) {
					if (term1CheckList.startsWith(",")) {
						term1CheckList = term1CheckList.substring(1);
					}

					checkBoxList = term1CheckList + "," + term2CheckList;
				} else if (studform.getTerm().contains("Term I")) {
					if (term1CheckList.startsWith(",")) {
						term1CheckList = term1CheckList.substring(1);
					}
					checkBoxList = term1CheckList;
				} else {

					if (term2CheckList.startsWith(",")) {
						term2CheckList = term2CheckList.substring(1);
					}
					checkBoxList = term2CheckList;
				}
			}

			System.out.println("...checkBoxList..." + checkBoxList);

			if (checkBoxList != null) {
				if (checkBoxList.contains(",")) {

					String[] newcheckBoxList = checkBoxList.split(",");

					for (int i = 0; i < newcheckBoxList.length; i++) {

						String[] newList = newcheckBoxList[i].split("\\$");

						if (Stage.equals("Primary")) {
							if (studform.getMarks().equals("marksObtained")) {

								finalcheckBoxList = finalcheckBoxList + "," + newList[0];
							} else {

								finalcheckBoxList = finalcheckBoxList + "," + newList[0];

								if (studform.getTotalValueString().equals("Total")) {

									String examType = daoInf.retrieveExaminationType(Integer.parseInt(newList[1]));

									if (examType.equals("Subject Enrichment") || examType.equals("Notebook")) {
										counterNew++;

										if (counterNew > 2) {
											SENBCheck = 1;
											counterNew = 0;
										}
									}
								}
							}

						} else {

							if (studform.getMarks().equals("marksObtained")) {
								finalcheckBoxList = finalcheckBoxList + "," + newList[0];
							} else {

								String examType = daoInf.retrieveExaminationType(Integer.parseInt(newList[1]));

								if (examType.equals("Subject Enrichment")) {

									SECheck = 1;

								}

								if (examType.equals("Notebook")) {

									NBCheck = 1;

								}
								finalcheckBoxList = finalcheckBoxList + "," + newList[0];

							}
						}
					}
				} else {

					String[] newList = checkBoxList.split("\\$");

					finalcheckBoxList = finalcheckBoxList + "," + newList[0];
				}

				if (studform.getTerm().equals("All")) {
					if (SENBCheck == 1) {
						finalcheckBoxList = finalcheckBoxList + ","
								+ "Best of Subject Enrichment + Notebook Term I,Best of Subject Enrichment + Notebook Term II";
					}
				} else {
					if (SENBCheck == 1) {
						finalcheckBoxList = finalcheckBoxList + "," + "Best of Subject Enrichment + Notebook";
					}
				}

				if (studform.getTerm().equals("All")) {
					if (NBCheck == 1 && SECheck == 1) {
						finalcheckBoxList = finalcheckBoxList + ","
								+ "Best Of Subject Enrichment Term I,Best Of Subject Enrichment Term II,Best Of Notebook I,Best Of Notebook II";
					} else if (SECheck == 1) {
						finalcheckBoxList = finalcheckBoxList + ","
								+ "Best Of Subject Enrichment Term I,Best Of Subject Enrichment Term II";
					} else if (NBCheck == 1) {
						finalcheckBoxList = finalcheckBoxList + "," + "Best Of Notebook I,Best Of Notebook II";
					}
				} else {
					if (NBCheck == 1 && SECheck == 1) {
						finalcheckBoxList = finalcheckBoxList + "," + "Best Of Subject Enrichment,Best Of Notebook";
					} else if (SECheck == 1) {
						finalcheckBoxList = finalcheckBoxList + "," + "Best Of Subject Enrichment";
					} else if (NBCheck == 1) {
						finalcheckBoxList = finalcheckBoxList + "," + "Best Of Notebook";
					}
				}

				if (studform.getTotalValueString().equals("Total")) {
					ExamCustomList = ExamCustomList + finalcheckBoxList + "," + studform.getTotalValueString() + ","
							+ "Grade";

				} else {
					ExamCustomList = ExamCustomList + finalcheckBoxList;
				}
			} else {

				ExamCustomList = ExamCustomList;
			}

			if (checkBoxList != null) {

				String examID = "";
				if (checkBoxList.contains(",")) {
					String[] newcheckBox = checkBoxList.split(",");

					for (int i = 0; i < newcheckBox.length; i++) {

						String[] newList = newcheckBox[i].split("\\$");
						examID = examID + "," + newList[1];

						if (examID.startsWith(",")) {
							examID = examID.substring(1);
						}
					}
				} else {

					String[] newList = checkBoxList.split("\\$");
					examID = newList[1];
				}

				if (studform.getDivision().contains(",")) {

					studentsBasedExamCustomReportList = daoInf.retrievestudentsBasedAllExamCustomReportList(
							studform.getStandardID(), AcademicYearID, examID, studform.getSubjectID(),
							studform.getSearchFields(), studform.getContainsName(), studform.getSearchFieldsNew(),
							studform.getAgeValue());
				} else {

					studentsBasedExamCustomReportList = daoInf.retrievestudentsBasedExamCustomReportList(
							studform.getStandardID(), studform.getDivision(), AcademicYearID, examID,
							studform.getSubjectID(), studform.getSearchFields(), studform.getContainsName(),
							studform.getSearchFieldsNew(), studform.getAgeValue());
				}

				studentFinalExamCustomReportList = new ArrayList<String>();

				for (StudentForm studentsExamFormName : studentsBasedExamCustomReportList) {

					int SECheckCounter = 0;

					int NBCheckCounter = 0;

					int counter = 0;

					StudentForm form = new StudentForm();

					String FinalList = "";
					String FinalListNew = "";
					String FinalListMarks = "";
					String FinalSEANBMarks = "";
					String FinalSEANB1Marks = "";
					String grade = "";
					String Studentgrade = "";
					double TotalMarksGrade = 0.0;
					double TotalMarksGrade1 = 0.0;
					double Marks = 0.0;
					double NewMarks = 0.0;
					int OutOfMarks = 0;
					int FinalOutOfMarks = 0;
					double MarksNew = 0.0;
					double TotalMarks = 0;
					double FinalTotalMarks = 0;

					int seCheckCounter = 1;

					String SubjectTypeValue = daoInf.retrieveSubjectTypeValue(studform.getSubjectID());

					String NewFinalGrade = studform.getGradeValue();

					String NewGrade = "";
					String NewGradeNew = "";

					int counterAll = 1;
					String Term = "";

					if (checkBoxList != null) {

						if (checkBoxList.contains(",")) {

							String[] newcheckBoxList = checkBoxList.split(",");

							for (int i = 0; i < newcheckBoxList.length; i++) {

								if (counterAll == 1) {
									Term = "Term I";
								} else {
									Term = "Term II";
								}

								String[] newList = newcheckBoxList[i].split("\\$");

								if (studform.getContainsName().equals("GradeBased")) {

									if (studform.getDivision().contains(",")) {

										if (SubjectTypeValue.equals("Scholastic")) {

											FinalListNew = studentsExamFormName.getRollNumber() + "="
													+ studentsExamFormName.getStudentName();

											/*---------------------------------------------------------------*/
											if (Stage.equals("Primary")) {

												if (studform.getTerm().contains("All")) {

													NewExaminationList = daoInf2.retrieveExaminationList(AcademicYearID,
															Term, AYClassID);

													double value, value1, value2, value3, value4, finalOutOfMarks,
															totalMarksScaled, toatlScaleTo1 = 0;
													int marksObtained, marksObtained1, marksObtained2, marksObtained3,
															marksObtained4 = 0;
													double value5, value6, finalSEAMarks = 0D;
													int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5 = 0;
													int toatlScaleTo, outOfMarks, outOfMarks1, outOfMarks2,
															finalSEAmarksvalue = 0;

													value = daoInf.retrievrAllMarksScaledForStudent(
															NewExaminationList.get("Term End"), studform.getSubjectID(),
															studform.getStandardID(), AcademicYearID,
															studentsExamFormName.getStudentID());

													value1 = daoInf.retrievrAllMarksScaledForStudent(
															NewExaminationList.get("Unit Test"),
															studform.getSubjectID(), studform.getStandardID(),
															AcademicYearID, studentsExamFormName.getStudentID());

													value2 = daoInf.retrievrAllMarksScaledForStudent(
															NewExaminationList.get("Subject Enrichment1"),
															studform.getSubjectID(), studform.getStandardID(),
															AcademicYearID, studentsExamFormName.getStudentID());

													value3 = daoInf.retrievrAllMarksScaledForStudent(
															NewExaminationList.get("Subject Enrichment2"),
															studform.getSubjectID(), studform.getStandardID(),
															AcademicYearID, studentsExamFormName.getStudentID());

													value4 = daoInf.retrievrAllMarksScaledForStudent(
															NewExaminationList.get("Notebook"), studform.getSubjectID(),
															studform.getStandardID(), AcademicYearID,
															studentsExamFormName.getStudentID());

													outOfMarks = daoInf.retrievAllTotalMarks(
															NewExaminationList.get("Subject Enrichment1"),
															studform.getSubjectID(), studform.getStandardID(),
															AcademicYearID);

													outOfMarks1 = daoInf.retrievAllTotalMarks(
															NewExaminationList.get("Subject Enrichment2"),
															studform.getSubjectID(), studform.getStandardID(),
															AcademicYearID);

													outOfMarks2 = daoInf.retrievAllTotalMarks(
															NewExaminationList.get("Notebook"), studform.getSubjectID(),
															studform.getStandardID(), AcademicYearID);

													finalOutOfMarks = (outOfMarks + outOfMarks1 + outOfMarks2);

													value5 = (value2 + value3 + value4);

													String numberAsString = Double.toString(value5);

													String marks[] = numberAsString.split("\\.");

													if (Long.parseLong(marks[1]) > 0) {

														value6 = Integer.parseInt(marks[0]) + 1;

													} else {

														value6 = Integer.parseInt(marks[0]);
													}

													scaleTo = daoInf.retrieveAllScaledMarks(Term, AcademicYearID,
															NewExaminationList.get("Term End"),
															studform.getStandardID(), studform.getSubjectID());

													scaleTo1 = daoInf.retrieveAllScaledMarks(Term, AcademicYearID,
															NewExaminationList.get("Unit Test"),
															studform.getStandardID(), studform.getSubjectID());

													scaleTo2 = daoInf.retrieveAllScaledMarks(Term, AcademicYearID,
															NewExaminationList.get("Subject Enrichment1"),
															studform.getStandardID(), studform.getSubjectID());

													scaleTo3 = daoInf.retrieveAllScaledMarks(Term, AcademicYearID,
															NewExaminationList.get("Subject Enrichment2"),
															studform.getStandardID(), studform.getSubjectID());

													scaleTo4 = daoInf.retrieveAllScaledMarks(Term, AcademicYearID,
															NewExaminationList.get("Notebook"),
															studform.getStandardID(), studform.getSubjectID());

													// finalSEAMarks = (value6 * scaleTo4) / finalOutOfMarks;

													finalSEAMarks = daoInf2.retrieveScholasticGradeList1(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), 0, Term);

													/*
													 * String finalSEAMarksString = Double.toString(finalSEAMarks);
													 * 
													 * String finalSEAmarks[] = finalSEAMarksString.split("\\.");
													 * 
													 * if (Long.parseLong(finalSEAmarks[1]) > 0) { finalSEAmarksvalue =
													 * Integer.parseInt(finalSEAmarks[0]) + 1;
													 * 
													 * } else {
													 * 
													 * finalSEAmarksvalue = Integer.parseInt(finalSEAmarks[0]); }
													 */

													totalMarksScaled = (value + value1 + finalSEAMarks);

													scaleTo5 = (scaleTo2 + scaleTo3 + scaleTo4) / 3;

													toatlScaleTo = (scaleTo + scaleTo1 + scaleTo5);

													// toatlScaleTo1 = totalMarksScaled;

													toatlScaleTo1 = (totalMarksScaled * 50) / toatlScaleTo;

													boolean termEndCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
															studentsExamFormName.getStudentID(), 0,
															NewExaminationList.get("Term End"));

													boolean unitTestCheck = daoInf2.verifyAbsent(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), 0,
															NewExaminationList.get("Unit Test"));

													boolean SEANBCheck = daoInf2.verifySEANBAbsent(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), 0, Term,
															AcademicYearID);

													if (termEndCheck) {
														grade = "ex";
													} else if (unitTestCheck) {
														grade = "ex";
													} else if (SEANBCheck) {
														grade = "ex";
													} else {
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
													}
													NewGrade += "=" + grade;

													NewGradeNew = "=" + NewFinalGrade + "=" + NewFinalGrade;

													if (studform.getGradeValue().equals("-1")) {

														FinalList = FinalListNew + NewGrade;
													} else {

														if (NewGradeNew.equals(NewGrade)) {

															FinalList = FinalListNew + NewGrade;
														}
													}
													counterAll++;

												} else {

													NewExaminationList = daoInf2.retrieveExaminationList(AcademicYearID,
															studform.getTerm(), AYClassID);

													/* double = 0D; */

													double value, value1, value2, value3, value4, finalOutOfMarks,
															totalMarksScaled, toatlScaleTo1 = 0;
													int marksObtained, marksObtained1, marksObtained2, marksObtained3,
															marksObtained4 = 0;
													double value5, value6, finalSEAMarks = 0D;
													int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5 = 0;
													int toatlScaleTo, outOfMarks, outOfMarks1, outOfMarks2,
															finalSEAmarksvalue = 0;

													value = daoInf.retrievrAllMarksScaledForStudent(
															NewExaminationList.get("Term End"), studform.getSubjectID(),
															studform.getStandardID(), AcademicYearID,
															studentsExamFormName.getStudentID());

													value1 = daoInf.retrievrAllMarksScaledForStudent(
															NewExaminationList.get("Unit Test"),
															studform.getSubjectID(), studform.getStandardID(),
															AcademicYearID, studentsExamFormName.getStudentID());

													value2 = daoInf.retrievrAllMarksScaledForStudent(
															NewExaminationList.get("Subject Enrichment1"),
															studform.getSubjectID(), studform.getStandardID(),
															AcademicYearID, studentsExamFormName.getStudentID());

													value3 = daoInf.retrievrAllMarksScaledForStudent(
															NewExaminationList.get("Subject Enrichment2"),
															studform.getSubjectID(), studform.getStandardID(),
															AcademicYearID, studentsExamFormName.getStudentID());

													value4 = daoInf.retrievrAllMarksScaledForStudent(
															NewExaminationList.get("Notebook"), studform.getSubjectID(),
															studform.getStandardID(), AcademicYearID,
															studentsExamFormName.getStudentID());

													outOfMarks = daoInf.retrievAllTotalMarks(
															NewExaminationList.get("Subject Enrichment1"),
															studform.getSubjectID(), studform.getStandardID(),
															AcademicYearID);

													outOfMarks1 = daoInf.retrievAllTotalMarks(
															NewExaminationList.get("Subject Enrichment2"),
															studform.getSubjectID(), studform.getStandardID(),
															AcademicYearID);

													outOfMarks2 = daoInf.retrievAllTotalMarks(
															NewExaminationList.get("Notebook"), studform.getSubjectID(),
															studform.getStandardID(), AcademicYearID);

													finalOutOfMarks = (outOfMarks + outOfMarks1 + outOfMarks2);

													value5 = (value2 + value3 + value4);

													String numberAsString = Double.toString(value5);

													String marks[] = numberAsString.split("\\.");

													if (Long.parseLong(marks[1]) > 0) {

														value6 = Integer.parseInt(marks[0]) + 1;

													} else {

														value6 = Integer.parseInt(marks[0]);
													}

													scaleTo = daoInf.retrieveAllScaledMarks(studform.getTerm(),
															AcademicYearID, NewExaminationList.get("Term End"),
															studform.getStandardID(), studform.getSubjectID());

													scaleTo1 = daoInf.retrieveAllScaledMarks(studform.getTerm(),
															AcademicYearID, NewExaminationList.get("Unit Test"),
															studform.getStandardID(), studform.getSubjectID());

													scaleTo2 = daoInf.retrieveAllScaledMarks(studform.getTerm(),
															AcademicYearID,
															NewExaminationList.get("Subject Enrichment1"),
															studform.getStandardID(), studform.getSubjectID());

													scaleTo3 = daoInf.retrieveAllScaledMarks(studform.getTerm(),
															AcademicYearID,
															NewExaminationList.get("Subject Enrichment2"),
															studform.getStandardID(), studform.getSubjectID());

													scaleTo4 = daoInf.retrieveAllScaledMarks(studform.getTerm(),
															AcademicYearID, NewExaminationList.get("Notebook"),
															studform.getStandardID(), studform.getSubjectID());

													// finalSEAMarks = (value6 * scaleTo4) / finalOutOfMarks;

													finalSEAMarks = daoInf2.retrieveScholasticGradeList1(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), 0, studform.getTerm());

													/*
													 * String finalSEAMarksString = Double.toString(finalSEAMarks);
													 * 
													 * String finalSEAmarks[] = finalSEAMarksString.split("\\.");
													 * 
													 * if (Long.parseLong(finalSEAmarks[1]) > 0) { finalSEAmarksvalue =
													 * Integer.parseInt(finalSEAmarks[0]) + 1;
													 * 
													 * } else {
													 * 
													 * finalSEAmarksvalue = Integer.parseInt(finalSEAmarks[0]); }
													 */

													// totalMarksScaled = (value + value1 + finalSEAmarksvalue);

													totalMarksScaled = (value + value1 + finalSEAMarks);

													scaleTo5 = (scaleTo2 + scaleTo3 + scaleTo4) / 3;

													toatlScaleTo = (scaleTo + scaleTo1 + scaleTo5);

													/*
													 * if (toatlScaleTo == 50) {
													 * 
													 * toatlScaleTo1 = (totalMarksScaled * 2); } else {
													 */
													// toatlScaleTo1 = totalMarksScaled;

													toatlScaleTo1 = (totalMarksScaled * 50) / toatlScaleTo;

													boolean termEndCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
															studentsExamFormName.getStudentID(), 0,
															NewExaminationList.get("Term End"));

													boolean unitTestCheck = daoInf2.verifyAbsent(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), 0,
															NewExaminationList.get("Unit Test"));

													boolean SEANBCheck = daoInf2.verifySEANBAbsent(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), 0, studform.getTerm(),
															AcademicYearID);

													if (termEndCheck) {

														grade = "ex";
													} else if (unitTestCheck) {
														grade = "ex";
													} else if (SEANBCheck) {
														grade = "ex";
													} else {
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
													}
													if (studform.getGradeValue().equals("-1")) {
														FinalList += studentsExamFormName.getRollNumber() + "="
																+ studentsExamFormName.getStudentName() + "=" + grade;
													} else {

														if (NewFinalGrade.equals(grade)) {
															FinalList += studentsExamFormName.getRollNumber() + "="
																	+ studentsExamFormName.getStudentName() + "="
																	+ grade;
														}
													}
												}

											} else {

												if (studform.getTerm().contains("All")) {

													NewExaminationList = daoInf2.retrieveExaminationList(AcademicYearID,
															Term, AYClassID);

													double value, value1, value2, value3, value4, finalOutOfMarks,
															totalMarksScaled, toatlScaleTo1 = 0;
													int marksObtained, marksObtained1, marksObtained2, marksObtained3,
															marksObtained4 = 0;
													double value5, value6, finalSEAMarks = 0D;
													int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5 = 0;
													int toatlScaleTo, outOfMarks, outOfMarks1, outOfMarks2,
															finalSEAmarksvalue = 0;

													value = daoInf.retrievrAllMarksScaledForStudent(
															NewExaminationList.get("Term End"), studform.getSubjectID(),
															studform.getStandardID(), AcademicYearID,
															studentsExamFormName.getStudentID());

													value1 = daoInf.retrievrAllMarksScaledForStudent(
															NewExaminationList.get("Unit Test"),
															studform.getSubjectID(), studform.getStandardID(),
															AcademicYearID, studentsExamFormName.getStudentID());

													/*
													 * value2 = daoInf.retrievrAllMarksScaledForStudent(
													 * NewExaminationList.get("Subject Enrichment1"),
													 * studform.getSubjectID(), studform.getStandardID(),
													 * AcademicYearID, studentsExamFormName.getStudentID());
													 */

													value2 = daoInf2.retrieveScholasticGradeListNew(
															studform.getSubjectID(), "Subject Enrichment",
															studentsExamFormName.getStudentID(), 0, Term);

													/*
													 * value4 = daoInf.retrievrAllMarksScaledForStudent(
													 * NewExaminationList.get("Notebook"), studform.getSubjectID(),
													 * studform.getStandardID(), AcademicYearID,
													 * studentsExamFormName.getStudentID());
													 */

													value4 = daoInf2.retrieveScholasticGradeListNew(
															studform.getSubjectID(), "Notebook",
															studentsExamFormName.getStudentID(), 0, Term);

													scaleTo = daoInf.retrieveAllScaledMarks(Term, AcademicYearID,
															NewExaminationList.get("Term End"),
															studform.getStandardID(), studform.getSubjectID());

													scaleTo1 = daoInf.retrieveAllScaledMarks(Term, AcademicYearID,
															NewExaminationList.get("Unit Test"),
															studform.getStandardID(), studform.getSubjectID());

													scaleTo2 = daoInf.retrieveAllScaledMarks(Term, AcademicYearID,
															NewExaminationList.get("Subject Enrichment1"),
															studform.getStandardID(), studform.getSubjectID());

													scaleTo4 = daoInf.retrieveAllScaledMarks(Term, AcademicYearID,
															NewExaminationList.get("Notebook"),
															studform.getStandardID(), studform.getSubjectID());

													totalMarksScaled = (value + value1 + value2 + value4);

													toatlScaleTo = (scaleTo + scaleTo1 + scaleTo2 + scaleTo4);

													String SubjectName = daoInf
															.retrieveSubjectBySubjectID(studform.getSubjectID());

													/*
													 * if (SubjectName.equals("Hindi")) { toatlScaleTo1 =
													 * totalMarksScaled; } else { if (toatlScaleTo == 50) {
													 * 
													 * toatlScaleTo1 = (totalMarksScaled * 2); } else if (toatlScaleTo1
													 * > 100) {
													 * 
													 * toatlScaleTo1 = (totalMarksScaled / 200) * 100; } else {
													 * toatlScaleTo1 = totalMarksScaled; } }
													 */

													toatlScaleTo1 = (totalMarksScaled * 100) / toatlScaleTo;

													boolean termEndCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
															studentsExamFormName.getStudentID(), 0,
															NewExaminationList.get("Term End"));

													boolean unitTestCheck = daoInf2.verifyAbsent(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), 0,
															NewExaminationList.get("Unit Test"));

													boolean SEAAbsentCheck = daoInf2.verifySEANBAbsentCheck(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), "Subject Enrichment",
															0, Term, AcademicYearID);

													boolean NBAbsentCheck = daoInf2.verifySEANBAbsentCheck(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), "Notebook", 0, Term,
															AcademicYearID);

													if (termEndCheck) {
														grade = "ex";
													} else if (unitTestCheck) {
														grade = "ex";
													} else if (SEAAbsentCheck) {
														grade = "ex";
													} else if (NBAbsentCheck) {
														grade = "ex";
													} else {
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
													}
													NewGrade += "=" + grade;

													NewGradeNew = "=" + NewFinalGrade + "=" + NewFinalGrade;

													if (studform.getGradeValue().equals("-1")) {

														FinalList = FinalListNew + NewGrade;
													} else {

														if (NewGradeNew.equals(NewGrade)) {

															FinalList = FinalListNew + NewGrade;
														}
													}
													counterAll++;

												} else {

													NewExaminationList = daoInf2.retrieveExaminationList(AcademicYearID,
															studform.getTerm(), AYClassID);

													double value, value1, value2, value3, value4, finalOutOfMarks,
															totalMarksScaled, toatlScaleTo1 = 0;
													int marksObtained, marksObtained1, marksObtained2, marksObtained3,
															marksObtained4 = 0;
													double value5, value6, finalSEAMarks = 0D;
													int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5 = 0;
													int toatlScaleTo, outOfMarks, outOfMarks1, outOfMarks2,
															finalSEAmarksvalue = 0;

													value = daoInf.retrievrAllMarksScaledForStudent(
															NewExaminationList.get("Term End"), studform.getSubjectID(),
															studform.getStandardID(), AcademicYearID,
															studentsExamFormName.getStudentID());

													value1 = daoInf.retrievrAllMarksScaledForStudent(
															NewExaminationList.get("Unit Test"),
															studform.getSubjectID(), studform.getStandardID(),
															AcademicYearID, studentsExamFormName.getStudentID());

													/*
													 * value2 = daoInf.retrievrAllMarksScaledForStudent(
													 * NewExaminationList.get("Subject Enrichment1"),
													 * studform.getSubjectID(), studform.getStandardID(),
													 * AcademicYearID, studentsExamFormName.getStudentID());
													 * 
													 * value4 = daoInf.retrievrAllMarksScaledForStudent(
													 * NewExaminationList.get("Notebook"), studform.getSubjectID(),
													 * studform.getStandardID(), AcademicYearID,
													 * studentsExamFormName.getStudentID());
													 */

													value2 = daoInf2.retrieveScholasticGradeListNew(
															studform.getSubjectID(), "Subject Enrichment",
															studentsExamFormName.getStudentID(), 0, studform.getTerm());

													value4 = daoInf2.retrieveScholasticGradeListNew(
															studform.getSubjectID(), "Notebook",
															studentsExamFormName.getStudentID(), 0, studform.getTerm());

													scaleTo = daoInf.retrieveAllScaledMarks(studform.getTerm(),
															AcademicYearID, NewExaminationList.get("Term End"),
															studform.getStandardID(), studform.getSubjectID());

													scaleTo1 = daoInf.retrieveAllScaledMarks(studform.getTerm(),
															AcademicYearID, NewExaminationList.get("Unit Test"),
															studform.getStandardID(), studform.getSubjectID());

													scaleTo2 = daoInf.retrieveAllScaledMarks(studform.getTerm(),
															AcademicYearID,
															NewExaminationList.get("Subject Enrichment1"),
															studform.getStandardID(), studform.getSubjectID());

													scaleTo4 = daoInf.retrieveAllScaledMarks(studform.getTerm(),
															AcademicYearID, NewExaminationList.get("Notebook"),
															studform.getStandardID(), studform.getSubjectID());

													totalMarksScaled = (value + value1 + value2 + value4);

													toatlScaleTo = (scaleTo + scaleTo1 + scaleTo2 + scaleTo4);

													String SubjectName = daoInf
															.retrieveSubjectBySubjectID(studform.getSubjectID());

													/*
													 * if (SubjectName.equals("Hindi")) { toatlScaleTo1 =
													 * totalMarksScaled; } else { if (toatlScaleTo == 50) {
													 * 
													 * toatlScaleTo1 = (totalMarksScaled * 2); } else if (toatlScaleTo1
													 * > 100) {
													 * 
													 * toatlScaleTo1 = (totalMarksScaled / 200) * 100; } else {
													 * toatlScaleTo1 = totalMarksScaled; } }
													 */

													toatlScaleTo1 = (totalMarksScaled * 100) / toatlScaleTo;

													boolean termEndCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
															studentsExamFormName.getStudentID(), 0,
															NewExaminationList.get("Term End"));

													boolean unitTestCheck = daoInf2.verifyAbsent(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), 0,
															NewExaminationList.get("Unit Test"));

													boolean SEAAbsentCheck = daoInf2.verifySEANBAbsentCheck(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), "Subject Enrichment",
															0, studform.getTerm(), AcademicYearID);

													boolean NBAbsentCheck = daoInf2.verifySEANBAbsentCheck(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), "Notebook", 0,
															studform.getTerm(), AcademicYearID);

													if (termEndCheck) {
														grade = "ex";
													} else if (unitTestCheck) {
														grade = "ex";
													} else if (SEAAbsentCheck) {
														grade = "ex";
													} else if (NBAbsentCheck) {
														grade = "ex";
													} else {

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
													}
													if (studform.getGradeValue().equals("-1")) {
														FinalList += studentsExamFormName.getRollNumber() + "="
																+ studentsExamFormName.getStudentName() + "=" + grade;
													} else {

														if (NewFinalGrade.equals(grade)) {
															FinalList += studentsExamFormName.getRollNumber() + "="
																	+ studentsExamFormName.getStudentName() + "="
																	+ grade;
														}
													}

												}
											}

										} else {

											FinalListNew = studentsExamFormName.getRollNumber() + "="
													+ studentsExamFormName.getStudentName();

											grade = daoInf.retrievrAllGradesForStudent(Integer.parseInt(newList[1]),
													studform.getSubjectID(), studform.getStandardID(), AcademicYearID,
													studentsExamFormName.getStudentID());

											NewGrade += "=" + grade;

											if (studform.getGradeValue().equals("-1")) {

												FinalList = FinalListNew + NewGrade;
											} else {

												if (studform.getTerm().contains("All")) {

													NewGradeNew = "=" + NewFinalGrade + "=" + NewFinalGrade;

													if (NewGradeNew.equals(NewGrade)) {

														FinalList = FinalListNew + NewGrade;
													}
												} else {

													FinalList = FinalListNew + NewGrade;
												}
											}
										}

									} else {

										if (SubjectTypeValue.equals("Scholastic")) {

											FinalListNew = studentsExamFormName.getRollNumber() + "="
													+ studentsExamFormName.getStudentName();

											/*---------------------------------------------------------------*/
											if (Stage.equals("Primary")) {

												if (studform.getTerm().contains("All")) {

													NewExaminationList = daoInf2.retrieveExaminationList(AcademicYearID,
															Term, AYClassID);

													double value, value1, value2, value3, value4, finalOutOfMarks,
															totalMarksScaled, toatlScaleTo1 = 0;
													int marksObtained, marksObtained1, marksObtained2, marksObtained3,
															marksObtained4 = 0;
													double value5, value6, finalSEAMarks = 0D;
													int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5 = 0;
													int toatlScaleTo, outOfMarks, outOfMarks1, outOfMarks2,
															finalSEAmarksvalue = 0;

													value = daoInf.retrievrAllMarksScaledForStudent(
															NewExaminationList.get("Term End"), studform.getSubjectID(),
															studform.getStandardID(), AcademicYearID,
															studentsExamFormName.getStudentID());

													value1 = daoInf.retrievrAllMarksScaledForStudent(
															NewExaminationList.get("Unit Test"),
															studform.getSubjectID(), studform.getStandardID(),
															AcademicYearID, studentsExamFormName.getStudentID());

													value2 = daoInf.retrievrAllMarksScaledForStudent(
															NewExaminationList.get("Subject Enrichment1"),
															studform.getSubjectID(), studform.getStandardID(),
															AcademicYearID, studentsExamFormName.getStudentID());

													value3 = daoInf.retrievrAllMarksScaledForStudent(
															NewExaminationList.get("Subject Enrichment2"),
															studform.getSubjectID(), studform.getStandardID(),
															AcademicYearID, studentsExamFormName.getStudentID());

													value4 = daoInf.retrievrAllMarksScaledForStudent(
															NewExaminationList.get("Notebook"), studform.getSubjectID(),
															studform.getStandardID(), AcademicYearID,
															studentsExamFormName.getStudentID());

													outOfMarks = daoInf.retrievAllTotalMarks(
															NewExaminationList.get("Subject Enrichment1"),
															studform.getSubjectID(), studform.getStandardID(),
															AcademicYearID);

													outOfMarks1 = daoInf.retrievAllTotalMarks(
															NewExaminationList.get("Subject Enrichment2"),
															studform.getSubjectID(), studform.getStandardID(),
															AcademicYearID);

													outOfMarks2 = daoInf.retrievAllTotalMarks(
															NewExaminationList.get("Notebook"), studform.getSubjectID(),
															studform.getStandardID(), AcademicYearID);

													finalOutOfMarks = (outOfMarks + outOfMarks1 + outOfMarks2);

													value5 = (value2 + value3 + value4);

													String numberAsString = Double.toString(value5);

													String marks[] = numberAsString.split("\\.");

													if (Long.parseLong(marks[1]) > 0) {

														value6 = Integer.parseInt(marks[0]) + 1;

													} else {

														value6 = Integer.parseInt(marks[0]);
													}

													scaleTo = daoInf.retrieveAllScaledMarks(Term, AcademicYearID,
															NewExaminationList.get("Term End"),
															studform.getStandardID(), studform.getSubjectID());

													scaleTo1 = daoInf.retrieveAllScaledMarks(Term, AcademicYearID,
															NewExaminationList.get("Unit Test"),
															studform.getStandardID(), studform.getSubjectID());

													scaleTo2 = daoInf.retrieveAllScaledMarks(Term, AcademicYearID,
															NewExaminationList.get("Subject Enrichment1"),
															studform.getStandardID(), studform.getSubjectID());

													scaleTo3 = daoInf.retrieveAllScaledMarks(Term, AcademicYearID,
															NewExaminationList.get("Subject Enrichment2"),
															studform.getStandardID(), studform.getSubjectID());

													scaleTo4 = daoInf.retrieveAllScaledMarks(Term, AcademicYearID,
															NewExaminationList.get("Notebook"),
															studform.getStandardID(), studform.getSubjectID());

													// finalSEAMarks = (value6 * scaleTo4) / finalOutOfMarks;

													finalSEAMarks = daoInf2.retrieveScholasticGradeList1(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), AYClassID, Term);

													/*
													 * String finalSEAMarksString = Double.toString(finalSEAMarks);
													 * 
													 * String finalSEAmarks[] = finalSEAMarksString.split("\\.");
													 * 
													 * if (Long.parseLong(finalSEAmarks[1]) > 0) { finalSEAmarksvalue =
													 * Integer.parseInt(finalSEAmarks[0]) + 1;
													 * 
													 * } else {
													 * 
													 * finalSEAmarksvalue = Integer.parseInt(finalSEAmarks[0]); }
													 */

													// totalMarksScaled = (value + value1 + finalSEAmarksvalue);

													totalMarksScaled = (value + value1 + finalSEAMarks);

													scaleTo5 = (scaleTo2 + scaleTo3 + scaleTo4) / 3;

													toatlScaleTo = (scaleTo + scaleTo1 + scaleTo5);

													// toatlScaleTo1 = totalMarksScaled;

													toatlScaleTo1 = (totalMarksScaled * 50) / toatlScaleTo;

													boolean termEndCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
															studentsExamFormName.getStudentID(), AYClassID,
															NewExaminationList.get("Term End"));

													boolean unitTestCheck = daoInf2.verifyAbsent(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), AYClassID,
															NewExaminationList.get("Unit Test"));

													boolean SEANBCheck = daoInf2.verifySEANBAbsent(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), AYClassID, Term,
															AcademicYearID);

													if (termEndCheck) {
														grade = "ex";
													} else if (unitTestCheck) {
														grade = "ex";
													} else if (SEANBCheck) {
														grade = "ex";
													} else {

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
													}

													NewGrade += "=" + grade;

													NewGradeNew = "=" + NewFinalGrade + "=" + NewFinalGrade;

													if (studform.getGradeValue().equals("-1")) {

														FinalList = FinalListNew + NewGrade;
													} else {

														if (NewGradeNew.equals(NewGrade)) {

															FinalList = FinalListNew + NewGrade;
														}
													}
													counterAll++;

												} else {

													NewExaminationList = daoInf2.retrieveExaminationList(AcademicYearID,
															studform.getTerm(), AYClassID);

													/* double = 0D; */

													double value, value1, value2, value3, value4, finalOutOfMarks,
															totalMarksScaled, toatlScaleTo1 = 0;
													int marksObtained, marksObtained1, marksObtained2, marksObtained3,
															marksObtained4 = 0;
													double value5, value6, finalSEAMarks = 0D;
													int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5 = 0;
													int toatlScaleTo, outOfMarks, outOfMarks1, outOfMarks2,
															finalSEAmarksvalue = 0;

													value = daoInf.retrievrMarksScaledForStudent(
															NewExaminationList.get("Term End"), studform.getSubjectID(),
															studform.getStandardID(),
															Integer.parseInt(studform.getDivision()), AcademicYearID,
															studentsExamFormName.getStudentID());

													value1 = daoInf.retrievrMarksScaledForStudent(
															NewExaminationList.get("Unit Test"),
															studform.getSubjectID(), studform.getStandardID(),
															Integer.parseInt(studform.getDivision()), AcademicYearID,
															studentsExamFormName.getStudentID());

													value2 = daoInf.retrievrMarksScaledForStudent(
															NewExaminationList.get("Subject Enrichment1"),
															studform.getSubjectID(), studform.getStandardID(),
															Integer.parseInt(studform.getDivision()), AcademicYearID,
															studentsExamFormName.getStudentID());

													value3 = daoInf.retrievrMarksScaledForStudent(
															NewExaminationList.get("Subject Enrichment2"),
															studform.getSubjectID(), studform.getStandardID(),
															Integer.parseInt(studform.getDivision()), AcademicYearID,
															studentsExamFormName.getStudentID());

													value4 = daoInf.retrievrMarksScaledForStudent(
															NewExaminationList.get("Notebook"), studform.getSubjectID(),
															studform.getStandardID(),
															Integer.parseInt(studform.getDivision()), AcademicYearID,
															studentsExamFormName.getStudentID());

													outOfMarks = daoInf.retrievTotalMarks(
															NewExaminationList.get("Subject Enrichment1"),
															studform.getSubjectID(), studform.getStandardID(),
															Integer.parseInt(studform.getDivision()), AcademicYearID);

													outOfMarks1 = daoInf.retrievTotalMarks(
															NewExaminationList.get("Subject Enrichment2"),
															studform.getSubjectID(), studform.getStandardID(),
															Integer.parseInt(studform.getDivision()), AcademicYearID);

													outOfMarks2 = daoInf.retrievTotalMarks(
															NewExaminationList.get("Notebook"), studform.getSubjectID(),
															studform.getStandardID(),
															Integer.parseInt(studform.getDivision()), AcademicYearID);

													finalOutOfMarks = (outOfMarks + outOfMarks1 + outOfMarks2);

													value5 = (value2 + value3 + value4);

													String numberAsString = Double.toString(value5);

													String marks[] = numberAsString.split("\\.");

													if (Long.parseLong(marks[1]) > 0) {

														value6 = Integer.parseInt(marks[0]) + 1;

													} else {

														value6 = Integer.parseInt(marks[0]);
													}

													scaleTo = daoInf.retrieveScaledMarksNew(studform.getTerm(),
															AcademicYearID, NewExaminationList.get("Term End"),
															studform.getStandardID(),
															Integer.parseInt(studform.getDivision()),
															studform.getSubjectID());

													scaleTo1 = daoInf.retrieveScaledMarksNew(studform.getTerm(),
															AcademicYearID, NewExaminationList.get("Unit Test"),
															studform.getStandardID(),
															Integer.parseInt(studform.getDivision()),
															studform.getSubjectID());

													scaleTo2 = daoInf.retrieveScaledMarksNew(studform.getTerm(),
															AcademicYearID,
															NewExaminationList.get("Subject Enrichment1"),
															studform.getStandardID(),
															Integer.parseInt(studform.getDivision()),
															studform.getSubjectID());

													scaleTo3 = daoInf.retrieveScaledMarksNew(studform.getTerm(),
															AcademicYearID,
															NewExaminationList.get("Subject Enrichment2"),
															studform.getStandardID(),
															Integer.parseInt(studform.getDivision()),
															studform.getSubjectID());

													scaleTo4 = daoInf.retrieveScaledMarksNew(studform.getTerm(),
															AcademicYearID, NewExaminationList.get("Notebook"),
															studform.getStandardID(),
															Integer.parseInt(studform.getDivision()),
															studform.getSubjectID());

													// finalSEAMarks = (value6 * scaleTo4) / finalOutOfMarks;

													finalSEAMarks = daoInf2.retrieveScholasticGradeList1(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), AYClassID,
															studform.getTerm());

													/*
													 * String finalSEAMarksString = Double.toString(finalSEAMarks);
													 * 
													 * String finalSEAmarks[] = finalSEAMarksString.split("\\.");
													 * 
													 * if (Long.parseLong(finalSEAmarks[1]) > 0) { finalSEAmarksvalue =
													 * Integer.parseInt(finalSEAmarks[0]) + 1;
													 * 
													 * } else {
													 * 
													 * finalSEAmarksvalue = Integer.parseInt(finalSEAmarks[0]); }
													 */

													// totalMarksScaled = (value + value1 + finalSEAmarksvalue);

													totalMarksScaled = (value + value1 + finalSEAMarks);

													scaleTo5 = (scaleTo2 + scaleTo3 + scaleTo4) / 3;

													toatlScaleTo = (scaleTo + scaleTo1 + scaleTo5);

													// toatlScaleTo1 = totalMarksScaled;

													toatlScaleTo1 = (totalMarksScaled * 50) / toatlScaleTo;

													boolean termEndCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
															studentsExamFormName.getStudentID(), AYClassID,
															NewExaminationList.get("Term End"));

													boolean unitTestCheck = daoInf2.verifyAbsent(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), AYClassID,
															NewExaminationList.get("Unit Test"));

													boolean SEANBCheck = daoInf2.verifySEANBAbsent(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), AYClassID,
															studform.getTerm(), AcademicYearID);

													if (termEndCheck) {

														grade = "ex";
													} else if (unitTestCheck) {
														grade = "ex";
													} else if (SEANBCheck) {
														grade = "ex";
													} else {

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
													}
													if (studform.getGradeValue().equals("-1")) {
														FinalList += studentsExamFormName.getRollNumber() + "="
																+ studentsExamFormName.getStudentName() + "=" + grade;
													} else {

														if (NewFinalGrade.equals(grade)) {
															FinalList += studentsExamFormName.getRollNumber() + "="
																	+ studentsExamFormName.getStudentName() + "="
																	+ grade;
														}
													}
												}

											} else {

												if (studform.getTerm().contains("All")) {

													NewExaminationList = daoInf2.retrieveExaminationList(AcademicYearID,
															Term, AYClassID);

													double value, value1, value2, value3, value4, finalOutOfMarks,
															totalMarksScaled, toatlScaleTo1 = 0;
													int marksObtained, marksObtained1, marksObtained2, marksObtained3,
															marksObtained4 = 0;
													double value5, value6, finalSEAMarks = 0D;
													int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5 = 0;
													int toatlScaleTo, outOfMarks, outOfMarks1, outOfMarks2,
															finalSEAmarksvalue = 0;

													value = daoInf.retrievrAllMarksScaledForStudent(
															NewExaminationList.get("Term End"), studform.getSubjectID(),
															studform.getStandardID(), AcademicYearID,
															studentsExamFormName.getStudentID());

													value1 = daoInf.retrievrAllMarksScaledForStudent(
															NewExaminationList.get("Unit Test"),
															studform.getSubjectID(), studform.getStandardID(),
															AcademicYearID, studentsExamFormName.getStudentID());

													/*
													 * value2 = daoInf.retrievrAllMarksScaledForStudent(
													 * NewExaminationList.get("Subject Enrichment1"),
													 * studform.getSubjectID(), studform.getStandardID(),
													 * AcademicYearID, studentsExamFormName.getStudentID());
													 * 
													 * value4 = daoInf.retrievrAllMarksScaledForStudent(
													 * NewExaminationList.get("Notebook"), studform.getSubjectID(),
													 * studform.getStandardID(), AcademicYearID,
													 * studentsExamFormName.getStudentID());
													 */

													value2 = daoInf2.retrieveScholasticGradeListNew(
															studform.getSubjectID(), "Subject Enrichment",
															studentsExamFormName.getStudentID(), AYClassID, Term);

													value4 = daoInf2.retrieveScholasticGradeListNew(
															studform.getSubjectID(), "Notebook",
															studentsExamFormName.getStudentID(), AYClassID, Term);

													scaleTo = daoInf.retrieveAllScaledMarks(Term, AcademicYearID,
															NewExaminationList.get("Term End"),
															studform.getStandardID(), studform.getSubjectID());

													scaleTo1 = daoInf.retrieveAllScaledMarks(Term, AcademicYearID,
															NewExaminationList.get("Unit Test"),
															studform.getStandardID(), studform.getSubjectID());

													scaleTo2 = daoInf.retrieveAllScaledMarks(Term, AcademicYearID,
															NewExaminationList.get("Subject Enrichment1"),
															studform.getStandardID(), studform.getSubjectID());

													scaleTo4 = daoInf.retrieveAllScaledMarks(Term, AcademicYearID,
															NewExaminationList.get("Notebook"),
															studform.getStandardID(), studform.getSubjectID());

													totalMarksScaled = (value + value1 + value2 + value4);

													toatlScaleTo = (scaleTo + scaleTo1 + scaleTo2 + scaleTo4);

													String SubjectName = daoInf
															.retrieveSubjectBySubjectID(studform.getSubjectID());

													/*
													 * if (SubjectName.equals("Hindi")) { toatlScaleTo1 =
													 * totalMarksScaled; } else { if (toatlScaleTo == 50) {
													 * 
													 * toatlScaleTo1 = (totalMarksScaled * 2); } else if (toatlScaleTo1
													 * > 100) {
													 * 
													 * toatlScaleTo1 = (totalMarksScaled / 200) * 100; } else {
													 * toatlScaleTo1 = totalMarksScaled; } }
													 */

													toatlScaleTo1 = (totalMarksScaled * 100) / toatlScaleTo;

													boolean termEndCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
															studentsExamFormName.getStudentID(), AYClassID,
															NewExaminationList.get("Term End"));

													boolean unitTestCheck = daoInf2.verifyAbsent(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), AYClassID,
															NewExaminationList.get("Unit Test"));

													boolean SEAAbsentCheck = daoInf2.verifySEANBAbsentCheck(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), "Subject Enrichment",
															AYClassID, Term, AcademicYearID);

													boolean NBAbsentCheck = daoInf2.verifySEANBAbsentCheck(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), "Notebook", AYClassID,
															Term, AcademicYearID);

													if (termEndCheck) {
														grade = "ex";
													} else if (unitTestCheck) {
														grade = "ex";
													} else if (SEAAbsentCheck) {
														grade = "ex";
													} else if (NBAbsentCheck) {
														grade = "ex";
													} else {
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
													}

													NewGrade += "=" + grade;

													NewGradeNew = "=" + NewFinalGrade + "=" + NewFinalGrade;

													if (studform.getGradeValue().equals("-1")) {

														FinalList = FinalListNew + NewGrade;
													} else {

														if (NewGradeNew.equals(NewGrade)) {

															FinalList = FinalListNew + NewGrade;
														}
													}
													counterAll++;

												} else {

													NewExaminationList = daoInf2.retrieveExaminationList(AcademicYearID,
															studform.getTerm(), AYClassID);

													double value, value1, value2, value3, value4, finalOutOfMarks,
															totalMarksScaled, toatlScaleTo1 = 0;
													int marksObtained, marksObtained1, marksObtained2, marksObtained3,
															marksObtained4 = 0;
													double value5, value6, finalSEAMarks = 0D;
													int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5 = 0;
													int toatlScaleTo, outOfMarks, outOfMarks1, outOfMarks2,
															finalSEAmarksvalue = 0;

													value = daoInf.retrievrMarksScaledForStudent(
															NewExaminationList.get("Term End"), studform.getSubjectID(),
															studform.getStandardID(),
															Integer.parseInt(studform.getDivision()), AcademicYearID,
															studentsExamFormName.getStudentID());

													value1 = daoInf.retrievrMarksScaledForStudent(
															NewExaminationList.get("Unit Test"),
															studform.getSubjectID(), studform.getStandardID(),
															Integer.parseInt(studform.getDivision()), AcademicYearID,
															studentsExamFormName.getStudentID());

													/*
													 * value2 = daoInf.retrievrMarksScaledForStudent(
													 * NewExaminationList.get("Subject Enrichment1"),
													 * studform.getSubjectID(), studform.getStandardID(),
													 * Integer.parseInt(studform.getDivision()), AcademicYearID,
													 * studentsExamFormName.getStudentID());
													 * 
													 * value4 = daoInf.retrievrMarksScaledForStudent(
													 * NewExaminationList.get("Notebook"), studform.getSubjectID(),
													 * studform.getStandardID(),
													 * Integer.parseInt(studform.getDivision()), AcademicYearID,
													 * studentsExamFormName.getStudentID());
													 */

													value2 = daoInf2.retrieveScholasticGradeListNew(
															studform.getSubjectID(), "Subject Enrichment",
															studentsExamFormName.getStudentID(), AYClassID,
															studform.getTerm());

													value4 = daoInf2.retrieveScholasticGradeListNew(
															studform.getSubjectID(), "Notebook",
															studentsExamFormName.getStudentID(), AYClassID,
															studform.getTerm());

													scaleTo = daoInf.retrieveScaledMarksNew(studform.getTerm(),
															AcademicYearID, NewExaminationList.get("Term End"),
															studform.getStandardID(),
															Integer.parseInt(studform.getDivision()),
															studform.getSubjectID());

													scaleTo1 = daoInf.retrieveScaledMarksNew(studform.getTerm(),
															AcademicYearID, NewExaminationList.get("Unit Test"),
															studform.getStandardID(),
															Integer.parseInt(studform.getDivision()),
															studform.getSubjectID());

													scaleTo2 = daoInf.retrieveScaledMarksNew(studform.getTerm(),
															AcademicYearID,
															NewExaminationList.get("Subject Enrichment1"),
															studform.getStandardID(),
															Integer.parseInt(studform.getDivision()),
															studform.getSubjectID());

													scaleTo4 = daoInf.retrieveScaledMarksNew(studform.getTerm(),
															AcademicYearID, NewExaminationList.get("Notebook"),
															studform.getStandardID(),
															Integer.parseInt(studform.getDivision()),
															studform.getSubjectID());

													totalMarksScaled = (value + value1 + value2 + value4);

													toatlScaleTo = (scaleTo + scaleTo1 + scaleTo2 + scaleTo4);

													String SubjectName = daoInf
															.retrieveSubjectBySubjectID(studform.getSubjectID());

													/*
													 * if (SubjectName.equals("Hindi")) { toatlScaleTo1 =
													 * totalMarksScaled; } else { if (toatlScaleTo == 50) {
													 * 
													 * toatlScaleTo1 = (totalMarksScaled * 2); } else if (toatlScaleTo1
													 * > 100) {
													 * 
													 * toatlScaleTo1 = (totalMarksScaled / 200) * 100; } else {
													 * toatlScaleTo1 = totalMarksScaled; } }
													 */

													toatlScaleTo1 = (totalMarksScaled * 100) / toatlScaleTo;

													boolean termEndCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
															studentsExamFormName.getStudentID(), AYClassID,
															NewExaminationList.get("Term End"));

													boolean unitTestCheck = daoInf2.verifyAbsent(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), AYClassID,
															NewExaminationList.get("Unit Test"));

													boolean SEAAbsentCheck = daoInf2.verifySEANBAbsentCheck(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), "Subject Enrichment",
															AYClassID, studform.getTerm(), AcademicYearID);

													boolean NBAbsentCheck = daoInf2.verifySEANBAbsentCheck(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), "Notebook", AYClassID,
															studform.getTerm(), AcademicYearID);

													if (termEndCheck) {
														grade = "ex";
													} else if (unitTestCheck) {
														grade = "ex";
													} else if (SEAAbsentCheck) {
														grade = "ex";
													} else if (NBAbsentCheck) {
														grade = "ex";
													} else {
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
													}

													if (studform.getGradeValue().equals("-1")) {
														FinalList += studentsExamFormName.getRollNumber() + "="
																+ studentsExamFormName.getStudentName() + "=" + grade;
													} else {

														if (NewFinalGrade.equals(grade)) {
															FinalList += studentsExamFormName.getRollNumber() + "="
																	+ studentsExamFormName.getStudentName() + "="
																	+ grade;
														}
													}
												}

											}

											/*---------------------------------------------------------------*/

										} else {

											FinalListNew = studentsExamFormName.getRollNumber() + "="
													+ studentsExamFormName.getStudentName();

											grade = daoInf.retrievrGradesForStudent(Integer.parseInt(newList[1]),
													studform.getSubjectID(), studform.getStandardID(),
													Integer.parseInt(studform.getDivision()), AcademicYearID,
													studentsExamFormName.getStudentID());

											NewGrade += "=" + grade;

											if (studform.getGradeValue().equals("-1")) {

												FinalList = FinalListNew + NewGrade;
											} else {

												if (studform.getTerm().contains("All")) {

													NewGradeNew = "=" + NewFinalGrade + "=" + NewFinalGrade;

													if (NewGradeNew.equals(NewGrade)) {

														FinalList = FinalListNew + NewGrade;
													}
												} else {

													FinalList = FinalListNew + NewGrade;
												}
											}
										}
									}

								} else {

									FinalListNew = studentsExamFormName.getRollNumber() + "="
											+ studentsExamFormName.getStudentName();

									if (studform.getDivision().contains(",")) {
										if (Stage.equals("Primary")) {
											if (studform.getMarks().equals("marksObtained")) {

												Marks = daoInf.retrievrAllMarksForStudent(Integer.parseInt(newList[1]),
														studform.getSubjectID(), studform.getStandardID(),
														AcademicYearID, studentsExamFormName.getStudentID());

												FinalListMarks += "=" + Marks;

											} else {

												if (studform.getTotalValueString().equals("Total")) {

													/*
													 * int check = daoInf.verifyExamType(Integer.parseInt(newList[1]),
													 * "Subject Enrichment");
													 */

													String exampType = daoInf
															.retrieveExaminationType(Integer.parseInt(newList[1]));

													/*
													 * int ScaledMarks = daoInf.retrieveAllScaledMarks(AcademicYearID,
													 * studform.getStandardID(), studform.getSubjectID());
													 */

													String subjectList = "Subject Enrichment, Notebook";

													if (subjectList.contains(exampType)) {

														counter++;

														OutOfMarks = daoInf.retrievAllTotalMarks(
																Integer.parseInt(newList[1]), studform.getSubjectID(),
																studform.getStandardID(), AcademicYearID);

														FinalOutOfMarks = FinalOutOfMarks + OutOfMarks;

														/*
														 * MarksNew = daoInf.retrievrAllMarksForStudent(
														 * Integer.parseInt(newList[1]), studform.getSubjectID(),
														 * studform.getStandardID(), AcademicYearID,
														 * studentsExamFormName.getStudentID());
														 */

														/*
														 * MarksNew = daoInf2.retrieveMaxScaledMarksAllForStudent(
														 * studform.getSubjectID(), "Subject Enrichment",
														 * studentsExamFormName.getStudentID(),
														 * studform.getStandardID(), AcademicYearID);
														 */

														Marks = daoInf.retrievrAllMarksScaledForStudent(
																Integer.parseInt(newList[1]), studform.getSubjectID(),
																studform.getStandardID(), AcademicYearID,
																studentsExamFormName.getStudentID());

														// FinalTotalMarks = FinalTotalMarks + MarksNew;

														if (SENBCheck == 1 && seCheckCounter == 1) {

															if (studform.getTerm().equals("All")) {

																double SENBBestMarks = daoInf2
																		.retrieveScholasticGradeList1(
																				studform.getSubjectID(),
																				studentsExamFormName.getStudentID(), 0,
																				"Term I");

																double SENBBestMarksTermII = daoInf2
																		.retrieveScholasticGradeList1(
																				studform.getSubjectID(),
																				studentsExamFormName.getStudentID(), 0,
																				"Term II");

																TotalMarks = TotalMarks + SENBBestMarks
																		+ SENBBestMarksTermII;

																FinalSEANBMarks = FinalSEANBMarks + "=" + SENBBestMarks
																		+ "=" + SENBBestMarksTermII;

																seCheckCounter++;

															} else {

																double SENBBestMarks = daoInf2
																		.retrieveScholasticGradeList1(
																				studform.getSubjectID(),
																				studentsExamFormName.getStudentID(), 0,
																				studform.getTerm());

																TotalMarks = TotalMarks + SENBBestMarks;

																FinalSEANBMarks = FinalSEANBMarks + "=" + SENBBestMarks;

																seCheckCounter++;
															}

															// System.out.println("TotalMarks...." + TotalMarks);

														}

														/*
														 * NewMarks = (FinalTotalMarks / FinalOutOfMarks) * ScaledMarks;
														 * 
														 * String numberAsString = Double.toString(NewMarks);
														 * 
														 * String valueMarks[] = numberAsString.split("\\.");
														 * 
														 * if (Long.parseLong(valueMarks[1]) > 0) {
														 * 
														 * Marks = Integer.parseInt(valueMarks[0]) + 1;
														 * 
														 * } else {
														 * 
														 * Marks = Integer.parseInt(valueMarks[0]); }
														 */

														if (counter > 2) {

															TotalMarks = TotalMarks + FinalTotalMarks;
															// System.out.println("TotalMarks: "+TotalMarks);

															// System.out.println("TotalMarks 1 " + TotalMarks);

															boolean termEndCheck = daoInf2.verifySEANBAbsentCheck(
																	studform.getSubjectID(),
																	studentsExamFormName.getStudentID(), "Term End", 0,
																	studform.getTerm(), AcademicYearID);

															boolean unitTestCheck = daoInf2.verifySEANBAbsentCheck(
																	studform.getSubjectID(),
																	studentsExamFormName.getStudentID(), "Unit Test", 0,
																	studform.getTerm(), AcademicYearID);

															boolean SEANBCheck = daoInf2.verifySEANBAbsent(
																	studform.getSubjectID(),
																	studentsExamFormName.getStudentID(), 0, Term,
																	AcademicYearID);

															if (termEndCheck) {
																Studentgrade = "ex";
															} else if (unitTestCheck) {
																Studentgrade = "ex";
															} else if (SEANBCheck) {
																Studentgrade = "ex";
															} else {

																if (studform.getTerm().contains("All")) {

																	/* Calculating Final Grades */
																	if (TotalMarks >= 91 && TotalMarks <= 100) {
																		Studentgrade = "A1";
																	} else if (TotalMarks >= 81 && TotalMarks <= 90) {
																		Studentgrade = "A2";
																	} else if (TotalMarks >= 71 && TotalMarks <= 80) {
																		Studentgrade = "B1";
																	} else if (TotalMarks >= 61 && TotalMarks <= 70) {
																		Studentgrade = "B2";
																	} else if (TotalMarks >= 51 && TotalMarks <= 60) {
																		Studentgrade = "C1";
																	} else if (TotalMarks >= 41 && TotalMarks <= 50) {
																		Studentgrade = "C2";
																	} else if (TotalMarks >= 33 && TotalMarks <= 40) {
																		Studentgrade = "D";
																	} else {

																		Studentgrade = "E";
																	}
																	// System.out.println("Studentgrade:
																	// "+Studentgrade);
																} else {

																	/* Calculating Final Grades */
																	if (TotalMarks >= 46 && TotalMarks <= 50) {
																		Studentgrade = "A1";
																	} else if (TotalMarks >= 41 && TotalMarks <= 45) {
																		Studentgrade = "A2";
																	} else if (TotalMarks >= 36 && TotalMarks <= 40) {
																		Studentgrade = "B1";
																	} else if (TotalMarks >= 31 && TotalMarks <= 35) {
																		Studentgrade = "B2";
																	} else if (TotalMarks >= 26 && TotalMarks <= 30) {
																		Studentgrade = "C1";
																	} else if (TotalMarks >= 21 && TotalMarks <= 25) {
																		Studentgrade = "C2";
																	} else if (TotalMarks >= 17 && TotalMarks <= 20) {
																		Studentgrade = "D";
																	} else {
																		Studentgrade = "E";
																	}

																	// System.out.println("Studentgrade 1 :
																	// "+Studentgrade);

																}

															}

															counter = 0;
														}

														FinalListMarks += "=" + Marks;
													} else {

														Marks = daoInf.retrievrAllMarksScaledForStudent(
																Integer.parseInt(newList[1]), studform.getSubjectID(),
																studform.getStandardID(), AcademicYearID,
																studentsExamFormName.getStudentID());

														// System.out.println("..Marks..."+Marks);

														TotalMarks = TotalMarks + Marks;
														/* Calculating Final Grades */

														System.out.println("..TotalMarks..." + TotalMarks);

														boolean termEndCheck = daoInf2.verifySEANBAbsentCheck(
																studform.getSubjectID(),
																studentsExamFormName.getStudentID(), "Term End", 0,
																studform.getTerm(), AcademicYearID);

														boolean unitTestCheck = daoInf2.verifySEANBAbsentCheck(
																studform.getSubjectID(),
																studentsExamFormName.getStudentID(), "Unit Test", 0,
																studform.getTerm(), AcademicYearID);

														boolean SEANBCheck = daoInf2.verifySEANBAbsent(
																studform.getSubjectID(),
																studentsExamFormName.getStudentID(), 0, Term,
																AcademicYearID);

														if (termEndCheck) {
															Studentgrade = "ex";
														} else if (unitTestCheck) {
															Studentgrade = "ex";
														} else if (SEANBCheck) {
															Studentgrade = "ex";
														} else {

															if (studform.getTerm().contains("All")) {

																/* Calculating Final Grades */
																if (TotalMarks >= 91 && TotalMarks <= 100) {
																	Studentgrade = "A1";
																} else if (TotalMarks >= 81 && TotalMarks <= 90) {
																	Studentgrade = "A2";
																} else if (TotalMarks >= 71 && TotalMarks <= 80) {
																	Studentgrade = "B1";
																} else if (TotalMarks >= 61 && TotalMarks <= 70) {
																	Studentgrade = "B2";
																} else if (TotalMarks >= 51 && TotalMarks <= 60) {
																	Studentgrade = "C1";
																} else if (TotalMarks >= 41 && TotalMarks <= 50) {
																	Studentgrade = "C2";
																} else if (TotalMarks >= 33 && TotalMarks <= 40) {
																	Studentgrade = "D";
																} else {

																	Studentgrade = "E";
																}
																// System.out.println("Studentgrade: "+Studentgrade);
															} else {

																/* Calculating Final Grades */
																if (TotalMarks >= 46 && TotalMarks <= 50) {
																	Studentgrade = "A1";
																} else if (TotalMarks >= 41 && TotalMarks <= 45) {
																	Studentgrade = "A2";
																} else if (TotalMarks >= 36 && TotalMarks <= 40) {
																	Studentgrade = "B1";
																} else if (TotalMarks >= 31 && TotalMarks <= 35) {
																	Studentgrade = "B2";
																} else if (TotalMarks >= 26 && TotalMarks <= 30) {
																	Studentgrade = "C1";
																} else if (TotalMarks >= 21 && TotalMarks <= 25) {
																	Studentgrade = "C2";
																} else if (TotalMarks >= 17 && TotalMarks <= 20) {
																	Studentgrade = "D";
																} else {
																	Studentgrade = "E";
																}

																// System.out.println("Studentgrade 1 : "+Studentgrade);

															}
														}

														FinalListMarks += "=" + Marks;
													}

												} else {

													Marks = daoInf.retrievrAllMarksScaledForStudent(
															Integer.parseInt(newList[1]), studform.getSubjectID(),
															studform.getStandardID(), AcademicYearID,
															studentsExamFormName.getStudentID());

													FinalListMarks += "=" + Marks;
												}
											}

										} else {
											if (studform.getMarks().equals("marksObtained")) {

												Marks = daoInf.retrievrAllMarksForStudent(Integer.parseInt(newList[1]),
														studform.getSubjectID(), studform.getStandardID(),
														AcademicYearID, studentsExamFormName.getStudentID());

												FinalListMarks += "=" + Marks;

											} else {

												if (studform.getTotalValueString().equals("Total")) {

													String examType = daoInf
															.retrieveExaminationType(Integer.parseInt(newList[1]));

													String subjectList = "Subject Enrichment, Notebook";

													Marks = daoInf.retrievrAllMarksScaledForStudent(
															Integer.parseInt(newList[1]), studform.getSubjectID(),
															studform.getStandardID(), AcademicYearID,
															studentsExamFormName.getStudentID());

													if (!subjectList.contains(examType)) {
														TotalMarks = TotalMarks + Marks;
													}

													String SubjectName = daoInf
															.retrieveSubjectBySubjectID(studform.getSubjectID());

													if (studform.getTerm().equals("All")) {
														if (SubjectName.equals("Hindi")) {
															TotalMarksGrade1 = TotalMarks;
														} else {
															TotalMarksGrade1 = (TotalMarks / 200) * 100;
														}

													} else {
														TotalMarksGrade1 = TotalMarks;
													}

													String numberAsString = Double.toString(TotalMarksGrade1);

													String marks[] = numberAsString.split("\\.");

													if (Long.parseLong(marks[1]) > 0) {

														TotalMarksGrade = Integer.parseInt(marks[0]) + 1;

													} else {

														TotalMarksGrade = Integer.parseInt(marks[0]);
													}

													boolean termEndCheck = daoInf2.verifySEANBAbsentCheck(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), "Term End", 0,
															studform.getTerm(), AcademicYearID);

													boolean unitTestCheck = daoInf2.verifySEANBAbsentCheck(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), "Unit Test", 0,
															studform.getTerm(), AcademicYearID);

													boolean SEAAbsentCheck = daoInf2.verifySEANBAbsentCheck(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), "Subject Enrichment",
															0, studform.getTerm(), AcademicYearID);

													boolean NBAbsentCheck = daoInf2.verifySEANBAbsentCheck(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), "Notebook", 0,
															studform.getTerm(), AcademicYearID);

													if (termEndCheck) {
														Studentgrade = "ex";
													} else if (unitTestCheck) {
														Studentgrade = "ex";
													} else if (SEAAbsentCheck) {
														Studentgrade = "ex";
													} else if (NBAbsentCheck) {
														Studentgrade = "ex";
													} else {

														/* Calculating Final Grades */
														if (TotalMarksGrade >= 91 && TotalMarksGrade <= 100) {
															Studentgrade = "A1";
														} else if (TotalMarksGrade >= 81 && TotalMarksGrade <= 90) {
															Studentgrade = "A2";
														} else if (TotalMarksGrade >= 71 && TotalMarksGrade <= 80) {
															Studentgrade = "B1";
														} else if (TotalMarksGrade >= 61 && TotalMarksGrade <= 70) {
															Studentgrade = "B2";
														} else if (TotalMarksGrade >= 51 && TotalMarksGrade <= 60) {
															Studentgrade = "C1";
														} else if (TotalMarksGrade >= 41 && TotalMarksGrade <= 50) {
															Studentgrade = "C2";
														} else if (TotalMarksGrade >= 33 && TotalMarksGrade <= 40) {
															Studentgrade = "D";
														} else {
															Studentgrade = "E";
														}
													}

													FinalListMarks += "=" + Marks;

													if (SECheck == 1 && examType.equals("Subject Enrichment")) {

														if (studform.getTerm().equals("All")) {
															double maxSEA = daoInf2.retrieveScholasticGradeListNew(
																	studform.getSubjectID(), "Subject Enrichment",
																	studentsExamFormName.getStudentID(), 0, "Term I");

															double maxSEATerm2 = daoInf2.retrieveScholasticGradeListNew(
																	studform.getSubjectID(), "Subject Enrichment",
																	studentsExamFormName.getStudentID(), 0, "Term II");

															if (SECheckCounter == 0) {
																// FinalListMarks += "=" + maxSEA;
																FinalSEANBMarks += "=" + maxSEA + "=" + maxSEATerm2;

																TotalMarks = TotalMarks + maxSEA + maxSEATerm2;

																if (studform.getTerm().equals("All")) {
																	if (SubjectName.equals("Hindi")) {
																		TotalMarksGrade1 = TotalMarks;
																	} else {
																		TotalMarksGrade1 = (TotalMarks / 200) * 100;
																	}

																} else {
																	TotalMarksGrade1 = TotalMarks;
																}

																String numberAsString1 = Double
																		.toString(TotalMarksGrade1);

																String marks1[] = numberAsString1.split("\\.");

																if (Long.parseLong(marks1[1]) > 0) {

																	TotalMarksGrade = Integer.parseInt(marks1[0]) + 1;

																} else {

																	TotalMarksGrade = Integer.parseInt(marks1[0]);
																}

																if (termEndCheck) {
																	Studentgrade = "ex";
																} else if (unitTestCheck) {
																	Studentgrade = "ex";
																} else if (SEAAbsentCheck) {
																	Studentgrade = "ex";
																} else if (NBAbsentCheck) {
																	Studentgrade = "ex";
																} else {
																	/* Calculating Final Grades */
																	if (TotalMarksGrade >= 91
																			&& TotalMarksGrade <= 100) {
																		Studentgrade = "A1";
																	} else if (TotalMarksGrade >= 81
																			&& TotalMarksGrade <= 90) {
																		Studentgrade = "A2";
																	} else if (TotalMarksGrade >= 71
																			&& TotalMarksGrade <= 80) {
																		Studentgrade = "B1";
																	} else if (TotalMarksGrade >= 61
																			&& TotalMarksGrade <= 70) {
																		Studentgrade = "B2";
																	} else if (TotalMarksGrade >= 51
																			&& TotalMarksGrade <= 60) {
																		Studentgrade = "C1";
																	} else if (TotalMarksGrade >= 41
																			&& TotalMarksGrade <= 50) {
																		Studentgrade = "C2";
																	} else if (TotalMarksGrade >= 33
																			&& TotalMarksGrade <= 40) {
																		Studentgrade = "D";
																	} else {
																		Studentgrade = "E";
																	}
																}

															}

															SECheckCounter++;

														} else {
															double maxSEA = daoInf2.retrieveScholasticGradeListNew(
																	studform.getSubjectID(), "Subject Enrichment",
																	studentsExamFormName.getStudentID(), 0,
																	studform.getTerm());

															if (SECheckCounter == 0) {
																// FinalListMarks += "=" + maxSEA;
																FinalSEANBMarks += "=" + maxSEA;

																TotalMarks = TotalMarks + maxSEA;

																if (studform.getTerm().equals("All")) {
																	if (SubjectName.equals("Hindi")) {
																		TotalMarksGrade1 = TotalMarks;
																	} else {
																		TotalMarksGrade1 = (TotalMarks / 200) * 100;
																	}

																} else {
																	TotalMarksGrade1 = TotalMarks;
																}

																String numberAsString1 = Double
																		.toString(TotalMarksGrade1);

																String marks1[] = numberAsString1.split("\\.");

																if (Long.parseLong(marks1[1]) > 0) {

																	TotalMarksGrade = Integer.parseInt(marks1[0]) + 1;

																} else {

																	TotalMarksGrade = Integer.parseInt(marks1[0]);
																}

																if (termEndCheck) {
																	Studentgrade = "ex";
																} else if (unitTestCheck) {
																	Studentgrade = "ex";
																} else if (SEAAbsentCheck) {
																	Studentgrade = "ex";
																} else if (NBAbsentCheck) {
																	Studentgrade = "ex";
																} else {
																	/* Calculating Final Grades */
																	if (TotalMarksGrade >= 91
																			&& TotalMarksGrade <= 100) {
																		Studentgrade = "A1";
																	} else if (TotalMarksGrade >= 81
																			&& TotalMarksGrade <= 90) {
																		Studentgrade = "A2";
																	} else if (TotalMarksGrade >= 71
																			&& TotalMarksGrade <= 80) {
																		Studentgrade = "B1";
																	} else if (TotalMarksGrade >= 61
																			&& TotalMarksGrade <= 70) {
																		Studentgrade = "B2";
																	} else if (TotalMarksGrade >= 51
																			&& TotalMarksGrade <= 60) {
																		Studentgrade = "C1";
																	} else if (TotalMarksGrade >= 41
																			&& TotalMarksGrade <= 50) {
																		Studentgrade = "C2";
																	} else if (TotalMarksGrade >= 33
																			&& TotalMarksGrade <= 40) {
																		Studentgrade = "D";
																	} else {
																		Studentgrade = "E";
																	}
																}

															}

															SECheckCounter++;
														}
													}

													if (NBCheck == 1 && examType.equals("Notebook")) {

														if (studform.getTerm().equals("All")) {
															double maxNB = daoInf2.retrieveScholasticGradeListNew(
																	studform.getSubjectID(), "Notebook",
																	studentsExamFormName.getStudentID(), 0, "Term I");

															double maxNBTerm2 = daoInf2.retrieveScholasticGradeListNew(
																	studform.getSubjectID(), "Notebook",
																	studentsExamFormName.getStudentID(), 0, "Term II");

															if (NBCheckCounter == 0) {
																// FinalListMarks += "=" + maxSEA;
																FinalSEANB1Marks += "=" + maxNB + "=" + maxNBTerm2;

																TotalMarks = TotalMarks + maxNB + maxNBTerm2;

																if (studform.getTerm().equals("All")) {
																	if (SubjectName.equals("Hindi")) {
																		TotalMarksGrade1 = TotalMarks;
																	} else {
																		TotalMarksGrade1 = (TotalMarks / 200) * 100;
																	}

																} else {
																	TotalMarksGrade1 = TotalMarks;
																}

																String numberAsString1 = Double
																		.toString(TotalMarksGrade1);

																String marks1[] = numberAsString1.split("\\.");

																if (Long.parseLong(marks1[1]) > 0) {

																	TotalMarksGrade = Integer.parseInt(marks1[0]) + 1;

																} else {

																	TotalMarksGrade = Integer.parseInt(marks1[0]);
																}

																/* Calculating Final Grades */

																if (termEndCheck) {
																	Studentgrade = "ex";
																} else if (unitTestCheck) {
																	Studentgrade = "ex";
																} else if (SEAAbsentCheck) {
																	Studentgrade = "ex";
																} else if (NBAbsentCheck) {
																	Studentgrade = "ex";
																} else {
																	if (TotalMarksGrade >= 91
																			&& TotalMarksGrade <= 100) {
																		Studentgrade = "A1";
																	} else if (TotalMarksGrade >= 81
																			&& TotalMarksGrade <= 90) {
																		Studentgrade = "A2";
																	} else if (TotalMarksGrade >= 71
																			&& TotalMarksGrade <= 80) {
																		Studentgrade = "B1";
																	} else if (TotalMarksGrade >= 61
																			&& TotalMarksGrade <= 70) {
																		Studentgrade = "B2";
																	} else if (TotalMarksGrade >= 51
																			&& TotalMarksGrade <= 60) {
																		Studentgrade = "C1";
																	} else if (TotalMarksGrade >= 41
																			&& TotalMarksGrade <= 50) {
																		Studentgrade = "C2";
																	} else if (TotalMarksGrade >= 33
																			&& TotalMarksGrade <= 40) {
																		Studentgrade = "D";
																	} else {
																		Studentgrade = "E";
																	}
																}
															}

															NBCheckCounter++;

														} else {
															double maxNB = daoInf2.retrieveScholasticGradeListNew(
																	studform.getSubjectID(), "Notebook",
																	studentsExamFormName.getStudentID(), 0,
																	studform.getTerm());

															if (NBCheckCounter == 0) {
																// FinalListMarks += "=" + maxNB;
																FinalSEANB1Marks += "=" + maxNB;

																TotalMarks = TotalMarks + maxNB;

																if (studform.getTerm().equals("All")) {
																	if (SubjectName.equals("Hindi")) {
																		TotalMarksGrade1 = TotalMarks;
																	} else {
																		TotalMarksGrade1 = (TotalMarks / 200) * 100;
																	}

																} else {
																	TotalMarksGrade1 = TotalMarks;
																}

																String numberAsString1 = Double
																		.toString(TotalMarksGrade1);

																String marks1[] = numberAsString1.split("\\.");

																if (Long.parseLong(marks1[1]) > 0) {

																	TotalMarksGrade = Integer.parseInt(marks1[0]) + 1;

																} else {

																	TotalMarksGrade = Integer.parseInt(marks1[0]);
																}

																/* Calculating Final Grades */
																if (termEndCheck) {
																	Studentgrade = "ex";
																} else if (unitTestCheck) {
																	Studentgrade = "ex";
																} else if (SEAAbsentCheck) {
																	Studentgrade = "ex";
																} else if (NBAbsentCheck) {
																	Studentgrade = "ex";
																} else {
																	if (TotalMarksGrade >= 91
																			&& TotalMarksGrade <= 100) {
																		Studentgrade = "A1";
																	} else if (TotalMarksGrade >= 81
																			&& TotalMarksGrade <= 90) {
																		Studentgrade = "A2";
																	} else if (TotalMarksGrade >= 71
																			&& TotalMarksGrade <= 80) {
																		Studentgrade = "B1";
																	} else if (TotalMarksGrade >= 61
																			&& TotalMarksGrade <= 70) {
																		Studentgrade = "B2";
																	} else if (TotalMarksGrade >= 51
																			&& TotalMarksGrade <= 60) {
																		Studentgrade = "C1";
																	} else if (TotalMarksGrade >= 41
																			&& TotalMarksGrade <= 50) {
																		Studentgrade = "C2";
																	} else if (TotalMarksGrade >= 33
																			&& TotalMarksGrade <= 40) {
																		Studentgrade = "D";
																	} else {
																		Studentgrade = "E";
																	}
																}
															}
															NBCheckCounter++;
														}
													}

												} else {

													Marks = daoInf.retrievrAllMarksScaledForStudent(
															Integer.parseInt(newList[1]), studform.getSubjectID(),
															studform.getStandardID(), AcademicYearID,
															studentsExamFormName.getStudentID());

													FinalListMarks += "=" + Marks;

													String examType = daoInf
															.retrieveExaminationType(Integer.parseInt(newList[1]));

													if (SECheck == 1 && examType.equals("Subject Enrichment")) {

														if (studform.getTerm().equals("All")) {
															double maxSEA = daoInf2.retrieveScholasticGradeListNew(
																	studform.getSubjectID(), "Subject Enrichment",
																	studentsExamFormName.getStudentID(), 0, "Term I");

															double maxSEATerm2 = daoInf2.retrieveScholasticGradeListNew(
																	studform.getSubjectID(), "Subject Enrichment",
																	studentsExamFormName.getStudentID(), 0, "Term II");

															if (SECheckCounter == 0) {
																// FinalListMarks += "=" + maxSEA;
																FinalSEANBMarks += "=" + maxSEA + "=" + maxSEATerm2;

															}

															SECheckCounter++;

														} else {
															double maxSEA = daoInf2.retrieveScholasticGradeListNew(
																	studform.getSubjectID(), "Subject Enrichment",
																	studentsExamFormName.getStudentID(), 0,
																	studform.getTerm());

															if (SECheckCounter == 0) {
																// FinalListMarks += "=" + maxSEA;
																FinalSEANBMarks += "=" + maxSEA;
															}

															SECheckCounter++;
														}
													}

													if (NBCheck == 1 && examType.equals("Notebook")) {

														if (studform.getTerm().equals("All")) {
															double maxNB = daoInf2.retrieveScholasticGradeListNew(
																	studform.getSubjectID(), "Notebook",
																	studentsExamFormName.getStudentID(), 0, "Term I");

															double maxNBTerm2 = daoInf2.retrieveScholasticGradeListNew(
																	studform.getSubjectID(), "Notebook",
																	studentsExamFormName.getStudentID(), 0, "Term II");

															if (NBCheckCounter == 0) {
																// FinalListMarks += "=" + maxSEA;
																FinalSEANB1Marks += "=" + maxNB + "=" + maxNBTerm2;
															}

															NBCheckCounter++;

														} else {
															double maxNB = daoInf2.retrieveScholasticGradeListNew(
																	studform.getSubjectID(), "Notebook",
																	studentsExamFormName.getStudentID(), 0,
																	studform.getTerm());

															if (NBCheckCounter == 0) {
																// FinalListMarks += "=" + maxNB;
																FinalSEANB1Marks += "=" + maxNB;
															}

															NBCheckCounter++;
														}
													}
												}
											}

										}

									} else {

										if (Stage.equals("Primary")) {
											if (studform.getMarks().equals("marksObtained")) {

												Marks = daoInf.retrievrMarksForStudent(Integer.parseInt(newList[1]),
														studform.getSubjectID(), studform.getStandardID(),
														Integer.parseInt(studform.getDivision()), AcademicYearID,
														studentsExamFormName.getStudentID());

												FinalListMarks += "=" + Marks;

											} else {

												if (studform.getTotalValueString().equals("Total")) {

													int check = daoInf.verifyExamType(Integer.parseInt(newList[1]), "");
													String exampType = daoInf
															.retrieveExaminationType(Integer.parseInt(newList[1]));

													/*
													 * int ScaledMarks = daoInf.retrieveAllScaledMarks(AcademicYearID,
													 * studform.getStandardID(), studform.getSubjectID());
													 */

													String subjectList = "Subject Enrichment, Notebook";

													if (subjectList.contains(exampType)) {

														counter++;

														OutOfMarks = daoInf.retrievTotalMarks(
																Integer.parseInt(newList[1]), studform.getSubjectID(),
																studform.getStandardID(),
																Integer.parseInt(studform.getDivision()),
																AcademicYearID);

														FinalOutOfMarks = FinalOutOfMarks + OutOfMarks;

														/*
														 * MarksNew = daoInf.retrievrMarksForStudent(
														 * Integer.parseInt(newList[1]), studform.getSubjectID(),
														 * studform.getStandardID(),
														 * Integer.parseInt(studform.getDivision()), AcademicYearID,
														 * studentsExamFormName.getStudentID());
														 */

														Marks = daoInf.retrievrAllMarksScaledForStudent(
																Integer.parseInt(newList[1]), studform.getSubjectID(),
																studform.getStandardID(), AcademicYearID,
																studentsExamFormName.getStudentID());

														// FinalTotalMarks = FinalTotalMarks + MarksNew;

														System.out.println("SENBCheck.." + SENBCheck);

														if (SENBCheck == 1 && seCheckCounter == 1) {
															/*
															 * double SENBBestMarks =
															 * daoInf2.retrieveScholasticGradeList1(studform.
															 * getSubjectID(), studentsExamFormName.getStudentID(),
															 * AYClassID, studform.getTerm());
															 * 
															 * System.out.println("SENBBestMarks...."+SENBBestMarks);
															 * 
															 * TotalMarks = TotalMarks + SENBBestMarks;
															 * 
															 * FinalSEANBMarks = FinalSEANBMarks + "=" + SENBBestMarks;
															 * 
															 * seCheckCounter++;
															 */

															if (studform.getTerm().equals("All")) {

																double SENBBestMarks = daoInf2
																		.retrieveScholasticGradeList1(
																				studform.getSubjectID(),
																				studentsExamFormName.getStudentID(),
																				AYClassID, "Term I");

																double SENBBestMarksTermII = daoInf2
																		.retrieveScholasticGradeList1(
																				studform.getSubjectID(),
																				studentsExamFormName.getStudentID(),
																				AYClassID, "Term II");

																System.out.println("SENBBestMarks...." + SENBBestMarks);

																TotalMarks = TotalMarks + SENBBestMarks
																		+ SENBBestMarksTermII;

																FinalSEANBMarks = FinalSEANBMarks + "=" + SENBBestMarks
																		+ "=" + SENBBestMarksTermII;

																seCheckCounter++;

															} else {

																double SENBBestMarks = daoInf2
																		.retrieveScholasticGradeList1(
																				studform.getSubjectID(),
																				studentsExamFormName.getStudentID(),
																				AYClassID, studform.getTerm());

																System.out.println("SENBBestMarks...." + SENBBestMarks);

																TotalMarks = TotalMarks + SENBBestMarks;

																FinalSEANBMarks = FinalSEANBMarks + "=" + SENBBestMarks;

																seCheckCounter++;
															}

														}

														/*
														 * NewMarks = (FinalTotalMarks / FinalOutOfMarks) * ScaledMarks;
														 * 
														 * String numberAsString = Double.toString(NewMarks);
														 * 
														 * String valueMarks[] = numberAsString.split("\\.");
														 * 
														 * if (Long.parseLong(valueMarks[1]) > 0) {
														 * 
														 * Marks = Integer.parseInt(valueMarks[0]) + 1;
														 * 
														 * } else {
														 * 
														 * Marks = Integer.parseInt(valueMarks[0]); }
														 */

														if (counter > 2) {

															TotalMarks = TotalMarks + FinalTotalMarks;
															// System.out.println("TotalMarks: "+TotalMarks);

															boolean termEndCheck = daoInf2.verifySEANBAbsentCheck(
																	studform.getSubjectID(),
																	studentsExamFormName.getStudentID(), "Term End",
																	AYClassID, studform.getTerm(), AcademicYearID);

															boolean unitTestCheck = daoInf2.verifySEANBAbsentCheck(
																	studform.getSubjectID(),
																	studentsExamFormName.getStudentID(), "Unit Test",
																	AYClassID, studform.getTerm(), AcademicYearID);

															boolean SEANBCheck = daoInf2.verifySEANBAbsent(
																	studform.getSubjectID(),
																	studentsExamFormName.getStudentID(), AYClassID,
																	Term, AcademicYearID);

															if (termEndCheck) {
																Studentgrade = "ex";
															} else if (unitTestCheck) {
																Studentgrade = "ex";
															} else if (SEANBCheck) {
																Studentgrade = "ex";
															} else {

																if (studform.getTerm().contains("All")) {
																	/* Calculating Final Grades */

																	if (TotalMarks >= 91 && TotalMarks <= 100) {
																		Studentgrade = "A1";
																	} else if (TotalMarks >= 81 && TotalMarks <= 90) {
																		Studentgrade = "A2";
																	} else if (TotalMarks >= 71 && TotalMarks <= 80) {
																		Studentgrade = "B1";
																	} else if (TotalMarks >= 61 && TotalMarks <= 70) {
																		Studentgrade = "B2";
																	} else if (TotalMarks >= 51 && TotalMarks <= 60) {
																		Studentgrade = "C1";
																	} else if (TotalMarks >= 41 && TotalMarks <= 50) {
																		Studentgrade = "C2";
																	} else if (TotalMarks >= 33 && TotalMarks <= 40) {
																		Studentgrade = "D";
																	} else {
																		Studentgrade = "E";
																	}

																} else {
																	/* Calculating Final Grades */

																	if (TotalMarks >= 46 && TotalMarks <= 50) {
																		Studentgrade = "A1";
																	} else if (TotalMarks >= 41 && TotalMarks <= 45) {
																		Studentgrade = "A2";
																	} else if (TotalMarks >= 36 && TotalMarks <= 40) {
																		Studentgrade = "B1";
																	} else if (TotalMarks >= 31 && TotalMarks <= 35) {
																		Studentgrade = "B2";
																	} else if (TotalMarks >= 26 && TotalMarks <= 30) {
																		Studentgrade = "C1";
																	} else if (TotalMarks >= 21 && TotalMarks <= 25) {
																		Studentgrade = "C2";
																	} else if (TotalMarks >= 17 && TotalMarks <= 20) {
																		Studentgrade = "D";
																	} else {
																		Studentgrade = "E";
																	}

																}
															}

															counter = 0;
														}

														FinalListMarks += "=" + Marks;

													} else {

														Marks = daoInf.retrievrMarksScaledForStudent(
																Integer.parseInt(newList[1]), studform.getSubjectID(),
																studform.getStandardID(),
																Integer.parseInt(studform.getDivision()),
																AcademicYearID, studentsExamFormName.getStudentID());

														TotalMarks = TotalMarks + Marks;

														boolean termEndCheck = daoInf2.verifySEANBAbsentCheck(
																studform.getSubjectID(),
																studentsExamFormName.getStudentID(), "Term End",
																AYClassID, studform.getTerm(), AcademicYearID);

														boolean unitTestCheck = daoInf2.verifySEANBAbsentCheck(
																studform.getSubjectID(),
																studentsExamFormName.getStudentID(), "Unit Test",
																AYClassID, studform.getTerm(), AcademicYearID);

														boolean SEANBCheck = daoInf2.verifySEANBAbsent(
																studform.getSubjectID(),
																studentsExamFormName.getStudentID(), AYClassID, Term,
																AcademicYearID);
														if (termEndCheck) {
															Studentgrade = "ex";
														} else if (unitTestCheck) {
															Studentgrade = "ex";
														} else if (SEANBCheck) {
															Studentgrade = "ex";
														} else {

															/* Calculating Final Grades */
															if (TotalMarks >= 46 && TotalMarks <= 50) {
																Studentgrade = "A1";
															} else if (TotalMarks >= 41 && TotalMarks <= 45) {
																Studentgrade = "A2";
															} else if (TotalMarks >= 36 && TotalMarks <= 40) {
																Studentgrade = "B1";
															} else if (TotalMarks >= 31 && TotalMarks <= 35) {
																Studentgrade = "B2";
															} else if (TotalMarks >= 26 && TotalMarks <= 30) {
																Studentgrade = "C1";
															} else if (TotalMarks >= 21 && TotalMarks <= 25) {
																Studentgrade = "C2";
															} else if (TotalMarks >= 17 && TotalMarks <= 20) {
																Studentgrade = "D";
															} else {
																Studentgrade = "E";
															}

														}
														FinalListMarks += "=" + Marks;

													}

												} else {

													Marks = daoInf.retrievrMarksScaledForStudent(
															Integer.parseInt(newList[1]), studform.getSubjectID(),
															studform.getStandardID(),
															Integer.parseInt(studform.getDivision()), AcademicYearID,
															studentsExamFormName.getStudentID());

													FinalListMarks += "=" + Marks;
												}
											}

										} else {
											if (studform.getMarks().equals("marksObtained")) {

												Marks = daoInf.retrievrMarksForStudent(Integer.parseInt(newList[1]),
														studform.getSubjectID(), studform.getStandardID(),
														Integer.parseInt(studform.getDivision()), AcademicYearID,
														studentsExamFormName.getStudentID());

												FinalListMarks += "=" + Marks;

											} else {

												if (studform.getTotalValueString().equals("Total")) {

													String examType = daoInf
															.retrieveExaminationType(Integer.parseInt(newList[1]));

													String subjectList = "Subject Enrichment, Notebook";

													String SubjectName = daoInf
															.retrieveSubjectBySubjectID(studform.getSubjectID());

													Marks = daoInf.retrievrMarksScaledForStudent(
															Integer.parseInt(newList[1]), studform.getSubjectID(),
															studform.getStandardID(),
															Integer.parseInt(studform.getDivision()), AcademicYearID,
															studentsExamFormName.getStudentID());

													if (!subjectList.contains(examType)) {
														TotalMarks = TotalMarks + Marks;
													}

													if (studform.getTerm().equals("All")) {
														if (SubjectName.equals("Hindi")) {
															TotalMarksGrade = TotalMarks;
														} else {
															TotalMarksGrade = (TotalMarks / 200) * 100;
														}

													} else {
														TotalMarksGrade = TotalMarks;
													}

													/* Calculating Final Grades */

													boolean termEndCheck = daoInf2.verifySEANBAbsentCheck(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), "Term End", AYClassID,
															studform.getTerm(), AcademicYearID);

													boolean unitTestCheck = daoInf2.verifySEANBAbsentCheck(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), "Unit Test", AYClassID,
															studform.getTerm(), AcademicYearID);

													boolean SEAAbsentCheck = daoInf2.verifySEANBAbsentCheck(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), "Subject Enrichment",
															AYClassID, studform.getTerm(), AcademicYearID);

													boolean NBAbsentCheck = daoInf2.verifySEANBAbsentCheck(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), "Notebook", AYClassID,
															studform.getTerm(), AcademicYearID);

													if (termEndCheck) {
														Studentgrade = "ex";
													} else if (unitTestCheck) {
														Studentgrade = "ex";
													} else if (SEAAbsentCheck) {
														Studentgrade = "ex";
													} else if (NBAbsentCheck) {
														Studentgrade = "ex";
													} else {

														if (TotalMarksGrade >= 91 && TotalMarksGrade <= 100) {
															Studentgrade = "A1";
														} else if (TotalMarksGrade >= 81 && TotalMarksGrade <= 90) {
															Studentgrade = "A2";
														} else if (TotalMarksGrade >= 71 && TotalMarksGrade <= 80) {
															Studentgrade = "B1";
														} else if (TotalMarksGrade >= 61 && TotalMarksGrade <= 70) {
															Studentgrade = "B2";
														} else if (TotalMarksGrade >= 51 && TotalMarksGrade <= 60) {
															Studentgrade = "C1";
														} else if (TotalMarksGrade >= 41 && TotalMarksGrade <= 50) {
															Studentgrade = "C2";
														} else if (TotalMarksGrade >= 33 && TotalMarksGrade <= 40) {
															Studentgrade = "D";
														} else {
															Studentgrade = "E";
														}
													}

													FinalListMarks += "=" + Marks;

													if (SECheck == 1 && examType.equals("Subject Enrichment")) {

														if (studform.getTerm().equals("All")) {
															double maxSEA = daoInf2.retrieveScholasticGradeListNew(
																	studform.getSubjectID(), "Subject Enrichment",
																	studentsExamFormName.getStudentID(), AYClassID,
																	"Term I");

															double maxSEATerm2 = daoInf2.retrieveScholasticGradeListNew(
																	studform.getSubjectID(), "Subject Enrichment",
																	studentsExamFormName.getStudentID(), AYClassID,
																	"Term II");

															if (SECheckCounter == 0) {
																// FinalListMarks += "=" + maxSEA;
																FinalSEANBMarks += "=" + maxSEA + "=" + maxSEATerm2;

																TotalMarks = TotalMarks + maxSEA + maxSEATerm2;

																if (studform.getTerm().equals("All")) {
																	if (SubjectName.equals("Hindi")) {
																		TotalMarksGrade = TotalMarks;
																	} else {
																		TotalMarksGrade = (TotalMarks / 200) * 100;
																	}

																} else {
																	TotalMarksGrade = TotalMarks;
																}

																/* Calculating Final Grades */

																if (termEndCheck) {
																	Studentgrade = "ex";
																} else if (unitTestCheck) {
																	Studentgrade = "ex";
																} else if (SEAAbsentCheck) {
																	Studentgrade = "ex";
																} else if (NBAbsentCheck) {
																	Studentgrade = "ex";
																} else {
																	if (TotalMarksGrade >= 91
																			&& TotalMarksGrade <= 100) {
																		Studentgrade = "A1";
																	} else if (TotalMarksGrade >= 81
																			&& TotalMarksGrade <= 90) {
																		Studentgrade = "A2";
																	} else if (TotalMarksGrade >= 71
																			&& TotalMarksGrade <= 80) {
																		Studentgrade = "B1";
																	} else if (TotalMarksGrade >= 61
																			&& TotalMarksGrade <= 70) {
																		Studentgrade = "B2";
																	} else if (TotalMarksGrade >= 51
																			&& TotalMarksGrade <= 60) {
																		Studentgrade = "C1";
																	} else if (TotalMarksGrade >= 41
																			&& TotalMarksGrade <= 50) {
																		Studentgrade = "C2";
																	} else if (TotalMarksGrade >= 33
																			&& TotalMarksGrade <= 40) {
																		Studentgrade = "D";
																	} else {
																		Studentgrade = "E";
																	}
																}

															}

															SECheckCounter++;

														} else {
															double maxSEA = daoInf2.retrieveScholasticGradeListNew(
																	studform.getSubjectID(), "Subject Enrichment",
																	studentsExamFormName.getStudentID(), AYClassID,
																	studform.getTerm());

															if (SECheckCounter == 0) {
																// FinalListMarks += "=" + maxSEA;
																FinalSEANBMarks += "=" + maxSEA;

																TotalMarks = TotalMarks + maxSEA;

																if (studform.getTerm().equals("All")) {
																	if (SubjectName.equals("Hindi")) {
																		TotalMarksGrade = TotalMarks;
																	} else {
																		TotalMarksGrade = (TotalMarks / 200) * 100;
																	}

																} else {
																	TotalMarksGrade = TotalMarks;
																}

																/* Calculating Final Grades */
																if (termEndCheck) {
																	Studentgrade = "ex";
																} else if (unitTestCheck) {
																	Studentgrade = "ex";
																} else if (SEAAbsentCheck) {
																	Studentgrade = "ex";
																} else if (NBAbsentCheck) {
																	Studentgrade = "ex";
																} else {

																	if (TotalMarksGrade >= 91
																			&& TotalMarksGrade <= 100) {
																		Studentgrade = "A1";
																	} else if (TotalMarksGrade >= 81
																			&& TotalMarksGrade <= 90) {
																		Studentgrade = "A2";
																	} else if (TotalMarksGrade >= 71
																			&& TotalMarksGrade <= 80) {
																		Studentgrade = "B1";
																	} else if (TotalMarksGrade >= 61
																			&& TotalMarksGrade <= 70) {
																		Studentgrade = "B2";
																	} else if (TotalMarksGrade >= 51
																			&& TotalMarksGrade <= 60) {
																		Studentgrade = "C1";
																	} else if (TotalMarksGrade >= 41
																			&& TotalMarksGrade <= 50) {
																		Studentgrade = "C2";
																	} else if (TotalMarksGrade >= 33
																			&& TotalMarksGrade <= 40) {
																		Studentgrade = "D";
																	} else {
																		Studentgrade = "E";
																	}
																}
															}

															SECheckCounter++;
														}
													}

													if (NBCheck == 1 && examType.equals("Notebook")) {

														if (studform.getTerm().equals("All")) {
															double maxNB = daoInf2.retrieveScholasticGradeListNew(
																	studform.getSubjectID(), "Notebook",
																	studentsExamFormName.getStudentID(), AYClassID,
																	"Term I");

															double maxNBTerm2 = daoInf2.retrieveScholasticGradeListNew(
																	studform.getSubjectID(), "Notebook",
																	studentsExamFormName.getStudentID(), AYClassID,
																	"Term II");

															if (NBCheckCounter == 0) {
																// FinalListMarks += "=" + maxSEA;
																FinalSEANB1Marks += "=" + maxNB + "=" + maxNBTerm2;

																TotalMarks = TotalMarks + maxNB + maxNBTerm2;

																if (studform.getTerm().equals("All")) {
																	if (SubjectName.equals("Hindi")) {
																		TotalMarksGrade = TotalMarks;
																	} else {
																		TotalMarksGrade = (TotalMarks / 200) * 100;
																	}

																} else {
																	TotalMarksGrade = TotalMarks;
																}

																/* Calculating Final Grades */
																if (termEndCheck) {
																	Studentgrade = "ex";
																} else if (unitTestCheck) {
																	Studentgrade = "ex";
																} else if (SEAAbsentCheck) {
																	Studentgrade = "ex";
																} else if (NBAbsentCheck) {
																	Studentgrade = "ex";
																} else {
																	if (TotalMarksGrade >= 91
																			&& TotalMarksGrade <= 100) {
																		Studentgrade = "A1";
																	} else if (TotalMarksGrade >= 81
																			&& TotalMarksGrade <= 90) {
																		Studentgrade = "A2";
																	} else if (TotalMarksGrade >= 71
																			&& TotalMarksGrade <= 80) {
																		Studentgrade = "B1";
																	} else if (TotalMarksGrade >= 61
																			&& TotalMarksGrade <= 70) {
																		Studentgrade = "B2";
																	} else if (TotalMarksGrade >= 51
																			&& TotalMarksGrade <= 60) {
																		Studentgrade = "C1";
																	} else if (TotalMarksGrade >= 41
																			&& TotalMarksGrade <= 50) {
																		Studentgrade = "C2";
																	} else if (TotalMarksGrade >= 33
																			&& TotalMarksGrade <= 40) {
																		Studentgrade = "D";
																	} else {
																		Studentgrade = "E";
																	}
																}

															}

															NBCheckCounter++;

														} else {
															double maxNB = daoInf2.retrieveScholasticGradeListNew(
																	studform.getSubjectID(), "Notebook",
																	studentsExamFormName.getStudentID(), AYClassID,
																	studform.getTerm());

															if (NBCheckCounter == 0) {
																// FinalListMarks += "=" + maxNB;
																FinalSEANB1Marks += "=" + maxNB;

																TotalMarks = TotalMarks + maxNB;

																if (studform.getTerm().equals("All")) {
																	if (SubjectName.equals("Hindi")) {
																		TotalMarksGrade = TotalMarks;
																	} else {
																		TotalMarksGrade = (TotalMarks / 200) * 100;
																	}

																} else {
																	TotalMarksGrade = TotalMarks;
																}

																/* Calculating Final Grades */

																if (termEndCheck) {
																	Studentgrade = "ex";
																} else if (unitTestCheck) {
																	Studentgrade = "ex";
																} else if (SEAAbsentCheck) {
																	Studentgrade = "ex";
																} else if (NBAbsentCheck) {
																	Studentgrade = "ex";
																} else {
																	if (TotalMarksGrade >= 91
																			&& TotalMarksGrade <= 100) {
																		Studentgrade = "A1";
																	} else if (TotalMarksGrade >= 81
																			&& TotalMarksGrade <= 90) {
																		Studentgrade = "A2";
																	} else if (TotalMarksGrade >= 71
																			&& TotalMarksGrade <= 80) {
																		Studentgrade = "B1";
																	} else if (TotalMarksGrade >= 61
																			&& TotalMarksGrade <= 70) {
																		Studentgrade = "B2";
																	} else if (TotalMarksGrade >= 51
																			&& TotalMarksGrade <= 60) {
																		Studentgrade = "C1";
																	} else if (TotalMarksGrade >= 41
																			&& TotalMarksGrade <= 50) {
																		Studentgrade = "C2";
																	} else if (TotalMarksGrade >= 33
																			&& TotalMarksGrade <= 40) {
																		Studentgrade = "D";
																	} else {
																		Studentgrade = "E";
																	}
																}

															}

															NBCheckCounter++;
														}
													}
												} else {

													String examType = daoInf
															.retrieveExaminationType(Integer.parseInt(newList[1]));

													String subjectList = "Subject Enrichment, Notebook";

													Marks = daoInf.retrievrMarksScaledForStudent(
															Integer.parseInt(newList[1]), studform.getSubjectID(),
															studform.getStandardID(),
															Integer.parseInt(studform.getDivision()), AcademicYearID,
															studentsExamFormName.getStudentID());

													FinalListMarks += "=" + Marks;

													if (SECheck == 1 && examType.equals("Subject Enrichment")) {

														if (studform.getTerm().equals("All")) {
															double maxSEA = daoInf2.retrieveScholasticGradeListNew(
																	studform.getSubjectID(), "Subject Enrichment",
																	studentsExamFormName.getStudentID(), AYClassID,
																	"Term I");

															double maxSEATerm2 = daoInf2.retrieveScholasticGradeListNew(
																	studform.getSubjectID(), "Subject Enrichment",
																	studentsExamFormName.getStudentID(), AYClassID,
																	"Term II");

															if (SECheckCounter == 0) {
																// FinalListMarks += "=" + maxSEA;
																FinalSEANBMarks += "=" + maxSEA + "=" + maxSEATerm2;

															}

															SECheckCounter++;

														} else {
															double maxSEA = daoInf2.retrieveScholasticGradeListNew(
																	studform.getSubjectID(), "Subject Enrichment",
																	studentsExamFormName.getStudentID(), AYClassID,
																	studform.getTerm());

															if (SECheckCounter == 0) {
																// FinalListMarks += "=" + maxSEA;
																FinalSEANBMarks += "=" + maxSEA;
															}

															SECheckCounter++;
														}
													}

													if (NBCheck == 1 && examType.equals("Notebook")) {

														if (studform.getTerm().equals("All")) {
															double maxNB = daoInf2.retrieveScholasticGradeListNew(
																	studform.getSubjectID(), "Notebook",
																	studentsExamFormName.getStudentID(), AYClassID,
																	"Term I");

															double maxNBTerm2 = daoInf2.retrieveScholasticGradeListNew(
																	studform.getSubjectID(), "Notebook",
																	studentsExamFormName.getStudentID(), AYClassID,
																	"Term II");

															if (NBCheckCounter == 0) {
																// FinalListMarks += "=" + maxSEA;
																FinalSEANB1Marks += "=" + maxNB + "=" + maxNBTerm2;
															}

															NBCheckCounter++;

														} else {
															double maxNB = daoInf2.retrieveScholasticGradeListNew(
																	studform.getSubjectID(), "Notebook",
																	studentsExamFormName.getStudentID(), AYClassID,
																	studform.getTerm());

															if (NBCheckCounter == 0) {
																// FinalListMarks += "=" + maxNB;
																FinalSEANB1Marks += "=" + maxNB;
															}

															NBCheckCounter++;
														}
													}
												}
											}
										}
									}

									FinalList = FinalListNew + FinalListMarks + FinalSEANBMarks + FinalSEANB1Marks;
									System.out.println("FinalList: " + FinalList);
								}
							}

						} else {

							String[] newList = checkBoxList.split("\\$");

							if (studform.getContainsName().equals("GradeBased")) {

								if (studform.getDivision().contains(",")) {

									if (SubjectTypeValue.equals("Scholastic")) {

										/*---------------------------------------------------------------*/
										if (Stage.equals("Primary")) {

											NewExaminationList = daoInf2.retrieveExaminationList(AcademicYearID,
													studform.getTerm(), AYClassID);

											/* double = 0D; */

											double value, value1, value2, value3, value4, finalOutOfMarks,
													totalMarksScaled, toatlScaleTo1 = 0;
											int marksObtained, marksObtained1, marksObtained2, marksObtained3,
													marksObtained4 = 0;
											double value5, value6, finalSEAMarks = 0D;
											int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5 = 0;
											int toatlScaleTo, outOfMarks, outOfMarks1, outOfMarks2,
													finalSEAmarksvalue = 0;

											value = daoInf.retrievrAllMarksScaledForStudent(
													NewExaminationList.get("Term End"), studform.getSubjectID(),
													studform.getStandardID(), AcademicYearID,
													studentsExamFormName.getStudentID());

											value1 = daoInf.retrievrAllMarksScaledForStudent(
													NewExaminationList.get("Unit Test"), studform.getSubjectID(),
													studform.getStandardID(), AcademicYearID,
													studentsExamFormName.getStudentID());

											value2 = daoInf.retrievrAllMarksScaledForStudent(
													NewExaminationList.get("Subject Enrichment1"),
													studform.getSubjectID(), studform.getStandardID(), AcademicYearID,
													studentsExamFormName.getStudentID());

											value3 = daoInf.retrievrAllMarksScaledForStudent(
													NewExaminationList.get("Subject Enrichment2"),
													studform.getSubjectID(), studform.getStandardID(), AcademicYearID,
													studentsExamFormName.getStudentID());

											value4 = daoInf.retrievrAllMarksScaledForStudent(
													NewExaminationList.get("Notebook"), studform.getSubjectID(),
													studform.getStandardID(), AcademicYearID,
													studentsExamFormName.getStudentID());

											outOfMarks = daoInf.retrievAllTotalMarks(
													NewExaminationList.get("Subject Enrichment1"),
													studform.getSubjectID(), studform.getStandardID(), AcademicYearID);

											outOfMarks1 = daoInf.retrievAllTotalMarks(
													NewExaminationList.get("Subject Enrichment2"),
													studform.getSubjectID(), studform.getStandardID(), AcademicYearID);

											outOfMarks2 = daoInf.retrievAllTotalMarks(
													NewExaminationList.get("Notebook"), studform.getSubjectID(),
													studform.getStandardID(), AcademicYearID);

											finalOutOfMarks = (outOfMarks + outOfMarks1 + outOfMarks2);

											value5 = (value2 + value3 + value4);

											String numberAsString = Double.toString(value5);

											String marks[] = numberAsString.split("\\.");

											if (Long.parseLong(marks[1]) > 0) {

												value6 = Integer.parseInt(marks[0]) + 1;

											} else {

												value6 = Integer.parseInt(marks[0]);
											}

											scaleTo = daoInf.retrieveAllScaledMarks(studform.getTerm(), AcademicYearID,
													NewExaminationList.get("Term End"), studform.getStandardID(),
													studform.getSubjectID());

											scaleTo1 = daoInf.retrieveAllScaledMarks(studform.getTerm(), AcademicYearID,
													NewExaminationList.get("Unit Test"), studform.getStandardID(),
													studform.getSubjectID());

											scaleTo2 = daoInf.retrieveAllScaledMarks(studform.getTerm(), AcademicYearID,
													NewExaminationList.get("Subject Enrichment1"),
													studform.getStandardID(), studform.getSubjectID());

											scaleTo3 = daoInf.retrieveAllScaledMarks(studform.getTerm(), AcademicYearID,
													NewExaminationList.get("Subject Enrichment2"),
													studform.getStandardID(), studform.getSubjectID());

											scaleTo4 = daoInf.retrieveAllScaledMarks(studform.getTerm(), AcademicYearID,
													NewExaminationList.get("Notebook"), studform.getStandardID(),
													studform.getSubjectID());

											// finalSEAMarks = (value6 * scaleTo4) / finalOutOfMarks;

											finalSEAMarks = daoInf2.retrieveScholasticGradeList1(
													studform.getSubjectID(), studentsExamFormName.getStudentID(), 0,
													studform.getTerm());

											/*
											 * String finalSEAMarksString = Double.toString(finalSEAMarks);
											 * 
											 * String finalSEAmarks[] = finalSEAMarksString.split("\\.");
											 * 
											 * if (Long.parseLong(finalSEAmarks[1]) > 0) { finalSEAmarksvalue =
											 * Integer.parseInt(finalSEAmarks[0]) + 1;
											 * 
											 * } else {
											 * 
											 * finalSEAmarksvalue = Integer.parseInt(finalSEAmarks[0]); }
											 */

											// totalMarksScaled = (value + value1 + finalSEAmarksvalue);

											totalMarksScaled = (value + value1 + finalSEAMarks);

											// totalMarksScaled = (value + value1 + value2 + value3 + value4);

											scaleTo5 = (scaleTo2 + scaleTo3 + scaleTo4) / 3;

											toatlScaleTo = (scaleTo + scaleTo1 + scaleTo5);

											/*
											 * if (toatlScaleTo == 50) {
											 * 
											 * toatlScaleTo1 = (totalMarksScaled * 2); } else {
											 */
											// toatlScaleTo1 = totalMarksScaled;

											toatlScaleTo1 = (totalMarksScaled * 50) / toatlScaleTo;

											/* Calculating Final Grades */

											boolean termEndCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
													studentsExamFormName.getStudentID(), 0,
													NewExaminationList.get("Term End"));

											boolean unitTestCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
													studentsExamFormName.getStudentID(), 0,
													NewExaminationList.get("Unit Test"));

											boolean SEANBCheck = daoInf2.verifySEANBAbsent(studform.getSubjectID(),
													studentsExamFormName.getStudentID(), 0, Term, AcademicYearID);

											if (termEndCheck) {
												grade = "ex";
											} else if (unitTestCheck) {
												grade = "ex";
											} else if (SEANBCheck) {
												grade = "ex";
											} else {
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
											}

											if (studform.getGradeValue().equals("-1")) {
												FinalList += studentsExamFormName.getRollNumber() + "="
														+ studentsExamFormName.getStudentName() + "=" + grade;

											} else {

												if (NewFinalGrade.equals(grade)) {
													FinalList += studentsExamFormName.getRollNumber() + "="
															+ studentsExamFormName.getStudentName() + "=" + grade;
												}
											}

										} else {

											NewExaminationList = daoInf2.retrieveExaminationList(AcademicYearID,
													studform.getTerm(), AYClassID);

											double value, value1, value2, value3, value4, finalOutOfMarks,
													totalMarksScaled, toatlScaleTo1 = 0;
											int marksObtained, marksObtained1, marksObtained2, marksObtained3,
													marksObtained4 = 0;
											double value5, value6, finalSEAMarks = 0D;
											int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5 = 0;
											int toatlScaleTo, outOfMarks, outOfMarks1, outOfMarks2,
													finalSEAmarksvalue = 0;

											value = daoInf.retrievrAllMarksScaledForStudent(
													NewExaminationList.get("Term End"), studform.getSubjectID(),
													studform.getStandardID(), AcademicYearID,
													studentsExamFormName.getStudentID());

											value1 = daoInf.retrievrAllMarksScaledForStudent(
													NewExaminationList.get("Unit Test"), studform.getSubjectID(),
													studform.getStandardID(), AcademicYearID,
													studentsExamFormName.getStudentID());

											/*
											 * value2 = daoInf.retrievrAllMarksScaledForStudent(
											 * NewExaminationList.get("Subject Enrichment1"), studform.getSubjectID(),
											 * studform.getStandardID(), AcademicYearID,
											 * studentsExamFormName.getStudentID());
											 * 
											 * value4 = daoInf.retrievrAllMarksScaledForStudent(
											 * NewExaminationList.get("Notebook"), studform.getSubjectID(),
											 * studform.getStandardID(), AcademicYearID,
											 * studentsExamFormName.getStudentID());
											 */

											value2 = daoInf2.retrieveScholasticGradeListNew(studform.getSubjectID(),
													"Subject Enrichment", studentsExamFormName.getStudentID(), 0,
													studform.getTerm());

											value4 = daoInf2.retrieveScholasticGradeListNew(studform.getSubjectID(),
													"Notebook", studentsExamFormName.getStudentID(), 0,
													studform.getTerm());

											scaleTo = daoInf.retrieveAllScaledMarks(studform.getTerm(), AcademicYearID,
													NewExaminationList.get("Term End"), studform.getStandardID(),
													studform.getSubjectID());

											scaleTo1 = daoInf.retrieveAllScaledMarks(studform.getTerm(), AcademicYearID,
													NewExaminationList.get("Unit Test"), studform.getStandardID(),
													studform.getSubjectID());

											scaleTo2 = daoInf.retrieveAllScaledMarks(studform.getTerm(), AcademicYearID,
													NewExaminationList.get("Subject Enrichment1"),
													studform.getStandardID(), studform.getSubjectID());

											scaleTo4 = daoInf.retrieveAllScaledMarks(studform.getTerm(), AcademicYearID,
													NewExaminationList.get("Notebook"), studform.getStandardID(),
													studform.getSubjectID());

											totalMarksScaled = (value + value1 + value2 + value4);

											toatlScaleTo = (scaleTo + scaleTo1 + scaleTo2 + scaleTo4);

											String SubjectName = daoInf
													.retrieveSubjectBySubjectID(studform.getSubjectID());

											/*
											 * if (SubjectName.equals("Hindi")) { toatlScaleTo1 = totalMarksScaled; }
											 * else { if (toatlScaleTo == 50) {
											 * 
											 * toatlScaleTo1 = (totalMarksScaled * 2); } else if (toatlScaleTo1 > 100) {
											 * 
											 * toatlScaleTo1 = (totalMarksScaled / 200) * 100; } else { toatlScaleTo1 =
											 * totalMarksScaled; } }
											 */

											toatlScaleTo1 = (totalMarksScaled * 100) / toatlScaleTo;

											/* Calculating Final Grades */

											boolean termEndCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
													studentsExamFormName.getStudentID(), 0,
													NewExaminationList.get("Term End"));

											boolean unitTestCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
													studentsExamFormName.getStudentID(), 0,
													NewExaminationList.get("Unit Test"));

											boolean SEAAbsentCheck = daoInf2.verifySEANBAbsentCheck(
													studform.getSubjectID(), studentsExamFormName.getStudentID(),
													"Subject Enrichment", 0, studform.getTerm(), AcademicYearID);

											boolean NBAbsentCheck = daoInf2.verifySEANBAbsentCheck(
													studform.getSubjectID(), studentsExamFormName.getStudentID(),
													"Notebook", 0, studform.getTerm(), AcademicYearID);

											if (termEndCheck) {
												grade = "ex";
											} else if (unitTestCheck) {
												grade = "ex";
											} else if (SEAAbsentCheck) {
												grade = "ex";
											} else if (NBAbsentCheck) {
												grade = "ex";
											} else {
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
											}

											if (studform.getGradeValue().equals("-1")) {
												FinalList += studentsExamFormName.getRollNumber() + "="
														+ studentsExamFormName.getStudentName() + "=" + grade;
											} else {

												if (NewFinalGrade.equals(grade)) {
													FinalList += studentsExamFormName.getRollNumber() + "="
															+ studentsExamFormName.getStudentName() + "=" + grade;
												}
											}
										}

										/*---------------------------------------------------------------*/

									} else {

										FinalListNew = studentsExamFormName.getRollNumber() + "="
												+ studentsExamFormName.getStudentName();

										grade = daoInf.retrievrAllGradesForStudent(Integer.parseInt(newList[1]),
												studform.getSubjectID(), studform.getStandardID(), AcademicYearID,
												studentsExamFormName.getStudentID());

										if (studform.getGradeValue().equals("-1")) {

											NewGrade += "=" + grade;
											FinalList += FinalListNew + NewGrade;
										} else {

											if (NewFinalGrade.equals(grade)) {
												NewGrade += "=" + grade;
												FinalList += FinalListNew + NewGrade;
											}
										}
									}

								} else {

									if (SubjectTypeValue.equals("Scholastic")) {

										/*---------------------------------------------------------------*/
										if (Stage.equals("Primary")) {

											NewExaminationList = daoInf2.retrieveExaminationList(AcademicYearID,
													studform.getTerm(), AYClassID);

											/* double = 0D; */

											double value, value1, value2, value3, value4, finalOutOfMarks,
													totalMarksScaled, toatlScaleTo1 = 0;
											int marksObtained, marksObtained1, marksObtained2, marksObtained3,
													marksObtained4 = 0;
											double value5, value6, finalSEAMarks = 0D;
											int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5 = 0;
											int toatlScaleTo, outOfMarks, outOfMarks1, outOfMarks2,
													finalSEAmarksvalue = 0;

											value = daoInf.retrievrMarksScaledForStudent(
													NewExaminationList.get("Term End"), studform.getSubjectID(),
													studform.getStandardID(), Integer.parseInt(studform.getDivision()),
													AcademicYearID, studentsExamFormName.getStudentID());

											value1 = daoInf.retrievrMarksScaledForStudent(
													NewExaminationList.get("Unit Test"), studform.getSubjectID(),
													studform.getStandardID(), Integer.parseInt(studform.getDivision()),
													AcademicYearID, studentsExamFormName.getStudentID());

											value2 = daoInf.retrievrMarksScaledForStudent(
													NewExaminationList.get("Subject Enrichment1"),
													studform.getSubjectID(), studform.getStandardID(),
													Integer.parseInt(studform.getDivision()), AcademicYearID,
													studentsExamFormName.getStudentID());

											value3 = daoInf.retrievrMarksScaledForStudent(
													NewExaminationList.get("Subject Enrichment2"),
													studform.getSubjectID(), studform.getStandardID(),
													Integer.parseInt(studform.getDivision()), AcademicYearID,
													studentsExamFormName.getStudentID());

											value4 = daoInf.retrievrMarksScaledForStudent(
													NewExaminationList.get("Notebook"), studform.getSubjectID(),
													studform.getStandardID(), Integer.parseInt(studform.getDivision()),
													AcademicYearID, studentsExamFormName.getStudentID());

											outOfMarks = daoInf.retrievTotalMarks(
													NewExaminationList.get("Subject Enrichment1"),
													studform.getSubjectID(), studform.getStandardID(),
													Integer.parseInt(studform.getDivision()), AcademicYearID);

											outOfMarks1 = daoInf.retrievTotalMarks(
													NewExaminationList.get("Subject Enrichment2"),
													studform.getSubjectID(), studform.getStandardID(),
													Integer.parseInt(studform.getDivision()), AcademicYearID);

											outOfMarks2 = daoInf.retrievTotalMarks(NewExaminationList.get("Notebook"),
													studform.getSubjectID(), studform.getStandardID(),
													Integer.parseInt(studform.getDivision()), AcademicYearID);

											finalOutOfMarks = (outOfMarks + outOfMarks1 + outOfMarks2);

											value5 = (value2 + value3 + value4);

											String numberAsString = Double.toString(value5);

											String marks[] = numberAsString.split("\\.");

											if (Long.parseLong(marks[1]) > 0) {

												value6 = Integer.parseInt(marks[0]) + 1;

											} else {

												value6 = Integer.parseInt(marks[0]);
											}

											scaleTo = daoInf.retrieveScaledMarksNew(studform.getTerm(), AcademicYearID,
													NewExaminationList.get("Term End"), studform.getStandardID(),
													Integer.parseInt(studform.getDivision()), studform.getSubjectID());

											scaleTo1 = daoInf.retrieveScaledMarksNew(studform.getTerm(), AcademicYearID,
													NewExaminationList.get("Unit Test"), studform.getStandardID(),
													Integer.parseInt(studform.getDivision()), studform.getSubjectID());

											scaleTo2 = daoInf.retrieveScaledMarksNew(studform.getTerm(), AcademicYearID,
													NewExaminationList.get("Subject Enrichment1"),
													studform.getStandardID(), Integer.parseInt(studform.getDivision()),
													studform.getSubjectID());

											scaleTo3 = daoInf.retrieveScaledMarksNew(studform.getTerm(), AcademicYearID,
													NewExaminationList.get("Subject Enrichment2"),
													studform.getStandardID(), Integer.parseInt(studform.getDivision()),
													studform.getSubjectID());

											scaleTo4 = daoInf.retrieveScaledMarksNew(studform.getTerm(), AcademicYearID,
													NewExaminationList.get("Notebook"), studform.getStandardID(),
													Integer.parseInt(studform.getDivision()), studform.getSubjectID());

											// finalSEAMarks = (value6 * scaleTo4) / finalOutOfMarks;

											finalSEAMarks = daoInf2.retrieveScholasticGradeList1(
													studform.getSubjectID(), studentsExamFormName.getStudentID(),
													AYClassID, studform.getTerm());

											/*
											 * String finalSEAMarksString = Double.toString(finalSEAMarks);
											 * 
											 * String finalSEAmarks[] = finalSEAMarksString.split("\\.");
											 * 
											 * if (Long.parseLong(finalSEAmarks[1]) > 0) { finalSEAmarksvalue =
											 * Integer.parseInt(finalSEAmarks[0]) + 1;
											 * 
											 * } else {
											 * 
											 * finalSEAmarksvalue = Integer.parseInt(finalSEAmarks[0]); }
											 */

											// totalMarksScaled = (value + value1 + finalSEAmarksvalue);

											totalMarksScaled = (value + value1 + finalSEAMarks);

											scaleTo5 = (scaleTo2 + scaleTo3 + scaleTo4) / 3;

											toatlScaleTo = (scaleTo + scaleTo1 + scaleTo5);

											// toatlScaleTo1 = totalMarksScaled;

											toatlScaleTo1 = (totalMarksScaled * 50) / toatlScaleTo;

											/* Calculating Final Grades */

											boolean termEndCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
													studentsExamFormName.getStudentID(), AYClassID,
													NewExaminationList.get("Term End"));

											boolean unitTestCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
													studentsExamFormName.getStudentID(), AYClassID,
													NewExaminationList.get("Unit Test"));

											boolean SEANBCheck = daoInf2.verifySEANBAbsent(studform.getSubjectID(),
													studentsExamFormName.getStudentID(), AYClassID, Term,
													AcademicYearID);

											if (termEndCheck) {
												grade = "ex";
											} else if (unitTestCheck) {
												grade = "ex";
											} else if (SEANBCheck) {
												grade = "ex";
											} else {
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
											}

											if (studform.getGradeValue().equals("-1")) {
												FinalList += studentsExamFormName.getRollNumber() + "="
														+ studentsExamFormName.getStudentName() + "=" + grade;
											} else {

												if (NewFinalGrade.equals(grade)) {
													FinalList += studentsExamFormName.getRollNumber() + "="
															+ studentsExamFormName.getStudentName() + "=" + grade;
												}
											}

										} else {

											NewExaminationList = daoInf2.retrieveExaminationList(AcademicYearID,
													studform.getTerm(), AYClassID);

											double value, value1, value2, value3, value4, finalOutOfMarks,
													totalMarksScaled, toatlScaleTo1 = 0;
											int marksObtained, marksObtained1, marksObtained2, marksObtained3,
													marksObtained4 = 0;
											double value5, value6, finalSEAMarks = 0D;
											int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5 = 0;
											int toatlScaleTo, outOfMarks, outOfMarks1, outOfMarks2,
													finalSEAmarksvalue = 0;

											value = daoInf.retrievrMarksScaledForStudent(
													NewExaminationList.get("Term End"), studform.getSubjectID(),
													studform.getStandardID(), Integer.parseInt(studform.getDivision()),
													AcademicYearID, studentsExamFormName.getStudentID());

											value1 = daoInf.retrievrMarksScaledForStudent(
													NewExaminationList.get("Unit Test"), studform.getSubjectID(),
													studform.getStandardID(), Integer.parseInt(studform.getDivision()),
													AcademicYearID, studentsExamFormName.getStudentID());

											/*
											 * value2 = daoInf.retrievrMarksScaledForStudent(
											 * NewExaminationList.get("Subject Enrichment1"), studform.getSubjectID(),
											 * studform.getStandardID(), Integer.parseInt(studform.getDivision()),
											 * AcademicYearID, studentsExamFormName.getStudentID());
											 * 
											 * value4 = daoInf.retrievrMarksScaledForStudent(
											 * NewExaminationList.get("Notebook"), studform.getSubjectID(),
											 * studform.getStandardID(), Integer.parseInt(studform.getDivision()),
											 * AcademicYearID, studentsExamFormName.getStudentID());
											 */

											value2 = daoInf2.retrieveScholasticGradeListNew(studform.getSubjectID(),
													"Subject Enrichment", studentsExamFormName.getStudentID(),
													AYClassID, studform.getTerm());

											value4 = daoInf2.retrieveScholasticGradeListNew(studform.getSubjectID(),
													"Notebook", studentsExamFormName.getStudentID(), AYClassID,
													studform.getTerm());

											scaleTo = daoInf.retrieveScaledMarksNew(studform.getTerm(), AcademicYearID,
													NewExaminationList.get("Term End"), studform.getStandardID(),
													Integer.parseInt(studform.getDivision()), studform.getSubjectID());

											scaleTo1 = daoInf.retrieveScaledMarksNew(studform.getTerm(), AcademicYearID,
													NewExaminationList.get("Unit Test"), studform.getStandardID(),
													Integer.parseInt(studform.getDivision()), studform.getSubjectID());

											scaleTo2 = daoInf.retrieveScaledMarksNew(studform.getTerm(), AcademicYearID,
													NewExaminationList.get("Subject Enrichment1"),
													studform.getStandardID(), Integer.parseInt(studform.getDivision()),
													studform.getSubjectID());

											scaleTo4 = daoInf.retrieveScaledMarksNew(studform.getTerm(), AcademicYearID,
													NewExaminationList.get("Notebook"), studform.getStandardID(),
													Integer.parseInt(studform.getDivision()), studform.getSubjectID());

											totalMarksScaled = (value + value1 + value2 + value4);

											toatlScaleTo = (scaleTo + scaleTo1 + scaleTo2 + scaleTo4);

											String SubjectName = daoInf
													.retrieveSubjectBySubjectID(studform.getSubjectID());

											/*
											 * if (SubjectName.equals("Hindi")) { toatlScaleTo1 = totalMarksScaled; }
											 * else { if (toatlScaleTo == 50) {
											 * 
											 * toatlScaleTo1 = (totalMarksScaled * 2); } else if (toatlScaleTo1 > 100) {
											 * 
											 * toatlScaleTo1 = (totalMarksScaled / 200) * 100; } else { toatlScaleTo1 =
											 * totalMarksScaled; } }
											 */

											toatlScaleTo1 = (totalMarksScaled * 100) / toatlScaleTo;

											/* Calculating Final Grades */

											boolean termEndCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
													studentsExamFormName.getStudentID(), AYClassID,
													NewExaminationList.get("Term End"));

											boolean unitTestCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
													studentsExamFormName.getStudentID(), AYClassID,
													NewExaminationList.get("Unit Test"));

											boolean SEAAbsentCheck = daoInf2.verifySEANBAbsentCheck(
													studform.getSubjectID(), studentsExamFormName.getStudentID(),
													"Subject Enrichment", AYClassID, studform.getTerm(),
													AcademicYearID);

											boolean NBAbsentCheck = daoInf2.verifySEANBAbsentCheck(
													studform.getSubjectID(), studentsExamFormName.getStudentID(),
													"Notebook", AYClassID, studform.getTerm(), AcademicYearID);

											if (termEndCheck) {
												grade = "ex";
											} else if (unitTestCheck) {
												grade = "ex";
											} else if (SEAAbsentCheck) {
												grade = "ex";
											} else if (NBAbsentCheck) {
												grade = "ex";
											} else {
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
											}

											if (studform.getGradeValue().equals("-1")) {
												FinalList += studentsExamFormName.getRollNumber() + "="
														+ studentsExamFormName.getStudentName() + "=" + grade;
											} else {

												if (NewFinalGrade.equals(grade)) {
													FinalList += studentsExamFormName.getRollNumber() + "="
															+ studentsExamFormName.getStudentName() + "=" + grade;
												}
											}
										}

									} else {

										FinalListNew = studentsExamFormName.getRollNumber() + "="
												+ studentsExamFormName.getStudentName();

										grade = daoInf.retrievrGradesForStudent(Integer.parseInt(newList[1]),
												studform.getSubjectID(), studform.getStandardID(),
												Integer.parseInt(studform.getDivision()), AcademicYearID,
												studentsExamFormName.getStudentID());

										if (studform.getGradeValue().equals("-1")) {

											NewGrade += "=" + grade;
											FinalList = FinalListNew + NewGrade;
										} else {

											if (NewFinalGrade.equals(grade)) {
												NewGrade += "=" + grade;
												FinalList = FinalListNew + NewGrade;
											}
										}
									}
								}

							} else {

								FinalListNew = studentsExamFormName.getRollNumber() + "="
										+ studentsExamFormName.getStudentName();

								if (studform.getDivision().contains(",")) {
									if (Stage.equals("Primary")) {
										if (studform.getMarks().equals("marksObtained")) {

											Marks = daoInf.retrievrAllMarksForStudent(Integer.parseInt(newList[1]),
													studform.getSubjectID(), studform.getStandardID(), AcademicYearID,
													studentsExamFormName.getStudentID());

											FinalListMarks += "=" + Marks;

										} else {

											if (studform.getTotalValueString().equals("Total")) {

												int check = daoInf.verifyExamType(Integer.parseInt(newList[1]), "");
												int ScaledMarks = daoInf.retrieveAllScaledMarks(AcademicYearID,
														studform.getStandardID(), studform.getSubjectID());

												if (check == 1) {

													counter++;

													OutOfMarks = daoInf.retrievAllTotalMarks(
															Integer.parseInt(newList[1]), studform.getSubjectID(),
															studform.getStandardID(), AcademicYearID);

													FinalOutOfMarks = FinalOutOfMarks + OutOfMarks;

													MarksNew = daoInf.retrievrAllMarksForStudent(
															Integer.parseInt(newList[1]), studform.getSubjectID(),
															studform.getStandardID(), AcademicYearID,
															studentsExamFormName.getStudentID());

													FinalTotalMarks = FinalTotalMarks + MarksNew;

													NewMarks = (FinalTotalMarks / FinalOutOfMarks) * ScaledMarks;

													String numberAsString = Double.toString(NewMarks);

													String valueMarks[] = numberAsString.split("\\.");

													if (Long.parseLong(valueMarks[1]) > 0) {

														Marks = Integer.parseInt(valueMarks[0]) + 1;

													} else {

														Marks = Integer.parseInt(valueMarks[0]);
													}

													if (counter > 2) {

														if (studform.getTerm().contains("All")) {
															/* Calculating Final Grades */
															if (TotalMarks >= 91 && TotalMarks <= 100) {
																Studentgrade = "A1";
															} else if (TotalMarks >= 81 && TotalMarks <= 90) {
																Studentgrade = "A2";
															} else if (TotalMarks >= 71 && TotalMarks <= 80) {
																Studentgrade = "B1";
															} else if (TotalMarks >= 61 && TotalMarks <= 70) {
																Studentgrade = "B2";
															} else if (TotalMarks >= 51 && TotalMarks <= 60) {
																Studentgrade = "C1";
															} else if (TotalMarks >= 41 && TotalMarks <= 50) {
																Studentgrade = "C2";
															} else if (TotalMarks >= 33 && TotalMarks <= 40) {
																Studentgrade = "D";
															} else {
																Studentgrade = "E";
															}
														} else {
															/* Calculating Final Grades */
															if (TotalMarks >= 46 && TotalMarks <= 50) {
																Studentgrade = "A1";
															} else if (TotalMarks >= 41 && TotalMarks <= 45) {
																Studentgrade = "A2";
															} else if (TotalMarks >= 36 && TotalMarks <= 40) {
																Studentgrade = "B1";
															} else if (TotalMarks >= 31 && TotalMarks <= 35) {
																Studentgrade = "B2";
															} else if (TotalMarks >= 26 && TotalMarks <= 30) {
																Studentgrade = "C1";
															} else if (TotalMarks >= 21 && TotalMarks <= 25) {
																Studentgrade = "C2";
															} else if (TotalMarks >= 17 && TotalMarks <= 20) {
																Studentgrade = "D";
															} else {
																Studentgrade = "E";
															}
														}

														FinalListMarks += "=" + Marks;
														counter = 0;
													}

												} else {

													Marks = daoInf.retrievrAllMarksScaledForStudent(
															Integer.parseInt(newList[1]), studform.getSubjectID(),
															studform.getStandardID(), AcademicYearID,
															studentsExamFormName.getStudentID());

													TotalMarks = TotalMarks + Marks;
													/* Calculating Final Grades */
													if (TotalMarks >= 46 && TotalMarks <= 50) {
														Studentgrade = "A1";
													} else if (TotalMarks >= 41 && TotalMarks <= 45) {
														Studentgrade = "A2";
													} else if (TotalMarks >= 36 && TotalMarks <= 40) {
														Studentgrade = "B1";
													} else if (TotalMarks >= 31 && TotalMarks <= 35) {
														Studentgrade = "B2";
													} else if (TotalMarks >= 26 && TotalMarks <= 30) {
														Studentgrade = "C1";
													} else if (TotalMarks >= 21 && TotalMarks <= 25) {
														Studentgrade = "C2";
													} else if (TotalMarks >= 17 && TotalMarks <= 20) {
														Studentgrade = "D";
													} else {
														Studentgrade = "E";
													}

													FinalListMarks += "=" + Marks;
												}

											} else {

												Marks = daoInf.retrievrAllMarksScaledForStudent(
														Integer.parseInt(newList[1]), studform.getSubjectID(),
														studform.getStandardID(), AcademicYearID,
														studentsExamFormName.getStudentID());

												FinalListMarks += "=" + Marks;
											}
										}

									} else {
										if (studform.getMarks().equals("marksObtained")) {

											Marks = daoInf.retrievrAllMarksForStudent(Integer.parseInt(newList[1]),
													studform.getSubjectID(), studform.getStandardID(), AcademicYearID,
													studentsExamFormName.getStudentID());

											FinalListMarks += "=" + Marks;

										} else {

											if (studform.getTotalValueString().equals("Total")) {

												String examType = daoInf
														.retrieveExaminationType(Integer.parseInt(newList[1]));

												String subjectList = "Subject Enrichment, Notebook";

												String SubjectName = daoInf
														.retrieveSubjectBySubjectID(studform.getSubjectID());

												Marks = daoInf.retrievrAllMarksScaledForStudent(
														Integer.parseInt(newList[1]), studform.getSubjectID(),
														studform.getStandardID(), AcademicYearID,
														studentsExamFormName.getStudentID());

												if (!subjectList.contains(examType)) {
													TotalMarks = TotalMarks + Marks;
												}

												if (studform.getTerm().equals("All")) {
													if (SubjectName.equals("Hindi")) {
														TotalMarksGrade = TotalMarks;
													} else {
														TotalMarksGrade = (TotalMarks / 200) * 100;
													}

												} else {
													TotalMarksGrade = TotalMarks;
												}

												/* Calculating Final Grades */
												if (TotalMarksGrade >= 91 && TotalMarksGrade <= 100) {
													Studentgrade = "A1";
												} else if (TotalMarksGrade >= 81 && TotalMarksGrade <= 90) {
													Studentgrade = "A2";
												} else if (TotalMarksGrade >= 71 && TotalMarksGrade <= 80) {
													Studentgrade = "B1";
												} else if (TotalMarksGrade >= 61 && TotalMarksGrade <= 70) {
													Studentgrade = "B2";
												} else if (TotalMarksGrade >= 51 && TotalMarksGrade <= 60) {
													Studentgrade = "C1";
												} else if (TotalMarksGrade >= 41 && TotalMarksGrade <= 50) {
													Studentgrade = "C2";
												} else if (TotalMarksGrade >= 33 && TotalMarksGrade <= 40) {
													Studentgrade = "D";
												} else {
													Studentgrade = "E";
												}

												FinalListMarks += "=" + Marks;

												if (SECheck == 1 && examType.equals("Subject Enrichment")) {

													if (studform.getTerm().equals("All")) {
														double maxSEA = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Subject Enrichment",
																studentsExamFormName.getStudentID(), 0, "Term I");

														double maxSEATerm2 = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Subject Enrichment",
																studentsExamFormName.getStudentID(), 0, "Term II");

														if (SECheckCounter == 0) {
															// FinalListMarks += "=" + maxSEA;
															FinalSEANBMarks += "=" + maxSEA + "=" + maxSEATerm2;

															TotalMarks = TotalMarks + maxSEA + maxSEATerm2;

															if (studform.getTerm().equals("All")) {
																if (SubjectName.equals("Hindi")) {
																	TotalMarksGrade = TotalMarks;
																} else {
																	TotalMarksGrade = (TotalMarks / 200) * 100;
																}

															} else {
																TotalMarksGrade = TotalMarks;
															}

															/* Calculating Final Grades */
															if (TotalMarksGrade >= 91 && TotalMarksGrade <= 100) {
																Studentgrade = "A1";
															} else if (TotalMarksGrade >= 81 && TotalMarksGrade <= 90) {
																Studentgrade = "A2";
															} else if (TotalMarksGrade >= 71 && TotalMarksGrade <= 80) {
																Studentgrade = "B1";
															} else if (TotalMarksGrade >= 61 && TotalMarksGrade <= 70) {
																Studentgrade = "B2";
															} else if (TotalMarksGrade >= 51 && TotalMarksGrade <= 60) {
																Studentgrade = "C1";
															} else if (TotalMarksGrade >= 41 && TotalMarksGrade <= 50) {
																Studentgrade = "C2";
															} else if (TotalMarksGrade >= 33 && TotalMarksGrade <= 40) {
																Studentgrade = "D";
															} else {
																Studentgrade = "E";
															}
														}

														SECheckCounter++;

													} else {
														double maxSEA = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Subject Enrichment",
																studentsExamFormName.getStudentID(), 0,
																studform.getTerm());

														if (SECheckCounter == 0) {
															// FinalListMarks += "=" + maxSEA;
															FinalSEANBMarks += "=" + maxSEA;

															TotalMarks = TotalMarks + maxSEA;

															if (studform.getTerm().equals("All")) {
																if (SubjectName.equals("Hindi")) {
																	TotalMarksGrade = TotalMarks;
																} else {
																	TotalMarksGrade = (TotalMarks / 200) * 100;
																}

															} else {
																TotalMarksGrade = TotalMarks;
															}

															/* Calculating Final Grades */
															if (TotalMarksGrade >= 91 && TotalMarksGrade <= 100) {
																Studentgrade = "A1";
															} else if (TotalMarksGrade >= 81 && TotalMarksGrade <= 90) {
																Studentgrade = "A2";
															} else if (TotalMarksGrade >= 71 && TotalMarksGrade <= 80) {
																Studentgrade = "B1";
															} else if (TotalMarksGrade >= 61 && TotalMarksGrade <= 70) {
																Studentgrade = "B2";
															} else if (TotalMarksGrade >= 51 && TotalMarksGrade <= 60) {
																Studentgrade = "C1";
															} else if (TotalMarksGrade >= 41 && TotalMarksGrade <= 50) {
																Studentgrade = "C2";
															} else if (TotalMarksGrade >= 33 && TotalMarksGrade <= 40) {
																Studentgrade = "D";
															} else {
																Studentgrade = "E";
															}
														}

														SECheckCounter++;
													}
												}

												if (NBCheck == 1 && examType.equals("Notebook")) {

													if (studform.getTerm().equals("All")) {
														double maxNB = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Notebook",
																studentsExamFormName.getStudentID(), 0, "Term I");

														double maxNBTerm2 = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Notebook",
																studentsExamFormName.getStudentID(), 0, "Term II");

														if (NBCheckCounter == 0) {
															// FinalListMarks += "=" + maxSEA;
															FinalSEANB1Marks += "=" + maxNB + "=" + maxNBTerm2;

															TotalMarks = TotalMarks + maxNB + maxNBTerm2;

															if (studform.getTerm().equals("All")) {
																if (SubjectName.equals("Hindi")) {
																	TotalMarksGrade = TotalMarks;
																} else {
																	TotalMarksGrade = (TotalMarks / 200) * 100;
																}

															} else {
																TotalMarksGrade = TotalMarks;
															}

															/* Calculating Final Grades */
															if (TotalMarksGrade >= 91 && TotalMarksGrade <= 100) {
																Studentgrade = "A1";
															} else if (TotalMarksGrade >= 81 && TotalMarksGrade <= 90) {
																Studentgrade = "A2";
															} else if (TotalMarksGrade >= 71 && TotalMarksGrade <= 80) {
																Studentgrade = "B1";
															} else if (TotalMarksGrade >= 61 && TotalMarksGrade <= 70) {
																Studentgrade = "B2";
															} else if (TotalMarksGrade >= 51 && TotalMarksGrade <= 60) {
																Studentgrade = "C1";
															} else if (TotalMarksGrade >= 41 && TotalMarksGrade <= 50) {
																Studentgrade = "C2";
															} else if (TotalMarksGrade >= 33 && TotalMarksGrade <= 40) {
																Studentgrade = "D";
															} else {
																Studentgrade = "E";
															}
														}

														NBCheckCounter++;

													} else {
														double maxNB = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Notebook",
																studentsExamFormName.getStudentID(), 0,
																studform.getTerm());

														if (NBCheckCounter == 0) {
															// FinalListMarks += "=" + maxNB;
															FinalSEANB1Marks += "=" + maxNB;

															TotalMarks = TotalMarks + maxNB;

															if (studform.getTerm().equals("All")) {
																if (SubjectName.equals("Hindi")) {
																	TotalMarksGrade = TotalMarks;
																} else {
																	TotalMarksGrade = (TotalMarks / 200) * 100;
																}

															} else {
																TotalMarksGrade = TotalMarks;
															}

															/* Calculating Final Grades */
															if (TotalMarksGrade >= 91 && TotalMarksGrade <= 100) {
																Studentgrade = "A1";
															} else if (TotalMarksGrade >= 81 && TotalMarksGrade <= 90) {
																Studentgrade = "A2";
															} else if (TotalMarksGrade >= 71 && TotalMarksGrade <= 80) {
																Studentgrade = "B1";
															} else if (TotalMarksGrade >= 61 && TotalMarksGrade <= 70) {
																Studentgrade = "B2";
															} else if (TotalMarksGrade >= 51 && TotalMarksGrade <= 60) {
																Studentgrade = "C1";
															} else if (TotalMarksGrade >= 41 && TotalMarksGrade <= 50) {
																Studentgrade = "C2";
															} else if (TotalMarksGrade >= 33 && TotalMarksGrade <= 40) {
																Studentgrade = "D";
															} else {
																Studentgrade = "E";
															}
														}

														NBCheckCounter++;
													}
												}

											} else {

												String examType = daoInf
														.retrieveExaminationType(Integer.parseInt(newList[1]));

												Marks = daoInf.retrievrAllMarksScaledForStudent(
														Integer.parseInt(newList[1]), studform.getSubjectID(),
														studform.getStandardID(), AcademicYearID,
														studentsExamFormName.getStudentID());

												FinalListMarks += "=" + Marks;

												if (SECheck == 1 && examType.equals("Subject Enrichment")) {

													if (studform.getTerm().equals("All")) {
														double maxSEA = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Subject Enrichment",
																studentsExamFormName.getStudentID(), 0, "Term I");

														double maxSEATerm2 = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Subject Enrichment",
																studentsExamFormName.getStudentID(), 0, "Term II");

														if (SECheckCounter == 0) {
															// FinalListMarks += "=" + maxSEA;
															FinalSEANBMarks += "=" + maxSEA + "=" + maxSEATerm2;
														}

														SECheckCounter++;

													} else {
														double maxSEA = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Subject Enrichment",
																studentsExamFormName.getStudentID(), 0,
																studform.getTerm());

														if (SECheckCounter == 0) {
															// FinalListMarks += "=" + maxSEA;
															FinalSEANBMarks += "=" + maxSEA;
														}

														SECheckCounter++;
													}
												}

												if (NBCheck == 1 && examType.equals("Notebook")) {

													if (studform.getTerm().equals("All")) {
														double maxNB = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Notebook",
																studentsExamFormName.getStudentID(), 0, "Term I");

														double maxNBTerm2 = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Notebook",
																studentsExamFormName.getStudentID(), 0, "Term II");

														if (NBCheckCounter == 0) {
															// FinalListMarks += "=" + maxSEA;
															FinalSEANB1Marks += "=" + maxNB + "=" + maxNBTerm2;
														}

														NBCheckCounter++;

													} else {
														double maxNB = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Notebook",
																studentsExamFormName.getStudentID(), 0,
																studform.getTerm());

														if (NBCheckCounter == 0) {
															// FinalListMarks += "=" + maxNB;
															FinalSEANB1Marks += "=" + maxNB;
														}

														NBCheckCounter++;
													}
												}
											}
										}
									}
								} else {

									if (Stage.equals("Primary")) {
										if (studform.getMarks().equals("marksObtained")) {

											Marks = daoInf.retrievrMarksForStudent(Integer.parseInt(newList[1]),
													studform.getSubjectID(), studform.getStandardID(),
													Integer.parseInt(studform.getDivision()), AcademicYearID,
													studentsExamFormName.getStudentID());

											FinalListMarks += "=" + Marks;

										} else {

											if (studform.getTotalValueString().equals("Total")) {

												int check = daoInf.verifyExamType(Integer.parseInt(newList[1]), "");
												int ScaledMarks = daoInf.retrieveScaledMarks(studform.getTerm(),
														AcademicYearID, studform.getStandardID(),
														Integer.parseInt(studform.getDivision()),
														studform.getSubjectID());

												if (check == 1) {

													counter++;

													OutOfMarks = daoInf.retrievTotalMarks(Integer.parseInt(newList[1]),
															studform.getSubjectID(), studform.getStandardID(),
															Integer.parseInt(studform.getDivision()), AcademicYearID);

													FinalOutOfMarks = FinalOutOfMarks + OutOfMarks;

													MarksNew = daoInf.retrievrMarksForStudent(
															Integer.parseInt(newList[1]), studform.getSubjectID(),
															studform.getStandardID(),
															Integer.parseInt(studform.getDivision()), AcademicYearID,
															studentsExamFormName.getStudentID());

													FinalTotalMarks = FinalTotalMarks + MarksNew;

													NewMarks = (FinalTotalMarks / FinalOutOfMarks) * ScaledMarks;

													String numberAsString = Double.toString(NewMarks);

													String valueMarks[] = numberAsString.split("\\.");

													if (Long.parseLong(valueMarks[1]) > 0) {

														Marks = Integer.parseInt(valueMarks[0]) + 1;

													} else {

														Marks = Integer.parseInt(valueMarks[0]);
													}

													if (counter > 2) {

														if (studform.getTerm().contains("All")) {
															/* Calculating Final Grades */
															if (TotalMarks >= 91 && TotalMarks <= 100) {
																Studentgrade = "A1";
															} else if (TotalMarks >= 81 && TotalMarks <= 90) {
																Studentgrade = "A2";
															} else if (TotalMarks >= 71 && TotalMarks <= 80) {
																Studentgrade = "B1";
															} else if (TotalMarks >= 61 && TotalMarks <= 70) {
																Studentgrade = "B2";
															} else if (TotalMarks >= 51 && TotalMarks <= 60) {
																Studentgrade = "C1";
															} else if (TotalMarks >= 41 && TotalMarks <= 50) {
																Studentgrade = "C2";
															} else if (TotalMarks >= 33 && TotalMarks <= 40) {
																Studentgrade = "D";
															} else {
																Studentgrade = "E";
															}
														} else {
															TotalMarks = TotalMarks + Marks;
															/* Calculating Final Grades */
															if (TotalMarks >= 46 && TotalMarks <= 50) {
																Studentgrade = "A1";
															} else if (TotalMarks >= 41 && TotalMarks <= 45) {
																Studentgrade = "A2";
															} else if (TotalMarks >= 36 && TotalMarks <= 40) {
																Studentgrade = "B1";
															} else if (TotalMarks >= 31 && TotalMarks <= 35) {
																Studentgrade = "B2";
															} else if (TotalMarks >= 26 && TotalMarks <= 30) {
																Studentgrade = "C1";
															} else if (TotalMarks >= 21 && TotalMarks <= 25) {
																Studentgrade = "C2";
															} else if (TotalMarks >= 17 && TotalMarks <= 20) {
																Studentgrade = "D";
															} else {
																Studentgrade = "E";
															}
														}
														FinalListMarks += "=" + Marks;

														counter = 0;
													}

												} else {

													Marks = daoInf.retrievrMarksScaledForStudent(
															Integer.parseInt(newList[1]), studform.getSubjectID(),
															studform.getStandardID(),
															Integer.parseInt(studform.getDivision()), AcademicYearID,
															studentsExamFormName.getStudentID());

													TotalMarks = TotalMarks + Marks;
													/* Calculating Final Grades */
													if (TotalMarks >= 46 && TotalMarks <= 50) {
														Studentgrade = "A1";
													} else if (TotalMarks >= 41 && TotalMarks <= 45) {
														Studentgrade = "A2";
													} else if (TotalMarks >= 36 && TotalMarks <= 40) {
														Studentgrade = "B1";
													} else if (TotalMarks >= 31 && TotalMarks <= 35) {
														Studentgrade = "B2";
													} else if (TotalMarks >= 26 && TotalMarks <= 30) {
														Studentgrade = "C1";
													} else if (TotalMarks >= 21 && TotalMarks <= 25) {
														Studentgrade = "C2";
													} else if (TotalMarks >= 17 && TotalMarks <= 20) {
														Studentgrade = "D";
													} else {
														Studentgrade = "E";
													}

													FinalListMarks += "=" + Marks;
												}

											} else {

												Marks = daoInf.retrievrMarksScaledForStudent(
														Integer.parseInt(newList[1]), studform.getSubjectID(),
														studform.getStandardID(),
														Integer.parseInt(studform.getDivision()), AcademicYearID,
														studentsExamFormName.getStudentID());

												FinalListMarks += "=" + Marks;
											}
										}

									} else {
										if (studform.getMarks().equals("marksObtained")) {

											Marks = daoInf.retrievrMarksForStudent(Integer.parseInt(newList[1]),
													studform.getSubjectID(), studform.getStandardID(),
													Integer.parseInt(studform.getDivision()), AcademicYearID,
													studentsExamFormName.getStudentID());

											FinalListMarks += "=" + Marks;

										} else {

											if (studform.getTotalValueString().equals("Total")) {

												String examType = daoInf
														.retrieveExaminationType(Integer.parseInt(newList[1]));

												String subjectList = "Subject Enrichment, Notebook";

												String SubjectName = daoInf
														.retrieveSubjectBySubjectID(studform.getSubjectID());

												Marks = daoInf.retrievrMarksScaledForStudent(
														Integer.parseInt(newList[1]), studform.getSubjectID(),
														studform.getStandardID(),
														Integer.parseInt(studform.getDivision()), AcademicYearID,
														studentsExamFormName.getStudentID());

												if (!subjectList.contains(examType)) {
													TotalMarks = TotalMarks + Marks;
												}

												if (studform.getTerm().equals("All")) {
													if (SubjectName.equals("Hindi")) {
														TotalMarksGrade = TotalMarks;
													} else {
														TotalMarksGrade = (TotalMarks / 200) * 100;
													}

												} else {
													TotalMarksGrade = TotalMarks;
												}

												/* Calculating Final Grades */
												if (TotalMarksGrade >= 91 && TotalMarksGrade <= 100) {
													Studentgrade = "A1";
												} else if (TotalMarksGrade >= 81 && TotalMarksGrade <= 90) {
													Studentgrade = "A2";
												} else if (TotalMarksGrade >= 71 && TotalMarksGrade <= 80) {
													Studentgrade = "B1";
												} else if (TotalMarksGrade >= 61 && TotalMarksGrade <= 70) {
													Studentgrade = "B2";
												} else if (TotalMarksGrade >= 51 && TotalMarksGrade <= 60) {
													Studentgrade = "C1";
												} else if (TotalMarksGrade >= 41 && TotalMarksGrade <= 50) {
													Studentgrade = "C2";
												} else if (TotalMarksGrade >= 33 && TotalMarksGrade <= 40) {
													Studentgrade = "D";
												} else {
													Studentgrade = "E";
												}

												FinalListMarks += "=" + Marks;

												if (SECheck == 1 && examType.equals("Subject Enrichment")) {

													if (studform.getTerm().equals("All")) {
														double maxSEA = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Subject Enrichment",
																studentsExamFormName.getStudentID(), AYClassID,
																"Term I");

														double maxSEATerm2 = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Subject Enrichment",
																studentsExamFormName.getStudentID(), AYClassID,
																"Term II");

														if (SECheckCounter == 0) {
															// FinalListMarks += "=" + maxSEA;
															FinalSEANBMarks += "=" + maxSEA + "=" + maxSEATerm2;

															TotalMarks = TotalMarks + maxSEA + maxSEATerm2;

															if (studform.getTerm().equals("All")) {
																if (SubjectName.equals("Hindi")) {
																	TotalMarksGrade = TotalMarks;
																} else {
																	TotalMarksGrade = (TotalMarks / 200) * 100;
																}

															} else {
																TotalMarksGrade = TotalMarks;
															}

															/* Calculating Final Grades */
															if (TotalMarksGrade >= 91 && TotalMarksGrade <= 100) {
																Studentgrade = "A1";
															} else if (TotalMarksGrade >= 81 && TotalMarksGrade <= 90) {
																Studentgrade = "A2";
															} else if (TotalMarksGrade >= 71 && TotalMarksGrade <= 80) {
																Studentgrade = "B1";
															} else if (TotalMarksGrade >= 61 && TotalMarksGrade <= 70) {
																Studentgrade = "B2";
															} else if (TotalMarksGrade >= 51 && TotalMarksGrade <= 60) {
																Studentgrade = "C1";
															} else if (TotalMarksGrade >= 41 && TotalMarksGrade <= 50) {
																Studentgrade = "C2";
															} else if (TotalMarksGrade >= 33 && TotalMarksGrade <= 40) {
																Studentgrade = "D";
															} else {
																Studentgrade = "E";
															}
														}

														SECheckCounter++;

													} else {
														double maxSEA = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Subject Enrichment",
																studentsExamFormName.getStudentID(), AYClassID,
																studform.getTerm());

														if (SECheckCounter == 0) {
															// FinalListMarks += "=" + maxSEA;
															FinalSEANBMarks += "=" + maxSEA;

															TotalMarks = TotalMarks + maxSEA;

															if (studform.getTerm().equals("All")) {
																if (SubjectName.equals("Hindi")) {
																	TotalMarksGrade = TotalMarks;
																} else {
																	TotalMarksGrade = (TotalMarks / 200) * 100;
																}

															} else {
																TotalMarksGrade = TotalMarks;
															}

															/* Calculating Final Grades */
															if (TotalMarksGrade >= 91 && TotalMarksGrade <= 100) {
																Studentgrade = "A1";
															} else if (TotalMarksGrade >= 81 && TotalMarksGrade <= 90) {
																Studentgrade = "A2";
															} else if (TotalMarksGrade >= 71 && TotalMarksGrade <= 80) {
																Studentgrade = "B1";
															} else if (TotalMarksGrade >= 61 && TotalMarksGrade <= 70) {
																Studentgrade = "B2";
															} else if (TotalMarksGrade >= 51 && TotalMarksGrade <= 60) {
																Studentgrade = "C1";
															} else if (TotalMarksGrade >= 41 && TotalMarksGrade <= 50) {
																Studentgrade = "C2";
															} else if (TotalMarksGrade >= 33 && TotalMarksGrade <= 40) {
																Studentgrade = "D";
															} else {
																Studentgrade = "E";
															}
														}

														SECheckCounter++;
													}
												}

												if (NBCheck == 1 && examType.equals("Notebook")) {

													if (studform.getTerm().equals("All")) {
														double maxNB = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Notebook",
																studentsExamFormName.getStudentID(), AYClassID,
																"Term I");

														double maxNBTerm2 = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Notebook",
																studentsExamFormName.getStudentID(), AYClassID,
																"Term II");

														if (NBCheckCounter == 0) {
															// FinalListMarks += "=" + maxSEA;
															FinalSEANB1Marks += "=" + maxNB + "=" + maxNBTerm2;

															TotalMarks = TotalMarks + maxNB + maxNBTerm2;

															if (studform.getTerm().equals("All")) {
																if (SubjectName.equals("Hindi")) {
																	TotalMarksGrade = TotalMarks;
																} else {
																	TotalMarksGrade = (TotalMarks / 200) * 100;
																}

															} else {
																TotalMarksGrade = TotalMarks;
															}

															/* Calculating Final Grades */
															if (TotalMarksGrade >= 91 && TotalMarksGrade <= 100) {
																Studentgrade = "A1";
															} else if (TotalMarksGrade >= 81 && TotalMarksGrade <= 90) {
																Studentgrade = "A2";
															} else if (TotalMarksGrade >= 71 && TotalMarksGrade <= 80) {
																Studentgrade = "B1";
															} else if (TotalMarksGrade >= 61 && TotalMarksGrade <= 70) {
																Studentgrade = "B2";
															} else if (TotalMarksGrade >= 51 && TotalMarksGrade <= 60) {
																Studentgrade = "C1";
															} else if (TotalMarksGrade >= 41 && TotalMarksGrade <= 50) {
																Studentgrade = "C2";
															} else if (TotalMarksGrade >= 33 && TotalMarksGrade <= 40) {
																Studentgrade = "D";
															} else {
																Studentgrade = "E";
															}
														}

														NBCheckCounter++;

													} else {
														double maxNB = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Notebook",
																studentsExamFormName.getStudentID(), AYClassID,
																studform.getTerm());

														if (NBCheckCounter == 0) {
															// FinalListMarks += "=" + maxNB;
															FinalSEANB1Marks += "=" + maxNB;

															TotalMarks = TotalMarks + maxNB;

															if (studform.getTerm().equals("All")) {
																if (SubjectName.equals("Hindi")) {
																	TotalMarksGrade = TotalMarks;
																} else {
																	TotalMarksGrade = (TotalMarks / 200) * 100;
																}

															} else {
																TotalMarksGrade = TotalMarks;
															}

															/* Calculating Final Grades */
															if (TotalMarksGrade >= 91 && TotalMarksGrade <= 100) {
																Studentgrade = "A1";
															} else if (TotalMarksGrade >= 81 && TotalMarksGrade <= 90) {
																Studentgrade = "A2";
															} else if (TotalMarksGrade >= 71 && TotalMarksGrade <= 80) {
																Studentgrade = "B1";
															} else if (TotalMarksGrade >= 61 && TotalMarksGrade <= 70) {
																Studentgrade = "B2";
															} else if (TotalMarksGrade >= 51 && TotalMarksGrade <= 60) {
																Studentgrade = "C1";
															} else if (TotalMarksGrade >= 41 && TotalMarksGrade <= 50) {
																Studentgrade = "C2";
															} else if (TotalMarksGrade >= 33 && TotalMarksGrade <= 40) {
																Studentgrade = "D";
															} else {
																Studentgrade = "E";
															}
														}

														NBCheckCounter++;
													}
												}

											} else {

												String examType = daoInf
														.retrieveExaminationType(Integer.parseInt(newList[1]));

												Marks = daoInf.retrievrMarksScaledForStudent(
														Integer.parseInt(newList[1]), studform.getSubjectID(),
														studform.getStandardID(),
														Integer.parseInt(studform.getDivision()), AcademicYearID,
														studentsExamFormName.getStudentID());

												FinalListMarks += "=" + Marks;

												if (SECheck == 1 && examType.equals("Subject Enrichment")) {

													if (studform.getTerm().equals("All")) {
														double maxSEA = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Subject Enrichment",
																studentsExamFormName.getStudentID(), AYClassID,
																"Term I");

														double maxSEATerm2 = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Subject Enrichment",
																studentsExamFormName.getStudentID(), AYClassID,
																"Term II");

														if (SECheckCounter == 0) {
															// FinalListMarks += "=" + maxSEA;
															FinalSEANBMarks += "=" + maxSEA + "=" + maxSEATerm2;
														}

														SECheckCounter++;

													} else {
														double maxSEA = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Subject Enrichment",
																studentsExamFormName.getStudentID(), AYClassID,
																studform.getTerm());

														if (SECheckCounter == 0) {
															// FinalListMarks += "=" + maxSEA;
															FinalSEANBMarks += "=" + maxSEA;
														}

														SECheckCounter++;
													}
												}

												if (NBCheck == 1 && examType.equals("Notebook")) {

													if (studform.getTerm().equals("All")) {
														double maxNB = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Notebook",
																studentsExamFormName.getStudentID(), AYClassID,
																"Term I");

														double maxNBTerm2 = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Notebook",
																studentsExamFormName.getStudentID(), AYClassID,
																"Term II");

														if (NBCheckCounter == 0) {
															// FinalListMarks += "=" + maxSEA;
															FinalSEANB1Marks += "=" + maxNB + "=" + maxNBTerm2;
														}

														NBCheckCounter++;

													} else {
														double maxNB = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Notebook",
																studentsExamFormName.getStudentID(), AYClassID,
																studform.getTerm());

														if (NBCheckCounter == 0) {
															// FinalListMarks += "=" + maxNB;
															FinalSEANB1Marks += "=" + maxNB;
														}

														NBCheckCounter++;
													}
												}
											}
										}
									}
								}
								FinalList = FinalListNew + FinalListMarks + FinalSEANBMarks + FinalSEANB1Marks;
							}
						}
					} else {
						FinalList = FinalList;
					}

					if (studform.getTotalValueString().equals("Total")) {
						System.out.println("StudentgradeNew: " + Studentgrade);
						FinalList += "=" + TotalMarks + "=" + Studentgrade;
					}

					studentFinalExamCustomReportList.add(FinalList);
				}
			}

			// Workbook wb = new HSSFWorkbook();
			XSSFWorkbook wb = new XSSFWorkbook();

			XSSFSheet spreadSheet = wb.createSheet("Exam customise report");
			Row row;

			row = spreadSheet.createRow(0);

			spreadSheet.setColumnWidth((short) 0, (short) (256 * 25));

			int counter = 1;

			if (ExamCustomList != null) {

				String[] newcheckBoxList = ExamCustomList.split(",");

				for (int i = 0; i < newcheckBoxList.length; i++) {

					spreadSheet.setColumnWidth((short) counter, (short) (256 * 25));

					counter++;
				}

			}

			/*
			 * Creating header in XLSX
			 */
			CellStyle headerCellStyle = wb.createCellStyle();

			/*
			 * Setting up the font
			 */
			Font setFont = wb.createFont();
			setFont.setFontHeightInPoints((short) 12);
			setFont.setBold(true);
			headerCellStyle.setFont(setFont);

			/*
			 * Setting up Data style in XLSX
			 */
			CellStyle dataCellStyle = wb.createCellStyle();
			Font setDataFont = wb.createFont();
			setDataFont.setFontHeightInPoints((short) 12);
			dataCellStyle.setFont(setDataFont);

			/*
			 * Initializing Cell reference variable
			 */
			Cell cell = null;

			cell = row.createCell((short) 0);
			cell.setCellValue("Standard: " + StandardName);
			cell.setCellStyle(dataCellStyle);

			row = spreadSheet.createRow(1);

			if (studform.getDivision().contains(",")) {
				cell = row.createCell((short) 0);
				cell.setCellValue("Divison: All");
				cell.setCellStyle(dataCellStyle);
			} else {
				cell = row.createCell((short) 0);
				cell.setCellValue("Division: " + DivisionName);
				cell.setCellStyle(dataCellStyle);
			}

			row = spreadSheet.createRow(2);

			cell = row.createCell((short) 0);
			cell.setCellValue("Term: " + studform.getTerm());
			cell.setCellStyle(dataCellStyle);

			row = spreadSheet.createRow(3);

			cell = row.createCell((short) 0);
			cell.setCellValue("Contains: " + studform.getContainsName());
			cell.setCellStyle(dataCellStyle);

			row = spreadSheet.createRow(4);

			cell = row.createCell((short) 0);
			cell.setCellValue("Subject: " + Subject);
			cell.setCellStyle(dataCellStyle);

			if (studform.getContainsName().equals("MarksBased")) {
				row = spreadSheet.createRow(5);

				cell = row.createCell((short) 0);
				cell.setCellValue("Format: " + studform.getMarks());
				cell.setCellStyle(dataCellStyle);

				currentRow = 6;
			}

			int counter1 = 1;

			row = spreadSheet.createRow(currentRow);

			cell = row.createCell((short) 0);
			cell.setCellValue("Sr. No.");
			cell.setCellStyle(headerCellStyle);

			if (ExamCustomList != null) {

				String[] newcheckBoxList = ExamCustomList.split(",");

				for (int i = 0; i < newcheckBoxList.length; i++) {

					cell = row.createCell((short) counter1);
					cell.setCellValue(newcheckBoxList[i]);
					cell.setCellStyle(headerCellStyle);

					counter1++;
				}

				// StudentCustomList = StudentCustomList + finalcheckBoxList;

			}

			currentRow = 7;

			int srNo = 1;

			for (String FinalStudentsReportData : studentFinalExamCustomReportList) {

				if (FinalStudentsReportData.isEmpty()) {

				} else {
					int counter2 = 1;
					row = spreadSheet.createRow(currentRow++);

					cell = row.createCell((short) 0);
					cell.setCellValue(srNo);
					cell.setCellStyle(dataCellStyle);
					srNo++;
					String[] List = FinalStudentsReportData.split("=");

					for (int j = 0; j < List.length; j++) {

						cell = row.createCell((short) counter2);
						cell.setCellValue(List[j]);
						cell.setCellStyle(dataCellStyle);
						counter2++;

					}
				}
			}

			/*
			 * writing to the xlsx file
			 */
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wb.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();

			/*
			 * Write the output to a file
			 */
			FileOutputStream fileOut = new FileOutputStream(new File(realPath + "/" + excelFileName));
			fileOut.write(outArray);
			fileOut.flush();
			fileOut.close();

			System.out.println("Excel sheet for Students Customised Report is created successfully.");

			status = "success";

			/*
			 * Closing resultSet, preparedStatement, connection objects
			 */

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;

	}

	public String convertCustomReportToExcel(int standardID, String StandardName, int divisionID, String divisionName,
			String term, int organizationID, int userID, int subjectID, int academicYearID, String realPath,
			String excelFileName) {

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		HashMap<Integer, String> subjectNameList = null;

		HashMap<String, Integer> NewExaminationList = null;

		LinkedHashMap<Integer, String> studentCustomList = null;

		List<ConfigurationForm> studentsCustomReportList = null;

		List<String> SubjectEnrichmentList = null;

		List<String> NotebookList = null;

		List<String> PortfolioList = null;

		List<String> MultipleAssessmentList = null;

		int currentRow = 1;

		try {

			int AYClassID = daoInf2.retrieveAYCLassID(standardID, divisionID, academicYearID);

			subjectNameList = daoInf2.retrieveSubjectListForCLassTeacher(userID, academicYearID);

			String subjectName = daoInf2.retrieveSubject(subjectID);

			String Stage = daoInf2.getStandardStageByStandardID(standardID);

			if (Stage.equals("Primary")) {

				studentCustomList = daoInf2.retrieveExistingStudentAssessmentListForClassTeacher(AYClassID);

				NewExaminationList = daoInf2.retrieveExaminationList(academicYearID, term, AYClassID);

				/* double = 0D; */

				int value, value1, value2, value3, value4, finalOutOfMarks = 0;
				int marksObtained, marksObtained1, marksObtained2, marksObtained3, marksObtained4 = 0;
				double value5, value6, finalSEAMarks = 0D;
				int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5 = 0;
				int toatlScaleTo, toatlScaleTo1, outOfMarks, outOfMarks1, outOfMarks2, finalSEAmarksvalue,
						totalMarksScaled = 0;

				String grade = "";

				studentsCustomReportList = new ArrayList<ConfigurationForm>();

				SubjectEnrichmentList = new ArrayList<String>();

				NotebookList = new ArrayList<String>();

				for (Integer studentID : studentCustomList.keySet()) {

					String marksObtainedValue = "", marksObtainedValue1 = "", marksObtainedNewValue = "",
							marksObtainedNewValue1 = "";

					String[] array = studentCustomList.get(studentID).split("=");

					String studentName = array[0];

					String rollNo = array[1];

					ConfigurationForm form = new ConfigurationForm();

					/*
					 * request.setAttribute("GradeBased",
					 * daoInf2.retrieveGradeBasedValue(NewExaminationList.get("Term End"),
					 * conform.getSubjectID(), AYClassID));
					 */
					int gradeCheck = daoInf2.retrieveGradeBasedValue(NewExaminationList.get("Term End"), subjectID,
							AYClassID);

					if (gradeCheck == 1) {
						System.out.println("gradecheck");

						boolean termEdCheck = daoInf2.verifyAbsent(subjectID, studentID, AYClassID,
								NewExaminationList.get("Term End"));

						grade = daoInf2.retrieveGradeValue(subjectID, studentID, AYClassID,
								NewExaminationList.get("Term End"));

						if (termEdCheck) {
							grade = "ex";
						}

						form.setStudentName(studentName);
						form.setRollNumber(Integer.parseInt(rollNo));
						form.setTermEndMarks("-");
						form.setTermEndMarksObtained("-");
						form.setUnitTestMarksObtained("-");
						form.setFinalTotalMarks("-");
						form.setUnitTestMarks("-");
						form.setSubjectEnrichmentMarksObtained("-");
						form.setSubjectEnrichmentMarks1Obtained("-");
						form.setNotebookMarksObtained("-");
						form.setNotebookMarks("-");
						form.setGrade(grade);

						studentsCustomReportList.add(form);

					} else {
						System.out.println(" No gradecheck");

						boolean termEdCheck = daoInf2.verifyAbsent(subjectID, studentID, AYClassID,
								NewExaminationList.get("Term End"));

						boolean unitTestCheck = daoInf2.verifyAbsent(subjectID, studentID, AYClassID,
								NewExaminationList.get("Unit Test"));

						/*
						 * boolean sbjEnrch1Check = daoInf2.verifyAbsent(subjectID, studentID,
						 * AYClassID, NewExaminationList.get("Subject Enrichment1"));
						 * 
						 * boolean sbjEnrch2Check = daoInf2.verifyAbsent(subjectID, studentID,
						 * AYClassID, NewExaminationList.get("Subject Enrichment2"));
						 * 
						 * boolean noteBkCheck = daoInf2.verifyAbsent(subjectID, studentID, AYClassID,
						 * NewExaminationList.get("Notebook"));
						 */

						marksObtained = daoInf2.retrievemarksObtained(subjectID, NewExaminationList.get("Term End"),
								studentID, AYClassID);

						marksObtained1 = daoInf2.retrievemarksObtained(subjectID, NewExaminationList.get("Unit Test"),
								studentID, AYClassID);

						for (Entry<String, Integer> Subjectentry : NewExaminationList.entrySet()) {
							if (Subjectentry.getKey().startsWith("Subject Enrichment")) {

								marksObtainedNewValue = daoInf2.retrievemarksObtainedForSubjectEnrichment(subjectID,
										Subjectentry.getValue(), studentID, AYClassID);

								marksObtainedValue += ", " + marksObtainedNewValue;

							}
						}

						if (marksObtainedValue.startsWith(",")) {
							marksObtainedValue = marksObtainedValue.substring(1);
						}

						SubjectEnrichmentList.add(marksObtainedValue);

						/*
						 * marksObtained2 = daoInf2.retrievemarksObtained(subjectID,
						 * NewExaminationList.get("Subject Enrichment1"), studentID, AYClassID);
						 * 
						 * marksObtained3 = daoInf2.retrievemarksObtained(subjectID,
						 * NewExaminationList.get("Subject Enrichment2"), studentID, AYClassID);
						 * 
						 * marksObtained4 = daoInf2.retrievemarksObtained(subjectID,
						 * NewExaminationList.get("Notebook"), studentID, AYClassID);
						 */

						for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
							if (entry.getKey().startsWith("Notebook")) {

								marksObtainedNewValue1 = daoInf2.retrievemarksObtainedForSubjectEnrichment(subjectID,
										entry.getValue(), studentID, AYClassID);

								marksObtainedValue1 += ", " + marksObtainedNewValue1;
								System.out.println("marksObtainedValue1 :" + marksObtainedValue1);
							}
						}

						if (marksObtainedValue1.startsWith(",")) {
							marksObtainedValue1 = marksObtainedValue1.substring(1);
						}

						NotebookList.add(marksObtainedValue1);

						value = daoInf2.retrieveScholasticGradeList(subjectID, NewExaminationList.get("Term End"),
								studentID, AYClassID);

						value1 = daoInf2.retrieveScholasticGradeList(subjectID, NewExaminationList.get("Unit Test"),
								studentID, AYClassID);

						value4 = daoInf2.retrieveScholasticGradeList1(subjectID, studentID, AYClassID, term);

						totalMarksScaled = (value + value1 + value4);

						// toatlScaleTo1 = totalMarksScaled;
						/* } */

						scaleTo = daoInf2.retrieveScaleTo(subjectID, NewExaminationList.get("Term End"), AYClassID);

						scaleTo1 = daoInf2.retrieveScaleTo(subjectID, NewExaminationList.get("Unit Test"), AYClassID);

						scaleTo4 = daoInf2.retrieveScaleTo1(subjectID, AYClassID);

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
						 * Verifying whether absentFlag is true for any of exam, if so set grade to ex
						 * else set grade as calculated
						 * ,marksObtained1,marksObtained2,marksObtained3,marksObtained4
						 */
						if (termEdCheck) {
							grade = "ex";
							form.setTermEndMarksObtained("ex");
							form.setTermEndMarks("ex");
							form.setFinalTotalMarks("ex");
						} else {
							form.setTermEndMarksObtained("" + marksObtained);
							form.setTermEndMarks("" + value);
							form.setFinalTotalMarks("" + totalMarksScaled);
						}

						if (unitTestCheck) {
							form.setUnitTestMarksObtained("ex");
							form.setUnitTestMarks("ex");
							grade = "ex";
							form.setFinalTotalMarks("ex");
						} else {
							form.setUnitTestMarksObtained("" + marksObtained1);
							form.setUnitTestMarks("" + value1);
							form.setFinalTotalMarks("" + totalMarksScaled);
						}

						form.setNotebookMarks("" + value4);

						form.setRollNumber(Integer.parseInt(rollNo));
						form.setStudentName(studentName);
						form.setGrade(grade);

						studentsCustomReportList.add(form);

					}

				}

			} else {

				studentCustomList = daoInf2.retrieveExistingStudentAssessmentListForClassTeacher(AYClassID);

				NewExaminationList = daoInf2.retrieveExaminationList(academicYearID, term, AYClassID);

				/* double = 0D; */

				int value, value1, value2, value3, value4, value7, value8, finalOutOfMarks = 0;
				int marksObtained, marksObtained1, marksObtained2, marksObtained3, marksObtained4 = 0;
				double value5, value6, finalSEAMarks = 0D;
				int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5 = 0;
				int toatlScaleTo, toatlScaleTo1, outOfMarks, outOfMarks1, outOfMarks2, finalSEAmarksvalue,
						totalMarksScaled = 0;

				String grade = "";

				studentsCustomReportList = new ArrayList<ConfigurationForm>();

				SubjectEnrichmentList = new ArrayList<String>();

				NotebookList = new ArrayList<String>();

				PortfolioList = new ArrayList<String>();

				MultipleAssessmentList = new ArrayList<String>();

				for (Integer studentID : studentCustomList.keySet()) {

					String marksObtainedValue = "", marksObtainedValue1 = "", marksObtainedNewValue2 = "",
							marksObtainedNewValue3 = "", marksObtainedNewValue = "", marksObtainedValue2 = "",
							marksObtainedValue3 = "", marksObtainedNewValue1 = "";

					String[] array = studentCustomList.get(studentID).split("=");

					String studentName = array[0];

					String rollNo = array[1];

					ConfigurationForm form = new ConfigurationForm();

					int gradeCheck = daoInf2.retrieveGradeBasedValue(NewExaminationList.get("Term End"), subjectID,
							AYClassID);

					// System.out.println("grade check.." + gradeCheck);

					if (gradeCheck == 1) {

						boolean termEdCheck = daoInf2.verifyAbsent(subjectID, studentID, AYClassID,
								NewExaminationList.get("Term End"));
						// System.out.println("termEdCheck: " + termEdCheck);
						grade = daoInf2.retrieveGradeValue(subjectID, studentID, AYClassID,
								NewExaminationList.get("Term End"));
						// System.out.println("grade: " + grade);
						if (termEdCheck) {
							grade = "ex";
						}
						System.out.println("studentName: " + studentName);

						form.setStudentName(studentName);
						form.setRollNumber(Integer.parseInt(rollNo));
						form.setTermEndMarks("-");
						form.setTermEndMarksObtained("-");
						form.setUnitTestMarksObtained("-");
						form.setFinalTotalMarks("-");
						form.setUnitTestMarks("-");
						form.setSubjectEnrichmentMarksObtained("-");
						form.setSubjectEnrichmentMarks("-");
						form.setNotebookMarksObtained("-");
						form.setNotebookMarks("-");
						form.setPortfolioMarks("-");
						form.setPortfolioFinalMarks("-");
						form.setMultipleAssessmentMarks("-");
						form.setMultipleAssessmentFinalMarks("-");
						form.setGrade(grade);

						studentsCustomReportList.add(form);

					} else {

						boolean termEdCheck = daoInf2.verifyAbsent(subjectID, studentID, AYClassID,
								NewExaminationList.get("Term End"));

						boolean unitTestCheck = daoInf2.verifyAbsent(subjectID, studentID, AYClassID,
								NewExaminationList.get("Unit Test"));

						marksObtained = daoInf2.retrievemarksObtained(subjectID, NewExaminationList.get("Term End"),
								studentID, AYClassID);

						marksObtained1 = daoInf2.retrievemarksObtained(subjectID, NewExaminationList.get("Unit Test"),
								studentID, AYClassID);

						for (Entry<String, Integer> Subjectentry : NewExaminationList.entrySet()) {
							if (Subjectentry.getKey().startsWith("Subject Enrichment")) {

								// System.out.println("Subject Enrichment: " + Subjectentry.getValue());
								marksObtainedNewValue = daoInf2.retrievemarksObtainedForSubjectEnrichment(subjectID,
										Subjectentry.getValue(), studentID, AYClassID);

								marksObtainedValue += ", " + marksObtainedNewValue;

								// System.out.println("marksObtainedNewValue: " + marksObtainedValue);
							}
						}

						if (marksObtainedValue.startsWith(",")) {
							marksObtainedValue = marksObtainedValue.substring(1);
						}

						SubjectEnrichmentList.add(marksObtainedValue);

						for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
							if (entry.getKey().startsWith("Notebook")) {

								marksObtainedNewValue1 = daoInf2.retrievemarksObtainedForSubjectEnrichment(subjectID,
										entry.getValue(), studentID, AYClassID);

								marksObtainedValue1 += ", " + marksObtainedNewValue1;
								// System.out.println("marksObtainedValue1 :" + marksObtainedValue1);
							}
						}

						if (marksObtainedValue1.startsWith(",")) {
							marksObtainedValue1 = marksObtainedValue1.substring(1);
						}

						NotebookList.add(marksObtainedValue1);

						for (Entry<String, Integer> Portfolioentry : NewExaminationList.entrySet()) {
							if (Portfolioentry.getKey().startsWith("Portfolio")) {

								System.out.println("Portfolio: " + Portfolioentry.getValue());

								marksObtainedNewValue2 = daoInf2.retrievemarksObtainedForSubjectEnrichment(subjectID,
										Portfolioentry.getValue(), studentID, AYClassID);

								marksObtainedValue2 += ", " + marksObtainedNewValue2;
								System.out.println("marksObtainedValue Portfolio :" + marksObtainedValue2);
							}
						}

						if (marksObtainedValue2.startsWith(",")) {
							marksObtainedValue2 = marksObtainedValue2.substring(1);
						}

						PortfolioList.add(marksObtainedValue2);

						for (Entry<String, Integer> MultipleAssessmententry : NewExaminationList.entrySet()) {
							if (MultipleAssessmententry.getKey().startsWith("Multiple Assessment")) {

								System.out.println("MultipleAssessmententry: " + MultipleAssessmententry.getValue());

								marksObtainedNewValue3 = daoInf2.retrievemarksObtainedForSubjectEnrichment(subjectID,
										MultipleAssessmententry.getValue(), studentID, AYClassID);

								marksObtainedValue3 += ", " + marksObtainedNewValue3;
								System.out.println("marksObtainedValue Multiple :" + marksObtainedValue3);
							}
						}

						if (marksObtainedValue3.startsWith(",")) {
							marksObtainedValue3 = marksObtainedValue3.substring(1);
						}

						MultipleAssessmentList.add(marksObtainedValue3);

						value = daoInf2.retrieveScholasticGradeList(subjectID, NewExaminationList.get("Term End"),
								studentID, AYClassID);

						value1 = daoInf2.retrieveScholasticGradeList(subjectID, NewExaminationList.get("Unit Test"),
								studentID, AYClassID);

						value2 = daoInf2.retrieveScholasticGradeListNew(subjectID, "Subject Enrichment", studentID,
								AYClassID, term);

						value3 = daoInf2.retrieveScholasticGradeListNew(subjectID, "Notebook", studentID, AYClassID,
								term);

						value7 = daoInf2.retrieveScholasticGradeListNew(subjectID, "Portfolio", studentID, AYClassID,
								term);

						value8 = daoInf2.retrieveScholasticGradeListNew(subjectID, "Multiple Assessment", studentID,
								AYClassID, term);

						scaleTo = daoInf2.retrieveScaleTo(subjectID, NewExaminationList.get("Term End"), AYClassID);

						scaleTo1 = daoInf2.retrieveScaleTo(subjectID, NewExaminationList.get("Unit Test"), AYClassID);

						scaleTo2 = daoInf2.retrieveScaleToNew(subjectID, "Subject Enrichment", AYClassID,
								academicYearID, term);

						scaleTo3 = daoInf2.retrieveScaleToNew(subjectID, "Notebook", AYClassID, academicYearID, term);

						scaleTo4 = daoInf2.retrieveScaleToNew(subjectID, "Portfolio", AYClassID, academicYearID, term);

						scaleTo5 = daoInf2.retrieveScaleToNew(subjectID, "Multiple Assessment", AYClassID,
								academicYearID, term);

						totalMarksScaled = (value + value1 + value2 + value3 + value7 + value8);

						toatlScaleTo = (scaleTo + scaleTo1 + scaleTo2 + scaleTo3 + scaleTo4 + scaleTo5);

						toatlScaleTo1 = (totalMarksScaled * 100) / toatlScaleTo;

						System.out
								.println("Grade marks: " + toatlScaleTo1 + "-" + toatlScaleTo + "-" + totalMarksScaled);
						/*
						 * if (toatlScaleTo == 50) {
						 * 
						 * toatlScaleTo1 = (totalMarksScaled * 2); } else {
						 * 
						 * toatlScaleTo1 = totalMarksScaled; }
						 */

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
						 * Verifying whether absentFlag is true for any of exam, if so set grade to ex
						 * else set grade as calculated
						 * ,marksObtained1,marksObtained2,marksObtained3,marksObtained4
						 */
						if (termEdCheck) {
							grade = "ex";
							form.setTermEndMarksObtained("ex");
							form.setTermEndMarks("ex");
							form.setFinalTotalMarks("ex");
						} else {
							form.setTermEndMarksObtained("" + marksObtained);
							form.setTermEndMarks("" + value);
							form.setFinalTotalMarks("" + totalMarksScaled);
						}

						if (unitTestCheck) {
							grade = "ex";
							form.setUnitTestMarksObtained("ex");
							form.setUnitTestMarks("ex");
							form.setFinalTotalMarks("ex");
						} else {
							form.setUnitTestMarksObtained("" + marksObtained1);
							form.setUnitTestMarks("" + value1);
							form.setFinalTotalMarks("" + totalMarksScaled);
						}

						form.setNotebookMarks("" + value3);

						form.setSubjectEnrichmentMarks("" + value2);

						form.setPortfolioFinalMarks("" + value7);

						form.setMultipleAssessmentFinalMarks("" + value8);

						form.setRollNumber(Integer.parseInt(rollNo));
						form.setStudentName(studentName);
						form.setGrade(grade);

						studentsCustomReportList.add(form);
					}
				}

			}

			int gradeCheck = daoInf2.retrieveGradeBasedValue(NewExaminationList.get("Term End"), subjectID, AYClassID);
			/*
			 * Setting path to store PDF file
			 */
			File file = new File(realPath + "/" + excelFileName);

			// Workbook wb = new HSSFWorkbook();
			XSSFWorkbook wb = new XSSFWorkbook();

			XSSFSheet spreadSheet = wb.createSheet("Student Custom report");
			Row row;

			row = spreadSheet.createRow(0);

			spreadSheet.setColumnWidth((short) 0, (short) (256 * 25));

			/*
			 * Creating header in XLSX
			 */
			CellStyle headerCellStyle = wb.createCellStyle();

			/*
			 * Setting up the font
			 */
			Font setFont = wb.createFont();
			setFont.setFontHeightInPoints((short) 12);
			setFont.setBold(true);
			headerCellStyle.setFont(setFont);

			/*
			 * Setting up Data style in XLSX
			 */
			CellStyle dataCellStyle = wb.createCellStyle();
			Font setDataFont = wb.createFont();
			setDataFont.setFontHeightInPoints((short) 12);
			dataCellStyle.setFont(setDataFont);

			/*
			 * Initializing Cell reference variable
			 */
			Cell cell = null;

			cell = row.createCell((short) 0);
			cell.setCellValue("Student Custom Report-" + term);
			cell.setCellStyle(dataCellStyle);

			row = spreadSheet.createRow(1);

			cell = row.createCell((short) 0);
			cell.setCellValue("Term: " + term);
			cell.setCellStyle(dataCellStyle);

			row = spreadSheet.createRow(2);

			cell = row.createCell((short) 0);
			cell.setCellValue("Standard: " + StandardName);
			cell.setCellStyle(dataCellStyle);

			row = spreadSheet.createRow(3);

			cell = row.createCell((short) 0);
			cell.setCellValue("Division: " + divisionName);
			cell.setCellStyle(dataCellStyle);

			row = spreadSheet.createRow(4);

			cell = row.createCell((short) 0);
			cell.setCellValue("Subject: " + subjectName);
			cell.setCellStyle(dataCellStyle);

			row = spreadSheet.createRow(5);

			spreadSheet.setColumnWidth((short) 0, (short) (256 * 25));
			spreadSheet.setColumnWidth((short) 1, (short) (256 * 25));

			cell = row.createCell((short) 0);
			cell.setCellValue("Roll No.");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 1);
			cell.setCellValue("Student Name");
			cell.setCellStyle(headerCellStyle);

			if (Stage.equals("Primary")) {

				if (gradeCheck == 0) {

					/*
					 * spreadSheet.setColumnWidth((short) 3, (short) (256 * 25));
					 * spreadSheet.setColumnWidth((short) 4, (short) (256 * 25));
					 * spreadSheet.setColumnWidth((short) 5, (short) (256 * 25));
					 * spreadSheet.setColumnWidth((short) 6, (short) (256 * 25));
					 * spreadSheet.setColumnWidth((short) 7, (short) (256 * 25));
					 * spreadSheet.setColumnWidth((short) 8, (short) (256 * 25));
					 * spreadSheet.setColumnWidth((short) 9, (short) (256 * 25));
					 * spreadSheet.setColumnWidth((short) 10, (short) (256 * 25));
					 * spreadSheet.setColumnWidth((short) 11, (short) (256 * 25));
					 */

					int k = 2;

					/*
					 * cell = row.createCell((short) 2);
					 * cell.setCellValue("Notebook Marks Obtained(10)");
					 * cell.setCellStyle(headerCellStyle);
					 * 
					 * cell = row.createCell((short) 3);
					 * cell.setCellValue("Subject Enrichment1 Marks Obtained(20)");
					 * cell.setCellStyle(headerCellStyle);
					 * 
					 * cell = row.createCell((short) 4);
					 * cell.setCellValue("Subject Enrichment2 Marks Obtained(20)");
					 * cell.setCellStyle(headerCellStyle);
					 */

					for (Entry<String, Integer> SubjectEnrichmententry : NewExaminationList.entrySet()) {
						if (SubjectEnrichmententry.getKey().startsWith("Subject Enrichment")) {

							spreadSheet.setColumnWidth((short) k, (short) (256 * 25));

							cell = row.createCell((short) k);
							cell.setCellValue(SubjectEnrichmententry.getKey() + "(5)");
							cell.setCellStyle(headerCellStyle);

							k++;
						}
					}

					for (Entry<String, Integer> Notebookentry : NewExaminationList.entrySet()) {
						if (Notebookentry.getKey().startsWith("Notebook")) {

							spreadSheet.setColumnWidth((short) k, (short) (256 * 25));

							cell = row.createCell((short) k);
							cell.setCellValue(Notebookentry.getKey() + "(10)");
							cell.setCellStyle(headerCellStyle);

							k++;
						}
					}

					cell = row.createCell((short) k++);
					cell.setCellValue("SEA+NB(5)");
					cell.setCellStyle(headerCellStyle);

					cell = row.createCell((short) k++);
					cell.setCellValue("Periodic Test Marks Obtained(20)");
					cell.setCellStyle(headerCellStyle);

					cell = row.createCell((short) k++);
					cell.setCellValue("Periodic Test Marks Scaled(5)");
					cell.setCellStyle(headerCellStyle);

					cell = row.createCell((short) k++);
					cell.setCellValue("Term End Marks Obtained(40)");
					cell.setCellStyle(headerCellStyle);

					cell = row.createCell((short) k++);
					cell.setCellValue("Term End Marks Scaled(40)");
					cell.setCellStyle(headerCellStyle);

					cell = row.createCell((short) k++);
					cell.setCellValue("Total Marks Scaled(50)");
					cell.setCellStyle(headerCellStyle);

					cell = row.createCell((short) k++);
					cell.setCellValue("Grade");
					cell.setCellStyle(headerCellStyle);

				} else if (gradeCheck == 1) {
					spreadSheet.setColumnWidth((short) 2, (short) (256 * 25));

					cell = row.createCell((short) 2);
					cell.setCellValue("Grade");
					cell.setCellStyle(headerCellStyle);
				}

			} else {

				int k = 2;

				if (gradeCheck == 0) {

					/*
					 * spreadSheet.setColumnWidth((short) 2, (short) (256 * 25));
					 * spreadSheet.setColumnWidth((short) 3, (short) (256 * 25));
					 * spreadSheet.setColumnWidth((short) 4, (short) (256 * 25));
					 * spreadSheet.setColumnWidth((short) 5, (short) (256 * 25));
					 * spreadSheet.setColumnWidth((short) 6, (short) (256 * 25));
					 * spreadSheet.setColumnWidth((short) 7, (short) (256 * 25));
					 * spreadSheet.setColumnWidth((short) 8, (short) (256 * 25));
					 * spreadSheet.setColumnWidth((short) 9, (short) (256 * 25));
					 * spreadSheet.setColumnWidth((short) 10, (short) (256 * 25));
					 */

					for (Entry<String, Integer> SubjectEnrichmententry : NewExaminationList.entrySet()) {
						if (SubjectEnrichmententry.getKey().startsWith("Subject Enrichment")) {

							spreadSheet.setColumnWidth((short) k, (short) (256 * 25));

							cell = row.createCell((short) k);
							cell.setCellValue(SubjectEnrichmententry.getKey() + "(5)");
							cell.setCellStyle(headerCellStyle);

							k++;
						}
					}

					spreadSheet.setColumnWidth((short) k, (short) (256 * 25));

					cell = row.createCell((short) k);
					cell.setCellValue("Subject Enrichment Marks Scaled(5)");
					cell.setCellStyle(headerCellStyle);

					k++;

					for (Entry<String, Integer> Notebookentry : NewExaminationList.entrySet()) {
						if (Notebookentry.getKey().startsWith("Notebook")) {

							spreadSheet.setColumnWidth((short) k, (short) (256 * 25));

							cell = row.createCell((short) k);
							cell.setCellValue(Notebookentry.getKey() + "(5)");
							cell.setCellStyle(headerCellStyle);

							k++;
						}

					}

					cell = row.createCell((short) k);
					cell.setCellValue("Notebook Marks Scaled(5)");
					cell.setCellStyle(headerCellStyle);

					k++;

					for (Entry<String, Integer> Portfolioentry : NewExaminationList.entrySet()) {
						if (Portfolioentry.getKey().startsWith("Portfolio")) {

							spreadSheet.setColumnWidth((short) k, (short) (256 * 25));

							cell = row.createCell((short) k);
							cell.setCellValue(Portfolioentry.getKey() + "(5)");
							cell.setCellStyle(headerCellStyle);

							k++;
						}
					}

					cell = row.createCell((short) k);
					cell.setCellValue("Portfolio Marks Scaled(5)");
					cell.setCellStyle(headerCellStyle);

					k++;

					for (Entry<String, Integer> MultipleAssessmententry : NewExaminationList.entrySet()) {
						if (MultipleAssessmententry.getKey().startsWith("Multiple Assessment")) {

							spreadSheet.setColumnWidth((short) k, (short) (256 * 25));

							cell = row.createCell((short) k);
							cell.setCellValue(MultipleAssessmententry.getKey() + "(5)");
							cell.setCellStyle(headerCellStyle);

							k++;
						}
					}

					cell = row.createCell((short) k);
					cell.setCellValue("MultipleAssessment Marks Scaled(5)");
					cell.setCellStyle(headerCellStyle);

					k++;

					cell = row.createCell((short) k++);
					cell.setCellValue("Periodic Test Marks Obtained(40)");
					cell.setCellStyle(headerCellStyle);

					cell = row.createCell((short) k++);
					cell.setCellValue("Periodic Test Marks Scaled(10)");
					cell.setCellStyle(headerCellStyle);

					cell = row.createCell((short) k++);
					cell.setCellValue("Term End Marks Obtained(30/80)");
					cell.setCellStyle(headerCellStyle);

					cell = row.createCell((short) k++);
					cell.setCellValue("Term End Marks Scaled(30/80)");
					cell.setCellStyle(headerCellStyle);

					cell = row.createCell((short) k++);
					cell.setCellValue("Total Marks Scaled(50/100)");
					cell.setCellStyle(headerCellStyle);

					cell = row.createCell((short) k++);
					cell.setCellValue("Grade");
					cell.setCellStyle(headerCellStyle);

				} else if (gradeCheck == 1) {
					spreadSheet.setColumnWidth((short) 0, (short) (256 * 25));

					cell = row.createCell((short) 2);
					cell.setCellValue("Grade");
					cell.setCellStyle(headerCellStyle);
				}
			}
			currentRow = 6;

			/* studentsCustomReportList = new ArrayList<ConfigurationForm>(); */

			/*
			 * SubjectEnrichmentList = new ArrayList<String>();
			 * 
			 * NotebookList = new ArrayList<String>();
			 */

			int k = 0;
			int j = 0;

			for (ConfigurationForm form : studentsCustomReportList) {

				row = spreadSheet.createRow(currentRow++);

				cell = row.createCell((short) 0);
				cell.setCellValue(form.getRollNumber());
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 1);
				cell.setCellValue(form.getStudentName());
				cell.setCellStyle(dataCellStyle);

				if (Stage.equals("Primary")) {

					int m = 2;

					if (gradeCheck == 0) {

						if (SubjectEnrichmentList.get(k).contains(",")) {
							String[] SubjectEnrichmentMarks = SubjectEnrichmentList.get(k).split(",");

							for (int i = 0; i < SubjectEnrichmentMarks.length; i++) {

								if (SubjectEnrichmentMarks[i].contains("$")) {

									String[] SubjectEnrichmentMarksValue = SubjectEnrichmentMarks[i].split("\\$");

									if (SubjectEnrichmentMarksValue[2].equals("1")) {

										cell = row.createCell((short) m);
										cell.setCellValue("ex");
										cell.setCellStyle(dataCellStyle);

									} else {

										cell = row.createCell((short) m);
										cell.setCellValue(Integer.parseInt(SubjectEnrichmentMarksValue[1]));
										cell.setCellStyle(dataCellStyle);
									}

								} else {

									cell = row.createCell((short) m);
									cell.setCellValue("0");
									cell.setCellStyle(dataCellStyle);
								}

								m++;
							}

						} else {

							if (SubjectEnrichmentList.get(k).contains("$")) {

								String[] SubjectEnrichmentMarksValue = SubjectEnrichmentList.get(k).split("\\$");

								if (SubjectEnrichmentMarksValue[2].equals("1")) {

									cell = row.createCell((short) m);
									cell.setCellValue("ex");
									cell.setCellStyle(dataCellStyle);

								} else {
									cell = row.createCell((short) m);
									cell.setCellValue(Integer.parseInt(SubjectEnrichmentMarksValue[1]));
									cell.setCellStyle(dataCellStyle);

								}

							} else {
								cell = row.createCell((short) m);
								cell.setCellValue("0");
								cell.setCellStyle(dataCellStyle);
							}
							m++;
						}

						if (NotebookList.get(k).contains(",")) {

							String[] NotebookMarksMarks = NotebookList.get(k).split(",");

							for (int i = 0; i < NotebookMarksMarks.length; i++) {

								String[] NotebookMarksMarksValue = NotebookMarksMarks[i].split("\\$");

								if (NotebookMarksMarksValue[2].equals("1")) {

									cell = row.createCell((short) m);
									cell.setCellValue("ex");
									cell.setCellStyle(dataCellStyle);

								} else {
									cell = row.createCell((short) m);
									cell.setCellValue(Integer.parseInt(NotebookMarksMarksValue[1]));
									cell.setCellStyle(dataCellStyle);

								}
								m++;
							}

						} else {

							if (NotebookList.get(k).contains("$")) {

								String[] NotebookMarksMarksValue = NotebookList.get(k).split("\\$");

								if (NotebookMarksMarksValue[2].equals("1")) {

									cell = row.createCell((short) m);
									cell.setCellValue("ex");
									cell.setCellStyle(dataCellStyle);

								} else {
									System.out.println("NotebookMarksMarksValue : " + NotebookMarksMarksValue[1]);
									cell = row.createCell((short) m);
									cell.setCellValue(Integer.parseInt(NotebookMarksMarksValue[1]));
									cell.setCellStyle(dataCellStyle);
								}

							} else {
								cell = row.createCell((short) m);
								cell.setCellValue("0");
								cell.setCellStyle(dataCellStyle);
							}
							m++;
						}

						cell = row.createCell((short) m++);
						cell.setCellValue(form.getNotebookMarks());
						cell.setCellStyle(dataCellStyle);

						cell = row.createCell((short) m++);
						cell.setCellValue(form.getUnitTestMarksObtained());
						cell.setCellStyle(dataCellStyle);

						cell = row.createCell((short) m++);
						cell.setCellValue(form.getUnitTestMarks());
						cell.setCellStyle(dataCellStyle);

						cell = row.createCell((short) m++);
						cell.setCellValue(form.getTermEndMarksObtained());
						cell.setCellStyle(dataCellStyle);

						cell = row.createCell((short) m++);
						cell.setCellValue(form.getTermEndMarks());
						cell.setCellStyle(dataCellStyle);

						cell = row.createCell((short) m++);
						cell.setCellValue(form.getFinalTotalMarks());
						cell.setCellStyle(dataCellStyle);

						cell = row.createCell((short) m++);
						cell.setCellValue(form.getGrade());
						cell.setCellStyle(dataCellStyle);

					} else if (gradeCheck == 1) {

						cell = row.createCell((short) 2);
						cell.setCellValue(form.getGrade());
						cell.setCellStyle(dataCellStyle);

					}

					k++;

				} else {

					int m = 2;

					if (gradeCheck == 0) {

						if (SubjectEnrichmentList.get(j).contains(",")) {

							String[] SubjectEnrichmentMarks = SubjectEnrichmentList.get(j).split(",");

							for (int i = 0; i < SubjectEnrichmentMarks.length; i++) {

								if (SubjectEnrichmentMarks[i].contains("$")) {

									String[] SubjectEnrichmentMarksValue = SubjectEnrichmentMarks[i].split("\\$");

									if (SubjectEnrichmentMarksValue[2].equals("1")) {

										cell = row.createCell((short) m);
										cell.setCellValue("ex");
										cell.setCellStyle(dataCellStyle);

									} else {

										cell = row.createCell((short) m);
										cell.setCellValue(Integer.parseInt(SubjectEnrichmentMarksValue[1]));
										cell.setCellStyle(dataCellStyle);

									}
								} else {

									cell = row.createCell((short) m);
									cell.setCellValue("0");
									cell.setCellStyle(dataCellStyle);
								}
								m++;
							}
						} else {

							if (SubjectEnrichmentList.get(j).contains("$")) {

								String[] SubjectEnrichmentMarksValue = SubjectEnrichmentList.get(j).split("\\$");

								if (SubjectEnrichmentMarksValue[2].equals("1")) {

									cell = row.createCell((short) m);
									cell.setCellValue("ex");
									cell.setCellStyle(dataCellStyle);

								} else {

									cell = row.createCell((short) m);
									cell.setCellValue(Integer.parseInt(SubjectEnrichmentMarksValue[1]));
									cell.setCellStyle(dataCellStyle);

								}
							}

							m++;

						}

						cell = row.createCell((short) m);
						cell.setCellValue(form.getSubjectEnrichmentMarks());
						cell.setCellStyle(dataCellStyle);

						m++;

						if (NotebookList.get(j).contains(",")) {
							String[] NotebookMarksMarks = NotebookList.get(j).split(",");

							for (int i = 0; i < NotebookMarksMarks.length; i++) {

								String[] NotebookMarksMarksValue = NotebookMarksMarks[i].split("\\$");

								if (NotebookMarksMarksValue[2].equals("1")) {

									cell = row.createCell((short) m);
									cell.setCellValue("ex");
									cell.setCellStyle(dataCellStyle);

								} else {

									cell = row.createCell((short) m);
									cell.setCellValue(Integer.parseInt(NotebookMarksMarksValue[1]));
									cell.setCellStyle(dataCellStyle);

								}

								m++;
							}

						} else {

							if (NotebookList.get(j).contains("$")) {

								String[] NotebookMarksMarksValue = NotebookList.get(j).split("\\$");

								if (NotebookMarksMarksValue[2].equals("1")) {

									cell = row.createCell((short) m);
									cell.setCellValue("ex");
									cell.setCellStyle(dataCellStyle);

								} else {
									cell = row.createCell((short) m);
									cell.setCellValue(Integer.parseInt(NotebookMarksMarksValue[1]));
									cell.setCellStyle(dataCellStyle);

								}
							} else {

								cell = row.createCell((short) m);
								cell.setCellValue("0");
								cell.setCellStyle(dataCellStyle);

							}
							m++;
						}

						/*
						 * cell = row.createCell((short) 2);
						 * cell.setCellValue(form.getNotebookMarksObtained());
						 * cell.setCellStyle(dataCellStyle);
						 */
						// System.out.println("form.getNotebookMarks(): " + form.getNotebookMarks());
						cell = row.createCell((short) m++);
						cell.setCellValue(form.getNotebookMarks());
						cell.setCellStyle(dataCellStyle);

						if (PortfolioList.get(k).contains(",")) {

							String[] PortfolioMarks = PortfolioList.get(k).split(",");

							for (int i = 0; i < PortfolioMarks.length; i++) {

								if (PortfolioMarks[i].contains("$")) {

									String[] PortfolioMarksValue = PortfolioMarks[i].split("\\$");

									if (PortfolioMarksValue[2].equals("1")) {

										cell = row.createCell((short) m);
										cell.setCellValue("ex");
										cell.setCellStyle(dataCellStyle);

									} else {

										cell = row.createCell((short) m);
										cell.setCellValue(Integer.parseInt(PortfolioMarksValue[1]));
										cell.setCellStyle(dataCellStyle);
									}

								} else {

									cell = row.createCell((short) m);
									cell.setCellValue("0");
									cell.setCellStyle(dataCellStyle);
								}

								m++;
							}

						} else {

							if (PortfolioList.get(k).contains("$")) {

								String[] PortfolioMarksValue = PortfolioList.get(k).split("\\$");

								if (PortfolioMarksValue[2].equals("1")) {

									cell = row.createCell((short) m);
									cell.setCellValue("ex");
									cell.setCellStyle(dataCellStyle);

								} else {
									cell = row.createCell((short) m);
									cell.setCellValue(Integer.parseInt(PortfolioMarksValue[1]));
									cell.setCellStyle(dataCellStyle);

								}
								m++;
							}

						}

						cell = row.createCell((short) m++);
						cell.setCellValue(form.getPortfolioFinalMarks());
						cell.setCellStyle(dataCellStyle);

						if (MultipleAssessmentList.get(k).contains(",")) {
							String[] MultipleAssessmentMarks = MultipleAssessmentList.get(k).split(",");

							for (int i = 0; i < MultipleAssessmentMarks.length; i++) {

								if (MultipleAssessmentMarks[i].contains("$")) {

									String[] MultipleAssessmentMarksValue = MultipleAssessmentMarks[i].split("\\$");

									if (MultipleAssessmentMarksValue[2].equals("1")) {

										cell = row.createCell((short) m);
										cell.setCellValue("ex");
										cell.setCellStyle(dataCellStyle);

									} else {

										cell = row.createCell((short) m);
										cell.setCellValue(Integer.parseInt(MultipleAssessmentMarksValue[1]));
										cell.setCellStyle(dataCellStyle);
									}

								} else {

									cell = row.createCell((short) m);
									cell.setCellValue("0");
									cell.setCellStyle(dataCellStyle);
								}

								m++;
							}

						} else {

							if (MultipleAssessmentList.get(k).contains("$")) {

								String[] MultipleAssessmentMarksValue = MultipleAssessmentList.get(k).split("\\$");

								if (MultipleAssessmentMarksValue[2].equals("1")) {

									cell = row.createCell((short) m);
									cell.setCellValue("ex");
									cell.setCellStyle(dataCellStyle);

								} else {
									cell = row.createCell((short) m);
									cell.setCellValue(Integer.parseInt(MultipleAssessmentMarksValue[1]));
									cell.setCellStyle(dataCellStyle);

								}
								m++;
							}
						}

						cell = row.createCell((short) m++);
						cell.setCellValue(form.getMultipleAssessmentFinalMarks());
						cell.setCellStyle(dataCellStyle);

						cell = row.createCell((short) m++);
						cell.setCellValue(form.getUnitTestMarksObtained());
						cell.setCellStyle(dataCellStyle);

						cell = row.createCell((short) m++);
						cell.setCellValue(form.getUnitTestMarks());
						cell.setCellStyle(dataCellStyle);

						cell = row.createCell((short) m++);
						cell.setCellValue(form.getTermEndMarksObtained());
						cell.setCellStyle(dataCellStyle);

						cell = row.createCell((short) m++);
						cell.setCellValue(form.getTermEndMarks());
						cell.setCellStyle(dataCellStyle);

						cell = row.createCell((short) m++);
						cell.setCellValue(form.getFinalTotalMarks());
						cell.setCellStyle(dataCellStyle);

						cell = row.createCell((short) m++);
						cell.setCellValue(form.getGrade());
						cell.setCellStyle(dataCellStyle);

					} else if (gradeCheck == 1) {
						cell = row.createCell((short) 2);
						cell.setCellValue(form.getGrade());
						cell.setCellStyle(dataCellStyle);
					}

					j++;
					k++;
				}
			}

			/*
			 * writing to the xlsx file
			 */
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wb.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();

			/*
			 * Write the output to a file
			 */
			FileOutputStream fileOut = new FileOutputStream(new File(realPath + "/" + excelFileName));
			fileOut.write(outArray);
			fileOut.flush();
			fileOut.close();

			System.out.println("Excel sheet for Students Customised Report is created successfully.");

			status = "success";

			/*
			 * Closing resultSet, preparedStatement, connection objects
			 */

		} catch (Exception exception) {
			exception.printStackTrace();
			status = "error";
		}
		return status;

	}

	public String exportStudentHistoryReportExcel(ConfigurationForm conform, int academicYearID, String realPath,
			String excelFileName) {

		int currentRow = 1;

		try {

			File file = new File(realPath + "/" + excelFileName);

			/*
			 * Generating XLSX Header value for Patient Information
			 */

			// Workbook wb = new HSSFWorkbook();
			XSSFWorkbook wb = new XSSFWorkbook();

			XSSFSheet spreadSheet = wb.createSheet("Students Excel Sheet");
			Row row;

			row = spreadSheet.createRow(0);

			spreadSheet.setColumnWidth((short) 0, (short) (256 * 25));

			int counter = 1;

			/*
			 * Creating header in XLSX
			 */
			CellStyle headerCellStyle = wb.createCellStyle();

			/*
			 * Setting up the font
			 */
			Font setFont = wb.createFont();
			setFont.setFontHeightInPoints((short) 12);
			setFont.setBold(true);
			headerCellStyle.setFont(setFont);

			/*
			 * Setting up Data style in XLSX
			 */
			CellStyle dataCellStyle = wb.createCellStyle();
			Font setDataFont = wb.createFont();
			setDataFont.setFontHeightInPoints((short) 12);
			dataCellStyle.setFont(setDataFont);

			/*
			 * Initializing Cell reference variable
			 */
			Cell cell = null;

			cell = row.createCell((short) 0);
			cell.setCellValue("GR No.");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 1);
			cell.setCellValue("Student First Name");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 2);
			cell.setCellValue("Student Middle Name");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 3);
			cell.setCellValue("Student Last Name");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 4);
			cell.setCellValue("Standard");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 5);
			cell.setCellValue("Division");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 6);
			cell.setCellValue("Roll No.");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 7);
			cell.setCellValue("Address");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 8);
			cell.setCellValue("Date Of Birth");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 9);
			cell.setCellValue("Weight");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 10);
			cell.setCellValue("Height");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 11);
			cell.setCellValue("Blood Group");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 12);
			cell.setCellValue("Has Spectacles");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 13);
			cell.setCellValue("Emergency Contact Name");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 14);
			cell.setCellValue("Emergency Contact Number");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 15);
			cell.setCellValue("Aadhaar No");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 16);
			cell.setCellValue("House");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 17);
			cell.setCellValue("Physical Activity");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 18);
			cell.setCellValue("Creative Activity");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 19);
			cell.setCellValue("Student's Religion");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 20);
			cell.setCellValue("Category");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 21);
			cell.setCellValue("Siblings Details");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 22);
			cell.setCellValue("Mode of Transport");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 23);
			cell.setCellValue("Rickshaw/Van Kaka's Name");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 24);
			cell.setCellValue("Major Illness/Surgery/Allergy");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 25);
			cell.setCellValue("Fathers First Name");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 26);
			cell.setCellValue("Fathers Middle Name");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 27);
			cell.setCellValue("Fathers Last Name");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 28);
			cell.setCellValue("Fathers Mobile");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 29);
			cell.setCellValue("Fathers Occupation");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 30);
			cell.setCellValue("Fathers EmailId");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 31);
			cell.setCellValue("Mothers First Name");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 32);
			cell.setCellValue("Mothers Middle Name");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 33);
			cell.setCellValue("Mothers Last Name");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 34);
			cell.setCellValue("Mothers Mobile");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 35);
			cell.setCellValue("Mothers Occupation");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 36);
			cell.setCellValue("Mothers EmailId");
			cell.setCellStyle(headerCellStyle);

			connection = getConnection();

			String retrieveStudentListByAcademicYearIDQuery = QueryMaker.RETRIEVE_StudentList_By_AcademicYearID;

			preparedStatement = connection.prepareStatement(retrieveStudentListByAcademicYearIDQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			ResultSetMetaData metaData = resultSet.getMetaData();

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

			while (resultSet.next()) {

				row = spreadSheet.createRow(currentRow++);

				cell = row.createCell((short) 0);
				cell.setCellValue(resultSet.getString("grNumber"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 1);
				cell.setCellValue(resultSet.getString("firstName"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 2);
				cell.setCellValue(resultSet.getString("middleName"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 3);
				cell.setCellValue(resultSet.getString("lastName"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 4);
				cell.setCellValue(resultSet.getString("standard"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 5);
				cell.setCellValue(resultSet.getString("division"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 6);
				cell.setCellValue(resultSet.getInt("rollNumber"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 7);
				cell.setCellValue(resultSet.getString("address"));
				cell.setCellStyle(dataCellStyle);

				CellStyle cellStyle = wb.createCellStyle();
				CreationHelper createHelper = wb.getCreationHelper();
				cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("m-d-yyyy"));

				cell = row.createCell((short) 8);
				String Date = dateFormat.format(dateFormat1.parse(resultSet.getString("dateOfBirth")));

				java.util.Date convertedDate = dateFormat.parse(Date);

				cell.setCellValue(Date);
				cell.setCellStyle(cellStyle);

				cell = row.createCell((short) 9);
				cell.setCellValue(resultSet.getDouble("weight"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 10);
				cell.setCellValue(resultSet.getDouble("height"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 11);
				cell.setCellValue(resultSet.getString("bloodgroup"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 12);
				cell.setCellValue(resultSet.getString("hasSpectacles"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 13);
				cell.setCellValue(resultSet.getString("name"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 14);
				cell.setCellValue("" + resultSet.getString("phone"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 15);
				cell.setCellValue(resultSet.getString("aadhaar"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 16);
				cell.setCellValue(resultSet.getString("house"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 17);
				cell.setCellValue(resultSet.getString("physicalActivities"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 18);
				cell.setCellValue(resultSet.getString("creativeActivities"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 19);
				cell.setCellValue(resultSet.getString("religion"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 20);
				cell.setCellValue(resultSet.getString("category"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 21);
				cell.setCellValue(resultSet.getString("siblingID"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 22);
				cell.setCellValue(resultSet.getString("commutationMode"));
				cell.setCellStyle(dataCellStyle);

				if (resultSet.getString("commutationMode").equals("On your own")) {
					cell = row.createCell((short) 23);
					cell.setCellValue("");
					cell.setCellStyle(dataCellStyle);
				} else {
					cell = row.createCell((short) 23);
					cell.setCellValue(resultSet.getString("nameOfDriver"));
					cell.setCellStyle(dataCellStyle);
				}

				cell = row.createCell((short) 24);
				cell.setCellValue(resultSet.getString("medCondition"));
				cell.setCellStyle(dataCellStyle);

				int studentID = resultSet.getInt("id");

				String retrieveFatherListBySTudentIDQuery = QueryMaker.RETRIEVE_ParentList_By_STudentID;

				preparedStatement1 = connection.prepareStatement(retrieveFatherListBySTudentIDQuery);

				preparedStatement1.setInt(1, studentID);

				preparedStatement1.setString(2, "Father");

				resultSet1 = preparedStatement1.executeQuery();

				while (resultSet1.next()) {

					cell = row.createCell((short) 25);
					cell.setCellValue(resultSet1.getString("firstName"));
					cell.setCellStyle(dataCellStyle);

					cell = row.createCell((short) 26);
					cell.setCellValue(resultSet1.getString("middleName"));
					cell.setCellStyle(dataCellStyle);

					cell = row.createCell((short) 27);
					cell.setCellValue(resultSet1.getString("lastName"));
					cell.setCellStyle(dataCellStyle);

					cell = row.createCell((short) 28);
					cell.setCellValue(resultSet1.getString("mobile"));
					cell.setCellStyle(dataCellStyle);

					cell = row.createCell((short) 29);
					cell.setCellValue(resultSet1.getString("occupation"));
					cell.setCellStyle(dataCellStyle);

					cell = row.createCell((short) 30);
					cell.setCellValue(resultSet1.getString("emailId"));
					cell.setCellStyle(dataCellStyle);

				}

				String retrieveMotherListBySTudentIDQuery = QueryMaker.RETRIEVE_ParentList_By_STudentID;

				preparedStatement2 = connection.prepareStatement(retrieveMotherListBySTudentIDQuery);

				preparedStatement2.setInt(1, studentID);

				preparedStatement2.setString(2, "Mother");

				resultSet2 = preparedStatement2.executeQuery();

				while (resultSet2.next()) {

					cell = row.createCell((short) 31);
					cell.setCellValue(resultSet2.getString("firstName"));
					cell.setCellStyle(dataCellStyle);

					cell = row.createCell((short) 32);
					cell.setCellValue(resultSet2.getString("middleName"));
					cell.setCellStyle(dataCellStyle);

					cell = row.createCell((short) 33);
					cell.setCellValue(resultSet2.getString("lastName"));
					cell.setCellStyle(dataCellStyle);

					cell = row.createCell((short) 34);
					cell.setCellValue(resultSet2.getString("mobile"));
					cell.setCellStyle(dataCellStyle);

					cell = row.createCell((short) 35);
					cell.setCellValue(resultSet2.getString("occupation"));
					cell.setCellStyle(dataCellStyle);

					cell = row.createCell((short) 36);
					cell.setCellValue(resultSet2.getString("emailId"));
					cell.setCellStyle(dataCellStyle);

				}

			}

			/*
			 * writing to the xlsx file
			 */
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wb.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();

			/*
			 * Write the output to a file
			 */
			FileOutputStream fileOut = new FileOutputStream(new File(realPath + "/" + excelFileName));
			fileOut.write(outArray);
			fileOut.flush();
			fileOut.close();

			System.out.println("Excel sheet for Students exported successfully.");

			status = "success";

			/*
			 * Closing resultSet, preparedStatement, connection objects
			 */

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;

	}

	public String importStudentHistoryReportExcel(ConfigurationForm conform, File excelFileName, int academicYearID)
			throws IOException, InvalidFormatException {

		StuduntDAOInf daoInf = new StudentDAOImpl();

		try {

			DataFormatter formatter = new DataFormatter();

			Workbook wb = WorkbookFactory.create(excelFileName);

			Sheet sheet = wb.getSheetAt(0);

			int totalRows = sheet.getPhysicalNumberOfRows();

			System.out.println("Total rows.." + totalRows);

			Map<String, Integer> map = new HashMap<String, Integer>();

			XSSFRow row = (XSSFRow) sheet.getRow(0);

			short minColIx = row.getFirstCellNum(); // get the first column index for a row
			short maxColIx = row.getLastCellNum(); // get the last column index for a row

			System.out.println("Total col index.." + minColIx + "--" + maxColIx);

			// int i = 1;
			for (short colIx = minColIx; colIx < maxColIx; colIx++) { // loop from first to last index
				// System.out.println("inside..."+i);
				XSSFCell cell = row.getCell(colIx); // get the cell
				map.put(cell.getStringCellValue(), cell.getColumnIndex()); // add the cell contents (name of column) and
																			// cell index to the map
				// i++;
			}

			List<ReportRow> list = new ArrayList<ReportRow>();

			for (int j = 1; j < totalRows; j++) {
				ReportRow reportRow = new ReportRow();

				// System.out.println("inside...."+j);

				XSSFRow dataRow = (XSSFRow) sheet.getRow(j);

				int grnoColIndex = map.get("GR No.");
				int studfnameColIndex = map.get("Student First Name");
				int studmnameColIndex = map.get("Student Middle Name");
				int studlnameColIndex = map.get("Student Last Name");
				int StdColIndex = map.get("Standard");
				int DivColIndex = map.get("Division");
				int RollnoColIndex = map.get("Roll No.");
				int AddrColIndex = map.get("Address");
				int DOBColIndex = map.get("Date Of Birth");
				int WeightColIndex = map.get("Weight");
				int HeightColIndex = map.get("Height");
				int BloodGrpColIndex = map.get("Blood Group");
				int spectColIndex = map.get("Has Spectacles");
				int EnameColIndex = map.get("Emergency Contact Name");
				int EphoneColIndex = map.get("Emergency Contact Number");
				int AadhaarColIndex = map.get("Aadhaar No");
				int HouseColIndex = map.get("House");
				int PhyActivityColIndex = map.get("Physical Activity");
				int CreActivityColIndex = map.get("Creative Activity");
				int ReligionColIndex = map.get("Student's Religion");
				int CategoryColIndex = map.get("Category");
				/* int SiblingsColIndex = map.get("Siblings Details"); */
				int TransportColIndex = map.get("Mode of Transport");
				int TransportNameColIndex = map.get("Rickshaw/Van Kaka's Name");
				int MedicalColIndex = map.get("Major Illness/Surgery/Allergy");
				int PFirstNameColIndex = map.get("Fathers First Name");
				int PMiddleNameColIndex = map.get("Fathers Middle Name");
				int PLastNameColIndex = map.get("Fathers Last Name");
				int PMobileColIndex = map.get("Fathers Mobile");
				int POccupationColIndex = map.get("Fathers Occupation");
				int PEmailIdColIndex = map.get("Fathers EmailId");
				int MFirstNameColIndex = map.get("Mothers First Name");
				int MMiddleNameColIndex = map.get("Mothers Middle Name");
				int MLastNameColIndex = map.get("Mothers Last Name");
				int MMobileColIndex = map.get("Mothers Mobile");
				int MOccupationColIndex = map.get("Mothers Occupation");
				int MEmailIdColIndex = map.get("Mothers EmailId");

				XSSFCell grnoCell = dataRow.getCell(grnoColIndex);
				XSSFCell studentfnameCell = dataRow.getCell(studfnameColIndex);
				XSSFCell studentmnameCell = dataRow.getCell(studmnameColIndex);
				XSSFCell studentlnameCell = dataRow.getCell(studlnameColIndex);
				XSSFCell stdCell = dataRow.getCell(StdColIndex);
				XSSFCell divCell = dataRow.getCell(DivColIndex);
				XSSFCell rollNoCell = dataRow.getCell(RollnoColIndex);
				XSSFCell addrCell = dataRow.getCell(AddrColIndex);
				XSSFCell DOBCell = dataRow.getCell(DOBColIndex);
				XSSFCell weightCell = dataRow.getCell(WeightColIndex);
				XSSFCell heightCell = dataRow.getCell(HeightColIndex);
				XSSFCell bloodgrpCell = dataRow.getCell(BloodGrpColIndex);
				XSSFCell spectCell = dataRow.getCell(spectColIndex);
				XSSFCell enameCell = dataRow.getCell(EnameColIndex);
				XSSFCell ephoneCell = dataRow.getCell(EphoneColIndex);
				XSSFCell aadharCell = dataRow.getCell(AadhaarColIndex);
				XSSFCell housetCell = dataRow.getCell(HouseColIndex);
				XSSFCell PhyActivityCell = dataRow.getCell(PhyActivityColIndex);
				XSSFCell CreActivityCell = dataRow.getCell(CreActivityColIndex);
				XSSFCell religionCell = dataRow.getCell(ReligionColIndex);
				XSSFCell CategoryCell = dataRow.getCell(CategoryColIndex);
				XSSFCell transportCell = dataRow.getCell(TransportColIndex);
				XSSFCell TransportNameCell = dataRow.getCell(TransportNameColIndex);
				XSSFCell MedicalCell = dataRow.getCell(MedicalColIndex);
				XSSFCell PFirstNameCell = dataRow.getCell(PFirstNameColIndex);
				XSSFCell PMiddleNameCell = dataRow.getCell(PMiddleNameColIndex);
				XSSFCell PLastNameCell = dataRow.getCell(PLastNameColIndex);
				XSSFCell PMobileCell = dataRow.getCell(PMobileColIndex);
				XSSFCell POccupationCell = dataRow.getCell(POccupationColIndex);
				XSSFCell PEmailIdCell = dataRow.getCell(PEmailIdColIndex);
				XSSFCell MFirstNameCell = dataRow.getCell(MFirstNameColIndex);
				XSSFCell MMiddleNameCell = dataRow.getCell(MMiddleNameColIndex);
				XSSFCell MLastNameCell = dataRow.getCell(MLastNameColIndex);
				XSSFCell MMobileCell = dataRow.getCell(MMobileColIndex);
				XSSFCell MOccupationCell = dataRow.getCell(MOccupationColIndex);
				XSSFCell MEmailIdCell = dataRow.getCell(MEmailIdColIndex);

				reportRow.setGrNumber("" + grnoCell);
				reportRow.setFirstName(studentfnameCell.getStringCellValue());
				reportRow.setMiddleName(studentmnameCell.getStringCellValue());
				reportRow.setLastName(studentlnameCell.getStringCellValue());
				reportRow.setStandard(stdCell.getStringCellValue());
				reportRow.setDivision(divCell.getStringCellValue());
				reportRow.setRollNumber((int) rollNoCell.getNumericCellValue());
				reportRow.setAddress("" + addrCell);
				reportRow.setDateOfBirth("" + DOBCell);
				reportRow.setWeight(weightCell.getNumericCellValue());
				reportRow.setHeight(heightCell.getNumericCellValue());
				reportRow.setBloodgroup("" + bloodgrpCell);
				reportRow.setHasSpectacles("" + spectCell);
				reportRow.setName("" + enameCell);
				reportRow.setPhone("" + ephoneCell);
				reportRow.setAadhaar("" + aadharCell);
				reportRow.setHouse("" + housetCell);
				reportRow.setPhysicalActivities("" + PhyActivityCell);
				reportRow.setCreativeActivities("" + CreActivityCell);
				reportRow.setReligion("" + religionCell);
				reportRow.setCategory("" + CategoryCell);
				reportRow.setCommutationMode("" + transportCell);
				reportRow.setNameOfDriver("" + TransportNameCell);
				reportRow.setMedCondition("" + MedicalCell);
				reportRow.setPfirstName("" + PFirstNameCell);
				reportRow.setPmiddleName("" + PMiddleNameCell);
				reportRow.setPlastName("" + PLastNameCell);
				reportRow.setPmobile("" + PMobileCell);
				reportRow.setPOccupation("" + POccupationCell);
				reportRow.setPemailId("" + PEmailIdCell);
				reportRow.setMfirstName("" + MFirstNameCell);
				reportRow.setMmiddleName("" + MMiddleNameCell);
				reportRow.setMlastName("" + MLastNameCell);
				reportRow.setMmobile("" + MMobileCell);
				reportRow.setMOccupation("" + MOccupationCell);
				reportRow.setMemailId("" + MEmailIdCell);

				list.add(reportRow);
			}

			/*
			 * GR No. Students First Name Students Middle Name Students Last Name Standard
			 * Division Roll No. Address Date Of Birth Weight Height Blood Group Has
			 * Spectacles Emergency Contact Name Emergency Contact Number Aadhaar No House
			 * Physical Activity Creative Activity Student's Religion Category Siblings
			 * Details Mode of Transport Rickshaw/Van Kaka's Name Major
			 * Illness/Surgery/Allergy
			 */

			for (ReportRow reportRow : list) {

				statusMessage = daoInf.importStudentDetails(reportRow);
				if (statusMessage.equalsIgnoreCase("success")) {
					/*
					 * Retrieving patientID from Patient table on the basis of firstname lastname
					 * and dateOfBirth
					 */

					int studentID = daoInf.retrievestudentID("" + reportRow.getAadhaar());

					if (studentID != 0) {
						System.out.println("Student ID is ::: " + studentID);
						/*
						 * Inserting identification details into Identification table
						 */
						statusMessage = daoInf.importStudentContact(reportRow, studentID);

						/*
						 * inserting emergency contact information into EmergencyContact table
						 */
						int CommutationID = daoInf.retrieveCommutationID(reportRow.getNameOfDriver());

						statusMessage = daoInf.importStudentPersonalInfo(reportRow, studentID, CommutationID);

						statusMessage = daoInf.importEmergencyContacts(reportRow, studentID);

						String NewCondition = reportRow.getMedCondition();

						if (NewCondition.contains(",")) {

							String[] Condition = NewCondition.split(",");

							for (int i = 0; i < Condition.length; i++) {

								statusMessage = daoInf.insertCondition(Condition[i], studentID);
								statusMessage = "success";
							}

						} else {

							if (reportRow.getMedCondition() == "" || reportRow.getMedCondition() == null) {
								continue;
							} else if (reportRow.getMedCondition().isEmpty()) {
								continue;
							} else {
								statusMessage = daoInf.insertCondition(reportRow.getMedCondition(), studentID);
								statusMessage = "success";
							}
						}

						int AYClassID = 0;

						AYClassID = daoInf.retrieveAYClassIDNew(reportRow.getStandard(), reportRow.getDivision(),
								academicYearID);

						/*
						 * inserting emergency contact information into EmergencyContact table
						 */
						statusMessage = daoInf.importRegistrationInfo(reportRow, studentID, AYClassID);

						statusMessage = daoInf.importParentsDetails(reportRow.getPfirstName(),
								reportRow.getPmiddleName(), reportRow.getPlastName(), reportRow.getPmobile(),
								reportRow.getPemailId(), reportRow.getPOccupation(), "Father", studentID);

						statusMessage = daoInf.importParentsDetails(reportRow.getMfirstName(),
								reportRow.getMmiddleName(), reportRow.getMlastName(), reportRow.getMmobile(),
								reportRow.getMemailId(), reportRow.getMOccupation(), "Mother", studentID);

					} else {
						System.out.println("Failed to retrieve studentID from database...");
						statusMessage = "error";
					}

				} else {
					System.out.println("Failed to import student Details into student Table");
					statusMessage = "error";
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Invalid file format. Please upload a valid file");
		}

		return statusMessage;
	}

	public String exportConfigurationsExcel(ConfigurationForm conform, int academicYearID, String realPath,
			String excelFileName) {

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		StuduntDAOInf daoInf = new StudentDAOImpl();

		List<ConfigurationForm> AcademicYearDetailsList = null;

		List<ConfigurationForm> AYSubjectDetailsList = null;

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

		int currentRow = 1;

		try {

			File file = new File(realPath + "/" + excelFileName);

			/*
			 * Generating XLSX Header value for Patient Information
			 */

			// Workbook wb = new HSSFWorkbook();
			XSSFWorkbook wb = new XSSFWorkbook();

			CellStyle cellStyle = wb.createCellStyle();
			CreationHelper createHelper = wb.getCreationHelper();
			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("m-d-yyyy"));

			/* SpreadSheet For Attendance */

			XSSFSheet spreadSheet = wb.createSheet("ConfigureTermAttendance");
			Row row;

			row = spreadSheet.createRow(0);

			spreadSheet.setColumnWidth((short) 0, (short) (256 * 25));

			/*
			 * Creating header in XLSX
			 */
			CellStyle headerCellStyle = wb.createCellStyle();

			/*
			 * Setting up the font
			 */
			Font setFont = wb.createFont();
			setFont.setFontHeightInPoints((short) 12);
			setFont.setBold(true);
			headerCellStyle.setFont(setFont);

			/*
			 * Setting up Data style in XLSX
			 */
			CellStyle dataCellStyle = wb.createCellStyle();
			Font setDataFont = wb.createFont();
			setDataFont.setFontHeightInPoints((short) 12);
			dataCellStyle.setFont(setDataFont);

			/*
			 * Initializing Cell reference variable
			 */
			Cell cell = null;

			cell = row.createCell((short) 0);
			cell.setCellValue("AcademicYear");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 1);
			cell.setCellValue("Term");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 2);
			cell.setCellValue("Standard");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 3);
			cell.setCellValue("Month");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 4);
			cell.setCellValue("WorkingDays");
			cell.setCellStyle(headerCellStyle);

			connection = getConnection();

			String retrieveAttendanceConfigurationsByAcademicYearIDQuery = QueryMaker.RETRIEVE_Attendance_Configurations_By_AcademicYearID;

			preparedStatement = connection.prepareStatement(retrieveAttendanceConfigurationsByAcademicYearIDQuery);

			preparedStatement.setInt(1, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				row = spreadSheet.createRow(currentRow++);

				cell = row.createCell((short) 0);
				cell.setCellValue(resultSet.getString("yearName"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 1);
				cell.setCellValue(resultSet.getString("term"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 2);
				cell.setCellValue(resultSet.getString("standard"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 3);
				cell.setCellValue(resultSet.getString("workingMonth"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 4);
				cell.setCellValue(resultSet.getInt("workingDays"));
				cell.setCellStyle(dataCellStyle);

			}

			/* SpreadSheet For Examination */

			XSSFSheet spreadSheet1 = wb.createSheet("ConfigureExamination");
			Row row1;

			row1 = spreadSheet1.createRow(0);

			spreadSheet1.setColumnWidth((short) 0, (short) (256 * 25));

			int counter1 = 1;

			cell = row1.createCell((short) 0);
			cell.setCellValue("AcademicYear");
			cell.setCellStyle(headerCellStyle);

			cell = row1.createCell((short) 1);
			cell.setCellValue("Term");
			cell.setCellStyle(headerCellStyle);

			cell = row1.createCell((short) 2);
			cell.setCellValue("Examination Name");
			cell.setCellStyle(headerCellStyle);

			cell = row1.createCell((short) 3);
			cell.setCellValue("Examination Type");
			cell.setCellStyle(headerCellStyle);

			cell = row1.createCell((short) 4);
			cell.setCellValue("Start Date");
			cell.setCellStyle(headerCellStyle);

			cell = row1.createCell((short) 5);
			cell.setCellValue("End Date");
			cell.setCellStyle(headerCellStyle);

			// connection = getConnection();

			String retrieveExaminationConfigurationsByAcademicYearIDQuery = QueryMaker.RETRIEVE_Examination_Configurations_By_AcademicYearID;

			preparedStatement1 = connection.prepareStatement(retrieveExaminationConfigurationsByAcademicYearIDQuery);

			preparedStatement1.setInt(1, academicYearID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				row1 = spreadSheet1.createRow(counter1++);

				cell = row1.createCell((short) 0);
				cell.setCellValue(resultSet1.getString("yearName"));
				cell.setCellStyle(dataCellStyle);

				cell = row1.createCell((short) 1);
				cell.setCellValue(resultSet1.getString("term"));
				cell.setCellStyle(dataCellStyle);

				cell = row1.createCell((short) 2);
				cell.setCellValue(resultSet1.getString("examName"));
				cell.setCellStyle(dataCellStyle);

				cell = row1.createCell((short) 3);
				cell.setCellValue(resultSet1.getString("examType"));
				cell.setCellStyle(dataCellStyle);

				cell = row1.createCell((short) 4);
				String StartDate = dateFormat.format(dateFormat1.parse(resultSet1.getString("startDate")));

				java.util.Date convertedDate = dateFormat.parse(StartDate);

				cell.setCellValue(StartDate);
				cell.setCellStyle(cellStyle);

				cell = row1.createCell((short) 5);
				String EndDate = dateFormat.format(dateFormat1.parse(resultSet1.getString("endDate")));

				java.util.Date convertedDate1 = dateFormat.parse(EndDate);

				cell.setCellValue(EndDate);
				cell.setCellStyle(cellStyle);

			}

			/* SpreadSheet For TimeTable */

			XSSFSheet spreadSheet2 = wb.createSheet("ConfigureTimeTable");
			Row row2;

			row2 = spreadSheet2.createRow(0);

			spreadSheet2.setColumnWidth((short) 0, (short) (256 * 25));

			int counter2 = 1;

			/*
			 * Initializing Cell reference variable
			 */
			/* Cell cell = null; */

			cell = row2.createCell((short) 0);
			cell.setCellValue("AcademicYear");
			cell.setCellStyle(headerCellStyle);

			cell = row2.createCell((short) 1);
			cell.setCellValue("Exam");
			cell.setCellStyle(headerCellStyle);

			cell = row2.createCell((short) 2);
			cell.setCellValue("Date");
			cell.setCellStyle(headerCellStyle);

			cell = row2.createCell((short) 3);
			cell.setCellValue("Standard");
			cell.setCellStyle(headerCellStyle);

			cell = row2.createCell((short) 4);
			cell.setCellValue("Subject");
			cell.setCellStyle(headerCellStyle);

			// connection = getConnection();

			String retrieveTimeTableConfigurationsByAcademicYearIDQuery = QueryMaker.RETRIEVE_TimeTable_Configurations_By_AcademicYearID;

			preparedStatement2 = connection.prepareStatement(retrieveTimeTableConfigurationsByAcademicYearIDQuery);

			preparedStatement2.setInt(1, academicYearID);

			resultSet2 = preparedStatement2.executeQuery();

			while (resultSet2.next()) {

				row2 = spreadSheet2.createRow(counter2++);

				cell = row2.createCell((short) 0);
				cell.setCellValue(resultSet2.getString("yearName"));
				cell.setCellStyle(dataCellStyle);

				cell = row2.createCell((short) 1);
				cell.setCellValue(resultSet2.getString("examName"));
				cell.setCellStyle(dataCellStyle);

				cell = row2.createCell((short) 2);
				String ExamDate = dateFormat.format(dateFormat1.parse(resultSet2.getString("examDate")));

				java.util.Date convertedDate = dateFormat.parse(ExamDate);

				cell.setCellValue(ExamDate);
				cell.setCellStyle(cellStyle);

				cell = row2.createCell((short) 3);
				cell.setCellValue(resultSet2.getString("standard"));
				cell.setCellStyle(dataCellStyle);

				cell = row2.createCell((short) 4);
				cell.setCellValue(resultSet2.getString("subject"));
				cell.setCellStyle(dataCellStyle);

			}

			/* SpreadSheet For ConfigureAcademicYear */

			XSSFSheet spreadSheet3 = wb.createSheet("ConfigureAcademicYear");
			Row row3;

			row3 = spreadSheet3.createRow(0);

			spreadSheet3.setColumnWidth((short) 0, (short) (256 * 25));

			int counter = 1;

			/*
			 * Initializing Cell reference variable
			 */
			/* Cell cell = null; */

			cell = row3.createCell((short) 0);
			cell.setCellValue("AcademicYear");
			cell.setCellStyle(headerCellStyle);

			cell = row3.createCell((short) 1);
			cell.setCellValue("Standard");
			cell.setCellStyle(headerCellStyle);

			cell = row3.createCell((short) 2);
			cell.setCellValue("Division");
			cell.setCellStyle(headerCellStyle);

			cell = row3.createCell((short) 3);
			cell.setCellValue("Class Teacher");
			cell.setCellStyle(headerCellStyle);

			String Values = "Subject,Teacher1,Teacher2";

			String[] NewValues = Values.split(",");

			int k = 4;

			for (int i = 0; i < 60; i++) {

				for (int j = 0; j < NewValues.length; j++) {

					cell = row3.createCell((short) k);
					cell.setCellValue(NewValues[j]);
					cell.setCellStyle(headerCellStyle);
					k++;
				}
			}

			AcademicYearDetailsList = daoInf2.retrieveAYClassDetails(conform.getAcademicYearID());

			for (ConfigurationForm AcademicYearData : AcademicYearDetailsList) {

				row3 = spreadSheet3.createRow(counter++);

				cell = row3.createCell((short) 0);
				cell.setCellValue(AcademicYearData.getAcademicYear());
				cell.setCellStyle(dataCellStyle);

				cell = row3.createCell((short) 1);
				cell.setCellValue(AcademicYearData.getStandard());
				cell.setCellStyle(dataCellStyle);

				cell = row3.createCell((short) 2);
				cell.setCellValue(AcademicYearData.getDivision());
				cell.setCellStyle(dataCellStyle);

				cell = row3.createCell((short) 3);
				cell.setCellValue(AcademicYearData.getTeacherNameID());
				cell.setCellStyle(dataCellStyle);

				AYSubjectDetailsList = daoInf2.retrieveAYSubjectDetails(AcademicYearData.getAYClassID());

				int counter4 = 4;

				for (ConfigurationForm AYSubjectData : AYSubjectDetailsList) {

					cell = row3.createCell((short) counter4++);
					cell.setCellValue(AYSubjectData.getSubject());
					cell.setCellStyle(dataCellStyle);

					cell = row3.createCell((short) counter4++);
					cell.setCellValue(AYSubjectData.getTeacherName1());
					cell.setCellStyle(dataCellStyle);

					cell = row3.createCell((short) counter4++);
					cell.setCellValue(AYSubjectData.getTeacherName2());
					cell.setCellStyle(dataCellStyle);

				}

			}

			/* SpreadSheet For SubjectAssessment */

			XSSFSheet spreadSheet4 = wb.createSheet("ConfigureSubjectAssessment");
			Row row4;

			row4 = spreadSheet4.createRow(0);

			spreadSheet4.setColumnWidth((short) 0, (short) (256 * 25));

			int counter5 = 1;

			cell = row4.createCell((short) 0);
			cell.setCellValue("Standard");
			cell.setCellStyle(headerCellStyle);

			cell = row4.createCell((short) 1);
			cell.setCellValue("Division");
			cell.setCellStyle(headerCellStyle);

			cell = row4.createCell((short) 2);
			cell.setCellValue("Term");
			cell.setCellStyle(headerCellStyle);

			cell = row4.createCell((short) 3);
			cell.setCellValue("Exam");
			cell.setCellStyle(headerCellStyle);

			cell = row4.createCell((short) 4);
			cell.setCellValue("Subject");
			cell.setCellStyle(headerCellStyle);

			cell = row4.createCell((short) 5);
			cell.setCellValue("Grade Based");
			cell.setCellStyle(headerCellStyle);

			cell = row4.createCell((short) 6);
			cell.setCellValue("Maximum Marks");
			cell.setCellStyle(headerCellStyle);

			cell = row4.createCell((short) 7);
			cell.setCellValue("Scale To");
			cell.setCellStyle(headerCellStyle);

			// connection = getConnection();

			String retrieveSubjectAssessmentConfigurationsByAcademicYearIDQuery = QueryMaker.RETRIEVE_SubjectAssessment_Configurations_By_AcademicYearID;

			preparedStatement3 = connection
					.prepareStatement(retrieveSubjectAssessmentConfigurationsByAcademicYearIDQuery);

			preparedStatement3.setInt(1, academicYearID);
			preparedStatement3.setString(2, ActivityStatus.ACTIVE);

			resultSet3 = preparedStatement3.executeQuery();

			while (resultSet3.next()) {

				row4 = spreadSheet4.createRow(counter5++);

				cell = row4.createCell((short) 0);
				cell.setCellValue(resultSet3.getString("standard"));
				cell.setCellStyle(dataCellStyle);

				cell = row4.createCell((short) 1);
				cell.setCellValue(resultSet3.getString("division"));
				cell.setCellStyle(dataCellStyle);

				cell = row4.createCell((short) 2);
				cell.setCellValue(resultSet3.getString("term"));
				cell.setCellStyle(dataCellStyle);

				cell = row4.createCell((short) 3);
				cell.setCellValue(resultSet3.getString("examName"));
				cell.setCellStyle(dataCellStyle);

				cell = row4.createCell((short) 4);
				cell.setCellValue(resultSet3.getString("name"));
				cell.setCellStyle(dataCellStyle);

				String grade = "";
				if (resultSet3.getString("gradeBased").equals("1")) {
					grade = "Yes";
				} else if (resultSet3.getString("gradeBased").equals("0")) {
					grade = "No";
				} else {
					grade = "";
				}

				cell = row4.createCell((short) 5);
				cell.setCellValue(grade);
				cell.setCellStyle(dataCellStyle);

				cell = row4.createCell((short) 6);
				cell.setCellValue(resultSet3.getString("totalMarks"));
				cell.setCellStyle(dataCellStyle);

				cell = row4.createCell((short) 7);
				cell.setCellValue(resultSet3.getString("scaleTo"));
				cell.setCellStyle(dataCellStyle);

			}

			/*
			 * writing to the xlsx file
			 */
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wb.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();

			/*
			 * Write the output to a file
			 */
			FileOutputStream fileOut = new FileOutputStream(new File(realPath + "/" + excelFileName));
			fileOut.write(outArray);
			fileOut.flush();
			fileOut.close();

			System.out.println("Excel sheet for Configurations exported successfully.");

			status = "success";

			resultSet3.close();
			preparedStatement3.close();

			resultSet2.close();
			preparedStatement2.close();

			resultSet1.close();
			preparedStatement1.close();

			resultSet.close();
			preparedStatement.close();
			connection.close();
			/*
			 * Closing resultSet, preparedStatement, connection objects
			 */

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;

	}

	public String importConfigurationsReportExcel(ConfigurationForm conform, File excelFileName, int academicYearID,
			int organizationID) {

		StuduntDAOInf daoInf = new StudentDAOImpl();

		ConfigurationDAOInf daoInf1 = new ConfigurationDAOImpl();

		try {

			DataFormatter formatter = new DataFormatter();

			Workbook wb = WorkbookFactory.create(excelFileName);

			Map<String, Integer> map = null;

			/* Attendance configurations excel import */

			Sheet sheet1 = wb.getSheet("ConfigureTermAttendance");

			if (sheet1 == null) {
				System.out.println("Configure term attendance Sheet not found");

			} else {

				int totalRows1 = sheet1.getPhysicalNumberOfRows();

				System.out.println("Total rows1.." + totalRows1);

				map = new HashMap<String, Integer>();

				XSSFRow row1 = (XSSFRow) sheet1.getRow(0);

				short minColIx1 = row1.getFirstCellNum(); // get the first column index for a row
				short maxColIx1 = row1.getLastCellNum(); // get the last column index for a row

				for (short colIx1 = minColIx1; colIx1 < maxColIx1; colIx1++) { // loop from first to last index

					XSSFCell cell1 = row1.getCell(colIx1); // get the cell
					map.put(cell1.getStringCellValue(), cell1.getColumnIndex()); // add the cell contents (name of
																					// column)
				}

				List<ReportRow> list1 = new ArrayList<ReportRow>();

				for (int j = 1; j < totalRows1; j++) {
					ReportRow reportRow1 = new ReportRow();

					// System.out.println("inside...."+j);

					XSSFRow dataRow = (XSSFRow) sheet1.getRow(j);

					int AttendanceYearColIndex = map.get("AcademicYear");
					int AttendanceTermColIndex = map.get("Term");
					int AttendanceStdColIndex = map.get("Standard");
					int AttendanceMonthColIndex = map.get("Month");
					int AttendanceDaysColIndex = map.get("WorkingDays");

					XSSFCell AttendanceYearCell = dataRow.getCell(AttendanceYearColIndex);
					XSSFCell AttendanceTermCell = dataRow.getCell(AttendanceTermColIndex);
					XSSFCell AttendanceStdCell = dataRow.getCell(AttendanceStdColIndex);
					XSSFCell AttendanceMonthCell = dataRow.getCell(AttendanceMonthColIndex);
					XSSFCell AttendanceDaysCell = dataRow.getCell(AttendanceDaysColIndex);

					if (AttendanceYearCell.getStringCellValue() == null
							|| AttendanceYearCell.getStringCellValue() == "") {
						continue;
					} else if (AttendanceYearCell.getStringCellValue().isEmpty()) {
						continue;
					} else {

						reportRow1.setAcademicYear(AttendanceYearCell.getStringCellValue());
						reportRow1.setTerm(AttendanceTermCell.getStringCellValue());
						reportRow1.setStandard(AttendanceStdCell.getStringCellValue());
						reportRow1.setMonth(AttendanceMonthCell.getStringCellValue());
						reportRow1.setWorkigDays("" + AttendanceDaysCell);

						list1.add(reportRow1);
					}
				}

				for (ReportRow reportRow1 : list1) {

					/*
					 * retrieving standardID from Standard table on the basis of standard name
					 */

					int standardID = daoInf.retrieveStandardIDByStandardName(reportRow1.getStandard());

					/*
					 * Verifying academicYearID from AcademicYear table on the basis of yearName
					 * academicYearID
					 */

					boolean check = daoInf.verifyAcademicYearID(reportRow1.getAcademicYear(), academicYearID);

					if (check) {
						/*
						 * Inserting Attendance details into Attendance table
						 */
						statusMessage = daoInf.importAttendanceDetails(reportRow1, academicYearID, standardID);

						if (statusMessage.equalsIgnoreCase("success")) {
							System.out
									.println("Successfully imported attendance configuration's into Attendance table");
							status = "success";

						} else {
							System.out.println("Failed to import attendance configuration's into Attendance Table");
							status = "error";
							return status;
						}
					}
				}
			}

			/* Examination configurations excel import */

			Sheet sheet = wb.getSheet("ConfigureExamination");

			if (sheet == null) {

				System.out.println("Configure Examination Sheet not found");

			} else {

				int totalRows = sheet.getPhysicalNumberOfRows();

				System.out.println("Total rows.." + totalRows);

				map = new HashMap<String, Integer>();

				XSSFRow row = (XSSFRow) sheet.getRow(0);

				short minColIx = row.getFirstCellNum(); // get the first column index for a row
				short maxColIx = row.getLastCellNum(); // get the last column index for a row

				for (short colIx = minColIx; colIx < maxColIx; colIx++) { // loop from first to last index
					// System.out.println("inside..."+i);
					XSSFCell cell = row.getCell(colIx); // get the cell
					map.put(cell.getStringCellValue(), cell.getColumnIndex()); // add the cell contents (name of column)
																				// and
				}

				List<ReportRow> list = new ArrayList<ReportRow>();

				for (int j = 1; j < totalRows; j++) {
					ReportRow reportRow = new ReportRow();

					// System.out.println("inside...."+j);

					XSSFRow dataRow = (XSSFRow) sheet.getRow(j);

					int ExamYearColIndex = map.get("AcademicYear");
					int ExamTermColIndex = map.get("Term");
					int ExamNameColIndex = map.get("Examination Name");
					int ExamTypeColIndex = map.get("Examination Type");
					int ExamSDateColIndex = map.get("Start Date");
					int ExamEDateColIndex = map.get("End Date");

					XSSFCell ExamYearCell = dataRow.getCell(ExamYearColIndex);
					XSSFCell ExamTermCell = dataRow.getCell(ExamTermColIndex);
					XSSFCell ExamNameCell = dataRow.getCell(ExamNameColIndex);
					XSSFCell ExamTypeCell = dataRow.getCell(ExamTypeColIndex);
					XSSFCell ExamSDateCell = dataRow.getCell(ExamSDateColIndex);
					XSSFCell ExamEDateCell = dataRow.getCell(ExamEDateColIndex);

					if (ExamYearCell.getStringCellValue() == null || ExamYearCell.getStringCellValue() == "") {
						continue;
					} else if (ExamYearCell.getStringCellValue().isEmpty()) {
						continue;
					} else {
						reportRow.setAcademicYear(ExamYearCell.getStringCellValue());
						reportRow.setTerm(ExamTermCell.getStringCellValue());
						reportRow.setExamName(ExamNameCell.getStringCellValue());
						reportRow.setExamType(ExamTypeCell.getStringCellValue());
						reportRow.setStartDate("" + ExamSDateCell);
						reportRow.setEndDate("" + ExamEDateCell);

						list.add(reportRow);
					}

				}

				for (ReportRow reportRow : list) {

					/*
					 * Verifying academicYearID from AcademicYear table on the basis of yearName
					 * academicYearID
					 */

					boolean check = daoInf.verifyAcademicYearID(reportRow.getAcademicYear(), academicYearID);

					if (check) {
						/*
						 * Inserting examination details into Examination table
						 */
						statusMessage = daoInf.importExaminationDetails(reportRow, academicYearID);

						if (statusMessage.equalsIgnoreCase("success")) {
							System.out.println(
									"Successfully imported examination configuration's into Examination table");
							status = "success";

						} else {
							System.out.println("Failed to import examination configuration's into Examination Table");
							status = "error";

							return status;
						}
					}
				}
			}

			/* TimeTable configurations excel import */

			Sheet sheet2 = wb.getSheet("ConfigureTimeTable");

			if (sheet2 == null) {

				System.out.println("Configure TimeTable Sheet not found");

			} else {

				int totalRows2 = sheet2.getPhysicalNumberOfRows();

				System.out.println("Total rows2.." + totalRows2);

				map = new HashMap<String, Integer>();

				XSSFRow row2 = (XSSFRow) sheet2.getRow(0);

				short minColIx2 = row2.getFirstCellNum(); // get the first column index for a row
				short maxColIx2 = row2.getLastCellNum(); // get the last column index for a row
				// int i = 1;
				for (short colIx2 = minColIx2; colIx2 < maxColIx2; colIx2++) { // loop from first to last index
					// System.out.println("inside..."+i);
					XSSFCell cell = row2.getCell(colIx2); // get the cell
					map.put(cell.getStringCellValue(), cell.getColumnIndex()); // add the cell contents (name of column)
																				// and
				}

				List<ReportRow> list2 = new ArrayList<ReportRow>();

				for (int j = 1; j < totalRows2; j++) {
					ReportRow reportRow2 = new ReportRow();

					XSSFRow dataRow = (XSSFRow) sheet2.getRow(j);

					int TimeTableYearColIndex = map.get("AcademicYear");
					int TimeTableExamColIndex = map.get("Exam");
					int TimeTableDateColIndex = map.get("Date");
					int TimeTableStdColIndex = map.get("Standard");
					int TimeTableSubjectColIndex = map.get("Subject");

					XSSFCell TimeTableYearCell = dataRow.getCell(TimeTableYearColIndex);
					XSSFCell TimeTableExamCell = dataRow.getCell(TimeTableExamColIndex);
					XSSFCell TimeTableDateCell = dataRow.getCell(TimeTableDateColIndex);
					XSSFCell TimeTableStdCell = dataRow.getCell(TimeTableStdColIndex);
					XSSFCell TimeTableSubjectCell = dataRow.getCell(TimeTableSubjectColIndex);

					if (TimeTableYearCell.getStringCellValue() == null
							|| TimeTableYearCell.getStringCellValue() == "") {
						continue;
					} else if (TimeTableYearCell.getStringCellValue().isEmpty()) {
						continue;
					} else {
						reportRow2.setAcademicYear(TimeTableYearCell.getStringCellValue());
						reportRow2.setExamName(TimeTableExamCell.getStringCellValue());
						reportRow2.setStartDate("" + TimeTableDateCell);
						reportRow2.setStandard(TimeTableStdCell.getStringCellValue());
						reportRow2.setSubject(TimeTableSubjectCell.getStringCellValue());

						list2.add(reportRow2);
					}

				}

				for (ReportRow reportRow2 : list2) {

					/*
					 * retrieving examID from Examination table on the basis of exam name
					 */
					int examID = daoInf.retrieveExamIDByExamName(reportRow2.getExamName(), reportRow2.getStartDate(),
							academicYearID);

					/*
					 * retrieving standardID from Standard table on the basis of standard name
					 */
					int standardID = daoInf.retrieveStandardIDByStandardName(reportRow2.getStandard());

					/*
					 * Verifying academicYearID from AcademicYear table on the basis of yearName
					 * academicYearID
					 */

					boolean check = daoInf.verifyAcademicYearID(reportRow2.getAcademicYear(), academicYearID);

					if (check) {

						/* Inserting TimeTable details into TimeTable table */

						statusMessage = daoInf.importTimeTableDetails(reportRow2, academicYearID, examID, standardID);

						if (statusMessage.equalsIgnoreCase("success")) {
							System.out.println("Successfully imported timeTable configuration's into TimeTable table");
							status = "success";

						} else {
							System.out.println("Failed to import timeTable configuration's into TimeTable Table");
							status = "error";

							return status;
						}
					}
				}
			}

			/* AcademicYear configurations excel import */

			Sheet sheet3 = wb.getSheet("ConfigureAcademicYear");

			if (sheet3 == null) {

				System.out.println("Configure AcademicYear Sheet not found");

			} else {

				int totalRows3 = sheet3.getPhysicalNumberOfRows();

				System.out.println("Total rows3.." + totalRows3);

				map = new HashMap<String, Integer>();

				XSSFRow row3 = (XSSFRow) sheet3.getRow(0);

				int columnCounter = 4;

				short minColIx3 = row3.getFirstCellNum(); // get the first column index for a row
				short maxColIx3 = row3.getLastCellNum(); // get the last column index for a row
				System.out.println("..." + maxColIx3);
				// int i = 1;
				for (short colIx3 = minColIx3; colIx3 < maxColIx3; colIx3++) { // loop from first to last index
					// System.out.println("colIx3 ..." + colIx3);

					if (colIx3 == columnCounter) {
						// System.out.println("inside..." + columnCounter);
						XSSFCell cell = row3.getCell(colIx3); // get the cell
						map.put(cell.getStringCellValue() + columnCounter, cell.getColumnIndex()); // add the cell
																									// contents

						columnCounter++;
					} else {
						// System.out.println("inside..."+i);
						XSSFCell cell = row3.getCell(colIx3); // get the cell
						map.put(cell.getStringCellValue(), cell.getColumnIndex()); // add the cell contents (name of
																					// column)

					}
				}

				List<ReportRow> list3 = new ArrayList<ReportRow>();

				for (int j = 1; j < totalRows3; j++) {
					ReportRow reportRow3 = new ReportRow();

					// System.out.println("inside...."+j);

					XSSFRow dataRow = (XSSFRow) sheet3.getRow(j);

					int AcademicYearColIndex = map.get("AcademicYear");
					int AcademicYearStandardColIndex = map.get("Standard");
					int AcademicYearDivisionColIndex = map.get("Division");
					int AcademicYearClsTeachColIndex = map.get("Class Teacher");

					XSSFCell AcademicYearCell = dataRow.getCell(AcademicYearColIndex);
					XSSFCell AcademicYearStandardCell = dataRow.getCell(AcademicYearStandardColIndex);
					XSSFCell AcademicYearDivisionCell = dataRow.getCell(AcademicYearDivisionColIndex);
					XSSFCell AcademicYearClsTeachCell = dataRow.getCell(AcademicYearClsTeachColIndex);

					if (AcademicYearCell.getStringCellValue() == null || AcademicYearCell.getStringCellValue() == "") {
						continue;
					} else if (AcademicYearCell.getStringCellValue().isEmpty()) {
						continue;
					} else {

						reportRow3.setAcademicYear(AcademicYearCell.getStringCellValue());
						reportRow3.setStandard(AcademicYearStandardCell.getStringCellValue());
						reportRow3.setDivision(AcademicYearDivisionCell.getStringCellValue());
						reportRow3.setClassTeacher(AcademicYearClsTeachCell.getStringCellValue());

						String Values = "Subject,Teacher1,Teacher2";

						String[] NewValues = Values.split(",");

						HashMap<String, List<String>> subjectMap = new HashMap<String, List<String>>();

						List<String> subjectList = new ArrayList<String>();

						List<String> subjectTeacher1List = new ArrayList<String>();

						List<String> subjectTeacher2List = new ArrayList<String>();

						int k = 4;

						// System.out.println("Map columnCounter : " + columnCounter + " - " +
						// map.toString());

						int iterator = (maxColIx3 - 4) / 3;

						for (int i = 0; i < iterator; i++) {

							System.out.println("...k.." + k);

							int SubjectColIndex = map.get("Subject" + k);
							XSSFCell SubjectTeachCell = dataRow.getCell(SubjectColIndex);

							subjectList.add("" + SubjectTeachCell);
							subjectMap.put("Subject", subjectList);
							k++;

							int SubjectTeach1ColIndex = map.get("Teacher1" + k);
							XSSFCell SubjectTeach1Cell = dataRow.getCell(SubjectTeach1ColIndex);

							subjectTeacher1List.add("" + SubjectTeach1Cell);
							subjectMap.put("Teacher1", subjectTeacher1List);
							k++;

							int SubjectTeach2ColIndex = map.get("Teacher2" + k);
							XSSFCell SubjectTeach2Cell = dataRow.getCell(SubjectTeach2ColIndex);

							subjectTeacher2List.add("" + SubjectTeach2Cell);
							subjectMap.put("Teacher2", subjectTeacher2List);
							k++;
						}

						reportRow3.setSubjectMap(subjectMap);

						list3.add(reportRow3);
					}
				}

				for (ReportRow reportRow3 : list3) {

					/*
					 * retrieving standardID from Standard table on the basis of standard name
					 */

					int standardID = daoInf.retrieveStandardIDByStandardName(reportRow3.getStandard());

					/*
					 * retrieving divisionID from Division table on the basis of standardID &
					 * division name
					 */

					int divisionID = daoInf.retrieveDivisionIDByStandardDivision(standardID, reportRow3.getDivision());

					/*
					 * retrieving divisionID from Division table on the basis of standardID &
					 * division name
					 */

					int classTeacherID = daoInf.retrieveClassTeacherIDByclassTeacherName(reportRow3.getClassTeacher());

					/*
					 * Verifying academicYearID from AcademicYear table on the basis of yearName
					 * academicYearID
					 */

					boolean check = daoInf.verifyAcademicYearID(reportRow3.getAcademicYear(), academicYearID);

					int AYClassID = 0;

					if (check) {

						boolean checkNew = daoInf.verifyTeacherID(academicYearID, divisionID, standardID,
								classTeacherID);

						if (checkNew) {

							AYClassID = daoInf1.retrieveAYClassIDByCLassTeacherID(academicYearID, divisionID,
									standardID, classTeacherID);

						} else {
							/* Inserting TimeTable details into TimeTable table */

							statusMessage = daoInf.importAYClassDetails(reportRow3, academicYearID, divisionID,
									standardID, classTeacherID);

							AYClassID = daoInf1.retrieveAYClassID();
						}

						int ij = 0;

						for (String subject : reportRow3.getSubjectMap().get("Subject")) {

							 //System.out.println("Values:"+subjectNameID+"-"+teacher2ID+"-"+teacher1ID+"-"+AYClassID);
							 //System.out.println("Subject..."+subject);
							if (subject.equals(null)) {
								continue;
							} else {

								String teacher1 = reportRow3.getSubjectMap().get("Teacher1").get(ij);
								String teacher2 = reportRow3.getSubjectMap().get("Teacher2").get(ij);

								int subjectNameID = daoInf.retrieveSubjectIDBySubjectName(subject, organizationID);

								int teacher1ID = daoInf.retrieveClassTeacherIDByclassTeacherName(teacher1);

								int teacher2ID = daoInf.retrieveClassTeacherIDByclassTeacherName(teacher2);

								if (subjectNameID != 0) {
									boolean subjectListCheck = daoInf.verifySubjectAvailabilityInStandard(subjectNameID,
											standardID);

									if (subjectListCheck) {

										System.out.println("Final Values: " + AYClassID + "-" + subjectNameID + "-"
												+ teacher1ID + "-" + teacher2ID);
										statusMessage = daoInf.importAYSubjectDetails(reportRow3, AYClassID,
												subjectNameID, teacher1ID, teacher2ID);
									} else {
										continue;
									}
								}
							}
							ij++;

							if (statusMessage.equalsIgnoreCase("success")) {
								System.out.println(
										"Successfully imported AYSubject configuration's into AYSubject table");
								status = "success";

							} else {
								System.out.println("Failed to import AYSubject configuration's into AYSubject Table");
								status = "error";

							}

						}

						if (statusMessage.equalsIgnoreCase("success")) {
							System.out.println("Successfully imported AYClass configuration's into AYClass table");
							status = "success";

						} else {
							System.out.println("Failed to import AYClass configuration's into AYClass Table");
							status = "error";

							return status;
						}
					}
				}
			}

			/* SubjectAssessment configurations excel import */

			Sheet sheet4 = wb.getSheet("ConfigureSubjectAssessment");

			System.out.println("ConfigureSubjectAssessment: " + sheet4);
			if (sheet4 == null) {

				System.out.println("Configure SubjectAssessment Sheet not found");

			} else {

				int totalRows4 = sheet4.getPhysicalNumberOfRows();

				System.out.println("Total rows4.." + totalRows4);

				map = new HashMap<String, Integer>();

				XSSFRow row4 = (XSSFRow) sheet4.getRow(0);

				short minColIx4 = row4.getFirstCellNum(); // get the first column index for a row
				short maxColIx4 = row4.getLastCellNum(); // get the last column index for a row
				// int i = 1;
				for (short colIx4 = minColIx4; colIx4 < maxColIx4; colIx4++) { // loop from first to last index
					// System.out.println("inside..."+i);
					XSSFCell cell = row4.getCell(colIx4); // get the cell
					map.put(cell.getStringCellValue(), cell.getColumnIndex()); // add the cell contents (name of column)
																				// and
																				// cell index to the map
					// i++;
				}

				List<ReportRow> list4 = new ArrayList<ReportRow>();

				for (int j = 1; j < totalRows4; j++) {
					ReportRow reportRow4 = new ReportRow();

					System.out.println("inside...." + j);

					XSSFRow dataRow = (XSSFRow) sheet4.getRow(j);

					int StandardColIndex = map.get("Standard");
					int DivisionColIndex = map.get("Division");
					int TermColIndex = map.get("Term");
					int ExamColIndex = map.get("Exam");
					int SubjectColIndex = map.get("Subject");
					int GradeBasedColIndex = map.get("Grade Based");
					int MaximumMarksColIndex = map.get("Maximum Marks");
					int ScaleToColIndex = map.get("Scale To");

					XSSFCell StandardCell = dataRow.getCell(StandardColIndex);
					XSSFCell DivisionCell = dataRow.getCell(DivisionColIndex);
					XSSFCell TermCell = dataRow.getCell(TermColIndex);
					XSSFCell ExamCell = dataRow.getCell(ExamColIndex);
					XSSFCell SubjectCell = dataRow.getCell(SubjectColIndex);
					XSSFCell GradeBasedCell = dataRow.getCell(GradeBasedColIndex);
					XSSFCell MaximumMarksCell = dataRow.getCell(MaximumMarksColIndex);
					XSSFCell ScaleToCell = dataRow.getCell(ScaleToColIndex);

					if (StandardCell == null) {
						continue;
					} else if (StandardCell.getStringCellValue() == null || StandardCell.getStringCellValue() == "") {
						continue;
					} else if (StandardCell.getStringCellValue().isEmpty()) {
						continue;
					} else {

						reportRow4.setStandard(StandardCell.getStringCellValue());
						reportRow4.setDivision(DivisionCell.getStringCellValue());
						reportRow4.setTerm(TermCell.getStringCellValue());
						reportRow4.setExamName(ExamCell.getStringCellValue());
						reportRow4.setSubject(SubjectCell.getStringCellValue());
						reportRow4.setGradeBased(GradeBasedCell.getStringCellValue());
						reportRow4.setTotalMarks((int) MaximumMarksCell.getNumericCellValue());
						reportRow4.setScaleTo((int) ScaleToCell.getNumericCellValue());

						list4.add(reportRow4);
					}
				}

				for (ReportRow reportRow4 : list4) {

					/*
					 * retrieving ayclassID on the basis of standard & division
					 */

					int ayclassID = daoInf.retrieveAYClassIDNew(reportRow4.getStandard(), reportRow4.getDivision(),
							academicYearID);

					/*
					 * retrieving examinationID on the basis of term & academicYearID & examName
					 */

					int examinationID = daoInf.retrieveExaminationID(reportRow4.getTerm(), reportRow4.getExamName(),
							academicYearID);

					/*
					 * retrieving subjectID on the basis of subject name
					 */

					int subjectID = daoInf.retrieveSubjectIDBySubjectName(reportRow4.getSubject(), organizationID);

					int gradeChack = 0;
					int scaleVal = 0;
					int totalMarks = 0;

					if (reportRow4.getGradeBased().equals("Yes")) {

						System.out.println("GradeCheck: " + reportRow4.getGradeBased());
						gradeChack = 1;
						scaleVal = 0;
						totalMarks = 0;

					} else if (reportRow4.getGradeBased().equals("No")) {
						System.out.println("GradeCheck1: " + reportRow4.getGradeBased());
						gradeChack = 0;
						scaleVal = reportRow4.getScaleTo();
						totalMarks = reportRow4.getTotalMarks();
					}

					/*
					 * Inserting SubjectAssessment details into SubjectAssessment table
					 */
					statusMessage = daoInf.importSubjectAssessmentDetails(gradeChack, scaleVal, totalMarks, ayclassID,
							examinationID, subjectID);

					if (statusMessage.equalsIgnoreCase("success")) {
						System.out.println(
								"Successfully imported SubjectAssessment configuration's into SubjectAssessment table");
						status = "success";

					} else {
						System.out.println(
								"Failed to import SubjectAssessment configuration's into SubjectAssessment Table");
						status = "error";

						return status;
					}
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Invalid file format. Please upload a valid file");

			status = "error";
		}

		return status;

	}

	public String convertXthSTDCustomReportToExcel(int standardID, String StandardName, int divisionID,
			String divisionName, int AYClassID, int userID, int subjectID, String subjectName, int academicYearID,
			String realPath, String excelFileName) {

		System.out.println("convertXthSTDCustomReportToExcel");

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		HashMap<Integer, String> subjectNameList = null;

		HashMap<String, Integer> NewExaminationList = null;

		HashMap<String, Integer> NewExaminationList2 = null;

		LinkedHashMap<Integer, String> studentCustomList = null;

		List<ConfigurationForm> studentsCustomReportList = null;

		List<Integer> SubjectMarksList = null;

		int currentRow = 1;

		try {

			studentCustomList = daoInf2.retrieveExistingStudentAssessmentListForClassTeacher(AYClassID);

			NewExaminationList = daoInf2.retrieveExaminationList(academicYearID, "Term I", AYClassID);

			NewExaminationList2 = daoInf2.retrieveExaminationList(academicYearID, "Term II", AYClassID);

			/* double = 0D; */

			int SubjectEnrichment1Marks, SubjectEnrichment2Marks, Notebook1Marks, Notebook2Marks,
					SubjectEnrichmentBestMarks, NotebookBestMarks, portfolio1Marks, portfolio2Marks, portfolioBestMarks,
					multipleAss1Marks, multipleAss2Marks, multipleAssBestMarks = 0;
			double TermEndmarksObtained = 0;
			int UnitTest1marksObtained, TermEndConvertedmarks, UnitTest2marksObtained;

			studentsCustomReportList = new ArrayList<ConfigurationForm>();

			for (Integer studentID : studentCustomList.keySet()) {

				SubjectMarksList = new ArrayList<Integer>();

				double maximumMarks = 0D;
				double TermEndmarksObtained1 = 0D;
				int finalReducedMark = 0;
				int InternalMarks = 0;
				int reducedMarks = 0;

				double maximumMarks1 = 0;

				String[] array = studentCustomList.get(studentID).split("=");

				String studentName = array[0];

				String rollNo = array[1];

				ConfigurationForm form = new ConfigurationForm();

				boolean termEdCheck = daoInf2.verifyAbsent(subjectID, studentID, AYClassID,
						NewExaminationList.get("Term End"));

				boolean unitTestCheck = daoInf2.verifyAbsent(subjectID, studentID, AYClassID,
						NewExaminationList.get("Unit Test"));

				boolean unitTest2Check = daoInf2.verifyAbsent(subjectID, studentID, AYClassID,
						NewExaminationList2.get("Unit Test"));

				TermEndmarksObtained = daoInf2.retrievemarksObtained(subjectID, NewExaminationList.get("Term End"),
						studentID, AYClassID);

				TermEndmarksObtained1 = (TermEndmarksObtained * 40) / 80;

				// System.out.println("TermEndmarksObtained: "+TermEndmarksObtained*40);

				String numberAsString = Double.toString(TermEndmarksObtained1);

				String valueMarks[] = numberAsString.split("\\.");

				if (Long.parseLong(valueMarks[1]) > 0) {

					TermEndConvertedmarks = Integer.parseInt(valueMarks[0]) + 1;

				} else {

					TermEndConvertedmarks = Integer.parseInt(valueMarks[0]);
				}

				UnitTest1marksObtained = daoInf2.retrievemarksObtained(subjectID, NewExaminationList.get("Unit Test"),
						studentID, AYClassID);

				UnitTest2marksObtained = daoInf2.retrievemarksObtained(subjectID, NewExaminationList2.get("Unit Test"),
						studentID, AYClassID);

				SubjectEnrichment1Marks = daoInf2.retrieveScholasticGradeListNew(subjectID, "Subject Enrichment",
						studentID, AYClassID, "Term I");

				SubjectEnrichment2Marks = daoInf2.retrieveScholasticGradeListNew(subjectID, "Subject Enrichment",
						studentID, AYClassID, "Term II");

				SubjectEnrichmentBestMarks = Math.max(SubjectEnrichment1Marks, SubjectEnrichment2Marks);

				Notebook1Marks = daoInf2.retrieveScholasticGradeListNew(subjectID, "Notebook", studentID, AYClassID,
						"Term I");

				Notebook2Marks = daoInf2.retrieveScholasticGradeListNew(subjectID, "Notebook", studentID, AYClassID,
						"Term II");

				NotebookBestMarks = Math.max(Notebook1Marks, Notebook2Marks);

				portfolio1Marks = daoInf2.retrieveScholasticGradeListNew(subjectID, "Portfolio", studentID, AYClassID,
						"Term I");

				portfolio2Marks = daoInf2.retrieveScholasticGradeListNew(subjectID, "Portfolio", studentID, AYClassID,
						"Term II");

				portfolioBestMarks = Math.max(portfolio1Marks, portfolio2Marks);

				multipleAss1Marks = daoInf2.retrieveScholasticGradeListNew(subjectID, "Multiple Assessment", studentID,
						AYClassID, "Term I");

				multipleAss2Marks = daoInf2.retrieveScholasticGradeListNew(subjectID, "Multiple Assessment", studentID,
						AYClassID, "Term II");

				multipleAssBestMarks = Math.max(multipleAss1Marks, multipleAss2Marks);

				SubjectMarksList.add(UnitTest1marksObtained);
				SubjectMarksList.add(TermEndConvertedmarks);
				SubjectMarksList.add(UnitTest2marksObtained);

				for (int i = 0; i < 2; i++) {
					maximumMarks1 = maximumMarks1 + Collections.max(SubjectMarksList);

					// System.out.println("maximumMarks1: "+maximumMarks1);

					SubjectMarksList.remove(SubjectMarksList.indexOf(Collections.max(SubjectMarksList)));
				}

				// System.out.println("Final maximumMarks: "+maximumMarks1);

				maximumMarks = (maximumMarks1 * 5) / 80;

				String numberAsString1 = Double.toString(maximumMarks);

				// System.out.println("numberAsString1: "+numberAsString1);

				String valueMarks1[] = numberAsString1.split("\\.");

				if (Long.parseLong(valueMarks1[1]) > 0) {

					reducedMarks = Integer.parseInt(valueMarks1[0]) + 1;

				} else {

					reducedMarks = Integer.parseInt(valueMarks1[0]);
				}

				InternalMarks = (SubjectEnrichmentBestMarks + NotebookBestMarks + portfolioBestMarks
						+ multipleAssBestMarks + reducedMarks);

				form.setSubjectEnrichmentMarks("" + SubjectEnrichment1Marks);

				form.setSubjectEnrichmentMarksObtained("" + SubjectEnrichment2Marks);

				form.setSubjectEnrichmentMarks1("" + SubjectEnrichmentBestMarks);

				form.setNotebookMarks("" + Notebook1Marks);

				form.setNotebookMarksObtained("" + Notebook2Marks);

				form.setNotebookMarks1("" + NotebookBestMarks);

				form.setPortfolioMarks("" + portfolio1Marks);

				form.setPortfolioMarks1("" + portfolio2Marks);

				form.setPortfolioFinalMarks("" + portfolioBestMarks);

				form.setMultipleAssessmentMarks("" + multipleAss1Marks);

				form.setMultipleAssessmentMarks1("" + multipleAss2Marks);

				form.setMultipleAssessmentFinalMarks("" + multipleAssBestMarks);

				form.setUnitTestMarksObtained("" + UnitTest1marksObtained);

				form.setUnitTestMarks("" + UnitTest2marksObtained);

				form.setTermEndMarksObtained("" + TermEndmarksObtained);

				form.setTermEndMarks("" + TermEndConvertedmarks);

				form.setFinalBestMarks("" + maximumMarks1);

				form.setFinalReducedMarks("" + reducedMarks);

				form.setFinalTotalMarks("" + InternalMarks);

				form.setRollNumber(Integer.parseInt(rollNo));

				form.setStudentName(studentName);

				studentsCustomReportList.add(form);

			}

			/*
			 * Setting path to store PDF file
			 */
			File file = new File(realPath + "/" + excelFileName);

			// Workbook wb = new HSSFWorkbook();
			XSSFWorkbook wb = new XSSFWorkbook();

			XSSFSheet spreadSheet = wb.createSheet("Xth STD Students Custom report");
			Row row;

			row = spreadSheet.createRow(0);

			spreadSheet.setColumnWidth((short) 0, (short) (256 * 25));

			/*
			 * Creating header in XLSX
			 */
			CellStyle headerCellStyle = wb.createCellStyle();

			/*
			 * Setting up the font
			 */
			Font setFont = wb.createFont();
			setFont.setFontHeightInPoints((short) 12);
			setFont.setBold(true);
			headerCellStyle.setFont(setFont);

			/*
			 * Setting up Data style in XLSX
			 */
			CellStyle dataCellStyle = wb.createCellStyle();
			Font setDataFont = wb.createFont();
			setDataFont.setFontHeightInPoints((short) 12);
			dataCellStyle.setFont(setDataFont);

			/*
			 * Initializing Cell reference variable
			 */
			Cell cell = null;

			cell = row.createCell((short) 0);
			cell.setCellValue("Standard " + StandardName + "-" + divisionName + "(" + subjectName + ")"
					+ " Internal Marks Report");
			cell.setCellStyle(dataCellStyle);

			currentRow = currentRow + 2;

			row = spreadSheet.createRow(currentRow);

			row = spreadSheet.createRow(currentRow++);

			spreadSheet.setColumnWidth((short) 0, (short) (256 * 25));
			spreadSheet.setColumnWidth((short) 1, (short) (256 * 25));

			cell = row.createCell((short) 0);
			cell.setCellValue("Roll No.");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 1);
			cell.setCellValue("Student Name");
			cell.setCellStyle(headerCellStyle);

			spreadSheet.setColumnWidth((short) 2, (short) (256 * 25));
			spreadSheet.setColumnWidth((short) 3, (short) (256 * 25));
			spreadSheet.setColumnWidth((short) 4, (short) (256 * 25));
			spreadSheet.setColumnWidth((short) 5, (short) (256 * 25));
			spreadSheet.setColumnWidth((short) 6, (short) (256 * 25));
			spreadSheet.setColumnWidth((short) 7, (short) (256 * 25));
			spreadSheet.setColumnWidth((short) 8, (short) (256 * 25));
			spreadSheet.setColumnWidth((short) 9, (short) (256 * 25));
			spreadSheet.setColumnWidth((short) 10, (short) (256 * 25));
			spreadSheet.setColumnWidth((short) 11, (short) (256 * 25));
			spreadSheet.setColumnWidth((short) 12, (short) (256 * 25));
			spreadSheet.setColumnWidth((short) 13, (short) (256 * 25));
			spreadSheet.setColumnWidth((short) 14, (short) (256 * 25));

			cell = row.createCell((short) 2);
			cell.setCellValue("Subject Enrichment Term I (5M)");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 3);
			cell.setCellValue("Subject Enrichment Term II (5M)");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 4);
			cell.setCellValue("Subject Enrichment Best /n of the 2 Terms (5M) I");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 5);
			cell.setCellValue("Notebook Term I (5M)");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 6);
			cell.setCellValue("Notebook Term II (5M)");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 7);
			cell.setCellValue("Notebook Best of the 2 Terms (5M) II ");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 8);
			cell.setCellValue("Portfolio Term I (5M)");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 9);
			cell.setCellValue("Portfolio Term II (5M)");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 10);
			cell.setCellValue("Portfolio Best of the 2 Terms (5M) II ");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 11);
			cell.setCellValue("Multiple Assessment Term I (5M)");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 12);
			cell.setCellValue("Multiple Assessment Term II (5M)");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 13);
			cell.setCellValue("Multiple Assessment Best of the 2 Terms (5M) II ");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 14);
			cell.setCellValue("Unit Test Term I (40M) (A)");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 15);
			cell.setCellValue("Mid Term (80M)");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 16);
			cell.setCellValue("Mid Term converted to (40M) (B)");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 17);
			cell.setCellValue("Unit Test Term II (40M) (C)");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 18);
			cell.setCellValue("Best 2 scores from (A)(B)&(C) (80M)");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 19);
			cell.setCellValue("Reduce to (5M) III");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 20);
			cell.setCellValue("Internal marks out of (20M) (I+II+III)");
			cell.setCellStyle(headerCellStyle);

			for (ConfigurationForm form : studentsCustomReportList) {

				row = spreadSheet.createRow(currentRow++);

				cell = row.createCell((short) 0);
				cell.setCellValue(form.getRollNumber());
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 1);
				cell.setCellValue(form.getStudentName());
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 2);
				cell.setCellValue(form.getSubjectEnrichmentMarks());
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 3);
				cell.setCellValue(form.getSubjectEnrichmentMarksObtained());
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 4);
				cell.setCellValue(form.getSubjectEnrichmentMarks1());
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 5);
				cell.setCellValue(form.getNotebookMarks());
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 6);
				cell.setCellValue(form.getNotebookMarksObtained());
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 7);
				cell.setCellValue(form.getNotebookMarks1());
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 8);
				cell.setCellValue(form.getPortfolioMarks());
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 9);
				cell.setCellValue(form.getPortfolioMarks1());
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 10);
				cell.setCellValue(form.getPortfolioFinalMarks());
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 11);
				cell.setCellValue(form.getMultipleAssessmentMarks());
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 12);
				cell.setCellValue(form.getMultipleAssessmentMarks1());
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 13);
				cell.setCellValue(form.getMultipleAssessmentFinalMarks());
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 14);
				cell.setCellValue(form.getUnitTestMarksObtained());
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 15);
				cell.setCellValue(form.getTermEndMarksObtained());
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 16);
				cell.setCellValue(form.getTermEndMarks());
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 17);
				cell.setCellValue(form.getUnitTestMarks());
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 18);
				cell.setCellValue(form.getFinalBestMarks());
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 19);
				cell.setCellValue(form.getFinalReducedMarks());
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 20);
				cell.setCellValue(form.getFinalTotalMarks());
				cell.setCellStyle(dataCellStyle);
			}

			/*
			 * writing to the xlsx file
			 */
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wb.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();

			/*
			 * Write the output to a file
			 */
			FileOutputStream fileOut = new FileOutputStream(new File(realPath + "/" + excelFileName));
			fileOut.write(outArray);
			fileOut.flush();
			fileOut.close();

			System.out.println("Excel sheet for Xth STD Students Customised Report is created successfully.");

			status = "success";

			/*
			 * Closing resultSet, preparedStatement, connection objects
			 */

		} catch (Exception exception) {
			exception.printStackTrace();
			status = "error";
		}
		return status;

	}

	public String convertCustomHistoryReportToExcel(int standardID, String StandardName, int divisionID,
			String divisionName, String term, int AcademicYearID, int organizationID, int userID,
			String subjectNameList, String realPath, String excelFileName) {

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		StuduntDAOInf daoInf = new StudentDAOImpl();

		HashMap<String, Integer> NewExaminationList = null;

		LinkedHashMap<Integer, String> studentCustomList = null;

		List<ConfigurationForm> studentsCustomReportList = null;

		List<String> SubjectEnrichmentList = null;

		List<String> NotebookList = null;

		int currentRow = 1;

		try {

			int AYClassID = daoInf.retrieveAyIDByStandardID(standardID, divisionID, AcademicYearID);

			String Stage = daoInf2.getStandardStageByStandardID(standardID);

			// Workbook wb = new HSSFWorkbook();
			XSSFWorkbook wb = new XSSFWorkbook();

			String[] subjectName = subjectNameList.split(",");

			/*
			 * For loop is to create subject wise sheets in single excel sheet
			 */

			for (int l = 0; l < subjectName.length; l++) {

				String[] subject = subjectName[l].split("\\$");

				if (Stage.equals("Primary")) {

					studentCustomList = daoInf2.retrieveExistingStudentHistoryAssessmentList(AYClassID);

					NewExaminationList = daoInf2.retrieveExaminationList(AcademicYearID, term, AYClassID);

					/* double = 0D; */

					int value, value1, value2, value3, value4, finalOutOfMarks = 0;
					int marksObtained, marksObtained1, marksObtained2, marksObtained3, marksObtained4 = 0;
					double value5, value6, finalSEAMarks = 0D;
					int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5 = 0;
					int toatlScaleTo, toatlScaleTo1, outOfMarks, outOfMarks1, outOfMarks2, finalSEAmarksvalue,
							totalMarksScaled = 0;

					String grade = "";

					studentsCustomReportList = new ArrayList<ConfigurationForm>();

					SubjectEnrichmentList = new ArrayList<String>();

					NotebookList = new ArrayList<String>();

					for (Integer studentID : studentCustomList.keySet()) {

						String marksObtainedValue = "", marksObtainedValue1 = "", marksObtainedNewValue = "",
								marksObtainedNewValue1 = "";

						String[] array = studentCustomList.get(studentID).split("=");

						String studentName = array[0];

						String rollNo = array[1];

						ConfigurationForm form = new ConfigurationForm();

						/*
						 * request.setAttribute("GradeBased",
						 * daoInf2.retrieveGradeBasedValue(NewExaminationList.get("Term End"),
						 * conform.getSubjectID(), AYClassID));
						 */
						int gradeCheck = daoInf2.retrieveGradeBasedValue(NewExaminationList.get("Term End"),
								Integer.parseInt(subject[1]), AYClassID);

						if (gradeCheck == 1) {
							System.out.println("gradecheck");

							boolean termEdCheck = daoInf2.verifyAbsent(Integer.parseInt(subject[1]), studentID,
									AYClassID, NewExaminationList.get("Term End"));

							grade = daoInf2.retrieveGradeValue(Integer.parseInt(subject[1]), studentID, AYClassID,
									NewExaminationList.get("Term End"));

							if (termEdCheck) {
								grade = "ex";
							}

							form.setStudentName(studentName);
							form.setRollNumber(Integer.parseInt(rollNo));
							form.setTermEndMarks("-");
							form.setTermEndMarksObtained("-");
							form.setUnitTestMarksObtained("-");
							form.setFinalTotalMarks("-");
							form.setUnitTestMarks("-");
							form.setSubjectEnrichmentMarksObtained("-");
							form.setSubjectEnrichmentMarks1Obtained("-");
							form.setNotebookMarksObtained("-");
							form.setNotebookMarks("-");
							form.setGrade(grade);

							studentsCustomReportList.add(form);

						} else {
							System.out.println(" No gradecheck");

							boolean termEdCheck = daoInf2.verifyAbsent(Integer.parseInt(subject[1]), studentID,
									AYClassID, NewExaminationList.get("Term End"));

							boolean unitTestCheck = daoInf2.verifyAbsent(Integer.parseInt(subject[1]), studentID,
									AYClassID, NewExaminationList.get("Unit Test"));

							marksObtained = daoInf2.retrievemarksObtained(Integer.parseInt(subject[1]),
									NewExaminationList.get("Term End"), studentID, AYClassID);

							marksObtained1 = daoInf2.retrievemarksObtained(Integer.parseInt(subject[1]),
									NewExaminationList.get("Unit Test"), studentID, AYClassID);

							for (Entry<String, Integer> Subjectentry : NewExaminationList.entrySet()) {
								if (Subjectentry.getKey().startsWith("Subject Enrichment")) {

									marksObtainedNewValue = daoInf2.retrievemarksObtainedForSubjectEnrichment(
											Integer.parseInt(subject[1]), Subjectentry.getValue(), studentID,
											AYClassID);

									marksObtainedValue += ", " + marksObtainedNewValue;

								}
							}

							if (marksObtainedValue.startsWith(",")) {
								marksObtainedValue = marksObtainedValue.substring(1);
							}

							SubjectEnrichmentList.add(marksObtainedValue);

							for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
								if (entry.getKey().startsWith("Notebook")) {

									marksObtainedNewValue1 = daoInf2.retrievemarksObtainedForSubjectEnrichment(
											Integer.parseInt(subject[1]), entry.getValue(), studentID, AYClassID);

									marksObtainedValue1 += ", " + marksObtainedNewValue1;
									System.out.println("marksObtainedValue1 :" + marksObtainedValue1);
								}
							}

							if (marksObtainedValue1.startsWith(",")) {
								marksObtainedValue1 = marksObtainedValue1.substring(1);
							}

							NotebookList.add(marksObtainedValue1);

							value = daoInf2.retrieveScholasticGradeList(Integer.parseInt(subject[1]),
									NewExaminationList.get("Term End"), studentID, AYClassID);

							value1 = daoInf2.retrieveScholasticGradeList(Integer.parseInt(subject[1]),
									NewExaminationList.get("Unit Test"), studentID, AYClassID);

							value4 = daoInf2.retrieveScholasticGradeList1(Integer.parseInt(subject[1]), studentID,
									AYClassID, term);

							totalMarksScaled = (value + value1 + value4);

							toatlScaleTo1 = totalMarksScaled;

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
							 * Verifying whether absentFlag is true for any of exam, if so set grade to ex
							 * else set grade as calculated
							 * ,marksObtained1,marksObtained2,marksObtained3,marksObtained4
							 */
							if (termEdCheck) {
								grade = "ex";
								form.setTermEndMarksObtained("ex");
								form.setTermEndMarks("ex");
								form.setFinalTotalMarks("ex");
							} else {
								form.setTermEndMarksObtained("" + marksObtained);
								form.setTermEndMarks("" + value);
								form.setFinalTotalMarks("" + totalMarksScaled);
							}

							if (unitTestCheck) {
								form.setUnitTestMarksObtained("ex");
								form.setUnitTestMarks("ex");
								grade = "ex";
								form.setFinalTotalMarks("ex");
							} else {
								form.setUnitTestMarksObtained("" + marksObtained1);
								form.setUnitTestMarks("" + value1);
								form.setFinalTotalMarks("" + totalMarksScaled);
							}

							form.setNotebookMarks("" + value4);

							form.setRollNumber(Integer.parseInt(rollNo));
							form.setStudentName(studentName);
							form.setGrade(grade);

							studentsCustomReportList.add(form);

						}

					}

				} else {

					studentCustomList = daoInf2.retrieveExistingStudentHistoryAssessmentList(AYClassID);

					NewExaminationList = daoInf2.retrieveExaminationList(AcademicYearID, term, AYClassID);

					/* double = 0D; */

					int value, value1, value2, value3, value4, finalOutOfMarks = 0;
					int marksObtained, marksObtained1, marksObtained2, marksObtained3, marksObtained4 = 0;
					double value5, value6, finalSEAMarks = 0D;
					int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5 = 0;
					int toatlScaleTo, toatlScaleTo1, outOfMarks, outOfMarks1, outOfMarks2, finalSEAmarksvalue,
							totalMarksScaled = 0;

					String grade = "";

					studentsCustomReportList = new ArrayList<ConfigurationForm>();

					SubjectEnrichmentList = new ArrayList<String>();

					NotebookList = new ArrayList<String>();

					for (Integer studentID : studentCustomList.keySet()) {

						String marksObtainedValue = "", marksObtainedValue1 = "", marksObtainedNewValue = "",
								marksObtainedNewValue1 = "";

						String[] array = studentCustomList.get(studentID).split("=");

						String studentName = array[0];

						String rollNo = array[1];

						ConfigurationForm form = new ConfigurationForm();

						int gradeCheck = daoInf2.retrieveGradeBasedValue(NewExaminationList.get("Term End"),
								Integer.parseInt(subject[1]), AYClassID);

						// System.out.println("grade check.." + gradeCheck);

						if (gradeCheck == 1) {

							boolean termEdCheck = daoInf2.verifyAbsent(Integer.parseInt(subject[1]), studentID,
									AYClassID, NewExaminationList.get("Term End"));
							// System.out.println("termEdCheck: " + termEdCheck);
							grade = daoInf2.retrieveGradeValue(Integer.parseInt(subject[1]), studentID, AYClassID,
									NewExaminationList.get("Term End"));
							// System.out.println("grade: " + grade);
							if (termEdCheck) {
								grade = "ex";
							}
							System.out.println("studentName: " + studentName);

							form.setStudentName(studentName);
							form.setRollNumber(Integer.parseInt(rollNo));
							form.setTermEndMarks("-");
							form.setTermEndMarksObtained("-");
							form.setUnitTestMarksObtained("-");
							form.setFinalTotalMarks("-");
							form.setUnitTestMarks("-");
							form.setSubjectEnrichmentMarksObtained("-");
							form.setSubjectEnrichmentMarks("-");
							form.setNotebookMarksObtained("-");
							form.setNotebookMarks("-");
							form.setGrade(grade);

							studentsCustomReportList.add(form);

						} else {

							boolean termEdCheck = daoInf2.verifyAbsent(Integer.parseInt(subject[1]), studentID,
									AYClassID, NewExaminationList.get("Term End"));

							boolean unitTestCheck = daoInf2.verifyAbsent(Integer.parseInt(subject[1]), studentID,
									AYClassID, NewExaminationList.get("Unit Test"));

							marksObtained = daoInf2.retrievemarksObtained(Integer.parseInt(subject[1]),
									NewExaminationList.get("Term End"), studentID, AYClassID);

							marksObtained1 = daoInf2.retrievemarksObtained(Integer.parseInt(subject[1]),
									NewExaminationList.get("Unit Test"), studentID, AYClassID);

							for (Entry<String, Integer> Subjectentry : NewExaminationList.entrySet()) {
								if (Subjectentry.getKey().startsWith("Subject Enrichment")) {

									System.out.println("Subject Enrichment: " + Subjectentry.getValue());
									marksObtainedNewValue = daoInf2.retrievemarksObtainedForSubjectEnrichment(
											Integer.parseInt(subject[1]), Subjectentry.getValue(), studentID,
											AYClassID);

									marksObtainedValue += ", " + marksObtainedNewValue;

									System.out.println("marksObtainedNewValue: " + marksObtainedValue);
								}
							}

							if (marksObtainedValue.startsWith(",")) {
								marksObtainedValue = marksObtainedValue.substring(1);
							}

							SubjectEnrichmentList.add(marksObtainedValue);

							for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
								if (entry.getKey().startsWith("Notebook")) {

									marksObtainedNewValue1 = daoInf2.retrievemarksObtainedForSubjectEnrichment(
											Integer.parseInt(subject[1]), entry.getValue(), studentID, AYClassID);

									marksObtainedValue1 += ", " + marksObtainedNewValue1;
									System.out.println("marksObtainedValue1 :" + marksObtainedValue1);
								}
							}

							if (marksObtainedValue1.startsWith(",")) {
								marksObtainedValue1 = marksObtainedValue1.substring(1);
							}

							NotebookList.add(marksObtainedValue1);

							value = daoInf2.retrieveScholasticGradeList(Integer.parseInt(subject[1]),
									NewExaminationList.get("Term End"), studentID, AYClassID);

							value1 = daoInf2.retrieveScholasticGradeList(Integer.parseInt(subject[1]),
									NewExaminationList.get("Unit Test"), studentID, AYClassID);

							value2 = daoInf2.retrieveScholasticGradeListNew(Integer.parseInt(subject[1]),
									"Subject Enrichment", studentID, AYClassID, term);

							value3 = daoInf2.retrieveScholasticGradeListNew(Integer.parseInt(subject[1]), "Notebook",
									studentID, AYClassID, term);

							scaleTo = daoInf2.retrieveScaleTo(Integer.parseInt(subject[1]),
									NewExaminationList.get("Term End"), AYClassID);

							scaleTo1 = daoInf2.retrieveScaleTo(Integer.parseInt(subject[1]),
									NewExaminationList.get("Unit Test"), AYClassID);

							scaleTo2 = daoInf2.retrieveScaleToNew(Integer.parseInt(subject[1]), "Subject Enrichment",
									AYClassID, AcademicYearID, term);

							scaleTo3 = daoInf2.retrieveScaleToNew(Integer.parseInt(subject[1]), "Notebook", AYClassID,
									AcademicYearID, term);

							totalMarksScaled = (value + value1 + value2 + value3);

							toatlScaleTo = (scaleTo + scaleTo1 + scaleTo2 + scaleTo3);

							if (toatlScaleTo == 50) {

								toatlScaleTo1 = (totalMarksScaled * 2);
							} else {

								toatlScaleTo1 = totalMarksScaled;
							}

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
							 * Verifying whether absentFlag is true for any of exam, if so set grade to ex
							 * else set grade as calculated
							 * ,marksObtained1,marksObtained2,marksObtained3,marksObtained4
							 */
							if (termEdCheck) {
								grade = "ex";
								form.setTermEndMarksObtained("ex");
								form.setTermEndMarks("ex");
								form.setFinalTotalMarks("ex");
							} else {
								form.setTermEndMarksObtained("" + marksObtained);
								form.setTermEndMarks("" + value);
								form.setFinalTotalMarks("" + totalMarksScaled);
							}

							if (unitTestCheck) {
								grade = "ex";
								form.setUnitTestMarksObtained("ex");
								form.setUnitTestMarks("ex");
								form.setFinalTotalMarks("ex");
							} else {
								form.setUnitTestMarksObtained("" + marksObtained1);
								form.setUnitTestMarks("" + value1);
								form.setFinalTotalMarks("" + totalMarksScaled);
							}

							form.setNotebookMarks("" + value3);

							form.setSubjectEnrichmentMarks("" + value2);

							form.setRollNumber(Integer.parseInt(rollNo));
							form.setStudentName(studentName);
							form.setGrade(grade);

							studentsCustomReportList.add(form);
						}
					}

				}

				int gradeCheck = daoInf2.retrieveGradeBasedValue(NewExaminationList.get("Term End"),
						Integer.parseInt(subject[1]), AYClassID);
				/*
				 * Setting path to store PDF file
				 */
				File file = new File(realPath + "/" + excelFileName);

				XSSFSheet spreadSheet = wb.createSheet(subject[0] + " History Custom report");

				Row row;

				row = spreadSheet.createRow(0);

				spreadSheet.setColumnWidth((short) 0, (short) (256 * 25));

				/*
				 * Creating header in XLSX
				 */
				CellStyle headerCellStyle = wb.createCellStyle();

				/*
				 * Setting up the font
				 */
				Font setFont = wb.createFont();
				setFont.setFontHeightInPoints((short) 12);
				setFont.setBold(true);
				headerCellStyle.setFont(setFont);

				/*
				 * Setting up Data style in XLSX
				 */
				CellStyle dataCellStyle = wb.createCellStyle();
				Font setDataFont = wb.createFont();
				setDataFont.setFontHeightInPoints((short) 12);
				dataCellStyle.setFont(setDataFont);

				/*
				 * Initializing Cell reference variable
				 */
				Cell cell = null;

				cell = row.createCell((short) 0);
				cell.setCellValue("Student Custom Report-" + term);
				cell.setCellStyle(dataCellStyle);

				row = spreadSheet.createRow(1);

				cell = row.createCell((short) 0);
				cell.setCellValue("Term: " + term);
				cell.setCellStyle(dataCellStyle);

				row = spreadSheet.createRow(2);

				cell = row.createCell((short) 0);
				cell.setCellValue("Standard: " + StandardName);
				cell.setCellStyle(dataCellStyle);

				row = spreadSheet.createRow(3);

				cell = row.createCell((short) 0);
				cell.setCellValue("Division: " + divisionName);
				cell.setCellStyle(dataCellStyle);

				row = spreadSheet.createRow(4);

				cell = row.createCell((short) 0);
				cell.setCellValue("Subject: " + subject[0]);
				cell.setCellStyle(dataCellStyle);

				row = spreadSheet.createRow(5);

				spreadSheet.setColumnWidth((short) 0, (short) (256 * 25));
				spreadSheet.setColumnWidth((short) 1, (short) (256 * 25));

				cell = row.createCell((short) 0);
				cell.setCellValue("Roll No.");
				cell.setCellStyle(headerCellStyle);

				cell = row.createCell((short) 1);
				cell.setCellValue("Student Name");
				cell.setCellStyle(headerCellStyle);

				if (Stage.equals("Primary")) {

					if (gradeCheck == 0) {

						int k = 2;

						for (Entry<String, Integer> SubjectEnrichmententry : NewExaminationList.entrySet()) {
							if (SubjectEnrichmententry.getKey().startsWith("Subject Enrichment")) {

								spreadSheet.setColumnWidth((short) k, (short) (256 * 25));

								cell = row.createCell((short) k);
								cell.setCellValue(SubjectEnrichmententry.getKey() + "(5)");
								cell.setCellStyle(headerCellStyle);

								k++;
							}
						}

						for (Entry<String, Integer> Notebookentry : NewExaminationList.entrySet()) {
							if (Notebookentry.getKey().startsWith("Notebook")) {

								spreadSheet.setColumnWidth((short) k, (short) (256 * 25));

								cell = row.createCell((short) k);
								cell.setCellValue(Notebookentry.getKey() + "(10)");
								cell.setCellStyle(headerCellStyle);

								k++;
							}
						}

						cell = row.createCell((short) k++);
						cell.setCellValue("SEA+NB(5)");
						cell.setCellStyle(headerCellStyle);

						cell = row.createCell((short) k++);
						cell.setCellValue("Unit Test Marks Obtained(20)");
						cell.setCellStyle(headerCellStyle);

						cell = row.createCell((short) k++);
						cell.setCellValue("Unit Test Marks Scaled(5)");
						cell.setCellStyle(headerCellStyle);

						cell = row.createCell((short) k++);
						cell.setCellValue("Term End Marks Obtained(40)");
						cell.setCellStyle(headerCellStyle);

						cell = row.createCell((short) k++);
						cell.setCellValue("Term End Marks Scaled(40)");
						cell.setCellStyle(headerCellStyle);

						cell = row.createCell((short) k++);
						cell.setCellValue("Total Marks Scaled(50)");
						cell.setCellStyle(headerCellStyle);

						cell = row.createCell((short) k++);
						cell.setCellValue("Grade");
						cell.setCellStyle(headerCellStyle);

					} else if (gradeCheck == 1) {
						spreadSheet.setColumnWidth((short) 2, (short) (256 * 25));

						cell = row.createCell((short) 2);
						cell.setCellValue("Grade");
						cell.setCellStyle(headerCellStyle);
					}

				} else {

					int k = 2;

					if (gradeCheck == 0) {

						for (Entry<String, Integer> SubjectEnrichmententry : NewExaminationList.entrySet()) {
							if (SubjectEnrichmententry.getKey().startsWith("Subject Enrichment")) {

								spreadSheet.setColumnWidth((short) k, (short) (256 * 25));

								cell = row.createCell((short) k);
								cell.setCellValue(SubjectEnrichmententry.getKey() + "(5)");
								cell.setCellStyle(headerCellStyle);

								k++;
							}
						}

						spreadSheet.setColumnWidth((short) k, (short) (256 * 25));

						cell = row.createCell((short) k);
						cell.setCellValue("Subject Enrichment Marks Scaled(5)");
						cell.setCellStyle(headerCellStyle);

						k++;

						for (Entry<String, Integer> Notebookentry : NewExaminationList.entrySet()) {
							if (Notebookentry.getKey().startsWith("Notebook")) {

								spreadSheet.setColumnWidth((short) k, (short) (256 * 25));

								cell = row.createCell((short) k);
								cell.setCellValue(Notebookentry.getKey() + "(5)");
								cell.setCellStyle(headerCellStyle);

								k++;
							}
						}

						cell = row.createCell((short) k++);
						cell.setCellValue("Notebook Marks Scaled(5)");
						cell.setCellStyle(headerCellStyle);

						cell = row.createCell((short) k++);
						cell.setCellValue("Unit Test Marks Obtained(40)");
						cell.setCellStyle(headerCellStyle);

						cell = row.createCell((short) k++);
						cell.setCellValue("Unit Test Marks Scaled(10)");
						cell.setCellStyle(headerCellStyle);

						cell = row.createCell((short) k++);
						cell.setCellValue("Term End Marks Obtained(30/80)");
						cell.setCellStyle(headerCellStyle);

						cell = row.createCell((short) k++);
						cell.setCellValue("Term End Marks Scaled(30/80)");
						cell.setCellStyle(headerCellStyle);

						cell = row.createCell((short) k++);
						cell.setCellValue("Total Marks Scaled(50/100)");
						cell.setCellStyle(headerCellStyle);

						cell = row.createCell((short) k++);
						cell.setCellValue("Grade");
						cell.setCellStyle(headerCellStyle);

					} else if (gradeCheck == 1) {
						spreadSheet.setColumnWidth((short) 0, (short) (256 * 25));

						cell = row.createCell((short) 2);
						cell.setCellValue("Grade");
						cell.setCellStyle(headerCellStyle);
					}
				}
				currentRow = 6;

				int k = 0;
				int j = 0;

				for (ConfigurationForm form : studentsCustomReportList) {

					row = spreadSheet.createRow(currentRow++);

					cell = row.createCell((short) 0);
					cell.setCellValue(form.getRollNumber());
					cell.setCellStyle(dataCellStyle);

					cell = row.createCell((short) 1);
					cell.setCellValue(form.getStudentName());
					cell.setCellStyle(dataCellStyle);

					if (Stage.equals("Primary")) {

						int m = 2;

						if (gradeCheck == 0) {

							if (SubjectEnrichmentList.get(k).contains(",")) {
								String[] SubjectEnrichmentMarks = SubjectEnrichmentList.get(k).split(",");

								System.out.println("SubjectEnrichmentList.get(k) : "
										+ SubjectEnrichmentList.get(k).toString() + "K :" + k);
								for (int i = 0; i < SubjectEnrichmentMarks.length; i++) {

									if (SubjectEnrichmentMarks[i].contains("$")) {

										String[] SubjectEnrichmentMarksValue = SubjectEnrichmentMarks[i].split("\\$");

										if (SubjectEnrichmentMarksValue[2].equals("1")) {

											cell = row.createCell((short) m);
											cell.setCellValue("ex");
											cell.setCellStyle(dataCellStyle);

										} else {

											cell = row.createCell((short) m);
											cell.setCellValue(Integer.parseInt(SubjectEnrichmentMarksValue[1]));
											cell.setCellStyle(dataCellStyle);
										}

									} else {

										cell = row.createCell((short) m);
										cell.setCellValue("0");
										cell.setCellStyle(dataCellStyle);
									}

									m++;
								}

							} else {

								if (SubjectEnrichmentList.get(k).contains("$")) {

									String[] SubjectEnrichmentMarksValue = SubjectEnrichmentList.get(k).split("\\$");

									if (SubjectEnrichmentMarksValue[2].equals("1")) {

										cell = row.createCell((short) m);
										cell.setCellValue("ex");
										cell.setCellStyle(dataCellStyle);

									} else {
										cell = row.createCell((short) m);
										cell.setCellValue(Integer.parseInt(SubjectEnrichmentMarksValue[1]));
										cell.setCellStyle(dataCellStyle);

									}

								} else {
									cell = row.createCell((short) m);
									cell.setCellValue("0");
									cell.setCellStyle(dataCellStyle);
								}
								m++;
							}

							if (NotebookList.get(k).contains(",")) {

								String[] NotebookMarksMarks = NotebookList.get(k).split(",");

								for (int i = 0; i < NotebookMarksMarks.length; i++) {

									String[] NotebookMarksMarksValue = NotebookMarksMarks[i].split("\\$");

									if (NotebookMarksMarksValue[2].equals("1")) {

										cell = row.createCell((short) m);
										cell.setCellValue("ex");
										cell.setCellStyle(dataCellStyle);

									} else {
										cell = row.createCell((short) m);
										cell.setCellValue(Integer.parseInt(NotebookMarksMarksValue[1]));
										cell.setCellStyle(dataCellStyle);

									}
									m++;
								}

							} else {

								if (NotebookList.get(k).contains("$")) {

									String[] NotebookMarksMarksValue = NotebookList.get(k).split("\\$");

									if (NotebookMarksMarksValue[2].equals("1")) {

										cell = row.createCell((short) m);
										cell.setCellValue("ex");
										cell.setCellStyle(dataCellStyle);

									} else {
										System.out.println("NotebookMarksMarksValue : " + NotebookMarksMarksValue[1]);
										cell = row.createCell((short) m);
										cell.setCellValue(Integer.parseInt(NotebookMarksMarksValue[1]));
										cell.setCellStyle(dataCellStyle);
									}

								} else {
									cell = row.createCell((short) m);
									cell.setCellValue("0");
									cell.setCellStyle(dataCellStyle);
								}
								m++;
							}

							cell = row.createCell((short) m++);
							cell.setCellValue(form.getNotebookMarks());
							cell.setCellStyle(dataCellStyle);

							cell = row.createCell((short) m++);
							cell.setCellValue(form.getUnitTestMarksObtained());
							cell.setCellStyle(dataCellStyle);

							cell = row.createCell((short) m++);
							cell.setCellValue(form.getUnitTestMarks());
							cell.setCellStyle(dataCellStyle);

							cell = row.createCell((short) m++);
							cell.setCellValue(form.getTermEndMarksObtained());
							cell.setCellStyle(dataCellStyle);

							cell = row.createCell((short) m++);
							cell.setCellValue(form.getTermEndMarks());
							cell.setCellStyle(dataCellStyle);

							cell = row.createCell((short) m++);
							cell.setCellValue(form.getFinalTotalMarks());
							cell.setCellStyle(dataCellStyle);

							cell = row.createCell((short) m++);
							cell.setCellValue(form.getGrade());
							cell.setCellStyle(dataCellStyle);

						} else if (gradeCheck == 1) {

							cell = row.createCell((short) 2);
							cell.setCellValue(form.getGrade());
							cell.setCellStyle(dataCellStyle);

						}

						k++;

					} else {

						int m = 2;

						if (gradeCheck == 0) {

							if (SubjectEnrichmentList.get(j).contains(",")) {

								String[] SubjectEnrichmentMarks = SubjectEnrichmentList.get(j).split(",");

								for (int i = 0; i < SubjectEnrichmentMarks.length; i++) {

									if (SubjectEnrichmentMarks[i].contains("$")) {

										String[] SubjectEnrichmentMarksValue = SubjectEnrichmentMarks[i].split("\\$");

										if (SubjectEnrichmentMarksValue[2].equals("1")) {

											cell = row.createCell((short) m);
											cell.setCellValue("ex");
											cell.setCellStyle(dataCellStyle);

										} else {

											cell = row.createCell((short) m);
											cell.setCellValue(Integer.parseInt(SubjectEnrichmentMarksValue[1]));
											cell.setCellStyle(dataCellStyle);

										}
									} else {

										cell = row.createCell((short) m);
										cell.setCellValue("0");
										cell.setCellStyle(dataCellStyle);
									}
									m++;
								}
							} else {

								if (SubjectEnrichmentList.get(j).contains("$")) {

									String[] SubjectEnrichmentMarksValue = SubjectEnrichmentList.get(j).split("\\$");

									if (SubjectEnrichmentMarksValue[2].equals("1")) {

										cell = row.createCell((short) m);
										cell.setCellValue("ex");
										cell.setCellStyle(dataCellStyle);

									} else {

										cell = row.createCell((short) m);
										cell.setCellValue(Integer.parseInt(SubjectEnrichmentMarksValue[1]));
										cell.setCellStyle(dataCellStyle);

									}
								} else {

									cell = row.createCell((short) m);
									cell.setCellValue("0");
									cell.setCellStyle(dataCellStyle);

								}

								m++;
							}

							cell = row.createCell((short) m);
							cell.setCellValue(form.getSubjectEnrichmentMarks());
							cell.setCellStyle(dataCellStyle);

							m++;

							if (NotebookList.get(j).contains(",")) {
								String[] NotebookMarksMarks = NotebookList.get(j).split(",");

								for (int i = 0; i < NotebookMarksMarks.length; i++) {

									String[] NotebookMarksMarksValue = NotebookMarksMarks[i].split("\\$");

									if (NotebookMarksMarksValue[2].equals("1")) {

										cell = row.createCell((short) m);
										cell.setCellValue("ex");
										cell.setCellStyle(dataCellStyle);

									} else {

										cell = row.createCell((short) m);
										cell.setCellValue(Integer.parseInt(NotebookMarksMarksValue[1]));
										cell.setCellStyle(dataCellStyle);

									}
									m++;

								}

							} else {

								if (NotebookList.get(j).contains("$")) {

									String[] NotebookMarksMarksValue = NotebookList.get(j).split("\\$");

									if (NotebookMarksMarksValue[2].equals("1")) {

										cell = row.createCell((short) m);
										cell.setCellValue("ex");
										cell.setCellStyle(dataCellStyle);

									} else {
										cell = row.createCell((short) m);
										cell.setCellValue(Integer.parseInt(NotebookMarksMarksValue[1]));
										cell.setCellStyle(dataCellStyle);

									}
								} else {
									cell = row.createCell((short) m);
									cell.setCellValue("0");
									cell.setCellStyle(dataCellStyle);
								}

								m++;
							}

							System.out.println("form.getNotebookMarks(): " + form.getNotebookMarks());
							cell = row.createCell((short) m++);
							cell.setCellValue(form.getNotebookMarks());
							cell.setCellStyle(dataCellStyle);

							cell = row.createCell((short) m++);
							cell.setCellValue(form.getUnitTestMarksObtained());
							cell.setCellStyle(dataCellStyle);

							cell = row.createCell((short) m++);
							cell.setCellValue(form.getUnitTestMarks());
							cell.setCellStyle(dataCellStyle);

							cell = row.createCell((short) m++);
							cell.setCellValue(form.getTermEndMarksObtained());
							cell.setCellStyle(dataCellStyle);

							cell = row.createCell((short) m++);
							cell.setCellValue(form.getTermEndMarks());
							cell.setCellStyle(dataCellStyle);

							cell = row.createCell((short) m++);
							cell.setCellValue(form.getFinalTotalMarks());
							cell.setCellStyle(dataCellStyle);

							cell = row.createCell((short) m++);
							cell.setCellValue(form.getGrade());
							cell.setCellStyle(dataCellStyle);

						} else if (gradeCheck == 1) {
							cell = row.createCell((short) 2);
							cell.setCellValue(form.getGrade());
							cell.setCellStyle(dataCellStyle);
						}

						j++;
					}
				}
			}

			/*
			 * writing to the xlsx file
			 */
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wb.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();

			/*
			 * Write the output to a file
			 */
			FileOutputStream fileOut = new FileOutputStream(new File(realPath + "/" + excelFileName));
			fileOut.write(outArray);
			fileOut.flush();
			fileOut.close();

			System.out.println("Excel sheet for Students Customised Report is created successfully.");

			status = "success";

			/*
			 * Closing resultSet, preparedStatement, connection objects
			 */

		} catch (Exception exception) {
			exception.printStackTrace();
			status = "error";
		}
		return status;

	}

	public String generateStudentsMarksReport(int studentID, int registrationID, int standardID, String termName,
			int ayClassID, int userID, int organisationID, int academicYearID, String realPath, String excelFileName) {

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		List<ConfigurationForm> studentDetailsList = null;

		HashMap<String, Integer> NewExaminationList = null;

		List<ConfigurationForm> ScholasticGradeList = null;

		String studentName = "";
		String standard = "";
		String division = "";
		int rollNo = 0;

		int currentRow = 0;

		int SrNo = 1;

		try {

			String StandardName = daoInf2.getStandardNameByStandardID(standardID);

			String Stage = daoInf2.getStandardStageByStandardID(standardID);

			String StdDivName = daoInf2.retrieveStdDivName(studentID);

			if (Stage.equals("Primary")) {

				studentDetailsList = daoInf2.retrieveStudentDetailsList(studentID, termName, ayClassID);

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

							// toatlScaleTo1 = totalMarksScaled;

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

				studentDetailsList = daoInf2.retrieveStudentDetailsList(studentID, termName, ayClassID);

				String NewSubjectList = daoInf2.retrieveSubjectListForStandardByStandardID(standardID);

				NewExaminationList = daoInf2.retrieveExaminationList(academicYearID, termName, ayClassID);

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
									ayClassID, academicYearID, termName);

							scaleTo3 = daoInf2.retrieveScaleToNew(Integer.parseInt(subArr[j]), "Notebook", ayClassID,
									academicYearID, termName);

							scaleTo4 = daoInf2.retrieveScaleToNew(Integer.parseInt(subArr[j]), "Portfolio", ayClassID,
									academicYearID, termName);

							scaleTo5 = daoInf2.retrieveScaleToNew(Integer.parseInt(subArr[j]), "Multiple Assessment",
									ayClassID, academicYearID, termName);

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
			}

			File file = new File(realPath + "/" + excelFileName);

			/*
			 * Generating XLSX Header value for Patient Information
			 */

			// Workbook wb = new HSSFWorkbook();
			XSSFWorkbook wb = new XSSFWorkbook();

			XSSFSheet spreadSheet = wb.createSheet("Students Marks Excel Sheet");
			Row row;

			row = spreadSheet.createRow(0);

			spreadSheet.setColumnWidth((short) 0, (short) (256 * 25));

			int counter = 1;

			/*
			 * Creating header in XLSX
			 */
			CellStyle headerCellStyle = wb.createCellStyle();

			/*
			 * Setting up the font
			 */
			Font setFont = wb.createFont();
			setFont.setFontHeightInPoints((short) 12);
			setFont.setBold(true);
			headerCellStyle.setFont(setFont);

			/*
			 * Setting up Data style in XLSX
			 */
			CellStyle dataCellStyle = wb.createCellStyle();
			Font setDataFont = wb.createFont();
			setDataFont.setFontHeightInPoints((short) 12);
			dataCellStyle.setFont(setDataFont);

			/*
			 * Initializing Cell reference variable
			 */
			Cell cell = null;

			cell = row.createCell((short) 0);
			cell.setCellValue(
					"Marks Report Card " + termName + " (" + daoInf1.retrieveAcademicYearName(organisationID) + ")");
			cell.setCellStyle(dataCellStyle);

			row = spreadSheet.createRow(1);

			cell = row.createCell((short) 0);
			cell.setCellValue("Student Name: " + studentName);
			cell.setCellStyle(dataCellStyle);

			row = spreadSheet.createRow(2);

			cell = row.createCell((short) 0);
			cell.setCellValue("Standard: " + standard);
			cell.setCellStyle(dataCellStyle);

			row = spreadSheet.createRow(3);

			cell = row.createCell((short) 0);
			cell.setCellValue("Division: " + division);
			cell.setCellStyle(dataCellStyle);

			row = spreadSheet.createRow(4);

			cell = row.createCell((short) 0);
			cell.setCellValue("Roll Number: " + rollNo);
			cell.setCellStyle(dataCellStyle);

			int counter1 = 1;

			row = spreadSheet.createRow(6);

			cell = row.createCell((short) 0);
			cell.setCellValue("Scholastic Areas: ");
			cell.setCellStyle(headerCellStyle);

			if (StandardName.equals("I") || StandardName.equals("II")
					|| StandardName.equals("III") | StandardName.equals("IV")) {

				row = spreadSheet.createRow(7);

				cell = row.createCell((short) 0);
				cell.setCellValue("Sr No.");
				cell.setCellStyle(headerCellStyle);

				cell = row.createCell((short) 1);
				cell.setCellValue("Subjects");
				cell.setCellStyle(headerCellStyle);

				cell = row.createCell((short) 2);
				cell.setCellValue("Subject Enrichment\n+ Notebook(5)");
				cell.setCellStyle(headerCellStyle);

				cell = row.createCell((short) 3);
				cell.setCellValue("Unit Test\n(5)");
				cell.setCellStyle(headerCellStyle);

				cell = row.createCell((short) 4);
				cell.setCellValue("Term End\n(40)");
				cell.setCellStyle(headerCellStyle);

				cell = row.createCell((short) 5);
				cell.setCellValue("Marks Obtained\n(50)");
				cell.setCellStyle(headerCellStyle);

				cell = row.createCell((short) 6);
				cell.setCellValue("Grade");
				cell.setCellStyle(headerCellStyle);

				currentRow = 8;

				for (ConfigurationForm form : ScholasticGradeList) {
					row = spreadSheet.createRow(currentRow++);

					cell = row.createCell((short) 0);
					cell.setCellValue("" + SrNo);
					cell.setCellStyle(dataCellStyle);

					cell = row.createCell((short) 1);
					cell.setCellValue(form.getSubject());
					cell.setCellStyle(dataCellStyle);

					cell = row.createCell((short) 2);
					cell.setCellValue(form.getSubjectEnrichmentMarks());
					cell.setCellStyle(dataCellStyle);

					cell = row.createCell((short) 3);
					cell.setCellValue(form.getUnitTestMarks());
					cell.setCellStyle(dataCellStyle);

					cell = row.createCell((short) 4);
					cell.setCellValue(form.getTermEndMarks());
					cell.setCellStyle(dataCellStyle);

					cell = row.createCell((short) 5);
					cell.setCellValue(form.getFinalTotalMarks());
					cell.setCellStyle(dataCellStyle);

					cell = row.createCell((short) 6);
					cell.setCellValue(form.getGrade());
					cell.setCellStyle(dataCellStyle);

					SrNo++;
				}

			} else {

				row = spreadSheet.createRow(6);

				cell = row.createCell((short) 0);
				cell.setCellValue("Sr No.");
				cell.setCellStyle(headerCellStyle);

				cell = row.createCell((short) 1);
				cell.setCellValue("Subjects");
				cell.setCellStyle(headerCellStyle);

				cell = row.createCell((short) 2);
				cell.setCellValue("Internal Marks\n(20)");
				cell.setCellStyle(headerCellStyle);

				cell = row.createCell((short) 3);
				cell.setCellValue("Term End\n(30/80)");
				cell.setCellStyle(headerCellStyle);

				cell = row.createCell((short) 4);
				cell.setCellValue("Marks Obtained\n(50/100)");
				cell.setCellStyle(headerCellStyle);

				cell = row.createCell((short) 5);
				cell.setCellValue("Grade");
				cell.setCellStyle(headerCellStyle);

				currentRow = 8;

				for (ConfigurationForm form : ScholasticGradeList) {
					row = spreadSheet.createRow(currentRow++);

					cell = row.createCell((short) 0);
					cell.setCellValue("" + SrNo);
					cell.setCellStyle(dataCellStyle);

					cell = row.createCell((short) 1);
					cell.setCellValue(form.getSubject());
					cell.setCellStyle(dataCellStyle);

					cell = row.createCell((short) 2);
					cell.setCellValue(form.getUnitTestMarks());
					cell.setCellStyle(dataCellStyle);

					cell = row.createCell((short) 3);
					cell.setCellValue(form.getTermEndMarks());
					cell.setCellStyle(dataCellStyle);

					cell = row.createCell((short) 4);
					cell.setCellValue(form.getFinalTotalMarks());
					cell.setCellStyle(dataCellStyle);

					cell = row.createCell((short) 5);
					cell.setCellValue(form.getGrade());
					cell.setCellStyle(dataCellStyle);

					SrNo++;
				}
			}

			/*
			 * writing to the xlsx file
			 */
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wb.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();

			/*
			 * Write the output to a file
			 */
			FileOutputStream fileOut = new FileOutputStream(new File(realPath + "/" + excelFileName));
			fileOut.write(outArray);
			fileOut.flush();
			fileOut.close();

			System.out.println("Excel sheet for Students exported successfully.");

			status = "success";

			/*
			 * Closing resultSet, preparedStatement, connection objects
			 */

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;

	}

	public String generateExamsConsolidatedReport(StudentForm studform, String standard, int standardID,
			int organisationID, int AcademicYearID, String realPath, String excelFileName) {

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		StuduntDAOInf daoInf = new StudentDAOImpl();

		HashMap<Integer, String> standardWiseSubjectDetailsList = null;

		HashMap<String, Integer> NewExaminationListTermI = null;

		HashMap<String, Integer> NewExaminationListTermII = null;

		List<String> studentFinalExamCustomReportList = null;

		HashMap<String, String> StudentsSubjectFinalTotalMarksList = null;

		HashMap<String, HashMap<Double, String>> StudentsSubjectTotalMarksList = null;

		Map<Double, String> StudentsFinalTotalMarksList = null;

		HashMap<Double, String> StudentsTotalMarksList = null;

		List<StudentForm> studentsBasedExamCustomReportList = null;

		List<StudentForm> ScholasticGradeList = null;

		int currentRow = 0;

		try {

			File file = new File(realPath + "/" + excelFileName);

			/*
			 * Generating XLSX Header value for Patient Information
			 */

			// retriving Academic Year ID value
			String AcademicYear = "";
			AcademicYear = daoInf1.retrieveAcademicYearName(organisationID);

			String Stage = daoInf2.getStandardStageByStandardID(standardID);

			int AYClassID = 0;

			AYClassID = daoInf2.retrieveAYCLassIDForAll(standardID, AcademicYearID);

			int counterNew = 0;

			String Value, StudName = "";

			double SubMarks, topperMarks = 0D;

			studentsBasedExamCustomReportList = daoInf.retrievStudentsListByStandardID(standardID, AcademicYearID);

			standardWiseSubjectDetailsList = daoInf2.retrieveStandardWiseSubjectList(standardID, AYClassID);

			studentFinalExamCustomReportList = new ArrayList<String>();

			StudentsSubjectTotalMarksList = new HashMap<String, HashMap<Double, String>>();

			StudentsSubjectFinalTotalMarksList = new HashMap<String, String>();

			StudentsTotalMarksList = new HashMap<Double, String>();

			int test = 0;

			for (StudentForm studentsExamFormName : studentsBasedExamCustomReportList) {

				double StudentFinalTotalMarks = 0, finalTermMarks = 0, toatlTermMarks = 0;
				String Studentgrade = "";
				String FinalList = "";

				int maxCount, tempcount = 0;

				AYClassID = studentsExamFormName.getAyclassID();

				for (Integer SubjectName : standardWiseSubjectDetailsList.keySet()) {

					NewExaminationListTermI = daoInf2.retrieveExaminationList(AcademicYearID, "Term I", AYClassID);

					NewExaminationListTermII = daoInf2.retrieveExaminationList(AcademicYearID, "Term II", AYClassID);

					if (Stage.equals("Primary")) {

						double TermIIvalue, TermIIvalue1, TermIIvalue2, TermIvalue, TermIvalue1, TermIvalue2,
								toatlTermMarksVaalue = 0;

						int scaleToTermI, scaleToTermI1, scaleToTermI2, scaleToTermII, scaleToTermII1, scaleToTermII2,
								toatlScaleTo = 0;

						boolean termEdCheck = daoInf2.verifyAbsent(SubjectName, studentsExamFormName.getStudentID(),
								AYClassID, NewExaminationListTermI.get("Term End"));

						boolean unitTestCheck = daoInf2.verifyAbsent(SubjectName, studentsExamFormName.getStudentID(),
								AYClassID, NewExaminationListTermI.get("Unit Test"));

						TermIvalue = daoInf.retrievrAllMarksScaledForStudent(NewExaminationListTermI.get("Term End"),
								SubjectName, standardID, AcademicYearID, studentsExamFormName.getStudentID());

						TermIvalue1 = daoInf.retrievrAllMarksScaledForStudent(NewExaminationListTermI.get("Unit Test"),
								SubjectName, standardID, AcademicYearID, studentsExamFormName.getStudentID());

						TermIvalue2 = daoInf2.retrieveScholasticGradeList1(SubjectName,
								studentsExamFormName.getStudentID(), 0, "Term I");

						TermIIvalue = daoInf.retrievrAllMarksScaledForStudent(NewExaminationListTermII.get("Term End"),
								SubjectName, standardID, AcademicYearID, studentsExamFormName.getStudentID());

						TermIIvalue1 = daoInf.retrievrAllMarksScaledForStudent(
								NewExaminationListTermII.get("Unit Test"), SubjectName, standardID, AcademicYearID,
								studentsExamFormName.getStudentID());

						TermIIvalue2 = daoInf2.retrieveScholasticGradeList1(SubjectName,
								studentsExamFormName.getStudentID(), 0, "Term II");

						finalTermMarks = (TermIvalue + TermIvalue1 + TermIvalue2 + TermIIvalue + TermIIvalue1
								+ TermIIvalue2);

						scaleToTermI = daoInf2.retrieveScaleTo(SubjectName, NewExaminationListTermI.get("Term End"),
								AYClassID);

						scaleToTermI1 = daoInf2.retrieveScaleTo(SubjectName, NewExaminationListTermI.get("Unit Test"),
								AYClassID);

						scaleToTermI2 = daoInf2.retrieveScaleToNew1(SubjectName, AYClassID, AcademicYearID, "Term I");

						scaleToTermII = daoInf2.retrieveScaleTo(SubjectName, NewExaminationListTermII.get("Term End"),
								AYClassID);

						scaleToTermII1 = daoInf2.retrieveScaleTo(SubjectName, NewExaminationListTermII.get("Unit Test"),
								AYClassID);

						scaleToTermII2 = daoInf2.retrieveScaleToNew1(SubjectName, AYClassID, AcademicYearID, "Term II");

						toatlScaleTo = (scaleToTermI + scaleToTermI1 + scaleToTermI2 + scaleToTermII + scaleToTermII1
								+ scaleToTermII2);

						toatlTermMarks = (finalTermMarks / toatlScaleTo) * 100;

						String numberAsString = Double.toString(toatlTermMarks);

						String marks[] = numberAsString.split("\\.");

						if (Long.parseLong(marks[1]) > 0) {

							toatlTermMarksVaalue = Integer.parseInt(marks[0]) + 1;

						} else {

							toatlTermMarksVaalue = Integer.parseInt(marks[0]);
						}
						/* Calculating Final Grades */
						if (toatlTermMarksVaalue >= 91 && toatlTermMarksVaalue <= 100) {
							Studentgrade = "A1";
						} else if (toatlTermMarksVaalue >= 81 && toatlTermMarksVaalue <= 90) {
							Studentgrade = "A2";
						} else if (toatlTermMarksVaalue >= 71 && toatlTermMarksVaalue <= 80) {
							Studentgrade = "B1";
						} else if (toatlTermMarksVaalue >= 61 && toatlTermMarksVaalue <= 70) {
							Studentgrade = "B2";
						} else if (toatlTermMarksVaalue >= 51 && toatlTermMarksVaalue <= 60) {
							Studentgrade = "C1";
						} else if (toatlTermMarksVaalue >= 41 && toatlTermMarksVaalue <= 50) {
							Studentgrade = "C2";
						} else if (toatlTermMarksVaalue >= 33 && toatlTermMarksVaalue <= 40) {
							Studentgrade = "D";
						} else {

							Studentgrade = "E";
						}

						if (termEdCheck || unitTestCheck) {
							Studentgrade = "ex";
						}

						FinalList = FinalList + "$" + finalTermMarks + "$" + Studentgrade;

					} else {

						double TermIIvalue, TermIIvalue1, TermIIvalue2, TermIIvalue3, TermIIvalue4, TermIIvalue5,
								TermIvalue, TermIvalue1, TermIvalue2, TermIvalue3, TermIvalue4, TermIvalue5,
								toatlTermMarksVaalue = 0;

						int scaleToTermI, scaleToTermI1, scaleToTermI2, scaleToTermI3, scaleToTermI4, scaleToTermI5,
								scaleToTermII, scaleToTermII1, scaleToTermII2, scaleToTermII3, scaleToTermII4,
								scaleToTermII5, toatlScaleTo = 0;

						boolean termEdCheck = daoInf2.verifyAbsent(SubjectName, studentsExamFormName.getStudentID(),
								AYClassID, NewExaminationListTermI.get("Term End"));

						boolean unitTestCheck = daoInf2.verifyAbsent(SubjectName, studentsExamFormName.getStudentID(),
								AYClassID, NewExaminationListTermI.get("Unit Test"));

						TermIvalue = daoInf.retrievrAllMarksScaledForStudent(NewExaminationListTermI.get("Term End"),
								SubjectName, standardID, AcademicYearID, studentsExamFormName.getStudentID());

						TermIvalue1 = daoInf.retrievrAllMarksScaledForStudent(NewExaminationListTermI.get("Unit Test"),
								SubjectName, standardID, AcademicYearID, studentsExamFormName.getStudentID());

						TermIvalue2 = daoInf2.retrieveScholasticGradeListNew(SubjectName, "Subject Enrichment",
								studentsExamFormName.getStudentID(), 0, "Term I");

						TermIvalue3 = daoInf2.retrieveScholasticGradeListNew(SubjectName, "Notebook",
								studentsExamFormName.getStudentID(), 0, "Term I");

						TermIvalue4 = daoInf2.retrieveScholasticGradeListNew(SubjectName, "Portfolio",
								studentsExamFormName.getStudentID(), 0, "Term I");

						TermIvalue5 = daoInf2.retrieveScholasticGradeListNew(SubjectName, "Multiple Assessment",
								studentsExamFormName.getStudentID(), 0, "Term I");

						TermIIvalue = daoInf.retrievrAllMarksScaledForStudent(NewExaminationListTermII.get("Term End"),
								SubjectName, standardID, AcademicYearID, studentsExamFormName.getStudentID());

						TermIIvalue1 = daoInf.retrievrAllMarksScaledForStudent(
								NewExaminationListTermII.get("Unit Test"), SubjectName, standardID, AcademicYearID,
								studentsExamFormName.getStudentID());

						TermIIvalue2 = daoInf2.retrieveScholasticGradeListNew(SubjectName, "Subject Enrichment",
								studentsExamFormName.getStudentID(), 0, "Term II");

						TermIIvalue3 = daoInf2.retrieveScholasticGradeListNew(SubjectName, "Notebook",
								studentsExamFormName.getStudentID(), 0, "Term II");

						TermIIvalue4 = daoInf2.retrieveScholasticGradeListNew(SubjectName, "Portfolio",
								studentsExamFormName.getStudentID(), 0, "Term II");

						TermIIvalue5 = daoInf2.retrieveScholasticGradeListNew(SubjectName, "Multiple Assessment",
								studentsExamFormName.getStudentID(), 0, "Term II");

						finalTermMarks = (TermIvalue + TermIvalue1 + TermIvalue2 + TermIvalue3 + TermIvalue4
								+ TermIvalue5 + TermIIvalue + TermIIvalue1 + TermIIvalue2 + TermIIvalue3 + TermIIvalue4
								+ TermIIvalue5);

						scaleToTermI = daoInf2.retrieveScaleTo(SubjectName, NewExaminationListTermI.get("Term End"),
								AYClassID);

						scaleToTermI1 = daoInf2.retrieveScaleTo(SubjectName, NewExaminationListTermI.get("Unit Test"),
								AYClassID);

						scaleToTermI2 = daoInf2.retrieveScaleToNew(SubjectName, "Subject Enrichment", AYClassID,
								AcademicYearID, "Term I");

						scaleToTermI3 = daoInf2.retrieveScaleToNew(SubjectName, "Notebook", AYClassID, AcademicYearID,
								"Term I");

						scaleToTermI4 = daoInf2.retrieveScaleToNew(SubjectName, "Portfolio", AYClassID, AcademicYearID,
								"Term I");

						scaleToTermI5 = daoInf2.retrieveScaleToNew(SubjectName, "Multiple Assessment", AYClassID,
								AcademicYearID, "Term I");

						scaleToTermII = daoInf2.retrieveScaleTo(SubjectName, NewExaminationListTermII.get("Term End"),
								AYClassID);

						scaleToTermII1 = daoInf2.retrieveScaleTo(SubjectName, NewExaminationListTermII.get("Unit Test"),
								AYClassID);

						scaleToTermII2 = daoInf2.retrieveScaleToNew(SubjectName, "Subject Enrichment", AYClassID,
								AcademicYearID, "Term II");

						scaleToTermII3 = daoInf2.retrieveScaleToNew(SubjectName, "Notebook", AYClassID, AcademicYearID,
								"Term II");

						scaleToTermII4 = daoInf2.retrieveScaleToNew(SubjectName, "Portfolio", AYClassID, AcademicYearID,
								"Term II");

						scaleToTermII5 = daoInf2.retrieveScaleToNew(SubjectName, "Multiple Assessment", AYClassID,
								AcademicYearID, "Term II");

						toatlScaleTo = (scaleToTermI + scaleToTermI1 + scaleToTermI2 + scaleToTermI3 + scaleToTermI4
								+ scaleToTermI5 + scaleToTermII + scaleToTermII1 + scaleToTermII2 + scaleToTermII3
								+ scaleToTermII4 + scaleToTermII5);

						/*
						 * if (standardWiseSubjectDetailsList.get(SubjectName).equals("Hindi")) {
						 * 
						 * toatlTermMarks = finalTermMarks; } else {
						 */

						toatlTermMarks = (finalTermMarks / toatlScaleTo) * 100;
						/* } */

						String numberAsString = Double.toString(toatlTermMarks);

						String marks[] = numberAsString.split("\\.");

						System.out.println("...marks..." + Arrays.toString(marks) + "...subject name..." + SubjectName);

						if (Long.parseLong(marks[1]) > 0) {

							toatlTermMarksVaalue = Integer.parseInt(marks[0]) + 1;

						} else {

							toatlTermMarksVaalue = Integer.parseInt(marks[0]);
						}
						/* Calculating Final Grades */
						if (toatlTermMarksVaalue >= 91 && toatlTermMarksVaalue <= 100) {
							Studentgrade = "A1";
						} else if (toatlTermMarksVaalue >= 81 && toatlTermMarksVaalue <= 90) {
							Studentgrade = "A2";
						} else if (toatlTermMarksVaalue >= 71 && toatlTermMarksVaalue <= 80) {
							Studentgrade = "B1";
						} else if (toatlTermMarksVaalue >= 61 && toatlTermMarksVaalue <= 70) {
							Studentgrade = "B2";
						} else if (toatlTermMarksVaalue >= 51 && toatlTermMarksVaalue <= 60) {
							Studentgrade = "C1";
						} else if (toatlTermMarksVaalue >= 41 && toatlTermMarksVaalue <= 50) {
							Studentgrade = "C2";
						} else if (toatlTermMarksVaalue >= 33 && toatlTermMarksVaalue <= 40) {
							Studentgrade = "D";
						} else {

							Studentgrade = "E";
						}

						if (termEdCheck || unitTestCheck) {
							Studentgrade = "ex";
						}

						FinalList = FinalList + "$" + finalTermMarks + "$" + Studentgrade;
					}

					StudentFinalTotalMarks = (StudentFinalTotalMarks + finalTermMarks);

					if (StudentsSubjectTotalMarksList.containsKey(standardWiseSubjectDetailsList.get(SubjectName))) {

						HashMap<Double, String> valueMap = StudentsSubjectTotalMarksList
								.get(standardWiseSubjectDetailsList.get(SubjectName));

						if (valueMap.containsKey(finalTermMarks)) {

							String studentNameValue = valueMap.get(finalTermMarks);
							studentNameValue = studentNameValue + "," + studentsExamFormName.getStudentName();

							if (studentNameValue.startsWith(",")) {
								studentNameValue = studentNameValue.substring(1);
							}

							valueMap.put(finalTermMarks, studentNameValue);

						} else {
							valueMap.put(finalTermMarks, studentsExamFormName.getStudentName());
						}

						// Value = Value + "=" + studentsExamFormName.getStudentName() + "$" +
						// finalTermMarks;

						StudentsSubjectTotalMarksList.put(standardWiseSubjectDetailsList.get(SubjectName), valueMap);
					} else {

						Value = studentsExamFormName.getStudentName() + "$" + finalTermMarks;

						HashMap<Double, String> valueMap = new HashMap<Double, String>();

						valueMap.put(finalTermMarks, studentsExamFormName.getStudentName());
						StudentsSubjectTotalMarksList.put(standardWiseSubjectDetailsList.get(SubjectName), valueMap);
					}
				}

				if (StudentsTotalMarksList.containsKey(StudentFinalTotalMarks)) {

					String ValueNew = StudentsTotalMarksList.get(StudentFinalTotalMarks);

					ValueNew = ValueNew + "," + studentsExamFormName.getStudentName();

					StudentsTotalMarksList.put(StudentFinalTotalMarks, ValueNew);
				} else {
					StudentsTotalMarksList.put(StudentFinalTotalMarks, studentsExamFormName.getStudentName());
				}

				FinalList = studentsExamFormName.getRollNumber() + "$" + studentsExamFormName.getStudentName()
						+ FinalList + "$" + StudentFinalTotalMarks;

				studentFinalExamCustomReportList.add(FinalList);

				System.out.println("...list....." + StudentsTotalMarksList);
			}

			/* Finding subject toppers logic */
			for (String Subject : StudentsSubjectTotalMarksList.keySet()) {

				Map<Double, String> valueMap = new TreeMap<Double, String>(StudentsSubjectTotalMarksList.get(Subject));

				StudentsSubjectFinalTotalMarksList.put(Subject,
						valueMap.get(valueMap.keySet().toArray()[valueMap.size() - 1]) + "("
								+ valueMap.keySet().toArray()[valueMap.size() - 1] + ")");
			}

			/* Finding toppers logic */

			StudentsFinalTotalMarksList = new TreeMap<Double, String>(StudentsTotalMarksList);

			System.out.println("length:" + StudentsFinalTotalMarksList);
			int counter = 0;

			// Workbook wb = new HSSFWorkbook();
			XSSFWorkbook wb = new XSSFWorkbook();

			XSSFSheet spreadSheet = wb.createSheet("Standards Consolidated Sheet");
			Row row;

			row = spreadSheet.createRow(0);

			spreadSheet.setColumnWidth((short) 0, (short) (256 * 25));

			/*
			 * Creating header in XLSX
			 */
			CellStyle headerCellStyle = wb.createCellStyle();

			/*
			 * Setting up the font
			 */
			Font setFont = wb.createFont();
			setFont.setFontHeightInPoints((short) 12);
			setFont.setBold(true);
			headerCellStyle.setFont(setFont);

			/*
			 * Setting up Data style in XLSX
			 */
			CellStyle dataCellStyle = wb.createCellStyle();
			Font setDataFont = wb.createFont();
			setDataFont.setFontHeightInPoints((short) 12);
			dataCellStyle.setFont(setDataFont);

			/*
			 * Initializing Cell reference variable
			 */
			Cell cell = null;

			cell = row.createCell((short) 0);
			cell.setCellValue(daoInf1.retrieveOrganizationName(organisationID));
			cell.setCellStyle(headerCellStyle);

			row = spreadSheet.createRow(1);

			cell = row.createCell((short) 0);
			cell.setCellValue("CONSOLIDATED SHEET FOR STD " + standard + "(" + AcademicYear + ")");
			cell.setCellStyle(headerCellStyle);

			row = spreadSheet.createRow(3);

			// System.out.println("..."+StudentsFinalTotalMarksList.size());
			System.out.println("........" + Arrays.toString(StudentsFinalTotalMarksList.keySet().toArray()));

			cell = row.createCell((short) 0);
			cell.setCellValue("First topper: "
					+ (StudentsFinalTotalMarksList.get(
							StudentsFinalTotalMarksList.keySet().toArray()[StudentsFinalTotalMarksList.size() - 1]))
					+ "(" + (StudentsFinalTotalMarksList.keySet().toArray()[StudentsFinalTotalMarksList.size() - 1])
					+ ")");
			cell.setCellStyle(headerCellStyle);

			row = spreadSheet.createRow(4);

			cell = row.createCell((short) 0);
			cell.setCellValue("Second topper: "
					+ (StudentsFinalTotalMarksList.get(
							StudentsFinalTotalMarksList.keySet().toArray()[StudentsFinalTotalMarksList.size() - 2]))
					+ "(" + (StudentsFinalTotalMarksList.keySet().toArray()[StudentsFinalTotalMarksList.size() - 2])
					+ ")");
			cell.setCellStyle(headerCellStyle);

			row = spreadSheet.createRow(5);

			cell = row.createCell((short) 0);
			cell.setCellValue("Third topper: "
					+ (StudentsFinalTotalMarksList.get(
							StudentsFinalTotalMarksList.keySet().toArray()[StudentsFinalTotalMarksList.size() - 3]))
					+ "(" + (StudentsFinalTotalMarksList.keySet().toArray()[StudentsFinalTotalMarksList.size() - 3])
					+ ")");
			cell.setCellStyle(headerCellStyle);

			counter = 6;

			counter++;

			row = spreadSheet.createRow(counter);

			cell = row.createCell((short) 0);
			cell.setCellValue("Subject wise toppers list: ");
			cell.setCellStyle(headerCellStyle);

			counter++;

			for (String subTopper : StudentsSubjectFinalTotalMarksList.keySet()) {

				row = spreadSheet.createRow(counter);

				cell = row.createCell((short) 1);
				cell.setCellValue(subTopper + " : " + StudentsSubjectFinalTotalMarksList.get(subTopper));
				cell.setCellStyle(dataCellStyle);

				counter++;
			}

			int counter1 = 3;

			counter++;

			row = spreadSheet.createRow(counter++);

			cell = row.createCell((short) 0);
			cell.setCellValue("Sr. No.");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 1);
			cell.setCellValue("Roll NO.");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 2);
			cell.setCellValue("NAME OF STUDENT");
			cell.setCellStyle(headerCellStyle);

			for (Integer SubjectName : standardWiseSubjectDetailsList.keySet()) {

				cell = row.createCell((short) counter1);
				cell.setCellValue(standardWiseSubjectDetailsList.get(SubjectName));
				cell.setCellStyle(headerCellStyle);

				counter1++;

				cell = row.createCell((short) counter1);
				cell.setCellValue("Grade");
				cell.setCellStyle(headerCellStyle);

				counter1++;
			}

			cell = row.createCell((short) counter1++);
			cell.setCellValue("Total");
			cell.setCellStyle(headerCellStyle);

			int srNo = 1;

			for (String FinalStudentsReportData : studentFinalExamCustomReportList) {

				if (FinalStudentsReportData.isEmpty()) {

				} else {
					int counter2 = 1;
					row = spreadSheet.createRow(counter++);

					cell = row.createCell((short) 0);
					cell.setCellValue(srNo);
					cell.setCellStyle(dataCellStyle);
					srNo++;

					String[] List = FinalStudentsReportData.split("\\$");

					for (int j = 0; j < List.length; j++) {

						cell = row.createCell((short) counter2);
						cell.setCellValue(List[j]);
						cell.setCellStyle(dataCellStyle);
						counter2++;
					}
				}
			}

			/*
			 * writing to the xlsx file
			 */
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wb.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();

			/*
			 * Write the output to a file
			 */
			FileOutputStream fileOut = new FileOutputStream(new File(realPath + "/" + excelFileName));
			fileOut.write(outArray);
			fileOut.flush();
			fileOut.close();

			System.out.println("Excel sheet for Students Consolidated Report is created successfully.");

			status = "success";

			/*
			 * Closing resultSet, preparedStatement, connection objects
			 */

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	public String exportStudentPersonalityReportExcel(int userID, int examID, int academicYearID, int standardID,
			int divisionID, int ayClassID, int OrganizationID, String realPath, String excelFileName) {

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		StuduntDAOInf daoInf = new StudentDAOImpl();

		HashMap<Integer, String> standardWiseSubjectDetailsList = null;

		List<String> studentFinalExamCustomReportList = null;

		List<StudentForm> studentsBasedExamCustomReportList = null;

		int currentRow = 4;

		try {

			File file = new File(realPath + "/" + excelFileName);

			/*
			 * Generating XLSX Header value for Patient Information
			 */

			int counterNew = 0;

			String standardDiv = daoInf2.retrieveStandardNameDivisionName(standardID, divisionID);

			String term = daoInf.retrieveTermByExaminationID(examID);

			studentsBasedExamCustomReportList = daoInf.retriveStudentListByStandardAndDivision(standardID, divisionID);

			standardWiseSubjectDetailsList = daoInf2.retrieveSubjectListByExamIDByForPersonalityDevelopment(userID,
					ayClassID, OrganizationID);

			studentFinalExamCustomReportList = new ArrayList<String>();

			for (StudentForm studentsExamFormName : studentsBasedExamCustomReportList) {

				double StudentFinalTotalMarks = 0, finalTermMarks = 0, toatlTermMarks = 0;
				String Studentgrade = "";
				String SubGrade = "";
				String FinalList = "";

				for (Integer SubjectName : standardWiseSubjectDetailsList.keySet()) {

					StudentFinalTotalMarks = (StudentFinalTotalMarks + finalTermMarks);

					Studentgrade = daoInf.retrievePersonalityDevelopmentMarksBySubjectID(ayClassID, examID,
							studentsExamFormName.getStudentID(), SubjectName);

					SubGrade = SubGrade + "$" + Studentgrade;
				}

				FinalList = studentsExamFormName.getRollNumber() + "$" + studentsExamFormName.getStudentName()
						+ SubGrade;

				System.out.println("Final list: " + FinalList);

				studentFinalExamCustomReportList.add(FinalList);
			}

			int counter = 0;

			// Workbook wb = new HSSFWorkbook();
			XSSFWorkbook wb = new XSSFWorkbook();

			XSSFSheet spreadSheet = wb.createSheet("Standards Personality Development Marks Sheet");
			Row row;

			row = spreadSheet.createRow(0);

			spreadSheet.setColumnWidth((short) 0, (short) (256 * 25));

			/*
			 * Creating header in XLSX
			 */
			CellStyle headerCellStyle = wb.createCellStyle();

			/*
			 * Setting up the font
			 */
			Font setFont = wb.createFont();
			setFont.setFontHeightInPoints((short) 12);
			setFont.setBold(true);
			headerCellStyle.setFont(setFont);

			/*
			 * Setting up Data style in XLSX
			 */
			CellStyle dataCellStyle = wb.createCellStyle();
			Font setDataFont = wb.createFont();
			setDataFont.setFontHeightInPoints((short) 12);
			dataCellStyle.setFont(setDataFont);

			/*
			 * Initializing Cell reference variable
			 */
			Cell cell = null;

			cell = row.createCell((short) 0);
			cell.setCellValue("Students Personality Development Marks Sheet FOR STD " + standardDiv + " " + term);
			cell.setCellStyle(dataCellStyle);

			int counter1 = 3;

			row = spreadSheet.createRow(3);

			cell = row.createCell((short) 0);
			cell.setCellValue("Sr. No.");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 1);
			cell.setCellValue("Roll NO.");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 2);
			cell.setCellValue("NAME OF STUDENT");
			cell.setCellStyle(headerCellStyle);

			for (Integer SubjectName : standardWiseSubjectDetailsList.keySet()) {

				cell = row.createCell((short) counter1);
				cell.setCellValue(standardWiseSubjectDetailsList.get(SubjectName));
				cell.setCellStyle(headerCellStyle);

				counter1++;
			}

			int srNo = 1;

			for (String FinalStudentsReportData : studentFinalExamCustomReportList) {

				if (FinalStudentsReportData.isEmpty()) {

				} else {
					int counter2 = 1;
					row = spreadSheet.createRow(currentRow++);

					cell = row.createCell((short) 0);
					cell.setCellValue(srNo);
					cell.setCellStyle(dataCellStyle);
					srNo++;

					String[] List = FinalStudentsReportData.split("\\$");

					for (int j = 0; j < List.length; j++) {

						cell = row.createCell((short) counter2);
						cell.setCellValue(List[j]);
						cell.setCellStyle(dataCellStyle);
						counter2++;

					}
				}
			}

			/*
			 * writing to the xlsx file
			 */
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wb.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();

			/*
			 * Write the output to a file
			 */
			FileOutputStream fileOut = new FileOutputStream(new File(realPath + "/" + excelFileName));
			fileOut.write(outArray);
			fileOut.flush();
			fileOut.close();

			System.out.println("Excel sheet for Students Personality Development Marks is created successfully.");

			status = "success";

			/*
			 * Closing resultSet, preparedStatement, connection objects
			 */

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	public String exportStudentAttendanceReportExcel(int userID, String term, String standard, int standardID,
			String division, int divisionID, int AcademicYearID, String realPath, String excelFileName) {

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		StuduntDAOInf daoInf = new StudentDAOImpl();

		LinkedHashMap<Integer, String> standardWiseMonthList = null;

		List<String> studentFinalReportList = null;

		List<StudentForm> studentsBasedReportList = null;

		int currentRow = 4;

		try {

			File file = new File(realPath + "/" + excelFileName);

			/*
			 * Generating XLSX Header value for Patient Information
			 */

			int counterNew = 0;

			studentsBasedReportList = daoInf.retriveStudentListByStandardAndDivision(standardID, divisionID);

			if (term.equals("-1")) {
				System.out.println("Term: " + term);
				standardWiseMonthList = daoInf.retrieveMonthListByStandard(AcademicYearID, standardID);
			} else {
				System.out.println("Term1: " + term);
				standardWiseMonthList = daoInf.retrieveMonthListByTermAndStandard(term, AcademicYearID, standardID);
			}
			studentFinalReportList = new ArrayList<String>();

			for (StudentForm studentsExamFormName : studentsBasedReportList) {

				double StudentFinalTotalMarks = 0, finalTermMarks = 0, toatlTermMarks = 0;
				int Studentattandance = 0;
				String daysPresent = "";
				String FinalList = "";
				int total = 0;

				for (Integer MonthName : standardWiseMonthList.keySet()) {

					Studentattandance = daoInf.retrieveStudentsAttendanceByMonth(studentsExamFormName.getStudentID(),
							MonthName);

					daysPresent = daysPresent + "$" + Studentattandance;

					total = total + Studentattandance;
				}

				FinalList = studentsExamFormName.getRollNumber() + "$" + studentsExamFormName.getStudentName()
						+ daysPresent + "$" + total;

				System.out.println("Final list: " + FinalList);

				studentFinalReportList.add(FinalList);
			}

			int counter = 0;

			// Workbook wb = new HSSFWorkbook();
			XSSFWorkbook wb = new XSSFWorkbook();

			XSSFSheet spreadSheet = wb.createSheet("Students Attendance Sheet");
			Row row;

			row = spreadSheet.createRow(0);

			spreadSheet.setColumnWidth((short) 0, (short) (256 * 25));

			/*
			 * Creating header in XLSX
			 */
			CellStyle headerCellStyle = wb.createCellStyle();

			/*
			 * Setting up the font
			 */
			Font setFont = wb.createFont();
			setFont.setFontHeightInPoints((short) 12);
			setFont.setBold(true);
			headerCellStyle.setFont(setFont);

			/*
			 * Setting up Data style in XLSX
			 */
			CellStyle dataCellStyle = wb.createCellStyle();
			Font setDataFont = wb.createFont();
			setDataFont.setFontHeightInPoints((short) 12);
			dataCellStyle.setFont(setDataFont);

			/*
			 * Initializing Cell reference variable
			 */
			Cell cell = null;

			if (term.equals("-1")) {
				cell = row.createCell((short) 0);
				cell.setCellValue("Students Attendance Sheet FOR STD " + standard + division + " Term All");
				cell.setCellStyle(dataCellStyle);
			} else {
				cell = row.createCell((short) 0);
				cell.setCellValue("Students Attendance Sheet FOR STD " + standard + division + " " + term);
				cell.setCellStyle(dataCellStyle);
			}

			int counter1 = 3;

			row = spreadSheet.createRow(3);

			cell = row.createCell((short) 0);
			cell.setCellValue("Sr. No.");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 1);
			cell.setCellValue("Roll NO.");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 2);
			cell.setCellValue("Name Of Student");
			cell.setCellStyle(headerCellStyle);

			for (Integer Month : standardWiseMonthList.keySet()) {

				cell = row.createCell((short) counter1);
				cell.setCellValue(standardWiseMonthList.get(Month));
				cell.setCellStyle(headerCellStyle);

				counter1++;
			}

			cell = row.createCell((short) counter1++);
			cell.setCellValue("Total");
			cell.setCellStyle(headerCellStyle);

			int srNo = 1;

			for (String FinalStudentsReportData : studentFinalReportList) {

				if (FinalStudentsReportData.isEmpty()) {

				} else {
					int counter2 = 1;
					row = spreadSheet.createRow(currentRow++);

					cell = row.createCell((short) 0);
					cell.setCellValue(srNo);
					cell.setCellStyle(dataCellStyle);
					srNo++;

					String[] List = FinalStudentsReportData.split("\\$");

					for (int j = 0; j < List.length; j++) {

						cell = row.createCell((short) counter2);
						cell.setCellValue(List[j]);
						cell.setCellStyle(dataCellStyle);
						counter2++;

					}
				}
			}

			/*
			 * writing to the xlsx file
			 */
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wb.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();

			/*
			 * Write the output to a file
			 */
			FileOutputStream fileOut = new FileOutputStream(new File(realPath + "/" + excelFileName));
			fileOut.write(outArray);
			fileOut.flush();
			fileOut.close();

			System.out.println("Excel sheet for Students yearly attendance is created successfully.");

			status = "success";

			/*
			 * Closing resultSet, preparedStatement, connection objects
			 */

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	public String generateBifurcationExamReport(StudentForm studform, String standard, int standardID, String division,
			int divisionID, String term, int organisationID, int AcademicYearID, String realPath,
			String excelFileName) {

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		StuduntDAOInf daoInf = new StudentDAOImpl();

		HashMap<Integer, String> standardWiseSubjectDetailsList = null;

		HashMap<String, Integer> NewExaminationListTermI = null;

		HashMap<String, Integer> NewExaminationListTermII = null;

		List<String> studentFinalExamCustomReportList = null;

		HashMap<String, String> StudentsSubjectFinalTotalMarksList = null;

		HashMap<String, HashMap<String, HashMap<String, Integer>>> StudentsSubjectTotalMarksList = null;

		HashMap<String, Double> StudentsFinalTotalMarksList = null;

		HashMap<String, Double> StudentsTotalMarksList = null;

		List<StudentForm> studentsBasedExamCustomReportList = null;

		List<StudentForm> ScholasticGradeList = null;

		int currentRow = 0;

		try {

			File file = new File(realPath + "/" + excelFileName);

			/*
			 * Generating XLSX Header value for Patient Information
			 */

			// retriving Academic Year ID value
			String AcademicYear = "";
			AcademicYear = daoInf1.retrieveAcademicYearName(organisationID);

			String Stage = daoInf2.getStandardStageByStandardID(standardID);

			int AYClassID = 0;

			AYClassID = daoInf2.retrieveAYCLassID(standardID, divisionID, AcademicYearID);

			int counterNew = 0;
			int count = 0;
			String Value, StudName = "";

			double SubMarks, topperMarks, ValueNew = 0D;

			studentsBasedExamCustomReportList = daoInf.retriveStudentDetailsListByStandardIDAndDivisionID(standardID,
					divisionID);

			standardWiseSubjectDetailsList = daoInf2.retrieveStandardWiseSubjectList(standardID, AYClassID);

			studentFinalExamCustomReportList = new ArrayList<String>();

			StudentsSubjectFinalTotalMarksList = new HashMap<String, String>();

			StudentsFinalTotalMarksList = new HashMap<String, Double>();

			StudentsTotalMarksList = new HashMap<String, Double>();

			StudentsSubjectTotalMarksList = new HashMap<String, HashMap<String, HashMap<String, Integer>>>();

			for (StudentForm studentsExamFormName : studentsBasedExamCustomReportList) {

				double StudentFinalTotalMarks = 0, finalTermMarks = 0, toatlTermMarks = 0;
				String Studentgrade = "";
				String FinalList = "";

				int maxCount, tempcount = 0;

				for (Integer SubjectName : standardWiseSubjectDetailsList.keySet()) {

					if (term.equals("All")) {
						NewExaminationListTermI = daoInf2.retrieveExaminationList(AcademicYearID, "Term I", AYClassID);

						NewExaminationListTermII = daoInf2.retrieveExaminationList(AcademicYearID, "Term II",
								AYClassID);
					} else {
						NewExaminationListTermI = daoInf2.retrieveExaminationList(AcademicYearID, term, AYClassID);
					}

					if (Stage.equals("Primary")) {

						double TermIIvalue, TermIIvalue1, TermIIvalue2, TermIvalue, TermIvalue1, TermIvalue2,
								toatlTermMarksVaalue = 0;

						int scaleToTermI, scaleToTermI1, scaleToTermI2, scaleToTermI3, scaleToTermI4, scaleToTermI5,
								scaleToTermII, scaleToTermII1, scaleToTermII2, scaleToTermII3, scaleToTermII4,
								scaleToTermII5, toatlScaleTo = 0;

						if (term.equals("All")) {

							boolean termEdCheck = daoInf2.verifyAbsent(SubjectName, studentsExamFormName.getStudentID(),
									AYClassID, NewExaminationListTermI.get("Term End"));

							boolean unitTestCheck = daoInf2.verifyAbsent(SubjectName,
									studentsExamFormName.getStudentID(), AYClassID,
									NewExaminationListTermI.get("Unit Test"));

							TermIvalue = daoInf.retrievrAllMarksScaledForStudent(
									NewExaminationListTermI.get("Term End"), SubjectName, standardID, AcademicYearID,
									studentsExamFormName.getStudentID());

							TermIvalue1 = daoInf.retrievrAllMarksScaledForStudent(
									NewExaminationListTermI.get("Unit Test"), SubjectName, standardID, AcademicYearID,
									studentsExamFormName.getStudentID());

							TermIvalue2 = daoInf2.retrieveScholasticGradeList1(SubjectName,
									studentsExamFormName.getStudentID(), AYClassID, "Term I");

							TermIIvalue = daoInf.retrievrAllMarksScaledForStudent(
									NewExaminationListTermII.get("Term End"), SubjectName, standardID, AcademicYearID,
									studentsExamFormName.getStudentID());

							TermIIvalue1 = daoInf.retrievrAllMarksScaledForStudent(
									NewExaminationListTermII.get("Unit Test"), SubjectName, standardID, AcademicYearID,
									studentsExamFormName.getStudentID());

							TermIIvalue2 = daoInf2.retrieveScholasticGradeList1(SubjectName,
									studentsExamFormName.getStudentID(), AYClassID, "Term II");

							finalTermMarks = (TermIvalue + TermIvalue1 + TermIvalue2 + TermIIvalue + TermIIvalue1
									+ TermIIvalue2);

							scaleToTermI = daoInf2.retrieveScaleTo(SubjectName, NewExaminationListTermI.get("Term End"),
									AYClassID);

							scaleToTermI1 = daoInf2.retrieveScaleTo(SubjectName,
									NewExaminationListTermI.get("Unit Test"), AYClassID);

							scaleToTermI2 = daoInf2.retrieveScaleToNew1(SubjectName, AYClassID, AcademicYearID,
									"Term I");

							scaleToTermII = daoInf2.retrieveScaleTo(SubjectName,
									NewExaminationListTermII.get("Term End"), AYClassID);

							scaleToTermII1 = daoInf2.retrieveScaleTo(SubjectName,
									NewExaminationListTermII.get("Unit Test"), AYClassID);

							scaleToTermII2 = daoInf2.retrieveScaleToNew1(SubjectName, AYClassID, AcademicYearID,
									"Term II");

							toatlScaleTo = (scaleToTermI + scaleToTermI1 + scaleToTermI2 + scaleToTermII
									+ scaleToTermII1 + scaleToTermII2);

							toatlTermMarks = (finalTermMarks / toatlScaleTo) * 100;

							/* Calculating Final Grades */
							if (termEdCheck) {
								Studentgrade = "ex";
							} else if (unitTestCheck) {
								Studentgrade = "ex";
							} else if (toatlTermMarks >= 91 && toatlTermMarks <= 100) {
								Studentgrade = "A1";
							} else if (toatlTermMarks >= 81 && toatlTermMarks <= 90) {
								Studentgrade = "A2";
							} else if (toatlTermMarks >= 71 && toatlTermMarks <= 80) {
								Studentgrade = "B1";
							} else if (toatlTermMarks >= 61 && toatlTermMarks <= 70) {
								Studentgrade = "B2";
							} else if (toatlTermMarks >= 51 && toatlTermMarks <= 60) {
								Studentgrade = "C1";
							} else if (toatlTermMarks >= 41 && toatlTermMarks <= 50) {
								Studentgrade = "C2";
							} else if (toatlTermMarks >= 33 && toatlTermMarks <= 40) {
								Studentgrade = "D";
							} else {

								Studentgrade = "E";
							}

							/*
							 * if (termEdCheck || unitTestCheck) { Studentgrade = "ex"; }
							 */

						} else {

							/*
							 * System.out.println("Values: " + NewExaminationListTermI.get("Term End") + "-"
							 * + SubjectName + "-" + standardID + "-" + AcademicYearID + "-" +
							 * studentsExamFormName.getStudentID());
							 */

							TermIvalue = daoInf.retrievrAllMarksScaledForStudent(
									NewExaminationListTermI.get("Term End"), SubjectName, standardID, AcademicYearID,
									studentsExamFormName.getStudentID());

							TermIvalue1 = daoInf.retrievrAllMarksScaledForStudent(
									NewExaminationListTermI.get("Unit Test"), SubjectName, standardID, AcademicYearID,
									studentsExamFormName.getStudentID());

							TermIvalue2 = daoInf2.retrieveScholasticGradeList1(SubjectName,
									studentsExamFormName.getStudentID(), AYClassID, term);

							finalTermMarks = (TermIvalue + TermIvalue1 + TermIvalue2);

							scaleToTermI = daoInf2.retrieveScaleTo(SubjectName, NewExaminationListTermI.get("Term End"),
									AYClassID);

							scaleToTermI1 = daoInf2.retrieveScaleTo(SubjectName,
									NewExaminationListTermI.get("Unit Test"), AYClassID);

							scaleToTermI2 = daoInf2.retrieveScaleToNew1(SubjectName, AYClassID, AcademicYearID, term);

							toatlScaleTo = (scaleToTermI + scaleToTermI1 + scaleToTermI2);

							/* Calculating Final Grades */

							boolean termEndCheck = daoInf2.verifyAbsent(SubjectName,
									studentsExamFormName.getStudentID(), AYClassID,
									NewExaminationListTermI.get("Term End"));

							boolean unitTestCheck = daoInf2.verifyAbsent(SubjectName,
									studentsExamFormName.getStudentID(), AYClassID,
									NewExaminationListTermI.get("Unit Test"));

							boolean SEANBCheck = daoInf2.verifySEANBAbsent(SubjectName,
									studentsExamFormName.getStudentID(), AYClassID, term, AcademicYearID);

							toatlTermMarks = (finalTermMarks / toatlScaleTo) * 50;

							if (termEndCheck) {
								Studentgrade = "ex";
							} else if (unitTestCheck) {
								Studentgrade = "ex";
							} else if (SEANBCheck) {
								Studentgrade = "ex";
							} else {

								if (toatlTermMarks >= 46 && toatlTermMarks <= 50) {
									Studentgrade = "A1";
								} else if (toatlTermMarks >= 41 && toatlTermMarks <= 45) {
									Studentgrade = "A2";
								} else if (toatlTermMarks >= 36 && toatlTermMarks <= 40) {
									Studentgrade = "B1";
								} else if (toatlTermMarks >= 31 && toatlTermMarks <= 35) {
									Studentgrade = "B2";
								} else if (toatlTermMarks >= 26 && toatlTermMarks <= 30) {
									Studentgrade = "C1";
								} else if (toatlTermMarks >= 21 && toatlTermMarks <= 25) {
									Studentgrade = "C2";
								} else if (toatlTermMarks >= 17 && toatlTermMarks <= 20) {
									Studentgrade = "D";
								} else {
									Studentgrade = "E";
								}
							}
						}
						FinalList = FinalList + "$" + Studentgrade;

					} else {

						double TermIIvalue, TermIIvalue1, TermIIvalue2, TermIIvalue3, TermIIvalue4, TermIIvalue5,
								TermIvalue, TermIvalue1, TermIvalue2, TermIvalue3, TermIvalue5, TermIvalue4,
								toatlTermMarksVaalue = 0;

						int scaleToTermI, scaleToTermI1, scaleToTermI2, scaleToTermI3, scaleToTermI4, scaleToTermI5,
								scaleToTermII, scaleToTermII1, scaleToTermII2, scaleToTermII3, scaleToTermII4,
								scaleToTermII5, toatlScaleTo = 0;

						if (term.equals("All")) {

							boolean termEdCheck = daoInf2.verifyAbsent(SubjectName, studentsExamFormName.getStudentID(),
									AYClassID, NewExaminationListTermI.get("Term End"));

							boolean unitTestCheck = daoInf2.verifyAbsent(SubjectName,
									studentsExamFormName.getStudentID(), AYClassID,
									NewExaminationListTermI.get("Unit Test"));

							TermIvalue = daoInf.retrievrAllMarksScaledForStudent(
									NewExaminationListTermI.get("Term End"), SubjectName, standardID, AcademicYearID,
									studentsExamFormName.getStudentID());

							TermIvalue1 = daoInf.retrievrAllMarksScaledForStudent(
									NewExaminationListTermI.get("Unit Test"), SubjectName, standardID, AcademicYearID,
									studentsExamFormName.getStudentID());

							TermIvalue2 = daoInf2.retrieveScholasticGradeListNew(SubjectName, "Subject Enrichment",
									studentsExamFormName.getStudentID(), AYClassID, "Term I");

							TermIvalue3 = daoInf2.retrieveScholasticGradeListNew(SubjectName, "Notebook",
									studentsExamFormName.getStudentID(), AYClassID, "Term I");

							TermIvalue4 = daoInf2.retrieveScholasticGradeListNew(SubjectName, "Portfolio",
									studentsExamFormName.getStudentID(), AYClassID, "Term I");

							TermIvalue5 = daoInf2.retrieveScholasticGradeListNew(SubjectName, "Multiple Assessment",
									studentsExamFormName.getStudentID(), AYClassID, "Term I");

							TermIIvalue = daoInf.retrievrAllMarksScaledForStudent(
									NewExaminationListTermII.get("Term End"), SubjectName, standardID, AcademicYearID,
									studentsExamFormName.getStudentID());

							TermIIvalue1 = daoInf.retrievrAllMarksScaledForStudent(
									NewExaminationListTermII.get("Unit Test"), SubjectName, standardID, AcademicYearID,
									studentsExamFormName.getStudentID());

							TermIIvalue2 = daoInf2.retrieveScholasticGradeListNew(SubjectName, "Subject Enrichment",
									studentsExamFormName.getStudentID(), AYClassID, "Term II");

							TermIIvalue3 = daoInf2.retrieveScholasticGradeListNew(SubjectName, "Notebook",
									studentsExamFormName.getStudentID(), AYClassID, "Term II");

							TermIIvalue4 = daoInf2.retrieveScholasticGradeListNew(SubjectName, "Portfolio",
									studentsExamFormName.getStudentID(), AYClassID, "Term II");

							TermIIvalue5 = daoInf2.retrieveScholasticGradeListNew(SubjectName, "Multiple Assessment",
									studentsExamFormName.getStudentID(), AYClassID, "Term II");

							finalTermMarks = (TermIvalue + TermIvalue1 + TermIvalue2 + TermIvalue3 + TermIvalue4
									+ TermIvalue5 + TermIIvalue + TermIIvalue1 + TermIIvalue2 + TermIIvalue3
									+ TermIIvalue4 + TermIIvalue5);

							scaleToTermI = daoInf2.retrieveScaleTo(SubjectName, NewExaminationListTermI.get("Term End"),
									AYClassID);

							scaleToTermI1 = daoInf2.retrieveScaleTo(SubjectName,
									NewExaminationListTermI.get("Unit Test"), AYClassID);

							scaleToTermI2 = daoInf2.retrieveScaleToNew(SubjectName, "Subject Enrichment", AYClassID,
									AcademicYearID, "Term I");

							scaleToTermI3 = daoInf2.retrieveScaleToNew(SubjectName, "Notebook", AYClassID,
									AcademicYearID, "Term I");

							scaleToTermI4 = daoInf2.retrieveScaleToNew(SubjectName, "Portfolio", AYClassID,
									AcademicYearID, "Term I");

							scaleToTermI5 = daoInf2.retrieveScaleToNew(SubjectName, "Multiple Assessment", AYClassID,
									AcademicYearID, "Term I");

							scaleToTermII = daoInf2.retrieveScaleTo(SubjectName,
									NewExaminationListTermII.get("Term End"), AYClassID);

							scaleToTermII1 = daoInf2.retrieveScaleTo(SubjectName,
									NewExaminationListTermII.get("Unit Test"), AYClassID);

							scaleToTermII2 = daoInf2.retrieveScaleToNew(SubjectName, "Subject Enrichment", AYClassID,
									AcademicYearID, "Term II");

							scaleToTermII3 = daoInf2.retrieveScaleToNew(SubjectName, "Notebook", AYClassID,
									AcademicYearID, "Term II");

							scaleToTermII4 = daoInf2.retrieveScaleToNew(SubjectName, "Portfolio", AYClassID,
									AcademicYearID, "Term II");

							scaleToTermII5 = daoInf2.retrieveScaleToNew(SubjectName, "Multiple Assessment", AYClassID,
									AcademicYearID, "Term II");

							toatlScaleTo = (scaleToTermI + scaleToTermI1 + scaleToTermI2 + scaleToTermI3 + scaleToTermI4
									+ scaleToTermI5 + scaleToTermII + scaleToTermII1 + scaleToTermII2 + scaleToTermII3
									+ scaleToTermII4 + scaleToTermII5);

							/*
							 * if (standardWiseSubjectDetailsList.get(SubjectName).equals("Hindi")) {
							 * 
							 * toatlTermMarks = finalTermMarks; } else {
							 */

							toatlTermMarks = (finalTermMarks / toatlScaleTo) * 100;
							/* } */

							String numberAsString = Double.toString(toatlTermMarks);

							String marks[] = numberAsString.split("\\.");

							if (Long.parseLong(marks[1]) > 0) {

								toatlTermMarksVaalue = Integer.parseInt(marks[0]) + 1;

							} else {

								toatlTermMarksVaalue = Integer.parseInt(marks[0]);
							}
							/* Calculating Final Grades */
							if (termEdCheck) {
								Studentgrade = "ex";
							} else if (unitTestCheck) {
								Studentgrade = "ex";
							} else if (toatlTermMarksVaalue >= 91 && toatlTermMarksVaalue <= 100) {
								Studentgrade = "A1";
							} else if (toatlTermMarksVaalue >= 81 && toatlTermMarksVaalue <= 90) {
								Studentgrade = "A2";
							} else if (toatlTermMarksVaalue >= 71 && toatlTermMarksVaalue <= 80) {
								Studentgrade = "B1";
							} else if (toatlTermMarksVaalue >= 61 && toatlTermMarksVaalue <= 70) {
								Studentgrade = "B2";
							} else if (toatlTermMarksVaalue >= 51 && toatlTermMarksVaalue <= 60) {
								Studentgrade = "C1";
							} else if (toatlTermMarksVaalue >= 41 && toatlTermMarksVaalue <= 50) {
								Studentgrade = "C2";
							} else if (toatlTermMarksVaalue >= 33 && toatlTermMarksVaalue <= 40) {
								Studentgrade = "D";
							} else {

								Studentgrade = "E";
							}

						} else {

							TermIvalue = daoInf.retrievrAllMarksScaledForStudent(
									NewExaminationListTermI.get("Term End"), SubjectName, standardID, AcademicYearID,
									studentsExamFormName.getStudentID());

							TermIvalue1 = daoInf.retrievrAllMarksScaledForStudent(
									NewExaminationListTermI.get("Unit Test"), SubjectName, standardID, AcademicYearID,
									studentsExamFormName.getStudentID());

							TermIvalue2 = daoInf2.retrieveScholasticGradeListNew(SubjectName, "Subject Enrichment",
									studentsExamFormName.getStudentID(), AYClassID, term);

							TermIvalue3 = daoInf2.retrieveScholasticGradeListNew(SubjectName, "Notebook",
									studentsExamFormName.getStudentID(), AYClassID, term);

							TermIvalue4 = daoInf2.retrieveScholasticGradeListNew(SubjectName, "Portfolio",
									studentsExamFormName.getStudentID(), AYClassID, term);

							TermIvalue5 = daoInf2.retrieveScholasticGradeListNew(SubjectName, "Multiple Assessment",
									studentsExamFormName.getStudentID(), AYClassID, term);

							finalTermMarks = (TermIvalue + TermIvalue1 + TermIvalue2 + TermIvalue3 + TermIvalue4
									+ TermIvalue5);

							scaleToTermI = daoInf2.retrieveScaleTo(SubjectName, NewExaminationListTermI.get("Term End"),
									AYClassID);

							scaleToTermI1 = daoInf2.retrieveScaleTo(SubjectName,
									NewExaminationListTermI.get("Unit Test"), AYClassID);

							scaleToTermI2 = daoInf2.retrieveScaleToNew(SubjectName, "Subject Enrichment", AYClassID,
									AcademicYearID, term);

							scaleToTermI3 = daoInf2.retrieveScaleToNew(SubjectName, "Notebook", AYClassID,
									AcademicYearID, term);

							scaleToTermI4 = daoInf2.retrieveScaleToNew(SubjectName, "Portfolio", AYClassID,
									AcademicYearID, term);

							scaleToTermI5 = daoInf2.retrieveScaleToNew(SubjectName, "Multiple Assessment", AYClassID,
									AcademicYearID, term);

							toatlScaleTo = (scaleToTermI + scaleToTermI1 + scaleToTermI2 + scaleToTermI3 + scaleToTermI4
									+ scaleToTermI5);

							/* Calculating Final Grades */
							boolean termEdCheck = daoInf2.verifyAbsent(SubjectName, studentsExamFormName.getStudentID(),
									AYClassID, NewExaminationListTermI.get("Term End"));

							boolean unitTestCheck = daoInf2.verifyAbsent(SubjectName,
									studentsExamFormName.getStudentID(), AYClassID,
									NewExaminationListTermI.get("Unit Test"));

							boolean SEANBCheck = daoInf2.verifySEANBAbsent(SubjectName,
									studentsExamFormName.getStudentID(), AYClassID, term, AcademicYearID);

							toatlTermMarks = finalTermMarks / toatlScaleTo * 100;

							if (termEdCheck) {
								Studentgrade = "ex";
							} else if (unitTestCheck) {
								Studentgrade = "ex";
							} else if (SEANBCheck) {
								Studentgrade = "ex";
							} else {

								if (toatlTermMarks >= 91 && toatlTermMarks <= 100) {
									Studentgrade = "A1";
								} else if (toatlTermMarks >= 81 && toatlTermMarks <= 90) {
									Studentgrade = "A2";
								} else if (toatlTermMarks >= 71 && toatlTermMarks <= 80) {
									Studentgrade = "B1";
								} else if (toatlTermMarks >= 61 && toatlTermMarks <= 70) {
									Studentgrade = "B2";
								} else if (toatlTermMarks >= 51 && toatlTermMarks <= 60) {
									Studentgrade = "C1";
								} else if (toatlTermMarks >= 41 && toatlTermMarks <= 50) {
									Studentgrade = "C2";
								} else if (toatlTermMarks >= 33 && toatlTermMarks <= 40) {
									Studentgrade = "D";
								} else {

									Studentgrade = "E";
								}
							}
						}

						FinalList = FinalList + "$" + Studentgrade;
					}

					if (StudentsSubjectTotalMarksList.containsKey(standardWiseSubjectDetailsList.get(SubjectName))) {

						HashMap<String, HashMap<String, Integer>> newValueMap = StudentsSubjectTotalMarksList
								.get(standardWiseSubjectDetailsList.get(SubjectName));

						if (newValueMap.containsKey(studentsExamFormName.getGender())) {

							HashMap<String, Integer> subValueMap = newValueMap.get(studentsExamFormName.getGender());

							if (subValueMap.containsKey(Studentgrade)) {

								count = subValueMap.get(Studentgrade);

								count = count + 1;

								subValueMap.put(Studentgrade, count);

							} else {
								count = 1;

								subValueMap.put(Studentgrade, count);
							}

							newValueMap.put(studentsExamFormName.getGender(), subValueMap);

							// Value = Value + "=" + studentsExamFormName.getStudentName() + "$" +
							// finalTermMarks;

							StudentsSubjectTotalMarksList.put(standardWiseSubjectDetailsList.get(SubjectName),
									newValueMap);

						} else {

							HashMap<String, Integer> subMap = new HashMap<String, Integer>();

							subMap.put("A1", 0);
							subMap.put("A2", 0);
							subMap.put("B1", 0);
							subMap.put("B2", 0);
							subMap.put("C1", 0);
							subMap.put("C2", 0);
							subMap.put("D", 0);
							subMap.put("E", 0);
							subMap.put("ex", 0);

							newValueMap.put(studentsExamFormName.getGender(), subMap);

							HashMap<String, Integer> subValueMap = newValueMap.get(studentsExamFormName.getGender());

							count = subValueMap.get(Studentgrade);

							count = count + 1;

							subValueMap.put(Studentgrade, count);

							newValueMap.put(studentsExamFormName.getGender(), subValueMap);

							String subjectName = standardWiseSubjectDetailsList.get(SubjectName);

							StudentsSubjectTotalMarksList.put(subjectName, newValueMap);

						}
					} else {

						HashMap<String, HashMap<String, Integer>> valueMap = new HashMap<String, HashMap<String, Integer>>();

						HashMap<String, Integer> subMap = new HashMap<String, Integer>();

						subMap.put("A1", 0);
						subMap.put("A2", 0);
						subMap.put("B1", 0);
						subMap.put("B2", 0);
						subMap.put("C1", 0);
						subMap.put("C2", 0);
						subMap.put("D", 0);
						subMap.put("E", 0);
						subMap.put("ex", 0);

						valueMap.put(studentsExamFormName.getGender(), subMap);

						String subjectName = standardWiseSubjectDetailsList.get(SubjectName);

						HashMap<String, Integer> subValueMap = valueMap.get(studentsExamFormName.getGender());

						count = subValueMap.get(Studentgrade);

						count = count + 1;

						subValueMap.put(Studentgrade, count);

						valueMap.put(studentsExamFormName.getGender(), subValueMap);

						StudentsSubjectTotalMarksList.put(subjectName, valueMap);
					}

				}

				FinalList = studentsExamFormName.getRollNumber() + "$" + studentsExamFormName.getGender() + "$"
						+ studentsExamFormName.getStudentName() + FinalList;

				studentFinalExamCustomReportList.add(FinalList);
			}

			int counter = 0;

			// Workbook wb = new HSSFWorkbook();
			XSSFWorkbook wb = new XSSFWorkbook();

			XSSFSheet spreadSheet = wb.createSheet("Standards Bifurcation Sheet");
			Row row;

			row = spreadSheet.createRow(0);

			spreadSheet.setColumnWidth((short) 0, (short) (256 * 25));

			/*
			 * Creating header in XLSX
			 */
			CellStyle headerCellStyle = wb.createCellStyle();

			/*
			 * Setting up the font
			 */
			Font setFont = wb.createFont();
			setFont.setFontHeightInPoints((short) 12);
			setFont.setBold(true);
			headerCellStyle.setFont(setFont);

			/*
			 * Setting up Data style in XLSX
			 */
			CellStyle dataCellStyle = wb.createCellStyle();
			Font setDataFont = wb.createFont();
			setDataFont.setFontHeightInPoints((short) 12);
			dataCellStyle.setFont(setDataFont);

			/*
			 * Initializing Cell reference variable
			 */
			Cell cell = null;

			cell = row.createCell((short) 0);
			cell.setCellValue(daoInf1.retrieveOrganizationName(organisationID));
			cell.setCellStyle(headerCellStyle);

			row = spreadSheet.createRow(1);

			cell = row.createCell((short) 0);
			cell.setCellValue(
					"Bifurcation SHEET FOR STD " + standard + division + "-" + term + "(" + AcademicYear + ")");
			cell.setCellStyle(headerCellStyle);

			counter = 3;

			int counter1 = 4;

			row = spreadSheet.createRow(counter++);

			cell = row.createCell((short) 0);
			cell.setCellValue("Sr. No.");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 1);
			cell.setCellValue("Roll NO.");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 2);
			cell.setCellValue("Gender");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 3);
			cell.setCellValue("NAME OF STUDENT");
			cell.setCellStyle(headerCellStyle);

			for (Integer SubjectName : standardWiseSubjectDetailsList.keySet()) {

				cell = row.createCell((short) counter1);
				cell.setCellValue(standardWiseSubjectDetailsList.get(SubjectName));
				cell.setCellStyle(headerCellStyle);

				counter1++;
			}

			int srNo = 1;

			for (String FinalStudentsReportData : studentFinalExamCustomReportList) {

				if (FinalStudentsReportData.isEmpty()) {

				} else {
					int counter2 = 1;
					row = spreadSheet.createRow(counter++);

					cell = row.createCell((short) 0);
					cell.setCellValue(srNo);
					cell.setCellStyle(dataCellStyle);
					srNo++;

					String[] List = FinalStudentsReportData.split("\\$");

					for (int j = 0; j < List.length; j++) {

						cell = row.createCell((short) counter2);
						cell.setCellValue(List[j]);
						cell.setCellStyle(dataCellStyle);
						counter2++;
					}
				}
			}

			counter = counter + 2;

			// System.out.println("countter..." + counter);

			int tempCounter = 0;

			// System.out.println("...tempcounter..."+tempCounter);

			counter1 = 4;

			row = spreadSheet.createRow(counter);

			cell = row.createCell((short) 2);
			cell.setCellValue("Gender");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 3);
			cell.setCellValue("Grade");
			cell.setCellStyle(headerCellStyle);

			for (String subjectName : StudentsSubjectTotalMarksList.keySet()) {
				HashMap<String, HashMap<String, Integer>> valueMap1 = StudentsSubjectTotalMarksList.get(subjectName);

				// System.out.println("valueMap1..." + valueMap1.size());

				row = spreadSheet.getRow(counter);

				cell = row.createCell((short) counter1);
				cell.setCellValue(subjectName);
				cell.setCellStyle(headerCellStyle);

				int totalGradeCount = 0;

				for (String gender : valueMap1.keySet()) {

					HashMap<String, Integer> subValueMap = valueMap1.get(gender);

					Map<String, Integer> sortedSubValueMap = new TreeMap<String, Integer>(subValueMap);

					// System.out.println("sortedSubValueMap..." + sortedSubValueMap.size());

					for (String grade : sortedSubValueMap.keySet()) {

						counter++;

						if (counter1 == 4) {

							row = spreadSheet.createRow(counter);

							cell = row.createCell((short) 2);
							cell.setCellValue(gender);
							cell.setCellStyle(dataCellStyle);

							cell = row.createCell((short) 3);
							cell.setCellValue(grade);
							cell.setCellStyle(dataCellStyle);

							cell = row.createCell((short) counter1);
							cell.setCellValue(subValueMap.get(grade));
							cell.setCellStyle(dataCellStyle);

						} else {

							row = spreadSheet.getRow(counter);

							cell = row.createCell((short) counter1);
							cell.setCellValue(subValueMap.get(grade));
							cell.setCellStyle(dataCellStyle);

						}

						tempCounter++;

						System.out.println("Subject.." + subjectName + "..Gender..." + gender + "...grade.." + grade
								+ "...count.." + subValueMap.get(grade));

						totalGradeCount = totalGradeCount + subValueMap.get(grade);

					}
				}
				int newCounter = counter + 1;

				if (counter1 == 4) {

					row = spreadSheet.createRow(newCounter++);

					cell = row.createCell((short) 3);
					cell.setCellValue("Total");
					cell.setCellStyle(headerCellStyle);

					cell = row.createCell((short) counter1);
					cell.setCellValue(totalGradeCount);
					cell.setCellStyle(headerCellStyle);

				} else {

					row = spreadSheet.getRow(newCounter++);

					cell = row.createCell((short) counter1);
					cell.setCellValue(totalGradeCount);
					cell.setCellStyle(headerCellStyle);

				}

				counter = counter - tempCounter;

				counter1++;

				tempCounter = 0;

				/*
				 * System.out.println(
				 * "----------------------------------------------------------" + counter);
				 */
			}

			/*
			 * writing to the xlsx file
			 */
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wb.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();

			/*
			 * Write the output to a file
			 */
			FileOutputStream fileOut = new FileOutputStream(new File(realPath + "/" + excelFileName));
			fileOut.write(outArray);
			fileOut.flush();
			fileOut.close();

			System.out.println("Excel sheet for Students Bifurcation Report is created successfully.");

			status = "success";

			/*
			 * Closing resultSet, preparedStatement, connection objects
			 */

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	public String exportStudentCoScholasticReportExcel(int examinationID, int standardID, int divisionID, int ayclassID,
			String realPath, String excelFileName) {

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		StuduntDAOInf daoInf = new StudentDAOImpl();

		HashMap<Integer, String> standardWiseSubjectDetailsList = null;

		HashMap<Integer, String> StandardWiseSubjectsActivityList = null;

		List<String> studentFinalCustomReportList = null;

		List<String> studentFinalActivitiesList = null;

		List<StudentForm> studentsBasedExamCustomReportList = null;

		int currentRow = 4;

		try {

			File file = new File(realPath + "/" + excelFileName);

			/*
			 * Generating XLSX Header value for Patient Information
			 */

			int counterNew = 0;

			String standardDiv = daoInf2.retrieveStandardNameDivisionName(standardID, divisionID);

			String term = daoInf.retrieveTermByExaminationID(examinationID);

			studentsBasedExamCustomReportList = daoInf.retriveStudentListByStandardAndDivision(standardID, divisionID);

			standardWiseSubjectDetailsList = daoInf.retrieveCoscholasticsubjectListByStandardID(standardID);

			studentFinalCustomReportList = new ArrayList<String>();

			studentFinalActivitiesList = new ArrayList<String>();

			for (StudentForm studentsExamFormName : studentsBasedExamCustomReportList) {

				double StudentFinalTotalMarks = 0;

				String FinalList, FinalActivityList = "";

				for (Integer SubjectNameID : standardWiseSubjectDetailsList.keySet()) {

					double activityMarks = 0;

					String toatlactivityList = "", total = "", activities = "", FinalActivity = "";

					StandardWiseSubjectsActivityList = daoInf2.retrieveActivityNameBySubjectID(SubjectNameID, ayclassID,
							examinationID);

					for (Integer activityID : StandardWiseSubjectsActivityList.keySet()) {

						activityMarks = daoInf2.retrieveactivityMarksForCoScholosticSubject(activityID, SubjectNameID,
								ayclassID, examinationID, studentsExamFormName.getStudentID());

						toatlactivityList = toatlactivityList + "$" + activityMarks;
					}

					total = daoInf.retrieveTotalMarksForCoscholasticSubject(studentsExamFormName.getStudentID(),
							SubjectNameID, ayclassID, examinationID);

					FinalActivityList = FinalActivityList + toatlactivityList + "$" + total;
				}

				System.out.println("FinalActivityList : " + FinalActivityList);

				FinalList = studentsExamFormName.getRollNumber() + "$" + studentsExamFormName.getStudentName()
						+ FinalActivityList;

				studentFinalCustomReportList.add(FinalList);
			}

			int counter = 0;

			// Workbook wb = new HSSFWorkbook();
			XSSFWorkbook wb = new XSSFWorkbook();

			XSSFSheet spreadSheet = wb.createSheet("Studentss CoScholastic Marks Sheet");
			Row row;

			row = spreadSheet.createRow(0);

			spreadSheet.setColumnWidth((short) 0, (short) (256 * 25));

			/*
			 * Creating header in XLSX
			 */
			CellStyle headerCellStyle = wb.createCellStyle();

			/*
			 * Setting up the font
			 */
			Font setFont = wb.createFont();
			setFont.setFontHeightInPoints((short) 12);
			setFont.setBold(true);
			headerCellStyle.setFont(setFont);

			/*
			 * Setting up Data style in XLSX
			 */
			CellStyle dataCellStyle = wb.createCellStyle();
			Font setDataFont = wb.createFont();
			setDataFont.setFontHeightInPoints((short) 12);
			dataCellStyle.setFont(setDataFont);

			/*
			 * Initializing Cell reference variable
			 */
			Cell cell = null;

			cell = row.createCell((short) 0);
			cell.setCellValue("Students CoScholastic Marks Sheet FOR STD " + standardDiv + " " + term);
			cell.setCellStyle(dataCellStyle);

			int counter1 = 3;

			row = spreadSheet.createRow(3);

			cell = row.createCell((short) 0);
			cell.setCellValue("Sr. No.");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 1);
			cell.setCellValue("Roll NO.");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 2);
			cell.setCellValue("NAME OF STUDENT");
			cell.setCellStyle(headerCellStyle);

			for (Integer SubjectName : standardWiseSubjectDetailsList.keySet()) {

				StandardWiseSubjectsActivityList = daoInf2.retrieveActivityNameBySubjectID(SubjectName, ayclassID,
						examinationID);

				System.out.println("SubjectNameID : " + SubjectName);

				for (Integer activityID : StandardWiseSubjectsActivityList.keySet()) {

					System.out.println("activityID : " + activityID);

					String[] activies = StandardWiseSubjectsActivityList.get(activityID).split("=");
					cell = row.createCell((short) counter1);
					cell.setCellValue(activies[0]);
					cell.setCellStyle(headerCellStyle);

					counter1++;
				}

				cell = row.createCell((short) counter1++);
				cell.setCellValue(standardWiseSubjectDetailsList.get(SubjectName) + "_Total");
				cell.setCellStyle(headerCellStyle);

				cell = row.createCell((short) counter1++);
				cell.setCellValue(standardWiseSubjectDetailsList.get(SubjectName) + "_Grade");
				cell.setCellStyle(headerCellStyle);
			}

			int srNo = 1;

			for (String FinalStudentsReportData : studentFinalCustomReportList) {

				if (FinalStudentsReportData.isEmpty()) {

				} else {
					int counter2 = 1;
					row = spreadSheet.createRow(currentRow++);

					cell = row.createCell((short) 0);
					cell.setCellValue(srNo);
					cell.setCellStyle(dataCellStyle);
					srNo++;

					String[] List = FinalStudentsReportData.split("\\$");

					for (int j = 0; j < List.length; j++) {

						cell = row.createCell((short) counter2);
						cell.setCellValue(List[j]);
						cell.setCellStyle(dataCellStyle);
						counter2++;

					}
				}
			}

			/*
			 * writing to the xlsx file
			 */
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wb.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();

			/*
			 * Write the output to a file
			 */
			FileOutputStream fileOut = new FileOutputStream(new File(realPath + "/" + excelFileName));
			fileOut.write(outArray);
			fileOut.flush();
			fileOut.close();

			System.out.println("Excel sheet for Students CoScholastic Marks is created successfully.");

			status = "success";

			/*
			 * Closing resultSet, preparedStatement, connection objects
			 */

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	public String generateStandardWiseStudentsEmailReport(HashMap<String, List<String>> emailHistoryDetails,
			String standard, String division, String realPath, String excelFileName) {

		int currentRow = 0;

		int SrNo = 1;

		List<String> Status = emailHistoryDetails.get("status");

		List<String> StudentName = emailHistoryDetails.get("studentName");

		List<String> EmailID = emailHistoryDetails.get("emailID");

		System.out.println("List size: " + Status.size() + "--" + StudentName.size() + "--" + EmailID.size());

		try {

			File file = new File(realPath + "/" + excelFileName);

			/*
			 * Generating XLSX Header value for Patient Information
			 */

			// Workbook wb = new HSSFWorkbook();
			XSSFWorkbook wb = new XSSFWorkbook();

			XSSFSheet spreadSheet = wb.createSheet("Students Marks Excel Sheet");
			Row row;

			row = spreadSheet.createRow(0);

			spreadSheet.setColumnWidth((short) 0, (short) (256 * 25));

			int counter = 1;

			/*
			 * Creating header in XLSX
			 */
			CellStyle headerCellStyle = wb.createCellStyle();

			/*
			 * Setting up the font
			 */
			Font setFont = wb.createFont();
			setFont.setFontHeightInPoints((short) 12);
			setFont.setBold(true);
			headerCellStyle.setFont(setFont);

			/*
			 * Setting up Data style in XLSX
			 */
			CellStyle dataCellStyle = wb.createCellStyle();
			Font setDataFont = wb.createFont();
			setDataFont.setFontHeightInPoints((short) 12);
			dataCellStyle.setFont(setDataFont);

			/*
			 * Initializing Cell reference variable
			 */
			Cell cell = null;

			cell = row.createCell((short) 0);
			cell.setCellValue("Student's Report card email sent history excel sheet");
			cell.setCellStyle(dataCellStyle);

			row = spreadSheet.createRow(2);

			cell = row.createCell((short) 0);
			cell.setCellValue("Standard: " + standard);
			cell.setCellStyle(dataCellStyle);

			row = spreadSheet.createRow(3);

			cell = row.createCell((short) 0);
			cell.setCellValue("Division: " + division);
			cell.setCellStyle(dataCellStyle);

			int counter1 = 1;

			row = spreadSheet.createRow(6);

			cell = row.createCell((short) 0);
			cell.setCellValue("Sr No.");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 1);
			cell.setCellValue("Student Name");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 2);
			cell.setCellValue("Parent's Email");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 3);
			cell.setCellValue("Status Message");
			cell.setCellStyle(headerCellStyle);

			currentRow = 7;

			for (int i = 0; i < Status.size(); i++) {

				row = spreadSheet.createRow(currentRow++);

				cell = row.createCell((short) 0);
				cell.setCellValue("" + SrNo);
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 1);
				cell.setCellValue(StudentName.get(i));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 2);
				cell.setCellValue(EmailID.get(i));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 3);
				cell.setCellValue(Status.get(i));
				cell.setCellStyle(dataCellStyle);

				SrNo++;
			}

			/*
			 * writing to the xlsx file
			 */
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wb.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();

			/*
			 * Write the output to a file
			 */
			FileOutputStream fileOut = new FileOutputStream(new File(realPath + "/" + excelFileName));
			fileOut.write(outArray);
			fileOut.flush();
			fileOut.close();

			System.out.println("Excel sheet of email sent record exported successfully.");

			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	public String importBookHistoryReportExcel(LibraryForm form, String realPath, File excelFileName,
			int academicYearID) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		LibraryDAOInf daoInf = new LibraryDAOImpl();

		StuduntDAOInf daoInf1 = new StudentDAOImpl();

		try {

			Workbook wb = WorkbookFactory.create(excelFileName);

			for (int i = 0; i < wb.getNumberOfSheets(); i++) {

				Sheet sheet = wb.getSheetAt(i);

				int totalRows = sheet.getPhysicalNumberOfRows();

				Map<String, Integer> map = new HashMap<String, Integer>();

				XSSFRow row = (XSSFRow) sheet.getRow(0);

				short minColIx = row.getFirstCellNum(); // get the first column index for a row
				short maxColIx = row.getLastCellNum(); // get the last column index for a row

				System.out.println();

				for (short colIx = minColIx; colIx < maxColIx; colIx++) { // loop from first to last index
					System.out.println("inside..." + i);
					XSSFCell cell = row.getCell(colIx); // get the cell
					// System.out.println("...."+cell.getStringCellValue()+"...");
					map.put(cell.getStringCellValue(), cell.getColumnIndex()); // add the cell contents (name of column)
																				// and cell index to the map
				}

				List<ReportRow> list = new ArrayList<ReportRow>();

				for (int j = 1; j < totalRows; j++) {
					ReportRow reportRow = new ReportRow();

					XSSFRow dataRow = (XSSFRow) sheet.getRow(j);

					int booknameColIndex = map.get("Book Name".trim());
					int authorColIndex = map.get("Author".trim());
					int genreColIndex = map.get("Genre".trim());
					int publicationColIndex = map.get("Publication".trim());
					int editionColIndex = map.get("Edition".trim());
					int accnoColIndex = map.get("Accession no".trim());
					int pagesColIndex = map.get("Pages");
					int descriptionColIndex = map.get("Description".trim());
					int barcodeColIndex = map.get("Barcode");
					int publicationYearColIndex = map.get("Publication Year".trim());
					int registrationDateColIndex = map.get("Registration Date".trim());
					int bookTypeColIndex = map.get("Book Type".trim());
					int sectionColIndex = map.get("Section".trim());
					int statusColIndex = map.get("Status".trim());
					int dateInactiveColIndex = map.get("Date Inactive".trim());
					int locationColIndex = map.get("Location".trim());
					int cupboardColIndex = map.get("Cupboard".trim());
					int shelfColIndex = map.get("Shelf".trim());
					int classNameColIndex = map.get("Class".trim());
					int ColNoColIndex = map.get("Col No.".trim());
					int VendorNameColIndex = map.get("Vendor Name".trim());
					int BookPriceColIndex = map.get("Price".trim());

					XSSFCell booknameCell = dataRow.getCell(booknameColIndex);
					XSSFCell authorCell = dataRow.getCell(authorColIndex);
					XSSFCell genreCell = dataRow.getCell(genreColIndex);
					XSSFCell publicationCell = dataRow.getCell(publicationColIndex);
					XSSFCell editionCell = dataRow.getCell(editionColIndex);
					XSSFCell accnoCell = dataRow.getCell(accnoColIndex);

					XSSFCell pagesCell = dataRow.getCell(pagesColIndex);
					XSSFCell descriptionCell = dataRow.getCell(descriptionColIndex);
					XSSFCell barcodeCell = dataRow.getCell(barcodeColIndex);
					XSSFCell publicationYearCell = dataRow.getCell(publicationYearColIndex);
					XSSFCell registrationDateCell = dataRow.getCell(registrationDateColIndex);
					XSSFCell bookTypeCell = dataRow.getCell(bookTypeColIndex);
					XSSFCell sectionCell = dataRow.getCell(sectionColIndex);
					XSSFCell statusCell = dataRow.getCell(statusColIndex);
					XSSFCell dateInactiveCell = dataRow.getCell(dateInactiveColIndex);
					XSSFCell locationCell = dataRow.getCell(locationColIndex);
					XSSFCell cupboardCell = dataRow.getCell(cupboardColIndex);
					XSSFCell shelfCell = dataRow.getCell(shelfColIndex);
					XSSFCell classNameCell = dataRow.getCell(classNameColIndex);
					XSSFCell ColNoCell = dataRow.getCell(ColNoColIndex);
					XSSFCell VendorNameCell = dataRow.getCell(VendorNameColIndex);
					XSSFCell BookPriceCell = dataRow.getCell(BookPriceColIndex);

					reportRow.setBookName("" + booknameCell);
					reportRow.setAuthor("" + authorCell);
					reportRow.setGenre("" + genreCell);
					reportRow.setPublication("" + publicationCell);
					reportRow.setEdition("" + editionCell);
					reportRow.setAccNum("" + accnoCell);

					reportRow.setPages("" + pagesCell);

					if (descriptionCell == null) {
						reportRow.setDescription(null);
					} else {
						reportRow.setDescription("" + descriptionCell);
					}

					reportRow.setBarcode("" + barcodeCell);
					reportRow.setPublicationYear("" + publicationYearCell);

					reportRow.setRegDate("" + registrationDateCell);
					reportRow.setType("" + bookTypeCell);
					reportRow.setSection("" + sectionCell);
					reportRow.setStatus("" + statusCell);

					if (dateInactiveCell == null) {
						reportRow.setDateInactive(null);
					} else {
						reportRow.setDateInactive("" + dateInactiveCell);
					}

					reportRow.setLocation("" + locationCell);
					reportRow.setCupboard("" + cupboardCell);
					reportRow.setShelf("" + shelfCell);
					reportRow.setClassName("" + classNameCell);
					reportRow.setColNo("" + ColNoCell);
					reportRow.setVendorName("" + VendorNameCell);

					reportRow.setPrice("" + BookPriceCell);

					list.add(reportRow);
				}

				for (ReportRow reportRow : list) {

					int ShelfID = 0;

					int vendorID = 0;
					if (reportRow.getShelf() != null || reportRow.getShelf() != "" && reportRow.getCupboard() != null
							|| reportRow.getCupboard() != "") {
						ShelfID = daoInf.retrieveShelfID(reportRow.getShelf(), reportRow.getCupboard());
					}

					/*
					 * String ClassValue = reportRow.getClassName();
					 */
					if (reportRow.getVendorName() == null || reportRow.getVendorName() == "") {
						vendorID = -1;

					} else if (reportRow.getVendorName().isEmpty()) {
						vendorID = -1;

					} else {
						vendorID = daoInf.retrieveVendorsID(reportRow.getVendorName());
					}

					statusMessage = daoInf.importBookDetails(reportRow, ShelfID, vendorID, form.getAcademicYearID(),
							form.getLibraryID());

					if (statusMessage.equalsIgnoreCase("success")) {

						System.out.println("Successfully imported Book details into Books table");

						int bookID = daoInf.retrieveBookIDNew();

						Date date = new java.util.Date();
						String NewDate = dateFormat.format(date);

						statusMessage = daoInf.updateBookStatusInStatusHistory(reportRow.getStatus(), NewDate, bookID);
						if (statusMessage.equalsIgnoreCase("success")) {

							System.out.println("Successfully inserted Book Status into StatusHistory table");
							statusMessage = "success";
						} else {

							System.out.println("Failed to import Book Status into StatusHistory Table");
							statusMessage = "error";
						}

					} else {
						System.out.println("Failed to import Book Details into Books Table");
						statusMessage = "error";
					}
				}
			}
			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Invalid file format. Please upload a valid file");
		}

		return status;
	}

	public String exportInventoryReportExcel(String statusVal, String section, String schoolSection, int academicYearID,
			int libraryID, String realPath, String excelFileName) {

		int currentRow = 3;

		try {

			File file = new File(realPath + excelFileName);

			/*
			 * Generating XLSX Header value for Patient Information
			 */

			// Workbook wb = new HSSFWorkbook();
			XSSFWorkbook wb = new XSSFWorkbook();

			XSSFSheet spreadSheet = wb.createSheet("Inventory Books Report");

			Row row1;

			row1 = spreadSheet.createRow(0);

			Row row2;

			row2 = spreadSheet.createRow(1);

			Row row;

			row = spreadSheet.createRow(2);

			spreadSheet.setColumnWidth((short) 0, (short) (256 * 25));

			int counter = 1;

			/*
			 * Creating header in XLSX
			 */
			CellStyle headerCellStyle = wb.createCellStyle();

			/*
			 * Setting up the font
			 */
			Font setFont = wb.createFont();
			setFont.setFontHeightInPoints((short) 12);
			setFont.setBold(true);
			headerCellStyle.setFont(setFont);

			/*
			 * Setting up Data style in XLSX
			 */
			CellStyle dataCellStyle = wb.createCellStyle();
			Font setDataFont = wb.createFont();
			setDataFont.setFontHeightInPoints((short) 12);
			dataCellStyle.setFont(setDataFont);

			/*
			 * Initializing Cell reference variable
			 */

			Cell cell = null;

			cell = row1.createCell((short) 0);
			cell.setCellValue("Section : " + section);
			cell.setCellStyle(headerCellStyle);

			cell = row2.createCell((short) 0);
			cell.setCellValue("Status : " + statusVal);
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 0);
			cell.setCellValue("Book Name.");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 1);
			cell.setCellValue("Author");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 0);
			cell.setCellValue("Book Name.");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 1);
			cell.setCellValue("Author");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 2);
			cell.setCellValue("Genre");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 3);
			cell.setCellValue("Publication");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 4);
			cell.setCellValue("Edition");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 5);
			cell.setCellValue("Accession No.");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 6);
			cell.setCellValue("Pages");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 7);
			cell.setCellValue("Description");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 8);
			cell.setCellValue("Barcode");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 9);
			cell.setCellValue("Publication Year");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 10);
			cell.setCellValue("Registration Date");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 11);
			cell.setCellValue("Status");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 12);
			cell.setCellValue("Section");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 13);
			cell.setCellValue("Date Inactive");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 14);
			cell.setCellValue("Type");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 15);
			cell.setCellValue("Cupboard");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 16);
			cell.setCellValue("Shelf");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 17);
			cell.setCellValue("Call No.");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 18);
			cell.setCellValue("Vendor Name");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 19);
			cell.setCellValue("Book Price");
			cell.setCellStyle(headerCellStyle);

			connection = getConnection();

			// if (academicYearID == 0) {
			System.out.println("inside....." + academicYearID);
			if (section.equals("all")) {

				if (statusVal.equals("all")) {

					String retrieveAllBooksInventoryReportQuery = QueryMaker.RETRIEVE_Books_Inventory_Report;
					preparedStatement = connection.prepareStatement(retrieveAllBooksInventoryReportQuery);

					preparedStatement.setInt(1, libraryID);

				} else {

					String retrieveStatusWiseBooksInventoryReportQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_Status;
					preparedStatement = connection.prepareStatement(retrieveStatusWiseBooksInventoryReportQuery);

					preparedStatement.setString(1, statusVal);
					preparedStatement.setInt(2, libraryID);
				}

			} else {

				if (statusVal.equals("all")) {

					if (section.equals("schoolSections")) {

						if (schoolSection.equals("primary")) {
							String searchBookByAuthorNameQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_Primary_School_Section;

							preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);

							preparedStatement.setInt(1, libraryID);

						} else if (schoolSection.equals("CD_primary")) {
							String searchBookByAuthorNameQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_CD_Primary_School_Section;

							preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);

							preparedStatement.setInt(1, libraryID);

						} else if (schoolSection.equals("CD_secondary")) {
							String searchBookByAuthorNameQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_CD_Secondary_School_Section;

							preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);

							preparedStatement.setInt(1, libraryID);

						} else {
							String searchBookByAuthorNameQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_School_Section;

							preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);

							preparedStatement.setString(1, schoolSection + "%");
							preparedStatement.setInt(2, libraryID);
						}
					} else {

						String retrieveBooksInventoryReportBystatusQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_Section;

						preparedStatement = connection.prepareStatement(retrieveBooksInventoryReportBystatusQuery);

						preparedStatement.setString(1, section);
						preparedStatement.setInt(2, libraryID);
					}
				} else {

					if (section.equals("schoolSections")) {

						if (schoolSection.equals("primary")) {
							String searchBookByAuthorNameQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_Primary_School_Section1;

							preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);

							preparedStatement.setString(1, statusVal);
							preparedStatement.setInt(2, libraryID);

						} else if (schoolSection.equals("CD_primary")) {
							String searchBookByAuthorNameQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_CD_Primary_School_Section1;

							preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);

							preparedStatement.setString(1, statusVal);
							preparedStatement.setInt(2, libraryID);

						} else if (schoolSection.equals("CD_secondary")) {
							String searchBookByAuthorNameQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_CD_Secondary_School_Section1;

							preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);

							preparedStatement.setString(1, statusVal);
							preparedStatement.setInt(2, libraryID);

						} else {
							String searchBookByAuthorNameQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_School_Section1;

							preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);

							preparedStatement.setString(1, schoolSection + "%");
							;
							preparedStatement.setString(2, statusVal);
							preparedStatement.setInt(3, libraryID);

						}
					} else {

						String retrieveStatusWiseBooksInventoryReportBystatusQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_Status_AND_Section;

						preparedStatement = connection
								.prepareStatement(retrieveStatusWiseBooksInventoryReportBystatusQuery);

						preparedStatement.setString(1, section);
						preparedStatement.setString(2, statusVal);
						preparedStatement.setInt(3, libraryID);
					}
				}
			}
//			} else {
//				System.out.println("inside else....." + academicYearID);
//				if (section.equals("all")) {
//
//					if (statusVal.equals("all")) {
//						System.out.println("inside eles if....." + academicYearID);
//						String retrieveAllBooksInventoryReportQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_AcademicYearID;
//						preparedStatement = connection.prepareStatement(retrieveAllBooksInventoryReportQuery);
//
//						preparedStatement.setInt(1, academicYearID);
//						preparedStatement.setInt(2, libraryID);
//
//					} else {
//						System.out
//								.println("inside eles else....." + academicYearID + "-" + statusVal + "-" + libraryID);
//						String retrieveStatusWiseBooksInventoryReportQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_Status_BY_AcademicYearID;
//						preparedStatement = connection.prepareStatement(retrieveStatusWiseBooksInventoryReportQuery);
//						preparedStatement.setString(1, statusVal);
//						preparedStatement.setInt(2, academicYearID);
//						preparedStatement.setInt(3, libraryID);
//
//					}
//
//				} else {
//
//					if (statusVal.equals("all")) {
//
//						if (section.equals("schoolSections")) {
//
//							if (schoolSection.equals("primary")) {
//								String searchBookByAuthorNameQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_Primary_School_Section_BY_AcademicYearID;
//
//								preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);
//
//								preparedStatement.setInt(1, academicYearID);
//								preparedStatement.setInt(2, libraryID);
//
//							} else if (schoolSection.equals("CD_primary")) {
//								String searchBookByAuthorNameQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_CD_Primary_School_Section_BY_AcademicYearID;
//
//								preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);
//
//								preparedStatement.setInt(1, academicYearID);
//								preparedStatement.setInt(2, libraryID);
//
//							} else if (schoolSection.equals("CD_secondary")) {
//								String searchBookByAuthorNameQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_CD_Secondary_School_Section_BY_AcademicYearID;
//
//								preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);
//
//								preparedStatement.setInt(1, academicYearID);
//								preparedStatement.setInt(2, libraryID);
//
//							} else {
//								String searchBookByAuthorNameQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_School_Section_AND_AcademicYearID;
//
//								preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);
//
//								preparedStatement.setString(1, schoolSection + "%");
//								;
//								preparedStatement.setInt(2, academicYearID);
//								preparedStatement.setInt(3, libraryID);
//							}
//						} else {
//
//							String retrieveBooksInventoryReportBystatusQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_Section_AcademicYearID;
//
//							preparedStatement = connection.prepareStatement(retrieveBooksInventoryReportBystatusQuery);
//
//							preparedStatement.setString(1, section);
//							preparedStatement.setInt(2, academicYearID);
//							preparedStatement.setInt(3, libraryID);
//
//						}
//					} else {
//
//						if (section.equals("schoolSections")) {
//
//							if (schoolSection.equals("primary")) {
//								String searchBookByAuthorNameQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_Primary_School_Section_AcademicYearID;
//
//								preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);
//								preparedStatement.setString(1, statusVal);
//								preparedStatement.setInt(2, academicYearID);
//								preparedStatement.setInt(3, libraryID);
//
//							} else if (schoolSection.equals("CD_primary")) {
//								String searchBookByAuthorNameQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_CD_Primary_School_Section_AcademicYearID;
//
//								preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);
//
//								preparedStatement.setString(1, statusVal);
//								preparedStatement.setInt(2, academicYearID);
//								preparedStatement.setInt(3, libraryID);
//
//							} else if (schoolSection.equals("CD_secondary")) {
//								String searchBookByAuthorNameQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_CD_Secondary_School_Section_AcademicYearID;
//
//								preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);
//
//								preparedStatement.setString(1, statusVal);
//								preparedStatement.setInt(2, academicYearID);
//								preparedStatement.setInt(3, libraryID);
//
//							} else {
//								String searchBookByAuthorNameQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_School_Section_AcademicYearID;
//
//								preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);
//
//								preparedStatement.setString(1, schoolSection + "%");
//								;
//								preparedStatement.setString(2, statusVal);
//								preparedStatement.setInt(3, academicYearID);
//								preparedStatement.setInt(4, libraryID);
//							}
//						} else {
//
//							String retrieveStatusWiseBooksInventoryReportBystatusQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_Status_AND_Section1;
//
//							preparedStatement = connection
//									.prepareStatement(retrieveStatusWiseBooksInventoryReportBystatusQuery);
//
//							preparedStatement.setString(1, section);
//							preparedStatement.setString(2, statusVal);
//							preparedStatement.setInt(3, academicYearID);
//							preparedStatement.setInt(4, libraryID);
//						}
//					}
//				}
//			}

			resultSet = preparedStatement.executeQuery();

			ResultSetMetaData metaData = resultSet.getMetaData();

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

			while (resultSet.next()) {

				row = spreadSheet.createRow(currentRow++);

				cell = row.createCell((short) 0);
				cell.setCellValue(resultSet.getString("name"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 1);
				cell.setCellValue(resultSet.getString("author"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 2);
				cell.setCellValue(resultSet.getString("genre"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 3);
				cell.setCellValue(resultSet.getString("publication"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 4);
				cell.setCellValue(resultSet.getString("edition"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 5);
				cell.setCellValue(resultSet.getString("accNum"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 6);
				cell.setCellValue(resultSet.getString("pages"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 7);
				cell.setCellValue(resultSet.getString("description"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 8);
				cell.setCellValue(resultSet.getString("barcode"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 9);
				cell.setCellValue(resultSet.getString("publicationYear"));
				cell.setCellStyle(dataCellStyle);

				if (resultSet.getString("regDate") == null || resultSet.getString("regDate") == "") {

					cell = row.createCell((short) 10);
					cell.setCellValue("");
					cell.setCellStyle(dataCellStyle);
				} else if (resultSet.getString("regDate").isEmpty()) {

					cell = row.createCell((short) 10);
					cell.setCellValue("");
					cell.setCellStyle(dataCellStyle);
				} else {

					cell = row.createCell((short) 10);
					cell.setCellValue(dateFormat.format(dateFormat1.parse(resultSet.getString("regDate"))));
					cell.setCellStyle(dataCellStyle);
				}

				cell = row.createCell((short) 11);
				cell.setCellValue(resultSet.getString("status"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 12);
				cell.setCellValue(resultSet.getString("section"));
				cell.setCellStyle(dataCellStyle);

				if (resultSet.getString("dateInactive") == null || resultSet.getString("dateInactive") == "") {

					cell = row.createCell((short) 13);
					cell.setCellValue("");
					cell.setCellStyle(dataCellStyle);
				} else if (resultSet.getString("dateInactive").isEmpty()) {

					cell = row.createCell((short) 13);
					cell.setCellValue("");
					cell.setCellStyle(dataCellStyle);
				} else {

					cell = row.createCell((short) 13);
					cell.setCellValue(dateFormat.format(dateFormat1.parse(resultSet.getString("dateInactive"))));
					cell.setCellStyle(dataCellStyle);
				}

				cell = row.createCell((short) 14);
				cell.setCellValue("" + resultSet.getString("type"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 15);
				cell.setCellValue(resultSet.getString("cupboard"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 16);
				cell.setCellValue(resultSet.getString("shelf"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 17);
				cell.setCellValue(resultSet.getString("colNo"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 18);
				cell.setCellValue(resultSet.getString("vendor"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 19);
				cell.setCellValue(resultSet.getDouble("price"));
				cell.setCellStyle(dataCellStyle);

			}
			/*
			 * writing to the xlsx file
			 */
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wb.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();

			/*
			 * Write the output to a file
			 */
			FileOutputStream fileOut = new FileOutputStream(new File(realPath + excelFileName));
			fileOut.write(outArray);
			fileOut.flush();
			fileOut.close();

			System.out.println("Excel sheet for Inventory Report of Books exported successfully.");

			status = "success";

			/*
			 * Closing resultSet, preparedStatement, connection objects
			 */

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	public String exportInventoryReportExcelForPrint(String statusVal, String section, String schoolSection,
			int academicYearID, int libraryID, String realPath, String excelFileName) {

		int currentRow = 3;

		try {

			File file = new File(realPath + excelFileName);

			/*
			 * Generating XLSX Header value for Patient Information
			 */

			// Workbook wb = new HSSFWorkbook();
			XSSFWorkbook wb = new XSSFWorkbook();

			XSSFSheet spreadSheet = wb.createSheet("Inventory Books Report");

			Row row1;

			row1 = spreadSheet.createRow(0);

			Row row2;

			row2 = spreadSheet.createRow(1);

			Row row;

			row = spreadSheet.createRow(2);

			spreadSheet.setColumnWidth((short) 0, (short) (256 * 25));

			int counter = 1;

			/*
			 * Creating header in XLSX
			 */
			CellStyle headerCellStyle = wb.createCellStyle();

			/*
			 * Setting up the font
			 */
			Font setFont = wb.createFont();
			setFont.setFontHeightInPoints((short) 12);
			setFont.setBold(true);
			headerCellStyle.setFont(setFont);

			/*
			 * Setting up Data style in XLSX
			 */
			CellStyle dataCellStyle = wb.createCellStyle();
			Font setDataFont = wb.createFont();
			setDataFont.setFontHeightInPoints((short) 12);
			dataCellStyle.setFont(setDataFont);

			/*
			 * Initializing Cell reference variable
			 */

			Cell cell = null;

			cell = row1.createCell((short) 0);
			cell.setCellValue("Section : " + section);
			cell.setCellStyle(headerCellStyle);

			cell = row2.createCell((short) 0);
			cell.setCellValue("Status : " + statusVal);
			cell.setCellStyle(headerCellStyle);

			/*
			 * cell = row.createCell((short) 0); cell.setCellValue("Book Name.");
			 * cell.setCellStyle(headerCellStyle);
			 * 
			 * cell = row.createCell((short) 1); cell.setCellValue("Author");
			 * cell.setCellStyle(headerCellStyle);
			 */

			cell = row.createCell((short) 0);
			cell.setCellValue("Accession no.");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 1);
			cell.setCellValue("Call no.");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 2);
			cell.setCellValue("Title");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 3);
			cell.setCellValue("Author");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 4);
			cell.setCellValue("Publisher");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 5);
			cell.setCellValue("Vendor");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 6);
			cell.setCellValue("Price");
			cell.setCellStyle(headerCellStyle);

			connection = getConnection();

			// if (academicYearID == 0) {
			System.out.println("inside....." + academicYearID);
			if (section.equals("all")) {

				if (statusVal.equals("all")) {

					String retrieveAllBooksInventoryReportQuery = QueryMaker.RETRIEVE_Books_Inventory_Report;
					preparedStatement = connection.prepareStatement(retrieveAllBooksInventoryReportQuery);

					preparedStatement.setInt(1, libraryID);

				} else {

					String retrieveStatusWiseBooksInventoryReportQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_Status;
					preparedStatement = connection.prepareStatement(retrieveStatusWiseBooksInventoryReportQuery);

					preparedStatement.setString(1, statusVal);
					preparedStatement.setInt(2, libraryID);
				}

			} else {

				if (statusVal.equals("all")) {

					if (section.equals("schoolSections")) {

						if (schoolSection.equals("primary")) {
							String searchBookByAuthorNameQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_Primary_School_Section;

							preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);

							preparedStatement.setInt(1, libraryID);

						} else if (schoolSection.equals("CD_primary")) {
							String searchBookByAuthorNameQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_CD_Primary_School_Section;

							preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);

							preparedStatement.setInt(1, libraryID);

						} else if (schoolSection.equals("CD_secondary")) {
							String searchBookByAuthorNameQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_CD_Secondary_School_Section;

							preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);

							preparedStatement.setInt(1, libraryID);

						} else {
							String searchBookByAuthorNameQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_School_Section;

							preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);

							preparedStatement.setString(1, schoolSection + "%");
							preparedStatement.setInt(2, libraryID);
						}
					} else {

						String retrieveBooksInventoryReportBystatusQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_Section;

						preparedStatement = connection.prepareStatement(retrieveBooksInventoryReportBystatusQuery);

						preparedStatement.setString(1, section);
						preparedStatement.setInt(2, libraryID);
					}
				} else {

					if (section.equals("schoolSections")) {

						if (schoolSection.equals("primary")) {
							String searchBookByAuthorNameQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_Primary_School_Section1;

							preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);

							preparedStatement.setString(1, statusVal);
							preparedStatement.setInt(2, libraryID);

						} else if (schoolSection.equals("CD_primary")) {
							String searchBookByAuthorNameQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_CD_Primary_School_Section1;

							preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);

							preparedStatement.setString(1, statusVal);
							preparedStatement.setInt(2, libraryID);

						} else if (schoolSection.equals("CD_secondary")) {
							String searchBookByAuthorNameQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_CD_Secondary_School_Section1;

							preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);

							preparedStatement.setString(1, statusVal);
							preparedStatement.setInt(2, libraryID);

						} else {
							String searchBookByAuthorNameQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_School_Section1;

							preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);

							preparedStatement.setString(1, schoolSection + "%");
							;
							preparedStatement.setString(2, statusVal);
							preparedStatement.setInt(3, libraryID);

						}
					} else {

						String retrieveStatusWiseBooksInventoryReportBystatusQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_Status_AND_Section;

						preparedStatement = connection
								.prepareStatement(retrieveStatusWiseBooksInventoryReportBystatusQuery);

						preparedStatement.setString(1, section);
						preparedStatement.setString(2, statusVal);
						preparedStatement.setInt(3, libraryID);
					}
				}
			}
//			} else {
//				System.out.println("inside else....." + academicYearID);
//				if (section.equals("all")) {
//
//					if (statusVal.equals("all")) {
//						System.out.println("inside eles if....." + academicYearID);
//						String retrieveAllBooksInventoryReportQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_AcademicYearID;
//						preparedStatement = connection.prepareStatement(retrieveAllBooksInventoryReportQuery);
//
//						preparedStatement.setInt(1, academicYearID);
//						preparedStatement.setInt(2, libraryID);
//
//					} else {
//						System.out
//								.println("inside eles else....." + academicYearID + "-" + statusVal + "-" + libraryID);
//						String retrieveStatusWiseBooksInventoryReportQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_Status_BY_AcademicYearID;
//						preparedStatement = connection.prepareStatement(retrieveStatusWiseBooksInventoryReportQuery);
//						preparedStatement.setString(1, statusVal);
//						preparedStatement.setInt(2, academicYearID);
//						preparedStatement.setInt(3, libraryID);
//
//					}
//
//				} else {
//
//					if (statusVal.equals("all")) {
//
//						if (section.equals("schoolSections")) {
//
//							if (schoolSection.equals("primary")) {
//								String searchBookByAuthorNameQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_Primary_School_Section_BY_AcademicYearID;
//
//								preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);
//
//								preparedStatement.setInt(1, academicYearID);
//								preparedStatement.setInt(2, libraryID);
//
//							} else if (schoolSection.equals("CD_primary")) {
//								String searchBookByAuthorNameQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_CD_Primary_School_Section_BY_AcademicYearID;
//
//								preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);
//
//								preparedStatement.setInt(1, academicYearID);
//								preparedStatement.setInt(2, libraryID);
//
//							} else if (schoolSection.equals("CD_secondary")) {
//								String searchBookByAuthorNameQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_CD_Secondary_School_Section_BY_AcademicYearID;
//
//								preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);
//
//								preparedStatement.setInt(1, academicYearID);
//								preparedStatement.setInt(2, libraryID);
//
//							} else {
//								String searchBookByAuthorNameQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_School_Section_AND_AcademicYearID;
//
//								preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);
//
//								preparedStatement.setString(1, schoolSection + "%");
//								;
//								preparedStatement.setInt(2, academicYearID);
//								preparedStatement.setInt(3, libraryID);
//							}
//						} else {
//
//							String retrieveBooksInventoryReportBystatusQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_Section_AcademicYearID;
//
//							preparedStatement = connection.prepareStatement(retrieveBooksInventoryReportBystatusQuery);
//
//							preparedStatement.setString(1, section);
//							preparedStatement.setInt(2, academicYearID);
//							preparedStatement.setInt(3, libraryID);
//
//						}
//					} else {
//
//						if (section.equals("schoolSections")) {
//
//							if (schoolSection.equals("primary")) {
//								String searchBookByAuthorNameQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_Primary_School_Section_AcademicYearID;
//
//								preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);
//								preparedStatement.setString(1, statusVal);
//								preparedStatement.setInt(2, academicYearID);
//								preparedStatement.setInt(3, libraryID);
//
//							} else if (schoolSection.equals("CD_primary")) {
//								String searchBookByAuthorNameQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_CD_Primary_School_Section_AcademicYearID;
//
//								preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);
//
//								preparedStatement.setString(1, statusVal);
//								preparedStatement.setInt(2, academicYearID);
//								preparedStatement.setInt(3, libraryID);
//
//							} else if (schoolSection.equals("CD_secondary")) {
//								String searchBookByAuthorNameQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_CD_Secondary_School_Section_AcademicYearID;
//
//								preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);
//
//								preparedStatement.setString(1, statusVal);
//								preparedStatement.setInt(2, academicYearID);
//								preparedStatement.setInt(3, libraryID);
//
//							} else {
//								String searchBookByAuthorNameQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_School_Section_AcademicYearID;
//
//								preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);
//
//								preparedStatement.setString(1, schoolSection + "%");
//								;
//								preparedStatement.setString(2, statusVal);
//								preparedStatement.setInt(3, academicYearID);
//								preparedStatement.setInt(4, libraryID);
//							}
//						} else {
//
//							String retrieveStatusWiseBooksInventoryReportBystatusQuery = QueryMaker.RETRIEVE_Books_Inventory_Report_By_Status_AND_Section1;
//
//							preparedStatement = connection
//									.prepareStatement(retrieveStatusWiseBooksInventoryReportBystatusQuery);
//
//							preparedStatement.setString(1, section);
//							preparedStatement.setString(2, statusVal);
//							preparedStatement.setInt(3, academicYearID);
//							preparedStatement.setInt(4, libraryID);
//						}
//					}
//				}
//			}

			resultSet = preparedStatement.executeQuery();

			ResultSetMetaData metaData = resultSet.getMetaData();

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

			while (resultSet.next()) {

				row = spreadSheet.createRow(currentRow++);

				cell = row.createCell((short) 0);
				cell.setCellValue(resultSet.getString("accNum"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 1);
				cell.setCellValue(resultSet.getString("colNo"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 2);
				cell.setCellValue(resultSet.getString("name"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 3);
				cell.setCellValue(resultSet.getString("author"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 4);
				cell.setCellValue(resultSet.getString("publication"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 5);
				cell.setCellValue(resultSet.getString("vendor"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 6);
				cell.setCellValue(resultSet.getDouble("price"));
				cell.setCellStyle(dataCellStyle);

				/*
				 * cell = row.createCell((short) 0);
				 * cell.setCellValue(resultSet.getString("name"));
				 * cell.setCellStyle(dataCellStyle);
				 * 
				 * cell = row.createCell((short) 1);
				 * cell.setCellValue(resultSet.getString("author"));
				 * cell.setCellStyle(dataCellStyle);
				 * 
				 * cell = row.createCell((short) 2);
				 * cell.setCellValue(resultSet.getString("genre"));
				 * cell.setCellStyle(dataCellStyle);
				 * 
				 * cell = row.createCell((short) 3);
				 * cell.setCellValue(resultSet.getString("publication"));
				 * cell.setCellStyle(dataCellStyle);
				 * 
				 * cell = row.createCell((short) 4);
				 * cell.setCellValue(resultSet.getString("edition"));
				 * cell.setCellStyle(dataCellStyle);
				 * 
				 * cell = row.createCell((short) 5);
				 * cell.setCellValue(resultSet.getString("accNum"));
				 * cell.setCellStyle(dataCellStyle);
				 * 
				 * cell = row.createCell((short) 6);
				 * cell.setCellValue(resultSet.getString("pages"));
				 * cell.setCellStyle(dataCellStyle);
				 * 
				 * cell = row.createCell((short) 7);
				 * cell.setCellValue(resultSet.getString("description"));
				 * cell.setCellStyle(dataCellStyle);
				 * 
				 * cell = row.createCell((short) 8);
				 * cell.setCellValue(resultSet.getString("barcode"));
				 * cell.setCellStyle(dataCellStyle);
				 * 
				 * cell = row.createCell((short) 9);
				 * cell.setCellValue(resultSet.getString("publicationYear"));
				 * cell.setCellStyle(dataCellStyle);
				 * 
				 * if (resultSet.getString("regDate") == null || resultSet.getString("regDate")
				 * == "") {
				 * 
				 * cell = row.createCell((short) 10); cell.setCellValue("");
				 * cell.setCellStyle(dataCellStyle); } else if
				 * (resultSet.getString("regDate").isEmpty()) {
				 * 
				 * cell = row.createCell((short) 10); cell.setCellValue("");
				 * cell.setCellStyle(dataCellStyle); } else {
				 * 
				 * cell = row.createCell((short) 10);
				 * cell.setCellValue(dateFormat.format(dateFormat1.parse(resultSet.getString(
				 * "regDate")))); cell.setCellStyle(dataCellStyle); }
				 * 
				 * cell = row.createCell((short) 11);
				 * cell.setCellValue(resultSet.getString("status"));
				 * cell.setCellStyle(dataCellStyle);
				 * 
				 * cell = row.createCell((short) 12);
				 * cell.setCellValue(resultSet.getString("section"));
				 * cell.setCellStyle(dataCellStyle);
				 * 
				 * if (resultSet.getString("dateInactive") == null ||
				 * resultSet.getString("dateInactive") == "") {
				 * 
				 * cell = row.createCell((short) 13); cell.setCellValue("");
				 * cell.setCellStyle(dataCellStyle); } else if
				 * (resultSet.getString("dateInactive").isEmpty()) {
				 * 
				 * cell = row.createCell((short) 13); cell.setCellValue("");
				 * cell.setCellStyle(dataCellStyle); } else {
				 * 
				 * cell = row.createCell((short) 13);
				 * cell.setCellValue(dateFormat.format(dateFormat1.parse(resultSet.getString(
				 * "dateInactive")))); cell.setCellStyle(dataCellStyle); }
				 * 
				 * cell = row.createCell((short) 14); cell.setCellValue("" +
				 * resultSet.getString("type")); cell.setCellStyle(dataCellStyle);
				 * 
				 * cell = row.createCell((short) 15);
				 * cell.setCellValue(resultSet.getString("cupboard"));
				 * cell.setCellStyle(dataCellStyle);
				 * 
				 * cell = row.createCell((short) 16);
				 * cell.setCellValue(resultSet.getString("shelf"));
				 * cell.setCellStyle(dataCellStyle);
				 * 
				 * cell = row.createCell((short) 17);
				 * cell.setCellValue(resultSet.getString("colNo"));
				 * cell.setCellStyle(dataCellStyle);
				 * 
				 * cell = row.createCell((short) 18);
				 * cell.setCellValue(resultSet.getString("vendor"));
				 * cell.setCellStyle(dataCellStyle);
				 * 
				 * cell = row.createCell((short) 19);
				 * cell.setCellValue(resultSet.getDouble("price"));
				 * cell.setCellStyle(dataCellStyle);
				 */

			}
			/*
			 * writing to the xlsx file
			 */
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wb.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();

			/*
			 * Write the output to a file
			 */
			FileOutputStream fileOut = new FileOutputStream(new File(realPath + excelFileName));
			fileOut.write(outArray);
			fileOut.flush();
			fileOut.close();

			System.out.println("Excel sheet for Inventory Report of Books exported successfully.");

			status = "success";

			/*
			 * Closing resultSet, preparedStatement, connection objects
			 */

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	public String exportStaffsReportExcel(String role, String staffString, String statusVal, int academicYearID,
			int libraryID, String realPath, String excelFileName) {

		int currentRow = 1;

		try {

			File file = new File(realPath + excelFileName);

			/*
			 * Generating XLSX Header value for Patient Information
			 */

			// Workbook wb = new HSSFWorkbook();
			XSSFWorkbook wb = new XSSFWorkbook();

			XSSFSheet spreadSheet = wb.createSheet("Staff Books Report");
			Row row;

			row = spreadSheet.createRow(0);

			spreadSheet.setColumnWidth((short) 0, (short) (256 * 25));

			int counter = 1;

			/*
			 * Creating header in XLSX
			 */
			CellStyle headerCellStyle = wb.createCellStyle();

			/*
			 * Setting up the font
			 */
			Font setFont = wb.createFont();
			setFont.setFontHeightInPoints((short) 12);
			setFont.setBold(true);
			headerCellStyle.setFont(setFont);

			/*
			 * Setting up Data style in XLSX
			 */
			CellStyle dataCellStyle = wb.createCellStyle();
			Font setDataFont = wb.createFont();
			setDataFont.setFontHeightInPoints((short) 12);
			dataCellStyle.setFont(setDataFont);

			/*
			 * Initializing Cell reference variable
			 */

			Cell cell = null;

			cell = row.createCell((short) 0);
			cell.setCellValue("Staff Name");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 1);
			cell.setCellValue("Book Name");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 2);
			cell.setCellValue("Accession No.");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 3);
			cell.setCellValue("Barcode");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 4);
			cell.setCellValue("Issue Date");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 5);
			cell.setCellValue("Expected Return Date");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 6);
			cell.setCellValue("Return Date");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 7);
			cell.setCellValue("Delay Days");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 8);
			cell.setCellValue("Fine Amount");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 9);
			cell.setCellValue("Status");
			cell.setCellStyle(headerCellStyle);

			connection = getConnection();

			// if (academicYearID == 0) {
			if (staffString.equals("all")) {

				if (statusVal.equals("all")) {

					if (role.equals("NonTeaching")) {
						String retrieveAllStudentsBooksReportQuery = "SELECT CONCAT(st.lastName, ' ', st.firstName)AS staffName, b.name, b.accNum, b.barcode, s.bookID, s.issueDate, s.expectedReturnDate, s.returnDate, s.delayDays, s.fineAmount, s.activityStatus FROM StaffIssue AS s, AppUser AS st, Books AS b WHERE st.id=s.userID AND b.id=s.bookID AND st.role NOT IN ('teacher','administrator', 'superAdmin') AND b.libraryID = "
								+ libraryID + " ORDER BY st.id ASC";
						preparedStatement = connection.prepareStatement(retrieveAllStudentsBooksReportQuery);
					} else {
						String retrieveAllStudentsBooksReportQuery = "SELECT CONCAT(st.lastName, ' ', st.firstName)AS staffName, b.name, b.accNum, b.barcode, s.bookID, s.issueDate, s.expectedReturnDate, s.returnDate, s.delayDays, s.fineAmount, s.activityStatus FROM StaffIssue AS s, AppUser AS st, Books AS b WHERE st.id=s.userID AND b.id=s.bookID AND st.role NOT IN ('officeAdmin', 'librarian', 'Non-Teaching') AND b.libraryID = "
								+ libraryID + " ORDER BY st.id ASC";
						preparedStatement = connection.prepareStatement(retrieveAllStudentsBooksReportQuery);
					}
				} else {

					if (role.equals("NonTeaching")) {
						String retrieveAllStudentsBooksReportQuery = "SELECT CONCAT(st.lastName,' ', st.firstName)AS staffName, b.name, b.accNum, b.barcode, s.bookID, s.issueDate, s.expectedReturnDate, s.returnDate, s.delayDays, s.fineAmount, s.activityStatus FROM StaffIssue AS s, AppUser AS st, Books AS b WHERE st.id=s.userID AND b.id=s.bookID AND s.activityStatus = "
								+ statusVal
								+ " AND st.role NOT IN ('teacher','administrator', 'superAdmin') AND b.libraryID = "
								+ libraryID + " ORDER BY st.id ASC";
						preparedStatement = connection.prepareStatement(retrieveAllStudentsBooksReportQuery);
					} else {
						String retrieveAllStudentsBooksReportQuery = "SELECT CONCAT(st.lastName,' ', st.firstName)AS staffName, b.name, b.accNum, b.barcode, s.bookID, s.issueDate, s.expectedReturnDate, s.returnDate, s.delayDays, s.fineAmount, s.activityStatus FROM StaffIssue AS s, AppUser AS st, Books AS b WHERE st.id=s.userID AND b.id=s.bookID AND s.activityStatus = "
								+ statusVal
								+ " AND st.role NOT IN ('officeAdmin', 'librarian', 'Non-Teaching') AND b.libraryID = "
								+ libraryID + " ORDER BY st.id ASC";
						preparedStatement = connection.prepareStatement(retrieveAllStudentsBooksReportQuery);
					}
				}

			} else {

				if (statusVal.equals("all")) {

					System.out.println("hello" + staffString + "-" + role);
					String retrieveStudentsBooksReportBystatusQuery = QueryMaker.RETRIEVE_Books_Staffs_All_Status_Report_By_Status;

					preparedStatement = connection.prepareStatement(retrieveStudentsBooksReportBystatusQuery);

					preparedStatement.setInt(1, Integer.parseInt(staffString));
					preparedStatement.setInt(2, libraryID);

				} else {
					System.out.println("hello" + statusVal + "-" + staffString + "-" + role);
					String retrieveStudentsBooksReportBystatusQuery = QueryMaker.RETRIEVE_Books_Staffs_All_Status_Report_By_Status_New;

					preparedStatement = connection.prepareStatement(retrieveStudentsBooksReportBystatusQuery);

					preparedStatement.setString(1, statusVal);

					preparedStatement.setInt(2, Integer.parseInt(staffString));

					preparedStatement.setInt(3, libraryID);
				}
			}

			/*
			 * } else {
			 * 
			 * if (staffString.equals("all")) {
			 * 
			 * if (statusVal.equals("all")) {
			 * 
			 * if (role.equals("NonTeaching")) { String retrieveAllStudentsBooksReportQuery
			 * =
			 * "SELECT CONCAT(st.lastName, ' ', st.firstName)AS staffName, b.name, b.accNum, b.barcode, s.bookID, s.issueDate, s.expectedReturnDate, s.returnDate, s.delayDays, s.fineAmount, s.activityStatus FROM StaffIssue AS s, AppUser AS st, Books AS b WHERE st.id=s.userID AND b.id=s.bookID AND st.role NOT IN ('teacher','administrator', 'superAdmin') AND b.academicYearID = "
			 * + academicYearID + " AND b.libraryID = " + libraryID + " ORDER BY st.id ASC";
			 * preparedStatement =
			 * connection.prepareStatement(retrieveAllStudentsBooksReportQuery); } else {
			 * String retrieveAllStudentsBooksReportQuery =
			 * "SELECT CONCAT(st.lastName, ' ', st.firstName)AS staffName, b.name, b.accNum, b.barcode, s.bookID, s.issueDate, s.expectedReturnDate, s.returnDate, s.delayDays, s.fineAmount, s.activityStatus FROM StaffIssue AS s, AppUser AS st, Books AS b WHERE st.id=s.userID AND b.id=s.bookID AND st.role NOT IN ('officeAdmin', 'librarian', 'Non-Teaching') AND b.academicYearID = "
			 * + academicYearID + " AND b.libraryID = " + libraryID + " ORDER BY st.id ASC";
			 * preparedStatement =
			 * connection.prepareStatement(retrieveAllStudentsBooksReportQuery); } } else {
			 * 
			 * if (role.equals("NonTeaching")) { String retrieveAllStudentsBooksReportQuery
			 * =
			 * "SELECT CONCAT(st.lastName,' ', st.firstName)AS staffName, b.name, b.accNum, b.barcode, s.bookID, s.issueDate, s.expectedReturnDate, s.returnDate, s.delayDays, s.fineAmount, s.activityStatus FROM StaffIssue AS s, AppUser AS st, Books AS b WHERE st.id=s.userID AND b.id=s.bookID AND s.activityStatus = "
			 * + statusVal +
			 * " AND st.role NOT IN ('teacher','administrator', 'superAdmin') AND b.academicYearID = "
			 * + academicYearID + " AND b.libraryID = " + libraryID + " ORDER BY st.id ASC";
			 * preparedStatement =
			 * connection.prepareStatement(retrieveAllStudentsBooksReportQuery); } else {
			 * String retrieveAllStudentsBooksReportQuery =
			 * "SELECT CONCAT(st.lastName,' ', st.firstName)AS staffName, b.name, b.accNum, b.barcode, s.bookID, s.issueDate, s.expectedReturnDate, s.returnDate, s.delayDays, s.fineAmount, s.activityStatus FROM StaffIssue AS s, AppUser AS st, Books AS b WHERE st.id=s.userID AND b.id=s.bookID AND s.activityStatus = "
			 * + statusVal +
			 * " AND st.role NOT IN ('officeAdmin', 'librarian', 'Non-Teaching') AND b.academicYearID = "
			 * + academicYearID + " AND b.libraryID = " + libraryID + " ORDER BY st.id ASC";
			 * preparedStatement =
			 * connection.prepareStatement(retrieveAllStudentsBooksReportQuery); } }
			 * 
			 * } else {
			 * 
			 * if (statusVal.equals("all")) {
			 * 
			 * System.out.println("hello" + staffString + "-" + role); String
			 * retrieveStudentsBooksReportBystatusQuery =
			 * QueryMaker.RETRIEVE_Books_Staffs_All_Status_Report_By_Status_AcademicYearID;
			 * 
			 * preparedStatement =
			 * connection.prepareStatement(retrieveStudentsBooksReportBystatusQuery);
			 * 
			 * preparedStatement.setInt(1, Integer.parseInt(staffString));
			 * preparedStatement.setInt(2, academicYearID); preparedStatement.setInt(3,
			 * libraryID);
			 * 
			 * } else { System.out.println("hello" + statusVal + "-" + staffString + "-" +
			 * role); String retrieveStudentsBooksReportBystatusQuery = QueryMaker.
			 * RETRIEVE_Books_Staffs_All_Status_Report_By_Status_New_AcademicYearID;
			 * 
			 * preparedStatement =
			 * connection.prepareStatement(retrieveStudentsBooksReportBystatusQuery);
			 * 
			 * preparedStatement.setString(1, statusVal); preparedStatement.setInt(2,
			 * Integer.parseInt(staffString)); preparedStatement.setInt(3, academicYearID);
			 * preparedStatement.setInt(4, libraryID); } } }
			 */

			resultSet = preparedStatement.executeQuery();

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

			while (resultSet.next()) {

				row = spreadSheet.createRow(currentRow++);

				cell = row.createCell((short) 0);
				cell.setCellValue(resultSet.getString("staffName"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 1);
				cell.setCellValue(resultSet.getString("name"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 2);
				cell.setCellValue(resultSet.getString("accNum"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 3);
				cell.setCellValue(resultSet.getString("barcode"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 4);
				cell.setCellValue(dateFormat.format(dateFormat1.parse(resultSet.getString("issueDate"))));
				cell.setCellStyle(dataCellStyle);

				String expectedReturnDate = "";

				if (resultSet.getString("expectedReturnDate") == null
						|| resultSet.getString("expectedReturnDate") == "") {
					expectedReturnDate = "";
				} else if (resultSet.getString("expectedReturnDate").isEmpty()) {
					expectedReturnDate = "";
				} else {
					expectedReturnDate = dateFormat
							.format(dateFormat1.parse(resultSet.getString("expectedReturnDate")));
				}

				cell = row.createCell((short) 5);
				cell.setCellValue(expectedReturnDate);
				cell.setCellStyle(dataCellStyle);

				if (resultSet.getString("returnDate") == null || resultSet.getString("returnDate") == "") {

					cell = row.createCell((short) 6);
					cell.setCellValue("");
					cell.setCellStyle(dataCellStyle);
				} else if (resultSet.getString("returnDate").isEmpty()) {

					cell = row.createCell((short) 6);
					cell.setCellValue("");
					cell.setCellStyle(dataCellStyle);
				} else {

					cell = row.createCell((short) 6);
					cell.setCellValue(dateFormat.format(dateFormat1.parse(resultSet.getString("returnDate"))));
					cell.setCellStyle(dataCellStyle);
				}

				cell = row.createCell((short) 7);
				cell.setCellValue(resultSet.getInt("delayDays"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 8);
				cell.setCellValue(resultSet.getDouble("fineAmount"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 9);
				cell.setCellValue(resultSet.getString("activityStatus"));
				cell.setCellStyle(dataCellStyle);

			}
			/*
			 * writing to the xlsx file
			 */
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wb.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();

			/*
			 * Write the output to a file
			 */
			FileOutputStream fileOut = new FileOutputStream(new File(realPath + excelFileName));
			fileOut.write(outArray);
			fileOut.flush();
			fileOut.close();

			System.out.println("Excel sheet for Students Books Report exported successfully.");

			status = "success";

			/*
			 * Closing resultSet, preparedStatement, connection objects
			 */
			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;

	}

	public String exportStudentsReportExcel(int ayclassID, String studentString, String statusVal, int academicYearID,
			int libraryID, String className, String realPath, String excelFileName) {

		int currentRow = 1;

		try {

			File file = new File(realPath + excelFileName);

			/*
			 * Generating XLSX Header value for Patient Information
			 */

			// Workbook wb = new HSSFWorkbook();
			XSSFWorkbook wb = new XSSFWorkbook();

			XSSFSheet spreadSheet = wb.createSheet("Students Report Excel Sheet");
			Row row;

			row = spreadSheet.createRow(0);

			spreadSheet.setColumnWidth((short) 0, (short) (256 * 25));

			int counter = 1;

			/*
			 * Creating header in XLSX
			 */
			CellStyle headerCellStyle = wb.createCellStyle();

			/*
			 * Setting up the font
			 */
			Font setFont = wb.createFont();
			setFont.setFontHeightInPoints((short) 12);
			setFont.setBold(true);
			headerCellStyle.setFont(setFont);

			/*
			 * Setting up Data style in XLSX
			 */
			CellStyle dataCellStyle = wb.createCellStyle();
			Font setDataFont = wb.createFont();
			setDataFont.setFontHeightInPoints((short) 12);
			dataCellStyle.setFont(setDataFont);

			/*
			 * Initializing Cell reference variable
			 */

			Cell cell = null;

			cell = row.createCell((short) 0);
			cell.setCellValue("Student Name");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 1);
			cell.setCellValue("Book Name");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 2);
			cell.setCellValue("Accession No.");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 3);
			cell.setCellValue("Barcode");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 4);
			cell.setCellValue("Issue Date");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 5);
			cell.setCellValue("Expected Return Date");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 6);
			cell.setCellValue("Return Date");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 7);
			cell.setCellValue("Delay Days");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 8);
			cell.setCellValue("Fine Amount");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 9);
			cell.setCellValue("Status");
			cell.setCellStyle(headerCellStyle);

			connection = getConnection();

			// if (academicYearID == 0) {
			if (studentString.equals("all")) {

				if (statusVal.equals("all")) {

					String retrieveAllStudentsBooksReportQuery = QueryMaker.RETRIEVE_Books_ALL_Students_Report;

					preparedStatement = connection.prepareStatement(retrieveAllStudentsBooksReportQuery);

					preparedStatement.setInt(1, ayclassID);
					preparedStatement.setInt(2, libraryID);

				} else {

					String retrieveAllStudentsBooksReportQuery = QueryMaker.RETRIEVE_Books_ALL_Students_Report_New;

					preparedStatement = connection.prepareStatement(retrieveAllStudentsBooksReportQuery);

					preparedStatement.setString(1, statusVal);
					preparedStatement.setInt(2, ayclassID);
					preparedStatement.setInt(3, libraryID);
				}

			} else {

				if (statusVal.equals("all")) {
					String retrieveStudentsBooksReportBystatusQuery = QueryMaker.RETRIEVE_Books_Student_All_Status_Report_By_Status;

					preparedStatement = connection.prepareStatement(retrieveStudentsBooksReportBystatusQuery);

					preparedStatement.setInt(1, Integer.parseInt(studentString));
					preparedStatement.setInt(2, ayclassID);
					preparedStatement.setInt(3, libraryID);

				} else {

					String retrieveStudentsBooksReportBystatusQuery = QueryMaker.RETRIEVE_Books_Student_All_Status_Report_By_Status_New;

					preparedStatement = connection.prepareStatement(retrieveStudentsBooksReportBystatusQuery);

					preparedStatement.setString(1, statusVal);

					preparedStatement.setInt(2, Integer.parseInt(studentString));

					preparedStatement.setInt(3, ayclassID);

					preparedStatement.setInt(4, libraryID);
				}
			}
			/*
			 * } else { if (studentString.equals("all")) {
			 * 
			 * if (statusVal.equals("all")) {
			 * 
			 * String retrieveAllStudentsBooksReportQuery =
			 * QueryMaker.RETRIEVE_Books_ALL_Students_Report_AcademicYearID;
			 * 
			 * preparedStatement =
			 * connection.prepareStatement(retrieveAllStudentsBooksReportQuery);
			 * 
			 * preparedStatement.setInt(1, ayclassID); preparedStatement.setInt(2,
			 * academicYearID); preparedStatement.setInt(3, libraryID);
			 * 
			 * } else {
			 * 
			 * String retrieveAllStudentsBooksReportQuery =
			 * QueryMaker.RETRIEVE_Books_ALL_Students_Report_New_AcademicYear;
			 * 
			 * preparedStatement =
			 * connection.prepareStatement(retrieveAllStudentsBooksReportQuery);
			 * 
			 * preparedStatement.setString(1, statusVal); preparedStatement.setInt(2,
			 * ayclassID); preparedStatement.setInt(3, academicYearID);
			 * preparedStatement.setInt(4, libraryID); }
			 * 
			 * } else {
			 * 
			 * if (statusVal.equals("all")) { String
			 * retrieveStudentsBooksReportBystatusQuery =
			 * QueryMaker.RETRIEVE_Books_Student_All_Status_Report_By_Status_AcademicYearID;
			 * 
			 * preparedStatement =
			 * connection.prepareStatement(retrieveStudentsBooksReportBystatusQuery);
			 * 
			 * preparedStatement.setInt(1, Integer.parseInt(studentString));
			 * preparedStatement.setInt(2, ayclassID); preparedStatement.setInt(3,
			 * academicYearID); preparedStatement.setInt(4, libraryID); } else {
			 * 
			 * String retrieveStudentsBooksReportBystatusQuery = QueryMaker.
			 * RETRIEVE_Books_Student_All_Status_Report_By_Status_New_AcademicYearID;
			 * 
			 * preparedStatement =
			 * connection.prepareStatement(retrieveStudentsBooksReportBystatusQuery);
			 * 
			 * preparedStatement.setString(1, statusVal);
			 * 
			 * preparedStatement.setInt(2, Integer.parseInt(studentString));
			 * 
			 * preparedStatement.setInt(3, ayclassID); preparedStatement.setInt(4,
			 * academicYearID); preparedStatement.setInt(5, libraryID); } } }
			 */

			resultSet = preparedStatement.executeQuery();

			ResultSetMetaData metaData = resultSet.getMetaData();

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

			while (resultSet.next()) {

				row = spreadSheet.createRow(currentRow++);

				cell = row.createCell((short) 0);
				cell.setCellValue(resultSet.getString("studentName"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 1);
				cell.setCellValue(resultSet.getString("name"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 2);
				cell.setCellValue(resultSet.getString("accNum"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 3);
				cell.setCellValue(resultSet.getString("barcode"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 4);
				cell.setCellValue(dateFormat.format(dateFormat1.parse(resultSet.getString("issueDate"))));
				cell.setCellStyle(dataCellStyle);

				String expectedReturnDate = "";

				if (resultSet.getString("expectedReturnDate") == null
						|| resultSet.getString("expectedReturnDate") == "") {
					expectedReturnDate = "";
				} else if (resultSet.getString("expectedReturnDate").isEmpty()) {
					expectedReturnDate = "";
				} else {
					expectedReturnDate = dateFormat
							.format(dateFormat1.parse(resultSet.getString("expectedReturnDate")));
				}

				cell = row.createCell((short) 5);
				cell.setCellValue(expectedReturnDate);
				cell.setCellStyle(dataCellStyle);

				if (resultSet.getString("returnDate") == null || resultSet.getString("returnDate") == "") {

					cell = row.createCell((short) 6);
					cell.setCellValue("");
					cell.setCellStyle(dataCellStyle);
				} else if (resultSet.getString("returnDate").isEmpty()) {

					cell = row.createCell((short) 6);
					cell.setCellValue("");
					cell.setCellStyle(dataCellStyle);
				} else {

					cell = row.createCell((short) 6);
					cell.setCellValue(dateFormat.format(dateFormat1.parse(resultSet.getString("returnDate"))));
					cell.setCellStyle(dataCellStyle);
				}

				cell = row.createCell((short) 7);
				cell.setCellValue(resultSet.getInt("delayDays"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 8);
				cell.setCellValue(resultSet.getDouble("fineAmount"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 9);
				cell.setCellValue(resultSet.getString("activityStatus"));
				cell.setCellStyle(dataCellStyle);

			}
			/*
			 * writing to the xlsx file
			 */
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wb.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();

			/*
			 * Write the output to a file
			 */
			FileOutputStream fileOut = new FileOutputStream(new File(realPath + excelFileName));
			fileOut.write(outArray);
			fileOut.flush();
			fileOut.close();

			System.out.println("Excel sheet for Students Books Report exported successfully.");

			status = "success";

			/*
			 * Closing resultSet, preparedStatement, connection objects
			 */
			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;

	}

}
