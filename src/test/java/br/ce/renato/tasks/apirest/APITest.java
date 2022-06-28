package br.ce.renato.tasks.apirest;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {

	@BeforeAll
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}
	
	
	@Test
	public void deveRetornarTarefas() {
		RestAssured.given()
		.when()
			.get("/todo")
		.then()
			.statusCode(200);
	}
	
	@Test
	public void deveAdicionarUmaTarefaComSucesso() {
		RestAssured.given()
			.body("{\r\n" + 
					"	\"task\":\"Teste via API\",\r\n" + 
					"	\"dueDate\":\"2022-12-30\"\r\n" + 
					"}")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.log().all()
			.statusCode(201);
	}
	
	@Test
	public void naoDeveAdicionarUmaTarefaInvalida() {
		RestAssured.given()
			.body("{\r\n" + 
					"	\"task\":\"Teste via API\",\r\n" + 
					"	\"dueDate\":\"2020-12-30\"\r\n" + 
					"}")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.log().all()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"));
	}
}