/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectomundial;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;


public class Modulo1Admin extends JFrame {

    
    private Pais[] paises;
    private Sede[] sedes;
    private Arbitro[] arbitros;

    private int cantidadEquipos; 
    private int cantidadRegistrados = 0;

    
    private JComboBox<Integer> comboTamano;
    private JButton btnConfirmarTamano;
    private JButton btnGenerarDemo;

    private JTextField txtNombrePais;
    private JTextField txtDirectorTecnico;
    private JList<String> listaPaises;
    private DefaultListModel<String> modeloListaPaises;

    public Modulo1Admin() {
        setTitle("Copa Mundial Java - Módulo 1: Administración");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);

        initComponentesConfiguracion();
        initComponentesRegistro();

        setVisible(true);
    }

    private void initComponentesConfiguracion() {
        JPanel panelSuperior = new JPanel();
        panelSuperior.add(new JLabel("Cantidad de países participantes:"));

        comboTamano = new JComboBox<>(new Integer[]{24, 32, 48, 64});
        panelSuperior.add(comboTamano);

        btnConfirmarTamano = new JButton("Confirmar Tamaño");
        btnConfirmarTamano.addActionListener(this::onConfirmarTamano);
        panelSuperior.add(btnConfirmarTamano);

        btnGenerarDemo = new JButton("Generar Datos de Demostración");
        btnGenerarDemo.setEnabled(false); 
        btnGenerarDemo.addActionListener(this::onGenerarDemo);
        panelSuperior.add(btnGenerarDemo);

        add(panelSuperior, BorderLayout.NORTH);
    }

    private void initComponentesRegistro() {
        JPanel panelCentral = new JPanel(new BorderLayout());

        
        JPanel panelForm = new JPanel(new GridLayout(3, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Registrar / Editar País"));

        panelForm.add(new JLabel("Nombre del país:"));
        txtNombrePais = new JTextField();
        panelForm.add(txtNombrePais);

        panelForm.add(new JLabel("Director Técnico:"));
        txtDirectorTecnico = new JTextField();
        panelForm.add(txtDirectorTecnico);

        JButton btnAgregarPais = new JButton("Agregar País");
        btnAgregarPais.addActionListener(this::onAgregarPais);
        panelForm.add(btnAgregarPais);

        JButton btnEditarPais = new JButton("Editar País Seleccionado");
        btnEditarPais.addActionListener(this::onEditarPais);
        panelForm.add(btnEditarPais);

        panelCentral.add(panelForm, BorderLayout.NORTH);

        
        modeloListaPaises = new DefaultListModel<>();
        listaPaises = new JList<>(modeloListaPaises);
        listaPaises.addListSelectionListener(e -> cargarPaisSeleccionadoEnFormulario());
        panelCentral.add(new JScrollPane(listaPaises), BorderLayout.CENTER);

        add(panelCentral, BorderLayout.CENTER);
    }

    

    private void onConfirmarTamano(ActionEvent e) {
        cantidadEquipos = (Integer) comboTamano.getSelectedItem();

        
        paises = new Pais[cantidadEquipos];
        sedes = new Sede[cantidadEquipos / 2];   
        arbitros = new Arbitro[cantidadEquipos / 4]; 

        cantidadRegistrados = 0;
        modeloListaPaises.clear();

        btnGenerarDemo.setEnabled(true);
        comboTamano.setEnabled(false);
        btnConfirmarTamano.setEnabled(false);

        JOptionPane.showMessageDialog(this,
                "Torneo configurado para " + cantidadEquipos + " equipos.\n" +
                "Ya puede registrar países manualmente o generar datos de demostración.");
    }

    private void onAgregarPais(ActionEvent e) {
        if (paises == null) {
            JOptionPane.showMessageDialog(this, "Primero confirme el tamaño del torneo.");
            return;
        }
        if (cantidadRegistrados >= paises.length) {
            JOptionPane.showMessageDialog(this, "Ya se registraron todos los países (" + paises.length + ").");
            return;
        }
        String nombre = txtNombrePais.getText().trim();
        String dt = txtDirectorTecnico.getText().trim();
        if (nombre.isEmpty() || dt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete nombre y director técnico.");
            return;
        }

        paises[cantidadRegistrados] = new Pais(nombre, dt);
        cantidadRegistrados++;
        modeloListaPaises.addElement(nombre + " (DT: " + dt + ")");

        txtNombrePais.setText("");
        txtDirectorTecnico.setText("");
    }

    private void onEditarPais(ActionEvent e) {
        int indice = listaPaises.getSelectedIndex();
        if (indice < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un país de la lista para editar.");
            return;
        }
        String nombre = txtNombrePais.getText().trim();
        String dt = txtDirectorTecnico.getText().trim();
        if (nombre.isEmpty() || dt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete nombre y director técnico.");
            return;
        }

        paises[indice].setNombre(nombre);
        paises[indice].setDirectorTecnico(dt);
        modeloListaPaises.set(indice, nombre + " (DT: " + dt + ")");
    }

    private void cargarPaisSeleccionadoEnFormulario() {
        int indice = listaPaises.getSelectedIndex();
        if (indice < 0 || paises == null || paises[indice] == null) return;
        txtNombrePais.setText(paises[indice].getNombre());
        txtDirectorTecnico.setText(paises[indice].getDirectorTecnico());
    }

    
    private void onGenerarDemo(ActionEvent e) {
        if (paises == null) return;
        Random rnd = new Random();

        for (int i = 0; i < paises.length; i++) {
            Pais p = new Pais("País Demo " + (i + 1), "DT Demo " + (i + 1));
            
            for (int j = 0; j < 5; j++) {
                p.agregarJugador(new Jugador("Jugador " + (j + 1) + "-" + (i + 1),
                        j + 1, j == 0 ? "Portero" : "Delantero"));
            }
            paises[i] = p;
        }

        for (int i = 0; i < sedes.length; i++) {
            sedes[i] = new Sede("Estadio Demo " + (i + 1), "Ciudad Demo " + (i + 1),
                    30000 + rnd.nextInt(50000));
        }

        for (int i = 0; i < arbitros.length; i++) {
            arbitros[i] = new Arbitro("Árbitro Demo " + (i + 1), "País Demo");
        }

        
        modeloListaPaises.clear();
        cantidadRegistrados = paises.length;
        for (Pais p : paises) {
            modeloListaPaises.addElement(p.getNombre() + " (DT: " + p.getDirectorTecnico() + ")");
        }

        JOptionPane.showMessageDialog(this, "Datos de demostración generados con éxito.");
    }

    
    public Pais[] getPaises() { return paises; }
    public Sede[] getSedes() { return sedes; }
    public Arbitro[] getArbitros() { return arbitros; }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Modulo1Admin::new);
    }
}