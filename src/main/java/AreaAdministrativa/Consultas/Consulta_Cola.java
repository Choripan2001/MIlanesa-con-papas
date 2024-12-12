/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package AreaAdministrativa.Consultas;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.ImageIcon;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 *
 * @author PC
 */
public class Consulta_Cola extends javax.swing.JPanel {
    
    @Getter
    @Setter
    public class Nodo {

        private String codigo;
        private String consulta;
        private String nombre;
        private String descripcion;
        private String estado;
        private Nodo sig;
        public static int contador = 0;

        public Nodo(String cod,String csl ,String nom, String desc, String est) {
            this.codigo = cod;
            this.consulta = csl;
            this.nombre = nom;
            this.descripcion = desc;
            this.estado = est;
            sig = null;
            contador++;
        }
    }
    
    public Nodo frente, fincola;
    public Nodo mostrar;
    int num = 0, tam = 3;
    
    
    DefaultTableModel hp = new DefaultTableModel();
    
    public Consulta_Cola() {
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
    String[] cabecera={"N°Codigo","Tipo Consulta","Nombre","Descripcion","Estado"};
    String[][] data={};
    
    private void mensaje(String data) {
        StringTokenizer st = new StringTokenizer(data, ",");
        //Partiendo el texto
        String c = st.nextToken();
        String a = st.nextToken();
        String ap = st.nextToken();
        String fi = st.nextToken();
        String g = st.nextToken();
        String datos = "Descripcion del dato en Proceso: \n"
                + "Código : " + c + "\n"
                + "Consutla : " + a + "\n"
                + "Nombre : " + ap + "\n"
                + "Descripcion: " + fi + "\n"
                + "Estado : " + g + "\n";
        JOptionPane.showMessageDialog(this, datos); 
    }
    
    public void VerDatos() {
        //variables para recorrer la lista
        String cod, con, nom, desc, est;
        int tel;
        
        Nodo aux = frente;
        vaciar_tabla();//limpiando la tabla
        num = 0;
        while (aux != null) {
            cod = aux.codigo;
            con = aux.consulta;
            nom = aux.nombre;
            desc = aux.descripcion;
            est = aux.estado;         
            
            num++;
            Object[] fila = {cod, con, nom, desc,est};
            miModelo.addRow(fila);
            aux = aux.sig;
        }
    }
  
    public void LimpiarEntradas() {
        TextCodigo.setText("");
        TextConsulta.setSelectedItem("SELECCIONE");
        TextNombre.setText("");
        TextDescripcion.setText("");
        TextEstado.setSelectedItem(1); 
    }
    
    public void vaciar_tabla(){
        //obteniendo el número de filas de la tabla
        int filas =jTable1.getRowCount();
        for (int i = 0; i < filas; i++) {
            miModelo.removeRow(0);
        }
    }
    
    public void restaurar() {
        frente = null;
        fincola = null;
        vaciar_tabla();
        num = 0;
        Nodo.contador = 0;
    }
    
    // -------------------------------------------------------------

    // --------------------- METODOS CRUD --------------------------
    
    public void encolar(String codi, String cons, String noms, String descr, String est) {
        Nodo nuevo = new Nodo(codi,cons ,noms, descr, est);
        //realizando los enlaces correspondientes
        if (frente == null) {
            frente = nuevo;
        } else {
            fincola.sig = nuevo;
        }
        fincola = nuevo;
        fincola.sig = null;
        
    }
    
    public String frente() {
        String eliminado = "";
        Nodo aux = frente;
        //Extrayendo los datos a ser eliminados
        String c = aux.codigo;
        String a = aux.consulta;
        String ap = aux.nombre;
        String fi = aux.descripcion;
        String g = aux.estado;
        eliminado = c + "," + a + "," + ap + "," + fi + "," + g + ",";
        frente = frente.sig;
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("cola.txt"))) {
            Nodo actual = frente;
            while (actual != null) {
                writer.write(actual.codigo + "," + actual.consulta + "," + actual.nombre + "," + actual.descripcion + "," + actual.estado);
                writer.newLine();
                actual = actual.sig;
            }
            JOptionPane.showMessageDialog(null, "Datos guardados en cola.txt", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar en el archivo", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    public void cargarArchivoCola() {
        try (BufferedReader reader = new BufferedReader(new FileReader("cola.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    encolar(parts[0], parts[1], parts[2], parts[3], parts[4]);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar desde el archivo", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    public void cargarArchivoTxt() {
        cargarArchivoCola();
        VerDatos(); 
    }
     
    // -------------------------------------------------------------
    
    // --------------------- METODOS PARA GUARDAR DATOS EN UN PDF Y EXCEL --------------------------
    
    public void convertirJTableAPDF() {
        Document document = new Document();
        try {
            
            String rutaPDF = "consultas_cola.pdf";
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

            String textoTitulo = "Reporte de Consultas";
            Paragraph titulo = new Paragraph(textoTitulo.toUpperCase(), titleFont);
            titulo.setAlignment(Element.ALIGN_CENTER); 
            titulo.setSpacingAfter(30f);
            document.add(titulo);

            PdfPTable pdfTable = new PdfPTable(5); 
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
 
            Sheet sheet = workbook.createSheet("Consultas");
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
            titleCell.setCellValue("Reporte de Consultas");
            titleCell.setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(filaInicio, filaInicio, columnaInicio, columnaInicio + miModelo.getColumnCount() - 1));

            sheet.createRow(filaInicio + 1);

            byte[] rgb = {(byte) 6, (byte) 75, (byte) 84};
            XSSFColor customColor = new XSSFColor(new java.awt.Color(rgb[0] & 0xFF, rgb[1] & 0xFF, rgb[2] & 0xFF), null);

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
                sheet.setColumnWidth(i + 2, 15 * 256); 
            }

            String rutaExcel = "consultas_cola.xlsx";
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
        Consulta = new javax.swing.JLabel();
        Codigo2 = new javax.swing.JLabel();
        TextCodigo = new javax.swing.JTextField();
        Codigo3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        BtnAgregar = new javax.swing.JButton();
        BtnEliminar = new javax.swing.JButton();
        BtnRestaurar = new javax.swing.JButton();
        TextEstado = new javax.swing.JComboBox<>();
        TextConsulta = new javax.swing.JComboBox<>();
        Codigo4 = new javax.swing.JLabel();
        TextDescripcion = new javax.swing.JTextField();
        Codigo5 = new javax.swing.JLabel();
        TextNombre = new javax.swing.JTextField();
        BtnConsultar = new javax.swing.JButton();
        BtnActualizar = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 51));
        jLabel7.setText("CONSULTAS - COLAS");

        Consulta.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Consulta.setForeground(new java.awt.Color(0, 51, 51));
        Consulta.setText("Tipo de Consulta:");

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

        Codigo3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Codigo3.setForeground(new java.awt.Color(0, 51, 51));
        Codigo3.setText("Estado:");

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

        BtnAgregar.setIcon(new javax.swing.ImageIcon("C:\\Users\\jr860\\Desktop\\ProyectoJava\\Imagenes Java\\agregar_dato.png")); // NOI18N
        BtnAgregar.setText("AGREGAR");
        BtnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAgregarActionPerformed(evt);
            }
        });

        BtnEliminar.setIcon(new javax.swing.ImageIcon("C:\\Users\\jr860\\Desktop\\ProyectoJava\\Imagenes Java\\eliminar_datopng.png")); // NOI18N
        BtnEliminar.setText("CONSULTA REALIZADA");
        BtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEliminarActionPerformed(evt);
            }
        });

        BtnRestaurar.setIcon(new javax.swing.ImageIcon("C:\\Users\\jr860\\Desktop\\ProyectoJava\\Imagenes Java\\restaurar_dato.png")); // NOI18N
        BtnRestaurar.setText("RESTAURAR");
        BtnRestaurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRestaurarActionPerformed(evt);
            }
        });

        TextEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PENDIENTE" }));
        TextEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextEstadoActionPerformed(evt);
            }
        });
        TextEstado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextEstadoKeyPressed(evt);
            }
        });

        TextConsulta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECCIONE", "QUEJAS", "PAGOS", "ACTIVIDADES" }));
        TextConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextConsultaActionPerformed(evt);
            }
        });
        TextConsulta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextConsultaKeyPressed(evt);
            }
        });

        Codigo4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Codigo4.setForeground(new java.awt.Color(0, 51, 51));
        Codigo4.setText("Descripcion:");

        TextDescripcion.setBackground(new java.awt.Color(255, 253, 253));
        TextDescripcion.setForeground(new java.awt.Color(0, 51, 51));
        TextDescripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextDescripcionActionPerformed(evt);
            }
        });
        TextDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextDescripcionKeyPressed(evt);
            }
        });

        Codigo5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Codigo5.setForeground(new java.awt.Color(0, 51, 51));
        Codigo5.setText("Nombre Pers.:");

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

        BtnConsultar.setIcon(new javax.swing.ImageIcon("C:\\Users\\jr860\\Desktop\\ProyectoJava\\Imagenes Java\\buscar_dato.png")); // NOI18N
        BtnConsultar.setText("BUSCAR");
        BtnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnConsultarActionPerformed(evt);
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
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addGap(43, 43, 43))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(142, 142, 142)
                                .addComponent(Codigo4, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(TextDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(BtnAgregar)
                                .addGap(58, 58, 58)
                                .addComponent(BtnEliminar)
                                .addGap(55, 55, 55)
                                .addComponent(BtnRestaurar)))
                        .addGap(58, 58, 58)
                        .addComponent(Codigo3, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TextEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 117, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(327, 327, 327))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(Codigo2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TextCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(Consulta, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(BtnActualizar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BtnConsultar)
                        .addGap(43, 43, 43))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(TextConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                        .addComponent(Codigo5)
                        .addGap(18, 18, 18)
                        .addComponent(TextNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Codigo5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(TextNombre))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Consulta, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(TextConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Codigo2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(TextCodigo)))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Codigo3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Codigo4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextDescripcion))
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnRestaurar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void TextCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextCodigoActionPerformed

    private void TextCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextCodigoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TextConsulta.grabFocus();
        }
    }//GEN-LAST:event_TextCodigoKeyPressed

    private void BtnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAgregarActionPerformed
        //Capturando la informacion de los objetos
        String cod = TextCodigo.getText().toUpperCase();
        String cons = (String) TextConsulta.getSelectedItem();
        String nom = TextNombre.getText().toUpperCase();
        String des = TextDescripcion.getText().toUpperCase();
        String est = TextEstado.getSelectedItem().toString().toUpperCase();
        
        if (des.isEmpty()) {
            des = "--";
        }

        if (!cod.isEmpty() && !cons.equals("SELECCIONE") && !nom.isEmpty()) {
            int co = Integer.parseInt(cod);

            if (numPositivo(co)) {

                encolar(cod, cons, nom, des, est);
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
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_BtnAgregarActionPerformed

    private void BtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarActionPerformed
        if (frente == null) {
        JOptionPane.showMessageDialog(this, "La cola está vacía");
        TextCodigo.requestFocus();
        } else {
            int confirmacion = JOptionPane.showConfirmDialog(this, "¿La consulta ha sido realizada?", "Confirmación", JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                String dato = frente();
                mensaje(dato);
                JOptionPane.showMessageDialog(this, "Consulta completa");
                LimpiarEntradas();
                VerDatos();
                Nodo.contador--;
                if (frente == null) {
                    JOptionPane.showMessageDialog(this, "La cola está vacía");
                }
                
                guardarEnArchivoTxt();
                convertirJTableAExcel();
                convertirJTableAPDF();
                JOptionPane.showMessageDialog(null, "EXCEL y PDF actualizados correctamente");
            }
        }
    }//GEN-LAST:event_BtnEliminarActionPerformed

    private void BtnRestaurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRestaurarActionPerformed
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de restaurar los datos?", "Confirmación", JOptionPane.YES_NO_OPTION);
    
        if (confirmacion == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Se eliminaron todos los elementos de la cola");
            restaurar();
            JOptionPane.showMessageDialog(null, "Cola vacia");
            guardarEnArchivoTxt();
            convertirJTableAExcel();
            convertirJTableAPDF();
            JOptionPane.showMessageDialog(null, "EXCEL y PDF actualizados correctamente");
        }
    }//GEN-LAST:event_BtnRestaurarActionPerformed

    private void TextDescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextDescripcionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextDescripcionActionPerformed

    private void TextDescripcionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextDescripcionKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextDescripcionKeyPressed

    private void TextNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextNombreActionPerformed

    private void TextNombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextNombreKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TextDescripcion.grabFocus();
        }
    }//GEN-LAST:event_TextNombreKeyPressed

    private void TextConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextConsultaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextConsultaActionPerformed

    private void TextEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextEstadoActionPerformed

    private void TextEstadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextEstadoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnAgregar.grabFocus();
            //Capturando la informacion de los objetos
            String cod = TextCodigo.getText().toUpperCase();
            String cons = (String) TextConsulta.getSelectedItem();
            String nom = TextNombre.getText().toUpperCase();
            String des = TextDescripcion.getText().toUpperCase();
            String est = TextEstado.getSelectedItem().toString().toUpperCase();

            if (des.isEmpty()) {
                des = "--";
            }

            if (!cod.isEmpty() && !cons.equals("SELECCIONE") && !nom.isEmpty()) {
                int co = Integer.parseInt(cod);

                if (numPositivo(co)) {

                    encolar(cod, cons, nom, des, est);
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
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_TextEstadoKeyPressed

    private void TextConsultaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextConsultaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TextNombre.grabFocus();
        }
    }//GEN-LAST:event_TextConsultaKeyPressed

    private void BtnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnConsultarActionPerformed
        //ImageIcon Check = new ImageIcon("C:\\Users\\jr860\\Desktop\\ProyectoJava\\Imagenes Java\\error.png");
        
        String cod = JOptionPane.showInputDialog(this, "Ingrese un codigo por favor");
        if (cod.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Ingrese un codigo por favor");
        } else {
            mostrar = Buscar(frente, cod);
            
            if (mostrar != null) {
                String mensaje = "Información encontrada:\n\n" +
                "Nombres: " + mostrar.nombre + "\n" +
                "Apellidos: " + mostrar.consulta + "\n" +
                "Curso: " + mostrar.descripcion + "\n" +
                "Pago por hora: " + mostrar.estado + "\n";

                JOptionPane.showMessageDialog(this, mensaje, "Información de la consulta", JOptionPane.INFORMATION_MESSAGE);

                int confirmacion = JOptionPane.showConfirmDialog(this, "¿Desea cambiar la información?", "Modificar la información", JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    TextCodigo.setText(mostrar.codigo);
                    TextNombre.setText(mostrar.nombre);
                    String consulta = mostrar.consulta;
                    TextConsulta.setSelectedItem(consulta);
                    TextDescripcion.setText(mostrar.descripcion);
                    String estado = String.valueOf(mostrar.estado);
                    TextEstado.setSelectedItem(estado);
                }
            } else {
                JOptionPane.showMessageDialog(this, "El código " + cod + " no esta en la lista.", null, JOptionPane.PLAIN_MESSAGE);
                //JOptionPane.showMessageDialog(this, "El código " + cod + " no está en la cola.", "Error", JOptionPane.PLAIN_MESSAGE, Check);
            }
        }
    }//GEN-LAST:event_BtnConsultarActionPerformed

    private void BtnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnActualizarActionPerformed
        String codigoAActualizar = TextCodigo.getText().toUpperCase();

        Nodo nodoAActualizar = Buscar(frente, codigoAActualizar);

        if (nodoAActualizar != null) {
            nodoAActualizar.nombre = TextNombre.getText().toUpperCase();
            nodoAActualizar.consulta = (String) TextConsulta.getSelectedItem();
            nodoAActualizar.descripcion = TextDescripcion.getText().toUpperCase();
            nodoAActualizar.estado = (String) TextEstado.getSelectedItem();

            LimpiarEntradas();
            VerDatos();
            guardarEnArchivoTxt();
            convertirJTableAExcel();
            convertirJTableAPDF();
            JOptionPane.showMessageDialog(null, "EXCEL y PDF actualizados correctamente");
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró el nodo con el código ingresado.");
        }
    }//GEN-LAST:event_BtnActualizarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnActualizar;
    private javax.swing.JButton BtnAgregar;
    private javax.swing.JButton BtnConsultar;
    private javax.swing.JButton BtnEliminar;
    private javax.swing.JButton BtnRestaurar;
    private javax.swing.JLabel Codigo2;
    private javax.swing.JLabel Codigo3;
    private javax.swing.JLabel Codigo4;
    private javax.swing.JLabel Codigo5;
    private javax.swing.JLabel Consulta;
    private javax.swing.JTextField TextCodigo;
    private javax.swing.JComboBox<String> TextConsulta;
    private javax.swing.JTextField TextDescripcion;
    private javax.swing.JComboBox<String> TextEstado;
    private javax.swing.JTextField TextNombre;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
