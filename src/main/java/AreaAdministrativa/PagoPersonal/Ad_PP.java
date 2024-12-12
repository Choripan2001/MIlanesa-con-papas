/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package AreaAdministrativa.PagoPersonal;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
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
public class Ad_PP extends javax.swing.JPanel {
    
    @Getter
    @Setter
    public class Nodo {
        private int codigo;
        private String nombre;
        private String apellido;
        private String rol;
        private int pago;
        public static int contador = 0;

        public Nodo(int cod, String nom, String ap, String rol, int pago) {
            this.codigo = cod;
            this.nombre = nom;
            this.apellido = ap;
            this.rol = rol;
            this.pago = pago;
            contador++;
        }
    }
    
    public Nodo ini,fin;
    public Nodo pFound;
    int num=0;
    private int FilaMax = 5;
    
    private DefaultTableModel miModelo;
    
    public Ad_PP() {
        initComponents();
        Object[][] data = new Object[FilaMax][5];
        miModelo = new DefaultTableModel(data, cabecera);
        jTable1.setModel(miModelo);
        DefaultTableCellRenderer centrar = new DefaultTableCellRenderer();
        centrar.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        for (int i = 0; i < cabecera.length; i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(centrar);
        }
        cargarArchivoTxt();
    }
    
    // --------------------- METODOS PARA LA TABLA --------------------------
    
    String[] cabecera={"N°Codigo","Nombre","Apellido","Rol","Monto total"};
    
    public void LimpiarEntradas() {
        TextCodigo.setText("");
        TextNombre.setText("");
        TextAp.setText("");
        TextRol.setSelectedItem("SELECCIONE");
        TextPago.setText("");
    }
    
    // -------------------------------------------------------------

    // --------------------- METODOS HASH --------------------------
    
    public int aritmeticaModular(int codigo) {
        return codigo % FilaMax;
    }
    
    // -------------------------------------------------------------
    
    // --------------------- METODOS RECURSIVOS --------------------------
    
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("hash.txt"))) {
            for (int i = 0; i < miModelo.getRowCount(); i++) {
                for (int j = 0; j < miModelo.getColumnCount(); j++) {
                    Object cellValue = miModelo.getValueAt(i, j);
                    writer.write(cellValue != null ? cellValue.toString() : "");
                    if (j < miModelo.getColumnCount() - 1) {
                        writer.write(",");
                    }
                }
                writer.newLine();
            }
            JOptionPane.showMessageDialog(null, "Datos guardados en hash.txt", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar en el archivo", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void cargarArchivoTxt() {
        try (BufferedReader reader = new BufferedReader(new FileReader("hash.txt"))) {
            String linea;
            int fila = 0;
            while ((linea = reader.readLine()) != null && fila < miModelo.getRowCount()) {
                String[] partes = linea.split(",");
                for (int i = 0; i < partes.length && i < miModelo.getColumnCount(); i++) {
                    // Verificar si la cadena no está vacía antes de convertirla a cadena
                    miModelo.setValueAt(!partes[i].isEmpty() ? partes[i] : null, fila, i);
                }
                fila++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // -------------------------------------------------------------
    
    // --------------------- METODOS PARA GUARDAR DATOS EN UN PDF Y EXCEL --------------------------
    
    public void convertirJTableAPDF() {
        Document document = new Document();
        try {

            String rutaPDF = "PagoPersonal_hash.pdf";
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

            String textoTitulo = "Reporte acerca del Pago de Personal";
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
                    String cellText = (cellValue != null) ? cellValue.toString() : "null";
                    PdfPCell dataCell = new PdfPCell(new Phrase(cellText, dataFont));
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
 
            Sheet sheet = workbook.createSheet("Tabla Hash");
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
            titleCell.setCellValue("Reporte acerca del Pago de Personal");
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

            String rutaExcel = "PagoPersonal_hash.xlsx";
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
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        TextPago = new javax.swing.JTextField();
        TextNombre = new javax.swing.JTextField();
        TextCodigo = new javax.swing.JTextField();
        TextAp = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScroll = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnAgregar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        Genero = new javax.swing.JLabel();
        TextRol = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 51));
        jLabel3.setText("Pago:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 51));
        jLabel4.setText("N°Codigo:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 51));
        jLabel5.setText("Nombres:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 51));
        jLabel6.setText("Apellidos:");

        TextPago.setBackground(new java.awt.Color(255, 253, 253));
        TextPago.setForeground(new java.awt.Color(0, 51, 51));
        TextPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextPagoActionPerformed(evt);
            }
        });

        TextNombre.setBackground(new java.awt.Color(255, 253, 253));
        TextNombre.setForeground(new java.awt.Color(0, 51, 51));

        TextCodigo.setBackground(new java.awt.Color(255, 253, 253));
        TextCodigo.setForeground(new java.awt.Color(0, 51, 51));

        TextAp.setBackground(new java.awt.Color(255, 253, 253));
        TextAp.setForeground(new java.awt.Color(0, 51, 51));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 51));
        jLabel7.setText("PAGO DE PERSONAL DEL PLANTEL EDUCATIVO");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ));
        jScroll.setViewportView(jTable1);

        btnAgregar.setIcon(new javax.swing.ImageIcon("C:\\Users\\jr860\\Desktop\\ProyectoJava\\Imagenes Java\\anadir.png")); // NOI18N
        btnAgregar.setText("AGREGAR");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new javax.swing.ImageIcon("C:\\Users\\jr860\\Desktop\\ProyectoJava\\Imagenes Java\\eliminar_usu.png")); // NOI18N
        btnEliminar.setText("ELIMINAR");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnActualizar.setIcon(new javax.swing.ImageIcon("C:\\Users\\jr860\\Desktop\\ProyectoJava\\Imagenes Java\\actualizar.png")); // NOI18N
        btnActualizar.setText("ACTUALIZAR");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnBuscar.setIcon(new javax.swing.ImageIcon("C:\\Users\\jr860\\Desktop\\ProyectoJava\\Imagenes Java\\buscar.png")); // NOI18N
        btnBuscar.setText("BUSCAR");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        Genero.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Genero.setForeground(new java.awt.Color(0, 51, 51));
        Genero.setText("Rol:");

        TextRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECCIONE", "DIRECTOR", "SUBDIRECTOR", "AUXILIAR", "COORDINADOR", "ADMINISTRADOR", "PERSONAL DE APOYO", "PERSONAL DE LIMPIEZA" }));
        TextRol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextRolActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Genero, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(TextPago, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(TextNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(TextAp, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(TextCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(TextRol, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(59, 59, 59)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnAgregar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addComponent(btnEliminar)
                        .addGap(18, 18, 18)
                        .addComponent(btnActualizar)
                        .addGap(19, 19, 19)
                        .addComponent(btnBuscar))
                    .addComponent(jScroll))
                .addGap(80, 80, 80))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(221, 221, 221)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScroll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextAp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Genero, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(101, 101, 101))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void TextPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextPagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextPagoActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        int codigo = Integer.parseInt(TextCodigo.getText());
        String nombre = TextNombre.getText().toUpperCase();
        String apellido = TextAp.getText().toUpperCase();
        String rol = (String) TextRol.getSelectedItem();
        int pago = Integer.parseInt(TextPago.getText());

        int residuo = aritmeticaModular(codigo);

        int fila = residuo;
        
        if (miModelo.getValueAt(fila, 0) != null && miModelo.getValueAt(fila, 0).equals("-")) {
            // Sobrescribir la posición que contiene "-"
            miModelo.setValueAt(codigo, fila, 0);
            miModelo.setValueAt(nombre, fila, 1);
            miModelo.setValueAt(apellido, fila, 2);
            miModelo.setValueAt(rol, fila, 3);
            miModelo.setValueAt(pago, fila, 4);
        } else {
            while (miModelo.getValueAt(fila, 0) != null && !miModelo.getValueAt(fila, 0).equals("-")) {
                fila = (fila + 1) % FilaMax; 
            }
            miModelo.setValueAt(codigo, fila, 0);
            miModelo.setValueAt(nombre, fila, 1);
            miModelo.setValueAt(apellido, fila, 2);
            miModelo.setValueAt(rol, fila, 3);
            miModelo.setValueAt(pago, fila, 4);
        }

        LimpiarEntradas();
        guardarEnArchivoTxt();
        convertirJTableAExcel();
        convertirJTableAPDF();
        JOptionPane.showMessageDialog(null, "EXCEL y PDF actualizados correctamente");
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        String codigoEliminar = JOptionPane.showInputDialog(this, "Ingrese el código a eliminar:");

        if (codigoEliminar != null && !codigoEliminar.isEmpty()) {
            int rowCount = jTable1.getRowCount();
            int foundRow = -1;

            for (int i = 0; i < rowCount; i++) {
                Object valueAt = jTable1.getValueAt(i, 0);
                if (valueAt != null && codigoEliminar.equals(valueAt.toString())) {
                    foundRow = i;
                    break;
                }
            }

            if (foundRow != -1) {
                for (int i = 0; i < miModelo.getColumnCount(); i++) {
                    miModelo.setValueAt("-", foundRow, i);
                }
                LimpiarEntradas();
                guardarEnArchivoTxt();
                convertirJTableAExcel();
                convertirJTableAPDF();
                JOptionPane.showMessageDialog(null, "EXCEL y PDF actualizados correctamente");
            } else {
                JOptionPane.showMessageDialog(this, "El código a eliminar no existe en la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Ingrese un código válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        String codigoBuscarStr = TextCodigo.getText();

        if (!codigoBuscarStr.isEmpty()) {
            try {
                int codigoBuscar = Integer.parseInt(codigoBuscarStr);

                for (int i = 0; i < FilaMax; i++) {
                    Object codigoEnTabla = miModelo.getValueAt(i, 0);

                    if (codigoEnTabla != null) {
                        if (codigoEnTabla instanceof Integer) {
                            Integer codigoEnTablaInt = (Integer) codigoEnTabla;
                            if (codigoEnTablaInt == codigoBuscar) {
                                miModelo.setValueAt(TextNombre.getText().toUpperCase(), i, 1);
                                miModelo.setValueAt(TextAp.getText().toUpperCase(), i, 2);
                                miModelo.setValueAt(TextRol.getSelectedItem(), i, 3);
                                miModelo.setValueAt(Integer.parseInt(TextPago.getText()), i, 4);
                                
                                LimpiarEntradas(); 
                                guardarEnArchivoTxt();
                                convertirJTableAExcel();
                                convertirJTableAPDF();
                                JOptionPane.showMessageDialog(null, "EXCEL y PDF actualizados correctamente");
                                return; 
                            }
                        } else if (codigoEnTabla instanceof String) {
                            String codigoEnTablaStr = (String) codigoEnTabla;
                            if (Integer.parseInt(codigoEnTablaStr) == codigoBuscar) {
                                miModelo.setValueAt(TextNombre.getText().toUpperCase(), i, 1);
                                miModelo.setValueAt(TextAp.getText().toUpperCase(), i, 2);
                                miModelo.setValueAt(TextRol.getSelectedItem(), i, 3);
                                miModelo.setValueAt(Integer.parseInt(TextPago.getText()), i, 4);

                               
                                LimpiarEntradas(); 
                                guardarEnArchivoTxt();
                                convertirJTableAExcel();
                                convertirJTableAPDF();
                                JOptionPane.showMessageDialog(null, "EXCEL y PDF actualizados correctamente");
                                return; 
                            }
                        }
                    }
                }
                JOptionPane.showMessageDialog(this, "Registro no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Ingrese un código válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        cargarArchivoTxt();
        String codigoBuscarStr = JOptionPane.showInputDialog(this, "Ingrese el código a buscar:");

        if (codigoBuscarStr != null && !codigoBuscarStr.isEmpty()) {
            try {
                int codigoBuscar = Integer.parseInt(codigoBuscarStr);

                for (int i = 0; i < FilaMax; i++) {
                    Object codigoEnTabla = miModelo.getValueAt(i, 0);

                    if (codigoEnTabla != null) {
                        if (codigoEnTabla instanceof Integer) {
                            Integer codigoEnTablaInt = (Integer) codigoEnTabla;
                            if (codigoEnTablaInt == codigoBuscar) {
                                String mensaje = "Código: " + codigoEnTablaInt + "\n" +
                                        "Nombre: " + miModelo.getValueAt(i, 1) + "\n" +
                                        "Apellido: " + miModelo.getValueAt(i, 2) + "\n" +
                                        "Rol: " + miModelo.getValueAt(i, 3) + "\n" +
                                        "Pago: " + miModelo.getValueAt(i, 4);

                                JOptionPane.showMessageDialog(this, mensaje, "Información del Registro", JOptionPane.INFORMATION_MESSAGE);

                                int confirmacion = JOptionPane.showConfirmDialog(this, "¿Desea cambiar la información?", "Modificar la información", JOptionPane.YES_NO_OPTION);

                                if (confirmacion == JOptionPane.YES_OPTION) {
                                    TextCodigo.setText(String.valueOf(codigoEnTablaInt));
                                    TextNombre.setText((String) miModelo.getValueAt(i, 1));
                                    TextAp.setText((String) miModelo.getValueAt(i, 2));
                                    TextRol.setSelectedItem(miModelo.getValueAt(i, 3));
                                    TextPago.setText(String.valueOf(miModelo.getValueAt(i, 4)));

                                } else {
                                    JOptionPane.showMessageDialog(this, "No se realizaron cambios.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                                }
                                return;
                            }
                        } else if (codigoEnTabla instanceof String) {
                            String codigoEnTablaStr = (String) codigoEnTabla;
                            if (Integer.parseInt(codigoEnTablaStr) == codigoBuscar) {
                                String mensaje = "Código: " + codigoEnTablaStr + "\n" +
                                        "Nombre: " + miModelo.getValueAt(i, 1) + "\n" +
                                        "Apellido: " + miModelo.getValueAt(i, 2) + "\n" +
                                        "Rol: " + miModelo.getValueAt(i, 3) + "\n" +
                                        "Pago: " + miModelo.getValueAt(i, 4);

                                JOptionPane.showMessageDialog(this, mensaje, "Información del Registro", JOptionPane.INFORMATION_MESSAGE);

                                int confirmacion = JOptionPane.showConfirmDialog(this, "¿Desea cambiar la información?", "Modificar la información", JOptionPane.YES_NO_OPTION);

                                if (confirmacion == JOptionPane.YES_OPTION) {
                                    TextCodigo.setText(codigoEnTablaStr);
                                    TextNombre.setText((String) miModelo.getValueAt(i, 1));
                                    TextAp.setText((String) miModelo.getValueAt(i, 2));
                                    TextRol.setSelectedItem(miModelo.getValueAt(i, 3));
                                    TextPago.setText(String.valueOf(miModelo.getValueAt(i, 4)));

                                } else {
                                    JOptionPane.showMessageDialog(this, "No se realizaron cambios.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                                }
                                return;
                            }
                        }
                    }
                }
                JOptionPane.showMessageDialog(this, "Registro no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Ingrese un código válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void TextRolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextRolActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextRolActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Genero;
    private javax.swing.JTextField TextAp;
    private javax.swing.JTextField TextCodigo;
    private javax.swing.JTextField TextNombre;
    private javax.swing.JTextField TextPago;
    private javax.swing.JComboBox<String> TextRol;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScroll;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
