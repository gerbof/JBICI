package model;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name="persona")
public class Persona implements Serializable{
	
	@Id @GeneratedValue
	private long id;
	@Column(nullable=false, unique=true)
	private String dni;
	@Column(nullable=false)
	private String apellido;
	@Column(nullable=false)
	private String nombres;
	@Column(nullable=false)
	private String domicilio;
	@Column(nullable=false)
	private Date fechaNacimiento;
	@Column(nullable=false)
	private String sexo;
	@Column(nullable=false)
	private String email;
	
	public Persona(){
		
	}
	
	public Persona(String dni, String apellido, String nombres,
			String domicilio, Date fechaNacimiento, String sexo, String email) {
		super();
		this.dni = dni;
		this.apellido = apellido;
		this.nombres = nombres;
		this.domicilio = domicilio;
		this.fechaNacimiento = fechaNacimiento;
		this.sexo = sexo;
		this.email = email;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public long getId() {
		return id;
	}
}
