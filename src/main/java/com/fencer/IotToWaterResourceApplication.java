package com.fencer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class IotToWaterResourceApplication {

    private final static int port = 18006;

	public static void main(String[] args) {
		SpringApplication.run(IotToWaterResourceApplication.class, args);

        /*try {
            Runtime.getRuntime().exec("cmd /c start http://localhost:" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

	}

}
