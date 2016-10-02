package br.com.copy;

import static spark.Spark.delete;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;

import org.eclipse.jetty.http.HttpStatus;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import br.com.copy.exception.ApplicationException;
import br.com.copy.helper.ResponseStatus;
import br.com.copy.model.User;
import br.com.copy.repository.UserRepository;
import br.com.copy.service.UserService;
import spark.ResponseTransformer;

@Component
public class WebContext {

	public static final String APPLICATION_JSON = "application/json";

	@Autowired private UserRepository usuarios;
	@Autowired private UserService userService;

	@Value("${application.port}")
	private String port;

	public void startApplication() {
		port(Integer.parseInt(port));

		// busca de todos os usuarios
		get("/users", (req, res) -> {
			res.type(APPLICATION_JSON);
			res.status(HttpStatus.OK_200);
			return usuarios.findAll();
		}, jsonConverter());

		//busca de usuario por id
		get("/users/:id", (req, res) -> {
			User usuario = usuarios.findById(Long.valueOf(req.params("id")));
			if (usuario == null) throw new ApplicationException(ResponseStatus.USER_NOT_EXIST);
			
			res.type(APPLICATION_JSON);
			res.status(HttpStatus.OK_200);
			
			return usuario;
		}, jsonConverter());

		//adicionando novo usuario
		post("/users", (req, res) -> {
			// processo utilizado para converter o JSON em Object, utilizando a api Jackson 2
			ObjectMapper mapper = new ObjectMapper();
			User usuario = mapper.readValue(req.body(), User.class);
			
			// chamar o service
			userService.save(usuario);
			res.status(HttpStatus.CREATED_201);
			return "";
		});
		
		put("/users/:id", (req, res) -> {
			Long id = Long.valueOf(req.params("id"));

			ObjectMapper mapper = new ObjectMapper();
			User usuario = mapper.readValue(req.body(), User.class);
			
			usuario.setId(id);
			userService.update(usuario);
			res.status(HttpStatus.OK_200);
			return "";
		});
		
		delete("/users/:id", (req, res) -> {
			Long id = Long.valueOf(req.params("id"));
			User userBase = usuarios.findById(id);
			
			if (userBase == null) throw new ApplicationException(ResponseStatus.USER_NOT_EXIST);
			
			// possível candidato a chamar o service
			usuarios.delete(id);
			res.status(HttpStatus.OK_200);
			return "";
		});
		
		handleExceptions();
	}

	/**
	 * Metodo responsável por interceptar os exceptions da aplicação e efetuar o tratamento dos mesmos.
	 * Evitando que a stacktrace seja impressa para o usuario final.
	 */
	private void handleExceptions() {
		exception(ApplicationException.class, (exception, req, res) -> {
			exception.printStackTrace(); //USAR LOG4J
			ApplicationException ex = (ApplicationException) exception;
		    JSONObject obj = new JSONObject();
		    obj.put("code", ex.getCode());
		    obj.put("message", ex.getMessage());
		    res.status(HttpStatus.FORBIDDEN_403);
		    res.type(APPLICATION_JSON);
		    res.body(obj.toString());
		});
		
		exception(Exception.class, (exception, req, res) -> {
		    exception.printStackTrace(); //USAR LOG4J
		    JSONObject obj = new JSONObject(new ApplicationException(ResponseStatus.ERRO_DE_APLICACAO).getResponseInfo());
		    res.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
		    res.type(APPLICATION_JSON);
		    res.body(obj.toString());
		});
	}

	private ResponseTransformer jsonConverter() {
		return new Gson()::toJson;
	}
}
