package com.example.projectSophos;

import com.example.projectSophos.entities.Affiliates;
import com.example.projectSophos.entities.Appointments;
import com.example.projectSophos.entities.Tests;
import com.example.projectSophos.repositories.AffiliatesRepository;
import com.example.projectSophos.repositories.AppointmentsRepository;
import com.example.projectSophos.repositories.TestsRepository;
import com.example.projectSophos.serializers.AppointmentsCount;
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
	@MockBean
	private AffiliatesRepository affiliatesRepository;
	@MockBean
	private TestsRepository testsRepository;
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
		Tests test = new Tests(1 , "name test", "description test");
		Affiliates affiliate = new Affiliates(1, "affiliate test", 22, "affiliate@mail.com");
		List<Appointments> appointments = List.of(
			new Appointments(1, new Date(), new Date(), test , affiliate)
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

	@Test
	public void getByDateShouldReturnNoContentEmptyAppointments() throws Exception {
		List<AppointmentsCount> appointments = new ArrayList<>();
		Mockito.when(appointmentsRepository.countTotalAffiliatesByDate(Mockito.any(Date.class))).thenReturn(appointments);

		this.mockMvc
			.perform(get("/api/appointments/getByDate/11-11-2022"))
			.andDo(print())
			.andExpect(status().isNoContent())
			.andExpect(
				content().string(containsString(""))
			);


		Mockito.verify(appointmentsRepository).countTotalAffiliatesByDate(Mockito.any(Date.class)); // check that the method was called
	}

	@Test
	public void getByDateShouldReturnOkAppointments() throws Exception {
		List<AppointmentsCount> appointments = List.of(
				new AppointmentsCount(new Affiliates(), 5L)
		);

		String json = objectMapper.writeValueAsString(appointments);
		Mockito.when(appointmentsRepository.countTotalAffiliatesByDate(Mockito.any(Date.class))).thenReturn(appointments);

		this.mockMvc
			.perform(get("/api/appointments/getByDate/11-11-2022"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(
				content().json(json)
			);
	}
	@Test
	public void getByDateShouldReturnBadRequestNotFoundWithWrongDateFormat() throws Exception {
		this.mockMvc.perform(get("/api/appointments/getByDate/11/11/2022")).andExpect(status().isNotFound());
		this.mockMvc.perform(get("/api/appointments/getByDate/11-56-2022")).andExpect(status().isBadRequest());
		this.mockMvc.perform(get("/api/appointments/getByDate/45-11-2022")).andExpect(status().isBadRequest());

		Mockito.verify(appointmentsRepository, Mockito.never()).countTotalAffiliatesByDate(Mockito.any(Date.class)); // check that the method was called

	}

	@Test
	public void getByAffiliateShouldReturnNoContentEmptyAppointments() throws Exception {
		List<Appointments> appointments = new ArrayList<>();
		Mockito.when(appointmentsRepository.findByAffiliate_Id(1)).thenReturn(appointments);

		this.mockMvc
			.perform(get("/api/appointments/getByAffiliate/" + 1))
			.andDo(print())
			.andExpect(status().isNoContent())
			.andExpect(
				content().string(containsString(""))
			);

		Mockito.verify(appointmentsRepository).findByAffiliate_Id(1); // check that the method was called
	}

	@Test
	public void getByAffiliateShouldReturnOkAppointments() throws Exception {
		Affiliates affiliate = new Affiliates(1, "affiliate", 22, "affiliate@mail.com");
		List<Appointments> appointments = List.of(
				new Appointments(1, new Date(), new Date(), new Tests(), affiliate)
		);

		String json = objectMapper.writeValueAsString(appointments);
		Mockito.when(appointmentsRepository.findByAffiliate_Id(affiliate.getId())).thenReturn(appointments);

		this.mockMvc
			.perform(get("/api/appointments/getByAffiliate/" + affiliate.getId()))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(
				content().json(json)
			);


		Mockito.verify(appointmentsRepository).findByAffiliate_Id(affiliate.getId()); // check that the method was called
	}

	@Test
	public void postShouldReturnCreatedAppointment() throws Exception {
		Tests test = new Tests(1 , "name test", "description test");
		Affiliates affiliate = new Affiliates(1, "affiliate test", 22, "affiliate@mail.com");
		Appointments appointment = new Appointments(1, new Date(), new Date(), test , affiliate);

		String json = objectMapper.writeValueAsString(appointment);

		Mockito.when(testsRepository.findById(test.getId())).thenReturn(Optional.of(test));
		Mockito.when(affiliatesRepository.findById(affiliate.getId())).thenReturn(Optional.of(affiliate));
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
	public void postShouldReturnNotFoundWithWrongTestsForeignKeyAppointment() throws Exception {
		Tests test = new Tests(1 , "name test", "description test");
		Affiliates affiliate = new Affiliates(1, "affiliate test", 22, "affiliate@mail.com");
		Appointments appointment = new Appointments(1, new Date(), new Date(), test , affiliate);

		String json = objectMapper.writeValueAsString(appointment);

		Mockito.when(testsRepository.findById(test.getId())).thenReturn(Optional.empty());
		Mockito.when(appointmentsRepository.save(Mockito.any(Appointments.class))).thenReturn(appointment);

		this.mockMvc
				.perform(
						post("/api/appointments").contentType(MediaType.APPLICATION_JSON).content(json)
				)
				.andDo(print())
				.andExpect(status().isNotFound());

		Mockito.verify(testsRepository).findById(test.getId()); // check that the method was called
		Mockito.verify(affiliatesRepository, Mockito.never()).findById(affiliate.getId()); // check that the method was never called

	}
	@Test
	public void postShouldReturnNotFoundWithWrongAffiliatesForeignKeyAppointment() throws Exception {
		Tests test = new Tests(1 , "name test", "description test");
		Affiliates affiliate = new Affiliates(1, "affiliate test", 22, "affiliate@mail.com");
		Appointments appointment = new Appointments(1, new Date(), new Date(), test , affiliate);

		String json = objectMapper.writeValueAsString(appointment);

		Mockito.when(testsRepository.findById(test.getId())).thenReturn(Optional.of(test));
		Mockito.when(affiliatesRepository.findById(affiliate.getId())).thenReturn(Optional.empty());
		Mockito.when(appointmentsRepository.save(Mockito.any(Appointments.class))).thenReturn(appointment);

		this.mockMvc
				.perform(
						post("/api/appointments").contentType(MediaType.APPLICATION_JSON).content(json)
				)
				.andDo(print())
				.andExpect(status().isNotFound());

		Mockito.verify(testsRepository).findById(test.getId()); // check that the method was called
		Mockito.verify(affiliatesRepository).findById(affiliate.getId()); // check that the method was called
		Mockito.verify(appointmentsRepository, Mockito.never()).save(Mockito.any(Appointments.class)); // check that the method was never called
	}


	@Test
	public void putShouldReturnCreatedAppointment() throws Exception {
		Tests test = new Tests(1 , "name test", "description test");
		Affiliates affiliate = new Affiliates(1, "affiliate test", 22, "affiliate@mail.com");
		Appointments appointment = new Appointments(1, new Date(), new Date(), test , affiliate);

		String json = objectMapper.writeValueAsString(appointment);

		Mockito.when(appointmentsRepository.findById(appointment.getId())).thenReturn(Optional.of(appointment));

		Mockito.when(testsRepository.findById(test.getId())).thenReturn(Optional.of(test));
		Mockito.when(affiliatesRepository.findById(affiliate.getId())).thenReturn(Optional.of(affiliate));
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
	public void putShouldReturnNotFoundWithWrongAppointmentId() throws Exception {
		Tests test = new Tests(1 , "name test", "description test");
		Affiliates affiliate = new Affiliates(1, "affiliate test", 22, "affiliate@mail.com");
		Appointments appointment = new Appointments(1, new Date(), new Date(), test , affiliate);

		String json = objectMapper.writeValueAsString(appointment);

		Mockito.when(testsRepository.findById(test.getId())).thenReturn(Optional.of(test));
		Mockito.when(affiliatesRepository.findById(affiliate.getId())).thenReturn(Optional.of(affiliate));

		Mockito.when(appointmentsRepository.findById(appointment.getId())).thenReturn(Optional.empty());

		this.mockMvc
				.perform(
						put("/api/appointments/" + appointment.getId()).contentType(MediaType.APPLICATION_JSON).content(json)
				)
				.andDo(print())
				.andExpect(status().isNotFound());

		Mockito.verify(appointmentsRepository, Mockito.never()).save(Mockito.any(Appointments.class)); // check that the method was never called

	}
	@Test
	public void putShouldReturnNotFoundWithWrongTestsForeignKeyAppointment() throws Exception {
		Tests test = new Tests(1 , "name test", "description test");
		Affiliates affiliate = new Affiliates(1, "affiliate test", 22, "affiliate@mail.com");
		Appointments appointment = new Appointments(1, new Date(), new Date(), test , affiliate);

		String json = objectMapper.writeValueAsString(appointment);

		Mockito.when(appointmentsRepository.findById(appointment.getId())).thenReturn(Optional.of(appointment));
		Mockito.when(testsRepository.findById(test.getId())).thenReturn(Optional.empty());
		Mockito.when(appointmentsRepository.save(Mockito.any(Appointments.class))).thenReturn(appointment);

		this.mockMvc
				.perform(
						put("/api/appointments/" + appointment.getId()).contentType(MediaType.APPLICATION_JSON).content(json)
				)
				.andDo(print())
				.andExpect(status().isNotFound());

		Mockito.verify(testsRepository).findById(test.getId()); // check that the method was called
		Mockito.verify(affiliatesRepository, Mockito.never()).findById(affiliate.getId()); // check that the method was never called
		Mockito.verify(appointmentsRepository, Mockito.never()).save(Mockito.any(Appointments.class)); // check that the method was never called
	}
	@Test
	public void putShouldReturnNotFoundWithWrongAffiliatesForeignKeyAppointment() throws Exception {
		Tests test = new Tests(1 , "name test", "description test");
		Affiliates affiliate = new Affiliates(1, "affiliate test", 22, "affiliate@mail.com");
		Appointments appointment = new Appointments(1, new Date(), new Date(), test , affiliate);

		String json = objectMapper.writeValueAsString(appointment);

		Mockito.when(testsRepository.findById(test.getId())).thenReturn(Optional.of(test));
		Mockito.when(appointmentsRepository.findById(appointment.getId())).thenReturn(Optional.of(appointment));
		Mockito.when(affiliatesRepository.findById(affiliate.getId())).thenReturn(Optional.empty());
		Mockito.when(appointmentsRepository.save(Mockito.any(Appointments.class))).thenReturn(appointment);

		this.mockMvc
				.perform(
						put("/api/appointments/" + appointment.getId()).contentType(MediaType.APPLICATION_JSON).content(json)
				)
				.andDo(print())
				.andExpect(status().isNotFound());

		Mockito.verify(testsRepository).findById(test.getId()); // check that the method was called
		Mockito.verify(affiliatesRepository).findById(affiliate.getId()); // check that the method was called
		Mockito.verify(appointmentsRepository, Mockito.never()).save(Mockito.any(Appointments.class)); // check that the method was never called
	}

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

		Mockito.verify(appointmentsRepository, Mockito.never()).deleteById(appointmentId); // check that the method was never called
	}
}
