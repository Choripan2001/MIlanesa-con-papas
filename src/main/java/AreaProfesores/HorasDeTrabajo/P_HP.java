/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package AreaProfesores.HorasDeTrabajo;

import AreaProfesores.RegistroDeProfesores.P_RP;
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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import lombok.Setter;
import lombok.Getter;
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


public class P_HP extends javax.swing.JPanel {
      
    DefaultTableModel hp = new DefaultTableModel();
    
    @Getter
    @Setter
    public class Nodo {
        //initComponents();
        private String codigo;
        private String nombre;
        private String apellidos ;
        private String cursos ;
        private int horas;
        private double sueldo;
        private double pagoH;
        private double bonificacion;
        private double montoF;
        private Nodo sig;
        static int contador = 0;
        
        public Nodo (String cod,String nom,String ap,String curs,double ph,int h){
            this.codigo=cod;
            this.nombre=nom;
            this.apellidos=ap;
            this.cursos=curs;
            this.horas=h;
            this.pagoH=ph;
            contador++;
        }
    }
    
    public Nodo ini,fin;
    public Nodo mostrar;
    int num=0, tam = 3;
   
    public P_HP() {
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
    String[] cabecera={"Codigo","Nombres","Apellidos","Curso","Pago x hora"
                       ,"Horas Mensuales","Sueldo","Bonificacion","Sueldo Final"};
    String[][] data={};
    
    
    public void vaciar_tabla() {
        int filas = jTable1.getRowCount();
        for (int i = 0; i < filas; i++) {
            miModelo.removeRow(0);
        }
    } 
       
    public void LimpiarEntradas(){
        TextCodigo.setText("");
        TextNombre.setText("");
        TextApellido.setText("");
        TextCurso.setText("");
        TextPago.setText("");
        TextHoras.setText("");
        TextCodigo.requestFocus();
    }
    
    public void VerDatos(){
        String cod;
        int hora;
        String nom,ap,cu;
        double s,mf,pho;
        double bo=0;
        vaciar_tabla();
        Nodo aux=ini;
        while(aux!=null){
            cod=aux.codigo;
            nom=aux.nombre;
            ap=aux.apellidos;
            cu=aux.cursos;        
            pho=aux.pagoH;
            hora=aux.horas;
            s=aux.horas*aux.pagoH;
            if(s>=0 && s<=1500){
                bo=0.0;
            }else if(s>1500 && s<=2300){
                bo=(0.02*s);
            }else if(s>2300 && s<=3000){
                bo= (0.03*s);
            }else if(s>3000){
                bo= (0.04*s);
            }
            DecimalFormat df = new DecimalFormat("#.00");
            bo=Double.parseDouble(df.format(bo));
            mf=s+bo;
            Object[] fila={cod,nom,ap,cu,pho,hora,s,bo,mf};
            miModelo.addRow(fila);         
            aux=aux.sig;
        }
    }
    
    // -------------------------------------------------------------

    // --------------------- METODOS CRUD --------------------------
    
    public void insertar(String cod,String nom,String ap,String curs,double ph,int h) {
        Nodo nuevo = new Nodo(cod,nom ,ap, curs, ph, h);
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
    
    public Nodo burbuja(Nodo primero, Nodo ultimo, boolean ordenAscendente) {
        if (primero != ultimo && primero != null && ultimo != null) {
            Nodo nodoInicial = primero;

            Nodo pivote = primero;
            Nodo i = primero;
            Nodo j = primero.sig;

            while (j != null) {
                if ((ordenAscendente && Integer.parseInt(j.codigo) <= Integer.parseInt(pivote.codigo)) ||
                    (!ordenAscendente && Integer.parseInt(j.codigo) >= Integer.parseInt(pivote.codigo))) {
                    i = i.sig;
                    intercambiarNodos(i, j);
                }
                j = j.sig;
            }

            intercambiarNodos(primero, i);

            if (nodoInicial == i) {
                primero = primero.sig;
            }

            Nodo nuevoPrimero = burbuja(primero, i, ordenAscendente);
            i.sig = burbuja(i.sig, ultimo, ordenAscendente);

            VerDatos();

            return nuevoPrimero;
        }

        return primero;
    }

    private void intercambiarNodos(Nodo nodo1, Nodo nodo2) {
        String tempCodigo = nodo1.codigo;
        String tempNombre = nodo1.nombre;
        String tempApellidos = nodo1.apellidos;
        String tempCursos = nodo1.cursos;
        int tempHoras = nodo1.horas;
        double tempPagoH = nodo1.pagoH;

        nodo1.codigo = nodo2.codigo;
        nodo1.nombre = nodo2.nombre;
        nodo1.apellidos = nodo2.apellidos;
        nodo1.cursos = nodo2.cursos;
        nodo1.horas = nodo2.horas;
        nodo1.pagoH = nodo2.pagoH;

        nodo2.codigo = tempCodigo;
        nodo2.nombre = tempNombre;
        nodo2.apellidos = tempApellidos;
        nodo2.cursos = tempCursos;
        nodo2.horas = tempHoras;
        nodo2.pagoH = tempPagoH;
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Lista_Profesores.txt"))) {
            Nodo actual = ini;
            while (actual != null) {
                writer.write(actual.codigo + "," + actual.nombre + "," + actual.apellidos + "," + actual.cursos + "," +
                             actual.horas + "," + actual.sueldo + "," + actual.pagoH + "," + actual.bonificacion + "," +
                             actual.montoF);
                writer.newLine();
                actual = actual.sig;
            }
            JOptionPane.showMessageDialog(null, "Datos guardados en Lista_Profesores.txt", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar en el archivo", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void cargarArchivoLista() {
        try (BufferedReader reader = new BufferedReader(new FileReader("Lista_Profesores.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 9) {
                    insertar(parts[0], parts[1], parts[2], parts[3], Double.parseDouble(parts[6]), Integer.parseInt(parts[4]));
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
            
            String rutaPDF = "HorasDeTrabajo_Listas.pdf";
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

            String textoTitulo = "Reporte acerca de las Horas de Trabajo de los Profesores";
            Paragraph titulo = new Paragraph(textoTitulo.toUpperCase(), titleFont);
            titulo.setAlignment(Element.ALIGN_CENTER); 
            titulo.setSpacingAfter(30f);
            document.add(titulo);

            PdfPTable pdfTable = new PdfPTable(9); 
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
            titleCell.setCellValue("Reporte acerca de las Horas de Trabajo de los Profesores");
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

            String rutaExcel = "HorasDeTrabajo_Listas.xlsx";
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
        jLabel1 = new javax.swing.JLabel();
        Codigo = new javax.swing.JLabel();
        TextCodigo = new javax.swing.JTextField();
        Nombres = new javax.swing.JLabel();
        TextNombre = new javax.swing.JTextField();
        Apellidos = new javax.swing.JLabel();
        TextApellido = new javax.swing.JTextField();
        Curso = new javax.swing.JLabel();
        TextCurso = new javax.swing.JTextField();
        Hora = new javax.swing.JLabel();
        TextPago = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        BtnAgregar = new javax.swing.JButton();
        BtnEliminar = new javax.swing.JButton();
        BtnActualizar = new javax.swing.JButton();
        BtnConsultar = new javax.swing.JButton();
        Hora1 = new javax.swing.JLabel();
        TextHoras = new javax.swing.JTextField();
        btnAscendente = new javax.swing.JButton();
        btnDescendente = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("HORAS DE TRABAJO - LISTAS ENLAZADAS SIMPLE");

        Codigo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Codigo.setForeground(new java.awt.Color(0, 51, 51));
        Codigo.setText("N°Codigo:");

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

        Nombres.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Nombres.setForeground(new java.awt.Color(0, 51, 51));
        Nombres.setText("Nombres:");

        TextNombre.setBackground(new java.awt.Color(255, 253, 253));
        TextNombre.setForeground(new java.awt.Color(0, 51, 51));
        TextNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextNombreKeyPressed(evt);
            }
        });

        Apellidos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Apellidos.setForeground(new java.awt.Color(0, 51, 51));
        Apellidos.setText("Apellidos:");

        TextApellido.setBackground(new java.awt.Color(255, 253, 253));
        TextApellido.setForeground(new java.awt.Color(0, 51, 51));
        TextApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextApellidoKeyPressed(evt);
            }
        });

        Curso.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Curso.setForeground(new java.awt.Color(0, 51, 51));
        Curso.setText("Curso:");

        TextCurso.setBackground(new java.awt.Color(255, 253, 253));
        TextCurso.setForeground(new java.awt.Color(0, 51, 51));
        TextCurso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextCursoKeyPressed(evt);
            }
        });

        Hora.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Hora.setForeground(new java.awt.Color(0, 51, 51));
        Hora.setText("Pago x Hora");

        TextPago.setBackground(new java.awt.Color(255, 253, 253));
        TextPago.setForeground(new java.awt.Color(0, 51, 51));
        TextPago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextPagoKeyPressed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 3, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 51));
        jLabel7.setText("Nota: Recordar que el pago por hora es depende de cada curso que estaba en su contrato.");

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

        Hora1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Hora1.setForeground(new java.awt.Color(0, 51, 51));
        Hora1.setText("Horas mensuales:");

        TextHoras.setBackground(new java.awt.Color(255, 253, 253));
        TextHoras.setForeground(new java.awt.Color(0, 51, 51));
        TextHoras.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextHorasKeyPressed(evt);
            }
        });

        btnAscendente.setIcon(new javax.swing.ImageIcon("C:\\Users\\jr860\\Desktop\\ProyectoJava\\Imagenes Java\\ascendente.png")); // NOI18N
        btnAscendente.setText("ASCENDENTE");
        btnAscendente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAscendenteActionPerformed(evt);
            }
        });

        btnDescendente.setIcon(new javax.swing.ImageIcon("C:\\Users\\jr860\\Desktop\\ProyectoJava\\Imagenes Java\\descendente.png")); // NOI18N
        btnDescendente.setText("DESCENDENTE");
        btnDescendente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDescendenteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(Curso, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(TextCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(26, 26, 26)
                                        .addComponent(Codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(TextCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(23, 23, 23)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(Nombres, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(TextNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(37, 37, 37)
                                        .addComponent(Apellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(TextApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(Hora, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(TextPago, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(64, 64, 64)
                                        .addComponent(Hora1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(TextHoras, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(22, 22, 22))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnAscendente)
                                .addGap(18, 18, 18)
                                .addComponent(btnDescendente)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(42, 42, 42))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(168, 168, 168))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(234, 234, 234)
                .addComponent(BtnAgregar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnEliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnActualizar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnConsultar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextCodigo)
                    .addComponent(Nombres, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextNombre)
                    .addComponent(Apellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextApellido))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Curso, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextCurso)
                    .addComponent(Hora, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextPago)
                    .addComponent(Hora1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextHoras))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(btnAscendente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDescendente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
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

    private void BtnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAgregarActionPerformed
        //capturando la informacion de los objetos e insertandolo en variables para enviar al metodo insertarInicio
        String cod = TextCodigo.getText();
        String nom = TextNombre.getText().toUpperCase();
        String ap = TextApellido.getText().toUpperCase();
        String cur = TextCurso.getText().toUpperCase();
        String pag = TextPago.getText();
        String h = TextHoras.getText();

        if (!cod.isEmpty() && !nom.isEmpty() && !ap.isEmpty() && !cur.isEmpty() && !pag.isEmpty() && !h.isEmpty()) {
            int co = Integer.parseInt(cod);
            double ph = Double.parseDouble(pag);
            int hor = Integer.parseInt(h);

            if (numPositivo(co)) {
                if (numPositivo((int) ph)) {
                    if (numPositivo(hor)) {
                        // Puedes continuar con la inserción del nodo y otras operaciones aquí
                        insertar(cod, nom, ap, cur, ph, hor);
                        LimpiarEntradas();
                        VerDatos();
                        if (Nodo.contador == tam-1) {
                            JOptionPane.showMessageDialog(null, "La lista está casi llena, contiene "+ Nodo.contador + " elementos de "+ tam);
                        }
                        
                        guardarEnArchivoTxt();
                        convertirJTableAExcel();
                        convertirJTableAPDF();
                        JOptionPane.showMessageDialog(null, "EXCEL y PDF actualizados correctamente");
                    } else {
                        JOptionPane.showMessageDialog(this, "No puede ingresar un número negativo en horas mensuales", "Error", JOptionPane.ERROR_MESSAGE);
                        TextHoras.setText("");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No puede ingresar un número negativo en el pago por hora", "Error", JOptionPane.ERROR_MESSAGE);
                    TextPago.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(this, "No puede ingresar un número negativo en el codigo", "Error", JOptionPane.ERROR_MESSAGE);
                TextCodigo.setText("");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_BtnAgregarActionPerformed

    private void BtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarActionPerformed
        // Mostrar un cuadro de diálogo de entrada para que el usuario ingrese el código a eliminar
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
        // Obtener el código ingresado en el campo de texto
        String codigoAActualizar = TextCodigo.getText().toUpperCase();;

        // Buscar el nodo en la lista
        Nodo nodoAActualizar = Buscar(ini, codigoAActualizar);

        if (nodoAActualizar != null) {
            // Actualizar los datos del nodo
            nodoAActualizar.codigo = TextCodigo.getText().toUpperCase();
            nodoAActualizar.nombre = TextNombre.getText().toUpperCase();
            nodoAActualizar.apellidos = TextApellido.getText().toUpperCase();
            nodoAActualizar.cursos = TextCurso.getText().toUpperCase();
            nodoAActualizar.pagoH = Double.parseDouble(TextPago.getText());
            nodoAActualizar.horas = Integer.parseInt(TextHoras.getText());

            // Limpiar los campos y refrescar la tabla
            LimpiarEntradas();
            VerDatos();
            guardarEnArchivoTxt();
            convertirJTableAExcel();
            convertirJTableAPDF();
            JOptionPane.showMessageDialog(null, "EXCEL y PDF actualizados correctamente");
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró el nodo con el código seleccionado.");
        }
    }//GEN-LAST:event_BtnActualizarActionPerformed

    private void BtnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnConsultarActionPerformed
        //Este bonton es  consultar, su funcion es por que por medio del codigo del trabajador este pueda consultar la demas información        
        String cod = JOptionPane.showInputDialog(this, "Ingrese un codigo por favor");
        if (cod.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Ingrese un codigo por favor");
        } else {
            //LLamada a la funcion que retorna la posicion del dato buscado
            mostrar = Buscar(ini, cod);
            //Verificando el puntero pFound para mostar la informacion buscada
            if (mostrar != null) {
                String mensaje = "Información encontrada:\n\n" +
                        "Nombres: " + mostrar.nombre + "\n" +
                        "Apellidos: " + mostrar.apellidos + "\n" +
                        "Curso: " + mostrar.cursos + "\n" +
                        "Pago por hora: " + mostrar.pagoH + "\n" +
                        "Horas trabajadas: " + mostrar.horas;

                JOptionPane.showMessageDialog(this, mensaje, "Información del Profesor", JOptionPane.INFORMATION_MESSAGE);
                
                int confirmacion = JOptionPane.showConfirmDialog(this, "¿Desea cambiar la información?", "Modificar la información", JOptionPane.YES_NO_OPTION);
                
                
                if (confirmacion == JOptionPane.YES_OPTION) {
                    TextCodigo.setText(mostrar.codigo);
                    TextNombre.setText(mostrar.nombre);
                    TextApellido.setText(mostrar.apellidos);
                    TextCurso.setText(mostrar.cursos);
                    TextPago.setText(String.valueOf(mostrar.pagoH));
                    TextHoras.setText(String.valueOf(mostrar.horas));
                }
            } else {
                JOptionPane.showMessageDialog(this, "El codigo:" + cod + " no esta en la lista.");
            }
        }
    }//GEN-LAST:event_BtnConsultarActionPerformed

    private void TextCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextCodigoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TextNombre.grabFocus();
        }
    }//GEN-LAST:event_TextCodigoKeyPressed

    private void TextNombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextNombreKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            TextApellido.grabFocus();
        }
    }//GEN-LAST:event_TextNombreKeyPressed

    private void TextApellidoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextApellidoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TextCurso.grabFocus();
        }
    }//GEN-LAST:event_TextApellidoKeyPressed

    private void TextCursoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextCursoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TextPago.grabFocus();
        }
    }//GEN-LAST:event_TextCursoKeyPressed

    private void TextPagoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextPagoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TextHoras.grabFocus();
        }
    }//GEN-LAST:event_TextPagoKeyPressed

    private void TextHorasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextHorasKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnAgregar.grabFocus();
            //capturando la informacion de los objetos e insertandolo en variables para enviar al metodo insertarInicio
            String cod = TextCodigo.getText();
            String nom = TextNombre.getText().toUpperCase();
            String ap = TextApellido.getText().toUpperCase();
            String cur = TextCurso.getText().toUpperCase();
            String pag = TextPago.getText();
            String h = TextHoras.getText();

            if (!cod.isEmpty() && !nom.isEmpty() && !ap.isEmpty() && !cur.isEmpty() && !pag.isEmpty() && !h.isEmpty()) {
                int co = Integer.parseInt(cod);
                double ph = Double.parseDouble(pag);
                int hor = Integer.parseInt(h);

                if (numPositivo(co)) {
                    if (numPositivo((int) ph)) {
                        if (numPositivo(hor)) {
                            // Puedes continuar con la inserción del nodo y otras operaciones aquí
                            insertar(cod, nom, ap, cur, ph, hor);
                            LimpiarEntradas();
                            VerDatos();
                            if (Nodo.contador == tam-1) {
                                JOptionPane.showMessageDialog(null, "La lista está casi llena, contiene "+ Nodo.contador + " elementos de "+ tam);
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "No puede ingresar un número negativo en horas mensuales", "Error", JOptionPane.ERROR_MESSAGE);
                            TextHoras.setText("");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "No puede ingresar un número negativo en el pago por hora", "Error", JOptionPane.ERROR_MESSAGE);
                        TextPago.setText("");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No puede ingresar un número negativo en el codigo", "Error", JOptionPane.ERROR_MESSAGE);
                    TextCodigo.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_TextHorasKeyPressed

    private void btnAscendenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAscendenteActionPerformed
        burbuja(ini, fin, true);
    }//GEN-LAST:event_btnAscendenteActionPerformed

    private void btnDescendenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDescendenteActionPerformed
        burbuja(ini, fin, false);
    }//GEN-LAST:event_btnDescendenteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Apellidos;
    private javax.swing.JButton BtnActualizar;
    private javax.swing.JButton BtnAgregar;
    private javax.swing.JButton BtnConsultar;
    private javax.swing.JButton BtnEliminar;
    private javax.swing.JLabel Codigo;
    private javax.swing.JLabel Curso;
    private javax.swing.JLabel Hora;
    private javax.swing.JLabel Hora1;
    private javax.swing.JLabel Nombres;
    private javax.swing.JTextField TextApellido;
    private javax.swing.JTextField TextCodigo;
    private javax.swing.JTextField TextCurso;
    private javax.swing.JTextField TextHoras;
    private javax.swing.JTextField TextNombre;
    private javax.swing.JTextField TextPago;
    private javax.swing.JButton btnAscendente;
    private javax.swing.JButton btnDescendente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
