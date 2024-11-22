//package utn.dacs.ms.backend.service;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import utn.dacs.ms.backend.model.entity.Alumno;
//import utn.dacs.ms.backend.model.repository.AlumnoRepository;
//
//@Service
//public class AlumnoServiceImpl implements AlumnoService {
//
//	@Autowired
//	private AlumnoRepository alumnoRepository;
//
//	@Override
//	public Optional<Alumno> getById(Long id) {
//		return alumnoRepository.findById(id);
//	}
//
//	@Override
//	public List<Alumno> getAll() {
//		return alumnoRepository.findAll();
//	}
//
//	
//	@Override
//	public void delete(Long id) {
//		Optional<Alumno> alumno = getById(id);
//		alumnoRepository.delete(alumno.get());
//	}
//
//	@Override
//	public Alumno save(Alumno entity) {
//		return alumnoRepository.save(entity);
//	}
//
//	@Override
//	public List<Alumno> find(Map<String, Object> filter) {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public Alumno getBy(Map<String, Object> filter) {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public Boolean existById(Long id) {
//		return alumnoRepository.existsById(id);
//	}
//	
//}
