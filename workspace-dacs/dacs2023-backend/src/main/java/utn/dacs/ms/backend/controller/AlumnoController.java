//package utn.dacs.ms.backend.controller;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import utn.dacs.ms.backend.dto.AlumnoDto;
//import utn.dacs.ms.backend.exceptions.ResourceNotFoundException;
//import utn.dacs.ms.backend.model.entity.Alumno;
//import utn.dacs.ms.backend.service.AlumnoService;
//
//@RestController
//@RequestMapping(value = "/alumno")
//public class AlumnoController {
//
//	@Autowired
//	private AlumnoService alumnoService;
//
//	@Autowired
//	private ModelMapper modelMapper;
//
//	@GetMapping("")
//	public ResponseEntity<List<AlumnoDto>> getAll() {
//		List<Alumno> alumnos = alumnoService.getAll();
//		List<AlumnoDto> data = alumnos.stream().map(alumno -> modelMapper.map(alumno, AlumnoDto.class))
//				.collect(Collectors.toList());
//		return new ResponseEntity<List<AlumnoDto>>(data, HttpStatus.OK);
//	}
//
//	@GetMapping("/{id}")
//	public ResponseEntity<AlumnoDto> getById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
//		Optional<Alumno> alumno = alumnoService.getById(id);
//
//		if (alumno.isEmpty()) {
//			throw new ResourceNotFoundException("");
//		}
//		AlumnoDto data = modelMapper.map(alumno.get(), AlumnoDto.class);
//		return new ResponseEntity<AlumnoDto>(data, HttpStatus.OK);
//	}
//
//	@PostMapping("")
//	public ResponseEntity<AlumnoDto> create(@RequestBody AlumnoDto alumnoDto) {
//		Alumno alumno = modelMapper.map(alumnoDto, Alumno.class);
//		AlumnoDto data = modelMapper.map(alumnoService.save(alumno), AlumnoDto.class);
//		return new ResponseEntity<AlumnoDto>(data, HttpStatus.OK);
//	}
//
//	@PutMapping("")
//	public ResponseEntity<AlumnoDto> update(@RequestBody AlumnoDto alumnoDto) throws ResourceNotFoundException {
//
//		if (alumnoDto.getId() == null || !alumnoService.existById(alumnoDto.getId())) {
//			throw new ResourceNotFoundException("");
//		}
//
//		Alumno alumno = modelMapper.map(alumnoDto, Alumno.class);
//		AlumnoDto data = modelMapper.map(alumnoService.save(alumno), AlumnoDto.class);
//		return new ResponseEntity<AlumnoDto>(data, HttpStatus.OK);
//	}
//
//	@DeleteMapping("/{id}")
//	public ResponseEntity<String> delete(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
//
//		if (id == null || !alumnoService.existById(id)) {
//			throw new ResourceNotFoundException("");
//		}
//
//		alumnoService.delete(id);
//		return new ResponseEntity<>(HttpStatus.OK);
//	}
//
//}
