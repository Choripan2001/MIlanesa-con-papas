/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package AreaAdministrativa.Actividades;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.demo.DateChooserPanel;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import lombok.Getter;
import lombok.Setter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;


/**
 *
 * @author PC
 */
public class Actividades_Pila extends javax.swing.JPanel {
    
    @Getter
    @Setter
    public class Nodo {
        private String codigo;
        private String nombre;
        private String fecha;
        private Nodo sig;
        public static int contador = 0;

        public Nodo(String cod, String nom, String fec) {
            this.codigo = cod;
            this.nombre = nom;
            this.fecha = fec;
            sig = null;
            contador++;
        }
    }
    
    public Nodo cima;
    public Nodo mostrar;
    int num = 0, tam = 6;
    
    DefaultTableModel hp = new DefaultTableModel();
   
    public Actividades_Pila() {
        initComponents();
        miModelo = new DefaultTableModel(data,cabecera);
        jTable1.setModel(miModelo); 
        DefaultTableCellRenderer centrar = new DefaultTableCellRenderer();
        centrar.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        for (int i = 0; i < cabecera.length; i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(centrar);
        }
        
        cargarArchivoTxt();
    }
    
    // --------------------- METODOS PARA LA TABLA --------------------------
  
    DefaultTableModel miModelo;
    String[] cabecera={"N°Codigo","Nombre","Fecha"};
    String[][] data={};
    
    public void VerDatos() {
        String cod, nom, fec;
        Nodo aux = cima;
        num = 0;
        miModelo.setRowCount(0);
        while (aux != null) {
            cod = aux.codigo;
            nom = aux.nombre;
            fec = aux.fecha;        
            
            num++;
            Object[] fila = {cod, nom, fec};
            miModelo.addRow(fila);
            aux = aux.sig;
        }
    }
   
    public void LimpiarEntradas() {
        TextCodigo.setText("");
        TextNombre.setText("");
        TextFec.setDate(null);
    }
    
    public void vaciar_tabla(){
        int filas =jTable1.getRowCount();
        for (int i = 0; i < filas; i++) {
            miModelo.removeRow(0);
        }
    }
    
    public void mensaje(String data) {
        StringTokenizer st = new StringTokenizer(data, ",");
        //Partiendo el texto
        String c = st.nextToken();
        String n = st.nextToken();
        String f = st.nextToken();
        String datos = "Descripcion de la actividad en proceso: \n"
                + "Código : " + c + "\n"
                + "Nombre : " + n + "\n"
                + "Nombre : " + f + "\n";
        JOptionPane.showMessageDialog(this, datos);
    }
    
    public void restaurar( ) {
        cima = null;
        vaciar_tabla();
        num = 0;
        Nodo.contador = 0;
    }
    
    public String getFecha(JDateChooser jd) {
        SimpleDateFormat Formato = new SimpleDateFormat("dd/MM/yyyy");
        if (jd.getDate() != null) {
            return Formato.format(jd.getDate());
        } else {
            return null;
        }
    }
    
    // -------------------------------------------------------------

    // --------------------- METODOS CRUD --------------------------
    
    public void insertarPila(String cod, String nombre, String fecha) {
        Nodo nuevo = new Nodo(cod,nombre ,fecha);
        
        if (cima == null) {
            cima = nuevo;
        } else {
            nuevo.sig = cima;
            cima = nuevo;
        }
    }
    
    public String cima() {
        String eliminado = "";
        Nodo aux = cima;
        String c = aux.codigo;
        String n = aux.nombre;
        String f = aux.fecha;
        eliminado = c + "," + n + "," + f + ",";
        cima = cima.sig;
        return eliminado;
    }
    
    // -------------------------------------------------------------
    
    // --------------------- METODOS RECURSIVOS --------------------------

    public Nodo Buscar(Nodo inicio, String cod) {
        if (inicio == null) {
            return null;
        }

        if (cod.equalsIgnoreCase(inicio.codigo)) {
           return inicio; 
        } else {
           return Buscar(inicio.sig, cod);
        }
    }
    
    public static int contarElementos(Nodo inicio) {
        if (inicio == null) {
            return 0; 
        } else {
            return 1 + contarElementos(inicio.sig);
        }
    }
    
    public static boolean numPositivo(int num){
        if(num>0){
            return true;
        }else{
            return !numNegativo(num);
        }
    }
    
    public static boolean numNegativo(int num){
        if(num<0){
            return true;
        }else{
            return !numPositivo(num);
        }
    }
    
    // -------------------------------------------------------------
    
    // --------------------- METODOS PARA GUARDAR Y CARGAR TXT --------------------------
    
    public void guardarEnArchivoTxt() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("pila.txt"))) {
            Nodo actual = cima;
            while (actual != null) {
                writer.write(actual.getCodigo() + "," + actual.getNombre() + "," + actual.getFecha());
                writer.newLine();
                actual = actual.getSig();
            }
            JOptionPane.showMessageDialog(null, "Datos guardados en pila.txt", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar en el archivo", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    public void cargarArchivoPila() {
        try (BufferedReader reader = new BufferedReader(new FileReader("pila.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    insertarPila(parts[0], parts[1], parts[2]);
                }
            }
            actualizarTabla();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar desde el archivo", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void actualizarTabla() {
        
        int elementosEnPila = contarElementos(cima);

        miModelo.setRowCount(elementosEnPila);

        Nodo actual = cima;
        for (int i = elementosEnPila - 1; i >= 0; i--) {
            jTable1.setValueAt(actual.getCodigo(), i, 0);
            jTable1.setValueAt(actual.getNombre(), i, 1);
            jTable1.setValueAt(actual.getFecha(), i, 2);
            actual = actual.getSig();
        }
    }

    public void cargarArchivoTxt() {
        cargarArchivoPila();
        VerDatos(); 
    }
    
    // -------------------------------------------------------------
    
    // --------------------- METODOS PARA GUARDAR DATOS EN UN PDF Y EXCEL --------------------------
    
    public void convertirJTableAPDF() {
        Document document = new Document();
        try {
            
            String rutaPDF = "actividades_pilas.pdf";
            PdfWriter.getInstance(document, new FileOutputStream(rutaPDF));
            document.open();

            try {
                Image imagen = Image.getInstance("C:\\Users\\jr860\\Desktop\\ProyectoJava\\Imagenes Java\\newton college.png");
                imagen.scaleToFit(100, 50); 
                document.add(imagen);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al cargar la imagen", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }

            
            Paragraph infoDerecha = new Paragraph();
            infoDerecha.setAlignment(Element.ALIGN_RIGHT);

            // Usuario
            Chunk usuarioChunk = new Chunk("Usuario: Administrador", FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK));
            infoDerecha.add(usuarioChunk);
            Chunk fechaChunk = new Chunk("\nFecha: " + obtenerFechaActual(), FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK));
            infoDerecha.add(fechaChunk);
            Chunk horaChunk = new Chunk("\nHora: " + obtenerHoraActual(), FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK));
            infoDerecha.add(horaChunk);

            
            infoDerecha.setSpacingBefore(-50);

            document.add(infoDerecha);

            LineSeparator separator = new LineSeparator();
            separator.setLineColor(BaseColor.BLACK);
            separator.setLineWidth(1);
            Chunk linebreak = new Chunk(separator);
            document.add(linebreak);

            Paragraph espacio = new Paragraph(" ");
            document.add(espacio);

            BaseColor titleColor = new BaseColor(23, 32, 49); 
            com.itextpdf.text.Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, titleColor);

            String textoTitulo = "Reporte de Actividades";
            Paragraph titulo = new Paragraph(textoTitulo.toUpperCase(), titleFont);
            titulo.setAlignment(Element.ALIGN_CENTER); // Centrar el texto
            titulo.setSpacingAfter(30f);
            document.add(titulo);

            PdfPTable pdfTable = new PdfPTable(3); 
            pdfTable.setWidthPercentage(100);
            pdfTable.setHorizontalAlignment(Element.ALIGN_CENTER);

            pdfTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            pdfTable.getDefaultCell().setBackgroundColor(BaseColor.GRAY);

            com.itextpdf.text.Font headerFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 12, com.itextpdf.text.Font.BOLD, BaseColor.WHITE);
            BaseColor headerBackgroundColor = new BaseColor(6, 75, 84); 

            DefaultTableModel miModelo = (DefaultTableModel) jTable1.getModel(); 
            for (int i = 0; i < miModelo.getColumnCount(); i++) {
                PdfPCell headerCell = new PdfPCell(new Phrase(miModelo.getColumnName(i), headerFont));
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                headerCell.setBackgroundColor(headerBackgroundColor);

                headerCell.setFixedHeight(30f); 

                pdfTable.addCell(headerCell);
            }
            
            com.itextpdf.text.Font dataFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 10, com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);

            float alturaFila = 20f; 

            for (int i = 0; i < miModelo.getRowCount(); i++) {
                for (int j = 0; j < miModelo.getColumnCount(); j++) {
                    Object cellValue = miModelo.getValueAt(i, j);
                    PdfPCell dataCell = new PdfPCell(new Phrase(cellValue != null ? cellValue.toString() : "", dataFont));
                    dataCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    dataCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    dataCell.setFixedHeight(alturaFila);
                    pdfTable.addCell(dataCell);
                }
            }
            
            document.add(pdfTable);

        } catch (DocumentException | IOException e) {
            JOptionPane.showMessageDialog(null, "Error al generar el PDF", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
    
    private String obtenerFechaActual() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String obtenerHoraActual() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    public void convertirJTableAExcel() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        try {
 
            Sheet sheet = workbook.createSheet("Actividades");
            int filaInicio = 3; 
            int columnaInicio = 2;

            CellStyle titleStyle = workbook.createCellStyle();
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
            titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            XSSFFont titleFont = workbook.createFont();
            titleFont.setFontHeightInPoints((short) 18);
            titleFont.setBold(true);
            titleStyle.setFont(titleFont);

            Row titleRow = sheet.createRow(filaInicio);
            Cell titleCell = titleRow.createCell(columnaInicio);
            titleCell.setCellValue("Reporte de Actividades");
            titleCell.setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(filaInicio, filaInicio, columnaInicio, columnaInicio + miModelo.getColumnCount() - 1));

            sheet.createRow(filaInicio + 1);

            byte[] rgb = {(byte) 6, (byte) 75, (byte) 84};
            XSSFColor customColor = new XSSFColor(new java.awt.Color(rgb[0] & 0xFF, rgb[1] & 0xFF, rgb[2] & 0xFF), null);

            // Establecer el estilo para los encabezados
            XSSFCellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle.setFillForegroundColor(customColor);
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            XSSFFont headerFont = workbook.createFont();
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            Row headerRow = sheet.createRow(filaInicio + 2);
            for (int i = 0; i < miModelo.getColumnCount(); i++) {
                Cell cell = headerRow.createCell(columnaInicio + i);
                cell.setCellValue(miModelo.getColumnName(i));
                cell.setCellStyle(headerStyle);
            }

            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setAlignment(HorizontalAlignment.CENTER);
            dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            dataStyle.setBorderTop(BorderStyle.THIN);
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);

            for (int i = 0; i < miModelo.getRowCount(); i++) {
                Row row = sheet.createRow(filaInicio + 3 + i);
                for (int j = 0; j < miModelo.getColumnCount(); j++) {
                    Cell cell = row.createCell(columnaInicio + j);
                    cell.setCellValue(miModelo.getValueAt(i, j).toString());
                    cell.setCellStyle(dataStyle); 
                }
            }

            for (int i = 0; i < miModelo.getColumnCount(); i++) {
                sheet.setColumnWidth(i + 2, 15 * 256); // Ajusta este valor según sea necesario
            }

            String rutaExcel = "actividades_pilas.xlsx";
            try (FileOutputStream fileOut = new FileOutputStream(rutaExcel)) {
                workbook.write(fileOut);
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al generar el Excel", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    // -------------------------------------------------------------
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnAgregar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnConsultar = new javax.swing.JButton();
        btnVaciar = new javax.swing.JButton();
        Codigo2 = new javax.swing.JLabel();
        TextCodigo = new javax.swing.JTextField();
        Codigo5 = new javax.swing.JLabel();
        TextNombre = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        TextFec = new com.toedter.calendar.JDateChooser();
        BtnActualizar = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 51));
        jLabel7.setText("CRONOGRAMA DE ACTIVIDADES - PILAS");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        btnAgregar.setIcon(new javax.swing.ImageIcon("C:\\Users\\jr860\\Desktop\\ProyectoJava\\Imagenes Java\\agregar_dato.png")); // NOI18N
        btnAgregar.setText("AGREGAR");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new javax.swing.ImageIcon("C:\\Users\\jr860\\Desktop\\ProyectoJava\\Imagenes Java\\eliminar_datopng.png")); // NOI18N
        btnEliminar.setText("ACTIVIDAD REALIZADA");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnConsultar.setIcon(new javax.swing.ImageIcon("C:\\Users\\jr860\\Desktop\\ProyectoJava\\Imagenes Java\\buscar_dato.png")); // NOI18N
        btnConsultar.setText("BUSCAR");
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });

        btnVaciar.setIcon(new javax.swing.ImageIcon("C:\\Users\\jr860\\Desktop\\ProyectoJava\\Imagenes Java\\restaurar_dato.png")); // NOI18N
        btnVaciar.setText("RESTAURAR");
        btnVaciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVaciarActionPerformed(evt);
            }
        });

        Codigo2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Codigo2.setForeground(new java.awt.Color(0, 51, 51));
        Codigo2.setText("N°Codigo:");

        TextCodigo.setBackground(new java.awt.Color(255, 253, 253));
        TextCodigo.setForeground(new java.awt.Color(0, 51, 51));
        TextCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextCodigoActionPerformed(evt);
            }
        });
        TextCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextCodigoKeyPressed(evt);
            }
        });

        Codigo5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Codigo5.setForeground(new java.awt.Color(0, 51, 51));
        Codigo5.setText("Nombre:");

        TextNombre.setBackground(new java.awt.Color(255, 253, 253));
        TextNombre.setForeground(new java.awt.Color(0, 51, 51));
        TextNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextNombreActionPerformed(evt);
            }
        });
        TextNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextNombreKeyPressed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 51));
        jLabel8.setText("Fecha Actividad:");

        TextFec.setForeground(new java.awt.Color(204, 204, 204));
        TextFec.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextFecKeyPressed(evt);
            }
        });

        BtnActualizar.setIcon(new javax.swing.ImageIcon("C:\\Users\\jr860\\Desktop\\ProyectoJava\\Imagenes Java\\actualizar_dato.png")); // NOI18N
        BtnActualizar.setText("ACTUALIZAR");
        BtnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnActualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(117, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(227, 227, 227))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(Codigo2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(TextCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(69, 69, 69)
                                        .addComponent(Codigo5, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(TextNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(72, 72, 72))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnAgregar)
                                        .addGap(44, 44, 44)
                                        .addComponent(btnEliminar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnVaciar)
                                        .addGap(61, 61, 61)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(BtnActualizar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnConsultar))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(18, 18, 18)
                                        .addComponent(TextFec, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(36, 36, 36))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Codigo2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextCodigo)
                            .addComponent(Codigo5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextNombre)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(TextFec, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVaciar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(324, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // Capturando la información de los objetos
        String cod = TextCodigo.getText().toUpperCase();
        String nom = TextNombre.getText().toUpperCase();
        Date fechaActividad = TextFec.getDate();
        
        if (!cod.isEmpty() && fechaActividad != null) {
            Calendar hoy = Calendar.getInstance();
            Calendar actividad = Calendar.getInstance();
            actividad.setTime(fechaActividad);

            if (hoy.get(Calendar.YEAR) != actividad.get(Calendar.YEAR)) {
                JOptionPane.showMessageDialog(this, "La fecha seleccionada no es del presente año.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int co = Integer.parseInt(cod);

                if (numPositivo(co)) {

                    insertarPila(cod, nom, getFecha(TextFec));

                    LimpiarEntradas();
                    VerDatos();
                    if (Nodo.contador == tam - 1) {
                        JOptionPane.showMessageDialog(null, "La cola está casi llena, contiene " + Nodo.contador + " elementos de " + tam);
                    }

                    guardarEnArchivoTxt();
                    convertirJTableAExcel();
                    convertirJTableAPDF();
                    JOptionPane.showMessageDialog(null, "EXCEL y PDF actualizados correctamente");

                } else {
                    JOptionPane.showMessageDialog(this, "No puede ingresar un número negativo en el código", "Error", JOptionPane.ERROR_MESSAGE);
                    TextCodigo.setText("");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        //Este bonton es  consultar, su funcion es por que por medio del codigo del trabajador este pueda consultar la demas información
        String cod = JOptionPane.showInputDialog(this, "Ingrese un codigo por favor");
        if (cod.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Ingrese un codigo por favor");
        } else {
            // Llamada a la función que retorna la posición del dato buscado
            mostrar = Buscar(cima, cod);

            // Verificando el puntero pFound para mostrar la información buscada
            if (mostrar != null) {
                String mensaje = "Información encontrada:\n\n" +
                        "Nombre: " + mostrar.nombre + "\n" +
                        "Fecha de la Actividad: " + mostrar.fecha;
                
                JOptionPane.showMessageDialog(this, mensaje, "Información de la consulta", JOptionPane.INFORMATION_MESSAGE);

                int confirmacion = JOptionPane.showConfirmDialog(this, "¿Desea cambiar la información?", "Modificar la información", JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    TextCodigo.setText(mostrar.codigo);
                    TextNombre.setText(mostrar.nombre);
                }
            } else {
                JOptionPane.showMessageDialog(this, "El codigo:" + cod + " no esta en la lista.");
            }
        }
    }//GEN-LAST:event_btnConsultarActionPerformed

    private void btnVaciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVaciarActionPerformed
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de restaurar los datos?", "Confirmación", JOptionPane.YES_NO_OPTION);
    
        if (confirmacion == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Se eliminaron todos los elementos de la pila");
            restaurar();
            guardarEnArchivoTxt();
            convertirJTableAExcel();
            convertirJTableAPDF();
            JOptionPane.showMessageDialog(null, "EXCEL y PDF actualizados correctamente");
        }
    }//GEN-LAST:event_btnVaciarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (cima == null) {
            JOptionPane.showMessageDialog(this, "La pila está vacía");
            TextCodigo.requestFocus();
        } else {
            int confirmacion = JOptionPane.showConfirmDialog(this, "¿La actividad ha sido realizada?", "Confirmación", JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                String dato = cima();
                mensaje(dato);
                JOptionPane.showMessageDialog(this, "Actividad realizada");
                LimpiarEntradas();
                VerDatos();
                Nodo.contador--;
                if (cima == null) {
                    JOptionPane.showMessageDialog(this, "La pila está vacía");          
                }
                
                guardarEnArchivoTxt();
                convertirJTableAExcel();
                convertirJTableAPDF();
                JOptionPane.showMessageDialog(null, "EXCEL y PDF actualizados correctamente");
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void TextCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextCodigoActionPerformed

    private void TextCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextCodigoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TextNombre.grabFocus();
        }
    }//GEN-LAST:event_TextCodigoKeyPressed

    private void TextNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextNombreActionPerformed

    private void TextNombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextNombreKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TextFec.grabFocus();
        }
    }//GEN-LAST:event_TextNombreKeyPressed

    private void TextFecKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextFecKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnAgregar.grabFocus();
            String cod = TextCodigo.getText().toUpperCase();
            String nom = TextNombre.getText().toUpperCase();
            Date fechaActividad = TextFec.getDate();

            if (Nodo.contador == tam) {
                JOptionPane.showMessageDialog(null, "La cola está llena, contiene " + Nodo.contador + " elementos de " + tam);
            } else {
                if (!cod.isEmpty() && fechaActividad != null) {
                    Calendar hoy = Calendar.getInstance();
                    Calendar actividad = Calendar.getInstance();
                    actividad.setTime(fechaActividad);

                    if (hoy.get(Calendar.YEAR) != actividad.get(Calendar.YEAR)) {
                        JOptionPane.showMessageDialog(this, "La fecha seleccionada no es del presente año.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        int co = Integer.parseInt(cod);

                        if (numPositivo(co)) {

                            insertarPila(cod, nom, getFecha(TextFec));

                            LimpiarEntradas();
                            VerDatos();
                            if (Nodo.contador == tam - 1) {
                                JOptionPane.showMessageDialog(null, "La cola está casi llena, contiene " + Nodo.contador + " elementos de " + tam);
                            }

                            guardarEnArchivoTxt();
                            convertirJTableAExcel();
                            convertirJTableAPDF();
                            JOptionPane.showMessageDialog(null, "EXCEL y PDF actualizados correctamente");

                        } else {
                            JOptionPane.showMessageDialog(this, "No puede ingresar un número negativo en el código", "Error", JOptionPane.ERROR_MESSAGE);
                            TextCodigo.setText("");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_TextFecKeyPressed

    private void BtnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnActualizarActionPerformed
        String codigoAActualizar = TextCodigo.getText().toUpperCase();

        Nodo nodoAActualizar = Buscar(cima, codigoAActualizar);

        if (nodoAActualizar != null) {

            Date fechaActividad = TextFec.getDate();

            if (fechaActividad != null) {
                Calendar hoy = Calendar.getInstance();
                Calendar actividad = Calendar.getInstance();
                actividad.setTime(fechaActividad);

                if (hoy.get(Calendar.YEAR) != actividad.get(Calendar.YEAR)) {
                    JOptionPane.showMessageDialog(this, "La fecha seleccionada no es del presente año.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Actualizar los datos del nodo
                    nodoAActualizar.codigo = TextCodigo.getText();
                    nodoAActualizar.nombre = TextNombre.getText().toUpperCase();
                    nodoAActualizar.fecha = getFecha(TextFec);

                    LimpiarEntradas();
                    VerDatos();
                    convertirJTableAExcel();
                    convertirJTableAPDF();
                    
                }
            } else {
                // Conservar la fecha existente si no se ingresó una nueva
                nodoAActualizar.codigo = TextCodigo.getText();
                nodoAActualizar.nombre = TextNombre.getText().toUpperCase();

                LimpiarEntradas();
                VerDatos();
                convertirJTableAExcel();
                convertirJTableAPDF();
            }
            
            guardarEnArchivoTxt();
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró el código seleccionado en la pila.");
        }
    }//GEN-LAST:event_BtnActualizarActionPerformed
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnActualizar;
    private javax.swing.JLabel Codigo2;
    private javax.swing.JLabel Codigo5;
    private javax.swing.JTextField TextCodigo;
    private com.toedter.calendar.JDateChooser TextFec;
    private javax.swing.JTextField TextNombre;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnVaciar;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
