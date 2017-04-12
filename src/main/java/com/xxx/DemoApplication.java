package com.xxx;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@ImportResource({ "classpath*:applicationContext.xml" })
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	ReservationDao reservationDao;

	// Insert data at initailizing phase using ReservationDao#insert
	@Bean
	CommandLineRunner runner() {
		return args -> Arrays.asList("spring", "spring boot", "spring cloud", "doma").forEach(s -> {
			Reservation r = new Reservation();
			r.name = s;
			reservationDao.insert(r);
		});
	}

	@RequestMapping(path = "/")
	List<Reservation> all() {
		return reservationDao.selectAll();
	}

	@RequestMapping(path = "/name/{name}")
	List<Reservation> byname(@PathVariable String name) {
		List<Reservation> l=reservationDao.selectByName(name);
		// System.out.println(l.getClass());
		// System.out.println(l.size());
		// System.out.println(l.isEmpty());
		return l;
	}

	@RequestMapping(path = "/id/{id}")
	List<Reservation> byid(@PathVariable int id) {
		return reservationDao.selectByID(id);
	}
}