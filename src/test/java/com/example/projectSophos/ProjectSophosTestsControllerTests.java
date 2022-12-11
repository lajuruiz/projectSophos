package com.example.projectSophos;

import com.example.projectSophos.controllers.TestsController;
import com.example.projectSophos.entities.Tests;
import com.example.projectSophos.repositories.TestsRepository;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProjectSophosTestsControllerTests {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private TestsRepository testsRepository;
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void getShouldReturnNoContentEmptyTests() throws Exception {
		List<Tests> tests = new ArrayList<>();
		Mockito.when(testsRepository.findAll()).thenReturn(tests);

		this.mockMvc
			.perform(get("/api/tests"))
			.andDo(print())
			.andExpect(status().isNoContent())
			.andExpect(
				content().string(containsString(""))
			);
	}

	@Test
	public void getShouldReturnOKTests() throws Exception {
		List<Tests> tests = List.of(
				new Tests(1, "test 1", "description 1")
		);
		String json = objectMapper.writeValueAsString(tests);

		Mockito.when(testsRepository.findAll()).thenReturn(tests);

		this.mockMvc
				.perform(get("/api/tests"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(
					content().json(json)
				);
	}

	@Test
	public void getByIdShouldReturnOKTest() throws Exception {
		Tests test = new Tests(1, "test 1", "description 1");
		String json = objectMapper.writeValueAsString(test);

		Mockito.when(testsRepository.findById(test.getId())).thenReturn(Optional.of(test));

		this.mockMvc
				.perform(get("/api/tests/" + test.getId()))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(
					content().json(json)
				);
	}

	@Test
	public void getByIdShouldReturnNotFoundTest() throws Exception {
		Mockito.when(testsRepository.findById(1)).thenReturn(Optional.empty());

		this.mockMvc
				.perform(get("/api/tests/1"))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(
					content().string("")
				);
	}

	@Test
	public void postShouldReturnCreatedTest() throws Exception {
		Tests test = new Tests(1, "test 1", "description 1");
		String json = objectMapper.writeValueAsString(test);

		Mockito.when(testsRepository.save(Mockito.any(Tests.class))).thenReturn(test);

		this.mockMvc
				.perform(
					post("/api/tests").contentType(MediaType.APPLICATION_JSON).content(json)
				)
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(
					content().json(json)
				);
	}

	@Test
	public void postShouldReturnNotFoundTest() throws Exception {
		Tests test = new Tests(null, "", "");
		String json = objectMapper.writeValueAsString(test);

		Mockito.when(testsRepository.save(Mockito.any(Tests.class))).thenReturn(test);

		this.mockMvc
				.perform(
						post("/api/tests").contentType(MediaType.APPLICATION_JSON).content(json)
				)
				.andDo(print())
				.andExpect(status().isNotFound());
	}

	@Test
	public void putShouldReturnCreatedTest() throws Exception {
		Tests test = new Tests(1, "test 1", "description 1");
		String json = objectMapper.writeValueAsString(test);

		Mockito.when(testsRepository.findById(test.getId())).thenReturn(Optional.of(test));
		Mockito.when(testsRepository.save(Mockito.any(Tests.class))).thenReturn(test);

		this.mockMvc
				.perform(
						put("/api/tests/" + test.getId()).contentType(MediaType.APPLICATION_JSON).content(json)
				)
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(
					content().json(json)
				);
	}

	@Test
	public void putShouldReturnNotFoundTest() throws Exception {
		Tests test = new Tests(1, "test updated name", "test updated name");
		String json = objectMapper.writeValueAsString(test);

		Mockito.when(testsRepository.findById(test.getId())).thenReturn(Optional.empty());
		Mockito.when(testsRepository.save(Mockito.any(Tests.class))).thenReturn(test);

		this.mockMvc
				.perform(
					put("/api/tests/" + test.getId()).contentType(MediaType.APPLICATION_JSON).content(json)
				)
				.andDo(print())
				.andExpect(status().isNotFound());
	}

	@Test
	public void putShouldReturnBadRequestTest() throws Exception {
		Tests test = new Tests(null, "", "");
		String json = objectMapper.writeValueAsString(test);

		Mockito.when(testsRepository.save(Mockito.any(Tests.class))).thenReturn(test);

		this.mockMvc
				.perform(
					put("/api/tests/" + test.getId()).contentType(MediaType.APPLICATION_JSON).content(json)
				)
				.andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	public void deleteShouldReturnOkSuccessRemoveTest() throws Exception {
		Integer testId = 1;
		Mockito.when(testsRepository.existsById(testId)).thenReturn(true);

		this.mockMvc
				.perform(
					delete("/api/tests/" + testId)
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(
					content().string("")
				);

		Mockito.verify(testsRepository).deleteById(testId); // check that the method was called
	}

	@Test
	public void deleteShouldReturnNoContentTest() throws Exception {
		Integer testId = 2;
		Mockito.when(testsRepository.existsById(2)).thenReturn(false);

		this.mockMvc
				.perform(
					delete("/api/tests/" + testId)
				)
				.andDo(print())
				.andExpect(status().isNoContent())
				.andExpect(
					content().string("")
				);

		Mockito.verify(testsRepository, Mockito.never()).deleteById(testId); // check that the method was called
	}
}
