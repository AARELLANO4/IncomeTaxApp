/* Alexis Arellano OCT 03/2020 */

package workshop2;

import java.util.Scanner;

public class IncomeTax {

	// CONSTANTS
	final int SINGLE_FILER = 0;
	final int MARRIED_JOINTLY_OR_QUALIFYING_WIDOW = 1;
	final int MARRIED_SEPARATELY = 2;
	final int HEAD_OF_HOUSEHOLD = 3;
	
	final double rates_2001[] = {0.15, 0.275,0.305,0.355,0.391};
	final double intervals_2001[][] = {
			{27050, 45200, 22600, 36250},
			{65550, 109250, 54625, 93650},
			{136750, 166500, 83250, 151650},
			{297350, 297350, 148675, 297350},
			{297351, 297351, 148676, 297351}
	};

	final double rates_2009[] = {0.10, 0.15, 0.25, 0.28, 0.33, 0.35};
	final double intervals_2009[][] = {
			{8350, 16700, 8350, 11950},
			{33950, 67900, 33950, 45500},
			{82250, 137050, 68525, 117450},
			{171550, 208850, 104425, 190200},
			{372950, 372950, 286475, 372950},
			{372951, 372951, 186476, 372951}
	};
	
	// DATA FIELDS
	int filingStatus;
	double intervals[][];
	double rates[];
	double taxableIncome;
	
	// CONSTRUCTORS
	public IncomeTax() {
		filingStatus = SINGLE_FILER;
		intervals = intervals_2009;
		rates = rates_2009;
		taxableIncome = 0;
	};
	
	public IncomeTax(int p_status, double[][] p_intervals, double[] p_rates, double p_income) {
		setFilingStatus(p_status);
		setIntervals(p_intervals);
		setRates(p_rates);
		setTaxableIncome(p_income);
	};
	
	// GETTER/SETTERS
	public int getFilingStatus() {
		return filingStatus;
	}

	public void setFilingStatus(int filingStatus) {
		this.filingStatus = filingStatus;
	}

	public double[][] getIntervals() {
		return intervals;
	}

	public void setIntervals(double[][] intervals) {
		this.intervals = new double[intervals.length][intervals[0].length];
		
		for (int r = 0; r < intervals.length; r++) {
			for (int c = 0; c < intervals[r].length; c++ ) {
				this.intervals[r][c] = intervals[r][c];
			}
		}
	}

	public double[] getRates() {
		return rates;
	}

	public void setRates(double[] rates) {
		this.rates = new double[rates.length];
		for (int i = 0; i < rates.length; i++) {
			this.rates[i] = rates[i];
		}
	}

	public double getTaxableIncome() {
		return taxableIncome;
	}

	public void setTaxableIncome(double taxableIncome) {
		this.taxableIncome = taxableIncome;
	}
	
	// GET INCOME TAX
	
	public double getIncomeTax (int year) {
		double tax = 0;
		
		if (year == 2009) {
			
			double rates[] = rates_2009;
			double intervals [][] = intervals_2009;	
			int row = 0;
			
			while(getTaxableIncome() > intervals[row][getFilingStatus()]) {
				tax = tax + (intervals[row][getFilingStatus()] * rates[row]);
				setTaxableIncome(getTaxableIncome() - intervals[row][getFilingStatus()]);
				row++;
			}
			if (getTaxableIncome() > 0) {			
				tax = tax + (getTaxableIncome() * rates[row]);
			}

		}
		
		if (year == 2001) {
			
			double rates[] = rates_2001;
			double intervals [][] = intervals_2001;	
			int row = 0;
			
			while(getTaxableIncome() > intervals[row][getFilingStatus()]) {
				tax = tax + (intervals[row][getFilingStatus()] * rates[row]);
				setTaxableIncome(getTaxableIncome() - intervals[row][getFilingStatus()]);
				row++;
			}
			if (getTaxableIncome() > 0) {			
				tax = tax + (getTaxableIncome() * rates[row]);
			}

		}
		
		return tax; 
	}
	
	public void printTable(double min, double max, int year) {
		System.out.printf("\n%4d Taxable Income Range: $%.2f to $%.2f\n\n",year,min,max);
		System.out.println("");
		System.out.printf("%-20s %-20s %-20s %-20s %-20s\n", "Taxable", "Single", "Married", "Married", "Head of");
		System.out.printf("%-20s %-20s %-20s %-20s %-20s\n", "Income", " ", "(Joint)", "(Separate)", "HouseHold");
		System.out.print("----------------------------------------------------------------------------------------------\n");
		while (min <= max) {
			System.out.printf("%-20.2f ",min);
			
			// Loop through filing statuses
			for (int i = 0; i < 4; i++) {
				setTaxableIncome(min);
				setFilingStatus(i);
				System.out.printf(" %-20.2f ",getIncomeTax(year));
				
			}
			System.out.println("");
			min += 1000;
		}
	}
	
	// MENU METHODS
	
	public void calculateTaxMenu() {
		System.out.println("-- Tax Calculator --");
		System.out.println("Select your Filing Status: ");
		System.out.println("0. Single Filer");
		System.out.println("1. Married Jointly or Qualifying Widow(er)");
		System.out.println("2. Married Filing Separately");
		System.out.println("3. Head of Household");
		System.out.print("Enter your filing status: ");
		Scanner inStatus = new Scanner(System.in);
		setFilingStatus(inStatus.nextInt());
		
		System.out.print("Enter your taxable income: $");
		Scanner inIncome = new Scanner(System.in);
		setTaxableIncome(inIncome.nextDouble());
		
		double tax = getIncomeTax(2009);
		System.out.printf("Your Tax is: $%.2f",tax);
		
		inStatus.close();
		inIncome.close();
	}
	
	public void tableMenu() {;
		System.out.println("");
		System.out.println("-- Tax Tables --");
		
		System.out.print("Enter the amount from: ");
		Scanner inMin = new Scanner(System.in);
		double min = inMin.nextDouble();
		
		System.out.print("Enter the amount to: ");
		Scanner inMax = new Scanner(System.in);
		double max = inMax.nextDouble();
		
		printTable(min,max,2001);
		printTable(min,max,2009);
		inMax.close(); inMin.close();
		
		
	}
	
	public void menu() {
		
		System.out.println("1. Compute Personal Income Tax");
		System.out.println("2. Print Tax Tables for Taxable Incomes (in Range)");
		System.out.println("0. Exit");
		System.out.print("Select an option: ");
		
		Scanner input = new Scanner(System.in);
		int option = input.nextInt();
		

		switch(option) {
		case 1:
			calculateTaxMenu();
			break;
		case 2:
			tableMenu();
			break;
		case 0:
			System.out.println("Thank you! Good bye.");
			break;
		default:
			System.out.println("Invalid Option. Select an Option: ");
			break;
		}
			
		

		input.close();
	}
	
	public static void main(String[] args) {
		IncomeTax incomeTax = new IncomeTax();
		incomeTax.menu();
	}

}
