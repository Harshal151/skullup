package com.kovidRMS.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateToWords {

	private static final String[] specialNamesMonthDay = { "", " First", " Second", " Third", " Fourth", " Fifth",
			" Sixth", " Seventh", " Eighth", " Nineth", " Tenth", " Eleventh", " Twelveth", " Thirteenth",
			" Fourteenth", " Fifteenth", " Sixteenth", " Seventeenth", " Eighteenth", " Nineteenth", " Twentieth",
			" Twenty First", " Twenty Second", " Twenty Third", " Twenty Fourth", " Twenty Fifth", " Twenty Sixth",
			" Twenty Seventh", " Twenty Eighth", " Twenty Nineth", " Thirty", " Thirty First"

	};
	private static final String[] specialNames = { "", " Thousand"

	};

	private static final String[] tensNames = { "", " Ten", " Twenty", " Thirty", " Forty", " Fifty", " Sixty",
			" Seventy", " Eighty", " Ninety" };

	private static final String[] numNames = { "", " One", " Two", " Three", " Four", " Five", " Six", " Seven",
			" Eight", " Nine", " Ten", " Eleven", " Twelve", " Thirteen", " Fourteen", " Fifteen", " Sixteen",
			" Seventeen", " Eighteen", " Nineteen" };

	public String convertDateToWords(String dateString) {

		String date = dateString;

		if (validateDate(date)) {

			SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
			ParsePosition parsePosition = new ParsePosition(0);
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateFormatter.parse(date, new ParsePosition(0)));
			DateFormat format2 = new SimpleDateFormat("MMMMM ");

			int day = cal.get(Calendar.DATE);
			String strDateToWords = getMonthDay(day);
			strDateToWords += " " + format2.format(cal.getTime());

			int year = cal.get(Calendar.YEAR);
			strDateToWords += " " + convert(year);

			return strDateToWords;
		} else {
			System.out.println("Wrongt! Please enter date in dd/mm/yyyy format");
			return "Error";
		}
	}

	public String getMonthDay(int day) {
		return specialNamesMonthDay[day];
	}

	private String convertLessThanOneThousand(int number) {
		String current;

		if (number % 100 < 20) {
			current = numNames[number % 100];
			number /= 100;
		} else {
			current = numNames[number % 10];
			number /= 10;

			current = tensNames[number % 10] + current;
			number /= 10;
		}
		if (number == 0) {
			return current;
		}
		return numNames[number] + " Hundred" + current;
	}

	public String convert(int number) {

		if (number == 0) {
			return "Zero";
		}

		String prefix = "";

		String current = "";
		int place = 0;

		if (number >= 1 && number < 2000) {
			do {
				int n = number % 100;
				if (n != 0) {
					String s = convertLessThanOneThousand(n);
					current = s + current;
				}
				place++;
				number /= 100;
			} while (number > 0);
		} else {
			do {
				int n = number % 1000;
				if (n != 0) {
					String s = convertLessThanOneThousand(n);
					current = s + specialNames[place] + current;
				}
				place++;
				number /= 1000;
			} while (number > 0);
		}

		return (prefix + current).trim();
	}

	public boolean validateDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			sdf.setLenient(false);
			sdf.parse(date);
			return true;
		} catch (ParseException ex) {
			return false;
		}
	}

}
