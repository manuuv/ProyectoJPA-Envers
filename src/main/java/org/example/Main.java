package org.example;

import org.example.Entidades.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        EntityManager em = emf.createEntityManager();

        try{
            em.getTransaction().begin();

            Domicilio domicilio = Domicilio.builder()
                    .nombreCalle("El Limón")
                    .numero(3829)
                    .build();
            Cliente cliente = Cliente.builder()
                    .nombre("Manuel")
                    .apellido("Vazquez")
                    .dni(44625892)
                    .domicilio(domicilio)
                    .build();
            Categoria categoria = Categoria.builder()
                    .denominacion("Util")
                    .build();
            Articulo articulo = Articulo.builder()
                    .cantidad(20)
                    .denominacion("Lápiz")
                    .precio(30)
                    .build();
            articulo.getCategorias().add(categoria);
            categoria.getArticulos().add(articulo);

            DetalleFactura detalle = DetalleFactura.builder()
                    .articulo(articulo)
                    .cantidad(3)
                    .subtotal(90)
                    .build();

            Factura factura = Factura.builder()
                    .total(90)
                    .fecha("4/9/2024")
                    .cliente(cliente)
                    .numero(1)
                    .build();
            factura.getDetalles().add(detalle);

            em.persist(factura);
            em.flush();
            em.getTransaction().commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("No se pudo grabar la clase");
            em.getTransaction().rollback();

        };

        em.close();
        emf.close();

    }
}