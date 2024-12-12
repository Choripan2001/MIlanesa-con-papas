/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package AreaAlumnos.MatriculaEstudiantil;

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
import com.toedter.calendar.JDateChooser;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
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
public class A_ME extends javax.swing.JPanel {
    
    DefaultTableModel c = new DefaultTableModel();  
    
    @Getter
    @Setter
    public class Nodo {
        //initComponents();
        private String id;
        private String nombre;
        private String Ap;
        private String Am;
        private int dni;
        private int edad;
        private String fecha_nac;
        private String genero;
        private String fecha_mat;
        private String grado;
        private Nodo sig;
        private static int contador = 0;
        
        public Nodo (String ids,String nom,String Ape, String Ame,int dnis,int ed, String fc,String gen, String fm,String grade){
            this.id=ids;
            this.nombre=nom;
            this.Ap=Ape;
            this.Am=Ame;
            this.dni=dnis;
            this.edad = ed;
            this.fecha_nac= fc;
            this.genero = gen;
            this.fecha_mat = fm;
            this.grado=grade;
            contador++;
        }
    }
    
    public Nodo ini,fin;
    public Nodo mostrar;
    int num=0, tam = 3;
    String eda="";

    public A_ME() {
        initComponents();
        //Creacion de tabla Principal
        miModelo=new DefaultTableModel(data,cabecera);
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
    String[] cabecera={"ID","Nombres","Apell.Paterno","Apell.Materno","DNI"
                       ,"Fech.Nac","Edad","Genero","Fech.Mat","Grado"};
    String[][] data={};
    
    public void vaciar_tabla() {
        int filas = jTable1.getRowCount();
        for (int i = 0; i < filas; i++) {
            miModelo.removeRow(0);
        }
    } 
       
    public void restaurar() {
        ini = null;
        fin = null;
        vaciar_tabla();
        num = 0;
        Nodo.contador = 0;
    }
       
    public void LimpiarEntradas(){
        TextId.setText("");
        TextNombre.setText("");
        TextAp.setText("");
        TextAm.setText("");
        TextDNI.setText("");
        TextNac.setDate(null);
        TextGenero.setSelectedItem("SELECCIONE");
        TextMatricula.setDate(null);
        TextGrado.setSelectedItem("SELECCIONE");
        TextId.requestFocus();
    }
    
    public void VerDatos(){
        String ids;
        int edads,dnis;
        String nom,ap,am,ma,fn,gen,gr;
        vaciar_tabla();
        Nodo aux=ini;
        while(aux!=null){
            ids=aux.id;
            nom=aux.nombre;
            ap=aux.Ap;
            am=aux.Am;
            dnis=aux.dni;
            fn=aux.fecha_nac;
            edads=aux.edad;
            gen=aux.genero;
            ma=aux.fecha_mat;
            gr=aux.grado;
         
            Object[] fila={ids,nom,ap,am,dnis,fn,edads,gen,ma,gr};
            miModelo.addRow(fila);
            aux=aux.sig;
        }
    }

    public String getFecha(JDateChooser jd) {
        SimpleDateFormat Formato = new SimpleDateFormat("dd/MM/yyyy");
        if (jd.getDate() != null) {
            return Formato.format(jd.getDate());
        } else {
            return null;
        }
    }
    
    public Date getdate(String da) throws ParseException{
        java.text.SimpleDateFormat formato=new java.text.SimpleDateFormat("dd/MM/yyyy");
        java.util.Date fechadate=formato.parse(da);
        return fechadate;
    }
    
    // -------------------------------------------------------------

    // --------------------- METODOS CRUD --------------------------
     
    public void insertar(String ids,String nom,String Ap,String Am,int edads,int dnis,String fn,String gen,String fm,String gr) {
        Nodo nuevo= new Nodo(ids, nom, Ap, Am, edads, dnis, fn, gen, fm, gr);
        if (ini == null) {
            ini = nuevo;
        } else {
            fin.sig = nuevo;
        }
        fin = nuevo;
        fin.sig = null;
    }
    
    public void Eliminar(Nodo actual){
        Nodo anterior=ini;
        
        while(anterior.sig!=actual && anterior.sig!=null){
            anterior=anterior.sig;
        }

        if(actual !=null){
            if(anterior==actual) ini = actual.sig;
            else anterior.sig=actual.sig;
        }
    }
    
    // -------------------------------------------------------------
    
    // --------------------- METODOS RECURSIVOS --------------------------

    public Nodo Buscar(Nodo inicio, String cod) {
        if (inicio == null) {
            return null; 
        }

        if (cod.equalsIgnoreCase(inicio.id)) {
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Lista_Alumnos.txt"))) {
            Nodo actual = ini;
            while (actual != null) {
                writer.write(actual.id + "," + actual.nombre + "," + actual.Ap + "," + actual.Am + "," +
                             actual.dni + "," + actual.edad + "," + actual.fecha_nac + "," + actual.genero + "," +
                             actual.fecha_mat + "," + actual.grado);
                writer.newLine();
                actual = actual.sig;
            }
            JOptionPane.showMessageDialog(null, "Datos guardados en Lista_Alumnos.txt", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar en el archivo", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void cargarArchivoLista() {
        try (BufferedReader reader = new BufferedReader(new FileReader("Lista_Alumnos.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 10) {
                    insertar(parts[0], parts[1], parts[2], parts[3], Integer.parseInt(parts[4]),
                             Integer.parseInt(parts[5]), parts[6], parts[7], parts[8], parts[9]);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar desde el archivo", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    public void cargarArchivoTxt() {
        cargarArchivoLista();
        VerDatos();
    }
    
    // -------------------------------------------------------------
    
    // --------------------- METODOS PARA GUARDAR DATOS EN UN PDF Y EXCEL --------------------------
    
    public void convertirJTableAPDF() {
        Document document = new Document();
        try {
            
            String rutaPDF = "MatriculaEstudiantil_Listas.pdf";
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

            String textoTitulo = "Reporte acerca de la Matricula Estudiantil";
            Paragraph titulo = new Paragraph(textoTitulo.toUpperCase(), titleFont);
            titulo.setAlignment(Element.ALIGN_CENTER); 
            titulo.setSpacingAfter(30f);
            document.add(titulo);

            PdfPTable pdfTable = new PdfPTable(10); 
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
 
            Sheet sheet = workbook.createSheet("Listas");
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
            titleCell.setCellValue("Reporte acerca de la Matricula Estudiantil");
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

            String rutaExcel = "MatriculaEstudiantil_Listas.xlsx";
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

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        TextId = new javax.swing.JTextField();
        TextAm = new javax.swing.JTextField();
        TextNombre = new javax.swing.JTextField();
        TextAp = new javax.swing.JTextField();
        TextDNI = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        TextGenero = new javax.swing.JComboBox<>();
        TextMatricula = new com.toedter.calendar.JDateChooser();
        TextNac = new com.toedter.calendar.JDateChooser();
        BtnAgregar = new javax.swing.JButton();
        BtnEliminar = new javax.swing.JButton();
        BtnActualizar = new javax.swing.JButton();
        BtnConsultar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        TextGrado = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 51));
        jLabel2.setText("ID:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 51));
        jLabel3.setText("Nombres:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 51));
        jLabel4.setText("Apellido Paterno:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 51));
        jLabel5.setText("Apellido Materno:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 51));
        jLabel6.setText("Grado:");

        TextId.setBackground(new java.awt.Color(255, 253, 253));
        TextId.setForeground(new java.awt.Color(0, 51, 51));
        TextId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextIdKeyPressed(evt);
            }
        });

        TextAm.setBackground(new java.awt.Color(255, 253, 253));
        TextAm.setForeground(new java.awt.Color(0, 51, 51));
        TextAm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextAmKeyPressed(evt);
            }
        });

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

        TextAp.setBackground(new java.awt.Color(255, 253, 253));
        TextAp.setForeground(new java.awt.Color(0, 51, 51));
        TextAp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextApKeyPressed(evt);
            }
        });

        TextDNI.setBackground(new java.awt.Color(255, 253, 253));
        TextDNI.setForeground(new java.awt.Color(0, 51, 51));
        TextDNI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextDNIActionPerformed(evt);
            }
        });
        TextDNI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextDNIKeyPressed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 51));
        jLabel8.setText("Fech.Nacimiento:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 51, 51));
        jLabel10.setText("Genero:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 51, 51));
        jLabel11.setText("Fech.Matricula:");

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\jr860\\Desktop\\ProyectoJava\\Imagenes Java\\matricula.png")); // NOI18N
        jLabel1.setText("jLabel1");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 51, 51));
        jLabel12.setText("MATRICULA ESTUDIANTIL");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        TextGenero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECCIONE", "MASCULINO", "FEMENINO" }));
        TextGenero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextGeneroActionPerformed(evt);
            }
        });
        TextGenero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextGeneroKeyPressed(evt);
            }
        });

        TextMatricula.setForeground(new java.awt.Color(204, 204, 204));
        TextMatricula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextMatriculaKeyPressed(evt);
            }
        });

        TextNac.setForeground(new java.awt.Color(204, 204, 204));
        TextNac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextNacKeyPressed(evt);
            }
        });

        BtnAgregar.setIcon(new javax.swing.ImageIcon("C:\\Users\\jr860\\Desktop\\ProyectoJava\\Imagenes Java\\anadir.png")); // NOI18N
        BtnAgregar.setText("AGREGAR");
        BtnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAgregarActionPerformed(evt);
            }
        });

        BtnEliminar.setIcon(new javax.swing.ImageIcon("C:\\Users\\jr860\\Desktop\\ProyectoJava\\Imagenes Java\\eliminar_usu.png")); // NOI18N
        BtnEliminar.setText("ELIMINAR");
        BtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEliminarActionPerformed(evt);
            }
        });

        BtnActualizar.setIcon(new javax.swing.ImageIcon("C:\\Users\\jr860\\Desktop\\ProyectoJava\\Imagenes Java\\actualizar.png")); // NOI18N
        BtnActualizar.setText("ACTUALIZAR");
        BtnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnActualizarActionPerformed(evt);
            }
        });

        BtnConsultar.setIcon(new javax.swing.ImageIcon("C:\\Users\\jr860\\Desktop\\ProyectoJava\\Imagenes Java\\buscar.png")); // NOI18N
        BtnConsultar.setText("BUSCAR");
        BtnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnConsultarActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 51));
        jLabel9.setText("DNI:");

        TextGrado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECCIONE", "1°", "2°", "3°", "4°", "5°" }));
        TextGrado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextGradoActionPerformed(evt);
            }
        });
        TextGrado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextGradoKeyPressed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 51, 51));
        jLabel13.setText("LISTAS-ENLAZADAS SIMPLES");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(BtnAgregar)
                                .addGap(18, 18, 18)
                                .addComponent(BtnEliminar)
                                .addGap(18, 18, 18)
                                .addComponent(BtnActualizar)
                                .addGap(18, 18, 18)
                                .addComponent(BtnConsultar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel13)
                                .addGap(23, 23, 23))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(11, 11, 11)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(TextNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(TextAp, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(TextAm, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(TextDNI, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(TextId, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(55, 55, 55)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(TextGenero, 0, 158, Short.MAX_VALUE)
                                                        .addComponent(TextMatricula, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                    .addComponent(TextGrado, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(41, 41, 41))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(TextNac, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                                                .addComponent(jLabel12)))
                                        .addGap(37, 37, 37)))))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(TextNac, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(TextId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8))))))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(TextNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(6, 6, 6))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(TextGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(8, 8, 8)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TextMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(TextAp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(TextAm, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(TextGrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextDNI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(BtnAgregar)
                        .addComponent(BtnEliminar)
                        .addComponent(BtnActualizar)
                        .addComponent(BtnConsultar))
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
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
    
    private void BtnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAgregarActionPerformed
        // Capturando la información de los objetos
        String ids = TextId.getText();
        String nom = TextNombre.getText().toUpperCase();
        String ap = TextAp.getText().toUpperCase();
        String am = TextAm.getText().toUpperCase();
        String gen = (String) TextGenero.getSelectedItem();
        String grade = (String) TextGrado.getSelectedItem();
        String dniText = TextDNI.getText();

        if (ids.isEmpty() || dniText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos correctamente.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            int id = Integer.parseInt(ids);
            int dnis = Integer.parseInt(dniText);
            String fn = getFecha(TextNac);
            Date fechaNacimiento = TextNac.getDate();
            String fm = getFecha(TextMatricula);
            Date fechaMatricula = TextMatricula.getDate();

            if (fechaNacimiento == null || fechaMatricula == null) {
                JOptionPane.showMessageDialog(this, "Ingrese fechas de nacimiento y matrícula válidas.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Calendar hoy = Calendar.getInstance();
                Calendar nacimiento = Calendar.getInstance();
                nacimiento.setTime(fechaNacimiento);
                int años = hoy.get(Calendar.YEAR) - nacimiento.get(Calendar.YEAR);
                int meses = hoy.get(Calendar.MONTH) - nacimiento.get(Calendar.MONTH);
                int dias = hoy.get(Calendar.DAY_OF_MONTH) - nacimiento.get(Calendar.DAY_OF_MONTH);

                if (meses < 0 || (meses == 0 && dias < 0)) {
                    años--;
                }

                int ed = años;

                Calendar matricula = Calendar.getInstance();
                matricula.setTime(fechaMatricula);
                int añoActual = Calendar.getInstance().get(Calendar.YEAR);

                if (matricula.get(Calendar.YEAR) != añoActual) {
                    JOptionPane.showMessageDialog(this, "La fecha de matrícula debe ser del año actual.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (dnis < 10000000 || dnis > 999999999 || !numPositivo(id) || años < 10 || años > 18 || grade == null || nom.isEmpty() || ap.isEmpty() || am.isEmpty() || gen.equals("SELECCIONE") || grade.equals("SELECCIONE")) {
                    JOptionPane.showMessageDialog(this, "Complete todos los campos correctamente.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    insertar(ids, nom, ap, am, dnis, ed, fn, gen, fm, grade);
                    LimpiarEntradas();
                    VerDatos();
                    guardarEnArchivoTxt();
                    convertirJTableAExcel();
                    convertirJTableAPDF();
                    JOptionPane.showMessageDialog(null, "EXCEL y PDF actualizados correctamente");
                }
            }
        }
    }//GEN-LAST:event_BtnAgregarActionPerformed

    
    private void BtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarActionPerformed
        
        String codigoAEliminar = JOptionPane.showInputDialog(this, "Ingrese el código que desea eliminar:");

        // Verificar si el usuario ingresó un código
        if (codigoAEliminar != null && !codigoAEliminar.isEmpty()) {
            // Buscar el nodo en la lista
            Nodo nodoAEliminar = Buscar(ini, codigoAEliminar);

            if (nodoAEliminar != null) {
                int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar el código " + codigoAEliminar + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    Eliminar(nodoAEliminar);

                    LimpiarEntradas();
                    VerDatos();
                    guardarEnArchivoTxt();
                    convertirJTableAExcel();
                    convertirJTableAPDF();
                    JOptionPane.showMessageDialog(null, "EXCEL y PDF actualizados correctamente");
                    Nodo.contador--;
                }
            } else {
                JOptionPane.showMessageDialog(this, "El código " + codigoAEliminar + " no se encuentra en la lista.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe ingresar un código para eliminar.");
        }
    }//GEN-LAST:event_BtnEliminarActionPerformed

    private void BtnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnActualizarActionPerformed
       
        String codigoAActualizar = TextId.getText().toUpperCase();

        
        Nodo nodoAActualizar = Buscar(ini, codigoAActualizar);

        if (nodoAActualizar != null) {
            
            nodoAActualizar.id = TextId.getText();
            nodoAActualizar.nombre = TextNombre.getText();
            nodoAActualizar.Ap = TextAp.getText();
            nodoAActualizar.Am = TextAm.getText();

            
            int dni = Integer.parseInt(TextDNI.getText());
            if (dni >= 10000000 && dni <= 99999999) {
                nodoAActualizar.dni = dni;

                
                Date fechaNacimiento = TextNac.getDate();
                if (fechaNacimiento != null) {
                    nodoAActualizar.fecha_nac = getFecha(TextNac);
                }

                nodoAActualizar.genero = (String) TextGenero.getSelectedItem();

                
                Date fechaMatricula = TextMatricula.getDate();
                if (fechaMatricula != null) {
                    nodoAActualizar.fecha_mat = getFecha(TextMatricula);
                }

                nodoAActualizar.grado = (String) TextGrado.getSelectedItem();

                LimpiarEntradas();
                VerDatos();
                guardarEnArchivoTxt();
                convertirJTableAExcel();
                convertirJTableAPDF();
                JOptionPane.showMessageDialog(null, "EXCEL y PDF actualizados correctamente");
            } else {
                JOptionPane.showMessageDialog(this, "Ingrese un DNI válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró el nodo con el código seleccionado.");
        }
    }//GEN-LAST:event_BtnActualizarActionPerformed

    private void BtnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnConsultarActionPerformed
        String codigo = JOptionPane.showInputDialog(this, "Ingrese el código del trabajador:");


        if (codigo != null && !codigo.isEmpty()) {
            mostrar = Buscar(ini, codigo);

            if (mostrar != null) {
                String mensaje = "Información encontrada:\n\n" +
                        "Nombres: " + mostrar.nombre + "\n" +
                        "Apellido Paterno: " + mostrar.Ap + "\n" +
                        "Apellido Materno: " + mostrar.Am + "\n" +
                        "DNI: " + mostrar.dni + "\n" +
                        "Edad: " + mostrar.edad + "\n" +
                        "Fecha de Nacimiento: " + mostrar.fecha_nac + "\n" +
                        "Género: " + mostrar.genero + "\n" +
                        "Fecha de Matrícula: " + mostrar.fecha_mat + "\n" +
                        "Grado: " + mostrar.grado;

                JOptionPane.showMessageDialog(this, mensaje, "Información del Trabajador", JOptionPane.INFORMATION_MESSAGE);

                int confirmacion = JOptionPane.showConfirmDialog(this, "¿Desea cambiar la información?", "Modificar la información", JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    TextId.setText(mostrar.id);
                    TextNombre.setText(mostrar.nombre);
                    TextAp.setText(mostrar.Ap);
                    TextAm.setText(mostrar.Am);
                    String doc = String.valueOf(mostrar.dni);
                    TextDNI.setText(doc);
                    String gen = mostrar.genero;
                    TextGenero.setSelectedItem(gen);
                    String grad = mostrar.grado;
                    TextGrado.setSelectedItem(grad);
                }
            } else {
                JOptionPane.showMessageDialog(this, "El código " + codigo + " no se encuentra en la lista.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Ingrese un código válido.", "Código Vacío", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_BtnConsultarActionPerformed

    private void TextIdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextIdKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TextNombre.grabFocus();
        }
    }//GEN-LAST:event_TextIdKeyPressed

    private void TextNombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextNombreKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            TextAp.grabFocus();
        }
    }//GEN-LAST:event_TextNombreKeyPressed

    private void TextApKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextApKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TextAm.grabFocus();
        }
    }//GEN-LAST:event_TextApKeyPressed

    private void TextAmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextAmKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TextDNI.grabFocus();
        }
    }//GEN-LAST:event_TextAmKeyPressed

    private void TextDNIKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextDNIKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TextNac.grabFocus();
        }
    }//GEN-LAST:event_TextDNIKeyPressed

    private void TextNacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextNacKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TextGenero.grabFocus();
        }
    }//GEN-LAST:event_TextNacKeyPressed

    private void TextGeneroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextGeneroKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TextMatricula.grabFocus();
        }
    }//GEN-LAST:event_TextGeneroKeyPressed

    private void TextMatriculaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextMatriculaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TextGrado.grabFocus();
        }
    }//GEN-LAST:event_TextMatriculaKeyPressed

    private void TextGradoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextGradoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnAgregar.grabFocus();
            // Capturando la información de los objetos
            String ids = TextId.getText();
            String nom = TextNombre.getText().toUpperCase();
            String ap = TextAp.getText().toUpperCase();
            String am = TextAm.getText().toUpperCase();
            String gen = (String) TextGenero.getSelectedItem().toString().toUpperCase();
            String grade = (String) TextGrado.getSelectedItem();
            String dniText = TextDNI.getText();

            if (ids.isEmpty() || dniText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos correctamente.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int id = Integer.parseInt(ids);
                int dnis = Integer.parseInt(dniText);
                String fn = getFecha(TextNac);
                Date fechaNacimiento = TextNac.getDate();
                String fm = getFecha(TextMatricula);
                Date fechaMatricula = TextMatricula.getDate();

                if (fechaNacimiento == null || fechaMatricula == null) {
                    JOptionPane.showMessageDialog(this, "Ingrese fechas de nacimiento y matrícula válidas.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Calendar hoy = Calendar.getInstance();
                    Calendar nacimiento = Calendar.getInstance();
                    nacimiento.setTime(fechaNacimiento);
                    int años = hoy.get(Calendar.YEAR) - nacimiento.get(Calendar.YEAR);
                    int meses = hoy.get(Calendar.MONTH) - nacimiento.get(Calendar.MONTH);
                    int dias = hoy.get(Calendar.DAY_OF_MONTH) - nacimiento.get(Calendar.DAY_OF_MONTH);

                    if (meses < 0 || (meses == 0 && dias < 0)) {
                        años--;
                    }

                    int ed = años;

                    Calendar matricula = Calendar.getInstance();
                    matricula.setTime(fechaMatricula);
                    int añoActual = Calendar.getInstance().get(Calendar.YEAR);

                    if (matricula.get(Calendar.YEAR) != añoActual) {
                        JOptionPane.showMessageDialog(this, "La fecha de matrícula debe ser del año actual.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (dnis < 10000000 || dnis > 999999999 || !numPositivo(id) || años < 10 || años > 18 || grade == null || nom.isEmpty() || ap.isEmpty() || am.isEmpty() || gen.equals("SELECCIONE") || grade.equals("SELECCIONE")) {
                        JOptionPane.showMessageDialog(this, "Complete todos los campos correctamente.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        insertar(ids, nom, ap, am, dnis, ed, fn, gen, fm, grade);
                        LimpiarEntradas();
                        VerDatos();
                    }
                }
            }
        }
    }//GEN-LAST:event_TextGradoKeyPressed

    private void TextGeneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextGeneroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextGeneroActionPerformed

    private void TextNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextNombreActionPerformed

    private void TextDNIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextDNIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextDNIActionPerformed

    private void TextGradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextGradoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextGradoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnActualizar;
    private javax.swing.JButton BtnAgregar;
    private javax.swing.JButton BtnConsultar;
    private javax.swing.JButton BtnEliminar;
    private javax.swing.JTextField TextAm;
    private javax.swing.JTextField TextAp;
    private javax.swing.JTextField TextDNI;
    private javax.swing.JComboBox<String> TextGenero;
    private javax.swing.JComboBox<String> TextGrado;
    private javax.swing.JTextField TextId;
    private com.toedter.calendar.JDateChooser TextMatricula;
    private com.toedter.calendar.JDateChooser TextNac;
    private javax.swing.JTextField TextNombre;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}

