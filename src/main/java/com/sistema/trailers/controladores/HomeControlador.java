package com.sistema.trailers.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sistema.trailers.modelo.Pelicula;
import com.sistema.trailers.repositorios.PeliculaRepositorio;
import com.sistema.trailers.servicio.UsuarioServicio;

import org.springframework.ui.Model;

@Controller
@RequestMapping("")
public class HomeControlador {

	@Autowired
	private PeliculaRepositorio peliculaRepositorio;
	
	@Autowired
	private UsuarioServicio servicio;

	@GetMapping("")
	public ModelAndView verPaginaDeInicio() {
		List<Pelicula> ultimasPeliculas = peliculaRepositorio.findAll(PageRequest.of(0,4,Sort.by("fechaEstreno").descending())).toList();
		return new ModelAndView("index")
				      .addObject("ultimasPeliculas", ultimasPeliculas);

	}
	@GetMapping("/login")
	public String iniciarSesion() {
		return "login";
	}

	@GetMapping("/peliculas")
	public ModelAndView listarPeliculas(@PageableDefault(sort = "fechaEstreno",direction = Sort.Direction.DESC) Pageable pageable) {
		Page<Pelicula> peliculas = peliculaRepositorio.findAll(pageable);
		return new ModelAndView("peliculas")
				        .addObject("peliculas",peliculas);
	}
	
	@GetMapping("/peliculas/{id}")
	public ModelAndView mostrarDetallesDePelicula(@PathVariable Integer id) {
		Pelicula pelicula = peliculaRepositorio.getOne(id);
		return new ModelAndView("pelicula").addObject("pelicula",pelicula);
	}

	@GetMapping("usuarios")
	public String usuarios(Model modelo) {
		modelo.addAttribute("usuarios", servicio.listarUsuarios());
		return "usuarios";
    }

}
