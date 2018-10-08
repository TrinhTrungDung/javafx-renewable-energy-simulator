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
import javafx.scene.control.TableView;
import vgu.consumer.Consumer;
import vgu.consumer.ConsumerFactory;
import vgu.control.Control;
import vgu.generator.Generator;
import vgu.generator.GeneratorFactory;

public class DataUtils {
	
	public static void generateConsumers(Control control) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(new FileOutputStream("consumers.csv", false));
		
		for (AbstractComponent consumer : control.getConsumers()) {
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
	
	public static void generateGenerators(Control control) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(new FileOutputStream("generators.csv", false));
		
		for (AbstractComponent generator : control.getGenerators()) {
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
