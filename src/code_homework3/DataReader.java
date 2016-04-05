package code_homework3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataReader {
	private int N;
	private double[] prices;
	private String symbol;
	private String[] info;
	int count;
	boolean isStock;
	
	public DataReader(int N, String symbol, boolean isStock) {
		this.N = N;
		this.symbol = symbol;
		this.isStock = isStock;
		prices = new double[N];
		String fileName;
		if(isStock) {
			fileName = "StockData/" + symbol + "_history.csv";
		} else {
			fileName = "StockData/data_" + symbol + ".csv";
		}
		info = new String[300];
		String line = "";
		count = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			line = reader.readLine();
			while(line != null) {
				String[] item = line.split(",");
				if(isStock) {
					info[count] = item[3];
				} else {
					info[count] = item[1];
				}
				count++;
				line = reader.readLine();
			} 
			reader.close();
		} catch(IOException e) {
			e.printStackTrace();
		} 
		for(int i = 0; i < N; i++) {
			prices[i] = Double.parseDouble(info[count - i - 1]);
		}
	}
	
	public double[] getPrices() {
		return prices;
	}
	
	public double getActualPrice() {
		double actual = Double.parseDouble(info[count - N - 1]);
		return actual;
	}
	
	public String getSymbol() {
		return symbol;
	}
}
