package com.alltruth.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class SeungynApplication {

	public static void main(String[] args) throws MalformedURLException {
		SpringApplication.run(SeungynApplication.class, args);
		File file = new File("/upload");
		file.mkdir();
		System.out.println(file.getAbsoluteFile());
		System.out.println("asd");
		System.out.println(Path.of("upload").resolve(

				"dsf"));

		Path a = Path.of("upload","whiteboardappdotorg20210910235613.png");
		System.out.println(a.toString());
		Resource res = new UrlResource(a.toUri());
		System.out.println(a.toUri());
		System.out.println(res);

		System.out.println("-------enum-------");
		System.out.println(Test.TTTTT);
		System.out.println(Test.TTTTT.name());
		System.out.println(Test.TTTTT.getA());
		System.out.println(Test.TTTTT.getB());
	}

}

@AllArgsConstructor
@Getter
enum Test{
	TTTTT("aaa", "bbb");

	private String a;
	private String b;


}
