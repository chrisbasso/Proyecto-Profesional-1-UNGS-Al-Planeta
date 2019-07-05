package com.tp.proyecto1.model.users;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.pasajes.Transaccion;
import com.tp.proyecto1.model.sucursales.Sucursal;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

    @NaturalId
	private String user;

	private String password;

	private Boolean enabled = true;

    @ManyToOne
    private Role rol;

    @OneToMany(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
    private List<Transaccion> transacciones = new ArrayList<>();

    @OneToOne
	private Sucursal sucursal;

    @OneToOne
	private Cliente cliente;

    private String email;


	public User() {
	}

    public User(String user) {
        this.user = user;
    }

    public User(String user, String password) {
        this.user = user;
        this.password = password;
        this.enabled = true;
    }

    public User(String user, String password, Role rol) {
        this.user = user;
        this.password = password;
        this.rol = rol;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public List<Transaccion> getTransacciones() {
		return transacciones;
	}

	public void setTransacciones(List<Transaccion> transacciones) {
		this.transacciones = transacciones;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

	public Role getRol() {
		return rol;
	}

	public void setRol(Role rol) {
		this.rol = rol;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user1 = (User) o;
        return enabled == user1.enabled &&
                Objects.equals(id, user1.id) &&
                Objects.equals(user, user1.user) &&
                Objects.equals(password, user1.password) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, password, enabled, rol);
    }
    
    
}
