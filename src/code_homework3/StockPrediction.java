package code_homework3;

import java.util.Scanner;

public class StockPrediction {
	
	public static void main(String[] args) {
		DataReader reader;
		BayesianCurveFitting fit;
		String cont = "Y";
		int num = 0;
		double absMeanErr = 0.0;	// absolute mean error
		double relErr = 0.0;	// relative error;
		double sumErr = 0.0;
		double sumPrice = 0.0;
		while(cont.equals("Y") || cont.equals("y")) {
			String[] symbolList = {"AAPL", "AMZN", "CAJ", "FB", "GOOG", "MSFT", "NINOY", "SNE", "WMT", "YHOO"};
			System.out.println("Please choose a stock symbol:");
			for(int i = 0; i < symbolList.length; i++) {
				System.out.println((i + 1) + ". " + symbolList[i]);
			}
			for(int i = 11; i <= 15; i++) {
				System.out.println(i + ". " + "data " + (i - 10));
			}
			
			Scanner sc = new Scanner(System.in);
			int choice = sc.nextInt();
			while(choice <= 0 || choice > symbolList.length + 5) {
				System.out.println("Please enter again.");
				choice = sc.nextInt();
			}
			System.out.println("Enter number of data (N):");
			int N = sc.nextInt();
			double[] x = new double[N];
			for(int i = 0; i < N; i++) {
				x[i] = i + 1;
			}
			int m;
			if(choice < symbolList.length) {
				m = 9;	// order of polynomial
			} else {
				m = 5;
			}
			if(choice < symbolList.length) {
				// my own stock data file
				reader = new DataReader(N, symbolList[choice - 1], true);
			} else {
				// data used for demo
				reader = new DataReader(N, String.valueOf(choice - 10), false);
			}
			double[] stockPrices = reader.getPrices();
			fit = new BayesianCurveFitting(x, stockPrices, m);
			double prediction = fit.getMx(N + 1);
			double s2x = fit.getS2X(N + 1);
			//double stdVar = Math.sqrt(s2x);	// standard variance
			double actualPrice = 0.0;
			if(choice < symbolList.length) {
				actualPrice = reader.getActualPrice();
			}
			num++;
			sumErr += Math.abs(actualPrice - prediction);
			sumPrice += actualPrice;
			absMeanErr = sumErr / num;
			relErr = sumErr / sumPrice;
			System.out.println("----------------------------");
			if(choice < symbolList.length) {
				System.out.println("Symbol: " + symbolList[choice - 1]);
			} else {
				System.out.println("Data: data " + (choice - 10));
			}
			System.out.printf("Predicted price is: %.2f\n", prediction);
			System.out.println("Variation of prediction: " + s2x);
			if(choice < symbolList.length) {
				System.out.println("Actual price is: " + actualPrice);
				System.out.println("Absolute mean error: " + absMeanErr);
				System.out.println("Average relative error: " + relErr);
			}
			System.out.println("Continue? (Y/N)");
			cont = sc.next();
		}
	}
	
	

}
