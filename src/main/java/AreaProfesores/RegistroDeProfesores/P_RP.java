/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package AreaProfesores.RegistroDeProfesores;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
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
public class P_RP extends javax.swing.JPanel {
    
    @Getter
    @Setter
    public class Nodo {
        //initComponents();
        private String codigo;
        private String nombre;
        private String apellido;
        private String inicio;
        private String genero;
        private int telefono;
        private Nodo sig;
        private static int contador = 0;

        public Nodo(String cod, String nom, String ap, String ini, String gen, int telf) {
            this.codigo = cod;
            this.nombre = nom;
            this.apellido = ap;
            this.inicio = ini;
            this.genero = gen;
            this.telefono = telf;
            sig = null;
            contador++;
        }
    }
    
    public Nodo inic,fin;
    public Nodo mostrar;
    int num = 0, tam = 3;
    
    public P_RP() {
        initComponents();
        //inicializando la tabla
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
    
    DefaultTableModel miModelo;
    String[] cabecera = {"Código", "Nombre", "Apellidos", "Fecha de inicio","Género","Telefono"};
    String[][] data = {};
    
    DefaultTableModel mt = new DefaultTableModel();
    
    public void vaciar_tabla() {
        int filas = jTable1.getRowCount();
        for (int i = 0; i < filas; i++) {
            miModelo.removeRow(0);
        }
    } 
    
    public void restaurar() {
        inic = null;
        fin = null;
        vaciar_tabla();
        num = 0;
        Nodo.contador = 0;
    }
    
    public void LimpiarEntradas() {
        TextCodigo.setText("");
        TextNombre.setText("");
        TextAp.setText("");
        TextFecha.setDate(null);
        TextGenero.setSelectedItem("SELECCIONE");
        TextTelefono.setText("");
    }
    
    public void VerDatos(){
        String cod;
        int telf;
        String nom,ap,ini,gen;
        vaciar_tabla();
        Nodo aux=inic;
        while(aux!=null){
            cod=aux.codigo;
            nom=aux.nombre;
            ap=aux.apellido;
            ini=aux.inicio;
            gen=aux.genero;
            telf=aux.telefono;
            Object[] fila={cod,nom,ap,ini,gen,telf};
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
    
    public void insertar(String cod, String nom, String ap, String ini, String gen, int telf) {
        Nodo nuevo = new Nodo(cod, nom, ap, ini, gen, telf);
        // Realizando los enlaces correspondientes
        if (inic == null) {
            inic = nuevo;
            fin = nuevo;
        } else {
            fin.sig = nuevo;
            fin = nuevo;
        }
        Nodo.contador++;
    }
    
    public void Eliminar(Nodo actual){
        Nodo anterior=inic;

        while(anterior.sig!=actual && anterior.sig!=null){
            anterior=anterior.sig;
        }

        if(actual !=null){
            if(anterior==actual) inic = actual.sig;
            else anterior.sig=actual.sig;
        }
    }
    
    public Nodo BuscarBinaria(String cod) {
        int inicio = 0;
        int fin = Nodo.contador - 1;

        while (inicio <= fin) {
            int central = inicio + (fin - inicio) / 2;

            Nodo nodoCentral = obtenerNodoEnPosicion(central);

            if (nodoCentral == null) {
                return null;
            }

            int comparacion = cod.compareToIgnoreCase(nodoCentral.codigo);

            if (comparacion == 0) {
                return nodoCentral; 
            } else if (comparacion < 0) {
                fin = central - 1; 
            } else {
                inicio = central + 1; 
            }
        }

        return null; 
    }

    public Nodo obtenerNodoEnPosicion(int posicion) {
        Nodo nodoActual = inic;
        for (int i = 0; i < posicion; i++) {
            nodoActual = nodoActual.sig;
        }
        return nodoActual;
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("busquedabinaria.txt"))) {
            Nodo actual = inic;
            while (actual != null) {
                writer.write(actual.codigo + "," + actual.nombre + "," + actual.apellido + "," + actual.inicio + "," + actual.genero + "," + actual.telefono);
                writer.newLine();
                actual = actual.sig;
            }
            JOptionPane.showMessageDialog(null, "Datos guardados en busquedabinaria.txt", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar en el archivo", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void cargarArchivoLista() {
        try (BufferedReader reader = new BufferedReader(new FileReader("busquedabinaria.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    insertar(parts[0], parts[1], parts[2], parts[3], parts[4], Integer.parseInt(parts[5]));
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
            
            String rutaPDF = "RegistroProfesores_busqueda.pdf";
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

            String textoTitulo = "Reporte acerca de los Registros de Profesores";
            Paragraph titulo = new Paragraph(textoTitulo.toUpperCase(), titleFont);
            titulo.setAlignment(Element.ALIGN_CENTER); 
            titulo.setSpacingAfter(30f);
            document.add(titulo);

            PdfPTable pdfTable = new PdfPTable(6); 
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
 
            Sheet sheet = workbook.createSheet("Registro");
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
            titleCell.setCellValue("Reporte acerca de los Registros de Profesores");
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

            String rutaExcel = "RegistroProfesores_busqueda.xlsx";
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

        content = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        Codigo = new javax.swing.JLabel();
        TextCodigo = new javax.swing.JTextField();
        Nombre = new javax.swing.JLabel();
        TextNombre = new javax.swing.JTextField();
        Ap = new javax.swing.JLabel();
        TextAp = new javax.swing.JTextField();
        Am = new javax.swing.JLabel();
        Genero = new javax.swing.JLabel();
        Telefono = new javax.swing.JLabel();
        TextTelefono = new javax.swing.JTextField();
        TextFecha = new com.toedter.calendar.JDateChooser();
        TextGenero = new javax.swing.JComboBox<>();
        BtnEliminar = new javax.swing.JButton();
        BtnAgregar = new javax.swing.JButton();
        BtnBuscar = new javax.swing.JButton();
        BtnActualizar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(935, 510));

        content.setBackground(new java.awt.Color(255, 255, 255));
        content.setPreferredSize(new java.awt.Dimension(935, 510));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("REGISTRO DE PROFESORES-BUSQUEDA BINARIA ");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nuevo Profesor");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap())
        );

        Codigo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Codigo.setForeground(new java.awt.Color(0, 51, 51));
        Codigo.setText("N° Codigo:");

        TextCodigo.setBackground(new java.awt.Color(254, 253, 253));
        TextCodigo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
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

        Nombre.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Nombre.setForeground(new java.awt.Color(0, 51, 51));
        Nombre.setText("Nombres:");
        Nombre.setPreferredSize(new java.awt.Dimension(64, 16));

        TextNombre.setBackground(new java.awt.Color(255, 253, 253));
        TextNombre.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
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

        Ap.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Ap.setForeground(new java.awt.Color(0, 51, 51));
        Ap.setText("Apellidos:");

        TextAp.setBackground(new java.awt.Color(255, 253, 253));
        TextAp.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TextAp.setForeground(new java.awt.Color(0, 51, 51));
        TextAp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextApActionPerformed(evt);
            }
        });
        TextAp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextApKeyPressed(evt);
            }
        });

        Am.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Am.setForeground(new java.awt.Color(0, 51, 51));
        Am.setText("Inicio:");

        Genero.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Genero.setForeground(new java.awt.Color(0, 51, 51));
        Genero.setText("Genero:");

        Telefono.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Telefono.setForeground(new java.awt.Color(0, 51, 51));
        Telefono.setText("Telefono:");

        TextTelefono.setBackground(new java.awt.Color(255, 253, 253));
        TextTelefono.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TextTelefono.setForeground(new java.awt.Color(0, 51, 51));
        TextTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextTelefonoActionPerformed(evt);
            }
        });
        TextTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextTelefonoKeyPressed(evt);
            }
        });

        TextFecha.setBackground(new java.awt.Color(255, 255, 255));
        TextFecha.setForeground(new java.awt.Color(255, 255, 255));
        TextFecha.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        TextFecha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextFechaKeyPressed(evt);
            }
        });

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(Telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(TextTelefono))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(Am, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Genero, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(Ap, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(TextAp, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addGap(0, 0, Short.MAX_VALUE)
                                    .addComponent(TextFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(TextGenero, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(Codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(TextNombre)
                                .addComponent(TextCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(14, 14, 14))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextNombre))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TextAp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Ap, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TextFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(Am, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Genero, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(128, 128, 128))
        );

        BtnEliminar.setIcon(new javax.swing.ImageIcon("C:\\Users\\jr860\\Desktop\\ProyectoJava\\Imagenes Java\\eliminar_usu.png")); // NOI18N
        BtnEliminar.setText("ELIMINAR");
        BtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEliminarActionPerformed(evt);
            }
        });

        BtnAgregar.setIcon(new javax.swing.ImageIcon("C:\\Users\\jr860\\Desktop\\ProyectoJava\\Imagenes Java\\anadir.png")); // NOI18N
        BtnAgregar.setText("AGREGAR");
        BtnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAgregarActionPerformed(evt);
            }
        });

        BtnBuscar.setIcon(new javax.swing.ImageIcon("C:\\Users\\jr860\\Desktop\\ProyectoJava\\Imagenes Java\\buscar.png")); // NOI18N
        BtnBuscar.setText("BUSQUEDA BINARIA");
        BtnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscarActionPerformed(evt);
            }
        });

        BtnActualizar.setIcon(new javax.swing.ImageIcon("C:\\Users\\jr860\\Desktop\\ProyectoJava\\Imagenes Java\\actualizar.png")); // NOI18N
        BtnActualizar.setText("ACTUALIZAR");
        BtnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnActualizarActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon("C:\\Users\\jr860\\Desktop\\ProyectoJava\\Imagenes Java\\restaurar.png")); // NOI18N
        jButton1.setText("RESTAURAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

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
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout contentLayout = new javax.swing.GroupLayout(content);
        content.setLayout(contentLayout);
        contentLayout.setHorizontalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentLayout.createSequentialGroup()
                .addGap(245, 245, 245)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(contentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contentLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(contentLayout.createSequentialGroup()
                                .addComponent(BtnAgregar)
                                .addGap(27, 27, 27)
                                .addComponent(BtnEliminar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(BtnActualizar)
                                .addGap(27, 27, 27)
                                .addComponent(jButton1))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 562, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35))
                    .addGroup(contentLayout.createSequentialGroup()
                        .addGap(208, 208, 208)
                        .addComponent(BtnBuscar)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        contentLayout.setVerticalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addGap(30, 30, 30)
                .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contentLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BtnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BtnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BtnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(BtnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(content, javax.swing.GroupLayout.DEFAULT_SIZE, 937, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(content, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de restaurar los datos?", "Confirmación", JOptionPane.YES_NO_OPTION);
    
        if (confirmacion == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Se eliminaron todos los elementos de la lista");
            restaurar();
            JOptionPane.showMessageDialog(null, "Lista vacia");
            guardarEnArchivoTxt();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void BtnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnActualizarActionPerformed
        // Obtener el código ingresado en el campo de texto
        String codigoAActualizar = TextCodigo.getText().toUpperCase();

        // Buscar el nodo en la lista
        Nodo nodoAActualizar = Buscar(inic, codigoAActualizar);

        if (nodoAActualizar != null) {
            // Actualizar los datos del nodo
            nodoAActualizar.codigo = TextCodigo.getText();
            nodoAActualizar.nombre = TextNombre.getText().toUpperCase();
            nodoAActualizar.apellido = TextAp.getText().toUpperCase();
            nodoAActualizar.genero = (String) TextGenero.getSelectedItem();

            Date fechaIni = TextFecha.getDate();
            if (fechaIni != null) {
                nodoAActualizar.inicio = getFecha(TextFecha);
            } 
            
            int telf = Integer.parseInt(TextTelefono.getText());
            if (telf >= 900000000 && telf <= 999999999) {
                nodoAActualizar.telefono = telf;

                LimpiarEntradas();
                VerDatos();
                guardarEnArchivoTxt();
                convertirJTableAExcel();
                convertirJTableAPDF();
                JOptionPane.showMessageDialog(null, "EXCEL y PDF actualizados correctamente");
            } else {
                JOptionPane.showMessageDialog(this, "Ingrese un número de teléfono válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró el nodo con el código seleccionado.");
        }
    }//GEN-LAST:event_BtnActualizarActionPerformed

    private void BtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscarActionPerformed
        String codigo = JOptionPane.showInputDialog(this, "Ingrese el código del trabajador:");

        if (codigo != null && !codigo.isEmpty()) {
            mostrar = BuscarBinaria(codigo);

            if (mostrar != null) {
                String mensaje = "Información encontrada:\n\n" +
                "Nombres: " + mostrar.nombre + "\n" +
                "Apellidos: " + mostrar.apellido + "\n" +
                "Fecha de Inicio: " + mostrar.inicio + "\n" +
                "Género: " + mostrar.genero + "\n" +
                "Teléfono: " + mostrar.telefono;

                JOptionPane.showMessageDialog(this, mensaje, "Información del Trabajador", JOptionPane.INFORMATION_MESSAGE);

                int confirmacion = JOptionPane.showConfirmDialog(this, "¿Desea cambiar la información?", "Modificar la información", JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    TextCodigo.setText(mostrar.codigo);
                    TextNombre.setText(mostrar.nombre);
                    TextAp.setText(mostrar.apellido);
                    String gen = mostrar.genero;
                    TextGenero.setSelectedItem(gen);
                    String tel = String.valueOf(mostrar.telefono);
                    TextTelefono.setText(tel);
                }
            } else {
                JOptionPane.showMessageDialog(this, "El código " + codigo + " no se encuentra en la lista.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Ingrese un código válido.", "Código Vacío", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_BtnBuscarActionPerformed

    private void BtnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAgregarActionPerformed
        // Capturando la información de los objetos
        String codigo = TextCodigo.getText().toUpperCase();
        String nombre = TextNombre.getText().toUpperCase();
        String apellidos = TextAp.getText().toUpperCase();
        String genero = (String) TextGenero.getSelectedItem();
        String tele = TextTelefono.getText();

        if (codigo.isEmpty() || tele.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos correctamente.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            int co = Integer.parseInt(codigo);
            int telf = Integer.parseInt(tele);
            String fn = getFecha(TextFecha);
            Date fecha = TextFecha.getDate();

            if (fecha == null) {
                JOptionPane.showMessageDialog(this, "Ingrese fechas de nacimiento y matrícula válidas.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Calendar hoy = Calendar.getInstance();
                Calendar fe = Calendar.getInstance();
                fe.setTime(fecha);
                int años = hoy.get(Calendar.YEAR) - fe.get(Calendar.YEAR);
                int meses = hoy.get(Calendar.MONTH) - fe.get(Calendar.MONTH);
                int dias = hoy.get(Calendar.DAY_OF_MONTH) - fe.get(Calendar.DAY_OF_MONTH);

                if (meses < 0 || (meses == 0 && dias < 0)) {
                    años--;
                }

                Calendar fec = Calendar.getInstance();
                fec.setTime(fecha);
                int añoActual = Calendar.getInstance().get(Calendar.YEAR);

                if (fec.get(Calendar.YEAR) != añoActual) {
                    JOptionPane.showMessageDialog(this, "La fecha de matrícula debe ser del año actual.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!numPositivo(co) || telf < 900000000 || telf > 999999999) {
                    JOptionPane.showMessageDialog(this, "No puede ingresar números negativos.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (genero == null || nombre.isEmpty() || apellidos.isEmpty() || genero.equals("SELECCIONE")){
                    JOptionPane.showMessageDialog(this, "Complete todos los campos correctamente.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Llama a la función insertar para agregar el registro.
                    insertar(codigo, nombre, apellidos, getFecha(TextFecha), genero, telf);
                    LimpiarEntradas();
                    VerDatos();
                }
                guardarEnArchivoTxt();
                convertirJTableAExcel();
                convertirJTableAPDF();
                JOptionPane.showMessageDialog(null, "EXCEL y PDF actualizados correctamente");
            }
        }
    }//GEN-LAST:event_BtnAgregarActionPerformed

    private void BtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarActionPerformed
        // Mostrar un cuadro de diálogo de entrada para que el usuario ingrese el código a eliminar
        String codigoAEliminar = JOptionPane.showInputDialog(this, "Ingrese el código que desea eliminar:");

        // Verificar si el usuario ingresó un código
        if (codigoAEliminar != null && !codigoAEliminar.isEmpty()) {
            // Buscar el nodo en la lista
            Nodo nodoAEliminar = Buscar(inic, codigoAEliminar);

            if (nodoAEliminar != null) {
                int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar el código " + codigoAEliminar + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    Eliminar(nodoAEliminar);
                    JOptionPane.showMessageDialog(null, "Código " + codigoAEliminar + " eliminado");
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

    private void TextGeneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextGeneroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextGeneroActionPerformed

    private void TextTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextTelefonoActionPerformed

    private void TextApActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextApActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextApActionPerformed

    private void TextNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextNombreActionPerformed
        
    }//GEN-LAST:event_TextNombreActionPerformed

    private void TextCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextCodigoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TextNombre.grabFocus();
        }
    }//GEN-LAST:event_TextCodigoKeyPressed

    private void TextCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextCodigoActionPerformed

    private void TextNombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextNombreKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TextAp.grabFocus();
        }
    }//GEN-LAST:event_TextNombreKeyPressed

    private void TextApKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextApKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TextFecha.grabFocus();
        }
    }//GEN-LAST:event_TextApKeyPressed

    private void TextFechaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextFechaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TextGenero.grabFocus();
        }
    }//GEN-LAST:event_TextFechaKeyPressed

    private void TextGeneroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextGeneroKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TextTelefono.grabFocus();
        }
    }//GEN-LAST:event_TextGeneroKeyPressed

    private void TextTelefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextTelefonoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnAgregar.grabFocus();
            // Capturando la información de los objetos
            String codigo = TextCodigo.getText().toUpperCase();
            String nombre = TextNombre.getText().toUpperCase();
            String apellidos = TextAp.getText().toUpperCase();
            String genero = (String) TextGenero.getSelectedItem();
            String tele = TextTelefono.getText();

            if (codigo.isEmpty() || tele.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos correctamente.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int co = Integer.parseInt(codigo);
                int telf = Integer.parseInt(tele);
                String fn = getFecha(TextFecha);
                Date fecha = TextFecha.getDate();

                if (fecha == null) {
                    JOptionPane.showMessageDialog(this, "Ingrese fechas de nacimiento y matrícula válidas.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Calendar hoy = Calendar.getInstance();
                    Calendar fe = Calendar.getInstance();
                    fe.setTime(fecha);
                    int años = hoy.get(Calendar.YEAR) - fe.get(Calendar.YEAR);
                    int meses = hoy.get(Calendar.MONTH) - fe.get(Calendar.MONTH);
                    int dias = hoy.get(Calendar.DAY_OF_MONTH) - fe.get(Calendar.DAY_OF_MONTH);

                    if (meses < 0 || (meses == 0 && dias < 0)) {
                        años--;
                    }

                    Calendar fec = Calendar.getInstance();
                    fec.setTime(fecha);
                    int añoActual = Calendar.getInstance().get(Calendar.YEAR);

                    if (fec.get(Calendar.YEAR) != añoActual) {
                        JOptionPane.showMessageDialog(this, "La fecha de matrícula debe ser del año actual.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (!numPositivo(co) || telf < 900000000 || telf > 999999999) {
                        JOptionPane.showMessageDialog(this, "No puede ingresar números negativos.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (genero == null || nombre.isEmpty() || apellidos.isEmpty() || genero.equals("SELECCIONE")){
                        JOptionPane.showMessageDialog(this, "Complete todos los campos correctamente.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Llama a la función insertar para agregar el registro.
                        insertar(codigo, nombre, apellidos, getFecha(TextFecha), genero, telf);
                        LimpiarEntradas();
                        VerDatos();
                    }
                }
            }
        }
    }//GEN-LAST:event_TextTelefonoKeyPressed
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Am;
    private javax.swing.JLabel Ap;
    private javax.swing.JButton BtnActualizar;
    private javax.swing.JButton BtnAgregar;
    private javax.swing.JButton BtnBuscar;
    private javax.swing.JButton BtnEliminar;
    private javax.swing.JLabel Codigo;
    private javax.swing.JLabel Genero;
    private javax.swing.JLabel Nombre;
    private javax.swing.JLabel Telefono;
    private javax.swing.JTextField TextAp;
    private javax.swing.JTextField TextCodigo;
    private com.toedter.calendar.JDateChooser TextFecha;
    private javax.swing.JComboBox<String> TextGenero;
    private javax.swing.JTextField TextNombre;
    private javax.swing.JTextField TextTelefono;
    private javax.swing.JPanel content;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
