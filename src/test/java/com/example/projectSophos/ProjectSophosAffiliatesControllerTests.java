package com.example.projectSophos;

import com.example.projectSophos.entities.Affiliates;
import com.example.projectSophos.repositories.AffiliatesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProjectSophosAffiliatesControllerTests {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private AffiliatesRepository affiliatesRepository;
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void getShouldReturnNoContentEmptyAffiliates() throws Exception {
		List<Affiliates> affiliates = new ArrayList<>();
		Mockito.when(affiliatesRepository.findAll()).thenReturn(affiliates);

		this.mockMvc
			.perform(get("/api/affiliates"))
			.andDo(print())
			.andExpect(status().isNoContent())
			.andExpect(
				content().string(containsString(""))
			);
	}

	@Test
	public void getShouldReturnOKAffiliates() throws Exception {
		List<Affiliates> affiliates = List.of(
			new Affiliates(1, "test 1", 22, "affiliate@mail.com")
		);
		String json = objectMapper.writeValueAsString(affiliates);

		Mockito.when(affiliatesRepository.findAll()).thenReturn(affiliates);

		this.mockMvc
				.perform(get("/api/affiliates"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(
					content().json(json)
				);
	}

	@Test
	public void getByIdShouldReturnOKAffiliate() throws Exception {
		Affiliates affiliate = new Affiliates(1, "affiliate 1", 22, "affiliate@mail.com");
		String json = objectMapper.writeValueAsString(affiliate);

		Mockito.when(affiliatesRepository.findById(affiliate.getId())).thenReturn(Optional.of(affiliate));

		this.mockMvc
				.perform(get("/api/affiliates/" + affiliate.getId()))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(
					content().json(json)
				);
	}

	@Test
	public void getByIdShouldReturnNotFoundAffiliate() throws Exception {
		Mockito.when(affiliatesRepository.findById(1)).thenReturn(Optional.empty());

		this.mockMvc
				.perform(get("/api/affiliates/1"))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(
					content().string("")
				);
	}

	@Test
	public void postShouldReturnCreatedAffiliate() throws Exception {
		Affiliates affiliate = new Affiliates(1, "affiliate 1", 22, "affiliate@mail.com");
		String json = objectMapper.writeValueAsString(affiliate);

		Mockito.when(affiliatesRepository.save(Mockito.any(Affiliates.class))).thenReturn(affiliate);

		this.mockMvc
				.perform(
					post("/api/affiliates").contentType(MediaType.APPLICATION_JSON).content(json)
				)
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(
					content().json(json)
				);
	}

	@Test
	public void postShouldReturnNotFoundAffiliate() throws Exception {
		Affiliates affiliate = new Affiliates(null, "", null, "");
		String json = objectMapper.writeValueAsString(affiliate);

		Mockito.when(affiliatesRepository.save(Mockito.any(Affiliates.class))).thenReturn(affiliate);

		this.mockMvc
				.perform(
						post("/api/affiliates").contentType(MediaType.APPLICATION_JSON).content(json)
				)
				.andDo(print())
				.andExpect(status().isNotFound());
	}

	@Test
	public void putShouldReturnCreatedAffiliate() throws Exception {
		Affiliates affiliate = new Affiliates(1, "affiliate 1", 22, "affiliate@mail.com");
		String json = objectMapper.writeValueAsString(affiliate);

		Mockito.when(affiliatesRepository.findById(affiliate.getId())).thenReturn(Optional.of(affiliate));
		Mockito.when(affiliatesRepository.save(Mockito.any(Affiliates.class))).thenReturn(affiliate);

		this.mockMvc
				.perform(
						put("/api/affiliates/" + affiliate.getId()).contentType(MediaType.APPLICATION_JSON).content(json)
				)
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(
					content().json(json)
				);
	}

	@Test
	public void putShouldReturnNotFoundAffiliate() throws Exception {
		Affiliates affiliate = new Affiliates(1, "affiliate 1", 22, "affiliate@mail.com");
		String json = objectMapper.writeValueAsString(affiliate);

		Mockito.when(affiliatesRepository.findById(affiliate.getId())).thenReturn(Optional.empty());
		Mockito.when(affiliatesRepository.save(Mockito.any(Affiliates.class))).thenReturn(affiliate);

		this.mockMvc
				.perform(
					put("/api/affiliates/" + affiliate.getId()).contentType(MediaType.APPLICATION_JSON).content(json)
				)
				.andDo(print())
				.andExpect(status().isNotFound());
	}

	@Test
	public void putShouldReturnBadRequestAffiliate() throws Exception {
		Affiliates affiliate = new Affiliates(null, "", null, "");
		String json = objectMapper.writeValueAsString(affiliate);

		Mockito.when(affiliatesRepository.save(Mockito.any(Affiliates.class))).thenReturn(affiliate);

		this.mockMvc
				.perform(
					put("/api/affiliates/" + affiliate.getId()).contentType(MediaType.APPLICATION_JSON).content(json)
				)
				.andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	public void deleteShouldReturnOkSuccessRemoveAffiliate() throws Exception {
		Integer affiliateId = 1;
		Mockito.when(affiliatesRepository.existsById(affiliateId)).thenReturn(true);

		this.mockMvc
				.perform(
					delete("/api/affiliates/" + affiliateId)
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(
					content().string("")
				);

		Mockito.verify(affiliatesRepository).deleteById(affiliateId); // check that the method was called
	}

	@Test
	public void deleteShouldReturnNoContentAffiliate() throws Exception {
		Integer affiliateId = 2;
		Mockito.when(affiliatesRepository.existsById(2)).thenReturn(false);

		this.mockMvc
				.perform(
					delete("/api/affiliates/" + affiliateId)
				)
				.andDo(print())
				.andExpect(status().isNoContent())
				.andExpect(
					content().string("")
				);

		Mockito.verify(affiliatesRepository, Mockito.never()).deleteById(affiliateId); // check that the method was called
	}
}
