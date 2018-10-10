package gui;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import interfaces.AbstractComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableView;
import vgu.consumer.Consumer;
import vgu.consumer.ConsumerFactory;
import vgu.control.Control;
import vgu.generator.Generator;
import vgu.generator.GeneratorFactory;

public class DataUtils {
	
	public static void generateConsumers(List<AbstractComponent> consumers) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(new FileOutputStream("consumers.csv", false));
		
		for (AbstractComponent consumer : consumers) {
			writer.print(((Consumer) consumer).getName());
			writer.print(",");
			writer.print(consumer.getMaxPower());
			writer.print(",");
			writer.print(consumer.getMinPower());
			writer.print(",");
			writer.print(consumer.getMaxChange());
			writer.print(",");
			writer.print(consumer.getMinChange());
			writer.print("\n");
		}
		
		writer.close();
	}
	
	public static void generateGenerators(List<AbstractComponent> generators) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(new FileOutputStream("generators.csv", false));
		
		for (AbstractComponent generator : generators) {
			writer.print(((Generator) generator).getName());
			writer.print(",");
			writer.print(generator.getMaxPower());
			writer.print(",");
			writer.print(generator.getMinPower());
			writer.print(",");
			writer.print(generator.getMaxChange());
			writer.print(",");
			writer.print(generator.getMinChange());
			writer.print("\n");
		}
		
		writer.close();
	}
	
	public static List<AbstractComponent> getConsumersFromCSV(String fileName) throws IOException {
		List<AbstractComponent> consumers = new ArrayList<>();
		Path path = Paths.get(fileName);
		BufferedReader reader = Files.newBufferedReader(path);
		String line = reader.readLine();
		
		while (line != null) {
			String[] attributes = line.split(",");
			AbstractComponent consumer = createConsumer(attributes);
			consumers.add(consumer);
			line = reader.readLine();
		}
		
		return consumers;
	}
	
	private static AbstractComponent createConsumer(String[] metadata) {
		String name = metadata[0];
		Double maxPower = Double.parseDouble(metadata[1]);
		Double minPower = Double.parseDouble(metadata[2]);
		Double maxChange = Double.parseDouble(metadata[3]);
		Double minChange = Double.parseDouble(metadata[4]);
		
		return ConsumerFactory.generate(name, maxPower, minPower, maxChange, minChange);
	}
	
	public static List<AbstractComponent> getGeneratorsFromCSV(String fileName) throws IOException {
		List<AbstractComponent> generators = new ArrayList<>();
		Path path = Paths.get(fileName);
		BufferedReader reader = Files.newBufferedReader(path);
		String line = reader.readLine();
		
		while (line != null) {
			String[] attributes = line.split(",");
			AbstractComponent generator = createGenerator(attributes);
			generators.add(generator);
			line = reader.readLine();
		}
		
		return generators;
	}
	
	private static AbstractComponent createGenerator(String[] metadata) {
		String name = metadata[0];
		Double maxPower = Double.parseDouble(metadata[1]);
		Double minPower = Double.parseDouble(metadata[2]);
		Double maxChange = Double.parseDouble(metadata[3]);
		Double minChange = Double.parseDouble(metadata[4]);
		
		return GeneratorFactory.generate(name, maxPower, minPower, maxChange, minChange);
	}
	
	public static ObservableList<XYChart.Series<Number, Number>> getFrequencyChartSeries(int iteration, Control control) {
		XYChart.Series<Number, Number> frequencySeries = new XYChart.Series<Number, Number>();
		
		for (int i = 1; i <= iteration; i++) {
			frequencySeries.getData().add(new XYChart.Data<>(i, control.getFrequency()));
			control.nextIteration();
		}
		
		ObservableList<XYChart.Series<Number, Number>> observableData = 
				FXCollections.<XYChart.Series<Number, Number>>observableArrayList();
		observableData.add(frequencySeries);
		
		return observableData;
	}
	
	public static List<XYChart.Series<String, Number>> getDemandSupplyChartSeries(int iteration, Control control) {
		List<XYChart.Series<String, Number>> demandSupplySeries = new ArrayList<>();
		XYChart.Series<String, Number> demandSeries = new XYChart.Series<>();
		XYChart.Series<String, Number> supplySeries = new XYChart.Series<>();
		demandSeries.setName("Demand");
		supplySeries.setName("Supply");
		
		for (int i = 1; i <= iteration; i++) {
			demandSeries.getData().add(new XYChart.Data<>(String.valueOf(i), control.getTotalDemand()));
			supplySeries.getData().add(new XYChart.Data<>(String.valueOf(i), control.getTotalSupply()));
			control.nextIteration();
		}
		
		demandSupplySeries.add(demandSeries);
		demandSupplySeries.add(supplySeries);
		
		return demandSupplySeries;
	}
	
	public static List<XYChart.Series<String, Number>> getCostProfitChartSeries(int iteration, Control control) {
		List<XYChart.Series<String, Number>> costProfitSeries = new ArrayList<>();
		XYChart.Series<String, Number> costSeries = new XYChart.Series<>();
		XYChart.Series<String, Number> profitSeries = new XYChart.Series<>();
		costSeries.setName("Cost");
		profitSeries.setName("Profit");
		
		for (int i = 1; i <= iteration; i++) {
			costSeries.getData().add(new XYChart.Data<>(String.valueOf(i), control.getCost()));
			profitSeries.getData().add(new XYChart.Data<>(String.valueOf(i), control.getProfit()));
			control.nextIteration();
		}
		
		costProfitSeries.add(costSeries);
		costProfitSeries.add(profitSeries);
		
		return costProfitSeries;
	}
	
	public static void addConsumers(Control control, List<AbstractComponent> consumers) {
		consumers.forEach(consumer -> control.addConsumer(consumer));
	}
	
	public static void addGenerators(Control control, List<AbstractComponent> generators) {
		generators.forEach(generator -> control.addGenerator(generator));
	}
	
	public static void addConsumersToTable(TableView<AbstractComponent> table, Control control) {
		table.getItems().addAll(FXCollections.observableArrayList(control.getConsumers()));
	}
	
	public static void addGeneratorsToTable(TableView<AbstractComponent> table, Control control) {
		table.getItems().addAll(FXCollections.observableArrayList(control.getGenerators()));
	}
	
}
