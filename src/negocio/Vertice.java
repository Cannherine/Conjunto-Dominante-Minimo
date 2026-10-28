package negocio;

import java.util.Objects;

public class Vertice {
	private final String nombre;

	public Vertice(String nombre) {
		if (nombre == null || nombre.trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre no puede estar vacio");
		}
		this.nombre = nombre.trim();
			}

	public String getNombre() {
		return nombre;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Vertice)) {
			return false;
		}

		Vertice otro = (Vertice) obj;
		return nombre.equals(otro.nombre);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombre);
	}

	@Override
	public String toString() {
		return nombre;
	}
}
