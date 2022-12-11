package com.example.projectSophos;

import com.example.projectSophos.entities.Affiliates;
import com.example.projectSophos.entities.Appointments;
import com.example.projectSophos.entities.Tests;
import com.example.projectSophos.repositories.AppointmentsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProjectSophosAppointmentsControllerTests {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private AppointmentsRepository appointmentsRepository;
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void getShouldReturnNoContentEmptyAppointments() throws Exception {
		List<Appointments> appointments = new ArrayList<>();
		Mockito.when(appointmentsRepository.findAll()).thenReturn(appointments);

		this.mockMvc
			.perform(get("/api/appointments"))
			.andDo(print())
			.andExpect(status().isNoContent())
			.andExpect(
				content().string(containsString(""))
			);
	}

	@Test
	public void getShouldReturnOKAppointments() throws Exception {
		List<Appointments> appointments = List.of(
			new Appointments(1, new Date(), new Date(), new Tests(), new Affiliates())
		);

		String json = objectMapper.writeValueAsString(appointments);

		Mockito.when(appointmentsRepository.findAll()).thenReturn(appointments);

		this.mockMvc
				.perform(get("/api/appointments"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(
					content().json(json)
				);
	}

	@Test
	public void getByIdShouldReturnOKAppointment() throws Exception {
		Appointments appointment = new Appointments(1, new Date(), new Date(), new Tests(), new Affiliates());
		String json = objectMapper.writeValueAsString(appointment);

		Mockito.when(appointmentsRepository.findById(appointment.getId())).thenReturn(Optional.of(appointment));

		this.mockMvc
				.perform(get("/api/appointments/" + appointment.getId()))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(
					content().json(json)
				);
	}

	@Test
	public void getByIdShouldReturnNotFoundAppointment() throws Exception {
		Mockito.when(appointmentsRepository.findById(1)).thenReturn(Optional.empty());

		this.mockMvc
				.perform(get("/api/appointments/1"))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(
					content().string("")
				);
	}
/*
	@Test
	public void postShouldReturnCreatedAppointment() throws Exception {
		Appointments appointment = new Appointments(1, new Date(), new Date(), new Tests(), new Affiliates());
		String json = new Gson().toJson(appointment);

		Mockito.when(appointmentsRepository.save(Mockito.any(Appointments.class))).thenReturn(appointment);

		this.mockMvc
				.perform(
					post("/api/appointments").contentType(MediaType.APPLICATION_JSON).content(json)
				)
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(
					content().json(json)
				);
	}

	@Test
	public void postShouldReturnNotFoundAppointment() throws Exception {
		Appointments appointment = new Appointments(null, null, null, null, null);
		String json = new Gson().toJson(appointment);

		Mockito.when(appointmentsRepository.save(Mockito.any(Appointments.class))).thenReturn(appointment);

		this.mockMvc
				.perform(
						post("/api/appointments").contentType(MediaType.APPLICATION_JSON).content(json)
				)
				.andDo(print())
				.andExpect(status().isNotFound());
	}

	@Test
	public void putShouldReturnCreatedAppointment() throws Exception {
		Appointments appointment = new Appointments(1, new Date(), new Date(), new Tests(), new Affiliates());
		String json = new Gson().toJson(appointment);

		Mockito.when(appointmentsRepository.findById(appointment.getId())).thenReturn(Optional.of(appointment));
		Mockito.when(appointmentsRepository.save(Mockito.any(Appointments.class))).thenReturn(appointment);

		this.mockMvc
				.perform(
						put("/api/appointments/" + appointment.getId()).contentType(MediaType.APPLICATION_JSON).content(json)
				)
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(
					content().json(json)
				);
	}

	@Test
	public void putShouldReturnNotFoundAppointment() throws Exception {
		Appointments appointment = new Appointments(1, new Date(), new Date(), new Tests(), new Affiliates());
		String json = new Gson().toJson(appointment);

		Mockito.when(appointmentsRepository.findById(appointment.getId())).thenReturn(Optional.empty());
		Mockito.when(appointmentsRepository.save(Mockito.any(Appointments.class))).thenReturn(appointment);

		this.mockMvc
				.perform(
					put("/api/appointments/" + appointment.getId()).contentType(MediaType.APPLICATION_JSON).content(json)
				)
				.andDo(print())
				.andExpect(status().isNotFound());
	}

	@Test
	public void putShouldReturnBadRequestAppointment() throws Exception {
		Appointments appointment = new Appointments(null, null, null, null, null);
		String json = new Gson().toJson(appointment);

		Mockito.when(appointmentsRepository.save(Mockito.any(Appointments.class))).thenReturn(appointment);

		this.mockMvc
				.perform(
					put("/api/appointments/" + appointment.getId()).contentType(MediaType.APPLICATION_JSON).content(json)
				)
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
*/
	@Test
	public void deleteShouldReturnOkSuccessRemoveAppointment() throws Exception {
		Integer appointmentId = 1;
		Mockito.when(appointmentsRepository.existsById(appointmentId)).thenReturn(true);

		this.mockMvc
				.perform(
					delete("/api/appointments/" + appointmentId)
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(
					content().string("")
				);

		Mockito.verify(appointmentsRepository).deleteById(appointmentId); // check that the method was called
	}

	@Test
	public void deleteShouldReturnNoContentAppointment() throws Exception {
		Integer appointmentId = 2;
		Mockito.when(appointmentsRepository.existsById(2)).thenReturn(false);

		this.mockMvc
				.perform(
					delete("/api/appointments/" + appointmentId)
				)
				.andDo(print())
				.andExpect(status().isNoContent())
				.andExpect(
					content().string("")
				);

		Mockito.verify(appointmentsRepository, Mockito.never()).deleteById(appointmentId); // check that the method was called
	}
}
