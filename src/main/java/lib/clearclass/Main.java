package lib.clearclass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
		try {
			String pathToBrowser = args[0];
			String[] cmd = {pathToBrowser, "http://localhost:8080/"};
			Process browser = Runtime.getRuntime().exec(cmd);
		} catch (Exception e) {
			System.err.println("Не удалось запустить браузер: " + e);
		}
	}
}