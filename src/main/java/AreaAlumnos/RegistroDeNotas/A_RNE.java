package AreaAlumnos.RegistroDeNotas;

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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.DefaultTableCellRenderer;
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

public class A_RNE extends javax.swing.JPanel {

    Arbol algebra = new Arbol();
    Arbol aritmetica = new Arbol();
    Arbol geometria = new Arbol();
    Arbol trigonometria = new Arbol();

    Arbol quimica = new Arbol();
    Arbol fisica = new Arbol();
    Arbol biologia = new Arbol();

    Arbol lenguaje = new Arbol();
    Arbol literatura = new Arbol();
    Arbol historia = new Arbol();
    Arbol geografia = new Arbol();

    DefaultTableModel tabla2 = new DefaultTableModel();

    public A_RNE() {

        initComponents();
        //Creacionn de la tabla Hash
        tabla2 = new DefaultTableModel(data,cabecera);
        jTable1.setModel(tabla2); 
        DefaultTableCellRenderer centrar = new DefaultTableCellRenderer();
        centrar.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        for (int i = 0; i < cabecera.length; i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(centrar);
        }
        refrescarTabla();
        cargarArchivoTabla();
    }
    
    String[] cabecera={"Nombre","Apellidos","Curso","1°BIM","2°BIM"
                       ,"3°BIM","4°BIM","Promedio","Código"};
    String[][] data={};

    public void refrescarTabla() {
        jTable1.setModel(tabla2);
    }
    
    public void listar_algebra(boolean particular) {
        if (algebra.vacio() == true) {
            JOptionPane.showMessageDialog(null, "El arbol se encuentra actualmente vacio.");
        } else {
            DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
            if(particular==true){
                dt.setRowCount(0);
            }
            ArrayList<Nodo> listita = new ArrayList<>();
            listita = algebra.inorden();
            for (int i = listita.size(); i > 0; i--) {
                // hacer algo con arrayList.get(i)
                Nodo elemento = listita.get(i - 1);
                Object v[] = {elemento.getNombre(), elemento.getApellido(), elemento.getCurso(), elemento.getNota1(), elemento.getNota2(), elemento.getNota3(), elemento.getNota4(), elemento.getNotaF(), elemento.getCodigo()};
                dt.addRow(v);
            }
            algebra.vaciar_lista();
        }
    }

    public void listar_aritmetica(boolean particular) {
        if (aritmetica.vacio() == true) {
            JOptionPane.showMessageDialog(null, "El arbol se encuentra actualmente vacio.");
        } else {
            DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
            if(particular==true){
                dt.setRowCount(0);
            }
            ArrayList<Nodo> listita = new ArrayList<>();
            listita = aritmetica.inorden();
            for (int i = listita.size(); i > 0; i--) {
                // hacer algo con arrayList.get(i)
                Nodo elemento = listita.get(i - 1);
                Object v[] = {elemento.getNombre(), elemento.getApellido(), elemento.getCurso(), elemento.getNota1(), elemento.getNota2(), elemento.getNota3(), elemento.getNota4(), elemento.getNotaF(), elemento.getCodigo()};
                dt.addRow(v);
            }
            aritmetica.vaciar_lista();
        }
    }

    public void listar_geometria(boolean particular) {
        if (geometria.vacio() == true) {
            JOptionPane.showMessageDialog(null, "El arbol se encuentra actualmente vacio.");
        } else {
            DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
            if(particular==true){
                dt.setRowCount(0);
            }
            ArrayList<Nodo> listita = new ArrayList<>();
            listita = geometria.inorden();
            for (int i = listita.size(); i > 0; i--) {
                // hacer algo con arrayList.get(i)
                Nodo elemento = listita.get(i - 1);
                Object v[] = {elemento.getNombre(), elemento.getApellido(), elemento.getCurso(), elemento.getNota1(), elemento.getNota2(), elemento.getNota3(), elemento.getNota4(), elemento.getNotaF(), elemento.getCodigo()};
                dt.addRow(v);
            }
            geometria.vaciar_lista();
        }
    }

    public void listar_trigonometria(boolean particular) {
        if (trigonometria.vacio() == true) {
            JOptionPane.showMessageDialog(null, "El arbol se encuentra actualmente vacio.");
        } else {
            DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
            if(particular==true){
                dt.setRowCount(0);
            }
            ArrayList<Nodo> listita = new ArrayList<>();
            listita = trigonometria.inorden();
            for (int i = listita.size(); i > 0; i--) {
                // hacer algo con arrayList.get(i)
                Nodo elemento = listita.get(i - 1);
                Object v[] = {elemento.getNombre(), elemento.getApellido(), elemento.getCurso(), elemento.getNota1(), elemento.getNota2(), elemento.getNota3(), elemento.getNota4(), elemento.getNotaF(), elemento.getCodigo()};
                dt.addRow(v);
            }
            trigonometria.vaciar_lista();
        }
    }

    public void listar_quimica(boolean particular) {
        if (quimica.vacio() == true) {
            JOptionPane.showMessageDialog(null, "El arbol se encuentra actualmente vacio.");
        } else {
            DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
            if(particular==true){
                dt.setRowCount(0);
            }
            ArrayList<Nodo> listita = new ArrayList<>();
            listita = quimica.inorden();
            for (int i = listita.size(); i > 0; i--) {
                // hacer algo con arrayList.get(i)
                Nodo elemento = listita.get(i - 1);
                Object v[] = {elemento.getNombre(), elemento.getApellido(), elemento.getCurso(), elemento.getNota1(), elemento.getNota2(), elemento.getNota3(), elemento.getNota4(), elemento.getNotaF(), elemento.getCodigo()};
                dt.addRow(v);
            }
            quimica.vaciar_lista();
        }
    }

    public void listar_fisica(boolean particular) {
        if (fisica.vacio() == true) {
            JOptionPane.showMessageDialog(null, "El arbol se encuentra actualmente vacio.");
        } else {
            DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
            if(particular==true){
                dt.setRowCount(0);
            }
            ArrayList<Nodo> listita = new ArrayList<>();
            listita = fisica.inorden();
            for (int i = listita.size(); i > 0; i--) {
                // hacer algo con arrayList.get(i)
                Nodo elemento = listita.get(i - 1);
                Object v[] = {elemento.getNombre(), elemento.getApellido(), elemento.getCurso(), elemento.getNota1(), elemento.getNota2(), elemento.getNota3(), elemento.getNota4(), elemento.getNotaF(), elemento.getCodigo()};
                dt.addRow(v);
            }
            fisica.vaciar_lista();
        }
    }

    public void listar_biologia(boolean particular) {
        if (biologia.vacio() == true) {
            JOptionPane.showMessageDialog(null, "El arbol se encuentra actualmente vacio.");
        } else {
            DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
            if(particular==true){
                dt.setRowCount(0);
            }
            ArrayList<Nodo> listita = new ArrayList<>();
            listita = biologia.inorden();
            for (int i = listita.size(); i > 0; i--) {
                // hacer algo con arrayList.get(i)
                Nodo elemento = listita.get(i - 1);
                Object v[] = {elemento.getNombre(), elemento.getApellido(), elemento.getCurso(), elemento.getNota1(), elemento.getNota2(), elemento.getNota3(), elemento.getNota4(), elemento.getNotaF(), elemento.getCodigo()};
                dt.addRow(v);
            }
            biologia.vaciar_lista();
        }
    }

    public void listar_lenguaje(boolean particular) {
        if (lenguaje.vacio() == true) {
            JOptionPane.showMessageDialog(null, "El arbol se encuentra actualmente vacio.");
        } else {
            DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
            if(particular==true){
                dt.setRowCount(0);
            }
            ArrayList<Nodo> listita = new ArrayList<>();
            listita = lenguaje.inorden();
            for (int i = listita.size(); i > 0; i--) {
                // hacer algo con arrayList.get(i)
                Nodo elemento = listita.get(i - 1);
                Object v[] = {elemento.getNombre(), elemento.getApellido(), elemento.getCurso(), elemento.getNota1(), elemento.getNota2(), elemento.getNota3(), elemento.getNota4(), elemento.getNotaF(), elemento.getCodigo()};
                dt.addRow(v);
            }
            lenguaje.vaciar_lista();
        }
    }

    public void listar_literatura(boolean particular) {
        if (literatura.vacio() == true) {
            JOptionPane.showMessageDialog(null, "El arbol se encuentra actualmente vacio.");
        } else {
            DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
            if(particular==true){
                dt.setRowCount(0);
            }
            ArrayList<Nodo> listita = new ArrayList<>();
            listita = literatura.inorden();
            for (int i = listita.size(); i > 0; i--) {
                // hacer algo con arrayList.get(i)
                Nodo elemento = listita.get(i - 1);
                Object v[] = {elemento.getNombre(), elemento.getApellido(), elemento.getCurso(), elemento.getNota1(), elemento.getNota2(), elemento.getNota3(), elemento.getNota4(), elemento.getNotaF(), elemento.getCodigo()};
                dt.addRow(v);
            }
            literatura.vaciar_lista();
        }
    }

    public void listar_historia(boolean particular) {
        if (historia.vacio() == true) {
            JOptionPane.showMessageDialog(null, "El arbol se encuentra actualmente vacio.");
        } else {
            DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
            if(particular==true){
                dt.setRowCount(0);
            }
            ArrayList<Nodo> listita = new ArrayList<>();
            listita = historia.inorden();
            for (int i = listita.size(); i > 0; i--) {
                // hacer algo con arrayList.get(i)
                Nodo elemento = listita.get(i - 1);
                Object v[] = {elemento.getNombre(), elemento.getApellido(), elemento.getCurso(), elemento.getNota1(), elemento.getNota2(), elemento.getNota3(), elemento.getNota4(), elemento.getNotaF(), elemento.getCodigo()};
                dt.addRow(v);
            }
            historia.vaciar_lista();
        }
    }

    public void listar_geografia(boolean particular) {
        if (geografia.vacio() == true) {
            JOptionPane.showMessageDialog(null, "El arbol se encuentra actualmente vacio.");
        } else {
            DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
            if(particular==true){
                dt.setRowCount(0);
            }
            ArrayList<Nodo> listita = new ArrayList<>();
            listita = geografia.inorden();
            for (int i = listita.size(); i > 0; i--) {
                Nodo elemento = listita.get(i - 1);
                Object v[] = {elemento.getNombre(), elemento.getApellido(), elemento.getCurso(), elemento.getNota1(), elemento.getNota2(), elemento.getNota3(), elemento.getNota4(), elemento.getNotaF(), elemento.getCodigo()};
                dt.addRow(v);
            }
            geografia.vaciar_lista();
        }
    }
    
    private void cargarArchivoTabla() {
        try (BufferedReader reader = new BufferedReader(new FileReader("Arbol.txt"))) {
            String primeraLinea = reader.readLine();
            String[] encabezados = primeraLinea.split(",");
            tabla2.setColumnIdentifiers(encabezados);

            tabla2.setRowCount(0);

            DefaultTableCellRenderer centrar = new DefaultTableCellRenderer();
            centrar.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);

            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                tabla2.addRow(datos);
            }

            // Aplicar centrado a todas las columnas después de cargar los datos
            for (int i = 0; i < tabla2.getColumnCount(); i++) {
                jTable1.getColumnModel().getColumn(i).setCellRenderer(centrar);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar desde el archivo", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void guardarEnArchivoTxt() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Arbol.txt"))) {
            int numFilas = tabla2.getRowCount();
            int numColumnas = tabla2.getColumnCount();

            // Escribir cabeceras en la primera línea
            for (int columna = 0; columna < numColumnas; columna++) {
                writer.write(tabla2.getColumnName(columna));
                if (columna < numColumnas - 1) {
                    writer.write(",");
                }
            }
            writer.newLine();

            // Escribir datos en el archivo
            for (int fila = 0; fila < numFilas; fila++) {
                for (int columna = 0; columna < numColumnas; columna++) {
                    Object valor = tabla2.getValueAt(fila, columna);
                    writer.write(valor != null ? valor.toString() : "");
                    if (columna < numColumnas - 1) {
                        writer.write(",");
                    }
                }
                writer.newLine();
            }

            JOptionPane.showMessageDialog(null, "Datos guardados en Arbol.txt", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar en el archivo", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    public void convertirJTableAPDF() {
        Document document = new Document();
        try {
            
            String rutaPDF = "RegistroNota_Arbol.pdf";
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

            String textoTitulo = "Reporte acerca de los Registros de Notas";
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
            Sheet sheet = workbook.createSheet("Arbol");
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
            titleCell.setCellValue("Reporte acerca de los Registros de Notas");
            titleCell.setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(filaInicio, filaInicio, columnaInicio, columnaInicio + tabla2.getColumnCount() - 1));

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
            for (int i = 0; i < tabla2.getColumnCount(); i++) {
                Cell cell = headerRow.createCell(columnaInicio + i);
                cell.setCellValue(tabla2.getColumnName(i));
                cell.setCellStyle(headerStyle);
            }

            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setAlignment(HorizontalAlignment.CENTER);
            dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            dataStyle.setBorderTop(BorderStyle.THIN);
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);

            for (int i = 0; i < tabla2.getRowCount(); i++) {
                Row row = sheet.createRow(filaInicio + 3 + i);
                for (int j = 0; j < tabla2.getColumnCount(); j++) {
                    Cell cell = row.createCell(columnaInicio + j);
                    cell.setCellValue(tabla2.getValueAt(i, j).toString());
                    cell.setCellStyle(dataStyle);
                }
            }
            
            for (int i = 0; i < tabla2.getColumnCount(); i++) {
                sheet.setColumnWidth(i + 2, 15 * 256); 
            }

            String rutaExcel = "RegistroNota_Arbol.xlsx";
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
        jLabel2 = new javax.swing.JLabel();
        TextNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        TextApellido = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        TextCurso = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        Text1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        Text2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        Text3 = new javax.swing.JTextField();
        BtnAgregar = new javax.swing.JButton();
        BtnEliminar = new javax.swing.JButton();
        BtnActualizar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        Text4 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        BtnBuscar = new javax.swing.JButton();
        BtnMostrar = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("REGISTRO DE NOTA ESTUDIANTIL - ÁRBOL");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 51));
        jLabel2.setText("Nombre:");

        TextNombre.setBackground(new java.awt.Color(255, 253, 253));
        TextNombre.setForeground(new java.awt.Color(0, 51, 51));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 51));
        jLabel3.setText("Apellido:");

        TextApellido.setBackground(new java.awt.Color(255, 253, 253));
        TextApellido.setForeground(new java.awt.Color(0, 51, 51));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 51));
        jLabel4.setText("Curso:");

        TextCurso.setBackground(new java.awt.Color(255, 253, 253));
        TextCurso.setForeground(new java.awt.Color(0, 51, 51));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 51));
        jLabel5.setText("1°Bimestre:");

        Text1.setBackground(new java.awt.Color(255, 253, 253));
        Text1.setForeground(new java.awt.Color(0, 51, 51));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 51));
        jLabel6.setText("2°Bimestre");

        Text2.setBackground(new java.awt.Color(255, 253, 253));
        Text2.setForeground(new java.awt.Color(0, 51, 51));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 51));
        jLabel7.setText("3°Bimestre");

        Text3.setBackground(new java.awt.Color(255, 253, 253));
        Text3.setForeground(new java.awt.Color(0, 51, 51));

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

        Text4.setBackground(new java.awt.Color(255, 253, 253));
        Text4.setForeground(new java.awt.Color(0, 51, 51));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 51));
        jLabel8.setText("4°Bimestre");

        BtnBuscar.setIcon(new javax.swing.ImageIcon("C:\\Users\\jr860\\Desktop\\ProyectoJava\\Imagenes Java\\buscar.png")); // NOI18N
        BtnBuscar.setText("BUSCAR");
        BtnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscarActionPerformed(evt);
            }
        });

        BtnMostrar.setIcon(new javax.swing.ImageIcon("C:\\Users\\jr860\\Desktop\\ProyectoJava\\Imagenes Java\\mostrar.png")); // NOI18N
        BtnMostrar.setText("MOSTRAR");
        BtnMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnMostrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(282, 282, 282))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Text1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(TextApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(150, 150, 150)
                                        .addComponent(TextCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(Text2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(58, 58, 58)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Text3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(22, 22, 22)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(Text4, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TextNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(360, 360, 360)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(169, 169, 169)
                        .addComponent(BtnAgregar)
                        .addGap(18, 18, 18)
                        .addComponent(BtnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(BtnActualizar)
                        .addGap(18, 18, 18)
                        .addComponent(BtnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(BtnMostrar)))
                .addContainerGap(132, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(TextNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(TextApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(TextCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(Text1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(Text2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Text3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Text4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnMostrar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
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
        String nombre = String.valueOf(TextNombre.getText());
        String apellido = String.valueOf(TextApellido.getText());
        String curso = String.valueOf(TextCurso.getText());
        int b1 = Integer.parseInt(Text1.getText());
        int b2 = Integer.parseInt(Text2.getText());
        int b3 = Integer.parseInt(Text3.getText());
        int b4 = Integer.parseInt(Text4.getText());
        double pf = ((b1 + b2 + b3 + b4) * 1.0) / 4;
        int indice = 0;
        int numcod = b1 + b2 + b3 + b4;
        String codigo = "" + nombre.charAt(0) + nombre.charAt(1) + apellido.charAt(0) + apellido.charAt(1) + numcod;

        String datito = "" + b1 + " " + b2 + " " + b3 + " " + b4 + " " + pf + " " + curso + " " + nombre + " " + apellido + " " + codigo;

        if (curso.equalsIgnoreCase("algebra")) {
            indice = 1;
        } else {
            if (curso.equalsIgnoreCase("aritmetica")) {
                indice = 2;
            } else {
                if (curso.equalsIgnoreCase("geometria")) {
                    indice = 3;
                } else {
                    if (curso.equalsIgnoreCase("trigonometria")) {
                        indice = 4;
                    } else {
                        if (curso.equalsIgnoreCase("quimica")) {
                            indice = 5;
                        } else {
                            if (curso.equalsIgnoreCase("fisica")) {
                                indice = 6;
                            } else {
                                if (curso.equalsIgnoreCase("biologia")) {
                                    indice = 7;
                                } else {
                                    if (curso.equalsIgnoreCase("lenguaje")) {
                                        indice = 8;
                                    } else {
                                        if (curso.equalsIgnoreCase("literatura")) {
                                            indice = 9;
                                        } else {
                                            if (curso.equalsIgnoreCase("historia")) {
                                                indice = 10;
                                            } else {
                                                if (curso.equalsIgnoreCase("geografia")) {
                                                    indice = 11;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (indice != 0) {
            if (b1 <= 20 && b1 >= 0 && b2 <= 20 && b2 >= 0 && b3 <= 20 && b3 >= 0 && b4 <= 20 && b4 >= 0) {

                //arbol.insertar(datito);
                if (indice == 1) {
                    algebra.insertar(datito);
                    listar_algebra(true);
                } else {
                    if (indice == 2) {
                        aritmetica.insertar(datito);
                        listar_aritmetica(true);
                    } else {
                        if (indice == 3) {
                            geometria.insertar(datito);
                            listar_geometria(true);
                        } else {
                            if (indice == 4) {
                                trigonometria.insertar(datito);
                                listar_trigonometria(true);
                            } else {
                                if (indice == 5) {
                                    quimica.insertar(datito);
                                    listar_quimica(true);
                                } else {
                                    if (indice == 6) {
                                        fisica.insertar(datito);
                                        listar_fisica(true);
                                    } else {
                                        if (indice == 7) {
                                            biologia.insertar(datito);
                                            listar_biologia(true);
                                        } else {
                                            if (indice == 8) {
                                                lenguaje.insertar(datito);
                                                listar_lenguaje(true);
                                            } else {
                                                if (indice == 9) {
                                                    literatura.insertar(datito);
                                                    listar_literatura(true);
                                                } else {
                                                    if (indice == 10) {
                                                        historia.insertar(datito);
                                                        listar_historia(true);
                                                    } else {
                                                        if (indice == 11) {
                                                            geografia.insertar(datito);
                                                            listar_geografia(true);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                //preorden(arbol.getRaiz());
                TextNombre.setText("");
                TextApellido.setText("");
                TextCurso.setText("");
                Text1.setText("");
                Text2.setText("");
                Text3.setText("");
                Text4.setText("");
                guardarEnArchivoTxt();
                convertirJTableAExcel();
                convertirJTableAPDF();
                JOptionPane.showMessageDialog(null, "EXCEL y PDF actualizados correctamente");
            } else {
                if (b1 > 20 || b1 < 0) {
                    JOptionPane.showMessageDialog(null, "La nota ingresada del 1er bimestre no esta entre los parametros de 0 y 20.");
                    Text1.setText("");
                }
                if (b2 > 20 || b2 < 0) {
                    JOptionPane.showMessageDialog(null, "La nota ingresada del 2er bimestre no esta entre los parametros de 0 y 20.");
                    Text2.setText("");
                }
                if (b3 > 20 || b3 < 0) {
                    JOptionPane.showMessageDialog(null, "La nota ingresada del 3er bimestre no esta entre los parametros de 0 y 20.");
                    Text3.setText("");
                }
                if (b4 > 20 || b4 < 0) {
                    JOptionPane.showMessageDialog(null, "La nota ingresada del 4er bimestre no esta entre los parametros de 0 y 20.");
                    Text4.setText("");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "El curso ingresado no existe");
            TextCurso.setText("");
        }
    }//GEN-LAST:event_BtnAgregarActionPerformed

    private void BtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarActionPerformed
        
        DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
        int selectedRow = jTable1.getSelectedRow();
        
        String nombre = (String) jTable1.getValueAt(selectedRow, 0);
        String apellido = (String) jTable1.getValueAt(selectedRow, 1);
        String curso = (String) jTable1.getValueAt(selectedRow, 2);
        int b1 = (int) jTable1.getValueAt(selectedRow, 3);
        int b2 = (int) jTable1.getValueAt(selectedRow, 4);
        int b3 = (int) jTable1.getValueAt(selectedRow, 5);
        int b4 = (int) jTable1.getValueAt(selectedRow, 6);
        double pf = (double) jTable1.getValueAt(selectedRow, 7);
        String codigo = (String) jTable1.getValueAt(selectedRow, 8);

        String datito = "" + b1 + " " + b2 + " " + b3 + " " + b4 + " " + pf + " " + curso + " " + nombre + " " + apellido + " " + codigo;
        
        if (selectedRow != -1) {
            if (curso.equalsIgnoreCase("algebra")) {
                algebra.eliminar(datito);
            } else {
                if (curso.equalsIgnoreCase("aritmetica")) {
                    aritmetica.eliminar(datito);
                } else {
                    if (curso.equalsIgnoreCase("geometria")) {
                        geometria.eliminar(datito);
                    } else {
                        if (curso.equalsIgnoreCase("trigonometria")) {
                            trigonometria.eliminar(datito);
                        } else {
                            if (curso.equalsIgnoreCase("quimica")) {
                                quimica.eliminar(datito);
                            } else {
                                if (curso.equalsIgnoreCase("fisica")) {
                                    fisica.eliminar(datito);
                                } else {
                                    if (curso.equalsIgnoreCase("biologia")) {
                                        biologia.eliminar(datito);
                                    } else {
                                        if (curso.equalsIgnoreCase("lenguaje")) {
                                            lenguaje.eliminar(datito);
                                        } else {
                                            if (curso.equalsIgnoreCase("literatura")) {
                                                literatura.eliminar(datito);
                                            } else {
                                                if (curso.equalsIgnoreCase("historia")) {
                                                    historia.eliminar(datito);
                                                } else {
                                                    if (curso.equalsIgnoreCase("geografia")) {
                                                        geografia.eliminar(datito);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "El elemento seleccionado se ha eliminado con exito");
            tabla2.removeRow(selectedRow);
            guardarEnArchivoTxt();
            convertirJTableAExcel();
            convertirJTableAPDF();
            JOptionPane.showMessageDialog(null, "EXCEL y PDF actualizados correctamente");
        }
    }//GEN-LAST:event_BtnEliminarActionPerformed

    private void BtnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnActualizarActionPerformed
        DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow >= 0) {
            String cod = (String) jTable1.getValueAt(selectedRow, 8);
            String curs = (String) jTable1.getValueAt(selectedRow, 2);
            String nombre = TextNombre.getText();
            String apellido = TextApellido.getText();
            String curso = TextCurso.getText();
            int b1 = Integer.parseInt(Text1.getText());
            int b2 = Integer.parseInt(Text2.getText());
            int b3 = Integer.parseInt(Text3.getText());
            int b4 = Integer.parseInt(Text4.getText());
            int numcod = b1 + b2 + b3 + b4;
            String codigo = "" + nombre.charAt(0) + nombre.charAt(1) + apellido.charAt(0) + apellido.charAt(1) + numcod;
            if (b1 <= 20 && b1 >= 0 && b2 <= 20 && b2 >= 0 && b3 <= 20 && b3 >= 0 && b4 <= 20 && b4 >= 0) {
                double pf = (b1 + b2 + b3 + b4) / 4;
                String datito = "" + b1 + " " + b2 + " " + b3 + " " + b4 + " " + pf + " " + curso + " " + nombre + " " + apellido + " " + codigo;
                if (curso.equalsIgnoreCase(curs)) {
                    Nodo nodo = new Nodo("");
                    if (curso.equalsIgnoreCase("algebra")) {
                        algebra.actualizar(datito, cod);
                    } else {
                        if (curso.equalsIgnoreCase("aritmetica")) {
                            aritmetica.actualizar(datito, cod);
                        } else {
                            if (curso.equalsIgnoreCase("geometria")) {
                                geometria.actualizar(datito, cod);
                            } else {
                                if (curso.equalsIgnoreCase("trigonometria")) {
                                    trigonometria.actualizar(datito, cod);
                                } else {
                                    if (curso.equalsIgnoreCase("quimica")) {
                                        quimica.actualizar(datito, cod);
                                    } else {
                                        if (curso.equalsIgnoreCase("fisica")) {
                                            fisica.actualizar(datito, cod);
                                        } else {
                                            if (curso.equalsIgnoreCase("biologia")) {
                                                biologia.actualizar(datito, cod);
                                            } else {
                                                if (curso.equalsIgnoreCase("lenguaje")) {
                                                    lenguaje.actualizar(datito, cod);
                                                } else {
                                                    if (curso.equalsIgnoreCase("literatura")) {
                                                        literatura.actualizar(datito, cod);
                                                    } else {
                                                        if (curso.equalsIgnoreCase("historia")) {
                                                            historia.actualizar(datito, cod);
                                                        } else {
                                                            if (curso.equalsIgnoreCase("geografia")) {
                                                                geografia.actualizar(datito, cod);
                                                            } else {
                                                                nodo = new Nodo("");
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    dt.setValueAt(nombre, selectedRow, 0);
                    dt.setValueAt(apellido, selectedRow, 1);
                    dt.setValueAt(curso, selectedRow, 2);
                    dt.setValueAt(b1, selectedRow, 3);
                    dt.setValueAt(b2, selectedRow, 4);
                    dt.setValueAt(b3, selectedRow, 5);
                    dt.setValueAt(b4, selectedRow, 6);
                    dt.setValueAt(pf, selectedRow, 7);
                    dt.setValueAt(codigo, selectedRow, 8);
                    guardarEnArchivoTxt();
                    convertirJTableAExcel();
                    convertirJTableAPDF();
                    JOptionPane.showMessageDialog(null, "EXCEL y PDF actualizados correctamente");
                } else {
                    JOptionPane.showMessageDialog(null, "No es posible actualizar el curso, unicamente nombres, apellidos y notas.");
                }
                // Actualiza el elemento en la lista

                // Actualiza la fila en la tabla
                TextNombre.setText("");
                TextApellido.setText("");
                TextCurso.setText("");
                Text1.setText("");
                Text2.setText("");
                Text3.setText("");
                Text4.setText("");
            } else {
                if (b1 > 20 || b1 < 0) {
                    JOptionPane.showMessageDialog(null, "La nota ingresada del 1er bimestre no esta entre los parametros de 0 y 20.");
                    Text1.setText("");
                }
                if (b2 > 20 || b2 < 0) {
                    JOptionPane.showMessageDialog(null, "La nota ingresada del 2er bimestre no esta entre los parametros de 0 y 20.");
                    Text2.setText("");
                }
                if (b3 > 20 || b3 < 0) {
                    JOptionPane.showMessageDialog(null, "La nota ingresada del 3er bimestre no esta entre los parametros de 0 y 20.");
                    Text3.setText("");
                }
                if (b4 > 20 || b4 < 0) {
                    JOptionPane.showMessageDialog(null, "La nota ingresada del 4er bimestre no esta entre los parametros de 0 y 20.");
                    Text4.setText("");
                }
            }
        }
    }//GEN-LAST:event_BtnActualizarActionPerformed

    private void BtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscarActionPerformed

        String cod = JOptionPane.showInputDialog("Ingrese el codigo del estudiante a buscar");
        String curso = JOptionPane.showInputDialog("Ingrese el curso del estudiante a buscar");
        Nodo nodo = new Nodo("");

        if (curso.equalsIgnoreCase("algebra")) {
            nodo = algebra.buscar(cod);
        } else {
            if (curso.equalsIgnoreCase("aritmetica")) {
                nodo = aritmetica.buscar(cod);
            } else {
                if (curso.equalsIgnoreCase("geometria")) {
                    nodo = geometria.buscar(cod);
                } else {
                    if (curso.equalsIgnoreCase("trigonometria")) {
                        nodo = trigonometria.buscar(cod);
                    } else {
                        if (curso.equalsIgnoreCase("quimica")) {
                            nodo = quimica.buscar(cod);
                        } else {
                            if (curso.equalsIgnoreCase("fisica")) {
                                nodo = fisica.buscar(cod);
                            } else {
                                if (curso.equalsIgnoreCase("biologia")) {
                                    nodo = biologia.buscar(cod);
                                } else {
                                    if (curso.equalsIgnoreCase("lenguaje")) {
                                        nodo = lenguaje.buscar(cod);
                                    } else {
                                        if (curso.equalsIgnoreCase("literatura")) {
                                            nodo = literatura.buscar(cod);
                                        } else {
                                            if (curso.equalsIgnoreCase("historia")) {
                                                nodo = historia.buscar(cod);
                                            } else {
                                                if (curso.equalsIgnoreCase("geografia")) {
                                                    nodo = geografia.buscar(cod);
                                                } else {
                                                    nodo = new Nodo("");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (nodo == null) {
            JOptionPane.showMessageDialog(null, "No se encontro el elemento buscado");
        } else {
            DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
            dt.setRowCount(0);
            JOptionPane.showMessageDialog(null, "Se encontro el elemento buscado");
            Object v[] = {nodo.getNombre(), nodo.getApellido(), nodo.getCurso(), nodo.getNota1(), nodo.getNota2(), nodo.getNota3(), nodo.getNota4(), nodo.getNotaF(), nodo.getCodigo()};
            dt.addRow(v);
            TextNombre.setText(nodo.getNombre());
            TextApellido.setText(nodo.getApellido());
            TextCurso.setText(nodo.getCurso());
            Text1.setText(nodo.getNota1() + "");
            Text2.setText(nodo.getNota2() + "");
            Text3.setText(nodo.getNota3() + "");
            Text4.setText(nodo.getNota4() + "");
        }
    }//GEN-LAST:event_BtnBuscarActionPerformed

    private void BtnMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnMostrarActionPerformed
        if(algebra.vacio()==false){
            listar_algebra(true);
        }
        if(aritmetica.vacio()==false){
            listar_aritmetica(false);
        }
        if(geometria.vacio()==false){
            listar_geometria(false);
        }
        if(trigonometria.vacio()==false){
            listar_trigonometria(false);
        }
        if(quimica.vacio()==false){
            listar_quimica(false);
        }
        if(fisica.vacio()==false){
            listar_fisica(false);
        }
        if(biologia.vacio()==false){
            listar_biologia(false);
        }
        if(lenguaje.vacio()==false){
            listar_lenguaje(false);
        }
        if(literatura.vacio()==false){
            listar_literatura(false);
        }
        if(historia.vacio()==false){
            listar_historia(false);
        }
        if(geografia.vacio()==false){
            listar_geografia(false);
        }
    }//GEN-LAST:event_BtnMostrarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnActualizar;
    private javax.swing.JButton BtnAgregar;
    private javax.swing.JButton BtnBuscar;
    private javax.swing.JButton BtnEliminar;
    private javax.swing.JButton BtnMostrar;
    private javax.swing.JTextField Text1;
    private javax.swing.JTextField Text2;
    private javax.swing.JTextField Text3;
    private javax.swing.JTextField Text4;
    private javax.swing.JTextField TextApellido;
    private javax.swing.JTextField TextCurso;
    private javax.swing.JTextField TextNombre;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
